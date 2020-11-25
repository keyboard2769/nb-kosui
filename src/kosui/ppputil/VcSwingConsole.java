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

package kosui.ppputil;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import kosui.ppplocalui.EcConst;
import kosui.pppswingui.ScConst;
import kosui.pppswingui.ScStoker;

/**
 * this was my template of swing application, i thought this is the best way 
 * for triggering and watching.<br>
 * for normal static building, i think you should avoid using this 
 * manager class directly.<br>
 */
public final class VcSwingConsole{
  
  private static final String C_INIT_TEXT
    = "kosui standby::"+VcConst.C_V_NEWLINE
    + ".build $ "+EcConst.ccGetLastLeavingStamp()+VcConst.C_V_NEWLINE
    + ".with $ "+VcConst.C_V_OS+VcConst.C_V_NEWLINE
    + ".at $ "+VcConst.C_V_PWD+VcConst.C_V_NEWLINE;
  
  //===
  
  private static boolean pbIsInitiated=false;
  
  private static final JPanel O_PANEL
    = new JPanel(new BorderLayout());
  
  private static final ScStoker O_STOCKER 
    = new ScStoker(C_INIT_TEXT, -1, -1);
  
  private static final JTextField O_FIELD
   = new JTextField("");
  
  private static final KeyListener O_LISTENER = new KeyListener() {
    @Override public void keyTyped(KeyEvent ke){}//+++
    @Override public void keyPressed(KeyEvent ke){}//+++
    @Override public void keyReleased(KeyEvent ke){
      int lpCharCode=(int)ke.getKeyChar();
      switch(lpCharCode){
        case 0x0A:
          ccWriteln(VcSwingCoordinator.ccExecute(O_FIELD.getText()));
          O_FIELD.setText("");
        break;
        default:break;
      }//..?
    }//+++
  };//***
  
  public static final void ccInit(){
    if(pbIsInitiated){return;}
    if(!ScConst.ccIsEDT()){return;}
    O_PANEL.setBorder(BorderFactory.createEtchedBorder());
    O_FIELD.addKeyListener(O_LISTENER);
    O_PANEL.add(O_STOCKER,BorderLayout.CENTER);
    O_PANEL.add(O_FIELD,BorderLayout.PAGE_END);
    pbIsInitiated=true;
  }//..!
  
  //===
  
  /**
   * alias for ScStoker::ccPrintln().<br>
   * might get passed to JTextArea::append() eventually.<br>
   * @param pxTag must have something
   * @param pxVal can be any thing
   */
  public static final void ccWriteln(String pxTag, Object pxVal){
    O_STOCKER.ccWriteln(pxTag, pxVal);
  }//+++
  
  /**
   * alias for ScStoker::ccPrintln().<br>
   * might get passed to JTextArea::append() eventually.<br>
   * @param pxLine must have some thing.
   */
  public static final void ccWriteln(String pxLine){
    O_STOCKER.ccWriteln(pxLine);
  }//+++
  
  /**
   * reset output area content
   */
  public static final void ccClear(){
    O_STOCKER.ccClear(C_INIT_TEXT);
  }//+++
  
  /**
   * for input field
   */
  public static final void ccRequestFocus(){
    O_FIELD.requestFocus();
  }//+++
  
  /**
   * @return directly from input field
   */
  public static final String ccGetFieldText(){
    return O_FIELD.getText();
  }//+++
  
  /**
   * @return directly from output area
   */
  public static final String ccGetAreaText(){
    return O_STOCKER.ccGetText();
  }//+++
  
  //=== entry
  
  public static final JPanel ccGetPanel(){
    if(!pbIsInitiated){ccInit();}
    return O_PANEL;
  }//+++
  
 }//***eof
