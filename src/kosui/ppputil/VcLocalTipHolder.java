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

import java.util.HashMap;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcPoint;
import kosui.ppplocalui.EcRect;
import kosui.ppplogic.ZcOnDelayTimer;
import kosui.ppplogic.ZcTimer;
import processing.core.PApplet;

/**
 * basically a hash map finds tip by id, not supposed to be used
 * outside from a coordinator. <br>
 * but actually it is just a rectangle with text. <br>
 */
public final class VcLocalTipHolder {
  
  private static final int 
    C_TEXT_ADJ_X = 2,
    C_TEXT_ADJ_Y = 2
  ;//...
  
  //=== 
  
  private static PApplet cmOwner=null;
  
  private static boolean cmIsDisabled = false;
  private static boolean cmIsVisible = false;
  
  private static int cmPaneColor = 0xAA111111;
  private static int cmTextColor = 0xFFCCCCCC;
  
  private static final HashMap<Integer, String> O_MAP_OF_TIP
   = new HashMap<Integer, String>();
  private static final HashMap<Integer, EcRect> O_MAP_OF_PANE
   = new HashMap<Integer, EcRect>();
  
  private static final EcPoint O_ANCHOR     = new EcPoint();
  private static final ZcTimer O_SHOW_TIMER = new ZcOnDelayTimer(32);//..2s
  
  //===
  
  /**
   * <pre>
   * should get invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * if you want to change the raw setting you can call the setter anywhere.
   * the default show time is 2 second.
   * </pre>
   * @param pxOwner your sketch
   */
  public static final void ccInit(PApplet pxOwner){
    if(pxOwner==null){return;}
    if(cmOwner==null){cmOwner=pxOwner;}
    ccRegisterTipMessage(0, "<?>");
    ccRegisterTipMessage(EcConst.C_ID_IGNORE, "<?>");
  }//++!
  
  //===

  /**
   * not supposed to get invoked outside a living sketch. <br>
   */
  public static final void ccUpdate(){
    int lpID = VcLocalCoordinator.ccGetMouseOverID();
    if(cmIsDisabled){return;}
    O_SHOW_TIMER.ccAct(cmIsVisible);
    if(O_SHOW_TIMER.ccIsUp()){cmIsVisible=false;}
    if(!cmIsVisible){return;}
    if(O_MAP_OF_TIP.containsKey(lpID)&&O_MAP_OF_PANE.containsKey(lpID)){
      String lpTip=O_MAP_OF_TIP.get(lpID);
      EcRect lpPane=O_MAP_OF_PANE.get(lpID);
      O_ANCHOR.ccSetX(cmOwner.mouseX<(cmOwner.width/2)?
        cmOwner.mouseX:cmOwner.mouseX-lpPane.ccGetW()
      );
      O_ANCHOR.ccSetY(cmOwner.mouseY<(cmOwner.height/2)?
        cmOwner.mouseY:cmOwner.mouseY-lpPane.ccGetH()
      );
      cmOwner.fill(cmPaneColor);
      cmOwner.rect(O_ANCHOR.ccGetX(),O_ANCHOR.ccGetY(),
        lpPane.ccGetW(),lpPane.ccGetH()
      );
      cmOwner.fill(cmTextColor);
      cmOwner.text(lpTip,
        O_ANCHOR.ccGetX()+C_TEXT_ADJ_X,O_ANCHOR.ccGetY()+C_TEXT_ADJ_Y
      );
    }//..?
  }//+++
  
  /**
   * not supposed to be used outside from a living sketch. <br>
   * that coordinator provide a public method for this. <br>
   * @param pxID zero and ignore is registered by default
   * @param pxTip do not pass null
   */
  public static final
  void ccRegisterTipMessage(int pxID, String pxTip){
    if(!VcConst.ccIsValidString(pxTip)){return;}
    if(O_MAP_OF_TIP.containsKey(pxID)){return;}
    O_MAP_OF_TIP.put(pxID, pxTip);
    EcRect lpRect=new EcRect();
    lpRect.ccSetSize(pxTip);
    O_MAP_OF_PANE.put(pxID, lpRect);
  }//+++
  
  /**
   * not supposed to be used outside from a living sketch. <br>
   * that coordinator provide a public method for this. <br>
   * @param pxComponent do not pass null
   * @param pxTip do not pass null
   */
  public static final
  void ccRegisterTipMessage(EcElement pxComponent, String pxTip){
    if(pxComponent==null){return;}
    ccRegisterTipMessage(pxComponent.ccGetID(), pxTip);
  }//+++
  
  //===
  
  /**
   * if it is not enabled it does not draw and can not be activated.<br>
   * @param pxStatus #
   */
  public static final void ccSetIsEnabled(boolean pxStatus){
    cmIsDisabled=!pxStatus;
  }//+++
  
  /**
   * flip version
   */
  public static final void ccSetIsEnabled(){
    cmIsDisabled=!cmIsDisabled;
  }//+++
  
  /**
   * ##
   * @param pxColor #
   */
  public static final void ccSetTextColor(int pxColor){
    cmTextColor=pxColor;
  }//+++
  
  /**
   * ##
   * @param pxColor #
   */
  public static final void ccSetPaneColor(int pxColor){
    cmPaneColor=pxColor;
  }//+++
  
  /**
   * ##
   * @param pxText #
   * @param pxPane #
   */
  public static final void ccSetupColor(int pxText, int pxPane){
    cmTextColor=pxText;
    cmPaneColor=pxPane;
  }//+++
  
  /**
   * @param pxSecond #
   */
  public static final void ccSetShowTime(float pxSecond){
    O_SHOW_TIMER.ccSetTime(EcConst.ccToFrameCount(pxSecond));
  }//+++
  
  /**
   * let tip pane show for a given time.<br>
   */
  public static final void ccActivate(){
    cmIsVisible=true;
  }//+++
  
}//***eof
