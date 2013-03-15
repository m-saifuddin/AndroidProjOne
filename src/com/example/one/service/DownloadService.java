package com.example.one.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.one.MainActivity.AppBroadCastReceiver;
import com.example.one.model.AppConstants;

public class DownloadService extends IntentService {

	private String requestType;

	public DownloadService() {
		super("DownloadService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		Bundle bundle = intent.getBundleExtra(AppConstants.FLICKR_BUNDLE);
		requestType = bundle.getString(AppConstants.REQUEST_TYPE);

		if (requestType.equals("FlickrFeeds")) {
			String urlpath = bundle.getString("urlpath");
			getFlickrFeed(urlpath);
		}

	}

	private void getFlickrFeed(String url) {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		String responseData = "";
		try {
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream instream = response.getEntity().getContent();
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						instream));
				StringBuilder total = new StringBuilder();
				String line;
				if (rd != null) {
					while ((line = rd.readLine()) != null) {
						total.append(line);
					}
				}
				// instream.close();
				responseData = total.toString();
				rd.close();
			} else {

				Log.w("HTTP1:", response.getStatusLine().getReasonPhrase());
				response.getEntity().getContent().close();
				throw new IOException(response.getStatusLine()
						.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(AppBroadCastReceiver.PROCESS_RESPONSE);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);

		/* parepare bundle for broadcase */
		Bundle finishBundle = new Bundle();
		finishBundle.putString(AppConstants.RESPONSE_TYPE, "FlickrFeeds");
		finishBundle.putString(AppConstants.RESPONSE_DATA, responseData);
		broadcastIntent.putExtra(AppConstants.FLICKR_BUNDLE, finishBundle);
		sendBroadcast(broadcastIntent);
	}

}