package lol.chendong.noveldata;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lol.chendong.noveldata.bean.NovelRankBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/17 - 13:12
 * 注释：排行榜数据
 */
public class RankListData {
    public static final String TYPE_RANK = "/mokph.html"; // 月排行
    public static final String TYPE_DONE = "/quanben/";  //完结小说
    private static final String BASE_URL = "http://www.biqulou.net";
    private String url;


    private RankListData(String type) {
        url = BASE_URL + type;
    }

    /**
     * 传入TYPE_RANK 或者 TYPE_DONE
     * @param type
     * @return
     */
    private static RankListData create(String type) {
        return new RankListData(type);
    }

    /**
     * 获取月排行数据
     * @return
     */
    public static RankListData createRank( ) {
        return create(TYPE_RANK);
    }

    /**
     * 获取完结小说数据
     * @return
     */
    public static RankListData createDone( ) {
        return create(TYPE_DONE);
    }




    /**
     * 获取排行榜数据
     * @return
     */
    public Observable<List<NovelRankBean>> getRankList() {
        Logger.d(url);
        return Observable.create(new Observable.OnSubscribe<List<NovelRankBean>>() {

            @Override
            public void call(Subscriber<? super List<NovelRankBean>> subscriber) {
                try {
                    List<NovelRankBean> list = new ArrayList<>();
                    Document index = Jsoup.connect(url).get();
                    Elements datalist = index.getElementById("main").getElementsByTag("li");
                    datalist.remove(0);
                    for (Element data : datalist) {
                        NovelRankBean rankBean = new NovelRankBean();
                        String type = data.getElementsByClass("s1").first().text();
                        rankBean.setType(type.substring(1, type.length() - 1));
                        Element s2 = data.getElementsByClass("s2").first().getElementsByTag("a").first();
                        rankBean.setName(s2.text());
                        rankBean.setCatalogUrl(BASE_URL + s2.attr("href"));
                        Element s3 = data.getElementsByClass("s3").first().getElementsByTag("a").first();
                        rankBean.setNewChapter(s3.text());
                        rankBean.setNewUrl(BASE_URL + s3.attr("href"));
                        rankBean.setAuthor(data.getElementsByClass("s4").first().text());
                        rankBean.setStart(data.getElementsByClass("s5").first().text().trim());
                        rankBean.setData(data.getElementsByClass("s6").first().text().trim());
                        rankBean.setState(data.getElementsByClass("s7").first().text().trim());
                        list.add(rankBean);
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


}
