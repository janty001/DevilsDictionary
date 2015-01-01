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

import java.io.IOException;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import static android.provider.BaseColumns._ID;

import static ca.lotuspond.devilsdictionary.Constants.*;

public class BrowseDictionary extends ListActivity {
	private BrowseDictionaryHelper doh;
	private static String[] FROM = { _ID, WORD, DEFINITION };
	private static int[] TO = { R.id.word };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse);

		View mlayout = findViewById(android.R.id.list);
		mlayout.setBackgroundColor(DevilsDictionary.BgColour);

		doh = new BrowseDictionaryHelper(this);
		try {
			doh.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			Cursor cursor = getWords();
			showWords(cursor);
			doh.close();
		} finally {
			doh.close();
		}
	}

	private Cursor getWords() {
		SQLiteDatabase db = doh.getReadableDatabase();
		Cursor cursor = db.query(DICTIONARY_TABLE_NAME, FROM, null, null, null,
				null, null);
		startManagingCursor(cursor);
		return cursor;
	}

	private void showWords(final Cursor cursor) {
		String[] FROM = { WORD, _ID, DEFINITION };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.item, cursor, FROM, TO);
		setListAdapter(adapter);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String theWord = cursor.getString(1);
				String theDefinition = cursor.getString(2);
				Intent i = new Intent(BrowseDictionary.this,
						ShowBrowseResults.class);
				Bundle b = new Bundle();
				b.putString("WORD", theWord);
				b.putString("DEFINITION", theDefinition);
				i.putExtras(b);
				startActivity(i);
			}

		});

	}
}
