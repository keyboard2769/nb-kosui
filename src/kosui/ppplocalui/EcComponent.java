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

package kosui.ppplocalui;

import processing.core.PApplet;

public abstract class EcComponent extends EcRect{
  
  /**
   * im not sure if it is right to handle the reference problem like this.
   */
  static protected PApplet pbOwner=null;
  
  /**
   * <pre>
   * it is NOT SAFE if an illegal PApplet has been initiated
   * make sure it is your RUNNING PApplet passed to this class
   * </pre>
   * @return true if owner is not null
   */
  static public final boolean ccHasOwner(){
    return pbOwner!=null;
  }//+++
  
  /**
   * <pre>
   * im not sure if it is right to put the main reference
   *   to THE SKETCH here.
   * but anyway i think this is the most convenient way!!
   * this will get to work only if what it has is not null.
   * </pre>
   * @param pxOwner your sketch
   */
  static public final void ccInitOwner(PApplet pxOwner){
    if(pxOwner==null){return;}
    pbOwner=pxOwner;
  }//+++
  
  /**
   * supposedly might get called from draw() loop.<br>
   * @param pxKey #
   * @return #
   */
  static public final boolean ccIsKeyPressed(char pxKey){
    if(pbOwner==null){return false;}
    return pbOwner.keyPressed && (pbOwner.key==pxKey);
  }//+++
  
  //===
  
  /**
   * supposedly get called from draw() of a PApplet.
   */
  abstract public void ccUpdate();
  
  //===
  
  /**
   * <b>NO NULL CHECK!!</b><br>
   * <b>MUST INITTIATE ROOT CLASS!!</b><br>
   * <pre>
   * calls rect() of PApplet.
   * </pre>
   * @param pxColor ARGB
   */
  protected final void drawRect(int pxColor){
    pbOwner.fill(pxColor);
    pbOwner.rect(cmX, cmY, cmW, cmH);
  }//+++
  
  /**
   * <b>NO NULL CHECK!!</b><br>
   * <b>MUST INITTIATE ROOT CLASS!!</b><br>
   * <pre>
   * calls line() of PApplet. 
   * this also calls noStroke() for convention reason. 
   * </pre>
   * @param pxColor ARGB
   * @deprecated combine complex pattern with this is not recommended.
   */
  @Deprecated protected final void drawLine(int pxColor){
    pbOwner.stroke(pxColor);
    pbOwner.line(cmX, cmY, ccEndX(), ccEndY());
    pbOwner.noStroke();
  }//+++
  
}//***eof
