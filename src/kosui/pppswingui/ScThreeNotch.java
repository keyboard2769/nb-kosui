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

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * a mimic to a (auto/disable/manual) typed switch. <br>
 * maybe normal desktop UI is not good at this. <br>
 */
public class ScThreeNotch extends JPanel implements ActionListener {

  private final ScThreeNotch self;

  private SiAdditionalListener cmClickedAsWhole;

  private int cmState;

  /**
   * notches
   */
  protected final JButton cmUpSW, cmMidSW, cmLowSW;

  //===
  
  /**
   * 
   * @param pxTitle #
   * @param pxUpName #
   * @param pxMidName #
   * @param pxLowName #
   */
  public ScThreeNotch
    (String pxTitle, String pxUpName, String pxMidName, String pxLowName)
  { super(new GridLayout(0, 1));
    self = this;
    //--
    cmState = 0;
    //--
    cmUpSW = ScFactory.ccMyCommandButton(pxUpName, "upper", 75, 18);
    cmUpSW.addActionListener(self);
    self.add(cmUpSW);
    //--
    cmMidSW = ScFactory.ccMyCommandButton(pxMidName, "middie", 75, 18);
    cmMidSW.addActionListener(self);
    self.add(cmMidSW);
    //--
    cmLowSW = ScFactory.ccMyCommandButton(pxLowName, "lower", 75, 18);
    cmLowSW.addActionListener(self);
    self.add(cmLowSW);
    //--
    ccRefresh();
    if (pxTitle != null) {
      self.setBorder(BorderFactory.createTitledBorder(pxTitle));
    }
    //--
    cmClickedAsWhole = null;
  }//+++

  //===
  
  /**
   * a additional listener implement will be fired 
   * after any notch clicks.
   * @param pxListener #
   */
  public final void ccSetAdditionalListenter
    (SiAdditionalListener pxListener)
  {cmClickedAsWhole = pxListener;}//+++

  /**
   * 
   * @param pxState [1]up..[0]mid..[2]low..
   */
  public final void ccSetState(int pxState)
  {cmState = pxState;}//---
  
  //===
  
  /**
   * reset background of notches
   */
  protected final void ccRefresh() {
    cmUpSW.setBackground(cmState == 1 ? Color.YELLOW : Color.LIGHT_GRAY);
    cmMidSW.setBackground(cmState == 0 ? Color.YELLOW : Color.LIGHT_GRAY);
    cmLowSW.setBackground(cmState == 2 ? Color.YELLOW : Color.LIGHT_GRAY);
  }//+++
  
  //===
  
  /**
   * 
   * @return [1]up..[0]mid..[2]low..
   */
  public final int ccGetState() {
    return cmState;
  }//+++

  //=== interface

  /**
   * {@inheritDoc}
   */
  @Override public void actionPerformed(ActionEvent e) {
    String lpCommand = e.getActionCommand();

    if (lpCommand.equals("upper")) {
      cmState = 1;
    } else if (lpCommand.equals("middie")) {
      cmState = 0;
    } else if (lpCommand.equals("lower")) {
      cmState = 2;
    } else {
      System.err.println(
        ".ScThreeNotch.actionPerformed()::unhandled_command"
        + lpCommand
      );
    }

    ccRefresh();
    if (cmClickedAsWhole != null) {
      cmClickedAsWhole.ccFire();
    }
  }//+++

}//***eof
