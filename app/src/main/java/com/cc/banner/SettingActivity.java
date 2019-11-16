package com.cc.banner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.cc.bannerlib.Banner;
import com.cc.bannerlib.bean.BannerCorner;
import com.cc.bannerlib.bean.BannerIndicatorType;
import com.cc.bannerlib.bean.BannerMarginType;
import com.cc.bannerlib.bean.BannerTitleType;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private RadioGroup rgTitleType, rgIndicatorType, rgMarginType, rgUserType, rgAutoType;
    private LinearLayout rgCornerType;

    private Button btnStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
    }

    private void initViews() {
        rgTitleType = findViewById(R.id.group_title_type);
        rgIndicatorType = findViewById(R.id.group_indicator_type);
        rgMarginType = findViewById(R.id.group_margin_type);
        rgUserType = findViewById(R.id.group_user_type);
        rgAutoType = findViewById(R.id.group_auto_type);
        rgCornerType = findViewById(R.id.group_corner_type);

        btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

    }

    private void start() {
        StartBean bean = new StartBean();
        switch (rgTitleType.getCheckedRadioButtonId()) {
            case R.id.btn_no_title:
                bean.setTitleType(BannerTitleType.NO_TITLE);
                break;
            case R.id.btn_only_title:
                bean.setTitleType(BannerTitleType.ONLY_TITLE);
                break;
            case R.id.btn_title_with_num:
                bean.setTitleType(BannerTitleType.TITLE_WITH_NUM);
                break;
            case R.id.btn_title_with_circle:
                bean.setTitleType(BannerTitleType.TITLE_WITH_CIRCLE);
                break;
            case R.id.btn_only_num:
                bean.setTitleType(BannerTitleType.ONLY_NUM);
                break;
            case R.id.btn_only_circle:
                bean.setTitleType(BannerTitleType.ONLY_CIRCLE);
                break;
        }
        switch (rgIndicatorType.getCheckedRadioButtonId()) {
            case R.id.btn_center:
                bean.setIndicatorType(BannerIndicatorType.GRAVITY_CENTER);
                break;
            case R.id.btn_left:
                bean.setIndicatorType(BannerIndicatorType.GRAVITY_LEFT);
                break;
            case R.id.btn_right:
                bean.setIndicatorType(BannerIndicatorType.GRAVITY_RIGHT);
                break;
        }
        switch (rgMarginType.getCheckedRadioButtonId()) {
            case R.id.btn_margin:
                bean.setMarginType(BannerMarginType.TYPE_MARGIN);
                break;
            case R.id.btn_padding:
                bean.setMarginType(BannerMarginType.TYPE_PADDING);
                break;
        }
        switch (rgUserType.getCheckedRadioButtonId()) {
            case R.id.btn_user_true:
                bean.setUserType(true);
                break;
            case R.id.btn_padding:
                bean.setUserType(false);
                break;
        }
        switch (rgAutoType.getCheckedRadioButtonId()) {
            case R.id.btn_auto_true:
                bean.setAutoType(true);
                break;
            case R.id.btn_auto_false:
                bean.setAutoType(false);
                break;
        }

        List<BannerCorner> corners = new ArrayList<>();
        for (int i = 0; i < rgCornerType.getChildCount(); i++) {
            if (((CheckBox) rgCornerType.getChildAt(i)).isChecked()) {
                switch (i) {
                    case 0:
                        corners.add(BannerCorner.TOP_LEFT);
                        break;
                    case 1:
                        corners.add(BannerCorner.TOP_RIGHT);
                        break;
                    case 2:
                        corners.add(BannerCorner.BOTTOM_LEFT);
                        break;
                    case 3:
                        corners.add(BannerCorner.BOTTOM_RIGHT);
                        break;
                    case 4:
                        corners.add(BannerCorner.ALL);
                        break;
                }
            }
        }
        bean.setCorner(corners);
        MainActivity.launch(this, bean);
    }
}
