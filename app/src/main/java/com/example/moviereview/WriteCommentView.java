package com.example.moviereview;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class WriteCommentView extends AppCompatActivity {
    final static int SAVE_REQUEST = 1;
    final static int CANCEL_REQUEST = 2;

    EditText editText;
    RatingBar ratingBar;
    Intent intent;
    TextView ratingText;
    String defaultRatingText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.write_comment_view);

        editText = (EditText)findViewById(R.id.et_write_comment);
        ratingBar = (RatingBar)findViewById(R.id.rb_write_comment_rating_bar);
        intent = getIntent();
        ratingText = (TextView)findViewById(R.id.tv_write_comment_rating_text);
        defaultRatingText = ratingText.getText().toString();

        findViewById(R.id.btn_write_comment_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(SAVE_REQUEST);
            }
        });

        findViewById(R.id.btn_write_comment_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage(CANCEL_REQUEST);
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating != 0){
                    ratingText.setText(defaultRatingText + " (" + Float.toString(rating * 2) + ")");
                } else{
                    ratingText.setText(defaultRatingText);
                }
            }
        });
    }

    public void showMessage(int request){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("안내");
        switch (request){
            case SAVE_REQUEST:
                builder.setMessage("저장 하시겠습니까?");

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("comment", editText.getText().toString());
                        intent.putExtra("rating_score", ratingBar.getRating());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing
                    }
                });
                break;
            case CANCEL_REQUEST:
                builder.setMessage("종료 하시겠습니까?");

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    finish();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing
                    }
                });
                break;
        }
        AlertDialog dialog = builder.create();
        dialog.show();
        /*
            // Can resize alert dialog
            Display display = getWindowManager(). getDefaultDisplay();
            Point size = new Point();
            display. getSize(size);
            int width = size.x - 50;
            int height = size.y - 50;

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(width, height);
        */
    }
}
