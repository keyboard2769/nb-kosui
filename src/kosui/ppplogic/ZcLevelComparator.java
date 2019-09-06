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
 * ranks a given value in a preset serial of values as level. <br>
 * for some reason we just have a fixed levels here. <br>
 */
public class ZcLevelComparator{
  
  private static final int C_SIZE = 32;//..arbitary
  
  private static final int C_MASK = 31;//..arbitary
  
  //===

  private final int[] cmLevel;
  private int cmCurrentValue=0, cmCurrentLevel=0;

  /**
   * level range is arbitrarily hard coded as 0-32.<br>
   * @param pxMaxLevel will get fine split by default
   */
  public ZcLevelComparator(int pxMaxLevel){
    cmLevel=VcNumericUtility.ccFineSplit(pxMaxLevel, C_SIZE);
  }//+++
  
  //===
  
  /**
   * 
   * supposedly should be called from the draw() loop.<br>
   * <em>BUT THIS IS A PROBLEM</em><br>
   */
  public final void ccRun(){
    
    //[todo]::refine this to make it can get comparated any where
    
    cmCurrentLevel=-1;
    for(int i=1; i<C_SIZE; i++){
      if(cmCurrentValue<=cmLevel[i]){
        cmCurrentLevel=i;
        break;
      }//..?
    }//..~
  }//+++
  
  //===
  
  /**
   * will get blocked out if passed level is out of boundary
   *   or passed value is invalid. 
   * @param pxLevel #
   * @param pxValue #
   */
  public final void ccSetCompareLevel(int pxLevel, int pxValue){
    if(pxLevel<0){return;}
    if(pxLevel>=C_SIZE){return;}
    if(pxLevel==0){cmLevel[0]=pxValue;}
    if(pxLevel>0){
      if(pxValue<cmLevel[pxLevel-1])
        {cmLevel[pxLevel]=cmLevel[pxLevel-1];}
      else
        {cmLevel[pxLevel]=pxValue;}
    }//..?
  }//+++

  /**
   * 
   * @param pxValue or, say, input value
   */
  public final void ccSetCurrentLevel(int pxValue){
    cmCurrentValue=pxValue;
  }//+++
  
  //===

  /**
   * 
   * @return #
   */
  public final int ccGetCurrentLevel(){
    return cmCurrentLevel;
  }//+++

  /**
   * 
   * @param pxLevel #
   * @return #
   */
  public final boolean ccIsAtLevel(int pxLevel){
    return pxLevel==cmCurrentLevel;
  }//+++

  /**
   * 
   * @return #
   */
  public final boolean ccIsZero(){
    return cmCurrentValue<=cmLevel[0];
  }//+++

  /**
   * compared with the max level, AKA, eighth.
   * @return #
   */
  public final boolean ccIsFull(){
    return cmCurrentValue>=cmLevel[C_MASK];
  }//+++
  
  //=== test
  
  /**
   * 
   * @return #
   * @deprecated for test use
   */
  @Deprecated public final int[] tstGetLevelSetting(){
    return cmLevel;
  }//+++
  
  /**
   * 
   * @return #
   * @deprecated for test use
   */
  @Deprecated public final int tstGetValue(){
    return cmCurrentValue;
  }//+++
  
  /**
   * 
   * @return #
   * @deprecated for test use
   */
  @Deprecated public final int tstGetLevel(){
    return cmCurrentLevel;
  }//+++

}//***eof
