package com.cc.bannerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.cc.bannerlib.bean.BannerBean;
import com.cc.bannerlib.bean.BannerCorner;
import com.cc.bannerlib.bean.BannerIndicatorType;
import com.cc.bannerlib.bean.BannerMarginType;
import com.cc.bannerlib.bean.BannerTitleType;
import com.cc.bannerlib.listener.BImageLoader;
import com.cc.bannerlib.listener.BannerClickListener;
import com.cc.bannerlib.utils.LogUtil;
import com.cc.bannerlib.utils.SizeUtil;
import com.cc.bannerlib.widget.BannerAdapter;
import com.cc.bannerlib.widget.BannerViewPager;
import com.cc.bannerlib.widget.RoundImageView;
import com.cc.bannerlib.widget.RoundLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019-11-04  13:48
 * Description:
 *
 * @author 644898042@qq.com
 */
public class Banner extends RelativeLayout {

    private final String TAG = getClass().getSimpleName();

    /**
     * 圆角 左上 右上 左下 右下
     */
    private int corner;
    /**
     * 圆角角度
     */
    private float radius;
    /**
     * 边距类型
     */
    private int marginType;
    /**
     * 边距
     */
    private float marginLeft;
    private float marginRight;
    private float marginLeftAndRight;
    private RoundLayout roundLayout;
    private BannerViewPager vpBanner;
    private BannerAdapter bannerAdapter;
    private List<RoundImageView> imageViews;
    private List<BannerBean> sourceDatas;

    /**
     * 当前位置
     */
    private int currentIndex;
    /**
     * 当前显示位置
     */
    private int currentShowIndex = -1;
    /**
     * 只是数据数量
     */
    private int count;
    /**
     * 图片加载器
     */
    private BImageLoader imageLoader;
    /**
     * 点击监听
     */
    private BannerClickListener clickListener;

    /**
     * 标题
     */
    private RelativeLayout rlTitleParent;
    private TextView tvTitle;
    private LinearLayout llIndicatorParent;
    private LinearLayout llCircleIndicator;
    private TextView tvNumIndicator;

    /**
     * 标题类型
     */
    private int titleType;
    /**
     * 标题 指示器类型
     */
    private int titleIndicatorType;

    /**
     * 标题背景资源id
     */
    private int titleBgResId;
    /**
     * 标题指示器资源id
     */
    private int titleIndicatorSelectorResId;
    /**
     * 是否自动轮播 默认 是
     */
    private boolean autoPlay;
    private AutoPlayHandler autoPlayHandler;
    /**
     * 轮播事件间隔 默认 3000 ms
     */
    private int autoPlayTime;
    private boolean isPlaying;
    /**
     * 用户能否手动滚动
     */
    private boolean canUserScroll;

    public Banner(Context context) {
        super(context);
        init(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Banner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        getTypeValue(context, attrs);
        initParams();
        initViews(context);
        autoPlayHandler = new AutoPlayHandler();
    }

    /**
     * 初始化一些变量
     */
    private void initParams() {
        sourceDatas = new ArrayList<>();
        imageViews = new ArrayList<>();
    }

    /**
     * 初始化视图 监听
     *
     * @param context
     */
    private void initViews(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_banner, this, true);
        findViews(rootView);
        initListener();
    }

    private void findViews(View rootView) {
        roundLayout = rootView.findViewById(R.id.round_layout);
        vpBanner = rootView.findViewById(R.id.vp_banner);

        rlTitleParent = rootView.findViewById(R.id.rl_title_parent);
        tvTitle = rootView.findViewById(R.id.tv_title);
        llIndicatorParent = rootView.findViewById(R.id.ll_indicator_parent);
        llCircleIndicator = rootView.findViewById(R.id.ll_circle_indicator);
        tvNumIndicator = rootView.findViewById(R.id.tv_num_indicator);
    }

    private void initListener() {
        vpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (count <= 1) {
                    return;
                }
                boolean needPlay;
                if (currentIndex == 0) {
                    needPlay = false;
                    vpBanner.setCurrentItem(count, false);
                } else if (currentIndex == count + 1) {
                    needPlay = false;
                    vpBanner.setCurrentItem(1, false);
                } else {
                    needPlay = true;
                }
                currentIndex = position;
                updateIndicator(getShowPosition());
                updateTitle(sourceDatas.get(currentIndex).getBannerTitle());
                if (needPlay) {
                    LogUtil.e(TAG, "onPageSelected = " + position);
                    isPlaying = false;
                    startAutoPlay();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (count <= 1) {
                            return;
                        }
                        if (currentIndex == 0) {
                            vpBanner.setCurrentItem(count, false);
                        } else if (currentIndex == count + 1) {
                            vpBanner.setCurrentItem(1, false);
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        if (count <= 1) {
                            return;
                        }
                        if (currentIndex == count + 1) {
                            vpBanner.setCurrentItem(1, false);
                        } else if (currentIndex == 0) {
                            vpBanner.setCurrentItem(count, false);
                        }
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     */
    private void getTypeValue(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        corner = typedArray.getInt(R.styleable.Banner_bCorner, BannerCorner.ALL.getCornerValue());
        radius = typedArray.getDimension(R.styleable.Banner_bRadius, 0);
        marginType = typedArray.getInt(R.styleable.Banner_bMarginType, BannerMarginType.TYPE_MARGIN.getMarginType());
        marginLeft = typedArray.getDimension(R.styleable.Banner_bMarginLeft, 0);
        marginRight = typedArray.getDimension(R.styleable.Banner_bMarginRight, 0);
        marginLeftAndRight = typedArray.getDimension(R.styleable.Banner_bMarginLeftAndRight, 0);

        titleType = typedArray.getInt(R.styleable.Banner_bTitleType, BannerTitleType.NO_TITLE.getTitleType());
        titleIndicatorType = typedArray.getInt(R.styleable.Banner_bTitleIndicatorType, BannerIndicatorType.GRAVITY_CENTER.getType());
        titleBgResId = typedArray.getResourceId(R.styleable.Banner_bTitleBg, R.drawable.banner_title_bg_shape);
        titleIndicatorSelectorResId = typedArray.getResourceId(R.styleable.Banner_bTitleIndicatorSelector, R.drawable.banner_indicator_selector);

        autoPlay = typedArray.getBoolean(R.styleable.Banner_bAutoPlay, false);
        autoPlayTime = typedArray.getInt(R.styleable.Banner_bAutoPlayTime, 3000);
        canUserScroll = typedArray.getBoolean(R.styleable.Banner_bUserScroll, true);
        typedArray.recycle();
    }

    /**
     * 初始化banner adapter 数据
     */
    private void initImageViews() {
        imageViews.clear();
        if (sourceDatas != null) {
            for (int i = 0; i < sourceDatas.size(); i++) {
                RoundImageView imageView = createImageView();
                setImage(imageView, sourceDatas.get(i));
                setClick(imageView, i, sourceDatas.get(i));
                imageViews.add(imageView);
            }
            initAdapter(imageViews);
        }
    }

    private void initAdapter(List<RoundImageView> viewList) {
        bannerAdapter = new BannerAdapter(viewList);
        vpBanner.setAdapter(bannerAdapter);
        vpBanner.setCurrentItem(currentIndex);
        vpBanner.setOffscreenPageLimit(viewList.size());
    }

    private RoundImageView createImageView() {
        RoundImageView imageView = new RoundImageView(getContext());
        setImageMargin(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    /**
     * 根据marginType 设置图片 边距 配合 RoundLayout 实现 margin 和 padding 效果
     * @param imageView
     */
    private void setImageMargin(View imageView) {
        if (!(imageView instanceof RoundImageView)) {
            return;
        }
        ((RoundImageView) imageView).setCorner(corner);
        ((RoundImageView) imageView).setRadius(radius);
        if (marginType == BannerMarginType.TYPE_PADDING.getMarginType()) {
            ((RoundImageView) imageView).setLeftMargin(marginLeftAndRight == 0 ? marginLeft : marginLeftAndRight);
            ((RoundImageView) imageView).setRightMargin(marginLeftAndRight == 0 ? marginRight : marginLeftAndRight);
        } else {
            ((RoundImageView) imageView).setLeftMargin(0);
            ((RoundImageView) imageView).setRightMargin(0);
        }
    }

    /**
     * 图片显示
     * @param imageView
     * @param bean
     */
    private void setImage(RoundImageView imageView, BannerBean bean) {
        if (imageLoader != null) {
            imageLoader.displayImage(imageView, bean);
        }
    }

    private void setClick(RoundImageView imageView, final int position, final BannerBean bean) {
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    if (count <= 1) {
                        clickListener.onBannerClick(position, bean);
                    } else {
                        if (position == 0) {
                            clickListener.onBannerClick(count - 1, bean);
                        } else if (position == sourceDatas.size() - 1) {
                            clickListener.onBannerClick(0, bean);
                        } else {
                            clickListener.onBannerClick(position - 1, bean);
                        }
                    }
                }
            }
        });
    }

    /**
     * 根据marginType 设置 圆角布局的边距 配合 RoundImageView 实现 margin 和 padding 效果
     */
    private void setMarginUi() {
        LayoutParams params = (RelativeLayout.LayoutParams) roundLayout.getLayoutParams();
        if (marginType == BannerMarginType.TYPE_MARGIN.getMarginType()) {
            if (marginLeftAndRight == 0) {
                params.leftMargin = (int) marginLeft;
                params.rightMargin = (int) marginRight;
            } else {
                params.leftMargin = (int) marginLeftAndRight;
                params.rightMargin = (int) marginLeftAndRight;
            }
        } else if (marginType == BannerMarginType.TYPE_PADDING.getMarginType()) {
            params.leftMargin = 0;
            params.rightMargin = 0;
        }
        roundLayout.setLayoutParams(params);
    }

    public Banner setCorner(BannerCorner corner) {
        this.corner = corner.getCornerValue();
        roundLayout.setCorner(corner.getCornerValue());
        return this;
    }

    public Banner setRadius(int dpValue) {
        this.radius = SizeUtil.dpToPx(getContext(), dpValue);
        roundLayout.setRadius(radius);
        return this;
    }

    /**
     * 设置 数据 对原数据进行处理， 原数据个数大于1 在数据前后各增加一个数据，用于实现 无限循环
     * @param list
     * @return
     */
    public Banner setBannerList(List<BannerBean> list) {
        if (list == null) {

        } else {
            isPlaying = false;
            currentIndex = 0;
            clearHandler();
            sourceDatas.clear();
            sourceDatas.addAll(list);
            count = sourceDatas.size();
            if (list.size() > 1) {
                currentIndex = 1;
                sourceDatas.add(0, sourceDatas.get(sourceDatas.size() - 1));
                sourceDatas.add(sourceDatas.get(1));
            }
        }
        return this;
    }

    public Banner setMarginType(BannerMarginType marginType) {
        this.marginType = marginType.getMarginType();
        return this;
    }

    public Banner setMarginLeft(int marginLeftDp) {
        this.marginLeft = SizeUtil.dpToPx(getContext(), marginLeftDp);
        return this;
    }

    public Banner setMarginRight(int marginRightDp) {
        this.marginRight = SizeUtil.dpToPx(getContext(), marginRightDp);
        return this;
    }

    public Banner setMarginLeftAndRight(int marginLeftAndRightDp) {
        this.marginLeftAndRight = SizeUtil.dpToPx(getContext(), marginLeftAndRightDp);
        return this;
    }

    public Banner setImageLoader(BImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public Banner setClickListener(BannerClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public Banner setTitleType(BannerTitleType titleType) {
        this.titleType = titleType.getTitleType();
        return this;
    }

    public Banner setTitleIndicatorType(BannerIndicatorType titleIndicatorType) {
        this.titleIndicatorType = titleIndicatorType.getType();
        return this;
    }

    public Banner setTitleBgResId(@DrawableRes int titleBgResId) {
        this.titleBgResId = titleBgResId;
        return this;
    }

    public Banner setTitleIndicatorSelectorResId(@DrawableRes int titleIndicatorSelectorResId) {
        this.titleIndicatorSelectorResId = titleIndicatorSelectorResId;
        return this;
    }

    public Banner setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
        return this;
    }

    public Banner setAutoPlayTime(int autoPlayTime) {
        this.autoPlayTime = autoPlayTime;
        return this;
    }

    public Banner setCanUserScroll(boolean canUserScroll) {
        this.canUserScroll = canUserScroll;
        return this;
    }

    /**
     * 显示banner
     */
    public void show() {
        setViewParams();
        setMarginUi();
        updateTitleUi();
        initImageViews();
    }

    /**
     * 设置视图属性 圆角 等
     */
    private void setViewParams() {
        if (roundLayout != null) {
            roundLayout.setCorner(corner);
            roundLayout.setRadius(radius);
        }
        if (vpBanner != null) {
            vpBanner.setCanScroll(canUserScroll);
        }
    }

    /**
     * 开始轮播
     */
    private void startAutoPlay() {
        if (!isPlaying && count > 1 && autoPlay && autoPlayHandler != null && vpBanner != null) {
            isPlaying = true;
            clearHandler();
            autoPlayHandler.sendEmptyMessageDelayed(1, autoPlayTime);
        }
    }

    /**
     * 更新标题
     */
    private void updateTitleUi() {
        if (titleType == BannerTitleType.NO_TITLE.getTitleType()) {
            rlTitleParent.setVisibility(GONE);
        } else {
            rlTitleParent.setVisibility(VISIBLE);
            rlTitleParent.setBackgroundResource(titleBgResId);
            if (titleType == BannerTitleType.ONLY_TITLE.getTitleType()) {
                llIndicatorParent.setVisibility(GONE);
                tvTitle.setVisibility(VISIBLE);
                updateTitle(sourceDatas.get(currentIndex).getBannerTitle());
            } else if (titleType == BannerTitleType.TITLE_WITH_NUM.getTitleType()) {
                llIndicatorParent.setVisibility(VISIBLE);
                tvTitle.setVisibility(VISIBLE);
                tvNumIndicator.setVisibility(VISIBLE);
                llCircleIndicator.setVisibility(GONE);
                updateTitle(sourceDatas.get(currentIndex).getBannerTitle());
                updateIndicator(getShowPosition());
                setIndicatorGravity(true);
            } else if (titleType == BannerTitleType.TITLE_WITH_CIRCLE.getTitleType()) {
                llIndicatorParent.setVisibility(VISIBLE);
                tvTitle.setVisibility(VISIBLE);
                llCircleIndicator.setVisibility(VISIBLE);
                initCircleIndicator();
                tvNumIndicator.setVisibility(GONE);
                updateIndicator(getShowPosition());
                updateTitle(sourceDatas.get(currentIndex).getBannerTitle());
                setIndicatorGravity(true);
            } else if (titleType == BannerTitleType.ONLY_NUM.getTitleType()) {
                llIndicatorParent.setVisibility(VISIBLE);
                tvNumIndicator.setVisibility(VISIBLE);
                tvTitle.setVisibility(GONE);
                llCircleIndicator.setVisibility(GONE);
                setIndicatorGravity(false);
                updateIndicator(getShowPosition());
            } else if (titleType == BannerTitleType.ONLY_CIRCLE.getTitleType()) {
                llIndicatorParent.setVisibility(VISIBLE);
                llCircleIndicator.setVisibility(VISIBLE);
                setIndicatorGravity(false);
                initCircleIndicator();
                tvNumIndicator.setVisibility(GONE);
                tvTitle.setVisibility(GONE);
                updateIndicator(getShowPosition());
            }
        }
    }

    /**
     * 设置标题指示器 位置
     * @param withTitle
     */
    private void setIndicatorGravity(boolean withTitle) {
        RelativeLayout.LayoutParams params = (LayoutParams) llIndicatorParent.getLayoutParams();
        if (withTitle) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            if (titleIndicatorType == BannerIndicatorType.GRAVITY_CENTER.getType()) {
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
            } else if (titleIndicatorType == BannerIndicatorType.GRAVITY_LEFT.getType()) {
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            } else if (titleIndicatorType == BannerIndicatorType.GRAVITY_RIGHT.getType()) {
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
        }
        llIndicatorParent.setLayoutParams(params);
    }

    /**
     * 初始化圆形指示器
     */
    private void initCircleIndicator() {
        llCircleIndicator.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < count; i++) {
            View view = inflater.inflate(R.layout.layout_circle, llCircleIndicator, false);
            view.setBackgroundResource(titleIndicatorSelectorResId);
            currentShowIndex = getShowPosition();
            view.setSelected(i == currentShowIndex);
            llCircleIndicator.addView(view);
        }
    }

    /**
     * 根据当前位置 获取 显示位置
     *
     * @return
     */
    private int getShowPosition() {
        int position = 0;
        if (count <= 1) {
            return currentIndex;
        }
        if (currentIndex == 0) {
            position = count - 1;
        } else if (currentIndex == sourceDatas.size() - 1) {
            position = 0;
        } else {
            position = currentIndex - 1;
        }
        return position;
    }

    /**
     * 更新指示器
     * @param position
     */
    private void updateIndicator(int position) {
        if (currentShowIndex == position) {
            return;
        }
        currentShowIndex = position;
        if (titleType == BannerTitleType.TITLE_WITH_CIRCLE.getTitleType() || titleType == BannerTitleType.ONLY_CIRCLE.getTitleType()) {
            for (int i = 0; i < llCircleIndicator.getChildCount(); i++) {
                llCircleIndicator.getChildAt(i).setSelected(position == i);
            }
        } else if (titleType == BannerTitleType.TITLE_WITH_NUM.getTitleType() || titleType == BannerTitleType.ONLY_NUM.getTitleType()) {
            tvNumIndicator.setText((position + 1) + "/" + count);
        }
    }

    /**
     * 更新标题
     * @param title
     */
    private void updateTitle(String title) {
        if (titleType == BannerTitleType.TITLE_WITH_CIRCLE.getTitleType()
                || titleType == BannerTitleType.TITLE_WITH_NUM.getTitleType()
                || titleType == BannerTitleType.ONLY_TITLE.getTitleType()) {
            tvTitle.setText(title);
        }
    }

    public void onDestroy() {
        if (autoPlayHandler != null) {
            autoPlayHandler.removeCallbacksAndMessages(null);
        }
        autoPlayHandler = null;
    }

    public void onPause() {
        isPlaying = false;
        clearHandler();
    }

    public void onResume() {
        startAutoPlay();
    }

    /**
     * 取消轮播
     */
    private void clearHandler() {
        if (autoPlayHandler != null) {
            autoPlayHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 修改数据列表 为空无效
     * @param dats
     */
    public void notifyData(List<BannerBean> dats) {
        setBannerList(dats);
        updateTitleUi();
        initImageViews();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (autoPlay && canUserScroll) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    isPlaying = false;
                    clearHandler();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    startAutoPlay();
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private class AutoPlayHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    LogUtil.e(TAG, "handleMessage cur = " + currentIndex);
                    toNext();
                    break;
                default:
                    break;
            }
        }
    }

    private void toNext() {
        if (vpBanner != null) {
            if (currentIndex == 0 || currentIndex == sourceDatas.size() - 1) {
                return;
            }
            if (currentIndex < sourceDatas.size() - 1) {
                vpBanner.setCurrentItem(currentIndex + 1, true);
            }
        }
    }

}
