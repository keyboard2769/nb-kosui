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

package kosui.ppputil;

/**
 * i cant remenber why i build this. <br>
 * possiblely it is for a memory viewer application. <br>
 */
public final class McLockedIntArray {

  private final int[] cmArray;

  private final int cmMask;

  //===
  
  /**
   *
   * @param pxSize array size
   */
  public McLockedIntArray(int pxSize) {
    cmArray = new int[VcConst.ccToPowerOfTwo(pxSize)];
    cmMask = cmArray.length - 1;
  }//+++

  //===
  /**
   *
   * @param pxIndex #
   * @param pxVal #
   */
  synchronized public void ccSet(int pxIndex, int pxVal) {
    cmArray[pxIndex & cmMask] = pxVal & 0xFFFF;
  }//+++

  //===
  
  /**
   *
   * @param pxIndex #
   * @param pxOffset #
   */
  synchronized public void ccShift(int pxIndex, int pxOffset) {
    cmArray[pxIndex & cmMask] += pxOffset;
    cmArray[pxIndex & cmMask] &= 0xFFFF;
  }//+++

  //===
  
  /**
   *
   * @param pxIndex #
   * @return #
   */
  synchronized public int ccGet(int pxIndex) {
    return cmArray[pxIndex & cmMask];
  }//+++

  /**
   *
   * @return length of array
   */
  synchronized public int ccGetSize() {
    return cmArray.length;
  }//+++

}//***eof