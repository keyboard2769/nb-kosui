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
 * i wonder if there is such a utility in the library or not.<br>
 * but hey, you did not reach this approach for a long long time,
 * how can you blame me for the "record" class now ?<br>
 */
public interface MiValue{
  
  /**
   * @param pxVal could be anything and do not expect
   */
  public void ccSetValue(Object pxVal);
  
  /**
   * @return could be anything and do not expect
   */
  public Object ccGetValue();
  
}//***eof
