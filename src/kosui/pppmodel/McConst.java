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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import kosui.pppswingui.ScConst;
import processing.data.XML;
import processing.data.Table;
import processing.data.TableRow;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStampUtility;
import kosui.ppputil.VcStringUtility;
import kosui.ppputil.VcTranslator;
import processing.data.JSONObject;
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
  
  //=== verify ** file
  
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
      if(ScConst.ccIsEDT()){
        return ScConst.ccYesOrNoBox(
          VcTranslator.tr(C_KEY_OVERWRITE_COMFIRMATION)
        )?0:-104;
      }else{
        return -103;
      }//..?
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
  
  //=== verify ** table
  
  /**
   * must contains every given list member.<br>
   * @param pxTable the source object to check
   * @param pxLesColumn those supposed must have content
   * @return 0 if everything okay or step code
   */
  public static final
  int ccVerifyTableColumn(Table pxTable, Set<String> pxLesColumn){
    
    //-- check in
    if(pxTable==null){return -101;}
    if(pxLesColumn==null){return -102;}
    if(pxLesColumn.size()<=1){return -103;}
    
    //-- additional check
    int lpColumnCount=pxTable.getColumnCount();
    int lpRowCount=pxTable.getRowCount();
    if(lpColumnCount<=1 || lpRowCount==0){return -104;}//..?
    /* 4 */VcConst.ccLogln(String.format(
      ".ccVerifyTableColumn $ got table with [ %d x %d] for : %s",
      lpColumnCount, lpRowCount, Arrays.toString(pxLesColumn.toArray())
    ));
    
    //-- loop
    Set<String> lpTitleSet
      = new HashSet<String>(Arrays.asList(pxTable.getColumnTitles()));
    boolean lpProb=true;
    for(String it:pxLesColumn){
      lpProb&=lpTitleSet.contains(it);
      if(!lpProb){
        VcConst.ccLogln(".ccVerifyTableColumn $ failed at",it);
        break;
      }//..?
    }//..~
    
    //-- report
    return lpProb?0:-109;
    
  }//+++
  
  /**
   * must contains every given list member.<br>
   * @param pxTable the source object to check
   * @param pxLesColumn those supposed must have content
   * @return 0 if everything okay or step code
   */
  public static final
  int ccVerifyTableColumn(Table pxTable, String[] pxLesColumn){
    if(pxLesColumn==null){return -102;}
    if(pxLesColumn.length<=0){return -103;}
    return ccVerifyTableColumn(pxTable, Collections
      .unmodifiableSet(new TreeSet<String>(Arrays.asList(pxLesColumn))));
  }//+++
  
  /**
   * ##
   * @param pxTable ##
   * @param pxRowMin inclusive
   * @param pxColMin inclusive
   * @return actual row count times actual column count or minus one
   */
  public static final
  int ccVerifyTableSize(Table pxTable, int pxRowMin, int pxColMin){
    if(pxTable==null){return -1;}
    if(pxTable.getRowCount()<pxRowMin){return -1;}
    if(pxTable.getColumnCount()<pxColMin){return -1;}
    return pxTable.getRowCount()*pxTable.getColumnCount();
  }//+++
  
  //=== data process ** table
  
  /**
   * <pre>
   * a safer alternative to just calling TableRow::getString.
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
   * a safer alternative to just calling TableRow::getString.
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
   * a safer alternative to just calling `Table::getString(row,col)`.
   * swallow exception and nothingness for you.
   * </pre>
   * @param pxTable ##
   * @param pxRow ##
   * @param pxColumn ##
   * @param pxOrDefault ##
   * @return never null if you don not pass any
   */
  static public final
  String ccGetValue(Table pxTable, int pxRow, int pxColumn, String pxOrDefault){
    if(ccVerifyTableSize(pxTable, pxRow, pxColumn)<0){return pxOrDefault;}
    String lpRes;
    try {
      lpRes = pxTable.getString(pxRow, pxColumn);
    } catch (Exception e) {
      VcConst.ccErrln("McConst.ccGetValue(Table)",e.getMessage());
      lpRes=null;
    }//..?
    if(lpRes==null){
       return pxOrDefault;
    }else{
      return lpRes;
    }//..?
  }//+++
  
  /**
   * matches column title add retrieves invalid value for you. 
   * @param pxTable must have column header longer than one
   * @param pxRow no null
   * @return column count
   */
  public static final int ccAddTableRow(Table pxTable, TableRow pxRow){
    if(pxTable==null){return -101;}
    if(pxRow==null){return -102;}
    String[] lpDesCoulmn = pxTable.getColumnTitles();
    if(lpDesCoulmn==null){return -103;}
    if(lpDesCoulmn.length==0){return -104;}
    int lpRes = 0;
    TableRow lpNewRow = pxTable.addRow();
    for(String it : lpDesCoulmn){
      lpNewRow.setString(it, McConst.ccGetValue(pxRow, it, ""));
      lpRes++;
    }//..~
    return lpRes;
  }//+++
  
  //=== data process ** xml
  
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
  
  /**
   * ##
   * @param pxTarget no null
   * @param pxKey no null no empty
   * @param pxValue no null no empty
   */
  public static
  void ccAddAttribute(XML pxTarget, String pxKey, String pxValue){
    if(pxTarget==null){return;}
    if(!VcConst.ccIsValidString(pxKey)){return;}
    if(!VcConst.ccIsValidString(pxValue)){return;}
    pxTarget.setString(pxKey, pxValue);
  }//..?
  
  //=== data process ** json
  
  //[plan]:: ??? ccToJSON???
  
  /**
   * replace simple format to full format 
   *   than pass to JSONObject.parse and swallow runtime exception for you.
   * @param pxInput in the form of `[k1:v1;k2:v2;k3:v3;]`
   * @return could be null
   */
  public static final
  JSONObject ccParseSimpleMap(String pxInput){
    if(!VcConst.ccIsValidString(pxInput)){return null;}
    String lpBuf = pxInput.replaceAll(";]", "]");
    lpBuf = lpBuf.replaceAll("\\[", "{ \"");
    lpBuf = lpBuf.replaceAll("\\]", "\" }");
    lpBuf = lpBuf.replaceAll(":", "\":\"");
    lpBuf = lpBuf.replaceAll(";", "\",\"");
    VcConst.ccLogln("McConst.ccParseSimpleMap $ before", pxInput);
    VcConst.ccLogln("McConst.ccParseSimpleMap $ after", pxInput);
    JSONObject lpRes;
    try {
      lpRes = JSONObject.parse(lpBuf);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      lpRes=null;
    }//..?
    return lpRes;
  }//+++
  
  /**
   * <pre>
   * a safer alternative to just calling JSONObject::getString.
   * swallow exception and nothingness for you.
   * </pre>
   * @param pxJSON ##
   * @param pxKey ##
   * @param pxOrDefault ##
   * @return ##
   */
  static public final
  String ccGetValue(JSONObject pxJSON, String pxKey, String pxOrDefault){
    if(pxJSON == null){return pxOrDefault;}
    if(!VcConst.ccIsValidString(pxKey)){return pxOrDefault;}
    String lpRes;
    try {
      lpRes = pxJSON.getString(pxKey);
    } catch (Exception e) {
      VcConst.ccErrln(e.getMessage(), pxKey);
      lpRes = pxOrDefault;
    }//..?
    return lpRes;
  }//+++
  
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
   * alias to VcStringUtility.ccExtractFileName
   * @param pxFile no null
   * @return never null
   */
  public static final String ccExtractFileName(File pxFile){
    if(pxFile==null){return "";}
    return VcStringUtility
      .ccNulloutString(VcStringUtility
        .ccExtractFileName(pxFile.getName()));
  }//+++
  
  /**
   * alias to VcStringUtility.ccExtractFileExtension
   * @param pxFile no null
   * @return never null
   */
  public static final String ccExtractFileExtension(File pxFile){
    if(pxFile==null){return "";}
    return VcStringUtility
      .ccNulloutString(VcStringUtility
        .ccExtractFileExtension(pxFile.getName()));
  }//+++
  
  /**
   * alias to Desktop.getDesktop::open and swallow exception for you
   * @param pxFolder will get verified by ccOpenFolderFromDesktop
   */
  public static final void ccOpenFolderFromDesktop(File pxFolder){
    if(McConst.ccVerifyFolder(pxFolder)<0){return;}
    try {
      Desktop.getDesktop().open(pxFolder);
    } catch (IOException ioe){
      VcConst.ccErrln("McConst.ccOpenFolderFromDesktop",ioe.getMessage());
    }//..?
  }//+++
  
  //=== test
  
  /**
   * ##[dep]::##[todo]::break this down to McStringMap and ?.Pack <br>
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
