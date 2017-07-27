package lol.chendong.noveldata;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lol.chendong.noveldata.bean.NovelListBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/16 - 15:08
 * 注释：首页列表数据
 */
public class HomeListData {

    public static final int TYPE_ALL = 0;
    public static final int TYPE_XUANHUAN = 1;//玄幻奇幻
    public static final int TYPE_WUXIA = 2;//武侠仙侠
    public static final int TYPE_YANQING = 3;//都市言情
    public static final int TYPE_JUNSHI = 4;//历史军事
    public static final int TYPE_KEHUAN = 5;//科幻灵异
    public static final int TYPE_WANGYOU = 6;//网游竞技
    public static final int TYPE_NVSHENG = 7;//女生频道
    private static final String URL = "http://www.biqulou.net";
    private static final String LIST_URL = "http://www.biqulou.net/moksort/";
    private int TimeOut = 1000*15;
    private int type;

    private HomeListData(int novelType) {
        type = novelType;
    }

    public static HomeListData create(int novelType) {
        return new HomeListData(novelType);
    }

    public static HomeListData create() {
        return new HomeListData(TYPE_ALL);
    }

    public HomeListData setType(int type) {
        this.type = type;
        return this;
    }

    /**
     * 获取最近更新数据
     */
    public Observable<List<NovelListBean>> getNewData() {
        return Observable.create(new Observable.OnSubscribe<List<NovelListBean>>() {
            @Override
            public void call(Subscriber<? super List<NovelListBean>> subscriber) {
                List<NovelListBean> list = new ArrayList<>();
                try {
                    Element newsContent = getDocument().getElementById("newscontent");
                    Elements newDataList = newsContent.getElementsByClass("l").first().getElementsByTag("li");
                    for (Element newdata : newDataList) {
                        NovelListBean novelListBean = new NovelListBean();
                        novelListBean.setType(newdata.getElementsByClass("s1").first().text());
                        novelListBean.setName(newdata.getElementsByClass("s2").first().getElementsByTag("a").first().text());
                        novelListBean.setCatalogUrl(URL + newdata.getElementsByClass("s2").first().getElementsByTag("a").first().attr("href"));
                        novelListBean.setNewChapter(newdata.getElementsByClass("s3").first().getElementsByTag("a").first().text());
                        novelListBean.setNewUrl(URL + newdata.getElementsByClass("s3").first().getElementsByTag("a").first().attr("href"));
                        novelListBean.setAuthor(newdata.getElementsByClass("s4").first().text());
                        novelListBean.setData(newdata.getElementsByClass("s5").first().text());
                        list.add(novelListBean);
                    }
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * 获取最热数据
     *
     * @throws IOException
     */
    public Observable<List<NovelListBean>> getHotData() {
        return Observable.create(new Observable.OnSubscribe<List<NovelListBean>>() {
            @Override
            public void call(Subscriber<? super List<NovelListBean>> subscriber) {
                List<NovelListBean> list = new ArrayList<>();
                try {
                    Element newsContent = getDocument().getElementById("newscontent");
                    Elements hotDataList = newsContent.getElementsByClass("r").first().getElementsByTag("li");
                    for (Element hotdata : hotDataList) {
                        NovelListBean novelListBean = new NovelListBean();
                        novelListBean.setType(hotdata.getElementsByClass("s1").first().text());
                        novelListBean.setName(hotdata.getElementsByClass("s2").first().getElementsByTag("a").first().text());
                        novelListBean.setCatalogUrl(URL + hotdata.getElementsByClass("s2").first().getElementsByTag("a").first().attr("href"));
                        novelListBean.setNewChapter(hotdata.getElementsByClass("s2").first().getElementsByTag("a").first().text() + "最新章节");
                        novelListBean.setNewUrl(URL + hotdata.getElementsByClass("s2").first().getElementsByTag("a").first().attr("href"));
                        novelListBean.setAuthor(hotdata.getElementsByClass("s5").first().text());
                        list.add(novelListBean);
                    }
                    subscriber.onNext(list);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }











    /**
     * 获取网页
     *
     * @return
     * @throws IOException
     */
    private Document getDocument() throws IOException {
        Document index;
        if (type == TYPE_XUANHUAN
                || type == TYPE_JUNSHI
                || type == TYPE_KEHUAN
                || type == TYPE_NVSHENG
                || type == TYPE_YANQING
                || type == TYPE_WUXIA
                || type == TYPE_WANGYOU) {
            Logger.d(LIST_URL + type + "/1.html");
            index = Jsoup.connect(LIST_URL + type + "/1.html").get();
        } else {
            index = Jsoup.connect(URL).get();
        }
        return index;
    }

}
