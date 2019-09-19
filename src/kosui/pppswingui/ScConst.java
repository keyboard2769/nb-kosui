/*
 * Copyright (C) 2019 Key Parker from K.I.C
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.File;
import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import kosui.ppputil.VcConst;
import processing.core.PApplet;

/**
 * if a full bloomed UI library need lots of constants,<br>
 * i thought they must be colors and dialogs.<br>
 */
public class ScConst {
  
  private static Frame cmOwner = null;
  
  /**
   * may got returned to any null-able string return value. <br>
   * preset but not necessarily as an empty string. <br>
   */
  public static final String
    C_M_INVALID = ""
  ;//...
  
  /**
   * in case you don't how trim informations from font matrix
   */
  public static final int 
    C_CHAR_DEFAULT_HEIGHT = 18,
    C_CHAR_DEFAULT_WIDTH = 4
  ;//...
  
  /**
   * predefined colors
   */
  public static final Color
    //-- component ** responsive
    C_PBL_FORE     =Color.decode("#111111"),
    C_PBL_BACK_ON  =Color.decode("#EEEE33"),
    C_PBL_BACK_OFF =Color.decode("#EEEEEE"),
    C_TPL_FORE     =Color.decode("#111111"),
    C_TPL_BACK_ON  =Color.decode("#33EE33"),
    C_TPL_BACK_OFF =Color.decode("#999999"),
    //-- component ** normal
    C_COMBO_BACK  =Color.decode("#EEEECC"),
    C_TOGGLE_FORE =Color.decode("#111111"),
    C_TOGGLE_BACK =Color.decode("#DDDDDD"),
    //-- named
    WATER         =Color.decode("#6699CC"),
    //-- pole
    C_WHITE        = Color.decode("#EEEEEE"),
    //-- lit
    C_LIT_RED      = Color.decode("#EE6666"),
    C_LIT_ORANGE   = Color.decode("#EE9966"),
    C_LIT_YELLOW   = Color.decode("#EEEE66"),
    C_LIT_GREEN    = Color.decode("#66EE66"),
    C_LIT_WATER    = Color.decode("#66EEEE"),
    C_LIT_BLUE     = Color.decode("#6699EE"),
    C_LIT_PURPLE   = Color.decode("#EE66EE"),
    C_LIT_GRAY     = Color.decode("#CCCCCC"),
    //-- normal
    C_RED          = Color.decode("#EE3333"),
    C_ORANGE       = Color.decode("#EE6633"),
    C_YELLOW       = Color.decode("#EEEE33"),
    C_GREEN        = Color.decode("#33EE33"),
    C_WATER        = Color.decode("#33EEEE"),
    C_BLUE         = Color.decode("#3366EE"),
    C_PURPLE       = Color.decode("#EE33EE"),
    C_GRAY         = Color.decode("#999999"),
    //-- dim
    C_DIM_RED      = Color.decode("#991111"),
    C_DIM_ORANGE   = Color.decode("#993311"),
    C_DIM_YELLOW   = Color.decode("#999911"),
    C_DIM_GREEN    = Color.decode("#119911"),
    C_DIM_WATER    = Color.decode("#119999"),
    C_DIM_BLUE     = Color.decode("#116699"),
    C_DIM_PURPLE   = Color.decode("#991199"),
    C_DIM_GRAY     = Color.decode("#666666"),
    //-- dark
    C_DARK_RED     = Color.decode("#660000"),
    C_DARK_ORANGE  = Color.decode("#663300"),
    C_DARK_YELLOW  = Color.decode("#666600"),
    C_DARK_GREEN   = Color.decode("#006600"),
    C_DARK_WATER   = Color.decode("#006666"),
    C_DARK_BLUE    = Color.decode("#003366"),
    C_DARK_PURPLE  = Color.decode("#660066"),
    C_DARK_GRAY    = Color.decode("#333333"),
    //-- pole
    C_BLACK        = Color.decode("#111111")
  ;//...
  
  /**
   * plain 1 pix straight line
   */
  public static final BasicStroke C_DEFAULT_STROKE
    = new BasicStroke(1.0f, BasicStroke.JOIN_MITER, BasicStroke.CAP_BUTT);
  
  //===
  
  private static final JFileChooser O_FILE_CHOOSER
    = new JFileChooser(VcConst.C_V_PWD);
  
  private static final Font O_DEFAULT_FONT
    = new Font(Font.DIALOG_INPUT,  Font.PLAIN, 12);;
  
  private ScConst(){}//..!
  
  /**
   * @param pxOwner basically dialog uses this so he can be null.
   */
  public static final void ccSetOwner(Frame pxOwner){
    cmOwner=pxOwner;
  }//+++
  
  /**
   * @param pxSketch but if you are passing your sketch it can't be null. 
   */
  public static final void ccSetOwner(PApplet pxSketch){
    if(pxSketch==null){return;}
    cmOwner=pxSketch.frame;
  }//+++
  
  //=== dialog
  
  /**
   * prints a simple error message to the output.<br>
   * @return SwingUtilities.isEventDispatchThread()
   */
  public static final boolean ccIsEDT(){
    if(SwingUtilities.isEventDispatchThread()){return true;}
    System.err.println("kosui.pppswing.ScConst.ccIsEDT()::BLOCK!!");
    return false;
  }//+++
  
  /**
   * will get blocked out from event dispatch thread.<br>
   * @param pxMessage must have something
   */
  public static final void ccMessageBox(String pxMessage){
    if(!ccIsEDT()){return;}
    if(!VcConst.ccIsValidString(pxMessage)){return;}
    JOptionPane.showMessageDialog(cmOwner,pxMessage);
  }//+++
  
  /**
   * will get blocked out from event dispatch thread.<br>
   * icon will get set to JOptionPane.ERROR_MESSAGE.<br>
   * and yes, there will be no ccWarnBox().<br>
   * @param pxMessage must have something
   */
  public static final void ccErrorBox(String pxMessage){
    if(!ccIsEDT()){return;}
    JOptionPane.showMessageDialog(
      cmOwner,
      pxMessage,
      cmOwner==null?"<!>":cmOwner.getTitle(),
      JOptionPane.ERROR_MESSAGE
    );
  }//+++
  
  /**
   * will get blocked out from event dispatch thread.<br>
   * icon will get set to JOptionPane.ERROR_MESSAGE.<br>
   * @param pxMessage does not check for null or emptiness
   * @return false by default
   */
  public static final boolean ccYesOrNoBox(String pxMessage){
    if(!ccIsEDT()){return false;}
    int i=JOptionPane.showConfirmDialog(
      cmOwner,
      pxMessage,
      cmOwner==null?"<?>":cmOwner.getTitle(),
      JOptionPane.YES_NO_OPTION
    );
    return i==0;
  }//+++
  
  /**
   * will get blocked out from event dispatch thread.<br>
   * there will never be a version does invoke later
   * for you because you should decide if invoke and wait is necessary
   * or not and when and how to use the returned string.<br>
   * @param pxBrief a short message you describe about what you want
   * @param pxDefault will be in the input box before getting input
   * @return #
   */
  public static final
  String ccGetStringByInputBox(String pxBrief, String pxDefault){
    if(!ccIsEDT()){return "<blocked>";}
    String lpRes=JOptionPane.showInputDialog(cmOwner, pxBrief, pxDefault);
    if(!VcConst.ccIsValidString(lpRes)){return "<?>";}
    return lpRes;
  }//+++
  
  /**
   * <pre>
   * will get blocked out from event dispatch thread.
   * this thing iterates through the whole list to find the index.
   * if you are not confortable with those overheads just don't use this one.
   * </pre>
   * @param pxBrief a short message you describe about what you want
   * @param pxList supposedly a string array but you can pass anything
   * @return #
   */
  public static final
  int ccGetIndexFromComboBox(String pxBrief, Object[] pxList){
    
    //-- pre check
    if(pxList==null){return -1;}
    if(!ccIsEDT()){return 0;}
    int lpLength=pxList.length;
    if(lpLength<=1  || pxList.length>9){return 0;}
    
    //-- getting
    Object lpBuf = JOptionPane.showInputDialog(
      cmOwner,
      pxBrief,"Select...",//[later]::localization!!
      JOptionPane.PLAIN_MESSAGE,null,pxList,pxList[0]
    );
    if(lpBuf==null){return -1;}
    
    //-- matching
    int lpIndex;
    for(lpIndex=0;lpIndex<lpLength;lpIndex++){
      if(lpBuf.equals(pxList[lpIndex])){break;}
    }//..?
    return lpIndex;
    
  }//+++
  
  /**
   * <pre>
   * mode:
   *  - [f]:file
   *  - [d]:direcory
   *  - [x]:UNDEFINED.(or you can pass any letter)
   * </pre>
   * @param pxMode #
   * @return might be null if something goes wrong
   */
  public static final File ccGetFileByFileChooser(char pxMode){
    
    //-- pre
    if(ccIsEDT()){return null;}
    
    //-- apply
    int lpMode=JFileChooser.FILES_AND_DIRECTORIES;
    switch(pxMode){
      case 'f':lpMode=JFileChooser.FILES_ONLY;break;
      case 'd':lpMode=JFileChooser.DIRECTORIES_ONLY;break;
      default:break;
    }//..?
    
    //-- show
    O_FILE_CHOOSER.updateUI();
    O_FILE_CHOOSER.setFileSelectionMode(lpMode);
    int lpFlag=O_FILE_CHOOSER.showDialog(cmOwner, null);
    if(lpFlag==JFileChooser.APPROVE_OPTION){
      File lpFile=O_FILE_CHOOSER.getSelectedFile();
      return lpFile;
    }else{return null;}
    
  }//+++
  
  /**
   * <pre>
   * mode:
   *  - [f]:file
   *  - [d]:direcory
   *  - [x]:UNDEFINED.(or you can pass any letter)
   * and,
   * for all this years i have diceded the follow rule:
   *   a file is a File,
   *   a path is a Path,
   *   a url is a URL,
   *   a uri is a URI,
   * and location is a String.
   * </pre>
   * @param cmMode_fd #
   * @return string of the absolute path
   */
  public static final String ccGetLocationByFileChooser(char cmMode_fd){ 
    File lpFile=ccGetFileByFileChooser(cmMode_fd);
    if(lpFile==null){return C_M_INVALID;}
    if(!lpFile.isAbsolute()){return C_M_INVALID;}
    return lpFile.getAbsolutePath();
  }//+++
  
  /**
   * will get blocked out from event dispatch thread.<br>
   * not sure what is gonna happen if owner is not initiated.<br>
   * @return the chosen one. if anything is wrong, the DARK GRAY.
   */
  public static final int ccGetColorByColorChooser(){
    if(!ccIsEDT()){return 0xFF111111;}
    Color lpRes=JColorChooser.showDialog(cmOwner, "Choose...", Color.BLACK);
    if(lpRes!=null){return lpRes.getRGB();}
    return 0xFF111111;
  }//+++
    
  //=== operate
  
  /**
   * alias for JFrame::setState.<br>
   * @param pxFrame do not pass null
   */
  public static final void ccRestoreFrame(JFrame pxFrame){
    if(!ccIsEDT()){return;}
    if(pxFrame==null){return;}
    pxFrame.setState(JFrame.NORMAL);
  }//+++
  
  /**
   * alias for JFrame::setState.<br>
   * @param pxFrame do not pass null
   */
  public static final void ccMinimizeFrame(JFrame pxFrame){
    if(!ccIsEDT()){return;}
    if(pxFrame==null){return;}
    pxFrame.setState(JFrame.ICONIFIED);
  }//+++
  
  /**
   * change the back color to predefined one.<br>
   * @param pxTarget do not pass null
   * @param pxStatus #
   */
  public static final
  void ccActivateCommandButton(JButton pxTarget, boolean pxStatus){
    if(!ccIsEDT()){return;}
    if(pxTarget==null){return;}
    pxTarget.setBackground(pxStatus?C_PBL_BACK_ON:C_PBL_BACK_OFF);
  }//+++
  
  /**
   * change the back color to predefined one.<br>
   * @param pxTarget do not pass null
   * @param pxStatus #
   */
  public static final
  void ccActivateTextLamp(JTextField pxTarget, boolean pxStatus){
    if(!ccIsEDT()){return;}
    if(pxTarget==null){return;}
    pxTarget.setBackground(pxStatus?C_TPL_BACK_ON:C_TPL_BACK_OFF);
  }//+++
  
  /**
   * sets scroll bar model of given scroll pane to maximum.<br>
   * should get called after refreshing targeted scroll pane.<br>
   * will get blocked out for EDT.<br>
   * @param pxTarget don't pass null
   */
  public static final void ccScrollToLast(JScrollPane pxTarget){
    if(!ccIsEDT()){return;}
    if(pxTarget==null){return;}
    pxTarget.validate();
    BoundedRangeModel lpModel = pxTarget.getVerticalScrollBar().getModel();
    if(lpModel==null){return;}
    lpModel.setValue(lpModel.getMaximum()-lpModel.getExtent());
  }//+++
  
  /**
   * sets scroll bar model of given scroll pane to maximum. <br>
   * will get blocked out for EDT.
   * @param pxTarget don't pass null
   */
  public static final void ccScrollToLast(JTextArea pxTarget){
    if(!ccIsEDT()){return;}
    if(pxTarget==null){return;}
    int lpLength = pxTarget.getText().length();
    if(lpLength<=4){return;}//..then it cant be out bounded
    pxTarget.setSelectionStart(lpLength - 2);
    pxTarget.setSelectionEnd(lpLength - 1);
  }//+++
  
  //=== system
  
  /**
   * @return default font is hard coded.
   */
  public static final Font ccGetDefaultFont(){
    return O_DEFAULT_FONT;
  }//+++
  
  /**
   * an alias to pxTarget.setFont.<br>
   * if it contains children than all will be set to the default.<br>
   * @param pxTarget do not pass null
   */
  public static final void ccSetToDefaultFont(JComponent pxTarget){
    if(!ccIsEDT()){return;}
    if(O_DEFAULT_FONT==null){return;}
    if(pxTarget==null){return;}
    pxTarget.setFont(O_DEFAULT_FONT);
    Component[] lpList = pxTarget.getComponents();
    if(lpList==null){return;}
    if(lpList.length==0){return;}
    for(Component it:lpList){
      if(it instanceof JComponent){it.setFont(O_DEFAULT_FONT);}
    }//..~
  }//+++
  
  /**
   * an alias to FileChooser.setApproveButtonText().<br>
   * @param pxText must have some thing
   */
  public static final void ccSetFileChooserButtonText(String pxText){
    if(!VcConst.ccIsValidString(pxText)){return;}
    O_FILE_CHOOSER.setApproveButtonText(pxText);
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
      int lpFixedIndex=pxIndex>=lpInfos.length?lpInfos.length-1:pxIndex;
      lpTarget=lpInfos[lpFixedIndex].getClassName();
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
  
  /**
   * for multiple hardware monitor situation.<br>
   * @return (0,0) or (%resolution%,0)
   */
  public static final Point ccGetScreenInitPoint(){
    Point lpDummyPoint = null;
    Point lpInitPoint = null;
    for (
      GraphicsDevice lpDevice:
      GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()
    ){
      if (lpDummyPoint == null) {
        lpDummyPoint = 
          lpDevice.getDefaultConfiguration().getBounds().getLocation();
      } else if (lpInitPoint == null) {
        lpInitPoint = 
          lpDevice.getDefaultConfiguration().getBounds().getLocation();
      }//..?
    }//..~
    if (lpInitPoint == null) {lpInitPoint = lpDummyPoint;}
    if (lpInitPoint == null) {lpInitPoint = new Point(0,0);}
    return lpInitPoint;
  }//+++
  
}//***eof
