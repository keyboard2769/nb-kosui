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

import processing.core.PApplet;

/**
 * backing those days we needed to use two timer to build a flicker.<br>
 * now this thing makes me think i am cheating.
 */
public class ZcFlicker extends ZcTimer{
  
  private boolean cmIsCounting;
  
  /**
   * you can consider this thing as a square wave generator
   * @param pxSpan max span as frame count 
   * @param pxDuty 0.0 ~ 1.0 factor as high level proportion
   */
  public ZcFlicker(int pxSpan, float pxDuty){
    super(pxSpan);
    ssInit(pxDuty);
  }//++!
  
  private void ssInit(float pxDuty){
    cmJudge=(int)(
      PApplet.constrain(pxDuty, 0f, 1f) 
      *(float)cmMax
    );
  }//++!
  
  //===
  
  /**
   * @param pxFactor 0.0 ~ 1.0 factor as high level proportion
   */
  public final void ccSetDuty(float pxFactor){
    ssInit(pxFactor);
  }//++<
  
  /**
   * @return as pulse signal
   */
  public final boolean ccAtEdge(){
    return cmValue==cmJudge;
  }//++>
  
  //===
  
  /**
   * {@inheritDoc}
   */
  @Override public void ccAct(boolean pxAct){
    cmIsCounting=pxAct;
    if(pxAct){ccRoll(1);}
    else{ccSetValue(0);}
  }//++~

  /**
   * @return a flag that NOT based on current value
   */
  @Override public boolean ccIsCounting(){
    return cmIsCounting;
  }//++>
  
  /**
   * {@inheritDoc}
   */
  @Override public boolean ccIsUp(){
    return cmValue>cmJudge;
  }//++>
  
  /**
   * the duty rate of flicker will be 50% by default. <br>
   * for implement reason you have to use other method if you need to change.
   * @param pxDivision max span as frame count 
   */
  @Override public void ccSetTime(int pxDivision){
    ccSetRange(ccLimitTimeValue(pxDivision));
    ssInit(0.5f);
  }//+++
  
}//***eof
