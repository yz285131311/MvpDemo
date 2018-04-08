# MvpDemo
提供了一套MVP的基类实现


## 添加依赖
```
  compile 'com.whisper.mvp:mvp:1.1.0'
```

### Activity
```
xxxActivity extentds MVPActivity<xxPresenter> implements xxBaseView {
...
}
```

### Presenter 
```
xxxPresenter extentds BasePresenter<XXBaseModel,XXBaseView> {
...
}
```

### BaseModel 
```
xxxModel extentds BaseModel{
...
}
```

### BaseView 
```
为了减少代码，可以在XXPresenter类中定义一个内部类实现BaseView, 由activity去实现
View extentds BaseView{
...
}
```
