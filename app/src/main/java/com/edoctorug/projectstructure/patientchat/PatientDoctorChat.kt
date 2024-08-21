package com.edoctorug.projectstructure.patientchat

import kotlinx.coroutines.launch

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ColorStateListDrawable
import android.os.Bundle
import android.util.Log
import android.util.MutableBoolean
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.VectorConverter
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.sharp.ArrowCircleRight
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.material.icons.sharp.ArrowDropUp
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

import com.edoctorug.projectstructure.patientchat.ChatModel
import com.edoctorug.projectstructure.patientchat.constants.ConnectionParams


import com.spr.jetpack_loading.components.indicators.PacmanIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import patientdoctorwebsockets.Hospitalman
import patientdoctorwebsockets.Models.AuthModel
import patientdoctorwebsockets.Models.AuthResponse
import patientdoctorwebsockets.Models.WSModels.WSAuthModel
import patientdoctorwebsockets.WSmanCB
import kotlin.jvm.internal.Ref.BooleanRef

/**
 * @constructor
 * @property this_patient_name name of the patient
 * @property this_doctor_name name of the doctor
 * @property chats list of available chats
 * @property this_context current context of the application
 *
 *
 */
class PatientDoctorChat: ComponentActivity()//apatient_name: String, adoctor_name: String)
{
    lateinit var this_patient_name: String
    lateinit var this_doctor_name: String
    lateinit var this_user_role: String
    lateinit var chats: MutableList<ChatModel>
    lateinit var hospitalman: Hospitalman
    lateinit var specialities: Array<String>
    lateinit var user_matched: MutableState<Boolean>

    val hospital_url = ConnectionParams.hospital_url
    var hospital_port = ConnectionParams.hospital_port
    //lateinit var hospital_man: Hospital

    lateinit var this_context: Context
    lateinit var this_ws_listener: WSmanCB //web socket listener
    var global_session_id: String  = ""
    var user_role: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var user_names: String? = intent.getStringExtra("user_names")
        var user_role: String?  = intent.getStringExtra("user_role")
        var tmp_session_id: String? = intent.getStringExtra("USER_SESSION_ID")
        var tmp_specialities: Array<String>? = intent.getStringArrayExtra("SPECIALITIES")

        specialities = if (tmp_specialities!=null) tmp_specialities else emptyArray<String>()

        println(specialities)

        this_ws_listener = WSmanCB()
        println("temp global session: "+tmp_session_id)
        hospitalman = Hospitalman(hospital_url,hospital_port)

        global_session_id = if (tmp_session_id!=null) tmp_session_id else ""
        GlobalScope.launch {
            wslogin(global_session_id,hospitalman,this_ws_listener)
        }
        this_user_role = if (user_role!=null) user_role else ""

        setContent{

            this_context = LocalContext.current
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


    suspend fun wslogin(session_id: String,zhospital_man: Hospitalman,zwsmancb: WSmanCB)
    {
        var auth_bool = zhospital_man.authWebSocket(zwsmancb,session_id)





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
        chats = remember{ mutableStateListOf<ChatModel>() } //mutable list of current chat objects
        var scroll_state = rememberScrollState() //get a sctate of the scroll




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
                                        if(user_matched.value == true) {
                                            ChatUI(chats, scroll_state) //chat user container
                                        }
                                        else
                                        {
                                            if(this_user_role == "doctor")
                                            {
                                                chatLoader()
                                            }
                                            else
                                            {
                                                patientLoader()
                                            }

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
                    .background(Color(11, 65, 156, 255), shape = RoundedCornerShape(10.dp))
                    .align(alignment = Alignment.Center)
              ) 
        {
            showText(text = "Matching Please Wait ......")
            PacmanIndicator(ballDiameter = 10f)
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
                .background(Color.Black, shape = RoundedCornerShape(10.dp))
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
                                                 },
                                    enabled = (enabled_state.value || last_enabled_state.value),
                                    contentPadding = PaddingValues(1.dp),
                                    colors = ButtonDefaults.buttonColors(
                                                                            containerColor = Color.Blue,
                                                                            disabledContainerColor = Color.DarkGray
                                                                        )
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
                        tint = Color.Blue,
                        modifier = Modifier.size(integerResource(id = R.integer.default_icon_size).dp)
                    )
                }
                IconButton(
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
                        tint = Color.Blue,
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
                    onClick = { /*TODO*/ },
                    //modifier = Modifier.align(alignment = Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Sharp.ArrowCircleRight,
                        contentDescription = "next",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Blue
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
    fun ChatBox(chat_bmodel: ChatModel,chatter_type: Boolean)
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
                    .background(Color.Black, shape = RoundedCornerShape(20.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                    //.width(150.dp)
                    //.shadow(elevation = 5.dp)

            )
            {
                Icon(Icons.Outlined.Face, contentDescription = "", tint = Color.White, modifier = Modifier
                    .size(18.dp)
                    .padding(top = 5.dp, start = 8.dp)
                    )
                Row()
                {


                    Text(
                        chat_msg,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = TextUnit(12f, TextUnitType.Sp),
                            fontStyle = FontStyle.Normal,
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
            )
        {
            Column(
                modifier = Modifier
                    .background(Color.Gray, shape = RoundedCornerShape(20.dp))
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(20.dp))
                    .align(Alignment.TopEnd)
                //.width(150.dp)
                //.shadow(elevation = 5.dp)

            )
            {
                Icon(Icons.Outlined.Person, contentDescription = "", tint = Color.Black, modifier = Modifier
                    .size(19.dp)
                    .padding(top = 5.dp, start = 8.dp)
                    )
                Row()
                {


                    Text(
                        chat_msg,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = TextUnit(13f, TextUnitType.Sp),
                            fontStyle = FontStyle.Normal,
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
                        Color(83, 167, 240, 255),
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
                        cursorColor = Color.Black,
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
                            var dchat_model = ChatModel(message, false)
                            chats.add(chat_model)
                            chats.add(dchat_model)
                            message = ""

                            //}
                        }) {
                        Icon(
                            Icons.Filled.Send,
                            contentDescription = "",
                            tint = Color.Black,
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
                            contentDescription = "",
                            tint = Color.Black,
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
                                                    Icon(Icons.TwoTone.Delete, contentDescription = "",tint=Color.White)//, modifier = Modifier.size(5.dp))

                                    }
                                    IconButton(onClick = { /*TODO*/
                                                         //reset.value= false
                                                         startActivity(Intent(this_context,MainActivity::class.java))

                                                         },
                                              colors = IconButtonDefaults.iconButtonColors(
                                                  //containerColor = Color.Black,
                                                  disabledContainerColor = Color.White
                                              ),

                                              )
                                    {
                                        Icon(Icons.Filled.Cancel, contentDescription = "",tint=Color.Red)

                                    }
                            //    }
                              }
                )
    }

}