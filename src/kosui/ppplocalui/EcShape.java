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

import kosui.pppmodel.McConst;
import processing.data.XML;

/**
 * a shape don't react at user, but it is a component. <br>
 * and it always lies under elements. <br>
 */
public class EcShape extends EcComponent {
  
  /**
   * the fill of the shape
   */
  protected int cmBaseColor=EcConst.C_BLACK;

  /**
   * base color is black by default.<br>
   */
  public EcShape(){
    super();
  }//..!
  
  /**
   * ##
   * @param pxW ##
   * @param pxH ##
   */
  public EcShape(int pxW, int pxH){
    super();
    ccSetSize(pxW, pxH);
  }//..!
  
  public EcShape(XML pxSVGRepresentation){
    super();
    if(pxSVGRepresentation!=null){
      cmX=McConst.ccGetIntegerValue(pxSVGRepresentation, "x", cmX);
      cmY=McConst.ccGetIntegerValue(pxSVGRepresentation, "y", cmY);
      cmW=McConst.ccGetIntegerValue(pxSVGRepresentation, "width", cmW);
      cmH=McConst.ccGetIntegerValue(pxSVGRepresentation, "height", cmH);
    }//..?
  }//..!
  
  //===
  
  /**
   * should never be called directly from draw()
   * even it might works
   */
  @Override public void ccUpdate(){
    if(!ccIsVisible()){return;}
    drawRect(cmBaseColor);
  }//+++
  
  //===
  
  /**
   * 
   * @param pxColor the fill of the shape
   */
  public final void ccSetBaseColor(int pxColor){
    cmBaseColor=pxColor;
  }//+++
  
}//***eof
