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
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import processing.core.PApplet;
import kosui.ppputil.VcConst;

/**
 * i think swing is still an excellent library for dirty case. <br>
 * and this library is that dirty case. <br>
 */
public final class ScFactory {
  
  private static PApplet pbOwner=null;
  
  //===
  
  /**
   * may got returned to any null-able string return value. <br>
   * preset but not necessarily as an empty string. <br>
   */
  public static final String
    C_M_INVALID = "";
  
  /**
   * predefined colors
   */
  public static final Color
    DIM_WHITE     =Color.decode("#DDDDDD"),
    
    
    WATER         =Color.decode("#6699CC"),
    
    DARK_YELLOW   =Color.decode("#666633"),
    DARK_PURPLE   =Color.decode("#663366"),
    DARK_ORANGE   =Color.decode("#996633"),
    DARK_BLUE     =Color.decode("#336666"),
    DARK_RED      =Color.decode("#991111"),
    DARK_GREEN    =Color.decode("#119911")
  ;//--
  
  private static final Color
    C_COMBO_BACK  =Color.decode("#EEEECC"),
    C_TOGGLE_BACK =Color.decode("#99AAEE"),
    C_TOGGLE_FORE =Color.decode("#777744")
  ;//--
  
  private static final JFileChooser
    O_FILE_CHOOSER = new JFileChooser();
  
  //=== constructor
  
  private ScFactory(){}//++!
  
  /**
   * this is used for getting the frame of your sketch 
   * and it will be the owner of dialog. <br>
   * but for some JWindow, their owner should be set separately.<br>
   * i know having a owner to be a frame of PApplet or just null
   * may change the behavior of swing component depends on 
   * operating system, but i don't what the different exactly is,
   * and why.<br>
   * also sets up some system embedded dialog such as file chooser
   * and color chooser.<br>
   * @param pxOwner your sketch
   */
  public static void ccInit(PApplet pxOwner){
    pbOwner=pxOwner;
    if(pxOwner!=null){
      final String lpCurrentPath=pxOwner.sketchPath;
      SwingUtilities.invokeLater(new Runnable() {
        @Override public void run(){
          ccSetFileChooserDefualtFolder(lpCurrentPath);
        }
      });
    }
  }//+++
  
  //== modifier
  
  /**
   * also initiate some status of file chooser
   * @param lpFolderPath #
   */
  public static final void ccSetFileChooserDefualtFolder
    (String lpFolderPath)
  { if(!VcConst.ccIsValidString(lpFolderPath)){return;}
    File lpFile=new File(lpFolderPath);
    if(ccIsEDT()){
      O_FILE_CHOOSER.setDialogType(JFileChooser.OPEN_DIALOG);
      O_FILE_CHOOSER.setMultiSelectionEnabled(false);
      O_FILE_CHOOSER.setDragEnabled(false);
      O_FILE_CHOOSER.setFileHidingEnabled(false);
      if(lpFile.isDirectory()){
        O_FILE_CHOOSER.setCurrentDirectory(lpFile);
      }else{
        System.err.println(
          ".ScFactory.ccSetFileChooserDefualtFolder()::"
          +"passed pass is not directory!"
        );
      }
    }
  }//+++
  
  //== container
  
  //== container ** panel
  
  //== container ** border panel
  
  /**
   * 
   * @param pxGap will be passed to both v and h for border layout
   * @return #
   */
  public static final JPanel ccMyBorderPanel(int pxGap)
    {return new JPanel(new BorderLayout(pxGap, pxGap));}//+++
  
  /**
   * 
   * @param pxGap will be passed to both v and h for border layout
   * @param pxColor of line border
   * @param pxWeight of line border
   * @return #
   */
  public static final JPanel ccMyBorderPanel
    (int pxGap, Color pxColor,int pxWeight)
  { JPanel lpRes=ccMyBorderPanel(pxGap);
    lpRes.setBorder(BorderFactory.createLineBorder(pxColor, pxWeight));
    return lpRes;
  }//+++
  
  /**
   * 
   * @param pxGap will be passed to both v and h for border layout
   * @param pxTitle of titled border
   * @return #
   */
  public static final JPanel ccMyBorderPanel
    (int pxGap, String pxTitle)
  { JPanel lpRes=ccMyBorderPanel(pxGap);
    lpRes.setBorder(BorderFactory.createTitledBorder(pxTitle));
    return lpRes;
  }//+++
  
  //== container ** flow panel
  
  /**
   * 
   * @param pxGap #
   * @param pxDoesAlignRight #
   * @return #
   */
  public static final JPanel ccMyFlowPanel
    (int pxGap, boolean pxDoesAlignRight)
  { return new JPanel(new FlowLayout
      (pxDoesAlignRight?FlowLayout.RIGHT:FlowLayout.LEFT,pxGap, pxGap));
  }//+++
  
  /**
   * 
   * @param pxGap #
   * @param pxDoesAlignRight #
   * @param pxColor #
   * @param pxWeight #
   * @return #
   */
  public static final JPanel ccMyFlowPanel
    (int pxGap, boolean pxDoesAlignRight, Color pxColor,int pxWeight)
  { JPanel lpRes=ccMyFlowPanel(pxGap, pxDoesAlignRight);
    lpRes.setBorder(BorderFactory.createLineBorder(pxColor, pxWeight));
    return lpRes;
  }//+++
  
  /**
   * 
   * @param pxGap #
   * @param pxDoesAlignRight #
   * @param pxTitle #
   * @return #
   */
  public static final JPanel ccMyFlowPanel
    (int pxGap, boolean pxDoesAlignRight, String pxTitle)
  { JPanel lpRes=ccMyFlowPanel(pxGap, pxDoesAlignRight);
    lpRes.setBorder(BorderFactory.createTitledBorder(pxTitle));
    return lpRes;
  }//+++
  
  //== container ** grid panel
  
  /**
   * 
   * @param pxRow #
   * @param pxColumn #
   * @return #
   */
  public static final JPanel ccMyGridPanel(int pxRow, int pxColumn)
     {return new JPanel(new GridLayout(pxRow, pxColumn));}//+++
  
  /**
   * 
   * @param pxRow #
   * @param pxColumn #
   * @param pxColor #
   * @param pxWeight #
   * @return #
   */
  public static final JPanel ccMyGridPanel
    (int pxRow, int pxColumn, Color pxColor,int pxWeight)
  { JPanel lpRes=ccMyGridPanel(pxRow, pxColumn);
    lpRes.setBorder(BorderFactory.createLineBorder(pxColor, pxWeight));
    return lpRes;
  }//+++
    
  /**
   * 
   * @param pxRow #
   * @param pxColumn #
   * @param pxTitle #
   * @return #
   */
  public static final JPanel ccMyGridPanel(int pxRow, int pxColumn, String pxTitle){
    JPanel lpRes=ccMyGridPanel(pxRow, pxColumn);
    lpRes.setBorder(BorderFactory.createTitledBorder(pxTitle));
    return lpRes;
  }//+++
  
  //== container ** toolbar
  
  /**
   * floatable will be set to false. <br>
   * rollover will be set to false. <br>
   * @return #
   */
  public static final JToolBar ccMyStuckedToolBar(){
    JToolBar lpRes=new JToolBar("nc");
    lpRes.setFloatable(false);
    lpRes.setRollover(false);
    return lpRes;
  }//+++
  
  //== component
  
  //== component ** button
  
  /**
   * 
   * @param pxName will also be the action command
   * @return #
   */
  public static final JButton ccMyCommandButton
    (String pxName)
  { JButton lpRes=new JButton(pxName);
    lpRes.setBackground(Color.LIGHT_GRAY);
    lpRes.setForeground(Color.DARK_GRAY);
    lpRes.setMargin(new Insets(2, 2, 2, 2));
    return lpRes;
  }//+++
  
  /**
   * 
   * @param pxName #
   * @param pxCommand action command
   * @param pxListener action listener
   * @return #
   */
  public static final JButton ccMyCommandButton
    (String pxName, String pxCommand, ActionListener pxListener)
  { JButton lpRes=ccMyCommandButton(pxName);
    lpRes.setActionCommand(pxCommand);
    lpRes.addActionListener(pxListener);
    return lpRes;
  }//+++
  
  /**
   * 
   * @param pxName #
   * @param pxCommand #
   * @param pxW #
   * @param pxH #
   * @return #
   */
  public static final JButton ccMyCommandButton
    (String pxName, String pxCommand, int pxW, int pxH)
  { JButton lpRes=ccMyCommandButton(pxName);
    lpRes.setActionCommand(pxCommand);
    lpRes.setPreferredSize(new Dimension(pxW, pxH));
    return lpRes;
  }//+++
  
  //== component ** notch
  
  /**
   * 
   * @param pxName #
   * @param pxCommand action command
   * @param pxListener action listener
   * @return #
   */
  public static final JToggleButton ccMyCommandToggle(
    String pxName, String pxCommand, ActionListener pxListener
  ){
    JToggleButton lpRes =new JToggleButton(pxName);
    lpRes.setActionCommand(pxCommand);
    lpRes.addActionListener(pxListener);
    lpRes.setBackground(C_TOGGLE_BACK);
    lpRes.setForeground(C_TOGGLE_FORE);
    lpRes.setMargin(new Insets(2, 2, 2, 2));
    return lpRes;
  }//+++
  
  /**
   * 
   * @param pxList the model
   * @param pxCommand action command
   * @param pxListener action listener
   * @return #
   */
  public static final JComboBox<String> ccMyCommandComboBox(
    String[] pxList, String pxCommand, ActionListener pxListener
  ){
    JComboBox<String> lpRes=new JComboBox<String>(pxList);
    lpRes.setActionCommand(pxCommand);
    lpRes.addActionListener(pxListener);
    lpRes.setBackground(C_COMBO_BACK);
    return lpRes;
  }//+++
  
  //== component ** textfield

  /**
   * not editable nor enabled. <br>
   * with fixed color set, aligned in center. <br>
   * @param pxInitText #
   * @param pxW width of preferred size or zero will not invoke the setter
   * @param pxH height of preferred size or zero will not invoke the setter
   * @return #
   */
  public static final JTextField ccMyTextLamp
    (String pxInitText, int pxW, int pxH)
  { JTextField lpRes=new JTextField(pxInitText, 0);
    lpRes.setEditable(false);
    lpRes.setEnabled(false);
    lpRes.setBackground(Color.DARK_GRAY);
    lpRes.setDisabledTextColor(Color.GREEN);
    lpRes.setHorizontalAlignment(JTextField.CENTER);
    if(pxW>0 && pxH>0){lpRes.setPreferredSize(new Dimension(pxW, pxH));}
    return lpRes;
  }//+++
  
  /**
   * with fixed color set, aligned in right. <br>
   * @param pxInitText #
   * @param pxW width of preferred size or zero will not invoke the setter
   * @param pxH height of preferred size or zero will not invoke the setter
   * @return #
   */
  public static final JTextField ccMyTextContainer
    (String pxInitText,int pxW,int pxH)
  { JTextField lpRes=ccMyTextLamp(pxInitText, pxW, pxH);
    lpRes.setBackground(DIM_WHITE);
    lpRes.setDisabledTextColor(DARK_GREEN);
    lpRes.setHorizontalAlignment(JTextField.RIGHT);
    return lpRes;
  }//+++
  
  /**
   * have a different fore color.
   * @param pxInitText #
   * @param pxW width of preferred size or zero will not invoke the setter
   * @param pxH height of preferred size or zero will not invoke the setter
   * @return #
   */
  public static final JTextField ccMyTextBox
    (String pxInitText,int pxW,int pxH)
  { JTextField lpRes=ccMyTextContainer(pxInitText, pxW, pxH);
    lpRes.setDisabledTextColor(Color.DARK_GRAY);
    return lpRes;
  }//+++
  
  //== component ** misc
  
  /**
   * more convenient to have a handy content 
   * when you still don't know what to show. <br>
   * i always build layout first. <br>
   * @param pxW width of preferred size or zero will not invoke the setter
   * @param pxH height of preferred size or zero will not invoke the setter
   * @return actually a scroll pane
   */
  public static final JScrollPane ccMyDummyScrollList
    (int pxW, int pxH)
  { DefaultListModel<String> lpModel=new DefaultListModel();
    lpModel.addElement("--replace this!!");
    lpModel.addElement("uno");
    lpModel.addElement("!dos");
    lpModel.addElement("tre!"); 
    lpModel.addElement("?quatro?"); 
    JScrollPane lpRes=new JScrollPane(new JList(lpModel));
    if(pxW>0 && pxH>0){lpRes.setPreferredSize(new Dimension(pxW, pxH));}
    return lpRes;
  }//+++
  
  //== dialog
  
  /**
   * will be blocked out from event dispatch thread
   * @param pxMessage #
   */
  public static final void ccMessageBox
    (String pxMessage)
  { if(ccIsEDT()){
      JOptionPane.showMessageDialog
        (pbOwner==null?null:pbOwner.frame,pxMessage);
    }//+++
    else{System.err.println("..ccMessageBox()::"+pxMessage);}
  }//+++
  
  /**
   * this version calls the invoke later method for you using a
   * dirty way with some extra overhead, but is convenient when you just 
   * wanna show something directly from your sketch.
   * @param pxOwner your sketch
   * @param pxMessage #
   */
  public static final void ccMessageBox
    (PApplet pxOwner, String pxMessage)
  { final Frame lpOwner=(pxOwner==null?null:pxOwner.frame);
    final String lpBuf=pxMessage;
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        JOptionPane.showMessageDialog(lpOwner,lpBuf);
      }//+++
    });
  }//+++
  
  /**
   * will be blocked out from event dispatch thread.<br>
   * there will never be a version does invoke later
   * for you because your should decide if invoke and wait is necessary
   * or not and when and how to use the returned string.<br>
   * @param pxBrief a short message you describe about what you want
   * @param pxDefault will be in the input box before getting input
   * @return #
   */
  public static final String ccGetStringFromInputBox
    (String pxBrief, String pxDefault)
  { if(ccIsEDT()){
      String lpRes=JOptionPane.showInputDialog
        (pbOwner==null?null:pbOwner.frame, pxBrief, pxDefault);
      if(VcConst.ccIsValidString(lpRes)){return lpRes;}
      else{return "<nc/>";}
    } return "<oedt/>";
  }//+++
  
  /**
   * will be blocked out from event dispatch thread.<br>
   * this thing iterates through the whole list to find the index.<br>
   * if you don't want those overheads just don't use this.
   * @param pxBrief a short message you describe about what you want
   * @param pxList supposedly a string array but you can pass anything
   * @return #
   */
  public static final int ccGetIndexFromComboBox
    (String pxBrief, Object[] pxList)
  { if(pxList==null){return -1;}
    int lpIndex=-1;
    int lpLength=pxList.length;
    if(lpLength<=1  || pxList.length>9){return 0;}
    if(ccIsEDT()){
      Object lpBuf = JOptionPane.showInputDialog(
        pbOwner==null?null:pbOwner.frame,pxBrief,"Select...",
        JOptionPane.PLAIN_MESSAGE,null,pxList,pxList[0]
      );
      if(lpBuf==null){return -1;}
      for(lpIndex=0;lpIndex<lpLength;lpIndex++){
        if(lpBuf.equals(pxList[lpIndex]))
          {return lpIndex;}
      }//..?
    }return lpIndex;
  }//+++
    
  /**
   * 
   * @param pxMode [f]file..[d]directory..
   * @return undefined tag if something went wrong
   */
  public static final String ccGetPathByFileChooser
    (char pxMode)
  { if(ccIsEDT()){
      int lpMode=JFileChooser.FILES_AND_DIRECTORIES;
      switch(pxMode){
        case 'f':lpMode=JFileChooser.FILES_ONLY;break;
        case 'd':lpMode=JFileChooser.DIRECTORIES_ONLY;break;
        default:break;
      }//..?
      O_FILE_CHOOSER.updateUI();
      O_FILE_CHOOSER.setFileSelectionMode(lpMode);
      int lpFlag=O_FILE_CHOOSER
        .showDialog(pbOwner==null?null:pbOwner.frame, null);
      if(lpFlag==JFileChooser.APPROVE_OPTION){
        File lpFile=O_FILE_CHOOSER.getSelectedFile();
        if(lpFile!=null){
          return lpFile.getAbsolutePath();
        }else{return C_M_INVALID;}
      }else{return C_M_INVALID;}
    }return C_M_INVALID;
  }//+++
  
  /**
   * wrapper for JFileChooser::setSelectedFile. 
   * will check passed string by File::isAbsolute and File::isDirectory. 
   * @param pxDefaultFile #
   * @return #
   */
  public static final String ccGetPathByFileChooser(String pxDefaultFile){
    File lpFile=new File(pxDefaultFile);
    if(lpFile.isAbsolute()){
      O_FILE_CHOOSER.setSelectedFile(lpFile);
    }//+++
    return ccGetPathByFileChooser(lpFile.isDirectory()?'d':'f');
  }//+++
    
  /**
   * 
   * @return color chosen one
   */
  public static final int ccGetColorByColorChooser(){
    if(pbOwner!=null){
      if(ccIsEDT()){
        Color lpRes=JColorChooser
          .showDialog(pbOwner.frame, "Choose...", Color.BLACK);
        if(lpRes!=null){
          return pbOwner
            .color(lpRes.getRed(), lpRes.getGreen(), lpRes.getBlue());
        }//..?
      }//..?
    }//..?
    return 0xFF111111;
  }//+++
    
  //== utility
  
  /**
   * prints a simple error message to the output
   * @return SwingUtilities.isEventDispatchThread()
   */
  public static final boolean ccIsEDT(){
    if(SwingUtilities.isEventDispatchThread()){return true;}
    System.err.println("--err::out_from_edt");
    return false;
  }//+++
    
  /**
   * sets scroll bar model of given scroll pane to maximum. <br>
   * will get blocked out for EDT.
   * @param pxTarget does not check for null
   */
  public static final void fnScrollToLast(JScrollPane pxTarget){
    if(ccIsEDT()){
      int lpMax=pxTarget.getVerticalScrollBar().getModel().getMaximum();
      pxTarget.getVerticalScrollBar().getModel().setValue(lpMax);
    }//..?
  }//+++
  
  /**
   * try to get an array of LookAndFeelInfo from UIManager, 
   *   than apply an indexed one via setLookAndFeel(). <br>
   * if a minus index is passed, getCrossPlatformLookAndFeelClassName()
   *   will get applied. <br>
   * will not get blocked out for EDT.
   * @param pxIndex #
   * @param pxRead test print array returned if true is passed
   */
  public static final void ccApplyLookAndFeel(int pxIndex, boolean pxRead){
    
    String lpTarget=UIManager.getCrossPlatformLookAndFeelClassName();
    
    //-- getting
    if(pxIndex>=0){
      UIManager.LookAndFeelInfo[] lpInfos=UIManager.getInstalledLookAndFeels();
      if(pxRead){
        System.out.println("--installed lookNfeel: 0->");
        for(UIManager.LookAndFeelInfo it : lpInfos){
          System.out.println(it.getClassName());
        }//..~
      }//..?
      lpTarget=lpInfos[pxIndex].getClassName();
    }//..?
    
    //-- applying
    try{
      UIManager.setLookAndFeel(lpTarget);
    }catch(ClassNotFoundException e){
      System.err.println("..ScFactory.ccApplyLookAndFeel()::"+e.getMessage());
    }catch(InstantiationException e){
      System.err.println("..ScFactory.ccApplyLookAndFeel()::"+e.getMessage());
    }catch(IllegalAccessException e){
      System.err.println("..ScFactory.ccApplyLookAndFeel()::"+e.getMessage());
    }catch(UnsupportedLookAndFeelException e){
      System.err.println("..ScFactory.ccApplyLookAndFeel()::"+e.getMessage());
    }//..%
    
  }//+++
  
}//***eof
