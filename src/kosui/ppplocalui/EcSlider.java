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

  @Override public void ccUpdate(){
    
    int lpPosition=ccTellScale(cmIsVertical?cmH:cmW);
    
    if(cmHasStroke){pbOwner.stroke(cmStrokeColor);}
    
    pbOwner.fill(cmBackColor);
      
    if(cmIsVertical){
      pbOwner.rect(ccCenterX()-2, cmY, 4, cmH);
      ccActFill();
      pbOwner.rect(cmX,cmY+lpPosition-4,cmW,8);
      if(ccIsMousePressed()){cmContentValue=(pbOwner.mouseY-cmY)*127/cmH;}
    }else{
      pbOwner.rect(cmX, ccCenterY()-2, cmW, 4);
      ccActFill();
      pbOwner.rect(cmX+lpPosition-4, cmY, 8, cmH);
      if(ccIsMousePressed()){cmContentValue=(pbOwner.mouseX-cmX)*127/cmW;}
    }
    
    if(cmHasStroke){pbOwner.noStroke();}
    
  }//+++
  
}//***eof
