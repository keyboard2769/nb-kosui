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

package kosui.pppswingui;

import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * two three notch part is set horizontal. <br>
 * typically controls a damper may only needs to be on/off manually. <br>
 */
public class ScLampedTwoNotch extends ScLampedThreeNotch {

  /**
   * 
   * @param pxTitle for titled border
   * @param pxLampText will be passed to factory
   */
  public ScLampedTwoNotch(String pxTitle, String pxLampText) {
    super(pxTitle, pxLampText);
    super.removeAll();
    Dimension lpSize = new Dimension(37, 27);
    cmUpSW.setPreferredSize(lpSize);
    cmLowSW.setPreferredSize(lpSize);
    super.add(cmLamp, BorderLayout.PAGE_START);
    super.add(cmUpSW, BorderLayout.LINE_START);
    super.add(cmLowSW, BorderLayout.LINE_END);
    ccSetState(1);
    ccRefresh();
  }//+++

}//***eof
