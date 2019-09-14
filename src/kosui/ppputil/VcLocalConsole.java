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

package kosui.ppputil;

import java.util.HashMap;
import kosui.ppplocalui.EiTriggerable;
import processing.core.PApplet;

/**
 * actually i got this idea from VSCode.<br>
 * but this is even less than a status bar.<br>
 */
public final class VcLocalConsole {
  
  /**
   * @return instance
   */
  static public final VcLocalConsole ccGetInstance(){
    if(self==null){self= new VcLocalConsole();}
    return self;
  }//+++
  static private VcLocalConsole self = null;
  private VcLocalConsole (){}//..!
  
  //===
  
  private static final String C_T_NUM = "<N>" ;
  
  /**
   * location constants
   */
  public static final int
    //-- length
    C_MAX_CHAR_L = 32,
    //-- pix
    C_TEXT_ADJ_X = 2,
    C_TEXT_ADJ_Y = 2
  ;//...
  
  //===
  
  private PApplet cmOwner=null;
  
  private final HashMap<String, EiTriggerable> cmMapOfCommand
    = new HashMap<String, EiTriggerable>();
  
  private boolean
    cmIsTypeMode          = false,
    cmIsMessageBarVisible = true
  ;//...
  
  private EiTriggerable cmSettling = null;
  
  private int
    //-- color
    cmTextColor =0xFFEEEEEE,
    cmMessageBarColor  =0xFF331133,
    cmFieldBarColor =0xCC333333,
    //-- pix ** window
    cmOwnerWidth    = 800,//.. arbitrary by default value
    cmOwnerHeight   = 600,//.. arbitrary by default value
    //-- pix ** bar
    cmBarH = 18,
    //-- pix ** field bar
    cmFieldBarX  = 0,  //.. arbitrary by default value
    cmFieldBarY  = 40, //.. arbitrary by default value
    cmFieldBarW     = 800,
    //-- pix ** message bar
    cmMessageBarX = 0,  //.. arbitrary by default value
    cmMessageBarY = 0,  //.. arbitrary by default value
    cmMessageBarW   = 800
  ;//...
  
  private String
    cmField="  $ ",
    cmMessage="kosui::",
    cmLastAccepted="<none>"
  ;//...
  
  private String[] cmDesAccepted = null;
  
  //===
  
  /**
   * <pre>
   * should get invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * </pre>
   * @param pxOwner your sketch
   */
  public final void ccInit(PApplet pxOwner){
    if(pxOwner==null){return;}
    if(cmOwner==null){
      cmOwner=pxOwner;
      cmOwnerWidth=cmOwner.width;
      cmOwnerHeight=cmOwner.height;
      ccSetFieldBarAnchor(0, cmOwnerHeight-cmBarH*2);
      ccSetMessageBarAnchor(0, cmOwnerHeight-cmBarH);
    }//..?
  }//++!
  
  //===
  
  /**
   * should be called inside draw()
   */
  public static final void ccUpdate(){
    self.ssUpdate();
  }//+++
  
  private void ssUpdate(){

    if(cmIsTypeMode){
      cmOwner.fill(cmFieldBarColor);
      cmOwner.rect(cmFieldBarX,cmFieldBarY,
        cmFieldBarW,cmBarH
      );
      cmOwner.fill(cmTextColor);
      cmOwner.text(cmField+"_",
        cmFieldBarX+C_TEXT_ADJ_X,cmFieldBarY+C_TEXT_ADJ_Y
      );
    }//..?
    
    if(cmIsMessageBarVisible){
      cmOwner.fill(cmMessageBarColor);
      cmOwner.rect(cmMessageBarX,cmMessageBarY,
        cmMessageBarW,cmBarH
      );
      cmOwner.fill(cmTextColor);
      cmOwner.text(cmMessage,
        cmMessageBarX+C_TEXT_ADJ_X,cmMessageBarY+C_TEXT_ADJ_Y
      );
    }//..?
    
  }//+++
    
  /**
   * <pre>
   * supposedly get called inside keyPressed()
   *   and cut the rest of inputting.
   * like : if(VcConsole.ccKeyTyped(key, keyCode)){return;}
   * </pre>
   * @param pxKey key of your sketch
   * @param pxKeyCode keyCode of your sketch
   * @return true if type mode
   */
  public static boolean ccKeyTyped(char pxKey, int pxKeyCode){
    if(pxKeyCode==0x0A){//..{ENTER}
      if(self.cmIsTypeMode){
        self.cmIsTypeMode=false;
        self.ssReadInput();
        self.ssFireOperation();
        if(self.cmSettling!=null){
          self.cmSettling.ccTrigger();
        }//..?
      }else{
        self.cmIsTypeMode=true;
      }//..?
    }else{if(self.cmIsTypeMode){
      if(pxKeyCode==0x08 && self.cmField.length()>4)//..{BS}
        {self.cmField=self.cmField.substring(0, self.cmField.length()-1);}
      if(pxKey>=' ' && pxKey<='z' && self.cmField.length()<C_MAX_CHAR_L)
        {self.cmField=self.cmField.concat(Character.toString(pxKey));}
    }}//..?
    return self.cmIsTypeMode;
  }//+++
  
  private void ssReadInput(){
    cmLastAccepted=cmField.substring(4, cmField.length());
    cmField="  $ ";
  }//+++
  
  private void ssFireOperation(){
    
    //-- null action
    if(cmLastAccepted==null){return;}
    
    //-- space action
    if(cmLastAccepted.equals(" ")){return;}
    if(cmLastAccepted.equals(" ")){return;}
    
    //-- empty action
    if(
      cmLastAccepted.isEmpty()||
      cmLastAccepted.equals(" ")||
      cmLastAccepted.matches("( )+")
    ){
      //[tofix]::VcLocalHelper.ccGetInstance.ccSetIsVisible(false);
      ccSetMessage("--");
      if(cmMapOfCommand.containsKey("")){cmMapOfCommand.get("").ccTrigger();}
      return;
    }//..?
    
    //-- numeric action
    if(VcNumericUtility.ccIsIntegerString(cmLastAccepted)||
       VcNumericUtility.ccIsFloatString(cmLastAccepted)
    ){
      if(cmMapOfCommand.containsKey(C_T_NUM)){
        cmMapOfCommand.get(C_T_NUM).ccTrigger();
      }else{
        ccSetMessage("[BAD]unhandled numeric input:"+cmLastAccepted);
      }return;
    }//..?
    
    //-- normal action
    cmDesAccepted=cmLastAccepted.split("( |,)");
    if(cmMapOfCommand.containsKey(cmDesAccepted[0])){
      cmMapOfCommand.get(cmDesAccepted[0]).ccTrigger();
    }else{
      ccSetMessage("[BAD]unhandled:"+cmDesAccepted[0]);
    }//..?
    
  }//+++
  
  //===
  
  /**
   * @param pxCommand does check for existence
   * @param pxTrigger # 
   */
  public static final 
  void ccRegisterCommand(String pxCommand, EiTriggerable pxTrigger){
    if(pxTrigger==null){return;}
    if(self.cmMapOfCommand.containsKey(pxCommand)){return;}
    self.cmMapOfCommand.put(pxCommand, pxTrigger);
  }//+++
  
  /**
   * @param pxTrigger will get fired every time a command get inputted
   */
  public static final
  void ccRegisterSettlement(EiTriggerable pxTrigger){
    self.cmSettling=pxTrigger;
  }//+++
  
  /**
   * <pre>
   * sometime you might want to make double-hitting enter key
   *   to do something special.
   * this is for that purposed.
   * actually this is just an alias to register empty string
   *   as a command.
   * hiding helper and clear message bar is implemented separately.
   * actually this will get invoked after that.
   * </pre>
   * @param pxOperation #
   */
  public static final 
  void ccRegisterEmptiness(EiTriggerable pxOperation){
    ccRegisterCommand("",pxOperation);
  }//+++
  
  /**
   * tagged command will be the reset one 
   * @param pxOperation #
   */
  public static final 
  void ccRegisterNumeric(EiTriggerable pxOperation){
    ccRegisterCommand(C_T_NUM,pxOperation);
  }//+++
  
  //===
  
  /**
   * applied to both input field and message field.<br>
   * @param pxHeight pix
   */
  public final void ccSetBarHeight(int pxHeight){
    cmBarH=pxHeight;
  }//+++
  
  /**
   * @return ##
   */
  public final int ccGetBarHeight(){
    return cmBarH;
  }//+++
  
  /**
   * bar width will get adjust based on current owner.<br>
   * blocking minus value.<br>
   * @param pxX pix
   * @param pxY pix
   */
  public final void ccSetFieldBarAnchor(int pxX, int pxY){
    if(pxX<0 || pxY<0){return;}
    cmFieldBarX=pxX;
    cmFieldBarY=pxY;
    cmFieldBarW=cmOwnerWidth-pxX;
  }//+++
  
  /**
   * bar width will get adjust based on current owner.<br>
   * blocking minus value.<br>
   * @param pxX pix
   * @param pxY pix
   */
  public final void ccSetMessageBarAnchor(int pxX, int pxY){
    if(pxX<0 || pxY<0){return;}
    cmMessageBarX=pxX;
    cmMessageBarY=pxY;
    cmMessageBarW=cmOwnerWidth-pxX;
  }//+++
  
  /**
   * @param pxText ARGB
   * @param pxMessageBar ARGB
   * @param pxFieldBar ARGB
   */
  public final void ccSetupColor(int pxText, int pxMessageBar, int pxFieldBar){
    cmTextColor=pxText;
    cmMessageBarColor=pxMessageBar;
    cmFieldBarColor=pxFieldBar;
  }//+++
  
  /**
   * @param pxColor ARGB
   */
  public final void ccSetTextColor(int pxColor){
    cmTextColor=pxColor;
  }//+++
  
  /**
   * @param pxColor ARGB
   */
  public final void ccSetMessageBarColor(int pxColor){
    cmMessageBarColor=pxColor;
  }//+++
  
  /**
   * @param pxColor ARGB
   */
  public final void ccSetFieldBarColor(int pxColor){
    cmFieldBarColor=pxColor;
  }//+++
  
  /**
   * @param pxMessage #
   */
  static public final void ccSetMessage(String pxMessage){
    if(!VcConst.ccIsValidString(pxMessage)){return;}
    self.cmMessage=pxMessage;
  }//+++
  
  /**
   * @param pxState #
   */
  public final void ccSetMessageBarVisible(boolean pxState){
    cmIsMessageBarVisible=pxState;
  }//+++
  
  /**
   * flip version 
   */
  public final void ccSetMessageBarVisible(){
    cmIsMessageBarVisible=!cmIsMessageBarVisible;
  }//+++
  
  //===
  
  /**
   * @return #
   */
  static public final String ccGetLastAccepted(){
    return self.cmLastAccepted;
  }//+++
  
  /**
   * the accepted array is split when the enter is pressed.<br>
   * space and comma is the separator.<br>
   * @param pxIndex #
   * @return if any
   */
  static public final String ccGetLastAccepted(int pxIndex){
    if(self.cmDesAccepted==null){return "";}
    int lpFixedIndex=pxIndex<0?0:(
      pxIndex>self.cmDesAccepted.length?self.cmDesAccepted.length:pxIndex
    );
    return self.cmDesAccepted[lpFixedIndex];
  }//+++
  
}//***eof
