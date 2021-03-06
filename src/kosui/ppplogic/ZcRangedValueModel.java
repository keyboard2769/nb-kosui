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

import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;

/**
 * i made a mistake.<br>
 * cylinder is not a ranged model. cylinder is a range value model.<br>
 */
public class ZcRangedValueModel extends ZcRangedModel{
  
  /**
   * this stuff moves
   */
  protected int cmValue;
  
  /**
   * value will be set to minimum.<br>
   * @param pxMin the lower bound
   * @param pxRange the distance between lower bound and higher bound
   */
  public ZcRangedValueModel(int pxMin, int pxRange){
    super(pxMin, pxRange);cmValue=pxMin;
  }//++!
  
  //===
    
  /**
   * @param pxValue will get limited
   */
  public final void ccSetValue(int pxValue){
    cmValue=ccLimit(pxValue);
  }//+++
  
  /**
   * <pre>
   * pretend min is 50, max is 100, and say, 0.5f is passed,
   *   than value is gonna be like 75.
   * </pre>
   * @param pxZeroToOne does NOT check, don t get cross.
   */
  public final void ccSetValue(float pxZeroToOne){
    ccSetValue((int)(pxZeroToOne*(cmMax-cmMin))+cmMin);
  }//+++
  
  /**
   * it just happen
   */
  public final void ccSetToMinimum(){
    cmValue=cmMin;
  }//+++
  
  /**
   * it just happen
   */
  public final void ccSetToMaximum(){
    cmValue=cmMax;
  }//+++
  
  /**
   * stuck its self at range bounds via ccLimit()<br>
   * @param pxOffset #
   */
  public final void ccShift(int pxOffset){
    cmValue=ccLimit(cmValue+pxOffset);
  }//+++
  
  /**
   * starts over again after across bounds via ccWarp()<br>
   * @param pxOffset #
   */
  public final void ccRoll(int pxOffset){
    cmValue=ccWarp(cmValue+pxOffset);
  }//+++
  
  //===
  
  /**
   * @return actual value
   */
  public final int ccGetValue(){
    return cmValue;
  }//+++
  
  /**
   * @return value - min
   */
  public final int ccGetRelative(){
    return cmValue-cmMin;
  }//+++
  
  /**
   * aliasing local method to VcNumericUtility.ccFloat 
   * and you PAY for those overheads.<br>
   * @return supposedly 0-1f aka (current - min) / range
   */
  public final float ccGetProportion(){
    return VcNumericUtility.ccFloat(ccGetRelative(), ccGetRange());
  }//+++
  
  /**
   * @param pxGivenValue could be anything
   * @return equals
   */
  public final boolean ccIsAt(int pxGivenValue){
    return cmValue==pxGivenValue;
  }//+++
  
  /**
   * @param pxGivenValue could be anything
   * @return inclusive
   */
  public final boolean ccIsAbove(int pxGivenValue){
    return cmValue>=pxGivenValue;
  }//+++
  
  /**
   * @param pxGivenValue could be anything
   * @return exclusive
   */
  public final boolean ccIsBelow(int pxGivenValue){
    return cmValue<pxGivenValue;
  }//+++
  
  /**
   * @param pxLowBound must be smaller than the upper one
   * @param pxUpBound must be bigger than the lower one
   * @return inclusive
   */
  public final boolean ccIsWith(int pxLowBound, int pxUpBound){
    if(pxLowBound>=pxUpBound){return false;}
    return cmValue>=pxLowBound&&cmValue<=pxUpBound;
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
    lpRes.append(VcStringUtility.ccPackupPairedTag("val", cmValue));
    return lpRes.toString();
  }//+++
  
}//***eof
