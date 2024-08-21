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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.ui.text.input.ImeAction
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

class MarketComposable(private val tmp_home_nav_ctrl: NavHostController)//private val mutable_items_map: SnapshotStateMap<String, ItemDetails>)
{

    

    
    lateinit var show_home_dialog: MutableState<Boolean>
    lateinit var home_dialog_msg: MutableState<String>
    lateinit var main_context: Context
    lateinit var this_role: String
    lateinit var global_session_id: String
    lateinit var main_hospital_man: Hospitalman
    var this_ws_listener: WSmanCB  = WSmanCB()
    lateinit var home_nav_ctrl: NavHostController

    lateinit var show_item_details: MutableState<Boolean>

    lateinit var coroutineScope: CoroutineScope
    lateinit var show_response_window: MutableState<Boolean>

    lateinit var response_string: String
    lateinit var active_item_details: ItemDetails
    lateinit var active_item_uid: String

    lateinit var pharmacies: Array<PharmaModel>

    lateinit var mart_items: Array<ItemDetails>

    lateinit var show_item_dialog: MutableState<Boolean>
    //lateinit var mutable_items_map: SnapshotStateMap<String, ItemDetails>
    //mutableMapOf<String, String>()

    var is_auth = false
    //lateinit 
    //var doctor_viewmodel = viewModel()
    init{
        home_nav_ctrl = tmp_home_nav_ctrl

        //this_role = xthis_role
        //global_session_id = xsession_id

        main_hospital_man = HospitalManSingleton.getInstance()
        var pharma_test = PharmaModel()
        pharma_test.pharma_names = "Test Pharma"
        pharma_test.pharma_uuid = "Test UUID"
        pharma_test.pharma_owner = "Test Owner"
        pharma_test.pharma_address = "Kawempe"

        var item_test = ItemDetails()
        item_test.item_name = "Test 1"
        item_test.item_sku = "test sku"
        item_test.item_price = "5000/="
        item_test.item_description = "Test description"
        item_test.item_pic_url = "https://c8.alamy.com/comp/FXCX3J/packet-of-panadol-extra-advance-tablets-on-a-white-background-FXCX3J.jpg"
        pharmacies = arrayOf(pharma_test,pharma_test)

        mart_items = arrayOf(item_test,item_test,item_test,item_test,item_test,item_test,item_test,item_test,item_test,item_test)
            //Hospitalman(hospital_url,hospital_port)
       // this_ws_listener.setActiveRouter(this)

        

        
    }

    @Composable
    fun Home()
    {
        //Login(this_role,global_session_id, main_hospital_man, this_ws_listener).login()
       // doctor_viewmodel = ViewModelProvider(this).get(SharedHospitalModel::class.java)
       /*mutable_items_map = remember {
        mutableStateMapOf()
    }*/
        coroutineScope =  rememberCoroutineScope()
        show_response_window = remember{ mutableStateOf(false) }
        show_item_dialog = remember{ mutableStateOf(false) }
        ///show_item_request = remember{ mutableStateOf(false)}
        main_context = LocalContext.current
        
        OrderMart()

    }

    /**
    * Market dashBoard
    */    
    @Composable
    fun OrderMart()
    {
        show_item_details = remember{ mutableStateOf(false) }
        val items_modifier = Modifier
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
                                                                 
                                                                ).fillMaxHeight().fillMaxWidth()

                            Box(modifier = main_box_modifier)
                            {
                                Column(//verticalArrangement = Arrangement.spacedBy(5.dp),
                                       modifier = Modifier.fillMaxHeight().fillMaxWidth())
                                {
                                    MarketAppBar()
                                    PharmaciesView()
                                    ItemList()
                                }
                                
                                if(show_item_details.value==true)
                                {
                                    ItemDetailsDialog()
                                }

                            }
                       
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ColumnScope.ItemList()
    {
        var drop_down_scroll_state = rememberScrollState()
        
        /**
        *coroutine scope associated with this composable used to run coroutines in this composable
        */
        val coroutineScope = rememberCoroutineScope()
        
        /**
       .*Gridstate associated with this grid  
        */ 
        var grid_state = rememberLazyGridState()
        
        /**
        *The enabled state of the kast clicked button in this composable 
        */ 
         
        var last_enabled_state = remember{  mutableStateOf(false) }

        Box(modifier = Modifier.padding(top = 10.dp,start=10.dp,end = 10.dp))
        {
            LazyVerticalGrid(columns = GridCells.Adaptive(100.dp),state = grid_state,modifier = Modifier.fillMaxHeight(), userScrollEnabled = true,verticalArrangement = Arrangement.spacedBy(5.dp), horizontalArrangement = Arrangement.spacedBy(5.dp))
            {

                for( mart_item in mart_items)
                {
                    item {
                        var enabled_state = remember{ mutableStateOf(true) }
                       Box()//modifier = Modifier.padding(start = 5.dp,bottom = 5.dp))
                       {
                        MainComposables().ItemView(mart_item,{show_item_dialog.value = true})
                       }
                    

                    }
                    
                }

            }
        }
    }


    @Composable
    fun ColumnScope.PharmaciesView()
    {
        /**
        * scroll state of the current coloumn
        */
        var drop_down_scroll_state = rememberScrollState()
        
        /**
        *coroutine scope associated with this composable used to run coroutines in this composable
        */
        val coroutineScope = rememberCoroutineScope()
        
        /**
       .*Gridstate associated with this grid  
        */ 
        var grid_state = rememberLazyGridState()
        
        /**
        *The enabled state of the kast clicked button in this composable 
        */ 
         
        var last_enabled_state = remember{  mutableStateOf(false) }
        Box(modifier = Modifier.padding( start = 5.dp,
                          end = 5.dp
                    //start = integerResource(id = R.integer.padd).dp,
                    //end = integerResource(id = R.integer.padd).dp
                ))
        {
        Column(
            modifier = Modifier
                .background(Color(0xff050c1b))
                //.align(alignment = Alignment.Center)
                
                //.fillMaxWidth(0.8f)
                .fillMaxHeight(0.2f)
        )
        {
            showText(text = "....Please Choose Pharmacy ......")
            LazyVerticalGrid(columns = GridCells.Adaptive(100.dp),state = grid_state,modifier = Modifier.fillMaxHeight(0.5f), userScrollEnabled = true)
            {

                for( pharmacy in pharmacies)
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

                            //active_speciality = speciality


                        },
                            shape = RoundedCornerShape(1.dp),
                            modifier = Modifier.padding(start = 5.dp),
                            enabled = (enabled_state.value || last_enabled_state.value),
                            contentPadding = PaddingValues(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                disabledContainerColor = Color.DarkGray
                            )
                        ) {
                            showText(text = pharmacy.pharma_names)
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
            
            //PacmanIndicator(ballDiameter = 10f)
        }

        if(show_item_dialog.value == true)
        {
            ItemDetailsDialog()
        }
      }
    }

    /*
    * App bar for the items list view
    */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ColumnScope.MarketAppBar()//, reset: MutableState<Boolean>)
    {
        var query_item_name by remember{mutableStateOf("")}
        var is_enabled by remember{ mutableStateOf(false)}
        var coroutineScope = rememberCoroutineScope()
        Row(modifier = Modifier.background(Color.Transparent).fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly)
        {
                IconButton(onClick ={
                    home_nav_ctrl.navigate(DoctorViewScreens.CHATBOX_MAIN.name) //go back to home screen when clicked on the icon
                }) {
                                Icon(Icons.Filled.ArrowBack,contentDescription = "Go back",tint=Color.White,modifier=Modifier.padding(top=5.dp))
                    }
            
                /*
                BasicTextField(
                    value = query_item_name,
                    onValueChange = { query_item_name = it },//callback function everytime a value in written in the Textfield
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth(0.5f)
                        .padding(5.dp),
                        
                    decorationBox = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "item_name_name"
                        )
                    },*/
                    /*
                    colors = TextFieldDefaults.textFieldColors(
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                        containerColor = Color.White
                    ),
                    */
                    /*
                    //shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = TextUnit(10f, TextUnitType.Sp),
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                    )

                )
                */
                            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically))
                            {
                                BasicTextField(
                                value = query_item_name,
                                onValueChange = { query_item_name = it },
                                textStyle = TextStyle(
                                                        fontSize = TextUnit(10f, TextUnitType.Sp),
                                                        fontStyle = FontStyle.Normal,
                                                        fontFamily = FontFamily.Monospace,
                                                        letterSpacing = TextUnit(2f, TextUnitType.Sp)
                                                     ),
                                modifier = Modifier
                                        .background(
                                                    color = Color.White,
                                                    shape = RoundedCornerShape(22.dp) //rounded corners
                                                    )
                                        
                                                    /*
                                        .indicatorLine(
                                                        enabled = Color.White,
                                                        isError = false,
                                                        //interactionSource = interactionSource,
                                                        colors = TextFieldDefaults.textFieldColors(
                                                                                        unfocusedIndicatorColor = Color.Black,
                                                                                        focusedIndicatorColor = Color.Black,
                                                                                        cursorColor = Color.Black,
                                                                                        containerColor = Color.White
                                                      ),
                                                      
                                                        focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                                                        unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
                                                       )
                                                        */
                                        .height(30.dp)
                                        .fillMaxWidth(0.5f),

                                        //interactionSource = interactionSource,
                                        enabled = true,
                                        singleLine = false,
                                        keyboardOptions = KeyboardOptions(
                                        
                                            imeAction = ImeAction.Search
                                        ),
                                        keyboardActions = KeyboardActions(
                                                                onSearch = {
                                                                    Toast.makeText(main_context,"searching",Toast.LENGTH_LONG).show()
                                                                }
                                                            
                                        )
                                ) 
                                {innerTextField ->
                                    Row(modifier = Modifier.padding(5.dp))
                                    {
                                        Icon(Icons.Filled.Search,contentDescription = "searcher")
                                        Box(modifier = Modifier.align(alignment = Alignment.CenterVertically))
                                        {
                                            innerTextField()
                                        }
                                    }
                                    
                                }
                        }



                
                /**
                    Side menu icon
                 */
                IconButton(onClick = { /*TODO*/
                    home_nav_ctrl.navigate(DoctorViewScreens.CHATBOX_MAIN.name)
                },
                    colors = IconButtonDefaults.iconButtonColors(
                        //containerColor = Color.Black,
                        disabledContainerColor = Color.White
                    ),

                    )
                {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "side menu",tint=Color.White)

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
fun BoxScope.ItemDetailsDialog()
{
    var confirm_item_state = remember {
        mutableStateOf(false)
    }
    
    var item_name = "item 1"
    var item_uuid = "item uuid"
    var item_notes = "item notes"
    Column(//a column layout in the box layout
            modifier = Modifier.fillMaxWidth(0.7f)
            .align(alignment = Alignment.Center)//place this layout at the center of the parent
            .background(Color.Black, shape = RoundedCornerShape(20.dp))//Change this layout to have a black color
    )
    {
        /**
        * item title
        */
        Text(
            "Item Details",
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
        showText("\tItem: $item_name")
        
        showText("\tNotes:\n $item_notes")
        
        Row(modifier = Modifier.padding(top = 5.dp).fillMaxWidth().align(alignment = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
            )
        {
            
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = Color.Gray
                ),
                 modifier = Modifier.padding(top = 5.dp).align(alignment = Alignment.CenterVertically),
                onClick = { /*TODO*/

                    show_item_details.value = false

                })
            {
            
                    Icon(Icons.Filled.Biotech, contentDescription = "")
                    Text(
                        "+", style = TextStyle(
                            fontSize = TextUnit(10f, TextUnitType.Sp),
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = TextUnit(2f, TextUnitType.Sp)
                        )
                    )
                
            }

            showText("Click To Buy!")

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
                    "-", style = TextStyle(
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