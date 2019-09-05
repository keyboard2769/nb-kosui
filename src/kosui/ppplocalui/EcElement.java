/*
 * Copyright (C) 2018 Key Parker from K.I.C 
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

import kosui.ppputil.VcConst;

import static processing.core.PApplet.TOP;
import static processing.core.PApplet.BOTTOM;
import static processing.core.PApplet.LEFT;
import static processing.core.PApplet.RIGHT;
import static processing.core.PApplet.CENTER;

/**
 * <pre>
 * in the very first i thought that only thing can react to you 
 *   should be called "element" while actually everybody call it
 *   "component".
 * i still do so. you see, "element" is a "component",
 *   and "shape" too is a "component".<br>
 * </pre>
 */
public class EcElement extends EcComponent{
  
  private static final int
    //-- pix
    C_NAME_GAP          =  2,
    C_TEXT_MARG_X       =  3,
    C_TEXT_ADJ_Y        = -2
  ;//...
  
  //===
  
  /**
   * basic field
   */
  protected int
    cmID        = EcConst.C_ID_IGNORE,
    cmOnColor   = EcConst.C_YELLOW,
    cmOffColor  = EcConst.C_DIM_GRAY,
    cmNameColor = EcConst.C_LIT_GRAY,
    cmTextColor = EcConst.C_DARK_GRAY
  ;//...
  
  /**
   * basic field
   */
  protected String
    cmKey  ="<key/>",
    cmName ="<name/>",
    cmText ="<text/>"
  ;//...
  
  /**
   * basic field
   */
  protected boolean
    cmIsActivated = false
  ;//...
  
  /**
   * see the method doc
   */
  protected char
    cmNameAlign = 'x',
    cmTextAlign = 'c'
  ;//...
  
  //===

  /**
   * default ID is ignored.<br>
   * default texts is tagged.<br>
   */
  public EcElement(){
    super();
  }//..!
  
  /**
   * rest of those initiated values might be eight.
   * @param pxKey will get passed to setter directly
   * @param pxID will get passed to setter directly
   */
  public EcElement(String pxKey, int pxID){
    super();
    ccSetupKey(pxKey);
    ccSetID(pxID);
    ccSetSize();
  }//..!
  
  //===
  
  
  /**
   * {@inheritDoc}
   */
  @Override public void ccUpdate(){
    if(!ccIsVisible()){return;}
    drawRect(ccActColor());
    drawText(cmTextColor);
    drawName(cmNameColor);
  }//+++

  /**
   * inward use only
   * @param pxColor #
   */
  protected final void drawText(int pxColor){
    int lpX=ccCenterX();
    pbOwner.textAlign(CENTER, CENTER);
    switch(cmTextAlign){
      
      case 'l':
        lpX=cmX+C_TEXT_MARG_X;
        pbOwner.textAlign(LEFT, CENTER);
      break;
      
      case 'r':
        lpX=ccEndX()-C_TEXT_MARG_X;
        pbOwner.textAlign(RIGHT, CENTER);
      break;
      
    }//..?
    pbOwner.fill(pxColor);
    pbOwner.text(cmText,lpX,ccCenterY()+C_TEXT_ADJ_Y);
    pbOwner.textAlign(LEFT,TOP);
  }//+++

  /**
   * inward use only
   * @param pxColor #
   */
  protected final void drawName(int pxColor){
    int lpX=ccCenterX();
    int lpY=ccCenterY();
    switch (cmNameAlign) {
      case 'a':
        lpY=cmY-C_NAME_GAP;pbOwner.textAlign(CENTER, BOTTOM);
        break;
      case 'b':
        lpY=C_NAME_GAP+cmY+cmH;pbOwner.textAlign(CENTER, TOP);
        break;
      case 'l':
        lpX=cmX-C_NAME_GAP;pbOwner.textAlign(RIGHT , CENTER);
        break;
      case 'r':
        lpX=C_NAME_GAP+cmX+cmW;pbOwner.textAlign(LEFT  , CENTER);
        break;
      default:return;
    }
    //--
    pbOwner.fill(pxColor);
    pbOwner.text(cmName,lpX,C_TEXT_ADJ_Y+lpY);
    pbOwner.textAlign(LEFT,TOP);
  }//+++

  /**
   * inward use only
   */
  protected final void ccActFill(){
    pbOwner.fill(cmIsActivated?cmOnColor:cmOffColor);
  }//+++
  
  /**
   * inward use only
   * @return color
   */
  protected final int ccActColor(){
    return cmIsActivated?cmOnColor:cmOffColor;
  }//+++
  
  //===
  
  /**
   * an id is effectively serve as a key for a element
   * at various situation with undefined meaning.<br>
   * @param pxID #
   */
  public final void ccSetID(int pxID){
    cmID=pxID;
  }//+++
  
  /**
   * a name is something to get displayed outside the element
   * as a attached label.<br>
   * @param pxName can be empty but not null
   */
  public final void ccSetName(String pxName){
    if(pxName==null){return;}
    cmName=pxName;
  }//+++
  
  /**
   * a text is something to get displayed inside the element
   * as a content.<br>
   * @param pxText can be empty but not null
   */
  public final void ccSetText(String pxText){
    if(pxText==null){return;}
    cmText=pxText;
  }//+++
  
  /**
   * <pre>
   * mode:
   *  - 'a':above
   *  - 'b':below
   *  - 'l':left
   *  - 'r':right
   *  - 'x':hidden(or you can pass anything)
   * </pre>
   * @param pxMode_ablr # 
   */
  public final void ccSetNameAlign (char pxMode_ablr){
    cmNameAlign=pxMode_ablr;
  }//+++
    
  /**
   * <pre>
   * mode:
   *  - 'l':left
   *  - 'c':center(or you can pass anything)
   *  - 'r':right
   * </pre>
   * @param pxMode_lcr #
   */
  public final void ccSetTextAlign(char pxMode_lcr){
    cmTextAlign=pxMode_lcr;
  }//+++
  
  /**
   * 
   * @param pxOn ARGB
   * @param pxOff ARGB
   */
  public final void ccSetColor(int pxOn, int pxOff){
    cmOnColor=pxOn;cmOffColor=pxOff;
  }//+++
  
  /**
   * 
   * @param pxOn ARGB
   */
  public final void ccSetColor(int pxOn){
    cmOnColor=pxOn;
  }//+++
  
  /**
   * 
   * @param pxColor ARGB
   */
  public final void ccSetNameColor(int pxColor){
    cmNameColor=pxColor;
  }//+++
  
  /**
   * 
   * @param pxColor ARGB
   */
  public final void ccSetTextColor(int pxColor){
    cmTextColor=pxColor;
  }//+++

  /**
   * the activeness only represent the on/off status.<br>
   * @param pxStatus #
   */
  public final void ccSetIsActivated(boolean pxStatus){
    cmIsActivated=pxStatus;
  }//+++
  
  /**
   * flips activity
   */
  public final void ccSetIsActivated(){
    cmIsActivated=!cmIsActivated;
  }//+++
  
  /**
   * a alias for comparing id using focus logic
   * @param pxFocusedID activates this element if equals to his id
   */
  public final void ccSetIsActivated(int pxFocusedID){
    cmIsActivated=(cmID==pxFocusedID);
  }//+++
  
  //===

  /**
   * pass text set directly to EcRect.ccSetSize(String).<br>
   */
  public final void ccSetSize(){
    ccSetSize(cmText);
  }//+++

  /**
   * <pre>
   * a key should be a pure ASCII defined string
   *   which can later serve as a key for localization translation.
   * </pre>
   * @param pxKey must have some thing 
   */
  public final void ccSetKey(String pxKey){
    if(!VcConst.ccIsValidString(pxKey)){return;}
    cmKey=pxKey;
  }//+++
  
  //=== 
  
  /**
   * set both key and name and text to the same value.<br>
   * @param pxValue thus the value is the key it must have something
   */
  public final void ccSetupKey(String pxValue){
    if(!VcConst.ccIsValidString(pxValue)){return;}
    cmKey=pxValue;
    cmName=pxValue;
    cmText=pxValue;
  }//+++

  /**
   * color and alignment and well get fetched.<br>
   * @param pxTarget #
   */
  public void ccSetupStyle(EcElement pxTarget){
    cmOnColor=pxTarget.cmOnColor;
    cmOffColor=pxTarget.cmOffColor;
    cmNameAlign=pxTarget.cmNameAlign;
    cmNameColor=pxTarget.cmNameColor;
    cmTextAlign=pxTarget.cmTextAlign;
    cmTextColor=pxTarget.cmTextColor;
  }//+++

  //===
  
  /**
   * 
   * @return id only when is mouse over.
   */
  public final int ccTellMouseID(){
    return ccIsMouseHovered()?cmID:0;
  }//+++
  
  /**
   * 
   * @return #
   */
  public final boolean ccIsActivated(){
    return cmIsActivated;
  }//+++
  
  /**
   * 
   * @return true only when enabled
   */
  public final boolean ccIsMouseHovered(){
    if(!ccIsEnabled()){return false;}
    return ccContains(pbOwner.mouseX, pbOwner.mouseY);
  }//+++
  
  /**
   * 
   * @return just anded mouse pressed flag from your sketch
   */
  public final boolean ccIsMousePressed(){
    return ccIsMouseHovered()
         &&pbOwner.mousePressed;
  }//+++
  
  /**
   * @return pix compared from left side edge
   */
  public final int ccGetMouseOffsetX(){
    if(ccIsMousePressed()){
      return pbOwner.mouseX-ccGetX();
    }else{
      return 0;
    }//..?
  }//+++
  
  /**
   * @return pix compared from up side edge
   */
  public final int ccGetMouseOffsetY(){
    if(ccIsMousePressed()){
      return pbOwner.mouseY-ccGetY();
    }else{
      return 0;
    }//..?
  }//+++
  
  /**
   * 
   * @return #
   */
  public final int ccGetID(){
    return cmID;
  }//+++
  
  /**
   * 
   * @return #
   */
  public final String ccGetText(){
    return cmText;
  }//+++
  
}//***eof
