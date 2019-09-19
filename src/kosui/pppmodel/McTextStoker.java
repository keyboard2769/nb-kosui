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
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

/**
 * stacks lines as a combined huge string for showing logs.<br>
 * you might want to link a bunch of them together.<br>
 */
public class McTextStoker {
  
  private final McRingedIndex cmIndex;
  private final String[] cmData;
  
  public McTextStoker(int pxSize){
    cmIndex=new McRingedIndex(pxSize);
    cmData=new String[cmIndex.cmSize];
    Arrays.fill(cmData, "");
    VcConst.ccPrintln("i size?", cmIndex.cmSize);
    VcConst.ccPrintln("d size?", cmData.length);
  }//..!
  
  //===
  
  /**
   * add given text to tail and rolls index up.<br>
   * @param pxTag must have something
   * @param pxVal can be any thing
   */
  public final void ccWriteln(String pxTag, Object pxVal){
    if(!VcConst.ccIsValidString(pxTag)){return;}
    StringBuilder lpBuilder=new StringBuilder(pxTag);
    if(pxVal!=null){
      lpBuilder.append(':');
      lpBuilder.append(pxVal.toString());
    }//..?
    cmData[cmIndex.ccGetTail()]=lpBuilder.toString();
    cmIndex.ccRollupTailIndex();
  }//+++
  
  /**
   * add given text to tail and rolls index up.<br>
   * @param pxLine must have something
   */
  public final void ccWriteln(String pxLine){
    McTextStoker.this.ccWriteln(pxLine,null);
  }//+++
  
  /**
   * clear all lines.
   */
  public final void ccClear(){
    Arrays.fill(cmData, "");
    cmIndex.ccClear();
  }//+++
  
  /**
   * initiate with some message.
   * @param pxDefault must have something.
   */
  public final void ccClear(String pxDefault){
    ccClear();
    if(!VcConst.ccIsValidString(pxDefault)){return;}
    ccWriteln(pxDefault);
  }//+++
  
  /**
   * @param pxLogicalIndex smaller is younger
   * @return per stack
   */
  public final String ccGet(int pxLogicalIndex){
    String lpRes=cmData[cmIndex.ccToReversedAddress(pxLogicalIndex)];
    return VcStringUtility.ccNulloutString(lpRes);
  }//+++
  
  /**
   * pack up as a giant string of all lines stoked.<br>
   * aliasing to PApplet.join(), may cause big overhead.<br>
   * @return #
   */
  public final String ccGet(){
    return PApplet.join(cmData, VcConst.C_V_NEWLINE.charAt(0));
  }//+++
  
  /**
   * get and logically remove oldest stoked.<br>
   * actually it just rolls up the index.<br>
   * @return never null
   */
  public final String ccRetrieve(){
    String lpRes=cmData[cmIndex.ccGetNose()];
    cmIndex.ccRollupNoseIndex();
    return VcStringUtility.ccNulloutString(lpRes);
  }//+++
  
  /**
   * @return size of allocated string list
   */
  public final int ccGetSize(){
    return cmData.length;
  }//+++
  
  /**
   * @return result of index counting
   */
  public final int ccGetUsedSize(){
    return cmIndex.ccGetUsed();
  }//+++
  
}//***eof
