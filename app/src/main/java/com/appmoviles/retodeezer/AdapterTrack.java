package com.appmoviles.retodeezer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Track;

import java.util.ArrayList;

public class AdapterTrack extends RecyclerView.Adapter<AdapterTrack.CustomViewHolder> {

    ArrayList<Track> data;

    public void setData(ArrayList<Track> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTrack() {
        data = new ArrayList<Track>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_track, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AdapterTrack.CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_nombre_cancion)).setText(data.get(position).getTitle());
        ((TextView) holder.root.findViewById(R.id.tv_artista_cancion)).setText(data.get(position).getArtist().getName());
        ((TextView) holder.root.findViewById(R.id.tv_ano_lanzamiento)).setText("Rank: "+data.get(position).getRank()+"");
        ImageView img = (ImageView) holder.root.findViewById(R.id.iv_image_track);
        Glide.with(holder.root.getContext()).load(data.get(position).getAlbum().getImageUrl()).into(img);

        holder.root.findViewById(R.id.renglon_track).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(data.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        if (data.size() != 0 && data!= null) {
            data.clear();
        }
        notifyDataSetChanged();
    }

    //OBSERVER
    public interface OnItemClickListener{
        void onItemClick(Track track);
    }

    private AdapterTrack.OnItemClickListener listener;

    public void setListener(AdapterTrack.OnItemClickListener listener){
        this.listener = listener;
    }
}
