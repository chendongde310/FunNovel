package lol.chendong.noveldata;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import lol.chendong.noveldata.bean.NovelChapter;
import lol.chendong.noveldata.bean.NovelContentBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/16 - 17:49
 * 注释：内容数据
 */
public class ContentData {
    private NovelContentBean contentBean;
    private String this_url;
    private int TimeOut = 1000 * 15;

    private ContentData(NovelContentBean contentBean) {
        this.contentBean = contentBean;
        this_url = contentBean.getCurrentUrl();
    }

    /**
     * 创建内容页数据
     *
     * @param chapter 章节对象
     * @return
     */
    public static ContentData create(NovelChapter chapter) {

        return new ContentData(new NovelContentBean(chapter.getUrl()));
    }

    /**
     * 创建内容页数据
     *
     * @param contentBean 内容页对象
     * @return
     */
    public static ContentData create(NovelContentBean contentBean) {

        return new ContentData(contentBean);
    }

    /**
     * 创建内容页数据，不要直接使用这个方法创建
     *
     * @param url 内容页地址
     * @return
     */
    public static ContentData create(String url) {

        return new ContentData(new NovelContentBean(url));
    }

    /**
     * 获取下章内容，通过 create(NovelContentBean contentBean)创建才可调用
     *
     * @return
     */
    public Observable<NovelContentBean> getNextContent() {

        this_url = contentBean.getNextUrl();

        return getContent();
    }

    /**
     * 获取上章内容,通过 create(NovelContentBean contentBean)创建才可调用
     *
     * @return
     */
    public Observable<NovelContentBean> getLastContent() {
        this_url = contentBean.getLastUrl();
        return getContent();
    }

    /**
     * 获取本章内容
     *
     * @return
     */
    public Observable<NovelContentBean> getContent() {

        return Observable.create(new Observable.OnSubscribe<NovelContentBean>() {
            @Override
            public void call(Subscriber<? super NovelContentBean> subscriber) {
                if (this_url == null) {
                    subscriber.onError(new NullPointerException("url is null"));
                }
                try {
                    NovelContentBean novelContentBean = new NovelContentBean(this_url);
                    Logger.d(this_url);
                    Document index = Jsoup.connect(this_url).timeout(TimeOut).get();

                    Element bookname = index.getElementsByClass("bookname").first();
                    novelContentBean.setTitle(bookname.getElementsByTag("h1").first().text());
                    String url = splitUrl(this_url);
                    novelContentBean.setNextUrl(this_url);
                    String last = index.getElementById("pager_prev").attr("href");
                    String next = index.getElementById("pager_next").attr("href");
                    if (!"./".equals(last)) {
                        novelContentBean.setLastUrl(url + last);
                    }
                    if (!"./".equals(next)) {
                        novelContentBean.setNextUrl(url + next);
                    }
                    Element content = index.getElementById("content");
                    String contentText = content.html();
                    novelContentBean.setContent(filter(contentText));
                    subscriber.onNext(novelContentBean);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }


    /**
     * TODO- 新增过滤规则库
     * @param contentText
     * @return
     */
    private String filter(String contentText) {
        return contentText.replace("chaptererror();", " ");
    }

    /**
     * 截取链接
     *
     * @param url
     * @return
     */
    private String splitUrl(String url) {
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
        return newurl;
    }

}













