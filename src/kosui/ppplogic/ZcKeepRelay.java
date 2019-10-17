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

/**
 * a keep relay keeps its self, my supervisor used to think 
 * this is the real core of our department.<br>
 * by the way, he didn't make it long, he ends up taking phone call at
 * the Customer Support Center.<br> 
 */
public class ZcKeepRelay extends ZcBit{
  
  /**
   * make it on.<br>
   */
  public final void ccSet(){
    cmBit=true;
  }//++<
  
  /**
   * make it on in the instruction way.<br>
   * @param pxCondition ##
   */
  public final void ccSet(boolean pxCondition){
    if(pxCondition){ccSet();}
  }//++<
  
  /**
   * make it off.<br>
   */
  public final void ccReset(){
    cmBit=false;
  }//++<
  
  /**
   * make it off in the instruction way.<br>
   * @param pxCondition ##
   */
  public final void ccReset(boolean pxCondition){
    if(pxCondition){ccReset();}
  }//++<
  
  /**
   * feel the magic of self-engaging self-holding circuit.<br>
   * @param pxSet ##
   * @param pxReset ##
   */
  public final void ccTakeInput(boolean pxSet, boolean pxReset){
    cmBit=(!pxReset)&&(cmBit||pxSet);
  }//++<

}//***eof
