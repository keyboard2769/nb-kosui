/*
 * Copyright (C) 2018 Key Parker from K.I.C.
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

package kosui.pppmodel;

import java.util.Arrays;
import kosui.ppputil.VcNumericUtility;

/**
 * i cant remember why i build this. <br>
 * possibly it is for a memory viewer application. <br>
 */
public final class McLockedIntArray {

  private final int[] cmArray;

  private final int cmMask;

  //===
  
  /**
   * @param pxSize will get manipulated to power of two
   */
  public McLockedIntArray(int pxSize) {
    cmArray = new int[VcNumericUtility.ccToPowerOfTwo(pxSize)];
    Arrays.fill(cmArray, 0);
    cmMask = cmArray.length - 1;
  }//+++

  //===
  
  /**
   * @param pxIndex will get masked to fit size
   * @param pxVal will get masked to 0-65535
   */
  synchronized public void ccSet(int pxIndex, int pxVal) {
    cmArray[pxIndex & cmMask] = pxVal & 0xFFFF;
  }//+++

  //===
  
  /**
   * @param pxIndex will get masked to fit size
   * @param pxOffset value mask is performed after adding
   */
  synchronized public void ccShift(int pxIndex, int pxOffset) {
    cmArray[pxIndex & cmMask] += pxOffset;
    cmArray[pxIndex & cmMask] &= 0xFFFF;
  }//+++

  //===
  
  /**
   * @param pxIndex will get masked to fit size
   * @return raw data
   */
  synchronized public int ccGet(int pxIndex) {
    return cmArray[pxIndex & cmMask];
  }//+++

  /**
   * @return length of array
   */
  synchronized public int ccGetSize() {
    return cmArray.length;
  }//+++
  
  /**
   * @return via for iteration
   */
  synchronized public int ccSum(){
    int lpRes=0;
    for(int i:cmArray){lpRes+=i;}
    return lpRes;
  }//+++

}//***eof
