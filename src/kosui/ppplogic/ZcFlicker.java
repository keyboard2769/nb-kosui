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

/**
 * backing those days we needed to use two timer to build a flicker.<br>
 * now this thing makes me think i am cheating.
 */
public class ZcFlicker extends ZcTimer{
  
  
  /**
   * you can consider this thing as a square wave generator
   * @param pxSpan consider this as the wavelength
   * @param pxDuty high-level time percentage, pass value for 0.0-1.0
   */
  public ZcFlicker(int pxSpan, float pxDuty){
    super(pxSpan);
    ssInit(pxDuty);
  }//++!
  
  private void ssInit(float pxDuty){
    cmJudge=(int)(pxDuty*(float)cmMax);
  }//++!
  
  //===
  
  /**
   * 
   * @param pxDuty #
   */
  public final void ccSetDuty(float pxDuty){
    ssInit(pxDuty);
  }//+++
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccAct(boolean pxAct){
    if(pxAct){ccRoll(1);}
    else{ccSetValue(0);}
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public boolean ccIsUp()
    {return cmValue>cmJudge;}//+++
  
  /**
   * the duty rate of flicker will be 50% by default. <br>
   * for implement reason you have to use other method if you need to change.
   * @param pxDiv #
   */
  @Override public void ccSetTime(int pxDiv){
    ccSetRange(ccLimitTimeValue(pxDiv));
    ssInit(0.5f);
  }//+++
  
}//***eof
