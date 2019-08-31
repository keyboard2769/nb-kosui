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

package kosui.ppplocalui;

import java.util.HashMap;
import processing.core.PApplet;
import processing.data.StringList;
import kosui.ppputil.VcConst;

/**
 * actually i got this idea from VSCode.<br>
 * but this is even less than a status bar.<br>
 */
public final class VcConsole {
  
  private static final String
    C_T_NUM     = "<N>",
    C_INVALID     = "",
    C_DEFAULT_SEP = ","
  ;//...
  
  private static final String[]
    C_D_EMPTY={""};
  
  private static final int
    //-- length
    C_MAX_CHAR_L = 32,
    //-- pix
    C_TEXT_ADJ_Y = 2,
    C_BAR_H = 18
  ;//...
  
  //===
  
  private static final HashMap<String, ViOperable>
    O_MAP =new HashMap<String, ViOperable>();
  
  private static final StringList
    O_HLP_MSG_LIST=new StringList();
  
  private static PApplet pbOwner=null;
  
  private static int
    pbForeColor =0xFFEEEEEE,
    pbBarColor  =0xFF331133,
    pbTypeColor =0xCC333333,
    pbWidth  =800,
    pbHeight =600
  ;//--
  
  private static String
    pbTypo="  $ ",
    pbMessage="::standby",
    pbSeparator=",",
    pbLastAccepted="<none>",
    pbWatchBarContent=""
  ;//---
  
  private static int pbHelperIndex=0;
  
  private static boolean
    pbIsHelperVisible     = false,
    pbIsWatchBarVisible   = false,
    pbIsTypeMode          = false,
    pbIsMessageBarVisible = true
  ;//...
  
  //===
  
  private VcConsole (){}//+++
  
  //===
  
  /**
   * it is invoked from the factory's initiator.
   * you don't have to call this in your sketch;
   * @param pxParent your sketch
   */
  public static void ccInit(PApplet pxParent){
    pbOwner=pxParent;
    if(pbOwner!=null){
      pbWidth=pbOwner.width;
      pbHeight=pbOwner.height;
    }
  }//++!
  
  //===
  
  /**
   * should be called inside draw()
   */
  public static void ccUpdate(){
    
    if(pbIsHelperVisible && O_HLP_MSG_LIST.size()>0){
      pbOwner.fill(pbTypeColor);
      pbOwner.rect(pbWidth/4,pbHeight/4,pbWidth/2,pbHeight/2);
      pbOwner.fill(pbForeColor);
      pbOwner.text(O_HLP_MSG_LIST.get(pbHelperIndex)
        ,pbWidth/4+2,pbHeight/4+2+C_TEXT_ADJ_Y
      );
    }//..?
    
    if(pbIsWatchBarVisible){
      pbOwner.fill(pbTypeColor);
      pbOwner.rect(0,0,pbWidth,C_BAR_H);
      pbOwner.fill(pbForeColor);
      pbOwner.text(pbWatchBarContent,2,2+C_TEXT_ADJ_Y);
    }//..?
    
    if(pbIsTypeMode){
      pbOwner.fill(pbTypeColor);
      pbOwner.rect(0,pbHeight-C_BAR_H*2,pbWidth,C_BAR_H);
      pbOwner.fill(pbForeColor);
      pbOwner.text(pbTypo+"_",2,pbHeight-C_BAR_H*2+C_TEXT_ADJ_Y);
    }//..?
    
    if(pbIsMessageBarVisible){
      pbOwner.fill(pbBarColor);
      pbOwner.rect(0,pbHeight-C_BAR_H,pbWidth,C_BAR_H);
      pbOwner.fill(pbForeColor);
      pbOwner.text(pbMessage,2,pbHeight-C_BAR_H+C_TEXT_ADJ_Y);
    }//..?
    
  }//+++
    
  /**
   * should be called inside keyPressed() and cut the rest of inputting.<br>
   * like : if(VcConsole.ccKeyTyped(key, keyCode)){return;}
   * @param pxKey key of your sketch
   * @param pxKeyCode keyCode of your sketch
   * @return true if type mode
   */
  public static boolean ccKeyTyped(char pxKey, int pxKeyCode){
    
    if(pxKeyCode==0x0A){//..{ENTER}
      if(pbIsTypeMode){
        pbIsTypeMode=false;
        ssReadInput();
        ssFireOperation();
      }else{
        pbIsTypeMode=true;
      }//..?
    }else{if(pbIsTypeMode){
      if(pxKeyCode==0x08 && pbTypo.length()>4)
        {pbTypo=pbTypo.substring(0, pbTypo.length()-1);}
      if(pxKey>=' ' && pxKey<='z' && pbTypo.length()<C_MAX_CHAR_L)
        {pbTypo=pbTypo.concat(Character.toString(pxKey));}
    }}//..?
    
    return pbIsTypeMode;
  }//+++
  
  private static void ssReadInput(){
    pbLastAccepted=pbTypo.substring(4, pbTypo.length());
    pbTypo="  $ ";
  }//+++
  
  private static void ssFireOperation(){
    if(pbLastAccepted==null){return;}
    if(pbLastAccepted.isEmpty()){
      pbIsHelperVisible=false;
      ccSetMessage("--");
      if(O_MAP.containsKey("")){O_MAP.get("").ccOperate(C_D_EMPTY);}
      return;
    }//..?
    if(VcConst.ccIsIntegerString(pbLastAccepted)||
       VcConst.ccIsFloatString(pbLastAccepted)
    ){
      if(O_MAP.containsKey(C_T_NUM)){
        O_MAP.get(C_T_NUM).ccOperate(new String[]{C_T_NUM,pbLastAccepted});
      }else{
        ccSetMessage("::number input is not handled:"+pbLastAccepted);
      }return;
    }//..?
    String[] lpLine=pbLastAccepted.split(pbSeparator);
    if(O_MAP.containsKey(lpLine[0])){O_MAP.get(lpLine[0]).ccOperate(lpLine);}
    else{ccSetMessage("::unhandled command:"+pbLastAccepted);}
  }//+++
  
  /**
   * will be stacked to show on watch bar. <br>
   * supposedly to be called from draw loop. <br>
   * an alternative way to the TAGGER. <br>
   * @param pxTag #
   * @param pxValue # 
   */
  public static void ccWatch(String pxTag, Object pxValue){
    if(pbWatchBarContent.length()>=512){return;}
    StringBuilder lpBuilder=new StringBuilder("[");
    lpBuilder.append(pxTag);lpBuilder.append(":");
    lpBuilder.append(pxValue);lpBuilder.append("]");
    pbWatchBarContent+=lpBuilder.toString();
  }//+++
  
  //===
  
  /**
   * 
   * @param pxCommand does check for existence
   * @param pxOperation # 
   */
  public static final 
  void ccAddOperation(String pxCommand, ViOperable pxOperation){
    if(!O_MAP.containsKey(pxCommand)){
      O_MAP.put(pxCommand, pxOperation);
    }//..?
  }//+++
  
  /**
   * tagged command will be ""
   * @param pxOperation #
   */
  public static final 
  void ccAddEmptyOperation(ViOperable pxOperation){
    ccAddOperation("",pxOperation);
  }//+++
  
  /**
   * tagged command will be the reset one 
   * @param pxOperation #
   */
  public static final 
  void ccAddNumericOperation(ViOperable pxOperation){
    ccAddOperation(C_T_NUM,pxOperation);
  }//+++
  
  /**
   * will be shown as a whole page of helper box. 
   * @param pxMessage #
   */
  public static void ccAddHelpMessage(String pxMessage)
    {O_HLP_MSG_LIST.append(pxMessage);}//+++
  
  //===
  
  /**
   * 
   * @param pxFore #
   * @param pxBar #
   * @param pxType #
   */
  public static void ccSetColor
    (int pxFore, int pxBar, int pxType)
  { pbForeColor=pxFore;
    pbBarColor=pxBar;
    pbTypeColor=pxType;
  }//+++
    
  /**
   * the split call will be in ccFireOperation()
   * @param pxSeparator #
   */
  public static final void ccSetSeparator(String pxSeparator){
    if(pxSeparator==null){return;}
    if(pxSeparator.length()!=1){return;}
    pbSeparator=pxSeparator;
  }//+++
  
  /**
   * will be set to default one.
   */
  public static final void ccSetSeparator(){
    pbSeparator=C_DEFAULT_SEP;
  }//+++
  
  /**
   * 
   * @param pxMessage #
   */
  public static void ccSetMessage(String pxMessage)
    {pbMessage=pxMessage;}//+++
  
  /**
   * 
   * @param pxState #
   */
  public static void ccSetMessageBarVisible(boolean pxState)
    {pbIsMessageBarVisible=pxState;}//+++
  
  /**
   * flip version 
   */
  public static void ccSetIsMessageBarVisible()
    {pbIsMessageBarVisible=!pbIsMessageBarVisible;}//+++
  
  /**
   * 
   * @param pxStatus #
   */
  public static void ccSetIsMessageBarVisible(boolean pxStatus)
    {pbIsMessageBarVisible=pxStatus;}//+++
  
  /**
   * 
   * @param pxState #
   */
  public static void ccSetWatchBarVisible(boolean pxState)
    {pbIsWatchBarVisible=pxState;}//+++
  
  /**
   * flip version 
   */
  public static void ccSetIsWatchBarVisible()
    {pbIsWatchBarVisible=!pbIsWatchBarVisible;}//+++
  
  /**
   * 
   * @param pxState #
   */
  public static void ccSetIsWatchBarVisible(boolean pxState)
    {pbIsWatchBarVisible=pxState;}//+++
  
  /**
   * 
   * @param pxState #
   */
  public static void ccSetHelperVisible(boolean pxState)
    {pbIsHelperVisible=pxState;pbHelperIndex=0;}//+++
  
  /**
   * flip version. index will also be set to zero. 
   */
  public static void ccSetIsHelperVisible()
    {pbIsHelperVisible=!pbIsHelperVisible;pbHelperIndex=0;}//+++
  
  /**
   * index will also be set to zero
   * @param pxState #
   */
  public static void ccSetIsHelperVisible(boolean pxState)
    {pbIsHelperVisible=pxState;pbHelperIndex=0;}//+++
  
  //===
  
  /**
   * supposedly can be called from key pressed
   * @param pxOffset will be constrained to fit size
   */
  public static void ccShiftHelperIndex(int pxOffset){
    if(!pbIsHelperVisible){return;}
    if(O_HLP_MSG_LIST.size()<=0){return;}
    pbHelperIndex+=pxOffset;
    pbHelperIndex=PApplet.constrain(pbHelperIndex, 0, O_HLP_MSG_LIST.size()-1);
  }//+++
  
  /**
   * must be called at the end of draw()
   */
  public static void ccStabilize(){pbWatchBarContent=C_INVALID;}//+++
  
}//***eof
