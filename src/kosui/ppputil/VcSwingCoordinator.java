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
  
  /**
   * @return instance
   */
  public static VcSwingCoordinator ccGetInstance(){return SELF;}//+++
  private static final VcSwingCoordinator SELF = new VcSwingCoordinator();
  private VcSwingCoordinator(){}//..!

  //===
  
  private String cmLastAccepted="";
  
  private EiTriggerable cmRedirected = null;
  
  private final Runnable cmRedirectedInvoking = new Runnable() {
    @Override public void run() {
      if(cmRedirected==null){return;}
      cmRedirected.ccTrigger();
      cmRedirected=null;
    }//+++
  };//***
  
  private final HashMap<String, EiTriggerable> cmMapOfCommandExecuting
    = new HashMap<String, EiTriggerable>();
  
  private final HashMap<AbstractButton, EiTriggerable> cmMapOfActionPerforming
    = new HashMap<AbstractButton, EiTriggerable>();
  
  private final HashMap<JComponent, EiTriggerable> cmMapOfMousePressing
    = new HashMap<JComponent, EiTriggerable>();
  
  private final ActionListener cmActionLister = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae){
      if(!ScConst.ccIsEDT()){return;}
      AbstractButton lpSource = (AbstractButton)ae.getSource();
      if(cmMapOfActionPerforming.containsKey(lpSource)){
        cmMapOfActionPerforming.get(lpSource).ccTrigger();
      }//..?
    }//+++
  };//***
  
  private final MouseAdapter cmMouseAdaptor = new MouseAdapter() {
    @Override  public void mousePressed(MouseEvent me){
      if(!ScConst.ccIsEDT()){return;}
      //..by now we don't support right click
      if(me.getButton()!=MouseEvent.BUTTON1){return;}
      JComponent lpSource = (JComponent)me.getSource();
      if(cmMapOfMousePressing.containsKey(lpSource)){
        cmMapOfMousePressing.get(lpSource).ccTrigger();
      }//..?
    }//+++
  };//***
  
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
   * registered action will get triggered from the main action listener.<br>
   * @param pxButton just don't pass null
   * @param pxTrigger just don't pass null
   */
  static public final void
  ccRegisterAction(AbstractButton pxButton, EiTriggerable pxTrigger){
    if(pxButton==null){return;}
    if(pxTrigger==null){return;}
    if(SELF.cmMapOfActionPerforming.containsKey(pxButton)){return;}
    pxButton.addActionListener(SELF.cmActionLister);
    SELF.cmMapOfActionPerforming.put(pxButton,pxTrigger);
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
    if(SELF.cmMapOfMousePressing.containsKey(pxComponent)){return;}
    pxComponent.addMouseListener(SELF.cmMouseAdaptor);
    SELF.cmMapOfMousePressing.put(pxComponent,pxTrigger);
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
    if(SELF.cmMapOfCommandExecuting.containsKey(pxCommand)){return;}
    SELF.cmMapOfCommandExecuting.put(pxCommand,pxTrigger);
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
    if(!VcConst.ccIsValidString(pxCommand)){return "[>]";}
    String[] lpSplit = pxCommand.split(" ");
    if(SELF.cmMapOfCommandExecuting.containsKey(lpSplit[0])){
       SELF.cmLastAccepted=pxCommand;
       SELF.cmMapOfCommandExecuting.get(lpSplit[0]).ccTrigger();
      return "[OK]accepted:"+lpSplit[0];
    }else{
       SELF.cmLastAccepted="";
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
    if(SELF.cmRedirected != null){return;}
    if(pxTrigger == null){return;}
    SELF.cmRedirected=pxTrigger;
    SwingUtilities.invokeLater(SELF.cmRedirectedInvoking);
  }//+++
  
 }//***eof
