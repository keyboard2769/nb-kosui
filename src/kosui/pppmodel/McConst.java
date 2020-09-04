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
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import kosui.pppswingui.ScConst;
import processing.data.XML;
import processing.data.Table;
import processing.data.TableRow;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;
import kosui.ppputil.VcTranslator;
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
  
  //=== resource
  
  /**
   * alias for Class<?>::getResourceAsStream.<br>
   * @param pxProjectClass do not pass null
   * @param pxResourceIdentifier can be anything
   * @return could be null
   */
  public static final InputStream ccGetPackageResource(
    Class<?> pxProjectClass, String pxResourceIdentifier
  ){
    if(pxProjectClass==null){return null;}
    return pxProjectClass.getResourceAsStream
      (VcStringUtility.ccNulloutString(pxResourceIdentifier));
  }//+++
  
  //=== verify
  
  /**
   * with null check, absolution check, existence check, identical check.<br>
   * @param pxFolder #
   * @return #
   */
  public static final boolean ccVerifyFolder(File pxFolder){
    if(pxFolder==null){return false;}
    if(!pxFolder.isAbsolute()){return false;}
    if(!pxFolder.exists()){return false;}
    if(!pxFolder.isDirectory()){return false;}
    return true;
  }//+++
  
  /**
   * @param pxFolder will get verified via ccVerifyFolder(File)
   * @param pxDesFileName elements should be the full file name 
   * @return true if all matched
   */
  public static final
  boolean ccVerifyFolder(File pxFolder, String[] pxDesFileName){
    if(!McConst.ccVerifyFolder(pxFolder)){return false;}
    if(pxDesFileName==null){return false;}
    LinkedList<String> lpFolderList = new LinkedList<String>();
    for(File it:pxFolder.listFiles()){
      lpFolderList.add(it.getName());
    }//..~
    boolean lpRes=true;
    for(String it:pxDesFileName){
      lpRes&=lpFolderList.contains(it);
    }//..~
    return lpRes;
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
   * @return #
   */
  public static final boolean ccVerifyFileForSaving(File pxFile){
    if(pxFile==null){return false;}
    if(!pxFile.isAbsolute()){return false;}
    if(pxFile.exists()){
      return ScConst.ccYesOrNoBox(
        VcTranslator.tr(C_KEY_OVERWRITE_COMFIRMATION)
      );
    }else{
      return true;
    }//..?
  }//+++
  
  /**
   * the extension check is performed via String.endWith().<br>
   * @param pxFile will get verified with the none extension version
   * @param pxExtension must have something
   * @return true if matches
   */
  public static final
  boolean ccVerifyFileForSaving(File pxFile, String pxExtension){
    if(pxFile==null){return false;}
    if(!VcConst.ccIsValidString(pxExtension)){return false;}
    boolean lpJustFile=ccVerifyFileForSaving(pxFile);
    if(lpJustFile){
      return pxFile.getName().endsWith(pxExtension);
    }else{
      return false;
    }//..?
  }//+++
  
  /**
   * with null check, absolution check, existence check, identical check.<br>
   * @param pxFile #
   * @return #
   */
  public static final boolean ccVerifyFileForLoading(File pxFile){
    if(pxFile==null){return false;}
    if(!pxFile.isAbsolute()){return false;}
    if(!pxFile.isFile()){return false;}
    if(!pxFile.exists()){return false;}
    return true;
  }//+++
  
  /**
   * expanding ccVerifyFileForLoading(File).<br>
   * the extension check is performed via String.endWith().<br>
   * @param pxFile will get verified with the none extension version
   * @param pxExtension must have something whit no "dot" prefix
   * @return true if matches
   */
  public static final
  boolean ccVerifyFileForLoading(File pxFile, String pxExtension){
    if(pxFile==null){return false;}
    if(!VcConst.ccIsValidString(pxExtension)){return false;}
    boolean lpJustFile=ccVerifyFileForLoading(pxFile);
    if(lpJustFile){
      return pxFile.getName().endsWith("."+pxExtension);
    }else{
      return false;
    }//..?
  }//+++
  
  /**
   * expanding ccVerifyFileForLoading(File, String).<br>
   * length check is performed via File.length().<br>
   * @param pxFile will get verified with the none extension version
   * @param pxExtension must have something
   * @param pxMaxByteLength must be lesser than this
   * @return true if all matches
   */
  public static final
  boolean ccVerifyFileForLoading(
    File pxFile, String pxExtension, long pxMaxByteLength
  ){
    boolean lpJustFile=ccVerifyFileForLoading(pxFile,pxExtension);
    if(lpJustFile){
      return pxFile.length()<=pxMaxByteLength;
    }else{
      return false;
    }//..?
  }//+++
  
  /**
   * ##
   * @param pxCSV the source object to check
   * @param pxDesColumn those supposed must have content
   * @return true if everything match
   */
  public static final
  boolean ccVerifyCSVColumn(Table pxCSV, String[] pxDesColumn){
    
    //-- precheck
    if(pxCSV==null){return false;}
    if(pxDesColumn==null){return false;}
    if(pxDesColumn.length<=1){
      System.err.println("pppmain.McConst.ccValidateCSVColumn()::"
       + "single column form is forbidden.");
      return false;
    }//..?
    
    //-- additional check
    int lpColumnCount=pxCSV.getColumnCount();
    int lpRowCount=pxCSV.getRowCount();
    /* 4 */VcConst.ccLogln("column count", lpColumnCount);
    /* 4 */VcConst.ccLogln("row clunt", lpRowCount);
    if(lpColumnCount<=1 || lpRowCount==0){
      VcConst.ccLogln("csvc::too few row and column", 0);
      return false;
    }//..?
    
    //-- loop
    Set<String> lpTitleSet
      = new HashSet<String>(Arrays.asList(pxCSV.getColumnTitles()));
    boolean lpTester=true;
    /* 4 */for(String it:lpTitleSet){VcConst.ccLogln("csv-c", it);}//..~
    for(String it:pxDesColumn){
      /* 4 */VcConst.ccLogln("csvc-given", it);
      lpTester&=lpTitleSet.contains(it);
    }//..~
    return lpTester;
    
  }//+++
  
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
  String ccGetColumnValue(TableRow pxRow, String pxColumn, String pxOrDefault){
    if(pxRow==null){return pxOrDefault;}
    if(!VcConst.ccIsValidString(pxColumn)){return pxOrDefault;}
    String lpCell;
    try {
      lpCell = pxRow.getString(pxColumn);
    } catch (Exception e) {
      System.err.println("kosui.pppmodel.McConst.ccGetColumnValue()::"
        + e.getMessage());
      lpCell=null;
    }//..?
    if(lpCell==null){return pxOrDefault;}
    else{return lpCell;}
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
  String ccGetColumnValue(TableRow pxRow, int  pxColumn, String pxOrDefault){
    if(pxRow==null){return pxOrDefault;}
    if(pxColumn<0){return pxOrDefault;}
    String lpCell;
    try {
      lpCell = pxRow.getString(pxColumn);
    } catch (Exception e) {
      System.err.println("kosui.pppmodel.McConst.ccGetColumnValue()::"
        + e.getMessage());
      lpCell=null;
    }//..?
    if(lpCell==null){return pxOrDefault;}
    else{return lpCell;}
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
  String ccGetXMLChildContent(
    XML pxXML, String pxChildName, String pxOrDefault
  ){
    if(pxXML==null){return pxOrDefault;}
    if(!VcConst.ccIsValidString(pxChildName)){return pxOrDefault;}
    XML lpChild = pxXML.getChild(pxChildName);
    if(lpChild==null){return pxOrDefault;}
    String lpContent=lpChild.getContent();
    if(lpContent==null){return pxOrDefault;}
    else{return lpContent;}
  }//+++
  
  //=== input
  
  /**
   * wrapping Scanner::hasNext() up and swallowing exceptions for you .<br>
   * looped result will get added to passed list.<br>
   * any one calling this utility is responsible for allocation.<br> 
   * be ware the passed list will get cleared before adding.<br>
   * @param pxFile checked via McConst.ccVerifyFileForLoading()
   * @param pxTarget do not pass null
   * @return true if nothing went wrong
   */
  public static final
  boolean ccImportTextFile(File pxFile, List<String> pxTarget){
    
    //-- checkin
    if(!McConst.ccVerifyFileForLoading(pxFile)){return false;}
    if(pxTarget==null){return false;}
    
    //-- scan
    Scanner lpScanner;
    try {
      lpScanner = new Scanner(pxFile, "utf-8");
    } catch (FileNotFoundException e) {
      System.err.println("kosui.McConst.ccImportTextFile::"+e.getMessage());
      lpScanner=null;
    }//..?
    if(lpScanner==null){return false;}
    
    //-- loop
    pxTarget.clear();
    for(int i=0;i<65535;i++){//..arbitrary or constant
      if(!lpScanner.hasNext()){break;}
      pxTarget.add(lpScanner.next());
    }//+++
    return true;
  
  }//+++
  
  //[todo]::List<HashMap> ccReadINIFile(File)
  
  
  
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
  
  /**
   * @param pxFolder do note pass null
   * @deprecated test use only
   */
  @Deprecated public static final void tstReadupFolderContent(File pxFolder){
    if(!McConst.ccVerifyFolder(pxFolder)){return;}
    for(File it:pxFolder.listFiles()){
      VcConst.ccPrintln("r-subf", it.getName());
    }//+++
  }//+++
  
}//***eof
