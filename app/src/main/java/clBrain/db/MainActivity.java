package clBrain.db;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    ImageButton nextDay, prevDay;
    TextView DayOfWeek, Date, GroupName;
    TextView[] task = new TextView[10];
    TextView[] lesson = new TextView[10];
    ImageView btnEdit, btnAdd;
    LinearLayout chooseDate;
    int myYear, myMonth, myDay;
    int DIALOG_DATE = 1;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GroupName = findViewById(R.id.group_name);
        Date = findViewById(R.id.main_date);
        DayOfWeek = findViewById(R.id.main_day_of_week);

        nextDay = findViewById(R.id.main_next_day);
        prevDay = findViewById(R.id.main_prev_day);
        chooseDate = findViewById(R.id.main_choose_date);
        btnAdd = findViewById(R.id.app_bar_add);
        btnEdit = findViewById(R.id.app_bar_edit);

        task[0] = (TextView) findViewById(R.id.task0);
        task[1] = (TextView) findViewById(R.id.task1);
        task[2] = (TextView) findViewById(R.id.task2);
        task[3] = (TextView) findViewById(R.id.task3);
        task[4] = (TextView) findViewById(R.id.task4);
        task[5] = (TextView) findViewById(R.id.task5);
        task[6] = (TextView) findViewById(R.id.task6);
        task[7] = (TextView) findViewById(R.id.task7);
        task[8] = (TextView) findViewById(R.id.task8);
        task[9] = (TextView) findViewById(R.id.task9);
        lesson[0] = (TextView) findViewById(R.id.lesson0);
        lesson[1] = (TextView) findViewById(R.id.lesson1);
        lesson[2] = (TextView) findViewById(R.id.lesson2);
        lesson[3] = (TextView) findViewById(R.id.lesson3);
        lesson[4] = (TextView) findViewById(R.id.lesson4);
        lesson[5] = (TextView) findViewById(R.id.lesson5);
        lesson[6] = (TextView) findViewById(R.id.lesson6);
        lesson[7] = (TextView) findViewById(R.id.lesson7);
        lesson[8] = (TextView) findViewById(R.id.lesson8);
        lesson[9] = (TextView) findViewById(R.id.lesson9);

        SwipeEvent swipeEvent = new SwipeEvent(findViewById(R.id.main_table));
        swipeEvent.setOnSwipeListener(new SwipeEvent.onSwipeEventListener() {
            @Override
            public void onLeftSwipe() {
                nextDay.callOnClick();
            }

            @Override
            public void onRightSwipe() {
                prevDay.callOnClick();
            }

            @Override
            public void onDownSwipe() {

            }

            @Override
            public void onUpSwipe() {

            }
        });


        //ToAddFile.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        startActivity(new Intent(getApplicationContext(), AddFileToProfile.class));
        //    }
        //});

        reference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_DATE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), AdminAuth.class), 0);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), AdminAuth2.class), 0);
            }
        });


        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextday();
                String date = Date.getText().toString();
                String dayOfWeek = DayOfWeek.getText().toString();
                date = date.substring(0, 2) + date.substring(3, 5) + date.substring(6, 10);
                myDay = Integer.valueOf(date.substring(0, 2));
                myMonth = Integer.valueOf(date.substring(2, 4));
                myYear = Integer.valueOf(date.substring(4, 8));
                adaptMain(date, dayOfWeek);
            }
        });
        prevDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevday();
                String date = Date.getText().toString();
                String dayOfWeek = DayOfWeek.getText().toString();
                date = date.substring(0, 2) + date.substring(3, 5) + date.substring(6, 10);
                myDay = Integer.valueOf(date.substring(0, 2));
                myMonth = Integer.valueOf(date.substring(2, 4));
                myYear = Integer.valueOf(date.substring(4, 8));
                adaptMain(date, dayOfWeek);
            }
        });

        reference.child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GroupName.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Calendar calendar = Calendar.getInstance();
        myDay = calendar.get(Calendar.DAY_OF_MONTH);
        myMonth = calendar.get(Calendar.MONTH);
        myYear = calendar.get(Calendar.YEAR);
        changeDate(myDay, myMonth + 1, myYear);
        changeDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
        adaptMain(Date.getText().toString().substring(0, 2) + Date.getText().toString()
                .substring(3, 5) + Date.getText().toString().substring(6, 10), DayOfWeek.getText().toString());
        findViewById(R.id.qwerty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddFileToProfile.class));
            }
        });
    }

    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_DATE) {
            return new DatePickerDialog(MainActivity.this, myCallBack, myYear, myMonth, myDay);
        }
        else
            return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            changeDate(myDay, myMonth + 1, myYear);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, myMonth, dayOfMonth);
            changeDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
            adaptMain(Date.getText().toString().substring(0, 2) + Date.getText().toString()
                    .substring(3, 5) + Date.getText().toString().substring(6, 10), DayOfWeek.getText().toString());
        }
    };

    private void nextday(){
        String date = Date.getText().toString();
        Integer day = Integer.valueOf(date.substring(0, 2));
        Integer month = Integer.valueOf(date.substring(3, 5));
        Integer year = Integer.valueOf(date.substring(6, 10));
        switch (month){
            case 4:
            case 6:
            case 9:
            case 11:{
                if (day < 30) day++;
                else{
                    day = 1;
                    month++;
                }
                break;
            }
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:{
                if (day < 31) day++;
                else{
                    day = 1;
                    month++;
                }
                break;
            }
            case 2: {
                if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)){
                    if (day < 29) day++;
                    else {
                        day = 1;
                        month++;
                    }
                }
                else {
                    if (day < 28) day++;
                    else {
                        day = 1;
                        month++;
                    }
                }
                break;
            }
            case 12: {
                if (day < 31) day++;
                else {
                    day = 1;
                    month = 1;
                    year++;
                }
                break;
            }
        }
        changeDate(day, month, year);
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        int WeekDay = c.get(Calendar.DAY_OF_WEEK);
        changeDayOfWeek(WeekDay);
    }

    public void changeDate(int day, int month, int year){
        String res = "";
        if (day >= 10){
            res += day + ".";
        }
        else res += "0" + day + ".";
        if (month >= 10){
            res += month + ".";
        }
        else res += "0" + month + ".";
        res += year;
        Date.setText(res);
    }

    public void changeDayOfWeek(int WeekDay){
        switch (WeekDay) {
            case 2:{
                DayOfWeek.setText("Понедельник");
                break;
            }
            case 3:{
                DayOfWeek.setText("Вторник");
                break;
            }
            case 4:{
                DayOfWeek.setText("Среда");
                break;
            }
            case 5:{
                DayOfWeek.setText("Четверг");
                break;
            }
            case 6:{
                DayOfWeek.setText("Пятница");
                break;
            }
            case 7:{
                DayOfWeek.setText("Суббота");
                break;
            }
            case 1:{
                DayOfWeek.setText("Воскресенье");
                break;
            }
        }
    }

    private void prevday(){
        String date = Date.getText().toString();
        Integer day = Integer.valueOf(date.substring(0, 2));
        Integer month = Integer.valueOf(date.substring(3, 5));
        Integer year = Integer.valueOf(date.substring(6, 10));
        switch (month){
            case 5:
            case 7:
            case 10:
            case 12:{
                if (day > 1) day--;
                else{
                    day = 30;
                    month--;
                }
                break;
            }
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:{
                if (day > 1) day--;
                else{
                    day = 31;
                    month--;
                }
                break;
            }
            case 3: {
                if ((year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)){
                    if (day > 1) day--;
                    else {
                        day = 29;
                        month--;
                    }
                }
                else {
                    if (day > 1) day--;
                    else {
                        day = 28;
                        month--;
                    }
                }
                break;
            }
            case 1: {
                if (day > 1) day--;
                else {
                    day = 31;
                    month = 12;
                    year--;
                }
                break;
            }
        }
        changeDate(day, month, year);
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        int WeekDay = c.get(Calendar.DAY_OF_WEEK);
        changeDayOfWeek(WeekDay);
    }

    public void adaptMain(String day, String weekDay){
        for (int i = 0; i < 10; i++) {
            lesson[i].setText("");
            task[i].setText("");
            final int x = i;
            reference.child("lessonsSchedule").child(weekDay).child(i + "").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lesson[x].setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            reference.child("task").child(day).child(i + "").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    task[x].setText(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}
