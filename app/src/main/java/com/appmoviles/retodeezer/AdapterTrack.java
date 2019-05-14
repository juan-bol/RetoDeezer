package com.appmoviles.retodeezer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.deezer.sdk.model.Track;

import java.util.ArrayList;

public class AdapterTrack extends RecyclerView.Adapter<AdapterTrack.CustomViewHolder> {

    ArrayList<Track> data;

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
    public void onBindViewHolder(@NonNull AdapterTrack.CustomViewHolder customViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }




}
