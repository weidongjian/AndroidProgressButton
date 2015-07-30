# AndroidProgressButton
a button with progress

![image](https://github.com/weidongjian/AndroidProgressButton/raw/master/art/device-2015-07-16-102914.gif)

 **使用方法**
- 在项目的build.gradle中添加如下代码

```
dependencies {
    compile 'cn.weidongjian.android:progress-button:0.2'
}
```
- 在xml中引用该控件：

```
<cn.xm.weidongjian.progressbuttonlib.ProgressButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="New Button"
        android:paddingLeft="30dp"
        android:paddingRight="30dp"
        android:textColor="@android:color/white"
        android:textSize="16sp"
        android:background="@drawable/selector_button"
        android:id="@+id/button"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true"/>
```

- 在activity中设置各种效果：

```
private ProgressButton button;
button = (ProgressButton) findViewById(R.id.button);
button.startRotate();\\添加并开始旋转progressBar
button.animError();\\显示错误符号
button.animFinish();\\显示正确符号
button.removeDrawable();\\移除progressBar
```

bugs：
目前button的layout属性不可以为MatchParent，不然drawable会跑到最左边

welcome comment
