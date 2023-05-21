package com.example.examenappmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenappmobile.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StoriesRecyclerViewAdapter.IStoryListener {

    public StoriesRecyclerViewAdapter storiesAdapter;
    public StoriesRecyclerViewAdapter favStoriesAdapter ;
    TextView headerTV;
    ImageButton SearchBtn;
    EditText SearchEntry;
    public boolean IsFavSelected = false;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public  ArrayList<Story> AllStories = new ArrayList<Story>();
    public  ArrayList<Story> FavoriteStories = new ArrayList<Story>();

    public static int[] StoriesImages = {R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5,R.drawable.i6,R.drawable.i7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            String json = loadJSONFromAsset();
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Story>>(){}.getType();
            AllStories = gson.fromJson(json,listType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        headerTV = findViewById(R.id.HeaderTV);
        headerTV.setText("Toutes les histoires ("+AllStories.size()+")");
        SearchBtn = findViewById(R.id.SearchBtn);
        SearchEntry = findViewById(R.id.SearchEntry);
        RecyclerView recyclerView = findViewById(R.id.StoriesRecycleView);
        storiesAdapter = new StoriesRecyclerViewAdapter(this, AllStories, this);
        recyclerView.setAdapter(storiesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.IsFavSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            IsFavSelected = isChecked;
            SearchEntry.setText("");
            if(IsFavSelected)
            {
                headerTV.setText("Favorites ("+FavoriteStories.size()+")");
                storiesAdapter.Stories = FavoriteStories;
            }
            else
            {
                headerTV.setText("Toutes les histoires ("+AllStories.size()+")");
                storiesAdapter.Stories = AllStories;
            }
            storiesAdapter.notifyDataSetChanged();
        });

        binding.SearchEntry.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence searchKey, int start, int before,
                                      int count) {
                CharSequence finalSearchKey = searchKey.toString().toLowerCase();
                if(!searchKey.equals("") ) {
                    if(IsFavSelected){
                        ArrayList<Story> storiesToSearch = new ArrayList<>(FavoriteStories);
                        storiesToSearch.removeIf(story -> !story.getTitle().toLowerCase().contains(finalSearchKey));
                        storiesAdapter.Stories = storiesToSearch;
                    }else {
                        ArrayList<Story> storiesToSearch = new ArrayList<>(AllStories);
                        storiesToSearch.removeIf(story -> !story.getTitle().toLowerCase().contains(finalSearchKey));
                        storiesAdapter.Stories = storiesToSearch;
                    }
                }
                else {
                    if(IsFavSelected)
                        storiesAdapter.Stories = FavoriteStories;
                    else
                        storiesAdapter.Stories = AllStories;
                }
                storiesAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public String loadJSONFromAsset() throws IOException {
        String json = null;
        try {
            InputStream is = this.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void AddToFav(int id) {
        Story story = Story.GetItemById(AllStories,id);
        if(story == null)
            return;
        FavoriteStories.add(story);
        if(IsFavSelected)
            headerTV.setText("Favorites ("+FavoriteStories.size()+")");
        storiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void RemoveFromFav(int id) {
        Story story = Story.GetItemById(AllStories,id);
        if(story == null)
            return;
        int indexOfStory = FavoriteStories.indexOf(story);
        FavoriteStories.remove(indexOfStory);
        storiesAdapter.notifyDataSetChanged();
        if(IsFavSelected && storiesAdapter!=null)
        {
            headerTV.setText("Favorites ("+FavoriteStories.size()+")");
            storiesAdapter.notifyItemRemoved(indexOfStory);
        }
    }

    @Override
    public void OnClick(int position) {
        Story story = Story.GetItemById(AllStories, position);
        if(story!=null)
        {
            Gson gson = new Gson();
            String json = gson.toJson(story);
            Intent intent = new Intent(MainActivity.this, SceneActivity.class);
            intent.putExtra("story",json);
            startActivity(intent);
        }
    }
}