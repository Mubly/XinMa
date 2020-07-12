package com.mubly.xinma.model;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categroyInfo")
public class CategoryInfoBean {

    /**
     * CategoryInfoID : c24d63a2-814d-46ac-9c44-3a60cdea9111
     * CategoryID : c24d63a2-814d-46ac-9c44-3a60cdea9113
     * Category : null
     * InfoName : null
     * InfoType : null
     * InfoValues : null
     * Status : 1
     * Stamp : 0
     */
    @NotNull
    @PrimaryKey
    private String CategoryInfoID;
    private String CategoryID;
    private String Category;
    private String InfoName;
    private String InfoType;
    private String InfoValues;
    private String Status;
    private String Stamp;

    public CategoryInfoBean() {
    }

    public CategoryInfoBean(String infoName, String infoType) {
        InfoName = infoName;
        InfoType = infoType;
    }

    public CategoryInfoBean(String categoryID, String category, String infoType) {
        CategoryID = categoryID;
        Category = category;
        InfoType = infoType;
    }

    public String getCategoryInfoID() {
        return CategoryInfoID;
    }

    public void setCategoryInfoID(String CategoryInfoID) {
        this.CategoryInfoID = CategoryInfoID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String CategoryID) {
        this.CategoryID = CategoryID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getInfoName() {
        return InfoName;
    }

    public void setInfoName(String InfoName) {
        this.InfoName = InfoName;
    }

    public String getInfoType() {
        return TextUtils.isEmpty(InfoType)?"Text":InfoType;
    }

    public void setInfoType(String InfoType) {
        this.InfoType = InfoType;
    }

    public String getInfoValues() {
        return InfoValues;
    }

    public void setInfoValues(String InfoValues) {
        this.InfoValues = InfoValues;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getStamp() {
        return Stamp;
    }

    public void setStamp(String Stamp) {
        this.Stamp = Stamp;
    }
}
