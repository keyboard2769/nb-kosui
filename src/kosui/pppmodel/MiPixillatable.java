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

package kosui.pppmodel;

/**
 * implement vector drawing API for a bitmap would be tissy, 
 * but this way is easy.<br>
 * actually just a filter.<br>
 */
public interface MiPixillatable {
  
  /**
   * <pre>
   * a pixillatable is supposedly to get passed to a image
   *   whitch implemented ccFillPixel() with a loop iterates
   *   its entire pixel array.
   * this filter might get invoked inside that loop.
   * </pre>
   * @param pxX pix
   * @param pxY pix
   * @return filter for filling a bit mapped image
   */
  public boolean ccPixillate(int pxX, int pxY);
  
}//***eof
