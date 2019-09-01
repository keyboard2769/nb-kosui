/*
 * Copyright (C) 2018 Key Parker from K.I.C.
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
 * in my logic controller a whole bunch of 16bit register, AKA "word". <br>
 * for some reason "word" is numbered in decimal, we don't keep it here. <br>
 */
public interface ZiMemory {
  
  /**
   * ##
   * @return word size
   */
  public int ccGetSize();

  /**
   * even though it is a Integer a 16 bit value will be returned.<br>
   * @param pxAddr word address
   * @return value
   */
  public int ccReadWord(int pxAddr);

  /**
   * ##
   * @param pxAddr word address
   * @param pxBit bit numbering
   * @return value
   */
  public boolean ccReadBit(int pxAddr, int pxBit);

  /**
   * even though it is a Integer it should get masked to 16 bit.<br>
   * @param pxAddr word address
   * @param pxValue to be set
   */
  public void ccWriteWord(int pxAddr, int pxValue);

  /**
   * ##
   * @param pxAddr word address
   * @param pxBit bit numbering
   * @param pxValue to be set
   */
  public void ccWriteBit(int pxAddr, int pxBit, boolean pxValue);

}//***eof
