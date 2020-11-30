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

import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;

/**
 * can serve as a dummy model to a JTable. <br>
 * real model might also extends this. <br>
 * <b>THIS HAVE NOTHING TO DO WITH THE DATA TABLE OF PROCESSING!!</b><br>
 */
public class McTableAdapter implements TableModel {

  /**
   * dummy content
   */
  static protected final String C_DEFAULT = "<?>";

  /**
   * changes when add or remove table listener is called
   */
  protected int cmListenerCounter = 0;
  
  //===
  
  /**
   * @return #
   */
  public int ccGetListenerCounter(){
    return cmListenerCounter;
  }//+++
  
  //=== 
  
  /**
   * {@inheritDoc }
   */
  @Override public int getRowCount(){
    return 2;
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public int getColumnCount(){
    return 2;
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public String getColumnName(int pxColumnIndex){
    return C_DEFAULT;
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public Class<?> getColumnClass(int pxColumnIndex){
    return C_DEFAULT.getClass();
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public boolean isCellEditable(int pxRowIndex, int pxColumnIndex){
    return false;
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public Object getValueAt(int pxRowIndex, int pxColumnIndex){
    return C_DEFAULT;
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public
  void setValueAt(Object pxaValue, int pxRowIndex, int pxColumnIndex){}//+++

  /**
   * {@inheritDoc }
   */
  @Override public
  void addTableModelListener(TableModelListener pxListener){
    cmListenerCounter++;
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public
  void removeTableModelListener(TableModelListener pxListener){
    cmListenerCounter--;
  }//+++

}//***eof
