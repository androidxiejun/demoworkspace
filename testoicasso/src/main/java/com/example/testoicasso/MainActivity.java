package com.example.testoicasso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

//    private static final sun.misc.Unsafe U = sun.misc.Unsafe.getUnsafe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private void doTest(){
       Picasso.with(this)
               .load("")
               .into(new ImageView(this));

        Glide.with(this)
                .load("")
                .into(new ImageView(this));

    }
}
