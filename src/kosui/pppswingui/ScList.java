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
import javax.swing.event.ListSelectionListener;

/**
 * it actually pissed me off at first i create every pane for a list. <br>
 * know i see separate them is not a bad idea at all. <br>
 */
public final class ScList extends JScrollPane {

  private final DefaultListModel<String> cmModel;

  private final JList<String> cmList;

  //===
  
  /**
   * auto size version
   */
  public ScList() {this(0, 0);}
  
  /**
   * 
   * @param pxW width
   * @param pxH height
   */
  public ScList(int pxW, int pxH) {
    super();
    cmModel = new DefaultListModel();
    cmList = new JList(cmModel);
    setViewportView(cmList);
    if (pxW > 0 && pxH > 0) {
      setPreferredSize(new Dimension(pxW, pxH));
    }
  }//+++

  //===
  
  /**
   * wrapper for JList::addElement(). 
   * @param pxElement #
   */
  public final void ccAdd(String pxElement) {
    cmModel.addElement(pxElement);
  }//+++

  /**
   * wrapper for JList::addListSelectionListener(). 
   * @param pxListener #
   */
  public final void ccAddListSelectionListener
    (ListSelectionListener pxListener)
  { cmList.addListSelectionListener(pxListener);}//+++

  /**
   * wrapper for JList::addMouseListener(). 
   * if you just add a listener to ScList which is
   * the pane of real list, you can never get any mouse clicked
   * event fired by that listener.
   * @param pxListener #
   */
  public final void ccAddMouseListener(MouseListener pxListener) {
    cmList.addMouseListener(pxListener);
  }//+++

  /**
   * loops every thing manually, may cause overheads.
   * @param pxList does check for null and emptiness
   */
  public final void ccSetModel(String[] pxList) {
    if(pxList==null){return;}
    if(pxList.length==0){return;}
    cmModel.clear();
    for (String it : pxList) {
      cmModel.addElement(it);
    }//..~
  }//+++

  //===
  
  /**
   * wrapper for JList::setSelectedIndex()
   * @param pxIndex does not check for boundary
   */
  public final void ccSetSelectedIndex(int pxIndex){
    cmList.setSelectedIndex(pxIndex);
  }//+++
  
  //===
  
  /**
   * wrapper for JList::getSelectedIndex()
   * @return #
   */
  public final int ccGetCurrentIndex() {
    return cmList.getSelectedIndex();
  }//+++

  /**
   * wrapper for JList::get(). 
   * will return the zero-th item if nothing is selected(-1). 
   * @return could be null
   */
  public final String ccGetCurrentItem() {
    int lpIndex=ccGetCurrentIndex();
    return cmModel.get(lpIndex<0?0:lpIndex);
  }//+++

  /**
   * 
   * @return the JList
   */
  public final Object ccGetEventSource() {
    return cmList;
  }//+++

}//***eof
