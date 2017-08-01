package lol.chendong.funnovel.data;

import android.content.Context;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lol.chendong.funnovel.bean.BookcaseBean;
import lol.chendong.funnovel.bean.ReadBean;

/**
 * ▎作者：此屁天下之绝响
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/22 - 16:56
 * ▎注释：
 * ▎更新内容：
 */
public class BookcaseHelper {


    private static BookcaseHelper helper;
    ReadBean lateBook;
    private String mBookcaseListName = "Novel_BookcaseList";
    private String mLateBook = "Novel_LateBook";
    private Map<String, BookcaseBean> bookcases = new HashMap<>();

    public BookcaseHelper() {
        bookcases.putAll(Hawk.get(mBookcaseListName, new HashMap<String, BookcaseBean>()));
        lateBook = Hawk.get(mLateBook);
    }

    public static BookcaseHelper BookCase() {
        if (helper == null) {
            helper = new BookcaseHelper();
        }
        return helper;
    }

    public Map<String, BookcaseBean> map() {
        return bookcases;
    }

    public boolean putBookcases(BookcaseBean newData, Context context) {
        if (bookcases.containsKey(newData.getDetailsBean().getCatalogUrl())) {
            if (context != null) {
                Toast.makeText(context, "本书已收藏啦~", Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            if (context != null) {
                Toast.makeText(context, "收藏成功，下次就能在书架找到啦", Toast.LENGTH_SHORT).show();
            }
            bookcases.put(newData.getDetailsBean().getCatalogUrl(), newData);
            save();
            return true;
        }
    }

    public boolean putBookcases(BookcaseBean newData) {
        return putBookcases(newData, null);
    }

    /**
     * 判断是否收藏过
     *
     * @param catalogUrl
     * @return
     */
    public boolean isContains(String catalogUrl) {
        return bookcases.containsKey(catalogUrl);

    }

    /**
     * 添加收藏
     *
     * @param readBean
     * @return
     */
    public boolean putBookcases(ReadBean readBean) {
        BookcaseBean newData = new BookcaseBean();
        newData.setPiont(readBean.getPiont());
        newData.setDetailsBean(readBean.getDetailsBean());
        return putBookcases(newData);
    }

    public void putLateBook(ReadBean readBean) {
        lateBook = readBean;
        update(readBean);
        Hawk.put(mLateBook, readBean);
    }

    public ReadBean getLateBook() {
        Hawk.get(mLateBook);
        return lateBook;
    }

    public void remove(BookcaseBean bookcaseBean) {
        bookcases.remove(bookcaseBean.getDetailsBean().getCatalogUrl());
        save();
    }

    private void save() {
        Hawk.put(mBookcaseListName, bookcases);
    }

    public Collection<BookcaseBean> list() {
        return bookcases.values();
    }

    public int size() {
        return bookcases.size();
    }


    public void update(ReadBean readBean) {
        BookcaseBean newData = new BookcaseBean();
        newData.setPiont(readBean.getPiont());
        newData.setDetailsBean(readBean.getDetailsBean());
        bookcases.put(readBean.getDetailsBean().getCatalogUrl(), newData);
        save();
    }


}
