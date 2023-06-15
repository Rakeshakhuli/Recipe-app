package com.example.recipeapp;

import android.content.Context;
import android.location.GnssAntennaInfo;

import com.example.recipeapp.Listners.RandomRecipeResponseListner;
import com.example.recipeapp.Models.RandomRecipeApiResponse;
import com.google.gson.internal.LinkedTreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager
{
    Context context;
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context)
    {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListner listner){
        CallRandomRecipes callRandomRecipes=retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call=callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key),
                "10");
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if(!response.isSuccessful())
                {
                    listner.didError(response.message());
                    return;
                }
                listner.didFetch((RandomRecipeResponseListner) response.body(),response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t)
            {
                listner.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes
    {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number
        );
    }
}
