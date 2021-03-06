package com.abc.mydemoapp.StudentsActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.abc.mydemoapp.CompanyActivity.Company;
import com.abc.mydemoapp.CompanyActivity.Job;
import com.abc.mydemoapp.StudentsActivity.StudentJob;
import com.abc.mydemoapp.Home;
import com.abc.mydemoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showjobdetailsActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databasejob,dbstujob,databasestudent,databasecompany;
    TextView textview1,textview2,textview3,textview4,textview5,textview6;
    String jobid;
    String salary;
    private Button btnapply;
    private String studentemail;
    private String currentstudentid;
    private String compemail;
    private String date;
    private String demoid,rollno;
    private String comname;
    private  String  myjobtitle;
    private int year;



    //String jobid=getIntent().getStringExtra("JOBID");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showjobdetails);
        jobid=getIntent().getStringExtra("JOBID");
        salary= getIntent().getStringExtra("Salary");
        textview1 = findViewById(R.id.textView1);
        studentemail = ((Home)getApplication()).getEmailaddress();
        textview2 = findViewById(R.id.textView2);
        textview3 = findViewById(R.id.textView3);
        textview4 = findViewById(R.id.textView4);
        textview5 = findViewById(R.id.textView5);
        textview6=findViewById(R.id.textView6);
        btnapply = (Button)findViewById(R.id.btnapply);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databasejob = firebaseDatabase.getReference("Job");
        dbstujob = firebaseDatabase.getReference("StudentJob");
        databasestudent = firebaseDatabase.getReference("Student");
        databasecompany=firebaseDatabase.getReference("Company");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


            }
        };

        databasestudent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    demo(dataSnapshot);
                else
                {
                    Log.w("Not initialized...","Demo");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databasejob.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = dbstujob.push().getKey();
                StudentJob sj = new StudentJob(demoid,currentstudentid,studentemail,compemail,date,myjobtitle,id,year,rollno);

                dbstujob.child(id).setValue(sj);
                Intent i = new Intent(showjobdetailsActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });

    }
    private void showData(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            //String key = databasestudent.push().getKey();
            //Log.w(key,"New key...");
            Job job = ds.getValue(Job.class);

            Log.w(job.getId(),"Job id ");
            //String key = student.getId();
            Log.w(""+job.getSalaryRange(), "New Salary...");
            if (job.getId().equals(jobid))
            {
                Log.w(jobid,"Job id now....");
                textview1.setText(job.getJobtitle());
                myjobtitle=job.getJobtitle();
                textview2.setText(job.getJobdescription());
                textview3.setText(job.getJobRequirements());
                compemail = job.getCompemail();
                demoid=job.getId();
                //textview4.setText(salary);
                textview4.setText(Float.toString(job.getSalaryRange()));
                textview5.setText(job.getCname());
                textview6.setText(job.getDate());
                date=job.getDate();
            }

            //Log.w(student.getName(), "Student name...");

            //Do this for roll number also.
        }
    }

    private  void demo(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            Student s = ds.getValue(Student.class);
            if(s.getEmailaddress().equals(studentemail))
            {
                currentstudentid = s.getId();
                year=s.getYear();
                rollno=s.getRollno();

                if(s.isSelected()){
                    btnapply.setEnabled(false);
                }
            }
        }
    }
}

