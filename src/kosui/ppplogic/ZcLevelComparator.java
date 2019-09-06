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

import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import processing.core.PApplet;

/**
 * ranks a given value in a preset serial of values as level. <br>
 * for some reason we just have a fixed levels here. <br>
 */
public class ZcLevelComparator{
  
  private static final int C_LEVEL_MAX  = 32;//..arbitary
  
  private static final int C_LEVEL_MASK = 31;//..arbitary
  
  private static final int C_VALUE_MASK = 0x0000FFFF;//..arbitary
  
  //===

  private final int[] cmDesPartitionValue;
  private final int[] cmDesPartitionLevel;

  /**
   * level range is arbitrarily hard coded as 0-32.<br>
   * @param pxMaxValue will get masked upto 65535 then split 
   */
  public ZcLevelComparator(int pxMaxValue){
    cmDesPartitionValue=VcNumericUtility
      .ccFineSplit(pxMaxValue&C_VALUE_MASK, C_LEVEL_MAX);
    cmDesPartitionLevel=VcNumericUtility
      .ccFineSplit(C_LEVEL_MAX, 4);
  }//+++
  
  //===
  
  /**
   * @param pxSource will get masked to 0-65535 arbitraryly
   * @return level
   */
  public final int ccComparate(int pxSource){
    int lpFixed=pxSource&C_VALUE_MASK;
    int lpBeginLV,lpEndLV;
    if(lpFixed==0){return 0;}
    if(lpFixed>=cmDesPartitionValue[cmDesPartitionLevel[2]]){
      if(lpFixed>=cmDesPartitionValue[cmDesPartitionLevel[3]]){
        lpBeginLV=cmDesPartitionLevel[3];
        lpEndLV=C_LEVEL_MAX;
      }else{
        lpBeginLV=cmDesPartitionLevel[2];
        lpEndLV=cmDesPartitionLevel[3];
      }//..?
    }else{
      if(lpFixed>=cmDesPartitionValue[cmDesPartitionLevel[1]]){
        lpBeginLV=cmDesPartitionLevel[1];
        lpEndLV=cmDesPartitionLevel[2];
      }else{
        lpBeginLV=cmDesPartitionLevel[0];
        lpEndLV=cmDesPartitionLevel[1];
      }//..?
    }//..?
    int lpRes;
    for(lpRes=lpBeginLV+1;lpRes<lpEndLV;lpRes++){
      //VcConst.ccPrintln("begin", lpRes-1);
      //VcConst.ccPrintln("end", lpRes);
      if(ZcRangedModel.ccContains(
        lpFixed,
        cmDesPartitionValue[lpRes-1],
        cmDesPartitionValue[lpRes  ]
      )){break;}//..?
    }//..~
    return lpRes;
  }//+++
  
  /**
   * will get blocked out if passed level is out of boundary
   *   or passed value is invalid. 
   * @param pxLevel #
   * @param pxValue #
   */
  public final void ccSetLevelValue(int pxLevel, int pxValue){
    if(!ZcRangedModel.ccContains(pxLevel, 0, C_LEVEL_MASK)){return;}
    if(pxLevel==0){return;}
    switch(pxLevel){
      case 0:break;
      case 1:
        cmDesPartitionValue[pxLevel]=ZcRangedModel.ccLimitExclude(
          pxValue,
          cmDesPartitionValue[0],
          cmDesPartitionValue[pxLevel+1]
        );
      break;
      case C_LEVEL_MASK:
        cmDesPartitionValue[pxLevel]=ZcRangedModel.ccLimitExclude(
          pxValue,
          cmDesPartitionValue[pxLevel-1],
          cmDesPartitionValue[C_LEVEL_MASK]
        );
      break;
      default:
        VcConst.ccPrintln("so?",pxLevel);
        VcConst.ccPrintln("pre?",cmDesPartitionValue[pxLevel - 1]);
        VcConst.ccPrintln("next?",cmDesPartitionValue[pxLevel+1]);
        VcConst.ccPrintln("so?",ZcRangedModel.ccLimitExclude(
          pxValue,
          cmDesPartitionValue[pxLevel-1],
          cmDesPartitionValue[pxLevel+1]
        ));
        cmDesPartitionValue[pxLevel]=ZcRangedModel.ccLimitExclude(
          pxValue,
          cmDesPartitionValue[pxLevel-1],
          cmDesPartitionValue[pxLevel+1]
        );
      break;
    }//..?
  }//+++

  //===

  /**
   * tell if the given value matches given level.<br>
   * @param pxValue #
   * @param pxLevel #
   * @return #
   */
  public final boolean ccIsAtLevel(int pxValue, int pxLevel){
    return pxLevel==ccComparate(pxValue);
  }//+++
  
  //=== test
  
  /**
   * prints contents to system out.<br>
   * @deprecated for test use
   */
  @Deprecated public final void tstReadup(){
    VcConst.ccPrintln(super.toString(),"value >>>");
    VcConst.ccPrintln
      (VcNumericUtility.ccPackupDecStringTable(cmDesPartitionValue, 8));
    VcConst.ccPrintln("level >>>");
    VcConst.ccPrintln
      (VcNumericUtility.ccPackupDecStringTable(cmDesPartitionLevel, 8));
    VcConst.ccPrintln("<<<");
  }//+++
  
}//***eof
