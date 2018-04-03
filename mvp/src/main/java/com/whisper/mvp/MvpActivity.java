package com.whisper.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
* @author yangzheng
* @time 2017/7/21 15:36
* @description  提供MVP支持
*/
public abstract class MvpActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initModelAndPresenter();
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null) mPresenter.onDestroy();
        super.onDestroy();
    }

    private void initModelAndPresenter() {
        this.mPresenter = MvpBinder.initPresenter(this);
        if(mPresenter != null) {
            mPresenter.setActivity(this);
            mPresenter.setView(this);
            mPresenter.onCreate(); //提供onCreate入口
        }
        Log.d(this.getClass().getName(),"initModeAndPresenter is invoked ...");
    }

    /** 获取布局文件ID */
    protected abstract int getLayoutId();
}
