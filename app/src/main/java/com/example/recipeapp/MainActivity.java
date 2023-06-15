package com.example.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.widget.Toast;

import com.example.recipeapp.Adapters.RandomRecipeAdapter;
import com.example.recipeapp.Listners.RandomRecipeResponseListner;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading...");

        manager =new RequestManager(this);
        manager.getRandomRecipes(randomRecipeResponseListner);
        dialog.show();
    }

    private final RandomRecipeResponseListner randomRecipeResponseListner=new RandomRecipeResponseListner() {
        @Override
        public void didFetch(RandomRecipeResponseListner response, String message)
        {
            dialog.dismiss();
            recyclerView =findViewById(R.id.recycler_random);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
            randomRecipeAdapter =new RandomRecipeAdapter(MainActivity.this,response.didFetch(recyclerView););
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this,"message",Toast.LENGTH_SHORT).show();
        }
    };
}