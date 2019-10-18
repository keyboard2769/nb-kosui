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

import processing.core.PApplet;

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
  private float cmRevised;
  
  /**
   * default additional bias is 100. <br>
   * default additional offset is 0. <br>
   * @param pxInputOffset passed to super directly
   * @param pxInputSpan passed to super directly
   * @param pxOutputOffset passed to super directly
   * @param pxOutputSpan passed to super directly
   */
  public ZcRevisedScaledModel(
    int pxInputOffset, int pxInputSpan,int pxOutputOffset, int pxOutputSpan
  ){
    super(pxInputOffset, pxInputSpan, pxOutputOffset, pxOutputSpan);
    ccResetReviser();
  }//++!
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public void ccRun(int pxInputValue) {
    super.ccRun(pxInputValue);
    ccRevise();
  }//++~
  
  /**
   * supposedly to get called in a loop only once
   */
  public final void ccRevise(){
    cmRevised=super.ccGetScaledFloatValue();
    cmRevised*=(((float)cmBias)/100f);
    cmRevised+=(float)cmOffset;
  }//++~
  
  //===
  
  /**
   * will get trimmed via bit masking 
   * @param pxPercentage say, 120 means "120%"
   */
  public final void ccSetReviseBias(int pxPercentage){
    cmBias=pxPercentage&0x1FFF;
  }//++<
  
  /**
   * @param pxOffset could be anything
   */
  public final void ccSetReviseOffset(int pxOffset){
    cmOffset=pxOffset;
  }//++<
  
  /**
   * aliasing setter
   * @param pxBias will get passed to setter directly
   * @param pxOffset will get passed to setter directly
   */
  public final void ccSetupReviser(int pxBias, int pxOffset){
    ccSetReviseBias(pxBias);
    ccSetReviseOffset(pxOffset);
  }//++<
  
  /**
   * default additional bias is 100. <br>
   * default additional offset is 0. <br>
   */
  public final void ccResetReviser(){
    ccSetReviseBias(100);
    ccSetReviseOffset(0);
  }//++<
  
  //===
  
  /**
   * calculates via float value. <br>
   * using force casting. <br>
   * @param pxSource could be any thing
   * @return (source - offset) *100 / bias
   */
  public final float ccToUnrevisedInputValue(float pxSource){
    float lpSource=pxSource-(float)cmOffset;
    lpSource*=100f;
    lpSource/=(float)cmBias;
    return ccToUnscaledInputValue(lpSource);
  }//++>
  
  /**
   * basically calculates via integer value. <br>
   * not using PApplet::ceil or force casting. <br>
   * @param pxSource could be any thing
   * @return (source - offset) *100 / bias
   */
  public final int ccToUnrevisedInputValue(int pxSource){
    int lpSource=pxSource-cmOffset;
    lpSource*=100;
    lpSource/=cmBias;
    return ccToUnscaledInputValue(lpSource);
  }//++>
  
  //===
  
  /**
   * @return could be anything
   */
  public final int ccGetReviseBias(){
    return cmBias;
  }//++>
  
  /**
   * @return could be anything
   */
  public final int ccGetReviseOffset(){
    return cmOffset;
  }//++>
  
  /**
   * must run before calling this getter.<br>
   * @return ceil(scaled * bias /100 + offset)
   */
  public final int ccGetRevisedIntegerValue(){
    return PApplet.ceil(cmRevised);
  }//++>
  
  /**
   * must run before calling this getter.<br>
   * @return scaled * bias /100 + offset
   */
  public final float ccGetRevisedFloatValue(){
    return cmRevised;
  }//++>

}//***eof
