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
    C_COLOR_SHADOW = 0xFF555555
  ;//...
  
  //===

  /**
   * default text align is left.<br>
   * default text color is light gray.<br>
   */
  public EcTextBox(){
    super();
    ccSetTextAlign('l');
    ccSetTextColor(EcFactory.C_LIT_GRAY);
  }//++!
  
  //===
  
  @Override public void ccUpdate() {
    
    drawDefaultTextBox();
    drawText(cmTextColor);
    drawName(cmNameColor);
  
  }//+++
  
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
    
  }//+++
  
}//***eof
