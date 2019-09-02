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

import kosui.pppmodel.MiPixillatable;
import kosui.pppmodel.MiPixillated;
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
  
  private final PImage cmImage;
  
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
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate(){
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
  
}//***eof
