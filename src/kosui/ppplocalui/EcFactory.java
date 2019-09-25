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

import java.awt.Font;
import java.io.File;
import java.lang.reflect.Constructor;
import kosui.ppplogic.ZcRangedModel;
import kosui.pppmodel.McConst;
import kosui.ppputil.VcConst;
import processing.core.PFont;
import processing.core.PGraphics;

/**
 * a factory is not a all mighty all knowing something.<br>
 * it only constructs and setups products.<br>
 */
public final class EcFactory {
  
  private EcFactory(){}//+++ 
  
  //=== ** 
  
  //=== ** button
  
  //[PLAN]:: ..MomentarySW()
  //[PLAN]:: ..AlternateSW()
  
  //=== ** lamp
  
  /**
   * @param pxKey will get passed to constructor directly
   * @param pxID will get passed to constructor directly
   * @param pxLetter must be alphabetic or digit
   * @param pxSideSize make it a square
   * @return #
   */
  static public final
  EcElement ccCreateSingleLetterPL(
    String pxKey, int pxID,
    char pxLetter, int pxSideSize
  ){
    EcElement lpRes=new EcElement(pxKey, pxID);
    ccSetupSingleLetterPL(lpRes, pxLetter, pxSideSize);
    return lpRes;
  }//+++
  
  /**
   * @param pxTarget #
   * @param pxLetter must be alphabetic or digit
   * @param pxSideSize make it a square
   */
  static public final
  void ccSetupSingleLetterPL(
    EcElement pxTarget, char pxLetter, int pxSideSize
  ){
    char lpFixed = pxLetter;
    if(!Character.isAlphabetic(pxLetter)
     ||!Character.isDigit(lpFixed)){
      lpFixed='?';
    }//..?
    pxTarget.ccSetText(String.valueOf(lpFixed));
    pxTarget.ccSetNameAlign('x');
    pxTarget.ccSetTextAlign('c');
    pxTarget.ccSetSize(pxSideSize,pxSideSize);
  }//+++
  
  //[PLAN]:: ..ActuatorPL()
  //[PLAN]:: ..IndicatorPL()
  
  //=== ** box
  
  //[PLAN]:: ..InputTB()
  //[PLAN]:: ..OutputCB()
  
  //=== ** gauge
  
  /**
   * <pre>
   * for general levelor use.
   * the content color is hard coded yellow. (red if engaged)
   * the background color is hard coded black.
   * the border color is hard coded light gray.
   * </pre>
   * @param pxTarget #
   * @param pxHasStroke #
   * @param pxIsVertical #
   * @param pxW pix
   * @param pxH pix
   */
  static public
  void ccSetupContentLV(
    EcGauge pxTarget,
    boolean pxHasStroke, boolean pxIsVertical, int pxW, int pxH
  ){
    pxTarget.ccSetSize(pxW, pxH);
    pxTarget.ccSetColor(0xFFEE3333,0xFFEEEE33);
    pxTarget.ccSetIsVertical(pxIsVertical);
    pxTarget.ccSetHasStroke(pxHasStroke);
    pxTarget.ccSetGaugeColor(0xFF111111, 0xFFCCCCCC);
    pxTarget.ccSetPercentage(32);
  }//+++
    
  /**
   * <pre>
   * for general adjustor use.
   * the content color is referenced light gray. (red if engaged)
   * the background color is referenced dim gray.
   * the border color is hard referenced light gray.
   * </pre>
   * @param pxTarget #
   * @param pxHasStroke #
   * @param pxIsVertical #
   * @param pxW pix
   * @param pxH pix
   */
  static public
  void ccSetupConfigSlider(
    EcSlider pxTarget,
    boolean pxHasStroke, boolean pxIsVertical, int pxW, int pxH
  ){
    pxTarget.ccSetSize(pxW, pxH);
    pxTarget.ccSetColor(EcConst.C_RED,EcConst.C_LIT_GRAY);
    pxTarget.ccSetIsVertical(pxIsVertical);
    pxTarget.ccSetHasStroke(pxHasStroke);
    pxTarget.ccSetGaugeColor(EcConst.C_DIM_GRAY, EcConst.C_LIT_GRAY);
    pxTarget.ccSetPercentage(63);
  }//+++
  
  //=== processing essential
  
  //=== processing essential ** font
  
  /**
   * bypassing AWT font construction for you.<br>
   * loading formatting is hard coded to Font.TRUETYPE_FONT,
   *   and this is supposed to work with a TTF file,
   *   but i decide still to accept TTC extension.
   * and i am not sure how this will perform with a TTC file.<br>
   * @param pxFontFile will get verified via McConst.ccVerifyFileForLoading
   * @return could be null
   */
  public static final
  PFont ccLoadTrueTypeFont(File pxFontFile){
    
    //-- check in
    if(!McConst.ccVerifyFileForLoading(pxFontFile)){
      System.err.println("kosui.ppplocalui.EcFactory.ccLoadTrueTypeFont()"
        + "failed to verify file for loading");
      return null;
    }//..?
    String lpName=pxFontFile.getName();
    boolean lpIsExtensionOK=false;
    lpIsExtensionOK|=lpName.endsWith(".TTF");
    lpIsExtensionOK|=lpName.endsWith(".ttf");
    lpIsExtensionOK|=lpName.endsWith(".TTC");
    lpIsExtensionOK|=lpName.endsWith(".ttc");
    if(!lpIsExtensionOK){
      System.err.println("kosui.ppplocalui.EcFactory.ccLoadTrueTypeFont()"
        + "failed to verify file extension");
      return null;
    }//..?
    
    //-- to awt font
    Font lpFont=null;
    try {
      lpFont = Font.createFont(Font.TRUETYPE_FONT, pxFontFile);
    } catch (Exception e) {
      System.err.println("kosui.ppplocalui.ccLoadTrueTypeFont()::"
       + e.getMessage());
    }//..?
    
    //-- to processing font
    PFont lpPFont = null;
    if(lpFont!=null){
      lpPFont=new PFont(lpFont, false);
    }//..?
    
    return lpPFont;
    
  }//+++
  
  //=== processing essential ** graphics
  
  private static PGraphics ccMakePGraphics(String pxRendererClass){
    if(!VcConst.ccIsValidString(pxRendererClass)){return null;}
    PGraphics lpRes=null;
    try{
      Class<?> rendererClass=Thread.currentThread()
        .getContextClassLoader().loadClass(pxRendererClass);
      Constructor<?> constructor=rendererClass.getConstructor(new Class[0]);
      lpRes=(PGraphics)constructor.newInstance(new Object[0]);
    }catch(Exception e){
      System.err.println(
        "kosui.ppplocalui.EcFactory.ccMakePGraphics():["
         + pxRendererClass+"]:"
         + e.getMessage()
      );
    }//..?
    return lpRes;
  }//+++
  
  /**
   * <pre>
   * snatching the PApplet::makeGraphics() for its none-static nature.
   * the key of renderer is hard coded to "processing.core.PGraphicsJava2D".
   * the primary field is hard coded to null.
   * </pre>
   * @param pxW will get masked to 0-65535
   * @param pxH will get masked to 0-65535
   * @return could be null if any exception occurred
   */
  public static final
  PGraphics ccCreatePGraphics(int pxW, int pxH){
    PGraphics lpRes=ccMakePGraphics("processing.core.PGraphicsJava2D");
    if(lpRes==null){return null;}
    lpRes.setSize(pxW&0xFFFF, pxH&0xFFFF);
    lpRes.setPrimary(false);
    return lpRes;
  }//+++
  
  /**
   * <pre>
   * snatching the PApplet::makeGraphics() for its none-static nature.
   * the key of renderer is hard coded to "processing.pdf.PGraphicsPDF".
   * you have to import the processing pdf library separately.
   * the primary field is hard coded to null.
   * NOTE:CONSTRUCTING PGraphicsPDF THIS WAY MAY CAUSE IT HAS A NULL PARENT.
   * this leads to a disaster inablity for it to handle text.
   * AND THERE WILL BE NO CURE.
   * </pre>
   * @param pxW will get limited to 400-65535
   * @param pxH will get limited to 400-65535
   * @param pxFile supposedly a PDF file
   * @return could be null
   */
  public static final
  PGraphics ccCreatePGraphics(int pxW, int pxH, File pxFile){
    PGraphics lpRes=ccMakePGraphics("processing.pdf.PGraphicsPDF");
    if(lpRes==null){return null;}
    if(pxFile==null){return null;}
    if(!pxFile.isAbsolute()){return null;}
    lpRes.setSize(
      ZcRangedModel.ccLimitInclude(pxW, 400, 65535),
      ZcRangedModel.ccLimitInclude(pxH, 400, 65535)
    );
    lpRes.setPath(pxFile.getAbsolutePath());
    lpRes.setPrimary(false);
    return lpRes;
  }//+++
  
}//***eof
