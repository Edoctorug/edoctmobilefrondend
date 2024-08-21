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
//import com.edoctorug.projectstructure.patientchat.ChatModel
import com.edoctorug.projectstructure.patientchat.MainActivity
import com.edoctorug.projectstructure.patientchat.R
import com.edoctorug.projectstructure.patientchat.WSRouterX
import com.spr.jetpack_loading.components.indicators.PacmanIndicator
import com.edoctorug.projectstructure.patientchat.HospitalManSingleton

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
import com.edoctorug.projectstructure.patientchat.composables.MarketComposable
import com.edoctorug.projectstructure.patientchat.composables.DoctorComposables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

import patientdoctorwebsockets.Models.ChatDetails
import patientdoctorwebsockets.Models.ChatModel
import com.edoctorug.projectstructure.patientchat.models.ChatCase
import java.time.Instant
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
* The `DoctorChatView` class is the main activity for the Doctor Chat feature.
*
* This activity is responsible for displaying the chat interface and handling user interactions.
* It uses the `ChatCase` class to manage the chat conversation and store the chat history.
*
* @param chat_case The `ChatCase` object that manages the chat conversation and stores the chat history.
*/

class DoctorChatView(private var prev_nav_ctrl: NavHostController,private var chat_case: ChatCase) : ComponentActivity() {

    /**
    *Variable containing the url to the hospital's server backend
    */
    //val hospital_url = "192.168.1.152" //ip address of the hospital server
    val hospital_url = ConnectionParams.hospital_url //localhost
    /**
    *Variable containing the port the hospital's server backend is listening on
    */
    var hospital_port = ConnectionParams.hospital_port //port number the server backend is listening to

    /**
    *enum class representing the different pages in this View
    */
    

    /**
    *variable holding the current patients name
    */
    lateinit var this_patient_name: String

    /**
    *variable holding this doctor's name
    */
    lateinit var this_doctor_name: String

    /**
    *variable holding this current user's role
    */
    lateinit var this_user_role: String

    /**
    *variable holding the current chat objects as ChatModels
    */
    lateinit var chats: SnapshotStateList<ChatModel>

    /**
    *variable holding a mutable state of Hospitalman instance for network connections
    */
    lateinit var hospitalman: MutableState<Hospitalman>

    /**
    *variable that will hold a list of all supported specialities
    */
    lateinit var specialities: Array<String>

    /**
    *variable holding a mutable boolean to show if a user has been matched
    */
    lateinit var user_matched: MutableState<Boolean>

    /**
    *variable holding the Hospital man object for network connections
    */
    lateinit var main_hospital_man: Hospitalman
    // = Hospitalman(hospital_url,hospital_port)

    /**
    *variable holding the websocket call back listener to be used in websocket transactions
    */
    var this_ws_listener: WSmanCB  = WSmanCB() //web socket listener

    /**
    *variable that will store the main navigation host controller for navigating between composables in the doctors view
    */
    
    /**
    *variable that will store the navigation host controller that will be used
    */
    lateinit var main_nav_ctrl: NavHostController //nav host controller to use for the patient view
    
    /**
    *a mutable state variable that will store the results message from the websocket connection
    */
    
    lateinit var result_msg: MutableState<String> //message result from network io
    
    /**
    *MutableState Boolean that will hold the state used to trigger the chat loading composable
    */
    lateinit var chat_loading_fin: MutableState<Boolean> //if loading has finished
    
    /**
    *MutableState Boolean variable used to trigger the date picker
    */
    lateinit var show_date_picker: MutableState<Boolean> //show date picker
    
    /**
    *MutableState Boolean used to trigger the side menu
    */
    lateinit var show_side_menu: MutableState<Boolean> //show side menu

    //lateinit var hospital_man: Hospital
    
    /**
    *variable that stores the context used in this activity
    */
    lateinit var this_context: Context
    
    /**
    *variable that stores the http connection session id that will be used during the websocket auth
    */
    var global_session_id: String  = ""
    
    /**
    *variable holding the speciality of the current doctor
    */
    
    var active_speciality: String = "" //doctor field the user wants to chat with

    /**
    *dialog message controller
    */

    lateinit var home_dialog_ctrl: MutableState<Boolean>
    /**
    *function called to after the activity has been created
    *@param savedInstanceState Bundle for the current activity
    */

    lateinit var doctor_composable: DoctorComposables

    /**
    *chatbox navigation controller
    */
    lateinit var chatbox_nav_ctrl: NavHostController

    //lateinit var prev_nav_ctrl: NavHostController

    lateinit var chat_details: ChatDetails

    init{
        chat_details = chat_case.getChatDetails()

        this_doctor_name = chat_case.getChatOwner()

        chats = chat_case.getChatList()
        main_hospital_man = HospitalManSingleton.getInstance()
        this_user_role = "consultant"
    }


/**
* Adds a new chat model to the list of available chat models.
*
* @param chat_model The chat model to add.
*
* @throws IllegalArgumentException if the chat model is already in the list of available chat models.
*/
    fun addChat(chat_model: ChatModel)
    {
        /**
        * add the chat model object
        */
        chats.add(chat_model)
    }

    fun getChatDetails(): ChatDetails?
    {
        return chat_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        /**
        *current doctors user_names
        */
        var user_names: String? = intent.getStringExtra("user_names") //name of the current user
       
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
        
        specialities = if (tmp_specialities!=null) tmp_specialities else emptyArray<String>() //put the specialities in another global variable

        println(specialities)
        






        println("temp global session: "+tmp_session_id)

        
        global_session_id = if (tmp_session_id!=null) tmp_session_id else "" //put the session id in a global variable

        this_user_role = if (user_role!=null) user_role else "" //put the current user role in a global variable
        
        //doctor_composable = DoctorComposables(this_user_role,global_session_id)

        setContent{
            //hospitalman = remember{ mutableStateOf(main_hospital_man)}
            
            chats = remember{ mutableStateListOf<ChatModel>() } //assign the chat messages mutable list

            chat_loading_fin = remember {mutableStateOf(false)} //assign the chat_loading_fin mutable boolean state to false

            show_date_picker = remember {mutableStateOf(false)} //assign the show_date_picker mutable boolean state to false

            show_side_menu = remember {mutableStateOf(false)} //assign the chat_loading_fin mutable boolean state to false

            home_dialog_ctrl = remember {mutableStateOf(false)} //controlls the home dialog state

            result_msg = remember { mutableStateOf("error msg") } //assign the result_msg state to error_msg string

            this_context = LocalContext.current // get the current context
            

             //assign the nav controller
            user_matched = remember { mutableStateOf(false) } //assign the user_matched state to false

            
            
            
            println("using global session: "+global_session_id) //print the global session id
            if((user_names==null)) //check if the user_names are null
            {
                startActivity(Intent(this_context,MainActivity::class.java))  //start main activity if null
            }

            
            this_doctor_name = if (user_names!=null) user_names else "" //assign usernames to this_doctor_name

            
           // showText("Test Doctor")
            MainUI()

          //  doctor_composable.Home()


        }


    }
    

    /*
    override fun onStart() {
        super.onStart()

        setContent {
            MainUI()
        }
    }

    override fun onRestart() {
        super.onRestart()
        setContent {
            MainUI()
        }

    }

    override fun onResume() {
        super.onResume()
        setContent {
            MainUI()
        }
    }
    */
    
    

    //@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    fun updateChats(chat_value: String)
    {

    }

    /**
    *function to asynchronously login via websockets
    *@param this_speciality specialty the doctor is going to be identified as
    *@param session_id session id identifying the current doctor
    *@param zhospital_man Http client to be used for this connection
    *@param zwsmancb WebSocket call back object that will be used to respond to events from the websocket connection
    */
    suspend fun wslogin(this_speciality: String,session_id: String, zhospital_man: Hospitalman, zwsmancb: WSmanCB)
    {
        var auth_bool = zhospital_man.authWebSocket(zwsmancb,session_id)
        var auth_bool2 = zhospital_man.findOnlineDoc(this_speciality)

    }
    
    /**
    *function that sends a websocket chat message
    *@param chat_msg String representing the chat message
    *@param zhospital_man Hospitalman object to be used for the connection
    */
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
    /**
    *function that prepares the doctor to wait for any available patient
    *@param this_speciality speciality the current doctor will advise to the patients 
    *@param zhospital_man Hospitalman object to use for the connection to the websocket
    */
    suspend fun wsfindDoc(this_speciality: String, zhospital_man: Hospitalman)//MutableState<Hospitalman>)
    {
        Log.d("find doc","finding doctor")
        var auth_bool = zhospital_man.findOnlineDoc(this_speciality)


    }

    /**
    *
    */
    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?)
    {
        super.onActivityResult(requestCode,resultCode,resultData)

        if((requestCode==1900) and (resultCode==RESULT_OK))
        {
            resultData?.data?.also { uri ->
                var media_chat: ChatModel = ChatModel(uri.toString(),false,false)
                chats.add(media_chat)
                // Perform operations on the document using its URI.
            }
        }

    }

    /**
     * Main user composable holding the Doctors View User Interface elements
     */
    @Composable
    fun MainUI()//patient/doctor chat landing page
    {
        //mutable list of current chat objects
            
            //remember{ mutableStateListOf<ChatModel>() } //assign the chat messages mutable list

            chat_loading_fin = remember {mutableStateOf(false)} //assign the chat_loading_fin mutable boolean state to false

            show_date_picker = remember {mutableStateOf(false)} //assign the show_date_picker mutable boolean state to false

            show_side_menu = remember {mutableStateOf(false)} //assign the chat_loading_fin mutable boolean state to false

            home_dialog_ctrl = remember {mutableStateOf(false)} //controlls the home dialog state

            result_msg = remember { mutableStateOf("error msg") } //assign the result_msg state to error_msg string

            this_context = LocalContext.current // get the current context
            

             //assign the nav controller
            user_matched = remember { mutableStateOf(true) } //assign the user_matched state to false
            
        var scroll_state = rememberScrollState() //get a sctate of the scroll
        main_nav_ctrl = rememberNavController()
        chatbox_nav_ctrl = rememberNavController()
        //this_ws_listener.setActiveRouter(WSRouterX(chats,result_msg,chat_loading_fin,home_dialog_ctrl))


        /*
        LaunchedEffect(Unit){
            scroll_state.scrollTo(100)
            // init_bool = !init_bool
        }
        */


        Scaffold (topBar={InAppBar(chats)}){innerPadding->Surface( //parent ui layout holding the patientchat components
                                        color= Color.Transparent, //make the background color transparent
                                        modifier = Modifier
                                        .padding(innerPadding)//user inset data from the appbars
                                        .fillMaxHeight()//cover the entire height asigned to it
                                        .fillMaxWidth(), //cover the available width
                                      ) 
                                        {
                                            Box( //box to hold chat interface
                                                    modifier = Modifier.background
                                                                (Brush.linearGradient( //make a list of colors to mix to create a linear gradient
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
                                                 )
                                                {
            
                                                     //dashNav()
                                                    ChatUI(chats, scroll_state)
                                                 }
                                        }
                 }

    }


    @Composable
    fun BoxScope.ChatBoxNav()
    {
        NavHost(navController = chatbox_nav_ctrl,startDestination = DoctorViewScreens.CHATBOX_MAIN.name)
        {
            /**
            *navigator page for the dashboard
            */
            composable(DoctorViewScreens.CHATBOX_MAIN.name)
            {
                //show_side_menu.value = false
                Box()
                {

                }
            }
            
            composable(DoctorViewScreens.PRESCRIPTIONS.name)
            {
                
                PrescriptionsBox()
            }

            composable(DoctorViewScreens.LABTEST.name)
            {
                
                LabTestBox()
            }

            composable(DoctorViewScreens.MAKEORDER.name)
            {
                
                MarketComposable(chatbox_nav_ctrl).Home()
            }


            composable(DoctorViewScreens.SAVE.name)
            {
                
                RecordsBox()
                
            }

            composable(DoctorViewScreens.DIALOG.name)
            {
                sucessDialogBox()
                
            }
        }
    }           

    @Composable
    fun BoxScope.dashNav()
    {
       
        var scroll_state = rememberScrollState()
        
        
         /**
         * Navigation controller for the main DoctorView DashBoard
         *
         */
        NavHost(navController = main_nav_ctrl, startDestination = DoctorViewScreens.DASHBOARD.name)
        {
            /**
            *navigator page for the dashboard
            */
            composable(DoctorViewScreens.DASHBOARD.name)
            {
                Box(
                        modifier = Modifier
                        .fillMaxHeight() //box should fill the maximum height of the parent
                        .fillMaxWidth() //box should fill the maximum width of the parent
                    )
                   {
                         dashBoard() //dashboard user interface
                    }
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
                        ChatUI(chats, scroll_state) //main user interface showing the chat messages
                    }
            }
            
            
            /**
            *navigator page for the chat userinterface and chat loader
            */
            composable(DoctorViewScreens.CHAT_LOADER.name)
            {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth())
                {
                    if(chat_loading_fin.value==false)
                    {
                        chatLoader()
                    }
                    else
                    {
                        ChatUI(chats, scroll_state)
                    }
                    
                }
            }

        }
    }
    
    
    /**
    *landing page show after logging in successfully
    */
    @Composable
    fun BoxScope.dashBoard()
    {
        /**
        *Boolean uodated whenever a button is enabled
        */
        var global_enabled = remember { mutableStateOf(true) }
        
        /**
        *Mutablr variable that stores the enabled state of the menu previous button
        */
        var last_enabled = remember { mutableStateOf(true) }
        
        /**
        *Grid holding all the landing page's composable buttons
        */
        
        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),modifier = Modifier
            .fillMaxHeight(0.6f)
            .fillMaxWidth(0.8f)
            .align(alignment = Alignment.Center)
            .background(
                Color.Transparent,
                // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                shape = RoundedCornerShape(5.dp)
            ),
            userScrollEnabled = true)
        {

            item {

                var this_enabled = remember{ mutableStateOf(true) }

                        TextButton(
                            onClick = { /*TODO*/
                                        global_enabled.value = !global_enabled.value
                                        this_enabled.value = !this_enabled.value
                                       last_enabled = this_enabled

                                        main_nav_ctrl.navigate(DoctorViewScreens.SPECIALITY.name)
                                      },
                            enabled = (this_enabled.value),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue,
                                disabledContainerColor = Color.DarkGray
                            )
                        )
                        {
                            ConstraintLayout(modifier = Modifier.background(
                                Color.Transparent,

                                // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                            )) {
                                val (icon_ref, text_ref) = createRefs()

                                Icon(   Icons.Filled.Biotech,
                                contentDescription = "Scan For Patients",
                                tint= Color.White,
                                    modifier = Modifier.constrainAs(icon_ref)
                                    {
                                        top.linkTo(parent.top, margin = 0.dp)
                                    }
                                )

                            Text( //Registration Box Heading
                                text = "Get Patient",
                                modifier = Modifier
                                    //.padding(10.dp)
                                    .constrainAs(text_ref)
                                    {
                                        absoluteLeft.linkTo(icon_ref.absoluteLeft, margin = 20.dp)
                                    }
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



                }
            }

            item {

                var this_enabled = remember{ mutableStateOf(true) }

                TextButton(
                    modifier = Modifier.padding(start = (integerResource(R.integer.padd)).dp),
                    onClick = { /*TODO*/
                        //global_enabled = this_enabled
                        this_enabled.value = !this_enabled.value
                        last_enabled = this_enabled
                    },
                    enabled = (this_enabled.value),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        disabledContainerColor = Color.DarkGray
                    )
                )
                {
                    ConstraintLayout(modifier = Modifier.background(
                        Color.Transparent,

                        // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                    )) {
                        val (icon_ref, text_ref) = createRefs()

                        Icon(   Icons.Filled.CalendarMonth,
                            contentDescription = "Appointements",
                            tint= Color.White,
                            modifier = Modifier.constrainAs(icon_ref)
                            {
                                top.linkTo(parent.top, margin = 0.dp)
                            }
                        )

                        Text( //Appointments Heading
                            text = "Appointments",
                            modifier = Modifier
                                //.padding(10.dp)
                                .constrainAs(text_ref)
                                {
                                    absoluteLeft.linkTo(icon_ref.absoluteLeft, margin = 25.dp)
                                }
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



                }
            }
            item {

                var this_enabled = remember{ mutableStateOf(true) }

                TextButton(
                    onClick = { /*TODO*/
                        global_enabled.value = !global_enabled.value
                        this_enabled.value = !this_enabled.value
                        last_enabled = this_enabled
                    },
                    enabled = (this_enabled.value),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        disabledContainerColor = Color.DarkGray
                    )
                )
                {
                    ConstraintLayout(modifier = Modifier.background(
                        Color.Transparent,

                        // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                    )) {
                        val (icon_ref, text_ref) = createRefs()

                        Icon(   Icons.Outlined.Build,
                            contentDescription = "Account Settings",
                            tint= Color.White,
                            modifier = Modifier.constrainAs(icon_ref)
                            {
                                top.linkTo(parent.top, margin = 0.dp)
                            }
                        )

                        Text( //Registration Box Heading
                            text = "Account Settings",
                            modifier = Modifier
                                //.padding(10.dp)
                                .constrainAs(text_ref)
                                {
                                    absoluteLeft.linkTo(icon_ref.absoluteLeft, margin = 20.dp)
                                }
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



                }
            }
            
            item {

                var this_enabled = remember{ mutableStateOf(true) }

                        TextButton(
                            onClick = { /*TODO*/
                                        global_enabled.value = !global_enabled.value
                                        this_enabled.value = !this_enabled.value
                                       last_enabled = this_enabled

                                        main_nav_ctrl.navigate(DoctorViewScreens.SPECIALITY.name)
                                      },
                            enabled = (this_enabled.value),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue,
                                disabledContainerColor = Color.DarkGray
                            )
                        )
                        {
                            ConstraintLayout(modifier = Modifier.background(
                                Color.Transparent,

                                // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                            )) {
                                val (icon_ref, text_ref) = createRefs()

                                Icon(   Icons.Filled.Biotech,
                                contentDescription = "Patient Records",
                                tint= Color.White,
                                    modifier = Modifier.constrainAs(icon_ref)
                                    {
                                        top.linkTo(parent.top, margin = 0.dp)
                                    }
                                )

                            Text( //Registration Box Heading
                                text = "Patient Records",
                                modifier = Modifier
                                    //.padding(10.dp)
                                    .constrainAs(text_ref)
                                    {
                                        absoluteLeft.linkTo(icon_ref.absoluteLeft, margin = 20.dp)
                                    }
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



                }
            }
        
        item {

                var this_enabled = remember{ mutableStateOf(true) }

                        TextButton(
                            onClick = { /*TODO*/
                                        global_enabled.value = !global_enabled.value
                                        this_enabled.value = !this_enabled.value
                                       last_enabled = this_enabled

                                        main_nav_ctrl.navigate(DoctorViewScreens.SPECIALITY.name)
                                      },
                            enabled = (this_enabled.value),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue,
                                disabledContainerColor = Color.DarkGray
                            )
                        )
                        {
                            ConstraintLayout(modifier = Modifier.background(
                                Color.Transparent,

                                // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                            )) {
                                val (icon_ref, text_ref) = createRefs()

                                Icon(   Icons.Filled.Biotech,
                                contentDescription = "Prescriptions",
                                tint= Color.White,
                                    modifier = Modifier.constrainAs(icon_ref)
                                    {
                                        top.linkTo(parent.top, margin = 0.dp)
                                    }
                                )

                            Text( //Registration Box Heading
                                text = "Prescriptions",
                                modifier = Modifier
                                    //.padding(10.dp)
                                    .constrainAs(text_ref)
                                    {
                                        absoluteLeft.linkTo(icon_ref.absoluteLeft, margin = 20.dp)
                                    }
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



                }
            }
            
            
            item {

                var this_enabled = remember{ mutableStateOf(true) }

                        TextButton(
                            onClick = { /*TODO*/
                                        global_enabled.value = !global_enabled.value
                                        this_enabled.value = !this_enabled.value
                                       last_enabled = this_enabled

                                        main_nav_ctrl.navigate(DoctorViewScreens.SPECIALITY.name)
                                      },
                            enabled = (this_enabled.value),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Blue,
                                disabledContainerColor = Color.DarkGray
                            )
                        )
                        {
                            ConstraintLayout(modifier = Modifier.background(
                                Color.Transparent,

                                // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                            )) {
                                val (icon_ref, text_ref) = createRefs()

                                Icon(   Icons.Filled.Biotech,
                                contentDescription = "Diagnosis Results",
                                tint= Color.White,
                                    modifier = Modifier.constrainAs(icon_ref)
                                    {
                                        top.linkTo(parent.top, margin = 0.dp)
                                    }
                                )

                            Text( //Registration Box Heading
                                text = "Diagnosis Results",
                                modifier = Modifier
                                    //.padding(10.dp)
                                    .constrainAs(text_ref)
                                    {
                                        absoluteLeft.linkTo(icon_ref.absoluteLeft, margin = 20.dp)
                                    }
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



                }
            }


            
        }
    }
    
    /**
    *Composable holding The chat loader box displayed after choosing the Get Patient Button
    *Displayed while waiting to be paired up with a patient
    */
    @Composable
    fun BoxScope.chatLoader()
    {
        Column(
            modifier = Modifier
                .background(Color(11, 65, 156, 255), shape = RoundedCornerShape(10.dp))
                .align(alignment = Alignment.Center)
        )
        {
            showText(text = "Matching Please Wait ......")
            PacmanIndicator(ballDiameter = 10f)
            
            showText(text = result_msg.value) 
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

    @OptIn(ExperimentalMaterial3Api::class) //opt in to use the experimental ui components in tje Material3 package
    @Composable
    fun ChatUI(chats: MutableList<ChatModel>, scroll_state: ScrollState) //layout container to hold the chat components
    {

        Box()
        {
            Column(modifier = Modifier // column layout for the chat messages and send message box
                .padding(10.dp) //space between the components
                .fillMaxWidth() //this layout fills all the available width
                .fillMaxHeight() //this layout fills all the available height
                .background(//set background color of the column view as a linear gradient of colors
                    Brush.linearGradient(
                        listOf(
                            Color.Black,
                            //Color(70, 195, 248, 255),
                            Color(40, 83, 168, 255),
                            //Color(173, 213, 241, 255),
                            Color(25, 27, 182, 255),
                            Color(55, 87, 170, 255),
                            // Color(255, 255, 255),
                            //Color(255, 255, 255, 176),
                            Color.Black
                         ),
                        Offset.Zero,
                        Offset.Infinite,
                        TileMode.Repeated
                    ),
                    shape = RoundedCornerShape(
                        20.dp,
                        20.dp,
                        20.dp,
                        20.dp
                    ) //add rounded corners to this column layout
                )
                .padding(10.dp),
            //verticalArrangement = Arrangement.SpaceBetween
            )
            {
                ChatBoxes(chats,scroll_state) //chat messages view

            //Box(modifier = Modifier.align(Alignment.BottomStart))
            //{
                MessageBox(chats,scroll_state) //send message view
            //}

            }

            if(show_side_menu.value == true)
            {
                sideMenu()
            }

            if(show_date_picker.value == true)
            {
                showDatePicker()
            }

            ChatBoxNav()
            
        }
    
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showDatePicker()
    {
        val date_picker_state = rememberDatePickerState()
        val time_picker_state = rememberTimePickerState()
        val is_time_picker = remember{mutableStateOf(false)}

        DatePickerDialog(
            /*
            colors = DatePickerDefaults.colors(
               containerColor = Color.Whi,
               titleContentColor = Color.White,
               headlineContentColor = Color.White,
               weekdayContentColor = Color.White
            ),*/
            onDismissRequest = {
                    
            },
            confirmButton = {TextButton(
                                                    modifier = Modifier.background(Color.Blue),
                                                    onClick = {
                                                        if(is_time_picker.value == false)
                                                        {
                                                            var selected_date = date_picker_state.selectedDateMillis?.let{Instant.ofEpochMilli(it)}
                                                            
                                                            Toast.makeText(this_context,
                                                                        "Selected Date: "+selected_date,
                                                                        Toast.LENGTH_LONG).show()
                                                            is_time_picker.value = true
                                                        }
                                                        else
                                                        {
                                                            var selected_time = time_picker_state.hour
                                                            Toast.makeText(this_context,
                                                                        " Selected Time: "+selected_time,
                                                                        Toast.LENGTH_LONG).show()
                                                        }
                                        })
                                        {
                                            showText("Next")
                                        }
                            },
            dismissButton = {
                                TextButton(
                                        modifier = Modifier.background(Color.Red),
                                        onClick = {
                                                    show_date_picker.value = false
                                        })
                                        {
                                            
                                            showText("Cancel")
                                        }
            })
        {
            if (is_time_picker.value==false) DatePicker(date_picker_state) else TimePicker(time_picker_state)
            
            
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showTimePicker()
    {
        
        val time_picker_state = rememberTimePickerState()
        Column()
        {
            TimePicker(time_picker_state)
        }
    }



    @OptIn()
    @Composable
    fun BoxScope.sideMenu()
    {
        Column(modifier = Modifier.background(color = Color(0xff040f41)).align(alignment = Alignment.TopEnd))
        {
            /**
            *Prescribe medicine button
            */
            Button(
                    onClick = {
                        //show_date_picker.value = true
                        show_side_menu.value = false
                        chatbox_nav_ctrl.navigate(DoctorViewScreens.PRESCRIPTIONS.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                showText("Precribe Medicine")
            }

            /**
            *Order lab test button
            */
            Button(
                    onClick = {
                        //show_date_picker.value = true
                        show_side_menu.value = false
                        chatbox_nav_ctrl.navigate(DoctorViewScreens.LABTEST.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                showText("Order Lab Test")
            }

            /**
            *Save Patients Record
            */
            Button(
                    onClick = {
                        //show_date_picker.value = true
                        show_side_menu.value = false
                        chatbox_nav_ctrl.navigate(DoctorViewScreens.SAVE.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                showText("Save Record")
            }

            /**
            *Order Medicine
            */

            Button(
                    onClick = {
                        //show_date_picker.value = true
                        show_side_menu.value = false
                        chatbox_nav_ctrl.navigate(DoctorViewScreens.MAKEORDER.name)
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                showText("Order Medicine")
            }


            /**
            *close chat button
            */
            Button(
                    onClick = {
                                    show_side_menu.value = false
                                    prev_nav_ctrl.navigate(DoctorViewScreens.CHAT_HISTORY.name)
                              },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                showText("Close Chat")
            }

            Button(
                    onClick = {
                                    show_side_menu.value = false
                                    startActivity(Intent(this_context,MainActivity::class.java))
                              },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                showText("Logout")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChatBoxes(chats: MutableList<ChatModel>,scroll_state: ScrollState) //scrollable list view showing ongoing chat text
    {
        //val chatboxes =  List<String>()
        //var chats = remember{ mutableStateListOf<String>() }
        var init_bool = true


        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .verticalScroll(scroll_state)
            //.padding(10.dp)
        )
        {

            for (chat in chats)//fill the column with previous chat
            {
                init_bool = chat.msg_owner
                ChatBox(chat, init_bool)


            }

            ///chats

            LaunchedEffect(Unit){ //scroll by 100 units
                //scroll_state.scrollBy(800f)
                GlobalScope.launch {
                    scroll_state.scrollBy(100f)
                }
                // init_bool = !init_bool
            }





        }

    }

    @Composable
    fun ColumnScope.ChatBox(chat_bmodel: ChatModel,chatter_type: Boolean)
    {
        if(chatter_type == true)//check if useris patient
        {
            PatientBox(chat_bmodel) //layout holding patient chat message
        }
        else
        {
            
            DoctorBox(chat_bmodel)//layout holding chat message from Doctor
        }
    }

    @Composable
    fun PatientBox(chat_model: ChatModel) //patient chat box template
    {
        var chat_msg: String = chat_model.msg_data
        var chat_type = chat_model.msg_type


        Box(modifier=Modifier.padding(10.dp))
        {
            Column(
                modifier = Modifier
                    .background(Color(0xff070d1e), shape = RoundedCornerShape(10.dp))
                    //.border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                    .padding(top = 10.dp)
                //.width(150.dp)
                //.shadow(elevation = 5.dp)

            )
            {
                Row(modifier = Modifier.padding(top = 1.dp, start = 8.dp,bottom = 3.dp))
                {
                Icon(Icons.Outlined.Face, contentDescription = "", tint = Color.White, modifier = Modifier
                    .size(18.dp)
                )

                showText(chat_details.full_names)
                }
                Row()
                {


                    Text(
                        chat_msg,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = TextUnit(12f, TextUnitType.Sp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = TextUnit(2f, TextUnitType.Sp)
                        ),
                        modifier = Modifier.padding(top = 5.dp, start = 8.dp, bottom = 5.dp)
                    )

                    Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                }
            }
        }
    }


    @Composable
    fun DoctorBox(chat_model: ChatModel) //Message From chat box template
    {
        var chat_msg: String = chat_model.msg_data
        var chat_type = chat_model.msg_type
        Box(modifier= Modifier
            //.padding(2.dp)
            .fillMaxWidth()
            .padding(top = 10.dp)
        )
        {
            Column(
                modifier = Modifier
                    .background(Color(0xff5c088e), shape = RoundedCornerShape(10.dp))
                    //.border(1.dp, Color.Gray, shape = RoundedCornerShape(20.dp))
                    .align(Alignment.TopEnd)
                //.width(150.dp)
                //.shadow(elevation = 5.dp)

            )
            {
                Icon(Icons.Filled.Biotech, contentDescription = "", tint = Color.White, modifier = Modifier
                    .size(19.dp)
                    .padding(top = 3.dp, start = 8.dp)
                )
                Row()
                {


                    Text(
                        chat_msg,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = TextUnit(12f, TextUnitType.Sp),
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = TextUnit(2f, TextUnitType.Sp)
                        ),
                        modifier = Modifier.padding(top = 5.dp, start=8.dp, bottom = 5.dp)
                    )

                    Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MessageBox(chats: MutableList<ChatModel>,scroll_state: ScrollState) {
        lateinit var launched_scroll: Job;
        var amsg: String;
        var message by remember { mutableStateOf("") };
        Row(
            modifier = Modifier
                .padding(5.dp)
                .height(46.dp)
                //.border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp,20.dp,20.dp,20.dp))
                .background(
                    Color(0xff070d1e),
                    //Color(83, 167, 240, 255),
                    shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {

            TextField(
                //label = {Text("Message")},
                placeholder = {Text("Message", style = TextStyle(
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = TextUnit(2f, TextUnitType.Sp)
                ))},
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
                    .background(Color.Transparent),
                maxLines = 50,
                minLines = 10,
                //.padding(10.dp),
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = "send mesage",
                        tint = Color.White
                    )
                },
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    errorTextColor = Color.Red,
                    containerColor = Color.Transparent
                ),

                textStyle = TextStyle(
                    fontSize = TextUnit(10f, TextUnitType.Sp),
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = TextUnit(2f, TextUnitType.Sp)
                )

            )

            ConstraintLayout()
            {
                val (send_refs, add_ref) = createRefs()

                IconButton(
                    modifier = Modifier.constrainAs(send_refs)
                    {
                        top.linkTo(parent.top, margin = 0.dp)
                    },
                    onClick = {
                        var chat_model = ChatModel(message,false,false)
                        //var dchat_model = ChatModel(message, false)
                        chats.add(chat_model)
                        //chats.add(dchat_model)
                        
                        var xmsg = message
                        message = ""
                        
                        GlobalScope.launch{
                            //wssendMsg(xmsg,main_hospital_man)
                            main_hospital_man.chatWebSocket(xmsg,chat_details.chat_uuid)
                        }
                        
                        
                        //}
                    }) {
                    Icon(
                        Icons.Filled.Send,
                        contentDescription = "Send Icon",
                        tint = Color.White,
                        //modifier = Modifier.size(20.dp)
                    )

                }

                IconButton(
                    modifier = Modifier.constrainAs(add_ref)
                    {
                        absoluteRight.linkTo(send_refs.absoluteRight, margin = 35.dp)
                    }, onClick = {
                        var file_picker = Intent(Intent.ACTION_GET_CONTENT)

                        startActivityForResult(file_picker,1900)

                    }) {
                    Icon(
                        Icons.TwoTone.AddCircle,
                        contentDescription = "",
                        tint = Color.White,
                        // modifier = Modifier.size(20.dp)
                    )

                }
            }
            //Spacer(modifier = Modifier.width(10.dp))
            //}
        }

        //  }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun InAppBar(chats: MutableList<ChatModel>)//, reset: MutableState<Boolean>)
    {
        TopAppBar(//top app bar
            title = {
                Text(
                    "Hello\n$this_doctor_name", //greeting text
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
            navigationIcon = {
                if(this_user_role=="doctor")
                {
                    Icon(
                        Icons.Filled.Biotech,
                        contentDescription = "Patient",
                        tint=Color.White,
                        modifier=Modifier.padding(top=5.dp)
                    ) //icon at the beginning of the top app bar
                }
                else
                {
                    Icon(
                        Icons.Filled.AccountCircle,
                        contentDescription = "Patient",
                        tint=Color.White,
                        modifier=Modifier.padding(top=5.dp)
                    ) //icon at the beginning of the top app bar
                }

            },
            modifier = Modifier
                .height(50.dp) //height of the top app bar to 35dp
                .shadow(elevation = 10.dp), //elevation of the top app bar from the main app layout
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(11, 65, 156, 255) //container color of the top app bar
            ),
            actions = {

                IconButton(
                    onClick = { /*TODO*/
                        chats.clear()
                    },

                    colors = IconButtonDefaults.iconButtonColors(
                        //containerColor = Color.Black,
                        disabledContainerColor = Color.Red
                    )
                ) //delete icon button to clear the chat area
                {
                    Icon(Icons.TwoTone.Delete, contentDescription = "clear chat",tint=Color.White)//, modifier = Modifier.size(5.dp))

                }
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

    /**
    *Composable holding the prescription components
    */
    @OptIn(ExperimentalMaterial3Api::class) //opting in to use the experimental ui features since they are most likely to change
    @Composable
    fun PrescriptionsBox()
    {
        //below, am getting my activities current context
        
        if(home_dialog_ctrl.value == false)
        {
        var login = remember { mutableStateOf(false) } //saving the login state to track any changes to it's value
        var medicine_name by remember { mutableStateOf("") } //saving the name state to track any changes to it's value
        var medicine_dose by remember { mutableStateOf("") } //saving the password state to track any changes to it's value
        var prescribe_btn_state = remember { mutableStateOf(false) } //used to check if the visible password button has been toggled it not

        Box(//box composable to hold the login components
            modifier = Modifier
                .fillMaxHeight()//set height to cover the parent device screen height
                .fillMaxWidth()//set width to cover the parent device width
        )
        {
            Column(//a column layout in the box layout
                modifier = Modifier
                    .align(alignment = Alignment.Center)//place this layout at the center of the parent
                    .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
            )
            {
                Text(
                    "Enter Prescription Below",
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
                TextField(
                    value = medicine_name,
                    label = {
                        Text(
                            "Medicine Name",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )//Textfield for the user to write their username
                    },
                    onValueChange = { medicine_name = it },//callback function everytime a value in written in the Textfield
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "medicine_name"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )

                TextField(
                    value = medicine_dose,
                    label = {
                        Text(
                            "Medicine Dosage",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )
                    },
                    onValueChange = { medicine_dose = it },
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "dosage"
                        )
                    },
                    
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = { /*TODO*/
                                if(prescribe_btn_state.value == false)
                                {
                                    prescribe_btn_state.value = true
                                    GlobalScope.launch{

                                        wsPrescibe(medicine_name,medicine_dose, main_hospital_man)
                                    }
                                }
                              })
                {
                    if(prescribe_btn_state.value == false )
                    {
                        Icon(Icons.Filled.Biotech, contentDescription = "")
                        Text(
                            "CONFIRM", style = TextStyle(
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
                    modifier = Modifier
                        //.padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = { /*TODO*/ 
                                    chatbox_nav_ctrl.navigate(DoctorViewScreens.CHATBOX_MAIN.name)
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
        else
        {
            chatbox_nav_ctrl.navigate(DoctorViewScreens.DIALOG.name)
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun sucessDialogBox()
    {
        
        AlertDialog(
            onDismissRequest = {
                                chatbox_nav_ctrl.navigate(DoctorViewScreens.CHATBOX_MAIN.name)
                                home_dialog_ctrl.value = false
                               },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true),
            modifier = Modifier.background(color = Color.Black),

        )
        
       {
            Column()
            {
                Row(modifier = Modifier.background(Color.DarkGray))
                {
                    Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "alert message icon",
                            tint = Color.White
                        )
                    showText(text = "Hospital Message") 
                }
                
                
                showText(text = result_msg.value)
            }
       }
            
            
       }


    /**
    *Composable holding the Order Lab Test components
    */
    @OptIn(ExperimentalMaterial3Api::class) //opting in to use the experimental ui features since they are most likely to change
    @Composable
    fun LabTestBox()
    {
        //below, am getting my activities current context
        
        if(home_dialog_ctrl.value == false)
        {
        //var login = remember { mutableStateOf(false) } //saving the login state to track any changes to it's value
        var test_name by remember { mutableStateOf("") } //saving the name state to track any changes to it's value
        var test_personel by remember { mutableStateOf("") } //saving the password state to track any changes to it's value
        var prescribe_btn_state = remember { mutableStateOf(false) } //used to check if the lab test button has been triggered

        Box(//box composable to hold the lab test components
            modifier = Modifier
                .fillMaxHeight()//set height to cover the parent device screen height
                .fillMaxWidth()//set width to cover the parent device width
        )
        {
            Column(//a column layout in the box layout
                modifier = Modifier
                    .align(alignment = Alignment.Center)//place this layout at the center of the parent
                    .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
            )
            {
                Text(
                    "Lab Test Details",
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
                TextField(
                    value = test_name,
                    label = {
                        Text(
                            "Lab Test Name",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )//Textfield for the user to write their username
                    },
                    onValueChange = { test_name = it },//callback function everytime a value in written in the Textfield
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "lab test name"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )

                TextField(
                    value = test_personel,
                    label = {
                        Text(
                            "Lab Test Personel username",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )
                    },
                    onValueChange = { test_personel = it },
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "test personel"
                        )
                    },
                    
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = { /*TODO*/
                                if(prescribe_btn_state.value == false)
                                {
                                    prescribe_btn_state.value = true
                                    GlobalScope.launch{

                                        wsLabTest(test_name,test_personel, main_hospital_man)
                                    }
                                }
                              })
                {
                    if(prescribe_btn_state.value == false )
                    {
                        Icon(Icons.Filled.Biotech, contentDescription = "")
                        Text(
                            "CONFIRM", style = TextStyle(
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
                    modifier = Modifier
                        //.padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = { /*TODO*/ 
                                    chatbox_nav_ctrl.navigate(DoctorViewScreens.CHATBOX_MAIN.name)
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
        else
        {
            chatbox_nav_ctrl.navigate(DoctorViewScreens.DIALOG.name)
        }

    }

    /**
    *Composable holding the Order Medicine components
    */
    @OptIn(ExperimentalMaterial3Api::class) //opting in to use the experimental ui features since they are most likely to change
    @Composable
    fun OrderMedicineBox()
    {
        //below, am getting my activities current context
        
        if(home_dialog_ctrl.value == false)
        {
        //var login = remember { mutableStateOf(false) } //saving the login state to track any changes to it's value
        var item_name by remember { mutableStateOf("") } //saving the medicine name state to track any changes to it's value
        var item_quantity by remember { mutableStateOf("") } //saving the medicine quantity state to track any changes to it's value
        var pharmacy_id by remember { mutableStateOf("") } //saving the medicine quantity state to track any changes to it's value

        var  order_btn_state = remember { mutableStateOf(false) } //used to check if the order button has been triggered

        Box(//box composable to hold the lab test components
            modifier = Modifier
                .fillMaxHeight()//set height to cover the parent device screen height
                .fillMaxWidth()//set width to cover the parent device width
        )
        {
            Column(//a column layout in the box layout
                modifier = Modifier
                    .align(alignment = Alignment.Center)//place this layout at the center of the parent
                    .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
            )
            {
                Text(
                    "Order Medicine",
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


                TextField(
                    value = item_name,
                    label = {
                        Text(
                            "Medicine Name",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )//Textfield for the user to write their username
                    },
                    onValueChange = { item_name = it },//callback function everytime a value in written in the Textfield
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "ordered medicine name"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )

                TextField(
                    value = item_quantity,
                    label = {
                        Text(
                            "Item Quantity",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                    ),
                    onValueChange = { item_quantity = it },
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "Item Quantity"
                        )
                    },
                    
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )

                TextField(
                    value = pharmacy_id,
                    label = {
                        Text(
                            "Pharmacy ID",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )
                    },
                    
                    onValueChange = { pharmacy_id = it },
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "Item Quantity"
                        )
                    },
                    
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        disabledContainerColor = Color.Gray
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = { /*TODO*/
                                if(order_btn_state.value == false)
                                {
                                    order_btn_state.value = true
                                    GlobalScope.launch{

                                        wsOrderItem(item_name,item_quantity.toInt(), pharmacy_id,main_hospital_man)
                                    }
                                }
                              })
                {
                    if(order_btn_state.value == false )
                    {
                        Icon(Icons.Filled.Biotech, contentDescription = "")
                        Text(
                            "ORDER", style = TextStyle(
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
                    modifier = Modifier
                        //.padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = { /*TODO*/ 
                                    chatbox_nav_ctrl.navigate(DoctorViewScreens.CHATBOX_MAIN.name)
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
        else
        {
            chatbox_nav_ctrl.navigate(DoctorViewScreens.DIALOG.name)
        }

    }

    /**
    *Composable holding the prescription components
    */
    @OptIn(ExperimentalMaterial3Api::class) //opting in to use the experimental ui features since they are most likely to change
    @Composable
    fun RecordsBox()
    {
        //below, am getting my activities current context
        
        if(home_dialog_ctrl.value == false)
        {
        var record_name by remember { mutableStateOf("") } //saving the record title state to track any changes to it's value
        var record_details by remember { mutableStateOf("") } //saving the record details to track any changes to it's value
        var save_record_btn_state = remember { mutableStateOf(false) } //used to check if the visible password button has been toggled it not

        Box(//box composable to hold the login components
            modifier = Modifier
                .fillMaxHeight()//set height to cover the parent device screen height
                .fillMaxWidth()//set width to cover the parent device width
        )
        {
            Column(//a column layout in the box layout
                modifier = Modifier
                    .align(alignment = Alignment.Center)//place this layout at the center of the parent
                    .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
            )
            {
                Text(
                    "Enter Record Details Below",
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
                TextField(
                    value = record_name,
                    label = {
                        Text(
                            "Record Title",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )//Textfield for the user to write their username
                    },
                    onValueChange = { record_name = it },//callback function everytime a value in written in the Textfield
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "medicine_name"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )

                TextField(
                    value = record_details,
                    label = {
                        Row()
                        {
                        Icon(
                            Icons.Filled.Biotech,
                            contentDescription = "dosage"
                        )
                        Text(
                            "Record Details",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )
                        }
                    },
                    onValueChange = { record_details = it },
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight(0.5f)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

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
                                if(save_record_btn_state.value == false)
                                {
                                    save_record_btn_state.value = true
                                    GlobalScope.launch{
                                        
                                        wsSaveRecord(record_name,record_details, main_hospital_man)
                                    }
                                }
                              })
                {
                    if(save_record_btn_state.value == false )
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
                                    chatbox_nav_ctrl.navigate(DoctorViewScreens.CHATBOX_MAIN.name)
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
        else
        {
            chatbox_nav_ctrl.navigate(DoctorViewScreens.DIALOG.name)
        }

    }

}