package lol.chendong.funnovel.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

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
    private SearchView mSearchView;
    private ListView mRecyclerView;
    private int index = 0;
    private List<NovelSearchBean> datas;

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
        datas = new ArrayList<>();
    }

    @Override
    public void initView() {
        mSearchView = (SearchView) findViewById(R.id.search);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(false);
        mSearchView.onActionViewExpanded();
//          mSearchView.setQueryHint("搜索书名");
        mRecyclerView = (ListView) findViewById(R.id.search_list);
        // mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter(this, datas);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setEmptyView(View.inflate(this, R.layout.view_empty, null));
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
                closeKeybord(mSearchView, SearchActivity.this); // 关闭软键盘
                mSearchView.clearFocus(); // 不获取焦点
                index = 0;
                getSearch(queryText);
                return true;
            }
        });

        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReadHelper.create().catalog(SearchActivity.this, datas.get(position));
            }
        });
    }


    private void getSearch(String search) {
        SearchData.create(search).getSearchData(index).subscribe(new Subscriber<List<NovelSearchBean>>() {
            @Override
            public void onCompleted() {
                index++;
            }

            @Override
            public void onError(Throwable e) {
                datas.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNext(List<NovelSearchBean> novelSearchBeen) {
                if (index == 0) {
                    datas.clear();
                }
                datas.addAll(novelSearchBeen);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
