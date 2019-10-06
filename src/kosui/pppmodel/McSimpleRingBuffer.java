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
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;

/**
 * push to the tail and pop from nose, just like a giant nollie laser flip.<br>
 * for something you need to pick quit and soon toss away.<br>
 */
public class McSimpleRingBuffer{
  
  private final int[] cmData;
  
  /**
   * hold a nose and a tail
   */
  public final McRingedIndex cmIndex;
  
  /**
   * implemented on an integer array.<br>
   * @param pxSize will be fixed to the value of power of two
   */
  public McSimpleRingBuffer(int pxSize){
    cmIndex=new McRingedIndex(pxSize);
    cmData=new int[cmIndex.cmSize];
    ccClear();
  }//..!
  
  //===
  
  /**
   * this might be equal to the size
   * only if implemented correctly.<br>
   * @return length of data held
   */
  public final int ccGetCapacity(){
    return cmData.length;
  }//+++
  
  //===
  
  /**
   * add given data to tail and rolls index up.<br>
   * @param pxValue #
   */
  public final void ccOffer(int pxValue){
    ccSetAbsolute(cmIndex.ccGetTail(), pxValue);
    cmIndex.ccRollupTailIndex();
  }//+++
  
  /**
   * retrieve data from nose and rolls index up.<br>
   * @return #
   */
  public final int ccPoll(){
    int lpRes=ccGetAbsolute(cmIndex.ccGetNose());
    ccSetAbsolute(cmIndex.ccGetNose(), 0);
    cmIndex.ccRollupNoseIndex();
    return lpRes;
  }//+++
  
  /**
   * set data to given address in the random access way.<br>
   * @param pxAddr absolute offset of the data array
   * @param pxValue no matter target is marked used or not
   */
  public final void ccSetAbsolute(int pxAddr, int pxValue){
    cmData[pxAddr&cmIndex.cmMask]=pxValue;
  }//+++
  
  /**
   * get data from given address in the random access way.<br>
   * @param pxAddr absolute offset of the data array
   * @return no matter it is marked used or not
   */
  public final int ccGetAbsolute(int pxAddr){
    return cmData[pxAddr&cmIndex.cmMask];
  }//+++
  
  /**
   * get data from given index.<br>
   * @param pxIndex logical index count from nose
   * @param pxValue no matter target is marked used or not
   */
  public final void ccSetLogical(int pxIndex, int pxValue){
    cmData[cmIndex.ccToAbsoluteAddress(pxIndex)]=pxValue;
  }//+++
  
  /**
   * get data to given index.<br>
   * @param pxIndex logical index count from nose
   * @return no matter it is marked used or not
   */
  public final int ccGetLogical(int pxIndex){
    return cmData[cmIndex.ccToAbsoluteAddress(pxIndex)];
  }//+++
  
  /**
   * clear every thing
   */
  public final void ccClear(){
    cmIndex.ccClear();
    Arrays.fill(cmData, 0);
  }//+++
  
  //===
  
  /**
   * @return packed up indicators
   */
  public final String ccFormat() {
    StringBuilder lpRes = new StringBuilder("|");
    lpRes.append(VcStringUtility.ccPackupPairedTag("nose",cmIndex.ccGetNose()));
    lpRes.append(VcStringUtility.ccPackupPairedTag("tail",cmIndex.ccGetTail()));
    lpRes.append(VcStringUtility.ccPackupPairedTag("used",cmIndex.ccGetUsed()));
    lpRes.append(VcStringUtility.ccPackupPairedTag("mask",cmIndex.cmMask));
    lpRes.append(VcStringUtility.ccPackupPairedTag("total",cmData.length));
    return lpRes.toString();
  }//+++
  
  /**
   * @param pxWrap #
   * @return in absolute order
   * @deprecated # for test use only
   */
  @Deprecated public final String tstPackupAbsolute(int pxWrap){
    StringBuilder lpRes
      = new StringBuilder(super.toString()+VcConst.C_V_NEWLINE);
    lpRes.append("absolute:");
    lpRes.append(VcConst.C_V_NEWLINE);
    int lpWrapCount=0;
    for(int i=0,s=ccGetCapacity();i<s;i++){
      lpRes.append(VcStringUtility
        .ccPackupPairedTag(Integer.toString(i), ccGetAbsolute(i)));
      lpRes.append(' ');
      lpWrapCount++;
      if(lpWrapCount==pxWrap){
        lpRes.append(VcConst.C_V_NEWLINE);
        lpWrapCount=0;
      }//..?
    }//..~
    return lpRes.toString();
  }//+++
  
    /**
   * @param pxWrap #
   * @return in logical order
   * @deprecated # for test use only
   */
  @Deprecated public final String tstPackupLogical(int pxWrap){
    StringBuilder lpRes
      = new StringBuilder(super.toString()+VcConst.C_V_NEWLINE);
    lpRes.append("logical:");
    lpRes.append(VcConst.C_V_NEWLINE);
    int lpWrapCount=0;
    for(int i=0,s=ccGetCapacity();i<s;i++){
      lpRes.append(VcStringUtility
        .ccPackupPairedTag(Integer.toString(i), ccGetLogical(i)));
      lpRes.append(' ');
      lpWrapCount++;
      if(lpWrapCount==pxWrap){
        lpRes.append(VcConst.C_V_NEWLINE);
        lpWrapCount=0;
      }//..?
    }//..~
    return lpRes.toString();
  }//+++
  
}//***
