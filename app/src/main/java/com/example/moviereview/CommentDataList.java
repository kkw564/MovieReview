package com.example.moviereview;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.stream.Stream;

public class CommentDataList extends ArrayList<Parcelable> implements Parcelable {
    ArrayList<CommentData> items = new ArrayList<>();

    protected CommentDataList(Parcel in) {
        items = new ArrayList<>();
        in.readTypedList(items, CommentData.CREATOR);
    }
    protected CommentDataList(CommentData item) {
        items.add(item);
    }
    protected CommentDataList(ArrayList<CommentData> items) { this.items = items; }

    public static final Creator<CommentDataList> CREATOR = new Creator<CommentDataList>() {
        @Override
        public CommentDataList createFromParcel(Parcel in) {
            return new CommentDataList(in);
        }

        @Override
        public CommentDataList[] newArray(int size) {
            return new CommentDataList[size];
        }
    };

    public CommentDataList() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
    }

    public int size() {
        return items.size();
    }

    public CommentData get(int index){
        return items.get(index);
    }

    @Override
    public Stream<Parcelable> parallelStream() {
        return null;
    }

    public void addItem(CommentData item){
        items.add(item);
    }
}
