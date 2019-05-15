package com.appmoviles.retodeezer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

public class TrackActivity extends AppCompatActivity {

    private DeezerConnect deezerConnect;

    private ImageView iv_imagen_cancion;
    private TextView tv_nombre_cancion;
    private TextView tv_artista_cancion;
    private TextView tv_album_cancion;
    private TextView tv_duracion;
    private Button btn_escuchar;
    private ImageView iv_atras;

    private long playlist_id;
    private String previewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        iv_imagen_cancion = findViewById(R.id.iv_imagen_cancion);
        tv_nombre_cancion = findViewById(R.id.tv_nombre_cancion_track);
        tv_artista_cancion = findViewById(R.id.tv_artista_cancion_track);
        tv_album_cancion = findViewById(R.id.tv_album);
        tv_duracion = findViewById(R.id.tv_duracion);
        btn_escuchar = findViewById(R.id.btn_escuchar);
        iv_atras = findViewById(R.id.iv_atras_track);

        deezerConnect = DeezerConnect.forApp(MainActivity.APP_ID).withContext(this).build();

        Bundle bundle = getIntent().getExtras();
        long track_id = 0;
        if (bundle!=null){
            track_id = bundle.getLong("id_track");
            playlist_id = bundle.getLong("id_playlist");
            llenarCampos(track_id);
        }

        iv_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrackActivity.this, PlaylistActivity.class);
                i.putExtra("id_playlist", playlist_id);
                Log.e("TrackActivity",playlist_id+"");
                startActivity(i);
                finish();
            }
        });
    }

    private void llenarCampos(final long track_id) {

        RequestListener listener = new JsonRequestListener() {
            @Override
            public void onResult(Object o, Object o1) {
                Track track = (Track) o;
                previewUrl = track.getPreviewUrl();
                Glide.with(getApplicationContext()).load(track.getAlbum().getSmallImageUrl()).into(iv_imagen_cancion);
                tv_nombre_cancion.setText(track.getTitle());
                tv_artista_cancion.setText(track.getArtist().getName());
                tv_album_cancion.setText(track.getAlbum().getTitle());
                int duration = track.getDuration();
                int minutes = (int) duration/60;
                int seconds = duration%60;
                tv_duracion.setText(minutes+" min "+seconds+" seg");

            }

            @Override
            public void onUnparsedResult(String s, Object o) {

            }

            @Override
            public void onException(Exception e, Object o) {

            }
        };

        DeezerRequest request =DeezerRequestFactory.requestTrack(track_id);
        request.setId("Track request");
        deezerConnect.requestAsync(request,listener);

        btn_escuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arrayWeb = previewUrl.split("//");
                Uri web = Uri.parse(arrayWeb[0] + "//" + arrayWeb[1]);
                Intent i = new Intent(Intent.ACTION_VIEW, web);
                Intent chooser = Intent.createChooser(i, "Continuar con:");
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

    }
}
