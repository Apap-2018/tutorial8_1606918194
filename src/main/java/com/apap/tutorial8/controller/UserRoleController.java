package com.apap.tutorial8.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tutorial8.model.PasswordModel;
import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	private boolean validatePassword(String password) {
         if (password.length() >= 8 && Pattern.compile("[0-9]").matcher(password).find() && Pattern.compile("[a-zA-Z]").matcher(password).find()) {
            return true;
         }
       
         return false;
     }
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	private ModelAndView addUserSubmit(@ModelAttribute UserRoleModel user, RedirectAttributes redirect) {
		String message = "";
		if (this.validatePassword(user.getPassword())) {
			userService.addUser(user);
			message = null;
		}
		
		else {
			message = "Password tidak mengandung angka, huruf, dan minimal 8 karakter";
		}
   
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		redirect.addFlashAttribute("msg", message);
		return modelAndView;
 }
	
	@RequestMapping("/updatePassword")
	public String updatePassword() {
		return "update-password";
	}
	
	@RequestMapping(value="/passwordSubmit",method=RequestMethod.POST)
	public ModelAndView updatePasswordSubmit(@ModelAttribute PasswordModel pass, Model model, RedirectAttributes redir) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserRoleModel user = userService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		String message = "";
		String pattern = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,}";
		
		if (pass.getConPassword().equals(pass.getNewPassword())) {
			if (passwordEncoder.matches(pass.getOldPassword(), user.getPassword())) {
				if (pass.getNewPassword().matches(pattern)) {
					userService.changePassword(user, pass.getNewPassword());
					message = "Password Anda berhasil diubah";
				}
				
				else {
					message = "Password Anda tidak mengandung huruf atau angka atau kurang dari 8 karakter";
				}
			}
			
			else {
				message = "Password lama Anda salah";
			}
			
		}
		
		else {
			message = "Password Baru Tidak Sesuai";
		}
		
		
		ModelAndView modelAndView = new ModelAndView("redirect:/user/updatePassword");
		redir.addFlashAttribute("msg", message);
		return modelAndView;
	}
}
