package lol.chendong.funnovel.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import java.util.List;

import lol.chendong.funnovel.BaseActivity;
import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;
import lol.chendong.noveldata.ContentData;
import lol.chendong.noveldata.SearchData;
import lol.chendong.noveldata.bean.NovelContentBean;
import lol.chendong.noveldata.bean.NovelSearchBean;
import rx.Observer;
import rx.Subscriber;

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
        }, 1000);



    }


    @Override
    public void initListener() {
//        text2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ReadHelper.BookCase(activity).bookcase();
//            }
//        });
//
//        text3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getContent();
//            }
//        });
//
//        text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String editStr = edit1.getText().toString();
//                if (editStr != null && editStr.length() > 0) {
//                    getSearch(editStr);
//                } else {
//                    getRandom();
//                }
//
//            }
//        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void getContent() {
        ContentData.create("http://www.biqulou.net/24/24835/7406090.html").getContent()
                .subscribe(new Observer<NovelContentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(final NovelContentBean novelContentBean) {
                        ReadHelper.create().read(FristActivity.this, novelContentBean);
                    }
                });
    }


    private void getSearch(String search) {
        SearchData.create(search).getSearchData().subscribe(new Subscriber<List<NovelSearchBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<NovelSearchBean> novelSearchBeen) {
                ReadHelper.create().catalog(FristActivity.this, novelSearchBeen.get(0));
            }
        });
    }


}
