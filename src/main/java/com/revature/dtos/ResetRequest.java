package com.revature.dtos;

public class ResetRequest {

    //TODO:
    //I NEED TO GET UNFUCKED!
    //ADD A USER ID, THAT IS THEN USED TO CONFIRM USER INSTEAD OF THE PASSWORD FIELD HERE!
    //THIS USER ID IS _NOT_ AN INT, BUT A CHARSEQUENCE, SO IT PLAYS NICE WITH OUR DECODER!
    private String email;
    private String password;
    private String confirmPassword;

    private String securityAnswer;

    public ResetRequest(){}

    public ResetRequest(String email, String password, String confirmPassword, String securityAnswer){
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
