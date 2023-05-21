package com.example.examenappmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class StoriesRecyclerViewAdapter extends RecyclerView.Adapter<StoriesRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<Story> Stories;
    IStoryListener storyListener;

    public StoriesRecyclerViewAdapter(Context context, ArrayList<Story> Stories, IStoryListener listener){
        this.context = context;
        this.Stories = Stories;
        this.storyListener = listener;
    }

    @NonNull
    @Override
    public StoriesRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.story_row_view,parent,false);
        return new StoriesRecyclerViewAdapter.MyViewHolder(view, storyListener, Stories);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesRecyclerViewAdapter.MyViewHolder holder, int position) {
        Story story = Stories.get(position);
        holder.titleTextView.setText(story.getTitle());
        holder.descriptionTextView.setText(story.getDescription());
        holder.imageView.setImageResource(MainActivity.StoriesImages[story.Image]);

        if(story.getFavorite())
            holder.favButton.setImageResource(R.drawable.star_1);
        else
            holder.favButton.setImageResource(R.drawable.star_0);


        holder.favButton.setOnClickListener(v -> {
            Story s = Stories.get(position);
            s.IsFavorite = !s.IsFavorite;
            if(s.getFavorite())
            {
                holder.favButton.setImageResource(R.drawable.star_1);
                Snackbar.make(v, s.getTitle() + " est ajoutée à la liste de favoris.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                storyListener.AddToFav(s.getId());
            }
            else
            {
                holder.favButton.setImageResource(R.drawable.star_0);
                Snackbar.make(v, s.getTitle() + " est retirée de la liste de favoris", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                storyListener.RemoveFromFav(s.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return Stories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView,descriptionTextView;
        ImageButton favButton;

        public MyViewHolder(@NonNull View itemView, IStoryListener storyListener, ArrayList<Story> stories) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.TitleTV);
            descriptionTextView = itemView.findViewById(R.id.DescriptionTV);
            favButton = itemView.findViewById(R.id.FavButton);

            itemView.setOnClickListener(v -> {
                if(storyListener != null)
                {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        Story story = Stories.get(position);
                        storyListener.OnClick(story.Id);
                    }
                }
            });
        }
    }

    public interface IStoryListener{
        void AddToFav(int id);
        void RemoveFromFav(int id);
        void OnClick(int position);
    }
}
