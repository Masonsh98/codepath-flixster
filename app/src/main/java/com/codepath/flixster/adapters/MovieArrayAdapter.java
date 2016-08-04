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
import com.codepath.flixster.databinding.ItemPopularMovieBinding;
import com.codepath.flixster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by laura_kelly on 8/1/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
  public double voteThreshold = 5;

  public MovieArrayAdapter(Context context, List<Movie> movies) {
    super(context, android.R.layout.simple_list_item_1, movies);
  }

  @BindingAdapter({"app:imageUrl"})
  public static void loadImage(ImageView view, String url) {
    Picasso.with(view.getContext())
            .load(url)
            .placeholder(R.drawable.ic_movie_black_72dp)
            .transform(new RoundedCornersTransformation(5, 5))
            .into(view);
  }

  @Override
  public int getViewTypeCount() {
    return 2;
  }

  @Override
  public int getItemViewType(int position) {
    Movie movie = getItem(position);
    if (movie.getVoteAverage() > voteThreshold) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    int viewType = getItemViewType(position);

    switch(viewType) {

      // regular movie
      case 0:
        ItemMovieBinding movieBinding;
        if (convertView == null || convertView.getTag() == null) {
          LayoutInflater inflater = LayoutInflater.from(getContext());
          movieBinding = ItemMovieBinding.inflate(inflater, parent, false);
          movieBinding.setMovie(this.getItem(position));
          convertView = movieBinding.getRoot();
        } else {
          movieBinding = (ItemMovieBinding) convertView.getTag();
          movieBinding.setMovie(this.getItem(position));
          convertView.setTag(movieBinding);
        }
        break;

      // popular movie
      case 1:
        ItemPopularMovieBinding popularMovieBinding;

        if (convertView == null || convertView.getTag() == null) {
          LayoutInflater inflater = LayoutInflater.from(getContext());
          popularMovieBinding = ItemPopularMovieBinding.inflate(inflater, parent, false);
          popularMovieBinding.setMovie(this.getItem(position));
          convertView = popularMovieBinding.getRoot();
        } else {
          popularMovieBinding = (ItemPopularMovieBinding) convertView.getTag();
          popularMovieBinding.setMovie(this.getItem(position));
          convertView.setTag(popularMovieBinding);
        }

        break;

      default:
        break;
    }

    return convertView;
  }
}
