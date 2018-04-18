package com.libtop.bigmomzhang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.reactivestreams.Subscription;

import butterknife.ButterKnife;


/**
 * Created by LianTu on 2016-9-29.
 */

public class BaseActivity extends AppCompatActivity
{

    protected Subscription subscription;
//    protected CompositeSubscription _subscriptions = new CompositeSubscription();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onDestroy()
    {
        unsubscribe();
//        _subscriptions.clear();
        super.onDestroy();
    }


    protected void unsubscribe()
    {
//        if (subscription != null && !subscription.isUnsubscribed())
//        {
//            subscription.unsubscribe();
//        }
    }

    protected void setInjectContentView(int layoutId)
    {
        setContentView(layoutId);
        ButterKnife.bind(this);
    }

    /**
     * 覆写startactivity方法
     */
    public void startActivity(Bundle bundle, Class<?> target)
    {
        Intent intent = new Intent(this, target);
        if (bundle != null)
        {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
