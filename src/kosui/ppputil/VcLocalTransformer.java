/*
 * Copyright (C) 2020 Key Parker from K.I.C
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
 * most graphic software have separated `hand` tool and `zoom` tool.
 * most design aid software have them defualtly combined
 *   but they did not give it a name.
 * just it's just i can't figure it out.
 * </pre>
 */
public final class VcLocalTransformer {
  
  private static final ScDragAnchor O_DRAGGER = new ScDragAnchor();

  private static float cmScale = 1.0f;

  public static final void ccReset(){
    O_DRAGGER.ccClearAnchor();
    cmScale   = 1.0f;
  }//++!
  
  //=== direct

  /**
   * the offset is the start point of rectangle region location
   * @param pxX ##
   * @param pxY ##
   */
  public static final void ccSetOffset(int pxX, int pxY){
    O_DRAGGER.ccSetOffset(pxX, pxY);
  }//+++

  /**
   * the offset is the start point of rectangle region location
   * @param pxDiffX ##
   * @param pxDiffY ##
   */
  public static final void ccShiftOffset(int pxDiffX, int pxDiffY){
    O_DRAGGER.ccSetOffset(O_DRAGGER.ccGetOffsetX()+pxDiffX, 
      O_DRAGGER.ccGetOffsetY()+pxDiffY
    );
  }//+++
  
  /**
   * ##
   * @param pxScale ##
   */
  public static final void ccSetScale(float pxScale){
    cmScale=PApplet.constrain(pxScale, 0.25f, 8.0f);
  }//+++
  
  /**
   * ##
   * @param pxDiff ##
   */
  public static final void ccShiftScale(float pxDiff){
    ccSetScale(cmScale+pxDiff);
  }//+++
  
  //== teller 
  
  /**
   * ##
   * @param pxRawX can be location or length
   * @return scale*(raw-offset)
   */
  public static final int ccTransformX(int pxRawX){
    return VcNumericUtility.ccMagnify(pxRawX-O_DRAGGER.ccGetOffsetX(), 1f/cmScale);
  }//+++
  
  /**
   * ##
   * @param pxRawY can be location or length
   * @return scale*(raw-offset)
   */
  public static final int ccTransformY(int pxRawY){
    return VcNumericUtility.ccMagnify(pxRawY-O_DRAGGER.ccGetOffsetY(), 1f/cmScale);
  }//+++
  
  //=== loop

  /**
   * call this before. 
   * @param pxOwner never null
   */
  public static final void ccEnter(PApplet pxOwner){
    if(pxOwner==null){return;}
    pxOwner.pushMatrix();
    pxOwner.translate(O_DRAGGER.ccGetOffsetX(), O_DRAGGER.ccGetOffsetY());
    pxOwner.scale(cmScale);
  }//+++

  /**
   * call this after.
   * @param pxOwner never null
   */
  public static final void ccExit(PApplet pxOwner){
    if(pxOwner==null){return;}
    pxOwner.popMatrix();
  }//+++
  
  /**
   * call this on event.
   */
  public static final void ccMouseReleased(){
    O_DRAGGER.ccClearAnchor();
  }//+++
  
  /**
   * call this on event.
   * @param pxMouseRawX ##
   * @param pxMouseRawY ##
   */
  public static final void ccMouseDragged(int pxMouseRawX, int pxMouseRawY){
    O_DRAGGER.ccMouseDragged(pxMouseRawX, pxMouseRawY);
  }//+++
  
}//***eof
