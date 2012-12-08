package com.words_ver_0_1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StasticsPart extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */

	public StasticsPart() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Create a new TextView and set its text to the fragment's section
		// number argument value.

		return inflater.inflate(R.layout.stasticspart, container, false);
	}
}
