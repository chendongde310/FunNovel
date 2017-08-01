package lol.chendong.funnovel.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import lol.chendong.funnovel.BaseActivity;
import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;

public class FristActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ReadHelper.create().bookcase(FristActivity.this);
                finish();
            }
        }, 0);



    }


    @Override
    public void initListener() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }







}
