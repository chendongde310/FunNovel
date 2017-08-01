package lol.chendong.funnovel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import lol.chendong.funnovel.R;
import lol.chendong.funnovel.bean.BookcaseBean;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/18 - 20:16
 * 注释：
 */
public class BookcaseAdapter extends RecyclerView.Adapter<BookcaseAdapter.BookcaseViewHolder> {

    private Context context;
    private List<BookcaseBean> bookcaseBeens;
    private onItemClickListener mItemClickListener;
    private onItemLongClickListener mItemLongClickListener;

    public BookcaseAdapter(Context context, List<BookcaseBean> bookcases) {
        this.context = context;
        this.bookcaseBeens = bookcases;
    }


    @Override
    public BookcaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BookcaseViewHolder holder = new BookcaseViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_bookcase_index, parent, false)
                , mItemClickListener, mItemLongClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookcaseViewHolder holder, int position) {
        if (position != bookcaseBeens.size()) {
            BookcaseBean data = bookcaseBeens.get(position);
            holder.mBookcaseName.setText(data.getDetailsBean().getName());
            Glide.with(context).load(data.getDetailsBean().getImgUrl()).centerCrop().into(holder.mBookcaseImg);
            holder.progress.setMax(data.getDetailsBean().getChapterList().size());
            holder.progress.setProgress(data.getPiont());
        }
    }

    @Override
    public int getItemCount() {
        return bookcaseBeens.size();
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

    public class BookcaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView mBookcaseImg;
        TextView mBookcaseName;
        ProgressBar progress;
        private onItemClickListener mListener;
        private onItemLongClickListener mLongClickListener;

        public BookcaseViewHolder(View itemView, onItemClickListener listener, onItemLongClickListener longClickListener) {
            super(itemView);
            mListener = listener;
            mLongClickListener = longClickListener;
            mBookcaseImg = (ImageView) itemView.findViewById(R.id.bookcase_poster_img);
            mBookcaseName = (TextView) itemView.findViewById(R.id.bookcase_poster_name);
            progress = (ProgressBar) itemView.findViewById(R.id.progressBar);

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
