package com.whisper.mvp;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * MVP 粘合剂
* @author yangzheng
* @time 2017/7/21 17:43
* @description 为MVP各层提供绑定接口 负责初始化BasePresenter 、BaseModel
*/
public class MVPBinder {

    /**
     * 初始化Presenter
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T initPresenter(Object object) {
        Type tpPresenter = pasePresenterType(object);
        if(tpPresenter == null)
            return null;
        Type tpModel = getModelFromPresenterType(tpPresenter);
        Log.d("MVPBinder",tpPresenter + "," + tpModel);
        BasePresenter bp = (BasePresenter) getInstance(tpPresenter);
        if(bp != null) {
            BaseModel bm = (BaseModel) getInstance(tpModel);
            bp.setModel(bm);
        }
        return (T) bp;
    }

    /**
     * 获取指定类型的实例对象
     * @param t
     * @return
     */
    public static Object getInstance(Type t) {
        try {
            return  ((Class)t).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析Presenter的具体类型
     * @param o
     * @return
     */
    public static Type pasePresenterType(Object o) {
        Class<?> clazz = o.getClass();
        Type[] types2 = clazz.getGenericInterfaces();
        Log.d("MVPBinder","检测中..1 : "  + clazz.getCanonicalName() + "," + types2.length);
        // 判断当前类型是否是泛型 如果不是 检查父类是不是泛型 循环向上检测 这里需要注意：
        //BaseActivity -> AppCompatActivity -> FragmentActivity -> BaseFragementActivity -> ... -> ContextWrapper -> Context
        //可以看到AppCompatAcitivty间接继承了不少类 而其实在AppCompatActivity之后我们就不需要去做泛型检查了 所以做了如下优化：减少检测层级
        while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            Type type = clazz.getGenericSuperclass();
            clazz = clazz.getSuperclass();
            Log.d("MVPBinder","检测中.. : "  + type );
            if(type == null) {
                Log.e("MVP","type == null");
                return null;
            }
            String cName = ((Class)type).getName();
            if(clazz == null || "android.support.v7.app.AppCompatActivity".equals(cName) || "android.app.Activity".equals(cName) || "android.support.v4.app.Fragment".equals(cName)) {
                //当未设置泛型时 会检测只最顶层的类 所以这里减少检测层级
                // 由于Acitvity继承层级较多，我们其实只需要遍历检测至基类Acitivity中即可 当前项目是基于AppCompateActivity
                //同时也对继承于Activity的项目做了兼容
                Log.e("MVPBinder","找不到泛型类");
                return null;
            }
        }
        Type type = clazz.getGenericSuperclass();
        Log.d("MVPBinder","已找到泛型类: "  + type);
        // 获取泛型的参数列表
        Type[] types = getGeneralTypes(type);
        for(Type t : types) {
            if("P".equals(t.toString())) {
                Log.d("MVPBinder","未使用MVP");
                continue;
            }
            Log.d("MVPBinder","Presenter类型" + t.toString() + " class = " + t.getClass().getName());
            if(BasePresenter.class.isAssignableFrom((Class<?>) t)){
                return t;
            }
        }

        return null;
    }

    /**
     * 获取某类型的泛型参数数组
     * @param type
     * @return
     */
    public static Type[] getGeneralTypes(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] types = parameterizedType.getActualTypeArguments();
        return types;
    }

    /**
     * 解析presenter中的BaseModel类型
     * @param type
     * @return
     */
    public static Type getModelFromPresenterType(Type type) {
        type = ((Class)type).getGenericSuperclass();
        if(!(type instanceof ParameterizedType)) return null;

        Type[] types = ((ParameterizedType)type).getActualTypeArguments();
        for(Type temp : types) {
            if(BaseModel.class.isAssignableFrom((Class)temp)) {
                return temp;
            }
        }

        return null;
    }
}
