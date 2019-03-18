package com.news.grab.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

/**
 * Source of the news article
 * Only the mandatory details are included
 */
public class ArticleSource implements Parcelable {
    @ColumnInfo(name = "id")
    private final String id;
    @ColumnInfo(name = "name")
    private final String name;

    /**
     * @param id   id of the news source, example <b>cnn</b>
     * @param name display name of news source, example <b>CNN</b>
     */
    public ArticleSource(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ArticleSource{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected ArticleSource(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<ArticleSource> CREATOR = new Creator<ArticleSource>() {
        @Override
        public ArticleSource createFromParcel(Parcel source) {
            return new ArticleSource(source);
        }

        @Override
        public ArticleSource[] newArray(int size) {
            return new ArticleSource[size];
        }
    };
}
