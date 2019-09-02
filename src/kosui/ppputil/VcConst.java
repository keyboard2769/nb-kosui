/*
 * Copyright (C) 2018 Key Parker from K.I.C
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

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * an utility class should never use such a nonsense name.<br>
 * i don't know from where i got this idea.<br>
 */
public final class VcConst {
  
  /**
   * a property by file.separator
   */
  public static final String C_V_PATHSEP
    = System.getProperty("file.separator");
  
  /**
   * a property by line.separator
   */
  public static final String   C_V_NEWLINE
    = System.getProperty("line.separator");
  
  /**
   * a property by os.name
   */
  public static final String   C_V_OS
    = System.getProperty("os.name");
  
  /**
   * a property by user.dir
   */
  public static final String C_V_PWD
    = System.getProperty("user.dir");
  
  private static final boolean C_DOSE_LOG = false;
  
  private VcConst(){}//+++ 
  
  //===
  
  /**
   * ##
   * @param pxLine #
   * @return #
   */
  static public final boolean ccIsValidString(String pxLine){
    if(pxLine==null){return false;}
    else{return !pxLine.isEmpty();}
  }//+++
  
  /**
   * masked out for ccPrintln()<br>
   * @param pxLine must have some thing
   */
  static public final void ccLogln(String pxLine){
    if(C_DOSE_LOG){ccPrintln(pxLine);}
  }//+++
  
  /**
   * masked out for ccPrintln()<br>
   * @param pxTag must have something
   * @param pxValue can be anything
   */
  static public final void ccLogln(String pxTag, Object pxValue){
    if(C_DOSE_LOG){ccPrintln(pxTag, pxValue);}
  }//+++
  
  /**
   * alias to ccPrintln(String, null)<br>
   * @param pxLine must have some thing
   */
  public static final void ccPrintln(String pxLine){
    ccPrintln(pxLine, null);
  }//+++
  
  /**
   * @param pxTag must have something
   * @param pxValue can be anything
   */
  public static final void ccPrintln(String pxTag, Object pxValue){
    if(!ccIsValidString(pxTag)){return;}
    System.out.print(pxTag);
    if(pxValue==null){
      System.out.println("");
    }else{
      System.out.print(":");
      System.out.println(pxValue.toString());
    }//..?
  }//+++
  
  /**
   * a wrapper to Files.readAllBytes() and will convert it to string
   * @param pxURL supposedly should get from class.getResource()
   * @return a custom tag if error occurred
   */
  static public final String ccLoadFromURL(URL pxURL){
    //[TODO]::should this be part of McFile??
    String lpRes=null;
    byte[] lpByteBuf=null;
    if(pxURL!=null){
      try{
        lpByteBuf=Files.readAllBytes(Paths.get(pxURL.toURI()));
      }catch(Exception e){
        System.err.println("kosui.ppputil.VcConst.ccLoadFromURL()::"
          + e.getLocalizedMessage());
      }//..?
    }//..?
    if(lpByteBuf!=null){
      lpRes=new String(lpByteBuf);
    }//..?
    return lpRes==null?"<loadFailed/>":lpRes;
  }//+++
  
  //=== misc
  
  //<editor-fold defaultstate="collapsed" desc="ignore_code">
  /*[IGNORE]::*/
  //</editor-fold>
  
}//***eof
