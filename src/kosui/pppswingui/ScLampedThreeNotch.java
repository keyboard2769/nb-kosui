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
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextField;

/**
 * the three notch part is set horizontal. <br>
 * typically controls a damper may has a auto mode. <br>
 */
public class ScLampedThreeNotch extends ScThreeNotch {

  private Color cmLampColor;

  /**
   * lamp
   */
  protected final JTextField cmLamp;

  //===
  
  /**
   * 
   * @param pxTitle for titled border
   * @param pxLampText will be passed to factory
   */
  public ScLampedThreeNotch(String pxTitle, String pxLampText) {
    super(pxTitle, "x", "#", "o");
    cmLampColor = ScFactory.DARK_GREEN;
    cmLamp = ScFactory.ccMyTextLamp(pxLampText, 75, 27);
    Dimension lpSize = new Dimension(25, 27);
    cmUpSW.setPreferredSize(lpSize);
    cmMidSW.setPreferredSize(lpSize);
    cmLowSW.setPreferredSize(lpSize);
    super.removeAll();
    super.setLayout(new BorderLayout());
    super.add(cmLamp, BorderLayout.PAGE_START);
    super.add(cmUpSW, BorderLayout.LINE_START);
    super.add(cmMidSW, BorderLayout.CENTER);
    super.add(cmLowSW, BorderLayout.LINE_END);
    ccRefresh();
  }

  //===
  
  /**
   * 
   * @param pxColor the color of if considered activated
   */
  public final void ccSetLampColor(Color pxColor) {
    cmLampColor = pxColor;
  }//+++

  /**
   * on color is settable. <br>
   * off color is always dark gray. <br>
   * @param pxStatus activated or not
   */
  public final void ccSetLampStatus(boolean pxStatus) {
    cmLamp.setBackground(pxStatus ? cmLampColor : Color.DARK_GRAY);
  }//+++

}//***eof
