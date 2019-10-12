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
 * this is pretty useful actually and i wonder why we did not make it 
 * all these time.<br>
 * maybe i just felt use a combination of a separated pulser and timer 
 * was not tedious enough for it.<br>
 */
public class ZcImpulsivePulser extends ZcImpulsiveTimer{
  
  /**
   * @param pxSpan frame
   */
  public ZcImpulsivePulser(int pxSpan) {
    super(pxSpan);
  }//++!
  
  /**
   * {@inheritDoc }
   */
  @Override public boolean ccIsUp() {
    return cmValue == 1;
  }//+++
  
}//***eof
