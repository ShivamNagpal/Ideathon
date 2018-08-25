package com.example.ideathon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {
    private ArrayList<Ads> adsList;
    private ItemClickHandler itemClickHandler;

    public AdsAdapter(ArrayList<Ads> adsList) {
        this.adsList = adsList;
        notifyDataSetChanged();
    }

    public void add(Ads ad) {
        adsList.add(ad);
    }

    @NonNull
    @Override
    public AdsAdapter.AdsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ad_layout, viewGroup, false);
        return new AdsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdsAdapter.AdsViewHolder viewHolder, int i) {
        Ads ad = adsList.get(i);
        viewHolder.name.setText(ad.getCompany());
        viewHolder.detail.setText(ad.getDetail());
        Glide.with(viewHolder.image.getContext())
                .load(ad.getPhotoUrl())
                .into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        if (adsList == null) {
            return 0;
        } else {
            return adsList.size();
        }
    }

    public void setItemClickHandler(ItemClickHandler itemClickHandler) {
        this.itemClickHandler = itemClickHandler;
    }

    public interface ItemClickHandler {
        void onAdClick(Ads ad);
    }

    class AdsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView name;
        TextView detail;

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.logo_image_view);
            name = itemView.findViewById(R.id.company_text_view);
            detail = itemView.findViewById(R.id.detail_text_view);
        }

        @Override
        public void onClick(View v) {
            if (itemClickHandler != null) {
                itemClickHandler.onAdClick(adsList.get(getAdapterPosition()));
            }
        }
    }
}
