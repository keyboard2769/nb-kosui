/*
 * Copyright (C) 2019 Key Parker from K.I.C
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kosui.ppplogic;

import kosui.ppputil.VcNumericUtility;

/**
 * after reading this a kid who didn't know about bit mask 
 * thought it is a black magic.<br>
 * it is too essential to me that i didn't even thought about making it a class
 * until i endup with a bunch of volatile integer.<br>
 */
public class ZcRoller {
  
  private volatile int cmVal;
  private final int cmMask;
  
  /**
   * @param pxPowerOfTwo supposedly THE frame rate
   * @param pxInitValue supposedly as frame count
   */
  public ZcRoller(int pxPowerOfTwo, int pxInitValue) {
    cmMask=VcNumericUtility.ccToPowerOfTwo(pxPowerOfTwo&0x7FFF)-1;
    cmVal=pxInitValue;
  }//..!
  
  /**
   * adding and masking.<br>
   * supposedly to get called from a scan loop.<br>
   */
  public final void ccRoll(){
    cmVal++;
    cmVal&=cmMask;
  }//+++
  
  /**
   * @return currently at 
   */
  public final int ccGetValue(){
    return cmVal;
  }//+++
  
  /**
   * @param pxVal frame count
   * @return if equals
   */
  public final boolean ccIsAt(int pxVal){
    return cmVal==pxVal;
  }//+++
  
  /**
   * @param pxVal frame count
   * @return if greater than
   */
  public final boolean ccIsAbove(int pxVal){
    return cmVal>=pxVal;
  }//+++
  
}//***eof
