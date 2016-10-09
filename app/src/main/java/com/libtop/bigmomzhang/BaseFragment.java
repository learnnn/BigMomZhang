package com.libtop.bigmomzhang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import rx.Subscription;


/**
 * Created by LianTu on 2016-10-8.
 */

public class BaseFragment extends Fragment
{

    protected Subscription subscription;
    protected FragmentManager mFm;

    private boolean injected = false;
    View rootView;//缓存Fragment view


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mFm = getChildFragmentManager();
    }


    protected void unsubscribe()
    {
        if (subscription != null && !subscription.isUnsubscribed())
        {
            subscription.unsubscribe();
        }
    }

//    @Override
//    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        injected = true;
//        if (rootView == null)
//        {
//            View view = inflater.inflate(getLayoutId(), container, false);
//            rootView = view;
//            if (view == null)
//            {
//                throw new NullPointerException("please set fixed layout id!");
//            }
//            ButterKnife.bind(this, view);
//            onCreation(rootView);
//        }
//
//
//        ViewGroup parent = (ViewGroup) rootView.getParent();
//        if (parent != null)
//        {
//            parent.removeView(rootView);
//        }
//        return rootView;
//    }
//
//
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//        if (!injected)
//        {
//            ButterKnife.bind(this, this.getView());
//            onCreation(this.getView());
//        }
//    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unsubscribe();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mFm = null;
    }

}
