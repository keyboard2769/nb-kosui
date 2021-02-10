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
   * ##
   * @param pxLine no null no empty
   * @param pxIndex will get constrained via PApplet.constrain
   * @return Character.MIN_VALUE if anything went wrong
   */
  static public final char ccGetCharAt(String pxLine, int pxIndex){
    if(!VcConst.ccIsValidString(pxLine)){return Character.MIN_VALUE;}
    return pxLine.charAt(PApplet.constrain(pxIndex, 0, pxLine.length()-1));
  }//+++
  
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
  
  //=== assert
  
  /**
   * if you think REGEX is heavy you should NOT use this.<br>
   * @param pxNum #
   * @return only underscore and alphabet
   */
  static public final boolean ccIsCommandString(String pxNum){
    return pxNum.matches("^[_a-zA-Z]{1,64}$");
  }//+++
  
  /**
   * ##
   * @param pxLine ##
   * @return ##
   */
  public static boolean ccIsInvisibleString(String pxLine){
    if(pxLine==null){return false;}
    if(pxLine.isEmpty()){return true;}
    return pxLine.matches("\\s+");
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
   * alias for String::charAt to bypass null check
   * @param pxLine ##
   * @param pxChar ##
   * @return ##
   */
  static public final
  boolean ccStartWith(String pxLine, char pxChar){
    if(VcConst.ccIsValidString(pxLine)){
      return pxLine.charAt(0)==pxChar;
    }else{return false;}//..?
  }//+++
  
  /**
   * alias for String::startsWith to bypass null check
   * @param pxLine ##
   * @param pxStart ##
   * @return ##
   */
  static public final
  boolean ccStartWith(String pxLine, String pxStart){
    if(!VcConst.ccIsValidString(pxLine))
      {return !VcConst.ccIsValidString(pxStart);}
    if(!VcConst.ccIsValidString(pxStart))
      {return !VcConst.ccIsValidString(pxLine);}
    return pxLine.startsWith(pxStart);
  }//+++
  
  /**
   * alias for String::charAt to bypass null check
   * @param pxLine ##
   * @param pxChar ##
   * @return ##
   */
  static public final
  boolean ccEndWith(String pxLine, char pxChar){
    if(VcConst.ccIsValidString(pxLine)){
      return pxLine.charAt(pxLine.length()-1)==pxChar;
    }else{return false;}//..?
  }//+++
  
  /**
   * alias for String::endsWith to bypass null check
   * @param pxLine ##
   * @param pxEnd ##
   * @return ##
   */
  public static final boolean ccEndWith(String pxLine, String pxEnd){
    if(!VcConst.ccIsValidString(pxLine))
      {return !VcConst.ccIsValidString(pxEnd);}
    if(!VcConst.ccIsValidString(pxEnd))
      {return !VcConst.ccIsValidString(pxLine);}
    return pxLine.endsWith(pxEnd);
  }//+++
  
  /**
   * alias for String::equals to bypass null check
   * @param pxLine ##
   * @param pxTarget ##
   * @return ##
   */
  static public final boolean ccEquals(String pxLine, String pxTarget){
    if(pxLine == null){return pxTarget==null;}
    if(pxLine.isEmpty()){
      if(pxTarget==null){
        return false;
      }else{
        return pxTarget.isEmpty();
      }//..?
    }//..?
    return pxLine.equals(pxTarget);
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
   * ##
   * @param pxLine ##
   * @param pxOrDefault ##
   * @return default if null passed
   */
  public static final
  String ccNulloutString(String pxLine, String pxOrDefault){
    if(pxLine==null){return pxOrDefault;}
    else{return pxLine;}
  }//+++
  
  /**
   * in form of `abc.efg`.<br>
   * swallow those error stuff for you.<br>
   * @param pxName separated by the dot
   * @return the one before the first dot
   */
  public static final String ccExtractFileName(String pxName){
    String lpAbn = "VcStringUtility.ccExtractFileName $ abort";
    if(!VcConst.ccIsValidString(pxName))
      {VcConst.ccErrln(lpAbn, -101);return pxName;}//..?
    String[] lpSplit = pxName.split("\\.");
    if(lpSplit == null)
      {VcConst.ccErrln(lpAbn, -102);return pxName;}//..?
    if(lpSplit.length <=1 )
      {VcConst.ccErrln(lpAbn, -103);return pxName;}//..?
    return lpSplit[0];
  }//+++
  
  /**
   * in form of `abc.efg`.<br>
   * swallow those error stuff for you.<br>
   * @param pxName separated by the dot
   * @return the one after the last dot
   */
  public static final String ccExtractFileExtension(String pxName){
    String lpAbn = "VcStringUtility.ccExtractFileExtension() $ abort";
    if(!VcConst.ccIsValidString(pxName))
      {VcConst.ccErrln(lpAbn, -101);return pxName;}//..?
    String[] lpSplit = pxName.split("\\.");
    if(lpSplit == null)
      {VcConst.ccErrln(lpAbn, -102);return pxName;}//..?
    if(lpSplit.length <=1 )
      {VcConst.ccErrln(lpAbn, -103);return pxName;}//..?
    return lpSplit[lpSplit.length-1];
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
   * just replacing all back-slash-r and black-slash-n.<br>
   * previously called `return-killer`.<br>
   * @param pxSource #
   * @return #
   */
  static public final String ccFlat(String pxSource){
    if(!VcConst.ccIsValidString(pxSource)){return pxSource;}
    String lpRes = pxSource.replaceAll("\\r", "");
    return lpRes.replaceAll("\\n", "");
  }//+++
  
  /**
   * cut the source from left via String::substring.<br>
   * supposedly , say, you want
   *   `uno_does_tree` to become `uno_` .<br>
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
   * cut the left side of the source line via String::replaceFirst.<br>
   * supposedly , say, you want
   *   `uno_does_tree` to become `_does_tree` .<br>
   * @param pxLine ##
   * @param pxStartWith ##
   * @return ##
   */
  public static final String ccCutLeft(String pxLine, String pxStartWith){
    if(!VcStringUtility.ccStartWith(pxLine, pxStartWith)){return pxLine;}
    return pxLine.replaceFirst(pxStartWith, "");
  }//+++
  
  /**
   * cut the source from left via String::substring.<br>
   * supposedly , say, you want
   *   `uno_does_tree` to become `_tree` .<br>
   * @param pxSource ##
   * @param pxCharCount ##
   * @return ##
   */
  static public final String ccRight(String pxSource, int pxCharCount){
    if(!ccIsValidString(pxSource)){return pxSource;}
    int lpLength=pxSource.length();
    if(pxCharCount<1 || pxCharCount>lpLength){return pxSource;}
    return pxSource.substring(lpLength-pxCharCount, lpLength);
  }//+++
  
  /**
   * cut the left side of the source line via VcStringUtility.ccLeft.<br>
   * supposedly , say, you want
   *   `uno_does_tree` to become `uno_does_` .<br>
   * @param pxLine ##
   * @param pxEndWith ##
   * @return ##
   */
  public static final String ccCutRight(String pxLine, String pxEndWith){
    if(!VcStringUtility.ccEndWith(pxLine, pxEndWith)){return pxLine;}
    if(pxLine.length()<=pxEndWith.length()){return pxLine;}
    return VcStringUtility.ccLeft(pxLine, pxLine.length()-pxEndWith.length());
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
    if(!VcArrayUtility.ccIsValidList(pxTarget, 1)){return "";}
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
   * in the form of `[k:v]`.<br>
   * @param pxTag must have something
   * @param pxValue can be anything
   * @return ##
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
   * in the form of `[???:o|x]`.<br>
   * @param pxTag must have something
   * @param pxValue can be anything
   * @return ##
   */
  static public final
  String ccPackupBoolTag(String pxTag, boolean pxValue){
    if(!VcConst.ccIsValidString(pxTag)){return "[:]";}
    StringBuilder lpRes=new StringBuilder("[");
    lpRes.append(pxTag);
    lpRes.append(':');
    lpRes.append(ccToString(pxValue));
    lpRes.append(']');
    return lpRes.toString();
  }//+++
  
  /**
   * in the form of `-k v`.<br>
   * @param pxFlag must have something
   * @param pxValue can be anything
   * @return ##
   */
  static public final
  String ccPackupFlag(String pxFlag, Object pxValue){
    if(!VcConst.ccIsValidString(pxFlag)){return "";}
    StringBuilder lpRes=new StringBuilder("-");
    lpRes.append(pxFlag);
    lpRes.append(' ');
    lpRes.append(pxValue==null?"<null>":pxValue.toString());
    return lpRes.toString();
  }//+++
  
  /**
   * in the form of `(0000x0000)`
   * @param pxPoleA like width or something
   * @param pxPoleB like height or something
   * @return ##
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
  
  /**
   * in the form of `{-0 := -0}`
   * @param pxThis supposedly a step code
   * @param pxPrev supposedly an error code
   * @return ##
   */
  static public final
  String ccPackupErrorTraceBlock(int pxThis, int pxPrev){
    StringBuilder lpRes=new StringBuilder("{");
    lpRes.append(Integer.toString(pxThis));
    lpRes.append(' ');
    lpRes.append('<');
    lpRes.append(' ');
    lpRes.append(Integer.toString(pxPrev));
    lpRes.append('}');
    return lpRes.toString();
  }//+++
  
 }//***eof
