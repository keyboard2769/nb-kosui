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

import java.util.List;
import kosui.ppplogic.ZcRangedModel;
import kosui.ppputil.VcConst;

/**
 * they say a rectangle is just two vector value.<br>
 * i think a rectangle should know just a little more about him self.<br>
 */
public class EcRect extends EcPoint{
  
  /**
   * size
   */
  protected int cmW=8,cmH=8;

  /**
   * all initiated value is eight.
   */
  public EcRect(){
    super();
    ccSetSize(8, 8);
  }//+++
  
  /**
   * rest of those initiated values might be eight.
   * @param pxW will get passed to ccSetSize() directly
   * @param pxH 
   */
  public EcRect(int pxW, int pxH){
    super();
    ccSetSize(pxW, pxH);
  }//+++
  
  //===
  
  /**
   * @param pxW 0~65535
   */
  public final void ccSetW(int pxW){
    cmW=pxW&0xFFFF;
  }//+++
  
  /**
   * 
   * @param pxH  0~65535
   */
  public final void ccSetH(int pxH){
    cmH=pxH&0xFFFF;
  }//+++
  
  /**
   * <pre>
   * relocate its self based on the target passed by offset.
   * start from the left-top corner of the target if.
   * both offset is not zero.
   * align vertical(below) or horizontal(right) next to the target if
   *   one of the offset is passed zero.
   * like when you want follow an element
   *   below to the target with 5pix offset : rect.ccFollows(target,0,5);
   * like when you want follow an element
   *   right to the target with 5pix offset : rect.ccFollows(target,5,0);
   * </pre>
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
    ccSetW(pxW);
    ccSetH(pxH);
  }//+++
  
  /**
   * 
   * @param pxTarget this does check null
   */
  public final void ccSetSize(EcRect pxTarget){
    if(pxTarget==null){return;}
    ccSetW(pxTarget.ccGetW());
    ccSetH(pxTarget.ccGetH());
  }//+++
  
  /**
   * fit size to the target passed
   * @param pxTarget if null is passed nothing will happen
   * @param pxInW pass false to ignore
   * @param pxInH pass false to ignore
   */
  public final void ccSetSize(EcRect pxTarget, boolean pxInW, boolean pxInH){
    if(pxTarget==null){return;}
    if(pxInW){ccSetW(pxTarget.ccGetW());}
    if(pxInH){ccSetH(pxTarget.ccGetH());}
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
      ccSetW(pxTarget.ccGetW()+pxOffsetW);
      ccSetH(pxTarget.ccGetH()+pxOffsetH);
    }else{
      cmW+=pxOffsetW;
      cmH+=pxOffsetH;
    }//..?
  }//+++
  
  /**
   * automatically change the size based on the given text.
   * thus the letter width is depends on the PApplet,
   *   if any instance invoke auto size before
   *   the base class is initiated,
   *   a hard coded default value will get applied.
   * if it didn't match, you might have to invoke it again somewhere.
   * @param pxText 
   */
  public final void ccSetSize(String pxText){
    
    if(!VcConst.ccIsValidString(pxText)){return;}
    
    cmW = (EcConst.C_DEFAULT_AUTOSIZE_MARGIN*2)
        + (!EcComponent.ccHasOwner()?
            (EcConst.C_DEFAULT_TEXT_WIDTH*pxText.length())
           :((int)(EcComponent.pbOwner.textWidth(pxText)))
          );
    cmH = EcConst.C_DEFAULT_AUTOSIZE_HEIGHT;
    
    char lpNewLine=VcConst.C_V_NEWLINE.charAt(0);
    for(char it:pxText.toCharArray())
      {if(it==lpNewLine){cmH+=EcConst.C_DEFAULT_AUTOSIZE_HEIGHT;}}
  
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
  
  //[plan]::ccSetEndX()
  //[plan]::ccSetEndY()
    
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
   * @param pxOffsetX value that bigger than 65535 is invalid
   * @param pxOffsetY value that bigger than 65535 is invalid
   */
  public final
  void ccSetEndPoint(EcRect pxTarget, int pxOffsetX, int pxOffsetY){
    if(pxTarget==null){ccSetEndPoint(pxOffsetX, pxOffsetY);return;}
    if(pxOffsetX<=65535){cmW=pxTarget.ccEndX()-cmX+pxOffsetX;}
    if(pxOffsetY<=65535){cmH=pxTarget.ccEndY()-cmY+pxOffsetY;}
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
    return ZcRangedModel.ccContains(pxX, cmX, ccEndX())
         &&ZcRangedModel.ccContains(pxY, cmY, ccEndY());
  }//+++
  
  //===
  
  /**
   * <pre>
   * layout elements of passed container 
   *   in a "DUO" motor switch manner,
   *   but in this case,
   *   you have to make sure the total number match the size.
   *   (say, for 3 column and 2 row, there have to be 6 elements)
   * the first one should has a valid location.
   * </pre>
   * @param pxList the 0th element will be the anchor
   * @param pxGapX count from the start point
   * @param pxGapY count from the start point
   * @param pxColumn count of column
   * @param pxRow count of row
   */
  public static final void ccGridLayout(
    List<? extends EcRect> pxList,
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
   * <pre>
   * layout elements of passed container 
   *   in a vertical or horizontal straight line.
   * the first one should has a valid location.
   * </pre>
   * @param pxList the 0th element will be the anchor
   * @param pxGap #
   * @param pxIsVertical #
   * @param pxIsReversed for vert, it s upward. for horizon, it s right ward.
   */
  public static final void ccFlowLayout(
    List<? extends EcRect> pxList,
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
