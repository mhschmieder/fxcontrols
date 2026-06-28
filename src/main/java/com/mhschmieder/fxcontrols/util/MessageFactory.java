/*
 * MIT License
 *
 * Copyright (c) 2020, 2026 Mark Schmieder. All rights reserved.
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is part of the fxcontrols Library
 *
 * You should have received a copy of the MIT License along with the fxcontrols
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxcontrols
 */
package com.mhschmieder.fxcontrols.util;

import com.mhschmieder.jcommons.io.FileAction;
import com.mhschmieder.jcommons.io.FileMode;
import com.mhschmieder.jcommons.security.LoginType;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

/**
 * {@code MessageFactory} is a factory class for methods related to general
 * messages.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class MessageFactory {

    /**
     * The default constructor is disabled, as this is a static factory class.
     */
    private MessageFactory() {}

    public static String getAcceptEulaMasthead( final String productName ) {
        return "Accept " + productName + " End User License Agreement?";
    }

    public static String getAccountManagementPreamble() {
        return "To review your login credentials, go to";
    }

    public static String getAutoAppendExtensionMayOverwriteMasthead() {
        return "Auto-Append of Default Extension May Overwrite Existing File";
    }

    public static String getBadUrlMasthead() {
        return "Bad URL or URI deviation from RFC 2396";
    }

    public static String getBrowserLaunchErrorMasthead() {
        return "Bad URL or Unsupported Browser";
    }

    public static String getBrowserLaunchErrorMessage() {
        return "Unable to launch default browser. See Session Log for details.";
    }

    public static String getBrowserLaunchErrorTitle() {
        return "Browser Launch Error";
    }

    public static String getCheckForUpdatesPreamble() {
        return "Check for";
    }

    public static String getClientServerProtocolErrorTitle() {
        return "Client-Server Protocol Error";
    }

    public static String getConfirmCoordinatesMasthead() {
        return "Please Confirm Coordinates";
    }

    public static String getContinueWithFileSaveMessage() {
        return "Continue with File Save?";
    }

    public static String getEulaBanner( final String productName ) {
        return productName + " End User License Agreement";
    }
    
    public static String getFileAutoSaveTitle() {
        return "File Auto-Save";
    }

    public static String getFileCloseMasthead() {
        return "Confirm Save File Changes and Close Window";
    }

    public static String getFileErrorMessage( final String errorMessageBody,
                                              final File file ) {
        try {
            final Path path = file.toPath();
            return "File: " + '"' + path.toString() + '"' + " "
                    + errorMessageBody;
        }
        catch ( final InvalidPathException ipe ) {
            ipe.printStackTrace();
            return errorMessageBody;
        }
    }

    public static String getFileExitMasthead() {
        return "Confirm Save File Changes and Exit Application";
    }

    public static String getFileExitMessage( final File file ) {
        final String promptMessageBody = " has been modified."
                + "\nSave changes and exit?";
        return getFilePromptMessage( promptMessageBody, file );
    }

    public static String getFileExitTitle( final String productName ) {
        return "Exit " + productName;
    }

    public static String getFileImportErrorMessage( final FileMode fileMode,
                                                    final File file ) {
        final String errorMessageQualifier
                = FileMode.IMPORT_CAD.equals( fileMode )
                ? ""
                : " was opened for project data, but";
        final String errorMessageBody = errorMessageQualifier
                + " could not load graphics data due to invalid file content.";
        return MessageFactory.getFileErrorMessage( errorMessageBody, file );
    }

    public static String getFileImportOutOfMemoryMessage(
            final FileMode fileMode,
            final File file ) {
        final String errorMessageQualifier
                = FileMode.IMPORT_CAD.equals( fileMode )
                ? ""
                : " was opened for project data, but" ;
        final String errorMessageBody = errorMessageQualifier
                + " could not load graphics data as more Java heap space memory"
                + " is required than is available.";
        return MessageFactory.getFileErrorMessage( errorMessageBody, file );
     }

    public static String getFileNameConflictTitle() {
        return "File Name Conflict";
    }

    public static String getFileNewerThanClientMessage() {
        return "Selected file contains new parameters not supported by this client."
                + "\nPlease upgrade to the latest client and try again.";
    }

    public static String getFileNotLoadedMessage( final File file ) {
        final String errorMessageBody = " could not be loaded.";
        return getFileErrorMessage( errorMessageBody, file );
    }

    public static String getFileNotOpenedMasthead() {
        return "File Not Opened";
    }

    public static String getFileNotOpenedMasthead( final FileMode fileMode ) {
        return FileMode.IMPORT_CAD.equals( fileMode )
            ? "File Partially Opened"
            : getFileNotOpenedMasthead();
    }
    
    public static String getFileNotOpenedMessage( final File file ) {
        final String errorMessageBody
                = " could not be opened (in full or in part).";
        return getFileErrorMessage( errorMessageBody, file );
    }

    public static String getFileNotSavedMasthead() {
        return "File Not Saved";
    }

    public static String getFileNotSavedMessage( final File file ) {
        final String errorMessageBody = " could not be saved.";
        return getFileErrorMessage( errorMessageBody, file );
     }

    public static String getFileReadErrorMessage( final FileMode fileMode,
                                                  final File file ) {
        final String errorMessageBody = FileMode.IMPORT_CAD.equals( fileMode )
            ? " could not load file contents due to parsing errors."
            : " could not fully load file contents due to parsing errors."
                    + " File content may be wrong data type for selected action.";
        return MessageFactory.getFileErrorMessage( errorMessageBody, file );
    }


    public static String getFileOpenErrorTitle() {
        return "File Open Error";
    }

    public static String getFileOpenOptionsTitle() {
        return "File Open Options";
    }

    public static String getFilePartiallySavedMasthead() {
        return "File Partially Saved";
    }

    public static String getFilePromptMessage( final String promptMessageBody,
                                               final File file ) {
        final String fileName = file.getName();
        return "File: " + '"' + fileName + '"' + " " + promptMessageBody;
    }

    public static String getFileReadErrorMessage( final File file ) {
        final String errorMessageBody = " could not open."
                + " Check the Session Log for possible run-time exceptions.";
        return getFileErrorMessage( errorMessageBody, file );
    }
    
    public static String getFileReadErrorTitle() {
        return "File Read Error";
    }

    /**
     * Returns the descriptive clause to insert in "Confirm File Changes" alert
     * boxes for the action that follows the File Save.
     * 
     * @param fileAction The file action type that determines the post-save clause
     * @return the descriptive clause to insert in "Confirm File Changes" alert
     */
    public static String getFileSavePostActionClause(
            final FileAction fileAction ) {
        String actionClause = "";
        switch ( fileAction ) {
        case NEW:
            actionClause = "creating a new one";
            break;
        case OPEN:
            actionClause = "opening another one";
            break;
        case RUN_BATCH:
            actionClause = "running a batch directory";
            break;
        case CLOSE:
            actionClause = "closing the window";
            break;
        case EXIT:
            actionClause = "exiting the application";
            break;
            //$CASES-OMITTED$
        default:
            break;       
        }
        
        return actionClause;
    }

    public static String getFileSaveErrorTitle() {
        return "File Save Error";
    }

    public static String getFileSaveOptionsTitle() {
        return "File Save Options";
    }

    public static String getFileWriteErrorMessage( final File file ) {
        final String errorMessageBody = " could not save."
                + " Check the Session Log for possible run-time exceptions.";
        return getFileErrorMessage( errorMessageBody, file );
    }

    public static String getGeneratedReportWriteErrorMessage( final File file ) {
        final String errorMessageBody
                = " could not save generated report due to write access denied."
                + " Please see Session Log for details (if the JRE forwarded exceptions).";
        return getFileErrorMessage( errorMessageBody, file );
    }

    public static String getGraphicsFileReadErrorMessage(
            final FileMode fileMode,
            final File file ) {
        final String errorMessageBody = FileMode.IMPORT_CAD.equals( fileMode )
            ? " could not load graphics data due to parsing errors."
            : " was opened for project data, but could not load graphics data due to parsing errors.";
        return MessageFactory.getFileErrorMessage( errorMessageBody, file );
    }

    public static String getGraphicsFileWriteErrorMessage( final File file ) {
        final String errorMessageBody = " could not save graphics data due to parsing errors."
                + " Please Zoom to Extents and try again.";
        return getFileErrorMessage( errorMessageBody, file );
    }

    public static String getGraphicsImportStatusBanner() {
        return "Graphics Import Status";
    }

    public static String getIncompatibleClientMasthead( final String productName ) {
        return "Incompatible Client";
    }

    public static String getIncompatibleClientMessage( final String productName ) {
        return "Your " + productName
                + " client is out of date and incompatible with the server.";
    }

    public static String getInvalidUserAccountMasthead() {
        return "Invalid User Account or Login Credentials";
    }

    public static String getLoginCredentialsMasthead( final LoginType loginType,
                                                            final String loginTarget ) {
        return "Please Log In to the " + loginTarget + " " + loginType.label();
    }

    public static String getLoginCredentialsTitle( final LoginType loginType ) {
        return loginType.label() + " Login Credentials";
    }

    public static String getLoginErrorMasthead() {
        return "Logins Disabled for This Session";
    }

    public static String getLoginErrorTitle() {
        return "Login Error";
    }

    public static String getMissingGraphicsSourceOnSaveMessage( final File file ) {
        final String errorMessageBody
                = " was partially saved as the graphics import source has moved,"
                + " is missing from file system, or is corrupted.";
        return MessageFactory.getFileErrorMessage( errorMessageBody, file );
    }

    public static String getNoPrinterAvailableMessage() {
        return "No Printer Available to Application";
    }

    public static String getNoSuchFileMessage( final File file ) {
        final String errorMessageBody = " does not exist.";
        return getFileErrorMessage( errorMessageBody, file );
    }

    public static String getNoTempFileMessage( final File file ) {
        final String errorMessageBody
                = " could not be saved as an intermediary temp file"
                + " cannot be created on the file system.";
        return getFileErrorMessage( errorMessageBody, file );
    }

    public static String getNullPrintJobMessage() {
        return "Nothing to print, or Printer not set up correctly";
    }

    public static String getObjectPropertyEditorApplyToolTip() {
        return "Applies current parameters and overwrites related project settings";
    }

    public static String getObjectPropertyEditorInsertToolTip() {
        return "Inserts at selected location using current parameters and"
                + " overwrites related project settings";
    }

    public static String getPasteCommandRejectedTitle() {
        return "Paste Command Rejected";
    }

    public static String getPasteReferencePointTitle() {
        return "Paste Reference Point";
    }

    public static String getPrinterBlockedMessage() {
        return "Cannot print due to Printer blocked by other Print Job";
    }

    public static String getPrintJobCanceledMessage() {
        return "Print Job Canceled";
    }

    public static String getPrintJobNotStartedMessage() {
        return "Unknown internal failure; Print Job not started";
    }

    public static String getPrintServicesProblemMasthead() {
        return "Problem with Print Services";
    }

    public static String getProjectReportHelpBanner() {
        return "Project Report Help";
    }

    public static String getRasterGraphicsExportOptionsMasthead() {
        return "Raster Graphics Export Options";
    }

    public static String getReadProtectedFileMessage(final File file) {
        final String errorMessageBody = " is read-protected.";
        final String readProtectedFileMessage = getFileErrorMessage( errorMessageBody, file );
        return readProtectedFileMessage;
    }

    public static String getSaveFileChangesMasthead() {
        return "Confirm Save File Changes";
    }

    public static String getSaveFileChangesMessage(final File file) {
        final String promptMessageBody = " has been modified."
                + " Save changes?";
        final String saveFileChangesMessage = getFilePromptMessage( promptMessageBody, file );
        return saveFileChangesMessage;
    }

    public static String getSecurityManagedFileMessage(final File file,
                                                       final String fileMode) {
        final String errorMessageBody = "is denied " + fileMode
                + " access by the Security Manager.";
        final String securityManagedFileMessage = getFileErrorMessage( errorMessageBody, file );
        return securityManagedFileMessage;
    }

    public static String getServerRequestFileWriteErrorMessage(final File file) {
        final String errorMessageBody = " could not save server request due to write access denied."
                + " Please see Session Log for details (if the JRE forwarded exceptions).";
        final String serverRequestFileNotSavedMessage =
                                                      getFileErrorMessage( errorMessageBody, file );
        return serverRequestFileNotSavedMessage;
    }

    public static String getServerResponseFileWriteErrorMessage(final File file) {
        final String errorMessageBody =
                                      " could not save server response due to write access denied."
                                              + " Please see Session Log for details (if the JRE forwarded exceptions).";
        final String serverResponseFileNotSavedMessage = getFileErrorMessage( errorMessageBody,
                                                                              file );
        return serverResponseFileNotSavedMessage;
    }

    public static String getUserAuthorizationErrorTitle() {
        return "User Authorization Error";
    }

    public static String getVectorGraphicsExportOptionsMasthead() {
        return "Vector Graphics Export Options";
    }

    public static String getWriteProtectedFileMessage(final File file) {
        final String errorMessageBody = " is write-protected.";
        final String writeProtectedFileMessage = MessageFactory
                .getFileErrorMessage( errorMessageBody, file );
        return writeProtectedFileMessage;
    }

    public static String getCopyReferencePointTitle() {
        return "Copy Reference Point";
    }

    public static String getGraphicsImportHelpBanner() {
        return "Graphics Import Help";
    }

    public static String getGraphicsImportOptionsMasthead() {
        return "Graphics Import Options";
    }

    public static String getInsertReferencePointTitle() {
        return "Insert Reference Point";
    }

    public static String getSurfaceMaterialTooltip() {
        return "Double-Click for List of Materials from Elements of Acoustical Engineering (Olson); Click ESC to Cancel and Exit List and Cell";
    }

    public static String getDeleteLayersMasthead() {
        return "Confirm Delete Layers";
    }

    public static String getDeleteLayersMessage() {
        return "Are you sure you want to delete the selected layers?";
    }

    public static String getDeleteLayersTitle() {
        return "Delete Layers";
    }
}
