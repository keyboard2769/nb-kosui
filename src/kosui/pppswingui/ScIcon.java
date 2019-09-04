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
package kosui.pppswingui;

import java.awt.image.BufferedImage;
import kosui.pppmodel.MiPixillatable;
import kosui.pppmodel.MiPixillated;

/**
 * actually i tried the windres.exe of MinGW before.<br>
 * i never made it.<br>
 */
public final class ScIcon extends BufferedImage implements MiPixillated{
  
  /**
   * 32x32 by default.<br>
   */
  public ScIcon() {
    super(32, 32, BufferedImage.TYPE_INT_RGB);
    for(int x=0;x<32;x++){for(int y=0;y<32;y++){
      setRGB(x, y, y);
    }}
  }//..!
  
  //===

  /**
   * {@inheritDoc }
   */
  @Override public void ccFillPixel(int pxColor, MiPixillatable pxFilter) {
    if(pxFilter==null){return;}
    for(int x=0,w=getWidth();x<w;x++){for(int y=0,h=getHeight();y<h;y++){
      if(pxFilter.ccPixillate(x, y)){ccSetPixel(x, y, pxColor);}
    }}//..~
    
  }//+++

  /**
   * @param pxX pix of offset
   * @param pxY pix of offset 
   * @return BufferedImage::getRGB()
   */
  @Override public int ccGetPixel(int pxX, int pxY) {
    return getRGB(pxY, pxY);
  }//+++

  /**
   * @param pxX pix of offset
   * @param pxY pix of offset
   * @param pxColor RGB
   */
  @Override public void ccSetPixel(int pxX, int pxY, int pxColor) {
    setRGB(pxX, pxY, pxColor);
  }//+++
  
}//***eof
