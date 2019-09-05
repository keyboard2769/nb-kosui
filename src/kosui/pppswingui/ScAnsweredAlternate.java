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

package kosui.pppswingui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import kosui.ppputil.VcConst;

/**
 * at first there was lot of notch button with lamp,<br>
 * than i figured out that swing is just not for that kind of job.<br>
 */
public final class ScAnsweredAlternate extends JPanel{
  
  private final JTextField cmPL = new JTextField();
  
  private final JToggleButton cmSW = new JToggleButton();
  
  /**
   * #
   * @param pxTitle must have something or we set something for you
   * @param pxLamp must have something or we set something for you
   * @param pxToggle must have something or we set something for you
   */
  public ScAnsweredAlternate(String pxTitle, String pxLamp, String pxToggle){
    super(new GridLayout(2, 1));
    if(pxTitle!=null){
      setBorder(BorderFactory.createTitledBorder(
        pxTitle.isEmpty()?"<?>":pxTitle
      ));
    }//..?
    cmPL.setText(VcConst.ccIsValidString(pxLamp)?pxLamp:"<?>");
    cmSW.setText(VcConst.ccIsValidString(pxToggle)?pxToggle:"<?>");
    ScFactory.ccSetupTextLamp(cmPL,-1,-1);
    ScFactory.ccSetupCommandToggler(cmSW, -1, -1);
    add(cmPL);
    add(cmSW);
  }//+++ 
  
  //===
  
  /**
   * please note that the lamp color is hard coded.<br>
   * @param pxStatus #
   */
  synchronized public final void ccSetActivated(boolean pxStatus){
    ScConst.ccActivateTextLamp(cmPL, pxStatus);
  }//+++
  
  /**
   * for action register use.<br>
   * not supposed to get called out from EDT but this does not check.<br>
   * @return #
   */
  public final JToggleButton ccGetToggler(){
    return cmSW;
  }//+++
  
  /**
   * #
   * @return JToggleButton.isSelected()
   */
  synchronized public final boolean ccIsPushed(){
    return cmSW.isSelected();
  }//+++
  
}//***eof
