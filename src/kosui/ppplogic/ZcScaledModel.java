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

import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

/**
 * we use this to convert analog input value to some real value.<br>
 * works the exactly same as map() method in processing.<br>
 */
public class ZcScaledModel {
  
  private int 
    cmInputValue,
    cmInputOffset, cmInputSpan,
    cmOutputOffset, cmOutputSpan
  ;//,,,
  private int cmIntegerOutput;
  private float cmFloatOutput;
  
  //===
  
  /**
   * @param pxInputOffset no minus no equals to span
   * @param pxInputSpan no minus no equals to offset
   * @param pxOutputOffset no minus no equals to span
   * @param pxOutputSpan no minus no equals to offset
   */
  public ZcScaledModel(
    int pxInputOffset, int pxInputSpan,int pxOutputOffset, int pxOutputSpan
  ){
    cmInputValue=pxInputOffset;
    ccSetupScale(pxInputOffset, pxInputSpan, pxOutputOffset, pxOutputSpan);
    cmIntegerOutput=0;
    cmFloatOutput=0f;
  }//++!
  
  //===
  
  /**
   * chaining setter with the looper
   * @param pxInputValue will get trimmed via PApplet.constrain
   */
  public void ccRun(int pxInputValue){
    ccSetInputValue(pxInputValue);
    ccRun();
  }//++~
  
  /**
   * supposedly should get called from a scan loop only once.<br>
   */
  public final void ccRun(){
    cmFloatOutput=ccToScaledFloatValue(cmInputValue);
    cmIntegerOutput=PApplet.ceil(cmFloatOutput);
  }//++~
  
  private void ssForceValidate(){
    
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
   * @param pxInputValue could be anything
   */
  public final void ccSetInputValue(int pxInputValue){
    cmInputValue = pxInputValue;
  }//++<

  /**
   * <pre>
   * since check function will be called separately, 
   *   using ccSetupScale() is recommended.
   * </pre>
   * @param pxInputOffset  no minus no equals to span
   */
  public final void ccSetInputOffset(int pxInputOffset){
    cmInputOffset = pxInputOffset;
    ssForceValidate();
  }//++<

  /**
   * <pre>
   * since check function will be called separately, 
   *   using ccSetupScale() is recommended.
   * </pre>
   * @param pxInputSpan no minus no equals to offset
   */
  public final void ccSetInputSpan(int pxInputSpan){
    cmInputSpan = pxInputSpan;
    ssForceValidate();
  }//++<

  /**
   * <pre>
   * since validate function will be called separately, 
   *   using ccSetupScale() is recommended.
   * </pre>
   * @param pxOutputOffset no minus no equals to span
   */
  public final void ccSetOutputOffset(int pxOutputOffset){
    cmOutputOffset = pxOutputOffset;
    ssForceValidate();
  }//++<

  /**
   * <pre>
   * since check function will be called separately, 
   *   using ccSetupScale() is recommended.
   * </pre>
   * @param outputSpan no minus no equals to offset
   */
  public final void ccSetOutputSpan(int outputSpan){
    cmOutputSpan = outputSpan;ssForceValidate();
  }//++<
    
  /**
   * @param pxInputOffset no minus no equals to span
   * @param pxInputSpan no minus no equals to offset
   * @param pxOutputOffset no minus no equals to span
   * @param pxOutputSpan no minus no equals to offset
   */
  public final void ccSetupScale(
    int pxInputOffset, int pxInputSpan,int pxOutputOffset, int pxOutputSpan
  ){
    cmInputOffset=pxInputOffset;
    cmInputSpan=pxInputSpan;
    cmOutputOffset=pxOutputOffset;
    cmOutputSpan=pxOutputSpan;
    ssForceValidate();
  }//++<
  
  //===
  
  /**
   * must run before calling this getter.<br>
   * @return map(input,i-off,i-span,o-off,o-span)
   */
  public final float ccGetScaledFloatValue(){
    return cmFloatOutput;
  }//++>
    
  /**
   * must run before calling this getter.<br>
   * @return ceil(map(input,i-off,i-span,o-off,o-span))
   */
  public final int ccGetScaledIntegerValue(){
    return cmIntegerOutput;
  }//++>
  
  /**
   * @return could be anything
   */
  public final int ccGetInputValue(){
    return cmInputValue;
  }//++>

  /**
   * @return could be anything
   */
  public final int ccGetInputOffset(){
    return cmInputOffset;
  }//++>

  /**
   * @return could be anything
   */
  public final int ccGetInputSpan(){
    return cmInputSpan;
  }//++>

  /**
   * @return could be anything
   */
  public final int ccGetOutputOffset(){
    return cmOutputOffset;
  }//++>

  /**
   * @return could be anything
   */
  public final int ccGetOutputSpan(){
    return cmOutputSpan;
  }//++>
  
  //===
  
  /**
   * via PApplet::map before casting.<br>
   * @param pxSource supposedly a output/real value
   * @return pxSource casted instead of PApplet::ceil
   */
  public final int ccToUnscaledInputValue(float pxSource){
    return (int)(PApplet.map(
      pxSource,
      (float)cmOutputOffset,(float)cmOutputSpan,
      (float)cmInputOffset,(float)cmInputSpan
    ));
  }//++>
  
  /**
   * via PApplet::map before casting.<br>
   * @param pxSource supposedly a output/real value
   * @return pxSource casted instead of PApplet::ceil
   */
  public final int ccToUnscaledInputValue(int pxSource){
    return (int)(PApplet.map(
      pxSource,cmOutputOffset,cmOutputSpan,cmInputOffset,cmInputSpan
    ));
  }//++>
  
  /**
   * via PApplet::map.<br>
   * @param pxSource #
   * @return #
   */
  public final float ccToScaledFloatValue(int pxSource){
    return PApplet.map(
      pxSource, cmInputOffset, cmInputSpan, cmOutputOffset, cmOutputSpan
    );
  }//++>
  
  /**
   * via PApplet::ceil after ::map.<br>
   * @param pxSource #
   * @return #
   */
  public final int ccToScaledIntegerValue(int pxSource){
    return PApplet.ceil(ccToScaledFloatValue(pxSource));
  }//++>
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public String toString() {
    StringBuilder lpRes
      = new StringBuilder(ZcRangedModel.class.getSimpleName());
    lpRes.append('@');
    lpRes.append(Integer.toHexString(hashCode()));
    lpRes.append('$');
    lpRes.append(VcStringUtility.ccPackupPairedTag("i", cmInputValue));
    lpRes.append(VcStringUtility.ccPackupPairedTag("o", cmIntegerOutput));
    lpRes.append('|');
    lpRes.append(VcStringUtility.ccPackupPairedTag("iO", cmInputOffset));
    lpRes.append(VcStringUtility.ccPackupPairedTag("iS", cmInputSpan));
    lpRes.append(VcStringUtility.ccPackupPairedTag("oO", cmOutputOffset));
    lpRes.append(VcStringUtility.ccPackupPairedTag("oS", cmOutputSpan));
    return lpRes.toString();
  }//+++
  
}//***eof
