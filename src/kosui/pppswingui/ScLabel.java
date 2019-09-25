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

package kosui.pppswingui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import kosui.ppplocalui.EcRect;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;

/**
 * you might ask what's wrong with JLabel then you need this one.<br>
 * well le'me tel'lya, absolutely, nothing.<br>
 */
public class ScLabel extends EcRect implements SiPaintable{
  
  /**
   * offset from left bottom corner
   */
  public static final int C_INPANE_MERGIN = 2;
  
  private String cmText;
  
  private int cmTextOffsetX, cmTextOffsetY;
  
  private Color cmBaseColor,cmThemeColor;
  
  private boolean ccHasBorder;
  
  private boolean cmIsReversed;

  /**
   * <pre>
   * since in swing i do not know who is supposed to tell me about
   *   character size, there will not be any auto sizing utility.
   * </pre>
   * @param pxText will get nulled out
   * @param pxW pix
   * @param pxH pix
   */
  public ScLabel(String pxText, int pxW, int pxH){
    super(pxW, pxH);
    cmText = VcStringUtility.ccNulloutString(pxText);
    ssInit();
  }//..!
  
  /**
   * <pre>
   * the initial size is 8x8 from the EcRect class.
   * it is assumed the size will got set later if calling this.
   * </pre>
   * @param pxText will get nulled out
   */
  public ScLabel(String pxText){
    super();
    cmText = VcStringUtility.ccNulloutString(pxText);
    ssInit();
  }//..!
  
  private void ssInit(){
    cmBaseColor=Color.GRAY;
    cmThemeColor=Color.BLACK;
    ccHasBorder=false;
    cmIsReversed=false;
    cmTextOffsetX=ccGetX();
    cmTextOffsetY=ccEndY();
  }//..!
  
  //===
  
  /**
   * {@inheritDoc }
   * @param pxGI ##
   */
  @Override public void ccPaint(Graphics pxGI){
            
    //-- border
    if(ccHasBorder){
      pxGI.setColor(cmThemeColor);
      if(cmIsReversed){
        pxGI.fillRect(cmX, cmY, cmW, cmH);
      }else{
        pxGI.drawRect(cmX, cmY, cmW-1, cmH-1);
      }//..?
    }//..?
    
    //-- text
    pxGI.setColor(cmIsReversed?cmBaseColor:cmThemeColor);
    pxGI.drawString(cmText, cmX+cmTextOffsetX, cmY+cmTextOffsetY);
    
  }//+++
  
  //===
  
  /**
   * @param pxColor supposedly same as its owner's background
   */
  public final void ccSetBaseColor(Color pxColor){
    if(pxColor==null){return;}
    cmBaseColor=pxColor;
  }//+++
  
  /**
   * @param pxColor supposedly for text and border color if not reversed
   */
  public final void ccSetThemeColor(Color pxColor){
    if(pxColor==null){return;}
    cmThemeColor=pxColor;
  }//+++
  
  /**
   * @param pxBase ##
   * @param pxTheme ##
   */
  public final void ccSetupColor(Color pxBase, Color pxTheme){
    ccSetBaseColor(pxBase);
    ccSetThemeColor(pxTheme);
  }//+++
  
  /**
   * @param pxStatus #
   */
  public final void ccSetHasBorder(boolean pxStatus){
    ccHasBorder=pxStatus;
  }//+++
  
  /**
   * @param pxStatus #
   */
  public final void ccSetIsReversed(boolean pxStatus){
    cmIsReversed=pxStatus;
  }//+++
  
  /**
   * flip version
   */
  public final void ccSetIsReversed(){
    cmIsReversed=!cmIsReversed;
  }//+++
  
  /**
   * @param pxText must have something
   */
  public final void ccSetText(String pxText){
    if(!VcConst.ccIsValidString(pxText)){return;}
    cmText=pxText;
  }//+++
  
  /**
   * @return ##
   */
  public final String ccGetText(){
    return cmText;
  }//+++
  
  /**
   * auto resizing and auto fitting.<br>
   * in this case, anchoring may have no meaning.<br>
   * @param pxMetrics from the owner of this label
   */
  public final void ccSetSize(FontMetrics pxMetrics){
    if(pxMetrics==null){return;}
    if(!VcConst.ccIsValidString(cmText)){return;}
    ccSetW(pxMetrics.stringWidth(cmText));
    ccSetH(pxMetrics.getAscent()+pxMetrics.getDescent());
    cmTextOffsetX=0;
    cmTextOffsetY=pxMetrics.getAscent();
  }//+++
  
  /**
   * relocate the text with the current rectangle area size.<br>
   * @param pxMetrics from the owner of this label
   * @param pxMode_abcdNSWEXx see the with size version
   */
  public final void ccSetSize(FontMetrics pxMetrics, char pxMode_abcdNSWEXx){
    ccSetSize(pxMetrics, cmW, cmH, pxMode_abcdNSWEXx);
  }//+++
  
  /**
   * <pre>
   * resize the rectrangle area
   *   and relocate the text with the given anchor point.
   * if the given size is smaller than the text size
   *   calculated from the given matrix, 
   *   nothing would happen.
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
   * @param pxMetrics from the owner of this label
   * @param pxW pix
   * @param pxH pix
   * @param pxMode_abcdNSWEXx see above
   */
  public final void ccSetSize(
    FontMetrics pxMetrics,
    int pxW, int pxH,
    char pxMode_abcdNSWEXx
  ){
    
    //-- check in
    if(pxMetrics==null){return;}
    int lpRawW=pxMetrics.stringWidth(cmText);
    int lpRawH=pxMetrics.getAscent()+pxMetrics.getDescent();
    if(pxW<lpRawW || pxH<lpRawH){
      ccSetSize(pxMetrics);
      return;
    }//+++
    
    //-- relocate
    ccSetSize(pxW, pxH);
    
    switch (pxMode_abcdNSWEXx) {
      
      //-- direction
      
      case 'N':
        cmTextOffsetX=(pxW-lpRawW)/2;
        cmTextOffsetY=pxMetrics.getAscent();
      break;
      
      case 'S':
        cmTextOffsetX=(pxW-lpRawW)/2;
        cmTextOffsetY=pxH-pxMetrics.getDescent();
      break;
      
      case 'W':
        cmTextOffsetX=0;
        cmTextOffsetY=ccCenterY()-pxMetrics.getAscent()/2;
      break;
      
      case 'E':
        cmTextOffsetX=pxW-lpRawW;
        cmTextOffsetY=ccCenterY()-pxMetrics.getAscent()/2;
      break;
      
      //-- point
      
      case 'a':
        cmTextOffsetX=0;
        cmTextOffsetY=pxMetrics.getAscent();
      break;
      
      case 'b':
        cmTextOffsetX=pxW-lpRawW;
        cmTextOffsetY=pxMetrics.getAscent();
      break;
      
      case 'c':
        cmTextOffsetX=pxW-lpRawW;
        cmTextOffsetY=pxH-pxMetrics.getDescent();
      break;
      
      case 'd':
        cmTextOffsetX=0;
        cmTextOffsetY=pxH-pxMetrics.getDescent();
      break;
      
      //-- center
      
      case 'X':
        cmTextOffsetX=(pxW-lpRawW)/2;
        cmTextOffsetY=ccCenterY()-pxMetrics.getAscent()/2;
      break;
      
      default:break;
    }//...?
    
  }//+++
  
}//***eof
