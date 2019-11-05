package com.cc.banner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cc.bannerlib.BImageLoader;
import com.cc.bannerlib.Banner;
import com.cc.bannerlib.BannerBean;
import com.cc.bannerlib.BannerClickListener;
import com.cc.bannerlib.BannerIndicatorType;
import com.cc.bannerlib.BannerTitleType;
import com.cc.bannerlib.RoundImageView;
import com.cc.bannerlib.RoundLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = findViewById(R.id.banner);
        testData();
    }

    private void testData() {
        List<String> list = new ArrayList<>();
        list.add("https://c-ssl.duitang.com/uploads/item/201504/20/20150420H3122_AmQH2.jpeg");
        list.add("https://c-ssl.duitang.com/uploads/blog/201510/22/20151022205355_EWBct.thumb.700_0.jpeg");
        list.add("https://c-ssl.duitang.com/uploads/blog/201508/30/20150830100906_dPXRQ.thumb.700_0.jpeg");
        list.add("https://c-ssl.duitang.com/uploads/item/201601/08/20160108211356_hFxUT.thumb.700_0.jpeg");
        List<BannerBean> bannerList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SelfBannerBean bean = new SelfBannerBean();
            bean.setImgUrl(list.get(i));
            bean.setTitle("banner " + i);
            bannerList.add(bean);
        }
        banner
                .setBannerList(bannerList)
                .setMarginLeftAndRight(10)
                .setRadius(10)
                .setImageLoader(new BImageLoader() {
                    @Override
                    public void displayImage(RoundImageView imageView, BannerBean bannerBean) {
                        setImage(imageView, bannerBean);
                    }
                })
                .setClickListener(new BannerClickListener() {
                    @Override
                    public void onBannerClick(int position, BannerBean bean) {
                        Log.e(TAG, "position = " + position + ", object = " + bean);
                    }
                })
                .setTitleType(BannerTitleType.ONLY_TITLE)
                .show();
    }

    private void setImage(ImageView image, BannerBean imgBean) {
        GlideApp
                .with(this)
                .load(imgBean.getBannerImgUrl())
                .error(R.mipmap.default_image)
                .into(image);
    }
}
