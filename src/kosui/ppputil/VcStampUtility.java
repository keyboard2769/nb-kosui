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

import java.util.Calendar;
import static processing.core.PApplet.day;
import static processing.core.PApplet.hour;
import static processing.core.PApplet.minute;
import static processing.core.PApplet.month;
import static processing.core.PApplet.nf;
import static processing.core.PApplet.second;
import static processing.core.PApplet.year;

/**
 * i struggled with the file name control on my windows computer.<br>
 * what saved me is the format(time) command from autohotkey.<br>
 */
public final class VcStampUtility {
  
  private VcStampUtility(){}//..!
  
  //===
  
  static public final int ccYear(){
   return Calendar.getInstance().get(Calendar.YEAR);
  }//+++
  
  static public final int ccMonth() {
    return Calendar.getInstance().get(Calendar.MONTH) + 1;
  }//+++
  
  static public final int ccDay() {
    return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
  }//+++
  
  static public int ccHour() {
    return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
  }//+++
  
  static public int ccMinute() {
    return Calendar.getInstance().get(Calendar.MINUTE);
  }//+++
  
  static public int ccSecond() {
    return Calendar.getInstance().get(Calendar.SECOND);
  }//+++
  
  //===

  /**
   * @param pxForm like:yy-MM-dd hh:mm'ss
   * @return formatted
   */
  synchronized public static final String ccTimeStamp(String pxForm){
    if(!VcConst.ccIsValidString(pxForm)){return pxForm;}
    String lpRes=pxForm.replaceFirst("yy",nf(year()%2000,2));
    lpRes=lpRes.replaceFirst("MM",nf(month(),2));
    lpRes=lpRes.replaceFirst("dd",nf(day(),2));
    lpRes=lpRes.replaceFirst("hh",nf(hour(),2));
    lpRes=lpRes.replaceFirst("mm",nf(minute(),2));
    lpRes=lpRes.replaceFirst("ss",nf(second(),2));
    return lpRes;
  }//+++
  
  /**
   * supposedly for document file.<br>
   * @return # _yyMMdd
   */
  synchronized static public final String ccTimeStampTypeFII(){
    StringBuilder lpRes=new StringBuilder("_");
    lpRes.append(nf(year()%2000,2));
    lpRes.append(nf(month(),2));
    lpRes.append(nf(day(),2));
    return lpRes.toString();
  }//+++
  
  /**
   * supposedly for archive file.<br>
   * @return # _yyMMddhhmm
   */
  synchronized static public final String ccTimeStampTypeFIII(){
    StringBuilder lpRes=new StringBuilder("_");
    lpRes.append(nf(year()%2000,2));
    lpRes.append(nf(month(),2));
    lpRes.append(nf(day(),2));
    lpRes.append(nf(hour(),2));
    lpRes.append(nf(minute(),2));
    return lpRes.toString();
  }//+++
  
  /**
   * supposedly for blueprint markup.<br>
   * @return # yy-MM-dd
   */
  synchronized static public final String ccTimeStampTypeFIV(){
    StringBuilder lpRes=new StringBuilder("");
    lpRes.append(nf(year()%2000,2));
    lpRes.append('-');
    lpRes.append(nf(month(),2));
    lpRes.append('-');
    lpRes.append(nf(day(),2));
    return lpRes.toString();
  }//+++
  
  /**
   * supposedly for auto generated test file.<br>
   * @param pxMark #
   * @return # _%mark%hhmmss 
   */
  synchronized static public final String ccTimeStampTypeFIV(String pxMark){
    StringBuilder lpRes=new StringBuilder("_");
    lpRes.append(pxMark);
    lpRes.append(nf(hour(),2));
    lpRes.append(nf(minute(),2));
    lpRes.append(nf(second(),2));
    return lpRes.toString();
  }//+++
  
  //===
  
  /**
   * supposedly for error generation.<br>
   * @return # %hh:mm'ss%
   */
  synchronized static public final String ccErrStampTypeI(){
    StringBuilder lpRes=new StringBuilder("[ERR-");
    lpRes.append(nf(hour(),2));
    lpRes.append(':');
    lpRes.append(nf(minute(),2));
    lpRes.append('\'');
    lpRes.append(nf(second(),2));
    lpRes.append(']');
    return lpRes.toString();
  }//++++
  
 }//***eof
