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

import kosui.pppmodel.McTableAdapter;

import processing.data.StringDict;

/**
 * i tried a lot to find the best table form for several situation. <br>
 * now i think those effort is pure waste of time. <br>
 */
public class McKeyValueModel extends McTableAdapter {

  private final String cmTitle;

  private final StringDict cmData;
  
  //===

  /**
   * 
   * @param pxTitle not always needed but still dont pass a null
   */
  public McKeyValueModel(String pxTitle) {
    cmListenerCounter = 0;
    cmData = new StringDict();
    cmTitle = pxTitle;
  }//+++

  //=== modifier
  
  /**
   * 
   * @param pxKey #
   * @param pxValue # 
   */
  synchronized public final void ccSet(String pxKey, String pxValue)
  {cmData.set(pxKey, pxValue);}//+++

  //=== supporter
  
  private int ccFixIndex(int pxIndex) {
    int lpMask = pxIndex & 0xFFFF;
    int lpSize = cmData.size();
    return lpMask > lpSize ? lpSize : lpMask;
  }//+++
  
  //=== teller
  
  /**
   * 
   * @return #
   */
  synchronized public final String ccGetTitle()
  {return cmTitle;}//+++

  /**
   * this version dont check if has key or not
   * @param pxKey #
   * @return #
   */
  synchronized public final String ccGet(String pxKey)
  {return cmData.get(pxKey);}//+++

  /**
   * 
   * @param pxKey #
   * @param pxDefault #
   * @return #
   */
  synchronized public final String ccGetOrDefault
    (String pxKey, String pxDefault)
  { if(cmData.hasKey(pxKey)){return cmData.get(pxKey);}
    return pxDefault;
  }//+++
  
  /**
   * the result depandes on how processing.data.StringDic implemented
   * @param pxIndex #
   * @return #
   */
  synchronized public final String ccGetKeyAt(int pxIndex)
  {return cmData.key(pxIndex);}//+++

  /**
   * the result depandes on how processing.data.StringDic implemented
   * @param pxIndex #
   * @return #
   */
  synchronized public final String ccGetValueAt(int pxIndex)
  {return cmData.value(pxIndex);}//+++

  //=== interface
  
  @Override public int getRowCount()
  {return cmData.size();}//+++

  @Override public int getColumnCount()
  {return 2;}//+++

  @Override public String getColumnName
    (int columnIndex)
  {if (columnIndex == 0) {return "key";}
    if (columnIndex == 1) {return "value";}
    return "<illeagle/>";
  }//+++

  @Override public boolean isCellEditable
    (int rowIndex, int columnIndex)
  {return columnIndex == 1;}//+++

  @Override public Object getValueAt(int rowIndex, int columnIndex)
  { int lpFix = ccFixIndex(rowIndex);
    if (columnIndex == 0) {return ccGetKeyAt(lpFix);}
    if (columnIndex == 1) {return ccGetValueAt(lpFix);}
    return "<nc/>";
  }//+++

  @Override public void setValueAt
    (Object aValue, int rowIndex, int columnIndex)
  { if (columnIndex == 1) {
      int lpFix = ccFixIndex(rowIndex);
      String lpKey = cmData.key(lpFix);
      ccSet(lpKey, aValue.toString());
    }
  }//+++

  //--
}//***eof
