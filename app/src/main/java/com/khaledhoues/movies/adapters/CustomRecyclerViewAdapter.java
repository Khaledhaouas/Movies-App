package com.khaledhoues.movies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khaledhoues.movies.R;
import com.khaledhoues.movies.entities.Article;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Article> mDataSet;

    public CustomRecyclerViewAdapter(ArrayList<Article> dataSet) {
        mDataSet = dataSet;
    }


    @NonNull
    @Override
    public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        viewHolder.getTxtTitle().setText(mDataSet.get(position).getTitle());
        viewHolder.getTxtDesc().setText(mDataSet.get(position).getDescription());
        viewHolder.getTxtMisc().setText(mDataSet.get(position).getDate());

        Glide.with(viewHolder.getImgThumbnail().getContext())
                .load(mDataSet.get(position).getImage())
                .into(viewHolder.getImgThumbnail());

        setAnimation(viewHolder.getCardLayout());

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setArticles(List<Article> articles) {
        mDataSet.clear();
        mDataSet.addAll(articles);
        notifyDataSetChanged();
    }

    private void setAnimation(View viewToAnimate) {
        // If the bound view wasn't previously displayed on screen, it's animated

        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);


    }


    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView mCardLayout;
        private final TextView mTxtTitle;
        private final TextView mTxtDesc;
        private final TextView mTxtMisc;
        private final ImageView mImgThumbnail;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            mCardLayout = (CardView) v.findViewById(R.id.card_view);
            mTxtTitle = (TextView) v.findViewById(R.id.txt_card_title);
            mTxtDesc = (TextView) v.findViewById(R.id.txt_card_content);
            mTxtMisc = (TextView) v.findViewById(R.id.txt_card_misc);
            mImgThumbnail = (ImageView) v.findViewById(R.id.img_card_thumbnail);
        }

        public TextView getTxtTitle() {
            return mTxtTitle;
        }

        public TextView getTxtDesc() {
            return mTxtDesc;
        }

        public TextView getTxtMisc() {
            return mTxtMisc;
        }

        public ImageView getImgThumbnail() {
            return mImgThumbnail;
        }

        public CardView getCardLayout() {
            return mCardLayout;
        }
    }
}
