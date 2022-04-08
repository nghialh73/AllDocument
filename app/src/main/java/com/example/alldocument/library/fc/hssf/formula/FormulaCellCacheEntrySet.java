/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package com.example.alldocument.library.fc.hssf.formula;

import com.example.alldocument.library.fc.hssf.formula.CellCacheEntry;
import com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry;

/**
 * A custom implementation of {@link java.util.HashSet} in order to reduce memory consumption.
 *
 * Profiling tests (Oct 2008) have shown that each element {@link com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry} takes
 * around 32 bytes to store in a HashSet, but around 6 bytes to store here.  For Spreadsheets with
 * thousands of formula cells with multiple interdependencies, the savings can be very significant.
 *
 * @author Josh Micich
 */
final class FormulaCellCacheEntrySet {
	private static final com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] EMPTY_ARRAY = { };

	private int _size;
	private com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] _arr;

	public FormulaCellCacheEntrySet() {
		_arr = EMPTY_ARRAY;
	}

	public com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] toArray() {
		int nItems = _size;
		if (nItems < 1) {
			return EMPTY_ARRAY;
		}
		com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] result = new com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[nItems];
		int j=0;
		for(int i=0; i<_arr.length; i++) {
			com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry cce = _arr[i];
			if (cce != null) {
				result[j++] = cce;
			}
		}
		if (j!= nItems) {
			throw new IllegalStateException("size mismatch");
		}
		return result;
	}


	public void add(com.example.alldocument.library.fc.hssf.formula.CellCacheEntry cce) {
		if (_size * 3 >= _arr.length * 2) {
			// re-hash
			com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] prevArr = _arr;
			com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] newArr = new com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[4 + _arr.length * 3 / 2]; // grow 50%
			for(int i=0; i<prevArr.length; i++) {
				com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry prevCce = _arr[i];
				if (prevCce != null) {
					addInternal(newArr, prevCce);
				}
			}
			_arr = newArr;
		}
		if (addInternal(_arr, cce)) {
			_size++;
		}
	}


	private static boolean addInternal(com.example.alldocument.library.fc.hssf.formula.CellCacheEntry[] arr, com.example.alldocument.library.fc.hssf.formula.CellCacheEntry cce) {
		int startIx = Math.abs(cce.hashCode() % arr.length);

		for(int i=startIx; i<arr.length; i++) {
			com.example.alldocument.library.fc.hssf.formula.CellCacheEntry item = arr[i];
			if (item == cce) {
				// already present
				return false;
			}
			if (item == null) {
				arr[i] = cce;
				return true;
			}
		}
		for(int i=0; i<startIx; i++) {
			com.example.alldocument.library.fc.hssf.formula.CellCacheEntry item = arr[i];
			if (item == cce) {
				// already present
				return false;
			}
			if (item == null) {
				arr[i] = cce;
				return true;
			}
		}
		throw new IllegalStateException("No empty space found");
	}

	public boolean remove(com.example.alldocument.library.fc.hssf.formula.CellCacheEntry cce) {
		com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] arr = _arr;

		if (_size * 3 < _arr.length && _arr.length > 8) {
			// re-hash
			boolean found = false;
			com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] prevArr = _arr;
			com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[] newArr = new com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry[_arr.length / 2]; // shrink 50%
			for(int i=0; i<prevArr.length; i++) {
				com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry prevCce = _arr[i];
				if (prevCce != null) {
					if (prevCce == cce) {
						found=true;
						_size--;
						// skip it
						continue;
					}
					addInternal(newArr, prevCce);
				}
			}
			_arr = newArr;
			return found;
		}
		// else - usual case
		// delete single element (without re-hashing)

		int startIx = Math.abs(cce.hashCode() % arr.length);

		// note - can't exit loops upon finding null because of potential previous deletes
		for(int i=startIx; i<arr.length; i++) {
			com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry item = arr[i];
			if (item == cce) {
				// found it
				arr[i] = null;
				_size--;
				return true;
			}
		}
		for(int i=0; i<startIx; i++) {
			com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry item = arr[i];
			if (item == cce) {
				// found it
				arr[i] = null;
				_size--;
				return true;
			}
		}
		return false;
	}
}
