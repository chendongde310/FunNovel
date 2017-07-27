package lol.chendong.funnovel;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/18 - 19:13
 * 注释：
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initListener();
    }

    public abstract void initData();
    public abstract void initView();
    public abstract void initListener();




}
