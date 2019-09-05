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

import java.util.HashMap;

/**
 * holds lots of maps as "dictionary" for you.<br>
 * add word from file should be implement separately.<br>
 */
public final class VcTranslator {
  
  /**
   * @return instance
   */
  public static VcTranslator ccGetInstance(){
    if(self==null){self = new VcTranslator();}
    return self;
  }//+++
  private static VcTranslator self = null;
  private VcTranslator(){}//..!

  //===
  
  private final HashMap<String, String> cmEnglishDict
    = new HashMap<String, String>();
  private final HashMap<String, String> cmChineseDict
    = new HashMap<String, String>();
  private final HashMap<String, String> cmJapaneseDict
    = new HashMap<String, String>();
  
  private char cmMode='c';
  
  /**
   * empty string will get registered by default.
   */
  public final void ccInit(){
    cmEnglishDict.put("", "");
    cmChineseDict.put("", "");
    cmJapaneseDict.put("", "");
  }//..!
  
  //===
  
  /**
   * <pre>
   * mode:
   *  - [e]:english
   *  - [j]:japanese
   *  - [c]:chinese
   *  - [x]:no translation.(or you can pass any letter)
   * </pre>
   * @param pxMode_ejcx #
   */
  public final void ccSetMode(char pxMode_ejcx){
    cmMode=pxMode_ejcx;
  }//+++
  
  /**
   * ##
   * @param pxKey must have something
   * @param pxWord must have something
   */
  public final void ccRegisterEnglishWord(String pxKey, String pxWord){
    if(!VcConst.ccIsValidString(pxKey)){return;}
    if(!VcConst.ccIsValidString(pxWord)){return;}
    if(cmEnglishDict.containsKey(pxKey)){return;}
    cmEnglishDict.put(pxKey, pxWord);
  }//+++
  
  /**
   * ##
   * @param pxKey must have something
   * @param pxWord must have something
   */
  public final void ccRegisterJapaneseWord(String pxKey, String pxWord){
    if(!VcConst.ccIsValidString(pxKey)){return;}
    if(!VcConst.ccIsValidString(pxWord)){return;}
    if(cmJapaneseDict.containsKey(pxKey)){return;}
    cmJapaneseDict.put(pxKey, pxWord);
  }//+++
  
  /**
   * ##
   * @param pxKey must have something
   * @param pxWord must have something
   */
  public final void ccRegisterChineseWord(String pxKey, String pxWord){
    if(!VcConst.ccIsValidString(pxKey)){return;}
    if(!VcConst.ccIsValidString(pxWord)){return;}
    if(cmChineseDict.containsKey(pxKey)){return;}
    cmChineseDict.put(pxKey, pxWord);
  }//+++
  
  //===
  
  /**
   * translates
   * @param pxSource #
   * @return never null if you never pass one
   */
  public static final String tr(String pxSource){
    if(pxSource==null){return pxSource;}
    if(pxSource.equals("")){return pxSource;}
    if(pxSource.equals("...")){return pxSource;}
    switch(self.cmMode){
      case 'e':return self.cmEnglishDict.getOrDefault(pxSource, pxSource);
      case 'j':return self.cmJapaneseDict.getOrDefault(pxSource, pxSource);
      case 'c':return self.cmChineseDict.getOrDefault(pxSource, pxSource);
      default:return pxSource;
    }//..?
  }//+++
  
 }//***eof
