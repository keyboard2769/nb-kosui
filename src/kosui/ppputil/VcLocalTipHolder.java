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
public class VcLocalTipHolder {
  
  private static final int 
    C_TEXT_ADJ_X = 2,
    C_TEXT_ADJ_Y = 2
  ;//...
  
  /**
   * @return instance
   */
  public static VcLocalTipHolder ccGetInstance(){
    if(self == null){self = new VcLocalTipHolder();}
    return self;
  }//..!
  private static VcLocalTipHolder self = null;
  private VcLocalTipHolder(){}//..!
  
  //=== 
  
  private PApplet cmOwner=null;
  
  private boolean cmIsDisabled = false;
  private boolean cmIsVisible = false;
  private int cmPaneColor = 0xAA111111;
  private int cmTextColor = 0xFFCCCCCC;
  
  private final HashMap<Integer, String> cmMapOfTip
    = new HashMap<Integer, String>();
  private final HashMap<Integer, EcRect> cmMapOfPane
    = new HashMap<Integer, EcRect>();
  
  private final EcPoint cmAnchor = new EcPoint();
  private final ZcTimer cmShowTM = new ZcOnDelayTimer(32);//..2s
  
  /**
   * <pre>
   * should get invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * if you want to change the raw setting you can call the setter anywhere.
   * the default show time is 2 second.
   * </pre>
   * @param pxOwner your sketch
   */
  public final void ccInit(PApplet pxOwner){
    if(pxOwner==null){return;}
    if(cmOwner==null){cmOwner=pxOwner;}
    ccRegisterTipMessage(0, "<?>");
    ccRegisterTipMessage(EcConst.C_ID_IGNORE, "<?>");
  }//..!
  
  //===

  /**
   * not supposed to get invoked outside a living sketch. <br>
   */
  static public final void ccUpdate(){
    self.ssUpdate(VcLocalCoordinator.ccGetMouseOverID());
  }//+++
  
  private void ssUpdate(int pxID){
    if(cmIsDisabled){return;}
    cmShowTM.ccAct(cmIsVisible);
    if(cmShowTM.ccIsUp()){cmIsVisible=false;}
    if(!cmIsVisible){return;}
    if(cmMapOfTip.containsKey(pxID)&&cmMapOfPane.containsKey(pxID)){
      String lpTip=cmMapOfTip.get(pxID);
      EcRect lpPane=cmMapOfPane.get(pxID);
      cmAnchor.ccSetX(cmOwner.mouseX<(cmOwner.width/2)?
        cmOwner.mouseX:cmOwner.mouseX-lpPane.ccGetW()
      );
      cmAnchor.ccSetY(cmOwner.mouseY<(cmOwner.height/2)?
        cmOwner.mouseY:cmOwner.mouseY-lpPane.ccGetH()
      );
      cmOwner.fill(cmPaneColor);
      cmOwner.rect(
        cmAnchor.ccGetX(),cmAnchor.ccGetY(),
        lpPane.ccGetW(),lpPane.ccGetH()
      );
      cmOwner.fill(cmTextColor);
      cmOwner.text(
        lpTip,
        cmAnchor.ccGetX()+C_TEXT_ADJ_X,cmAnchor.ccGetY()+C_TEXT_ADJ_Y
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
    if(self.cmMapOfTip.containsKey(pxID)){return;}
    self.cmMapOfTip.put(pxID, pxTip);
    EcRect lpRect=new EcRect();
    lpRect.ccSetSize(pxTip);
    self.cmMapOfPane.put(pxID, lpRect);
  }//+++
  
  /**
   * not supposed to be used outside from a living sketch. <br>
   * that coordinator provide a public method for this. <br>
   * @param pxComponent do not pass null
   * @param pxTip do not pass null
   */
  static public final
  void ccRegisterTipMessage(EcElement pxComponent, String pxTip){
    if(pxComponent==null){return;}
    ccRegisterTipMessage(pxComponent.ccGetID(), pxTip);
  }//+++
  
  //===
  
  /**
   * if it is not enabled it does not draw and can not be activated.<br>
   * @param pxStatus #
   */
  public final void ccSetIsEnabled(boolean pxStatus){
    cmIsDisabled=!pxStatus;
  }//+++
  
  /**
   * flip version
   */
  public final void ccSetIsEnabled(){
    cmIsDisabled=!cmIsDisabled;
  }//+++
  
  /**
   * ##
   * @param pxColor #
   */
  public final void ccSetTextColor(int pxColor){
    cmTextColor=pxColor;
  }//+++
  
  /**
   * ##
   * @param pxColor #
   */
  public final void ccSetPaneColor(int pxColor){
    cmPaneColor=pxColor;
  }//+++
  
  /**
   * ##
   * @param pxText #
   * @param pxPane #
   */
  public final void ccSetupColor(int pxText, int pxPane){
    cmTextColor=pxText;
    cmPaneColor=pxPane;
  }//+++
  
  /**
   * @param pxSecond #
   */
  public final void ccSetShowTime(float pxSecond){
    cmShowTM.ccSetTime(EcConst.ccToFrameCount(pxSecond));
  }//+++
  
  /**
   * let tip pane show for a given time.<br>
   */
  public static final void ccActivate(){
    self.cmIsVisible=true;
  }//+++
  
}//***eof
