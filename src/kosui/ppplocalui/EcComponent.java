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

/**
 * so what actually was a PAppler? graphic? frame? owner? or just "applet"?<br>
 * you can not hope a interface work without know what it is facing.<br>
 */
public abstract class EcComponent extends EcRect{
  
  /**
   * im not sure if it is right to handle the reference problem like this.
   */
  static protected PApplet pbOwner=null;
  
  /**
   * un-paged component still exists but not visible nor click-able.<r>
   */
  static private int pbCurrentPage=0;
  
  /**
   * max page amount is 255
   */
  static public final int C_PAGE_MASK = 0xFF;
  
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
   * ##
   * @param pxOffset will get added and masked
   */
  static public final void ccShiftCurrentPage(int pxOffset){
    pbCurrentPage=(pxOffset+pbCurrentPage)&C_PAGE_MASK;
  }//+++
  
  /**
   * ##
   * @param pxPage will get masked
   */
  static public final void ccSetCurrentPage(int pxPage){
    pbCurrentPage=pxPage&C_PAGE_MASK;
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
  
  /**
   * @return ##
   */
  static public final int ccGetCurrentPage(){
    return pbCurrentPage;
  }//+++
  
  //===
  
  private int
    cmPage = 0
  ;//...
  
  private boolean 
    cmIsEnabled   = true,
    cmIsVisible   = true
  ;//...
  
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
  
  //===
  
  /**
   * <pre>
   * paging is a status of component and the component class.
   * unpaged component is not visible nor clickable but still
   *   exists to occupy your memory.
   * </pre>
   * @param pxPage 0-255
   */
  public final void ccSetPage(int pxPage){
    cmPage=pxPage&C_PAGE_MASK;
  }//+++
  
  /**
   * an enabled one is a mouse click able one.<br>
   * @param pxStatus #
   */
  public final void ccSetIsEnabled(boolean pxStatus){
    cmIsEnabled=pxStatus;
  }//+++
  
  /**
   * the visibility only means the ccUpdate() will get bypassed or not.<br>
   * @param pxStatus #
   */
  public final void ccSetIsVisible(boolean pxStatus){
    cmIsVisible=pxStatus;
  }//+++
  
  /**
   * <pre>
   * page zero is the default page, they are always visible and click-able.
   * but zero paged element will still be there it is not at current page.
   * </pre>
   * @return #
   */
  public final boolean ccIsAtCurrentPage(){
    return cmPage==pbCurrentPage;
  }//+++
  
  /**
   * @return also affected with the paging mechanism.<br>
   */
  public final boolean ccIsEnabled(){
    return cmIsEnabled&(ccIsAtCurrentPage()||cmPage==0);
  }//+++
  
  /**
   * @return also affected with the paging mechanism.<br>
   */
  public final boolean ccIsVisible(){
    return cmIsVisible&(ccIsAtCurrentPage()||cmPage==0);
  }//+++
  
}//***eof
