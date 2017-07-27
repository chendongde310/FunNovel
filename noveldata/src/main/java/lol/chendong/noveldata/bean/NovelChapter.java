package lol.chendong.noveldata.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ▎作者：此屁天下之绝响
 * ▎Github:www.github.com/chendongde310
 * ▎日期：2017/1/22 - 12:00
 * ▎注释：小说章节
 * ▎更新内容：
 */
public class NovelChapter implements Parcelable {

    public static final Creator<NovelChapter> CREATOR = new Creator<NovelChapter>() {
        @Override
        public NovelChapter createFromParcel(Parcel source) {
            return new NovelChapter(source);
        }

        @Override
        public NovelChapter[] newArray(int size) {
            return new NovelChapter[size];
        }
    };
    private String chapterName;
    private String url;

    public NovelChapter() {

    }

    protected NovelChapter(Parcel in) {
        this.chapterName = in.readString();
        this.url = in.readString();
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.chapterName);
        dest.writeString(this.url);
    }
}
