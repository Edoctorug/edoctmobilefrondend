package com.edoctorug.projectstructure.patientchat.views




import android.widget.Toast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.window.DialogProperties
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.sharp.ArrowCircleRight
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.material.icons.sharp.ArrowDropUp
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edoctorug.projectstructure.patientchat.ChatModel
import com.edoctorug.projectstructure.patientchat.MainActivity
import com.edoctorug.projectstructure.patientchat.R
import com.edoctorug.projectstructure.patientchat.WSRouterX
import com.spr.jetpack_loading.components.indicators.PacmanIndicator
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import patientdoctorwebsockets.Hospitalman
import patientdoctorwebsockets.WSRouter
import patientdoctorwebsockets.WSmanCB

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.rememberDatePickerState

import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.TimePicker

import com.edoctorug.projectstructure.patientchat.constants.ConnectionParams
import com.edoctorug.projectstructure.patientchat.constants.MainParams.DoctorViewScreens
import com.edoctorug.projectstructure.patientchat.composables.MainComposables
import com.edoctorug.projectstructure.patientchat.composables.DoctorComposables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModelProvider
import com.edoctorug.projectstructure.patientchat.NetworkUtils
import com.edoctorug.projectstructure.patientchat.SharedHospitalModel
import com.edoctorug.projectstructure.patientchat.composables.AppointmentsComposable
import com.edoctorug.projectstructure.patientchat.composables.PatientAppointmentsComposable
import patientdoctorwebsockets.Models.AppointmentDetails

import java.time.Instant
/**
*Activity Class Representing a Doctor's screen extends ComponentActivity
 */

class AppointmentsView : ComponentActivity() {

    lateinit var doctor_view_model: SharedHospitalModel;
    //var doctor_composable = DoctorComposables()
    lateinit var appointments_composable: PatientAppointmentsComposable
    lateinit var appointments_holder: SnapshotStateMap<String, AppointmentDetails>
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        /**
        *current doctors user_names
        */
        var user_names: String? = intent.getStringExtra("user_names") //name of the current user
       // doctor_view_model = ViewModelProvider(this).get(SharedHospitalModel::class.java)
        
        /**
        *variable temporarily holding the current user's role
        */ 
        var user_role: String? = intent.getStringExtra("user_role") //current user's rule
        
        /**
        *variable temporarily holding the session id used after logging
        */
        var tmp_session_id: String? = intent.getStringExtra("USER_SESSION_ID") //previous session_id used
        
        /**
        *array with a list of specialists supported by the hospital to which the user is currently logged in
        */
        var tmp_specialities: Array<String>? = intent.getStringArrayExtra("SPECIALITIES") //specialities supported by the hospital


        /**
        *check if the tnpspecialities are null or else assign them to the global specialities variable
        */

        var specialities = if (tmp_specialities!=null) tmp_specialities else emptyArray<String>() //put the specialities in another global variable

        println(specialities)







        println("temp global session: "+tmp_session_id)

        
        var global_session_id = if (tmp_session_id!=null) tmp_session_id else "" //put the session id in a global variable

        var this_user_role = if (user_role!=null) user_role else "" //put the current user role in a global variable
        
        
        //(doctor_view_model)

        setContent{
            val this_context = LocalContext.current
            /*
            GlobalScope.launch{
                // NetworkUtils().wslogin(this_role,global_session_id, main_hospital_man, this_ws_listener,main_context)
                //NetworkUtils().xwslogin(this_ws_listener,doctor_view_model)
                //doctor_viewmodel.wslogin(this_ws_listener)
                doctor_view_model.printCookies()
            }
            */
            appointments_holder =  remember {
                mutableStateMapOf()
            }

            appointments_composable = PatientAppointmentsComposable(appointments_holder)
            println("using global session: "+global_session_id) //print the global session id
            if((user_names==null)) //check if the user_names are null
            {
                startActivity(Intent(this_context,MainActivity::class.java))  //start main activity if null
            }

            
            var this_doctor_name = if (user_names!=null) user_names else "" //assign usernames to this_doctor_name

            
           // showText("Test Doctor")
            //MainUI()

            //doctor_composable.Home()
            appointments_composable.Home()


        }


    }


}