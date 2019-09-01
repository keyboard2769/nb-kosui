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
 * a case by case implement base on integer and boolean array. <br>
 * local demo use only. <br>
 */
public class ZcSimplifiedMemory implements ZiMemory {

  private final int[] cmWordArray;

  private final boolean[][] cmBitArray;

  /**
   * the default size is 256 and is immutable. 
   */
  public ZcSimplifiedMemory() {
    cmWordArray = new int[256];
    cmBitArray = new boolean[256][16];
  }//+++
  
  //=== interface 
  
  /**
   * {@inheritDoc }
   */
  @Override public int ccReadWord(int pxAddr){
    return cmWordArray[pxAddr & 0xFF];
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public boolean ccReadBit(int pxAddr, int pxBit){
    return cmBitArray[pxAddr & 0xFF][pxBit & 0xF];
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccWriteWord(int pxAddr, int pxValue){
    cmWordArray[pxAddr & 0xFF] = pxValue;
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccWriteBit(int pxAddr, int pxBit, boolean pxValue){
    cmBitArray[pxAddr & 0xFF][pxBit & 0xF] = pxValue;
  }//+++

}//***eof
