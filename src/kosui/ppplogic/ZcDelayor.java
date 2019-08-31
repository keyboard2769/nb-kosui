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
 * consider this thing as a anti chattering capacitor. <br>
 * they also use this on some level checker. <br>
 */
public class ZcDelayor extends ZcTimer{
  
  /**
   * full-span-time = on-delay + off-delay
   * @param pxOnDelay #
   * @param pxOffDelay #
   */
  public ZcDelayor(int pxOnDelay, int pxOffDelay){
    super(0);
    ccInit(pxOnDelay, pxOffDelay);
  }//++!

  private void ccInit(int pxOnDelay, int pxOffDelay){
    ccSetRange(pxOnDelay+pxOffDelay);
    cmJudge=pxOnDelay;
  }//++!

  //===
  
  /**
   * full-span-time = on-delay + off-delay
   * @param pxOnDelay #
   * @param pxOffDelay #
   */
  public final void ccSetDelay(int pxOnDelay, int pxOffDelay){
    ccInit(pxOnDelay, pxOffDelay);
  }//+++
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public void ccAct(boolean pxAct){
    ccShift(pxAct?1:-1);
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public boolean ccIsUp(){
    return cmValue>cmJudge;
  }//+++

  /**
   * the delay value will be 50% of all by default. <br>
   * for implement reason you have to use other method if you need to change.
   * @param pxDiv #
   */
  @Override public void ccSetTime(int pxDiv){
    int lpDiv=ccLimitTimeValue(pxDiv);
    ccInit(lpDiv/2,lpDiv/2);
  }//+++
  
}//***eof
