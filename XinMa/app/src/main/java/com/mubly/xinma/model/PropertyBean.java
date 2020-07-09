package com.mubly.xinma.model;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "propertyBean")
public class PropertyBean {
    @NotNull
    @PrimaryKey
    private String PropertyID;
    private String Code;
    private String Property;
    private int ShowIndex;

    @NotNull
    public String getPropertyID() {
        return PropertyID;
    }

    public void setPropertyID(@NotNull String propertyID) {
        PropertyID = propertyID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getProperty() {
        return Property;
    }

    public void setProperty(String property) {
        Property = property;
    }

    public int getShowIndex() {
        return ShowIndex;
    }

    public void setShowIndex(int showIndex) {
        ShowIndex = showIndex;
    }
    @NonNull
    @Override
    public String toString() {
        return TextUtils.isEmpty(Property) ? "" : Property;
    }
}
