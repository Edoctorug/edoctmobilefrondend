package com.edoctorug.projectstructure.patientchat.composables




import android.widget.Toast

import android.content.Context
import android.app.Activity
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
import androidx.compose.runtime.remember
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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.sharp.ArrowCircleRight
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
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
import androidx.compose.runtime.mutableStateMapOf
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
//import com.edoctorug.projectstructure.patientchat.ChatModel
import com.edoctorug.projectstructure.patientchat.MainActivity
import com.edoctorug.projectstructure.patientchat.R
import com.edoctorug.projectstructure.patientchat.WSRouterX
import com.edoctorug.projectstructure.patientchat.NetworkUtils

import com.spr.jetpack_loading.components.indicators.PacmanIndicator
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

import patientdoctorwebsockets.Models.*
import patientdoctorwebsockets.Hospitalman
import patientdoctorwebsockets.WSRouter
import patientdoctorwebsockets.WSmanCB


import com.edoctorug.projectstructure.patientchat.ChatSummaryModel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.rememberDatePickerState

import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.TimePicker

import com.edoctorug.projectstructure.patientchat.constants.ConnectionParams
import com.edoctorug.projectstructure.patientchat.constants.MainParams.DoctorViewScreens

import com.edoctorug.projectstructure.patientchat.composables.*


import java.util.LinkedHashMap

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.edoctorug.projectstructure.patientchat.HospitalManSingleton
import com.edoctorug.projectstructure.patientchat.SharedHospitalModel
import com.edoctorug.projectstructure.patientchat.models.ChatCase
import java.time.Instant

import kotlin.collections.mutableMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.snapshots.SnapshotStateList

class DoctorComposables(): WSRouter()
{
    var home_loaded = false
    lateinit var this_doctor_names: String
    val hospital_url = ConnectionParams.hospital_url //localhost
    /**
    *Variable containing the port the hospital's server backend is listening on
    */
    var hospital_port = ConnectionParams.hospital_port //port number the server backend is listening to

    lateinit var show_chat_request: MutableState<Boolean>
    lateinit var show_side_menu: MutableState<Boolean>
    lateinit var online_radio_btn_status: MutableState<Boolean>
    lateinit var show_home_dialog: MutableState<Boolean>
    lateinit var home_dialog_msg: MutableState<String>
    lateinit var main_context: Context
    lateinit var this_role: String
    lateinit var global_session_id: String
    lateinit var main_hospital_man: Hospitalman
    var this_ws_listener: WSmanCB  = WSmanCB()
    lateinit var home_nav_ctrl: NavHostController

    lateinit var show_home_page: MutableState<Boolean>
    lateinit var show_appointments_page: MutableState<Boolean>
    lateinit var show_patient_records_page: MutableState<Boolean>
    lateinit var show_prescriptions_page: MutableState<Boolean>

    lateinit var coroutineScope: CoroutineScope
    lateinit var show_response_window: MutableState<Boolean>

    lateinit var response_string: String
    lateinit var active_chat_details: ChatDetails

    lateinit var active_doc: DoctorChatView

    lateinit var mutable_chat_map: SnapshotStateMap<String, DoctorChatView>
    lateinit var mutable_chats_map: SnapshotStateMap<String, MutableList<ChatModel>>
    lateinit var mutable_appointments_map: SnapshotStateMap<String, AppointmentDetails>
    lateinit var mutable_records_map: SnapshotStateMap<String, RecordDetails>
    lateinit var mutable_diagnoses_map: SnapshotStateMap<String, DiagnosisDetails>
    lateinit var mutable_prescriptions_map: SnapshotStateMap<String, PrescriptionDetails>
    lateinit var mutable_orders_map: SnapshotStateMap<String, OrderDetails>



    lateinit var appointments_composable: AppointmentsComposable
    lateinit var records_composable: RecordsComposable
    lateinit var diagnoses_composable: DiagnosesComposable
    lateinit var prescriptions_composable: PrescriptionsComposable
    lateinit var orders_composable: OrdersComposable

    lateinit var doc_chat: DoctorChatView
    //mutableMapOf<String, String>()

    var is_auth = false
    //lateinit 
    //var doctor_viewmodel = viewModel()
    init{
       // home_nav_ctrl = xhome_nav_ctrl

        //this_role = xthis_role
        //global_session_id = xsession_id

        main_hospital_man = HospitalManSingleton.getInstance()
            //Hospitalman(hospital_url,hospital_port)
        this_ws_listener.setActiveRouter(this)

        

        
    }

    class Login(private val this_role: String,private val global_session_id: String,private val main_hospital_man: Hospitalman,private val this_ws_listener: WSmanCB):ViewModel()
    {
            
            fun login()
            {
                viewModelScope.launch{

                           // NetworkUtils().wslogin(this_role,global_session_id, main_hospital_man, this_ws_listener)

                        }
            }
    }

    @Composable
    fun Home(doctor_names: String = "John Doe")
    {
        //Login(this_role,global_session_id, main_hospital_man, this_ws_listener).login()
       // doctor_viewmodel = ViewModelProvider(this).get(SharedHospitalModel::class.java)
       this_doctor_names = doctor_names

       
       if(home_loaded==false)
       {
        mutable_chat_map = remember {mutableStateMapOf()}
        mutable_chats_map = remember {mutableStateMapOf()}
        mutable_appointments_map = remember {mutableStateMapOf()}
        mutable_records_map = remember {mutableStateMapOf()}
        mutable_diagnoses_map = remember {mutableStateMapOf()}
        mutable_prescriptions_map = remember {mutableStateMapOf()}
        mutable_orders_map = remember {mutableStateMapOf()}

        coroutineScope =  rememberCoroutineScope()
        show_response_window = remember{ mutableStateOf(false) }
        show_chat_request = remember{ mutableStateOf(false)}
        main_context = LocalContext.current

        home_loaded = true
       }
        
        //doctor_viewmodel = viewModel()
        //doctor_viewmodel.printCookies()
        if(is_auth==false)
        {
        
        GlobalScope.launch{
            //NetworkUtils().wslogin(this_role,global_session_id, main_hospital_man, this_ws_listener,main_context)
            main_hospital_man.authWebSocket(this_ws_listener)

            main_hospital_man.getChatHistory()
            main_hospital_man.getRecords()
            main_hospital_man.getAppointments()
            main_hospital_man.getPrescriptions()
            //NetworkUtils().xwslogin(this_ws_listener,doctor_viewmodel)
            //doctor_viewmodel.wslogin(this_ws_listener)
            //doctor_viewmodel.printCookies()
        }
        }
       


        
        //main_hospital_man.authWebSocket(this_ws_listener,global_session_id)
        Box()

        {
            
            dashNav()
            if((show_response_window.value == true))
            {
                //showText(text = "Alert Message")
                XChatRequestWindow()
            }

            if(show_chat_request.value == true)
            {
                ChatRequestWindow()
            }
            //showText("hello")
        }
    }

    
    @Composable
    fun dashNav()
    {
        show_side_menu = remember {mutableStateOf(false)}
        var scroll_state = rememberScrollState()
        home_nav_ctrl = rememberNavController()
        online_radio_btn_status = remember{ mutableStateOf(true)}

        show_home_page = remember{ mutableStateOf(true)}
         /**
         * Navigation controller for the main DoctorView DashBoard
         *
         */
        appointments_composable = AppointmentsComposable(home_nav_ctrl,mutable_appointments_map)
        records_composable = RecordsComposable(home_nav_ctrl,mutable_records_map)
        diagnoses_composable = DiagnosesComposable(home_nav_ctrl,mutable_diagnoses_map)
        prescriptions_composable = PrescriptionsComposable(home_nav_ctrl,mutable_prescriptions_map)
        orders_composable = OrdersComposable(home_nav_ctrl,mutable_orders_map)
        NavHost(navController = home_nav_ctrl, startDestination = DoctorViewScreens.CHAT_HISTORY.name)
        {
            /**
            *navigator page for the dashboard
            */
           composable(DoctorViewScreens.CHAT_HISTORY.name)
            {
                
                ChatsHistory()
            }
            
            /**
            *navigator page for the chat page
            */
            composable(DoctorViewScreens.CHAT.name)
            {
                Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )
                    {
                        //ChatUI(chats, scroll_state) //main user interface showing the chat messages
                    }
            }

            composable((DoctorViewScreens.APPOINTMENTS.name))
            {
                appointments_composable.AppointmentsHistory()
            }

            composable((DoctorViewScreens.RECORDS.name))
            {
                records_composable.RecordsHistory()
            }

            composable((DoctorViewScreens.DIAGNOSES.name))
            {
                diagnoses_composable.DiagnosesHistory()
            }

            composable((DoctorViewScreens.PRESCRIPTIONS.name))
            {
                prescriptions_composable.PrescriptionsHistory()
            }

            composable((DoctorViewScreens.ORDERS.name))
            {
                orders_composable.OrdersHistory()
            }

            /**
            *navigator to selected chat page
            */
            composable(DoctorViewScreens.FOCUS.name+"/patient/{chat_id}") {backStackEntry->
                var tmp_active_uuid = backStackEntry.arguments?.getString("chat_id")
                var active_uuid = if (tmp_active_uuid!=null) tmp_active_uuid else "CHAT ERROR"
                var tmp_chat = mutable_chat_map[active_uuid]
                var current_chat = if( tmp_chat !=null) tmp_chat else null //temporarily store the names in the chat
                                        if(current_chat!=null)
                                        {
                                            var doc_names = "Aivan"
                                            
                                            //DoctorChatView(ChatCase(doc_names,chat_details,remember{ mutableStateListOf<ChatModel>()}))

                                            current_chat.MainUI()
                                        }
            }

            
            /**
            *navigator page for the specialities chooser
            */
            composable(DoctorViewScreens.SPECIALITY.name)
            {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    )
                    {
                        //doctorLoader() //composable shown when waiting for a user to pair up with the doctor
                    }
            }

            

            /**navigator page for the chat userinterface and chat loader
            */
            composable(DoctorViewScreens.CHAT_LOADER.name)
            {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth())
                {
                    
                    
                }
            }


        }
    }

    
    @Composable
    fun ChatsHistory()
    {
        var chats_list = remember{ mutableStateListOf<ChatSummaryModel>() }
        val chats_modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
        val main_box_modifier = Modifier.background(Brush.linearGradient( //make a list of colors to mix to create a linear gradient
                                                                 colors = listOf(
                                                                    Color.Black, Color(
                                                                                        2, //transparency
                                                                                        26, //red value
                                                                                        150, //green value
                                                                                        255 //blue value
                                                                                      ),
                                                                    Color.Black //black color
                                                                   ),
                                                                 start = Offset.Zero, //offset where to start the shading(top left corner)
                                                                 end = Offset.Infinite, //mix the colors to fill the entire container
                                                                 tileMode = TileMode.Mirror) //mirror the colors such that they repeat each other if the space is large
                                                                )
        Scaffold(topBar={ChatHistoryAppBar()})
        {innerPadding->Surface(color= Color.Transparent,modifier = chats_modifier.padding(innerPadding)) 
                       {
                            Box(modifier = main_box_modifier)
                            {
                                Column(verticalArrangement = Arrangement.spacedBy(5.dp))
                                {
                                    showText("Chat History")
                                    for  (item in mutable_chat_map.keys.toList())
                                    {
                                        var tmp_chat_details = mutable_chat_map[item]?.getChatDetails()
                                        var chat_details = if( tmp_chat_details !=null) tmp_chat_details else null //temporarily store the names in the chat

                                        if(chat_details!=null)
                                        {
                                            var chat_names = chat_details.full_names
                                            var chat_date = chat_details.chat_time
                                            MainComposables().ChatSummary(chat_names,chat_date,{
                                                home_nav_ctrl.navigate(DoctorViewScreens.FOCUS.name+ "/patient/" + item)
                                            })
                                        }
                                        else
                                        {
                                            break
                                        }
                                        
                                    }
                                }
                                if(show_side_menu.value==true)
                                {
                                    ChatsHistorySideMenu()
                                }

                            }
                       }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChatHistoryAppBar()//, reset: MutableState<Boolean>)
    {
        var coroutineScope = rememberCoroutineScope()
        TopAppBar(//top app bar
            title = {
                Text(
                    "Hello Word", //greeting text
                    style = TextStyle(
                        color=Color.White, //set font color to white
                        fontSize = TextUnit(13f, TextUnitType.Sp), //set size of the font to 13
                        fontWeight = FontWeight.Light, //use light font
                        fontStyle = FontStyle.Normal, //use normal font style
                        fontFamily = FontFamily.Monospace, //use monospace font family
                        letterSpacing = TextUnit(1f,TextUnitType.Sp), //space between letters

                    ), //change text style of the greeting text
                    modifier = Modifier.padding(5.dp) //space between top app bar components
                )
            },
            navigationIcon = {Icon(Icons.Filled.Biotech,contentDescription = "Patient",tint=Color.White,modifier=Modifier.padding(top=5.dp))},
            modifier = Modifier
                .height(50.dp) //height of the top app bar to 35dp
                .shadow(elevation = 10.dp), //elevation of the top app bar from the main app layout
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(11, 65, 156, 255) //container color of the top app bar
            ),
            actions = {
                
                RadioButton(
                    onClick = { /*TODO*/
                        //chats.clear()
                        if(online_radio_btn_status.value == true)
                        {
                            
                            GlobalScope.launch{
                                                 main_hospital_man.findOnlineDoc("consultant")
                            //NetworkUtils().wslogin(this_role,global_session_id, main_hospital_man, this_ws_listener)
                        }
                        }
                        online_radio_btn_status.value = !online_radio_btn_status.value
                    },
                    //enabled = online_radio_btn_status.value,
                    selected = online_radio_btn_status.value,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Green,
                        disabledColor = Color.Red,
                    )
                ) //delete icon button to clear the chat area
                
                /**
                    Side menu icon
                 */
                IconButton(onClick = { /*TODO*/
                    //reset.value= false
                    //
                    show_side_menu.value = !show_side_menu.value
                },
                    colors = IconButtonDefaults.iconButtonColors(
                        //containerColor = Color.Black,
                        disabledContainerColor = Color.White
                    ),

                    )
                {
                    Icon(Icons.Rounded.Menu, contentDescription = "side menu",tint=Color.White)

                }
                //    }
            }
        )
    }


    @Composable
    fun BoxScope.ChatsHistorySideMenu()
    {
        Column(modifier = Modifier
            .background(color = Color(0xff040f41))
            .align(alignment = Alignment.TopEnd))
        {
            /*
            Button(
                    onClick = {
                        //show_date_picker.value = true
                        show_side_menu.value = false
                        ////home_nav_ctrl.navigate(DoctorViewScreens.PRESCRIPTIONS.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                Icon(Icons.Filled.Biotech,contentDescription = "Scan For Patients",tint= Color.White)
                showText("Get Patient")
            }
            */

            Button(
                    onClick = {
                        //show_date_picker.value = true
                        show_side_menu.value = false
                        home_nav_ctrl.navigate(DoctorViewScreens.APPOINTMENTS.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                Icon(Icons.Filled.CalendarMonth,contentDescription = "Scan For Patients",tint= Color.White)
                showText("Appointments")
            }
            /**
            *Order lab test button
            */
            Button(
                    onClick = {
                        //show_date_picker.value = true
                        show_side_menu.value = false
                        home_nav_ctrl.navigate(DoctorViewScreens.RECORDS.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                Icon(Icons.Filled.Info,contentDescription = "Patients Records",tint= Color.White)
                showText("Patient Records")
            }

            /**
            *Save Patients Record
            */
            Button(
                    onClick = {
                        //show_date_picker.value = true
                        show_side_menu.value = false
                        home_nav_ctrl.navigate(DoctorViewScreens.DIAGNOSES.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                Icon(Icons.Filled.List,contentDescription = "Scan For Patients",tint= Color.White)
                showText("Diagnosis Results")
            }

            /**
            *Order Medicine
            */

            Button(
                    onClick = {
                        //show_date_picker.value = true
                        home_nav_ctrl.navigate(DoctorViewScreens.PRESCRIPTIONS.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                Icon(Icons.Filled.Biotech,contentDescription = "Scan For Patients",tint= Color.White)
                showText("Prescriptions")
            }


            /**
            *close chat button
            */
            Button(
                    onClick = {
                                    //home_nav_ctrl.navigate(DoctorViewScreens.DASHBOARD.name)
                              },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                Icon(Icons.Outlined.Build,contentDescription = "Account Settings",tint= Color.White)
                showText("Account Settings")
            }

            Button(
                    onClick = {
                                    //startActivity(Intent(this_context,MainActivity::class.java))
                              },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                Icon(Icons.Filled.ExitToApp,contentDescription = "Exit",tint= Color.White)
                showText("Logout")
            }
        }
    }

    @Composable
    fun showText(text: String)
    {
        Text( //Registration Box Heading
            text,
            modifier = Modifier
                .padding(10.dp)
            //  .align(alignment = Alignment.CenterHorizontally)
            , style = TextStyle(
                fontSize = TextUnit(10f, TextUnitType.Sp),
                fontStyle = FontStyle.Normal,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                letterSpacing = TextUnit(
                    integerResource(id = R.integer.text_spacing_default).toFloat(),
                    TextUnitType.Sp
                )
            )//Label for this layout
        )
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.XChatRequestWindow()
{
    var confirm_chat_state = remember {
        mutableStateOf(false)
    }
    Column(//a column layout in the box layout
        modifier = Modifier
            .align(alignment = Alignment.Center)//place this layout at the center of the parent
            .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
    )
    {
        Text(
            "Chat Request",
            modifier = Modifier
                .padding(5.dp)
                .align(alignment = Alignment.CenterHorizontally), style = TextStyle(
                fontSize = TextUnit(10f, TextUnitType.Sp),
                fontStyle = FontStyle.Normal,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                letterSpacing = TextUnit(2f, TextUnitType.Sp)
            )//Label for this layout
        )

        Row(modifier = Modifier.padding(top = 5.dp),

            )
        {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = Color.Gray
                ),
                /*  modifier = Modifier
                      .padding(top = 5.dp),
                      //.align(alignment = Alignment.CenterHorizontally),*/
                onClick = { /*TODO*/

                    if(confirm_chat_state.value == false)
                    {
                        confirm_chat_state.value = true
                        GlobalScope.launch{

                           // wsSaveRecord(record_name,record_details, main_hospital_man)
                        }
                    }

                })
            {
                if(confirm_chat_state.value == false )
                {
                    Icon(Icons.Filled.Biotech, contentDescription = "")
                    Text(
                        "SAVE", style = TextStyle(
                            fontSize = TextUnit(10f, TextUnitType.Sp),
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = TextUnit(2f, TextUnitType.Sp)
                        )
                    )
                }
                else
                {
                    MainComposables().showLoading()
                }
            }



            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    disabledContainerColor = Color.Blue
                ),
                /*modifier = Modifier
                    //.padding(10.dp)
                    .align(alignment = Alignment.CenterHorizontally),*/
                onClick = { /*TODO*/
                    show_response_window.value = false
                })
            {
                Icon(Icons.Filled.Biotech, contentDescription = "")
                Text(
                    "CANCEL", style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.ChatRequestWindow()
{
    var confirm_chat_state = remember {
        mutableStateOf(false)
    }

    var chat_name = active_chat_details.full_names
    var chat_uuid = active_chat_details.chat_uuid
    Column(//a column layout in the box layout
        modifier = Modifier
            .align(alignment = Alignment.Center)//place this layout at the center of the parent
            .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
    )
    {
        Text(
            "Chat Request",
            modifier = Modifier
                .padding(5.dp)
                .align(alignment = Alignment.CenterHorizontally), style = TextStyle(
                fontSize = TextUnit(10f, TextUnitType.Sp),
                fontStyle = FontStyle.Normal,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                letterSpacing = TextUnit(2f, TextUnitType.Sp)
            )//Label for this layout
        )
        showText("\tChat User: $chat_name")
        //showText("\tChat UUID: $chat_uuid")
        Row(modifier = Modifier.padding(top = 5.dp),

            )
        {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = Color.Gray
                ),
                /*  modifier = Modifier
                      .padding(top = 5.dp),
                      //.align(alignment = Alignment.CenterHorizontally),*/
                onClick = { /*TODO*/

                    if(confirm_chat_state.value == false)
                    {
                        confirm_chat_state.value = true
                        GlobalScope.launch{

                           // wsSaveRecord(record_name,record_details, main_hospital_man)
                        }
                    }

                })
            {
                if(confirm_chat_state.value == false )
                {
                    Icon(Icons.Filled.Biotech, contentDescription = "")
                    Text(
                        "CHAT", style = TextStyle(
                            fontSize = TextUnit(10f, TextUnitType.Sp),
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = TextUnit(2f, TextUnitType.Sp)
                        )
                    )
                }
                else
                {
                    MainComposables().showLoading()
                }
            }



            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    disabledContainerColor = Color.Blue
                ),
                /*modifier = Modifier
                    //.padding(10.dp)
                    .align(alignment = Alignment.CenterHorizontally),*/
                onClick = { /*TODO*/
                    show_chat_request.value = false
                })
            {
                Icon(Icons.Filled.Biotech, contentDescription = "")
                Text(
                    "OKAY", style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )
                )
            }
        }
    }
}


override fun authHandler(response_mdl: ResponseModel )
{
   
    //Toast.makeText(main_context,response_mdl.status_msg,Toast.LENGTH_LONG).show()
    //show_response_window.value = true
    println(response_mdl.status_msg)
    is_auth = true
    
}

override fun matchHandler(response_mdl: ResponseModel)
{
    /**
    * deserialize the  json to a data class object and assign it to chat_details
    */
    var chat_details: ChatDetails = ChatDetails.deJson(response_mdl.meta_data as LinkedHashMap<String, String>)

    /**
    * add the chat_details to the active_chat_details such that the current chat details are used for  communication with server
    */
    active_chat_details = chat_details

    /**
    * add the chat details to the list of available chats on this device
    */
    

    var doc_names = this_doctor_names

    
    (main_context as Activity).runOnUiThread{ 
        if(mutable_chat_map.get(chat_details.chat_uuid)==null)
        {
            mutable_chat_map[chat_details.chat_uuid] = DoctorChatView(home_nav_ctrl,ChatCase(doc_names,chat_details))
        }
    }
    
    /*
    *show the chat request pop up
    */
    show_chat_request.value = true
}

override fun msgHandler(response_mdl: ResponseModel )
{
        super.msgHandler(response_mdl);
        //results_string.setValue(responseModel.status_msg);
       // 
       var str_msg = response_mdl.status_msg;
       var chat_details: ChatDetails = ChatDetails.deJson(response_mdl.meta_data as LinkedHashMap<String, String>)
       // chat_lists
       mutable_chat_map[chat_details.chat_uuid]?.addChat(ChatModel(str_msg,true,false));

}


override fun chatHistoryHandler(response_mdl: ResponseModel )
{
    super.msgHandler(response_mdl);
    var str_code = response_mdl.status_code;
    var str_msg = response_mdl.status_msg;

    if(str_code == 200)
    {
        var latest_response_obj: ChatsHistory = ChatsHistory.deJson(response_mdl.meta_data as LinkedHashMap<String, ChatsList>)
        for(chat_list in latest_response_obj.chat_history)
        {
            var chat_details: ChatDetails = chat_list.summary
        
            var doc_names = this_doctor_names
            (main_context as Activity).runOnUiThread{ 
                                    mutable_chat_map[chat_details.chat_uuid] = DoctorChatView(home_nav_ctrl,ChatCase(doc_names,chat_details))
                                    for(chat_model in chat_list.history)
                                    {
                                         mutable_chat_map[chat_details.chat_uuid]?.addChat(chat_model)
                                     }
            }
       
       
        //System.out.println("chat list: "+summary_name+ " at "+summary_time);
        }
    }
}

override fun recordHandler(response_mdl: ResponseModel)
{
    var record_details: RecordDetails = RecordDetails.deJson(response_mdl.meta_data as LinkedHashMap<String, String>)
    mutable_records_map[record_details.record_uuid] = record_details
    
}

override fun prescriptionHandler(response_mdl: ResponseModel)
{
    var prescription_details: PrescriptionDetails = PrescriptionDetails.deJson(response_mdl.meta_data as LinkedHashMap<String, String>)
    mutable_prescriptions_map[prescription_details.prescription_id] = prescription_details
}

override fun diagnosisHandler(response_mdl: ResponseModel)
{
    var diagnosis_details: DiagnosisDetails = DiagnosisDetails.deJson(response_mdl.meta_data as LinkedHashMap<String, String>)
    mutable_diagnoses_map[diagnosis_details.diagnosis_uuid] = diagnosis_details
}

override fun orderHandler(response_mdl: ResponseModel)
{
    var order_details: OrderDetails = OrderDetails.deJson(response_mdl.meta_data as LinkedHashMap<String, String>)
    mutable_orders_map[order_details.order_uuid] = order_details
    
}

    
}