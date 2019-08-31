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

import static processing.core.PApplet.ceil;
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
    cmIsActivated = false,
    cmIsEnabled   = true,
    cmIsVisible   = true
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
   * {@inheritDoc}
   */
  @Override public void ccUpdate(){if(!cmIsVisible){return;}
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
  protected final void ccActFill()
    {pbOwner.fill(cmIsActivated?cmOnColor:cmOffColor);}//+++
  
  /**
   * inward use only
   * @return color
   */
  protected final int ccActColor()
    {return cmIsActivated?cmOnColor:cmOffColor;}//+++
  
  //===
  
  /**
   * 
   * @param pxID #
   */
  public final void ccSetID(int pxID){cmID=pxID;}//+++
  
  /**
   * 
   * @param pxName #
   */
  public final void ccSetName(String pxName)
    {cmName=pxName;}//+++
  
  /**
   * 
   * @param pxText #
   */
  public final void ccSetText(String pxText)
    {cmText=pxText;}//+++
  
  /**
   * 
   * @param pxMode_ablr
   * <div> 'a': above </div>
   * <div> 'b': below </div>
   * <div> 'l': left </div>
   * <div> 'r': right </div>
   * <div> 'x': (or you can pass anything) hidden </div>
   */
  public final void ccSetNameAlign (char pxMode_ablr)
    {cmNameAlign=pxMode_ablr;}//+++
    
  /**
   * 
   * @param pxMode_lcr 
   * <div> 'l': left </div>
   * <div> 'c': center </div>
   * <div> 'r': right </div>
   * <div> 'x': (or you can pass anything) same as center </div>
   */
  public final void ccSetTextAlign(char pxMode_lcr)
    {cmTextAlign=pxMode_lcr;}//+++
  
  /**
   * 
   * @param pxOn #
   * @param pxOff #
   */
  public final void ccSetColor(int pxOn, int pxOff)
    {cmOnColor=pxOn;cmOffColor=pxOff;}//+++
  
  /**
   * 
   * @param pxOn #
   */
  public final void ccSetColor(int pxOn)
    {cmOnColor=pxOn;}//+++
  
  /**
   * 
   * @param pxColor #
   */
  public final void ccSetNameColor(int pxColor)
    {cmNameColor=pxColor;}//+++
  
  /**
   * 
   * @param pxColor #
   */
  public final void ccSetTextColor(int pxColor)
    {cmTextColor=pxColor;}//+++

  /**
   * 
   * @param pxStatus #
   */
  public final void ccSetIsActivated(boolean pxStatus)
    {cmIsActivated=pxStatus;}//+++
  
  /**
   * flips activity
   */
  public final void ccSetIsActivated()
    {cmIsActivated=!cmIsActivated;}//+++
  
  /**
   * a alias for comparing id using focus logic
   * @param pxFocusedID activates this element if equals to his id
   */
  public final void ccSetIsActivated(int pxFocusedID)
    {cmIsActivated=(cmID==pxFocusedID);}//+++
  
  /**
   * 
   * @param pxStatus #
   */
  public final void ccSetIsEnabled(boolean pxStatus)
    {cmIsEnabled=pxStatus;}//+++
  
  /**
   * 
   * @param pxStatus #
   */
  public final void ccSetIsVisible(boolean pxStatus)
    {cmIsVisible=pxStatus;}//+++
  
  //===

  /**
   * automatically change the size based on the text 
   * currently set
   */
  public final void ccSetSize(){
    
    cmW = ceil(pbOwner.textWidth(cmText))
          +EcConst.C_DEFAULT_AUTOSIZE_MARGIN*2;
    cmH = EcConst.C_DEFAULT_AUTOSIZE_HEIGHT;
    
    for(char it:cmText.toCharArray())
      {if(it=='\n'){cmH+=EcConst.C_DEFAULT_AUTOSIZE_HEIGHT;}}
    
  }//+++

  /**
   * 
   * @param pxKey #
   */
  public final void ccSetKey(String pxKey)
    {cmKey=pxKey;}//+++
  
  //=== 
  
  /**
   * set both key and name and text
   * @param pxKey #
   */
  public final void ccSetupKey(String pxKey){
    cmKey=pxKey;
    cmName=pxKey;
    cmText=pxKey;
  }//+++

  /**
   * apply on/off color and name align
   * @param pxTarget #
   */
  public final void ccMatchStyle(EcElement pxTarget){
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
  public final int ccTellMouseID()
    {return ccIsMouseHovered()?cmID:0;}//+++
  
  /**
   * 
   * @return #
   */
  public final boolean ccIsActivated()
    {return cmIsActivated;}//+++
  
  /**
   * 
   * @return true only when enabled
   */
  public final boolean ccIsMouseHovered()
    {return ccContains(pbOwner.mouseX, pbOwner.mouseY)&&cmIsEnabled;}//+++
  
  /**
   * 
   * @return just anded mouse pressed flag from your sketch
   */
  public final boolean ccIsMousePressed()
    {return ccIsMouseHovered()&&pbOwner.mousePressed;}//+++
  
  /**
   * 
   * @return #
   */
  public final int ccGetID()
    {return cmID;}//+++
  
  /**
   * 
   * @return #
   */
  public final String ccGetText()
    {return cmText;}//+++
  
}//***eof
