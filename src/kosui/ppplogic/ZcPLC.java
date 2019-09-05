/*
 * Copyright (C) 2018 Key Parker from K.I.C
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

import java.util.ArrayList;

/**
 * simulate a PLC scan operation.<br>
 * if you can find, see how FUJI-ELECTRONICS PROGRAMMABLE LOGIC CONTROLLER
 * SERIES SX:NP1PM-256E programs him self.<br>
 */
public abstract class ZcPLC implements ZiMemory{

  private final ArrayList<ZiTask> cmTaskList = new ArrayList();
  
  /**
   * supposedly task is a piece of code mimics PLC movement 
   * inside a subclass from your sketch.
   * @param pxTask #
   */
  public final void ccAddTask(ZiTask pxTask) {
    if(pxTask==null){return;}
    if(cmTaskList.contains(pxTask)){return;}
    cmTaskList.add(pxTask);
  }//+++
  
  //===
  
  /**
   * supposedly should be called from draw()
   */
  public final void ccUpdate() {
    for (ZiTask it : cmTaskList) {
      it.ccScan();
      it.ccSimulate();
    }//..?
  }//+++

}//***eof
