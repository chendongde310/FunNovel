package lol.chendong.funnovel.bean;

import android.os.Parcel;
import android.os.Parcelable;

import lol.chendong.noveldata.bean.NovelDetailsBean;

/**
 * ▎作者：此屁天下之绝响
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/22 - 16:35
 * ▎注释：书架对象
 * ▎更新内容：
 */
public class BookcaseBean implements Parcelable {

    private NovelDetailsBean detailsBean; // 小说详情
    int piont; // 当前观看章节位置


    public NovelDetailsBean getDetailsBean() {
        return detailsBean;
    }

    public void setDetailsBean(NovelDetailsBean detailsBean) {
        this.detailsBean = detailsBean;
    }

    public int getPiont() {
        return piont;//<0?0:piont;
    }

    public void setPiont(int piont) {
        this.piont = piont;
    }

    @Override
    public String toString() {
        return "BookcaseBean{" +
                "detailsBean=" + detailsBean.toString() +
                ", piont=" + piont +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.detailsBean, flags);
        dest.writeInt(this.piont);
    }

    public BookcaseBean() {
    }


    public BookcaseBean(NovelDetailsBean detailsBean, int piont) {
        this.detailsBean = detailsBean;
        this.piont = piont;
    }

    protected BookcaseBean(Parcel in) {
        this.detailsBean = in.readParcelable(NovelDetailsBean.class.getClassLoader());
        this.piont = in.readInt();
    }

    public static final Parcelable.Creator<BookcaseBean> CREATOR = new Parcelable.Creator<BookcaseBean>() {
        @Override
        public BookcaseBean createFromParcel(Parcel source) {
            return new BookcaseBean(source);
        }

        @Override
        public BookcaseBean[] newArray(int size) {
            return new BookcaseBean[size];
        }
    };
}
