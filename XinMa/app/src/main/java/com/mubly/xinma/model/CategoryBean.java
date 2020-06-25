package com.mubly.xinma.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categoryBean")
public class CategoryBean {

    /**
     * CompanyID : 263b7fb1-0d23-4a63-90b3-708d9e11d079
     * CategoryID : f3ed1fa5-f312-4489-983e-424e2cb7acae
     * Category : 一般办公用品（预设）
     * ShowIndex : 0
     * Status : 1
     * Stamp : 1592957886
     */
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String CompanyID;
    private String CategoryID;
    private String Category;
    private String ShowIndex;
    private String Status;
    private String Stamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String CompanyID) {
        this.CompanyID = CompanyID;
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

    public String getShowIndex() {
        return ShowIndex;
    }

    public void setShowIndex(String ShowIndex) {
        this.ShowIndex = ShowIndex;
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
