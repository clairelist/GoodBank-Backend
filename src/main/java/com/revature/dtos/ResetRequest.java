package com.revature.dtos;

public class ResetRequest { //TODO: ADD SECURITY ANSWER --> when making a request, check that passed in sec answer = sec answer
                            //when clicking reset pass, should perform a GET request that gets the user's sec question
                            //and then FINALLY when click 'confirm', the sec question is passed and if it matches, we allow
                            //password reset.
    private String email;
    private String password;
    private String securityAnswer;

    public ResetRequest(){}

    public ResetRequest(String email, String password, String securityAnswer){
        this.email = email;
        this.password = password;
        this.securityAnswer = securityAnswer;
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
}
