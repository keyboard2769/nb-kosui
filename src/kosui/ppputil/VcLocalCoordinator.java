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

package kosui.ppputil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import processing.core.PApplet;

import kosui.ppplocalui.EcComponent;
import kosui.ppplocalui.EcConst;
import kosui.ppplocalui.EcElement;
import kosui.ppplocalui.EcShape;
import kosui.ppplocalui.EiGroup;
import kosui.ppplocalui.EiTriggerable;

/**
 * this should be just a container.
 * but we don't have to much to hold so this have to know more.
 */
public final class VcLocalCoordinator {
  
  private static final HashMap<Integer, EiTriggerable> O_MAP_OF_KEY_TRIGGER
    = new HashMap<Integer, EiTriggerable>();
  private static final HashMap<Integer, EiTriggerable> O_MAP_OF_MOUSE_TRIGGER
    = new HashMap<Integer, EiTriggerable>();
  
  private static final
  HashMap<Integer, EiTriggerable> O_MAP_OF_WHEEL_UP_TRIGGER
    = new HashMap<Integer, EiTriggerable>();
  private static final
  HashMap<Integer, EiTriggerable> O_MAP_OF_WHEEL_DOWN_TRIGGER
    = new HashMap<Integer, EiTriggerable>();
  
  private static final HashMap<Integer,EcElement> O_MAP_OF_INPUTTABLE
    = new HashMap<Integer, EcElement>();
  
  private static final Queue<EiTriggerable> O_QUEUE_OF_LOOP_TRIGGER
    = new LinkedList<EiTriggerable>();
  
  private static final List<EcShape> O_LIST_OF_SHAPE
    = new ArrayList<EcShape>();
  private static final List<EcElement> O_LIST_OF_ELEMENT
    = new ArrayList<EcElement>();
  
  private static int
    cmMouseOverID  = 0,
    cmInputFocusID = 0,
    cmInputIndex   = 0
  ;//...
  
  //===
  
  /**
   * all focus will be initiated to zero but NOT "ignore".
   * @param pxOwner will get passed to EcComponent statics.
   */
  public static final void ccInit(PApplet pxOwner) {
    if(pxOwner==null){return;}
    EcComponent.ccSetOwner(pxOwner);
  }//..!
  
  /**
   * <pre>
   * should be called inside draw().
   * both passive part and active part is iterated.
   * trigger queue is polled after passive but before active.
   * thus coordinator for-each all component he holds,
   *   ccUpdate() of components should never
   *   be called directly from draw().
   * </pre>
   */
  static public final void ccUpdate(){
    if(!EcComponent.ccHasOwner()){return;}
    ccUpdatePassive();
    ccUpdateQueue();
    ccUpdateActive();
  }//+++
  
  /**
   * <pre>
   * draws irresponsive UI serve as background layer.
   * this can get separated from draw() loop to setup() for
   *   performance improvement, but it sure left some tedious stuff
   *   for dislay lagging, so,  just a trade off.
   * </pre>
   */
  static public final void ccUpdatePassive(){
    //-- layer I
    for(EcShape it:O_LIST_OF_SHAPE){
      it.ccUpdate();
    }//..~
  }//+++
  
  /**
   * queue polling.<br>
   */
  static public final void ccUpdateQueue(){
    if(!O_QUEUE_OF_LOOP_TRIGGER.isEmpty()){
      O_QUEUE_OF_LOOP_TRIGGER.poll().ccTrigger();
    }//..?
  }//+++
  
  /**
   * responsive UI updating.<br>
   */
  static public final void ccUpdateActive(){
    
    //-- turn on selected inputtable 
    for(EcElement it:O_MAP_OF_INPUTTABLE.values()){
      it.ccSetIsActivated(it.ccGetID()==cmInputFocusID);
    }//..~
    
    //-- layer II
    cmMouseOverID=0;
    for(EcElement it:O_LIST_OF_ELEMENT){
      it.ccUpdate();
      if(it.ccIsMouseHovered()){cmMouseOverID=it.ccGetID();}
    }//..~
  
  }//+++
  
  //===
  
  /**
   * <pre>
   * the coordinator will do the offered job once a time per frame.
   * suppsedly to get called out of the AnimatedThread of PApplet.
   * yes, THE Event Dispatch Thread.
   * </pre>
   * @param pxTrigger to get invoked in the draw() loop
   */
  synchronized static public final
  void ccInvokeLater(EiTriggerable pxTrigger){
    if(pxTrigger==null){return;}
    O_QUEUE_OF_LOOP_TRIGGER.offer(pxTrigger);
  }//+++
  
  /**
   * <pre>
   * supposedly should get called from PApplet.mousePressed().
   * if something goes wrong this can cause a null exception.
   * </pre>
   */
  static public final void ccMousePressed(){
    if(O_MAP_OF_MOUSE_TRIGGER.isEmpty()){return;}
    int lpID=ccGetMouseOverID();
    if(!O_MAP_OF_MOUSE_TRIGGER.containsKey(lpID)){return;}
    O_MAP_OF_MOUSE_TRIGGER.get(lpID).ccTrigger();
  }//+++
  
  /**
   * <pre>
   * supposedly should get called from PApplet.mouseWheel().
   * if something goes wrong this can cause a null exception.
   * </pre>
   * @param pxIsWheelUp event judge shoud be done separately
   * @param pxIsWheelDown event judge shoud be done separately
   */
  static public final
  void ccMouseWheel(boolean pxIsWheelUp, boolean pxIsWheelDown){
    int lpID=ccGetMouseOverID();
    if(pxIsWheelUp){
      if(O_MAP_OF_WHEEL_UP_TRIGGER.isEmpty()){return;}
      if(!O_MAP_OF_WHEEL_UP_TRIGGER.containsKey(lpID)){return;}
      O_MAP_OF_WHEEL_UP_TRIGGER.get(lpID).ccTrigger();
    }else if(pxIsWheelDown){
      if(O_MAP_OF_WHEEL_DOWN_TRIGGER.isEmpty()){return;}
      if(!O_MAP_OF_WHEEL_DOWN_TRIGGER.containsKey(lpID)){return;}
      O_MAP_OF_WHEEL_DOWN_TRIGGER.get(lpID).ccTrigger();
    }else{
      return;
    }//..?
  }//+++
  
  /**
   * prevent from accidently quitting.<br>
   * simply setting PApplet.key to zero.<br>
   * @param pxSketch your main sketch
   */
  static public final void ccGuardEscKey(PApplet pxSketch){
    if(pxSketch==null){return;}
    if(pxSketch.key==27){pxSketch.key=0;}
  }//+++
  
  /**
   * <pre>
   * supposedly should get called from PApplet.keyPressed().
   * if something goes wrong this can cause a null exception.
   * </pre>
   * @param pxKeyCode generated by PApplet.keyPressed()
   */
  static public final void ccKeyPressed(int pxKeyCode){
    if(O_MAP_OF_KEY_TRIGGER.isEmpty()){return;}
    if(!O_MAP_OF_KEY_TRIGGER.containsKey(pxKeyCode)){return;}
    O_MAP_OF_KEY_TRIGGER.get(pxKeyCode).ccTrigger();
  }//+++
  
  //===
  
  /**
   * @param pxElement simply an alias to add()
   */
  static public final void ccAddElement(EcElement pxElement){
    if(pxElement==null){return;}
    if(O_LIST_OF_ELEMENT.contains(pxElement)){return;}
    O_LIST_OF_ELEMENT.add(pxElement);
  }//+++
  
  /**
   * @param pxList simply an alias to addAll()
   */
  static public final void ccAddElement(List<? extends EcElement> pxList){
    if(pxList==null){return;}
    if(pxList.isEmpty()){return;}
    if(O_LIST_OF_ELEMENT.containsAll(pxList)){return;}
    O_LIST_OF_ELEMENT.addAll(pxList);
  }//+++
  
  /**
   * @param pxShape simply an alias to add()
   */
  static public final void ccAddShape(EcShape pxShape){
    if(pxShape==null){return;}
    if(O_LIST_OF_SHAPE.contains(pxShape)){return;}
    O_LIST_OF_SHAPE.add(pxShape);
  }//+++
  
  /**
   * @param pxList simply an alias to addAll()
   */
  static public final void ccAddShape(List<? extends EcShape> pxList){
    if(pxList==null){return;}
    if(pxList.isEmpty()){return;}
    if(O_LIST_OF_SHAPE.containsAll(pxList)){return;}
    O_LIST_OF_SHAPE.addAll(pxList);
  }//+++
  
  /**
   * @param pxGroup do not pass null
   */
  static public final void ccAddGroup(EiGroup pxGroup){
    if(pxGroup==null){return;}
    ccAddShape(pxGroup.ccGiveShapeList());
    ccAddElement(pxGroup.ccGiveElementList());
  }//+++
  
  /**
   * iterates all public fields and add all element and shape to 
   * coordinator's update list.<br>
   * please not that this is just a not that safe and efficient way
   * for test sketch because you will have no control nor filtering.<br>
   * more over it just ignores containers like list or map because 
   * i just don't want to deal with those type erasing things.
   * so for normal application, adding manually or via the group interface
   * is still the preferred way.<br>
   * @param pxOwner the one instance holds local UI components
   */
  static public final void ccAddAll(Object pxOwner){
    VcConst.ccLogln(">>> ssRetrieveAllComponent:");
    if(pxOwner==null){return;}
    Field[] lpDesField = pxOwner.getClass().getFields();
    if(lpDesField==null){return;}
    if(lpDesField.length==0){return;}
    VcConst.ccLogln("got_fields", lpDesField.length);
    for(Field it:lpDesField){
      Object lpSource = VcConst.ccRetrieveField(it, pxOwner);
      if(lpSource instanceof EcElement){
        VcConst.ccLogln(it.getName(),lpSource.getClass().getSimpleName());
        VcLocalCoordinator.ccAddElement((EcElement)lpSource);
      }else
      if(lpSource instanceof EcShape){
        VcConst.ccLogln(it.getName(),lpSource.getClass().getSimpleName());
        VcLocalCoordinator.ccAddShape((EcShape)lpSource);
      }//..?
    }//..~
    VcConst.ccLogln("<<<");
  }//+++
  
  /**
   * @param pxBox will be turned "on" if id matches. input logic is not here.
   */
  static public final void ccAddInputtable(EcElement pxBox){
    if(pxBox==null){return;}
    O_MAP_OF_INPUTTABLE.put(pxBox.ccGetID(), pxBox);
    cmInputIndex=O_MAP_OF_INPUTTABLE.size();
  }//+++
  
  //===
  
  /**
   * 
   * @return id of current mouse hovered element
   */
  static public final int ccGetMouseOverID(){
    return cmMouseOverID;
  }//+++
  
  /**
   * 
   * @return focus id is not zero or "ignore"
   */
  static public final boolean ccHasInputtableFocused(){
    return cmInputFocusID!=0
        && cmInputFocusID!=EcConst.C_ID_IGNORE;
  }//+++
  
  /**
   * 
   * @return #
   */
  static public final int ccGetInputFocusID(){
    return cmInputFocusID;
  }//+++
  
  //===
  
  /**
   * <pre>
   * supposed the key code integer generated by PAapplet.keyPressed().<br>
   *   is exactly the value defined in awt key event.
   * </pre>
   * @param pxKeyCode virtual key value
   * @param pxTrigger don't pass null
   */
  static public final
  void ccRegisterKeyTrigger(int pxKeyCode, EiTriggerable pxTrigger){
    if(pxTrigger==null){return;}
    if(O_MAP_OF_KEY_TRIGGER.containsKey(pxKeyCode)){return;}
    O_MAP_OF_KEY_TRIGGER.put(pxKeyCode, pxTrigger);
  }//+++
  
  /**
   * just an alias to ccRegisterMouseTrigger(id, trigger).<br>
   * @param pxElement #
   * @param pxTrigger #
   */
  static public final 
  void ccRegisterMouseTrigger(EcElement pxElement, EiTriggerable pxTrigger){
    ccRegisterMouseTrigger(pxElement.ccGetID(), pxTrigger);
  }//+++
  
  /**
   * @param pxElementID zero and ignore is omitted
   * @param pxTrigger don't pass null
   */
  static public final
  void ccRegisterMouseTrigger(int pxElementID, EiTriggerable pxTrigger){
    if(pxTrigger==null){return;}
    if(pxElementID==0){return;}
    if(pxElementID==EcConst.C_ID_IGNORE){return;}
    if(O_MAP_OF_MOUSE_TRIGGER.containsKey(pxElementID)){return;}
    O_MAP_OF_MOUSE_TRIGGER.put(pxElementID, pxTrigger);
  }//+++
  
  /**
   * just an alias to ccRegisterWheelUpTrigger(id, trigger).<br>
   * @param pxElement #
   * @param pxTrigger #
   */
  static public final 
  void ccRegisterWheelUpTrigger(EcElement pxElement, EiTriggerable pxTrigger){
    ccRegisterWheelUpTrigger(pxElement.ccGetID(), pxTrigger);
  }//+++
  
  /**
   * @param pxElementID zero and ignore is omitted
   * @param pxTrigger don't pass null
   */
  static public final
  void ccRegisterWheelUpTrigger(int pxElementID, EiTriggerable pxTrigger){
    if(pxTrigger==null){return;}
    if(pxElementID==0){return;}
    if(pxElementID==EcConst.C_ID_IGNORE){return;}
    if(O_MAP_OF_WHEEL_UP_TRIGGER.containsKey(pxElementID)){return;}
    O_MAP_OF_WHEEL_UP_TRIGGER.put(pxElementID, pxTrigger);
  }//+++
  
  /**
   * just an alias to ccRegisterWheelDownTrigger(id, trigger)
   * @param pxElement #
   * @param pxTrigger #
   */
  static public final 
  void ccRegisterWheelDownTrigger(EcElement pxElement, EiTriggerable pxTrigger){
    ccRegisterWheelDownTrigger(pxElement.ccGetID(), pxTrigger);
  }//+++
  
  /**
   * @param pxElementID zero and ignore is omitted
   * @param pxTrigger don't pass null
   */
  static public final
  void ccRegisterWheelDownTrigger(int pxElementID, EiTriggerable pxTrigger){
    if(pxTrigger==null){return;}
    if(pxElementID==0){return;}
    if(pxElementID==EcConst.C_ID_IGNORE){return;}
    if(O_MAP_OF_WHEEL_DOWN_TRIGGER.containsKey(pxElementID)){return;}
    O_MAP_OF_WHEEL_DOWN_TRIGGER.put(pxElementID, pxTrigger);
  }//+++
  
  //===
  
  /**
   * set input focus to ignore id.
   * call this when you think a value has been taken
   */
  public static final void ccClearCurrentInputFocus(){
    cmInputFocusID=EcConst.C_ID_IGNORE;
    cmInputIndex=O_MAP_OF_INPUTTABLE.size();
  }//+++
  
  /**
   * @return ture if focus id is ignored
   */
  public static final boolean ccIsInputCurrentlyFocused(){
    return cmInputFocusID!=EcConst.C_ID_IGNORE;
  }//+++
  
  /**
   * set input focus to current mouse hovered element.
   * supposedly should be called from mousePressed().
   */
  public static final void ccSetCurrentInputFocus(){
    cmInputFocusID=cmMouseOverID;
    ssTransferInputIndex();
  }//+++
  
  private static void ssTransferInputIndex(){
    cmInputIndex=O_MAP_OF_INPUTTABLE.size();
    EcElement lpSource=ccGetCurrentFocusedBox();
    int lpTester=0;
    for(EcElement it:O_MAP_OF_INPUTTABLE.values()){
      if(it==lpSource){cmInputIndex=lpTester;break;}
      lpTester++;
    }//..~
  }//+++
  
  /**
   * set input focus to next indexed one in the list.<br>
   * supposedly should be triggered by pressing [tab] key.<br>
   */
  public static final void ccToNextInputIndex(){
    cmInputIndex++;
    ssRefreshInputUI();
  }//+++
  
  /**
   * set input focus to previous indexed one in the list.<br>
   * supposedly should be triggered by pressing [shift+tab].<br>
   * for processing you might need a pretty tricky work around
   * to make that happen so it is okay to just not to use this method.<br>
   */
  public static final void ccToPreviousInputIndex(){
    cmInputIndex--;
    ssRefreshInputUI();
  }//+++
  
  private static void ssRefreshInputUI(){
    int lpSize=O_MAP_OF_INPUTTABLE.size();
    if(cmInputIndex>lpSize){cmInputIndex=0;}
    if(cmInputIndex<0){cmInputIndex=lpSize;}
    if(cmInputIndex==lpSize){cmInputFocusID=EcConst.C_ID_IGNORE;return;}
    EcElement[] lpArray =new EcElement[lpSize];
    O_MAP_OF_INPUTTABLE.values().toArray(lpArray);
    EcElement lpBox=lpArray[cmInputIndex];
    if(lpBox!=null){cmInputFocusID=lpBox.ccGetID();}
  }//+++
  
  /**
   * 
   * @return null if there is nothing focused so you have to check
   */
  public static final EcElement ccGetCurrentFocusedBox(){
    if(cmInputFocusID==0
     ||cmInputFocusID==EcConst.C_ID_IGNORE
    ){return null;}
    return O_MAP_OF_INPUTTABLE.get(cmInputFocusID);
  }//+++
  
}//***eof
