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

/**
 * ranks a given value in a preset serial of values as level. <br>
 * for some reason we just have a fixed levels here. <br>
 */
public class ZcLevelComparator{
  
  /**
   * arbitrary
   */
  public static final int C_LEVEL_MAX  = 32;
  
  /**
   * arbitrary
   */
  public static final int C_LEVEL_MASK = 31;
  
  /**
   * arbitrary
   */
  public static final int C_VALUE_MASK = 0x0000FFFF;
  
  //===

  private final int[] cmDesJudge;
  private final int[] cmDesAnchor;

  /**
   * level range is arbitrarily hard coded as 0-32.<br>
   * @param pxMaxValue will get masked upto 65535 then split 
   */
  public ZcLevelComparator(int pxMaxValue){
    cmDesJudge=VcNumericUtility
      .ccFineSplit(pxMaxValue&C_VALUE_MASK, C_LEVEL_MAX);
    cmDesAnchor=VcNumericUtility
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
    if(lpFixed>=cmDesJudge[cmDesAnchor[2]]){
      if(lpFixed>=cmDesJudge[cmDesAnchor[3]]){
        lpBeginLV=cmDesAnchor[3];
        lpEndLV=C_LEVEL_MAX;
      }else{
        lpBeginLV=cmDesAnchor[2];
        lpEndLV=cmDesAnchor[3];
      }//..?
    }else{
      if(lpFixed>=cmDesJudge[cmDesAnchor[1]]){
        lpBeginLV=cmDesAnchor[1];
        lpEndLV=cmDesAnchor[2];
      }else{
        lpBeginLV=cmDesAnchor[0];
        lpEndLV=cmDesAnchor[1];
      }//..?
    }//..?
    int lpRes;
    for(lpRes=lpBeginLV+1;lpRes<lpEndLV;lpRes++){
      //VcConst.ccPrintln("begin", lpRes-1);
      //VcConst.ccPrintln("end", lpRes);
      if(ZcRangedModel.ccContains(lpFixed,
        cmDesJudge[lpRes-1],
        cmDesJudge[lpRes  ]
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
        cmDesJudge[pxLevel]=ZcRangedModel.ccLimitExclude(pxValue,
          cmDesJudge[0],
          cmDesJudge[pxLevel+1]
        );
      break;
      case C_LEVEL_MASK:
        cmDesJudge[pxLevel]=ZcRangedModel.ccLimitExclude(pxValue,
          cmDesJudge[pxLevel-1],
          cmDesJudge[C_LEVEL_MASK]
        );
      break;
      default:
        VcConst.ccPrintln("so?",pxLevel);
        VcConst.ccPrintln("pre?",cmDesJudge[pxLevel - 1]);
        VcConst.ccPrintln("next?",cmDesJudge[pxLevel+1]);
        VcConst.ccPrintln("so?",ZcRangedModel.ccLimitExclude(pxValue,
          cmDesJudge[pxLevel-1],
          cmDesJudge[pxLevel+1]
        ));
        cmDesJudge[pxLevel]=ZcRangedModel.ccLimitExclude(pxValue,
          cmDesJudge[pxLevel-1],
          cmDesJudge[pxLevel+1]
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
  
  /**
   * @param pxLevel will get masked 
   * @return judge value
   */
  public final int ccGetJudge(int pxLevel){
    return cmDesJudge[pxLevel&C_LEVEL_MASK];
  }//+++
  
  //=== test
  
  /**
   * prints contents to system out.<br>
   * @deprecated for test use
   */
  @Deprecated public final void tstReadup(){
    VcConst.ccPrintln(super.toString(),"judge >>>");
    VcConst.ccPrintln
      (VcNumericUtility.ccPackupDecStringTable(cmDesJudge, 8));
    VcConst.ccPrintln("achor >>>");
    VcConst.ccPrintln
      (VcNumericUtility.ccPackupDecStringTable(cmDesAnchor, 8));
    VcConst.ccPrintln("<<<");
  }//+++
  
}//***eof
