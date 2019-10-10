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

package kosui.ppplogic;

/**
 * a real value is intensively referred to temperature and pressure.<br>
 * this is supposed to be an alternative to the PVector class
 * from Processing because we just don't need multi-dimension.<br>
 */
public class ZcReal {
  
  private float cmVal;
  private boolean cmIsStatic;
  
  /**
   * the default value is zero
   */
  public ZcReal(){
    ccSet(0f);
  }//++!
  
  /**
   * @param pxVal can be anything
   */
  public ZcReal(float pxVal){
    ccSet(pxVal);
  }//++!
  
  /**
   * @param pxVal can be anything
   * @param pxStatic a static instance just does not changes its value
   */
  public ZcReal(float pxVal, boolean pxStatic){
    ccSet(pxVal);
    cmIsStatic=pxStatic;
  }//++!
  
  //===
  
  /**
   * @param pxVal can be anything
   */
  public final void ccSet(float pxVal){
    if(cmIsStatic){return;}
    cmVal=pxVal;
  }//++<
  
  /**
   * @param pxOffset just got added
   */
  public final void ccShift(float pxOffset){
    if(cmIsStatic){return;}
    cmVal+=pxOffset;
  }//++<
  
  /**
   * works exactly as transferred from a static source.<br>
   * a static one can also get effected.<br>
   * @param pxSource which effects this 
   * @param pxRatio the bigger the slower, will get masked to 1-255
   */
  public final void ccEffect(float pxSource, int pxRatio){
    cmVal+=((pxSource-cmVal)/ccFixRatio(pxRatio));
  }//++<
  
  /**
   * works exactly as transferred from a static source.<br>
   * a static one can also get effected.<br>
   * the default speed ratio is 16.<br>
   * @param pxSource which effects this 
   */
  public final void ccEffect(float pxSource){
    ccEffect(pxSource, 16);
  }//++<
  
  /**
   * @return the value
   */
  public final float ccGet(){
    return cmVal;
  }//++>
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public String toString() {
    StringBuilder lpRes
      = new StringBuilder(ZcReal.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(this.hashCode()));
    lpRes.append('$');
    lpRes.append(Float.toString(cmVal));
    return lpRes.toString();
  }//+++
  
  //===
  
  private static float ccFixRatio(int pxRatio){
    int lpFixed=pxRatio&0xFF;
    return lpFixed==0?1f:((float)lpFixed);
  }//+++
  
  /**
   * both values is effected via the transferring if not static.<br>
   * @param pxPotentialP serves as the positive one but does not matter
   * @param pxPotentialN serves as the negative one but does not matter
   * @param pxRatio the bigger the slower, will get masked to 1-255
   */
  public final static
  void ccTransfer(ZcReal pxPotentialP, ZcReal pxPotentialN, int pxRatio){
    float lpDiff=pxPotentialP.ccGet()-pxPotentialN.ccGet();
    lpDiff/=ccFixRatio(pxRatio);
    pxPotentialP.ccShift(-1*lpDiff);
    pxPotentialN.ccShift(   lpDiff);
  }//+++
  
  /**
   * both values is effected via the transferring if not static.<br>
   * the default speed ratio is 16.<br>
   * @param pxPotentialP serves as the positive one but does not matter
   * @param pxPotentialN serves as the negative one but does not matter
   */
  public final static 
  void ccTransfer(ZcReal pxPotentialP, ZcReal pxPotentialN){
    ccTransfer(pxPotentialP, pxPotentialN, 16);
  }//+++
  
}//***eof
