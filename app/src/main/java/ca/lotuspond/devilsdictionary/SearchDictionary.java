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

import static ca.lotuspond.devilsdictionary.Constants.*;
import java.io.IOException;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchDictionary extends Activity implements OnKeyListener {
	private Editable theWord;
	private BrowseDictionaryHelper doh;
	private EditText edittext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("inside onCreate ... ");
		setContentView(R.layout.search);
		findViewById(R.id.search).setBackgroundColor(DevilsDictionary.BgColour);
		edittext = (EditText) findViewById(R.id.editText1);
		edittext.setBackgroundColor(DevilsDictionary.BgColour);
		doh = new BrowseDictionaryHelper(this);
		try {
			doh.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}
		edittext.setOnKeyListener(this);
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// If the event is a key-down event on the "enter" button
		if ((event.getAction() == KeyEvent.ACTION_DOWN)
				&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
			// Perform action on key press
			theWord = edittext.getText();
			String theString = theWord.toString();
			theString = theString.toUpperCase();
			SQLiteDatabase db = doh.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + "*" + " FROM "
					+ DICTIONARY_TABLE_NAME + " WHERE " + WORD + " = " + "'"
					+ theString + "'", null);
			if (cursor.getCount() <= 0) {

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.toast_layout,
						(ViewGroup) findViewById(R.id.toast_layout_root));

				TextView text = (TextView) layout.findViewById(R.id.text);
				text.setBackgroundColor(DevilsDictionary.BgColour);
				text.setText(edittext.getText() + " Not Found!");

				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();
				cursor.close();
				doh.close();
			} else {
				cursor.moveToFirst();
				String theWord = cursor.getString(1);
				String theDefinition = cursor.getString(2);
				setContentView(R.layout.oneword);
				findViewById(R.id.oneword_root).setBackgroundColor(
						DevilsDictionary.BgColour);
				TextView wordView = (TextView) findViewById(R.id.word);
				TextView definitionView = (TextView) findViewById(R.id.definition);
				wordView.append(theWord);
				definitionView.append(theDefinition);
				cursor.close();
				doh.close();
			}
			return true;
		}
		return false;
	}

}
