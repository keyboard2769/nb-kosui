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

package kosui.ppputil;

import static kosui.ppputil.VcConst.ccIsValidString;
import static processing.core.PApplet.hex;

/**
 *
 * @author Key Parker from K.I.C
 */
public final class VcStringUtility {
  
  private static VcStringUtility self = null;
  public static VcStringUtility ccGetInstance() {
    if(self==null){self=new VcStringUtility();}
    return self;
  }//+++
  private VcStringUtility(){}//..!
  
  //===
  
  /**
   * @param pxInput #
   * @return empty string if null passed
   */
  static public final String ccNulloutString(String pxInput){
    return pxInput==null?"":pxInput;
  }//+++
  
  /**
   * <b>WARN:</b>
   * <b>BY NOW IT IS MEANLY IMPLEMENTED AS JUST SPLITTING BY SPACE</b><br>
   * extract the unit representation from the given form.<br>
   * @param pxForm something like "000.0 kpa"
   * @return extracted string like " kpa"
   */
  static public final String ccExtracUnit(String pxForm){
    //[plan]::make this better!!
    if(!VcConst.ccIsValidString(pxForm)){return "";}
    String[] lpSplitted=pxForm.split(" ");
    if(lpSplitted.length==1){return "";}
    return lpSplitted[lpSplitted.length-1];
  }//+++
  
  /**
   * insert new line with every given count. <br>
   * NOT synchronized. based on StringBuilder. <br>
   * @param pxSource #
   * @param pxCharCount #
   * @return new string with new line inserted
   */
  static public final String ccWrap(String pxSource, int pxCharCount){
    StringBuilder lpBuilder = new StringBuilder();
    for(int i=0,s=pxSource.length();i<s;i++){
      lpBuilder.append(pxSource.charAt(i));
      if(i%pxCharCount==0 && i!=0){lpBuilder.append('\n');}
    }//..~
    return lpBuilder.toString();
  }//+++
  
  /**
   * cut the source from left
   * @param pxSource #
   * @param pxCharCount #
   * @return substring-ed string
   */
  static public final String ccLeft(String pxSource, int pxCharCount){
    if(!ccIsValidString(pxSource)){return pxSource;}
    if(pxCharCount<1 || pxCharCount>pxSource.length()){return pxSource;}
    return pxSource.substring(0, pxCharCount);
  }//+++
  
  /**
   * cut the source from right
   * @param pxSource #
   * @param pxCharCount #
   * @return substring-ed string
   */
  static public final String ccRight(String pxSource, int pxCharCount){
    if(!ccIsValidString(pxSource)){return pxSource;}
    int lpLength=pxSource.length();
    if(pxCharCount<1 || pxCharCount>lpLength){return pxSource;}
    return pxSource.substring(lpLength-pxCharCount, lpLength);
  }//+++
  
  //===
  
  /**
   * actually i don't see why i needed this.
   * @param pxArray #
   * @return #
   */
  synchronized static public final String[] ccToStringArray(Object[] pxArray){
    String[] lpRes=new String[pxArray.length];
    for(int i=0,s=lpRes.length;i<s;i++)
      {lpRes[i]=pxArray[i].toString();}
    return lpRes;
  }//+++
  
  /**
   * pack up a big string for print.<br>
   * @param pxArray must have something 
   * @param pxWrap where the line breaks
   * @return 
   */
  static public final String ccPackupArray(byte[] pxArray, int pxWrap){
    if(pxArray==null){return "";}
    if(pxArray.length==0){return "";}
    StringBuilder lpBuilder
      = new StringBuilder("byte array:");
    lpBuilder.append(VcConst.C_V_NEWLINE);
    int lpWrapCount=0;
    for(int i=0,s=pxArray.length;i<s;i++){
      lpBuilder.append(" 0x");
      lpBuilder.append(hex(pxArray[i],2));
      lpWrapCount++;
      if(lpWrapCount==pxWrap){
        lpBuilder.append(VcConst.C_V_NEWLINE);
        lpWrapCount=0;
      }//..?
    }//..~
    lpBuilder.append(VcConst.C_V_NEWLINE);
    lpBuilder.append("<<<");
    lpBuilder.append(VcConst.C_V_NEWLINE);
    return lpBuilder.toString();
  }//+++
    
  //[PLAN]::static String ccPackupArray(boolean[] pxArray, int pxWrap){}
  //[PLAN]::static String ccPackupArray(int[] pxArray, int pxWrap){}
  //[PLAN]::static String ccPackupArray(float[] pxArray, int pxWrap){}
  
 }//***eof
