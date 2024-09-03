package com.edoctorug.projectstructure.patientchat

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.MutableBoolean
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import android.Manifest
import android.content.SharedPreferences
import android.util.Log

import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.lifecycleScope
import com.edoctorug.projectstructure.patientchat.views.PatientView
import com.edoctorug.projectstructure.patientchat.views.DoctorView
import com.edoctorug.projectstructure.patientchat.views.XDoctorView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.spr.jetpack_loading.components.indicators.BallBeatIndicator
import com.spr.jetpack_loading.components.indicators.BallPulseSyncIndicator
//import com.edoctorug.projectstructure.patientchat.ui.theme.PatientChatTheme
import com.spr.jetpack_loading.components.indicators.lineScaleIndicator.LineScaleIndicator
import com.spr.jetpack_loading.enums.PunchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

import patientdoctorwebsockets.Hospitalman
import patientdoctorwebsockets.Models.AuthModel
import patientdoctorwebsockets.Models.AuthResponse
import patientdoctorwebsockets.Models.RegistrationModel
import java.io.Serializable
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import com.edoctorug.projectstructure.patientchat.SharedHospitalModel
import com.edoctorug.projectstructure.patientchat.constants.ConnectionParams
/**
 * Main Activity that gets called after the app has just been launched extends ComponentActivity
 *
 * @property hospital_url url/ ip address to the hospital backend server
 * @property hospital_port port that the hospital backend server is listening to
 * @property main_context variable storing the current application's Context
 * @property home_dialog_ctrl boolean variable to toggle the dialog box at the home user interface on or off
 * @property home_dialog_msg String variable that stores the message to be shown by the dialog box
 */
class MainActivity : ComponentActivity()
{
    lateinit var main_view_model: SharedHospitalModel;//by viewModels()
    var toggle_btn = false
    lateinit var hospital_man: Hospitalman
    //val hospital_url = "192.168.1.152" //ip address of the hospital server
    val hospital_url = ConnectionParams.hospital_url //localhost
    /**
    *Variable containing the port the hospital's server backend is listening on
    */
    var hospital_port = ConnectionParams.hospital_port //port number the server backend is listening to
    lateinit var main_context: Context
    lateinit var home_dialog_ctrl: MutableState<Boolean>
    var home_dialog_msg = " " //MutableState<String>
    //main_context: Context
    
     /**
     * method called immediately the after the HomeUI has been created
     * @param savedInstanceState variable holding a Bundle containing any data to be shared with this activity
     *
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        //hospital_man = Hospitalman(hospital_url, hospital_port) //initialise Hospitalman with constructor
        hospital_man = HospitalManSingleton.getInstance()
        //}
        //jetpack compose entrypoint
        main_view_model = ViewModelProvider(this).get(SharedHospitalModel::class.java)
        //main_view_model.getLiveAuthResponse().observe(this){auth_data->handleAuth(auth_data)}
        setContent {
            //HomeUI is the composable for the landing page
            HomeUI()
            //RegisterUI()
        }

    }


    @Composable//dummy composable
    fun PatientChatUI() {

    }

    //login function that navigates to the chat activity after successful login
    suspend fun login(name: String,zpass: String, loading_ctrl: MutableState<Boolean>,zhospital_man: Hospitalman) {
        
        //loading_ctrl.value = true
        var aname: String? = name
        var apass: String? = zpass
        var auth_model: AuthModel = AuthModel(aname, apass)
        println(auth_model.toJson())
        Log.i("auth data: ", auth_model.toJson())
        var auth_response: AuthResponse = zhospital_man.auth(auth_model);
        toggle_btn = false
        if(auth_response==null){
            home_dialog_msg = "Connection Error"
            loading_ctrl.value = false
            home_dialog_ctrl.value = true
            return;
        }
        var status_code: Int = auth_response.status_code
        var chat_activity: Intent
        if (status_code == 200)
        {

            var full_names = auth_response.meta_data.names
            var user_role = auth_response.meta_data.user_role
            var user_role_group = auth_response.meta_data.role_group
            var specialities: Array<String> = auth_response.meta_data.extra_data
            var shared_prefs = main_context.getSharedPreferences("user_connection",Context.MODE_PRIVATE)
            var this_session_id = hospital_man.getSessionId()
            shared_prefs.edit().putString("USER_SESSION_ID",this_session_id)
            System.out.println("\nSESSION ID:"+this_session_id)
            var package_name = main_context.applicationInfo.packageName
            //val chat_activity: Intent = Intent(main_context, PatientDoctorChat::class.java) //intent to the Chat user interface
            /*
            *checks the user role group the user belongs to. Can be either a patient or medical staff
            */
            if((user_role_group=="medic"))
            {
                chat_activity = Intent(main_context, DoctorView::class.java) //intent to the Chat user interface
            }
            else {
                chat_activity = Intent(main_context, PatientView::class.java) //intent to the Chat user interface
            }
            chat_activity.putExtra("user_names",full_names) //add user's name extra to the chat intent for it to be displayed with the chat data
            chat_activity.putExtra("user_role",user_role)
            chat_activity.putExtra("USER_SESSION_ID",this_session_id)
            chat_activity.putExtra("SPECIALITIES",specialities)
            //chat_activity.putExtra("user_cookies",hospital_man.getCookieJar())

            main_context.startActivity(chat_activity)//starts the activity
            finish()

        }
        else
        {
            home_dialog_msg = auth_response.status_msg
            loading_ctrl.value = false
            home_dialog_ctrl.value = true

        }



    }



    //login function that navigates to the chat activity after successful login
    fun handleAuth(auth_response: AuthResponse) {
        var status_code: Int = auth_response.status_code
        
        if (status_code == 200)
        {

            var full_names = auth_response.meta_data.names
            var user_role = auth_response.meta_data.user_role
            var user_role_group = auth_response.meta_data.role_group
            var specialities: Array<String> = auth_response.meta_data.extra_data
            var shared_prefs = main_context.getSharedPreferences("user_connection",Context.MODE_PRIVATE)
            var this_session_id = "hospital_man.getSessionId()"
            shared_prefs.edit().putString("USER_SESSION_ID",this_session_id)
            System.out.println("\nSESSION ID:"+this_session_id)
            var package_name = main_context.applicationInfo.packageName
            //val chat_activity: Intent = Intent(main_context, PatientDoctorChat::class.java) //intent to the Chat user interface
            lateinit var chat_activity: Intent
            if((user_role_group=="medic"))
            {
                chat_activity = Intent(main_context, DoctorView::class.java) //intent to the Chat user interface
            }
            else {
                chat_activity = Intent(main_context, PatientView::class.java) //intent to the Chat user interface
            }
            chat_activity.putExtra("user_names",full_names) //add user's name extra to the chat intent for it to be displayed with the chat data
            chat_activity.putExtra("user_role",user_role)
            chat_activity.putExtra("USER_SESSION_ID",this_session_id)
            chat_activity.putExtra("SPECIALITIES",specialities)
            //chat_activity.putExtra("user_cookies",hospital_man.getCookieJar())
            main_context.startActivity(chat_activity)//starts the activity
            //finish()

        }
        else
        {
            home_dialog_msg = auth_response.status_msg
            //loading_ctrl.value = false
            home_dialog_ctrl.value = true

        }



    }


    //register function
    suspend fun register(registrationModel: RegistrationModel, loading_ctrl: MutableState<Boolean>,zhospital_man: Hospitalman) {

        //loading_ctrl.value = true
        //var aname: String? =
        //var apass: String? = zpass
        //var auth_model: AuthModel = AuthModel(aname, apass)
        println(registrationModel.toJson())
        Log.i("auth data: ", registrationModel.toJson())
        var auth_response: AuthResponse = zhospital_man.register(registrationModel);
        var status_code: Int = auth_response.status_code
        var chat_activity: Intent
        toggle_btn = false
        if (status_code == 200)
        {
            var full_names = auth_response.meta_data.names
            var user_role = auth_response.meta_data.user_role
            var role_group = auth_response.meta_data.role_group

            if((role_group=="medic"))
            {
                chat_activity = Intent(main_context, DoctorView::class.java) //intent to the Chat user interface
            }
            else {
                chat_activity = Intent(main_context, PatientView::class.java) //intent to the Chat user interface
            }
            //val chat_activity: Intent = Intent(main_context, PatientDoctorChat::class.java) //intent to the Chat user interface
            chat_activity.putExtra("user_names",full_names) //add user's name extra to the chat intent for it to be displayed with the chat data
            chat_activity.putExtra("user_role",user_role)
            //chat_activity.putExtra()
            //chat_activity.putExtra("hospital_man","hello")




            main_context.startActivity(chat_activity)//starts the activity

        }
        else
        {
            home_dialog_msg = auth_response.status_msg
            loading_ctrl.value = false
            home_dialog_ctrl.value = true

        }



    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showDialog(text: String,dialog_type: Int)
    {
        /*
        AlertDialog(
                        onDismissRequest = { },
                        confirmButton = {  Button(onClick = { }) {
                            showText(text = "Ok")
                        }},
                        text = {showText(text = "User or Password Incorrect")},
                        containerColor = Color.Blue,


                    )
        */



        AlertDialog(
            onDismissRequest = { /*TODO*/ home_dialog_ctrl.value = false},
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true),
            modifier = Modifier.background(color = Color.Blue)
        )
        {
            showText(text = text)
            //sshowText(text = "User name or password Incorrect")
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

    @OptIn(ExperimentalPermissionsApi::class) //optin to allow using experiment google accompanist features
    //@OptIn(ExperimentalMaterial3Api::class) //opting in to use the experimental ui features since they are most likely to change
    @Composable
    fun HomeUI()
    {
        var register = remember { mutableStateOf(true) } /**display registration page*/
        home_dialog_ctrl = remember { mutableStateOf(false) } /** show dialog */

    val internet_permission_state = rememberPermissionState(permission = Manifest.permission.INTERNET)
        val files_access_state = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

        val permission_launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission())
        {
                isGranted->
            if(isGranted)
            {

            }
            else
            {

            }
        }

        LaunchedEffect(internet_permission_state)
        {
            if(internet_permission_state.status.isGranted)
            {

            }
            else
            {
                //permission_launcher.launch(Manifest.permission.INTERNET)
                permission_launcher.launch(Manifest.permission.INTERNET)
            }
        }
        if(home_dialog_ctrl.value == true)
        {
            showDialog(text = home_dialog_msg, dialog_type = 69)
        }
        if(register.value ==true)
        {
            RegisterUI(register)
        }
        else
        {
            LoginUI(register)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class) //opting in to use the experimental ui features since they are most likely to change
    @Composable
    fun LoginUI(change_page: MutableState<Boolean>)
    {
        //below, am getting my activities current context
        main_context = LocalContext.current
        var login = remember { mutableStateOf(false) } //saving the login state to track any changes to it's value
        var name by remember { mutableStateOf("") } //saving the name state to track any changes to it's value
        var pass by remember { mutableStateOf("") } //saving the password state to track any changes to it's value
        var is_pass_visible = remember { mutableStateOf(false) } //used to check if the visible password button has been toggled it not

        Box(//box composable to hold the login components
            modifier = Modifier
                .fillMaxHeight()//set height to cover the parent device screen height
                .fillMaxWidth()//set width to cover the parent device width
                .background(//modify the background
                    Brush.linearGradient(
                        colors = listOf(
                            Color.Black,
                            Color(2, 26, 150, 255),
                            Color.Black
                        ), //change the background to a gradient made of 3 colors
                        start = Offset.Zero, //the gradient will start at the left,top corner
                        end = Offset.Infinite, //gradient will fill the entire designated space
                        tileMode = TileMode.Mirror //repeat the color ranges from last to first if the size of the container is small
                    )
                )
        )
        {
            Column(//a column layout in the box layout
                modifier = Modifier
                    .align(alignment = Alignment.Center)//place this layout at the center of the parent
                    .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
            )
            {
                Image(
                    ImageBitmap.imageResource(R.drawable.edoctor_logo_white),
                    contentDescription = "Patient",
                    //tint=Color.White,
                    modifier= Modifier
                        .padding(top = 5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    filterQuality = FilterQuality.High
                )
                Text(
                    "Please Login",
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
                    value = name,
                    label = {
                        Text(
                            "your username",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )//Textfield for the user to write their username
                    },
                    onValueChange = { name = it },//callback function everytime a value in written in the Textfield
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.AccountCircle,
                            contentDescription = "send mesage"
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
                    value = pass,
                    label = {
                        Text(
                            "your password",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )
                    },
                    visualTransformation = if (is_pass_visible.value == false) PasswordVisualTransformation() else VisualTransformation.None,
                    onValueChange = { pass = it },
                    modifier = Modifier
                        .width(230.dp)
                        .padding(5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = "send mesage"
                        )
                    },
                    trailingIcon = {
                        var icon =
                            if (is_pass_visible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = {
                            is_pass_visible.value = !is_pass_visible.value
                        })
                        {
                            Icon(icon, contentDescription = "password_status")
                        }
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

                loginButton(name,pass)

                Text( //Registration Box Heading
                    "Don't Have an account???\n Please Register below...",
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                    , style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(integerResource(id = R.integer.text_spacing_default).toFloat(),TextUnitType.Sp)
                    )//Label for this layout
                )

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        disabledContainerColor = Color.Blue
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    onClick = { /*TODO*/change_page.value = true })
                {
                    Icon(Icons.Outlined.Person, contentDescription = "")
                    Text(
                        "Register", style = TextStyle(
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


    /**
     * loginButton Component
     */
    @Composable
    fun ColumnScope.loginButton(name: String,password: String) //login button component
    {
        var is_clicked = remember { mutableStateOf(false) }

        if(is_clicked.value == false)
        {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(11, 65, 156, 255),
                    disabledContainerColor = Color.Blue
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),

                onClick =  { /*TODO*/
                    is_clicked.value = true
                    GlobalScope.launch(Dispatchers.IO)
                    {
                        login(name,password, is_clicked,hospital_man)
                    }
                    /*
                    GlobalScope.launch {
                        //main_view_model.login(name,password)
                        //NetworkUtils().login(name,password,main_view_model)
                        //main_view_model.printCookies()

                    }

                     */



                })
            {
                Icon(Icons.Outlined.Person, contentDescription = "")
                Text(
                    "Login", style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )
                )
            }
        }
        else
        {

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(11, 65, 156, 255),
                    disabledContainerColor = Color.Blue
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally),

                onClick = { /*TODO*/
                    // login(name)
                    is_clicked.value = true
                })
            {
                // Icon(Icons.Outlined.Person, contentDescription = "")
                showLoading()
            }
        }
    }

    @Composable
    fun showLoading()
    {
        BallBeatIndicator(ballDiameter = 8f,
            ballCount = 10,
            spaceBetweenBalls = integerResource(id = R.integer.text_spacing_default).toFloat()
        )
    }


    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        // PatientChatTheme {
        Greeting("Android")
        //}
    }

    @OptIn(ExperimentalMaterial3Api::class) //opting in to use the experimental ui features since they are most likely to change
    @Composable
    fun RegisterUI(change_page: MutableState<Boolean>)
    {
        //below, am getting my activities current context
        main_context = LocalContext.current
        var active_speciality: String = ""
        var login = remember { mutableStateOf(false) } //saving the login state to track any changes to it's value
        var name by remember { mutableStateOf("") } //saving the name state to track any changes to it's value
        var first_name by remember { mutableStateOf("") } //get first_name
        var last_name by remember { mutableStateOf("") } //get last_name
        var pass by remember { mutableStateOf("") } //saving the password state to track any changes to it's value
        var is_pass_visible = remember { mutableStateOf(false) } //used to check if the visible password button has been toggled it not
        var grid_state = rememberLazyGridState() //state of the speciality picker
        var specialities = arrayOf("consultant","Dentist","Physician","Dermatologist","Surgeon","Counselor","Psychiatrist","Pediatricians","Obstetrician","Nurse","Orthopedologist","Optician","Therapist","Pharmacist","Midwife","Nutritionist","Gynecologist","Urologist")
        var registration_model by remember { mutableStateOf(RegistrationModel())}
        var last_enabled_state = remember{  mutableStateOf(false) } //state of individual specialities buttons
        var show_speciality_dialog = remember{  mutableStateOf(false) } //state of whether to show specility or not
        Box(//box composable to Registration activity
            modifier = Modifier
                .fillMaxHeight()//set height to cover the parent device screen height
                .fillMaxWidth()//set width to cover the parent device width
                .background(//modify the background
                    Brush.linearGradient(
                        colors = listOf(
                            Color.Black,
                            Color(2, 26, 150, 255),
                            Color.Black
                        ), //change the background to a gradient made of 3 colors
                        start = Offset.Zero, //the gradient will start at the left,top corner
                        end = Offset.Infinite, //gradient will fill the entire designated space
                        tileMode = TileMode.Mirror //repeat the color ranges from last to first if the size of the container is small
                    )
                )
        )
        {
            Column(//a column layout in the box layout to align the registration fields in a column
                modifier = Modifier
                    .align(alignment = Alignment.Center)//place this layout at the center of the parent

                    .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
            )
            {
                Image(
                    ImageBitmap.imageResource(R.drawable.edoctor_logo_white),
                    contentDescription = "Patient",
                    //tint=Color.White,
                    modifier= Modifier
                        .padding(top = 5.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    filterQuality = FilterQuality.High
                )
                Text( //Registration Box Heading
                    ".....Please Register Here.....",
                    modifier = Modifier.padding(10.dp), style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )//Label for this layout
                )

                TextField( //Text input field for the firstname
                    value = first_name, //value from first_name
                    label = { //start of label
                        Text(
                            "first name",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )//Textfield for the user to write their username
                    },//end of label
                    onValueChange = {
                        first_name = it
                        registration_model.first_name = first_name
                    },//callback function to assign input to first_name everytime a value in written in the Textfield
                    modifier = Modifier
                        .width(230.dp) //set width of first_name
                        .padding(
                            start = (integerResource(id = R.integer.padd)).dp,
                            bottom = (integerResource(id = R.integer.padd)).dp
                        )
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.AccountCircle,
                            contentDescription = "send mesage"
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

                )//end of the first name input field


                TextField( //Text input field for the last name
                    value = last_name, //value from last_name
                    label = { //start of label
                        Text(
                            "last name",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )//Textfield for the user to write their username
                    },//end of label
                    onValueChange = {
                        last_name = it
                        registration_model.last_name = last_name
                    },//callback function to assign input to first_name everytime a value in written in the Textfield
                    modifier = Modifier
                        .width(230.dp) //set width of first_name
                        .padding(
                            start = (integerResource(id = R.integer.padd)).dp,
                            bottom = (integerResource(id = R.integer.padd)).dp
                        )
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.AccountCircle,
                            contentDescription = "send mesage"
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

                )//end of the last name input field



                TextField( //Text input field for the username
                    value = name,
                    label = {
                        Text(
                            "your username",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )//Textfield for the user to write their username
                    },
                    onValueChange = {
                        name = it
                        registration_model.user_name = name
                    },//callback function everytime a value in written in the Textfield
                    modifier = Modifier
                        .width(230.dp)
                        .padding(start = 5.dp, bottom = 5.dp)

                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.AccountCircle,
                            contentDescription = "send mesage"
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
                    value = pass,
                    label = {
                        Text(
                            "your password",
                            style = TextStyle(
                                fontSize = TextUnit(12f, TextUnitType.Sp),
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                letterSpacing = TextUnit(2f, TextUnitType.Sp)
                            )
                        )
                    },
                    visualTransformation = if (is_pass_visible.value == false) PasswordVisualTransformation() else VisualTransformation.None,
                    onValueChange = {
                        pass = it
                        registration_model.user_password = pass
                        println("password value: "+pass)
                    },
                    modifier = Modifier
                        .width(230.dp)
                        .padding(
                            start = (integerResource(id = R.integer.padd)).dp //left padding value from resources xml
                            ,
                            bottom = (integerResource(id = R.integer.padd)).dp //bottom padding value from resoures xml
                        )
                        .align(alignment = Alignment.CenterHorizontally),
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = "send mesage"
                        )
                    },
                    trailingIcon = {
                        var icon =
                            if (is_pass_visible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = {
                            is_pass_visible.value = !is_pass_visible.value
                        })
                        {
                            Icon(icon, contentDescription = "password_status")
                        }
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

                Text( //Registration Box Heading
                    "Register As:",
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                    , style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(integerResource(id = R.integer.text_spacing_default).toFloat(),TextUnitType.Sp)
                    )//Label for this layout
                )

                if(show_speciality_dialog.value==true) {
                    Dialog(onDismissRequest = { /*TODO*/show_speciality_dialog.value = false }) {
                        Card(
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
                            colors = CardColors(
                                containerColor = Color.Black,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Black,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text("Please Choose Speciality", textAlign = TextAlign.Center)
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(150.dp),
                                state = grid_state,
                                modifier = Modifier.fillMaxHeight(0.9f),
                                userScrollEnabled = true
                            )
                            {
                                for ((index, speciality) in specialities.withIndex()) {
                                    Log.i(
                                        "speciality_log",
                                        "Using speciality: " + index + " called " + speciality
                                    )
                                    item {
                                        var enabled_state = remember { mutableStateOf(true) }
                                        TextButton(
                                            onClick = { /*TODO*/
                                                var tmp_enabled_state: Boolean = enabled_state.value
                                                println("old enabled state: " + tmp_enabled_state)
                                                enabled_state.value = !tmp_enabled_state
                                                println("new enabled state: " + enabled_state)
                                                last_enabled_state.value = !last_enabled_state.value
                                                last_enabled_state = enabled_state

                                                active_speciality = speciality
                                                registration_model.user_role = index.toString()


                                            },
                                            enabled = (enabled_state.value || last_enabled_state.value),
                                            contentPadding = PaddingValues(1.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Blue,
                                                disabledContainerColor = Color.DarkGray
                                            ),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(text = speciality)
                                        }

                                    }
                                }
                            }
                            registerMedicBtn(registration_model)
                        }
                    }
                }


                Row(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                {

                    registerPatientBtn(registration_model)
                    registerDoctorBtn(show_speciality_dialog)
                }

                Text( //Registration Box Heading
                    "Have an account:",
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                    , style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        color = Color.White,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(integerResource(id = R.integer.text_spacing_default).toFloat(),TextUnitType.Sp)
                    )//Label for this layout
                )

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(11, 65, 156, 255),
                        disabledContainerColor = Color.Blue
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally),

                    onClick = { /*TODO*/change_page.value = false}) {
                    Icon(Icons.Outlined.Person, contentDescription = "")
                    Text(
                        "Login", style = TextStyle(
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

    @Composable
    fun RowScope.registerPatientBtn(registrationModel: RegistrationModel) {
        var show_load_patient = remember { mutableStateOf(false) }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                disabledContainerColor = Color.Blue
            ),

            onClick = { /*TODO*///login(name)
                if((show_load_patient.value == false)&&(toggle_btn == false))
                {
                    registrationModel.user_role = "patient"
                    registrationModel.user_type = "non-medic"

                    toggle_btn = true
                    show_load_patient.value = true
                    GlobalScope.launch(Dispatchers.IO)
                    {
                        register(registrationModel,show_load_patient,hospital_man)
                    }

                    println("registering: "+registrationModel.toJson())
                    Log.i("registering ",registrationModel.toJson())
                }

            }) {

            if(show_load_patient.value == false)
            {
                Icon(Icons.Outlined.Person, contentDescription = "")
                Text(
                    "Patient", style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )
                )
            }
            else
            {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = "", modifier =Modifier.padding(end=20.dp), tint = Color.White)
                showLoading()
            }
        }
    }

    @Composable
    fun registerMedicBtn(registrationModel: RegistrationModel)
    {
        var show_register_loading = remember{ mutableStateOf(false) }
        Button(

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                disabledContainerColor = Color.Blue
            ),
           // modifier = Modifier.padding(start = 20.dp),

            onClick = { /*TODO*///
                // login(name)
                Log.i("RG_STATUS","show_register_loading: "+show_register_loading.value+" toggle button "+toggle_btn)
                if((show_register_loading.value == false)&&(toggle_btn == false))
                {
                   registrationModel.user_type = "medic"

                    toggle_btn = true
                    show_register_loading.value = true
                    GlobalScope.launch(Dispatchers.IO)
                    {
                        register(registrationModel,show_register_loading,hospital_man)
                    }
                    //register(reg)
                    println("registering: "+registrationModel.toJson())
                    Log.i("registering ",registrationModel.toJson())

                }
            })
        {
            if(show_register_loading.value == false)
            {
                Icon(Icons.Outlined.Person, contentDescription = "")
                Text(
                    "Register As Medic", style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )
                )
            }
            else
            {
                Icon(Icons.Outlined.Person, contentDescription = "",modifier=Modifier.padding(end=20.dp))
                showLoading()
            }
        }
    }

    @Composable
    fun RowScope.registerDoctorBtn(toggle_btn: MutableState<Boolean>)
    {

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                disabledContainerColor = Color.Blue
            ),
            modifier = Modifier.padding(start = 20.dp),

            onClick = { /*TODO*///
                // login(name)
                val btn_state = toggle_btn.value
                toggle_btn.value = !btn_state
            })
        {
                Icon(Icons.Outlined.Person, contentDescription = "")
                Text(
                    "Doctor", style = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )
                )

        }
    }

    override fun onStart() {
        super.onStart()

        setContent {
            HomeUI()
        }
    }

    override fun onRestart() {
        super.onRestart()
        setContent {
           HomeUI()
            //RegisterUI()
        }

    }

    override fun onResume() {
        super.onResume()
        setContent {
            HomeUI()


        }
    }

}