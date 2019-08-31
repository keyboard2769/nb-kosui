/*
 * Copyright (C) 2019 Key Parker
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
 * sends a pulse at the starting of ON square. <br>
 * we used to build this by a self engaging TP timer. <br>
 */
public class ZcPulseFlicker extends ZcFlicker{
  
  /**
   * 
   * @param pxSpan consider this as the wavelength
   */
  public ZcPulseFlicker(int pxSpan){
    super(pxSpan,0.5f);
  }//++!
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public boolean ccIsUp()
    {return cmValue==cmJudge;}//+++

}//***eof
