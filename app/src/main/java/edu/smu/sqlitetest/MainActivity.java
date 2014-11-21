package edu.smu.sqlitetest;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;


import org.w3c.dom.Text;


//public class MainActivity extends Activity {
public class MainActivity extends TabActivity {

    EditText patientName;
    EditText patientDOB;
    EditText searchName;
    TextView outputDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup the tabs
        TabHost th = (TabHost) findViewById(android.R.id.tabhost);
        th.setup();

        TabHost.TabSpec ts = th.newTabSpec("Patient Info");
        ts.setContent(R.id.tab1);
        ts.setIndicator("Patient Info");
        th.addTab(ts);

        TabHost.TabSpec ts2 = th.newTabSpec("Patient Images");
        ts2.setContent(R.id.tab2);
        ts2.setIndicator("Patient Images");
        th.addTab(ts2);

        // initialize editText boxes
        //final EditText patientName = (EditText) findViewById(R.id.patient_name);
        //final EditText patientDOB = (EditText) findViewById(R.id.patient_dob);
        patientName = (EditText) findViewById(R.id.patient_name);
        patientDOB = (EditText) findViewById(R.id.patient_dob);
        searchName = (EditText) findViewById(R.id.search_name);
        outputDOB = (TextView) findViewById(R.id.output_DOB);

        Button addToDatabase = (Button) findViewById(R.id.add_to_database);
        Button searchDatabase = (Button) findViewById(R.id.search_database);
        Button removePatient = (Button) findViewById(R.id.remove_patient);

        //final TextView    patientName = (TextView) findViewById(R.id.patient_name);
        //final TextView patientDOB = (TextView) findViewById(R.id.patient_dob);

        //set the addToDatabase onClickListener
        View.OnClickListener addToDatabaseHandler = new View.OnClickListener() {
            public void onClick(View v) {
                newPatient(v);
            }
        };
        addToDatabase.setOnClickListener(addToDatabaseHandler);

        //set the searchDatabase onClickListener
        View.OnClickListener searchDatabaseHandler = new View.OnClickListener() {
            public void onClick(View v) {
                lookupPatient(v);
            }
        };
        searchDatabase.setOnClickListener(searchDatabaseHandler);

        //set the removePatient onClickListener
        View.OnClickListener removePatientHandler = new View.OnClickListener() {
            public void onClick(View v) {
                removePatient(v);
            }
        };
        removePatient.setOnClickListener(removePatientHandler);
    }

    // add patient
    public void newPatient(View v){
        TestDBHandler dbHandler = new TestDBHandler(this, null, null, 1);

        int dob = Integer.parseInt(patientDOB.getText().toString());
        TestDatabase patient = new TestDatabase(patientName.getText().toString(), dob);

        dbHandler.addPatient(patient);
        patientName.setText("");
        patientDOB.setText("");
    }
    public void lookupPatient (View view) {
        TestDBHandler dbHandler = new TestDBHandler(this, null, null, 1);

        TestDatabase patient =
                dbHandler.findPatient(searchName.getText().toString());

        if (patient != null) {
            //outputDOB.setText(String.valueOf(product.getID()));

            outputDOB.setText(String.valueOf(patient.getPatientDOB()));
        } else {
            outputDOB.setText("No Match Found");
        }
    }

    public void removePatient (View view) {
        TestDBHandler dbHandler = new TestDBHandler(this, null, null, 1);

        boolean result = dbHandler.deletePatient(searchName.getText().toString());

        if (result)
        {
            outputDOB.setText("Record Deleted");
            searchName.setText("");
        }
        else
            outputDOB.setText("No Match Found");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
