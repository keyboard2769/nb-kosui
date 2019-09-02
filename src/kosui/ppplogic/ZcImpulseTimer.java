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
 * to send a fixed width square wave while triggered. <br>
 * a bit like the TP instruction in my logic controller. <br>
 */
public class ZcImpulseTimer extends ZcTimer{
  
  private final ZcPulser cmPulser;

  /**
   * 
   * @param pxSpan #
   */
  public ZcImpulseTimer(int pxSpan){
    super(pxSpan);
    cmPulser=new ZcPulser();
  }//++!
  
  //===
  
  /**
   * {@inheritDoc}
   */
  @Override public void ccAct(boolean pxAct) {
    if(cmPulser.ccUpPulse(pxAct)){ccSetValue(cmMax);}
    ccShift(-1);
  }//+++

  /**
   * {@inheritDoc}
   */
  @Override public boolean ccIsUp(){
    return cmValue > 0;
  }//+++

  /**
   * {@inheritDoc}
   */
  @Override public boolean ccIsCounting(){
    return cmValue > 0;
  }//+++
  
  /**
   * {@inheritDoc}
   */
  @Override public void ccSetTime(int pxDivision){
    ccSetRange(ccLimitTimeValue(pxDivision));
  }//+++
  
}//***eof
