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

/**
 * some time i cant tell the difference between numeric and conversion.<br>
 * anyway this does not make things more tedious.<br>
 */
public final class VcNumericUtility {
  
  private static VcNumericUtility self = null;
  public static VcNumericUtility ccGetInstance() {
    if(self==null){self=new VcNumericUtility();}
    return self;
  }//+++
  private VcNumericUtility(){}//..!
  
  //===
  
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
   * one "true" "yes" "on" and none zero value will be true
   * @param pxSource #
   * @return #
   */
  synchronized public static boolean ccParseBoolString(String pxSource){
    if(!VcConst.ccIsValidString(pxSource)){return false;}
    if(pxSource.equals("true") || pxSource.equals("TRUE")){return true;}
    if(pxSource.equals("yes") || pxSource.equals("YES")){return true;}
    if(pxSource.equals("on") || pxSource.equals("ON")){return true;}
    if(ccParseIntegerString(pxSource)==0){return false;}
    return false;
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
    boolean lpIsFloat=ccIsFloatString(pxSource);
    boolean lpIsInteger=ccIsIntegerString(pxSource);
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
    boolean lpIsFloat=ccIsFloatString(pxSource);
    boolean lpIsInteger=ccIsIntegerString(pxSource);
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
    }//..~
    
    return lpTester==lpMasked?lpTester:lpTester*2;
    
  }//+++
  
  /**
   * <pre>
   * since PApplet::nfc() may cause a NullPointerException some times, 
   *   this method wraps String.format() for habitual usage.
   * </pre>
   * @param pxValue #
   * @return String.format("%.2f",...)
   */
  public static String ccFormatPointTwoFloat(float pxValue){
    return String.format("%.2f", pxValue);
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
      }//..?
    }//..~
    return lpRes;
  }//+++
  
 }//***eof
