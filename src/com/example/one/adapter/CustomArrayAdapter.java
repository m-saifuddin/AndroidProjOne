package com.example.one.adapter;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.one.R;
import com.example.one.R.id;
import com.example.one.R.layout;
import com.example.one.model.FlikrFeed;

public class CustomArrayAdapter extends ArrayAdapter<FlikrFeed> {

	private final LayoutInflater mInflater;
	private static final int IO_BUFFER_SIZE = 4 * 1024;
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String DATE_FORMAT = "EEE, MMM d, yyyy";

	public CustomArrayAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_2);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<FlikrFeed> data) {
		clear();
		if (data != null) {
			for (FlikrFeed appEntry : data) {
				add(appEntry);
			}
		}
	}

	/**
	 * Populate new items in the list.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.single_item, parent, false);
		} else {
			view = convertView;
		}

		FlikrFeed item = getItem(position);
		((TextView) view.findViewById(R.id.title)).setText(item.title);
		((TextView) view.findViewById(R.id.author)).setText(item.author);

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		Date publishedDate = null;
		try {
			publishedDate = sdf.parse(item.published);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String pubDate = new SimpleDateFormat(DATE_FORMAT)
				.format(publishedDate);

		((TextView) view.findViewById(R.id.published)).setText(pubDate);

		((ImageView) view.findViewById(R.id.list_image))
				.setImageDrawable(LoadImageFromWebOperations(item.media));
		return view;
	}

	private Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			System.out.println("Exc=" + e);
			return null;
		}
	}

}