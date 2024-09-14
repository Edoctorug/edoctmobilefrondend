package com.edoctorug.projectstructure.patientchat.composables




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
import androidx.compose.runtime.remember
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Build
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
import com.edoctorug.projectstructure.patientchat.ChatModel
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
import com.edoctorug.projectstructure.patientchat.composables.MainComposables
import com.edoctorug.projectstructure.patientchat.composables.DoctorChatView

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
import com.edoctorug.projectstructure.patientchat.constants.MainParams

class OrdersComposable(private val this_role: String,private val tmp_home_nav_ctrl: NavHostController,private val mutable_orders_map: SnapshotStateMap<String, OrderDetails>, private val loading_fin: MutableState<Boolean>)
{

    

    
    lateinit var show_home_dialog: MutableState<Boolean>
    lateinit var home_dialog_msg: MutableState<String>
    lateinit var main_context: Context
    //lateinit var this_role: String
    lateinit var global_session_id: String
    lateinit var main_hospital_man: Hospitalman
    var this_ws_listener: WSmanCB  = WSmanCB()
    lateinit var home_nav_ctrl: NavHostController

    lateinit var show_order_details: MutableState<Boolean>

    lateinit var coroutineScope: CoroutineScope
    lateinit var show_response_window: MutableState<Boolean>

    lateinit var response_string: String
    lateinit var active_order_details: OrderDetails
    lateinit var active_order_uid: String

    //lateinit var mutable_orders_map: SnapshotStateMap<String, OrderDetails>
    //mutableMapOf<String, String>()

    var is_auth = false
    //lateinit 
    //var doctor_viewmodel = viewModel()
    init{
        home_nav_ctrl = tmp_home_nav_ctrl

        //this_role = xthis_role
        //global_session_id = xsession_id

        main_hospital_man = HospitalManSingleton.getInstance()
            //Hospitalman(hospital_url,hospital_port)
       // this_ws_listener.setActiveRouter(this)

        

        
    }

    @Composable
    fun Home()
    {
        //Login(this_role,global_session_id, main_hospital_man, this_ws_listener).login()
       // doctor_viewmodel = ViewModelProvider(this).get(SharedHospitalModel::class.java)
       /*mutable_orders_map = remember {
        mutableStateMapOf()
    }*/
        coroutineScope =  rememberCoroutineScope()
        show_response_window = remember{ mutableStateOf(false) }
        ///show_order_request = remember{ mutableStateOf(false)}
        main_context = LocalContext.current
        //doctor_viewmodel = viewModel()
        //doctor_viewmodel.printCookies()
        
        //if(is_auth==false)
        //{
        GlobalScope.launch{
            //main_hospital_man.getOrders()
            //NetworkUtils().wslogin(this_role,global_session_id, main_hospital_man, this_ws_listener,main_context)
            //main_hospital_man.authWebSocket(this_ws_listener)
            //NetworkUtils().xwslogin(this_ws_listener,doctor_viewmodel)
            //doctor_viewmodel.wslogin(this_ws_listener)
            //doctor_viewmodel.printCookies()
        }
        OrdersHistory()
        //}

    }

    
    @Composable
    fun OrdersHistory()
    {
        var orders_list = remember{ mutableStateListOf<ChatSummaryModel>() }
        show_order_details = remember{ mutableStateOf(false) }
        val orders_modifier = Modifier
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
        Scaffold(topBar={OrdersHistoryAppBar()})
        {innerPadding->Surface(color= Color.Transparent,modifier = orders_modifier.padding(innerPadding)) 
                       {
                            Box(modifier = main_box_modifier)
                            {
                                Column(verticalArrangement = Arrangement.spacedBy(5.dp))
                                {
                                    /*
                                    showText("Orders History")
                                    MainComposables().OrderSummary("order 1","order_date",{
                                                active_order_details = OrderDetails()
                                                show_order_details.value = true
                                            })

                                    MainComposables().OrderSummary("order 2","order_date",{
                                            active_order_details = OrderDetails()
                                            show_order_details.value = true
                                            })
                                    */
                                    if(mutable_orders_map.size>0) {
                                        for (item in mutable_orders_map.keys.toList()) {
                                            var tmp_order_details = mutable_orders_map[item]
                                            var order_details =
                                                if (tmp_order_details != null) tmp_order_details else null //temporarily store the names in the order

                                            if (order_details != null) {
                                                var order_names = order_details.order_user_names
                                                var order_date = order_details.order_time
                                                MainComposables().OrderSummary(
                                                    order_names,
                                                    order_date,
                                                    {
                                                        active_order_details = order_details
                                                        show_order_details.value = true
                                                    })
                                            } else {
                                                break
                                            }

                                        }
                                    }
                                    else{
                                        if(loading_fin.value == false) {
                                            showText(text = "No Orders Available")
                                        }
                                        else{
                                            showText(text = "Loading, Please wait")
                                        }
                                    }
                                    
                                }
                                
                                if(show_order_details.value==true)
                                {
                                    OrderDetailsDialog()
                                }

                            }
                       }
        }
    }


    /*
    * App bar for the Orders list view
    */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OrdersHistoryAppBar()//, reset: MutableState<Boolean>)
    {
        var coroutineScope = rememberCoroutineScope()
        TopAppBar(//top app bar
            title = {
                Text(
                    "Your Orders", //greeting text
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
                containerColor = Color(1, 2, 75, 255) //container color of the top app bar
            ),
            actions = {
                //delete icon button to clear the order area
                
                /**
                    Side menu icon
                 */
                IconButton(onClick = { /*TODO*/
                    if (this_role.equals("patient")){
                        home_nav_ctrl.navigate(MainParams.PatientViewScreens.DASHBOARD.name)
                    }
                    else{
                        home_nav_ctrl.navigate(DoctorViewScreens.CHAT_HISTORY.name)
                    }
                },
                    colors = IconButtonDefaults.iconButtonColors(
                        //containerColor = Color.Black,
                        disabledContainerColor = Color.White
                    ),

                    )
                {
                    Icon(Icons.Filled.Home, contentDescription = "side menu",tint=Color.White)

                }
                //    }
            }
        )
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
fun BoxScope.OrderDetailsDialog()
{
    var confirm_order_state = remember {
        mutableStateOf(false)
    }
    
    var order_user_name = active_order_details.order_user_names
    var order_uuid = active_order_details.order_uuid
    var order_time =  active_order_details.order_time
    var order_note =  active_order_details.order_note
    Column(//a column layout in the box layout
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(alignment = Alignment.Center)//place this layout at the center of the parent
                .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
    )
    {
        /**
        * Order title
        */
        Text(
            "Order Details",
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
        showText("\tWith: $order_user_name")
        showText("\tAt: $order_time")
        showText("\tNotes:\n $order_note")
        
        Row(modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .align(alignment = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
            )
        {
            
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = Color.Gray
                ),
                 modifier = Modifier
                     .padding(top = 5.dp)
                     .align(alignment = Alignment.CenterVertically),
                onClick = { /*TODO*/

                    show_order_details.value = false

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



            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    disabledContainerColor = Color.Blue
                ),
                modifier = Modifier
                    //.padding(10.dp)
                    .align(alignment = Alignment.CenterVertically),
                    
                onClick = { /*TODO*/
                    
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

}