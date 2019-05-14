package com.appmoviles.retodeezer;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterPlaylist.OnItemClickListener{

    public static final String APP_ID = "347404";
    private DeezerConnect deezerConnect;

    private AdapterPlaylist adapterPlaylist;

    private RecyclerView lista_resultados;
    private EditText et_buscar_playlist;
    private ImageButton imbn_buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista_resultados = findViewById(R.id.lista_resultados);
        et_buscar_playlist = findViewById(R.id.et_buscar_playlist);
        imbn_buscar = findViewById(R.id.imbn_buscar);

        adapterPlaylist = new AdapterPlaylist();
        adapterPlaylist.setListener(this);
        lista_resultados.setLayoutManager(new LinearLayoutManager(this));
        lista_resultados.setAdapter(adapterPlaylist);

        deezerConnect = DeezerConnect.forApp(APP_ID).withContext(this).build();

        imbn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterPlaylist.clear();
                RequestListener listener = new JsonRequestListener() {
                    @Override
                    public void onResult(Object o, Object o1) {
                        List<Playlist> ListPlay = (List<Playlist>) o;
                        adapterPlaylist.setData(new ArrayList<Playlist>(ListPlay));
                    }

                    @Override
                    public void onUnparsedResult(String s, Object o) {

                    }

                    @Override
                    public void onException(Exception e, Object o) {

                    }
                };

                DeezerRequest request = null;

                if (et_buscar_playlist!=null && !et_buscar_playlist.getText().toString().equals("")) {
                    request = DeezerRequestFactory.requestSearchPlaylists(et_buscar_playlist.getText().toString());
                    request.setId("Playlist request");
                    deezerConnect.requestAsync(request, listener);
                } else {
                    Toast.makeText(getApplicationContext(), "Debe ingresar algo para realiar la búsqueda", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onItemClick(Playlist playlist) {
        Intent i = new Intent(MainActivity.this, PlaylistActivity.class);
        i.putExtra("id_playlist", playlist.getId());
        Log.e(">>>",playlist.getId()+"");
        startActivity(i);
    }
}
