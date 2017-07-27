package lol.chendong.funnovel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lol.chendong.funnovel.R;
import lol.chendong.funnovel.bean.ReadBean;
import lol.chendong.noveldata.bean.NovelChapter;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/22 - 14:17
 * 注释：
 */
public class ReadChapterAdapter extends BaseAdapter {
    int piont; // 当前观看章节位置
    private Context context;
    private List<NovelChapter> list;
    private String title;

    public ReadChapterAdapter(Context context, ReadBean readBean) {
        this.context = context;
        this.list = readBean.getDetailsBean().getChapterList();
        this.piont = readBean.getPiont();
        this.title = readBean.getContent().getTitle();
    }

    public ReadChapterAdapter(Context context, int piont,List<NovelChapter> list, String title) {
        this.piont = piont;
        this.context = context;
        this.list = list;
        this.title = title;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NovelChapter getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_chapter_list, null);
            viewHolder = new ViewHolder() ;
            viewHolder.textView = (TextView) convertView.findViewById(R.id.read_chapter_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getChapterName());
        viewHolder.textView.setTextColor(context.getResources().getColor(R.color.reader_catalog_text_defult));
        if(piont != -1 ){
            if(piont == position){
                viewHolder.textView.setTextColor(context.getResources().getColor(R.color.reader_catalog_text_present));
            }
        }else {
            if(title.equals(list.get(position).getChapterName())){
                viewHolder.textView.setTextColor(context.getResources().getColor(R.color.reader_catalog_text_present));
            }
        }
        return convertView;
    }


    public void setPosition(int position){

    }

    class ViewHolder {

        TextView textView;

        public ViewHolder() {
        }
    }
}
