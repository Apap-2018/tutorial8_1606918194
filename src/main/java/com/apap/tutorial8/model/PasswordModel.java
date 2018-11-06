package com.apap.tutorial8.model;

public class PasswordModel {
	private String oldPassword;
	private String newPassword;
	private String confPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConPassword() {
		return confPassword;
	}
	public void setConPassword(String conPassword) {
		this.confPassword = conPassword;
	}
}
