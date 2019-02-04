package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.finalapp.MainActivity;
import com.example.ayush.finalapp.profileSource;
import com.example.ayush.finalapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Negotiator_final extends AppCompatActivity {


    ImageView viewImage;

    Button b;

    //declaring Firebase authentication object

    private FirebaseAuth mauth;

    // Declaring firebaseuser variable

    private FirebaseUser mUser;


    // creating object of Negotiatordetails

    NegotiatorDetails details;



    AlertDialog.Builder builder2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder=new StrictMode.VmPolicy.Builder ();
        StrictMode.setVmPolicy (builder.build ());
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_negotiator_final);

        mauth = FirebaseAuth.getInstance ();


        final CheckBox rb2 = (CheckBox) findViewById (R.id.rb);


        //// using the intent object of NegotiatorDetails

        details = (NegotiatorDetails) getIntent ().getSerializableExtra ("details");

        rb2.setChecked (!rb2.isChecked ());



        TextView term1 = (TextView) findViewById (R.id.terms);

        term1.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(final View v) {


                builder2 = new AlertDialog.Builder (Negotiator_final.this);

                LayoutInflater inflater = Negotiator_final.this.getLayoutInflater ();
                builder2.setView (inflater.inflate (R.layout.terms, null))
                        // Add action buttons

                        .setPositiveButton ("Accept", new DialogInterface.OnClickListener () {
                            @Override

                            public void onClick(DialogInterface dialog, int id) {
                                // sign in the user ...
                                //radio
                                CheckBox rb = (CheckBox) findViewById (R.id.rb);
                                rb.setChecked (true);


                            }
                        })
                        .setNegativeButton ("Decline", new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface dialog, int id) {
                                CheckBox rb = (CheckBox) findViewById (R.id.rb);
                                if (rb.isChecked ())
                                    rb.setChecked (!rb.isChecked ());
                                dialog.cancel ();
                            }
                        });


                //Setting message manually and performing action on button click
                builder2.setCancelable (false);

                //Creating dialog box
                AlertDialog alert = builder2.create ();

                alert.show ();

            }


            //

        });



        //3
//        b1 = (Button) findViewById (R.id.btnSelectPhoto);
//
//        viewImage = (ImageView) findViewById (R.id.profilepic);
//
//
//        b1.setOnClickListener (new View.OnClickListener () {
//
//            @Override
//
//            public void onClick(View v) {
//
//                selectImage ();
//
//            }
//
//        });

        b = (Button) findViewById (R.id.btnSelectID);

        viewImage = (ImageView) findViewById (R.id.idimage); // id picture for valid id .

        b.setOnClickListener (new View.OnClickListener () {

            @Override

            public void onClick(View v) {

                selectImage ();

            }

        });

        Button nextButton = (Button) findViewById (R.id.next1);


        nextButton.setOnClickListener (new View.OnClickListener () {


            @Override
            public void onClick(View v) {

//                //3c2
//                final ImageView test = (ImageView) findViewById (R.id.profilepic); //image stored here
//                final Bitmap bmap = ((BitmapDrawable) test.getDrawable ()).getBitmap ();
//                Drawable myDrawable = getResources ().getDrawable (R.drawable.user);
//                final Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap ();

                //idk
                int k = 1;

// changed final string to variable name
//                3
                if (viewImage.getDrawable () == null) {
                    k--;
                    Toast.makeText (Negotiator_final.this, "Enter an ID", Toast.LENGTH_SHORT).show ();

                }//3

//                if (bmap.sameAs (myLogo)) {
//                    k--;
//                    Toast.makeText (Negotiator_final.this, "Add Profile Picture", Toast.LENGTH_SHORT).show ();
//
//                }
               if (k == 1 && rb2.isChecked ()) {
                    //categoryregis();
                    Intent myIntent = new Intent (Negotiator_final.this,
                           Negotiator_dash.class);
                    startActivity (myIntent);
                }
            }


        });

    }
        private void selectImage () {


            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


            AlertDialog.Builder builder = new AlertDialog.Builder (Negotiator_final.this);

            builder.setTitle ("Add Photo!");

            builder.setItems (options, new DialogInterface.OnClickListener () {

                @Override

                public void onClick(DialogInterface dialog, int item) {

                    if (options[item].equals ("Take Photo")) {

                        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

                        File f = new File (android.os.Environment.getExternalStorageDirectory (), "temp.jpg");

                        intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));

                        startActivityForResult (intent, 1);

                    } else if (options[item].equals ("Choose from Gallery")) {

                        Intent intent = new Intent (Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult (intent, 2);


                    } else if (options[item].equals ("Cancel")) {

                        dialog.dismiss ();


                    }
                }

            });

            builder.show ();



    }

      //2
        @Override

        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            Log.v ("ssasad", "RESULTCODE:" + Integer.toString (requestCode));

            super.onActivityResult (requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {

                if (requestCode == 1) {

                    File f = new File (Environment.getExternalStorageDirectory ().toString ());

                    for (File temp : f.listFiles ()) {

                        if (temp.getName ().equals ("temp.jpg")) {

                            f = temp;

                            break;

                        }

                    }

                    try {

                        Bitmap bitmap;

                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options ();


                        bitmap = BitmapFactory.decodeFile (f.getAbsolutePath (),

                                bitmapOptions);


                        viewImage.setImageBitmap (bitmap);


                        String path = android.os.Environment

                                .getExternalStorageDirectory ()

                                + File.separator

                                + "Phoenix" + File.separator + "default";

                        f.delete ();

                        OutputStream outFile = null;

                        File file = new File (path, String.valueOf (System.currentTimeMillis ()) + ".jpg");

                        try {

                            outFile = new FileOutputStream (file);

                            bitmap.compress (Bitmap.CompressFormat.JPEG, 85, outFile);

                            outFile.flush ();

                            outFile.close ();

                        } catch (FileNotFoundException e) {

                            e.printStackTrace ();

                        } catch (IOException e) {

                            e.printStackTrace ();

                        } catch (Exception e) {

                            e.printStackTrace ();

                        }

                    } catch (Exception e) {

                        e.printStackTrace ();

                    }

                } else if (requestCode == 2) {


                    Uri selectedImage = data.getData ();

                    String[] filePath = {MediaStore.Images.Media.DATA};

                    Cursor c = getContentResolver ().query (selectedImage, filePath, null, null, null);

                    c.moveToFirst ();

                    int columnIndex = c.getColumnIndex (filePath[0]);

                    String picturePath = c.getString (columnIndex);

                    c.close ();

                    Bitmap thumbnail = (BitmapFactory.decodeFile (picturePath));

                    Log.w ("pery", picturePath + "");

                    viewImage.setImageBitmap (thumbnail);
                }
            }

        }


    //3

}





