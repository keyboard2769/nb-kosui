/*
 * Copyright (C) 2018 Key Parker from K.I.C.
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;

/**
 * for some handy input command parsing. everything is finally String. <br>
 * you can let user get to type a string like :<br>
 * <pre>
 *   "addRect -bond --x 12 --y 12 --w 12 --h 12 -align left top"
 * </pre>
 * and parse it into a map like:<br>
 * <pre>
 *   addRect{
 *     "--addRect-bond-x":"12",
 *     "--addRect-bond-y":"12",
 *     "--addRect-bond-w":"12",
 *     "--addRect-bond-h":"12",
 *     "--addRect-align":"left top"
 *   }
 * </pre>
 * and than translate it to invoke your own
 *   `addRect(new Rect(12,12,12,12), Papplet.LEFT, Papplet.TOP);`
 * at somewhere of your sketch. <br>
 * <hr>
 * another sample:<br>
 * <pre>
 *   "gcc -c text.c -o text.exe"
 * </pre>
 * would became:<br>
 * <pre>
 *   gcc{
 *     "--gcc-c":"test.c",
 *     "--gcc-o":"test.o"
 *   }
 * </pre>
 * thats it.
 */
public class McStringMap {
  
  //=== inner
  
  private class McNode{
    int cmLevel = 0;
    String cmKey = null;
    String cmVal = null;
    int ccParse(String pxLine){
      
      //-- check in
      if(pxLine==null){return -101;}
      if(pxLine.length()<3){return -102;}
      if(!pxLine.startsWith("-")){return -103;}
      
      //-- split apart
      String[] lpSplit = pxLine.split(" ");
      if(lpSplit==null){return -201;}
      if(lpSplit.length<=1){return -202;}
      
      //-- count level
      cmLevel = 0;cmKey = null;cmVal = null;
      for(char it : lpSplit[0].toCharArray()){
        if(it=='-'){cmLevel++;}
      }//..?
      if(cmLevel<1){return -301;}
      
      //-- pack
      cmKey=lpSplit[0].replaceAll("-", "");
      switch (lpSplit.length) {
        case 1:
        return 0;
        case 2:
          cmVal=lpSplit[1];
        return 1;
        default:
        {
          StringBuilder lpBuilder=new StringBuilder("");
          for(int i=0, s=lpSplit.length;i<s;i++){
            if(i==0){continue;}
            if(i>1){
              lpBuilder.append(' ');
            }//..?
            lpBuilder.append(lpSplit[i]);
          }//..~
          cmVal=lpBuilder.toString();
        }
        return lpSplit.length-1; //..?
      }//..?
      
    }//+++
    @Override public String toString() {
      return String.format(
        "%s -lv %d -k %s -v %s",
        super.toString(),cmLevel,cmKey,cmVal
      );
    }//+++
  }//***
  
  //=== 
  
  private String cmKey = "";
  
  private final Map<String, String> cmMap = new TreeMap<String, String>();
  
  //McCommand(String){...

  //===
  
  /**
   * ##
   * @param pxKey in the form of `--a-b-c-d` better no special letter
   * @param pxOrDefault #
   * @return #
   */
  public final String ccGetValue(String pxKey, String pxOrDefault){
    if(!VcConst.ccIsValidString(pxKey)){return pxOrDefault;}
    return cmMap.getOrDefault(pxKey, pxOrDefault);
  }//+++

  /**
   * supposedly could be used in somewhere like `Map:String,McStringMap:` 
   * @return could be anything
   */
  public final String ccGetMapKey(){
    return cmKey;
  }//+++
  
  /**
   * ##
   * @return ##
   */
  public final int ccGetMapSize(){
    return cmMap.size();
  }//+++
  
  /**
   * ##
   * @return ##
   */
  public final Set<String> ccGetKeySet(){
    return Collections.unmodifiableSet(cmMap.keySet());
  }//+++
  
  //[later]::List<String> ccGetPathList("path"){...
  //[later]::List<String> ccGetPathList(int[] ){...
  
  //=== export
  
  //[later]::Table ccExportTable(){}
  //[later]::XML ccExportXML(){}
  
  //[later]::int ccExport(File ..., ".???"){...}
  
  //=== parse
  
  /**
   * ##
   * @param pxLine #
   * @return size of map if everything is okay or step code
   */
  public final int ccParse(String pxLine){
    
    final String lpAbs = "McStringMap.ccParse $ abort";
    final String lpAbn = "McStringMap.ccParse $ this should never happen";
    
    //-- check in
    if(!VcConst.ccIsValidString(pxLine)){return VcConst.ccErrln(lpAbs, -101);}
    
    //-- filter in
    String lpBuf = pxLine.trim();
    lpBuf = lpBuf.replaceAll("\\s+", " ");
    lpBuf = lpBuf.replaceAll("#", " -#");
    lpBuf = lpBuf.replaceAll("@", " -@");
    lpBuf = lpBuf.replaceAll(" -", " %--");
    String[] lpDesSplit = lpBuf.split("%-");
    if(lpDesSplit==null){return VcConst.ccErrln(lpAbs, -102);}
    if(lpDesSplit.length<=1){return VcConst.ccErrln(lpAbs, -103);}
    
    //-- loop
    int lpLevelCount=0;
    McNode lpBufNode = new McNode();
    String lpKeyPath = "--";
    String lpValue = null;
    cmKey="";
    cmMap.clear();
    for(String it : lpDesSplit){
      
      if(!VcConst.ccIsValidString(it)){continue;}
      if(VcStringUtility.ccIsInvisibleString(it)){continue;}
      
      //-- ** valid 
      if(it.startsWith("-")){
        
        //-- ** valid ** pre determine
        if(it.length()<=1){continue;}
        char lpSenondaryFlag = it.charAt(1);
        switch (lpSenondaryFlag) {
          
          //-- ** ** case of map key
          case '#':
            cmKey=it.trim().replaceAll("#", "").replaceAll("-", "");
          break;
          
          //-- ** ** case of new root
          case '@':
            lpKeyPath="-"+it.trim().replaceAll("@", "");
            lpLevelCount=0;
          break;
          
          //-- ** ** case of normal
          default:{
            
            //-- ** ** ** parse
            int lpParseRes = lpBufNode.ccParse(it);
            if(lpParseRes<0){continue;}
            
            //-- ** ** ** repath
            if(lpBufNode.cmLevel<=lpLevelCount){
              String[] lpDesPath = lpKeyPath.split("-");
              if(lpDesPath==null)
                {VcConst.ccErrln(lpAbn,"1");continue;}//..?
              if(lpDesPath.length<3)
                {VcConst.ccErrln(lpAbn,"2");continue;}//..?
              if(lpBufNode.cmLevel<=0)
                {VcConst.ccErrln(lpAbn,"3");continue;}//..?
              StringBuilder lpPreviousBuilder = new StringBuilder("");
              for(int i=0,s=lpBufNode.cmLevel+2;i<s;i++){
                lpPreviousBuilder.append(lpDesPath[i]);
                lpPreviousBuilder.append('-');
              }//..?
              lpPreviousBuilder.append(lpBufNode.cmKey);
              lpKeyPath=lpPreviousBuilder.toString();
              lpLevelCount=lpDesPath.length+1;
              if(cmMap.containsKey(lpKeyPath)){
                lpValue=cmMap.get(lpKeyPath)+" ";
              }else{
                lpValue=lpBufNode.cmVal;
              }//..?
            }else
            if(lpBufNode.cmLevel>lpLevelCount){
              lpKeyPath=lpKeyPath+"-"+lpBufNode.cmKey;
              lpValue=lpBufNode.cmVal;
              lpLevelCount++;
            }else{VcConst.ccErrln(lpAbn,"4");continue;}//..?
            
            //--
            cmMap.put(lpKeyPath.trim(), lpValue.trim());
            
          }break;
          
        }//...?
        
      }else{
        lpKeyPath="--"+it.trim();
        cmKey=lpKeyPath;
        lpLevelCount=0;
      }//..?
      
    }//..~
    
    //-- report
    return cmMap.size();
  
  }//+++
  
  //[later]::int ccParse(Table ...)
  //[later]::int ccParse(XML ...)
  
  //=== entry

  @Override public String toString() {
    return String.format(
      "%s $ -key %s -size %d",
      super.toString(),cmKey,cmMap.size()
    );
  }//+++
  
  //=== util
  
  public static final void ccPrintln(McStringMap pxMap){
    if(pxMap==null){return;}
    VcConst.ccPrintln("McStringMap.ccPrintln $ enter", pxMap.cmKey);
    for(String it : pxMap.ccGetKeySet()){
      System.out.println(String.format(
        "[%s:%s]", it,pxMap.ccGetValue(it, "?")
      ));
    }//..?
    VcConst.ccPrintln("McStringMap.ccPrintln $ exit");
  
  }//+++
  
}//***eof
