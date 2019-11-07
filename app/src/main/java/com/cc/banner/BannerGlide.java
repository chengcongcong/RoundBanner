package com.cc.banner;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created on 2019-11-04  15:42
 * Description:
 *
 * @author 644898042@qq.com
 */
@GlideModule
public class BannerGlide extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        final ActivityManager activityManager =(ActivityManager) context. getSystemService(Context.ACTIVITY_SERVICE);
        int memorySize = activityManager.getMemoryClass();
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_RGB_565)
                        .disallowHardwareConfig())
                .setMemoryCache(new LruResourceCache(memorySize/4));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
