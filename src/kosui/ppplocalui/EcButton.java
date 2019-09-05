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
 * a button react at mouse hovering, <br>
 * there is no other difference. <br>
 */
public class EcButton extends EcElement {
  
  //===
  
  private static final int
    //-- pix
    C_STROKE_THICK =2,
    //-- color
    C_COLOR_STROKE       =0xFF555555,
    C_COLOR_FILL_OVER    =0xFFAACCAA,
    C_COLOR_FILL_PRESSED =0xFF99BB99,
    C_COLOR_FILL_NORMAL  =0xFF999999
  ;//...

  /**
   * inherited default
   */
  public EcButton(){
    super();
  }//..!
  
  /**
   * inherited
   * @param pxKey #
   * @param pxID #
   */
  public EcButton(String pxKey, int pxID){
    super(pxKey, pxID);
    ccSetSize();
  }//..!
  
  //===
  
  /**
   * {@inheritDoc}
   */
  @Override public void ccUpdate() {
    
    drawDefualtButton();
    drawName(cmNameColor);
    drawText(ccActColor());
    
  }//+++
  
  /**
   * internal use only
   */
  protected final void drawDefualtButton(){
    
    pbOwner.fill(C_COLOR_STROKE);
    pbOwner.rect(cmX,cmY,cmW,cmH);
    
    pbOwner.fill(ccIsMouseHovered()?
      (pbOwner.mousePressed?
        C_COLOR_FILL_PRESSED
       :C_COLOR_FILL_OVER
      ):C_COLOR_FILL_NORMAL
    );
    pbOwner.rect(
      cmX+C_STROKE_THICK  ,cmY+C_STROKE_THICK,
      cmW-C_STROKE_THICK*2,cmH-C_STROKE_THICK*2
    );
    
  }//+++
  
}//***eof
