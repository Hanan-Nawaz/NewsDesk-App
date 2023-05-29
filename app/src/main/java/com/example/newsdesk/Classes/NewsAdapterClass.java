package com.example.newsdesk.Classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdesk.Fragments.ViewArticleFragment;
import com.example.newsdesk.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class NewsAdapterClass extends RecyclerView.Adapter<NewsAdapterClass.ViewHolder>{

    ArrayList<NewsArticles> newsArticles;
    String Category;


    public NewsAdapterClass(ArrayList<NewsArticles> newsArticles, String Category) {
        this.newsArticles = newsArticles;
        this.Category = Category;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainviewnews, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsArticles newsArticle = newsArticles.get(position);

        if(Objects.equals(newsArticle.getCategoies(), Category)){
            holder.Title.setText(newsArticle.getName());

            URL newurl = null;
            try {
                newurl = new URL(newsArticle.getImages());
                Bitmap bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                holder.NewsLogoIV.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            holder.NewsLogoIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                    Bundle bundle = new Bundle();
                    bundle.putString("Title", newsArticle.getName());
                    bundle.putString("Body", newsArticle.getBody());
                    bundle.putString("Image", newsArticle.getImages());
                    ViewArticleFragment articleFragment = new ViewArticleFragment();
                    articleFragment.setArguments(bundle);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.Main, articleFragment).addToBackStack(null).commit();
                }
            });
        }
        else{

        }



    }

    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView NewsLogoIV;
        TextView Title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            NewsLogoIV = itemView.findViewById(R.id.Image);
            Title = itemView.findViewById(R.id.tvTitle);
        }
    }
}