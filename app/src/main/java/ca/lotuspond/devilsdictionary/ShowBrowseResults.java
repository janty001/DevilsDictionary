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
import android.os.Bundle;
import android.widget.TextView;

public class ShowBrowseResults extends Activity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.oneword);
		findViewById(R.id.oneword_root).setBackgroundColor(
				DevilsDictionary.BgColour);
		Bundle b = getIntent().getExtras();
		String theWord = b.getString("WORD");
		String theDefinition = b.getString("DEFINITION");
		TextView wordView = (TextView) findViewById(R.id.word);
		TextView definitionView = (TextView) findViewById(R.id.definition);
		wordView.append(theWord);
		definitionView.append(theDefinition);
	}
}
