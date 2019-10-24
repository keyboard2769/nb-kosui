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

package kosui.pppmodel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.swing.SwingWorker;

/**
 * having such an facility does not means we needed it all the time.<br>
 * you can choose to use the PApplet::thread instead.<br>
 */
public final class McTextFileExporter extends SwingWorker<Integer, Void> {
  
  /**
   * for reporting
   */
  public static final int
    C_R_EXCEPITON     =-1,
    C_R_SUCCESS       = 0,
    C_R_NULL_FILE     = 1,
    C_R_RELATIVE_FILE = 2,
    C_R_NOT_FILE      = 3,
    C_R_NULL_CONTENT  = 4,
    C_R_EMPTY_CONTENT = 5
  ;//,,,
  
  //===
  
  private MiExecutable cmReporting;
  private final List<String> cmContent;
  private final File cmFile;
  private boolean cmPortReadOnly=true;
  
  //===
  
  /**
   * @param pxFile no null no relative no directory
   * @param pxContent no null no empty
   * @param pxReporting null means silence
   */
  public McTextFileExporter(
    File pxFile, List<String> pxContent, MiExecutable pxReporting
  ){
    cmFile = pxFile;
    cmContent = pxContent;
    cmReporting = pxReporting;
  }//++!
  
  /**
   * @param pxFile no null no relative no directory
   * @param pxContent no null no empty
   */
  public McTextFileExporter(
    File pxFile, List<String> pxContent
  ){
    this(pxFile, pxContent, null);
  }//++!
  
  //===
  
  /**
   * calls Files::setReadOnly after done or not.<br>
   * does not check for return value.<br>
   * true by default.<br>
   * @param pxVal could be any thing
   */
  public final void ccSetPortReadOnly(boolean pxVal){
    cmPortReadOnly=pxVal;
  }//+++
  
  //===
  
  /**
   * {@inheritDoc}
   */
  @Override protected Integer doInBackground() {
    if (cmFile == null) {return C_R_NULL_FILE;}
    if (!cmFile.isAbsolute()) {return C_R_RELATIVE_FILE;}
    if (cmFile.isDirectory()) {return C_R_NOT_FILE;}
    if (cmContent == null) {return C_R_NULL_CONTENT;}
    if (cmContent.isEmpty()) {return C_R_EMPTY_CONTENT;}
    int lpRes = C_R_SUCCESS;
    try {
      Files.write(cmFile.toPath(), cmContent);
    } catch (IOException ioe) {
      System.err.println("kosui.pppmodel.McTextFileExporter::"
        + ioe.getMessage());
      lpRes = C_R_EXCEPITON;
    }//..?
    return lpRes;
  }//+++
  
  /**
   * {@inheritDoc }
   */
  @Override protected void done() {
    int lpCode;
    try {
      lpCode = get();
    } catch (Exception e) {
      lpCode = C_R_EXCEPITON;
    }//..?
    if(cmReporting!=null){
      cmReporting.ccExecute(new String[]{Integer.toString(lpCode)});
    }else{
      System.out.println("kosui.pppmodel.McTextFileExporter::endwith:"
        + Integer.toString(lpCode));
    }//..?
    if(cmPortReadOnly){cmFile.setReadOnly();}
  }//+++

}//***eof
