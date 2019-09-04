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

import java.util.Arrays;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;

/**
 * push to the tail and pop from nose, just like a giant nollie laser flip.<br>
 * for something you need to pick quit and soon toss away.<br>
 */
public class McSimpleRingBuffer implements MiByteExchangeable{
  
  private final int[] cmData;
  private final int cmMask;
  private int cmNoseIndex,cmTailIndex;//...
  private int cmUsed;
  
  /**
   * implemented on an integer array.<br>
   * @param pxSize will be fixed to the number of power of two
   */
  public McSimpleRingBuffer(int pxSize){
    cmData=new int[VcNumericUtility.ccToPowerOfTwo(pxSize)];
    cmMask=cmData.length-1;
    ccClear();
  }//..!
  
  //===
  
  /**
   * @return length of data held
   */
  public final int ccGetCapacity(){
    return cmData.length;
  }//+++
  
  /**
   * @return an easy way to cut the out of bound facts
   */
  public final int ccGetIndexMask(){
    return cmMask;
  }//+++
  
  /**
   * @return based on poll and offer counting and might be wrong
   */
  public final int ccGetUsedSize(){
    return cmUsed;
  }//+++
  
  /**
   * @return the oldest
   */
  public final int ccGetNoseIndex(){
    return cmNoseIndex;
  }//+++
  
  /**
   * @return the youngest
   */
  public final int ccGetTailIndex(){
    return cmTailIndex;
  }//+++
  
  //===
  
  /**
   * add given data to tail and rolls index up.<br>
   * @param pxValue #
   */
  public final void ccOffer(int pxValue){
    ccSet(cmTailIndex, pxValue);
    ssRollupTailIndex();
  }//+++
  
  /**
   * retrieve data from nose and rolls index up.<br>
   * @return #
   */
  public final int ccPoll(){
    int lpRes=ccGet(cmNoseIndex);
    ccSet(cmNoseIndex, 0);
    ssRollupNoseIndex();
    return lpRes;
  }//+++
  
  //[plan]::public final void ccBreakTo(int pxIndex)
  //[plan]::public final void ccBreakFrom(int pxIndex)
  
  /**
   * set data to given index in the random access way.<br>
   * @param pxIndex will get masked
   * @param pxValue no matter it is marked used or not
   */
  public final void ccSet(int pxIndex, int pxValue){
    cmData[pxIndex&cmMask]=pxValue;
  }//+++
  
  /**
   * get data from given index in the random access way.<br>
   * @param pxIndex will get masked
   * @return no matter it is marked used or not
   */
  public final int ccGet(int pxIndex){
    return cmData[pxIndex&cmMask];
  }//+++
  
  /**
   * initiate everything
   */
  public final void ccClear(){
    Arrays.fill(cmData, 0);
    cmNoseIndex=0;
    cmTailIndex=1;
    cmUsed=0;
  }//+++
  
  private void ssRollupNoseIndex(){
    cmNoseIndex++;cmNoseIndex&=cmMask;
    cmUsed--;
    if(cmNoseIndex==cmTailIndex){
      cmTailIndex++;cmTailIndex&=cmMask;
      cmUsed=0;
    }//..?
  }//+++
  
  private void ssRollupTailIndex(){
    cmTailIndex++;cmTailIndex&=cmMask;
    cmUsed++;
    if(cmTailIndex==cmNoseIndex){
      cmNoseIndex++;cmNoseIndex&=cmMask;
      cmUsed=ccGetCapacity();
    }//..?  
  }//+++
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public void ccTakeByteArray(byte[] pxData) {
    /* 6 */throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public byte[] ccToByteArray() {
    /* 6 */throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }//+++
  
  /**
   * @return packed up indicators
   */
  @Override public String toString() {
    return 
       VcStringUtility.ccPackupParedTag("nose",cmNoseIndex)
      +VcStringUtility.ccPackupParedTag("tail",cmTailIndex)
      +"|"
      +VcStringUtility.ccPackupParedTag("total",cmData.length)
      +VcStringUtility.ccPackupParedTag("used",ccGetUsedSize())
      +"|"
      +VcStringUtility.ccPackupParedTag("mask",cmMask)
    ;
  }//+++
  
}//***
