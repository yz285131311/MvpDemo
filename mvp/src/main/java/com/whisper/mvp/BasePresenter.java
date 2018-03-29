package com.whisper.mvp;

import android.app.Activity;

/**
* @author yangzheng
* @time 2017/7/20 18:17
* @description MVP - P层
*/
public abstract class BasePresenter<M,V> {
    protected Activity mActivity;
    protected M mModel;
    protected V mView;

    /**
     * 提供初始化入口
     */
    public void onCreate() { }

    public void onDestroy() { }

    public void setModel(M model) { this.mModel = model;}
    public void setView(V view) { this.mView = view; }

    /**
     * 设置关联的Activity
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }
}
