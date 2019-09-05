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

package kosui.pppmodel;

import kosui.ppputil.VcNumericUtility;

public class McRingedIndex {
  
  /**
   * range of index
   */
  public final int cmSize;
  
  /**
   * mask of index
   */
  public final int cmMask;
  
  private int cmNose,cmTail;
  private int cmUsed;
  
  /**
   * @param pxSize will get fixed to the value of power of two
   */
  public McRingedIndex (int pxSize){
    cmSize=VcNumericUtility.ccToPowerOfTwo(pxSize);
    cmMask=cmSize-1;
  }//+++
  
  //===
  
  /**
   * @return based on poll and offer counting and might be wrong
   */
  public final int ccGetUsed(){
    return cmUsed;
  }//+++
  
  /**
   * @return the oldest
   */
  public final int ccGetNose(){
    return cmNose;
  }//+++
  
  /**
   * @return the youngest
   */
  public final int ccGetTail(){
    return cmTail;
  }//+++
  
  /**
   * initiate everything
   */
  public final void ccClear(){
    cmNose=cmMask;
    cmTail=0;
    cmUsed=0;
  }//+++
  
  /**
   * usage may vary per implement.<br>
   * count from nose.<br>
   * @param pxLogicalIndex seen from out side
   * @return offset to the actual data 
   */
  public final int ccToAbsoluteAddress(int pxLogicalIndex){
    //[figureout]::why we have to minus one here to make that char work??
    return (pxLogicalIndex+cmNose-1)&cmMask;
  }//+++
  
  /**
   * usage may vary per implement.<br>
   * count from tail.<br>
   * @param pxLogicalIndex seen from out side
   * @return offset to the actual data
   */
  public final int ccToReversedAddress(int pxLogicalIndex){
    return (cmTail-pxLogicalIndex+1)&cmMask;
  }//+++
  
  /**
   * also rolls up tail while max capacity reached.<br>
   * since used size is counted here too you should consider it as 
   * just a rule of thumb.<br>
   */
  public final void ccRollupNoseIndex(){
    cmNose++;cmNose&=cmMask;
    cmUsed--;
    if(cmNose==cmTail){
      cmTail++;cmTail&=cmMask;
      cmUsed=0;
    }//..?
  }//+++
  
  /**
   * also rolls up nose while max capacity reached.<br>
   * since used size is counted here too you should consider it as 
   * just a rule of thumb.<br>
   */
  public final void ccRollupTailIndex(){
    cmTail++;cmTail&=cmMask;
    cmUsed++;
    if(cmTail==cmNose){
      cmNose++;cmNose&=cmMask;
      cmUsed=cmSize;
    }//..?  
  }//+++

}//***eof
