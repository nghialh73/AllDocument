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
package com.example.alldocument.library.fc.hssf.record;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.alldocument.library.fc.EncryptedDocumentException;
import com.example.alldocument.library.fc.hssf.eventusermodel.HSSFEventFactory;
import com.example.alldocument.library.fc.hssf.eventusermodel.HSSFListener;
import com.example.alldocument.library.fc.hssf.record.AbstractEscherHolderRecord;
import com.example.alldocument.library.fc.hssf.record.BOFRecord;
import com.example.alldocument.library.fc.hssf.record.ContinueRecord;
import com.example.alldocument.library.fc.hssf.record.DBCellRecord;
import com.example.alldocument.library.fc.hssf.record.DrawingGroupRecord;
import com.example.alldocument.library.fc.hssf.record.DrawingRecord;
import com.example.alldocument.library.fc.hssf.record.EOFRecord;
import com.example.alldocument.library.fc.hssf.record.FilePassRecord;
import com.example.alldocument.library.fc.hssf.record.MulRKRecord;
import com.example.alldocument.library.fc.hssf.record.ObjRecord;
import com.example.alldocument.library.fc.hssf.record.RKRecord;
import com.example.alldocument.library.fc.hssf.record.Record;
import com.example.alldocument.library.fc.hssf.record.RecordFactory;
import com.example.alldocument.library.fc.hssf.record.RecordFormatException;
import com.example.alldocument.library.fc.hssf.record.RecordInputStream;
import com.example.alldocument.library.fc.hssf.record.TextObjectRecord;
import com.example.alldocument.library.fc.hssf.record.UnknownRecord;
import com.example.alldocument.library.fc.hssf.record.crypto.Biff8EncryptionKey;



/**
 * A stream based way to get at complete records, with
 * as low a memory footprint as possible.
 * This handles reading from a RecordInputStream, turning
 * the data into full records, processing continue records
 * etc.
 * Most users should use {@link HSSFEventFactory} /
 * {@link HSSFListener} and have new records pushed to
 * them, but this does allow for a "pull" style of coding.
 */
public final class RecordFactoryInputStream {

	/**
	 * Keeps track of the sizes of the initial records up to and including {@link com.example.alldocument.library.fc.hssf.record.FilePassRecord}
	 * Needed for protected files because each byte is encrypted with respect to its absolute
	 * position from the start of the stream.
	 */
	private static final class StreamEncryptionInfo {
		private final int _initialRecordsSize;
		private final com.example.alldocument.library.fc.hssf.record.FilePassRecord _filePassRec;
		private final com.example.alldocument.library.fc.hssf.record.Record _lastRecord;
		private final boolean _hasBOFRecord;

		public StreamEncryptionInfo(com.example.alldocument.library.fc.hssf.record.RecordInputStream rs, List<com.example.alldocument.library.fc.hssf.record.Record> outputRecs) {
			com.example.alldocument.library.fc.hssf.record.Record rec;
			rs.nextRecord();
			int recSize = 4 + rs.remaining();
			rec = com.example.alldocument.library.fc.hssf.record.RecordFactory.createSingleRecord(rs);
			outputRecs.add(rec);
			com.example.alldocument.library.fc.hssf.record.FilePassRecord fpr = null;
			if (rec instanceof com.example.alldocument.library.fc.hssf.record.BOFRecord) {
				_hasBOFRecord = true;
				if (rs.hasNextRecord()) {
					rs.nextRecord();
					rec = com.example.alldocument.library.fc.hssf.record.RecordFactory.createSingleRecord(rs);
					recSize += rec.getRecordSize();
					outputRecs.add(rec);
					if (rec instanceof com.example.alldocument.library.fc.hssf.record.FilePassRecord) {
						fpr = (com.example.alldocument.library.fc.hssf.record.FilePassRecord) rec;
						outputRecs.remove(outputRecs.size()-1);
						// TODO - add fpr not added to outputRecs
						rec = outputRecs.get(0);
					} else {
						// workbook not encrypted (typical case)
						if (rec instanceof com.example.alldocument.library.fc.hssf.record.EOFRecord) {
							// A workbook stream is never empty, so crash instead
							// of trying to keep track of nesting level
							throw new IllegalStateException("Nothing between BOF and EOF");
						}
					}
				}
			} else {
				// Invalid in a normal workbook stream.
				// However, some test cases work on sub-sections of
				// the workbook stream that do not begin with BOF
				_hasBOFRecord = false;
			}
			_initialRecordsSize = recSize;
			_filePassRec = fpr;
			_lastRecord = rec;
		}

		public com.example.alldocument.library.fc.hssf.record.RecordInputStream createDecryptingStream(InputStream original) {
			FilePassRecord fpr = _filePassRec;
			String userPassword = Biff8EncryptionKey.getCurrentUserPassword();

			Biff8EncryptionKey key;
			if (userPassword == null) {
				key = Biff8EncryptionKey.create(fpr.getDocId());
			} else {
				key = Biff8EncryptionKey.create(userPassword, fpr.getDocId());
			}
			if (!key.validate(fpr.getSaltData(), fpr.getSaltHash())) {
				/*throw new EncryptedDocumentException(
						(userPassword == null ? "Default" : "Supplied")
						+ " password is invalid for docId/saltData/saltHash");*/
				throw new EncryptedDocumentException("Cannot process encrypted office files!");
			}
			return new com.example.alldocument.library.fc.hssf.record.RecordInputStream(original, key, _initialRecordsSize);
		}

		public boolean hasEncryption() {
			return _filePassRec != null;
		}

		/**
		 * @return last record scanned while looking for encryption info.
		 * This will typically be the first or second record read. Possibly <code>null</code>
		 * if stream was empty
		 */
		public com.example.alldocument.library.fc.hssf.record.Record getLastRecord() {
			return _lastRecord;
		}

		/**
		 * <code>false</code> in some test cases
		 */
		public boolean hasBOFRecord() {
			return _hasBOFRecord;
		}
	}


	private /*final*/ com.example.alldocument.library.fc.hssf.record.RecordInputStream _recStream;
	private final boolean _shouldIncludeContinueRecords;

	/**
	 * Temporarily stores a group of {@link com.example.alldocument.library.fc.hssf.record.Record}s, for future return by {@link #nextRecord()}.
	 * This is used at the start of the workbook stream, and also when the most recently read
	 * underlying record is a {@link com.example.alldocument.library.fc.hssf.record.MulRKRecord}
	 */
	private com.example.alldocument.library.fc.hssf.record.Record[] _unreadRecordBuffer;

	/**
	 * used to help iterating over the unread records
	 */
	private int _unreadRecordIndex = -1;

	/**
	 * The most recent record that we gave to the user
	 */
	private com.example.alldocument.library.fc.hssf.record.Record _lastRecord = null;
	/**
	 * The most recent DrawingRecord seen
	 */
	private com.example.alldocument.library.fc.hssf.record.DrawingRecord _lastDrawingRecord = new com.example.alldocument.library.fc.hssf.record.DrawingRecord();

	private int _bofDepth;

	private boolean _lastRecordWasEOFLevelZero;


	/**
	 * @param shouldIncludeContinueRecords caller can pass <code>false</code> if loose
	 * {@link com.example.alldocument.library.fc.hssf.record.ContinueRecord}s should be skipped (this is sometimes useful in event based
	 * processing).
	 */
	public RecordFactoryInputStream(InputStream in, boolean shouldIncludeContinueRecords) {
		com.example.alldocument.library.fc.hssf.record.RecordInputStream rs = new RecordInputStream(in);
		List<com.example.alldocument.library.fc.hssf.record.Record> records = new ArrayList<com.example.alldocument.library.fc.hssf.record.Record>();
		StreamEncryptionInfo sei = new StreamEncryptionInfo(rs, records);
		if (sei.hasEncryption()) {
			rs = sei.createDecryptingStream(in);
		} else {
			// typical case - non-encrypted stream
		}

		if (!records.isEmpty()) {
			_unreadRecordBuffer = new com.example.alldocument.library.fc.hssf.record.Record[records.size()];
			records.toArray(_unreadRecordBuffer);
			_unreadRecordIndex =0;
		}
		_recStream = rs;
		_shouldIncludeContinueRecords = shouldIncludeContinueRecords;
		_lastRecord = sei.getLastRecord();

		/*
		* How to recognise end of stream?
		* In the best case, the underlying input stream (in) ends just after the last EOF record
		* Usually however, the stream is padded with an arbitrary byte count.  Excel and most apps
		* reliably use zeros for padding and if this were always the case, this code could just
		* skip all the (zero sized) records with sid==0.  However, bug 46987 shows a file with
		* non-zero padding that is read OK by Excel (Excel also fixes the padding).
		*
		* So to properly detect the workbook end of stream, this code has to identify the last
		* EOF record.  This is not so easy because the worbook bof+eof pair do not bracket the
		* whole stream.  The worksheets follow the workbook, but it is not easy to tell how many
		* sheet sub-streams should be present.  Hence we are looking for an EOF record that is not
		* immediately followed by a BOF record.  One extra complication is that bof+eof sub-
		* streams can be nested within worksheet streams and it's not clear in these cases what
		* record might follow any EOF record.  So we also need to keep track of the bof/eof
		* nesting level.
		*/
		_bofDepth = sei.hasBOFRecord() ? 1 : 0;
		_lastRecordWasEOFLevelZero = false;
	}

	/**
	 * Returns the next (complete) record from the
	 * stream, or null if there are no more.
	 */
	public com.example.alldocument.library.fc.hssf.record.Record nextRecord() {
		com.example.alldocument.library.fc.hssf.record.Record r;
		r = getNextUnreadRecord();
		if (r != null) {
			// found an unread record
			return r;
		}
		while (true) {
			if (!_recStream.hasNextRecord()) {
				// recStream is exhausted;
				return null;
			}

			if (_lastRecordWasEOFLevelZero) {
				// Potential place for ending the workbook stream
				// Check that the next record is not BOFRecord(0x0809)
				// Normally the input stream contains only zero padding after the last EOFRecord,
				// but bug 46987 and 48068 suggests that the padding may be garbage.
				// This code relies on the padding bytes not starting with BOFRecord.sid
				if (_recStream.getNextSid() != com.example.alldocument.library.fc.hssf.record.BOFRecord.sid) {
					return null;
				}
				// else - another sheet substream starting here
			}

            // step underlying RecordInputStream to the next record
            _recStream.nextRecord();

			r = readNextRecord();
			if (r == null) {
				// some record types may get skipped (e.g. DBCellRecord and ContinueRecord)
				continue;
			}
			return r;
		}
	}

	/**
	 * @return the next {@link com.example.alldocument.library.fc.hssf.record.Record} from the multiple record group as expanded from
	 * a recently read {@link com.example.alldocument.library.fc.hssf.record.MulRKRecord}. <code>null</code> if not present.
	 */
	private com.example.alldocument.library.fc.hssf.record.Record getNextUnreadRecord() {
		if (_unreadRecordBuffer != null) {
			int ix = _unreadRecordIndex;
			if (ix < _unreadRecordBuffer.length) {
				com.example.alldocument.library.fc.hssf.record.Record result = _unreadRecordBuffer[ix];
				_unreadRecordIndex = ix + 1;
				return result;
			}
			_unreadRecordIndex = -1;
			_unreadRecordBuffer = null;
		}
		return null;
	}

	/**
	 * @return the next available record, or <code>null</code> if
	 * this pass didn't return a record that's
	 * suitable for returning (eg was a continue record).
	 */
	private com.example.alldocument.library.fc.hssf.record.Record readNextRecord() {

		com.example.alldocument.library.fc.hssf.record.Record record = com.example.alldocument.library.fc.hssf.record.RecordFactory.createSingleRecord(_recStream);
		_lastRecordWasEOFLevelZero = false;

		//???????????????DrawingRecord/ContinueRecord???ObjRecord????????????
		if(_lastDrawingRecord != null 
				&& record.getSid() != com.example.alldocument.library.fc.hssf.record.ContinueRecord.sid
				&& record.getSid() != com.example.alldocument.library.fc.hssf.record.ObjRecord.sid
				&& record.getSid() != com.example.alldocument.library.fc.hssf.record.TextObjectRecord.sid)
		{
			_lastDrawingRecord = null;
		}
		
		if (record instanceof BOFRecord) {
			_bofDepth++;
			return record;
		}

		if (record instanceof com.example.alldocument.library.fc.hssf.record.EOFRecord) {
			_bofDepth--;
			if (_bofDepth < 1) {
				_lastRecordWasEOFLevelZero = true;
			}

			return record;
		}

		if (record instanceof DBCellRecord) {
			// Not needed by POI.  Regenerated from scratch by POI when spreadsheet is written
			return null;
		}

		if (record instanceof com.example.alldocument.library.fc.hssf.record.RKRecord) {
			return com.example.alldocument.library.fc.hssf.record.RecordFactory.convertToNumberRecord((RKRecord) record);
		}

		if (record instanceof com.example.alldocument.library.fc.hssf.record.MulRKRecord) {
			Record[] records = RecordFactory.convertRKRecords((MulRKRecord) record);

			_unreadRecordBuffer = records;
			_unreadRecordIndex = 1;
			return records[0];
		}

		if (record.getSid() == com.example.alldocument.library.fc.hssf.record.DrawingGroupRecord.sid
				&& _lastRecord instanceof com.example.alldocument.library.fc.hssf.record.DrawingGroupRecord) {
			com.example.alldocument.library.fc.hssf.record.DrawingGroupRecord lastDGRecord = (com.example.alldocument.library.fc.hssf.record.DrawingGroupRecord) _lastRecord;
			lastDGRecord.join((AbstractEscherHolderRecord) record);
			return null;
		}
		if (record.getSid() == com.example.alldocument.library.fc.hssf.record.ContinueRecord.sid) {
			com.example.alldocument.library.fc.hssf.record.ContinueRecord contRec = (ContinueRecord) record;

			if (_lastRecord instanceof ObjRecord || _lastRecord instanceof TextObjectRecord) {
				// Drawing records have a very strange continue behaviour.
				//There can actually be OBJ records mixed between the continues.
				if(_lastDrawingRecord != null)
				{
					_lastDrawingRecord.processContinueRecord(contRec.getData());
					contRec.resetData();
				}
				
				//we must remember the position of the continue record.
				//in the serialization procedure the original structure of records must be preserved
				if (_shouldIncludeContinueRecords) {
					return record;
				}
				return null;
			}
			if (_lastRecord instanceof com.example.alldocument.library.fc.hssf.record.DrawingGroupRecord) {
				((DrawingGroupRecord) _lastRecord).processContinueRecord(contRec.getData());
				return null;
			}
			if (_lastRecord instanceof com.example.alldocument.library.fc.hssf.record.DrawingRecord) {
				((com.example.alldocument.library.fc.hssf.record.DrawingRecord) _lastRecord).processContinueRecord(contRec.getData());
				return null;
			}
			if (_lastRecord instanceof UnknownRecord) {
				//Gracefully handle records that we don't know about,
				//that happen to be continued
				return record;
			}
			if (_lastRecord instanceof EOFRecord) {
				// This is really odd, but excel still sometimes
				//  outputs a file like this all the same
				return record;
			}
			throw new RecordFormatException("Unhandled Continue Record followining " + _lastRecord.getClass());
		}
		_lastRecord = record;
		if (record instanceof com.example.alldocument.library.fc.hssf.record.DrawingRecord) {
			_lastDrawingRecord = (DrawingRecord) record;
		}
		return record;
	}
	
	public void  dispose()
	{
	    _recStream = null;
	    
	    _unreadRecordBuffer = null;
	    
	    _lastRecord = null;
	    _lastDrawingRecord = null;
	}
}
