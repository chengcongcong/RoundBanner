package com.cc.bannerlib.listener;

import com.cc.bannerlib.bean.BannerBean;

/**
 * Created on 2019-11-05  16:10
 * Description: banner 点击监听
 *
 * @author 644898042@qq.com
 */
public interface BannerClickListener {
    void onBannerClick(int position, BannerBean o);
}
