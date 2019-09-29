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

import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;
import kosui.ppputil.VcConst;

/**
 * it actually pissed me off at first i create every pane for a list. <br>
 * know i see separate them is not a bad idea at all. <br>
 */
public final class ScList extends JScrollPane {

  private final JList<String> cmList;

  //===
  
  /**
   * auto size version
   */
  public ScList() {
    this(0, 0);
  }//..!
  
  /**
   * #
   * @param pxW pix
   * @param pxH pix
   */
  public ScList(int pxW, int pxH) {
    super();
    cmList = new JList(new DefaultListModel());
    setViewportView(cmList);
    if (pxW > 0 && pxH > 0) {
      setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  /**
   * @param cmMode if null passed an empty default list model will be set
   * @param pxW pix
   * @param pxH pix
   */
  public ScList(ListModel<String> cmMode,int pxW, int pxH){
    super();
    if(cmMode==null){
      cmList = new JList(new DefaultListModel());
    }else{
      cmList = new JList(cmMode);
    }//+++
    setViewportView(cmList);
    if (pxW > 0 && pxH > 0) {
      setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  //===
  
  /**
   * wrapper for JList::addListSelectionListener().<br>
   * @param pxListener #
   */
  public final
  void ccAddListSelectionListener(ListSelectionListener pxListener){
    if(pxListener==null){return;}
    cmList.addListSelectionListener(pxListener);
  }//+++
  
  /**
   * <pre>
   * wrapper for JList::addMouseListener(). 
   * if you just add a listener to ScList which is
   *   a scraoll pane actually, you can never get any mouse clicked
   *   event fired by that listener.
   * </pre>
   * @param pxListener #
   */
  public final void ccAddMouseListener(MouseListener pxListener) {
    if(pxListener==null){return;}
    cmList.addMouseListener(pxListener);
  }//+++
  
  /**
   * thus the implementation of DefaultListModel might not match every 
   * situation this is the watch-back solution.<br>
   * @param pxModel #
   */
  public final void ccSetModel(ListModel<String> pxModel){
    if(pxModel==null){return;}
    cmList.setModel(pxModel);
  }//+++
  
  /**
   * wrapper for DefaultListModel::addElement().<br>
   * if current model is not DefaultListModel it will do nothing.<br>
   * @param pxElement #
   */
  public final void ccAddElement(String pxElement) {
    if(!VcConst.ccIsValidString(pxElement)){return;}
    ListModel lpModel=cmList.getModel();
    if(lpModel == null){return ;}
    if(lpModel instanceof DefaultListModel){
      ((DefaultListModel)lpModel).addElement(pxElement);
    }//..?
  }//+++
  
  /**
   * wrapper for JList::setSelectedIndex()<br>
   * @param pxIndex does not check for boundary
   */
  public final void ccSetSelectedIndex(int pxIndex){
    cmList.setSelectedIndex(pxIndex);
  }//+++
  
  //===
  
  /**
   * wrapper for JList::getSelectedIndex().<br>
   * @return #
   */
  public final int ccGetCurrentIndex() {
    return cmList.getSelectedIndex();
  }//+++

  /**
   * wrapper for DefaultListModel::get().<br>
   * if current model is not DefaultListModel it will do nothing.<br>
   * will return the zero-th item if nothing is selected(-1).<br>
   * for those out modeled list, item must get accessed directly from that
   * model through ccGetCurrentIndex() but not specific list component.<br>
   * @return never null
   */
  public final String ccGetCurrentItem() {
    int lpIndex=ccGetCurrentIndex();
    ListModel cmModel = cmList.getModel();
    if(cmModel == null){return "<?>";}
    if(cmModel instanceof DefaultListModel){
      return ((DefaultListModel)cmModel).get(lpIndex<0?0:lpIndex).toString();
    }else{
      return "<?>";
    }//..?
  }//+++

  /**
   * @return the list component
   */
  public final Object ccGetEventSource() {
    return cmList;
  }//+++
  
  /**
   * calls updateUI() than repaint().<br>
   */
  public final void ccRefresh(){
    if(!ScConst.ccIsEDT()){return;}
    cmList.updateUI();
    cmList.repaint();
    updateUI();
    repaint();
  }//+++

}//***eof
