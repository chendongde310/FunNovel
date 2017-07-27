package lol.chendong.noveldata.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/16 - 17:52
 * 注释：文章内容类
 */
public class NovelContentBean implements Parcelable {


    String title; // 标题
    String content;// 内容
    String currentUrl;//当前文章地址
    String lastUrl;//上一篇文章地址
    String nextUrl;//下一篇文章地址


    public NovelContentBean() {

    }

    public NovelContentBean(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getLastUrl() {
        return lastUrl;
    }

    public void setLastUrl(String lastUrl) {
        this.lastUrl = lastUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.currentUrl);
        dest.writeString(this.lastUrl);
        dest.writeString(this.nextUrl);
    }

    protected NovelContentBean(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.currentUrl = in.readString();
        this.lastUrl = in.readString();
        this.nextUrl = in.readString();
    }

    public static final Parcelable.Creator<NovelContentBean> CREATOR = new Parcelable.Creator<NovelContentBean>() {
        @Override
        public NovelContentBean createFromParcel(Parcel source) {
            return new NovelContentBean(source);
        }

        @Override
        public NovelContentBean[] newArray(int size) {
            return new NovelContentBean[size];
        }
    };
}











