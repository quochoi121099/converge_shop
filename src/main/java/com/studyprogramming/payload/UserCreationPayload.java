package com.studyprogramming.payload;

import com.studyprogramming.entity.enums.EGender;
import com.studyprogramming.entity.enums.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class UserCreationPayload {
    @Size(min=4,max=30, message = "Tên phải từ 4 đến 30 ký tự")
    private String userName;

    @Size(min = 9, max = 11, message = "Số điện thoại phải từ 9 đến 11 số")
    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "")
    private String phoneNumber;

    @Size(min = 1, message = "Không được để trống")
    @Email(message = "Nhập sai email")
    private String email;

    @Size(min = 1, message = "Không được để trống")
    private String address;

    @Size(min = 8, message = "Password phải từ 8 ký tự trở lên")
    private String password;

    private EGender gender;

    private ERole role;

    @Size(min = 1, message = "Không được để trống")
    private String firstName;

    @Size(min = 1, message = "Không được để trống")
    private String lastName;

    private LocalDate dateOfBirth;

    private MultipartFile[] Images;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public MultipartFile[] getImages() {
        return Images;
    }

    public void setImages(MultipartFile[] images) {
        Images = images;
    }
}
