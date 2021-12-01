package com.example.assignment7;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Update extends AppCompatActivity {
    int mainRequestCode = 0;
    int galleryRequestCode=6;

    private EditText meetingIdEt;
    private EditText titleEt;
    private EditText participantsEt;
    private Button timePickerBtn;
    private TextView timeTv;
    private Button datePickerBtn;
    private Button openDialogBtn;
    private TextView dateTv;
    DialogFragment newFragment;
    private Button updateBtn;
    private Button backBtn;
    private String preferencesName = "my_setting";

    private TextView titleTv;
    private TextView meetingId;
    private TextView meetingTitleTv;
    private TextView participantsTv;
    private TextView startDateTv;
    private TextView startTimeTv;
    DBAdapter dbAdapter=null;
    private ImageView imageView;

    private Dialog imageDialog;
    ArrayList<String> participantsArray = new ArrayList<String>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dbAdapter = new DBAdapter(getApplicationContext(),MainActivity.dbPathFile.getAbsolutePath() + File.separator, MainActivity.dbName);
        SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);

        getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));

        meetingIdEt = findViewById(R.id.editTextTextPersonName7);
        titleEt = findViewById(R.id.editTextTextPersonName);
        participantsEt = findViewById(R.id.editTextTextPersonName2);
        timePickerBtn = findViewById(R.id.time_button);
        datePickerBtn = findViewById(R.id.date_button);
        timeTv = findViewById(R.id.textView12);
        dateTv = findViewById(R.id.textView11);
        updateBtn = findViewById(R.id.button4);
        backBtn = findViewById(R.id.button9);
        backBtn.setOnClickListener(backClickListener);
        titleTv = findViewById(R.id.textView15);
        meetingId = findViewById(R.id.textView16);
        meetingTitleTv= findViewById(R.id.textView2);
        participantsTv= findViewById(R.id.textView3);
        startDateTv= findViewById(R.id.textView4);
        startTimeTv= findViewById(R.id.textView5);
        openDialogBtn = findViewById(R.id.button20);
        openDialogBtn.setOnClickListener(showImageDialogClick);

        //        set font size font color
        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);
        titleTv.setTextSize(fontSize);
        meetingId.setTextSize(fontSize);
        meetingTitleTv.setTextSize(fontSize);
        participantsTv.setTextSize(fontSize);
        startDateTv.setTextSize(fontSize);
        startTimeTv.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        titleTv.setTextColor(fontColor);
        meetingId.setTextColor(fontColor);
        meetingTitleTv.setTextColor(fontColor);
        participantsTv.setTextColor(fontColor);
        startDateTv.setTextColor(fontColor);
        startTimeTv.setTextColor(fontColor);

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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString();
                String participants = participantsEt.getText().toString();
                String startDate = dateTv.getText().toString();
                String startTime = timeTv.getText().toString();
                Integer id = Integer.parseInt(meetingIdEt.getText().toString());
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
                    long updateSuccess = 0;
                    updateSuccess = dbAdapter.updateCustomer(id, title, startDate + " " + startTime, participantsArray, images);
                    participantsArray = new ArrayList<String>();
                    images = new ArrayList<Bitmap>();
                    if(updateSuccess > 0) {
                        Toast.makeText(getBaseContext(),
                                "Id " + id + " was updated", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(getBaseContext(),
                                "Id " + id + " wasn't updated", Toast.LENGTH_LONG)
                                .show();
                    }
                    meetingIdEt.setText("");
                    titleEt.setText("");
                    participantsEt.setText("");
                    dateTv.setText("");
                    timeTv.setText("");
                }
            }
        });
    }

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


    private void startActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
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

    private View.OnClickListener showImageDialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showImageDialog();
        }
    };

    void showImageDialog() {
        imageDialog = new Dialog(Update.this);
        imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        imageDialog.setCancelable(true);
        imageDialog.setContentView(R.layout.activity_image_dialog);

        EditText participantsText = imageDialog.findViewById(R.id.editTextTextPersonName6);
        imageView = imageDialog.findViewById(R.id.imageView);
        final Button selectImageButton = imageDialog.findViewById(R.id.button14);
        final Button addImageButton = imageDialog.findViewById(R.id.button18);

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
}