package lol.chendong.noveldata.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 * 作者：陈东  —  www.renwey.com
 * 日期：2017/1/16 - 16:36
 * 注释：文章详情类
 */
public class NovelDetailsBean implements Parcelable {

    public static final Parcelable.Creator<NovelDetailsBean> CREATOR = new Parcelable.Creator<NovelDetailsBean>() {
        @Override
        public NovelDetailsBean createFromParcel(Parcel source) {
            return new NovelDetailsBean(source);
        }

        @Override
        public NovelDetailsBean[] newArray(int size) {
            return new NovelDetailsBean[size];
        }
    };
    private String imgUrl;//封面图
    private String name; //名字
    private String author; //作者
    private String describe;//描述
    private String catalogUrl; //详情地址
    private List<NovelChapter> chapterList;//章节列表
    private String updataTime;//最后更新时间
    private String newUrl; // 最后更新地址
    private String newChapter; //最新章节名字


    public NovelDetailsBean() {

    }

    protected NovelDetailsBean(Parcel in) {
        this.imgUrl = in.readString();
        this.name = in.readString();
        this.author = in.readString();
        this.describe = in.readString();
        this.catalogUrl = in.readString();
        this.chapterList = in.createTypedArrayList(NovelChapter.CREATOR);
        this.updataTime = in.readString();
        this.newUrl = in.readString();
        this.newChapter = in.readString();
    }

    @Override
    public String toString() {
        return "NovelDetailsBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", describe='" + describe + '\'' +
                ", catalogUrl='" + catalogUrl + '\'' +
                ", chapterList=" + (chapterList != null ? chapterList.size() : "null") +
                ", updataTime='" + updataTime + '\'' +
                ", newUrl='" + newUrl + '\'' +
                ", newChapter='" + newChapter + '\'' +
                '}';
    }

    public String getCatalogUrl() {
        return catalogUrl;
    }

    public void setCatalogUrl(String catalogUrl) {
        this.catalogUrl = catalogUrl;
    }

    public String getUpdataTime() {

        //  return updataTime.substring(0,updataTime.length()-3);
        return updataTime;
    }

    public void setUpdataTime(String updataTime) {
        this.updataTime = updataTime;
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
        return TextUtils.isEmpty(describe) ? "暂无简介" : describe.replace("&nbp;", "");
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<NovelChapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<NovelChapter> chapterList) {
        this.chapterList = chapterList;
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
        dest.writeString(this.catalogUrl);
        dest.writeTypedList(this.chapterList);
        dest.writeString(this.updataTime);
        dest.writeString(this.newUrl);
        dest.writeString(this.newChapter);
    }
}
