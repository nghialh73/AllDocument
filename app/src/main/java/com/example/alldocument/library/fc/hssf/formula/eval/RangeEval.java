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

package com.example.alldocument.library.fc.hssf.formula.eval;

import com.example.alldocument.library.fc.hssf.formula.eval.AreaEval;
import com.example.alldocument.library.fc.hssf.formula.eval.ErrorEval;
import com.example.alldocument.library.fc.hssf.formula.eval.EvaluationException;
import com.example.alldocument.library.fc.hssf.formula.eval.RefEval;
import com.example.alldocument.library.fc.hssf.formula.eval.ValueEval;
import com.example.alldocument.library.fc.hssf.formula.function.Fixed2ArgFunction;
import com.example.alldocument.library.fc.hssf.formula.function.Function;


/**
 *
 * @author Josh Micich
 */
public final class RangeEval extends Fixed2ArgFunction {

	public static final Function instance = new RangeEval();

	private RangeEval() {
		// enforces singleton
	}

	public ValueEval evaluate(int srcRowIndex, int srcColumnIndex, ValueEval arg0, ValueEval arg1) {

		try {
			com.example.alldocument.library.fc.hssf.formula.eval.AreaEval reA = evaluateRef(arg0);
			com.example.alldocument.library.fc.hssf.formula.eval.AreaEval reB = evaluateRef(arg1);
			return resolveRange(reA, reB);
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
	}

	/**
	 * @return simple rectangular {@link com.example.alldocument.library.fc.hssf.formula.eval.AreaEval} which fully encloses both areas
	 * <tt>aeA</tt> and <tt>aeB</tt>
	 */
	private static com.example.alldocument.library.fc.hssf.formula.eval.AreaEval resolveRange(com.example.alldocument.library.fc.hssf.formula.eval.AreaEval aeA, com.example.alldocument.library.fc.hssf.formula.eval.AreaEval aeB) {
		int aeAfr = aeA.getFirstRow();
		int aeAfc = aeA.getFirstColumn();

		int top = Math.min(aeAfr, aeB.getFirstRow());
		int bottom = Math.max(aeA.getLastRow(), aeB.getLastRow());
		int left = Math.min(aeAfc, aeB.getFirstColumn());
		int right = Math.max(aeA.getLastColumn(), aeB.getLastColumn());

		return aeA.offset(top-aeAfr, bottom-aeAfr, left-aeAfc, right-aeAfc);
	}

	private static com.example.alldocument.library.fc.hssf.formula.eval.AreaEval evaluateRef(ValueEval arg) throws EvaluationException {
		if (arg instanceof com.example.alldocument.library.fc.hssf.formula.eval.AreaEval) {
			return (AreaEval) arg;
		}
		if (arg instanceof com.example.alldocument.library.fc.hssf.formula.eval.RefEval) {
			return ((RefEval) arg).offset(0, 0, 0, 0);
		}
		if (arg instanceof com.example.alldocument.library.fc.hssf.formula.eval.ErrorEval) {
			throw new EvaluationException((ErrorEval)arg);
		}
		throw new IllegalArgumentException("Unexpected ref arg class (" + arg.getClass().getName() + ")");
	}
}
