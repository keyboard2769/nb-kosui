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

import java.util.List;
import static kosui.ppputil.VcConst.ccIsValidString;
import processing.core.PApplet;

/**
 *
 * @author Key Parker from K.I.C
 */
public final class VcStringUtility {
  
  private VcStringUtility(){}//..!
  
  //=== charactor
  
  /**
   * @param pxKey could be anything
   * @return [0x20 ~ 0x7E]
   */
  static public final boolean ccIsPrintable(char pxKey){
    return (pxKey >= ' ') && (pxKey <= '~');
  }//+++
  
  /**
   * @param pxKey could be anything
   * @return no control no digit no alphabetic
   */
  static public final boolean ccIsPunctuation(char pxKey){
    boolean lpRes=ccIsPrintable(pxKey);
    lpRes&=!(Character.isDigit(pxKey));
    lpRes&=!(Character.isAlphabetic(pxKey));
    return lpRes;
  }//+++
  
  /**
   * @param pxKey could be anything
   * @return no control no digit no alphabetic no parentheses
   */
  static public final boolean ccIsSeparator(char pxKey){
    boolean lpRes=ccIsPunctuation(pxKey);
    lpRes&=(pxKey!='<');
    lpRes&=(pxKey!='>');
    lpRes&=(pxKey!='(');
    lpRes&=(pxKey!=')');
    lpRes&=(pxKey!='[');
    lpRes&=(pxKey!=']');
    lpRes&=(pxKey!='{');
    lpRes&=(pxKey!='}');
    return lpRes;
  }//+++
  
  //=== judging
  
  /**
   * if you think REGEX is heavy you should NOT use this.<br>
   * @param pxNum #
   * @return only underscore and alphabet
   */
  static public final boolean ccIsCommandString(String pxNum){
    return pxNum.matches("^[_a-zA-Z]{1,64}$");
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
  
  //=== manipulate
  
  /**
   * @param pxInput #
   * @return empty string if null passed
   */
  static public final String ccNulloutString(String pxInput){
    return pxInput==null?"":pxInput;
  }//+++
  
  /**
   * swallow those error stuff for you
   * @param pxFileName separated by the dot
   * @return the one before the first dot
   */
  public static final String ccExtractFileName(String pxFileName){
    if(!VcConst.ccIsValidString(pxFileName)){
      System.err.println(".ccTrimFileName $ ER_101");
      return pxFileName;
    }//..?
    String[] lpSplit = pxFileName.split("\\.");
    if(lpSplit == null){
      System.err.println(".ccTrimFileName $ ER_102");
      return pxFileName;
    }//..?
    if(lpSplit.length <=1 ){
      System.err.println(".ccTrimFileName $ ER_103");
      return pxFileName;
    }//..?
    return lpSplit[0];
  }//..?
  
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
  
  /**
   * the break character is hard coded to '@' '$' and '|'.<br>
   * also replace true and false to 'o' and 'x'.<br>
   * @param pxSource will get .toString() invoked explicitly
   * @return line broken string representation
   */
  static public final String ccBreakObject(Object pxSource){
    if(pxSource==null){return "";}
    return 
      pxSource.toString()
        .replaceAll("(\\$|@|\\|)", VcConst.C_V_NEWLINE)
        .replaceAll("true", "o")
        .replaceAll("false", "x");
  }//+++
  
  /**
   * wrapping String::replaceAll() with every stuff in the list for you.<br>
   * the looping nature may cause overheads.<br>
   * if the replacement arguments is invalid than it just walk through.<br>
   * @param pxSource no null no empty
   * @param pxRegex no null no empty
   * @param pxReplacement no null no empty
   */
  public static final
  void ccReplaceAll(List<String> pxSource, String pxRegex, String pxReplacement){
    if(pxSource==null){return;}
    if(pxSource.isEmpty()){return;}
    for(int i=0,s=pxSource.size();i<s;i++){
      String lpBUF = pxSource.get(i);
      if(VcConst.ccIsValidString(pxRegex)
       &&VcConst.ccIsValidString(pxRegex)
      ){lpBUF=lpBUF.replaceAll(pxRegex, pxReplacement);}
      pxSource.set(i, lpBUF);
    }//..~
  }//+++
  
  //=== formatting
  
  /**
   * @param pxVal to convert
   * @return true to 'o' and false to 'x'
   */
  static public final
  String ccToString(boolean pxVal){
    return pxVal?"o":"x";
  }//+++
  
  //=== packing
  
  /**
   * derived from PApplet.join but first,
   *   we are STILL stuck on StringBuilder and second,
   *   we eat single element and third,
   *   we only accept what we call separator.<br>
   * @param pxTarget no null no empty no single
   * @param pxSeparator see ccIsSeparator() and if fails we give an underscore
   * @return never null
   */
  static public final
  String ccJoin(List<String> pxTarget, char pxSeparator){
    if(!VcConst.ccIsValidList(pxTarget, 1)){return "";}
    StringBuilder lpRes=new StringBuilder("");
    char lpFixed=ccIsSeparator(pxSeparator)?pxSeparator:'_';
    for(String it : pxTarget){
      lpRes.append(it);
      lpRes.append(lpFixed);
    }//..~
    lpRes.deleteCharAt(lpRes.length()-1);
    return lpRes.toString();
  }//+++
  
  /**
   * in the same manner of VcConst.ccPrintln() with square bracket.<br>
   * @param pxTag must have something
   * @param pxValue can be anything
   * @return #
   */
  static public final
  String ccPackupPairedTag(String pxTag, Object pxValue){
    if(!VcConst.ccIsValidString(pxTag)){return "[:]";}
    StringBuilder lpRes=new StringBuilder("[");
    lpRes.append(pxTag);
    lpRes.append(':');
    lpRes.append(pxValue==null?"<null>":pxValue.toString());
    lpRes.append(']');
    return lpRes.toString();
  }//+++
  
  /**
   * true to 'o' and false to 'x'.<br>
   * @param pxTag must have something
   * @param pxValue can be anything
   * @return #
   */
  static public final
  String ccPackupBoolTag(String pxTag, boolean pxValue){
    if(!VcConst.ccIsValidString(pxTag)){return "[:]";}
    StringBuilder lpRes=new StringBuilder("[");
    lpRes.append(pxTag);
    lpRes.append(':');
    lpRes.append(pxValue?"o":"x");
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
