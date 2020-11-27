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

import java.util.Collection;
import java.util.Map;
import kosui.ppplogic.ZcRangedModel;
import processing.core.PApplet;

/**
 * access, modify, copy, pack, merge, cut, trim, well, say, they are not
 * 'calculate' or 'numeric' at all.<br>
 * for every thing i wish that was in the Arrays.<br>
 */
public final class VcArrayUtility {
  
  //=== verify
  
  /**
   * @param pxArray be tested
   * @return true if not null not empty
   */
  static public final boolean ccIsValidArray(Object[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  /**
   * @param pxArray be tested
   * @param pxLength as minimum count
   * @return true if not null not small
   */
  static public final boolean ccIsValidArray(Object[] pxArray, int pxLength){
    if(pxArray==null){return false;}
    return pxArray.length>=pxLength;
  }//+++
  
  /**
   * @param pxArray be tested
   * @return true if not null not empty
   */
  static public final boolean ccIsValidArray(boolean[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  //[todo]::ccIsValidArray(boolean[] pxArray, int ...){
  
  /**
   * @param pxArray be tested
   * @return true if not null not empty
   */
  static public final boolean ccIsValidArray(byte[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  //[todo]::ccIsValidArray(byte[] pxArray, int ...){
  
  /**
   * @param pxArray be tested
   * @return true if not null not empty
   */
  static public final boolean ccIsValidArray(int[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  //[todo]::ccIsValidArray(int[] pxArray, int ...){
  
  /**
   * @param pxArray be tested
   * @return true if not null not empty
   */
  static public final boolean ccIsValidArray(float[] pxArray){
    if(pxArray==null){return false;}
    return pxArray.length!=0;
  }//+++
  
  //[todo]::ccIsValidArray(float[] pxArray, int ...){
  
  //=== access
  
  //[todo]::ccSet(Object[] pxTartget, int pxIndex, pxVal){}//++<
  //[todo]::ccSet(boolean[] pxTartget, int pxIndex, pxVal){}//++<
  //[todo]::ccSet(byte[] pxTartget, int pxIndex, pxVal){}//++<
  //[todo]::ccSet(int[] pxTartget, int pxIndex, pxVal){}//++<
  //[todo]::ccSet(float[] pxTartget, int pxIndex, pxVal){}//++<
  
  /**
   * @param pxTartget no null no empty
   * @param pxIndex will get limited via ZcRangedModel.ccLimit
   * @return could be null
   */
  public static final Object ccGet(Object[] pxTartget, int pxIndex){
    if(!ccIsValidArray(pxTartget)){return null;}
    return pxTartget[ZcRangedModel
      .ccLimitInclude(pxIndex, 0, pxTartget.length-1)];
  }//++>
  
  //[todo]::ccGet([] pxTartget, int pxIndex){}//++>
  //[todo]::ccGet([] pxTartget, int pxIndex){}//++>
  //[todo]::ccGet([] pxTartget, int pxIndex){}//++>
  
  /**
   * @param pxTartget no null no empty
   * @param pxIndex will get limited via ZcRangedModel.ccLimit
   * @return 0.0f silently even if something is wrong 
   */
  public static final float ccGet(float[] pxTartget, int pxIndex){
    if(!ccIsValidArray(pxTartget)){return 0f;}
    return pxTartget[ZcRangedModel
      .ccLimitInclude(pxIndex, 0, pxTartget.length-1)];
  }//++>
  
  //=== copy
  
  //[todo]::public static final void ccCopy(Object[] pxFrom,Object[] pxTo){}//+++
  //[todo]::public static final void ccCopy(boolean[] pxFrom,boolean[] pxTo){}//+++
  //[todo]::public static final void ccCopy(byte[] pxFrom,byte[] pxTo){}//+++
  
  /**
   * @param pxFrom no null no empty
   * @param pxTo no null no empty
   */
  public static final void ccCopy(int[] pxFrom,int[] pxTo){
    if(!ccIsValidArray(pxFrom)){return;}
    if(!ccIsValidArray(pxTo)){return;}
    int lpFixedLength=VcNumericUtility.ccLesser(pxFrom.length, pxTo.length);
    for(int i=0;i<lpFixedLength;i++){pxTo[i]=pxFrom[i];}
  }//+++
  
  /**
   * @param pxFrom no null no empty
   * @param pxTo no null no empty
   */
  public static final void ccCopy(float[] pxFrom,float[] pxTo){
    if(!ccIsValidArray(pxFrom)){return;}
    if(!ccIsValidArray(pxTo)){return;}
    int lpFixedLength=VcNumericUtility.ccLesser(pxFrom.length, pxTo.length);
    for(int i=0;i<lpFixedLength;i++){pxTo[i]=pxFrom[i];}
  }//+++
  
  //=== binary
  
  //[plan]::ccBinaryCopyLE(int[] pxFrom, byte[] pxTo)
  //[plan]::ccBinaryCopySE(int[] pxFrom, byte[] pxTo)
  //[plan]::ccBinaryCopyLE(byte[] pxFrom, int[] pxTo)
  //[plan]::ccBinaryCopySE(byte[] pxFrom, int[] pxTo)
  
  //=== collection 
  
  //[plan]::boolean[]::ccToBooleanArray(List<Boolean> pxList){...
  //[plan]::int[]::ccToIntegerArray(List<Integer> pxList){...
  //[plan]::float[]::ccToFloatArray(List<Float> pxList){...
  
  public static final String[] ccToStringArray(Collection<String> pxList){
    if(pxList==null){return null;}
    if(pxList.isEmpty()){return new String[]{};}
    return pxList.toArray(new String[]{});
  }//+++
  
  /**
   * tweeked from Apache Commons Collection v4 for fast initiating.
   * @param <K> ##
   * @param <V> ##
   * @param pxMap no null
   * @param pxDesEntry no null no empty
   * @return 
   */
  public static <K extends Object, V extends Object>
  Map<K, V> ccPutAllToMap(Map<K, V> pxMap, Object[]pxDesEntry) {
    
    //-- check in
    if(pxMap == null){return pxMap;}
    if(pxDesEntry == null || pxDesEntry.length == 0){return pxMap;}
    
    //-- loop over
    int lpRes = 0;
    final Object lpBuf = pxDesEntry[0];
    if (lpBuf instanceof Map.Entry) {
      for (final Object it : pxDesEntry) {
        final Map.Entry<K, V> lpEntry = (Map.Entry<K, V>) it;
        pxMap.put(lpEntry.getKey(), lpEntry.getValue());
      }//..~
    }else
    if (lpBuf instanceof Object[]) {
      for (int i = 0; i < pxDesEntry.length; i++) {
        final Object[] lpSubstituent = (Object[]) pxDesEntry[i];
        if (lpSubstituent == null || lpSubstituent.length < 2) {
          lpRes = -1*Math.abs(i);
          break;
        }//..?
        pxMap.put((K) lpSubstituent[0], (V) lpSubstituent[1]);
      }//..~
    } else {
        for (int i = 0; i < pxDesEntry.length - 1;) {
          pxMap.put((K) pxDesEntry[i++], (V) pxDesEntry[i++]);
        }//..?
    }//..?
    
    //-- report
    if(lpRes < 0){VcConst.ccErrln(
      ".ccPutAllToMap $ abort at :",
      Integer.toString(Math.abs(lpRes)
    ));}
    return pxMap;
    
  }//+++
  
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
  public static final String ccPackupHexStringTable(int[] pxData, int pxWrap){
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
   * @return #
   */
  public static final String ccPackupDecStringTable(int[] pxData, int pxWrap){
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
  
}//***eof
