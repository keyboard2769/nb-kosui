/*
 * Copyright (C) 2019 Key Parker from K.I.C
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
import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

/**
 * a text don't even know what it is meant for.<br>
 * he is part of the back ground.<br>
 */
public class EcText extends EcShape{
  
  /**
   * ##
   */
  protected String cmText="<?>";
  
  /**
   * ##
   */
  protected char cmAlignment = 's';
  
  /**
   * ##
   */
  protected int cmTextColor=EcConst.C_DARK_GRAY;
  
  /**
   * text color is dark gray by default.<br>
   */
  public EcText(){
    super();
  }//+++
  
  /**
   * text color is dark gray by default.<br>
   * still doing auto sizing for external usage.<br>
   * @param pxText will gell null out to empty
   */
  public EcText(String pxText){
    super();
    ccSetText(pxText);
    ccSetSize(cmText);
  }//+++ 
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
    
    //-- check in
    if(!ccIsVisible()){return;}
    if(!VcConst.ccIsValidString(cmText)){return;}
    
    //-- plate
    if(cmBaseColor!=0){
      drawRect(cmBaseColor);
    }//..?
    
    //-- content
    pbOwner.pushStyle();
    {
      pbOwner.fill(cmTextColor);
      switch(cmAlignment){
          
          case 'q':case 'Q':
            pbOwner.textAlign(PApplet.LEFT, PApplet.TOP);
            pbOwner.text(cmText, ccGetX(), ccGetY());
          break;
        
          case 'w':case 'W':
            pbOwner.textAlign(PApplet.CENTER, PApplet.TOP);
            pbOwner.text(cmText, ccCenterX(), ccGetY());
          break;
          
          case 'e':case 'E':
            pbOwner.textAlign(PApplet.RIGHT, PApplet.TOP);
            pbOwner.text(cmText, ccEndX(), ccGetY());
          break;
          
          case 'd':case 'D':
            pbOwner.textAlign(PApplet.RIGHT, PApplet.CENTER);
            pbOwner.text(cmText, ccEndX(), ccCenterY());
          break;
          
          case 'c':case 'C':
            pbOwner.textAlign(PApplet.RIGHT, PApplet.BOTTOM);
            pbOwner.text(cmText, ccEndX(), ccEndY());
          break;
          
          case 'x':case 'X':
            pbOwner.textAlign(PApplet.CENTER, PApplet.BOTTOM);
            pbOwner.text(cmText, ccCenterX(), ccEndY());
          break;
          
          case 'z':case 'Z':
            pbOwner.textAlign(PApplet.LEFT, PApplet.BOTTOM);
            pbOwner.text(cmText, ccGetX(), ccEndY());
          break;
          
          case 'a':case 'A':
            pbOwner.textAlign(PApplet.LEFT, PApplet.CENTER);
            pbOwner.text(cmText, ccGetX(), ccCenterY());
          break;
          
          case 's':case 'S':
            pbOwner.textAlign(PApplet.CENTER, PApplet.CENTER);
            pbOwner.text(cmText, ccCenterX(), ccCenterY());
          break;
          
        default:break;
      }//..?
    }
    
    pbOwner.popStyle();
    
  }//+++
  
  /**
   * @param pxText will gell null out to empty
   */
  public final void ccSetText(String pxText){
    cmText=VcStringUtility.ccNulloutString(pxText);
  }//+++
  
  /**
   * [q] : top    - left   a.k.a northwest <br>
   * [w] : top    - center a.k.a north <br>
   * [e] : top    - right  a.k.a northeast <br>
   * [d] : center - right  a.k.a east <br>
   * [c] : bottom - right  a.k.a southeast<br>
   * [x] : bottom - center a.k.a south <br>
   * [z] : bottom - left   a.k.a southwest<br>
   * [a] : center - left   a.k.a south <br>
   * [s] : center - center a.k.a center<br>
   * @param pxAlignment do not get across a-z
   */
  public final void ccSetAlignment(char pxAlignment){
    if((pxAlignment >= 'a' )&&(pxAlignment<='z')){
      cmAlignment=pxAlignment;
    }//..?
  }//+++
  
  /**
   * will get converted to that char version.
   * @param pxHorizontal use the constant value from PApplet
   * @param pxVertical use the constant value from PApplet
   */
  public final void ccSetAlignment(int pxHorizontal, int pxVertical){
    
    //-- revert
    int lpCombined = pxHorizontal*1000+pxVertical;
    switch(lpCombined){
      //--
      case 37101:cmAlignment='q';break;
      case 3101:cmAlignment='w';break;
      case 39101:cmAlignment='e';break;
      //--
      case 39003:cmAlignment='d';break;
      case 39102:cmAlignment='c';break;
      //--
      case 3102:cmAlignment='x';break;
      case 37102:cmAlignment='z';break;
      //--
      case 37003:cmAlignment='a';break;
      case 3003:cmAlignment='s';break;
      default:break;
    }//..?
    
    //-- test
    VcConst.ccLogln(
      ".ccSetAlignment$",
      String.format(
        "[%d,%d] -> %d -> %c",
        pxHorizontal, pxVertical, lpCombined, cmAlignment
      )
    );
    
  }//+++
  
  /**
   * @param pxColor ARGB
   */
  public final void ccSetTextColor(int pxColor){
    cmTextColor=pxColor;
  }//+++  
  
  /**
   * <pre>
   * a compromising alternative methodology for passive updating.
   * made only for getting attached to an element,
   *   especially value box and text box.
   * so do not get confused with the one with the rectangle class.
   * mode :
   *  - [a] : above
   *  - [b] : below
   *  - [l] : left
   *  - [r] : right
   *  - [x] : does nothing
   * </pre>
   * @param pxTarget ##
   * @param pxMode_ablrx ##
   */
  public final void ccSetLocation(EcElement pxTarget,char pxMode_ablrx){
    if(pxTarget==null){return;}  
    switch(pxMode_ablrx){
      
      case 'a':
        ccSetLocation(pxTarget.ccCenterX(),
          pxTarget.ccGetY()-ccGetH()/2
        );
      break;
      
      case 'b':
        ccSetLocation(pxTarget.ccCenterX(),
          pxTarget.ccEndY()+ccGetH()/2
        );
      break;
      
      case 'l':
        ccSetLocation(pxTarget.ccGetX()-ccGetW()/2,
          pxTarget.ccCenterY()
        );
      break;
      
      case 'r':
        ccSetLocation(pxTarget.ccEndX()+ccGetW()/2,
          pxTarget.ccCenterY()
        );
      break;
      
      default:break;
      
    }//..?
  }//+++
  
}//***eof
