package com.example.cocktails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder> {
    Context context;
    private ArrayList<Cocktails> cocktails;

    public CocktailAdapter(Context context, ArrayList<Cocktails> cocktails) {
        this.context = context;
        this.cocktails = cocktails;
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cocktail_item, parent, false);
        return new CocktailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, int position) {
        Cocktails currentCocktail = cocktails.get(position);

        String picture = currentCocktail.getPictureUrl();
        String title = currentCocktail.getTitle();
        String category = currentCocktail.getCategory();
        String instructions = currentCocktail.getInstructions();

        holder.titleTextView.setText(title);
        holder.categoryTextView.setText(category);
        holder.instructionsTextView.setText(instructions);

        Picasso.get().load(picture).fit().centerCrop().into(holder.pictureImageView);
    }

    @Override
    public int getItemCount() {
        return cocktails.size();
    }

    public class CocktailViewHolder extends RecyclerView.ViewHolder {
        ImageView pictureImageView;
        TextView titleTextView, categoryTextView, instructionsTextView;
        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureImageView = itemView.findViewById(R.id.pictureImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            instructionsTextView = itemView.findViewById(R.id.instructionsTextView);



        }
    }
}
