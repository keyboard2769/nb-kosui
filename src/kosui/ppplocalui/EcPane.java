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
public class EcPane extends EcShape{
  
  //===
  private static final int C_TEXT_ADJ_Y=2;
  //===
  
  private String cmTitle="<title/>";
  
  //==
  
  /**
   * 
   * @param pxTitle #
   */
  public final void ccSetTitle(String pxTitle)
    {cmTitle=pxTitle;}//+++
  
  //==
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate() {
    
    pbOwner.fill(EcFactory.C_WHITE);
    pbOwner.rect(cmX,cmY,cmW,cmH);
    
    pbOwner.fill(EcFactory.C_BLACK);
    pbOwner.rect(cmX+2,cmY+18,cmW-4,cmH-21);
    
    pbOwner.text(cmTitle, cmX+2, cmY+2+C_TEXT_ADJ_Y);
  
  }//+++
  
}//***eof
