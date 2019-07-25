package com.example.moviereview;

/**
 * Parcelable 객체를 리스트(CommentDataList)에 하나의 객체만 생성하여 데이터만 바꾸며 넣으려 했지만
 * 하나의 객체당 하나의 Parcelable를 가져 데이터만 바꿔서 넣으면 모든 리스트의 값들이 변하게 된다.
 * 이는 C++과 객체지향과 다름을 보여주고 있다.(객체는 레퍼런스를 참조하게 된다.)
 */
@Deprecated
public class CommentDataSingleton {
    CommentData commentData = new CommentData();
    private static class SingletonHolder{
        public static final CommentDataSingleton Instance = new CommentDataSingleton();
    }

    private CommentDataSingleton(){}

    public static CommentDataSingleton getInstance(){
        return SingletonHolder.Instance;
    }

    public void setData(CommentItem commentItem){
        this.commentData.id = commentItem.id;
        this.commentData.time = commentItem.time;
        this.commentData.comment = commentItem.comment;
        this.commentData.recommendationCount = commentItem.recommendationCount;
        this.commentData.ratingScore = commentItem.ratingScore;
        //this.commentData.profileImage = commentItem.profileImage;
    }
    public CommentData getData(){
        return this.commentData;
    }
}
