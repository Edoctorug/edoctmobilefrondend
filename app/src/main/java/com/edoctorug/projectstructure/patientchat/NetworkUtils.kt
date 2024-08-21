package com.edoctorug.projectstructure.patientchat

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import patientdoctorwebsockets.Hospitalman
import patientdoctorwebsockets.WSRouter
import patientdoctorwebsockets.WSmanCB
import com.edoctorug.projectstructure.patientchat.SharedHospitalModel
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.viewModels
class NetworkUtils
{
    suspend fun wslogin(this_speciality: String,session_id: String, zhospital_man: Hospitalman, zwsmancb: WSmanCB,main_context: Context)
    {
        var auth_bool = zhospital_man.authWebSocket(zwsmancb,session_id)
        Toast.makeText(main_context,zhospital_man.getSessionId(),Toast.LENGTH_LONG).show()
        //var auth_bool2 = zhospital_man.findOnlineDoc(this_speciality)

    }

    suspend fun auth(name: String,pass: String,view_model : SharedHospitalModel)
    {
       // view_model.login(name,pass)
        //var auth_bool = zhospital_man.authWebSocket(zwsmancb)
        //Toast.makeText(main_context,zhospital_man.getSessionId(),Toast.LENGTH_LONG).show()
        //var auth_bool2 = zhospital_man.findOnlineDoc(this_speciality)

    }

    suspend fun xwslogin(zwsmancb: WSmanCB,view_model : SharedHospitalModel)
    {
       // view_model.wslogin(zwsmancb)
        //var auth_bool = zhospital_man.authWebSocket(zwsmancb)
        //Toast.makeText(main_context,zhospital_man.getSessionId(),Toast.LENGTH_LONG).show()
        //var auth_bool2 = zhospital_man.findOnlineDoc(this_speciality)

    }

    suspend fun wssendMsg(chat_msg: String, zhospital_man: Hospitalman)
    {
        var auth_bool = zhospital_man.chatWebSocket(chat_msg)
    }


    suspend fun wsPrescibe(med_name: String,med_dose: String, zhospital_man: Hospitalman)
    {
        var auth_bool = zhospital_man.makePrescription(med_name,med_dose)
    }

    suspend fun wsSaveRecord(record_title: String,record_details: String, zhospital_man: Hospitalman)
    {
        var auth_bool = zhospital_man.saveRecord(record_title,record_details)
    }


    suspend fun wsLabTest(test_name: String,test_personel: String, zhospital_man: Hospitalman)
    {
        var auth_bool = zhospital_man.makeLabTest(test_name,test_personel)
    }

    suspend fun wsOrderItem(item_name: String,item_quantity: Int, pharma_id: String ,zhospital_man: Hospitalman)
    {
        var auth_bool = zhospital_man.orderItem(item_name,item_quantity, pharma_id)
    }

    suspend fun wsfindDoc(this_speciality: String, zhospital_man: Hospitalman)//MutableState<Hospitalman>)
    {
        println("finding doctor")
        var auth_bool = zhospital_man.findOnlineDoc(this_speciality)


    }
}