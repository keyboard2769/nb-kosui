/*
 * Copyright (C) 2018 Key Parker
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
 * a triangle lamp points at a direction. <br>
 * sometime i think it is not a good idea to right a mark on it. <br>
 */
public class EcTriangleLamp extends EcElement{

  private char cmDirection='l';
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
  
    drawTriangleLamp(ccActColor());
    drawText(cmTextColor);
    drawName(cmNameColor);
    
  }//+++
  

  /**
   * internal use only.<br>
   * @param pxColor #
   */
  protected final void drawTriangleLamp(int pxColor){

    pbOwner.fill(pxColor);
    pbOwner.stroke(EcConst.C_LIT_GRAY);
    switch(cmDirection){

      case 'u':
        pbOwner.triangle(
          ccCenterX(), cmY,
          ccEndX(), ccEndY(),
          cmX, ccEndY()
        );
      break;

      case 'd':
        pbOwner.triangle(
          cmX, cmY,
          ccEndX(), cmY,
          ccCenterX(), ccEndY()
        );
      break;

      case 'l':
        pbOwner.triangle(
          cmX, ccCenterY(),
          ccEndX(), cmY,
          ccEndX(), ccEndY()
        );
      break;

      case 'r':
        pbOwner.triangle(
          cmX, cmY,
          ccEndX(), ccCenterY(),
          cmX, ccEndY()
        );
      break;

      default:break;
    }//..?
    
    pbOwner.noStroke();

  }//+++

  /**
   * <pre>
   * mode:
   *  - [u]:up
   *  - [d]:down
   *  - [l]:left
   *  - [r]:right
   *  - [x]:empty (or you can pass any letter)
   * </pre>
   * @param pxMode_udlr #
   */
  public final void ccSetDirection(char pxMode_udlr){
    cmDirection=pxMode_udlr;
  }//+++

}//***
