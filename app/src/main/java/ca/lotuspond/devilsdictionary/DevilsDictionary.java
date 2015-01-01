/**
 * DevilsDictionary Version 1.0
 * Copyright (c) 2011 John Selmys
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */
package ca.lotuspond.devilsdictionary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class DevilsDictionary extends Activity implements OnClickListener,
		OnSharedPreferenceChangeListener {

	private static final int MENU_ID = Menu.FIRST;
	public static int BgColour = 0xfffaebd7;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Set up preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		String arg1 = "Oops";
		arg1 = prefs.getString("listPref", arg1);
		if (!arg1.equals("Oops")) {
			BgColour = Integer.valueOf(arg1, 16).intValue();
			BgColour = BgColour | 0xff000000;
		}

		findViewById(R.id.main).setBackgroundColor(BgColour);

		// Set up click listeners for all the buttons
		View browseButton = findViewById(R.id.browse_button);
		browseButton.setOnClickListener(this);
		View searchButton = findViewById(R.id.search_button);
		searchButton.setOnClickListener(this);
		View randomButton = findViewById(R.id.random_button);
		randomButton.setOnClickListener(this);
		View aboutButton = findViewById(R.id.about_button);
		aboutButton.setOnClickListener(this);
		View exitButton = findViewById(R.id.exit_button);
		exitButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.browse_button:
			i = new Intent(this, BrowseDictionary.class);
			startActivity(i);
			break;
		case R.id.about_button:
			i = new Intent(this, About.class);
			startActivity(i);
			break;
		case R.id.search_button:
			i = new Intent(this, SearchDictionary.class);
			startActivity(i);
			break;
		case R.id.random_button:
			i = new Intent(this, RandomDictionary.class);
			startActivity(i);
			break;
		case R.id.exit_button:
			finish();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(0, MENU_ID, 0, "Background Colour").setIcon(
				android.R.drawable.btn_star);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ID:
			startActivity(new Intent(this, Prefs.class));
			return true;
			// More items go here 
		}
		return false;
	}

	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		String arg2 = "Oops";
		BgColour = Integer.valueOf(arg0.getString(arg1, arg2), 16).intValue();
		BgColour = BgColour | 0xff000000;
		findViewById(R.id.main).setBackgroundColor(BgColour);
	}

}
