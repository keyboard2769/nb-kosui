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
import processing.core.PApplet;

/**
 *
 * @author Key Parker from K.I.C
 */
public final class VcStringUtility {
  
  private VcStringUtility(){}//..!
  
  //===
  
  /**
   * if you think REGEX is heavy you should NOT use this.<br>
   * @param pxNum #
   * @return only underscore and alphabet
   */
  static public final boolean ccIsCommandString(String pxNum){
    return pxNum.matches("^[_a-zA-Z]{1,64}$");
  }//+++
  
  /**
   * @param pxInput #
   * @return empty string if null passed
   */
  static public final String ccNulloutString(String pxInput){
    return pxInput==null?"":pxInput;
  }//+++
  
  /**
   * i do not know why sax is calling the thing "qName".<br>
   * bur t thought it is COOL!<br>
   * well im going to call it QTag.<br>
   * @param pxQTag a XML tag like "</>"
   * @return empty string if anything went wrong
   */
  static public final String ccExtractQTagString(String pxQTag){
    if(pxQTag==null){return "";}
    if(pxQTag.length()<=2){return "";}
    if(!pxQTag.startsWith("<")){return "";}
    if(!pxQTag.endsWith(">")){return "";}
    return pxQTag.substring(1,pxQTag.length()-1);
  }//+++
  
  /**
   * extract first than simply compare.<br>
   * @param pxQTag #
   * @param pxName #
   * @return String::equals
   */
  static public final
  boolean ccCompareQTagString(String pxQTag, String pxName){
    if(!VcConst.ccIsValidString(pxName)){return false;}
    return ccExtractQTagString(pxQTag).equals(pxName);
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
  
  //=== packing
  
  //       ..pack up a big string for print
  //[plan]::String ccPackupStringList(Object[] pxData){}
  
  /**
   * in the same manner of VcConst.ccPrintln() with square bracket.<br>
   * @param pxTag must have something
   * @param pxValue can be anything
   * @return #
   */
  static public final
  String ccPackupParedTag(String pxTag, Object pxValue){
    if(!VcConst.ccIsValidString(pxTag)){return "[:]";}
    StringBuilder lpRes=new StringBuilder("[");
    lpRes.append(pxTag);
    lpRes.append(':');
    lpRes.append(pxValue==null?"<null>":pxValue.toString());
    lpRes.append(']');
    return lpRes.toString();
  }//+++
  
  /**
   * @param pxPoleA like width or something
   * @param pxPoleB like height or something
   * @return formatted at four digit by default
   */
  static public final
  String ccPackupDimensionValue(int pxPoleA, int pxPoleB){
    StringBuilder lpRes=new StringBuilder("(");
    lpRes.append(PApplet.nf(pxPoleA, 4));
    lpRes.append(':');
    lpRes.append(PApplet.nf(pxPoleB, 4));
    lpRes.append(')');
    return lpRes.toString();
  }//+++
  
 }//***eof
