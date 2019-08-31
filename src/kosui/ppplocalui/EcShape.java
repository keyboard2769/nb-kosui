/*
 * Copyright (C) 2018 Key Parker from K.I.C
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

/**
 * a shape don't react at user, but it is a component. <br>
 * and it always lies under elements. <br>
 */
public class EcShape extends EcRect implements EiUpdatable{
  
  /**
   * the fill of the shape
   */
  protected int cmBaseColor=EcFactory.C_LIT_GRAY;
  
  //==
  
  /**
   * 
   * @param pxColor the fill of the shape
   */
  public final void ccSetBaseColor(int pxColor)
    {cmBaseColor=pxColor;}//+++
  
  //===
  
  /**
   * should never be called directly from draw()
   * even it might works
   */
  @Override public void ccUpdate(){
    drawRect(cmBaseColor);
  }//+++

}//***eof