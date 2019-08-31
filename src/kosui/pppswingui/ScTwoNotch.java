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

import java.awt.Dimension;

/**
 * a mimic to a (off/on) typed switch. <br>
 * maybe normal desktop UI is not good at this. <br>
 */
public class ScTwoNotch extends ScThreeNotch {

  /**
   * 
   * @param pxTitle for the border
   * @param pxUpName #
   * @param pxLowName #
   */
  public ScTwoNotch(String pxTitle, String pxUpName, String pxLowName) {
    super(pxTitle, pxUpName, "<nc/>", pxLowName);
    super.removeAll();
    Dimension lpSize = new Dimension(75, 27);
    cmUpSW.setPreferredSize(lpSize);
    cmLowSW.setPreferredSize(lpSize);
    super.add(cmUpSW);
    super.add(cmLowSW);
    ccSetState(1);
    ccRefresh();
  }//+++

}//***eof
