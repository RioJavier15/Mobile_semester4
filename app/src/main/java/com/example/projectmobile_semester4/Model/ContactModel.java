package com.example.projectmobile_semester4.Model;

public class ContactModel {
    private int imageResId;
    private String contactName;
    private String phoneNumber;
    private String message;

    public ContactModel(int imageResId, String contactName, String phoneNumber, String message) {
        this.imageResId = imageResId;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }
}

