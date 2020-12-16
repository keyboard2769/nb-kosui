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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import kosui.ppplogic.ZcPLC;
import kosui.ppputil.VcArrayUtility;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;
import processing.data.XML;
import processing.data.Table;

/**
 * they say a file is a model, they say an array is a model.<br>
 * and i know that some model is harder to reproduce than others.<br>
 */
public final class McFactory {
  
  //=== stream
  
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
  
  //=== file
  
  /**
   * loop thru given array with McConst.ccVerifyFileForLoading.
   * @param pxDesPotentialPath no null no empty
   * @return first verified one
   */
  public static final
  File ccPickFileForLoading(final String[] pxDesPotentialPath){
    if(!VcArrayUtility.ccIsValidArray(pxDesPotentialPath, 1)){return null;}
    File lpProb = null;
    int lpRes;
    for(String ofPath : pxDesPotentialPath){
      lpProb = new File(ofPath);
      lpRes = McConst.ccVerifyFileForLoading(lpProb);
      if(lpRes<0){
        lpProb=null;
      }else{
        break;
      }//..?
    }//..~
    return lpProb;
  }//+++
  
  //=== text
  
  /**
   * wrapping Scanner::hasNext() up and swallowing exceptions for you .<br>
   * looped result will get added to passed list.<br>
   * any one calling this utility is responsible for allocation.<br> 
   * be ware the passed list will get cleared before adding.<br>
   * @param pxFile checked via McConst.ccVerifyFileForLoading()
   * @return null if anything went wrong
   */
  public static final
  List<String> ccLoadTextFromFile(File pxFile){
    
    final String lpAbs = "McFactory.ccImportTextFromFile $ abort";
    final String lpRpt = "McFactory.ccImportTextFromFile $ Got Line of ";
    
    //-- checkin
    int lpVerifyRes = McConst.ccVerifyFileForLoading(pxFile);
    if(lpVerifyRes<0){
      VcConst.ccErrln(
        lpAbs,VcStringUtility.ccPackupErrorTraceBlock(-101, lpVerifyRes)
      );
      return null;
    }//..?
    
    //-- scan
    Scanner lpScanner;
    try {
      lpScanner = new Scanner(pxFile, "utf-8");
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
      lpScanner=null;
    }//..?
    if(lpScanner==null){
      VcConst.ccErrln(lpAbs,"mk102");
      return null;
    }//..?
    
    //-- loop
    List<String> lpRes = new LinkedList<String>();
    lpRes.clear();
    for(int i=0;i<65535;i++){//..arbitrary or constant
      if(!lpScanner.hasNext()){break;}
      lpRes.add(lpScanner.next());
    }//+++
    
    //-- pack
    VcConst.ccLogln(
      lpRpt,
      VcStringUtility.ccPackupPairedTag(pxFile.getName(), lpRes.size())
    );
    return lpRes;
  
  }//+++
  
  /**
   * a wrapper to Files.readAllBytes() and will convert it to string.<br>
   * @param pxURL supposedly should get from class.getResource()
   * @return could be null
   */
  static public final String ccLoadStringFromURL(URL pxURL){
    if(pxURL==null){return null;}
    byte[] lpByteBuf=null;
    try{
      lpByteBuf=Files.readAllBytes(Paths.get(pxURL.toURI()));
    }catch(Exception e){
      System.err.println("kosui.pppmodel.McFactory.ccLoadText()::"
        + e.getMessage());
      lpByteBuf=null;
    }//..?
    if(lpByteBuf!=null){
      return new String(lpByteBuf);
    }//..?
    return null;
  }//+++
  
  //[todo]::ccSaveTextToFile(File, List<String>){...
  
  //=== JSON
  
  //[plan]::JSONArray ccLoadJSONArrayFromFile(File)
  //[plan]::JSONObject ccLoadJSONObjectFromFile(File)
  //[plan]::int ccSaveJSONArrayToFile(File)
  //[plan]::int ccSaveJSONObjectToFile(File)
  
  //=== XML 
  
  //[think_again]::public static final XML ccCreaEmptyXML(String ...
  
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
  
  /**
   * swallow the exception for you.<br>
   * @param pxFile do not pass null
   * @return might be null if exception occurred
   */
  public static final
  XML ccLoadXML(File pxFile){
    if(pxFile==null){return null;}
    XML lpXML = null;
    try {
       lpXML = new XML(pxFile);
    } catch (Exception e) {
      System.err.println("kosui.pppmodel.McFactory.ccLoadXML()::"
        + e.getMessage());
      lpXML = null;
    }//..?
    if(lpXML == null){
      System.err.println("kosui.pppmodel.McFactory.ccLoadXML()::failed.");
    }//..?
    return lpXML;
  }//+++

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
    
    //-- pre
    String lpAbs = "McFactory.ccLoadTableFromResource $ abort";
    
    //-- take in
    InputStream lpStream
      = ccGetResourceStream(pxProjectClass, pxResourceIdentifier);
    if(lpStream==null){
      VcConst.ccErrln(lpAbs, "st101");
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
      VcConst.ccErrln(lpAbs, "st102");
      return null;
    }//..?
    
    //-- pack
    if(ZcPLC.and(
      lpRes.getRowCount()>1,
      lpRes.getColumnCount()>1
    )){lpRes.setColumnTitles(lpRes.getStringRow(0));}
    else{
      VcConst.ccLogln(
        "McFactory.ccLoadTableFromResource $ goes on without headers"
      );
    }//..?
    return lpRes;
    
  }//+++
  
  /**
   * just a alias for XML::save to swallow exception for you
   * @param pxXML no null
   * @param pxFile no null
   * @return zero if everything is okay or step code
   */
  public static final
  int ccSaveXMLToFile(XML pxXML, File pxFile){
    if(pxXML==null){return -101;}
    if(pxFile==null){return -102;}
    if(McConst.ccVerifyFileForSaving(pxFile, "xml")<0){return -103;}
    int lpRes=0;
    try {
      pxXML.save(pxFile, "");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      lpRes = -209;
    }//..?
    return lpRes;
  }//+++
  
  //=== CSV
  
  //[think_again]::public static final Table ccCreateEmptyTable(String[] ...
  
  /**
   * ##
   * @param pxODSFile ##
   * @param pxWorksheet ##
   * @return ##
   */
  public static final Table ccLoadTableFromODSFile(File pxODSFile, String pxWorksheet){
    final String lpAbs = "kosui.pppmodel.McFactory.ccLoadTableFromODSFile $ abort";
    int lpVerifyRes = McConst.ccVerifyFileForLoading(pxODSFile, "ods");
    if(lpVerifyRes<0){VcConst.ccErrln(lpAbs,"mk101");return null;}//..?
    String lpOption = VcConst.ccIsValidString(pxWorksheet)
      ?("ods,worksheet="+pxWorksheet)
      :"ods";
    Table lpRes = null;
    try {
      lpRes = new Table(pxODSFile, lpOption);
    } catch (IOException ioe){
      System.err.println(ioe.getMessage());
      lpRes=null;
    }
    if(lpRes==null){VcConst.ccErrln(lpAbs,"mk102");return null;}//..?
    lpRes.setColumnTitles(lpRes.getStringRow(0));
    return lpRes;
  }//+++

  /**
   * ##
   * @param pxCSVFile ##
   * @return ##
   */  
  public static final Table ccLoadTableFromCSVFile(File pxCSVFile){
    String lpAbs = "McFactory.ccLoadTableFromCSVFile $ abort";
    int lpVerifyRes = McConst.ccVerifyFileForLoading(pxCSVFile, "csv");
    if(lpVerifyRes<0){VcConst.ccErrln(lpAbs,"mk101");return null;}//..?
    Table lpRes;
    try {
      lpRes = new Table(pxCSVFile, "header");
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage());
      lpRes=null;
    }//..?
    if(lpRes==null){VcConst.ccErrln(lpAbs,"mk102");return null;}//..?
    return lpRes;
  }//+++
  
  /**
   * just swallow the exception for you
   * @param pxFile get passed to verification with `csv` extension hard coded 
   * @return with header option
   */
  public static final 
  Table ccLoadTableFromFile(File pxFile){
    String lpAbs = "McFactory.ccLoadTableFromFile $ abort";
    if(pxFile==null)
      {VcConst.ccErrln(lpAbs,"er101");return null;}//..?
    String lpName = pxFile.getName();
    if(!VcConst.ccIsValidString(lpName))
      {VcConst.ccErrln(lpAbs,"er102");return null;}//..?
    if(lpName.endsWith("csv")){return ccLoadTableFromCSVFile(pxFile);}
    if(lpName.endsWith("ods")){return ccLoadTableFromODSFile(pxFile,null);}
    VcConst.ccErrln(lpAbs,"er909");return null;
  }//+++
  
}//***eof
