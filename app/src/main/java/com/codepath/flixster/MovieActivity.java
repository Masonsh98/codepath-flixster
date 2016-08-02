package com.codepath.flixster;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.flixster.adapters.MovieArrayAdapter;
import com.codepath.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
  ArrayList<Movie> movies;
  MovieArrayAdapter movieAdapter;
  ListView lvItems;

  private SwipeRefreshLayout swipeContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie);
    swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

    final AsyncHttpClient client = new AsyncHttpClient();

    // Set up refresh listener
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        fetchMoviesAsync(client);
      }
    });

    // configure the refreshing colors
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

    lvItems = (ListView) findViewById(R.id.lvMovies);
    movies = new ArrayList<>();
    movieAdapter = new MovieArrayAdapter(this, movies);
    lvItems.setAdapter(movieAdapter);
    fetchMoviesAsync(client);
  }

  public void fetchMoviesAsync(AsyncHttpClient client) {
    String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    client.get(url, new JsonHttpResponseHandler() {
      JSONArray movieJsonResults = null;

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);

        try {
          movieJsonResults = response.getJSONArray("results");
          movieAdapter.clear();
          movieAdapter.addAll(Movie.fromJSONArray(movieJsonResults));
          swipeContainer.setRefreshing(false);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);

        Log.d("Debug", "Fetch movies error: " + throwable.toString());
      }
    });
  }
}
