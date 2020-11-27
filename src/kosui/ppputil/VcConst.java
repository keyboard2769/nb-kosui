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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
  
  //===
  
  private static final RuntimeException E_GENERAL_GET
    = new RuntimeException("general_exception_of_get_mehod");
  
  private static boolean
    cmLogOn = false,
    cmErrOn = true
  ;//...
  
  //=== 
  
  private VcConst(){}//++!
  
  //=== assert
  
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
      .matches("^\\S+$");
  }//+++
  
  /**
   * @param pxList #
   * @param pxMinimal size or less than one for emptiness
   * @return no null no lesser
   */
  public static final boolean ccIsValidList(List pxList, int pxMinimal){
    if(pxList==null){return false;}
    if(pxMinimal<=1){return !pxList.isEmpty();}
    return pxList.size()>=pxMinimal;
  }//+++
  
  /**
   * alias for Object::equals to bypass null check
   * @param pxSubjective ##
   * @param pxObjective ##
   * @return ##
   */
  public static final
  boolean ccEquals(Object pxSubjective, Object pxObjective){
    if(pxSubjective==null){return pxObjective==null;}
    return pxSubjective.equals(pxObjective);
  }//+++
  
  //=== logger ** config
  
  /**
   * only affects on ::ccLogxx
   * @param pxVal ##
   */
  public static final void ccSetDoseLog(boolean pxVal){
    cmLogOn=pxVal;
  }//+++
  
  /**
   * only affects on ::ccErrxx
   * @param pxVal ##
   */
  public static final void ccSetSilentError(boolean pxVal){
    cmErrOn=!pxVal;
  }//+++
  
  //=== logger ** log
  
  /**
   * masked out for ccPrintln()<br>
   * @param pxLine must have some thing
   */
  static public final void ccLogln(String pxLine){
    ccLogln(pxLine, null);
  }//+++
  
  /**
   * masked out for ccPrintln()<br>
   * @param pxTag must have something
   * @param pxValue can be anything
   */
  static public final void ccLogln(String pxTag, Object pxValue){
    if(cmLogOn){
      ccPrintln(pxTag, pxValue);
    }//..?
  }//+++
  
  /**
   * in the form of `blah blah blah : {step_code := problem_code}`
   * @param pxTag ##
   * @param pxCodeT ##
   * @param pxCodeP ##
   */
  static public final void ccLogln(String pxTag, int pxCodeT, int pxCodeP){
    if(cmLogOn){
      ccPrintln(pxTag, String.format("{%d < %d}", pxCodeT, pxCodeP));
    }//..?
  }//+++
  
  /**
   * just iterate throw given list
   * @param pxTag direct to VcConst.ccPrintln
   * @param pxList null or emptiness to report
   */
  static public final void ccLogls(String pxTag, List pxList){
    if(cmLogOn){return;}//..?
    ccPrintln(pxTag);
    ccPrintls(pxList);
  }//+++
  
  /**
   * ##
   * @param pxTag ##
   * @param pxMap ##
   */
  static public final void ccLogmp(String pxTag, Map pxMap){
    if(cmLogOn){return;}//..?
    ccPrintln(pxTag);
    ccPrintmp(pxMap);
  }//+++
  
  //=== logger ** error
  
  /**
   * ##[to_fill]::
   * @param pxLine ##
   */
  public static void ccErrln(String pxLine){
    if(cmErrOn){
      System.err.println(pxLine);
    }//..?
  }//+++
  
  /**
   * ##[to_fill]::
   * @param pxLine ##
   * @param pxCodePrev ##
   */
  public static void ccErrln(String pxLine, Object pxCodePrev){
    if(cmErrOn){
      System.err.println(pxLine+":"+pxCodePrev.toString());
    }//..?
  }//+++
  
  /**
   * ##[to_fill]::
   * @param pxLine direct to System.err.println without check
   * @param pxCode direct return
   * @return direct return
   */
  public static final int ccErrln(String pxLine, int pxCode){
    if(cmErrOn){
      System.err.println(pxLine+Integer.toString(pxCode));
    }//..?
    return pxCode;
  }//+++
  
  /**
   * ##[to_fill]::
   * @param pxLine ##
   * @param pxCode ##
   * @param pxCodePrev ##
   * @return ##
   */
  public static final int ccErrln(String pxLine, int pxCode, Object pxCodePrev){
    if(cmErrOn){
      System.err.println(String.format(
        "%s{%d < %s}",
        pxLine, pxCode, pxCodePrev.toString()
      ));
    }//..?
    return pxCode;
  }//+++
  
  //=== logger ** print
  
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
      System.out.println("/");
    }else{
      System.out.print(":");
      System.out.println(pxValue.toString());
    }//..?
  }//+++
  
  /**
   * ##
   * @param pxList ##
   */
  public static final void ccPrintls(List pxList){
    if(pxList==null){System.out.println("<nulllist>");return;}
    if(pxList.isEmpty()){System.out.println("<emptylist>");return;}
    for(Object it : pxList){
      System.out.println(it.toString());
    }//..~
  }//+++
  
  /**
   * ##
   * @param pxMap ##
   */
  public static final void ccPrintmp(Map pxMap){
    if(pxMap==null){System.out.println("<nullmap>");return;}
    if(pxMap.isEmpty()){System.out.println("<emptymap>");return;}
    for(Object it: pxMap.keySet()){
      Object lpVal = pxMap.get(it);
      String lpRepresent = (lpVal==null)?"":lpVal.toString();
      System.out.println(String.format(
        "[%s:%s;]",it.toString(),lpRepresent
      ));
    }//..~
  }//+++
  
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
  
  /**
   * alias for Field.setInt and swallow exception for you.<br>
   * @param pxInstance do not pass null
   * @param pxFieldName do not pass null
   * @param pxVal can be any thing
   */
  public static final
  void ccSetIntegerFieldValue(Object pxInstance, String pxFieldName, int pxVal){
    if(pxInstance==null){return;}
    if(!ccIsValidString(pxFieldName)){return;}
    try {
      Field lpField = pxInstance.getClass().getField(pxFieldName);
      lpField.setInt(pxInstance, pxVal);
    } catch (Exception e) {
      System.err.println(".ccSetIntegerFieldValue()$failed_with:"
        + e.getMessage());
    }//..?
  }//+++
  
  /**
   * alias for Field.getInt and exchange exception for you.<br>
   * @param pxInstance do not pass null
   * @param pxFieldName do not pass null
   * @return can be anything
   * @throws RuntimeException the hard coded one
   */
  public static final
  int ccGetIntegerFieldValue(Object pxInstance, String pxFieldName)
  throws RuntimeException
  {
    if(pxInstance==null){throw E_GENERAL_GET;}
    if(!ccIsValidString(pxFieldName)){throw E_GENERAL_GET;}
    int lpRes=0;
    try {
      Field lpFiled = pxInstance.getClass().getField(pxFieldName);
      lpRes=lpFiled.getInt(pxInstance);
    }catch(Exception e){
      System.err.println(".ccGetIntegerFieldValue()$failed_with:"
        + e.getMessage());
      throw E_GENERAL_GET;
    }//..?
    return lpRes;
  }//+++
  
  /**
   * iterates Class.getDeclaredFields and extract their name and pack up.<br>
   * @param pxClass serve as source
   * @return could be null
   */
  public static final
  List<String> ccListAllIntegerFieldName(Class<?> pxClass){
    return ccListAllFieldName(pxClass, int.class);
  }//+++
  
  /**
   * iterates Class.getDeclaredFields and extract their name and pack up.<br>
   * @param pxClass serve as source
   * @return could be null
   */
  public static final
  List<String> ccListAllBooleanFieldName(Class<?> pxClass){
    return ccListAllFieldName(pxClass, boolean.class);
  }//+++
  
  /**
   * iterates Class.getDeclaredFields and extract their name and pack up.<br>
   * @param pxClass serve as source
   * @param pxFieldType serve as filter
   * @return could be null
   */
  public static final
  List<String> ccListAllFieldName(Class<?> pxClass, Class<?>pxFieldType){
    
    //-- check in
    if(pxClass==null){return null;}
    
    //-- retrieve
    Field[] lpDesField = pxClass.getDeclaredFields();
    if(lpDesField==null){return null;}
    if(lpDesField.length==0){return null;}
    
    //-- filtering
    List<String> lpRes=new LinkedList<String>();
    for(Field it:lpDesField){
      if(it==null){continue;}
      if(pxFieldType==null){continue;}
      if(it.getType().equals(pxFieldType)){
        lpRes.add(it.getName());
      }//+++
    }//..~
    
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
  
  //=== netbeans only
  
  /*[not_sure]::[netbeans_only]:: flags :
    ${cursor}
    ${className currClassFQName editable="false"}
    ${methodName currMethodName editable="false"}
  */
  
  /*[not_sure]::[netbeans_only]:: cclog $ vcc.log(... */
  
  /*[not_sure]::[netbeans_only]:: ccerr $ vcc.err(... */
  
  /*[not_sure]::[netbeans_only]:: sys $ sys.err.println(... */
  
  /*[not_sure]::[netbeans_only]:: tw $ throw e(notyet... */
  
  /*[not_sure]::[netbeans_only]:: iff $ if(x.eq(... */
  
  /*[not_sure]::[netbeans_only]:: lpabs $ final String lpAbs = ".$... */
  
  /*[not_sure]::[netbeans_only]:: lpres $ lpRes = cc(... */
  
  /*[not_sure]::[netbeans_only]:: unmodlist $ Col....unmodiableList(... */
  
  /*[not_sure]::[netbeans_only]:: unmodset $ Col....unmodiableSet(... */
  
  /*[not_sure]::[netbeans_only]:: unmodmap $ Col....unmodiableMap(... */
  
}//***eof
