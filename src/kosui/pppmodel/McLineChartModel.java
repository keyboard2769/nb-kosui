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

import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;

/**
 * the only reason i am stuck on the line chart is that burner 
 * trending problem.<br>
 * but this model does not have to, bar chart or just point chart 
 * might also be capable of.<br>
 */
public class McLineChartModel {
  
  private final int[] cmDesOffsetX;
  private final int[] cmDesOffsetY;
  private final McSimpleRingBuffer cmData;
  
  /**
   * buffer size is unchangeable.<br>
   * @param pxSize will get fixed to the value of power of two
   */
  public McLineChartModel(int pxSize) {
    cmData=new McSimpleRingBuffer(pxSize);
    cmDesOffsetX  = new int[cmData.ccGetCapacity()];
    cmDesOffsetY = new int[cmData.ccGetCapacity()];
    ccValidateOffsets(0, 0);
  }//+++
  
  //===
  
  /**
   * please note that this division is absolute.<br>
   * this means if you passed something just dividing the width in the
   * manner of integer, you might not get what you want.<br>
   * now i see why PApplet made everything float.<br>
   * <b>BUT I WON'T.</b><br>
   * @param pxDivisionWidth pix
   * @param pxFullHeight pix
   */
  public final void ccValidateOffsets(int pxDivisionWidth, int pxFullHeight){
    for(int i=0,s=cmData.ccGetCapacity();i<s;i++){
      cmDesOffsetX[i&cmData.cmIndex.cmMask]=i*pxDivisionWidth;
      cmDesOffsetY[i&cmData.cmIndex.cmMask]=(int)(
        VcNumericUtility.ccProportion(cmData.ccGetLogical(i))*pxFullHeight
      );
    }//..~
  }//+++
  
  //===
  
  /**
   * @param pxByte 0-255
   */
  synchronized public final void ccOffer(int pxByte){
    int lpFix = pxByte&0xFF;
    cmData.ccOffer(lpFix<=0?1:lpFix);
  }//+++
  
  /**
   * @param pxProportion 0.0-1.0
   */
  synchronized public final void ccOffer(float pxProportion){
    int lpFix = VcNumericUtility.ccProportion(pxProportion);
    cmData.ccOffer(lpFix<=0?1:lpFix);
  }//+++
  
  //===
  
  /**
   * @return size of buffer
   */
  public final int ccGetSize(){
    return cmData.ccGetCapacity();
  }//+++
  
  /**
   * @return the buffer
   */
  public final McSimpleRingBuffer ccGetData(){
    return cmData;
  }//+++
  
  /**
   * @param pxIndex smaller is older
   * @return pix
   */
  public final int ccGetOffsetX(int pxIndex){
    return cmDesOffsetX[pxIndex&cmData.cmIndex.cmMask];
  }//+++
  
  /**
   * @param pxIndex smaller is older
   * @return pix
   */
  public final int ccGetOffsetY(int pxIndex){
    return cmDesOffsetY[pxIndex&cmData.cmIndex.cmMask];
  }//+++
  
  //===
  
  /**
   * @param pxWrap #
   * @return #
   * @deprecated for test use only
   */
  @Deprecated public final String tstPackupData(int pxWrap){
    StringBuilder lpRes
      = new StringBuilder(super.toString()+VcConst.C_V_NEWLINE);
    int lpWrapCount=0;
    for(int i=0,s=cmData.ccGetCapacity();i<s;i++){
      lpRes.append(VcStringUtility
        .ccPackupDimensionValue(ccGetOffsetX(i), ccGetOffsetY(i)));
      lpRes.append(" ");
      lpWrapCount++;
      if(lpWrapCount==pxWrap){
        lpWrapCount=0;
        lpRes.append(VcConst.C_V_NEWLINE);
      }//..?
    }//..~
    return lpRes.toString();
  }//+++
  
}//***eof
