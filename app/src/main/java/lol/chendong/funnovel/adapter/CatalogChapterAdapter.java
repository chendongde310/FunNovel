package lol.chendong.funnovel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lol.chendong.funnovel.R;
import lol.chendong.funnovel.bean.CatalogBean;
import lol.chendong.noveldata.bean.NovelChapter;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/18 - 20:16
 * 注释：章节列表适配器
 */
public class CatalogChapterAdapter extends RecyclerView.Adapter<CatalogChapterAdapter.ChapterViewHolder> {

    private Context context;
    private List<NovelChapter> list;
    private CatalogBean catalogBean;
    private onItemClickListener mItemClickListener;
    private onItemLongClickListener mItemLongClickListener;

    public CatalogChapterAdapter(Context context, CatalogBean catalogBean) {
        this.catalogBean = catalogBean;
        this.context = context;
        this.list = catalogBean.getDetailsBean().getChapterList();
    }


    @Override
    public ChapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChapterViewHolder holder = new ChapterViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_catalog_chapter, parent, false)
                , mItemClickListener, mItemLongClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChapterViewHolder holder, int position) {
        holder.cTitle.setText(list.get(position).getChapterName());
        holder.cTitle.setTextColor(context.getResources().getColor(R.color.reader_catalog_text_defult));
        if (position == catalogBean.getPiont()) {
            holder.cTitle.setTextColor(context.getResources().getColor(R.color.reader_catalog_text_present));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(onItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(onItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }


    public interface onItemClickListener {
        void onItemClick(View view, int postion);
    }

    public interface onItemLongClickListener {
        void onItemLongClick(View view, int postion);
    }

    public class ChapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        public TextView cTitle;
        private onItemClickListener mListener;
        private onItemLongClickListener mLongClickListener;

        public ChapterViewHolder(View itemView, onItemClickListener listener, onItemLongClickListener longClickListener) {
            super(itemView);
            mListener = listener;
            mLongClickListener = longClickListener;
            cTitle = (TextView) itemView.findViewById(R.id.chapter_title);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        /**
         * 点击监听
         */
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }

        /**
         * 长按监听
         */
        @Override
        public boolean onLongClick(View arg0) {
            if (mLongClickListener != null) {
                mLongClickListener.onItemLongClick(arg0, getPosition());
            }
            return true;
        }
    }


}
