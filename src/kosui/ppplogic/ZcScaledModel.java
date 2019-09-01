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

package kosui.ppplogic;

import static processing.core.PApplet.map;
import static processing.core.PApplet.ceil;

/**
 * we use this to convert analog input value to some real value.<br>
 * works the exactly same as map() method in processing.<br>
 */
public class ZcScaledModel {
  
  private int 
    cmInputValue,
    cmInputOffset, cmInputSpan,
    cmOutputOffset, cmOutputSpan
  ;//...
  private int cmIntegerOutput;
  private float cmFloatOutput;
  
  //===
  
  /**
   * ##
   * @param pxInputOffset #
   * @param pxInputSpan #
   * @param pxOutputOffset #
   * @param pxOutputSpan #
   */
  public ZcScaledModel(
    int pxInputOffset, int pxInputSpan,int pxOutputOffset, int pxOutputSpan
  ){
    cmInputValue=pxInputOffset;
    ccSetupScale(pxInputOffset, pxInputSpan, pxOutputOffset, pxOutputSpan);
    cmIntegerOutput=0;
    cmFloatOutput=0f;
  }//+++
  
  //==
    
  /**
   * calculates at the same time.<br>
   * @param pxInputValue #
   */
  public void ccSetInputValue(int pxInputValue){
    cmInputValue = pxInputValue;
    cmFloatOutput=ccToScaledFloatValue(pxInputValue);
    cmIntegerOutput=ceil(cmFloatOutput);
  }//+++

  /**
   * <pre>
   * since check function will be called separately, 
   *   using ccSetupScale() is recommended.
   * </pre>
   * @param pxInputOffset the inputOffset to set
   */
  public void ccSetInputOffset(int pxInputOffset){
    cmInputOffset = pxInputOffset;
    ssForceValidity();
  }//+++

  /**
   * <pre>
   * since check function will be called separately, 
   *   using ccSetupScale() is recommended.
   * </pre>
   * @param pxInputSpan the inputSpan to set
   */
  public void ccSetInputSpan(int pxInputSpan){
    cmInputSpan = pxInputSpan;
    ssForceValidity();
  }//+++

  /**
   * <pre>
   * since check function will be called separately, 
   *   using ccSetupScale() is recommended.
   * </pre>
   * @param pxOutputOffset the outputOffset to set
   */
  public void ccSetOutputOffset(int pxOutputOffset){
    cmOutputOffset = pxOutputOffset;
    ssForceValidity();
  }//+++

  /**
   * <pre>
   * since check function will be called separately, 
   *   using ccSetupScale() is recommended.
   * </pre>
   * @param outputSpan the outputSpan to set
   */
  public void ccSetOutputSpan(int outputSpan){
    cmOutputSpan = outputSpan;ssForceValidity();
  }//+++
    
  /**
   * ##
   * @param pxInputOffset #
   * @param pxInputSpan #
   * @param pxOutputOffset #
   * @param pxOutputSpan #
   */
  public final void ccSetupScale(
    int pxInputOffset, int pxInputSpan,int pxOutputOffset, int pxOutputSpan
  ){
    cmInputOffset=pxInputOffset;cmInputSpan=pxInputSpan;
    cmOutputOffset=pxOutputOffset;cmOutputSpan=pxOutputSpan;
    ssForceValidity();
  }//+++
    
  //===
    
  private void ssForceValidity(){
    
    if(cmInputOffset<0 || cmInputSpan<0){
      cmInputOffset=0;cmInputSpan=1;
    }else{
      if(cmInputOffset>=cmInputSpan){
        cmInputSpan=cmInputOffset+1;
      }//..?
    }//..?
    
    if(cmOutputOffset<0 || cmOutputSpan<0){
      cmOutputOffset=0;cmOutputSpan=1;
    }else{
      if(cmOutputOffset>=cmOutputSpan){
        cmOutputSpan=cmOutputOffset+1;
      }//..?
    }//..?
    
  }//+++
  
  //===
  
  /**
   * via PApplet::map before casting.<br>
   * @param pxSource supposedly a output/real value
   * @return pxSource casted instead of PApplet::ceil
   */
  public final int ccToUnscaledInputValue(float pxSource){
    return (int)(map(
      pxSource,
      (float)cmOutputOffset,(float)cmOutputSpan,
      (float)cmInputOffset,(float)cmInputSpan
    ));
  }//+++
  
  /**
   * via PApplet::map before casting.<br>
   * @param pxSource supposedly a output/real value
   * @return pxSource casted instead of PApplet::ceil
   */
  public final int ccToUnscaledInputValue(int pxSource){
    return (int)(map(
      pxSource,cmOutputOffset,cmOutputSpan,cmInputOffset,cmInputSpan
    ));
  }//+++
  
  /**
   * via PApplet::map.<br>
   * @param pxSource #
   * @return #
   */
  public final float ccToScaledFloatValue(int pxSource){
    return map(
      pxSource, cmInputOffset, cmInputSpan, cmOutputOffset, cmOutputSpan
    );
  }//+++
  
  /**
   * via PApplet::ceil after ::map.<br>
   * @param pxSource #
   * @return #
   */
  public final int ccToScaledIntegerValue(int pxSource){
    return ceil(ccToScaledFloatValue(pxSource));
  }//+++
  
  //===
  
  /**
   * 
   * @return #
   */
  public final float ccGetScaledFloatValue(){
    return cmFloatOutput;
  }//+++
    
  /**
   * 
   * @return #
   */
  public final int ccGetScaledIntegerValue(){
    return cmIntegerOutput;
  }//+++
  
  /**
   * @return #
   */
  public int ccGetInputValue(){
    return cmInputValue;
  }//+++

  /**
   * @return #
   */
  public int ccGetInputOffset(){
    return cmInputOffset;
  }//+++

  /**
   * @return #
   */
  public int ccGetInputSpan(){
    return cmInputSpan;
  }//+++

  /**
   * @return #
   */
  public int ccGetOutputOffset(){
    return cmOutputOffset;
  }//+++

  /**
   * @return #
   */
  public int ccGetOutputSpan(){
    return cmOutputSpan;
  }//+++
  
}//***eof
