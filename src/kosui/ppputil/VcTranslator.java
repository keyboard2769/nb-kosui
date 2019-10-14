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
import java.util.Scanner;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * holds lots of maps as "dictionary" for you.<br>
 * adding word from file should be implement separately.<br>
 */
public final class VcTranslator {
  
  private static final String
    //-- header or tag
    Q_TR="tr",
    Q_EN="en",
    Q_JP="jp",
    Q_ZH="zh",
    Q_ATTR_KEY="key"
  ;//,,,
  
  private static final String
    //-- escape
    E_NEW_LINE = "<ln>",
    E_BLANK    = "<bk>"
  ;//,,,
  
  /**
   * @return instance
   */
  public static VcTranslator ccGetInstance(){
    if(self==null){self = new VcTranslator();}
    return self;
  }//++>
  private static VcTranslator self = null;
  private VcTranslator(){}//++!

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
        self.cmCurrentKey=lpKey;
        /* 4 */VcConst.ccLogln(".startElement()::got", lpKey);
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
      if(VcConst.ccIsAllNoneSpace(lpText)){return;}
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
    ) throws SAXException 
    {
      if(!pxQName.equals(Q_TR)){return;}
      /* 4 */VcConst.ccLogln(".endElement()::found", self.cmCurrentKey);
      self.ccRegisterEnglishWord(self.cmCurrentKey, self.cmCurrentEN);
      self.ccRegisterJapaneseWord(self.cmCurrentKey, self.cmCurrentJP);
      self.ccRegisterChineseWord(self.cmCurrentKey, self.cmCurrentZH);
      self.cmCurrentKey="";
      self.cmMode='x';
    }//+++
    
  };//***
  
  //===
  
  /**
   * empty string will get registered by default.<br>
   * <pre>
   * recommmend to call in this manner:
   *   .ccInit();
   *   .ccParse();
   *   ..
   *   .ccApplyLocale()/ccSetMode();
   * </pre>
   */
  public final void ccInit(){
    
    //-- emptiness
    cmEnglishDict.put("", "");
    cmJapaneseDict.put("", "");
    cmChineseDict.put("", "");
    
  }//++!
  
  //=== parsing
  
  /**
   * for detailed schema information see the library repository site.<br>
   * @param pxResource from Class<?>.getResourceAsStream()
   * @return true if nothing went wrong
   */
  public final boolean ccParseXML(InputStream pxResource){
    boolean lpRes;
    if(pxResource==null){return false;}
    try{
      SAXParserFactory.newInstance().newSAXParser()
        .parse(pxResource, O_HANDLER);
      lpRes=true;
    }catch (Exception e) {
      System.err.println(".ccParseXMl()::"+e.getMessage());
      lpRes=false;
    }//..?
    return lpRes;
  }//+++
  
  /**
   * for detailed order information see the library repository site.<br>
   * the text encode is harcoded to 'utf-8'.<br>
   * @param pxResource from Class<?>.getResourceAsStream()
   * @return true if nothing went wrong
   */
  public final boolean ccParseCSV(InputStream pxResource){
    if(pxResource==null){return false;}
    Scanner lpSanner = new Scanner(pxResource,"utf-8");
    boolean lpRes=false;
    for(int i=0;i<255;i++){
      if(!lpSanner.hasNext()){break;}
      lpRes|=ccParseCSV(lpSanner.next());
    }//..?
    return lpRes;
  }//+++
  
  private boolean ccParseCSV(String pxSingleLine){
    
    //-- check in
    if(!VcConst.ccIsValidString(pxSingleLine)){return false;}
    String[] lpRow=pxSingleLine.split(",");
    if(lpRow==null){return false;}
    if(lpRow.length!=6){return false;}
    if(!VcStringUtility.ccCompareQTagString(lpRow[0], Q_TR)){return false;}
    
    //-- register
    VcConst.ccLogln(".ccParseCSV()::key_found", lpRow[1]);
    self.ccRegisterEnglishWord(lpRow[1], lpRow[2]);
    self.ccRegisterJapaneseWord(lpRow[1], lpRow[3]);
    self.ccRegisterChineseWord(lpRow[1], lpRow[4]);
    return true;
  
  }//+++
  
  //[plan]::boolean ccParseINI(InputStream pxResource, char pxMode)
  //[plan]::boolean ccParseJSON ..do we actually need it ??
  
  //=== 
  
  /**
   * simply compare string from DefaultLocale::getCountry.<br>
   * supposedly should get call after ccInit();
   */
  public final void ccApplyLocale(){
    String lpCountry = Locale.getDefault().getCountry();
    /* 4 */VcConst.ccPrintln("locale",lpCountry);
    if(!(lpCountry==null)){
      if(lpCountry.equals("US")){
        cmMode='e';
      }else
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
  }//++<
  
  /**
   * ##
   * @param pxKey must have something
   * @param pxWord must have something
   */
  public final void ccRegisterEnglishWord(String pxKey, String pxWord){
    if(!VcConst.ccIsValidString(pxKey)){return;}
    if(!VcConst.ccIsValidString(pxWord)){return;}
    if(cmEnglishDict.containsKey(pxKey)){return;}
    cmEnglishDict.put(pxKey, ssReplaceEscapes(pxWord));
  }//++<
  
  /**
   * ##
   * @param pxKey must have something
   * @param pxWord must have something
   */
  public final void ccRegisterJapaneseWord(String pxKey, String pxWord){
    if(!VcConst.ccIsValidString(pxKey)){return;}
    if(!VcConst.ccIsValidString(pxWord)){return;}
    if(cmJapaneseDict.containsKey(pxKey)){return;}
    cmJapaneseDict.put(pxKey, ssReplaceEscapes(pxWord));
  }//++<
  
  /**
   * ##
   * @param pxKey must have something
   * @param pxWord must have something
   */
  public final void ccRegisterChineseWord(String pxKey, String pxWord){
    if(!VcConst.ccIsValidString(pxKey)){return;}
    if(!VcConst.ccIsValidString(pxWord)){return;}
    if(cmChineseDict.containsKey(pxKey)){return;}
    cmChineseDict.put(pxKey, ssReplaceEscapes(pxWord));
  }//++<
  
  private String ssReplaceEscapes(String pxString){
    return pxString
      .replaceAll(E_NEW_LINE, VcConst.C_V_NEWLINE)
      .replaceAll(E_BLANK, " ");
  }//++<
  
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
    String lpRes;
    switch(self.cmMode){
      case 'e':lpRes=self.cmEnglishDict.get(pxSource);break;
      case 'j':lpRes=self.cmJapaneseDict.get(pxSource);break;
      case 'c':lpRes=self.cmChineseDict.get(pxSource);break;
      default:lpRes=null;break;
    }//..?
    return lpRes==null?pxSource:lpRes;
  }//+++
  
  //=== test
  
  /**
   * @deprecated for test use only
   */
  @Deprecated public static final void tstReadupCurrent(){
    System.out.print(self.cmCurrentKey);
    System.out.print("->");
    System.out.print(self.cmCurrentEN);System.out.print("|");
    System.out.print(self.cmCurrentJP);System.out.print("|");
    System.out.print(self.cmCurrentZH);System.out.print("|");
    System.out.println("<<<");
  }//+++
  
  /**
   * @deprecated for test use only
   */
  @Deprecated public static final void tstReadupKeys(){
    System.out.println(">>> kosui.ppputil.VcTranslator.tstReadupKeys()");
    for(String it : self.cmEnglishDict.keySet()){
      System.out.println(it);
    }//..~
    System.out.println("<<< kosui.ppputil.VcTranslator.tstReadupKeys()");
  }//+++
  
 }//***eof
