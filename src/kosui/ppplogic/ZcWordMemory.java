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

import java.util.Arrays;

/**
 * a more real stuff like implement base on integer array. <br>
 * could be transformed to byte array for sending via socket. <br>
 */
public class ZcWordMemory implements ZiMemory {

  private final int[] cmData;

  private final int cmMask;

  //===
  
  /**
   * creates a integer array.
   * @param pxWordSize will be fixed to the number of power of two
   */
  public ZcWordMemory(int pxWordSize) {
    int lpMasked = pxWordSize & 0xFF;
    int lpTester = 0x80;
    while (lpTester != 1) {
      if ((lpTester & lpMasked) != 0) {
        break;
      }
      lpTester >>= 1;
    }
    int lpFixed = (lpTester == lpMasked)
      ? lpTester : (lpTester * 2);
    cmData = new int[lpFixed];
    cmMask = lpFixed - 1;
  }//+++

  //=== teller
  
  /**
   * 
   * @return array length
   */
  public int ccGetSize() {
    return cmData.length;
  }//+++

  //=== util
  
  //=== util ** byte array
  
  /**
   * 
   * @param pxData will be trimmed if over sized.
   */
  public final void ccTakeByteArray(byte[] pxData) {
    int lpLength = (pxData.length / 2 < cmData.length)
      ? pxData.length / 2 : cmData.length;
    for (int i = 0; i < lpLength; i++) {
      //[test]::System.out.println("::" + i);
      cmData[i] = 0;
      cmData[i] |= ((int) pxData[i * 2 + 1]);
      cmData[i] |= ((int) pxData[i * 2]) << 8;
    }
  }//+++

  /**
   * 
   * @return reversed for my logic controller's protocol.
   */
  public final byte[] ccToByteArray() {
    int lpLength = cmData.length;
    byte[] lpRes = new byte[lpLength * 2];
    for (int i = 0; i < lpLength; i++) {
      lpRes[i * 2 + 1] = (byte) (cmData[i] & 0xFF);
      lpRes[i * 2] = (byte) ((cmData[i] & 0xFF00) >> 8);
    }
    return lpRes;
  }//+++

  //=== util ** int array
  
  /**
   * using array copy method of system class.
   * @param pxAddr start address
   * @param pxArray data
   */
  public void ccWriteIntArray(int pxAddr, int[] pxArray) {
    //[TO_REWEITE]::use Array.copyof()
    int lpReamins = cmMask - pxAddr & cmMask;
    int lpLength = pxArray.length < lpReamins
      ? pxArray.length : lpReamins + 1;
    System.arraycopy(pxArray, 0, cmData, pxAddr & cmMask, lpLength);
  }//+++

  /**
   * 
   * @param pxAddr start address
   * @param pxLenth #
   * @return will be trimmed if over indexed.
   */
  public int[] ccReadIntArray(int pxAddr, int pxLenth) {
    //[TO_REWEITE]::use Array.copyof()
    int lpMasked = (pxAddr & cmMask) + pxLenth;
    int lpFixed = lpMasked <= (cmMask + 1)
      ? lpMasked : (cmMask + 1);
    return Arrays.copyOfRange(
      cmData, pxAddr & cmMask,
      lpFixed
    );
  }//+++

  //=== interface
  
  //=== interface ** write
  
  @Override synchronized public void ccWriteWord
    (int pxAddr, int pxVal)
  { cmData[pxAddr & cmMask] = pxVal & 0xFFFF;
  }//+++

  @Override synchronized public void ccWriteBit
    (int pxAddr, int pxBit, boolean pxVal)
  { int lpTester = pxVal ? 0x00008000 : 0xFFFF7FFF;
    lpTester >>= (15 - pxBit & 0x0F);
    if (pxVal) {
      cmData[pxAddr & cmMask] |= lpTester;
    } else {
      cmData[pxAddr & cmMask] &= lpTester;
    }
  }//+++

  //=== interface ** read
    
  @Override synchronized public int ccReadWord
    (int pxAddr)
  { return cmData[pxAddr & cmMask];
  }//+++

  @Override synchronized public boolean ccReadBit
    (int pxAddr, int pxBit)
  { int lpTester = 0x00008000;
    lpTester >>= (15 - pxBit & 0x0F);
    return (cmData[pxAddr & cmMask] & lpTester) != 0;
  }//+++

}//***eof
