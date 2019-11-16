# RoundBanner

##效果
![效果图](/home/app/1.jpg)

##app体验
[下载链接](https://www.pgyer.com/SADw) 密码：rb1234

##常量
|名称|描述|方法|备注|
|-|-|-|-|
|BannerCorner.TOP_LEFT|圆角位置-左上|setCorner||
|BannerCorner.TOP_RIGHT|圆角位置-又上|setCorner||
|BannerCorner.BOTTOM_LEFT|圆角位置-左下|setCorner||
|BannerCorner.BOTTOM_RIGHT|圆角位置-又上|setCorner||
|BannerCorner.ALL|圆角位置-全部|setCorner||
|BannerMarginType.TYPE_MARGIN|边距类型margin|setMarginType||
|BannerMarginType.TYPE_PADDING|边距类型padding|setMarginType||
|BannerTitleType.NO_TITLE|无标题|setTitleType||
|BannerTitleType.ONLY_TITLE|只有标题无指示器|setTitleType||
|BannerTitleType.TITLE_WITH_NUM|标题+数字指示器|setTitleType||
|BannerTitleType.TITLE_WITH_CIRCLE|标题+圆形指示器|setTitleType||
|BannerTitleType.ONLY_NUM|仅数字指示器|setTitleType||
|BannerTitleType.ONLY_CIRCLE|仅圆形指示器|setTitleType||
|BannerIndicatorType.GRAVITY_CENTER|指示器居中|setTitleIndicatorType|没有标题时有效|
|BannerIndicatorType.GRAVITY_LEFT|指示器居左|setTitleIndicatorType| |
|BannerIndicatorType.GRAVITY_RIGHT|指示器居右|setTitleIndicatorType|||

##属性
|名称|类型|描述|备注|
|-|-|-|-|
|bRadius|dimension|圆角弧度||
|bCorner|enum|圆角位置||
|bMarginLeftAndRight|dimension|左右边距||
|bMarginLeft|dimension|左边距|优先级低于bMarginLeftAndRight|
|bMarginRight|dimension|右边距|优先级低于bMarginLeftAndRight|
|bMarginType|enum|边距类型|默认margin|
|bTitleType|enum|标题类型|默认无标题|
|bTitleIndicatorType|enum|标题指示器位置|默认居中|
|bTitleBg|reference|标题背景||
|bTitleIndicatorSelector|reference|圆形指示器selector||
|bAutoPlay|boolean|是否自动轮播|默认true|
|bAutoPlayTime|integer|自动轮播时间间隔（毫秒）|默认3000|
|bUserScroll|boolean|用户影响轮播|默认true|

##方法
|名称|描述|备注|
|-|-|
|Banner setBannerList(List<BannerBean> list)|设置源数据||
|Banner setClickListener(BannerClickListener clickListener)|设置banner点击监听||
|Banner setImageLoader(BImageLoader imageLoader)|设置图片加载器|外部实现|
|Banner setRadius(int dpValue)|设置圆角弧度|dp|
|Banner setCorner(BannerCorner corner)|设置圆角位置||
|Banner setMarginLeftAndRight(int marginLeftAndRightDp)|设置左右边距|dp|
|Banner setMarginLeft(int marginLeftDp)|设置左边距|dp 优先级低于setMarginLeftAndRight|
|Banner setMarginRight(int marginRightDp)|设置右边距|dp 优先级低于setMarginLeftAndRight|
|Banner setMarginType(BannerMarginType marginType)|设置边距类型||
|Banner setTitleType(BannerTitleType titleType)|设置标题类型||
|Banner setTitleIndicatorType(BannerIndicatorType titleIndicatorType)|设置标题指示器位置||
|Banner setTitleBgResId(@DrawableRes int titleBgResId)|设置标题背景||
|Banner setTitleIndicatorSelectorResId(@DrawableRes int titleIndicatorSelectorResId)|设置圆形指示器selector||
|Banner setAutoPlay(boolean autoPlay)|设置是否自动轮播||
|Banner setAutoPlayTime(int autoPlayTime)|设置轮播时间间隔||
|Banner setCanUserScroll(boolean canUserScroll)|设置用户是否影响轮播||
|void show()|显示banner|设置完参数后再调用|
|void notifyData(List<BannerBean> dats)|banner数据改变后刷新banner|null无效|
|void onPause()|取消轮播||
|void onResume()|继续轮播|如果能轮播的话|
|void onDestroy()|destroy|||

##使用步骤
1.项目build.gradle 添加代码
```java
maven {
	url 'https://jitpack.io'
}
```
2.app build.gradle 添加代码
```java
dependencies {
    ...
	implementation 'com.github.chengcongcong:RoundBanner:v1.0.0'//support版本
    implementation 'com.github.chengcongcong:RoundBanner:androidx-v1.0.0'//androidx版本
}
```
3.Banner实体类实现BannerBean接口，提供banner image和title
4.设置banner数据及参数

