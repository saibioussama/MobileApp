package com.example.examenappmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.examenappmobile.databinding.ActivitySceneBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class SceneActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    TextToSpeech textToSpeech;
    Boolean IsTextToSpeechInitialized = false;
    TextView pagesTextView;

    private AppBarConfiguration appBarConfiguration;
    public static int[] ScenesImage = {R.drawable.s1,R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5,R.drawable.s6,R.drawable.s7,R.drawable.s8,R.drawable.s9,R.drawable.s10,R.drawable.s11,R.drawable.s12,R.drawable.s13,R.drawable.s14,R.drawable.s15,R.drawable.s16,R.drawable.s17,R.drawable.s18,R.drawable.s19,R.drawable.s20,R.drawable.s21,R.drawable.s22,R.drawable.s23,R.drawable.s24,R.drawable.s25,R.drawable.s26,R.drawable.s27,R.drawable.s28};

    private ActivitySceneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySceneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent currentIntent = getIntent();
        String storyJson = currentIntent.getStringExtra("story");
        TextView titleTextView = findViewById(R.id.DescriptionTV);
        viewPager = findViewById(R.id.MyViewPager);
        pagesTextView = findViewById(R.id.PagesTV);

        Gson gson = new Gson();
        Story story = gson.fromJson(storyJson,Story.class);
        if(story!=null)
        {
            ArrayList<Scene> scenes = story.getScenes();
            titleTextView.setText(story.getTitle());
            textToSpeech = new TextToSpeech(getBaseContext(), status -> {
                if(status == TextToSpeech.SUCCESS){
                    IsTextToSpeechInitialized = true;
                    textToSpeech.setLanguage(Locale.FRANCE);
                    textToSpeech.speak(story.Scenes.get(0).Content,TextToSpeech.QUEUE_FLUSH,null);
                }
            });

            ScenesPagerAdapter adapter = new ScenesPagerAdapter(scenes);
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position,
                                           float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    Scene scene = scenes.get(position);
                    pagesTextView.setText(position+1 + "/"+ scenes.size());
                    textToSpeech.speak(scene.Content,TextToSpeech.QUEUE_FLUSH,null);
                 }
            });

            viewPager.setAdapter(adapter);
            viewPager.setClipToPadding(false);
            viewPager.setClipChildren(false);
            viewPager.setOffscreenPageLimit(2);
            viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
            viewPager.setCurrentItem(0,true);
            pagesTextView.setText("1/"+ scenes.size());

            Button nextBtn = findViewById(R.id.NextBtn);
            nextBtn.setOnClickListener(v -> {
                int currentItemPosition = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItemPosition+1,true);
            });

            Button backBtn = findViewById(R.id.BackBtn);
            backBtn.setOnClickListener(v -> {
                int currentItemPosition = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentItemPosition-1,true);
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        textToSpeech.stop();
    }
}