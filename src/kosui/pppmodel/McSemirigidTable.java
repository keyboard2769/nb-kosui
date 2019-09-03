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
package kosui.pppmodel;

/**
 * say, with all my use case, columns of a table is fixed,
 * they might never change.<br>
 * a general AWT accessible interface is not that suit for 
 * this kind of case.<br>
 * @param <R> a row, supposedly some class implemented the record interface
 */
public abstract class McSemirigidTable<R> extends McTableAdapter{
  
  //=== inserting
  
  /**
   * #
   */
  abstract public void ccInsertRowFist();
  
  /**
   * #
   * @param pxIndex #
   */
  abstract public void ccInsertRowAbove(int pxIndex);
  
  /**
   * #
   * @param pxIndex #
   */
  abstract public void ccInsertRowBelow(int pxIndex);
  
  /**
   * #
   */
  abstract public void ccInsertRowLast();
  
  //=== retrieving
  
  /**
   * @return #
   */
  abstract public R ccRetrieveRowFirst();
  
  /**
   * @param pxIndex #
   * @return #
   */
  abstract public R ccRetrieveRowAt(int pxIndex);
  
  /**
   * #
   * @return #
   */
  abstract public R ccRetrieveRowLast();
  
}//***eof
