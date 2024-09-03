package com.edoctorug.projectstructure.patientchat.views


import java.time.Instant

import android.widget.Toast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import android.view.RoundedCorner
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.rounded.Deck
import androidx.compose.material.icons.sharp.DoNotDisturb
import androidx.compose.material.icons.twotone.ArrowBackIos
import androidx.compose.material.icons.twotone.DeleteOutline
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
import androidx.compose.ui.graphics.RectangleShape
//import com.edoctorug.projectstructure.patientchat.views.ui.theme.PatientChatTheme
import com.edoctorug.projectstructure.patientchat.constants.ConnectionParams
import com.edoctorug.projectstructure.patientchat.constants.MainParams.PatientViewScreens
import patientdoctorwebsockets.Models.ChatDetails

class PatientView : ComponentActivity() {

    val hospital_url = ConnectionParams.hospital_url //localhost
    /**
    *Variable containing the port the hospital's server backend is listening on
    */
    var hospital_port = ConnectionParams.hospital_port //port number the server backend is listening to

    lateinit var chat_details: ChatDetails

    lateinit var this_patient_name: String
    lateinit var this_doctor_name: String
    lateinit var this_user_role: String
    lateinit var chats: MutableList<ChatModel>
    lateinit var hospitalman: MutableState<Hospitalman>
    lateinit var specialities: Array<String>
    lateinit var user_matched: MutableState<Boolean>

    var main_hospital_man: Hospitalman = Hospitalman(hospital_url,hospital_port)
    var this_ws_listener: WSmanCB  = WSmanCB() //web socket listener

    lateinit var main_nav_ctrl: NavHostController //nav host controller to use for the patient view

    lateinit var result_msg: MutableState<String>

    lateinit var chat_loading_fin: MutableState<Boolean>

    lateinit var show_date_picker: MutableState<Boolean>

    lateinit var show_side_menu: MutableState<Boolean>
    lateinit var active_wsrouterx: WSRouterX
    //lateinit var hospital_man: Hospital

    lateinit var this_context: Context
    
    var global_session_id: String  = ""
    var user_role: String? = ""
    var active_speciality: String = "" //doctor field the user wants to chat with

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var user_names: String? = intent.getStringExtra("user_names")
        var user_role: String?  = intent.getStringExtra("user_role")
        var tmp_session_id: String? = intent.getStringExtra("USER_SESSION_ID")
        var tmp_specialities: Array<String>? = intent.getStringArrayExtra("SPECIALITIES")



        specialities = if (tmp_specialities!=null) tmp_specialities else emptyArray<String>()

        println(specialities)
        






        println("temp global session: "+tmp_session_id)


        global_session_id = if (tmp_session_id!=null) tmp_session_id else ""

        this_user_role = if (user_role!=null) user_role else ""
        


        setContent{
            //hospitalman = remember{ mutableStateOf(main_hospital_man)}
            chats = remember{ mutableStateListOf<ChatModel>() }
            result_msg = remember { mutableStateOf("") }
            chat_loading_fin = remember {mutableStateOf(false)}

            //chat_loading_fin = remember {mutableStateOf(false)}

            show_date_picker = remember {mutableStateOf(false)}

            show_side_menu = remember {mutableStateOf(false)}



            this_context = LocalContext.current
            //result_msg = remember { mutableStateOf("") }

             //assign the nav controller
            user_matched = remember { mutableStateOf(false) }

            
            

            println("using global session: "+global_session_id)
            if((user_names==null))
            {
                startActivity(Intent(this_context,MainActivity::class.java))
            }
            this_patient_name = if (user_names!=null) user_names else ""



            MainUI()


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
    init {
        //this_patient_name = apatient_name
        //this_doctor_name = adoctor_name

    }

    //@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    fun updateChats(chat_value: String)
    {

    }


    suspend fun wslogin(this_speciality: String,session_id: String, zhospital_man: Hospitalman, zwsmancb: WSmanCB)
    {
        var auth_bool = zhospital_man.authWebSocket(zwsmancb,session_id)
        var auth_bool2 = zhospital_man.findOnlineDoc(this_speciality)

    }

    suspend fun wssendMsg(chat_msg: String, zhospital_man: Hospitalman)
    {

        var auth_bool = zhospital_man.chatWebSocket(chat_msg,chat_details.chat_uuid)
    }

    suspend fun wsfindDoc(this_speciality: String, zhospital_man: Hospitalman)//MutableState<Hospitalman>)
    {
        Log.d("find doc","finding doctor")
        var auth_bool = zhospital_man.findOnlineDoc(this_speciality)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?)
    {
        super.onActivityResult(requestCode,resultCode,resultData)

        if((requestCode==1900) and (resultCode==RESULT_OK))
        {
            resultData?.data?.also { uri ->
                var media_chat: ChatModel = ChatModel(uri.toString(),true,1998)
                chats.add(media_chat)
                // Perform operations on the document using its URI.
            }
        }

    }

    /**
     * MainUI calls the chat dashboard
     */
    @Composable
    fun MainUI()//patient/doctor chat landing page
    {
        //mutable list of current chat objects
        var scroll_state = rememberScrollState() //get a sctate of the scroll
        main_nav_ctrl = rememberNavController()
        active_wsrouterx = WSRouterX(chats,result_msg,chat_loading_fin)
        this_ws_listener.setActiveRouter(active_wsrouterx)


        /*
        LaunchedEffect(Unit){
            scroll_state.scrollTo(100)
            // init_bool = !init_bool
        }
        */


        Scaffold (
            topBar={InAppBar(chats)},//app bar contents
            //bottomBar = {MessageBox(chats,scroll_state)}

        )
        {innerPadding->Surface( //parent ui layout holding the patientchat components
            color= Color.Transparent, //make the background color transparent
            modifier = Modifier
                .padding(innerPadding)//user inset data from the appbars
                .fillMaxHeight()//cover the entire height asigned to it
                .fillMaxWidth(), //cover the available width

        ) {
            Box( //box to hold chat interface
                modifier = Modifier.background(Brush.linearGradient( //make a list of colors to mix to create a linear gradient
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
                ))
            {
                /*
                if(user_matched.value == true) {
                    //ChatUI(chats, scroll_state) //chat user container
                }
                else
                {
                    if(this_user_role == "doctor")
                    {
                        chatLoader()
                    }
                    else
                    {
                        dashBoard()
                    }

                }

                */
                    dashNav()

            }
        }
        }

    }

    override fun onBackPressed() {
        result_msg.value = ""
        chat_loading_fin.value = false
        Log.i("BUTTON STATUS", "Back button pressed")
        super.onBackPressed()
    }
    @Composable
    fun BoxScope.dashNav()
    {
        /**
         * Navigation controller for the main PatientView DashBoard
         */
        var scroll_state = rememberScrollState()
        NavHost(navController = main_nav_ctrl, startDestination = PatientViewScreens.DASHBOARD.name)
        {
            composable(PatientViewScreens.DASHBOARD.name)
            {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth())
                {
                    dashBoard()
                }
            }

            composable(PatientViewScreens.CHAT.name)
            {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth())
                {
                    ChatUI(chats, scroll_state)
                }
            }

            composable(PatientViewScreens.SPECIALITY.name)
            {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth())
                {
                    patientLoader()
                }
            }

            composable(PatientViewScreens.CHAT_LOADER.name)
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
                        chat_details = ChatDetails.deJson(active_wsrouterx.chatDetails as LinkedHashMap<String, String>)

                        if (chat_details!=null){
                            Log.i("Chat Details","using chat: "+chat_details.chat_uuid)
                        }
                        else{
                            Log.i("Chat_Details","Chat details missing")
                        }

                        ChatUI(chats, scroll_state)
                    }
                    
                }
            }
        }
    }

    /**
     * Patient Dashboard
     */
    @Composable
    fun BoxScope.dashBoard()
    {
        var global_enabled = remember { mutableStateOf(true) }
        var last_enabled = remember { mutableStateOf(true) }
        LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),modifier = Modifier
            .fillMaxHeight(0.6f)
            .fillMaxWidth(0.8f)
            .align(alignment = Alignment.Center)
            .background(
                Color.Transparent,
                // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                shape = RoundedCornerShape(5.dp)
            ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = true)
        {
            /**
             * Visit doctor Button
             */
            item {

                var this_enabled = remember{ mutableStateOf(true) }


                Button(
                            onClick = { /*TODO*/
                                        global_enabled.value = !global_enabled.value
                                        this_enabled.value = !this_enabled.value
                                       last_enabled = this_enabled

                                        main_nav_ctrl.navigate(PatientViewScreens.SPECIALITY.name)
                                      },
                            enabled = (this_enabled.value),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                disabledContainerColor = Color.DarkGray
                            ),

                            shape = RoundedCornerShape(4.dp)
                        )
                        {
                            ConstraintLayout(modifier = Modifier.background(
                                Color.Transparent,

                                // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                            )) {
                                val (icon_ref, text_ref) = createRefs()

                                Icon(   Icons.Filled.Biotech,
                                contentDescription = "Click Here To Visit doctor",
                                tint= Color.White,
                                    modifier = Modifier.constrainAs(icon_ref)
                                    {
                                        top.linkTo(parent.top, margin = 0.dp)
                                    }
                                )

                            Text( //Registration Box Heading
                                text = "Visit Doctor",
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

            /**
             * Make Appointments Button
             */
            item {

                var this_enabled = remember{ mutableStateOf(true) }

                Button(
                    onClick = { /*TODO*/
                        //global_enabled = this_enabled
                        this_enabled.value = !this_enabled.value
                        last_enabled = this_enabled
                    },
                    enabled = (this_enabled.value),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(4.dp)
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

            /**
             * Account Settings Button
             */
            item {

                var this_enabled = remember{ mutableStateOf(true) }

                Button(
                    onClick = { /*TODO*/
                        global_enabled.value = !global_enabled.value
                        this_enabled.value = !this_enabled.value
                        last_enabled = this_enabled
                    },
                    enabled = (this_enabled.value),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(4.dp)
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

            /**
             * Logout Button
             */
            item {

                var this_enabled = remember{ mutableStateOf(true) }


                Button(
                    onClick = { /*TODO*/
                        /*global_enabled.value = !global_enabled.value
                        this_enabled.value = !this_enabled.value
                        last_enabled = this_enabled

                        main_nav_ctrl.navigate(PatientViewScreens.SPECIALITY.name)
                         */
                        startActivity(Intent(this_context,MainActivity::class.java))
                    },
                    enabled = (this_enabled.value),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.DarkGray
                    ),

                    shape = RoundedCornerShape(4.dp)
                )
                {
                    ConstraintLayout(modifier = Modifier.background(
                        Color.Transparent,

                        // (2, //transparency 26, //red value 150, //green value 255 //blue value),
                    )) {
                        val (icon_ref, text_ref) = createRefs()

                        Icon(   Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = "Click Here To Logout",
                            tint= Color.White,
                            modifier = Modifier.constrainAs(icon_ref)
                            {
                                top.linkTo(parent.top, margin = 0.dp)
                            }
                        )

                        Text( //Registration Box Heading
                            text = "Logout User",
                            modifier = Modifier
                                .padding(10.dp)
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

    @Composable
    fun BoxScope.chatLoader()
    {
        Column(
            modifier = Modifier
                .background(Color(1, 2, 75, 255), shape = RoundedCornerShape(10.dp))
                .align(alignment = Alignment.Center)
        )
        {
            
            if ((result_msg.value == "") && (chat_loading_fin.value!=true)){
                showText(text = "Matching Please Wait ......")
                PacmanIndicator(ballDiameter = 10f)

                showText(text = result_msg.value)
            }
            else if((chat_loading_fin.value!=true) && (result_msg.value=="")){
                showText(text = "Ooops, \uD83D\uDE36\u200D\uD83C\uDF2B\uFE0F")
                Icon(imageVector = Icons.Sharp.DoNotDisturb,
                    contentDescription = "no doctor",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp).align(alignment = Alignment.CenterHorizontally))
                showText(text = result_msg.value)
            }
            else{
                showText(text = "MATCHING FINISHED ......")
                Icon(imageVector = Icons.Rounded.Deck,
                    contentDescription = "doctor found",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp).align(alignment = Alignment.CenterHorizontally))
                showText(text = result_msg.value)

            }

            
        }
    }

    @Composable
    fun BoxScope.patientLoader()
    {
        var drop_down_scroll_state = rememberScrollState()
        val coroutineScope = rememberCoroutineScope()
        var grid_state = rememberLazyGridState()
        var last_enabled_state = remember{  mutableStateOf(false) }
        Column(
            modifier = Modifier
                //.background(Color.Black, shape = RoundedCornerShape(10.dp))
                .align(alignment = Alignment.Center)
                .padding(
                    start = integerResource(id = R.integer.padd).dp,
                    end = integerResource(id = R.integer.padd).dp
                )
        )
        {
            showText(text = "....Please Choose Speciality ......")
            LazyVerticalGrid(columns = GridCells.Adaptive(150.dp),state = grid_state,modifier = Modifier.fillMaxHeight(0.6f), userScrollEnabled = true)
            {

                for( speciality in specialities)
                {
                    item {
                        var enabled_state = remember{ mutableStateOf(true) }
                        TextButton(onClick = { /*TODO*/
                            var tmp_enabled_state: Boolean = enabled_state.value
                            println("old enabled state: "+tmp_enabled_state)
                            enabled_state.value = !tmp_enabled_state
                            println("new enabled state: "+enabled_state)
                            last_enabled_state.value = !last_enabled_state.value
                            last_enabled_state = enabled_state

                            active_speciality = speciality


                        },
                            enabled = (enabled_state.value || last_enabled_state.value),
                            contentPadding = PaddingValues(1.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                disabledContainerColor = Color.DarkGray
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            showText(text = speciality)
                        }

                    }
                }

            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            )
            {
                IconButton(
                    modifier = Modifier.background(color=Color.Transparent,shape = RoundedCornerShape(4.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.DarkGray
                    ),
                    onClick = { /*TODO*/
                        coroutineScope.launch{
                            grid_state.animateScrollToItem(5)
                        }
                    },
                    //modifier = Modifier.align(alignment = Alignment.End)

                ) {
                    Icon(
                        imageVector = Icons.Sharp.ArrowDropDown,
                        contentDescription = "pull down",
                        tint = Color.White,
                        modifier = Modifier.size(integerResource(id = R.integer.default_icon_size).dp)
                    )
                }
                IconButton(
                    modifier = Modifier.background(color=Color.Transparent,shape = RoundedCornerShape(4.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.DarkGray
                    ),
                    onClick = { /*TODO*/
                        coroutineScope.launch{
                            grid_state.animateScrollToItem(0)
                        }
                    },
                    //modifier = Modifier.align(alignment = Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Sharp.ArrowDropUp,
                        contentDescription = "pull up",
                        tint = Color.White,
                        modifier = Modifier.size(integerResource(id = R.integer.default_icon_size).dp)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            )
            {
                IconButton(
                    modifier = Modifier.background(color=Color.Transparent,shape = RoundedCornerShape(4.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Black,
                        disabledContainerColor = Color.DarkGray
                    ),
                    onClick = { /*TODO*/
                                main_nav_ctrl.navigate(PatientViewScreens.CHAT_LOADER.name)
                                var login_job = GlobalScope.launch{

                                    wslogin(active_speciality,global_session_id, main_hospital_man, this_ws_listener)
                                }

                               // login_job.join()
                               /*
                                GlobalScope.launch {
                                                        wsfindDoc(active_speciality,main_hospital_man)
                                }
                                */
                              }
                              ,
                    //modifier = Modifier.align(alignment = Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Sharp.ArrowCircleRight,
                        contentDescription = "next",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }
            }
            //PacmanIndicator(ballDiameter = 10f)
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
                            Color(1, 2, 75, 255),
                            //Color(40, 83, 168, 255),
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
        Column(modifier = Modifier
            .background(color = Color(1, 2, 75, 255))
            .align(alignment = Alignment.TopEnd))
        {
            Button(
                    onClick = {
                        show_date_picker.value = true
                    },
                    colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            ))
            {
                showText("Make appointment")
            }

            Button(
                    onClick = {
                                    main_nav_ctrl.navigate(PatientViewScreens.DASHBOARD.name)
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
                init_bool = chat.is_patient
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
        var chat_msg: String = chat_model.message
        var chat_type = chat_model.msg_type


        Box(modifier=Modifier.padding(10.dp))
        {
            Column(
                modifier = Modifier
                    .background(Color(0xff0b2db4), shape = RoundedCornerShape(10.dp))
                    //.border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                    .padding(top = 10.dp)
                //.width(150.dp)
                //.shadow(elevation = 5.dp)

            )
            {
                Icon(Icons.Outlined.Face, contentDescription = "", tint = Color.White, modifier = Modifier
                    .size(18.dp)
                    .padding(top = 1.dp, start = 8.dp, bottom = 3.dp)
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
                        modifier = Modifier.padding(top = 5.dp, start = 8.dp, bottom = 5.dp)
                    )

                    Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                }
            }
        }
    }


    @Composable
    fun DoctorBox(chat_model: ChatModel) //doctor chat box template
    {
        var chat_msg: String = chat_model.message
        var chat_type = chat_model.msg_type
        Box(modifier= Modifier
            //.padding(2.dp)
            .fillMaxWidth()
            .padding(top = 10.dp)
        )
        {
            Column(
                modifier = Modifier
                    .background(Color(0xff070d1e), shape = RoundedCornerShape(10.dp))
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
                        contentDescription = "send mesage"
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
                        var chat_model = ChatModel(message, true)
                        //var dchat_model = ChatModel(message, false)
                        chats.add(chat_model)
                        //chats.add(dchat_model)
                        
                        var xmsg = message
                        message = ""

                        GlobalScope.launch{
                            wssendMsg(xmsg,main_hospital_man)
                        }

                        
                        //}
                    }) {
                    Icon(
                        Icons.Filled.Send,
                        contentDescription = "Send message",
                        tint = Color.White,
                        //modifier = Modifier.size(20.dp)
                    )

                }

                IconButton(
                    modifier = Modifier.constrainAs(add_ref)
                    {
                        absoluteRight.linkTo(send_refs.absoluteRight, margin = 35.dp)
                    }, onClick = {
                        var file_picker = Intent(Intent.ACTION_OPEN_DOCUMENT)

                        startActivityForResult(file_picker,1900)

                    }) {
                    Icon(
                        Icons.TwoTone.AddCircle,
                        contentDescription = "attach attachment",
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
                    "Hello\n$this_patient_name", //greeting text
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
                        Icons.Outlined.Face,
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
                containerColor = Color(1, 2, 75, 255) //container color of the top app bar
            ),
            actions = {

                IconButton(
                    onClick = { /*TODO*/
                        chats.clear()
                        //result_msg.value = ""
                        //chat_loading_fin.value = false
                        //main_nav_ctrl.navigate(PatientViewScreens.DASHBOARD.name)
                    },

                    colors = IconButtonDefaults.iconButtonColors(
                        //containerColor = Color.Black,
                        disabledContainerColor = Color.Red
                    )
                ) //delete icon button to clear the chat area
                {
                    Icon(Icons.TwoTone.DeleteOutline, contentDescription = "Go back",tint=Color.White)//, modifier = Modifier.size(5.dp))

                }
                /**
                    Side menu icon
                 */

                IconButton(onClick = {
                    //reset.value= false
                    //startActivity(Intent(this_context,MainActivity::class.java))
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

}