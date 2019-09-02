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
import processing.data.XML;
import processing.data.Table;

/**
 * they say a file is a model, they say an array is a model.<br>
 * and i know that some model is harder to reproduce than others.<br>
 */
public final class McFactory {
  
  private McFactory(){}//+++
  
  //=== JSON
  
  //[plan]::ccToJSONArray(List<MiRecord>)
  //[plan]::ccToJSONObject(HashMap<String, MiRecord>)
  //[plan]::ccParseJSONArray()
  //[plan]::ccParseJSONObject()
  
  //=== XML 
  
  /**
   * swallow the exception for you.<br>
   * the root is named "root".<br>
   * @return might be null if exception occurred
   */
  public static final XML ccNewXML(){
    XML lpXML=null;
    try {
      lpXML = XML.parse("<root/>");
    } catch (Exception e) {
      System.out.println("kosui.pppmodel.McFactory.ccNewXML()::+"
        + e.getMessage());
      lpXML=null;
    }//..?
    return lpXML;
  }//+++
  
  /**
   * swallow the exception for you.<br>
   * @param pxFile do not pass null
   * @return might be null if exception occurred
   */
  public static final XML ccLoadXML(File pxFile){
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
  
  //=== CSV
  
  /**
   * loading with the "header" option.<br>
   * swallow the exception for you.<br>
   * @param pxFile do not pass null
   * @return might be null if exception occurred
   */
  public static final Table ccLoadCSV(File pxFile){
    if(pxFile==null){return null;}
    Table lpCSV=null;
    try {
      lpCSV = new Table(pxFile, "header");
    } catch (Exception e) {
      System.err.println("kosui.pppmodel.McFactory.ccLoadCSV()::"
        + e.getMessage());
      lpCSV=null;
    }//..?
    if(lpCSV==null){
      System.out.println("kosui.pppmodel.McFactory.ccLoadCSV()::failed.");
    }//..?
    return lpCSV;
  }//+++
  
}//***eof
