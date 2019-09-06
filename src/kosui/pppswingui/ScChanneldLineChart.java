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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import kosui.ppplocalui.EcRect;
import kosui.pppmodel.McLineChartModel;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;

/**
 * if it is not for the burner trending stuff implement a bar chart 
 * would be far more easier, but useless.<br>
 * maybe we will have a ScBarChart as a base class 
 * and spread them to the local UI world some day.<br>
 */
public class ScChanneldLineChart extends EcRect implements SiPaintable{
  
  /**
   * you can not get more line than this value
   */
  static public final int C_MAX_CHANNEL = 16;
  
  /**
   * stuck with the max value
   */
  static public final int C_MAX_MASK = 15;
  
  /**
   * at this point it is hard coded.<br>
   * it looked great applied to a 200x100 graph.<br>
   */
  static public final int C_DIVISION = 32;
  
  private static final BasicStroke O_DASH_STROKE
    = new BasicStroke(1.0f, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_MITER,
     10.0f, new float[]{10f}, 0.0f);

  private static final BasicStroke O_BORDER_STROKE
    = new BasicStroke(1.25f, BasicStroke.JOIN_MITER, BasicStroke.CAP_BUTT);

  private static final BasicStroke O_CHANNEL_STROKE
    = new BasicStroke(2.0f, BasicStroke.JOIN_ROUND, BasicStroke.CAP_ROUND);
  
  private static final int C_TEXT_ADJUST_X = 3;
   
  //===
  
  private boolean cmHasBorder=true;
  private int cmShowMask = 1;
  private Color cmBorderColor=Color.GRAY;
  
  private int[] cmDesSplitH = null;
  private int[] cmDesSplitV = null;
  
  private int cmBufFaceX, cmBufNextX;
  private int cmBufFaceY, cmBufNextY;
  private McLineChartModel cmBufM;
  
  private final List<McLineChartModel> cmListOfChannel
    = new ArrayList<McLineChartModel>();
  private final List<Color> cmListOfChannelColor
    = new ArrayList<Color>();
  
  /**
   * <pre>
   * line chart model as channel is also constructed.
   * every channel is colored black by default.
   * we just keep value as pixel point as well, for drawing.
   * if you need to keey those actual value, or name these channels,
   *   or even more, like specify a unit,
   *   think about maintain your own hash map some where.
   * vertical split line is fine split in 4 by default.
   * horizontal split line is fine split in 2 by default.
   * </pre>
   * @param pxW pix
   * @param pxH pix
   */
  public ScChanneldLineChart(int pxW, int pxH) {
    super(pxW, pxH);
    for(int i=0;i<C_MAX_CHANNEL;i++){
      cmListOfChannel.add(new McLineChartModel(C_DIVISION));
      cmListOfChannelColor.add(Color.DARK_GRAY);
    }//..~
    cmDesSplitV=VcNumericUtility.ccFineSplit(pxW, 4);
    cmDesSplitH=VcNumericUtility.ccFineSplit(pxH, 2);
  }//..!!
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public void ccPaint(Graphics pxGI) {
    
    //-- force cast
    //..[tofigureout]::i dont know why they have to do this
    Graphics2D lpGII=(Graphics2D)pxGI;
    
    //-- border
    if(cmHasBorder){
      lpGII.setStroke(O_BORDER_STROKE);
      pxGI.setColor(cmBorderColor);
      pxGI.drawRect(cmX, cmY, cmW, cmH);
    }//..?
    
    //-- splitter
    lpGII.setStroke(O_DASH_STROKE);
    lpGII.setColor(cmBorderColor);
    ssDrawHorizontalSplit(lpGII);
    ssDrawVerticalSplit(lpGII);
    
    //-- line
    if(cmShowMask==0){return;}
    lpGII.setStroke(O_CHANNEL_STROKE);
    for(int lpChannel=0;lpChannel<=cmShowMask;lpChannel++){
      pxGI.setColor(cmListOfChannelColor.get(lpChannel));
      cmBufM=cmListOfChannel.get(lpChannel);
      for(int i=0,s=cmBufM.ccGetSize()-1;i<s;i++){
        cmBufFaceX=cmBufM.ccGetOffsetX(i);
        cmBufNextX=cmBufM.ccGetOffsetX(i+1);
        cmBufFaceY=cmBufM.ccGetOffsetY(i);
        cmBufNextY=cmBufM.ccGetOffsetY(i+1);
        if(cmBufFaceX>cmBufFaceX){continue;}
        if(cmBufFaceY==0 || cmBufNextY==0){continue;}
        pxGI.drawLine(
          C_TEXT_ADJUST_X+cmX+cmBufFaceX, ccEndY()-cmBufFaceY,
          C_TEXT_ADJUST_X+cmX+cmBufNextX, ccEndY()-cmBufNextY
        );
      }//..~
    }//..~
    
  }//+++
  
  private void ssDrawHorizontalSplit(Graphics2D pxGII){
    if(cmDesSplitH==null){return;}
    if(cmDesSplitH.length<=1){return;}
    for(int i=1,s=cmDesSplitH.length;i<s;i++){
      int lpAbsolute=cmY+cmY+cmDesSplitH[i];
      if(lpAbsolute<=cmY||lpAbsolute>=ccEndY()){continue;}
      pxGII.drawLine(cmX, lpAbsolute, ccEndX(), lpAbsolute);
    }//..~
  }//+++
  
  private void ssDrawVerticalSplit(Graphics2D pxGII){
    if(cmDesSplitV==null){return;}
    if(cmDesSplitV.length<=1){return;}
    for(int i=1,s=cmDesSplitV.length;i<s;i++){
      int lpAbsolute=cmX+cmDesSplitV[i];
      if(lpAbsolute<=cmX||lpAbsolute>=ccEndX()){continue;}
      pxGII.drawLine(lpAbsolute, cmY, lpAbsolute, ccEndY());
    }//..~
  }//+++

  //===
  
  /**
   * @param pxChannel index
   */
  public final void ccValidataOffsets(int pxChannel){
    cmListOfChannel.get(pxChannel&C_MAX_MASK)
      .ccValidateOffsets(cmW/C_DIVISION, cmH);
  }//+++
  
  /**
   * @param pxValue will only get drawn once per update
   */
  public final void ccSetHasBorder(boolean pxValue){
    cmHasBorder=pxValue;
  }//+++
  
  /**
   * @param pxColor never pass null
   */
  public final void ccSetBorderColor(Color pxColor){
    cmBorderColor=pxColor;
  }//+++
  
  /**
   * the chart only holds a reference.<br>
   * @param pxOffsets must have something
   */
  public final void ccSetVerticalSplitOffsets(int[] pxOffsets){
    if(!VcConst.ccIsValidArray(pxOffsets)){return;}
    cmDesSplitV=pxOffsets;
  }//+++
  
  /**
   * the chart only holds a reference.<br>
   * @param pxOffsets must have something
   */
  public final void ccSetHorizontalSplitOffsets(int[] pxOffsets){
    if(!VcConst.ccIsValidArray(pxOffsets)){return;}
    cmDesSplitH=pxOffsets;
  }//+++
  
  /**
   * @param pxChannel index
   * @param pxColor never pass null
   */
  public final void ccSetChannelColor(int pxChannel, Color pxColor){
    cmListOfChannelColor.set(pxChannel, pxColor);
  }//+++
  
  /**
   * not serve for "masking" actually but for limiting.<br>
   * like if 4 is passed, only channel number 0-4 will get drawn.<br>
   * @param pxCount #
   */
  public final void ccSetChannelMask(int pxCount){
    cmShowMask=pxCount&C_MAX_MASK;
  }//+++
  
  /**
   * @param pxChannel index
   * @return user is supposed to access actual data through buffer of model
   */
  public final McLineChartModel ccGetModel(int pxChannel){
    return cmListOfChannel.get(pxChannel&C_MAX_MASK);
  }//+++
  
}//***
