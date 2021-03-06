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

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import kosui.pppmodel.MiPixillatable;
import kosui.pppmodel.MiPixillated;
import kosui.ppputil.VcConst;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * <pre>
 * for a long time i hated bitmap image cause it force me to deal with
 *   file io stuff before i actually done a design.
 * but we still might need to save some loop time, 
 *   well a programmatically drawable image as background would be handy.
 * </pre>
 */
public class EcImage extends EcShape implements MiPixillated{
  
  /**
   * ##
   */
  protected final PImage cmImage;
  
  /**
   * also constructs a PImage.<br>
   * @param pxW pix
   * @param pxH pix
   */
  public EcImage(int pxW, int pxH){
    super(pxW,pxH);
    cmImage=new PImage(pxW, pxH, PApplet.ARGB);
    for (int i = 0; i < cmImage.pixels.length; i++) {
      cmImage.pixels[i] = cmBaseColor; 
    }cmImage.updatePixels();
  }//+++
  
  /**
   * also constructs a PImage.<br>
   * default size is `8 x 8`.<br>
   */
  public EcImage(){
    this(8, 8);
  }//+++
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
    if(!ccIsVisible()){return;}
    pbOwner.image(cmImage, cmX, cmY);
  }//+++
  
  //===
  
  /**
   * alias to PImage.mask() and blend().<br>
   * @param pxImage do not pass null
   */
  public final void ccMerge(EcImage pxImage){
    if(pxImage==null){return;}
    cmImage.mask(pxImage.cmImage);
  }//+++
  
  /**
   * alias to PImage.mask() and blend().<br>
   * @param pxImage do not pass null
   */
  public final void ccMerge(PImage pxImage){
    if(pxImage==null){return;}
    cmImage.mask(pxImage);
  }//+++
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccFillPixel(int pxColor, MiPixillatable pxFilter){
    if(pxFilter==null){return;}
    for(int x=0;x<cmImage.width;x++){for(int y=0;y<cmImage.height;y++){
      if(pxFilter.ccPixillate(x, y)){ccSetPixel(x, y, pxColor);}
    }}//..~
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccSetPixel(int pxX, int pxY, int pxColor){
    cmImage.set(pxX, pxY, pxColor);
  } //+++
  
  /**
   * {@inheritDoc }
   */
  @Override public int ccGetPixel(int pxX, int pxY){
    return cmImage.get(pxX, pxY);
  }//+++
  
  //=== util
  
  /**
   * supposedly should get chained after a byte array
   *   decoded from a base64 string
   *   in the format of png.<br>
   * like:<br>
   * <pre>
      String encoded = "...==";
      byte[] decoded = Base64.getDecoder().decode(encoded);
      image = EcImage.ccCreatePImage(lpDecoded, this);
      if(image!=null){image.updatePixels();}
   * </pre>
   * @param pxOwner for awt toolkit referencing
   * @param pxPNGData data
   * @return null if anything went wrong
   */
  public static final
  PImage ccCreateImage(PApplet pxOwner, byte[] pxPNGData){
    
    //-- pre
    final String lpAhead = ".%test210109002 $ ";
    final String lpAbort = lpAhead + "abort";
    if(pxPNGData == null){VcConst.ccErrln(lpAbort, "ab101");return null;}
    if(pxPNGData.length <= 1){VcConst.ccErrln(lpAbort, "ab102");return null;}
    if(pxOwner == null){VcConst.ccErrln(lpAbort, "ab103");return null;}
    
    //-- load
    Image lpAwtImage = Toolkit.getDefaultToolkit().createImage(pxPNGData);
    MediaTracker lpAwtTracker = new MediaTracker(pxOwner);
    lpAwtTracker.addImage(lpAwtImage, 0);
    try {
      lpAwtTracker.waitForAll();
    } catch (InterruptedException ie) {
      VcConst.ccErrln(lpAbort, ie.getMessage());
      lpAwtTracker = null;
    }//..?//..?//..?//..?
    if(lpAwtTracker == null){
      VcConst.ccErrln(lpAbort, "ab201");
      return null;
    }//..?
    
    //-- create
    PImage lpProcessingImage = new PImage(lpAwtImage);
    lpProcessingImage.parent = pxOwner;
    if (lpProcessingImage.width == -1) {
      VcConst.ccErrln(lpAbort, "ab301");
      return null;
    }//..?
    
    //-- ** check alpha
    if (lpProcessingImage.pixels == null) {
      VcConst.ccErrln(lpAbort, "ab401");
      return null;
    }//..?
    for (int i = 0; i < lpProcessingImage.pixels.length; i++) {
      if ((lpProcessingImage.pixels[i] & 0xff000000) != 0xff000000) {
        lpProcessingImage.format = PApplet.ARGB;
        break;
      }//..?
    }//..~
    
    //-- post
    return lpProcessingImage;
    
  }//+++
  
}//***eof
