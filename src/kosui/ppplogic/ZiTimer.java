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

package kosui.ppplogic;

/**
 * in logic controller, timer is not a class or object. <br>
 * timer is a instruction takes parameter and runs under conditions. <br>
 */
public interface ZiTimer{
  
  /**
   * supposedly and ultimately should be called from draw().<br>
   * @param pxAct when you want timer to count
   */
  public void ccAct(boolean pxAct);

  /**
   * supposedly and ultimately should be called from draw().<br>
   * @return timer-up!
   */
  public boolean ccIsUp();
  
  /**
   * supposedly and ultimately should be called from draw().<br>
   * @return should based on the current value but not the action condition.
   */
  public boolean ccIsCounting();
  
  /**
   * may vary on purpose.<br>
   * @param pxDiv update count basically. like for 16FPS, 16 means one second.
   */
  public void ccSetTime(int pxDiv);
  
}//***eof
