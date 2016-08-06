package com.codepath.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by laura_kelly on 8/1/16.
 */
public class Movie {
  public String getBackdropPath() {
    return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
  }

  public String getPosterPath() {
    return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public String getOverview() {
    return overview;
  }

  public String getId() { return id; }

  public Double getVoteAverage() { return voteAverage; }

  String backdropPath;
  String posterPath;
  String originalTitle;
  String overview;
  String id;
  Double voteAverage;

  public Movie(JSONObject jsonObject) throws JSONException {
    this.backdropPath = jsonObject.getString("backdrop_path");
    this.posterPath = jsonObject.getString("poster_path");
    this.originalTitle = jsonObject.getString("original_title");
    this.overview = jsonObject.getString("overview");
    this.voteAverage = jsonObject.getDouble("vote_average");
    this.id = jsonObject.getString("id");
  }

  public static ArrayList<Movie> fromJSONArray(JSONArray array) {
    ArrayList<Movie> results = new ArrayList<>();

    for (int x = 0; x < array.length(); x++) {
      try {
        results.add(new Movie(array.getJSONObject(x)));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return results;
  }
}
