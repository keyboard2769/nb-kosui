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

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * to me if some swing window is based on a processing frame,
 * this thing should serve as a menu bar. 
 */
public final class ScToolBarWindow extends JWindow {

  private JLabel cmTitle;
  private JToolBar cmBar;
  private JMenuItem cmHelpMI, cmQuitMI;
  private JTextField cmRunPL;

  //===
  
  /**
   * #
   */
  public ScToolBarWindow() {
    super();
  }//+++
  
  //=== initiator

  /**
   * two default menu action will be sent to the listener passed: <br>
   *   - "--toolbar-help": from "help" menu item <br>
   *   - "--toolbar-quit": from "quit" menu item <br>
   * @param pxTitle will be show at head
   * @param pxListener action listener
   */
  public final
  void ccInit(String pxTitle, ActionListener pxListener){ 
    
    //-- selfie
    final ScToolBarWindow lpWindow = this;
  
    //-- pre
    Container lpContentPane = lpWindow.getContentPane();
    if(lpContentPane instanceof JPanel){
      ((JPanel)lpContentPane).setBorder(BorderFactory.createEtchedBorder());
    }//..?

    //-- public
    cmBar = ScFactory.ccCreateStuckedToolBar();

    cmHelpMI = new JMenuItem("help");
    cmHelpMI.setActionCommand("--toolbar-help");
    cmHelpMI.addActionListener(pxListener);

    cmQuitMI = new JMenuItem("quit");
    cmQuitMI.setActionCommand("--toolbar-quit");
    cmQuitMI.addActionListener(pxListener);

    cmRunPL = ScFactory.ccCreateTextLamp("Run", 30, 30);

    //-- local
    JPopupMenu lpPop = new JPopupMenu();
    lpPop.add(cmHelpMI);
    lpPop.add(cmQuitMI);
    ScPopupAdaptor lpPopper=new ScPopupAdaptor(lpPop,null);
    ScDragAnchor lpDragger = new ScDragAnchor(lpWindow);
    cmTitle = new JLabel(pxTitle);
    cmTitle.addMouseListener(lpPopper);
    cmTitle.addMouseListener(lpDragger);
    cmTitle.addMouseMotionListener(lpDragger);
    
    //-- setup
    cmBar.add(cmTitle);
    cmBar.add(cmRunPL);
    cmBar.addSeparator();

    lpWindow.add(cmBar);

  }//++!

  /**
   * does some packing job.
   * need to be called after adding components.
   * @param pxX of location
   * @param pxY of location
   */
  public final void ccFinish(int pxX, int pxY) {
    pack();
    setLocation(pxX, pxY);
    setVisible(true);
    setAlwaysOnTop(true);
  }//++!
  
  //=== 
  
  /**
   * 
   * @param pxComponent will be add to the end of bar
   */
  public final void ccAdd(JComponent pxComponent) {
    cmBar.add(pxComponent);
  }//+++

  /**
   * for some reason don't pass a JSeparator to add directly. <br>
   * if you wanna separate things, use this. <br>
   */
  public final void ccAddSeparator() {
    cmBar.addSeparator();
  }//+++
  
  //===
  
  /**
   * wrapper for JLabel::setText()
   * @param pxTitle #
   */
  public final void ccSetTitle(String pxTitle){
    cmTitle.setText(pxTitle);
  }//+++

  /**
   * in case you need show some non English word for these menu items. <br>
   * won't check for null or emptiness, pass something meaningful. <br>
   * @param pxQuit #
   * @param pxHelp #
   */
  public final void ccSetMenuItemText
    (String pxQuit, String pxHelp)
  { cmQuitMI.setText(pxQuit);
    cmHelpMI.setText(pxHelp);
  }//+++
  
  /**
   * suppsedly this should get linked to a roller shows the 
   * application is running.
   * @param pxStatus defaultly true for dark red and false for dark gray
   */
  public final void ccSetRunLampStatus(boolean pxStatus) {
    if (SwingUtilities.isEventDispatchThread()){
      cmRunPL.setBackground(pxStatus?ScConst.C_DARK_RED:ScConst.C_DARK_GRAY);
    }//..?
  }//+++
  
}//***eof
