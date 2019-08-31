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

import static processing.core.PApplet.nf;
import static processing.core.PApplet.hex;
import static processing.core.PApplet.hour;
import static processing.core.PApplet.minute;
import static processing.core.PApplet.second;
import static processing.core.PApplet.year;
import static processing.core.PApplet.month;
import static processing.core.PApplet.day;
import static processing.core.PApplet.nfc;

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
  
  private static char
    cmTimeSepD='/',
    cmTimeSepT=':'
  ;//...
  
  private VcConst(){}//+++ 
  
  //===
  
  //=== printing
  
  //=== printing ** singel
  
  /**
   * alias to System.err.println()
   * @param pxVal #
   */
  static public final void ccLogerr(Object pxVal)
    {System.err.println("kosui.::"+pxVal);}//+++
  
  /**
   * alias to print single string
   * @param pxLine : ##
   */
  static public final void ccLogln(String pxLine)
    {System.out.println("kosui.::"+pxLine);}//+++
  
  /**
   * alias to print a specific value
   * @param pxTag : ##
   * @param pxVal : ##
   */
  static public final void ccLogln(String pxTag, Object pxVal)
    {System.out.println("kosui.::"+pxTag+":"+pxVal.toString());}//+++
  
  /**
   * alias to print a array
   * @param pxTitle : you might want add a ":" at end
   * @param pxArray : array reference
   */
  static public final void ccLogln(String pxTitle,Object[] pxArray){
    VcConst.ccLogln(pxTitle+":");
    for(Object it:pxArray)
      {System.out.println(pxTitle+it.toString());}
  }//+++
  
  /**
   * alias System.out.println()
   * @param pxVal #
   */
  static public final void ccLogln(Object pxVal)
    {System.out.println("kosui.::"+pxVal.toString());}//+++
  
  //=== printing ** array
  
  //[PLAN]::static void ccPrintBoolArray(boolean[] pxData){}
  
  //[PLAN]::static void ccPrintByteArray(byte[] pxData){}
  
  /**
   * title hard coded
   * @param pxAray #
   */
  static public final void ccPrintByteArray(byte[] pxAray){
    System.out.print("kosui.::byteArray:");
    for(int i=0,s=pxAray.length;i<s;i++){
      if(i%4==0){System.out.println("  >");}
      System.out.print(nf(i,4)+"["+hex(pxAray[i],2)+"] ");
    } System.out.println("kosui.printByteArray::Over!");
  }//+++
  
  //[PLAN]::static void ccPrintIntArray(int[] pxData){}
  
  //[PLAN]::static void ccPrintFloatArray(float[] pxData){}
  
  //=== judgement
  
  /**
   * if you think REGEX is heavy you should NOT use this.
   * @param pxNum #
   * @return ture for zero suppressed 10 digit decimal number
   */
  static public final boolean ccIsIntegerString(String pxNum){
    return pxNum.matches("^[+-]?(([1-9][0-9]{0,9})|(0))$");
  }//+++
  
  /**
   * if you think REGEX is heavy you should NOT use this.
   * @param pxNum #
   * @return # ture for zero suppressed 10 digit decimal number
   * with two digit under point
   */
  static public final boolean ccIsFloatString(String pxNum){
    return pxNum.matches("^[+-]?(([1-9][0-9]{0,9})|(0))([.][0-9]{1,2})?$");
  }//+++
  
  /**
   * ##
   * @param pxLine #
   * @return #
   */
  static public final boolean ccIsValidString(String pxLine)
    {if(pxLine==null){return false;}else{return !pxLine.isEmpty();}}//+++
  
  //=== util
  
  /**
   * a wrapper to Files.readAllBytes() and will convert it to string
   * @param pxURL supposedly should get from class.getResource()
   * @return a custom tag if error occurred
   */
  static public final String ccLoadFromURL(URL pxURL){
    String lpRes=null;
    byte[] lpByteBuf=null;
    if(pxURL!=null){
      try{
        lpByteBuf=Files.readAllBytes(Paths.get(pxURL.toURI()));
      }catch(Exception e){ccLogerr(e.toString());}
    }
    if(lpByteBuf!=null){
      lpRes=new String(lpByteBuf);
    }
    return lpRes==null?"<loadFailed/>":lpRes;
  }//+++
  
  /**
   * 
   * @param pxForData #
   * @param pxForTime #
   */
  static public final void ccSetupTimeStampSeparator(
    char pxForData, char pxForTime
  ){
    cmTimeSepD=pxForData;
    cmTimeSepT=pxForTime;
  }//+++

  /**
   * (/) for date and (:) for time
   */
  static public final void ccDefaultTimeStampSeparator(){
    cmTimeSepD='/';
    cmTimeSepT=':';
  }//+++
  
  /**
   * a space character will be added to the end any way
   * @param pxTitle prefix, does NOT check for null
   * @param pxHasDate formed as %yyyy/mm/dd%
   * @param pxHasTime formed as %::hh:mm%
   * @param pxHasSecond formed as %:ss%
   * @return #
   */
  static public final String ccTimeStamp(
    String pxTitle,
    boolean pxHasDate, boolean pxHasTime, boolean pxHasSecond
  ){
    StringBuilder lpRes=new StringBuilder(pxTitle);
    if(pxHasDate){
      lpRes.append(Integer.toString(year()));
      lpRes.append(cmTimeSepD);
      lpRes.append(Integer.toString(month()));
      lpRes.append(cmTimeSepD);
      lpRes.append(Integer.toString(day()));
    }//..?
    if(pxHasTime){
      lpRes.append(cmTimeSepT);
      lpRes.append(nf(hour(),2));
      lpRes.append(cmTimeSepT);
      lpRes.append(nf(minute(),2));
    }
    if(pxHasTime&&pxHasSecond){
      lpRes.append(cmTimeSepT);
      lpRes.append(nf(second(),2));
    }//..?
    return lpRes.toString();
  }//+++
  
  /**
   * [2-&gt;2][3-&gt;4][4-&gt;4][5-&gt;8][6-&gt;8][7-&gt;8][9-&gt;16]...
   * @param pxSource #
   * @return fixed value
   */
  static public final int ccToPowerOfTwo(int pxSource){
    int lpMasked=pxSource&0xFFFF;
    int lpTester=0x00008000;
    while(lpTester!=1){
      if( (lpTester&lpMasked)!=0 ){break;}
      lpTester>>=1;
    }
    return lpTester==lpMasked?lpTester:lpTester*2;
  }//+++
  
  /**
   * a simple combination of multiplying and dividing and casting with 10
   * @param pxSource #
   * @return #
   */
  public static float ccRoundForOneAfter(float pxSource){
    return (float)(
      ((int)(pxSource*10f))
    )/10f;
  }//+++
  
  /**
   * a simple combination of multiplying and dividing and casting with 100
   * @param pxSource #
   * @return #
   */
  public static float ccRoundForTwoAfter(float pxSource){
    return (float)(
      ((int)(pxSource*100f))
    )/100f;
  }//+++
  
  /**
   * ##
   * @param pxLine #"0,0,0,0,0,"
   * @param pxSplit #","
   * @return #
   */
  synchronized static public int[] ccParseIntegerArrayString
    (String pxLine, String pxSplit)
  { String[] lpTokens=pxLine.split(pxSplit);
    int[] lpRes=new int[lpTokens.length];
    for(int i=0,s=lpRes.length;i<s;i++){
      if(ccIsIntegerString(lpTokens[i])){
        lpRes[i]=Integer.decode(lpTokens[i]);
      }else{
        lpRes[i]=0;
      }
    }
    return lpRes;
  }//+++
  
  /**
   * based on inner REGEX format checker.
   * any invalid format may cause no error or exception
   *   but a return of zero.
   * @param pxSource #
   * @return #
   */
  synchronized public static int ccParseIntegerString(String pxSource){

    //-- pre judge
    if(!VcConst.ccIsValidString(pxSource)){return 0;}

    //-- judge input ** pxSource
    boolean lpIsFloat=VcConst.ccIsFloatString(pxSource);
    boolean lpIsInteger=VcConst.ccIsIntegerString(pxSource);
    if(!lpIsFloat && !lpIsInteger){return 0;}

    //-- transform
    int lpRes=0;
    if(lpIsFloat){
      lpRes=(int)(Float.parseFloat(pxSource));
    }//..?
    if(lpIsInteger){
      lpRes=Integer.parseInt(pxSource);
    }//..?
    return lpRes;

  }//+++

  /**
   * based on inner REGEX format checker.
   * any invalid format may cause no error or exception
   *   but a return of zero.
   * @param pxSource #
   * @return #
   */
  synchronized public static float ccParseFloatString(String pxSource){

    //-- pre judge
    if(!VcConst.ccIsValidString(pxSource)){return 0.0f;}

    //-- judge input ** pxSource
    boolean lpIsFloat=VcConst.ccIsFloatString(pxSource);
    boolean lpIsInteger=VcConst.ccIsIntegerString(pxSource);
    if(!lpIsFloat && !lpIsInteger){return 0.0f;}

    //-- transform
    float lpRes=0.0f;
    if(lpIsFloat){
      lpRes=Float.parseFloat(pxSource);
    }//..?
    if(lpIsInteger){
      lpRes=(float)(Integer.parseInt(pxSource));
    }//..?
    return lpRes;

  }//+++
  
  /**
   * one "true" "yes" "on" and none zero value will be true
   * @param pxSource #
   * @return #
   */
  synchronized public static boolean ccParseBoolString(String pxSource){
    if(!ccIsValidString(pxSource)){return false;}
    if(pxSource.equals("true") || pxSource.equals("TRUE")){return true;}
    if(pxSource.equals("yes") || pxSource.equals("YES")){return true;}
    if(pxSource.equals("on") || pxSource.equals("ON")){return true;}
    if(ccParseIntegerString(pxSource)==0){return false;}
    return false;
  }//+++
  
  /**
   * actually i don't see why i needed this.
   * @param pxArray #
   * @return #
   */
  synchronized static public final String[] ccToStringArray(Object[] pxArray){
    String[] lpRes=new String[pxArray.length];
    for(int i=0,s=lpRes.length;i<s;i++)
      {lpRes[i]=pxArray[i].toString();}
    return lpRes;
  }//+++
  
  //=== string
  
  /**
   * for unknown reason PApplet::nfc() may cause a NullPointerException.
   * this wrapper simply swallows it and returns a "0.00" string
   *   via a try-catch block.
   * may cause overheads.
   * @param v #
   * @param d #
   * @return #
   */
  public static String cnfc(float v, int d){
    String r;
    try{r=nfc(v,d);}
    catch(Exception e){r="0.00";}
    return r;
  }//+++
  
  /**
   * insert new line with every given count. <br>
   * NOT synchronized. based on StringBuilder. <br>
   * @param pxSource #
   * @param pxCharCount #
   * @return new string with new line inserted
   */
  static public final String ccWrap(String pxSource, int pxCharCount){
    StringBuilder lpBuilder = new StringBuilder();
    for(int i=0,s=pxSource.length();i<s;i++){
      lpBuilder.append(pxSource.charAt(i));
      if(i%pxCharCount==0 && i!=0){lpBuilder.append('\n');}
    }
    return lpBuilder.toString();
  }//+++
  
  /**
   * cut the source from left
   * @param pxSource #
   * @param pxCharCount #
   * @return substring-ed string
   */
  static public final String ccLeft(String pxSource, int pxCharCount){
    if(!ccIsValidString(pxSource)){return pxSource;}
    if(pxCharCount<1 || pxCharCount>pxSource.length()){return pxSource;}
    return pxSource.substring(0, pxCharCount);
  }//+++
  
  /**
   * cut the source from right
   * @param pxSource #
   * @param pxCharCount #
   * @return substring-ed string
   */
  static public final String ccRight(String pxSource, int pxCharCount){
    if(!ccIsValidString(pxSource)){return pxSource;}
    int lpLength=pxSource.length();
    if(pxCharCount<1 || pxCharCount>lpLength){return pxSource;}
    return pxSource.substring(lpLength-pxCharCount, lpLength);
  }//+++
  
  //=== misc
  
  //<editor-fold defaultstate="collapsed" desc="ignore_code">
  /*[IGNORE]::*/
  //</editor-fold>
  
}//***eof
