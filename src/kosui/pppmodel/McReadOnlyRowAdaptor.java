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

/**
 * can serve as a base class to any internal private helper class
 *   which should never get its value altered.<br>
 * we used to have an idea called MiRecord but this is the current 
 *   approach to that.<br>
 * <b>THIS HAVE NOTHING TO DO WITH SWING TABLE STRUCTURE!!</b><br>
 */
public class McReadOnlyRowAdaptor extends McRowAdapter{
  
  private static boolean pbDoesClaim = true;
  
  /**
   * claim while setter got called or not
   * @param pxVal ##
   */
  public static final void ccSetDoesClaim(boolean pxVal){
    pbDoesClaim = pxVal;
  }//+++
  
  private static void ccClaim(){
    if(pbDoesClaim){
      System.err.println(
        "McReadOnlyRowAdaptor $ Dont your ever never do this."
      );
    }//..?
  }//+++
  
  //=== cut
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setString(int column, String value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setString(String columnName, String value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setInt(int column, int value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setInt(String columnName, int value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setLong(int column, long value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setLong(String columnName, long value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setFloat(int column, float value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setFloat(String columnName, float value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setDouble(int column, double value) {ccClaim();}//+++
  
  /**
   * {@inheritDoc }
   */
  @Override public
  void setDouble(String columnName, double value) {ccClaim();}//+++
  
}//***eof
