package com.whisper.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
* @author yangzheng
* @time 2017/7/21 15:36
* @description  提供MVP支持
*/
public abstract class MVPActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModelAndPresenter();
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null) mPresenter.onDestroy();
        super.onDestroy();
    }

    private void initModelAndPresenter() {
        this.mPresenter = MVPBinder.initPresenter(this);
        if(mPresenter != null) {
            mPresenter.setActivity(this);
            mPresenter.setView(this);
            mPresenter.onCreate(); //提供onCreate入口
        }
        Log.e(this.getClass().getName(),"initModeAndPresenter is invoked ...");
    }
}
