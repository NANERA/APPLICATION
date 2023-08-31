package online.intershipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button login,singup;
    String emailpattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText email,passwoard;
    SQLiteDatabase db;
    SharedPreferences sp;
    CheckBox rememberMe;
    ImageView passwordhide,passwordshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences(ConstanSp.PREF,MODE_PRIVATE);

        db=openOrCreateDatabase("online_intershipe",MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS (USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INT(10),PASSWOARD VARCHAR(20),GENDER VARCHAR(6),CITY VARCHAR(50),DOB VARCHAR(10))";
        db.execSQL(tableQuery);
        email = findViewById(R.id.main_email);
        passwoard = findViewById(R.id.main_Password);
        passwordshow =findViewById(R.id.login_passwoard_show);
        passwordhide = findViewById(R.id.login_passwoard_hide);

         passwordhide.setOnClickListener(new View.OnClickListener() {
             public void onClick(View view) {
                 passwordhide.setVisibility(View.GONE);
                 passwordshow.setVisibility(View.VISIBLE);
                 passwoard.setTransformationMethod(null);
             }
         });
        passwordshow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                passwordhide.setVisibility(View.VISIBLE);
                passwordshow.setVisibility(View.GONE);
                passwoard.setTransformationMethod(new PasswordTransformationMethod());
            }
        });

        rememberMe=findViewById(R.id.main_remember);
        singup=findViewById(R.id.main_signup);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(MainActivity.this, SignupActivity.class);
            }
        });
        login = findViewById(R.id.main_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().trim().matches(emailpattern)) {
                    email.setError("Valid Email Id Required");
                }
                  else if(passwoard.getText().toString().trim().equals("")) {
                    passwoard.setError("Passwoard required");
                } else if (passwoard.getText().toString().trim().length()<6) {
                    passwoard.setError("Minimum 6 Character Passwoard Required");
                } else {
                      String selectQuery = "SELECT * FROM USERS WHERE EMAIL='"+email.getText().toString()+"'AND PASSWOARD='"+passwoard.getText().toString()+"'";
                      Cursor cursor = db.rawQuery(selectQuery,null);
                      Log.d("CURSOR_COUNT",String.valueOf(cursor.getCount()));
                      if (cursor.getCount()>0) {
                          while (cursor.moveToNext()) {
                              String sUserId = cursor.getString(0);
                              String sName = cursor.getString(1);
                              String sEmail = cursor.getString(2);
                              String sContact = cursor.getString(3);
                              String sPasswoard = cursor.getString(4);
                              String sGender = cursor.getString(5);
                              String sCity = cursor.getString(6);
                              String sD0b = cursor.getString(7);

                              sp.edit().putString(ConstanSp.ID,sUserId).commit();
                              sp.edit().putString(ConstanSp.NAME,sName).commit();
                              sp.edit().putString(ConstanSp.EMAIL,sEmail).commit();
                              sp.edit().putString(ConstanSp.CONTACT,sContact).commit();
                              sp.edit().putString(ConstanSp.PASSWOARD,sPasswoard).commit();
                              sp.edit().putString(ConstanSp.GENDER,sGender).commit();
                              sp.edit().putString(ConstanSp.CITY,sCity).commit();
                              sp.edit().putString(ConstanSp.DOB,sD0b).commit();
                              if (rememberMe.isChecked()) {
                                  sp.edit().putString(ConstanSp.REMEMBER, "YES").commit();
                              }
                              else {
                                  sp.edit().putString(ConstanSp.REMEMBER, "").commit();
                              }
                              Log.d("USER_DATA",sUserId+"\n"+sName+"\n"+sEmail+"\n"+sContact+"\n"+sPasswoard+"\n"+sGender+"\n"+sCity+"\n"+sD0b);
                          }
                        System.out.println("Login successfully");
                        new CommonMethod(MainActivity.this, "Logine Successfully");
                        new CommonMethod(view, "Login Successfully");
                        new CommonMethod(MainActivity.this, DashboardActivity.class);
                     }
                      else {
                        new CommonMethod(MainActivity.this, "Logine Unsuccessfully");}
                }
             }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}