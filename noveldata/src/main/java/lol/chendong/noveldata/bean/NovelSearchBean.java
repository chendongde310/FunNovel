package lol.chendong.noveldata.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/17 - 10:03
 * 注释：搜索对象
 */
public class NovelSearchBean implements Parcelable {

    private String imgUrl;//封面图
    private String name; //名字
    private String author; //作者
    private String describe;//描述
    private String updataTime; //更新时间
    private String CatalogUrl; // 文章目录地址
    private String type; // 文章类型

    public NovelSearchBean( ) {

    }

    @Override
    public String toString() {
        return "NovelSearchBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", describe='" + describe + '\'' +
                ", updataTime='" + updataTime + '\'' +
                ", CatalogUrl='" + CatalogUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(String updataTime) {
        this.updataTime = updataTime;
    }

    public String getCatalogUrl() {
        return CatalogUrl;
    }

    public void setCatalogUrl(String catalogUrl) {
        this.CatalogUrl = catalogUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgUrl);
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeString(this.describe);
        dest.writeString(this.updataTime);
        dest.writeString(this.CatalogUrl);
        dest.writeString(this.type);
    }

    protected NovelSearchBean(Parcel in) {
        this.imgUrl = in.readString();
        this.name = in.readString();
        this.author = in.readString();
        this.describe = in.readString();
        this.updataTime = in.readString();
        this.CatalogUrl = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<NovelSearchBean> CREATOR = new Parcelable.Creator<NovelSearchBean>() {
        @Override
        public NovelSearchBean createFromParcel(Parcel source) {
            return new NovelSearchBean(source);
        }

        @Override
        public NovelSearchBean[] newArray(int size) {
            return new NovelSearchBean[size];
        }
    };
}
