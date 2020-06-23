package com.mubly.xinma.model;

import com.mubly.xinma.common.Constant;
import com.mubly.xinma.utils.StringUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "asset")
public class AssetBean {

    /**
     * AssetID : 9dd6a5db-4a66-4dcf-ac18-f51b42667f86
     * AssetNo : Zach
     * AssetName : 雪花膏
     * Category : 111221
     * CategoryID : 262096e3-dc1e-4bd8-beba-30ee18d59b5c
     * AssetModel : 铜仁的
     * Headimg : 286f7f2f-6697-4488-b713-760692ff708c.png
     * Supply : 雪花啤酒
     * PurchaseDate : 2020-06-19
     * ExpireDate : 2020-07-19
     * Unit : 件
     * Original : 3
     * Price : 3
     * Guaranteed : 1
     * Depreciated : 1
     * Remainder : 1
     * RFID :
     * Depart : 质检部
     * Staff :
     * Seat : 仓库
     * Remark : null
     * LastProcessTime : 2020-06-20 00:05
     * LastInventoryTime :
     * IsLock : 0
     * Status : 1
     * StatusName : 闲置
     */
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String AssetID;
    private String AssetNo;
    private String AssetName;
    private String Category;
    private String CategoryID;
    private String AssetModel;
    private String Headimg;
    private String Supply;
    private String PurchaseDate;
    private String ExpireDate;
    private String Unit;
    private String Original;
    private String Price;
    private String Guaranteed;
    private String Depreciated;
    private String Remainder;
    private String RFID;
    private String Depart;
    private String Staff;
    private String Seat;
    private String Remark;
    private String LastProcessTime;
    private String LastInventoryTime;
    private String IsLock;
    private String Status;
    private String StatusName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAssetID() {
        return AssetID;
    }

    public void setAssetID(String AssetID) {
        this.AssetID = AssetID;
    }

    public String getAssetNo() {
        return AssetNo;
    }

    public void setAssetNo(String AssetNo) {
        this.AssetNo = AssetNo;
    }

    public String getAssetName() {
        return StringUtils.notNull2(AssetName);
    }

    public void setAssetName(String AssetName) {
        this.AssetName = AssetName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String CategoryID) {
        this.CategoryID = CategoryID;
    }

    public String getAssetModel() {
        return StringUtils.notNull2(AssetModel);
    }

    public void setAssetModel(String AssetModel) {
        this.AssetModel = AssetModel;
    }

    public String getHeadimg() {
        return Headimg;
    }

    public void setHeadimg(String Headimg) {
        this.Headimg = Headimg;
    }

    public String getSupply() {
        return Supply;
    }

    public void setSupply(String Supply) {
        this.Supply = Supply;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String PurchaseDate) {
        this.PurchaseDate = PurchaseDate;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String ExpireDate) {
        this.ExpireDate = ExpireDate;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public String getOriginal() {
        return Original;
    }

    public void setOriginal(String Original) {
        this.Original = Original;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getGuaranteed() {
        return Guaranteed;
    }

    public void setGuaranteed(String Guaranteed) {
        this.Guaranteed = Guaranteed;
    }

    public String getDepreciated() {
        return Depreciated;
    }

    public void setDepreciated(String Depreciated) {
        this.Depreciated = Depreciated;
    }

    public String getRemainder() {
        return Remainder;
    }

    public void setRemainder(String Remainder) {
        this.Remainder = Remainder;
    }

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public String getDepart() {
        return StringUtils.notNull2(Depart);
    }

    public void setDepart(String Depart) {
        this.Depart = Depart;
    }

    public String getStaff() {
        return Staff;
    }

    public void setStaff(String Staff) {
        this.Staff = Staff;
    }

    public String getSeat() {
        return StringUtils.notNull2(Seat);
    }

    public void setSeat(String Seat) {
        this.Seat = Seat;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getLastProcessTime() {
        return LastProcessTime;
    }

    public void setLastProcessTime(String LastProcessTime) {
        this.LastProcessTime = LastProcessTime;
    }

    public String getLastInventoryTime() {
        return LastInventoryTime;
    }

    public void setLastInventoryTime(String LastInventoryTime) {
        this.LastInventoryTime = LastInventoryTime;
    }

    public String getIsLock() {
        return IsLock;
    }

    public void setIsLock(String IsLock) {
        this.IsLock = IsLock;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getStatusName() {
        return StatusName;
    }

    public String getSeatLabel() {
        String label = "--";
        if (Status.equals("1")) {
            label = "存放地点";
        } else if (Status.equals("3")) {
            label = "使用地点";
        } else if (Status.equals("5")) {
            label = "使用地点";
        } else if (Status.equals("6")) {
            label = "维修地点";
        } else if (Status.equals("8")) {
            label = "处置方式";
        }
        return label;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }
}
