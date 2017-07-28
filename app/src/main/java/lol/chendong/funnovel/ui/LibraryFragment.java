package lol.chendong.funnovel.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;
import lol.chendong.noveldata.RandomData;
import lol.chendong.noveldata.bean.NovelDetailsBean;
import rx.Subscriber;

/**
 * 作者：陈东
 * 日期：2017/7/29 0029 - 5:03
 * 书库
 */
public class LibraryFragment extends Fragment {


    private android.widget.Button randomread;

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
        randomread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandom();
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

}
