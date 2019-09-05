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

import java.util.List;

/**
 * after all the only reason for me to use a editable table is to make a 
 * giant indicator.<br>
 * by most of time these two is the only method i needed.<br>
 * @param <R> a row, supposedly some class implemented the list interface
 */
public abstract class McBufferTable<R extends List> extends McTableAdapter {
  
    /**
   * #
   * @param pxRow do not pass null
   */
  abstract public void ccInsertRowLast(R pxRow);
  
  /**
   * @return #
   */
  abstract public R ccRetrieveRowFirst();
  
}//***eof
