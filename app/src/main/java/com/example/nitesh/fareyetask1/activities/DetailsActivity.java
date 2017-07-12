package com.example.nitesh.fareyetask1.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitesh.fareyetask1.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView url1,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
            title=(TextView)findViewById(R.id.t1);
            url1= (TextView) findViewById(R.id.ima1);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        String titlt= getIntent().getStringExtra("title");
        String url= getIntent().getStringExtra("url");
        String thumb= getIntent().getStringExtra("thumb");
        title.setText(titlt);
        url1.setText(url);
        Picasso.with(this).load(thumb).into(imageView);
        Log.d("detaiuls data ",""+url+""+titlt+""+thumb);
    }
}
