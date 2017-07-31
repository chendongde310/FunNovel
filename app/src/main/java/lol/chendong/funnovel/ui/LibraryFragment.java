package lol.chendong.funnovel.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import java.util.List;

import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;
import lol.chendong.noveldata.RandomData;
import lol.chendong.noveldata.SearchData;
import lol.chendong.noveldata.bean.NovelDetailsBean;
import lol.chendong.noveldata.bean.NovelSearchBean;
import rx.Subscriber;

/**
 * 作者：陈东
 * 日期：2017/7/29 0029 - 5:03
 * 书库
 */
public class LibraryFragment extends Fragment {


    private android.widget.Button randomread;
    private EditText editText;

    public LibraryFragment() {

    }

    public static LibraryFragment newInstance() {

        return new LibraryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_library, container, false);
        this.randomread = (Button) rootView.findViewById(R.id.random_read);
        editText = (EditText) rootView.findViewById(R.id.editText);
        randomread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editStr = editText.getText().toString();
                if (!TextUtils.isEmpty(editStr)) {
                    getSearch(editStr);
                } else {
                    getRandom();
                }
            }
        });

        return rootView;
    }


    private void getRandom() {
        RandomData.create(1).getRandomNovel().subscribe(new Subscriber<List<NovelDetailsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<NovelDetailsBean> novelDetailsBeen) {
                ReadHelper.create().catalog(getContext(), novelDetailsBeen.get(0));
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
                for (NovelSearchBean bean : novelSearchBeen) {
                    Logger.d(bean.toString());

                }
                ReadHelper.create().catalog(getContext(), novelSearchBeen.get(0));
            }
        });
    }


}
