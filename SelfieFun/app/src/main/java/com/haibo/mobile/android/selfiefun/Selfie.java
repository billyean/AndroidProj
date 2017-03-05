package com.haibo.mobile.android.selfiefun;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by hyan on 3/4/17.
 */

public class Selfie {
    private final Integer id;

    private final String image_timestamp;

    private final Bitmap bitmap;

    public Selfie(Integer id, String image_timestamp, Bitmap bitmap) {
        this.id = id;
        this.image_timestamp = image_timestamp;
        this.bitmap = bitmap;
    }

    public Integer getId() {
        return id;
    }

    public String getImage_timestamp() {
        return image_timestamp;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
