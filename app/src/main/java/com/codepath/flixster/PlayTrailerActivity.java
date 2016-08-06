package com.codepath.flixster;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PlayTrailerActivity extends YouTubeBaseActivity {

  private AsyncHttpClient client;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_trailer);

    client = new AsyncHttpClient();

    YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
    String movieId = getIntent().getStringExtra("id");
    //int videoId = fetchVideoId(movieId);

    youTubePlayerView.initialize(API_KEY,
            new YouTubePlayer.OnInitializedListener() {
              @Override
              public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                  YouTubePlayer youTubePlayer,
                                                  boolean b) {
                // TODO fetch trailer
                String videoId = "5xVh-7ywKpE";
                youTubePlayer.cueVideo(videoId);
              }

              @Override
              public void onInitializationFailure(
                      YouTubePlayer.Provider provider,
                      YouTubeInitializationResult youTubeInitializationResult) {

              }
            }
    );
  }

  private void fetchVideoId(String movieId) {
    int videoId;

    String url = String.format("https://api.themoviedb.org/3/movie/%s/trailers", movieId);
    client.get(url, new JsonHttpResponseHandler() {
      JSONArray trailerJsonResults = null;
      String videoId;

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);

        try {
          trailerJsonResults = response.getJSONArray("youtube");
          //videoId = trailerJsonResults.get(0).getSource()
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });

    // TODO this is async, should be void
  }
}
