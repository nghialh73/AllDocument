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

package com.example.alldocument.library.fc.hslf.record;

import com.example.alldocument.library.fc.hslf.record.ColorSchemeAtom;
import com.example.alldocument.library.fc.hslf.record.PPDrawing;
import com.example.alldocument.library.fc.hslf.record.PositionDependentRecordContainer;

/**
 * The superclass of all sheet container records - Slide, Notes, MainMaster, etc.
 */
public abstract class SheetContainer extends PositionDependentRecordContainer
{

    /**
     * Returns the PPDrawing of this sheet, which has all the
     *  interesting data in it
     */
    public abstract PPDrawing getPPDrawing();

    public abstract ColorSchemeAtom getColorScheme();
    
    

}