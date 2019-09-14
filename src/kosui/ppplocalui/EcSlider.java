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
   * inherited default
   */
  public EcSlider(){
    super();
  }//..!
  
  /**
   * inherited
   * @param pxKey #
   */
  public EcSlider(String pxKey){
    super(pxKey);
    ccSetIsEnabled(true);
  }//..!
  
  /**
   * inherited default
   * @param pxKey #
   * @param pxID #
   */
  public EcSlider(String pxKey, int pxID){
    super(pxKey, pxID);
    ccSetIsEnabled(true);
  }//..!
  
  //===
  
  @Override public void ccUpdate(){
    
    if(!ccIsVisible()){return;}
    
    //-- pre
    int lpPosition=ccTellScale(cmIsVertical?cmH:cmW);
    ccApplyClickedValue();
    
    //-- draw
    int lpAdjust=0;
    if(cmHasStroke){
      pbOwner.stroke(cmStrokeColor);
      lpAdjust=1;
    }//..?
    pbOwner.fill(cmBackColor);
    if(cmIsVertical){
      pbOwner.rect(ccCenterX()-2, cmY, 4-lpAdjust, cmH-lpAdjust);
      ccActFill();
      pbOwner.rect(cmX,ccEndY()-lpPosition-8,cmW-lpAdjust,8-lpAdjust);
    }else{
      pbOwner.rect(cmX, ccCenterY()-2, cmW-lpAdjust, 4-lpAdjust);
      ccActFill();
      pbOwner.rect(cmX+lpPosition-4, cmY, 8-lpAdjust, cmH-lpAdjust);
    }//..?
    if(cmHasStroke){pbOwner.noStroke();}
    
  }//+++
  
}//***eof
