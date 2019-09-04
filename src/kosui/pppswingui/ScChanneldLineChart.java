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
import java.util.ArrayList;
import java.util.List;
import kosui.ppplocalui.EcRect;
import kosui.pppmodel.McLineChartModel;

/**
 * .<br>
 * .<br>
 */
public class ScChanneldLineChart extends EcRect implements SiPaintable{
  
  static public final int C_MAX_CHANNEL = 16;
  static public final int C_MAX_MASK = 15;
  static public final int C_DIVISION = 32;
  
  private boolean cmHasBorder=true;
  private int cmShowMask = 1;
  private Color cmBorderColor=Color.DARK_GRAY;
  
  private int cmBufFaceX, cmBufNextX;
  private int cmBufFaceY, cmBufNextY;
  private McLineChartModel cmBufM;
  
  private final List<McLineChartModel> cmListOfChannel
    = new ArrayList<McLineChartModel>();
  private final List<Color> cmListOfChannelColor
    = new ArrayList<Color>();
  
  public ScChanneldLineChart(int pxW, int pxH) {
    super(pxW, pxH);
    for(int i=0;i<C_MAX_CHANNEL;i++){
      cmListOfChannel.add(new McLineChartModel(C_DIVISION));
      cmListOfChannelColor.add(Color.BLACK);
    }//..~
  }//..!!
  
  //===

  @Override public void ccPaint(Graphics pxGraphic) {
    
    //-- border
    if(cmHasBorder){
      pxGraphic.setColor(cmBorderColor);
      pxGraphic.drawRect(cmX, cmY, cmW, cmH);
    }//..?
    
    //-- splitter
    //[plan]::drawVerticalSplitter
    
    //-- line
    if(cmShowMask==0){return;}
    for(int lpChannel=0;lpChannel<=cmShowMask;lpChannel++){
      pxGraphic.setColor(cmListOfChannelColor.get(lpChannel));
      cmBufM=cmListOfChannel.get(lpChannel);
      for(int i=0,s=cmBufM.ccGetSize()-1;i<s;i++){
        cmBufFaceX=cmBufM.ccGetOffsetX(i);
        cmBufNextX=cmBufM.ccGetOffsetX(i+1);
        cmBufFaceY=cmBufM.ccGetOffsetY(i);
        cmBufNextY=cmBufM.ccGetOffsetY(i+1);
        pxGraphic.drawLine(
          cmX+cmBufFaceX, ccEndY()-cmBufFaceY,
          cmX+cmBufNextX, ccEndY()-cmBufNextY
        );
      }//..~
    }//..~
    
  }//+++
  
  //===
  
  public final void ccValidataOffsets(int pxChannel){
    cmListOfChannel.get(pxChannel&C_MAX_MASK).ccValidateOffsets(
      5, cmH
    );
  }//+++
  
  public final void ccSetHasBorder(boolean pxValue){
    cmHasBorder=pxValue;
  }//+++
  
  public final void ccSetBorderColor(Color pxColor){
    cmBorderColor=pxColor;
  }//+++
  
  public final void ccSetChannelColor(int pxChannel, Color pxColor){
    cmListOfChannelColor.set(pxChannel, pxColor);
  }//+++
  
  public final void ccSetChannelMask(int pxCount){
    cmShowMask=pxCount&C_MAX_MASK;
  }//+++
  
  public final McLineChartModel ccGetModel(int pxChannel){
    return cmListOfChannel.get(pxChannel&C_MAX_MASK);
  }//+++
  
}//***
