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

package kosui.ppplogic;

/**
 * a boolean has the state of true or false, 
 * a bit has the state of on or off.<br>
 * you think they are all the same? 
 * well, that might be true, but that can not be on.<br>
 */
public class ZcBit implements ZiBitty{
  
  /**
   * this is the only state, this is the one.
   */
  protected boolean cmBit=false;
  
  /**
   * @param pxBit could be anything
   */
  public void ccSetBit(boolean pxBit){
    cmBit=pxBit;
  }//++<
  
  /**
   * {@inheritDoc }
   */
  @Override public boolean ccGetBit() {
    return cmBit;
  }//++>
  
}//***eof
