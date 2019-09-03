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
 * as there is a semirigid one there should be a total flexible one.<br>
 * but i hope nobody ever had to use this thing.<br>
 * @param <R> a row, supposedly some class implemented the record interface
 * @param <C> a column, supposedly an array list of certain data type
 */
public abstract class McArbitaryTable<R,C> extends McSemirigidTable<R> {
  
  //=== inserting
  
  /**
   * ##
   */
  abstract public void ccInsertColumnFist();
  
  /**
   * ##
   * @param pxIndex #
   */
  abstract public void ccInsertColumnAbove(int pxIndex);
  
  /**
   * ##
   * @param pxIndex #
   */
  abstract public void ccInsertColumnBelow(int pxIndex);
  
  /**
   * ##
   */
  abstract public void ccInsertColumnLast();
  
  //=== retrieving
  
  /**
   * ##
   * @return #
   */
  abstract public C ccRetrieveColumnFirst();
  
  /**
   * ##
   * @param pxIndex #
   * @return #
   */
  abstract public C ccRetrieveColumnAt(int pxIndex);
  
  /**
   * ##
   * @return 
   */
  abstract public C ccRetrieveColumnLast();
  
}//***
