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

package kosui.pppswingui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * a panel applied with GridBagLayout. <br>
 * since a constraints is needed i cant just make this in factory. <br>
 */
public class ScBagPanel extends JPanel{
  
  private final GridBagConstraints cmConstraints;

  /**
   * default padding and insets is 2 pix. <br>
   * default weight is 0.5d. <br>
   * default fill mode is HORIZONTAL and anchor is NORTH. <br>
   */
  public ScBagPanel(){
    super(new GridBagLayout());
    cmConstraints=new GridBagConstraints();
    cmConstraints.weightx=0.5d;
    cmConstraints.weighty=0.5d;
    cmConstraints.fill=GridBagConstraints.HORIZONTAL;
    cmConstraints.anchor=GridBagConstraints.NORTH;
    cmConstraints.insets.set(2, 2, 2, 2);
    ssSetupPad(2, 2);
  }//++!
  
  /**
   * same as default constructor but adds a titled border
   * @param pxTitle #
   */
  public ScBagPanel(String pxTitle){
    this();
    setBorder(BorderFactory.createTitledBorder(pxTitle));
  }//++!
  
  //===
  
  private void ssSetupGrid(int pxX, int pxY, int pxW, int pxH){
    cmConstraints.gridx=pxX;
    cmConstraints.gridy=pxY;
    cmConstraints.gridwidth=pxW;
    cmConstraints.gridheight=pxH;
  }//+++
  
  private void ssSetupPad(int pxPadX, int pxPadH){
    cmConstraints.ipadx=pxPadX;
    cmConstraints.ipady=pxPadH;
  }//+++
  
  //===
  
  /**
   * 
   * @param pxTarget does not check for null
   * @param pxX does not check for minus value or over value
   * @param pxY does not check for minus value or over value
   * @param pxW does not check for minus value or over value
   * @param pxH does not check for minus value or over value
   */
  public final void ccAdd(
    JComponent pxTarget,
    int pxX, int pxY, int pxW, int pxH
  ){
    ssSetupGrid(pxX, pxY, pxW, pxH);
    add(pxTarget, cmConstraints);
  }//+++
  
  /**
   * 
   * @param pxTarget does not check for null
   * @param pxX does not check for minus value or over value
   * @param pxY does not check for minus value or over value
   * @param pxW does not check for minus value or over value
   * @param pxH does not check for minus value or over value
   * @param pxPadW does not check for minus value or over value
   * @param pxPadH does not check for minus value or over value
   */
  public final void ccAdd(
    JComponent pxTarget,
    int pxX, int pxY, int pxW, int pxH,
    int pxPadW, int pxPadH
  ){
    ssSetupGrid(pxX, pxY, pxW, pxH);
    ssSetupPad(pxPadW, pxPadH);
    add(pxTarget, cmConstraints);
    ssSetupPad(2, 2);
  }//+++
  
}//***eof
