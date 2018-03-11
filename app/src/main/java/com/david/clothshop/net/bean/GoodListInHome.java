package com.david.clothshop.net.bean;

import java.util.List;

/**
 * Created by luxiaolin on 18/2/16.
 */

public class GoodListInHome {
    private List<Good> goodList;
    private boolean hasNext;

    public List<Good> getGoodList() {
        return goodList;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public static class  Good{
        private int id;
        private double price;
        private String title;
        private String show;

        public int getId() {
            return id;
        }

        public double getPrice() {
            return price;
        }

        public String getTitle() {
            return title;
        }

        public String getShow() {
            return show;
        }
    }
}
