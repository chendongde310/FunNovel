package lol.chendong.funnovel.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import lol.chendong.funnovel.bean.BookcaseBean;
import lol.chendong.funnovel.bean.CatalogBean;
import lol.chendong.funnovel.bean.ReadBean;

/**
 * ▎作者：此屁天下之绝响
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/22 - 16:56
 * ▎注释：
 * ▎更新内容：
 */
public class BookcaseData {

    private SharedPreferences sp;
    private Context context;
    private String bookcaseSPName = "Novel_Bookcase";
    private String mBookcaseListName = "Novel_BookcaseList";
    private String mLateBook = "Novel_LateBook";
    private List<BookcaseBean> bookcases = new ArrayList<>();

    public BookcaseData(Context context) {
        this.context = context;
        sp = context.getSharedPreferences(bookcaseSPName, Context.MODE_PRIVATE);
    }

    public static BookcaseData create(Context context) {
        return new BookcaseData(context);
    }

    public List<BookcaseBean> getBookcases() {
        String BookcaseJson = sp.getString(mBookcaseListName, null);

        if (BookcaseJson != null) {
            try {
                Logger.d(BookcaseJson);
                bookcases = JSON.parseArray(BookcaseJson, BookcaseBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bookcases;
    }


    public boolean putBookcases(BookcaseBean newData) {
        Logger.d(newData.toString());
        List<BookcaseBean> data = new ArrayList<>();
        data.addAll(getBookcases());
        if (isHas(newData, data)) {
            Logger.d("重复添加了");
            Toast.makeText(context, "本书已收藏啦~", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            data.add(newData);
            putBookcases(data);
            Toast.makeText(context, "收藏成功，下次就能在书架找到啦", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    public void putBookcases(List<BookcaseBean> list) {
        SharedPreferences.Editor editor = sp.edit();
        String BookcaseJson = JSON.toJSONString(list);
        editor.putString(mBookcaseListName, BookcaseJson);
        editor.apply();
    }


    /**
     * 判断是否收藏过
     *
     * @param catalogUrl
     * @param datas
     * @return
     */
    private boolean isHas(BookcaseBean catalogUrl, List<BookcaseBean> datas) {
        if (catalogUrl.getDetailsBean().getCatalogUrl() != null) {
            if (datas.size() > 0) {
                for (BookcaseBean data : datas) {
                    if (catalogUrl.getDetailsBean().getCatalogUrl().equals(data.getDetailsBean().getCatalogUrl())) {
                        return true;
                    }
                }
            }
        }
        return false;
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
        SharedPreferences.Editor editor = sp.edit();
        String lateBook = JSON.toJSONString(readBean);
        editor.putString(mLateBook, lateBook);
        editor.apply();
    }


    public ReadBean getLateBook() {
        String lateBook = sp.getString(mLateBook, null);
        ReadBean bean = null;
        if (lateBook != null) {
            try {
                bean = JSON.parseObject(lateBook, ReadBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    public void remove(BookcaseBean bookcaseBean) {
        List<BookcaseBean> datas = new ArrayList<>();
        datas.addAll(getBookcases());
        int removeInt = -1;
        for (int i = 0; i < datas.size(); i++) {
            if (bookcaseBean.getDetailsBean().getCatalogUrl().equals(datas.get(i).getDetailsBean().getCatalogUrl())) {
                removeInt = i;
            }
        }
        if (removeInt != -1) {
            datas.remove(removeInt);
            putBookcases(datas);
        }
    }

    public boolean putBookcases(CatalogBean catalogBean) {
        BookcaseBean newData = new BookcaseBean();
        newData.setPiont(catalogBean.getPiont());
        newData.setDetailsBean(catalogBean.getDetailsBean());
        return putBookcases(newData);
    }
}
