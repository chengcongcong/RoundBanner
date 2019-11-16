package com.cc.banner;

import com.cc.bannerlib.bean.BannerCorner;
import com.cc.bannerlib.bean.BannerIndicatorType;
import com.cc.bannerlib.bean.BannerMarginType;
import com.cc.bannerlib.bean.BannerTitleType;

import java.io.Serializable;
import java.util.List;

public class StartBean implements Serializable {
    private BannerTitleType titleType;
    private BannerIndicatorType indicatorType;
    private BannerMarginType marginType;
    private Boolean userType;
    private Boolean autoType;
    private List<BannerCorner> corner;

    public BannerTitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(BannerTitleType titleType) {
        this.titleType = titleType;
    }

    public BannerIndicatorType getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(BannerIndicatorType indicatorType) {
        this.indicatorType = indicatorType;
    }

    public BannerMarginType getMarginType() {
        return marginType;
    }

    public void setMarginType(BannerMarginType marginType) {
        this.marginType = marginType;
    }

    public Boolean getUserType() {
        return userType;
    }

    public void setUserType(Boolean userType) {
        this.userType = userType;
    }

    public Boolean getAutoType() {
        return autoType;
    }

    public void setAutoType(Boolean autoType) {
        this.autoType = autoType;
    }

    public List<BannerCorner> getCorner() {
        return corner;
    }

    public void setCorner(List<BannerCorner> corner) {
        this.corner = corner;
    }
}
