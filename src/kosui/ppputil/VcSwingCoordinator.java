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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EiTriggerable;
import kosui.pppswingui.ScConst;
import processing.core.PApplet;

/**
 * for local UI, the coordinator holds UI object instances,<br>
 * but this coordinator is just an action manager actually.<br>
 */
public final class VcSwingCoordinator {
  
  private static String cmLastAccepted="";
  
  private static EiTriggerable cmRedirected = null;
  
  private static final Runnable R_REDIRECTED_INVOKING = new Runnable() {
    @Override public void run() {
      if(cmRedirected==null){return;}
      cmRedirected.ccTrigger();
      cmRedirected=null;
    }//+++
  };//***
  
  private static final
  HashMap<String, EiTriggerable> O_MAP_OF_COMMAND_TRIGGER
    = new HashMap<String, EiTriggerable>();
  
  private static final
  HashMap<AbstractButton, EiTriggerable> O_MAP_OF_ACTION_TRIGGER
    = new HashMap<AbstractButton, EiTriggerable>();
  
  private static final
  HashMap<JComponent, EiTriggerable> O_MAP_OF_MOUSE_TRIGGER
    = new HashMap<JComponent, EiTriggerable>();
  
  private static final
  ActionListener R_ACTION_LISTENER = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae){
      if(!ScConst.ccIsEDT()){return;}
      AbstractButton lpSource = (AbstractButton)ae.getSource();
      if(O_MAP_OF_ACTION_TRIGGER.containsKey(lpSource)){
        O_MAP_OF_ACTION_TRIGGER.get(lpSource).ccTrigger();
      }//..?
    }//+++
  };//***
  
  private static final
  MouseAdapter R_MOUSE_ADAPTER = new MouseAdapter() {
    @Override  public void mousePressed(MouseEvent me){
      if(!ScConst.ccIsEDT()){return;}
      //..by now we don't support right click
      if(me.getButton()!=MouseEvent.BUTTON1){return;}
      JComponent lpSource = (JComponent)me.getSource();
      if(O_MAP_OF_MOUSE_TRIGGER.containsKey(lpSource)){
        O_MAP_OF_MOUSE_TRIGGER.get(lpSource).ccTrigger();
      }//..?
    }//+++
  };//***
  
  //===
  
  /**
   * alias to ScConst.ccSetOwner()
   * @param pxOwner #
   */
  public static final void ccInit(Frame pxOwner){
    ScConst.ccSetOwner(pxOwner);
  }//..!
  
  /**
   * alias to ScConst.ccSetOwner()
   * @param pxOwner your sketch
   */
  public static final void ccInit(PApplet pxOwner){
    ScConst.ccSetOwner(pxOwner);
  }//..!
  
  //===
  
  /**
   * registered action will get triggered from the main action listener.<br>
   * @param pxButton just don't pass null
   * @param pxTrigger just don't pass null
   */
  static public final void
  ccRegisterAction(AbstractButton pxButton, EiTriggerable pxTrigger){
    if(pxButton==null){return;}
    if(pxTrigger==null){return;}
    if(O_MAP_OF_ACTION_TRIGGER.containsKey(pxButton)){return;}
    pxButton.addActionListener(R_ACTION_LISTENER);
    O_MAP_OF_ACTION_TRIGGER.put(pxButton,pxTrigger);
  }//+++
  
  /**
   * registered action will get triggered from the main mouse adaptor.<br>
   * @param pxComponent do not pass null
   * @param pxTrigger do not pass null
   */
  static public final void
  ccRegisterPressing(JComponent pxComponent, EiTriggerable pxTrigger){
    if(pxComponent==null){return;}
    if(pxTrigger==null){return;}
    if(O_MAP_OF_MOUSE_TRIGGER.containsKey(pxComponent)){return;}
    pxComponent.addMouseListener(R_MOUSE_ADAPTER);
    O_MAP_OF_MOUSE_TRIGGER.put(pxComponent,pxTrigger);
  }//+++
  
  /**
   * registered action will get triggered from any where calls ccExecute().<br>
   * @param pxCommand something only contains alphabet and underscore 
   * @param pxTrigger do not pass null
   */
  static public final void
  ccRegisterCommand(String pxCommand, EiTriggerable pxTrigger){
    if(!VcStringUtility.ccIsCommandString(pxCommand)){return;}
    if(pxTrigger==null){return;}
    if(O_MAP_OF_COMMAND_TRIGGER.containsKey(pxCommand)){return;}
    O_MAP_OF_COMMAND_TRIGGER.put(pxCommand,pxTrigger);
  }//+++
  
  //===
  
  /**
   * @return accepted or empty string
   */
  static public final String ccGetLastAcceped(){
    return cmLastAccepted;
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
    if(!VcConst.ccIsValidString(pxCommand)){return "[>]";}
    String[] lpSplit = pxCommand.split(" ");
    if(O_MAP_OF_COMMAND_TRIGGER.containsKey(lpSplit[0])){
       cmLastAccepted=pxCommand;
       O_MAP_OF_COMMAND_TRIGGER.get(lpSplit[0]).ccTrigger();
      return "[OK]accepted:"+lpSplit[0];
    }else{
       cmLastAccepted="";
      return "[BAD]unhandled:"+pxCommand;
    }//..?
  }//+++
  
  /**
   * <pre>
   * wrapped SwingUtilities.invokeLater for triggerable actions.
   * i would still be loosely insisting on that
   *   Runnbale is meant for Thread only in any kosui project.
   * </pre>
   * @param pxTrigger never null
   */
  synchronized static public final
  void ccInvokeLater(EiTriggerable pxTrigger){
    if(cmRedirected != null){return;}
    if(pxTrigger == null){return;}
    cmRedirected=pxTrigger;
    SwingUtilities.invokeLater(R_REDIRECTED_INVOKING);
  }//+++
  
 }//***eof
