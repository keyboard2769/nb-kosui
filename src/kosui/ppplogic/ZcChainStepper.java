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
 * turns on a bunch of bits in sequency. <br>
 * as the stepper is a mimic to a state machine, 
 *   this chain stepper is just a alias to chained timer. <br>
 */
public class ZcChainStepper extends ZcStepper{
  
  private final int cmMax;
  
  /**
   * 
   * @param pxMax amount of bits. 
   */
  public ZcChainStepper(int pxMax){
    super();
    cmMax=pxMax;
  }//++!
  
  /**
   * 
   * @param pxAct supposedly, a pulse signal
   */
  public final void ccStep(boolean pxAct){
    if(pxAct && (cmStage<cmMax)){cmStage++;}
  }//+++
  
  /**
   * all bits will be set to off. 
   * @param pxAct #
   */
  public final void ccStop(boolean pxAct){
    if(pxAct){cmStage=0;}
  }//+++
  
  /**
   * 
   * @return if any bits is not off. 
   */
  public final boolean ccIsStepping(){
    return cmStage>0;
  }//+++

  /**
   * 
   * @param pxStage #
   * @return #
   */
  @Override public boolean ccIsAt(int pxStage){
    return cmStage>=pxStage;
  }//+++
  
}//***eof
