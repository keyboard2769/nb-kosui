/*
 * Copyright (C) 2018 Key Parker from K.I.C
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

/**
 * a bit flips its self every time it get energized. <br>
 * we used to call this "one-button self-holding circuit", 
 *   AKA "the holder". <br>
 */
public class ZcHookFlicker {
  
  private boolean cmHolder=false;
  private byte cmPulser=0;
  
  //===
  
  /**
   * @param pxInitStatus could be anything
   */
  public ZcHookFlicker(boolean pxInitStatus){
    cmHolder=pxInitStatus;
    cmPulser=0;
  }//++!
  
  /**
   * the status is OFF by default
   */
  public ZcHookFlicker(){
    this(false);
  }//++!
  
  //===
  
  /**
   * 
   * @param pxTrigger #
   * @return the bit
   */
  public final boolean ccHook(boolean pxTrigger){
    if(pxTrigger){cmPulser+=cmPulser<3?1:0;}else{cmPulser=0;}
    if(cmPulser==1){cmHolder=!cmHolder;}
    return cmHolder;
  }//++~
  
  /**
   * 
   * @param pxTrigger #
   * @param pxLock #
   * @return the bit
   */
  public final boolean ccHook(boolean pxTrigger, boolean pxLock){
    if(pxLock){cmHolder=false;return cmHolder;}
    else{return ccHook(pxTrigger);}
  }//++~
  
  //===
  
  /**
   * 
   * @param pxStatus #
   */
  public final void ccSetIsHooked(boolean pxStatus){
    cmHolder=pxStatus;
  }//++<
  
  /**
   * 
   * @return #
   */
  public final boolean ccIsHooked(){
    return cmHolder;
  }//++>
  
}//***eof
