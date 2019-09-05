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

import kosui.ppplocalui.EcConst;
import processing.core.PApplet;
import static processing.core.PConstants.BOTTOM;
import static processing.core.PConstants.LEFT;
import static processing.core.PApplet.constrain;

import kosui.pppmodel.McTextStoker;

/**
 * i used to wonder how should i handle the in window print problem, 
 * i gave the scroll thing up, and i got this.<br>
 * this is a wrapper for McLineStacker.<br>
 */
public final class VcLocalStoker {
  
  /**
   * you can decide when to retrieve and save based on this size
   */
  public static final int C_BUFFER_SIZE = 64;
  
  /**
   * get stacked after clear by default
   */
  private static final String C_DEFAULT_MESSAGE=".kosui::";
  
  /**
   * @return instance
   */
  public static final VcLocalStoker ccGetInstance(){
    if(self==null){self=new VcLocalStoker();}
    return self;
  }//+++
  private static VcLocalStoker self = null;
  private VcLocalStoker (){}//..!
  
  //===
  
  private PApplet cmOwner=null;
  
  private boolean cmIsVisible=true;
  
  private int
    //-- color
    //[abort]::cmConsoleColor,cmStackColor
    cmThemeColor=0xFF33EE33,
    //-- pix
    cmBaseLineOffsetY=40
  ;//...
  
  private int cmMaxLine,cmMaxLength;
  
  private final McTextStoker cmModel = new McTextStoker(C_BUFFER_SIZE);
  
  //===
  
  /**
   * <pre>
   * it is invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * </pre>
   * @param pxOwner your sketch
   */
  public final void ccInit(PApplet pxOwner){
    if(pxOwner==null){return;}
    if(cmOwner==null){cmOwner=pxOwner;}
    cmMaxLine=cmOwner.height/EcConst.C_DEFAULT_TEXT_HEIGHT;
    cmMaxLength=64;//[abort]::cmOwner.width/EcConst.C_DEFAULT_TEXT_WIDTH
  }//..!
  
  /**
   * @param pxColor both text and base line will be this color
   */
  public final void ccSetColor(int pxColor){
    cmThemeColor=pxColor;
  }//+++
  
  /**
   * <pre>
   * a base line will be placed on the scree,
   *   it is also where the text starts.
   * </pre>
   * @param pxOffsetY to the bottom
   */
  public final void ccSetBaseLineOffsetY(int pxOffsetY){
    cmBaseLineOffsetY=pxOffsetY;
  }//+++
  
  /**
   * max character number of a single line.<br>
   * @param pxCount 3~512
   */
  public final void ccMaxLineLength(int pxCount){
    cmMaxLength=constrain(pxCount, 3, 512);
  }//+++
  
  /**
   * @param pxState #
   */
  public final void ccSetIsVisible(boolean pxState){
    cmIsVisible=pxState;
  }//+++
  
  /**
   * flip version
   */
  public final void ccSetIsVisible(){
    cmIsVisible=!cmIsVisible;
  }//+++
  
  //===
  
  /**
   * supposedly should get called inside PApplet.draw() loop
   */
  public static final void ccUpdate(){
    self.ssUpdate();
  }//+++
  
  private void ssUpdate(){
    if(cmOwner==null || !cmIsVisible){return;}
    int lpOffset=cmOwner.height-cmBaseLineOffsetY;
    cmOwner.pushStyle();{
      cmOwner.fill(cmThemeColor);
      cmOwner.textAlign(LEFT, BOTTOM);
      for(int i=0;i<cmMaxLine;i++){
        cmOwner.text(cmModel.ccGet(i), 5,
          lpOffset-i*EcConst.C_DEFAULT_TEXT_HEIGHT
        );
      }//..~
      cmOwner.stroke(cmThemeColor);
      cmOwner.line(0,lpOffset,cmOwner.width,lpOffset);
    }cmOwner.popStyle();
  }//+++
  
  /**
   * will get passed to McLineStacker.ccStack() directly.<br>
   * @param pxTag #
   * @param pxVal #
   */
  static public final void ccStokeln(String pxTag, Object pxVal){
    self.cmModel.ccStokeln(pxTag, pxVal);
  }//+++
  
  /**
   * a wrapped input will get passed to McLineStacker.ccStack() directly.<br>
   * @param pxLine #
   */
  static public final void ccStokeln(String pxLine){
    self.cmModel.ccStokeln(VcStringUtility.ccWrap(pxLine, self.cmMaxLength));
  }//+++
  
  /**
   * set stacked to default message held by the system stacker.
   */
  static public final void ccClear(){
    self.cmModel.ccClear(C_DEFAULT_MESSAGE);
  }//+++
  
  /**
   * @param pxClearMessage clear with this alternatively
   */
  static public final void ccClear(String pxClearMessage){
    self.cmModel.ccClear(pxClearMessage);
  }//+++
  
}//***eof
