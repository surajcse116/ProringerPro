package com.android.llc.proringer.pro.proringerpro.helper;

/**
 * Created by su on 7/2/18.
 */
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import com.android.llc.proringer.pro.proringerpro.R;

import java.io.File;

public class Downloadandshowfile
{
    private static final String GOOGLE_DRIVE_PDF_READER_PREFIX = "http://drive.google.com/viewer?url=";
    private static final String HTML_MIME_TYPE = "text/html";

    /**
     * Downloads a PDF with the Android DownloadManager and opens it with an installed PDF reader app.
     * @param context
     * @param pdfUrl
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void downloadAndOpenPDF(final Context context, final String pdfUrl) {
        // Get filename
        final String filename = pdfUrl.substring( pdfUrl.lastIndexOf( "/" ) + 1 );

//        final String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(pdfUrl));
//
//        Loger.MSG("mimeType",""+mimeType);
        // The place where the downloaded PDF file will be put
        Logger.printMessage("context", String.valueOf(context));
        final File tempFile = new File( context.getExternalFilesDir( Environment.DIRECTORY_DOWNLOADS ),"/HappyWanNyan/"  + "/"+ filename );
        if ( tempFile.exists() ) {
            // If we have downloaded the file before, just go ahead and show it.
            Logger.printMessage("context2", String.valueOf(context));
            openPDF( context,tempFile);

            //Toast.makeText(context,context.getResources().getString(R.string.already_downloaded),Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress dialog while downloading
        final ProgressDialog progress = ProgressDialog.show( context, context.getString( R.string.pdf_show_local_progress_title ), context.getString( R.string.pdf_show_local_progress_content ), true );

        // Create the download request
        DownloadManager.Request request = new DownloadManager.Request( Uri.parse( pdfUrl ) );
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Attached File Downloading " + filename);
        request.setDescription("Downloading " + filename);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalFilesDir( context, Environment.DIRECTORY_DOWNLOADS,"/HappyWanNyan/"  + "/" + filename );
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/HappyWanNyan/"  + "/" + filename);
        final DownloadManager dm = (DownloadManager) context.getSystemService( Context.DOWNLOAD_SERVICE );
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ( !progress.isShowing() ) {
                    return;
                }
                context.unregisterReceiver( this );

                progress.dismiss();
                long downloadId = intent.getLongExtra( DownloadManager.EXTRA_DOWNLOAD_ID, -1 );
                Cursor c = dm.query( new DownloadManager.Query().setFilterById( downloadId ) );

                if ( c.moveToFirst() ) {
                    int status = c.getInt( c.getColumnIndex( DownloadManager.COLUMN_STATUS ) );
                    if ( status == DownloadManager.STATUS_SUCCESSFUL ) {
                        openPDF( context,tempFile);
                        //Toast.makeText(context,context.getResources().getString(R.string.download_complete),Toast.LENGTH_SHORT).show();
                    }
                }
                c.close();
            }
        };
        context.registerReceiver( onComplete, new IntentFilter( DownloadManager.ACTION_DOWNLOAD_COMPLETE ) );

        // Enqueue the request
        dm.enqueue( request );
    }

    /**
     * Show a dialog asking the user if he wants to open the PDF through Google Drive
     * @param context
     * @param pdfUrl
     */
    public static void askToOpenPDFThroughGoogleDrive( final Context context, final String pdfUrl ) {
        new AlertDialog.Builder( context )
                .setTitle( R.string.pdf_show_online_dialog_title )
                .setMessage( R.string.pdf_show_online_dialog_question )
                .setNegativeButton( R.string.no, null )
                .setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openPDFThroughGoogleDrive(context, pdfUrl);
                    }
                })
                .show();
    }

    /**
     * Launches a browser to view the PDF through Google Drive
     * @param context
     * @param pdfUrl
     */
    public static void openPDFThroughGoogleDrive(final Context context, final String pdfUrl) {
        Intent i = new Intent( Intent.ACTION_VIEW );
        i.setDataAndType(Uri.parse(GOOGLE_DRIVE_PDF_READER_PREFIX + pdfUrl ), HTML_MIME_TYPE );
        context.startActivity( i );
    }
    /**
     * Open a local PDF file with an installed reader
     * @param context
     * @param tempFile
     */
    public static final void openPDF(Context context,File tempFile) {

//        url.toString() return a String in the format: "file:///mnt/sdcard/myPicture.jpg",
//                whereas url.getPath() returns a String in the format: "/mnt/sdcard/myPicture.jpg",

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = tempFile.getName().substring(tempFile.getName().lastIndexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Logger.printMessage("temp_path",""+tempFile.getAbsolutePath());
                Uri contentUri = FileProvider.getUriForFile(context, "com.android.llc.proringer.pro.proringerpro.provider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                intent.setDataAndType(contentUri, type);
            } else {
                intent.setDataAndType(Uri.fromFile(tempFile), type);
            }
            context.startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(context, "No activity found to open this attachment.", Toast.LENGTH_SHORT).show();
        }
    }

}
