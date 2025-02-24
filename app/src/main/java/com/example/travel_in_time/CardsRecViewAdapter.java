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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CardsRecViewAdapter extends RecyclerView.Adapter<CardsRecViewAdapter.ViewHolder>{

    private List<WikiDataModel> wikiContents = new ArrayList<>();

    private Context context;

    public CardsRecViewAdapter(Context context){
        this.context = context;
//        this.wikiContents = wikiDataList;
    }


    @NonNull
    @Override
    public CardsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsRecViewAdapter.ViewHolder holder, int position) {

        WikiDataModel.Page page = wikiContents.get(position).getPages().get(0);

        //holidays does not have a year
        if(wikiContents.get(position).getYear() != null){
            holder.year.setText(wikiContents.get(position).getYear());
        }else{
            holder.year.setVisibility(View.GONE);
        }

        holder.title.setText(wikiContents.get(position).getText());
        holder.description.setText(wikiContents.get(position).getPages().get(0).getExtract());

        // custom width and height for the image from wiki
        if(page.getThumbnail() != null){
            ViewGroup.LayoutParams layoutParams = holder.image.getLayoutParams();
            layoutParams.width = wikiContents.get(position).getPages().get(0).getThumbnail().getWidth();
            layoutParams.height = wikiContents.get(position).getPages().get(0).getThumbnail().getHeight();

            Glide.with(holder.itemView.getContext())
                    .asBitmap()
                    .load(wikiContents.get(position).getPages().get(0).getThumbnail().getSource())
                    .into(holder.image);
        }else{
            holder.image.setImageResource(R.drawable.ic_launcher_background); // Set a placeholder image
        }

        holder.itemView.setOnClickListener(v -> {
            if(holder.description.getVisibility() == View.GONE){
                holder.description.setVisibility(View.VISIBLE);
            }else{
                holder.description.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wikiContents.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWikiContents(List<WikiDataModel> contacts) {
        this.wikiContents = contacts;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView year;
        private TextView title;

        private TextView description;

        private ImageView image;

        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            // not inside an activity
            super(itemView);
            year = itemView.findViewById(R.id.year);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
