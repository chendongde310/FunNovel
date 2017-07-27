package lol.chendong.noveldata.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/17 - 13:27
 * 注释：排行榜对象
 */
public class NovelRankBean implements Parcelable {

    private String type; //类型
    private String name; //名字
    private String author; //作者
    private String data; //日期
    private String CatalogUrl; //详情地址
    private String newChapter; //最新章节
    private String newUrl; //最新章节地址
    private String state; //文章状态
    private String start; //文章点击数，人气


    public NovelRankBean( ) {

    }

    @Override
    public String toString() {
        return "NovelRankBean{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", data='" + data + '\'' +
                ", CatalogUrl='" + CatalogUrl + '\'' +
                ", newChapter='" + newChapter + '\'' +
                ", newUrl='" + newUrl + '\'' +
                ", state='" + state + '\'' +
                ", start='" + start + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCatalogUrl() {
        return CatalogUrl;
    }

    public void setCatalogUrl(String catalogUrl) {
        CatalogUrl = catalogUrl;
    }

    public String getNewChapter() {
        return newChapter;
    }

    public void setNewChapter(String newChapter) {
        this.newChapter = newChapter;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeString(this.data);
        dest.writeString(this.CatalogUrl);
        dest.writeString(this.newChapter);
        dest.writeString(this.newUrl);
        dest.writeString(this.state);
        dest.writeString(this.start);
    }

    protected NovelRankBean(Parcel in) {
        this.type = in.readString();
        this.name = in.readString();
        this.author = in.readString();
        this.data = in.readString();
        this.CatalogUrl = in.readString();
        this.newChapter = in.readString();
        this.newUrl = in.readString();
        this.state = in.readString();
        this.start = in.readString();
    }

    public static final Parcelable.Creator<NovelRankBean> CREATOR = new Parcelable.Creator<NovelRankBean>() {
        @Override
        public NovelRankBean createFromParcel(Parcel source) {
            return new NovelRankBean(source);
        }

        @Override
        public NovelRankBean[] newArray(int size) {
            return new NovelRankBean[size];
        }
    };
}
