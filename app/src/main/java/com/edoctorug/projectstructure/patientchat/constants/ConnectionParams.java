package com.edoctorug.projectstructure.patientchat.constants;

import com.edoctorug.projectstructure.patientchat.R;

/**
 * interface containing connection constants used with in the Project
 */
public interface ConnectionParams
{
    public String hospital_url ="https://edoctorugapi-hxgt9y3s.b4a.run";
    //public String hospital_url ="192.168.1.152";
    //"127.0.0.1"; //localhost
    /**
    *Variable containing the port the hospital's server backend is listening on
    */
    public int hospital_port = 0;//8000; //port number the server backend is listening to
    //public int hospital_port = 8000;
}