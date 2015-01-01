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
import java.util.Random;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import static ca.lotuspond.devilsdictionary.Constants.*;

public class RandomDictionary extends Activity {
	private BrowseDictionaryHelper doh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse);
		doh = new BrowseDictionaryHelper(this);
		try {
			doh.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			Cursor cursor = getRandomWord();
			showRandomWord(cursor);
			cursor.close();
			doh.close();
		} finally {
			doh.close();
		}
	}

	private Cursor getRandomWord() {
		doh.close();
		doh.openDataBase();
		SQLiteDatabase db = doh.getReadableDatabase();

		Cursor cursor = db.query(DICTIONARY_TABLE_NAME, null, null, null, null,
				null, null);
		int numRows = cursor.getCount();
		Random rand = new Random();
		int randomRow = rand.nextInt(numRows);
		cursor.move(randomRow);
		return cursor;
	}

	private void showRandomWord(final Cursor cursor) {
		String theWord = cursor.getString(1);
		String theDefinition = cursor.getString(2);

		setContentView(R.layout.oneword);
		findViewById(R.id.oneword_root).setBackgroundColor(
				DevilsDictionary.BgColour);
		TextView wordView = (TextView) findViewById(R.id.word);
		TextView definitionView = (TextView) findViewById(R.id.definition);
		wordView.append(theWord);
		definitionView.append(theDefinition);
	}

}
