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
 * i thought if a box cant hold value it is not far away different
 * from a lamp.<br>
 * but anyway. so be it.<br>
 */
public class EcTextBox extends EcElement{
  
  private static final int
    //-- pix
    C_SHADOW_THICK = 2,
    //-- color
    C_COLOR_SHADOW = 0xFF444444
  ;//,,,
  
  //===
  
  /**
   * the key is intensely for naming translation
   * and have no effect on text.<br>
   * the form is the initial text content intensely for auto size calculating
   * and is supposed to get overwritten.<br>
   * @param pxKey will get passed to setter separately
   * @param pxForm will get passed to setter separately
   * @param pxID will get passed to setter directly
   */
  public EcTextBox(String pxKey, String pxForm, int pxID){
    super();
    ccSetKey(pxKey);
    ccSetName(pxKey);
    ccSetText(pxForm);
    ccSetID(pxID);
    ccSetTextAlign('l');
    ccSetTextColor(EcConst.C_LIT_GRAY);
    ccSetSize();
    ccSetColor(EcConst.ccAdjustColor(cmOffColor, 0x20));
  }//++!
  
  /**
   * output component can have no identical id.<br>
   * @param pxKey will get passed to key and name separately
   * @param pxForm serves as the initiate text
   */
  public EcTextBox(String pxKey, String pxForm){
    this(pxKey, pxForm, EcConst.C_ID_IGNORE);
  }//++!

  /**
   * will have no identical id.<br>
   * will have an empty string as key for name and text.<br>
   * default text is a dummy tag".<br>
   * default text align is left.<br>
   * default text color is light gray.<br>
   */
  public EcTextBox(){
    this("", "<text/>", EcConst.C_ID_IGNORE);
  }//++!
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate() {
    if(!ccIsVisible()){return;}
    drawDefaultTextBox();
    drawText(cmTextColor);
    drawName(cmNameColor);
  }//++~
  
  /**
   * internal use only
   */
  protected final void drawDefaultTextBox(){
    
    int lpW=cmW-C_SHADOW_THICK;
    int lpH=cmH-C_SHADOW_THICK;

    pbOwner.fill(C_COLOR_SHADOW);
    pbOwner.rect(cmX+C_SHADOW_THICK, cmY+C_SHADOW_THICK, lpW, lpH);
    pbOwner.fill(cmIsActivated?cmOnColor:cmOffColor);
    pbOwner.rect(cmX,cmY,lpW,lpH);
    
  }//++~
  
}//***eof
