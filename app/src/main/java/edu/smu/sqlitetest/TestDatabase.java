package edu.smu.sqlitetest;

/**
 * Created by NicholasSaulnier on 11/19/2014.
 */
public class TestDatabase {
    private int _id;
    private String _patientname;
    private int _patientdob;

    public TestDatabase() {

    }

    public TestDatabase(int id, String patientname, int patientdob) {
        this._id = id;
        this._patientname = patientname;
        this._patientdob = patientdob;
    }

    public TestDatabase(String patientname, int patientdob) {
        this._patientname = patientname;
        this._patientdob = patientdob;
    }

    public void setID(int id) {
        this._id = id;
    }

    public int getID() {
        return this._id;
    }

    public void setPatientName(String patientname) {
        this._patientname = patientname;
    }

    public String getPatientName() {
        return this._patientname;
    }

    public void setPatientDOB(int patientdob) {
        this._patientdob = patientdob;
    }

    public int getPatientDOB() {
        return this._patientdob;
    }

}
