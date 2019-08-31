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

package kosui.ppplocalui;

import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.TOP;

/**
 * this thing used to holds every UI element class
 * as an inner static class.<br>
 * now he just produce em. so, don't just new those elements.<br>
 */
public final class EcConst {
  
  /**
   * serves as a dummy id.<br>
   * might be confusing with zero and this is not supposed to be zero.<br>
   */
  public static final int C_ID_IGNORE = 0xFFFF;
  
  /**
   * auto sizing using this as a measurement.<br>
   */
  public static final int 
    //-- pix
    C_DEFAULT_TEXT_WIDTH      =  8,
    C_DEFAULT_TEXT_HEIGHT     = 16,
    C_DEFAULT_AUTOSIZE_MARGIN =  4,
    C_DEFAULT_AUTOSIZE_HEIGHT = 20
  ;//...
    
  /**
   * <pre>
   * pre configured colors:
   *   lit &gt; %non-fix% &gt; dim &gt; dark
   *   [red,orange,yellow,green,water,blue,purple,gray]
   * may not all well defined.
   * may changes on purpose.
   * </pre>
   */
  public static final int
    //-- color
    C_WHITE = 0xFFEEEEEE,
    //--
    C_LIT_RED      = 0xFFEE6666,
    C_LIT_ORANGE   = 0xFFEE9966,
    C_LIT_YELLOW   = 0xFFEEEE66,
    C_LIT_GREEN    = 0xFF66EE66,
    C_LIT_WATER    = 0xFF66EEEE,
    C_LIT_BLUE     = 0xFF6699EE,
    C_LIT_PURPLE   = 0xFFEE66EE,
    C_LIT_GRAY     = 0xFFCCCCCC,
    //--
    C_RED      = 0xFFEE3333,
    C_ORANGE   = 0xFFEE6633,
    C_YELLOW   = 0xFFEEEE33,
    C_GREEN    = 0xFF33EE33,
    C_WATER    = 0xFF33EEEE,
    C_BLUE     = 0xFF3366EE,
    C_PURPLE   = 0xFFEE33EE,
    C_GRAY     = 0xFF999999,
    //--
    C_DIM_RED        = 0xFF991111,
    C_DIM_ORANGE     = 0xFF993311,
    C_DIM_YELLOW     = 0xFF999911,
    C_DIM_GREEN      = 0xFF119911,
    C_DIM_WATER      = 0xFF119999,
    C_DIM_BLUE       = 0xFF116699,
    C_DIM_PURPLE     = 0xFF991199,
    C_DIM_GRAY       = 0xFF666666,
    //--
    C_DARK_RED        = 0xFF660000,
    C_DARK_ORANGE     = 0xFF663300,
    C_DARK_YELLOW     = 0xFF666600,
    C_DARK_GREEN      = 0xFF006600,
    C_DARK_WATER      = 0xFF006666,
    C_DARK_BLUE       = 0xFF003366,
    C_DARK_PURPLE     = 0xFF660066,
    C_DARK_GRAY       = 0xFF333333,
    //--
    C_BLACK = 0xFF111111
  ;//...
  
  private EcConst(){}//..!
  
  /**
   * <b>NO NULL CHECK!!</b><br>
   * <pre>
   * supposedly should get called from PApplet.setup()
   *   before constructing any UIElement but after size(), 
   *   for there is some basic mode setting this library intended prefers.
   * for some reason this does no calls noSmooth().
   * </pre>
   * @param pxOwner : your sketch
   */
  public static final void ccSetupSketch(PApplet pxOwner){
    pxOwner.frameRate(16);
    pxOwner.noStroke();
    pxOwner.textAlign(LEFT,TOP);
    pxOwner.ellipseMode(CENTER);
  }//+++
  
  //=== utility
  
  /**
   * only bit wise calculation involved
   * @param pxSource in processing color format
   * @return reversed
   */
  static public int ccReverseColor(int pxSource){
    int lpAlpha = pxSource&0xFF000000;
    int lpRed   =(pxSource&0x00FF0000)>>16;
    int lpGreen =(pxSource&0x0000FF00)>>8;
    int lpBlue  = pxSource&0x000000FF;
    lpRed  =0xFF-lpRed;
    lpGreen=0xFF-lpGreen;
    lpBlue =0xFF-lpBlue;
    int lpRes=lpBlue|(lpGreen<<8)|(lpRed<<16);
    return lpRes|=lpAlpha;
  }//+++
  
  /**
   * constrain() from PApplet is involved.
   * @param pxSource in processing color format
   * @param pxLight plus value to get brighter while minus is darker
   * @return #
   */
  static public int ccAdjustColor(int pxSource, int pxLight){
    int lpMasked= pxLight &0xFF;
    int lpAlpha = pxSource&0xFF000000;
    int lpRed   =(pxSource&0x00FF0000)>>16;
    int lpGreen =(pxSource&0x0000FF00)>>8;
    int lpBlue  = pxSource&0x000000FF;
    lpRed=PApplet.constrain(lpRed+lpMasked, 1, 254);
    lpGreen=PApplet.constrain(lpGreen+lpMasked, 1, 254);
    lpBlue=PApplet.constrain(lpBlue+lpMasked, 1, 254);
    int lpRes=lpBlue|(lpGreen<<8)|(lpRed<<16);
    return lpRes|=lpAlpha;
  }//+++
  
  /**
   * only bit wise calculation involved
   * @param pxSource in processing color format
   * @param pxAlpha will be masked to 255
   * @return #
   */
  static public int ccSetAlpha(int pxSource, int pxAlpha){
    return (pxSource&0x00FFFFFF)|((pxAlpha&0xFF)<<24);
  }//+++
    
  //== entry
  
  /**
   * for some arbitrary reason, the utility class of the local UI package
   * is the main class.<br>
   * @param args you know what it is.
   */
  public static void main(String[] args) {
    System.out.println("Key Optional System User Interface");
    System.out.println("v19b01");
    System.err.println("kosui.EcFactory.main()::NOT_FOR_PUBLIC_USE!!");
  }//+++

}//***eof
