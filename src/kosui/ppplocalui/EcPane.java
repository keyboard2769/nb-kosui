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
 * a pane with title just lies under elements.<br>
 * may looks like some window.<br>
 */
public class EcPane extends EcLabel{
  
  private static final int C_TEXT_ADJ_Y=2;
  
  //===
  
  /**
   * inherited default
   */
  public EcPane(){
    super();
  }//..!
  
  /**
   * inherited plus alpha.<br>
   * size is 100x100 pix by default.<br>
   * @param pxTitle will get passed to setter directly
   */
  public EcPane(String pxTitle){
    super(pxTitle);
    ccSetSize(100, 100);
  }//..!
  
  /**
   * inherited plus alpha
   * @param pxTitle will get passed to setter directly
   * @param pxW pix
   * @param pxH pix
   */
  public EcPane(String pxTitle, int pxW, int pxH){
    super(pxTitle);
    if(pxW > 0 || pxH > 0){
      ccSetSize(pxW, pxH);
    }else{
      ccSetSize(100, 100);
    }//..?
  }//..!
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate() {
    
    if(!ccIsVisible()){return;}
    
    pbOwner.fill(cmBorderColor);
    pbOwner.rect(cmX,cmY,cmW,cmH);
    
    pbOwner.fill(cmBaseColor);
    pbOwner.rect(cmX+2,cmY+18,cmW-4,cmH-21);
    
    pbOwner.fill(cmTextColor);
    pbOwner.text(cmText, cmX+2, cmY+2+C_TEXT_ADJ_Y);
  
  }//+++
  
}//***eof
