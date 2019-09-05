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
 * actually this was named as an abstract image base class.<br>
 * thus image implementation varies library to library, then i needed a verb, 
 * then i got this on my mac's built-in dictionary.<br>
 */
public interface MiPixillated{
  
  /**
   * <pre>
   * any image implement this interface is suppsed to walk through
   *   its entire pixel array.
   * although it is not that hard to implement,
   *   you can image that would be super time consuming.
   * just not suitable for hand-craft a huge background image.
   * </pre>
   * @param pxColor color to fill under the filter
   * @param pxFilter do not pass null
   */
  public void ccFillPixel(int pxColor, MiPixillatable pxFilter);
  
  /**
   * any image implement this interface is supposed to check the boundary.<br>
   * @param pxX pix
   * @param pxY pix 
   * @param pxColor supposedly ARGB
   */
  public void ccSetPixel(int pxX, int pxY, int pxColor);
  
  /**
   * any image implement this interface is supposed to check the boundary.<br>
   * @param pxX pix
   * @param pxY pix 
   * @return supposedly ARGB
   */
  public int ccGetPixel(int pxX, int pxY);
  
}//***eof
