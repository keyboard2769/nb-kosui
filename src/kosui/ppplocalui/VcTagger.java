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

import processing.core.PApplet;
import static processing.core.PApplet.ceil;

/**
 * draw string and value as tags on the screen.<br>
 * vertically flows down(raw) the screen and turns to right(column).<br>
 */
public final class VcTagger{
  
  static private PApplet pbOwner=null;
  
  static private int
    pbCounter=0,
    pbTagHeight=16,
    pbRow=8,
    pbGapX=100,
    pbGapY=20,
    pbOffsetX=5,
    pbOffsetY=20,
    pbBackground=0x99666666,
    pbForeround =0xFF33EE33
  ;//--
  
  static private float pbTagWidthCoeff=1.02f;
  
  static private boolean pbIsVisible=true;
  
  //== constructor
  
  private VcTagger(){}
  
  //== modifier
  
  /**
   * it is invoked from the factory's initiator.
   * you don't have to call this in your sketch.<br>
   * if you wanna change the raw setting you can call the
   * setter anywhere.<br>
   * @param pxParent your sketch
   * @param pxRow max row count, DONT PASS ZERO!!
   */
  static public void ccInit(PApplet pxParent, int pxRow)
    {pbOwner=pxParent;pbRow=pxRow==0?1:pxRow;}//+++
  
  /**
   * gap count from start point but not end. 
   * should be bigger than actual text height and width.
   * @param pxGapX #
   * @param pxGapY #
   */
  static public void ccSetGap(int pxGapX, int pxGapY)
    {pbGapX=pxGapX;pbGapY=pxGapY;}//+++
  
  /**
   * for 320*240 size, 7 is recommended. 
   * @param pxRow #
   */
  static public void ccSetRow(int pxRow)
    {pbRow=pxRow;}//+++
  
  /**
   * set how every blocks auto size its self
   * @param pxHeight #
   * @param pxWCoeff #
   */
  static public void ccSetTagSize(int pxHeight, float pxWCoeff)
    {pbTagHeight=pxHeight;pbTagWidthCoeff=pxWCoeff; }//+++
  
  /**
   * 
   * @param pxOffsetX #
   * @param pxOffsetY #
   */
  static public void ccSetLocationOffset(int pxOffsetX, int pxOffsetY)
    {pbOffsetX=pxOffsetX;pbOffsetY=pxOffsetY;}//+++
  
  /**
   * 
   * @param pxFore #
   * @param pxBack #
   */
  static public void ccSetColor(int pxFore, int pxBack)
    {pbForeround=pxFore;pbBackground=pxBack;}//+++
  
  /**
   * flips visibility
   */
  static public void ccSetIsVisible()
    {pbIsVisible=!pbIsVisible;}//+++
  
  /**
   * 
   * @param pxStatus #
   */
  static public void ccSetIsVisible(boolean pxStatus)
    {pbIsVisible=pxStatus;}//+++
  
  //== updator
  
  /**
   * 
   * @param pxLine #
   */
  static public void ccTag(String pxLine)
    {ccUpdate(pxLine);}//+++
  
  /**
   * 
   * @param pxTag #
   * @param pxValue #
   */
  static public void ccTag(String pxTag, Object pxValue)
    {ccUpdate(pxTag+":"+pxValue.toString());}//+++
  
  /**
   * must be called at the end of draw()
   */
  static public void ccStabilize(){pbCounter=0;}//+++
  
  //==  
  static private void ccUpdate(String pxTag){
    if(pbOwner==null || !pbIsVisible){return;}
    int lpX=(pbCounter/pbRow)*pbGapX+pbOffsetX;
    int lpY=(pbCounter%pbRow)*pbGapY+pbOffsetY;
    int lpW=ceil(pbOwner.textWidth(pxTag)*pbTagWidthCoeff);
    //--
    pbOwner.fill(pbBackground);
    pbOwner.rect(lpX, lpY, lpW, pbTagHeight);
    //--
    pbOwner.fill(pbForeround);
    pbOwner.text(pxTag, lpX, lpY);
    //--
    pbCounter++;
  }//+++
    
}//***eof
