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
package kosui.ppplocalui;

import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * still the only basic element i can image 
 * is a string in a rectangle.<br>
 * but we can not just apply it from the stuff from the screen.<br>
 */
public class EcLabelPDF extends EcRect implements EiDrawable{

  private String cmText="";
  private float cmTextSize=9f;
  private char cmTextAlign='X';
  
  //===

  /**
   * NOTE: 
   * YOUR ARE SUPPOSED TO IMPORT processing.pdf.PGraphicsPDF SEPARATELY.<br>
   * the initiated text is empty.<br>
   */
  public EcLabelPDF() {
    super();
  }//..!

  /**
   * NOTE: 
   * YOUR ARE SUPPOSED TO IMPORT processing.pdf.PGraphicsPDF SEPARATELY.<br>
   * @param pxText will get nulled out
   */
  public EcLabelPDF(String pxText) {
    super();
    ccSetText(pxText);
  }//..!
  
  //===
  
  /**
   * {@inheritDoc }
   * @param pxG ##
   */
  @Override public void ccDraw(PGraphics pxG) {
    if(pxG==null){return;}
    pxG.pushStyle();
    {
      pxG.stroke(0x0);
      pxG.rect(cmX, cmY, cmW, cmH);
      if(VcConst.ccIsValidString(cmText)){
        pxG.fill(0x0);
        pxG.textSize(cmTextSize);
        switch(cmTextAlign){
          case 'N':
            pxG.textAlign(PApplet.CENTER, PApplet.TOP);
            pxG.text(cmText, ccCenterX(), ccGetY());
          break;
          case 'S':
            pxG.textAlign(PApplet.CENTER, PApplet.BOTTOM);
            pxG.text(cmText, ccCenterX(), ccEndY());
          break;
          case 'W':
            pxG.textAlign(PApplet.LEFT, PApplet.CENTER);
            pxG.text(cmText, ccGetX(), ccCenterY());
          break;
          case 'E':
            pxG.textAlign(PApplet.RIGHT, PApplet.CENTER);
            pxG.text(cmText, ccEndX(), ccCenterY());
          break;
          //--
          case 'a':
            pxG.textAlign(PApplet.LEFT, PApplet.TOP);
            pxG.text(cmText, ccGetX(), ccGetY());
          break;
          case 'b':
            pxG.textAlign(PApplet.RIGHT, PApplet.TOP);
            pxG.text(cmText, ccEndX(), ccGetY());
          break;
          case 'c':
            pxG.textAlign(PApplet.RIGHT, PApplet.BOTTOM);
            pxG.text(cmText, ccEndX(), ccEndY());
          break;
          case 'd':
            pxG.textAlign(PApplet.LEFT, PApplet.BOTTOM);
            pxG.text(cmText, ccGetX(), ccEndY());
          break;
          //--
          case 'X':
            pxG.textAlign(PApplet.CENTER, PApplet.CENTER);
            pxG.text(cmText, ccCenterX(), ccCenterY());
          break;
          default:break;
        }//..?
        pxG.noFill();
        pxG.noStroke();
      }//..?
    }
    pxG.popStyle();
  }//+++
  
  //===

  /**
   * @param pxText will get nulled out
   */
  public final void ccSetText(String pxText){
    cmText=VcStringUtility.ccNulloutString(pxText);
  }//+++

  /**
   * @param pxSize will get constrained to 6f-72f
   */
  public final void ccSetTextSize(float pxSize){
    cmTextSize=PApplet.constrain(pxSize, 6f, 72f);
  }//+++

  /**
   * <pre>
   * mode:
   *  - [a]:align top left as point 'a'
   *  - [N]:align top as point 'north'
   *  - [b]:align top right as point 'b'
   *  - [E]:align right as point 'east'
   *  - [c]:align bottom right as point 'c'
   *  - [S]:align bottom as point 'south'
   *  - [d]:align bottom left as point 'd'
   *  - [W]:align left as point 'west'
   *  - [X]:align center
   *  - [x]:no effect or mass anything
   * </pre>
   * @param pxMode_NSWEabcdX see above
   */
  public final void ccSetTextAlign(char pxMode_NSWEabcdX){
    cmTextAlign=pxMode_NSWEabcdX;
  }//+++
  
  /**
   * alias for PGraphics::textWidth.<br>
   * @param pxOwner do not pass null
   * @return minus one if null passed
   */
  public final float ccGetRawW(PGraphics pxOwner){
    if(pxOwner==null){return -1;}
    return pxOwner.textWidth(cmText);
  }//+++
  
  /**
   * adding from PGraphics::textAscent and PGraphics::textDescent.<br>
   * @param pxOwner do not pass null
   * @return minus one if null passed
   */
  public final float ccGetRawH(PGraphics pxOwner){
    if(pxOwner==null){return -1;}
    return pxOwner.textAscent()+pxOwner.textDescent();
  }//+++
  
  /**
   * auto sizing with current text based on given graphics.<br>
   * @param pxOwner no not pass null
   */
  public final void ccSetSize(PGraphics pxOwner){
    if(pxOwner==null){return;}
    ccSetSize(
      PApplet.ceil(ccGetRawH(pxOwner)),
      PApplet.ceil(ccGetRawW(pxOwner))
    );
  }//+++

}//***eof
