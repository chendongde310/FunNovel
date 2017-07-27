package lol.chendong.funnovel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.List;

import lol.chendong.funnovel.bean.BookcaseBean;
import lol.chendong.funnovel.bean.CatalogBean;
import lol.chendong.funnovel.bean.ReadBean;
import lol.chendong.funnovel.constant.Constant;
import lol.chendong.funnovel.ui.BookcaseActivity;
import lol.chendong.funnovel.ui.CatalogActivity;
import lol.chendong.funnovel.ui.ReaderActivity;
import lol.chendong.funnovel.ui.SearchActivity;
import lol.chendong.noveldata.CatalogData;
import lol.chendong.noveldata.ContentData;
import lol.chendong.noveldata.bean.NovelChapter;
import lol.chendong.noveldata.bean.NovelContentBean;
import lol.chendong.noveldata.bean.NovelDetailsBean;
import lol.chendong.noveldata.bean.NovelSearchBean;
import rx.Observer;
import rx.Subscriber;

/**
 * ▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁
 * ▎作者：chendong
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/18 - 9:43
 * ▎注释：阅读器
 * ▎更新内容：
 * ▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔
 */
public class ReadHelper {
    private static ReadHelper reader;

    private NovelContentBean novelContentBean; //

    private ReadHelper() {

    }

    public static ReadHelper create() {
        if (reader == null) {
            reader = new ReadHelper();
        }
        return reader;
    }


    /**
     * 读书了！
     *
     * @param novelContentBean 内容页
     * @return
     */
    public void read(Context context, NovelContentBean novelContentBean, NovelDetailsBean detailsBean) {
        this.novelContentBean = novelContentBean;
        ReadBean readBean = new ReadBean();
        readBean.setContent(novelContentBean);
        readBean.setDetailsBean(detailsBean);
        readBean.setPiont(-1);
        read(context, readBean);
    }

    /**
     * @param readBean
     */
    public void read(Context context, ReadBean readBean) {
        Intent intent = new Intent(context, ReaderActivity.class);
        intent.putExtra(Constant.INTENT_CONTENT, readBean);
        context.startActivity(intent);
    }


    /**
     * 书架过来
     *
     * @param bookcaseBean
     */
    public void read(final Context context, final BookcaseBean bookcaseBean) {

        ContentData.create(bookcaseBean.getDetailsBean().getChapterList().get(bookcaseBean.getPiont())).getContent()
                .subscribe(new Subscriber<NovelContentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(NovelContentBean novelContentBean) {
                        read(context, novelContentBean, bookcaseBean.getDetailsBean(), bookcaseBean.getPiont());
                    }
                });
    }

    public void read(Context context, NovelContentBean novelContentBean, NovelDetailsBean detailsBean, int postion) {
        read(context, new ReadBean(novelContentBean, detailsBean, postion));
    }

    /***
     * 从目录页过来
     *
     * @param detailsBean
     * @param postion
     */
    public void read(final Context context, final NovelDetailsBean detailsBean, final int postion) {
        ContentData.create(detailsBean.getChapterList().get(postion)).getContent()
                .subscribe(new Observer<NovelContentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(NovelContentBean novelContentBean) {
                        read(context, novelContentBean, detailsBean, postion);
                    }
                });
    }

    /**
     * 传入一个内容数据，待定
     *
     * @param novelContentBean
     */
    public void read(final Context context, final NovelContentBean novelContentBean) {
        CatalogData.create(novelContentBean).getNovelDetail().subscribe(new Subscriber<NovelDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(NovelDetailsBean novelDetailsBean) {
                read(context, novelContentBean, novelDetailsBean, novelDetailsBean.getChapterList().size());
            }
        });
    }

    /**
     * 检查当前章节
     *
     * @param title
     * @param chapterList
     * @return
     */
    private int getPostion(@NonNull String title, @NonNull List<NovelChapter> chapterList) {
        int postion = -1;
        for (int i = 0; i < chapterList.size(); i++) {
            if (title.equals(chapterList.get(i).getChapterName())) {
                postion = i;
            }
        }
        return postion;
    }


    /**
     * 前往详情（目录）
     */
    public void catalog(Context context, CatalogBean catalogBean) {
        Intent intent = new Intent(context, CatalogActivity.class);
        intent.putExtra(Constant.INTENT_CATALOG, catalogBean);
        context.startActivity(intent);
    }

    public void catalog(Context context, ReadBean readBean) {
        CatalogBean catalogBean = new CatalogBean();
        catalogBean.setPiont(readBean.getPiont());
        catalogBean.setDetailsBean(readBean.getDetailsBean());
        catalog(context, catalogBean);
    }


    public void catalog(final Context context, String catalogUrl) {
        CatalogData.create(catalogUrl).getNovelDetail().subscribe(new Subscriber<NovelDetailsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(NovelDetailsBean novelDetailsBean) {
                catalog(context, novelDetailsBean);
            }
        });
    }

    public void catalog(final Context context, NovelDetailsBean detailsBean) {
        if (detailsBean.getChapterList() != null) {
            CatalogBean catalogBean = new CatalogBean();
            catalogBean.setPiont(-1);
            catalogBean.setDetailsBean(detailsBean);
            catalog(context, catalogBean);
        } else {
            CatalogData.create(detailsBean).getNovelDetail().subscribe(new Subscriber<NovelDetailsBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(NovelDetailsBean novelDetailsBean) {
                    catalog(context, novelDetailsBean);
                }
            });
        }
    }

    /**
     * 前往书架
     *
     * @param
     */
    public void bookcase(Context context) {
        Intent intent = new Intent(context, BookcaseActivity.class);
        context.startActivity(intent);
    }


    public void catalog(Context context, BookcaseBean bookcaseBean) {
        CatalogBean catalogBean = new CatalogBean();
        catalogBean.setPiont(bookcaseBean.getPiont());
        catalogBean.setDetailsBean(bookcaseBean.getDetailsBean());
        catalog(context, catalogBean);
    }

    /**
     * 跳转主页
     */
    public void index(Context context) {
        Intent intent = new Intent(context, FristActivity.class);
        context.startActivity(intent);
    }

    /**
     * 跳转搜索
     */
    public void search(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public void catalog(Context context, NovelSearchBean novelSearchBean) {
        catalog(context, novelSearchBean.getCatalogUrl());
    }


}














