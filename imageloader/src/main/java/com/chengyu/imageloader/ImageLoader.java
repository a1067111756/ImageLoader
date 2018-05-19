package com.chengyu.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 澄鱼 on 2018/5/20.
 *  负责图片的加载逻辑
 */

public class ImageLoader {

    // 图片缓存
    ImageCache mImageCache = new MemoryCache();
    // 线程池， 线程数量为CPU数目
    ExecutorService mExecutorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public void setmImageCache(ImageCache imageCache) {
        mImageCache = imageCache;
    }

    public void displayImage(final String url, final ImageView imageView) {

        Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        // 图片没有缓存， 提交到线程池中下载图片
        submitLoadRequest(url, imageView);
    }


    private void submitLoadRequest(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(imageUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(imageUrl, bitmap);
            }
        });
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
