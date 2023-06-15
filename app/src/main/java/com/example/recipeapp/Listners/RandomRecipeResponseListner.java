package com.example.recipeapp.Listners;

import androidx.recyclerview.widget.RecyclerView;

public interface RandomRecipeResponseListner
{
    void didFetch(RandomRecipeResponseListner response, String message);
    void didError(String message);

    Object didFetch(RecyclerView recyclerView);
}
