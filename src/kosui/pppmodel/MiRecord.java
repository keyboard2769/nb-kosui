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
 * a record holds an ID and a key and a value as a basic pare.<br>
 * other field like descriptions should get associated separately.<br>
 */
public interface MiRecord{
  
  /**
   * @return an ID may serve as an Integer key for hashing but not indexing.
   */
  public int ccGetID();
  
  /**
   * @return a key may serve for hashing.
   */
  public String ccGetKey();
  
  /**
   * @return a name may serve as a tag for a XML element.
   */
  public String ccGetType();
  
  //===
  
  /**
   * @param pxVal #
   */
  public void ccSetValue(boolean pxVal);
  
  /**
   * @param pxVal #
   */
  public void ccSetValue(int pxVal);
  
  /**
   * @param pxVal #
   */
  public void ccSetValue(float pxVal);
  
  /**
   * @param pxVal #
   */
  public void ccSetValue(String pxVal);
  
  //===
  
  /**
   * @return your mileage may vary
   */
  public boolean ccGetBoolValue();
  
  /**
   * @return your mileage may vary
   */
  public int ccGetIntValue();
  
  /**
   * @return your mileage may vary
   */
  public float ccGetFloatValue();
  
  /**
   * @return your mileage may vary
   */
  public String ccGetStringValue();
  
}//***
