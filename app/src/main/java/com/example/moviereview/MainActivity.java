package com.example.moviereview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView upCount;
    TextView downCount;
    ImageButton thumbUp;
    ImageButton thumbDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upCount = (TextView)findViewById(R.id.up_count);
        downCount = (TextView)findViewById(R.id.down_count);

        thumbUp = (ImageButton)findViewById(R.id.btn_thumb_up);
        thumbDown = (ImageButton)findViewById(R.id.btn_thumb_down);

        thumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upCount.setText(Integer.parseInt(upCount.getText().toString()) + 1 + "");
            }
        });
        thumbDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downCount.setText(Integer.parseInt(downCount.getText().toString()) + 1 + "");
            }
        });
    }
}
