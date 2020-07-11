package com.mubly.xinma.model;

import java.util.List;

public class PrintContentBean {

    private List<TextBean> Text;
    private List<QRCodeBean> QRCode;
    private List<LineBean> Line;
    private List<RectangleBean> Rectangle;

    public List<TextBean> getText() {
        return Text;
    }

    public void setText(List<TextBean> Text) {
        this.Text = Text;
    }

    public List<QRCodeBean> getQRCode() {
        return QRCode;
    }

    public void setQRCode(List<QRCodeBean> QRCode) {
        this.QRCode = QRCode;
    }

    public List<LineBean> getLine() {
        return Line;
    }

    public void setLine(List<LineBean> Line) {
        this.Line = Line;
    }

    public List<RectangleBean> getRectangle() {
        return Rectangle;
    }

    public void setRectangle(List<RectangleBean> Rectangle) {
        this.Rectangle = Rectangle;
    }

    public static class TextBean {
        /**
         * orientation : 0
         * x : 3
         * y : 3
         * width : 46
         * height : 6
         * content : $CompanyName$
         * horizontalAlignment : 1
         * verticalAlignment : 1
         * fontName : 黑体
         * fontHeight : 2.5
         * fontStyle : 0
         */

        private String orientation;
        private String x;
        private String y;
        private String width;
        private String height;
        private String content;
        private String horizontalAlignment;
        private String verticalAlignment;
        private String fontName;
        private String fontHeight;
        private String fontStyle;

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHorizontalAlignment() {
            return horizontalAlignment;
        }

        public void setHorizontalAlignment(String horizontalAlignment) {
            this.horizontalAlignment = horizontalAlignment;
        }

        public String getVerticalAlignment() {
            return verticalAlignment;
        }

        public void setVerticalAlignment(String verticalAlignment) {
            this.verticalAlignment = verticalAlignment;
        }

        public String getFontName() {
            return fontName;
        }

        public void setFontName(String fontName) {
            this.fontName = fontName;
        }

        public String getFontHeight() {
            return fontHeight;
        }

        public void setFontHeight(String fontHeight) {
            this.fontHeight = fontHeight;
        }

        public String getFontStyle() {
            return fontStyle;
        }

        public void setFontStyle(String fontStyle) {
            this.fontStyle = fontStyle;
        }
    }

    public static class QRCodeBean {
        /**
         * x : 33.5
         * y : 9
         * width : 13
         * content : $AssetNo$
         */

        private String x;
        private String y;
        private String width;
        private String content;

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class LineBean {
        /**
         * x1 : 2
         * y1 : 7
         * x2 : 48
         * y2 : 7
         * lineWidth : 0.5
         */

        private String x1;
        private String y1;
        private String x2;
        private String y2;
        private String lineWidth;

        public String getX1() {
            return x1;
        }

        public void setX1(String x1) {
            this.x1 = x1;
        }

        public String getY1() {
            return y1;
        }

        public void setY1(String y1) {
            this.y1 = y1;
        }

        public String getX2() {
            return x2;
        }

        public void setX2(String x2) {
            this.x2 = x2;
        }

        public String getY2() {
            return y2;
        }

        public void setY2(String y2) {
            this.y2 = y2;
        }

        public String getLineWidth() {
            return lineWidth;
        }

        public void setLineWidth(String lineWidth) {
            this.lineWidth = lineWidth;
        }
    }

    public static class RectangleBean {
        /**
         * x : 2
         * y : 1
         * width : 46
         * height : 23
         * lineWidth : 0.5
         */

        private String x;
        private String y;
        private String width;
        private String height;
        private String lineWidth;

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getLineWidth() {
            return lineWidth;
        }

        public void setLineWidth(String lineWidth) {
            this.lineWidth = lineWidth;
        }
    }
}
