package com.cc.banner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.cc.bannerlib.listener.BImageLoader;
import com.cc.bannerlib.Banner;
import com.cc.bannerlib.bean.BannerBean;
import com.cc.bannerlib.listener.BannerClickListener;
import com.cc.bannerlib.bean.BannerTitleType;
import com.cc.bannerlib.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private Banner banner;
    private Button btn;
    private Button btnNotify;

    private StartBean bean;

    public static void launch(Context context, StartBean bean) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("key_bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = findViewById(R.id.banner);
        btn = findViewById(R.id.btn);
        btn
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                    }
                });
        btnNotify = findViewById(R.id.btn_notify);
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeData();
            }
        });
        bean = (StartBean) getIntent().getSerializableExtra("key_bean");
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
                .setCorner(bean.getCorner())
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
                });
        if (bean.getAutoType() != null) {
            banner.setAutoPlay(bean.getAutoType());
        }
        if (bean.getUserType() != null) {
            banner.setCanUserScroll(bean.getUserType());
        }
        if (bean.getTitleType() != null) {
            banner.setTitleType(bean.getTitleType());
        }
        if (bean.getIndicatorType() != null) {
            banner.setTitleIndicatorType(bean.getIndicatorType());
        }
        if (bean.getMarginType() != null) {
            banner.setMarginType(bean.getMarginType());
        }
        banner.show();
    }

    private void changeData() {
        List<String> list = new ArrayList<>();
        list.add("https://c-ssl.duitang.com/uploads/blog/201510/22/20151022205355_EWBct.thumb.700_0.jpeg");
        list.add("https://c-ssl.duitang.com/uploads/blog/201508/30/20150830100906_dPXRQ.thumb.700_0.jpeg");
        list.add("https://c-ssl.duitang.com/uploads/item/201601/08/20160108211356_hFxUT.thumb.700_0.jpeg");
        List<BannerBean> bannerList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            SelfBannerBean bean = new SelfBannerBean();
            bean.setImgUrl(list.get(i));
            bean.setTitle("banner change" + i);
            bannerList.add(bean);
        }
        if (banner != null) {
            banner.notifyData(bannerList);
        }
    }

    private void setImage(ImageView image, BannerBean imgBean) {
        GlideApp
                .with(this)
                .load(imgBean.getBannerImgUrl())
                .error(R.mipmap.default_image)
                .into(image);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (banner != null) {
            banner.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (banner != null) {
            banner.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (banner != null) {
            banner.onDestroy();
        }
        super.onDestroy();
    }
}
