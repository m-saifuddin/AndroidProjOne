package com.example.one.model;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class FlikrFeed implements Parcelable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String title;
	public String link;
	public String media;
	public String date_taken;
	public String description;
	public String published;
	public String author;
	public String author_id;
	public String tags;
	public String mediaPath;

	public FlikrFeed(JSONObject obj) {
		try {
			if (!obj.isNull("title")) {
				this.title = obj.getString("title");
			} else {
				this.title = "";
			}

			if (!obj.isNull("link")) {
				this.link = obj.getString("link");
			} else {
				this.link = "";
			}

			if (!obj.isNull("media")) {

				String data = obj.getString("media");

				JSONObject mediaObj = new JSONObject(data);

				if (!mediaObj.isNull("m")) {

					this.media = mediaObj.getString("m");
				} else {
					this.media = "";
				}

			}

			if (!obj.isNull("date_taken")) {
				this.date_taken = obj.getString("date_taken");
			} else {
				this.date_taken = "";
			}

			if (!obj.isNull("description")) {
				this.description = obj.getString("description");
			} else {
				this.description = "";
			}

			if (!obj.isNull("published")) {
				this.published = obj.getString("published");
			} else {
				this.published = "";
			}

			if (!obj.isNull("author")) {
				this.author = obj.getString("author");
			} else {
				this.author = "";
			}

			if (!obj.isNull("author_id")) {
				this.author_id = obj.getString("author_id");
			} else {
				this.author_id = "";
			}

			if (!obj.isNull("tags")) {
				this.tags = obj.getString("tags");
			} else {
				this.tags = "";
			}

			this.mediaPath = "";

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		// TODO Auto-generated method stub

		dest.writeString(title);
		dest.writeString(link);
		dest.writeString(media);
		dest.writeString(date_taken);
		dest.writeString(description);
		dest.writeString(published);
		dest.writeString(author);
		dest.writeString(author_id);
		dest.writeString(tags);
		dest.writeString(mediaPath);

	}

	public static final Parcelable.Creator<FlikrFeed> CREATOR = new Parcelable.Creator<FlikrFeed>() {

		public FlikrFeed createFromParcel(Parcel in) {
			FlikrFeed complaint = new FlikrFeed(in);

			return complaint;
		}

		@Override
		public FlikrFeed[] newArray(int size) {
			return new FlikrFeed[size];
		}
	};

	/*
	 * Reconstruct from the Parcel
	 */
	public FlikrFeed(Parcel source) {

		title = source.readString();
		link = source.readString();
		media = source.readString();
		date_taken = source.readString();
		description = source.readString();
		published = source.readString();
		tags = source.readString();
		author = source.readString();
		author_id = source.readString();
		mediaPath = source.readString();

	}
}
