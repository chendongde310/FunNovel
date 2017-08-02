package lol.chendong.funnovel.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import lol.chendong.funnovel.R;
import lol.chendong.noveldata.bean.NovelSearchBean;

/**
 * Author：陈东
 * Time：2017/7/31 - 下午4:34
 * Notes:
 */
public class SearchAdapter extends BaseQuickAdapter<NovelSearchBean, BaseViewHolder> {


    public SearchAdapter() {
        super(R.layout.item_search);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, NovelSearchBean bean) {
        baseViewHolder.setText(R.id.search_updataTime, String.format("最后更新：%s", bean.getUpdataTime()))
                .setText(R.id.search_author, String.format("作者：%s", bean.getAuthor()))
                .setText(R.id.search_title, bean.getName())
                .setText(R.id.search_type, bean.getType());
        Glide.with(mContext).load(bean.getImgUrl()).centerCrop().into((ImageView) baseViewHolder.getView(R.id.search_poster_img));
    }
}
