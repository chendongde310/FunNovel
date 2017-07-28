package lol.chendong.funnovel.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lol.chendong.funnovel.BaseActivity;
import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;
import lol.chendong.funnovel.adapter.BookcaseAdapter;
import lol.chendong.funnovel.bean.BookcaseBean;
import lol.chendong.funnovel.bean.ReadBean;
import lol.chendong.funnovel.data.BookcaseHelper;

/**
 * ▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁
 * ▎作者：chendong
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/19 - 9:57
 * ▎注释：书架
 * ▎更新内容：
 * ▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔
 */
public class BookcaseActivity extends BaseActivity {

    private android.widget.ImageView mBookcaseposterimg;
    private android.widget.TextView mBookcasetitle;
    private android.widget.TextView mBookcaseauthor;
    private android.widget.TextView mBookcaseupdataTime;
    private android.widget.TextView mBookcasedescribe;
    private RecyclerView mBookcaseRV;

    private BookcaseAdapter adapter;
    private ReadBean readBean;
    private LinearLayout linearLayout;
    private int line = 3;
    private List<BookcaseBean> bookcases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookcase);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        this.linearLayout = (LinearLayout) findViewById(R.id.bookcase_latebook);
        this.mBookcasedescribe = (TextView) findViewById(R.id.bookcase_describe);
        this.mBookcaseupdataTime = (TextView) findViewById(R.id.bookcase_updataTime);
        this.mBookcaseauthor = (TextView) findViewById(R.id.bookcase_author);
        this.mBookcasetitle = (TextView) findViewById(R.id.bookcase_title);
        this.mBookcaseposterimg = (ImageView) findViewById(R.id.bookcase_poster_img);
        mBookcaseRV = (RecyclerView) findViewById(R.id.bookcase_rv);

    }

    @Override
    public void initListener() {
        mBookcaseRV.addOnScrollListener(new CenterScrollListener());


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadHelper.create().read(BookcaseActivity.this, readBean);
            }
        });

    }


    private void setLateBookData() {
        readBean = BookcaseHelper.BookCase().getLateBook();
        if (readBean != null) {
            linearLayout.setVisibility(View.VISIBLE);
            Glide.with(this).load(readBean.getDetailsBean().getImgUrl()).into(mBookcaseposterimg);
            mBookcasedescribe.setText(readBean.getDetailsBean().getDescribe());
            mBookcaseupdataTime.setText(readBean.getDetailsBean().getUpdataTime());
            mBookcaseauthor.setText(readBean.getDetailsBean().getAuthor());
            mBookcasetitle.setText(readBean.getDetailsBean().getName());
        } else {
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void setAdapterListener() {
        adapter.setOnItemClickListener(new BookcaseAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (postion != bookcases.size()) {
                    if (bookcases.get(postion).getDetailsBean().getChapterList().size() > bookcases.get(postion).getPiont()) {
                        ReadHelper.create().read(BookcaseActivity.this, bookcases.get(postion));
                    } else {
                        Toast.makeText(BookcaseActivity.this, "跳转到目录页", Toast.LENGTH_SHORT).show();
                        jumpCatalog(postion);
                    }
                } else {
                    Toast.makeText(BookcaseActivity.this, "跳转到主页", Toast.LENGTH_SHORT).show();
                    jumpIndex();
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
                    mBookcaseRV.setVisibility(View.VISIBLE);
                } else {
                    mBookcaseRV.setVisibility(View.GONE);
                }

            }
        });
    }


    private void setBookcaseList() {
        bookcases.clear();
        bookcases.addAll(BookcaseHelper.BookCase().list());
        if (bookcases.size() > 0) {
            mBookcaseRV.setVisibility(View.VISIBLE);
            mBookcaseRV.setLayoutManager(new StaggeredGridLayoutManager(line, StaggeredGridLayoutManager.VERTICAL));
            if (adapter == null) {
                adapter = new BookcaseAdapter(BookcaseActivity.this, bookcases);
                mBookcaseRV.setAdapter(adapter);
                setAdapterListener();
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            mBookcaseRV.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setLateBookData();
        setBookcaseList();
    }

    private void jumpIndex() {
        ReadHelper.create().index(this);
    }

    private void jumpCatalog(int postion) {
        ReadHelper.create().catalog(BookcaseActivity.this, bookcases.get(postion));
    }


}
