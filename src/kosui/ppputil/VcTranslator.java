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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * holds lots of maps as "dictionary" for you.<br>
 * add word from file should be implement separately.<br>
 */
public final class VcTranslator {
  
  private static final String
    Q_TR="tr",
    Q_EN="en",
    Q_JP="jp",
    Q_ZH="zh",
    Q_ATTR_KEY="key"
  ;//...
  
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
  
  private String cmCurrentKey;
  private String cmCurrentEN;
  private String cmCurrentJP;
  private String cmCurrentZH;
  private char cmMode='c';
  
  private static final DefaultHandler O_HANDLER = new DefaultHandler(){
    
    @Override public void startElement(
      String pxURI, String pxLocalName, String pxQName,
      Attributes pxAttributes
    )throws SAXException
    {
      if(pxQName.equals(Q_TR)){
        String lpKey=pxAttributes.getValue(Q_ATTR_KEY);
        if(lpKey==null){return;}
        self.cmMode='x';
      }else
      if(pxQName.equals(Q_EN)){
        self.cmMode='e';
      }else
      if(pxQName.equals(Q_JP)){
        self.cmMode='j';
      }else
      if(pxQName.equals(Q_ZH)){
        self.cmMode='c';
      }//..?
    }//+++

    @Override public void characters(
      char[] arg0, int arg1, int arg2
    )throws SAXException
    {
      String lpText = new String(arg0,arg1,arg2);
      if(!VcConst.ccIsAllNoneSpace(lpText)){return;}
      lpText=lpText.trim();
      switch(self.cmMode){
        case 'e':self.cmCurrentEN=lpText;break;
        case 'j':self.cmCurrentJP=lpText;break;
        case 'c':self.cmCurrentZH=lpText;break;
        default:break;
      }//..?
    }//+++

    @Override public void endElement(
      String pxURI, String pxLocalName, String pxQName
    ) throws SAXException {
      if(!pxQName.equals(Q_TR)){return;}
      self.ccRegisterEnglishWord(self.cmCurrentKey, self.cmCurrentEN);
      self.ccRegisterJapaneseWord(self.cmCurrentKey, self.cmCurrentJP);
      self.ccRegisterChineseWord(self.cmCurrentKey, self.cmCurrentZH);
      self.cmCurrentKey="";
      self.cmMode='x';
    }//+++
    
  };//***
  
  //===
  
  /**
   * empty string will get registered by default.
   */
  public final void ccInit(){
    
    //-- emptiness
    cmEnglishDict.put("", "");
    cmJapaneseDict.put("", "");
    cmChineseDict.put("", "");
    
  }//..!
  
  //===
  
  /**
   * ##
   * @param pxStream from Class<?>.getResourceAsStream()
   * @return if nothing is wrong
   */
  public final boolean ccParseXML(InputStream pxStream){
    boolean lpRes;
    if(pxStream==null){return false;}
    try{
      SAXParserFactory.newInstance().newSAXParser().parse(pxStream, O_HANDLER);
      lpRes=true;
    }catch (Exception e) {
      System.err.println(".ccParseXMl()::"+e.getMessage());
      lpRes=false;
    }//..?
    return lpRes;
  }//+++
  
  //[plan]::boolean ccParseCSV
  //[plan]::boolean ccParseINI
  //[plan]::boolean ccParseJSON ..do we actually need it ??
  
  /**
   * simply compare string from DefaultLocale::getCountry.<br>
   * supposedly should get call after ccInit();
   */
  public final void ccApplyLocale(){
    String lpCountry = Locale.getDefault().getCountry();
    /* 4 */VcConst.ccPrintln("locale",lpCountry);
    if(!(lpCountry==null)){
      if(lpCountry.equals("JP")){
        cmMode='j';
      }else
      /* 7 */if(lpCountry.equals("ZH")){//.. we need a test machine
        cmMode='c';
      }//..?
    }//..?
  }//+++
  
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
  
  //=== test
  
  /**
   * @deprecated for test use only
   */
  @Deprecated public static void tstReadupCurrent(){
    System.out.print(self.cmCurrentKey);
    System.out.print("->");
    System.out.print(self.cmCurrentEN);System.out.print("|");
    System.out.print(self.cmCurrentJP);System.out.print("|");
    System.out.print(self.cmCurrentZH);System.out.print("|");
    System.out.println("<<<");
  }//+++
  
 }//***eof
