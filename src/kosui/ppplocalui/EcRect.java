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

import java.util.ArrayList;

/**
 * they say a rectangle is just two vector value.<br>
 * i think a rectangle should know just a little more about him self.<br>
 */
public class EcRect extends EcPoint{
  
  /**
   * size
   */
  protected int cmW=8,cmH=8;
  
  //===
  
  /**
   * calls rect() of PApplet
   * @param pxColor #
   */
  protected final void drawRect(int pxColor){
    pbOwner.fill(pxColor);
    pbOwner.rect(cmX, cmY, cmW, cmH);
  }//+++
  
  /**
   * calls line() of PApplet. <br>
   * this also calls noStroke() for convention reason
   * @param pxColor#
   * @deprecated combine complex pattern with this is not recommended.
   */
  @Deprecated protected final void drawLine(int pxColor){
    pbOwner.stroke(pxColor);
    pbOwner.line(cmX, cmY, ccEndX(), ccEndY());
    pbOwner.noStroke();
  }//+++
  
  //===
  
  /**
   * relocate its self based on the target passed by offset.<br>
   * start from the left-top corner of the target if 
   * both offset is not zero.<br>
   * align vertical(below) or horizontal(right) next to the target if
   * one of the offset is passed zero.<br>
   * like when you want follow an element
   * below to the target with 5pix offset : rect.ccFollows(target,0,5);<br>
   * like when you want follow an element
   * right to the target with 5pix offset : rect.ccFollows(target,5,0);<br>
   * @param pxTarget #
   * @param pxOffsetX #
   * @param pxOffsetY #
   */
  public final void ccSetLocation(
    EcRect pxTarget, int pxOffsetX, int pxOffsetY
  ){
    cmX=pxOffsetX;
    cmY=pxOffsetY;
    if(pxTarget!=null){
      cmX+=pxTarget.cmX+(pxOffsetY==0?pxTarget.cmW:0);
      cmY+=pxTarget.cmY+(pxOffsetX==0?pxTarget.cmH:0);
    }//..?
  }//+++
    
  /**
   * will not be set if passed zero
   * @param pxW #
   * @param pxH #
   */
  public final void ccSetSize(int pxW, int pxH){
    if(pxW!=0){cmW=pxW;}
    if(pxH!=0){cmH=pxH;}
  }//+++
  
  /**
   * 
   * @param pxTarget this does check null
   */
  public final void ccSetSize(EcRect pxTarget){
    if(pxTarget!=null){
      cmW=pxTarget.ccGetW();
      cmH=pxTarget.ccGetH();
    }
  }//+++
  
  /**
   * fit size to the target passed
   * @param pxTarget if null is passed nothing will happen
   * @param pxInW pass false to ignore
   * @param pxInH pass false to ignore
   */
  public final void ccSetSize(EcRect pxTarget, boolean pxInW, boolean pxInH){
    if(pxTarget!=null){
      if(pxInW){cmW=pxTarget.ccGetW();}
      if(pxInH){cmH=pxTarget.ccGetH();}
    }//..?
  }//+++
    
  /**
   * fit size to the target passed
   * and simply add current value with offset
   * @param pxTarget if null is passed offset value will be applied directly
   * @param pxOffsetW #
   * @param pxOffsetH #
   */
  public final void ccSetSize(EcRect pxTarget, int pxOffsetW, int pxOffsetH){
    if(pxTarget!=null){
      cmW=pxTarget.ccGetW()+pxOffsetW;
      cmH=pxTarget.ccGetH()+pxOffsetH;
    }else{
      cmW+=pxOffsetW;
      cmH+=pxOffsetH;
    }//..?
  }//+++
  
  /**
   * simply add current value with offset
   * @param pxOffsetW #
   * @param pxOffsetH #
   */
  public final void ccShiftSize(int pxOffsetW, int pxOffsetH){
    cmW+=pxOffsetW;
    cmH+=pxOffsetH;
  }//+++
    
  /**
   * an other convenient way to set size
   * via other rectangle's end point getter method.<br>
   * if passed minus value it wont set anything.<br>
   * like : rectangle.ccSetEndPoint(other_rectangle.ccGetEndX(),-1);<br>
   * <b>BUT DONT USE THIS BEFORE THE LOCATION IS SET TO RIGHT.</b><br>
   * @param pxEndX #
   * @param pxEndY #
   */
  public final void ccSetEndPoint(int pxEndX, int pxEndY){
    if(pxEndX>=0){cmW=pxEndX-cmX;}
    if(pxEndY>=0){cmH=pxEndY-cmY;}
  }//+++
  
  /**
   * reset size value based on end point of passed target. 
   * @param pxTarget if passed null offset value will be applied directly
   * @param pxOffsetX #
   * @param pxOffsetY #
   */
  public final void ccSetEndPoint(
    EcRect pxTarget, int pxOffsetX, int pxOffsetY
  ){
    if(pxTarget==null){ccSetEndPoint(pxOffsetX, pxOffsetY);return;}
    cmW=pxTarget.ccEndX()-cmX+pxOffsetX;
    cmH=pxTarget.ccEndY()-cmY+pxOffsetY;
  }//+++
  
  /**
   * 
   * @param pxX #
   * @param pxY #
   * @param pxW #
   * @param pxH #
   */
  public final void ccSetBound(int pxX, int pxY, int pxW, int pxH){
    cmX=pxX;cmY=pxY;cmW=pxW;cmH=pxH;
  }//+++  
  
  //===
  
  /**
   * 
   * @return w
   */
  public final int ccGetW(){return cmW;}//+++
  
  /**
   * 
   * @return h
   */
  public final int ccGetH(){return cmH;}//+++
  
  /**
   * 
   * @return x+w/2
   */
  public final int ccCenterX(){return cmX+cmW/2;}//+++
  
  /**
   * 
   * @return y+h/2
   */
  public final int ccCenterY(){return cmY+cmH/2;}//+++
  
  /**
   * 
   * @return x+w
   */
  public final int ccEndX(){return cmX+cmW;}//+++
  
  /**
   * 
   * @return y+h
   */
  public final int ccEndY(){return cmY+cmH;}//+++
  
  /**
   * 
   * @param pxX point x
   * @param pxY point y
   * @return true if the passed point is inside the bound
   */
  public final boolean ccContains(int pxX, int pxY){
    return (pxX>cmX)&&(pxX<(cmX+cmW))
         &&(pxY>cmY)&&(pxY<(cmY+cmH));
  }//+++
  
  //===
  
  /**
   * layout elements of passed container 
   * in a "DUO" motor switch manner, but in this case,
   * you have make sure the total number match the size. <br>
   * (say, for 3 column and 2 row, there have to be 6 elements)
   * @param pxList the 0th element will be the anchor
   * @param pxGapX count from the start point
   * @param pxGapY count from the start point
   * @param pxColumn count of column
   * @param pxRow count of row
   */
  public static final void ccGridLayout(
    ArrayList<EcRect> pxList,
    int pxGapX, int pxGapY,
    int pxColumn, int pxRow
  ){
    if(pxList==null){return;}
    if(pxList.isEmpty()){return;}
    EcRect lpAnchor=pxList.get(0);
    int lpX=lpAnchor.ccGetX();
    int lpY=lpAnchor.ccGetY();
    for(int i=0;i<pxList.size();i++){
      EcRect it = pxList.get(i);
      it.ccSetLocation(
        lpX+(i%(pxColumn&0xFF))*pxGapX,
        lpY+(i%(pxRow&0xFF))*pxGapY
      );
    }//..~
  }//+++
  
  /**
   * layout elements of passed container 
   * in a vertical or horizontal straight line,
   * @param pxList the 0th element will be the anchor
   * @param pxGap #
   * @param pxIsVertical #
   * @param pxIsReversed for vert, it s upward. for horizon, it s right ward.
   */
  public static final void ccFlowLayout(
    ArrayList<EcRect> pxList,
    int pxGap, boolean pxIsVertical, boolean pxIsReversed
  ){
    if(pxList==null){return;}
    if(pxList.isEmpty()){return;}
    if(pxIsReversed){
      for(int i=1;i<pxList.size();i++){
        EcRect it = pxList.get(i);
        EcRect prev = pxList.get(i-1);
        int lpX=pxIsVertical?
          prev.ccGetX():
          prev.ccGetX()-prev.ccGetW()-pxGap;
        int lpY=pxIsVertical?
          prev.ccGetY()-prev.ccGetH()-pxGap:
          prev.ccGetY();
        it.ccSetLocation(lpX,lpY);
      }//..~
    }else{
      for(int i=1;i<pxList.size();i++){
        EcRect it = pxList.get(i);
        EcRect prev = pxList.get(i-1);
        it.ccSetLocation(prev, pxIsVertical?0:pxGap, pxIsVertical?pxGap:0);
      }//..~
    }//..?
  }//+++

  
}//***eof
