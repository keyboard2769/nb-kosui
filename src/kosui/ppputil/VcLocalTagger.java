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
import static processing.core.PApplet.ceil;
import static kosui.ppputil.VcConst.ccIsValidString;

/**
 * draw string and value as tags on the screen.<br>
 * vertically flows down(raw) the screen and turns to right(column).<br>
 */
public final class VcLocalTagger{
  
  /**
   * @return instance
   */
  public static final VcLocalTagger ccGetInstance(){
    if(self==null){self=new VcLocalTagger();}
    return self;
  }//+++
  private static VcLocalTagger self = null;
  private VcLocalTagger(){}//..!
  
  //===
  
  private PApplet cmOwner=null;
  
  private int
    //-- count
    cmCounter=0,
    cmRowWrap=8,
    //-- pix
    cmTagHeight=EcConst.C_DEFAULT_TEXT_HEIGHT,
    cmGapX=100,
    cmGapY=cmTagHeight+2,
    cmAnchorX=5,
    cmAnchorY=20,
    //-- ARGB
    cmBackground=0x99666666,
    cmForeround =0xFF33EE33
  ;//...
  
  private float cmTagWidthCoeff=1.02f;
  
  private boolean
    cmIsVisible = true,
    cmAsBar = false
  ;//..?
  
  //===
  
  /**
   * <pre>
   * should get invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * if you want to change the raw setting
   *   you can call the setter anywhere.
   * </pre>
   * @param pxOwner your sketch
   * @param pxRow wrap count .. bigger than three or AUTO 
   * @param pxVisible anyway
   */
  public final void ccInit(PApplet pxOwner, int pxRow, boolean pxVisible){
    if(pxOwner==null){return;}
    if(cmOwner==null){cmOwner=pxOwner;}
    int lpTextHeight = (int)(pxOwner.textAscent()+pxOwner.textDescent());
    if(lpTextHeight>EcConst.C_DEFAULT_AUTOSIZE_HEIGHT){
      cmTagHeight=lpTextHeight+2;
      cmGapY=cmTagHeight+2;
    }//..?
    if(pxRow>3){
      ccSetRowWrap(pxRow);
    }else{
      ccSetRowWrap((pxOwner.height*2/3)/cmGapY);
    }//..?
    ccSetIsVisible(pxVisible);
  }//..!
  
  /**
   * <pre>
   * should get invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * if you want to change the raw setting
   *   you can call the setter anywhere.
   * </pre>
   * visibility is true by default.<br>
   * @param pxOwner your sketch
   * @param pxRow max row count, DONT PASS ZERO!!
   */
  public final void ccInit(PApplet pxOwner, int pxRow){
    ccInit(pxOwner, pxRow, true);
  }//..!
  
  /**
   * <pre>
   * should get invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * if you want to change the raw setting
   *   you can call the setter anywhere.
   * </pre>
   * row wrap is auto calculated.<br>
   * @param pxOwner your sketch
   * @param pxVisible anyway
   */
  public final void ccInit(PApplet pxOwner, boolean pxVisible){
    ccInit(pxOwner, -1, pxVisible);
  }//..!
  
  /**
   * <pre>
   * should get invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * if you want to change the raw setting
   *   you can call the setter anywhere.
   * </pre>
   * row wrap is auto calculated.<br>
   * visibility is true by default.<br>
   * @param pxOwner your sketch
   */
  public final void ccInit(PApplet pxOwner){
    ccInit(pxOwner,-1,true);
  }//..!
  
  //===
  
  /**
   * gap count from start point but not end. 
   * should be bigger than actual text height and width.
   * @param pxGapX 0~255 pix
   * @param pxGapY 0~255 pix
   */
  public final void ccSetGap(int pxGapX, int pxGapY){
    cmGapX=pxGapX&0xFF;
    cmGapY=pxGapY&0xFF;
  }//+++
  
  /**
   * for 320*240 size, 7 is recommended. 
   * @param pxRow 1~31
   */
  public final void ccSetRowWrap(int pxRow){
    cmRowWrap=(pxRow&0x1F)|0x1;
  }//+++
  
  /**
   * set how every blocks auto size its self
   * @param pxHeight 0~255pix
   * @param pxWCoeff 1.0~2.0
   */
  public final void ccSetTagSize(int pxHeight, float pxWCoeff){
    cmTagHeight=pxHeight&0xFF;
    cmTagWidthCoeff=pxWCoeff<1f?1f
      :(pxWCoeff>2f?2f:pxWCoeff);
  }//+++
  
  /**
   * 
   * @param pxOffsetX pix
   * @param pxOffsetY pix
   */
  public final void ccSetLocationOffset(int pxOffsetX, int pxOffsetY){
    cmAnchorX=pxOffsetX;
    cmAnchorY=pxOffsetY;
  }//+++
  
  /**
   * 
   * @param pxFore ARGB
   * @param pxBack ARGB
   */
  public final void ccSetColor(int pxFore, int pxBack){
    cmForeround=pxFore;
    cmBackground=pxBack;
  }//+++
  
  /**
   * flips visibility
   */
  public final void ccSetIsVisible(){
    cmIsVisible=!cmIsVisible;
  }//+++
  
  /**
   * 
   * @param pxStatus #
   */
  public final void ccSetIsVisible(boolean pxStatus){
    cmIsVisible=pxStatus;
  }//+++
  
  /**
   * this is a compromise to replace the old "watch bar" style 
   * @param pxStatus #
   */
  public final void ccSetAsBar(boolean pxStatus){
    cmAsBar=pxStatus;
  }//+++
  
  //===
  
  private void ssUpdate(String pxTag){
    if(!cmIsVisible){return;}
    if(cmOwner==null){return;}
    if(!ccIsValidString(pxTag)){return;}
    //--
    int lpW=ceil(cmOwner.textWidth(pxTag)*cmTagWidthCoeff);
    int lpX;
    int lpY;
    if(cmAsBar){
      lpX=(cmCounter%cmRowWrap)*cmGapX+cmAnchorX;
      lpY=(cmCounter/cmRowWrap)*cmGapY+cmAnchorY;
    }else{
      lpX=(cmCounter/cmRowWrap)*cmGapX+cmAnchorX;
      lpY=(cmCounter%cmRowWrap)*cmGapY+cmAnchorY;
    }//..?
    //--
    cmOwner.fill(cmBackground);
    cmOwner.rect(lpX, lpY, lpW, cmTagHeight);
    //--
    cmOwner.fill(cmForeround);
    cmOwner.text(pxTag, lpX, lpY);
    //--
    cmCounter++;
  }//+++
  
  //===
  
  /**
   * <b>MUST BE INITIATED</b><br>
   * <b>MUST BE STABILIZED</b><br>
   * @param pxLine #
   */
  static public void ccTag(String pxLine){
    self.ssUpdate(pxLine);
  }//+++
  
  /**
   * <b>MUST BE INITIATED</b><br>
   * <b>MUST BE STABILIZED</b><br>
   * @param pxTag #
   * @param pxValue #
   */
  static public void ccTag(String pxTag, Object pxValue){
    if(pxValue==null){ccTag(pxTag);}
    else{self.ssUpdate(pxTag+":"+pxValue.toString());}
  }//+++
  
  /**
   * must be called at the end of draw()
   */
  static public void ccStabilize(){
    self.cmCounter=0;
  }//+++
  
}//***eof
