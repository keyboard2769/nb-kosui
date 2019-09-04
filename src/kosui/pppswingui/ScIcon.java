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
 * .<>
 * .<>
 */
public class ScIcon extends BufferedImage implements MiPixillated{
  
  

  public ScIcon() {
    super(32, 32, BufferedImage.TYPE_INT_RGB);
    for(int x=0;x<32;x++){for(int y=0;y<32;y++){
      setRGB(x, y, y);
    }}
  }
  
  
  

  @Override
  public void ccFillPixel(int pxColor, MiPixillatable pxFilter) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int ccGetPixel(int pxX, int pxY) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void ccSetPixel(int pxX, int pxY, int pxColor) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  
  
  
}//***eof
