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
import kosui.ppplogic.ZcRangedModel;
import processing.core.PApplet;

/**
 * actually i got this idea from VSCode.<br>
 * but this is even less than a status bar.<br>
 */
public final class VcLocalConsole {
  
  /**
   * hard coded
   */
  public static final int
    //-- length
    C_MAX_CHAR_L = 32,
    //-- pix
    C_TEXT_ADJ_X = 2,
    C_TEXT_ADJ_Y = 2
  ;//,,,
  
  private static final String C_T_NUM = "<N>" ;
  private static final String C_V_BLANK = " " ;
  
  //===
  
  private static PApplet cmOwner=null;
  
  private static final
  HashMap<String, EiTriggerable> O_MAP_OF_COMMAND
    = new HashMap<String, EiTriggerable>();
  
  private static boolean
    cmIsTypeMode          = false,
    cmIsMessageBarVisible = true,
    cmIsBlockingOutside   = false
  ;//,,,
  
  private static EiTriggerable cmSettling = null;
  
  private static int
    //-- color
    cmTextColor       = 0xFFEEEEEE,
    cmMessageBarColor = 0xFF331133,
    cmFieldBarColor   = 0xCC333333,
    //-- pix ** window
    cmOwnerWidth    = 800,//.. arbitrary by default value
    cmOwnerHeight   = 600,//.. arbitrary by default value
    //-- pix ** bar
    cmBarH = 18,
    //-- pix ** field bar
    cmFieldBarX  = 0,  //.. arbitrary by default value
    cmFieldBarY  = 40, //.. arbitrary by default value
    cmFieldBarW  = 800,
    //-- pix ** message bar
    cmMessageBarX = 0,  //.. arbitrary by default value
    cmMessageBarY = 0,  //.. arbitrary by default value
    cmMessageBarW = 800 //.. arbitrary by default value
  ;//,,,
  
  private static String
    cmField        = "  $ ",
    cmMessage      = "kosui $ ",
    cmLastAccepted = "<none>"
  ;//,,,
  
  private static String[] cmDesAccepted = null;
  
  //===
  
  /**
   * <pre>
   * should get invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * </pre>
   * @param pxOwner your sketch
   */
  public static final void ccInit(PApplet pxOwner){
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
    
    //-- case of **
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
    
    //-- case of **
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
    
  }//++~
  
  //===
    
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
      if(cmIsTypeMode){
        cmIsTypeMode=false;
        cmIsBlockingOutside=true;
        ssReadInput();
        ssFireOperation();
        if(cmSettling!=null){
          cmSettling.ccTrigger();
        }//..?
      }else{
        cmIsTypeMode=true;
      }//..?
    }else{if(cmIsTypeMode){
      if(pxKeyCode==0x08 && cmField.length()>4)//..{BS}
        {cmField=cmField.substring(0, cmField.length()-1);}
      if(pxKey>=' ' && pxKey<='z' && cmField.length()<C_MAX_CHAR_L)
        {cmField=cmField.concat(Character.toString(pxKey));}
    }}//..?
    return cmIsTypeMode;
  }//+++
  
  private static void ssReadInput(){
    cmLastAccepted=cmField.substring(4, cmField.length());
    cmField="  $ ";
  }//+++
  
  private static void ssFireOperation(){
    
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
      if(O_MAP_OF_COMMAND.containsKey("")){O_MAP_OF_COMMAND.get("").ccTrigger();}
      return;
    }//..?
    
    //-- numeric action
    if(VcNumericUtility.ccIsIntegerString(cmLastAccepted)||
       VcNumericUtility.ccIsFloatString(cmLastAccepted)
    ){
      if(O_MAP_OF_COMMAND.containsKey(C_T_NUM)){
        O_MAP_OF_COMMAND.get(C_T_NUM).ccTrigger();
      }else{
        ccSetMessage("[BAD]unhandled numeric input:"+cmLastAccepted);
      }return;
    }//..?
    
    //-- normal action
    cmDesAccepted=cmLastAccepted.split("( |,)");
    if(O_MAP_OF_COMMAND.containsKey(cmDesAccepted[0])){
      O_MAP_OF_COMMAND.get(cmDesAccepted[0]).ccTrigger();
    }else{
      ccSetMessage("[BAD]unhandled:"+cmDesAccepted[0]);
    }//..?
    
  }//+++
  
  //===
  
  /**
   * @param pxCommand do not pass null
   * @param pxTrigger no dot pass null
   */
  public static final 
  void ccRegisterCommand(String pxCommand, EiTriggerable pxTrigger){
    if(pxCommand==null){return;}
    if(pxTrigger==null){return;}
    if(O_MAP_OF_COMMAND.containsKey(pxCommand)){return;}
    O_MAP_OF_COMMAND.put(pxCommand, pxTrigger);
  }//+++
  
  /**
   * given action got fired from empty command.<br>
   * null means nothing here.<br>
   * @param pxTrigger could be any thing
   */
  public static final
  void ccRegisterSettlement(EiTriggerable pxTrigger){
    cmSettling=pxTrigger;
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
   * @param pxOperation will get passed to register directly
   */
  public static final 
  void ccRegisterEmptiness(EiTriggerable pxOperation){
    ccRegisterCommand("",pxOperation);
  }//+++
  
  /**
   * tagged command will be the reset one 
   * @param pxOperation will get passed to register directly
   */
  public static final 
  void ccRegisterNumeric(EiTriggerable pxOperation){
    ccRegisterCommand(C_T_NUM,pxOperation);
  }//+++
  
  //===
  
  /**
   * if the message bar is blocking outside input out
   * then nothing will happen.<br>
   * @param pxMessage will get passed to setter directly
   */
  public static final void ccSetMessageBarText(String pxMessage){
    if(cmIsBlockingOutside){return;}
    ccSetMessage(pxMessage);
  }//++<
  
  /**
   * if the message bar is block outside input out
   * then nothing will happen.<br>
   * after the manipulation the message bar locks it self for you.<br>
   * @param pxMessage will get passed to setter directly
   */
  static public final void ccBlockMessageBarText(String pxMessage){
    if(cmIsBlockingOutside){return;}
    ccSetMessage(pxMessage);
    cmIsBlockingOutside=true;
  }//++<
  
  /**
   * set the text to an space character then unlock the message bar.<br>
   */
  static public final void ccClearMessageBarText(){
    ccSetMessage(C_V_BLANK);
    cmIsBlockingOutside=false;
  }//++<
  
  /**
   * @return could be anything
   */
  static public final String ccGetLastAccepted(){
    return cmLastAccepted;
  }//++>
  
  /**
   * the accepted array is split when the enter is pressed.<br>
   * space and comma is the separator.<br>
   * @param pxIndex will get trimmed
   * @return if any
   */
  static public final String ccGetLastAccepted(int pxIndex){
    if(cmDesAccepted==null){return "";}
    if(cmDesAccepted.length<=1){return cmDesAccepted[0];}
    int lpFixedIndex=ZcRangedModel
      .ccLimitInclude(pxIndex,0,cmDesAccepted.length-1);
    return cmDesAccepted[lpFixedIndex];
  }//++>
  
  //===
  
  /**
   * bar width will get adjust based on current owner.<br>
   * blocking minus value.<br>
   * @param pxX pix
   * @param pxY pix
   */
  public static final void ccSetFieldBarAnchor(int pxX, int pxY){
    if(pxX<0 || pxY<0){return;}
    cmFieldBarX=pxX;
    cmFieldBarY=pxY;
    cmFieldBarW=cmOwnerWidth-pxX;
  }//++<
  
  /**
   * bar width will get adjust based on current owner.<br>
   * blocking minus value.<br>
   * @param pxX pix
   * @param pxY pix
   */
  public static final void ccSetMessageBarAnchor(int pxX, int pxY){
    if(pxX<0 || pxY<0){return;}
    cmMessageBarX=pxX;
    cmMessageBarY=pxY;
    cmMessageBarW=cmOwnerWidth-pxX;
  }//++<
  
  /**
   * @param pxText ARGB
   * @param pxMessageBar ARGB
   * @param pxFieldBar ARGB
   */
  public static final void ccSetupColor(int pxText, int pxMessageBar, int pxFieldBar){
    cmTextColor=pxText;
    cmMessageBarColor=pxMessageBar;
    cmFieldBarColor=pxFieldBar;
  }//++<
  
  /**
   * @param pxColor ARGB
   */
  public static final void ccSetTextColor(int pxColor){
    cmTextColor=pxColor;
  }//++<
  
  /**
   * @param pxColor ARGB
   */
  public static final void ccSetMessageBarColor(int pxColor){
    cmMessageBarColor=pxColor;
  }//++<
  
  /**
   * @param pxColor ARGB
   */
  public static final void ccSetFieldBarColor(int pxColor){
    cmFieldBarColor=pxColor;
  }//++<
  
  /**
   * @param pxMessage must have something
   */
  public static final void ccSetMessage(String pxMessage){
    if(!VcConst.ccIsValidString(pxMessage)){return;}
    cmMessage=pxMessage;
  }//++<
  
  /**
   * @param pxState could be anything
   */
  public static final void ccSetMessageBarVisible(boolean pxState){
    cmIsMessageBarVisible=pxState;
  }//++<
  
  /**
   * flip version 
   */
  public static final void ccSetMessageBarVisible(){
    cmIsMessageBarVisible=!cmIsMessageBarVisible;
  }//++<
  
  /**
   * applied to both input field and message field.<br>
   * @param pxHeight pix
   */
  public static final void ccSetBarHeight(int pxHeight){
    cmBarH=pxHeight;
  }//++<
  
  /**
   * @return pix
   */
  public static final int ccGetBarHeight(){
    return cmBarH;
  }//++<
  
}//***eof
