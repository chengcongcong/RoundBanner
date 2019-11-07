package com.cc.bannerlib.listener;

import com.cc.bannerlib.widget.RoundImageView;
import com.cc.bannerlib.bean.BannerBean;

/**
 * Created on 2019-11-05  15:59
 * Description: 提供给外部的图片加载器 可自行使用图片加载框架 如 Glide 等
 *
 * @author 644898042@qq.com
 */
public interface BImageLoader {
    void displayImage(RoundImageView imageView, BannerBean imgBean);
}
