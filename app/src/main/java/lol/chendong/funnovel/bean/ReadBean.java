package lol.chendong.funnovel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lol.chendong.noveldata.bean.NovelContentBean;
import lol.chendong.noveldata.bean.NovelDetailsBean;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/18 - 19:51
 * 注释：阅读类
 */
public class ReadBean implements Parcelable {
    public static final Parcelable.Creator<ReadBean> CREATOR = new Parcelable.Creator<ReadBean>() {
        @Override
        public ReadBean createFromParcel(Parcel source) {
            return new ReadBean(source);
        }

        @Override
        public ReadBean[] newArray(int size) {
            return new ReadBean[size];
        }
    };
    int piont; // 当前观看章节位置
    private NovelContentBean content; //当前章节内容
    private NovelDetailsBean detailsBean; // 小说详情

    public ReadBean() {

    }

    public ReadBean(NovelContentBean content, NovelDetailsBean detailsBean, int piont) {
        this.content = content;
        this.detailsBean = detailsBean;
        this.piont = piont;
    }

    protected ReadBean(Parcel in) {
        this.content = in.readParcelable(NovelContentBean.class.getClassLoader());
        this.detailsBean = in.readParcelable(NovelDetailsBean.class.getClassLoader());
        this.piont = in.readInt();
    }







    public NovelContentBean getContent() {
        return content;
    }

    public void setContent(NovelContentBean content) {
        this.content = content;
    }

    public NovelDetailsBean getDetailsBean() {
        return detailsBean;
    }

    public void setDetailsBean(NovelDetailsBean detailsBean) {
        this.detailsBean = detailsBean;
    }

    public int getPiont() {
        return piont;
    }

    public void setPiont(int piont) {
        this.piont = piont;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.content, flags);
        dest.writeParcelable(this.detailsBean, flags);
        dest.writeInt(this.piont);
    }
}
