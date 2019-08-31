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
 * i guess this is actually easier to understand than the timer version.<br>
 * it does wait. <hr>
 */
public class ZcOffDelayPulser extends ZcOffDelayTimer{
  
  /**
   * 
   * @param pxSpan #
   */
  public ZcOffDelayPulser(int pxSpan) {
    super(pxSpan);
  }//+++
    
  /**
   * {@inheritDoc }
   */
  @Override public boolean ccIsUp() {
    return cmValue==cmJudge;
  }//+++
  
}//***eof
