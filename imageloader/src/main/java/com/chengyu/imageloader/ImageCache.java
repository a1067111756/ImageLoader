package com.chengyu.imageloader;

import android.graphics.Bitmap;

/**
 * Created by 澄鱼 on 2018/5/20.
 */

public interface ImageCache {
    void put(String key, Bitmap bitmap);
    Bitmap get(String key);
}
