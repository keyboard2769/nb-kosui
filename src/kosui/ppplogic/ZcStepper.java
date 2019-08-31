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
 * in my logic controller a step register can hold ninety nine step. <br>
 * but that made no sense because you can only be at one step at a time. <br>
 */
public class ZcStepper {

  /**
   * stage 
   */
  protected int cmStage;

  /**
   * start at step zero
   */
  public ZcStepper()
    {cmStage=0;}//+++

  /**
   * 
   * @param pxFrom #
   * @param pxTo #
   * @param pxCondition a lock, or a trigger
   */
  public final void ccStep(int pxFrom, int pxTo, boolean pxCondition)
    {if(cmStage==pxFrom && pxCondition){cmStage=pxTo;}}//+++

  /**
   * 
   * @param pxStage #
   * @param pxCondition #
   */
  public void ccSetTo(int pxStage, boolean pxCondition)
    {if (pxCondition) {cmStage = pxStage;}}//+++

  /**
   * 
   * @param pxStage #
   * @return #
   */
  public boolean ccIsAt(int pxStage)
    {return cmStage==pxStage;}//+++
  
  //===
  
  /**
   * 
   * @return current stage
   * @deprecated i have to stick on how you should not concern on this. 
   */
  @Deprecated public final int testGetStage(){
    return cmStage;
  }//+++
    
}//***eof
