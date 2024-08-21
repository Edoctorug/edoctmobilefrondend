package com.edoctorug.projectstructure.patientchat

import patientdoctorwebsockets.Hospitalman
import com.edoctorug.projectstructure.patientchat.constants.ConnectionParams
object HospitalManSingleton
{
    private var hospitalman_instance: Hospitalman? = null

    fun getInstance(): Hospitalman{
        if(hospitalman_instance == null)
        {
            hospitalman_instance = Hospitalman(ConnectionParams.hospital_url,ConnectionParams.hospital_port)
        }

        return hospitalman_instance!!
    }
}