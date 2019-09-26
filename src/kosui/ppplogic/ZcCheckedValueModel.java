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

package kosui.ppplogic;

import kosui.ppputil.VcStringUtility;

/**
 * a water tank with water can be considered as a ranged value model.<br>
 * and with some leveler, it becomes checked value model.<br>
 */
public class ZcCheckedValueModel extends ZcRangedValueModel{
  
  private final ZcLevelComparator cmLevel;
  
  /**
   * value will be set to minimum
   * @param pxMin the lower bound
   * @param pxRange the distance between lower bound and higher bound
   */
  public ZcCheckedValueModel(int pxMin, int pxRange){
    super(pxMin, pxRange);
    cmLevel=new ZcLevelComparator(ccGetRange());
  }//+++
  
  /**
   * set current value to judge line of given level.<br>
   * @param pxLevel ##
   */
  public final void ccSetToLevel(int pxLevel){
    cmValue=cmLevel.ccGetJudge(pxLevel);
  }//+++
  
  /**
   * @return compared level
   */
  public final int ccGetCurrentLevel(){
    return cmLevel.ccComparate(cmValue);
  }//+++
  
  /**
   * you have to remember which level stands for what your self
   * @param pxLevel 0-31
   * @return true if current level matches
   */
  public final boolean ccIsLevelAt(int pxLevel) {
    return cmLevel.ccComparate(ccGetRelative())==pxLevel;
  }//+++
  
  /**
   * you have to remember which level stands for what your self
   * @param pxLevel 0-31
   * @return true if current level is higher
   */
  public final boolean ccIsLevelAbove(int pxLevel){
    return cmLevel.ccComparate(ccGetRelative())>=pxLevel;
  }//+++
  
  /**
   * you have to remember which level stands for what your self
   * @param pxLevel 0-31
   * @return true if current level is lower
   */
  public final boolean ccIsLevelBelow(int pxLevel){
    return cmLevel.ccComparate(ccGetRelative())<pxLevel;
  }//+++
  
  //===

  /**
   * {@inheritDoc }
   * @return packed string
   */
  @Override public String toString() {
    StringBuilder lpRes=new StringBuilder();
    lpRes.append(super.toString());
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupParedTag
      ("LV0", cmLevel.ccGetJudge(0)));
    lpRes.append(VcStringUtility.ccPackupParedTag
      ("LV16", cmLevel.ccGetJudge(16)));
    lpRes.append(VcStringUtility.ccPackupParedTag
      ("LV31", cmLevel.ccGetJudge(31)));
    lpRes.append(VcStringUtility.ccPackupParedTag
      ("now", ccGetCurrentLevel()));
    return lpRes.toString();
  }//+++
  
}//***eof
