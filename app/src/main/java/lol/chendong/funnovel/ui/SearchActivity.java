package lol.chendong.funnovel.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import dmax.dialog.SpotsDialog;
import lol.chendong.funnovel.BaseActivity;
import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;
import lol.chendong.funnovel.adapter.SearchAdapter;
import lol.chendong.noveldata.SearchData;
import lol.chendong.noveldata.bean.NovelSearchBean;
import rx.Subscriber;

/**
 * 作者：此屁天下之绝响
 * Github:www.github.com/chendongde310
 * 日期：2017/1/23 - 16:01
 * 注释：搜索
 * 更新内容：
 */
public class SearchActivity extends BaseActivity {

    SearchAdapter adapter;
    String searchText = "";
    AlertDialog dialog;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private int index = 0;

    /**
     * 关闭软键盘
     *
     * @param view
     * @param mContext
     */
    public void closeKeybord(View view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mSearchView = (SearchView) findViewById(R.id.search);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(false);
        mSearchView.onActionViewExpanded();
//          mSearchView.setQueryHint("搜索书名");
        mRecyclerView = (RecyclerView) findViewById(R.id.search_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.setEmptyView(View.inflate(this, R.layout.view_empty, null));
        adapter.openLoadAnimation();
        adapter.setPreLoadNumber(6);
    }

    @Override
    public void initListener() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String queryText) {
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Logger.d("搜索" + queryText);
                searchText = queryText;
                closeKeybord(mSearchView, SearchActivity.this); // 关闭软键盘
                mSearchView.clearFocus(); // 不获取焦点
                index = 0;
                getSearch(searchText);
                return true;
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                index++;
                getSearch(searchText);
            }
        }, mRecyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ReadHelper.create().catalog(SearchActivity.this, (NovelSearchBean) baseQuickAdapter.getData().get(i));
            }
        });

    }

    private void getSearch(String search) {
        if (TextUtils.isEmpty(search)) {
            return;
        }
        if (index == 0) {
            dialog = new SpotsDialog(this);
            dialog.show();
            dialog.setMessage("搜索中");
        }
        SearchData.create(search).getSearchData(index).subscribe(new Subscriber<List<NovelSearchBean>>() {
            @Override
            public void onCompleted() {
                adapter.loadMoreComplete();
                if (dialog != null)
                    dialog.dismiss();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (dialog != null)
                    dialog.dismiss();
            }

            @Override
            public void onNext(List<NovelSearchBean> novelSearchBeen) {
                if (index == 0) {
                    mRecyclerView.scrollToPosition(0);
                    adapter.setNewData(novelSearchBeen);
                }
                if (novelSearchBeen.size() == 0) {
                    adapter.loadMoreEnd();
                } else {
                    adapter.addData(novelSearchBeen);
                }

            }
        });
    }
}
