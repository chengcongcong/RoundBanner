package com.cc.bannerlib;

/**
 * Created on 2019-11-05  17:36
 * Description:
 *
 * @author
 */
public enum BannerIndicatorType {
    //标题导航位置
    GRAVITY_CENTER(0),
    GRAVITY_LEFT(1),
    GRAVITY_RIGHT(2);

    private int type;

    BannerIndicatorType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
