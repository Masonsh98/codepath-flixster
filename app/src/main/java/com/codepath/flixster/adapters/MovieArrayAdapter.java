package com.codepath.flixster.adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.codepath.flixster.R;
import com.codepath.flixster.databinding.ItemMovieBinding;
import com.codepath.flixster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by laura_kelly on 8/1/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
  public MovieArrayAdapter(Context context, List<Movie> movies) {
    super(context, android.R.layout.simple_list_item_1, movies);
  }

  @BindingAdapter({"app:imageUrl"})
  public static void loadImage(ImageView view, String url) {
    Picasso.with(view.getContext())
            .load(url)
            .placeholder(R.drawable.ic_movie_black_72dp)
            .into(view);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ItemMovieBinding binding;
    // check if the existing views being re-used
    if (convertView == null) {
      LayoutInflater inflater = LayoutInflater.from(getContext());
      binding = ItemMovieBinding.inflate(inflater, parent, false);
      convertView = binding.getRoot();
    } else {
      //recycle the view if possible
      binding = (ItemMovieBinding) convertView.getTag();
    }

    binding.setMovie(this.getItem(position));
    convertView.setTag(binding);
    return convertView;
  }
}
