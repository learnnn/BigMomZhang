package com.libtop.bigmomzhang;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by LianTu on 2016-9-29.
 */

public class ImageLoadUtil
{

    public static void LoadImage(Context context, String url, ImageView imageView){
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).centerInside().fit().into(imageView);
    }
}
