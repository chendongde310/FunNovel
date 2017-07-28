package lol.chendong.funnovel.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;
import lol.chendong.funnovel.adapter.BookcaseAdapter;
import lol.chendong.funnovel.bean.BookcaseBean;
import lol.chendong.funnovel.bean.ReadBean;
import lol.chendong.funnovel.data.BookcaseHelper;


/**
 * 作者：陈东
 * 日期：2017/7/29 0029 - 5:03
 * 书架
 */
public class BookcaseFragment extends Fragment {


    ReadBean book;
    private android.support.v7.widget.RecyclerView bookcaserv;
    private BookcaseAdapter adapter;
    private List<BookcaseBean> bookcases = new ArrayList<>();
    private android.widget.TextView latebook;


    public BookcaseFragment() {

    }

    public static BookcaseFragment newInstance() {
        return new BookcaseFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bookcase, container, false);
        this.latebook = (TextView) rootView.findViewById(R.id.late_book);
        this.bookcaserv = (RecyclerView) rootView.findViewById(R.id.bookcase_rv);
        setLateBookData();
        setBookcaseList();
        latebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadHelper.create().read(getContext(), book);
            }
        });
        return rootView;
    }

    private void setBookcaseList() {
        bookcases.clear();
        bookcases.addAll(BookcaseHelper.BookCase().list());
        if (bookcases.size() > 0) {
            bookcaserv.setVisibility(View.VISIBLE);
            bookcaserv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            if (adapter == null) {
                adapter = new BookcaseAdapter(getContext(), bookcases);
                bookcaserv.setAdapter(adapter);
                setAdapterListener();
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            bookcaserv.setVisibility(View.GONE);
        }
    }

    private void setAdapterListener() {
        adapter.setOnItemClickListener(new BookcaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (bookcases.get(postion).getPiont() >= 0) {
                    ReadHelper.create().read(getContext(), bookcases.get(postion));
                } else {
                    Toast.makeText(getContext(), "跳转到目录页", Toast.LENGTH_SHORT).show();
                    ReadHelper.create().catalog(getContext(), bookcases.get(postion));
                }
            }
        });

        adapter.setOnItemLongClickListener(new BookcaseAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int postion) {
                BookcaseHelper.BookCase().remove(bookcases.get(postion));
                bookcases.remove(postion);
                adapter.notifyDataSetChanged();
                if (bookcases.size() > 0) {
                    bookcaserv.setVisibility(View.VISIBLE);
                } else {
                    bookcaserv.setVisibility(View.GONE);
                }

            }
        });
    }

    private void setLateBookData() {
        book = BookcaseHelper.BookCase().getLateBook();
        if (book != null) {
            latebook.setVisibility(View.VISIBLE);
            latebook.setText(String.format("继续阅读：《%s》 %s", book.getDetailsBean().getName(), book.getDetailsBean().getChapterList().get(book.getPiont()).getChapterName()));
        } else {
            latebook.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setLateBookData();
        setBookcaseList();
    }

}
