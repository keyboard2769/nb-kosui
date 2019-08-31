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

import java.util.ArrayList;

/**
 * a water tank with water can be considered as a ranged value model.<br>
 * and with some leveler, it becomes checked value model.<br>
 */
public class ZcCheckedValueModel extends ZcRangedValueModel{
  
  private final ArrayList<ZcRangedModel> cmCheckList;
  
  /**
   * value will be set to minimum
   * @param pxMin the lower bound
   * @param pxRange the distance between lower bound and higher bound
   */
  public ZcCheckedValueModel(int pxMin, int pxRange)
    {super(pxMin, pxRange);cmCheckList=new ArrayList();}//+++
  
  /**
   * 
   * @param pxMin for the checker
   * @param pxRange for the checker
   */
  public final void ccAddChecker(int pxMin, int pxRange)
    {cmCheckList.add(new ZcRangedModel(pxMin, pxRange));}//+++
  
  /**
   * you have to remember which index stands for what your self
   * @param pxIndex #
   * @return true if current value is in range of the indexed checker
   */
  public final boolean ccCheckFor(int pxIndex) {
    ZcRangedModel lpChecker=ccGetChecker(pxIndex);
    if(lpChecker==null){return false;}
    return lpChecker.ccContains(cmValue);
  }//+++
  
  /**
   * use this to reset the position of checker 
   * @param pxIndex #
   * @return #
   */
  public final ZcRangedModel ccGetChecker(int pxIndex){
    if(cmCheckList.isEmpty()){return null;}
    if(pxIndex>cmCheckList.size()){return null;}
    return cmCheckList.get(pxIndex&0xFF);
  }//+++
  
}//***eof
