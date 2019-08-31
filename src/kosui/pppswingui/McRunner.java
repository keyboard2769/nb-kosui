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

package kosui.pppswingui;

/**
 * for some reason i don't like anonymous classes. <br>
 * for those reason i can't just pass code to invoke later. <br>
 */
public class McRunner implements Runnable {

  /**
   * serves inter threads
   */
  volatile protected String cmParam = "";

  /**
   * 
   * @param pxParam may be useful inside dispatch thread.
   */
  synchronized public void ccSetParam(String pxParam) {
    cmParam = pxParam;
  }//+++

  /**
   * {@inheritDoc }
   */
  @Override public void run(){
    System.err.println(".McRunner.run()::this_should_never_been_reached");
  }//+++

}//***eof
