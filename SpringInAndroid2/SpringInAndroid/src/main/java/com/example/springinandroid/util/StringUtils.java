/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.springinandroid.util;


import androidx.annotation.Nullable;


public abstract class StringUtils {

	private static final String[] EMPTY_STRING_ARRAY = {};

	private static final String FOLDER_SEPARATOR = "/";

	private static final char FOLDER_SEPARATOR_CHAR = '/';

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String TOP_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = '.';

	private static final int DEFAULT_TRUNCATION_THRESHOLD = 100;

	private static final String TRUNCATION_SUFFIX = " (truncated)...";


	//---------------------------------------------------------------------
	// General convenience methods for working with Strings  isBlank  isNotBlank
	//---------------------------------------------------------------------


	public static boolean isEmpty(@Nullable Object str) {
		return (str == null || "".equals(str));
	}

	public static boolean isBlank(@Nullable String str){
		return str==null||str.trim().isEmpty();
	}

	public static boolean isNotBlank(@Nullable String str){
		return !isBlank(str);
	}

	public static String replace(@Nullable String str,char oldC,char newC){
		return str.replace(oldC, newC);
	}

}
