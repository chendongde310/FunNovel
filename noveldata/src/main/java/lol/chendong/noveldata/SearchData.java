package lol.chendong.noveldata;

import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import lol.chendong.noveldata.bean.NovelSearchBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/17 - 9:19
 * 注释：
 */
public class SearchData {
    public static final String TYPE_ALL = "全部";
    public static final String TYPE_XUANHUAN = "玄幻奇幻";
    public static final String TYPE_WUXIA = "武侠仙侠";
    public static final String TYPE_YANQING = "都市言情";
    public static final String TYPE_JUNSHI = "历史军事";
    public static final String TYPE_KEHUAN = "科幻灵异";
    public static final String TYPE_WANGYOU = "网游竞技";
    public static final String TYPE_NVSHENG = "女生频道";
    public static final String TYPE_TONGREN = "小说同人";
    public static final String TYPE_DONGFANG = "东方玄幻";
    public static final String TYPE_NOT = "未分类";
    public static final String SORT_DEF = "def"; //默认
    public static final String SORT_CLICK = "totalClick"; //点击量
    public static final String SORT_DATA = "dateModified"; //更新日期
    private static final String URL_BASE = "http://zhannei.baidu.com/cse/search?s=3164401634903103732&entry=1&q=";
    private static final String URL_TYPE = "&flt=genre=genre:";
    private static final String URL_SORT = "&srt=";
    private static final String URL_PAGE = "&p=";
    private String name;
    private String url;
    private String sort;
    private String type;
    private final static String ENCODE = "GBK";

    private SearchData(String name, String type, String sort) {
        try {
            this.name = URLEncoder.encode(name, ENCODE);
        } catch (UnsupportedEncodingException e) {
            this.name = name;
        }
        this.type = type;
        this.sort = sort;
        setUrl();
    }

    /**
     * 创建搜索
     *
     * @param name 搜索的名称
     * @param type 类型  TYPE_XUANHUAN 、TYPE_WUXIA 、...
     * @param sort 排序方式 SORT_DEF 、 SORT_CLICK
     * @return
     */
    public static SearchData create(String name, String type, String sort) {
        return new SearchData(name, type, sort);
    }

    /**
     * 创建搜索
     *
     * @param name 名称
     * @param type 类型
     * @return
     */
    public static SearchData create(String name, String type) {
        return create(name, type, SORT_DEF);
    }

    /**
     * 创建搜索
     *
     * @param name 名称
     * @return
     */
    public static SearchData create(String name) {
        return create(name, TYPE_ALL, SORT_DEF);
    }

    /**
     * 设置url
     */
    private void setUrl() {
        if (!TYPE_ALL.equals(type)) {
            url = URL_BASE + name + URL_TYPE + type;
        } else {
            url = URL_BASE + name;
        }
        if (sort.equals(SORT_DEF) || sort.equals(SORT_CLICK) || sort.equals(SORT_DATA)) {
            url = url + URL_SORT + sort;
        }
    }

    /**
     * 获取搜索数据 ,默认第0页
     *
     * @return
     */
    public Observable<List<NovelSearchBean>> getSearchData() {
        return getSearchData(0);
    }

    /**
     * 更改搜索类型
     * @param type
     */
    public SearchData setType(String type) {
        this.type = type;
        setUrl();
        return this;
    }

    /**
     *更改搜索结果排序方式
     * @param sort
     */
    public SearchData setSort(String sort) {
        this.sort = sort;
        setUrl();
        return this;
    }

    public SearchData setName(String name) {
        this.name = name;
        setUrl();
        return this;
    }

    /**
     * 获取搜索数据
     *
     * @param page 第几页数据 ，默认 0
     * @return
     */
    public Observable<List<NovelSearchBean>> getSearchData(final int page) {

        return Observable.create(new Observable.OnSubscribe<List<NovelSearchBean>>() {
            @Override
            public void call(Subscriber<? super List<NovelSearchBean>> subscriber) {
                try {
                    List<NovelSearchBean> list = new ArrayList<NovelSearchBean>();
                    Logger.d(url + URL_PAGE + page);
                    Document index = Jsoup.connect(url + URL_PAGE + page).get();
                    Element resultList = index.getElementsByClass("result-list").first();
                    Elements dataList = resultList.getElementsByClass("result-item result-game-item");
                    for (Element data : dataList) {
                        NovelSearchBean searchBean = new NovelSearchBean();

                        searchBean.setImgUrl(data.getElementsByTag("img").first().attr("src"));
                        searchBean.setName(data.getElementsByClass("result-item-title result-game-item-title").first().text());
                        searchBean.setCatalogUrl(getUrl(data.getElementsByClass("game-legend-a").first().attr("onclick")));

                        Element desc = data.getElementsByClass("result-game-item-desc").first();
                        searchBean.setDescribe(desc.text());
                        Elements infolist = data.getElementsByClass("result-game-item-info-tag");
                        for (int i = 0; i < infolist.size(); i++) {
                            Element text = infolist.get(i);
                            switch (i) {
                                case 0:
                                    searchBean.setAuthor(text.text().trim().substring(4));
                                    break;
                                case 1:
                                    searchBean.setType((text.text().substring(4)));
                                    break;
                                case 2:
                                    searchBean.setUpdataTime(text.text().substring(6));
                                    break;
                            }
                        }
                        list.add(searchBean);
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
     * 获取链接
     *
     * @param s
     * @return
     */
    private String getUrl(String s) {
        String url = "http://www.";
        String s1 = s.substring(26, s.length() - 1);
        url = url + s1;
        return url;

    }


}
