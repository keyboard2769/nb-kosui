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

import static kosui.ppplocalui.EcComponent.pbOwner;
import processing.core.PApplet;

/**
 * label makes a text looks more like a sticker.<br>
 * since there is no bumper we did not need that until there was one.<br>
 */
public class EcLabel extends EcText{
  
  /**
   * can get applied to a line or just a bigger rectangle
   */
  protected int cmBorderColor=EcConst.C_WHITE;
  
  /**
   * do draw base or not
   */
  protected boolean cmHasBase=false;
  
  /**
   * do draw border or not
   */
  protected boolean cmHasBorder=true;
  
  /**
   * border color is white by default.<br>
   * base is not visible by default.<br>
   */
  public EcLabel(){
    super();
  }//+++ 
  
  /**
   * border color is white by default.<br>
   * base is not visible by default.<br>
   * @param pxText will gell null out to empty
   */
  public EcLabel(String pxText){
    super(pxText);
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
    
    if(!ccIsVisible()){return;}
    
    //-- base
    if(cmHasBorder){
      pbOwner.stroke(cmBorderColor);
    }else{
      pbOwner.noStroke();
    }//..?
    if(cmHasBase){
      pbOwner.fill(cmBorderColor);
    }else{
      pbOwner.noFill();
    }//..?
    pbOwner.rect(cmX, cmY, cmW, cmH);
    
    //-- text
    pbOwner.fill(cmTextColor);
    pbOwner.textAlign(PApplet.CENTER, PApplet.CENTER);
    pbOwner.text(cmText, ccCenterX(), ccCenterY());
    
    //-- pop
    pbOwner.textAlign(PApplet.LEFT, PApplet.TOP);
    pbOwner.noStroke();
    
  }//+++
  
  /**
   * @param pxStatus do draw border or not
   */
  public final void ccSetHasBorder(boolean pxStatus){
    cmHasBorder=pxStatus;
  }//+++
  
  /**
   * @param pxStatus do draw base or not
   */
  public final void ccSetHasBase(boolean pxStatus){
    cmHasBase=pxStatus;
  }//+++
  
  /**
   * @param pxColor ARGB
   */
  public final void ccSetBorderColor(int pxColor){
    cmBorderColor=pxColor;
  }//+++
  
}//***eof
