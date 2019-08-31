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

import processing.core.PApplet;

/**
 * i thought a point is not a vector.<br>
 * but a root class should not look like this.<br>
 */
public class EcPoint {
  
  /**
   * im not sure if it is right to handle the reference problem like this.
   */
  static protected PApplet pbOwner=null;
  
  /**
   * location
   */
  protected int cmX=8, cmY=8;
  
  //===
  
  /**
   * calls set() of PApplet
   * @param pxColor #
   */
  protected final void drawPoint(int pxColor){
    pbOwner.set(cmX, cmY, pxColor);
  }//+++
  
  //===
  
  /**
   * im not sure if it is right to put the main reference
   * to THE SKETCH here
   * but anyway i think this is the most convenient way!!
   * @param pxOwner your sketch
   */
  static public final void ccInitOwner(PApplet pxOwner)
    {pbOwner=pxOwner;}//+++
  
  /**
   * incase you just use one element.<br>
   * not sure if it is a good idea.
   * @param pxOwner your sketch
   */
  public final void ccSetOwner(PApplet pxOwner)
    {pbOwner=pxOwner;}//+++
  
  /**
   * does NOT accept minus value
   * @param pxX #
   * @param pxY #
   */
  public final void ccSetLocation(int pxX,int pxY){
    if(pxX>=0){cmX=pxX;}
    if(pxY>=0){cmY=pxY;}
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
  public final int ccGetX(){return cmX;}//+++
  
  /**
   * 
   * @return y
   */
  public final int ccGetY(){return cmY;}//+++
  
  //==
  
  /**
   * it is NOT SAFE if an illegal PApplet has been initiated
   * make sure it is your RUNNING PApplet passed to this class
   * @return true if owner is not null
   */
  static public final boolean ccHasOwner()
    {return pbOwner!=null;}//+++
  
}//***eof
