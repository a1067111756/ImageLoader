package com.chengyu.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by 澄鱼 on 2018/5/20.
 * 负责图片的缓存逻辑
 */

public class MemoryCache implements ImageCache{

    // 图片缓存
    LruCache<String, Bitmap> mImageCache = null;

    public MemoryCache() {
        initImageCache();
    }

    private void initImageCache() {
        // 获取可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 取1 / 8 使用于图片缓存
        final int cacheSize = maxMemory / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public void put(String key, Bitmap bitmap) {
        mImageCache.put(key, bitmap);
    }

    @Override
    public Bitmap get(String key) {
        return  mImageCache.get(key);
    }

}
