package lol.chendong.noveldata;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lol.chendong.noveldata.bean.NovelDetailsBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁▁
 * ▎作者：chendong
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/18 - 9:17
 * ▎注释：随机获取小说
 * ▎更新内容：
 * ▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔▔
 */
public class RandomData {
    private static final String URL = "http://www.biqulou.net";
    private int num;


    private RandomData(int num) {
        this.num = num;
    }

    /**
     * 创建随机数据获取
     *
     * @param num 获取几个
     * @return
     */
    public static RandomData create(int num) {
        return new RandomData(num);
    }

    /**
     * 获取随机小说  ，取值 149-141310    TODO--想办法避免重复
     * http://www.biqulou.net/24/24835/
     *
     * @return
     */
    private String getRandomUrl() {
        int no = (int) ((Math.random() * 141161) + 149);
        String url = URL + "/0/" + no;
        return url;
    }


    /**
     * 获取小说目录详情
     *
     * @return 详情类列表，不包含章节列表，章节仍需要通过CatalogData获取
     */
    public Observable<List<NovelDetailsBean>> getRandomNovel() {
        return Observable.create(new Observable.OnSubscribe<List<NovelDetailsBean>>() {

            @Override
            public void call(Subscriber<? super List<NovelDetailsBean>> subscriber) {
                List<NovelDetailsBean> list = new ArrayList<NovelDetailsBean>();
                for (int i = 0; i < num; i++) {
                    String url = getRandomUrl();
                    Logger.d(url);
                    try {
                        NovelDetailsBean novelBean = new NovelDetailsBean();
                        Document index = Jsoup.connect(url).timeout(15*1000).get();
                        novelBean.setCatalogUrl(url);
                        Element info = index.getElementById("info");
                        novelBean.setName(info.getElementsByTag("h1").first().text());
                        Element author = info.getElementsByTag("p").first();
                        novelBean.setAuthor(author.text().substring(5));
                        novelBean.setUpdataTime(author.nextElementSibling().nextElementSibling().text().substring(5));
                        Element newAdd = author.nextElementSibling().nextElementSibling().nextElementSibling().getElementsByTag("a").first();
                        novelBean.setNewChapter(newAdd.text());
                        novelBean.setNewUrl(url + "/" + newAdd.attr("href"));
                        Element describe = index.getElementById("intro");
                        novelBean.setDescribe(describe.text());
                        Element img = index.getElementById("fmimg");
                        novelBean.setImgUrl(URL + img.getElementsByTag("img").first().attr("src"));
                        list.add(novelBean);
                    } catch (Exception e) {
                        if (e instanceof IOException) {
                            i--;
                            e.printStackTrace();
                        } else {
                            subscriber.onError(e);
                        }
                    }
                }
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());

    }


}
