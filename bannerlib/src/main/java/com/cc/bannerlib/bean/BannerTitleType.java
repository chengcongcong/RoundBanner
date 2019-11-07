package com.cc.bannerlib.bean;

/**
 * Created on 2019-11-05  17:28
 * Description:
 *
 * @author 644898042@qq.com
 */
public enum BannerTitleType {
    //标题类型
    NO_TITLE(0),
    ONLY_TITLE(1),
    TITLE_WITH_NUM(2),
    TITLE_WITH_CIRCLE(3),
    ONLY_NUM(4),
    ONLY_CIRCLE(5);

    private int titleType;

    BannerTitleType(int titleType) {
        this.titleType = titleType;
    }

    public int getTitleType() {
        return titleType;
    }

    public void setTitleType(int titleType) {
        this.titleType = titleType;
    }
}
