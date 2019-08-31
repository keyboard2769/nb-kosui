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

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * can serve as a dummy model to a JTable. <br>
 * real model might also extends this. <br>
 */
public class McTableAdapter implements TableModel {

  /**
   * dummy
   */
  static protected final String C_DEFAULT = "<nn/>";

  /**
   * changes when add or remove table listener is called
   */
  protected int cmListenerCounter = 0;
  
  //===
  
  /**
   * 
   * @return #
   */
  public int ccGetListenerCounter()
  {return cmListenerCounter;}
  
  //=== 

  /**
   * 
   * @return #
   */
  @Override public int getRowCount()
  {return 2;}//+++

  /**
   * 
   * @return #
   */
  @Override public int getColumnCount()
  {return 2;}//+++
  
  /**
   * 
   * @param pxColumnIndex #
   * @return #
   */
  @Override public String getColumnName(int pxColumnIndex)
  {return C_DEFAULT;}//+++

  /**
   * 
   * @param pxColumnIndex #
   * @return #
   */
  @Override public Class<?> getColumnClass(int pxColumnIndex)
  {return C_DEFAULT.getClass();}//+++

  /**
   * 
   * @param pxRowIndex #
   * @param pxColumnIndex #
   * @return #
   */
  @Override public boolean isCellEditable(int pxRowIndex, int pxColumnIndex)
  {return false;}//+++

  /**
   * 
   * @param pxRowIndex #
   * @param pxColumnIndex #
   * @return #
   */
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex)
  {return C_DEFAULT;}//+++

  /**
   * 
   * @param pxaValue #
   * @param pxRowIndex #
   * @param pxColumnIndex #
   */
  @Override public void setValueAt
    (Object pxaValue, int pxRowIndex, int pxColumnIndex)
  {}//+++

  /**
   * counts up counter
   * @param pxL will be ignored
   */
  @Override public void addTableModelListener(TableModelListener pxL)
  {cmListenerCounter++;}//+++

  /**
   * counts up counter
   * @param pxL will be ignored
   */
  @Override public void removeTableModelListener(TableModelListener pxL)
  {cmListenerCounter--;}//+++

}//***eof
