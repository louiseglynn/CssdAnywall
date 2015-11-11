package com.ucd.cssd.anywall;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.Arrays;

/*
 * The FavoritePhotoAdapter is an extension of ParseQueryAdapter
 * that has a custom layout for favorite Photos, including a 
 * bigger preview image, the Photo's rating, and a "favorite"
 * star. 
 */

public class FavoritePhotoAdapter extends ParseQueryAdapter<Photo> {

	public FavoritePhotoAdapter(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Photo>() {
			public ParseQuery<Photo> create() {
				// Here we can configure a ParseQuery to display
				// only top-rated Photos.
				ParseQuery query = new ParseQuery("Photo");
				//query.whereContainedIn("rating", Arrays.asList("5", "4"));
				//query.orderByDescending("rating");
				return query;
			}
		});
	}

	@Override
	public View getItemView(Photo Photo, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.item_list_favorites, null);
		}

		super.getItemView(Photo, v, parent);

		ParseImageView PhotoImage = (ParseImageView) v.findViewById(R.id.icon);
		ParseFile photoFile = Photo.getParseFile("photo");
		if (photoFile != null) {
			PhotoImage.setParseFile(photoFile);
			PhotoImage.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					// nothing to do
				}
			});
		}

		TextView titleTextView = (TextView) v.findViewById(R.id.text1);
		titleTextView.setText(Photo.getText());
		//TextView ratingTextView = (TextView) v.findViewById(R.id.favorite_Photo_rating);
		//ratingTextView.setText(Photo.getRating());
		return v;
	}

}
