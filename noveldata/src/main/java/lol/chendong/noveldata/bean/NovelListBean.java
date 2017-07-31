package lol.chendong.noveldata.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/16 - 15:25
 * 注释：小说列表类
 */
public class NovelListBean implements Parcelable {

    private String type; //类型
    private String name; //名字
    private String newChapter; //最新章节
    private String author = "佚名"; //作者
    private String data = "刚刚"; //日期
    private String catalogUrl; //详情地址
    private String newUrl; //最新章节地址

    public NovelListBean() {

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

    public String getNewChapter() {
        return newChapter;
    }

    public void setNewChapter(String newChapter) {
        this.newChapter = newChapter;
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
        return catalogUrl;
    }

    public void setCatalogUrl(String catalogUrl) {
        this.catalogUrl = catalogUrl;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.name);
        dest.writeString(this.newChapter);
        dest.writeString(this.author);
        dest.writeString(this.data);
        dest.writeString(this.catalogUrl);
        dest.writeString(this.newUrl);
    }

    protected NovelListBean(Parcel in) {
        this.type = in.readString();
        this.name = in.readString();
        this.newChapter = in.readString();
        this.author = in.readString();
        this.data = in.readString();
        this.catalogUrl = in.readString();
        this.newUrl = in.readString();
    }

    public static final Parcelable.Creator<NovelListBean> CREATOR = new Parcelable.Creator<NovelListBean>() {
        @Override
        public NovelListBean createFromParcel(Parcel source) {
            return new NovelListBean(source);
        }

        @Override
        public NovelListBean[] newArray(int size) {
            return new NovelListBean[size];
        }
    };

    @Override
    public String toString() {
        return "NovelListBean{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", newChapter='" + newChapter + '\'' +
                ", author='" + author + '\'' +
                ", data='" + data + '\'' +
                ", catalogUrl='" + catalogUrl + '\'' +
                ", newUrl='" + newUrl + '\'' +
                '}';
    }
}
