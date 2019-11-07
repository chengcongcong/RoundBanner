package com.cc.banner;

import com.cc.bannerlib.bean.BannerBean;

/**
 * Created on 2019-11-05  16:57
 * Description:
 *
 * @author 644898042@qq.com
 */
public class SelfBannerBean implements BannerBean {

    private String imgUrl;
    private String title;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getBannerImgUrl() {
        return imgUrl;
    }

    @Override
    public String getBannerTitle() {
        return title;
    }
}
