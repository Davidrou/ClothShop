package com.david.clothshop.net.bean;


import java.util.List;

public class GoodDetailResponseBean {
    public GoodDetail getGoodDetail() {
        return goodDetail;
    }

    public void setGoodDetail(GoodDetail goodDetail) {
        this.goodDetail = goodDetail;
    }

    private GoodDetail goodDetail;

    public static class GoodDetail {
        private int id;
        private int menuId;
        private String content;
        private List<BannerItem> banner;
        private String color;
        private String title;
        private double price;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        private String size;


        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public List<BannerItem> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerItem> banner) {
            this.banner = banner;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public double getPrice() {
            return price;
        }
    }

    public static class BannerItem {
        public int getId() {
            return id;
        }

        public String getValue() {
            return value;
        }

        private int id;
        private String value;
    }

    public static class ColorItem{
        private String color;
        private String name;
        private boolean checked;
    }
}
