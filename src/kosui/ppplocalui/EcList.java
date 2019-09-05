/*
 * Copyright (C) 2018 Key Parker from K.I.C
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

import static processing.core.PApplet.constrain;
import static kosui.ppputil.VcStringUtility.ccLeft;

/**
 * a rectangle shows some lists.<br>
 * it can return some integer as index.<br>
 */
public class EcList extends EcElement{
  
  //===
  private static final int
    C_STROKE_THICK = 2
  ;//...
  //===
  
  private Object[] cmModel=null;
  private int cmCurrentIndex=0;
  private int cmCellWidth=48;
  private int cmCellHeight=18;
  private int cmCellCharCount=7;
  private int cmRow=3;
  private int cmGap=2;
  
  //===
  
  /**
   * <pre>
   * only initiates size and color.
   * the default row count is 3,
   * with cell sized 48*18, with gap as 2.
   * default color set is yellow as on and gray as off.
   * the list is not supposed to get clicked.
   * </pre>
   */
  public EcList(){
    super();
    ccSetIsEnabled(false);
    ccFitSize();
    ccSetColor(EcConst.C_YELLOW, EcConst.C_DIM_GRAY);
  }//+++ 
  
  //=== modifier
  
  /**
   * between cells is vertical, will be masked to 0-255.
   * @param pxGap #
   */
  public final void ccSetGap(int pxGap){
    cmGap=pxGap&0xFF;
    ccFitSize();
  }//+++
  
  /**
   * how many item you want to show, will be masked to odd between 0-255.
   * @param pxRow #
   */
  public final void ccSetRowCount(int pxRow){
    cmRow=((pxRow&0xFF)|1);
    ccFitSize();
  }//+++
  
  /**
   * width and height both are just for a single cell
   * and will be masked to 0-255.
   * @param pxW width
   * @param pxH height
   * @param pxCharCount max character number to show in a single cell
   */
  public final void ccSetCellSize(int pxW, int pxH, int pxCharCount){
    ccSetCellSize(pxW, pxH);
    cmCellCharCount=pxCharCount;
  }//+++
  
  /**
   * both are just for a single cell and will be masked to 0-255.
   * @param pxWidth #
   * @param pxHeight #
   */
  public final void ccSetCellSize(int pxWidth, int pxHeight){
    cmCellWidth=pxWidth&0xFF;
    cmCellHeight=pxHeight&0xFF;
    ccFitSize();
  }//+++
  
  /**
   * @param pxModel any thing with a solid to string method
   */
  public final void ccSetModel(Object[] pxModel){
    cmModel=pxModel;
    cmCurrentIndex=0;
  }//+++
  
  /**
   * @param pxOffset will be constrained
   */
  public final void ccShiftIndex(int pxOffset){
    if(cmModel==null){return;}
    cmCurrentIndex=constrain(cmCurrentIndex+pxOffset, 0, cmModel.length-1);
  }//+++
  
  //=== updator

  /**
   * {@inheritDoc}
   */
  @Override public void ccUpdate(){
    
    //-- draw pane
    ccActFill();
    pbOwner.rect(cmX, cmY, cmW, cmH);
    pbOwner.fill(EcConst.C_WHITE);
    pbOwner.rect(cmX+C_STROKE_THICK, cmY+C_STROKE_THICK,
      cmW-+C_STROKE_THICK*2, cmH-+C_STROKE_THICK*2);
    ccActFill();
    pbOwner.rect(cmX, ccCenterY()-(cmCellHeight/4),
      C_STROKE_THICK*2, cmCellHeight/2);
    
    //-- draw contents
    if(cmModel==null){return;}
    int lpHeadIndex=cmCurrentIndex-(cmRow/2);
    String lpBuf;
    int lpBufY;
    for(int i=0;i<cmRow;i++){
      lpBuf=ccGetString(lpHeadIndex+i);
      if(!lpBuf.equals("<nn>")){
        lpBufY=cmY+C_STROKE_THICK*2+(i*(cmCellHeight+cmGap));
        pbOwner.fill(i==cmRow/2?cmOnColor:cmOffColor);
        pbOwner.rect(cmX+C_STROKE_THICK*3,
          lpBufY,
          cmCellWidth, cmCellHeight);
        pbOwner.fill(EcConst.C_DIM_GRAY);
        pbOwner.text(ccLeft(lpBuf,cmCellCharCount),
          cmX+C_STROKE_THICK*4,
          lpBufY+2
        );
      }//..?
    }//..~
    
  }//+++
  
  //=== supportor
  
  private String ccGetString(int pxIndex){
    if(cmModel==null){return "<nm>";}
    if(pxIndex<0 || pxIndex>=cmModel.length){return "<nn>";}
    return cmModel[pxIndex].toString();
  }//+++
  
  private void ccFitSize(){
    cmW=cmCellWidth+C_STROKE_THICK*5;//..stroke for 2, gap for 2+1
    cmH=cmRow*cmCellHeight
      +((cmRow+1)*cmGap)
      +C_STROKE_THICK*2;
  }//+++
  
  //=== teller
  
  /**
   * @return #
   */
  public final int ccGetCurrentIndex(){
    return cmCurrentIndex;
  }//+++
  
  /**
   * @return toString-ed item
   */
  public final String ccGetCurrentItem(){
    return ccGetString(cmCurrentIndex);
  }//+++
  
  //===
  
}//***eof
