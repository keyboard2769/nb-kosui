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

package kosui.pppmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import kosui.ppplogic.ZcPLC;
import kosui.pppswingui.ScConst;
import processing.data.XML;
import processing.data.Table;
import processing.data.TableRow;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStampUtility;
import kosui.ppputil.VcStringUtility;
import kosui.ppputil.VcTranslator;
import org.xml.sax.SAXException;
import processing.data.StringList;

/**
 * sometimes it will be completely okay if you just don't talk to people.<br>
 * writing tedious file IO is just like live your own pathetic life.<br>
 */
public final class McConst {
  
  /**
   * all file this frame work deal with shall be tiny enough
   * to get recognized as a "config" file.<br>
   */
  public static final int C_MAX_FILESIE_FOR_LOADING
    = 0xFFFF;
  
  /**
   * supposed to get registered to the dictionary.<br>
   * maybe hard code it is not a good idea.<br>
   */
  public static final String C_KEY_OVERWRITE_COMFIRMATION
    = "--sys-msg-overwite-confirmation";
  
  private McConst(){}//+++ 
  
  //=== verify
  
  /**
   * with null check, absolution check, existence check, identical check.<br>
   * @param pxFolder #
   * @return 0 if everything match or step code
   */
  public static final int ccVerifyFolder(File pxFolder){
    if(pxFolder==null){return -101;}//..?
    if(!pxFolder.isAbsolute()){return -102;}//..?
    if(!pxFolder.exists()){return -103;}//..?
    if(!pxFolder.isDirectory()){return -104;}//..?
    return 0;
  }//+++
  
  /**
   * @param pxFolder will get verified via ccVerifyFolder(File)
   * @param pxDesFileName elements should be the full file name 
   * @return 0 if everything match or step code
   */
  public static final
  int ccVerifyFolder(File pxFolder, String[] pxDesFileName){
    int lpRes = McConst.ccVerifyFolder(pxFolder);
    if(lpRes<0){return lpRes;}//..?
    if(pxDesFileName==null){return -101;}//..?
    LinkedList<String> lpFolderList = new LinkedList<String>();
    for(File it:pxFolder.listFiles()){
      lpFolderList.add(it.getName());
    }//..~
    boolean lpProb=true;
    for(String it:pxDesFileName){
      lpProb&=lpFolderList.contains(it);
      if(!lpProb){
        VcConst.ccLogln(".ccVerifyFolder $ failed at", it);
        break;
      }//..?
    }//..~
    return lpProb?0:-1;
  }//+++
  
  /**
   * <pre>
   * with null check and absolution check.
   * also does existence check and automatically invokes 
   *   a dialog and translation for getting confirmation from user.
   * if you hate those overheads and initation sequence problems,
   *   just avoid use this method.
   * </pre>
   * @param pxFile #
   * @return 0 if everything okay or step code
   */
  public static final int ccVerifyFileForSaving(File pxFile){
    if(pxFile==null){return -101;}//..?
    if(!pxFile.isAbsolute()){return -102;}//..?
    if(pxFile.exists()){
      return ScConst.ccYesOrNoBox(
        VcTranslator.tr(C_KEY_OVERWRITE_COMFIRMATION)
      )?0:-1;
    }else{return 0;}//..?
  }//+++
  
  /**
   * the extension check is performed via String.endWith().<br>
   * @param pxFile will get verified with the none extension version
   * @param pxExtension must have something
   * @return 0 if everything okay or step code
   */
  public static final
  int ccVerifyFileForSaving(File pxFile, String pxExtension){
    if(pxFile==null){return -101;}//..?
    if(!VcConst.ccIsValidString(pxExtension)){return -102;}//..?
    int lpRes = ccVerifyFileForSaving(pxFile);
    if(lpRes < 0){return -103;}//..?
    return pxFile.getName().endsWith(pxExtension)?0:-104;
  }//+++
  
  /**
   * with null check, absolution check, existence check, identical check.<br>
   * @param pxFile absolutely exists file
   * @return 0 if everything okay or step code
   */
  public static final int ccVerifyFileForLoading(File pxFile){
    if(pxFile==null){return -101;}
    if(!pxFile.isAbsolute()){return -102;}
    if(!pxFile.isFile()){return -103;}
    if(!pxFile.exists()){return -104;}
    return 0;
  }//+++
  
  /**
   * expanding ccVerifyFileForLoading(File).<br>
   * the extension check is performed via String.endWith().<br>
   * @param pxFile will get verified with the none extension version
   * @param pxExtension must have something whit no "dot" prefix
   * @return 0 if everything okay or step code
   */
  public static final
  int ccVerifyFileForLoading(File pxFile, String pxExtension){
    if(pxFile==null){return -101;}
    if(!VcConst.ccIsValidString(pxExtension)){return -102;}
    int lpRes = ccVerifyFileForLoading(pxFile);
    if(lpRes < 0){return -103;}
    return pxFile.getName().endsWith("."+pxExtension)?0:-104;
  }//+++
  
  /**
   * expanding ccVerifyFileForLoading(File, String).<br>
   * length check is performed via File.length().<br>
   * @param pxFile will get verified with the none extension version
   * @param pxExtension must have something
   * @param pxMaxByteLength must be lesser than this
   * @return 0 if everything okay or step code
   */
  public static final
  int ccVerifyFileForLoading(
    File pxFile, String pxExtension, long pxMaxByteLength
  ){
    int lpRes =ccVerifyFileForLoading(pxFile,pxExtension);
    if(lpRes<0){return -101;}
    return (pxFile.length()<=pxMaxByteLength)?0:-102;
  }//+++
  
  /**
   * must contains every given list member.<br>
   * @param pxTable the source object to check
   * @param pxDesColumn those supposed must have content
   * @return 0 if everything okay or step code
   */
  public static final
  int ccVerifyTableColumn(Table pxTable, String[] pxDesColumn){
    
    //-- check in
    if(pxTable==null){return -101;}
    if(pxDesColumn==null){return -102;}
    if(pxDesColumn.length<=1){
      return VcConst.ccErrln(".ccVerifyTableColumn $ abort" , -103);
    }//..?
    
    //-- additional check
    int lpColumnCount=pxTable.getColumnCount();
    int lpRowCount=pxTable.getRowCount();
    if(lpColumnCount<=1 || lpRowCount==0){
      return VcConst.ccErrln(".ccVerifyTableColumn $ abort" , -103);
    }//..?
    /* 4 */VcConst.ccLogln(String.format(
      ".ccVerifyTableColumn $ got table with [ %d x %d] for : %s",
      lpColumnCount, lpRowCount, Arrays.toString(pxDesColumn)
    ));
    
    //-- loop
    Set<String> lpTitleSet
      = new HashSet<String>(Arrays.asList(pxTable.getColumnTitles()));
    boolean lpProb=true;
    for(String it:pxDesColumn){
      lpProb&=lpTitleSet.contains(it);
      if(!lpProb){
        VcConst.ccLogln(".ccVerifyTableColumn $ failed at",it);
        break;
      }//..?
    }//..~
    
    //-- report
    return lpProb?0:-109;
    
  }//+++
  
  //=== data process
  
  /**
   * <pre>
   * a safer alternative to just calling TableRow.getString().
   * swallow exception and nothingness for you.
   * </pre>
   * @param pxRow do not pass null
   * @param pxColumn do not pass null
   * @param pxOrDefault or this will get returned
   * @return never null if you don not pass any
   */
  static public final
  String ccGetValue(TableRow pxRow, String pxColumn, String pxOrDefault){
    if(pxRow==null){return pxOrDefault;}
    if(!VcConst.ccIsValidString(pxColumn)){return pxOrDefault;}
    String lpRes;
    try {
      lpRes = pxRow.getString(pxColumn);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      lpRes=null;
    }//..?//..?
    if(lpRes==null){
      VcConst.ccLogln(
        "McConst.ccGetValue $ failed on TableRow::getString with",pxColumn
      );
      return pxOrDefault;
    }else{return lpRes;}//..?
  }//+++
  
  /**
   * <pre>
   * a safer alternative to just calling TableRow.getString().
   * swallow exception and nothingness for you.
   * </pre>
   * @param pxRow do not pass null
   * @param pxColumn do not pass minus value
   * @param pxOrDefault or this will get returned
   * @return never null if you don not pass any
   */
  static public final
  String ccGetValue(TableRow pxRow, int  pxColumn, String pxOrDefault){
    if(pxRow==null){return pxOrDefault;}
    if(pxColumn<0){return pxOrDefault;}
    String lpRes;
    try {
      lpRes = pxRow.getString(pxColumn);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      lpRes=null;
    }//..?//..?
    if(lpRes==null){
      VcConst.ccLogln("McConst.ccGetValue $ failed on TableRow::getString");
      return pxOrDefault;
    }else{return lpRes;}//..?
  }//+++
  
  /**
   * <pre>
   * a safer alternative to just callling XML.getChild().getContent().
   * swallow exception and nothingness for you.
   * </pre>
   * @param pxXML do not pass null
   * @param pxChildName do not pass null
   * @param pxOrDefault or this will get returned
   * @return never null if you don not pass any
   */
  static public final
  String ccGetChildContent(
    XML pxXML, String pxChildName, String pxOrDefault
  ){
    if(pxXML==null){return pxOrDefault;}
    if(!VcConst.ccIsValidString(pxChildName)){return pxOrDefault;}
    XML lpChild = pxXML.getChild(pxChildName);
    if(lpChild==null){
      VcConst.ccLogln(
        "McConst.ccGetChildContent $ failed finding child",
        pxChildName
      );
      return pxOrDefault;
    }//..?
    String lpContent=lpChild.getContent();
    if(lpContent==null){
      VcConst.ccLogln(
        "McConst.ccGetChildContent $ failed on XML::getContent with",
        pxChildName
      );
      return pxOrDefault;
    }//..?
    else{return lpContent;}
  }//+++
  
  //=== file io
  
  /**
   * alias for Class<?>::getResourceAsStream.<br>
   * @param pxProjectClass do not pass null
   * @param pxResourceIdentifier can be anything
   * @return could be null
   */
  public static final
  InputStream ccGetResourceStream(
    Class<?> pxProjectClass, String pxResourceIdentifier
  ){
    if(pxProjectClass==null){return null;}
    InputStream lpRes = pxProjectClass.getResourceAsStream(
      VcStringUtility.ccNulloutString(pxResourceIdentifier)
    );
    if(lpRes==null){
      VcConst.ccLogln
        (".ccGetResourceStream $ failed on ",pxResourceIdentifier);
      return null;
    }//..?
    int lpAvailable = -1;
    try {
      lpAvailable = lpRes.available();
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage());
      lpAvailable = -1;
    }//..?
    if(lpAvailable<=0){
      System.err.println(String.format(
        ".ccGetResourceStream $ @abort -rsc %s -length %d",
        lpRes.toString(), lpAvailable
      ));
      return null;
    }//..?
    return lpRes;
  }//+++
  
  /**
   * wrapping Scanner::hasNext() up and swallowing exceptions for you .<br>
   * looped result will get added to passed list.<br>
   * any one calling this utility is responsible for allocation.<br> 
   * be ware the passed list will get cleared before adding.<br>
   * @param pxFile checked via McConst.ccVerifyFileForLoading()
   * @param pxTarget do not pass null
   * @return line count if everything okay or step code
   */
  public static final
  int ccImportTextFile(File pxFile, List<String> pxTarget){
    
    int lpRes = 0;
    
    //-- checkin
    lpRes = McConst.ccVerifyFileForLoading(pxFile);
    if(lpRes<0){return -101;}
    if(pxTarget==null){return -102;}
    
    //-- scan
    Scanner lpScanner;
    try {
      lpScanner = new Scanner(pxFile, "utf-8");
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
      lpScanner=null;
    }//..?
    if(lpScanner==null){
      return VcConst.ccErrln(".ccImportTextFile $ got caught", -103);
    }//..?
    
    //-- loop
    pxTarget.clear();
    for(int i=0;i<65535;i++){//..arbitrary or constant
      if(!lpScanner.hasNext()){break;}
      pxTarget.add(lpScanner.next());
    }//+++
    
    //--
    return pxTarget.size();
  
  }//+++
  
  //[todo]::Table ccLoadCSVFile()
  
  /**
   * ##[to_fill]::
   * @param pxProjectClass ##
   * @param pxResourceIdentifier ##
   * @return ##
   */
  public static final
  Table ccLoadTableFromResource(
    Class<?> pxProjectClass, String pxResourceIdentifier
  ){
    
    //-- take in
    InputStream lpStream
      = ccGetResourceStream(pxProjectClass, pxResourceIdentifier);
    if(lpStream==null){
      System.err.println(".ccLoadTableFromResource $ abort 1.1");
      return null;
    }//..?
    
    //-- generate
    Table lpRes;
    try {
      lpRes = new Table(lpStream, "csv");
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage());
      lpRes=null;
    }//..?
    if(lpRes==null){
      System.err.println(".ccLoadTableFromResource $ abort 1.2");
      return null;
    }//..?
    
    //-- pack
    if(ZcPLC.and(
      lpRes.getRowCount()>1,
      lpRes.getColumnCount()>1
    )){lpRes.setColumnTitles(lpRes.getStringRow(0));}
    else{
      VcConst.ccLogln(".ccLoadTableFromResource $ goes on without headers");
    }//..?
    return lpRes;
    
  }//+++
  
  /**
   * ##[to_fill]::
   * @param pxProjectClass ##
   * @param pxResourceIdentifier ##
   * @return ##
   */
  public static final
  XML ccLoadXMLFromResource(
    Class<?> pxProjectClass, String pxResourceIdentifier
  ){
    
    //-- take in
    InputStream lpStream
      = ccGetResourceStream(pxProjectClass, pxResourceIdentifier);
    if(lpStream==null){
      System.err.println(".ccLoadXMLFromResource $ abort 1.1");
      return null;
    }//..?
    
    //-- generate
    XML lpRes;
    try{
      lpRes = new XML(lpStream);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      lpRes=null;
    }//..?
    
    //-- pack
    if(lpRes==null){
      VcConst.ccLogln(".ccLoadXMLFromResource $ goes on with failed loading");
    }//..?
    return lpRes;
    
  }//+++
  
  //[todo]:: StringList ccLoadTextFile(File){...
  
  //[todo]::List<HashMap> ccLoadINIFile(File)
  
  //=== output
  
  //[todo]::ccWriteINIFile(File, Object)
  
  //[todo]::ccWriteINIFIle(File, List<Object>)
  
  /**
   * the canonical class name of given instance will be the section name.<br>
   * iterates only for public primary integer members.<br>
   * for private or static inner classes, an access exception still 
   * has chance to occur.<br>
   * @param pxSource do not pass null
   * @return never be null but could be empty
   */
  public static final StringList ccPackupINIFormat(Object pxSource){
    StringList lpRes = new StringList();
    if(pxSource==null){return lpRes;}
    Class<?> lpClass = pxSource.getClass();
    lpRes.append(String.format("[%s]", pxSource.getClass().getCanonicalName()));
    Field[] lpDesField = lpClass.getFields();
    for(Field it:lpDesField){
      Class<?> lpType = it.getType();
      if(!(lpType.equals(int.class))){continue;}
      String lpName = it.getName();
      Object lpValue = VcConst.ccRetrieveField(it, pxSource);
      if(lpValue==null){
        System.err.println("kosui.pppmodel.McConst.ccPackupInitFormat()::"
          + "some access exception might occurred with:"
          + pxSource.toString());
        break;
      }//..~
      int lpContent=VcNumericUtility.ccInteger(lpValue);
      lpRes.append(String.format("%s=%d", lpName,lpContent));
    }//..~
    return lpRes;
  }//+++
  
  //=== 
  
  //=== util
  
  /**
   * ##
   * @param pxFolder direct to McConst.ccVerifyFolder
   * @param pxName valid or underscore
   * @param pxExtention need no dot
   * @param pxStampMode direct to VcStampUtility.ccFileNameTypeFor
   * @return new from string constructor or null for invalid folder
   */
  public static final
  File ccPackFileForExport(
    File pxFolder, String pxName, String pxExtention, int pxStampMode
  ){
    boolean lpFolderOK = McConst.ccVerifyFolder(pxFolder)==0;
    if(!lpFolderOK){return null;}
    String lpFileName=VcConst.ccIsValidString(pxName)?pxName:"_";
    StringBuilder lpRes=new StringBuilder(pxFolder.getAbsolutePath());
    lpRes.append(VcConst.C_V_PATHSEP);
    lpRes.append(lpFileName);
    lpRes.append(VcStampUtility.ccFileNameTypeFor(pxStampMode));
    if(VcConst.ccIsValidString(pxExtention)){
      lpRes.append(".");
      lpRes.append(pxExtention);
    }//..?
    return new File(lpRes.toString());
  }//+++
  
  /**
   * ##
   * @param pxTarget no null
   * @param pxKey no null no empty
   * @param pxValue no null no empty
   */
  public static
  void ccRegisterValidAttribute(XML pxTarget, String pxKey, String pxValue){
    if(pxTarget==null){return;}
    if(!VcConst.ccIsValidString(pxKey)){return;}
    if(!VcConst.ccIsValidString(pxValue)){return;}
    pxTarget.setString(pxKey, pxValue);
  }//..?
  
  //=== test
  
  /**
   * @param pxFolder do note pass null
   * @deprecated test use only
   */
  @Deprecated public static final void tstReadupFolderContent(File pxFolder){
    if(McConst.ccVerifyFolder(pxFolder)<0){return;}
    for(File it:pxFolder.listFiles()){
      VcConst.ccPrintln("r-subf", it.getName());
    }//+++
  }//+++
  
}//***eof
