package com.example.travel_in_time;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CardsRecViewAdapter extends RecyclerView.Adapter<CardsRecViewAdapter.ViewHolder>{

    private List<WikiDataModel> wikiContents = new ArrayList<>();

    private Context context;

    public CardsRecViewAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public CardsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsRecViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(wikiContents.get(position).getTitle());
        holder.description.setText(wikiContents.get(position).getDescription());

        // custom width and height for the image from wiki
        ViewGroup.LayoutParams layoutParams = holder.image.getLayoutParams();
        layoutParams.width = wikiContents.get(position).getImageWidth();
        layoutParams.height = wikiContents.get(position).getImageHeight();

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(wikiContents.get(position).getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return wikiContents.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setContacts(List<WikiDataModel> contacts) {
        this.wikiContents = contacts;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView description;

        private ImageView image;

        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            // not inside an activity
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
