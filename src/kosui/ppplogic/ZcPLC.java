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

import java.util.LinkedList;
import java.util.List;

/**
 * simulate a PLC scan operation.<br>
 * if you can find, see how FUJI-ELECTRONICS PROGRAMMABLE LOGIC CONTROLLER
 * SERIES SX:NP1PM-256E programs him self.<br>
 */
public final class ZcPLC{

  private static final List<ZiTask> O_LIST_OF_TASK = new LinkedList<ZiTask>();
  
  /**
   * supposedly task is a piece of code mimics PLC movement 
   * inside a subclass from your sketch.
   * @param pxTask #
   */
  public static final void ccAddTask(ZiTask pxTask) {
    if(pxTask==null){return;}
    if(O_LIST_OF_TASK.contains(pxTask)){return;}
    O_LIST_OF_TASK.add(pxTask);
  }//+++
  
  //===
  
  /**
   * supposedly should be called from draw()
   */
  public static final void ccRun() {
    for (ZiTask it : O_LIST_OF_TASK) {
      it.ccScan();
      it.ccSimulate();
    }//..?
  }//+++
  
  //=== bit logic
  
  /**
   * LoaD
   * @param pxA does not check for null
   * @return considered on or off
   */
  public static final boolean ccLD(ZiBitty pxA){
    return pxA.ccGetBit();
  }//+++
  
  /**
   * LoaD Inverted
   * @param pxA does not check for null
   * @return considered on or off
   */
  public static final boolean ccLDI(ZiBitty pxA){
    return !pxA.ccGetBit();
  }//+++
  
  /**
   * @param pxA does not check for null
   * @param pxB does not check for null
   * @return considered on or off
   */
  public static final boolean ccAND(ZiBitty pxA, ZiBitty pxB){
    return pxA.ccGetBit()&&pxB.ccGetBit();
  }//+++
  
  /**
   * @param pxA does not check for null
   * @param pxB does not check for null
   * @param pxC does not check for null
   * @return considered on or off
   */
  public static final boolean ccAND(ZiBitty pxA, ZiBitty pxB, ZiBitty pxC){
    return pxA.ccGetBit()&&pxB.ccGetBit()&&pxC.ccGetBit();
  }//+++
  
  /**
   * @param pxA does not check for null
   * @param pxB does not check for null
   * @return considered on or off
   */
  public static final boolean ccOR(ZiBitty pxA, ZiBitty pxB){
    return pxA.ccGetBit()||pxB.ccGetBit();
  }//+++
  
  /**
   * @param pxA
   * @param pxB
   * @param pxC
   * @return considered on or off
   */
  public static final boolean ccOR(ZiBitty pxA, ZiBitty pxB, ZiBitty pxC){
    return pxA.ccGetBit()||pxB.ccGetBit()||pxC.ccGetBit();
  }//+++
  
  //[todo]::public static final boolean ccXOR(...){a^b}
  //[todo]::public static final boolean ccXOR(...){a^b^c}
  
  //=== condition aliasing
  
  /**
   * @param pxV could be anything
   * @return could be anything
   */
  public static final 
  boolean not(boolean pxV){
    return !pxV;
  }//+++
  
  /**
   * @param pxA could be anything
   * @param pxB could be anything
   * @return could be anything
   */
  public static final
  boolean and(boolean pxA, boolean pxB){
    return pxA&&pxB;
  }//+++
  
  /**
   * @param pxA could be anything
   * @param pxB could be anything
   * @return could be anything
   */
  public static final
  boolean or(boolean pxA, boolean pxB){
    return pxA||pxB;
  }//+++
  
  /**
   * @param pxCA could be anything
   * @param pxVA could be anything
   * @param pxVB could be anything
   * @return could be anything
   */
  public static final
  boolean sel(boolean pxCA, boolean pxVA, boolean pxVB){
    return pxCA ? pxVA : pxVB;
  }//+++
  
  /**
   * @param pxCA could be anything
   * @param pxVA could be anything
   * @param pxVB could be anything
   * @return could be anything
   */
  public static final
  int sel(boolean pxCA, int pxVA, int pxVB){
    return pxCA ? pxVA : pxVB;
  }//+++
  
  /**
   * @param pxCA could be anything
   * @param pxVA could be anything
   * @param pxVB could be anything
   * @return could be anything
   */
  public static final
  float sel(boolean pxCA, float pxVA, float pxVB){
    return pxCA ? pxVA : pxVB;
  }//+++
  
  /**
   * @param pxCA could be anything
   * @param pxCB could be anything
   * @param pxVA could be anything
   * @param pxVB could be anything
   * @return could be anything
   */
  public static final
  boolean gate(boolean pxCA, boolean pxVA, boolean pxCB, boolean pxVB){
    return
       pxCA ? pxVA 
     :(pxCB ? pxVB : false);
  }//+++
  
}//***eof
