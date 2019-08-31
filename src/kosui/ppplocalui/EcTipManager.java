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

import java.util.HashMap;

/**
 * basically a hash map finds tip by id, not supposed to be used
 * outside from a coordinator. <br>
 * but actually it is just a rectangle with text. <br>
 */
final class EcTipManager extends EcComponent {
  
  private static final int 
    C_TEXT_ADJ_X = 2,
    C_TEXT_ADJ_Y = 2
  ;//...
  
  //===
  
  private final HashMap<Integer, EcTip> cmTipMap=new HashMap();
  private boolean cmIsDisabled=true;
  private int cmPaneColor=0xAA111111;
  private int cmTextColor=0xFFCCCCCC;
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
    ssUpdate(0);
  }//+++
  
  /**
   * not supposed to be used outside from a coordinator. <br>
   * @param pxID supposedly the current mouse hovered element's id. br<> 
   */
  public final void ssUpdate(int pxID){
    if(cmIsDisabled){return;}
    if(cmTipMap.containsKey(pxID)){
      EcTip lpTip=cmTipMap.get(pxID);
      cmX=pbOwner.mouseX<(pbOwner.width/2)?
        pbOwner.mouseX:pbOwner.mouseX-lpTip.cmTipW;
      cmY=pbOwner.mouseY<(pbOwner.height/2)?
        pbOwner.mouseY:pbOwner.mouseY-lpTip.cmTipH;
      pbOwner.fill(cmPaneColor);
      pbOwner.rect(cmX,cmY,lpTip.cmTipW,lpTip.cmTipH);
      pbOwner.fill(cmTextColor);
      pbOwner.text(lpTip.cmTip,cmX+C_TEXT_ADJ_X,cmY+C_TEXT_ADJ_Y);
    }//..?
  }//+++
  
  /**
   * not supposed to be used outside from a coordinator. <br>
   * that coordinator provide a public method for this. <br>
   * @param pxID #
   * @param pxTip #
   */
  final void ccPut(int pxID, String pxTip)
    {cmTipMap.put(pxID, new EcTip(pxTip));}//+++
  
  //===
  
  /**
   * 
   * @param pxStatus #
   */
  public final void ccSetIsEnabled(boolean pxStatus)
    {cmIsDisabled=!pxStatus;}//+++
  
  /**
   * flip version
   */
  public final void ccSetIsEnabled()
    {cmIsDisabled=!cmIsDisabled;}//+++
  
  /**
   * 
   * @param pxColor #
   */
  public final void ccSetTextColor(int pxColor)
    {cmTextColor=pxColor;}//+++
  
  /**
   * 
   * @param pxColor #
   */
  public final void ccSetPaneColor(int pxColor)
    {cmPaneColor=pxColor;}//+++
  
  /**
   * 
   * @param pxText #
   * @param pxPane #
   */
  public final void ccSetupColor(int pxText, int pxPane){
    cmTextColor=pxText;
    cmPaneColor=pxPane;
  }//+++
  
}//***eof
