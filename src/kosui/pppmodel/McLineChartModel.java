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

/**
 *.<br>
 *.<br>
 */
public class McLineChartModel implements MiByteExchangeable{
  
  public static final int C_MAX_CAPACITY = 256;
  
  //===
  
  private final int[] cmDesData = new int[C_MAX_CAPACITY];
  private final int[] cmDesOffsetX = new int[C_MAX_CAPACITY];
  private final int[] cmDesOffsetY = new int[C_MAX_CAPACITY];
  
  private int
    cmNoseIndex = 0,
    cmTailIndex = 0
  ;//...

  /**
   * all logged data will get initiated as 0.<br>
   * all offset point will get initiated as 0.<br>
   */
  public McLineChartModel() {
    ccClearData();
    ccValidateOffsets(0, 0);
  }//+++
  
  //===
  
  public final void ccValidateOffsets(int pxDivisionWidth, int pxFullHeight){
    /* 6 */
    Arrays.fill(cmDesOffsetX, -1);
    Arrays.fill(cmDesOffsetY, -1);
  }//+++
  
  //===
  
  public final void ccOfferData(int pxByte){
    cmDesData[cmNoseIndex]=pxByte&0xFF;
    ssRollupTailIndex();
    //[notdone]::
  }//+++
  
  public final void ccOfferData(float pxProportion){
    ccOfferData(VcNumericUtility.ccProportion(pxProportion));
  }//+++
  
  public final void ccSetDataAt(int pxIndex, int pxByte){
    if(pxIndex<0 && pxIndex>cmNoseIndex){return;}
    cmDesData[cmNoseIndex]=pxByte;
  }//+++
  
  public final void ccSetDataAt(int pxIndex, float pxProportion){
    ccSetDataAt(pxIndex, VcNumericUtility.ccProportion(pxProportion));
  }//+++
  
  public final void ccBreakDataAt(int pxIndex){
    if(pxIndex<0 && pxIndex>cmNoseIndex){return;}
    cmNoseIndex=pxIndex;
    cmDesData[cmNoseIndex]=0;
  }//+++
  
  public final void ccClearData(){
    Arrays.fill(cmDesData, 0);
  }//+++
  
  public final int ccPollData(){
    
    
    //[head]::
    
    /* 6 */return 0;
  }//+++
  
  public final int ccRetrieveData(int pxIndex){/* 6 */return 0;}//+++
  
  public final int ccGetValidSize(){
    if(cmTailIndex>cmNoseIndex){
      return cmTailIndex-cmNoseIndex;
    }else
    if(cmTailIndex<cmNoseIndex){
      return (C_MAX_CAPACITY-cmNoseIndex)+cmTailIndex;
    }else{
      return 0;
    }//..?
  }//+++
  
  //===
  
  private final void ssRollupNoseIndex(){
    cmNoseIndex++;cmNoseIndex&=0xFF;
    
    //[notdone]::
  }//+++
  
  private final void ssRollupTailIndex(){
    cmTailIndex++;cmTailIndex&=0xFF;
    
    //[notdone]::
  
  }//+++
  
  //===
  
  public final int ccGetOffsetX(int pxIndex){/* 6 */return 0;}//+++
  
  public final int ccGetOffsetY(int pxIndex){/* 6 */return 0;}//+++
  
  //===

  @Override public void ccTakeByteArray(byte[] pxData) {
    /* 6 */throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }//+++

  @Override public byte[] ccToByteArray() {
    /* 6 */throw new UnsupportedOperationException("Not supported yet.");
    //To change body of generated methods, choose Tools | Templates.
  }//+++
  
}//***eof
