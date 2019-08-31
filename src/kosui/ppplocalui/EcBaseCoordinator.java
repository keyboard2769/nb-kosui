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

package kosui.ppplocalui;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * this should be just a container.
 * but we don't have to much to hold so this have to know more.
 */
public class EcBaseCoordinator {
  
  /**
   * supposedly the ID of elements
   */
  protected static int
    pbMouseOverID,
    pbInputFocusID
    
  ;//...
  
  /**
   * focus index for input able array list [0-?]
   */
  protected static int pbInputIndex ;
  
  private static LinkedHashMap<Integer,EcElement> pbInputtableList;
  private static EcTipManager pbTipManager;
  
  private final ArrayList<EiUpdatable> cmShapeList;
  private final ArrayList<EcElement> cmElementList;

  //==
  
  /**
   * all focus will be initiated to zero but NOT "ignore".
   */
  public EcBaseCoordinator() {
    pbMouseOverID=0;
    pbInputFocusID=0;
    pbInputIndex=0;
    cmShapeList=new ArrayList();
    cmElementList=new ArrayList();
    pbInputtableList=new LinkedHashMap();
    pbTipManager=new EcTipManager();
  }//+++
  
  //==
  
  /**
   * should be called inside draw().<br>
   * thus coordinator for-each all component he holds,
   * ccUpdate() of components should never
   * be called directly from draw().<br>
   */
  public final void ccUpdate(){
    
    if(!EcPoint.ccHasOwner()){return;}
    
    for(EcElement it:pbInputtableList.values())
      {it.ccSetIsActivated(it.ccGetID()==pbInputFocusID);}
    for(EiUpdatable it:cmShapeList){it.ccUpdate();}
    pbMouseOverID=0;
    for(EcElement it:cmElementList){
      it.ccUpdate();
      if(it.ccIsMouseHovered()){pbMouseOverID=it.ccGetID();}
    }
    
    pbTipManager.ccUpdate(pbMouseOverID);
  
  }//+++
  
  //== modifier
  
  /**
   * make sure not to pass null.
   * this don't check.
   * @param pxElement #
   */
  public final void ccAddElement(EcElement pxElement)
    {cmElementList.add(pxElement);}//+++
  
  /**
   * make sure not to pass null and the list hold no null.
   * this don't check.
   * @param pxList simply called addAll()
   */
  public final void ccAddElement(ArrayList<EcElement> pxList)
    {cmElementList.addAll(pxList);}//+++
  
  /**
   * make sure not to pass null.
   * this don't check.
   * @param pxShape #
   */
  public final void ccAddShape(EiUpdatable pxShape)
    {cmShapeList.add(pxShape);}//+++
  
  /**
   * make sure not to pass null and the list hold no null.
   * this don't check.
   * @param pxList # 
   */
  public final void ccAddShape(ArrayList<EiUpdatable> pxList)
    {cmShapeList.addAll(pxList);}//+++
  
  /**
   * does not check for null. 
   * @param pxGroup #
   */
  public final void ccAddGroup(EiGroup pxGroup){
    cmElementList.addAll(pxGroup.ccGiveElementList());
    cmShapeList.addAll(pxGroup.ccGiveShapeList());
  }//+++
  
  /**
   * make sure not to pass null.
   * this don't check.
   * @param pxBox will be turned "on" if id matches. input logic is not here.
   */
  public final void ccAddInputtable
    (EcElement pxBox)
  { pbInputtableList.put(pxBox.ccGetID(), pxBox);
    pbInputIndex=pbInputtableList.size();
  }//+++
  
  /**
   * passed tip will always show if the element of passed id is mouse hovered
   * @param pxID #
   * @param pxTip #
   */
  public final void ccAddTip(int pxID, String pxTip)
    {pbTipManager.ccPut(pxID, pxTip);}//+++ 
  
  //== updator
  
  /**
   * set input focus to ignore id.
   * call this when you think a value has been taken
   */
  public final void ccClearCurrentInputFocus(){
    pbInputFocusID=EcFactory.C_ID_IGNORE;
    pbInputIndex=pbInputtableList.size();
  }//+++
  
  /**
   * set input focus to current mouse hovered element.
   * supposedly should be called from mousePressed().
   */
  public final void ccSetCurrentInputFocus(){
    pbInputFocusID=pbMouseOverID;
    pbInputIndex=pbInputtableList.size();
  }//+++
  
  /**
   * set input focus to next indexed one in the list.
   * supposedly should be triggered by pressing [tab] key.
   */
  public final void ccToNextInputIndex(){
    pbInputIndex++;
    int lpSize=pbInputtableList.size();
    if(pbInputIndex>lpSize)
      {pbInputIndex=0;}
    if(pbInputIndex==lpSize)
      {pbInputFocusID=EcFactory.C_ID_IGNORE;return;}
    EcElement[] lpArray =new EcElement[lpSize];
    pbInputtableList.values().toArray(lpArray);
    EcElement lpBox=lpArray[pbInputIndex];
    if(lpBox!=null){pbInputFocusID=lpBox.ccGetID();}
  }//+++
  
  /**
   * 
   * @return null if there is nothing focused so you have to check
   */
  public final EcElement ccGetCurrentFocusedBox(){
    if(pbInputFocusID==0 || pbInputFocusID==EcFactory.C_ID_IGNORE)
      {return null;}
    return pbInputtableList.get(pbInputFocusID);
  }//+++
  
  //== teller
  
  /**
   * 
   * @return id of current mouse hovered element
   */
  public final int ccGetMouseOverID()
    {return pbMouseOverID;}//+++
  
  /**
   * 
   * @return focus id is not zero or "ignore"
   */
  public final boolean ccHasInputtableFocused()
    {return pbInputFocusID!=0 && pbInputFocusID!=EcFactory.C_ID_IGNORE;}//+++
  
  /**
   * 
   * @return #
   */
  public final int ccGetInputFocusID()
    {return pbInputFocusID;}//+++

}//***eof
