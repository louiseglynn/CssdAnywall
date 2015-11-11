package com.ucd.cssd.anywall;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseQueryAdapter;

public class PhotoListActivity extends ListActivity {

    private ParseQueryAdapter<Photo> mainAdapter;
    private FavoritePhotoAdapter favoritesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setClickable(false);

        mainAdapter = new ParseQueryAdapter<Photo>(this, Photo.class);
        mainAdapter.setTextKey("text");
        mainAdapter.setImageKey("photo");

        // Subclass of ParseQueryAdapter
        favoritesAdapter = new FavoritePhotoAdapter(this);

        // Default view is all Photos
        setListAdapter(mainAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     * Posting Photos and refreshing the list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updatePhotoList();
                break;
            }

            case R.id.action_favorites: {
                showFavorites();
                break;
            }

            case R.id.action_new: {
                newPhoto();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updatePhotoList() {
        mainAdapter.loadObjects();
        setListAdapter(mainAdapter);
    }

    private void showFavorites() {
        favoritesAdapter.loadObjects();
        setListAdapter(favoritesAdapter);
    }

    private void newPhoto() {
        Intent i = new Intent(this, PhotoActivity.class);
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updatePhotoList();
        }
    }

}

