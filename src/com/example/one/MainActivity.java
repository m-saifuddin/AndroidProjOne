package com.example.one;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.example.one.fragment.DataListFragment;
import com.example.one.model.AppConstants;
import com.example.one.model.FlikrFeed;
import com.example.one.service.DownloadService;

public class MainActivity extends FragmentActivity {

	AppBroadCastReceiver receiver;
	private static DataListFragment list;
	private Button loadFlickrBtn;
	// Adeel Ansari Flickr PhotoStream
	public static String flickrPath = "http://api.flickr.com/services/feeds/photos_public.gne?id=47906772@N05&lang=en-us&format=json";

	/**
	 * onCreate
	 * 
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		System.out.println("MainActivity onCreate called ");
	}

	public void onClick(View view) {

		Intent msgIntent = new Intent(this, DownloadService.class);
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.REQUEST_TYPE, "FlickrFeeds");
		bundle.putString("urlpath", flickrPath);
		msgIntent.putExtra(AppConstants.FLICKR_BUNDLE, bundle);
		startService(msgIntent);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter(
				AppBroadCastReceiver.PROCESS_RESPONSE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		receiver = new AppBroadCastReceiver();
		registerReceiver(receiver, filter);

		System.out.println("MainActivity onResume called ");
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("MainActivity onDestroy Called");
		unregisterReceiver(receiver);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		System.out.println("MainActivity onStart called ");
		super.onStart();
	}

	public class AppBroadCastReceiver extends BroadcastReceiver {

		public static final String PROCESS_RESPONSE = Intent.ACTION_VIEW;

		@Override
		public void onReceive(Context context, Intent intent) {
			String response = null;
			Bundle receiveBundle = intent
					.getBundleExtra(AppConstants.FLICKR_BUNDLE);
			String responseType = receiveBundle
					.getString(AppConstants.RESPONSE_TYPE);
			System.out.println("onReceive " + responseType);
			if (responseType.equals("FlickrFeeds")) {

				/* Receiving Feed from url */
				response = receiveBundle.getString(AppConstants.RESPONSE_DATA);
				response = fixJsonResult(response);
				ArrayList<FlikrFeed> flickrFeedsList = parepareList(response);

				FragmentManager fm = getSupportFragmentManager();

				// Create the list fragment and add it as our sole content.

				list = new DataListFragment(flickrFeedsList);
				fm.beginTransaction().add(R.id.fragment_container, list)
						.commit();

			}

		}

		public String fixJsonResult(String json) {

			json = json.replace("jsonFlickrFeed(", "");
			json = json.replace("})", "}");

			return json;
		} // fixJsonResult

		private ArrayList<FlikrFeed> parepareList(String response) {

			ArrayList<FlikrFeed> flickrFeedsList = new ArrayList<FlikrFeed>();

			try {

				if (response != null && response != "") {
					JSONObject jsonObj = new JSONObject(response);

					if (jsonObj.getJSONArray("items") != null) {
						JSONArray jobPosts = jsonObj.getJSONArray("items");

						for (int i = 0; i < jobPosts.length(); i++) {

							flickrFeedsList.add(new FlikrFeed(jobPosts
									.getJSONObject(i)));
						}

					}

				}

			} catch (JSONException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			return flickrFeedsList;
		} // parepareList

	} // AppBroadCastReceiver

}
