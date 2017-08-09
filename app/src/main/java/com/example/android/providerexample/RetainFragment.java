package com.example.android.providerexample;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;

/**
 * Created by Administrator on 2017/8/9.
 */

public class RetainFragment extends Fragment {

    private static final String TAG = "RetainFragment";
    public LruCache<String,Bitmap> mRetainCache;

    public RetainFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static RetainFragment findOrCreateRetainFragment(FragmentManager manager){
        RetainFragment retainFragment = (RetainFragment) manager.findFragmentByTag(TAG);
        if (retainFragment == null){
            retainFragment = new RetainFragment();
        }
        return retainFragment;
    }
}
