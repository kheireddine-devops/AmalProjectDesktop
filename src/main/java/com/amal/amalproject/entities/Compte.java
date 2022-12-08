package com.amal.amalproject.entities;

public class Compte {
    private int compteId;
    private String username;
    private String password;
    private String role;
    private String status;

    private String tempResetPassword;
    private String tempValidatePhone;
    private String tempValidateMail;

    private String photo;
    private String phone;
    private String email;


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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /* End Section Getters & Setters */


    /* Start Section ToString */

    @Override
    public String toString() {
        return "Compte{" +
                "compteId=" + compteId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                ", tempResetPassword='" + tempResetPassword + '\'' +
                ", tempValidatePhone='" + tempValidatePhone + '\'' +
                ", tempValidateMail='" + tempValidateMail + '\'' +
                ", photo='" + photo + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    /* End Section ToString */
}
