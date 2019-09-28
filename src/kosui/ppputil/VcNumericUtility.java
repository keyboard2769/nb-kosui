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

import java.util.Random;
import kosui.ppplogic.ZiMemory;
import processing.core.PApplet;

/**
 * some time i cant tell the difference between numeric and conversion.<br>
 * anyway this does not make things more tedious.<br>
 */
public final class VcNumericUtility {
  
  /**
   * just for conveniecy
   */
  public static final int  
    C_ONBIT_Z    = 0x00000001,
    C_ONBIT_I    = 0x00000002,
    C_ONBIT_II   = 0x00000004,
    C_ONBIT_III  = 0x00000008,
    //--
    C_ONBIT_IV   = 0x00000010,
    C_ONBIT_V    = 0x00000020,
    C_ONBIT_VI   = 0x00000040,
    C_ONBIT_VII  = 0x00000080,
    //--
    C_ONBIT_VIII = 0x00000100,
    C_ONBIT_IX   = 0x00000200,
    C_ONBIT_X    = 0x00000400,
    C_ONBIT_XI   = 0x00000800,
    //--
    C_ONBIT_XII  = 0x00001000,
    C_ONBIT_XIII = 0x00002000,
    C_ONBIT_XIV  = 0x00004000,
    C_ONBIT_XV   = 0x00008000
  ;//+++
  
  private VcNumericUtility(){}//..!
  
  //=== random
  
  private static final Random O_RANOM = new Random(
    VcStampUtility.ccSecond()*VcStampUtility.ccMinute()
  );
  
  /**
   * the initiated seed is the start second x minute.<br>
   * @return next float aka 0f-1f
   */
  public static float ccRandom(){
    return O_RANOM.nextFloat();
  };
  
  /**
   * @param pxBase ##
   * @param pxRange ##
   * @return range x [0f-1f] - base
   */
  public static float ccRandom(float pxBase, float pxRange){
    return pxRange*ccRandom()-pxBase;
  };
  
  /**
   * @param pxAbsolute ##
   * @return [-abs - +abs]
   */
  public static float ccRandom(float pxAbsolute){
    return ccRandom(pxAbsolute, pxAbsolute*2);
  };
  
  //=== judgement
  
  /**
   * if you think REGEX is heavy you should NOT use this.
   * @param pxNum #
   * @return ture for zero suppressed 10 digit decimal number
   */
  static public final boolean ccIsIntegerString(String pxNum){
    return VcStringUtility.ccNulloutString(pxNum)
      .matches("^[+-]?(([1-9][0-9]{0,9})|(0))$");
  }//+++
  
  /**
   * if you think REGEX is heavy you should NOT use this.
   * @param pxNum #
   * @return # ture for zero suppressed 10 digit decimal number
   * with two digit under point
   */
  static public final boolean ccIsFloatString(String pxNum){
    return VcStringUtility.ccNulloutString(pxNum)
      .matches("^[+-]?(([1-9][0-9]{0,9})|(0))([.][0-9]{1,2})?$");
  }//+++
  
  //=== conversion

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
  
  /**
   * <pre>
   * since PApplet::nfc() may cause a NullPointerException some times, 
   *   this method wraps String.format() for habitual usage.
   * </pre>
   * @param pxValue #
   * @return String.format("%.2f",...)
   */
  public static String ccFormatPointTwoFloat(float pxValue){
    return String.format("%.02f", pxValue);
  }//+++
  
  //=== comparating
  
  /**
   * @param pxA ##
   * @param pxB ##
   * @param pxC ##
   * @return if all equals
   */
  public static final boolean ccEquals(int pxA, int pxB, int pxC){
    boolean lpA=pxA==pxC;
    boolean lpB=pxB==pxC;
    return lpA&lpB;
  }//+++
  
  //=== calculation
  
  /**
   * interval of results might be equal.<br>
   * @param pxSource passing big value may cause overflow
   * @param pxSplit up to 63
   * @return never null?
   */  
  public final static float[] ccFineSplit(float pxSource, int pxSplit){
    if(pxSplit<=1){return new float[]{pxSource};}
    int lpFix=pxSplit&0x3F;
    float[] lpRes=new float[lpFix];
    for(int i=0;i<lpFix;i++){lpRes[i]=pxSource*i/(float)lpFix;}
    return lpRes;
  }//+++
  
  /**
   * interval of results might be equal.<br>
   * @param pxSource passing big value may cause overflow
   * @param pxSplit up to 63
   * @return never null?
   */  
  public final static int[] ccFineSplit(int pxSource, int pxSplit){
    if(pxSplit<=1){return new int[]{pxSource};}
    int lpFix=pxSplit&0x3F;
    int[] lpRes=new int[lpFix];
    for(int i=0;i<lpFix;i++){lpRes[i]=pxSource*i/lpFix;}
    return lpRes;
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
   * just casting.<br>
   * @param pxVal #
   * @return #
   */
  public static final float ccFloat(int pxVal){
    return (float)pxVal;
  }//+++
  
  /**
   * @param pxVal ##
   * @param pxBase ##
   * @return value / base
   */
  public static final float ccFloat(int pxVal, int pxBase){
    return ((float)pxVal)/((float)pxBase);
  }//+++
  
  public static final float ccToFloatForOneAfter(int pxVal){
    return ccRoundForOneAfter(ccFloat(pxVal, 10));
  }//++
  
  /**
   * just casting.<br>
   * @param pxVal #
   * @return #
   */
  public static final int ccInteger(byte pxVal){
    return (int)pxVal;
  }//+++
  
  /**
   * just casting.<br>
   * @param pxVal #
   * @return #
   */
  public static final int ccInteger(short pxVal){
    return (int)pxVal;
  }//+++
  
  /**
   * just casting.<br>
   * @param pxVal #
   * @return #
   */
  public static final int ccInteger(float pxVal){
    return (int)pxVal;
  }//+++
  
  /**
   * @param pxSource dose no check
   * @param pxMagnitude does no check
   * @return source x magnitude
   */
  public static final int ccMagnify(int pxSource, float pxMagnitude){
    return PApplet.ceil(
      ccFloat(pxSource)*pxMagnitude
    );
  }//+++
  
  //=== 
  
  //[plan]::ccArrayCopyLE(int[] from, byte[])
  //[plan]::ccArrayCopySE(int[] from, byte[])
  //[plan]::ccArrayCopyLE(byte[] from, int[])
  //[plan]::ccArrayCopySE(byte[] from, int[])
  
  //=== pack
  
  /**
   * pack up a big string for print.<br>
   * @param pxData must have something 
   * @param pxWrap where the line breaks
   * @return # 
   */
  static public final String ccPackupHexStringTable(byte[] pxData, int pxWrap){
    if(pxData==null){return "";}
    if(pxData.length==0){return "";}
    StringBuilder lpBuilder = new StringBuilder(".ccPackupHexStringTable()::");
    lpBuilder.append(VcConst.C_V_NEWLINE);
    int lpWrapCount=0;
    for(int i=0,s=pxData.length;i<s;i++){
      lpBuilder.append(" 0x");
      lpBuilder.append(PApplet.hex(pxData[i],2));
      lpWrapCount++;
      if(lpWrapCount==pxWrap){
        lpBuilder.append(VcConst.C_V_NEWLINE);
        lpWrapCount=0;
      }//..?
    }//..~
    lpBuilder.append(VcConst.C_V_NEWLINE);
    lpBuilder.append("<<<");
    lpBuilder.append(VcConst.C_V_NEWLINE);
    return lpBuilder.toString();
  }//+++
  
  /**
   * pack up a big string for print.<br>
   * @param pxData must have something 
   * @param pxWrap where the line breaks
   * @return #
   */
  public static String ccPackupHexStringTable(int[] pxData, int pxWrap){
    if(pxData==null){return ".[0]";}
    if(pxData.length<=1){return ".[1]";}
    StringBuilder lpBuilder = new StringBuilder(".ccPackupHexStringTable()::");
    lpBuilder.append(VcConst.C_V_NEWLINE);
    int lpWrapCNT=0;
    int lpWrap=pxWrap<4?4:pxWrap;
    for(int i:pxData){
      lpBuilder.append(PApplet.hex(i,8));
      lpBuilder.append("H ");
      lpWrapCNT++;
      if(lpWrapCNT==lpWrap){
        lpBuilder.append(VcConst.C_V_NEWLINE);
        lpWrapCNT=0;
      }//..?
    }//..~
    return lpBuilder.toString();
  }//+++
  
  /**
   * pack up a big string for print.<br>
   * @param pxData must have something 
   * @param pxWrap where the line breaks
   * @return 
   */
  public static String ccPackupHexStringTable(ZiMemory pxData, int pxWrap){
    if(pxData==null){return ".[0]";}
    StringBuilder lpBuilder
      = new StringBuilder(".ccPackupHexStringTable()::\n");
    lpBuilder.append(VcConst.C_V_NEWLINE);
    int lpWrapCNT=0;
    int lpWrap=pxWrap<4?4:pxWrap;
    for(int i=0,s=pxData.ccGetSize();i<s;i++){
      lpBuilder.append(PApplet.hex(pxData.ccReadWord(i),8));
      lpBuilder.append("H ");
      lpWrapCNT++;
      if(lpWrapCNT==lpWrap){
        lpBuilder.append(VcConst.C_V_NEWLINE);
        lpWrapCNT=0;
      }//..?
    }//..~
    return lpBuilder.toString();
  }//+++
  
  /**
   * pack up a big string for print.<br>
   * @param pxData must have something 
   * @param pxWrap where the line breaks
   * @return #
   */
  public static String ccPackupDecStringTable(int[] pxData, int pxWrap){
    if(pxData==null){return ".[0]";}
    if(pxData.length<=1){return ".[1]";}
    StringBuilder lpBuilder = new StringBuilder(".ccPackupDecStringTable()::");
    lpBuilder.append(VcConst.C_V_NEWLINE);
    int lpWrapCNT=0;
    int lpWrap=pxWrap<4?4:pxWrap;
    for(int i:pxData){
      lpBuilder.append(PApplet.nf(i,6));
      lpBuilder.append(" ");
      lpWrapCNT++;
      if(lpWrapCNT==lpWrap){
        lpBuilder.append(VcConst.C_V_NEWLINE);
        lpWrapCNT=0;
      }//..?
    }//..~
    return lpBuilder.toString();
  }//+++
  
  //[plan]::ccPackupFloatStringTable
  
  //=== proportion
  
  /**
   * @param pxByte 0-255
   * @return 0.0-1.0
   */
  public static final float ccProportion(int pxByte){
    if(pxByte==0){return 0f;}
    if(pxByte==255){return 1f;}
    return ((float)(pxByte&0xFF))/255f;
  }//+++
  
  /**
   * @param pxProportion 0.0-1.0
   * @return 0-255
   */
  public static final int ccProportion(float pxProportion){
    if(pxProportion<=0f){return 0;}
    if(pxProportion>255f){return 255;}
    return (int)(pxProportion*255f);
  }//+++
  
  /**
   * like if you wanna the value of 0.5f, you pass (50,100).<br>
   * @param pxValue should be less than span but this do NOT check
   * @param pxSpan if passed zero this will NOT throw or print anything
   * @return value/span
   */
  public static final float ccProportion(int pxValue, int pxSpan){
    if(pxSpan<=0 || pxValue<=0){return 0f;}
    if(pxValue>=pxSpan){return 1f;}
    return ccRoundForTwoAfter((ccFloat(pxValue)/ccFloat(pxSpan)));
  }//+++
  
  //=== compare
  
  /**
   * i just don't know how to inline this.<br>
   * @param pxSourceA #
   * @param pxSourceB #
   * @return inclusive
   */
  public static final int ccGreater(int pxSourceA, int pxSourceB){
    return pxSourceA>=pxSourceB?pxSourceA:pxSourceB;
  }//+++
  
  /**
   * i just don't know how to inline this.<br>
   * @param pxSourceA #
   * @param pxSourceB #
   * @return inclusive
   */
  public static final int ccLesser(int pxSourceA, int pxSourceB){
    return pxSourceA<=pxSourceB?pxSourceA:pxSourceB;
  }//+++
  
  //=== binary

  /**
   * [2-&gt;2][3-&gt;4][4-&gt;4][5-&gt;8][6-&gt;8][7-&gt;8][9-&gt;16]...
   * @param pxSource #
   * @return fixed value
   */
  static public final int ccToPowerOfTwo(int pxSource){
    if(pxSource<=0x0002){return 0x00000002;}
    if(pxSource>=0x8000){return 0x00008000;}
    int lpMasked=pxSource&0x0000FFFF;
    int lpTester=0x00008000;
    while(lpTester!=1){
      if( (lpTester&lpMasked)!=0 ){break;}
      lpTester>>=1;
    }//..~
    return lpTester==lpMasked?lpTester:lpTester*2;
  }//+++
  
  /**
   * generate an only-one-bit-on mask
   * @param pxBitAddr count from 0
   * @return the mask
   */
  public static final int ccToOnStateMask(int pxBitAddr){
    return 0x00008000>>(0xF-pxBitAddr&0xF);
  }//+++
  
  /**
   * generate an only-one-bit-off mask
   * @param pxBitAddr count from 0
   * @return the mask
   */
  public static final int ccToOffStateMask(int pxBitAddr){
    return 0xFFFF7FFF>>(0xF-pxBitAddr&0xF);
  }//+++
  
  /**
   * @param pxLow #
   * @param pxHigh #
   * @return new 16-bit value in integer form
   */
  public static final int ccBinaryCombine(byte pxLow, byte pxHigh){
    int lpRes=0;
    lpRes|=(((int)pxLow)&0x00FF);
    lpRes|=(((int)pxHigh)&0x00FF)<<8;
    return lpRes;
  }//+++
  
  /**
   * @param pxSource
   * @return 16-31th bits
   */
  static final int ccBinaryTrimH(int pxSource){
    return (pxSource&=0xFFFF0000)>>16;
  }//+++
  
  /**
   * @param pxSource #
   * @return 24~31th bits
   */
  public static final byte ccBinaryTrimHH(int pxSource){
    return (byte)(
      (pxSource&0xFF000000)>>24
    );
  }//+++
  
  /**
   * @param pxSource #
   * @return 16~23th bits
   */
  public static final byte ccBinaryTrimHL(int pxSource){
    return (byte)(
      (pxSource&0x00FF0000)>>16
    );
  }//+++
  
  /**
   * @param pxSource #
   * @return 8~15th bits
   */
  public static final byte ccBinaryTrimLH(int pxSource){
    return (byte)(
      (pxSource&0x0000FF00)>>8
    );
  }//+++
  
  /**
   * @param pxSource #
   * @return 0~7th bits
   */
  public static final byte ccBinaryTrimLL(int pxSource){
    return (byte)(
      (pxSource&0x000000FF)
    );
  }//+++
  
  /**
   * @param pxSource
   * @return 0-15th bits
   */
  static final int ccBinaryTrimL(int pxSource){
    return pxSource&0x0000FFFF;
  }//+++
  
  //[plan]::ccMaskedShiftL(int , int)
  //[plan]::ccMaskedShiftR(int , int)
  //[plan]::ccMaskedShiftR(byte , int)
  //[plan]::ccMaskedShiftR(byte , int)
  
  /**
   * <b>ALL 16-BIT BASED EVEN THOUGH IT DEAL WITH AN INTEGER</b><br>
   * set a given bit status to the value passed.<br>
   * @param pxSource #
   * @param pxBitAddr count from zero
   * @param pxValue #
   * @return # a new value
   */
  static public final
  int ccBinarySet(int pxSource, int pxBitAddr, boolean pxValue){
    return pxValue?
       pxSource|ccToOnStateMask(pxBitAddr)
      :pxSource&ccToOffStateMask(pxBitAddr);
  }//+++
  
  //[plan]::ccBinarySetBit(byte , int , bool)
  
  /**
   * <b>ALL 16-BIT BASED EVEN THOUGH IT DEAL WITH AN INTEGER</b><br>
   * test if the given bit is in on-state.<br>
   * @param pxSource #
   * @param pxBitAddr count from zero
   * @return #
   */
  static public final boolean ccBinaryLoad(int pxSource, int pxBitAddr){
    return (pxSource&ccToOnStateMask(pxBitAddr))!=0;
  }//+++
  
  //[plan]::ccBinaryLoad(byte , int )
  
  //[plan]::ccBinaryReassemble(int[],bool reversed)
  //[plan]::ccBinaryReassemble(byte[],bool reversed)
  
  //=== backward
  
  /**
   * pulled from unofficial OpenJDK repository.<br>
   * for JRE 1.6 capability.<br>
   * @param  pxVal the value to convert to an unsigned
   * @return unsigned conversion
   */
  public static int ccToUnsignedInt(byte pxVal) {
    return ((int) pxVal) & 0xff;
  }//+++
  
}//***eof
