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
 * well le'me tel'la, absolutely, nothing.<br>
 */
public class ScLabel extends EcRect implements SiPaintable{
  
  private String cmText;
  
  private Color cmColor;
  
  private boolean cmHasStroke;

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
    cmColor=Color.BLACK;
    cmHasStroke=false;
  }//..!
  
  //===
  
  @Override public void ccPaint(Graphics pxGI){
    
    pxGI.setColor(cmColor);
    //[todo]::can we make this thing better??
    pxGI.drawString(cmText, cmX+2, cmY+20);
    
    if(!cmHasStroke){return;}
    pxGI.drawRect(cmX, cmY, cmW, cmH);
    
  }//+++
  
  //===
  
  /**
   * @param pxColor both border and text
   */
  public final void ccSetColor(Color pxColor){
    if(pxColor==null){return;}
    cmColor=pxColor;
  }//+++
  
  /**
   * @param pxValue #
   */
  public final void ccSetHasBorder(boolean pxValue){
    cmHasStroke=pxValue;
  }//++
  
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
