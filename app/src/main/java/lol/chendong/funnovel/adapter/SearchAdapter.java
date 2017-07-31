package lol.chendong.funnovel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import lol.chendong.funnovel.R;
import lol.chendong.noveldata.bean.NovelSearchBean;

/**
 * Author：陈东
 * Time：2017/7/31 - 下午4:34
 * Notes:
 */
public class SearchAdapter extends BaseAdapter {

    private List<NovelSearchBean> datas;
    private Context context;

    public SearchAdapter(Context context, List<NovelSearchBean> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_search, null);
        }

        TextView catalogupdataTime = (TextView) convertView.findViewById(R.id.search_updataTime);
        TextView catalogauthor = (TextView) convertView.findViewById(R.id.search_author);
        TextView catalogtitle = (TextView) convertView.findViewById(R.id.search_title);
        ImageView catalogposterimg = (ImageView) convertView.findViewById(R.id.search_poster_img);
        TextView catalognewType = (TextView) convertView.findViewById(R.id.search_type);
        NovelSearchBean bean = datas.get(position);
        Glide.with(context).load(bean.getImgUrl()).into(catalogposterimg);
        catalogupdataTime.setText(String.format("最后更新：%s", bean.getUpdataTime()));
        catalogauthor.setText(String.format("作者：%s", bean.getAuthor()));
        catalogtitle.setText(bean.getName());
        catalognewType.setText(bean.getType());

        return convertView;
    }


}
