/*
 * Copyright (C) 2019 Key Parker
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

package ppptest;

import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import processing.core.PApplet;

public class CaseNumberRegex{
  
  private static final String[] O_CASE_I={
    "0","00","000","0000",
    "1","12","123","12345","","","","","",
    "1","2","4","08","004","4d","-256",
    "-26","-3","-09","-009",
    "0.0","0.003",
    "0.52","1.2","1.32457","1.258","-1.5","-1.22222",
    "2.3.4"
  };
  
  private static final int[] O_CASE_II={
    1,2,3,4,11,12,13,14,123,1234,1235,123456,1234567,12345678,
    -1,-2,-3,-4,-11,-12,-13,-14,-123,-1234,-1235,-123456,-1234567,-12345678,
    0xFEDC,0xBA98,0x7654,0x3210,
    0xFE,0xDC,0xBA,0x98,0x76,0x54,0x32,0x10
  };
  
  private static final float[] O_CASE_III = {
    1f,2f,3f,4f,
    1.2f,1.2f,1.23f,1.234f
  };
    
  static private void ssTestFloatStringVerify(){
    VcConst.ccPrintln("== ccIsFloatString?? >>");
    for(String it:O_CASE_I){
      VcConst.ccPrintln(it, VcNumericUtility.ccIsFloatString(it));
    }//..~
    VcConst.ccPrintln("== <<");
  }//+++
  
  static private void ssTestIntegerStringVerify(){
    VcConst.ccPrintln("== ccIsIntegerString?? >>");
    for(String it:O_CASE_I){
      VcConst.ccPrintln(it, VcNumericUtility.ccIsIntegerString(it));
    }//..~
    VcConst.ccPrintln("== <<");
  }//+++
  
  static private void ssTestMaskGeneration(){
    VcConst.ccPrintln("== ssTestMaskGeneration >>");
    for(int i=-1;i<17;i++){
      VcConst.ccPrintln(
        Integer.toString(i), 
        PApplet.binary(VcNumericUtility.ccToOnStateMask(i),16)
      );
    }//..~
  }//+++
  
  static private void ssTestBinaryLoad(){
    VcConst.ccPrintln("== ssTestBinaryLoad >>");
    for(int i=-1;i<17;i++){
      VcConst.ccPrintln(
        Integer.toString(i), 
        VcNumericUtility.ccBinaryLoad(7, i)
      );
    }//..~
    VcConst.ccPrintln("== <<");
  }//+++
  
  static private void ssTestBinarySet(){
    VcConst.ccPrintln("== ssTestBinarySet >>");
    int lpSource=1;
    for(int i=-1;i<17;i++){
      lpSource=VcNumericUtility.ccBinarySet(lpSource, i, true);
      VcConst.ccPrintln(
        Integer.toString(i), 
        PApplet.hex(lpSource,16)
      );
    }//..~
    for(int i=-1;i<17;i++){
      lpSource=VcNumericUtility.ccBinarySet(lpSource, i, false);
      VcConst.ccPrintln(
        Integer.toString(i), 
        PApplet.hex(lpSource,16)
      );
    }//..~
    VcConst.ccPrintln("== <<");
  }//+++
  
  //=== entry
  
  public static void main(String[] args){
    System.out.println("CaseNumberRegex.main()::start");
    //--
    
    ssTestBinarySet();
    
    //--
    System.out.println("CaseNumberRegex.main()::over");
  }//..!
  
}//***eof
