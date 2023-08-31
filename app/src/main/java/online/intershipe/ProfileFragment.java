package online.intershipe;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

  public class ProfileFragment extends Fragment {

    SharedPreferences sp;
    Button logout,updateprofile,editprofile;

    String emailpattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText email,Name,contact,dob,passwoard;
    RadioGroup gender;
    RadioButton male,female;
    Spinner city;

    ArrayList<String> arrayList;
    DatePickerDialog datePickerDialog;
    Calendar calendar;
    String sCity = "";
    String sGender;
    SQLiteDatabase db;

      public ProfileFragment() {
                // Required empty public constructor
            }
             @Override
             public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sp = getActivity().getSharedPreferences(ConstanSp.PREF, Context.MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("online_intershipe",Context.MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INT(10),PASSWOARD VARCHAR(20),GENDER VARCHAR(6),CITY VARCHAR(50),DOB VARCHAR(10))";
        db.execSQL(tableQuery);
        Name = view.findViewById(R.id.home_name);
        contact = view.findViewById(R.id.home_contact);
        dob = view.findViewById(R.id.home_dob);
        calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateclick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dob.setText(sdf.format(calendar.getTime()));
            }
        };
        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateclick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
                return true;
            }
        });
        email = view.findViewById(R.id.home_email);
        updateprofile = view.findViewById(R.id.home_Update_profile);
        logout = view.findViewById(R.id.home_logout);
        city = view.findViewById(R.id.home_city);
        arrayList = new ArrayList<>();
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
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        city.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    sCity = "";
                } else {
                    sCity = arrayList.get(i);
                    new CommonMethod(getActivity(), sCity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        gender = view.findViewById(R.id.home_gender);
        male = view.findViewById(R.id.home_male);
        female = view.findViewById(R.id.home_Female);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = view.findViewById(i);
                sGender = radioButton.getText().toString();
                new CommonMethod(getActivity(), sGender);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                new CommonMethod(getActivity(), MainActivity.class);
            }
        });
        updateprofile = view.findViewById(R.id.home_Update_profile);
        updateprofile.setOnClickListener(new View.OnClickListener() {
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
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    new CommonMethod(getActivity(), "Please Select Gender");
                } else if (sCity.equals("")) {
                    new CommonMethod(getActivity(), "Please Select City");
                } else if (dob.getText().toString().trim().equals("")) {
                    dob.setError("Please Select Date Of Birth");
                } else {
                    String selectQuery = "SELECT * FROM USERS WHERE USERID='" + sp.getString(ConstanSp.ID, "") + "'";
                    Cursor cursor = db.rawQuery(selectQuery, null);
                    if (cursor.getCount() > 0) {
                        String UpdateQuery = "UPDATE USERS SET NAME='" + Name.getText().toString() + "',EMAIL='" + email.getText().toString() + "',CONTACT='" + contact.getText().toString() + "',GENDER='" + sGender + "',CITY='" + sCity + "',DOB='" + dob.getText().toString() + "'WHERE USERID='" + sp.getString(ConstanSp.ID, "") + "'";
                        db.execSQL(UpdateQuery);
                        new CommonMethod(getActivity(), "Update Successfully");
                        sp.edit().putString(ConstanSp.NAME,Name.getText().toString()).commit();
                        sp.edit().putString(ConstanSp.EMAIL,email.getText().toString()).commit();
                        sp.edit().putString(ConstanSp.CONTACT,contact.getText().toString()).commit();
                        sp.edit().putString(ConstanSp.GENDER,sGender).commit();
                        sp.edit().putString(ConstanSp.CITY,sCity).commit();
                        sp.edit().putString(ConstanSp.DOB,dob.getText().toString()).commit();
                        setData(false);
                    } else {
                        String insertQuery = "INSERT INTO USERS VALUES(NULL,'" + Name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "','" + passwoard.getText().toString() + "','" + sGender + "','" + sCity + "','" + dob.getText().toString() + "')";
                        new CommonMethod(getActivity(), "Invaliid UserId");
                    }
                }
            }
        });
        editprofile = view.findViewById(R.id.home_Edit_profile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(true);
            }
        });
        setData(false);
        return view;
    }

    private void setData(boolean isEnable) {
        Name.setEnabled(isEnable);
        email.setEnabled(isEnable);
        contact.setEnabled(isEnable);
        dob.setEnabled(isEnable);
        male.setEnabled(isEnable);
        female.setEnabled(isEnable);
        city.setEnabled(isEnable);
        if (isEnable){
            editprofile.setVisibility(View.GONE);
            updateprofile.setVisibility(View.VISIBLE);
        }
        else {
            editprofile.setVisibility(View.VISIBLE);
            updateprofile.setVisibility(View.GONE);
        }
        Name.setText(sp.getString(ConstanSp.NAME,""));
        email.setText(sp.getString(ConstanSp.EMAIL,""));
        contact.setText(sp.getString(ConstanSp.CONTACT,""));
        dob.setText(sp.getString(ConstanSp.DOB,""));
        //male.setChecked(true);
        sGender = sp.getString(ConstanSp.GENDER,"");
        if (sGender.equalsIgnoreCase("Male")){
            male.setChecked(true);
        } else if (sGender.equalsIgnoreCase("Female")) {
            female.setChecked(true);

        }
        else {
        }
            sCity = sp.getString(ConstanSp.CITY, "");
        int iCityPosition = 0;
            for (int i = 0;i < arrayList.size();i++) {
                if (sCity.equalsIgnoreCase(arrayList.get(i))){
                    iCityPosition=i;

        }
    }
            city.setSelection(iCityPosition);
   }

}