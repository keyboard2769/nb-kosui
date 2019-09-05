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
import processing.data.StringList;

/**
 * actually i got this idea from VSCode.<br>
 * but this is even less than a status bar.<br>
 */
public final class VcResponsiveBar {
  
  /**
   * @return instance
   */
  static public final VcResponsiveBar ccGetInstance(){
    if(self==null){self= new VcResponsiveBar();}
    return self;
  }//+++
  static private VcResponsiveBar self = null;
  private VcResponsiveBar (){}//..!
  
  //===
  
  private static final String
    C_T_NUM     = "<N>",
    C_INVALID     = ""
  ;//...
  
  private static final int
    //-- length
    C_MAX_CHAR_L = 32,
    //-- pix
    C_TEXT_ADJ_Y = 2,
    C_BAR_H = 18
  ;//...
  
  //===
  
  private PApplet cmOwner=null;
  
  private final HashMap<String, EiTriggerable> cmMapOfCommand
    = new HashMap<String, EiTriggerable>();
  
  private final StringList cmListOfHelpMessage
    = new StringList();
  
  private boolean
    cmIsHelperVisible     = false,
    cmIsWatchBarVisible   = false,
    cmIsTypeMode          = false,
    cmIsMessageBarVisible = true
  ;//...
  
  private int
    //-- color
    cmForeColor =0xFFEEEEEE,
    cmBarColor  =0xFF331133,
    cmTypeColor =0xCC333333,
    //-- pix
    cmWidth  =800,
    cmHeight =600,
    //-- count
    cmHelperIndex=0
  ;//...
  
  private String
    cmField="  $ ",
    cmMessage="standby::",
    cmLastAccepted="<none>",
    cmWatchBarContent=""
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
      cmWidth=cmOwner.width;
      cmHeight=cmOwner.height;
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
    
    if(cmIsHelperVisible && cmListOfHelpMessage.size()>0){
      cmOwner.fill(cmTypeColor);
      cmOwner.rect(cmWidth/4,cmHeight/4,cmWidth/2,cmHeight/2);
      cmOwner.fill(cmForeColor);
      cmOwner.text(cmListOfHelpMessage.get(cmHelperIndex)
        ,cmWidth/4+2,cmHeight/4+2+C_TEXT_ADJ_Y
      );
    }//..?
    
    if(cmIsWatchBarVisible){
      cmOwner.fill(cmTypeColor);
      cmOwner.rect(0,0,cmWidth,C_BAR_H);
      cmOwner.fill(cmForeColor);
      cmOwner.text(cmWatchBarContent,2,2+C_TEXT_ADJ_Y);
    }//..?
    
    if(cmIsTypeMode){
      cmOwner.fill(cmTypeColor);
      cmOwner.rect(0,cmHeight-C_BAR_H*2,cmWidth,C_BAR_H);
      cmOwner.fill(cmForeColor);
      cmOwner.text(cmField+"_",2,cmHeight-C_BAR_H*2+C_TEXT_ADJ_Y);
    }//..?
    
    if(cmIsMessageBarVisible){
      cmOwner.fill(cmBarColor);
      cmOwner.rect(0,cmHeight-C_BAR_H,cmWidth,C_BAR_H);
      cmOwner.fill(cmForeColor);
      cmOwner.text(cmMessage,2,cmHeight-C_BAR_H+C_TEXT_ADJ_Y);
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
    
    //-- empty action
    if(cmLastAccepted.isEmpty()){
      cmIsHelperVisible=false;
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
  
  /**
   * <pre>
   * will be stacked to show on watch bar.
   * supposedly to get called from PApplet.draw() loop.
   * an alternative way to the VcTagger for tagger is basically for test use. 
   * </pre>
   * @param pxTag must have something
   * @param pxValue don't pass null
   */
  public static void ccWatch(String pxTag, Object pxValue){
    if(!VcConst.ccIsValidString(pxTag)){return;}
    if(pxValue==null){return;}
    if(self.cmWatchBarContent.length()>=512){return;}
    StringBuilder lpBuilder=new StringBuilder("[");
    lpBuilder.append(pxTag);lpBuilder.append(":");
    lpBuilder.append(pxValue);lpBuilder.append("]");
    self.cmWatchBarContent+=lpBuilder.toString();
  }//+++
  
  //===
  
  /**
   * @param pxCommand does check for existence
   * @param pxTrigger # 
   */
  public static final 
  void ccRegisterTrigger(String pxCommand, EiTriggerable pxTrigger){
    if(pxTrigger==null){return;}
    if(self.cmMapOfCommand.containsKey(pxCommand)){return;}
    self.cmMapOfCommand.put(pxCommand, pxTrigger);
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
  void ccRegisterEmptyTrigger(EiTriggerable pxOperation){
    ccRegisterTrigger("",pxOperation);
  }//+++
  
  /**
   * tagged command will be the reset one 
   * @param pxOperation #
   */
  public static final 
  void ccRegisterNumericOperation(EiTriggerable pxOperation){
    ccRegisterTrigger(C_T_NUM,pxOperation);
  }//+++
  
  /**
   * will be shown as a whole page of helper box. 
   * @param pxMessage #
   */
  public static void ccAddHelpMessage(String pxMessage){
    self.cmListOfHelpMessage.append(pxMessage);
  }//+++
  
  //===
  
  /**
   * @param pxFore ARGB
   * @param pxBar ARGB
   * @param pxType ARGB
   */
  public final void ccSetColor(int pxFore, int pxBar, int pxType){
    cmForeColor=pxFore;
    cmBarColor=pxBar;
    cmTypeColor=pxType;
  }//+++
    
  /**
   * 
   * @param pxMessage #
   */
  public final void ccSetMessage(String pxMessage){
    if(!VcConst.ccIsValidString(pxMessage)){return;}
    cmMessage=pxMessage;
  }//+++
  
  /**
   * 
   * @param pxState #
   */
  public final void ccSetMessageBarVisible(boolean pxState){
    cmIsMessageBarVisible=pxState;
  }//+++
  
  /**
   * flip version 
   */
  public final void ccSetIsMessageBarVisible(){
    cmIsMessageBarVisible=!cmIsMessageBarVisible;
  }//+++
  
  /**
   * 
   * @param pxStatus #
   */
  public final void ccSetIsMessageBarVisible(boolean pxStatus){
    cmIsMessageBarVisible=pxStatus;
  }//+++
  
  /**
   * 
   * @param pxState #
   */
  public final void ccSetWatchBarVisible(boolean pxState){
    cmIsWatchBarVisible=pxState;
  }//+++
  
  /**
   * flip version 
   */
  public final void ccSetIsWatchBarVisible(){
    cmIsWatchBarVisible=!cmIsWatchBarVisible;
  }//+++
  
  /**
   * 
   * @param pxState #
   */
  public final void ccSetIsWatchBarVisible(boolean pxState){
    cmIsWatchBarVisible=pxState;
  }//+++
  
  /**
   * flip version. index will also be set to zero.<br>
   */
  public final void ccSetHelperVisible(){
    cmIsHelperVisible=!cmIsHelperVisible;
    cmHelperIndex=0;
  }//+++
  
  /**
   * index will also be set to zero.<br>
   * @param pxState #
   */
  public final void ccSetHelperVisible(boolean pxState){
    cmIsHelperVisible=pxState;
    cmHelperIndex=0;
  }//+++
  
  //===
  
  /**
   * supposedly can be called from key pressed
   * @param pxOffset will be constrained to fit size
   */
  public final void ccShiftHelperIndex(int pxOffset){
    if(!cmIsHelperVisible){return;}
    if(cmListOfHelpMessage.size()<=0){return;}
    cmHelperIndex+=pxOffset;
    cmHelperIndex=PApplet.constrain(cmHelperIndex,
      0, cmListOfHelpMessage.size()-1
    );
  }//+++
  
  /**
   * must get called at the end of draw()
   */
  public final void ccStabilize(){
    cmWatchBarContent=C_INVALID;
  }//+++
  
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
