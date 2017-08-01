package lol.chendong.funnovel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lol.chendong.noveldata.bean.NovelDetailsBean;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/23 - 14:59
 * 注释：
 */
public class CatalogBean implements Parcelable {

    int piont; // 当前观看章节位置
    private NovelDetailsBean detailsBean; // 小说详情

    public int getPiont() {
        return piont<0?0:piont;
    }

    public void setPiont(int piont) {
        this.piont = piont;
    }

    public NovelDetailsBean getDetailsBean() {
        return detailsBean;
    }

    public void setDetailsBean(NovelDetailsBean detailsBean) {
        this.detailsBean = detailsBean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.piont);
        dest.writeParcelable(this.detailsBean, flags);
    }

    public CatalogBean() {
    }

    protected CatalogBean(Parcel in) {
        this.piont = in.readInt();
        this.detailsBean = in.readParcelable(NovelDetailsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CatalogBean> CREATOR = new Parcelable.Creator<CatalogBean>() {
        @Override
        public CatalogBean createFromParcel(Parcel source) {
            return new CatalogBean(source);
        }

        @Override
        public CatalogBean[] newArray(int size) {
            return new CatalogBean[size];
        }
    };
}
