package lol.chendong.funnovel.ui;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import lol.chendong.funnovel.BaseActivity;
import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;
import lol.chendong.funnovel.adapter.ReadChapterAdapter;
import lol.chendong.funnovel.bean.BookcaseBean;
import lol.chendong.funnovel.bean.CatalogBean;
import lol.chendong.funnovel.constant.Constant;
import lol.chendong.funnovel.data.BookcaseHelper;
import lol.chendong.noveldata.bean.NovelDetailsBean;

public class CatalogActivity extends BaseActivity implements View.OnClickListener {

    ReadChapterAdapter catalogAdapter;
    private android.widget.ImageView catalogposterimg;
    private android.widget.TextView catalogtitle;
    private android.widget.TextView catalogauthor;
    private android.widget.TextView catalogupdataTime;
    private android.widget.TextView catalogdescribe;
    private CatalogBean catalogBean;
    private NovelDetailsBean novelDetailsBean;
    private TextView catalognewChapter;
    private ImageView catalogaddCollect;
    private ImageView catalogshowCatalog;
    private ImageView catalogaddComment;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


    }

    @Override
    public void initData() {
        catalogBean = getIntent().getParcelableExtra(Constant.INTENT_CATALOG);
        novelDetailsBean = catalogBean.getDetailsBean();
    }

    private void setData() {
        Glide.with(CatalogActivity.this).load(novelDetailsBean.getImgUrl()).into(catalogposterimg);
        catalogdescribe.setText(String.format("      %s", novelDetailsBean.getDescribe().trim()));
        catalogupdataTime.setText(String.format("最后更新：%s", novelDetailsBean.getUpdataTime()));
        catalogauthor.setText(String.format("作者：%s", novelDetailsBean.getAuthor()));
        catalogtitle.setText(novelDetailsBean.getName());
        catalognewChapter.setText(novelDetailsBean.getNewChapter());

    }

    @Override
    public void initView() {


        this.catalogdescribe = (TextView) findViewById(R.id.catalog_describe);
        this.catalogupdataTime = (TextView) findViewById(R.id.catalog_updataTime);
        this.catalogauthor = (TextView) findViewById(R.id.catalog_author);
        this.catalogtitle = (TextView) findViewById(R.id.catalog_title);
        this.catalogposterimg = (ImageView) findViewById(R.id.catalog_poster_img);
        this.catalognewChapter = (TextView) findViewById(R.id.catalog_newChapter);
        this.catalogaddComment = (ImageView) findViewById(R.id.catalog_addComment);
        this.catalogshowCatalog = (ImageView) findViewById(R.id.catalog_showCatalog);
        this.catalogaddCollect = (ImageView) findViewById(R.id.catalog_addCollect);
        setData();

        if (BookcaseHelper.BookCase().isContains(novelDetailsBean.getCatalogUrl())) {
            catalogaddCollect.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_heart_red));
        }

    }

    @Override
    public void initListener() {
        catalogaddCollect.setOnClickListener(this);
        catalogshowCatalog.setOnClickListener(this);
        catalogaddComment.setOnClickListener(this);
        catalognewChapter.setOnClickListener(this);
    }

    /**
     * 添加到书架（收藏）
     */
    private void addCollect() {
        catalogaddCollect.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_heart_red));
        BookcaseHelper.BookCase().putBookcases(new BookcaseBean(catalogBean.getDetailsBean(), catalogBean.getPiont()), this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.catalog_addCollect:
                addCollect();
                break;
            case R.id.catalog_showCatalog:
                showCatalog();
                break;
            case R.id.catalog_addComment:
                addComment();
                break;
            case R.id.catalog_newChapter:
                ReadHelper.create().read(CatalogActivity.this, catalogBean.getDetailsBean(), catalogBean.getDetailsBean().getChapterList().size() - 1);
                break;

        }
    }

    /**
     * 添加评论
     */
    private void addComment() {
        Toast.makeText(this, "暂未开通评论，请期待下个版本", Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示目录
     */
    private void showCatalog() {

        if (mDialog == null) {
            mDialog = new AlertDialog.Builder(this).create();
            mDialog.show();
            Window window = mDialog.getWindow();
            window.setWindowAnimations(R.style.PopupAnimation);
            View view = View.inflate(this, R.layout.view_reader_catalog_dialog, null);
            mDialog.setContentView(view);
            window = mDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
            final ListView catalogList = (ListView) mDialog.findViewById(R.id.reader_catalog_list_rv);
            catalogAdapter = new ReadChapterAdapter(this, catalogBean.getPiont(), catalogBean.getDetailsBean().getChapterList(), catalogBean.getDetailsBean().getName());
            catalogList.setAdapter(catalogAdapter);
            catalogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mDialog.hide();
                    catalogBean.setPiont(position);
                    ReadHelper.create().read(CatalogActivity.this, catalogBean.getDetailsBean(), position);
                }
            });
        } else {
            mDialog.show();
            catalogAdapter.notifyDataSetChanged();
        }

    }
}
