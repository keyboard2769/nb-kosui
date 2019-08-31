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

import kosui.ppputil.VcConst;

/**
 * a pane with title just lies under elements.<br>
 * may looks like some window.<br>
 */
public class EcPane extends EcShape{
  
  private static final int C_TEXT_ADJ_Y=2;
  
  //===
  
  private String cmTitle="<title/>";
  
  /**
   * inherited default
   */
  public EcPane(){
    super();
  }//..!
  
  /**
   * inherited plus alpha
   * @param pxTitle will get passed to setter directly
   */
  public EcPane(String pxTitle){
    super();
    ccSetTitle(pxTitle);
  }//..!
  
  /**
   * inherited plus alpha
   * @param pxTitle will get passed to setter directly
   * @param pxW pix
   * @param pxH pix
   */
  public EcPane(String pxTitle, int pxW, int pxH){
    super(pxW, pxH);
    ccSetTitle(pxTitle);
  }//..!
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate() {
    
    pbOwner.fill(EcConst.C_WHITE);
    pbOwner.rect(cmX,cmY,cmW,cmH);
    
    pbOwner.fill(EcConst.C_BLACK);
    pbOwner.rect(cmX+2,cmY+18,cmW-4,cmH-21);
    
    pbOwner.text(cmTitle, cmX+2, cmY+2+C_TEXT_ADJ_Y);
  
  }//+++
  
  /**
   * if you want a empty title, pass a space.<br>
   * @param pxTitle must have something
   */
  public final void ccSetTitle(String pxTitle){
    if(!VcConst.ccIsValidString(pxTitle)){return;}
    cmTitle=pxTitle;
  }//+++
  
}//***eof
