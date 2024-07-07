package com.studyprogramming.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserLoginPayload {

    //@Size(min = 8, message = "User Name phải từ 8 ký tự trở lên")
    //private String  userName;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    private String  email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getUserName() {
//        return  userName;
//    }
//
//    public void setUserName(String  userName) {
//        this. userName =  userName;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
