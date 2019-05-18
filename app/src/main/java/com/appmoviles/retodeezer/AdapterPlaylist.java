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
import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;

import java.util.ArrayList;

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.CustomViewHolder> {

    ArrayList<Playlist> data;

    public void setData(ArrayList<Playlist> data) {
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

    public AdapterPlaylist() {
        data = new ArrayList<Playlist>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_playlist, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_nombre_lista)).setText(data.get(position).getTitle());
        ((TextView) holder.root.findViewById(R.id.tv_nombre_creador)).setText(data.get(position).getCreator().getName());
        ((TextView) holder.root.findViewById(R.id.tv_numero_items)).setText("Id: "+data.get(position).getId()+"");
        ImageView img = (ImageView) holder.root.findViewById(R.id.iv_image);
        Glide.with(holder.root.getContext()).load(data.get(position).getMediumImageUrl()).into(img);

        holder.root.findViewById(R.id.renglon_playlist).setOnClickListener(new View.OnClickListener() {
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
        void onItemClick(Playlist playlist);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }



}
