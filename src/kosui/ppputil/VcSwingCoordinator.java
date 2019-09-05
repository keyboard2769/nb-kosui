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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppswingui.ScConst;
import processing.core.PApplet;

/**
 * for local UI, the coordinator holds UI object instances,<br>
 * but this coordinator is just an action manager actually.<br>
 */
public final class VcSwingCoordinator {
  
  /**
   * @return instance
   */
  public static VcSwingCoordinator ccGetInstance(){return SELF;}//+++
  private static final VcSwingCoordinator SELF = new VcSwingCoordinator();
  private VcSwingCoordinator(){}//..!

  //===
  
  private final HashMap<String, EiTriggerable> cmMapOfCommandTrigger
    = new HashMap<String, EiTriggerable>();
  
  private final HashMap<JComponent, EiTriggerable> cmMapOfSwingTrigger
    = new HashMap<JComponent, EiTriggerable>();
  
  private final ActionListener cmTheLister = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae){
      if(!ScConst.ccIsEDT()){return;}
      JComponent lpSource = (JComponent)ae.getSource();
      if(cmMapOfSwingTrigger.containsKey(lpSource)){
        cmMapOfSwingTrigger.get(lpSource).ccTrigger();
      }//..?
    }//+++
  };
  
  private String cmLastAccepted="";
  
  //===
  
  /**
   * alias to ScConst.ccSetOwner()
   * @param pxOwner #
   */
  public final void ccInit(Frame pxOwner){
    ScConst.ccSetOwner(pxOwner);
  }//..!
  
  /**
   * alias to ScConst.ccSetOwner()
   * @param pxOwner your sketch
   */
  public final void ccInit(PApplet pxOwner){
    ScConst.ccSetOwner(pxOwner);
  }//..!
  
  //===
  
  /**
   * @return #
   * @deprecated you are not supposed to get to need it 
   */
  @Deprecated public final ActionListener ccGetTheListener(){
    return cmTheLister;
  }//+++
  
  /**
   * @param pxComponent just don't pass null
   * @param pxTrigger just don't pass null
   */
  static public final void
  ccRegisterComponent(JComponent pxComponent, EiTriggerable pxTrigger){
    if(pxComponent==null){return;}
    if(pxTrigger==null){return;}
    if(SELF.cmMapOfSwingTrigger.containsKey(pxComponent)){return;}
    if(pxComponent instanceof AbstractButton){
      ((AbstractButton)pxComponent).addActionListener(SELF.cmTheLister);
    }//..?
    SELF.cmMapOfSwingTrigger.put(pxComponent,pxTrigger);
  }//+++
  
  /**
   * @param pxCommand something only contains alphabet and underscore 
   * @param pxTrigger do not pass null
   */
  static public final void
  ccRegisterCommand(String pxCommand, EiTriggerable pxTrigger){
    if(!VcStringUtility.ccIsCommandString(pxCommand)){return;}
    if(pxTrigger==null){return;}
    if(SELF.cmMapOfCommandTrigger.containsKey(pxCommand)){return;}
    SELF.cmMapOfCommandTrigger.put(pxCommand,pxTrigger);
  }//+++
  
  /**
   * @return accepted or empty string
   */
  static public final String ccGetLastAcceped(){
    return SELF.cmLastAccepted;
  }//+++
  
  /**
   * <pre>
   * trigger a registered trigger for you.
   * its up to you when it is about to get triggered.
   * </pre>
   * @param pxCommand can be any thing
   * @return a tag with input
   */
  static public final String ccExecute(String pxCommand){
    if(!VcConst.ccIsValidString(pxCommand)){return "[>]"+VcConst.C_V_NEWLINE;}
    String[] lpSplit = pxCommand.split(" ");
    if(SELF.cmMapOfCommandTrigger.containsKey(lpSplit[0])){
       SELF.cmLastAccepted=pxCommand;
       SELF.cmMapOfCommandTrigger.get(lpSplit[0]).ccTrigger();
      return "[OK]accepted:"+lpSplit[0];
    }else{
       SELF.cmLastAccepted="";
      return "[BAD]unhandled:"+pxCommand;
    }//..?
  }//+++
  
 }//***eof
