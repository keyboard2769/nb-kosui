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

package kosui.ppputil;

import kosui.pppswingui.ScDragAnchor;
import processing.core.PApplet;

/**
 * <pre>
 * if i am drawing some sketch on a paper i will never use a ruler.
 * i wish you have a better way to determine any location and size.
 * </pre>
 */
public final class VcLocalAxis {
  
  /**
   * between lines or axis.
   */
  public static final int C_TEXT_GAP = 2;
  
  //===
  
  private static PApplet pbOwner=null;
  
  private static final ScDragAnchor O_DRAGGER = new ScDragAnchor();
  
  private static float pbScale = 1.0f;
  
  private static int pbAxisColor = 0x9933EE33;
  
  private static boolean pbIsEnabled = true;
  
  private static int pbAnchorX=0, pbAnchorY=0;
  
  private static boolean pbIsTransformed = false;
  
  private static int pbTransmouseX=0, pbTransmouseY=0;
  
  //===
  
  /**
   * <pre>
   * it is invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * </pre>
   * @param pxParent your sketch
   * @param pxEnable #
   */
  public static final void ccInit(PApplet pxParent, boolean pxEnable){
    if(pbOwner==null){pbOwner=pxParent;}
    ccSetIsEnabled(pxEnable);
  }//..!
  
  //=== overridden
  
  /**
   * <b>MUST BE INITIATED</b><br>
   * should be called inside draw()<br>
   */
  static public void ccUpdate(){
    
    //-- check
    if(!pbIsEnabled){return;}
    if(pbOwner==null){return;}
    
    //-- pre
    int lpMouseX=pbOwner.mouseX;
    int lpMouseY=pbOwner.mouseY;
    int lpWidth=lpMouseX-pbAnchorX;
    int lpHeight=lpMouseY-pbAnchorY;
    int lpLineHeight = PApplet.ceil(pbOwner.g.textSize);
    boolean lpHasAnchor=(pbAnchorX!=0)&&(pbAnchorY!=0);
    
    //-- axis
    pbOwner.noFill();
    pbOwner.stroke(pbAxisColor);
    pbOwner.line(0,lpMouseY,pbOwner.width,lpMouseY);
    pbOwner.line(lpMouseX,0,lpMouseX,pbOwner.height);
    
    //-- rect
    if(lpHasAnchor){
      pbOwner.rect(pbAnchorX,pbAnchorY,lpWidth,lpHeight);
    }//..?
    pbOwner.noStroke();
    
    //-- info 
    
    //-- info ** pre
    pbOwner.fill(pbAxisColor);
    
    //-- info ** draw ** mosue
    String lpMouse=VcStringUtility.ccPackupDimensionValue(lpMouseX, lpMouseY);
    pbOwner.textAlign(PApplet.LEFT, PApplet.BOTTOM);
    pbOwner.text(lpMouse,lpMouseX+C_TEXT_GAP,lpMouseY-C_TEXT_GAP);
    
    //-- info ** draw ** rect
    if(lpHasAnchor){
      pbOwner.textAlign(PApplet.LEFT, PApplet.TOP);
      String lpSize=VcStringUtility
        .ccPackupDimensionValue(lpWidth, lpHeight);
      String lpAnchor=VcStringUtility
        .ccPackupDimensionValue(pbAnchorX, pbAnchorY);
      pbOwner.text(lpAnchor,lpMouseX+C_TEXT_GAP,lpMouseY+C_TEXT_GAP);
      pbOwner.text(lpSize,lpMouseX+C_TEXT_GAP,lpMouseY+C_TEXT_GAP+lpLineHeight);
    }//..?
    
    //-- info ** draw ** transform
    if(pbIsTransformed){
      pbOwner.textAlign(PApplet.RIGHT, PApplet.BOTTOM);
      String lpTransformedMouse=VcStringUtility
        .ccPackupDimensionValue(pbTransmouseX,pbTransmouseY);
      pbOwner.text(lpTransformedMouse,lpMouseX-C_TEXT_GAP,lpMouseY-C_TEXT_GAP);
      if(lpHasAnchor){
        pbOwner.textAlign(PApplet.RIGHT, PApplet.TOP);
        String lpSize=VcStringUtility.ccPackupDimensionValue(
          VcNumericUtility.ccMagnify(lpWidth, 1f/pbScale),
          VcNumericUtility.ccMagnify(lpHeight, 1f/pbScale)
        );
        String lpAnchor=VcStringUtility.ccPackupDimensionValue(
          ccTransformX(pbAnchorX),
          ccTransformY(pbAnchorY)
        );
        pbOwner.text(lpAnchor,lpMouseX-C_TEXT_GAP,lpMouseY+C_TEXT_GAP);
        pbOwner.text(
          lpSize,
          lpMouseX-C_TEXT_GAP,
          lpMouseY+C_TEXT_GAP+lpLineHeight
        );
      }//..?
    }//..?
    
    //-- always
    pbOwner.textAlign(PApplet.LEFT, PApplet.TOP);
    
  }//+++
  
  /*
  / ** [head]:: cut this above 
   * <b>MUST BE INITIATED</b><br>
   * should be called inside draw()<br>
   * the axis him self does NOT calcualte transformation.<br>
   * @param pxTransformedX
   * @param pxTransformedY 
   * /
  static public void ccUpdate(int pxTransformedX, int pxTransformedY){
    ccSetTransformedMosuePoint(pxTransformedX, pxTransformedY);
    ccUpdate();
  }//+++
  */
  
  /**
   * pushes matrix then set translation and scale value on given owner.
   * call this before actual view.
   */
  public static final void ccEnterTransformation(){
    if(pbOwner==null){return;}
    pbOwner.pushMatrix();
    pbOwner.translate(O_DRAGGER.ccGetOffsetX(), O_DRAGGER.ccGetOffsetY());
    pbOwner.scale(pbScale);
    pbTransmouseX=ccTransformX(pbOwner.mouseX);
    pbTransmouseY=ccTransformY(pbOwner.mouseY);
  }//+++
  
  /**
   * pops matrix back on given owner.
   * call this after actual view.
   */
  public static final void ccExitTransformation(){
    if(pbOwner==null){return;}
    pbOwner.popMatrix();
  }//+++
  
  //=== evet ** transformer
  
  /**
   * supposedly should get called on mouse release.
   */
  public static final
  void ccCleanTransformationalAnchor(){
    O_DRAGGER.ccClearAnchor();
  }//+++
  
  /**
   * supposedly should get called on mouse dragged.
   * @param pxMouseRawX ##
   * @param pxMouseRawY ##
   */
  public static final
  void ccDragTransformationalAnchor(int pxMouseRawX, int pxMouseRawY){
    O_DRAGGER.ccMouseDragged(pxMouseRawX, pxMouseRawY);
  }//+++
  
  //=== access ** axis
  
  /**
   * ##
   * @param pxColor ARGB
   */
  public static final void ccSetColor(int pxColor){
    pbAxisColor=pxColor;
  }//+++
  
  /**
   * ##
   * @param pxX pix
   * @param pxY pix
   */
  public static final void ccSetAnchor(int pxX,int pxY){
    pbAnchorX=pxX;
    pbAnchorY=pxY;
  }//+++
  
  /**
   * to the current mouse point of given owner.
   */
  public static final void ccSetAnchor(){
    if(pbOwner==null){return;}
    pbAnchorX=pbOwner.mouseX;
    pbAnchorY=pbOwner.mouseY;
  }//+++
  
  /**
   * ##
   */
  public static final void ccResetAnchor(){
    ccSetAnchor(0, 0);
  }//+++
  
  /**
   * flips states.
   */
  public static final void ccSetIsEnabled(){
    pbIsEnabled=!pbIsEnabled;
  }//+++
  
  /**
   * ##
   * @param pxStatus #
   */
  public static final void ccSetIsEnabled(boolean pxStatus){
    pbIsEnabled=pxStatus;
  }//+++
  
  //=== access ** transformation
  
  /**
   * cleans anchor and set scale to 1.0f.
   */
  public static final
  void ccResetTransformation(){
    O_DRAGGER.ccClearAnchor();
    pbScale = 1.0f;
    pbIsTransformed = false;
  }//++!
  
  /**
   * the offset is the start point of rectangle region location
   * @param pxX ##
   * @param pxY ##
   */
  public static final
  void ccSetTransformationalOffset(int pxX, int pxY){
    O_DRAGGER.ccSetOffset(pxX, pxY);
    pbIsTransformed = true;
  }//+++

  /**
   * the offset is the start point of rectangle region location
   * @param pxDiffX ##
   * @param pxDiffY ##
   */
  public static final
  void ccShiftTransformationalOffset(int pxDiffX, int pxDiffY){
    O_DRAGGER.ccSetOffset(O_DRAGGER.ccGetOffsetX()+pxDiffX, 
      O_DRAGGER.ccGetOffsetY()+pxDiffY
    );
    pbIsTransformed = true;
  }//+++
  
  /**
   * ##
   * @param pxScale ##
   */
  public static final
  void ccSetTransformationalScale(float pxScale){
    pbScale=PApplet.constrain(pxScale, 0.25f, 8.0f);
    pbIsTransformed = true;
  }//+++
  
  /**
   * ##
   * @param pxDiff ##
   */
  public static final void ccShiftTransformationalScale(float pxDiff){
    ccSetTransformationalScale(pbScale+pxDiff);
    pbIsTransformed = true;
  }//+++
  
  /**
   * calculated in loop from given owner.
   * @return ##
   */
  public static final int ccGetTransformedMouseX(){
    return pbTransmouseX;
  }//+++
  
  /**
   * calculated in loop from given owner.
   * @return ##
   */
  public static final int ccGetTransformedMouseY(){
    return pbTransmouseY;
  }//+++
  
  /**
   * just tells value as result.
   * @param pxRawX can be location or length
   * @return scale*(raw-offset)
   */
  public static final int ccTransformX(int pxRawX){
    return VcNumericUtility
      .ccMagnify(pxRawX-O_DRAGGER.ccGetOffsetX(), 1f/pbScale);
  }//+++
  
  /**
   * just tells value as result.
   * @param pxRawY can be location or length
   * @return scale*(raw-offset)
   */
  public static final int ccTransformY(int pxRawY){
    return VcNumericUtility
      .ccMagnify(pxRawY-O_DRAGGER.ccGetOffsetY(), 1f/pbScale);
  }//+++
  
}//***eof
