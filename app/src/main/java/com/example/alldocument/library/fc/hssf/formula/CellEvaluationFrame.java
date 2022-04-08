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

import java.util.HashSet;
import java.util.Set;

import com.example.alldocument.library.fc.hssf.formula.CellCacheEntry;
import com.example.alldocument.library.fc.hssf.formula.FormulaCellCacheEntry;
import com.example.alldocument.library.fc.hssf.formula.FormulaUsedBlankCellSet;
import com.example.alldocument.library.fc.hssf.formula.eval.ValueEval;


/**
 * Stores details about the current evaluation of a cell.<br/>
 */
final class CellEvaluationFrame {

	private final FormulaCellCacheEntry _cce;
	private final Set<com.example.alldocument.library.fc.hssf.formula.CellCacheEntry> _sensitiveInputCells;
	private FormulaUsedBlankCellSet _usedBlankCellGroup;

	public CellEvaluationFrame(FormulaCellCacheEntry cce) {
		_cce = cce;
		_sensitiveInputCells = new HashSet<com.example.alldocument.library.fc.hssf.formula.CellCacheEntry>();
	}
	public com.example.alldocument.library.fc.hssf.formula.CellCacheEntry getCCE() {
		return _cce;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(64);
		sb.append(getClass().getName()).append(" [");
		sb.append("]");
		return sb.toString();
	}
	/**
	 * @param inputCell a cell directly used by the formula of this evaluation frame
	 */
	public void addSensitiveInputCell(com.example.alldocument.library.fc.hssf.formula.CellCacheEntry inputCell) {
		_sensitiveInputCells.add(inputCell);
	}
	/**
	 * @return never <code>null</code>, (possibly empty) array of all cells directly used while
	 * evaluating the formula of this frame.
	 */
	private com.example.alldocument.library.fc.hssf.formula.CellCacheEntry[] getSensitiveInputCells() {
		int nItems = _sensitiveInputCells.size();
		if (nItems < 1) {
			return com.example.alldocument.library.fc.hssf.formula.CellCacheEntry.EMPTY_ARRAY;
		}
		com.example.alldocument.library.fc.hssf.formula.CellCacheEntry[] result = new com.example.alldocument.library.fc.hssf.formula.CellCacheEntry[nItems];
		_sensitiveInputCells.toArray(result);
		return result;
	}
	public void addUsedBlankCell(int bookIndex, int sheetIndex, int rowIndex, int columnIndex) {
		if (_usedBlankCellGroup == null) {
			_usedBlankCellGroup = new FormulaUsedBlankCellSet();
		}
		_usedBlankCellGroup.addCell(bookIndex, sheetIndex, rowIndex, columnIndex);
	}

	public void updateFormulaResult(ValueEval result) {
		_cce.updateFormulaResult(result, getSensitiveInputCells(), _usedBlankCellGroup);
	}
}
