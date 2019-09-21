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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import processing.core.PApplet;
import processing.data.StringList;

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
  
  private VcConst(){}//..!
  
  //===
  
  /**
   * @param pxLine #
   * @return no null no empty
   */
  static public final boolean ccIsValidString(String pxLine){
    if(pxLine==null){return false;}
    else{return !pxLine.isEmpty();}
  }//+++
  
  /**
   * from oracle tutorial : whitespace character : [\t\n\x0B\f\r].<br>
   * @param pxLine #
   * @return no null no empty
   */
  static public final boolean ccIsAllNoneSpace(String pxLine){
    return VcStringUtility.ccNulloutString(pxLine)
      .matches("^\\s+$");
  }//+++
  
  /**
   * @param pxList #
   * @param pxMinimal size
   * @return no null no lesser
   */
  public static final boolean ccIsValidList(List pxList, int pxMinimal){
    if(pxList==null){return false;}
    return pxList.size()>=pxMinimal;
  }//+++
  
  /**
   * @param pxArray #
   * @return no null no empty
   */
  static public final boolean ccIsValidArray(Object[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  /**
   * @param pxArray #
   * @return no null no empty
   */
  static public final boolean ccIsValidArray(boolean[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  /**
   * @param pxArray #
   * @return no null no empty
   */
  static public final boolean ccIsValidArray(byte[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  /**
   * @param pxArray #
   * @return no null no empty
   */
  static public final boolean ccIsValidArray(int[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  /**
   * @param pxArray #
   * @return no null no empty
   */
  static public final boolean ccIsValidArray(float[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  //===
  
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
  
  //=== 
  
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
  
  //=== 
  
  /**
   * only a "[ERR]" tag with time stamp will be added through System.err.<br>
   * @param pxLine #
   */
  public static final void ccErrln(String pxLine){
    System.err.print(VcStampUtility.ccErrStampTypeI());
    ccPrintln(pxLine);
  }//..?
  
  /**
   * only a "[ERR]" tag with time stamp will be added through System.err.<br>
   * @param pxTag #
   * @param pxValue #
   */
  public static final void ccErrln(String pxTag, Object pxValue){
    System.err.print(VcStampUtility.ccErrStampTypeI());
    ccPrintln(pxTag,pxValue);
  }//..?
  
  //=== backward
  
  /**
   * for JRE 1.6 capability.<br>
   * @param <K> ##
   * @param <V> ##
   * @param pxMap null to default
   * @param pxKey null to default
   * @param pxDefault ##
   * @return default if contains not
   */
  public static final <K,V> V ccGetOrDefault(
    HashMap<K,V> pxMap,K pxKey, V pxDefault
  ){
    if(pxMap==null){return pxDefault;}
    if(pxKey==null){return pxDefault;}
    if(pxMap.containsKey(pxKey)){
      return pxMap.get(pxKey);
    }else{
      return pxDefault;
    }//..?
  }//+++
  
  //=== reflection
  
  /**
   * wrapping Filed.get and swallow exception for you.<br>
   * @param pxReflected do not pass null
   * @param pxOwner do not pass null
   * @return could be null
   */
  public static final Object ccRetrieveField(Field pxReflected, Object pxOwner){
    if(pxReflected==null){return null;}
    if(pxOwner==null){return null;}
    Object lpRes=null;
    try{
      lpRes = pxReflected.get(pxOwner);
    } catch(IllegalArgumentException iage){
      System.err.println("kosui.ppputil.VcConst.ccRetrieveField()"
        +iage.getMessage());
    } catch(IllegalAccessException iace){
      System.err.println("kosui.ppputil.VcConst.ccRetrieveField()"
        + iace.getMessage());
    }//..?
    return lpRes;
  }//+++
  
  //=== local
  
  /**
   * <pre>
   * wrapper for Runtime::exec and swallows lots of IOException.
   * pack up string results for you. 
   * for the package manner we choose to return a processing based data type.
   * CAUTION: may block caller thread via Runtime::waitFor
   * </pre>
   * @param pxCommand must have something
   * @param pxReadCount we have an upper bound for BufferedReader::readLine
   * @return never null
   */
  synchronized public static final
  StringList ccRuntimeExecute(String pxCommand, int pxReadCount){
    
    StringList lpRes=new StringList();
    lpRes.append("-> runtime execute ->");
    
    //-- checkin
    if(!ccIsValidString(pxCommand)){return lpRes;}
    int lpFixedCount=PApplet.constrain(pxReadCount, 1, 255);
    
    //-- execute
    lpRes.append("-> command:"+pxCommand);
    Runtime lpRuntime = Runtime.getRuntime();
    Process lpProcess = null;
    BufferedReader lpReader = null;
    try {
      lpProcess = lpRuntime.exec(pxCommand);
      lpReader = new BufferedReader(new InputStreamReader(lpProcess.getInputStream()));
    } catch (IOException e) {
      System.err.println(".ccRuntimeExecute()::"+e.getMessage());
    }//..?
    if (lpProcess == null) {return lpRes;}
    if (lpReader == null) {return lpRes;}
    
    //-- wait
    int lpReturn = 0;
    try {
      lpReturn = lpProcess.waitFor();
    } catch (InterruptedException e) {
      System.err.println(".ccRuntimeExecute()::"+e.getMessage());
    }//+++
    
    //-- read
    for (int i = 0; i <= lpFixedCount; i++) {
      String lpLine = null;
      try {
        lpLine = lpReader.readLine();
      } catch (IOException e) {
        System.err.println(".ccRuntimeExecute()::"+e.getMessage());
      }//..?
      if (lpLine != null) {
        lpRes.append(lpLine);
      } else {
        lpRes.append("-> exited with:"+PApplet.nf(lpReturn, 8));
        break;
      }//..?
    }//..~
    
    //-- post
    return lpRes;
    
  }//+++
  
}//***eof
