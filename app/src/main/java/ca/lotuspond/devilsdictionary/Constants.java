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

import android.provider.BaseColumns;

public interface Constants extends BaseColumns {
	public static final String DICTIONARY_TABLE_NAME = "dictionary";
	public static final String DATABASE_NAME = "dictionary.db";
	public static final String DB_PATH = "/data/data/ca.lotuspond.devilsdictionary/databases/";
	public static final String WORD = "word";
	public static final String DEFINITION = "definition";
	public static final int DATABASE_VERSION = 2;
	public static final String PREFS_FILE = "MyPrefsFile";
}
