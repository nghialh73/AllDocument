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

import com.example.alldocument.library.fc.hssf.formula.eval.BlankEval;
import com.example.alldocument.library.fc.hssf.formula.eval.EvaluationException;
import com.example.alldocument.library.fc.hssf.formula.eval.OperandResolver;
import com.example.alldocument.library.fc.hssf.formula.eval.StringEval;
import com.example.alldocument.library.fc.hssf.formula.eval.StringValueEval;
import com.example.alldocument.library.fc.hssf.formula.eval.ValueEval;
import com.example.alldocument.library.fc.hssf.formula.function.Fixed2ArgFunction;
import com.example.alldocument.library.fc.hssf.formula.function.Function;

/**
 * @author Amol S. Deshmukh &lt; amolweb at ya hoo dot com &gt;
 */
public final class ConcatEval  extends Fixed2ArgFunction {

	public static final Function instance = new ConcatEval();

	private ConcatEval() {
		// enforce singleton
	}

	public com.example.alldocument.library.fc.hssf.formula.eval.ValueEval evaluate(int srcRowIndex, int srcColumnIndex, com.example.alldocument.library.fc.hssf.formula.eval.ValueEval arg0, com.example.alldocument.library.fc.hssf.formula.eval.ValueEval arg1) {
		com.example.alldocument.library.fc.hssf.formula.eval.ValueEval ve0;
		com.example.alldocument.library.fc.hssf.formula.eval.ValueEval ve1;
		try {
			ve0 = OperandResolver.getSingleValue(arg0, srcRowIndex, srcColumnIndex);
			ve1 = OperandResolver.getSingleValue(arg1, srcRowIndex, srcColumnIndex);
		} catch (EvaluationException e) {
			return e.getErrorEval();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(getText(ve0));
		sb.append(getText(ve1));
		return new StringEval(sb.toString());
	}

	private Object getText(ValueEval ve) {
		if (ve instanceof StringValueEval) {
			StringValueEval sve = (StringValueEval) ve;
			return sve.getStringValue();
		}
		if (ve == BlankEval.instance) {
			return "";
		}
		throw new IllegalAccessError("Unexpected value type ("
					+ ve.getClass().getName() + ")");
	}
}
