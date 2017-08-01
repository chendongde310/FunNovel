package lol.chendong.funnovel.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import lol.chendong.funnovel.BaseActivity;
import lol.chendong.funnovel.R;
import lol.chendong.funnovel.ReadHelper;
import lol.chendong.funnovel.adapter.ReadChapterAdapter;
import lol.chendong.funnovel.bean.ReadBean;
import lol.chendong.funnovel.constant.Constant;
import lol.chendong.funnovel.data.BookcaseHelper;
import lol.chendong.funnovel.view.ReaderTextView;
import lol.chendong.funnovel.view.SwipScrollView;
import lol.chendong.noveldata.ContentData;
import lol.chendong.noveldata.bean.NovelContentBean;
import rx.Observer;

/**
 *
 * 作者：陈东
 * 日期：2017/8/1 0001 - 19:34
 * todo-预加载下一章
 */
public class ReaderActivity extends BaseActivity {

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    PopupMenu popupMenu;
    Menu menu;
    private ReaderTextView mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private LinearLayout mControlsView;  //管理面板
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible = true;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private ReadBean readBean;
    private SwipScrollView mScrollView;
    private Observer addDataObserver;  //加载更多新数据（加在底部）
    private Observer setDataObserver;  //设置新数据（会清空之前的文章）
    private ImageView mButtonCollect; //收藏
    private ImageView mButtonCatalog; //目录
    private ImageView mButtonStyle; //样式
    private ImageView mButtonMoerSet; //更多设置
    private RelativeLayout relativeLayout;
    private ListView catalogList;
    private Animation shakeAnimation;
    private Animation rotateAnimation;
    private AlertDialog mDialog;
    private ReadChapterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

    }

    @Override
    public void initData() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        readBean = getIntent().getParcelableExtra(Constant.INTENT_CONTENT);
        BookcaseHelper.BookCase().putLateBook(readBean);
        addDataObserver = new Observer<NovelContentBean>() {
            @Override
            public void onCompleted() {
                mScrollView.setScrollToBottomEnabled(true);
            }

            @Override
            public void onError(Throwable e) {
                mScrollView.setScrollToBottomEnabled(true);
                e.printStackTrace();
            }

            @Override
            public void onNext(NovelContentBean novelContentBean) {
                if (novelContentBean != null && !readBean.getContent().getCurrentUrl().equals(novelContentBean.getCurrentUrl())) {
                    readBean.setPiont(readBean.getPiont() + 1);
                    BookcaseHelper.BookCase().putLateBook(readBean);
                    readBean.setContent(novelContentBean);
                    mContentView.addContent(readBean.getContent().getTitle(), readBean.getContent().getContent());
                    Toast.makeText(ReaderActivity.this, "已为您加载下一章ヽ(･ω･｡)ﾉ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReaderActivity.this, "没有更多内容啦！先看看其他的吧(-`ω´-) ", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mScrollView.setScrollToBottomEnabled(true);
                        }
                    }, 5000);
                }
            }
        };
        setDataObserver = new Observer<NovelContentBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(NovelContentBean novelContentBean) {
                readBean.setContent(novelContentBean);
                BookcaseHelper.BookCase().putLateBook(readBean);
                mContentView.setContent(readBean.getContent().getTitle(), readBean.getContent().getContent());
                mScrollView.scrollTo(0, 0);
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        };
    }

    @Override
    public void initView() {
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_reader);
        mScrollView = (SwipScrollView) findViewById(R.id.text_scroll);
        mContentView = (ReaderTextView) findViewById(R.id.text_content);
        mControlsView = (LinearLayout) findViewById(R.id.fullscreen_content_controls);
        mButtonCatalog = (ImageView) findViewById(R.id.b1);
        mButtonCollect = (ImageView) findViewById(R.id.b2);
        mButtonStyle = (ImageView) findViewById(R.id.b3);
        mButtonMoerSet = (ImageView) findViewById(R.id.b4);
        mContentView.addContent(readBean.getContent().getTitle(), readBean.getContent().getContent());
        popupMenu = new PopupMenu(this, mButtonMoerSet);
        menu = popupMenu.getMenu();
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reader_moer_setting, menu);
    }

    @Override
    public void initListener() {
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        mContentView.setOnStyleListener(new ReaderTextView.OnColorListener() {
            @Override
            public void onClick(int style) {
                try {
                    relativeLayout.setBackgroundResource(style);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mButtonStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentView.creadteDialog();
            }
        });

        mScrollView.setOnScrollViewScrollToBottom(new SwipScrollView.OnScrollBottomListener() {
            @Override
            public void srollToBottom() {
                if (mScrollView.isSrollToBottom()) {
                    mScrollView.setScrollToBottomEnabled(false);
                    if (readBean.getPiont() < readBean.getDetailsBean().getChapterList().size()) {

                        ContentData.create(readBean.getContent()).getNextContent().subscribe(addDataObserver);
                    } else {
                        Toast.makeText(ReaderActivity.this, "没有更多内容啦！(-`ω´-) 要不大爷先看看其他的~", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mScrollView.setScrollToBottomEnabled(true);
                            }
                        }, 5000);
                    }
                }
            }
        });
        mScrollView.setOnScrollViewListener(new SwipScrollView.OnScrollViewListener() {
            @Override
            public void onScrollChanged(SwipScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (mVisible) {
                    delayedHide(100);
                }
            }
        });


        mButtonCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCatalog();
            }
        });

        mButtonCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCollect();
            }
        });

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_reader_catalog:
                        ReadHelper.create().catalog(ReaderActivity.this, readBean);
                        break;
                    case R.id.action_reader_bookcase:
                        ReadHelper.create().bookcase(ReaderActivity.this);
                        break;
                    case R.id.action_reader_settings:

                        break;
                }
                return true;
            }
        });
    }

    /**
     * 添加到书架（收藏）
     */
    private void addCollect() {
        if (BookcaseHelper.BookCase().putBookcases(readBean)) {
            //成功
            mButtonCollect.startAnimation(rotateAnimation);
        } else {
            //失败
            mButtonCollect.startAnimation(shakeAnimation);
        }
    }

    /**
     * 弹出目录框
     */
    private void showCatalog() {
        if (mDialog != null && mDialog.isShowing()) {
            return;
        }
        mDialog = new AlertDialog.Builder(ReaderActivity.this).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.setWindowAnimations(R.style.PopupAnimation);
        View view = View.inflate(ReaderActivity.this, R.layout.view_reader_catalog_dialog, null);
        mDialog.setContentView(view);
        window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        catalogList = (ListView) mDialog.findViewById(R.id.reader_catalog_list_rv);
        adapter = new ReadChapterAdapter(ReaderActivity.this, readBean);
        catalogList.setAdapter(adapter);
        catalogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readBean.setPiont(position);
                ContentData.create(readBean.getDetailsBean().getChapterList().get(position))
                        .getContent().subscribe(setDataObserver);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BookcaseHelper.BookCase().putLateBook(readBean);
    }

    public void popupmenu(View v) {
        popupMenu.show();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        mControlsView.setVisibility(View.GONE);
        mVisible = false;
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }


    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
