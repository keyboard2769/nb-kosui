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

import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

/**
 * a text don't even know what it is meant for.<br>
 * he is part of the back ground.<br>
 */
public class EcText extends EcShape{
  
  /**
   * ##
   */
  protected String cmText="<?>";
  
  /**
   * ##
   */
  protected int cmTextColor=EcConst.C_DARK_GRAY;
  
  /**
   * text color is dark gray by default.<br>
   */
  public EcText(){
    super();
  }//+++
  
  /**
   * text color is dark gray by default.<br>
   * @param pxText will gell null out to empty
   */
  public EcText(String pxText){
    super();
    ccSetText(pxText);
    ccSetSize(cmText);
  }//+++ 
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
    if(!ccIsVisible()){return;}
    pbOwner.fill(cmTextColor);
    pbOwner.textAlign(PApplet.CENTER, PApplet.CENTER);
    pbOwner.text(cmText, cmX, cmY);
    pbOwner.textAlign(PApplet.LEFT, PApplet.TOP);
  }//+++
  
  /**
   * @param pxText will gell null out to empty
   */
  public final void ccSetText(String pxText){
    cmText=VcStringUtility.ccNulloutString(pxText);
  }//+++
  
  /**
   * @param pxColor ARGB
   */
  public final void ccSetTextColor(int pxColor){
    cmTextColor=pxColor;
  }//+++  
  
}//***eof
