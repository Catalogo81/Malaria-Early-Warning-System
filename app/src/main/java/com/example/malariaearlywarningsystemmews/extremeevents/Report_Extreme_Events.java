package com.example.malariaearlywarningsystemmews.extremeevents;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.classes.ObservedExtremeEvents;
import com.example.malariaearlywarningsystemmews.classes.ObservedIndicators;
import com.example.malariaearlywarningsystemmews.ikindicators.Report_IK_Indicators;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static com.example.malariaearlywarningsystemmews.ikindicators.Report_IK_Indicators.CAMERA_PERM_CODE;
import static com.example.malariaearlywarningsystemmews.ikindicators.Report_IK_Indicators.CAMERA_REQUEST_CODE;
import static com.example.malariaearlywarningsystemmews.ikindicators.Report_IK_Indicators.GALLERY_REQUEST_CODE;

public class Report_Extreme_Events extends AppCompatActivity {

    ImageView ivEventImage, ivEventImageFromGallery;
    EditText etEventDescription, etEventLocation;
    Button btnSubmitEvent;
    TextView tvLocation;
    Spinner eventSpinner;

    List<String> items;

    String myCountry, myLatitude, myLongitude, eventLevel, currentPhotoPath, userName, eventUsername,
            userSurname, userID, userEmail, extremeEvent, location, description, extremeEventImage;

    FusedLocationProviderClient fusedLocationProviderClient;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    /*------------------sets the date format---------------------*/
    String DateFormat = "dd/MM/yyyy";
    String currentDateString = new SimpleDateFormat(DateFormat, Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__extreme__events);

        ivEventImage = findViewById(R.id.ivEventImage);
        ivEventImageFromGallery = findViewById(R.id.ivEventImageFromGallery);
        etEventDescription = findViewById(R.id.etEventDescription);
        etEventLocation = findViewById(R.id.etEventLocation);
        btnSubmitEvent = findViewById(R.id.btnSubmitEvent);
        tvLocation = findViewById(R.id.tvLocation);
        eventSpinner = findViewById(R.id.eventSpinner);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();
        userEmail = firebaseAuth.getCurrentUser().getEmail();

        progressDialog = new ProgressDialog(this);

        items = new ArrayList<>();

        items.add("Moderate");
        items.add("Mild");
        items.add("Severe");


        eventSpinner.setAdapter(new ArrayAdapter<>(Report_Extreme_Events.this,
                R.layout.support_simple_spinner_dropdown_item, items));

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                eventLevel = eventSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

        ivEventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                askCameraPermission();

            }
        });

        ivEventImageFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Report_Extreme_Events.this, "Gallery opening...", Toast.LENGTH_SHORT).show();

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

        btnSubmitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Intent intent = getIntent();
        userName = intent.getStringExtra("user_name");
        userSurname = intent.getStringExtra("user_surname");
        eventUsername = userName + " " + userSurname;

    }


    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else
        {
            /*-----Opens our camera-----*/
            //openCamera();
            dispatchTakePictureIntent();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE)
        {
            /*When the user accepts the request for the system to access camera*/
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                /*-----Opens our camera-----*/
                dispatchTakePictureIntent();
                //openCamera();
            }
            else/*When the user denies the request for the system to access camera*/
            {
                Toast.makeText(this, "Camera Permission is Required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*-----if statement for the Camera Button request-----*/
        if(requestCode == CAMERA_REQUEST_CODE)
        {
            //sets our captured image to the image view
            if(resultCode == Activity.RESULT_OK)
            {
//                Bitmap image = (Bitmap) data.getExtras().get("data");
//                ivIndicatorImage.setImageBitmap(image);

                Toast.makeText(this, "Camera opened", Toast.LENGTH_SHORT).show();

                File f = new File(currentPhotoPath);
                ivEventImage.setImageURI(Uri.fromFile(f));
                Toast.makeText(this, "URL of image is: " + Uri.fromFile(f), Toast.LENGTH_SHORT).show();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                /*------uploading the image to Firebase------*/
                uploadImageToFirebase(f.getName(),contentUri);
                if(!f.getName().isEmpty() || !etEventLocation.getText().toString().isEmpty() || !etEventDescription.getText().toString().isEmpty())
                {
                    btnSubmitEvent.setOnClickListener(v -> {
                        uploadImageToFirebase(f.getName(),contentUri);
                        //uploadDialog.dismiss();
                    });
                }
                else
                {
                    btnSubmitEvent.setOnClickListener(v -> {
                        //progressDialog.dismiss();
                        Toast.makeText(Report_Extreme_Events.this, "No image to upload", Toast.LENGTH_SHORT).show();
                    });

                }

            }
        }

        /*-----if statement for the Gallery Button request-----*/
        if(requestCode == GALLERY_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);

                //Toast.makeText(this, "Gallery Image Uri: "+ imageFileName, Toast.LENGTH_SHORT).show();
                ivEventImage.setImageURI(contentUri);

                //uploading the image to Firebase
                uploadImageToFirebase(imageFileName,contentUri);
                if(imageFileName != null || !etEventDescription.getText().toString().isEmpty() || !etEventLocation.getText().toString().isEmpty())
                {
                    btnSubmitEvent.setOnClickListener(v -> {
                        uploadImageToFirebase(imageFileName,contentUri);
                    });
                }
                else
                {
                    //progressDialog.dismiss();
                    Toast.makeText(Report_Extreme_Events.this, "No image to upload", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    private void uploadImageToFirebase(String imageFileName, Uri contentUri) {

        //contains the path of our image and saves it in firebase images folder
        final StorageReference image = storageReference.child("CapturedExtremeEvents/" + imageFileName);
        //final StorageReference image = storageReference.child("CapturedReadings/" + firebaseAuth.getCurrentUser().getUid()).child("readings/" + name);

        //mProgressBar.setVisibility(View.VISIBLE);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        if(etEventDescription.getText().toString().isEmpty() || etEventLocation.getText().toString().isEmpty())
        {
            progressDialog.dismiss();
            Toast.makeText(Report_Extreme_Events.this, "Please enter enquired fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    /*------Reading details----*/
                    //date = etDate.getText().toString().trim();
                    extremeEvent = etEventDescription.getText().toString().trim();
                    extremeEventImage = image.getDownloadUrl().toString();
                    location = etEventLocation.getText().toString().trim();

                    SimpleDateFormat format1 =new SimpleDateFormat("dd/MM/yyyy");
                    java.text.DateFormat format2=new SimpleDateFormat("MMMM");

                    /*checks if the image is successfully uploaded*/
                    image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //getting the image from the database in our ImageView using Picasso
                            //Picasso.with(CaptureReading.this).load(uri).into(ivReading);
                            //Toast.makeText(CaptureReading.this, "image taken from picasso", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Toast.makeText(Report_Extreme_Events.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                    ObservedExtremeEvents observedExtremeEvents = new ObservedExtremeEvents(extremeEvent, extremeEventImage, location, currentDateString, eventLevel, userEmail, eventUsername);
                    String imageUploadedId = databaseReference.push().getKey();
                    assert imageUploadedId != null;
                    databaseReference.child("ObservedExtremeEvents").child(imageUploadedId).setValue(observedExtremeEvents);
                    clearData();
                }
            }).addOnFailureListener(e ->
                    Toast.makeText(Report_Extreme_Events.this, "Upload Failed", Toast.LENGTH_SHORT).show()

            );/*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });*/
        }

    }

    //clears the data once user successfully reports an indicator
    private void clearData()
    {
        etEventDescription.setText("");
        etEventLocation.setText("");
        //ivIndicatorImageFromGallery.setImageBitmap(null);
    }


    private String getFileExt(Uri contentUri)
    {
        //This method takes the different file extensions of images
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile() throws IOException
    {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //saves the image to our Gallery
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /*prefix*/
                ".jpg", /*suffix*/
                storageDir /*directory*/
        );

        //Save a file: path for yse with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent()
    {
        //Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(camera, CAMERA_REQUEST_CODE);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


    private void getCurrentLocation() {

        //Check permissions
        //Toast.makeText(this, "Getting Location...", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(Report_Extreme_Events.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when granted
            //getCurrentLocation();

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    //Initialize Location
                    Location location = task.getResult();

                    if (location != null)
                    {
                        try {
                            //Initialize geoCoder
                            Geocoder geocoder = new Geocoder(Report_Extreme_Events.this, Locale.getDefault());

                            //Initialize address list
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            //Set latitude on EditText
                            //Toast.makeText(Report_Extreme_Events.this, "Latitude: " + addresses.get(0).getLatitude(), Toast.LENGTH_LONG).show();
                            myLatitude = String.valueOf(addresses.get(0).getLatitude());
                            myCountry = String.valueOf(addresses.get(0).getCountryName());
                            myLongitude = String.valueOf(addresses.get(0).getLongitude());
                            etEventLocation.setText(Html.fromHtml("Lat(" + myLatitude + ")" + ", Lon(" + myLongitude + ")" ));
//                            etEventLocation.setText(Html.fromHtml("Current Location: " + myLatitude));
//                            etEventLocation.setText(Html.fromHtml("Current Location: " + myLatitude));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(Report_Extreme_Events.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    //closes the activity when the user presses the phone 'back' button
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}