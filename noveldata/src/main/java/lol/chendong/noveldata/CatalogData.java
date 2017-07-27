package lol.chendong.noveldata;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lol.chendong.noveldata.bean.NovelChapter;
import lol.chendong.noveldata.bean.NovelContentBean;
import lol.chendong.noveldata.bean.NovelDetailsBean;
import lol.chendong.noveldata.bean.NovelListBean;
import lol.chendong.noveldata.bean.NovelSearchBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/16 - 16:47
 * 注释：小说章节页数据
 */
public class CatalogData {

    private static final String URL = "http://www.biqulou.net";
    private int TimeOut = 1000*15;
    private String url;

    private CatalogData(String url) {
        this.url = url;
    }

    /**
     * 传入首页列表数据，获取章节目录
     *
     * @param novelListBean
     * @return
     */
    public static CatalogData create(NovelListBean novelListBean) {
        return create(novelListBean.getCatalogUrl());
    }

    /**
     * 传入具体章节内容数据，获取章节目录
     *
     * @param novelContentBean 看小说的时候获取目录
     * @return
     */
    public static CatalogData create(NovelContentBean novelContentBean) {
        return create(splitUrl(novelContentBean.getCurrentUrl()));
    }


    /**
     * 传入搜索数据，获取章节目录
     *
     * @param novelSearchBean 搜索对象
     * @return
     */
    public static CatalogData create(NovelSearchBean novelSearchBean) {
        return create(novelSearchBean.getCatalogUrl());
    }


    /**
     * 传入详情类，获取章节目录
     *
     * @param novelDetailsBean 随机数据获取的 NovelDetailsBean
     * @return
     */
    public static CatalogData create(NovelDetailsBean novelDetailsBean) {
        return create(novelDetailsBean.getCatalogUrl());
    }

    /**
     * 不要使用这个方法创建 ， 传入url，获取章节目录 ,
     *
     * @param url
     * @return
     */
    public static CatalogData create(String url) {
        return new CatalogData(url);
    }

    /**
     * 截取链接
     *
     * @param url
     * @return
     */
    private static String splitUrl(String url) {
        String newurl = "";
        char[] d = url.toCharArray();
        int p = 0;
        for (char c : d) {
            newurl = newurl + c;
            if ('/' == c) {
                p++;
            }
            if (p == 5) {
                break;
            }
        }
        Logger.d(newurl+"/");
        return newurl;
    }

    /**
     * 获取小说目录详情
     *
     * @return
     */
    public Observable<NovelDetailsBean> getNovelDetail() {
        return Observable.create(new Observable.OnSubscribe<NovelDetailsBean>() {

            @Override
            public void call(Subscriber<? super NovelDetailsBean> subscriber) {
                try {
                    NovelDetailsBean novelBean = new NovelDetailsBean();
                    novelBean.setCatalogUrl(url);
                    Document index = Jsoup.connect(url).get();
                    Element info = index.getElementById("info");
                    novelBean.setName(info.getElementsByTag("h1").first().text());
                    Element author = info.getElementsByTag("p").first();
                    novelBean.setAuthor(author.text().substring(5));
                    novelBean.setUpdataTime(author.nextElementSibling().nextElementSibling().text().substring(5));
                    Element newAdd = author.nextElementSibling().nextElementSibling().nextElementSibling().getElementsByTag("a").first();
                    novelBean.setNewChapter(newAdd.text());
                    novelBean.setNewUrl(url + newAdd.attr("href"));
                    Element describe = index.getElementById("intro");
                    novelBean.setDescribe(describe.text());
                    Element img = index.getElementById("fmimg");
                    novelBean.setImgUrl(URL + img.getElementsByTag("img").first().attr("src"));
                    Elements list = index.getElementById("list").getElementsByTag("dd");
                    List<NovelChapter> chapterList = new ArrayList<>();
                    for (Element data : list) {
                        NovelChapter chapter = new NovelChapter();
                        chapter.setChapterName(data.getElementsByTag("a").first().text());
                        chapter.setUrl(url +"/"+ data.getElementsByTag("a").first().attr("href")); // href="7406091.html"
                        chapterList.add(chapter);
                    }
                    novelBean.setChapterList(chapterList);
                    subscriber.onNext(novelBean);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());

    }


}
