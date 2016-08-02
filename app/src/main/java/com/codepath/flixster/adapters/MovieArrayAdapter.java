package com.codepath.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flixster.R;
import com.codepath.flixster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by laura_kelly on 8/1/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
  // View lookup cache
  private static class ViewHolder {
    TextView tvTitle;
    TextView tvOverview;
    ImageView ivImage;
  }

  public MovieArrayAdapter(Context context, List<Movie> movies) {
    super(context, android.R.layout.simple_list_item_1, movies);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // get the data item for position
    Movie movie = getItem(position);
    ViewHolder viewHolder; // view lookup cache stored in tag

    // check if the existing views being re-used
    if (convertView == null) {
      viewHolder = new ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(getContext());
      convertView = inflater.inflate(R.layout.item_movie, parent, false);

      // Cache  the movie data
      viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
      viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
      viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
      convertView.setTag(viewHolder);
    } else {
      // recycle the view if possible
      viewHolder = (ViewHolder) convertView.getTag();
    }

    // find the image view
    ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
    // clear out image from last time
    ivImage.setImageResource(0);


    // populate data
    viewHolder.tvTitle.setText(movie.getOriginalTitle());
    viewHolder.tvOverview.setText(movie.getOverview());

    int orientation = getContext().getResources().getConfiguration().orientation;
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
      Picasso.with(getContext())
              .load(movie.getPosterPath())
              .placeholder(R.drawable.ic_movie_black_72dp)
              .into(viewHolder.ivImage);
    } else {
      Picasso.with(getContext())
              .load(movie.getBackdropPath())
              .placeholder(R.drawable.ic_movie_black_72dp)
              .into(viewHolder.ivImage);
    }

    // return the view
    return convertView;
  }
}
