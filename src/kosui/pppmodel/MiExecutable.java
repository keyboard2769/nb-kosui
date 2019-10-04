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
 * like the main function takes an array of string
 * and returns an integer value.<br>
 * any thing implement this takes an array of string
 * and returns an integer value.<br>
 */
public interface MiExecutable {
  
  /**
   * any thing can happen!<br>
   * @param pxArgs arguments
   * @return result
   */
  public int ccExecute(String[] pxArgs);
  
}//***eof
