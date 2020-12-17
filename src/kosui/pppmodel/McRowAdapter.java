/*
 * Copyright (C) 2020 Key Parker from K.I.C
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

import kosui.ppputil.VcStringUtility;
import processing.data.Table;
import processing.data.TableRow;

/**
 * can serve as a base class to any internal private helper class.<br>
 * we used to have an idea called MiRecord but this is the current 
 *   approach to that.<br>
 * <b>THIS HAVE NOTHING TO DO WITH SWING TABLE STRUCTURE!!</b><br>
 */
public class McRowAdapter implements TableRow{
  
  private static String pbEmptyRepresentation = "<?e>";
  
  /**
   * the one get returned if not got overridden.
   * @param pxRepresentation will get nulled out.
   */
  public final static
  void ccSetDefaultEmptyRepresentation(String pxRepresentation){
    pbEmptyRepresentation = VcStringUtility.ccNulloutString(pxRepresentation);
  }//+++
  
  //=== column
  
  /**
   * {@inheritDoc }
   */
  @Override public
  int getColumnCount() {return 1;}
  
  /**
   * {@inheritDoc }
   */
  @Override public
  int getColumnType(String columnName) {return Table.STRING;}
  
  /**
   * {@inheritDoc }
   */
  @Override public
  int getColumnType(int column) {return Table.STRING;}
  
  //=== getter
  
  /**
   * {@inheritDoc }
   */
  @Override public
  String getString(int column) {return pbEmptyRepresentation;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  String getString(String columnName){return pbEmptyRepresentation;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  int getInt(int column) {return 0;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  int getInt(String columnName) {return 0;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  long getLong(int column) {return 0;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  long getLong(String columnName) {return 0;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  float getFloat(int column) {return 0.0f;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  float getFloat(String columnName) {return 0.0f;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  double getDouble(int column) {return 0.0d;}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  double getDouble(String columnName) {return 0.0d;}//+++
  
  //=== setter
  
  /**
   * {@inheritDoc }
   */
  @Override public void setString(int column, String value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setString(String columnName, String value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setInt(int column, int value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setInt(String columnName, int value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setLong(int column, long value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setLong(String columnName, long value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setFloat(int column, float value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setFloat(String columnName, float value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setDouble(int column, double value) {}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public void setDouble(String columnName, double value) {}//+++
  
}//***eof
