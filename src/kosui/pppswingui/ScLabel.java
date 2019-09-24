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
  
  private Color cmBaseColor,cmThemeColor;
  
  private boolean ccHasBorder;
  
  private boolean cmIsReversed;

  /**
   * <pre>
   * since in swing i do not know who is supposed to tell me about
   *   character size, there will not be any auto sizing utility.
   * </pre>
   * @param pxText
   * @param pxW pix
   * @param pxH pix
   */
  public ScLabel(String pxText, int pxW, int pxH){
    super(pxW, pxH);
    cmText = VcStringUtility.ccNulloutString(pxText);
    cmBaseColor=Color.GRAY;
    cmThemeColor=Color.BLACK;
    ccHasBorder=false;
    cmIsReversed=false;
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
        pxGI.drawRect(cmX, cmY, cmW, cmH);
      }//..?
    }//..?
    
    //--
    pxGI.setColor(cmIsReversed?cmBaseColor:cmThemeColor);
    pxGI.drawString(cmText, cmX+2, ccEndY()-2);
    
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
  
}//***
