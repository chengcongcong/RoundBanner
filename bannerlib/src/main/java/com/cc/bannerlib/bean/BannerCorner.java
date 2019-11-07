package com.cc.bannerlib.bean;

public enum BannerCorner {
        //圆角位置
        TOP_LEFT(1),
        TOP_RIGHT(2),
        BOTTOM_LEFT(4),
        BOTTOM_RIGHT(8),
        ALL(15);

        private int cornerValue;

        BannerCorner(int cornerValue) {
            this.cornerValue = cornerValue;
        }

        public int getCornerValue() {
            return cornerValue;
        }

        public void setCornerValue(int cornerValue) {
            this.cornerValue = cornerValue;
        }
    }