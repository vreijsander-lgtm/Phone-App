package com.example.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.bumptech.glide.Glide;
public class ZeldaFotoAdapter extends RecyclerView.Adapter<ZeldaFotoAdapter.ViewHolder> {

    private List<fotoObject> dataSet;

    public ZeldaFotoAdapter(List<fotoObject> dataSet) {
        this.dataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
        }
        public ImageView getTextView() {
            return imageView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zeldafototemplate, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        fotoObject currentItem = dataSet.get(position);
        Glide.with(holder.itemView.getContext())
                .load(currentItem.getImageLink())
                .into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}