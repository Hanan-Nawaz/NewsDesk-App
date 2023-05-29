package com.example.newsdesk.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdesk.Fragments.Home_Fragment;
import com.example.newsdesk.Fragments.ViewArticleFragment;
import com.example.newsdesk.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CategoryAdapterClass extends RecyclerView.Adapter <CategoryAdapterClass.ViewHolder> {

    ArrayList <Category> categories;

    public CategoryAdapterClass(ArrayList <Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.tvCategory.setText(category.getCategory());


        holder.tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();

                Home_Fragment home_fragment = new Home_Fragment();

                SharedPreferences sharedPreferences = appCompatActivity.getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Category", category.getCategory());
                editor.apply();

                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.Main, home_fragment).addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategory = itemView.findViewById(R.id.tvCategory);
        }
    }
}