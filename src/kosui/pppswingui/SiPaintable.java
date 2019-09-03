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

import java.awt.Graphics;

/**
 * once upon the time, to me, JAVA2D is just a sloppy mode
 * of PApplet::size().<br> 
 * it really blowed up my mind when i actually made
 * a draw of rectangle on it.<br>
 */
public interface SiPaintable {
  
  /**
   * might get called inside any JComponent::paint().<br>
   * @param pxGraphic the "g" from JComponent::paint()
   */
  public void ccPaint(Graphics pxGraphic);
  
}//***eof
