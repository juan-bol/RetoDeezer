package com.appmoviles.retodeezer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class PlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Bundle bundle = getIntent().getExtras();
        String id_playlist = "";
        if (bundle!=null){
            id_playlist=bundle.getString("id_playlist");
            Log.e(">>>",id_playlist);
        }

    }
}
