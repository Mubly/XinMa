package com.mubly.xinma.model;

/**
 * type 1:资产  2：创建 3：盘点4：领用5：借用6：归还7：维修8：处置9：分析报表10：资产分类11：组织架构
 */
public class HomeMenuBean {
    public String menuName;
    public int iconResId;
    public int type;

    public HomeMenuBean(String menuName, int iconResId, int type) {
        this.menuName = menuName;
        this.iconResId = iconResId;
        this.type = type;
    }

    public HomeMenuBean() {
    }
}
