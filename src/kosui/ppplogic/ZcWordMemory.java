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
import kosui.pppmodel.MiByteInterchangeable;
import kosui.ppputil.VcNumericUtility;

/**
 * a more real stuff like implement base on integer array. <br>
 * could be transformed to byte array for sending via socket. <br>
 */
public class ZcWordMemory implements ZiMemory,MiByteInterchangeable {

  private final int[] cmData;
  private final int cmMask;

  //===
  
  /**
   * here "word" means 16-bit, which should be a short.<br>
   * but anyway this is implemented with a integer array.<br>
   * @param pxWordSize will be fixed to the number of power of two
   */
  public ZcWordMemory(int pxWordSize) {
    cmData=new int[VcNumericUtility.ccToPowerOfTwo(pxWordSize)];
    cmMask=cmData.length-1;
  }//+++

  //===
  
  /**
   * @return a copy of raw integer array.
   */
  public final int[] ccGetData(){
    return Arrays.copyOf(cmData, cmData.length);
  }//+++
  
  //=== interface

  /**
   * {@inheritDoc }
   */
  @Override public int ccGetSize(){
    return cmData.length;
  }//+++
  
  //=== interface ** write
  
  /**
   * {@inheritDoc }
   */
  @Override synchronized public void ccWriteWord(int pxAddr, int pxVal){
    cmData[pxAddr&cmMask]=pxVal&0x0000FFFF;
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override synchronized public
  void ccWriteBit(int pxAddr, int pxBit, boolean pxVal){
    int lpTester=pxVal?0x00008000:0xFFFF7FFF;
    lpTester>>=(15-pxBit&0x0F);
    if(pxVal){
      cmData[pxAddr&cmMask]|=lpTester;
    }else{
      cmData[pxAddr&cmMask]&=lpTester;
    }//..?
  }//+++

  //=== interface ** read
    
  /**
   * {@inheritDoc }
   */
  @Override synchronized public int ccReadWord(int pxAddr){
    return cmData[pxAddr&cmMask];
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override synchronized public
  boolean ccReadBit(int pxAddr, int pxBit){
    int lpTester=0x00008000;
    lpTester>>=(15-pxBit&0x0F);
    return (cmData[pxAddr&cmMask]&lpTester)!=0;
  }//+++
    
  //=== interface ** convert
  
  /**
   * @return reversed for my logic controller's protocol.
   */
  @Override public byte[] ccToByteArray() {
    int lpLength=cmData.length;
    byte[] lpRes=new byte[lpLength*2];
    for(int i=0;i<lpLength;i++){
      lpRes[i*2+1]=VcNumericUtility.ccBinaryTrimLL(cmData[i]);
      lpRes[i*2  ]=VcNumericUtility.ccBinaryTrimLH(cmData[i]);
    }//..~
    return lpRes;
  }//+++

  /**
   * order should get fixed to target system
   * @param pxData #
   */
  @Override public void ccTakeByteArray(byte[] pxData){
    int lpFixedLength
      =(pxData.length/2 < cmData.length)?
        pxData.length/2 : cmData.length;
    for(int i=0;i<lpFixedLength;i++){
      cmData[i]=VcNumericUtility.ccBinaryCombine(pxData[i*2+1], pxData[i*2  ]);
    }//..~
  }//+++

}//***eof
