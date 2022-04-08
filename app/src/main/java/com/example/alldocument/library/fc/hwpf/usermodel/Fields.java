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
package com.example.alldocument.library.fc.hwpf.usermodel;

import java.util.Collection;

import com.example.alldocument.library.fc.hwpf.model.FieldsDocumentPart;
import com.example.alldocument.library.fc.hwpf.usermodel.Field;


/**
 * User-friendly interface to access document {@link com.example.alldocument.library.fc.hwpf.usermodel.Field}s
 * 
 * @author Sergey Vladimirov (vlsergey {at} gmail {dot} com)
 */
public interface Fields
{
    com.example.alldocument.library.fc.hwpf.usermodel.Field getFieldByStartOffset(FieldsDocumentPart documentPart, int offset);

    Collection<Field> getFields(FieldsDocumentPart part);
}
