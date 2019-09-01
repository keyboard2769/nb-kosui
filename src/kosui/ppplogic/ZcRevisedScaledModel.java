/*
 * Copyright (C) 2019 Key Parker from K.I.C.
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
 * <pre>
 * sometime the result just can not match out side standards
 *   via offset-span scaling.
 * this model applies an additional adjust to the result
 *   by multiplying a "bias" and adding a "offset".
 * </pre>
 */
public class ZcRevisedScaledModel extends ZcScaledModel{
  
  private int cmBias,cmOffset;
  
  /**
   * default additional bias is 100. <br>
   * default additional offset is 0. <br>
   * @param pxInputOffset #
   * @param pxInputSpan #
   * @param pxOutputOffset #
   * @param pxOutputSpan #
   */
  public ZcRevisedScaledModel(
    int pxInputOffset, int pxInputSpan,int pxOutputOffset, int pxOutputSpan
  ){
    super(pxInputOffset, pxInputSpan, pxOutputOffset, pxOutputSpan);
    ccClearRevicer();
  }//++!
  
  //===
  
  /**
   * 
   * @param pxBias #
   * @param pxOffset #
   */
  public final void ccSetupRevicer(int pxBias, int pxOffset){
    ccSetBias(pxBias);
    ccSetOffset(pxOffset);
  }//+++
  
  /**
   * default additional bias is 100. <br>
   * default additional offset is 0. <br>
   */
  public final void ccClearRevicer(){
    ccSetBias(100);
    ccSetOffset(0);
  }//+++
  
  /**
   * will get trimmed via bit masking 
   * @param pxPercentage say, 120 means "120%"
   */
  public final void ccSetBias(int pxPercentage){
    cmBias=pxPercentage&0x1FFF;
  }//+++
  
  /**
   * 
   * @param pxOffset #
   */
  public final void ccSetOffset(int pxOffset){
    cmOffset=pxOffset;
  }//+++
  
  //===
  
  /**
   * calculates via float value. <br>
   * using force casting. <br>
   * @param pxSource #
   * @return #
   */
  public final float ccToUnrevisedInputValue(float pxSource){
    float lpSource=pxSource-(float)cmOffset;
    lpSource*=100f;
    lpSource/=(float)cmBias;
    return ccToUnscaledInputValue(lpSource);
  }//+++
  
  
  /**
   * basically calculates via integer value. <br>
   * not using PApplet::ceil or force casting. <br>
   * @param pxSource #
   * @return #
   */
  public final int ccToUnrevisedInputValue(int pxSource){
    int lpSource=pxSource-cmOffset;
    lpSource*=100;
    lpSource/=cmBias;
    return ccToUnscaledInputValue(lpSource);
  }//+++
  
  //===
  
  /**
   * basically calculates via integer value. <br>
   * not using PApplet::ceil or force casting. <br>
   * @return #
   */
  public final int ccGetRevisedIntegerValue(){
    int lpScaled=ccGetScaledIntegerValue();
    lpScaled*=cmBias;
    lpScaled/=100;
    lpScaled+=cmOffset;
    return lpScaled;
  }//+++
  
  /**
   * basically calculates via integer value. <br>
   * using force casting. <br>
   * @return #
   */
  public final float ccGetRevisedFloatValue(){
    float lpScaled=ccGetScaledFloatValue();
    lpScaled*=(((float)cmBias)/100f);
    lpScaled+=(float)cmOffset;
    return lpScaled;
  }//+++

}//***eof
