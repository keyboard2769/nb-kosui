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

/**
 * i thought a point is not a vector.<br>
 * but a root class should not look like this.<br>
 */
public class EcPoint {
  
  /**
   * location
   */
  protected int cmX, cmY;

  /**
   * all initiated value is eight.
   */
  public EcPoint(){
    cmX=8;cmY=8;
  }//..!
  
  /**
   * @param pxX will get passed to ccSetLocation() directly
   * @param pxY will get passed to ccSetLocation() directly 
   */
  public EcPoint(int pxX, int pxY){
    ccSetLocation(pxX, pxY);
  }//..!
  
  //===
  
  /**
   * 
   * @param pxX pix:0-65536
   */
  public final void ccSetX(int pxX){
    cmX=pxX&0xFFFF;
  }//+++
  
  /**
   * 
   * @param pxY pix:0-65535
   */
  public final void ccSetY(int pxY){
    cmY=pxY&0xFFFF;
  }//+++
  
  /**
   * @param pxX pix:0-65535
   * @param pxY pix:0-65535
   */
  public final void ccSetLocation(int pxX,int pxY){
    ccSetX(pxX);
    ccSetY(pxY);
  }//+++
  
  /**
   * simply add current value with offset
   * @param pxOffsetX #
   * @param pxOffsetY #
   */
  public final void ccShiftLocation(int pxOffsetX, int pxOffsetY){
    cmX+=pxOffsetX;
    cmY+=pxOffsetY;
  }//+++
  
  /**
   * 
   * @return x
   */
  public final int ccGetX(){
    return cmX;
  }//+++
  
  /**
   * 
   * @return y
   */
  public final int ccGetY(){
    return cmY;
  }//+++
  
}//***eof
