package com.whisper.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
* @author yangzheng
* @time 2017/7/24 18:42
* @description 
*/
public abstract class MvpFragment<P extends BasePresenter> extends Fragment {
    protected P mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(getLayoutId(),null);
        initPresenter();
        return view;
    }



    private void initPresenter() {
        mPresenter = MvpBinder.initPresenter(this);
        if(mPresenter != null) {
            mPresenter.setView(this);
            mPresenter.setActivity(getActivity());
            mPresenter.onCreate();
            Log.d("MvpFragment", "initPresneter " + mPresenter);
        }
    }

    protected abstract int getLayoutId();

}
