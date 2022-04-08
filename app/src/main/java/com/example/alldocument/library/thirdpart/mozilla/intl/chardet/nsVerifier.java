/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is mozilla.org code.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1998
 * the Initial Developer. All Rights Reserved.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package com.example.alldocument.library.thirdpart.mozilla.intl.chardet;

import java.lang.*;

public abstract class nsVerifier {

     static final byte eStart = (byte)0;
     static final byte eError = (byte)1;
     static final byte eItsMe = (byte)2;
     static final int eidxSft4bits = 3;
     static final int eSftMsk4bits = 7;
     static final int eBitSft4bits = 2;
     static final int eUnitMsk4bits = 0x0000000F;

     nsVerifier() {
     }

     public abstract String charset() ;
     public abstract int stFactor()   ;
     public abstract int[] cclass()   ;
     public abstract int[] states()   ;

     public abstract boolean isUCS2() ;

     public static byte getNextState(nsVerifier v, byte b, byte s) {

         return (byte) ( 0xFF & 
	     (((v.states()[((
		   (s*v.stFactor()+(((v.cclass()[((b&0xFF)>>v.eidxSft4bits)]) 
		   >> ((b & v.eSftMsk4bits) << v.eBitSft4bits)) 
		   & v.eUnitMsk4bits ))&0xFF)
		>> v.eidxSft4bits) ]) >> (((
		   (s*v.stFactor()+(((v.cclass()[((b&0xFF)>>v.eidxSft4bits)]) 
		   >> ((b & v.eSftMsk4bits) << v.eBitSft4bits)) 
		   & v.eUnitMsk4bits ))&0xFF) 
		& v.eSftMsk4bits) << v.eBitSft4bits)) & v.eUnitMsk4bits )
	 ) ;

     }


}
