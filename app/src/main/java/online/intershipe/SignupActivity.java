package online.intershipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.sql.SQLData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {
    Button login,signup;
    String emailpattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText email,Name,confirmpasswoard,contact,passwoard,dob;
    RadioGroup gender;
    Spinner city;
    ArrayList<String> arrayList;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    String sCity = "";
    String sGender;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db=openOrCreateDatabase("online_intershipe",MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INT(10),PASSWOARD VARCHAR(20),GENDER VARCHAR(6),CITY VARCHAR(50),DOB VARCHAR(10))";
        db.execSQL(tableQuery);
        Name=findViewById(R.id.Signup_name);
        contact=findViewById(R.id.Signup_contact);
        confirmpasswoard=findViewById(R.id.Signup_ConfirmPassword);
        dob=findViewById(R.id.Signup_dob);
        calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateclick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dob.setText(sdf.format(calendar.getTime()));
            }
        };
        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,dateclick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                        datePickerDialog.show();
                        return true;
            }
        });
        email = findViewById(R.id.Signup_email);
        passwoard = findViewById(R.id.Signup_Password);
        signup=findViewById(R.id.Signup_signup);
        login = findViewById(R.id.Signup_login);
        city=findViewById(R.id.signup_city);
        arrayList =new ArrayList<>();
        arrayList.add("Select City");
        arrayList.add("Gandhinager");
        arrayList.add("Rajkot");
        arrayList.add("Jamnager");
        arrayList.add("Bhanvad");
        arrayList.add("Ahemdabad");
        arrayList.add("Porbandar");
        arrayList.add("Okha");
        arrayList.add("Surat");
        arrayList.add("Dahod");
        arrayList.add("Bhavnager");
        ArrayAdapter adapter= new ArrayAdapter(SignupActivity.this, android.R.layout.simple_list_item_1,arrayList);
         city.setAdapter(adapter);
         adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
         city.setAdapter(adapter);
         city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 if (i == 0) {
                     sCity = "";
                 } else {
                     sCity=arrayList.get(i);
                     new CommonMethod(SignupActivity.this,sCity );
                 }
              }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });
         gender=findViewById(R.id.Signup_gender);
         gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, int i) {
                 RadioButton radioButton = findViewById(i);
                 sGender=radioButton.getText().toString();
                 new CommonMethod(SignupActivity.this,sGender);
             }
         });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Name.getText().toString().trim().equals("")) {
                    Name.setError("Name Required");
                } else if (email.getText().toString().trim().equals("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().trim().matches(emailpattern)) {
                    email.setError("Valid Email Id Required");
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Contact No. Required");
                } else if (contact.getText().toString().trim().length() < 10) {
                    contact.setError("Valid Contact No. Required");
                } else if (passwoard.getText().toString().trim().equals("")) {
                    passwoard.setError("Passwoard required");
                } else if (passwoard.getText().toString().trim().length() < 6) {
                    passwoard.setError("Minimum 6 Character Passwoard Required");
                } else if (confirmpasswoard.getText().toString().trim().equals("")) {
                    confirmpasswoard.setError("Confirm Passwoard required");
                } else if (passwoard.getText().toString().trim().length() < 6) {
                    passwoard.setError("Minimum 6 Character Confirm Passwoard Required");
                } else if (!confirmpasswoard.getText().toString().trim().matches(passwoard.getText().toString().trim())) {
                    confirmpasswoard.setError("Passwoard Does Not Match");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    new CommonMethod(SignupActivity.this, "Please Select Gender");
                } else if (sCity.equals("")) {
                    new CommonMethod(SignupActivity.this, "Please Select City");
                } else if (dob.getText().toString().trim().equals("")) {
                    dob.setError("Please Select Date Of Birth");
                } else {
                    String selectQuery = "SELECT * FROM USERS WHERE EMAIL='" + email.getText().toString() + "'OR CONTACT='" + contact.getText().toString() + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        new CommonMethod(SignupActivity.this, "Email Id/Contact No.Already Registered");
                    } else {
                        String insertQuery = "INSERT INTO USERS VALUES(NULL,'" + Name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "','" + passwoard.getText().toString() + "','" + sGender + "','" + sCity + "','" + dob.getText().toString() + "')";
                        db.execSQL(insertQuery);
                        System.out.println("Sign up successfully");
                        new CommonMethod(SignupActivity.this, "Signup Successfully");
                        new CommonMethod(view, "Signup Successfully");
                        onBackPressed();
                    }
                }
                }
             });
    }
}