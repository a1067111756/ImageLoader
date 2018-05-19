package com.chengyu.imageloader;

import android.graphics.Bitmap;

/**
 * Created by 澄鱼 on 2018/5/20.
 */

public class DoubleCache implements ImageCache{
    private final ImageCache mMemoryCache = new MemoryCache();
    private final ImageCache mDiskCache = new DiskCache();

    @Override
    public void put(String key, Bitmap bitmap) {
        mMemoryCache.put(key, bitmap);
        mDiskCache.put(key, bitmap);
    }

    @Override
    public Bitmap get(String key) {
        // 先从内存种获取
        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap == null) {
            bitmap = mDiskCache.get(key);
        }
        return bitmap;
    }
}
