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

/**
 *.<br>
 *.<br>
 */
public class McLineChartModel {
  
  private final int[] cmDesOffsetX;
  private final int[] cmDesOffsetY;
  private final McSimpleRingBuffer cmData;
  
  public McLineChartModel(int pxSize) {
    cmData=new McSimpleRingBuffer(pxSize);
    cmDesOffsetX  = new int[cmData.ccGetCapacity()];
    cmDesOffsetY = new int[cmData.ccGetCapacity()];
    ccValidateOffsets(0, 0);
  }//+++
  
  //===
  
  public final void ccValidateOffsets(int pxDivisionWidth, int pxFullHeight){
    for(int i=0,s=cmData.ccGetTailIndex();i<s;i++){
      cmDesOffsetX[i&cmData.ccGetIndexMask()]=i*pxDivisionWidth;
      cmDesOffsetY[(s-i)&cmData.ccGetIndexMask()]=(int)(
        VcNumericUtility.ccProportion(cmData.ccGet(s-i))*pxFullHeight
      );
    }//..~
  }//+++
  
  //===
  
  public final void ccOffer(int pxValue){
    cmData.ccOffer(pxValue&0xFF);
  }//+++
  
  public final void ccOffer(float pxValue){
    cmData.ccOffer(VcNumericUtility.ccProportion(pxValue));
  }//+++
  
  //===
  
  public final int ccGetSize(){
    return cmData.ccGetCapacity();
  }//+++
  
  public final McSimpleRingBuffer ccGetData(){
    return cmData;
  }//+++
  
  public final int ccGetOffsetX(int pxIndex){
    return cmDesOffsetX[pxIndex&cmData.ccGetIndexMask()];
  }//+++
  
  public final int ccGetOffsetY(int pxIndex){
    return cmDesOffsetY[pxIndex&cmData.ccGetIndexMask()];
  }//+++
  
}//***eof
