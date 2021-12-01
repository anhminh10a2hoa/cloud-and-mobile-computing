package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import java.io.File;
import java.util.Vector;

public class select_images extends AppCompatActivity {

    int mainRequestCode = 0;
    int galleryRequestCode=7;

    String meetingTitle = "";

    private Button backButton;
    private Button showImage;
    private TextView participants;
    private TextView participantsAndImage;
    private TextView imagePath;
    private EditText participantName;
    private Button addImage;
    private String preferencesName = "my_setting";
    DBAdapter dbAdapter=null;

    ImageView selectedImageView;
    Bitmap imageBitmap;
    String dirName;
    //Here we define the directory path for the database on SD card
    File dbPathFile;
    //Here we define the name of the database and the table
    String dbName;
    String tableName;
    File dbAbsolutePathFileName;
    String data="";
    ArrayList<ImageParticipants> imageParticipants = new ArrayList<ImageParticipants>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_images);
        dbName=getString(R.string.db_name);
        tableName=getString(R.string.table_name);
        dirName=getString(R.string.dir_name_database);
        dbPathFile = getApplicationContext().getExternalFilesDir(dirName);
        if(dbPathFile == null) {
            displayMessage(getString(R.string.not_found));
            return;
        }
        dbAbsolutePathFileName = new File(dbPathFile.getAbsolutePath() + File.separator + dbName);
        dbAdapter = new DBAdapter(getApplicationContext(), this.dbPathFile.getAbsolutePath() + File.separator, dbName, tableName );
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            data= extras.getString("data");
            meetingTitle = extras.getString("title");
        }

        SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);

        getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));

        backButton = findViewById(R.id.button18);
        addImage = findViewById(R.id.button20);
        showImage = findViewById(R.id.button19);
        participantName = findViewById(R.id.editTextTextPersonName6);
        participants = findViewById(R.id.textView20);
        participantsAndImage = findViewById(R.id.textView22);
        imagePath = findViewById(R.id.textView23);
        backButton.setOnClickListener(backClickListener);

        participants.setText(data);

        String result = "";
        for (ImageParticipants im : imageParticipants) {
            result += im.toString();
        }
        participantsAndImage.setText(result);

        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);
        participants.setTextSize(fontSize);
        participantsAndImage.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        participants.setTextColor(fontColor);
        participantsAndImage.setTextColor(fontColor);
        showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here we create an Intent to be used for invoking the gallery application
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Here we invoke the gallery application to pick an image
                startActivityForResult(Intent.createChooser(galleryIntent, getString(R.string.select_image_title)), galleryRequestCode);
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pName = participantName.getText().toString();
                String res = "";
                String[] name = participants.getText().toString().split(",");
                imageBitmap = ((BitmapDrawable) selectedImageView.getDrawable()).getBitmap();
                Toast.makeText(getApplicationContext(), imageBitmap.toString() , Toast.LENGTH_LONG).show();

                boolean a = false;
                for (ImageParticipants im : imageParticipants) {
                    if(pName.equals(im.getName())) {
                        for (int i = 0; i < name.length; i++) {
                            if(pName.equals(name[i])) {

                                im.setImage(imageBitmap);
                                im.setImagePath(imagePath.getText().toString());
                                a = true;
                                break;
                            }
                        }
                    }
                    if(a) {
                        imageParticipants.add(new ImageParticipants(pName, imageBitmap, imagePath.getText().toString()));
                    }
                }
                if(imageParticipants.size() == 0) {
                    for (int i = 0; i < name.length; i++) {
                        if(pName.equals(name[i])) {
                            imageParticipants.add(new ImageParticipants(pName, imageBitmap, imagePath.getText().toString()));
                        }
                    }
                }

                for (ImageParticipants im : imageParticipants) {
                    res += im.toString();
                }
                participantsAndImage.setText(res);
            }
        });

    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("participantsImage", imageParticipants);
        intent.putExtra("meetingTitle", meetingTitle);
        intent.putExtra("participants", data);
        startActivityForResult(intent, mainRequestCode);
    }

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity();
        }
    };
    private void displayMessage(String text) {
        Toast.makeText(getApplicationContext(), text , Toast.LENGTH_LONG).show();
    }
    private String displayData(Vector<Object[]> dataRows){
        String data = "";
        for(Object[] dataRow : dataRows ) {
            displayMessage(dataRows.toString());
            for (Object obj : dataRow) {
                data += obj;
            }
        }
        return data;
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = { MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        assert cursor != null;
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryRequestCode) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImageUri = data.getData();
                //Here we set the text of imagePathEditText the directory path of the
                //selected image
                String imagePathUrl = getRealPathFromURI(selectedImageUri);
                imagePath.setText(imagePathUrl);
                selectedImageView = findViewById(R.id.imageView);
                InputStream inputStream = null;
                Bitmap bitmap = null;
                try {
                    //We call the decodeFile() method to scale down the image
                    bitmap = decodeFile(selectedImageUri);
                    //Here we set the image of the ImageView to the selected image
                    if (bitmap != null) {
                        Toast.makeText(getApplicationContext(), bitmap.toString() , Toast.LENGTH_LONG).show();
                        selectedImageView.setImageBitmap(bitmap);
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
    //Here we decode the image and scales it to reduce memory consumption
    private Bitmap decodeFile(Uri selectedImageUri) throws IOException{
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
    private void displayResult(long id) {
        Toast.makeText(getApplicationContext(),  getString(R.string.returned_value)+ ": " + id, Toast.LENGTH_LONG).show();
    }
}