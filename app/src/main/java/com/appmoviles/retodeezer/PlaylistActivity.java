package com.appmoviles.retodeezer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity implements AdapterTrack.OnItemClickListener{

    private DeezerConnect deezerConnect;

    private ImageView iv_atras;
    private ImageView iv_imagen_lista;
    private TextView tv_nombre_playlist;
    private TextView tv_descripcion;
    private TextView tv_informacion_playlist;
    private RecyclerView lista_tracks;

    private AdapterTrack adapterTrack;

    private long playlist_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        iv_atras = findViewById(R.id.iv_atras);
        iv_imagen_lista = findViewById(R.id.iv_imagen_lista);
        tv_nombre_playlist = findViewById(R.id.tv_nombre_playlist);
        tv_descripcion = findViewById(R.id.tv_descripcion);
        tv_informacion_playlist = findViewById(R.id.tv_informacion_playlist);
        lista_tracks = findViewById(R.id.lista_tracks);

        adapterTrack = new AdapterTrack();
        adapterTrack.setListener(this);
        lista_tracks.setLayoutManager(new LinearLayoutManager(this));
        lista_tracks.setAdapter(adapterTrack);

        deezerConnect = new DeezerConnect(this, MainActivity.APP_ID);

        Bundle bundle = getIntent().getExtras();
        playlist_id = 0;
        if (bundle!=null){
            playlist_id = bundle.getLong("id_playlist");
            llenarCampos(playlist_id);
        }

        iv_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlaylistActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void llenarCampos(long playlist_id) {

        RequestListener listener = new JsonRequestListener() {
            @Override
            public void onResult(Object o, Object o1) {
                adapterTrack.clear();
                Playlist playlist = (Playlist) o;
                Glide.with(getApplicationContext()).load(playlist.getPictureUrl()).into(iv_imagen_lista);
                tv_nombre_playlist.setText(playlist.getTitle());
                tv_descripcion.setText(playlist.getDescription());
                tv_informacion_playlist.setText("("+playlist.getTracks().size()+" canciones) ("+playlist.getFans()+" fans)");
                adapterTrack.setData(new ArrayList<Track>(playlist.getTracks()));
            }

            @Override
            public void onUnparsedResult(String s, Object o) {

            }

            @Override
            public void onException(Exception e, Object o) {

            }
        };

        DeezerRequest request = DeezerRequestFactory.requestPlaylist(playlist_id);
        request.setId("Playlist request");
        deezerConnect.requestAsync(request, listener);

    }


    @Override
    public void onItemClick(Track track) {
        Intent i = new Intent(PlaylistActivity.this, TrackActivity.class);
        i.putExtra("id_track", track.getId());
        i.putExtra("id_playlist", playlist_id);
        Log.e("PlaylistActivity",track.getId()+"");
        startActivity(i);
    }
}
