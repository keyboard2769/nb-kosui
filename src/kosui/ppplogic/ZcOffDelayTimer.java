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
 * i felt it is quite hard to understand this kind of timer. <br>
 * they turns them self on immediately. <br>
 */
public class ZcOffDelayTimer extends ZcTimer{
  
  /**
   * @param pxSpan #
   */
  public ZcOffDelayTimer(int pxSpan) {
    super(pxSpan);
    ccInit();
  }//++!
  
  private void ccInit(){
    cmJudge=1;
  }//++!
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccAct(boolean pxAct) {
    if(pxAct){
      ccSetValue(cmMax);
    }else{
      ccShift(-1);
    }//..?
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public boolean ccIsUp(){
    return cmValue>=cmJudge;
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public boolean ccIsCounting(){
    return cmValue!=cmMax;
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccSetTime(int pxDivision){
    ccSetRange(ccLimitTimeValue(pxDivision));
    ccInit();
  }//+++
  
}//***eof
