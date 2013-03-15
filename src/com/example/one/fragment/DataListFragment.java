package com.example.one.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.example.one.adapter.CustomArrayAdapter;
import com.example.one.model.FlikrFeed;

public class DataListFragment extends ListFragment {

	CustomArrayAdapter mAdapter;
	ArrayList<FlikrFeed> list;

	public DataListFragment() {

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		System.out.println("DataListFragment onResume called ");
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("DataListFragment onDestroy called ");
		super.onDestroy();
	}

	public DataListFragment(ArrayList<FlikrFeed> flickrFeedsList) {
		// TODO Auto-generated constructor stub
		this.list = flickrFeedsList;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		System.out.println("DataListFragment onCreate called ");

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new CustomArrayAdapter(getActivity());
		mAdapter.setData(list);
		setListAdapter(mAdapter);

	}

}