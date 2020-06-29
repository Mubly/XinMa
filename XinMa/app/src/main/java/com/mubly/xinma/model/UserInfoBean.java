package com.mubly.xinma.model;

import com.mubly.xinma.utils.StringUtils;

public class UserInfoBean {
    public String CompanyID;
    private String Company;

    private String UserID;
    private String UserSN;


    private String Phone;
    private String FullName;

    private String Email;
    private int IsAutoNo;

    private int Status;

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserSN() {
        return UserSN;
    }

    public void setUserSN(String userSN) {
        UserSN = userSN;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFullName() {
        return StringUtils.notNull(FullName);
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getIsAutoNo() {
        return IsAutoNo;
    }

    public void setIsAutoNo(int isAutoNo) {
        IsAutoNo = isAutoNo;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
