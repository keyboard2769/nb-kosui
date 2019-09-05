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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import kosui.pppswingui.ScFactory;

/**
 * this was my template of swing application, i thought this is the best way 
 * for triggering and watching.<br>
 * for normal static building, i think you should avoid using this 
 * manager class directly.<br>
 */
public final class VcSwingConsole extends JPanel{
  
  private static final String C_INIT_TEXT
    = "kosui standby::"+VcConst.C_V_NEWLINE;
  
  /**
   * @return instance
   */
  public static final VcSwingConsole ccGetInstance() {
    if(self==null){self=new VcSwingConsole();return self;}
    else{return self;}
  }//+++
  private static VcSwingConsole self = null;
  
  //===
  
  private final JTextArea cmArea 
    = new JTextArea(C_INIT_TEXT);
  
  private final JTextField cmField
   = new JTextField("");
  
  private final KeyListener cmListener=new KeyListener() {
      @Override public void keyTyped(KeyEvent ke){}//+++
      @Override public void keyPressed(KeyEvent ke){}//+++
      @Override public void keyReleased(KeyEvent ke){
        int lpCharCode=(int)ke.getKeyChar();
        switch(lpCharCode){
          case 0x0A:
            ccStackln(VcSwingCoordinator.ccExecute(cmField.getText()));
            cmField.setText("");
          break;
          default:break;
        }//..?
      }//+++
  };
  
  private VcSwingConsole(){
    super(new BorderLayout());
    setBorder(BorderFactory.createEtchedBorder());
    ScFactory.ccSetupConsoleArea(cmArea);
    JScrollPane lpCenterPane=new JScrollPane(cmArea);
    cmField.addKeyListener(cmListener);
    add(lpCenterPane,BorderLayout.CENTER);
    add(cmField,BorderLayout.PAGE_END);
  }//..!
  
  //===
  
  /**
   * alias for JTextArea::append().<br>
   * re-direct viewport location via text selection setting.<br>
   * @param pxTag must have something
   * @param pxVal can be any thing
   */
  public static final void ccStackln(String pxTag, Object pxVal){
    if(pxTag==null){return;}
    if(pxVal==null){
      self.cmArea.append(pxTag+VcConst.C_V_NEWLINE);
    }else{
      self.cmArea.append(pxTag+":"+pxVal.toString()+VcConst.C_V_NEWLINE);
    }//..?
    int lpLength = self.cmArea.getText().length();
    self.cmArea.setSelectionStart(lpLength-1);
    self.cmArea.setSelectionEnd(lpLength);
  }//+++
  
  /**
   * might get passed to JTextArea::append() eventually.<br>
   * @param pxLine must have some thing.
   */
  public static final void ccStackln(String pxLine){
    ccStackln(pxLine, null);
  }//+++
  
  /**
   * reset output area content
   */
  public static final void ccClear(){
    self.cmArea.setText(C_INIT_TEXT);
  }//+++
  
  /**
   * for input field
   */
  public static final void ccRequestFocus(){
    self.cmField.requestFocus();
  }//+++
  
  /**
   * @return directly from input field
   */
  public static final String ccGetFieldText(){
    return self.cmField.getText();
  }//+++
  
  /**
   * @return directly from output area
   */
  public static final String ccGetAreaText(){
    return self.cmArea.getText();
  }//+++
  
 }//***eof
