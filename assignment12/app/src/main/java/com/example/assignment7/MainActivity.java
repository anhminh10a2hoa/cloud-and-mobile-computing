package com.example.assignment7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    int searchRequestCode = 1;
    int allRequestCode = 2;
    int updateRequestCode = 3;
    int settingRequestCode = 4;
    int aboutRequestCode = 5;
    int galleryRequestCode=6;
    DBAdapter dbAdapter=null;
    public static String dbPath, dbName, dirName;
    public static File dbPathFile;

    private Button addBtn;
    private Button updateBtn;
    private Button searchBtn;
    private Button allBtn;
    private Button submitBtn;
    private EditText titleEt;
    private EditText participantsEt;
    private Button timePickerBtn;
    private TextView timeTv;
    private Button datePickerBtn;
    private TextView dateTv;
    private Button settingBtn;
    private String preferencesName = "my_setting";
    private Button aboutBtn;
    private Button openDialogBtn;
    private Button downloadTitleFromInternetButton;

    private TextView titleTv;
    private TextView meetingTitleTv;
    private TextView participantsTv;
    private TextView startDateTv;
    private TextView startTimeTv;
    private ImageView imageView;
    Boolean dbCreated = false;

    DialogFragment newFragment;
    private Dialog imageDialog;
    ArrayList<String> participantsArray = new ArrayList<String>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    Dialog downloadFromUrlsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbName=getString(R.string.db_name);
        dirName=getString(R.string.dir_name_database);
        dbPathFile = getApplicationContext().getExternalFilesDir(dirName);
        if(dbPathFile == null) {
            displayMessage(getString(R.string.not_found));
            return;
        }
//        copyDBFile();
        dbAdapter = new DBAdapter(getApplicationContext(), this.dbPathFile.getAbsolutePath() + File.separator, dbName);

        //set background color
        SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);

        getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));

        addBtn = findViewById(R.id.button);
        updateBtn = findViewById(R.id.button2);
        searchBtn = findViewById(R.id.button3);
        allBtn = findViewById(R.id.button5);
        submitBtn = findViewById(R.id.button4);
        titleEt = findViewById(R.id.editTextTextPersonName);
        participantsEt = findViewById(R.id.editTextTextPersonName2);
        timePickerBtn = findViewById(R.id.time_button);
        datePickerBtn = findViewById(R.id.date_button);
        timeTv = findViewById(R.id.textView12);
        dateTv = findViewById(R.id.textView11);
        settingBtn = findViewById(R.id.button10);
        titleTv = findViewById(R.id.textView);
        meetingTitleTv= findViewById(R.id.textView2);
        participantsTv= findViewById(R.id.textView3);
        startDateTv= findViewById(R.id.textView4);
        startTimeTv= findViewById(R.id.textView5);
        aboutBtn = findViewById(R.id.button16);
        openDialogBtn = findViewById(R.id.button19);
        downloadTitleFromInternetButton = findViewById(R.id.button21);

        downloadTitleFromInternetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDownloadDialog();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        updateBtn.setOnClickListener(updateClickListener);

        searchBtn.setOnClickListener(searchClickListener);

        settingBtn.setOnClickListener(settingClickListener);

        allBtn.setOnClickListener(allClickListener);

        aboutBtn.setOnClickListener(aboutClickListener);

        openDialogBtn.setOnClickListener(showImageDialogClick);

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString();
                String participants = participantsEt.getText().toString();
                String startDate = dateTv.getText().toString();
                String startTime = timeTv.getText().toString();
                if(TextUtils.isEmpty(title)) {
                    titleEt.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(participants)) {
                    participantsEt.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(startDate)) {
                    dateTv.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(startTime)) {
                    timeTv.setBackgroundColor(Color.RED);
                }
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(participants) && !TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(startTime)) {
                    titleEt.setBackgroundColor(Color.WHITE);
                    participantsEt.setBackgroundColor(Color.WHITE);
                    dateTv.setBackgroundColor(Color.WHITE);
                    timeTv.setBackgroundColor(Color.WHITE);
//                    meetingArrayList.add(new Meeting(meetingArrayList.size(), title, participants, startDate + " " + startTime));
                    long id = randomId();
                    dbAdapter.addCustomer(randomId(), title,startDate + " " + startTime, participantsArray, images);
                    participantsArray = new ArrayList<String>();
                    images = new ArrayList<Bitmap>();
                    Toast.makeText(getBaseContext(),
                            "Id " + id + " was added to the database", Toast.LENGTH_LONG)
                            .show();
                    titleEt.setText("");
                    participantsEt.setText("");
                    dateTv.setText("");
                    timeTv.setText("");
                }
            }
        });

//        set font size font color
        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);
        titleTv.setTextSize(fontSize);
        meetingTitleTv.setTextSize(fontSize);
        participantsTv.setTextSize(fontSize);
        startDateTv.setTextSize(fontSize);
        startTimeTv.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        titleTv.setTextColor(fontColor);
        meetingTitleTv.setTextColor(fontColor);
        participantsTv.setTextColor(fontColor);
        startDateTv.setTextColor(fontColor);
        startTimeTv.setTextColor(fontColor);
    }

    private class TextBackgroundTask extends AsyncTask<String, Integer, String> {
        //This method receives an array of URLs as strings and returns
        //an object of type Bitmap. This method calls getInputStream() method
        //which makes HTTP connection to the given URL and returns the input stream
        @Override
        protected String doInBackground(String... urls) {
            String downloadedContent;
            //Here we initialize downloadedContent by calling
            //downLoadText() method
            downloadedContent = downLoadText(urls[0]);
            //Here we update the progress of the background job
            //by passing the size of downloaded text
            publishProgress(downloadedContent.length());
            return downloadedContent;
        }

        //This method updates the progress
        @Override
        protected void onProgressUpdate(Integer... progress) {
            //Here we display the size of the bitmap
            displayToast("The amount of downloaded text: " + progress[0]);
        }

        //This method will be called when the background job has finished
        @Override
        protected void onPostExecute(String text) {
            //Here we load the text to the note
            titleEt.setText(text);
        }
    }

    private class ImageBackgroundTask extends AsyncTask<String, Integer, Bitmap> {
        //This method receives an array of URLs as strings and returns
        //an object of type Bitmap. This method calls getInputStream() method
        //which makes HTTP connection to the given URL and returns the input stream
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                //Here we create an InputStream object
                InputStream inputStream = getInputStream(urls[0]);
                //Here we create a bitmap out of the received input stream
                bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null)
                    //Here we update the progress of the background job
                    //by passing the number o fbytes in the bitmap
                    publishProgress(bitmap.getByteCount());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        //This method updates the progress
        @Override
        protected void onProgressUpdate(Integer... progress) {
            //Here we display the size of the bitmap
            displayToast("Bitmap size: " + progress[0]);
        }

        //This method will be called when the background job has finished
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //Here we se the image of the imageView object
            imageView.setImageBitmap(bitmap);
        }
    }

    private String downLoadText(String urlString) {
        int BUFFER_SIZE = 2048;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        int charRead;
        //String content="";
        StringBuilder content = new StringBuilder();
        try {
            inputStream = getInputStream(urlString);
            //Here we create an InputStreamReader object to read the content
            //of the stream
            if (inputStream != null) {
                inputStreamReader = new InputStreamReader(inputStream);
                //Here we define inputBuffer array with which we read the content
                //of the input stream
                char[] inputBuffer = new char[BUFFER_SIZE];
                //In the following we read the content of the input stream and attach it
                //to content variable, which is of type String
                while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
                    String readString = String.copyValueOf(inputBuffer, 0, charRead);
                    //content+=readString;
                    content.append(readString);
                    inputBuffer = new char[BUFFER_SIZE];
                }
                inputStream.close();
            }
        } catch (IOException e) {
            displayToast(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return content.toString();
    }

    private InputStream getInputStream(String urlString) throws IOException {
        //Here we declare an object of type InputStream
        InputStream inputStream = null;
        //Here we create an URL object
        URL url = new URL(urlString);
        //Here we make an URL connection
        URLConnection urlConnection = url.openConnection();
        try {
            //Here we create a URLConnection with support for HTTP-specific features
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            //Here we determine whether if required the system can ask the user
            //for additional input
            httpConnection.setAllowUserInteraction(false);
            // Sets whether HTTP redirects (requests with response code 3xx)
            //should be automatically followed by this HttpURLConnection instance.
            httpConnection.setInstanceFollowRedirects(true);
            //Set the method for the URL request
            httpConnection.setRequestMethod("GET");
            //Here we establish HttpURLConnection
            httpConnection.connect();
            //Here we make sure that the HttpURL connection has been successfully
            //established
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Here we get the input stream that reads from this open connection
                inputStream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputStream;
    }

    void showDownloadDialog() {
        downloadFromUrlsDialog = new Dialog(MainActivity.this);
        downloadFromUrlsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // allow cancel by clicking anywhere outside the dialog
        downloadFromUrlsDialog.setCancelable(true);
        downloadFromUrlsDialog.setContentView(R.layout.activity_download_dialog);

        final EditText textUrl = downloadFromUrlsDialog.findViewById(R.id.et_text_url);
        final Button downloadButton = downloadFromUrlsDialog.findViewById(R.id.btn_download_content);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextBackgroundTask textBackgroundTask = new TextBackgroundTask();
                ImageBackgroundTask imageBackgroundTask = new ImageBackgroundTask();
                try {
                    if (!textUrl.getText().toString().isEmpty()) {
                        textBackgroundTask.execute(textUrl.getText().toString()).get();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                downloadFromUrlsDialog.dismiss();
            }
        });
        downloadFromUrlsDialog.show();
    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), Search.class);
        startActivityForResult(intent, searchRequestCode);
    }

    private void startActivityAll() {
        Intent intent = new Intent(getApplication(), All.class);
        startActivityForResult(intent, allRequestCode);
    }

    private void startActivityUpdate() {
        Intent intent = new Intent(getApplication(), Update.class);
        startActivityForResult(intent, updateRequestCode);
    }

    private void startActivitySetting() {
        Intent intent = new Intent(getApplication(), setting.class);
        startActivityForResult(intent, settingRequestCode);
    }

    private void startActivityAbout() {
        Intent intent = new Intent(getApplication(), about.class);
        startActivityForResult(intent, aboutRequestCode);
    }

    private View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity();
        }
    };

    private View.OnClickListener allClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityAll();
        }
    };

    private View.OnClickListener updateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityUpdate();
        }
    };

    private View.OnClickListener settingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivitySetting();
        }
    };

    private View.OnClickListener aboutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityAbout();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                //Here we set the text of imagePathEditText the directory path of the
                //selected image
                InputStream inputStream = null;
                Bitmap bitmap = null;
                try {
                    //We call the decodeFile() method to scale down the image
                    bitmap = decodeFile(selectedImageUri);
                    //Here we set the image of the ImageView to the selected image
                    if (bitmap != null) {
                        Toast.makeText(getApplicationContext(), bitmap.toString() , Toast.LENGTH_LONG).show();
                        imageView.setImageBitmap(bitmap);
                    } else {
                        displayToast(getString(R.string.null_bitmap));
                    }
                } catch (FileNotFoundException e) {
                    displayToast(e.getLocalizedMessage());
                } catch (IOException e) {
                    displayToast(e.getLocalizedMessage());
                } finally {
                    if (inputStream != null)
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            displayToast(e.getLocalizedMessage());
                        }
                }
            }
        }
    }

    private int randomId() {
        int min = 1;
        int max = 2000;
        int b = (int)(Math.random()*(max-min+1)+min);
        return b;
    }

    private View.OnClickListener showImageDialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showImageDialog();
        }
    };

    void showImageDialog() {
        imageDialog = new Dialog(MainActivity.this);
        imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        imageDialog.setCancelable(true);
        imageDialog.setContentView(R.layout.activity_image_dialog);

        EditText participantsText = imageDialog.findViewById(R.id.editTextTextPersonName6);
        imageView = imageDialog.findViewById(R.id.imageView);
        EditText imageUrlEditText = imageDialog.findViewById(R.id.editTextTextPersonName8);
        final Button selectImageButton = imageDialog.findViewById(R.id.button14);
        final Button addImageButton = imageDialog.findViewById(R.id.button18);
        final Button downloadImageButton = imageDialog.findViewById(R.id.button22);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Here we invoke the gallery application to pick an image
                startActivityForResult(Intent.createChooser(galleryIntent, getString(R.string.select_image_title)), galleryRequestCode);

            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                participantsArray.add(participantsText.getText().toString());
                images.add(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                participantsEt.append(participantsText.getText().toString() + ",");
                imageDialog.dismiss();
            }
        });

        downloadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageBackgroundTask imageBackgroundTask = new ImageBackgroundTask();
                try {
                    if (!imageUrlEditText.getText().toString().isEmpty()) {
                        imageBackgroundTask.execute(imageUrlEditText.getText().toString()).get();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        imageDialog.show();
    }

    private Bitmap decodeFile(Uri selectedImageUri) throws IOException {
        //Here we open an input stream to access the content of the image
        InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
        //Decode image size
        BitmapFactory.Options imageSizeOptions = new BitmapFactory.Options();
        //If set to true, the decoder will return null (no bitmap), but
        //the out... fields will still be set, allowing the caller to query
        //the bitmap without having to allocate the memory for its pixels.
        imageSizeOptions.inJustDecodeBounds = true;
        //Here we fetch image meta data
        BitmapFactory.decodeStream(inputStream, null, imageSizeOptions);
        //The new size we want to scale to
        final int REQUIRED_SIZE=150;
        //Here we find the correct scale value. It should be a power of 2.
        int scale=1;
        //Here we scale the result height and width of the image based on the required size
        while(imageSizeOptions.outWidth/scale/2>=REQUIRED_SIZE && imageSizeOptions.outHeight/scale/2>=REQUIRED_SIZE)
            scale*=2;
        //Decode with inSampleSize
        BitmapFactory.Options inSampleSizeOption = new BitmapFactory.Options();
        //Here we do the actual decoding. If set to a value > 1, requests the decoder to subsample the
        //original image, returning a smaller image to save memory. The
        //sample size is the number of pixels in either dimension that correspond
        //to a single pixel in the decoded bitmap.
        inSampleSizeOption.inSampleSize=scale;
        inputStream.close();
        //Here we initialize the inputStream again
        inputStream = getContentResolver().openInputStream(selectedImageUri);
        return BitmapFactory.decodeStream(inputStream, null, inSampleSizeOption);
    }
    //This method decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File imageFile){
        try {
            //Decode image size
            BitmapFactory.Options imageSizeOptions = new BitmapFactory.Options();
            //If set to true, the decoder will return null (no bitmap), but
            //the out... fields will still be set, allowing the caller to query
            //the bitmap without having to allocate the memory for its pixels.
            imageSizeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(imageFile), null, imageSizeOptions);
            //The new size we want to scale to
            final int REQUIRED_SIZE=70;
            //Here we find the correct scale value. It should be a power of 2.
            int scale=1;
            while(imageSizeOptions.outWidth/(scale*2)>=REQUIRED_SIZE && imageSizeOptions.outHeight/(scale*2)>=REQUIRED_SIZE)
                scale*=2;
            //Here we decode with inSampleSize
            BitmapFactory.Options inSampleSizeOption = new BitmapFactory.Options();
            inSampleSizeOption.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(imageFile), null, inSampleSizeOption);
        } catch (FileNotFoundException e) {}
        return null;
    }

    private void displayToast(String text) {
        Toast.makeText(getApplicationContext(), text , Toast.LENGTH_LONG).show();
    }

    private void copyDBFile() {
        //Here we make sure that the directory path for the database exists
        if(!this.dbPathFile.exists()) {
            this.dbPathFile.mkdirs();
        }
        File dbNameFile = new File(dbName);
        File dbAbsolutePathFileName = new File(dbPathFile.getAbsolutePath() + File.separator + dbName);
        //Here we check whether the database file exists or not. If not, we then create it
        if(!dbNameFile.exists()) {
            try {
                //Here we call the copyDB() method.
                copyDB(getApplicationContext().getAssets().open(dbName), new FileOutputStream(dbAbsolutePathFileName));
//                displayToast(dbName + " copied to " + dbAbsolutePathFileName );
            } catch (FileNotFoundException e) {
                displayMessage(getString(R.string.file_not_found) + ": " + e.getLocalizedMessage());
            } catch (IOException e) {
                displayMessage(getString(R.string.io_exception) + ": " + e.getLocalizedMessage());
            }
        }
        //Here we display feedback on whether the database file has been successfully copied or not.
        displayMessage(getString(R.string.file_exists) + " " + dbAbsolutePathFileName.exists() + "\n" + dbAbsolutePathFileName.getAbsolutePath());
        //Here we initialize the dbAdapter object
        dbAdapter = new DBAdapter(getApplicationContext(), this.dbPathFile.getAbsolutePath() + File.separator, dbName);
        //Here we indicate that the database file has been copied successfully.
        dbCreated=true;
        //Here we call getAllCustomers() method to show that the database
        //file has been successfully copied.
    }

    private void copyDB(InputStream inputStream, OutputStream outputStream) {
        //Copying 1K bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        try {
            while((length=inputStream.read(buffer))>0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            displayMessage(getString(R.string.io_exception) + ": " + e.getLocalizedMessage());
        }
    }

    private void displayMessage(String text) {
        Toast.makeText(getApplicationContext(), text , Toast.LENGTH_LONG).show();
    }
}
