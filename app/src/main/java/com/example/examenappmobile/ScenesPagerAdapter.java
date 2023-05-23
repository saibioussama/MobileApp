package com.example.examenappmobile;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScenesPagerAdapter extends RecyclerView.Adapter<ScenesPagerAdapter.ViewHolder> {

    ArrayList<Scene> Scenes;

    public ScenesPagerAdapter(ArrayList<Scene> scenes) {
        Scenes = scenes;
    }

    @NonNull
    @Override
    public ScenesPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpage_itemview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScenesPagerAdapter.ViewHolder holder, int position) {
        Scene scene = Scenes.get(position);
        holder.sceneContent.setText(scene.getContent());
        holder.sceneContent.setMovementMethod(new ScrollingMovementMethod());
        holder.sceneImage.setImageResource(SceneActivity.ScenesImage[scene.getImage()]);
//        holder.sceneTitle.setText(scene.getTitle());
    }

    @Override
    public int getItemCount() {
        return Scenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView sceneImage;
        TextView sceneContent;

//        TextView sceneTitle;
        ScrollView scrollView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sceneImage = itemView.findViewById(R.id.SceneImage);
            sceneContent = itemView.findViewById(R.id.SceneContent);
//            sceneTitle = itemView.findViewById(R.id.SceneTitleTV);

        }
    }
}
