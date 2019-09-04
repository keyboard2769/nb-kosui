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

package kosui.pppswingui;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import kosui.ppputil.VcConst;

/**
 * i think swing is still an excellent library for dirty case. <br>
 * and this library is that dirty case. <br>
 */
public final class ScFactory {
  
  private ScFactory(){}//++!
  
  //=== container
  
  
  /**
   * alias for both constructor.<br>
   * has an etched border by default.<br>
   * @return #
   */
  public static final JPanel ccCreateBorderPanel(){
    JPanel lpRes=new JPanel(new BorderLayout());
    lpRes.setBorder(BorderFactory.createEtchedBorder());
    return lpRes;
  }//+++
  
  /**
   * alias for both constructor.<br>
   * has an etched border by default.<br>
   * @param pxGap will be passed to both v and h for border layout
   * @return #
   */
  public static final JPanel ccCreateBorderPanel(int pxGap){
    JPanel lpRes=new JPanel(new BorderLayout(pxGap, pxGap));
    lpRes.setBorder(BorderFactory.createEtchedBorder());
    return lpRes;
  }//+++
  
  /**
   * alias for constructor
   * @param pxGap will be passed to both v and h for border layout
   * @param pxColor of line border and if null passed it will be dim white
   * @param pxWeight of line border
   * @return #
   */
  public static final
  JPanel ccCreateBoderPanel(int pxGap, Color pxColor,int pxWeight){
    JPanel lpRes=ScFactory.ccCreateBorderPanel(pxGap);
    lpRes.setBorder(BorderFactory.createLineBorder(
      pxColor==null?ScConst.DIM_WHITE:pxColor, pxWeight
    ));
    return lpRes;
  }//+++
  
  /**
   * alias for constructor
   * @param pxGap will be passed to both v and h for border layout
   * @param pxTitle border title and if nothing is it we set something for you
   * @return #
   */
  public static final
  JPanel ccCreateBorderPanel(int pxGap, String pxTitle){
    JPanel lpRes=ScFactory.ccCreateBorderPanel(pxGap);
    lpRes.setBorder(BorderFactory.createTitledBorder(
      VcConst.ccIsValidString(pxTitle)?pxTitle:"<?>"
    ));
    return lpRes;
  }//+++
  
  //== container ** flow panel
  
  /**
   * alias for constructor
   * @param pxGap #
   * @param pxDoesAlignRight #
   * @return #
   */
  public static final
  JPanel ccCreateFlowPanel(int pxGap, boolean pxDoesAlignRight){
    return new JPanel(new FlowLayout(
      pxDoesAlignRight?FlowLayout.RIGHT:FlowLayout.LEFT,pxGap, pxGap
    ));
  }//+++
  
  /**
   * alias for constructor
   * @param pxGap #
   * @param pxDoesAlignRight #
   * @param pxBorderColor for the border if null passed it will be dim white
   * @param pxBorderWeight #
   * @return #
   */
  public static final JPanel ccMyFlowPanel(
    int pxGap, boolean pxDoesAlignRight, Color pxBorderColor,int pxBorderWeight
  ){
    JPanel lpRes=ScFactory.ccCreateFlowPanel(pxGap, pxDoesAlignRight);
    lpRes.setBorder(BorderFactory.createLineBorder(
      pxBorderColor==null?ScConst.DIM_WHITE:pxBorderColor, pxBorderWeight
    ));
    return lpRes;
  }//+++
  
  /**
   * alias of construction
   * @param pxGap #
   * @param pxDoesAlignRight #
   * @param pxTitle of titled border if nothing is it we set something for you
   * @return #
   */
  public static final
  JPanel ccCreateFlowPanel(int pxGap, boolean pxDoesAlignRight, String pxTitle){
    JPanel lpRes=ScFactory.ccCreateFlowPanel(pxGap, pxDoesAlignRight);
    lpRes.setBorder(BorderFactory.createTitledBorder(
      VcConst.ccIsValidString(pxTitle)?pxTitle:"<?>"
    ));
    return lpRes;
  }//+++
  
  //== container ** grid panel
  
  /**
   * alias of construction
   * @param pxRow #
   * @param pxColumn #
   * @return #
   */
  public static final JPanel ccCreateGridPanel(int pxRow, int pxColumn){
    return new JPanel(new GridLayout(pxRow, pxColumn));
  }//+++
  
  /**
   * alias of construction
   * @param pxRow #
   * @param pxColumn #
   * @param pxBorderColor for the border if null passed it will be dim white
   * @param pxBorderWeight #
   * @return #
   */
  public static final JPanel ccCreateGridPanel(
    int pxRow, int pxColumn, Color pxBorderColor,int pxBorderWeight
  ){
    JPanel lpRes=ScFactory.ccCreateGridPanel(pxRow, pxColumn);
    lpRes.setBorder(BorderFactory.createLineBorder(
      pxBorderColor==null?ScConst.DIM_WHITE:pxBorderColor, pxBorderWeight
    ));
    return lpRes;
  }//+++
    
  /**
   * alias of construction
   * @param pxRow #
   * @param pxColumn #
   * @param pxTitle of titled border if nothing is it we set something for you
   * @return #
   */
  public static final
  JPanel ccCreateGridPanel(int pxRow, int pxColumn, String pxTitle){
    JPanel lpRes=ScFactory.ccCreateGridPanel(pxRow, pxColumn);
    lpRes.setBorder(BorderFactory.createTitledBorder(
      VcConst.ccIsValidString(pxTitle)?pxTitle:"<?>"
    ));
    return lpRes;
  }//+++
  
  /**
   * ability of floating will be set to false. <br>
   * rollover will be set to false. <br>
   * @return #
   */
  public static final JToolBar ccCreateStuckedToolBar(){
    JToolBar lpRes=new JToolBar("nc");
    lpRes.setFloatable(false);
    lpRes.setRollover(false);
    return lpRes;
  }//+++
  
  //=== input UI ** button
  
  /**
   * only color and margin is configured
   * @param pxTarget don't pass null
   * @param pxW pix
   * @param pxH pix
   */
  public static final
  void ccSetupCommandButton(JButton pxTarget,int pxW, int pxH){
    if(pxTarget==null){return;}
    pxTarget.setBackground(ScConst.C_PBL_BACK_OFF);
    pxTarget.setForeground(ScConst.C_PBL_FORE);
    pxTarget.setMargin(new Insets(2, 2, 2, 2));
    if(pxW>0 && pxH>0){
      pxTarget.setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  /**
   * alias for ccSetupCommandButton() with construction
   * @param pxText will get passed directly to constructor
   * @param pxW pix
   * @param pxH pix
   * @return 
   */
  public static final JButton ccCreateCommandButton(
    String pxText, int pxW, int pxH
  ){
    JButton lpRes=new JButton(pxText);
    ccSetupCommandButton(lpRes,pxW,pxH);
    return lpRes;
  }//+++
  
  /**
   * alias for ccSetupCommandButton() with construction
   * @param pxText will get passed directly to constructor
   * @return #
   */
  public static final JButton ccCreateCommandButton(String pxText){
    return ccCreateCommandButton(pxText, -1, -1);
  }//+++
  
  //=== input UI ** toggler
  
  /**
   * only color and margin is configured
   * @param pxTarget don't pass null
   * @param pxW pix
   * @param pxH pix
   */
  public static final void ccSetupCommandToggler(
    JToggleButton pxTarget, int pxW, int pxH
  ){
    if(pxTarget==null){return;}
    pxTarget.setBackground(ScConst.C_TOGGLE_BACK);
    pxTarget.setForeground(ScConst.C_TOGGLE_FORE);
    pxTarget.setMargin(new Insets(2, 2, 2, 2));
    if(pxW>0 && pxH>0){
      pxTarget.setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  /**
   * alias for ccSetupCommandToggler() with construction
   * @param pxText will get passed directly to constructor
   * @param pxW pix
   * @param pxH pix
   * @return #
   */
  public static final JToggleButton ccCreateCommandToggler(
    String pxText, int pxW, int pxH
  ){
    JToggleButton lpRes = new JToggleButton(pxText);
    ccSetupCommandToggler(lpRes, pxW, pxH);
    return lpRes;
  }//+++
  
  /**
   * alias for ccSetupCommandToggler() with construction
   * @param pxText will get passed directly to constructor
   * @return #
   */
  public static final JToggleButton ccCreateCommandToggler(String pxText){
    return ccCreateCommandToggler(pxText, -1, -1);
  }//+++
  
  //=== input UI ** combo box
  
  /**
   * only color is configured this time
   * @param pxTarget don't pass null
   * @param pxW pix
   * @param pxH pix
   */
  public static final
  void ccSetupNothch(JComboBox pxTarget, int pxW, int pxH){
    pxTarget.setBackground(ScConst.C_COMBO_BACK);
    if(pxW>0 && pxH>0){
      pxTarget.setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  /**
   * alias for ccSetupNothch() with construction
   * @param pxUpper must have something
   * @param pxMiddle if null passed you got the two notch 
   * @param pxLower must have something
   * @param pxW pix
   * @param pxH pix
   * @return 
   */
  public static final JComboBox<String> ccCreateNotch(
    String pxUpper, String pxMiddle, String pxLower, 
    int pxW, int pxH
  ){
    String[] lpModel;
    if(pxMiddle==null){
      lpModel=new String[]{
        VcConst.ccIsValidString(pxUpper)?pxUpper:"<u?>",
        VcConst.ccIsValidString(pxLower)?pxLower:"<l?>"
      };
    }else{
      lpModel=new String[]{
        VcConst.ccIsValidString(pxUpper)?pxUpper:"<u?>",
        pxMiddle.isEmpty()?"<m?>":pxMiddle,
        VcConst.ccIsValidString(pxLower)?pxLower:"<l?>"
      };
    }//..?
    JComboBox<String> lpRes=new JComboBox<String>(lpModel);
    ccSetupNothch(lpRes, pxW, pxH);
    return lpRes;
  }//+++
  
  /**
   * alias for ccSetupNothch() with construction
   * @param pxUpper must have something
   * @param pxMiddle if null passed you got the two notch 
   * @param pxLower must have something
   * @return #
   */
  public static final JComboBox<String> ccCreateNotch(
    String pxUpper, String pxMiddle, String pxLower
  ){return ccCreateNotch(pxUpper, pxMiddle, pxLower,-1,-1);}//+++
  
  //== input UI ** text box
  
  //[plan]::ccSetupInputBox()
  
  //== outout UI ** stucked 
  
  /**
   * alias to constructor.<br>
   * @return #
   */
  public static JSeparator ccCreateHrizontalSeparator(){
    return new JSeparator(SwingConstants.HORIZONTAL);
  }//+++
  
  /**
   * alias to constructor.<br>
   * @return #
   */
  public static JSeparator ccCreateVerticalSeparator(){
    return new JSeparator(SwingConstants.VERTICAL);
  }//+++
  
  /**
   * alias to constructor.<br>
   * @param ccIsVertical #
   * @return #
   */
  public static JSeparator ccCreateSeparator(boolean ccIsVertical){
    return ccIsVertical?
      ccCreateVerticalSeparator()
     :ccCreateHrizontalSeparator();
  }//+++
  
  //== outout UI ** text lamp
  
  /**
   * color and ability and text align is configured.<br>
   * @param pxTarget don't pass null
   * @param pxW pix
   * @param pxH pix
   */
  public static final
  void ccSetupTextLamp(JTextField pxTarget, int pxW, int pxH){
    if(pxTarget==null){return;}
    pxTarget.setEditable(false);
    pxTarget.setEnabled(false);
    pxTarget.setBackground(ScConst.C_TPL_BACK_OFF);
    pxTarget.setForeground(ScConst.C_TPL_FORE);
    pxTarget.setDisabledTextColor(ScConst.C_TPL_FORE);
    pxTarget.setHorizontalAlignment(JTextField.CENTER);
    if(pxW>0 && pxH>0){
      pxTarget.setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  /**
   * color and ability and text align is configured.<br>
   * @param pxTarget don't pass null
   * @param pxW pix
   * @param pxH pix
   */
  public static final
  void ccSetupStatusBar(JTextField pxTarget, int pxW, int pxH){
    if(pxTarget==null){return;}
    pxTarget.setEditable(false);
    pxTarget.setEnabled(false);
    pxTarget.setBackground(Color.LIGHT_GRAY);
    pxTarget.setForeground(ScConst.DARK_GREEN);
    pxTarget.setDisabledTextColor(ScConst.DARK_GREEN);
    pxTarget.setHorizontalAlignment(JTextField.RIGHT);
    if(pxW>0 && pxH>0){
      pxTarget.setPreferredSize(new Dimension(pxW, pxH));
    }//..?
  }//+++
  
  /**
   * alias for ccSetupCommandButton() with construction
   * @param pxText if nothing is it we set something for you
   * @param pxW pix
   * @param pxH pix
   * @return #
   */
  public static final
  JTextField ccCreateTextLamp(String pxText, int pxW, int pxH){
    JTextField lpRes=new JTextField(
      VcConst.ccIsValidString(pxText)?pxText:"<?>"
    );
    ccSetupTextLamp(lpRes, pxW, pxH);
    return lpRes;
  }//+++
  
  /**
   * alias for ccSetupCommandButton() with construction
   * @param pxText if nothing is it we set something for you
   * @return #
   */
  public static final
  JTextField ccCreateTextLamp(String pxText){
    JTextField lpRes=new JTextField(
      VcConst.ccIsValidString(pxText)?pxText:"<?>"
    );
    ccSetupTextLamp(lpRes, -1, -1);
    return lpRes;
  }//+++
  
  //== outout UI ** console area
  
  /**
   * color and ability and text align is configured
   * @param pxTarget don't pass null
   */
  public static final void ccSetupConsoleArea(JTextArea pxTarget){
    pxTarget.setBackground(Color.LIGHT_GRAY);
    pxTarget.setDisabledTextColor(Color.DARK_GRAY);
    pxTarget.setEditable(false);
    pxTarget.setEnabled(false);
  }//+++
  
  //== component ** misc
  
  /**
   * <pre>
   * more convenient to have a handy content 
   *   when you still don't know what to show.
   * well maybe the problem is i always build layout first.
   * </pre>
   * @param pxW width of preferred size or zero will not invoke the setter
   * @param pxH height of preferred size or zero will not invoke the setter
   * @return #
   * @deprecated only for test use
   */
  @Deprecated public static final
  JScrollPane tstCreateDummyList(int pxW, int pxH){
    DefaultListModel<String> lpModel=new DefaultListModel();
    lpModel.addElement("--replace this!!");
    lpModel.addElement("uno");
    lpModel.addElement("!dos");
    lpModel.addElement("tre!"); 
    lpModel.addElement("?quatro?"); 
    JScrollPane lpRes=new JScrollPane(new JList(lpModel));
    if(pxW>0 && pxH>0){lpRes.setPreferredSize(new Dimension(pxW, pxH));}
    return lpRes;
  }//+++
  
}//***eof
