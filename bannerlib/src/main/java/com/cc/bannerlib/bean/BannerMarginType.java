package com.cc.bannerlib.bean;

public enum BannerMarginType {
        //边距类型
        TYPE_MARGIN(1),
        TYPE_PADDING(2);

        private int marginType;

        BannerMarginType(int marginType) {
            this.marginType = marginType;
        }

        public int getMarginType() {
            return marginType;
        }

        public void setMarginType(int marginType) {
            this.marginType = marginType;
        }
    }