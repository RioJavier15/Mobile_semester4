package com.example.projectmobile_semester4.Model;

public class ContactModel {
    private int imageResId;
    private String contactName;

    public ContactModel(int imageResId, String contactName) {
        this.imageResId = imageResId;
        this.contactName = contactName;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getContactName() {
        return contactName;
    }
}
