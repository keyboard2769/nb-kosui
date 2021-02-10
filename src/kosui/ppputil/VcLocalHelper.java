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

import processing.core.PApplet;
import processing.data.StringList;

/**
 * i uesd to write long long table as inner help information
 * for my touch screen project.<br>
 * maybe simply opening a well formed html is more suitable 
 * for a desktop project, but for some reason, i made this.<br>
 */
public final class VcLocalHelper {
  
  private static final int
    //-- pix
    C_TEXT_ADJ_X = 2,
    C_TEXT_ADJ_Y = 4
  ;//...
  
  //===
  
  private static PApplet cmOwner=null;
  
  private static final StringList O_LIST_OF_MESSAGE
   = new StringList();
  
  private static boolean cmIsHelperVisible = false;
  
  private static int
    //-- index
    cmHelperIndex = 0,
    //-- pix
    cmOwnerWidth  = 800,//.. arbitrary by default value
    cmOwnerHeight = 600,//.. arbitrary by default value
    //-- color
    cmBaseColor = 0xCCCCCCCC,
    cmTextColor = 0xFF111111
  ;//...
  
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
    }//..?
  }//..!
  
  //===
  
  /**
   * should be called inside draw()
   */
  public static final void ccUpdate(){
    if(cmIsHelperVisible && O_LIST_OF_MESSAGE.size()>0){
      cmOwner.fill(cmBaseColor);
      cmOwner.rect(
        cmOwnerWidth/4,cmOwnerHeight/4,
        cmOwnerWidth/2,cmOwnerHeight/2
      );
      cmOwner.fill(cmTextColor);
      cmOwner.text(O_LIST_OF_MESSAGE.get(cmHelperIndex),
        cmOwnerWidth/4+C_TEXT_ADJ_X,cmOwnerWidth/4+C_TEXT_ADJ_Y
      );
    }//..?
  }//+++
  
  //===
  
  /**
   * will be shown as a whole page of helper box.<br>
   * @param pxMessage must have something
   */
  public static void ccAddHelpMessage(String pxMessage){
    if(!VcConst.ccIsValidString(pxMessage)){return;}
    //[todo]::can we wrap it??
    O_LIST_OF_MESSAGE.append(pxMessage);
  }//+++
  
  /**
   * @param pxColor ARGB
   */
  public static final void ccSetTextColor(int pxColor){
    cmTextColor=pxColor;
  }//+++
  
  /**
   * @param pxColor ARGB
   */
  public static final void ccSetBaseColor(int pxColor){
    cmBaseColor=pxColor;
  }//+++
  
  /**
   * @param pxBase ##
   * @param pxText ##
   */
  public static final void ccSetupColor(int pxBase, int pxText){
    cmBaseColor=pxBase;
    cmTextColor=pxText;
  }//+++
  
  /**
   * flip version. index will also be set to zero.<br>
   */
  public static final void ccSetVisible(){
    cmIsHelperVisible=!cmIsHelperVisible;
    cmHelperIndex=0;
  }//+++
  
  /**
   * index will also be set to zero.<br>
   * @param pxState #
   */
  public static final void ccSetVisible(boolean pxState){
    cmIsHelperVisible=pxState;
    cmHelperIndex=0;
  }//+++
  
  /**
   * supposedly can be called from key pressed
   * @param pxOffset will be constrained to fit size
   */
  public static final void ccShiftHelperIndex(int pxOffset){
    if(!cmIsHelperVisible){return;}
    if(O_LIST_OF_MESSAGE.size()<=0){return;}
    cmHelperIndex+=pxOffset;
    cmHelperIndex=PApplet.constrain(cmHelperIndex,
      0, O_LIST_OF_MESSAGE.size()-1
    );
  }//+++
  
 }//***eof
