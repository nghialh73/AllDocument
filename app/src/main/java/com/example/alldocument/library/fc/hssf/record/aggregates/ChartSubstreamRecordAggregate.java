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

package com.example.alldocument.library.fc.hssf.record.aggregates;

import java.util.ArrayList;
import java.util.List;

import com.example.alldocument.library.fc.hssf.model.RecordStream;
import com.example.alldocument.library.fc.hssf.record.BOFRecord;
import com.example.alldocument.library.fc.hssf.record.EOFRecord;
import com.example.alldocument.library.fc.hssf.record.HeaderFooterRecord;
import com.example.alldocument.library.fc.hssf.record.Record;
import com.example.alldocument.library.fc.hssf.record.RecordBase;
import com.example.alldocument.library.fc.hssf.record.aggregates.PageSettingsBlock;
import com.example.alldocument.library.fc.hssf.record.aggregates.RecordAggregate;


/**
 * Manages the all the records associated with a chart sub-stream.<br/>
 * Includes the initial {@link BOFRecord} and final {@link EOFRecord}.
 *
 * @author Josh Micich
 */
public final class ChartSubstreamRecordAggregate extends com.example.alldocument.library.fc.hssf.record.aggregates.RecordAggregate {

	private final BOFRecord _bofRec;
	/**
	 * All the records between BOF and EOF
	 */
	private final List<RecordBase> _recs;
	private com.example.alldocument.library.fc.hssf.record.aggregates.PageSettingsBlock _psBlock;

	public ChartSubstreamRecordAggregate(RecordStream rs) {
		_bofRec = (BOFRecord) rs.getNext();
		List<RecordBase> temp = new ArrayList<RecordBase>();
		while (rs.peekNextClass() != EOFRecord.class) {
			if (com.example.alldocument.library.fc.hssf.record.aggregates.PageSettingsBlock.isComponentRecord(rs.peekNextSid())) {
				if (_psBlock != null) {
					if (rs.peekNextSid() == HeaderFooterRecord.sid) {
						// test samples: 45538_classic_Footer.xls, 45538_classic_Header.xls
						_psBlock.addLateHeaderFooter((HeaderFooterRecord)rs.getNext());
						continue;
					}
					throw new IllegalStateException(
							"Found more than one PageSettingsBlock in chart sub-stream");
				}
				_psBlock = new PageSettingsBlock(rs);
				temp.add(_psBlock);
				continue;
			}
			temp.add(rs.getNext());
		}
		_recs = temp;
		Record eof = rs.getNext(); // no need to save EOF in field
		if (!(eof instanceof EOFRecord)) {
			throw new IllegalStateException("Bad chart EOF");
		}
	}

	public void visitContainedRecords(RecordVisitor rv) {
		if (_recs.isEmpty()) {
			return;
		}
		rv.visitRecord(_bofRec);
		for (int i = 0; i < _recs.size(); i++) {
			RecordBase rb = _recs.get(i);
			if (rb instanceof com.example.alldocument.library.fc.hssf.record.aggregates.RecordAggregate) {
				((RecordAggregate) rb).visitContainedRecords(rv);
			} else {
				rv.visitRecord((Record) rb);
			}
		}
		rv.visitRecord(EOFRecord.instance);
	}
}
