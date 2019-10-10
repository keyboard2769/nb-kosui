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

package kosui.ppplocalui;

/**
 * the input-able version of gauge. <br>
 * for every draw loop it detects mouse pressing and locations. <br>
 */
public class EcSlider extends EcGauge{
  
  /**
   * inherited default.<br>
   * the text align is hidden by default.<br>
   * the enablility is true by default.<br>
   * is vertical by default.<br>
   * has stroke by default.<br>
   * @param pxKey will get pass to this directly
   * @param pxID will get pass to this directly
   */
  public EcSlider(String pxKey, int pxID){
    super(pxKey, pxID);
    ccSetIsEnabled(true);
  }//++!
  
  /**
   * inherited default.<br>
   * the text align is hidden by default.<br>
   * the enablility is true by default.<br>
   * is vertical by default.<br>
   * has stroke by default.<br>
   * @param pxKey will get pass to super directly
   */
  public EcSlider(String pxKey){
    super(pxKey);
    ccSetIsEnabled(true);
  }//++!
  
  /**
   * inherited default.<br>
   * the text align is hidden by default.<br>
   * the enablility is true by default.<br>
   * is vertical by default.<br>
   * has stroke by default.<br>
   */
  public EcSlider(){
    super();
    ccSetIsEnabled(true);
  }//++!
  
  //===
  
  /**
   * inherited default.<br>
   * the text align is hidden by default.<br>
   * the enablility is true by default.<br>
   * has stroke by default.<br>
   * @param pxKey will get pass to super directly
   * @param pxID will get pass to super directly
   * @param pxVert will get pass to super directly
   */
  public EcSlider(String pxKey, int pxID, boolean pxVert){
    super(pxKey, pxID, pxVert);
    ccSetIsEnabled(true);
  }//++!
  
  /**
   * inherited default.<br>
   * the text align is hidden by default.<br>
   * the enablility is true by default.<br>
   * has stroke by default.<br>
   * @param pxKey will get pass to super directly
   * @param pxVert will get pass to super directly
   */
  public EcSlider(String pxKey, boolean pxVert){
    super(pxKey, pxVert);
    ccSetIsEnabled(true);
  }//++!
  
  /**
   * inherited default.<br>
   * the text align is hidden by default.<br>
   * the enablility is true by default.<br>
   * has stroke by default.<br>
   * @param pxVert will get pass to super directly
   */
  public EcSlider(boolean pxVert){
    super(pxVert);
    ccSetIsEnabled(true);
  }//++!
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
    
    //-- checkin
    if(!ccIsVisible()){return;}
    
    //-- pre
    int lpPosition=ssToScaledPixLength(cmIsVertical?cmH:cmW);
    ssApplyClickedValue();
    
    //-- draw
    int lpAdjust=0;
    if(cmHasStroke){
      pbOwner.stroke(cmStrokeColor);
      lpAdjust=1;
    }//..?
    pbOwner.fill(cmBackColor);
    if(cmIsVertical){
      pbOwner.rect(ccCenterX()-2, cmY, 4-lpAdjust, cmH-lpAdjust);
      ssActFill();
      pbOwner.rect(cmX,ccEndY()-lpPosition-8,cmW-lpAdjust,8-lpAdjust);
    }else{
      pbOwner.rect(cmX, ccCenterY()-2, cmW-lpAdjust, 4-lpAdjust);
      ssActFill();
      pbOwner.rect(cmX+lpPosition-4, cmY, 8-lpAdjust, cmH-lpAdjust);
    }//..?
    if(cmHasStroke){pbOwner.noStroke();}
    
  }//++~
  
}//***eof
