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

import kosui.ppputil.VcNumericUtility;

/**
 * after reading this a kid who didn't know about bit mask 
 * thought it is a black magic.<br>
 * it is too essential to me that i didn't even thought about making it a class
 * until i end up with a bunch of volatile integer.<br>
 */
public class ZcRoller {
  
  private volatile int cmVal;
  private final int cmMask;
  
  /**
   * @param pxMask supposedly THE frame rate -1 
   * @param pxInitValue supposedly as frame count
   */
  public ZcRoller(int pxMask, int pxInitValue) {
    cmMask=VcNumericUtility.ccToPowerOfTwo(pxMask&0x7FFF)-1;
    cmVal=pxInitValue;
  }//++!
  
  /**
   * initiated value is 1.<br>
   * @param pxMask supposedly THE frame rate -1
   */
  public ZcRoller(int pxMask){
    this(pxMask,1);
  }//++!
  
  /**
   * mask is 15.<br>
   * initiated value is 0.<br>
   */
  public ZcRoller(){
    cmMask=15;
    cmVal=0;
  }//++!
  
  //===
  
  /**
   * adding and masking.<br>
   * supposedly to get called from a scan loop.<br>
   */
  public final void ccRoll(){
    cmVal++;
    cmVal&=cmMask;
  }//++~
  
  //===
  
  /**
   * set current value to zero
   */
  public final void ccReset(){
    cmVal=0;
  }//++>
  
  /**
   * @return currently at 
   */
  public final int ccGetValue(){
    return cmVal;
  }//++>
  
  /**
   * @param pxVal does not check but supposedly should be smaller than mask
   * @return if equals
   */
  public final boolean ccIsAt(int pxVal){
    return cmVal==pxVal;
  }//++>
  
  /**
   * @param pxVal does not check but supposedly should be smaller than mask
   * @return if greater than
   */
  public final boolean ccIsAbove(int pxVal){
    return cmVal>=pxVal;
  }//++>
  
  /**
   * @param pxVal does not check but supposedly should be smaller than mask
   * @return if lesser than
   */
  public final boolean ccIsUnder(int pxVal){
    return cmVal<=pxVal;
  }//++>
  
  /**
   * @param pxMod does not check but supposedly should be smaller than mask
   * @param pxComp does not check but supposedly should be smaller than mask
   * @return val % mod == comp
   */
  public final boolean ccIsAcrossAt(int pxMod, int pxComp){
    return (cmVal % pxMod) == pxComp;
  }//++>
  
  /**
   * @param pxMod does not check but supposedly should be smaller than mask
   * @param pxComp does not check but supposedly should be smaller than mask
   * @return val % mod >= comp
   */
  public final boolean ccIsAcrossAbove(int pxMod, int pxComp){
    return (cmVal % pxMod) >= pxComp;
  }//++>
  
  /**
   * @param pxMod does not check but supposedly should be smaller than mask
   * @param pxComp does not check but supposedly should be smaller than mask
   * @return val % mod <= comp
   */
  public final boolean ccIsAcrossUnder(int pxMod, int pxComp){
    return (cmVal % pxMod) <= pxComp;
  }//++>
  
  //===

  /**
   * {@inheritDoc}
   */
  @Override public String toString() {
    return String.format(
      "%s@%08x$[v:%4d][m:%04d]",
      ZcRoller.class.getName(),this.hashCode(),
      cmVal,cmMask
    );
  }//+++
  
}//***eof
