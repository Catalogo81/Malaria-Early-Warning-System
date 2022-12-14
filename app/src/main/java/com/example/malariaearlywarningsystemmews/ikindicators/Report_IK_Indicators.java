package com.example.malariaearlywarningsystemmews.ikindicators;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malariaearlywarningsystemmews.MainActivity;
import com.example.malariaearlywarningsystemmews.R;
import com.example.malariaearlywarningsystemmews.classes.Indicators;
import com.example.malariaearlywarningsystemmews.classes.ObservedIndicators;
import com.example.malariaearlywarningsystemmews.extremeevents.Report_Extreme_Events;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Report_IK_Indicators extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 103;

    ImageView ivIndicatorImage, ivIndicatorImageFromGallery;
    EditText etIndicatorDescription, etIndicatorLocation;
    Button btnSubmitIndicator, btnCamera, btnGallery;
    TextView tvIKIndicator;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    String myCountry, myLatitude, myLongitude, currentPhotoPath,
            userID, userEmail, indicator, date, location, description, indicatorImage;

    FusedLocationProviderClient fusedLocationProviderClient;

    /*------------------sets the date format---------------------*/
    String DateFormat = "dd/MM/yyyy";
    String currentDateString = new SimpleDateFormat(DateFormat, Locale.getDefault()).format(new Date());

    private static final DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_ik__indicators);

        ivIndicatorImage = findViewById(R.id.ivIndicatorImage);
        //ivIndicatorImageFromGallery = findViewById(R.id.ivIndicatorImageFromGallery);
        etIndicatorDescription = findViewById(R.id.etIndicatorDescription);
        etIndicatorLocation = findViewById(R.id.etIndicatorLocation);
        btnSubmitIndicator = findViewById(R.id.btnSubmitIndicator);
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        tvIKIndicator = findViewById(R.id.tvIKIndicator);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();
        userEmail = firebaseAuth.getCurrentUser().getEmail();

        progressDialog = new ProgressDialog(this);

        //get the text clicked from the Select_Indicator activity
        tvIKIndicator.setText(getIntent().getExtras().getString("indicator"));

        //set the indicator variable
        if(!tvIKIndicator.getText().toString().isEmpty())
            indicator = tvIKIndicator.getText().toString();

        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

//        ivIndicatorImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(Report_IK_Indicators.this, "Camera opening...", Toast.LENGTH_SHORT).show();
//                askCameraPermission();
//
//            }
//        });

        btnCamera.setOnClickListener(view -> {
            askCameraPermission();
        });

        btnGallery.setOnClickListener(view -> {
            Toast.makeText(Report_IK_Indicators.this, "Gallery opening...", Toast.LENGTH_SHORT).show();

            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GALLERY_REQUEST_CODE);
        });

//        ivIndicatorImageFromGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(Report_IK_Indicators.this, "Gallery opening...", Toast.LENGTH_SHORT).show();
//
//                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
//            }
//        });

//        btnSubmitIndicator.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(Report_IK_Indicators.this, "Submit clicked", Toast.LENGTH_SHORT).show();
//                //uploadImageToFirebase();
//
//            }
//        });
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
            //Toast.makeText(this, "Permissions Granted on askCameraPermission()", Toast.LENGTH_SHORT).show();
            dispatchTakePictureIntent();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

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

//    private void openCamera()
//    {
//        //open camera app
//        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(camera, CAMERA_REQUEST_CODE);
//        //Toast.makeText(this, "Camera Open Request", Toast.LENGTH_SHORT).show();
//
//    }

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

                //Toast.makeText(this, "Camera opened", Toast.LENGTH_SHORT).show();

                File f = new File(currentPhotoPath);
                if(ivIndicatorImage != null)
                    ivIndicatorImage.setImageURI(Uri.fromFile(f));
                Toast.makeText(this, "URL of image is: " + Uri.fromFile(f), Toast.LENGTH_SHORT).show();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                /*------uploading the image to Firebase------*/
              uploadImageToFirebase(f.getName(),contentUri);
                if(!f.getName().isEmpty() || !etIndicatorLocation.getText().toString().isEmpty() || !etIndicatorDescription.getText().toString().isEmpty())
                {
                    btnSubmitIndicator.setOnClickListener(v -> {
                        uploadImageToFirebase(f.getName(),contentUri);
                        //uploadDialog.dismiss();
                    });
                }
                else
                {
                    btnSubmitIndicator.setOnClickListener(v -> {
                        //progressDialog.dismiss();
                        Toast.makeText(Report_IK_Indicators.this, "No image to upload", Toast.LENGTH_SHORT).show();
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
                ivIndicatorImage.setImageURI(contentUri);

                //uploading the image to Firebase
                uploadImageToFirebase(imageFileName,contentUri);
                if(imageFileName != null || !etIndicatorDescription.getText().toString().isEmpty() || !etIndicatorLocation.getText().toString().isEmpty())
                {
                    btnSubmitIndicator.setOnClickListener(v -> {
                        uploadImageToFirebase(imageFileName,contentUri);
                    });
                }
                else
                {
                    //progressDialog.dismiss();
                    Toast.makeText(Report_IK_Indicators.this, "No image to upload", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void uploadImageToFirebase(String imageFileName, Uri contentUri) {

        //contains the path of our image and saves it in firebase images folder
        final StorageReference image = storageReference.child("CapturedIndicator/" + imageFileName);
        //final StorageReference image = storageReference.child("CapturedReadings/" + firebaseAuth.getCurrentUser().getUid()).child("readings/" + name);

        //mProgressBar.setVisibility(View.VISIBLE);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        if(etIndicatorDescription.getText().toString().isEmpty() || etIndicatorLocation.getText().toString().isEmpty())
        {
            progressDialog.dismiss();
            Toast.makeText(Report_IK_Indicators.this, "Please enter enquired fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    /*------Reading details----*/
                    //date = etDate.getText().toString().trim();
                    description = etIndicatorDescription.getText().toString().trim();
                    indicatorImage = image.getDownloadUrl().toString();
                    location = etIndicatorLocation.getText().toString().trim();
                    String status = "pending";

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

                    Toast.makeText(Report_IK_Indicators.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                    ObservedIndicators observedIndicators = new ObservedIndicators(userID, indicator, location,description, indicatorImage, currentDateString, status, userEmail);
                    String imageUploadedId = databaseReference.push().getKey();
                    assert imageUploadedId != null;
                    databaseReference.child("ObservedIndicators").child(imageUploadedId).setValue(observedIndicators);
                    clearData();
                }
            }).addOnFailureListener(e ->
                    Toast.makeText(Report_IK_Indicators.this, "Upload Failed", Toast.LENGTH_SHORT).show()

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
        etIndicatorLocation.setText("");
        etIndicatorDescription.setText("");
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

        //Toast.makeText(this, "dispatchTakePictureIntent() Method entered", Toast.LENGTH_LONG).show();

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
        if (ActivityCompat.checkSelfPermission(Report_IK_Indicators.this,
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
                            Geocoder geocoder = new Geocoder(Report_IK_Indicators.this, Locale.getDefault());

                            //Initialize address list
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            //Set latitude on EditText
                            //Toast.makeText(Report_Extreme_Events.this, "Latitude: " + addresses.get(0).getLatitude(), Toast.LENGTH_LONG).show();
                            myLatitude = String.valueOf(addresses.get(0).getLatitude());
                            myCountry = String.valueOf(addresses.get(0).getCountryName());
                            myLongitude = String.valueOf(addresses.get(0).getLongitude());
                            etIndicatorLocation.setText(Html.fromHtml("Lat(" + myLatitude + ")" + ", Lon(" + myLongitude + ")" ));
//                            etEventLocation.setText(Html.fromHtml("Current Location: " + myLatitude));
//                            etEventLocation.setText(Html.fromHtml("Current Location: " + myLatitude));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(Report_IK_Indicators.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    //closes the activity when the user presses the phone 'back' button
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Report_IK_Indicators.this, Select_IK_Indicator.class));
        finish();
        super.onBackPressed();
    }
}