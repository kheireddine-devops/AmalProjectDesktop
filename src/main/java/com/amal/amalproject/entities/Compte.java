package com.amal.amalproject.entities;

public class Compte {
    private int compteId;
    private String login;
    private String password;
    private String role;
    private String status;

    private String tempResetPassword;
    private String tempValidatePhone;
    private String tempValidateMail;


    /* Start Section Constructor */
    public Compte() {
    }
    /* End Section Constructor */


    /* Start Section Getters & Setters */
    public int getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTempResetPassword() {
        return tempResetPassword;
    }

    public void setTempResetPassword(String tempResetPassword) {
        this.tempResetPassword = tempResetPassword;
    }

    public String getTempValidatePhone() {
        return tempValidatePhone;
    }

    public void setTempValidatePhone(String tempValidatePhone) {
        this.tempValidatePhone = tempValidatePhone;
    }

    public String getTempValidateMail() {
        return tempValidateMail;
    }

    public void setTempValidateMail(String tempValidateMail) {
        this.tempValidateMail = tempValidateMail;
    }
    /* End Section Getters & Setters */


    /* Start Section ToString */

    @Override
    public String toString() {
        return "Compte{" +
                "compteId=" + compteId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    /* End Section ToString */
}
