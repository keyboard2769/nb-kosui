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
public final class EcFactory {
  
  /**
   * pre configured colors:<br>
   *   lit &gt; %non-fix% &gt; dim &gt; dark<br>
   *   [red,orange,yellow,green,water,blue,purple,gray]<br>
   * may not all well defined.
   * may changes on purpose.
   */
  public static final int
    //-- id
    C_ID_IGNORE = 0xFFFF,
    //-- pix
    C_DEFAULT_TEXT_HEIGHT     = 16,
    C_DEFAULT_AUTOSIZE_MARGIN =  4,
    C_DEFAULT_AUTOSIZE_HEIGHT = 20,
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
  
  //===
  
  private EcFactory(){}//+++ 
  
  /**
   * should be called from setup() before
   * constructing any UIElement. <br>
   * also initiate some static element like axis and console,
   * and some basic mode setting this library prefers. <br>
   * for some reason this does no calls noSmooth(). <br>
   * like : EcFactory.ccInit(this);
   * @param pxOwner : your sketch
   */
  public static final void ccInit(PApplet pxOwner){
    if(pxOwner==null){
      System.err.println(".EcFactory.ccInit()::null value passed!!");
      return;
    }
    EcPoint.ccInitOwner(pxOwner);
    VcTagger.ccGetInstance().ccInit(pxOwner, 8);
    VcAxis.ccGetInstance().ccInit(pxOwner,false);
    VcConsole.ccInit(pxOwner);
    VcStacker.ccInit(pxOwner);
    pxOwner.frameRate(16);
    pxOwner.noStroke();
    pxOwner.textAlign(LEFT,TOP);
    pxOwner.ellipseMode(CENTER);
  }//+++

  //=== pane
  
  /**
   * 
   * @param pxTitle #
   * @param pxX #
   * @param pxY #
   * @param pxW #
   * @param pxH #
   * @return #
   */
  public static final EcPane ccCreatePane
    (String pxTitle,int pxX, int pxY, int pxW, int pxH)
  { EcPane lpRes=new EcPane();
    lpRes.ccSetTitle(pxTitle);
    lpRes.ccSetLocation(pxX, pxY);
    lpRes.ccSetSize(pxW, pxH);
    return lpRes;
  }//+++
    
  /**
   * default location is at 8.8
   * @param pxTitle #
   * @param pxW #
   * @param pxH #
   * @return #
   */
  static public final EcPane ccCreatePane
    (String pxTitle, int pxW, int pxH)
  { EcPane lpRes=ccCreatePane(pxTitle, 8, 8, pxW, pxH);
    return lpRes;
  }//+++
  
  //=== button & lamp
    
  /**
   * 
   * @param pxText #
   * @return #
   */
  static public final EcElement ccCreateTextLamp(String pxText){
    EcElement lpRes = new EcElement();
    lpRes.ccSetupKey(pxText);
    lpRes.ccSetSize();
    return lpRes;
  }//+++
    
  /**
   * 
   * @param pxName #
   * @param pxW #
   * @param pxH #
   * @param pxID #
   * @return #
   */
  static public final EcButton ccCreateButton
    (String pxName, int pxW, int pxH, int pxID)
  { EcButton lpRes=new EcButton();
    lpRes.ccSetID(pxID);
    lpRes.ccSetupKey(pxName);
    lpRes.ccSetSize(pxW, pxH);
    lpRes.ccSetColor(0xFFEEEE33, 0xFF111111);
    lpRes.ccSetIsEnabled(true);
    return lpRes;
  }//+++
  
  /**
   * 
   * @param pxName #
   * @param pxID #
   * @return #
   */
  static public final EcButton ccCreateButton
    (String pxName,int pxID)
  { EcButton lpRes=ccCreateButton(pxName, 8, 8, pxID);
    lpRes.ccSetSize();
    return lpRes;
  }//+++
    
  /**
   * 
   * @param pxName #
   * @param pxW #
   * @param pxH #
   * @return #
   */
  static public EcLamp ccCreateLamp
    (String pxName, int pxW, int pxH)
  { EcLamp lpRes = new EcLamp();
    lpRes.ccSetupKey(pxName);
    lpRes.ccSetText(pxName.substring(0, 1));
    lpRes.ccSetSize(pxW, pxH);
    lpRes.ccSetNameAlign('r');
    return lpRes;
  }//+++
  
  /**
   * lamp text will be initiated with the first letter of name
   * @param pxName #
   * @return #
   */
  static public EcLamp ccCreateLamp
    (String pxName)
    {return ccCreateLamp(pxName, C_DEFAULT_TEXT_HEIGHT, C_DEFAULT_TEXT_HEIGHT);}//+++
  
  //=== text box
    
  /**
   * 
   * @param pxForm like : "000.0 KG "
   * @param pxW #
   * @param pxH #
   * @return #
   */
  static public EcTextBox ccCreateBox
    (String pxForm, int pxW, int pxH)
  { EcTextBox lpRes=new EcTextBox();
    lpRes.ccSetupKey(pxForm);
    lpRes.ccSetSize(pxW, pxH);
    return lpRes;
  }//+++
    
  /**
   * 
   * @param pxForm like : "000.0 KG "
   * @return #
   */
  static public EcTextBox ccCreateBox 
    (String pxForm)
  { EcTextBox lpRes=ccCreateBox(pxForm, 8, 8);
    lpRes.ccSetSize();
    return lpRes;
  }//+++
  
  /**
   * 
   * @param pxForm like : "000.0 KG "
   * @param pxName #
   * @param pxAlign 'a'/'b'/'l'/'r'/'x' : see EcElement.ccSetNameAlign();
   * @return #
   */
  static public EcTextBox ccCreateBox
    (String pxForm, String pxName, char pxAlign)
  { EcTextBox lpRes=ccCreateBox(pxForm);
    lpRes.ccSetName(pxName);
    lpRes.ccSetNameAlign(pxAlign);
    return lpRes;
  }//+++
  
  /**
   * color set and has id or not is the only difference
   * @param pxForm #
   * @param pxName #
   * @param pxAlign 'a'/'b'/'l'/'r'/'x' : see EcElement.ccSetNameAlign();
   * @param pxID #
   * @return #
   */
  static public EcTextBox ccCreateInputBox
    (String pxForm, String pxName, char pxAlign, int pxID)
  { EcTextBox lpRes=ccCreateBox(pxForm, pxName, pxAlign);
    lpRes.ccSetTextColor(C_DARK_GRAY);
    lpRes.ccSetColor(EcFactory.C_LIT_YELLOW, EcFactory.C_DIM_YELLOW);
    lpRes.ccSetID(pxID);
    return lpRes;  
  }//+++
  
  //== gauge & slider
    
  /**
   * ##
   * @param pxName #
   * @param pxHasStroke #
   * @param pxIsVertical #
   * @param pxW #
   * @param pxH #
   * @return #
   */
  static public EcGauge ccCreateGauge
    (String pxName, boolean pxHasStroke, boolean pxIsVertical, int pxW, int pxH)
  { EcGauge lpRes=new EcGauge();
    lpRes.ccSetupKey(pxName);
    lpRes.ccSetSize(pxW, pxH);
    lpRes.ccSetColor(0xFFEE3333,0xFFEEEE33);
    lpRes.ccSetIsVertical(pxIsVertical);
    lpRes.ccSetHasStroke(pxHasStroke);
    lpRes.ccSetGaugeColor(0xFF111111, 0xFFCCCCCC);
    lpRes.ccSetPercentage(32);
    return lpRes;
  }//+++
    
  /**
   * 
   * @param pxName #
   * @return #
   */
  static public EcGauge ccCreateGauge
    (String pxName)
  { EcGauge lpRes=ccCreateGauge(pxName,true,true,8,8);
    lpRes.ccSetSize();
    return lpRes;
  }//+++
    
  /**
   * ##
   * @param pxName #
   * @param pxNameAlign #
   * @param pxHasStroke #
   * @param pxIsVertical #
   * @param pxW #
   * @param pxH #
   * @return #
   */
  static public EcGauge ccCreateGauge
    (String pxName,char pxNameAlign,
     boolean pxHasStroke, boolean pxIsVertical, int pxW, int pxH)
  { EcGauge lpRes=ccCreateGauge(pxName, pxHasStroke, pxIsVertical, pxW, pxH);
    lpRes.ccSetNameAlign(pxNameAlign);
    return lpRes;
  }//+++
  
  /**
   * 
   * @param pxName #
   * @param pxHasStroke #
   * @param pxIsVertical #
   * @param pxW #
   * @param pxH #
   * @return #
   */
  static public EcSlider ccCreateSlider
    (String pxName, boolean pxHasStroke, boolean pxIsVertical, int pxW, int pxH)
  { EcSlider lpRes=new EcSlider();
    lpRes.ccSetupKey(pxName);
    lpRes.ccSetSize(pxW, pxH);
    lpRes.ccSetColor(C_RED,C_LIT_GRAY);
    lpRes.ccSetIsVertical(pxIsVertical);
    lpRes.ccSetHasStroke(pxHasStroke);
    lpRes.ccSetGaugeColor(C_DIM_GRAY, C_LIT_GRAY);
    lpRes.ccSetPercentage(63);
    return lpRes;
  }//+++
    
  //== colours
  
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
   * 
   * @param args you know what it is.
   */
  public static void main(String[] args) {
    System.err.println("kosui.EcFactory.main()::DONT_JUST_RUN!!");
  }//+++

}//***eof
