package com.edoctorug.projectstructure.patientchat.composables

import androidx.compose.foundation.Image
import patientdoctorwebsockets.Models.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text

import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.res.integerResource

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollBy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import com.spr.jetpack_loading.components.indicators.BallBeatIndicator
import com.spr.jetpack_loading.components.indicators.PulsatingDot

import androidx.compose.ui.res.integerResource
import com.edoctorug.projectstructure.patientchat.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.shape.RectangleShape
import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.navigation.NavHostController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.edoctorug.projectstructure.patientchat.constants.MainParams.DoctorViewScreens

import coil.compose.AsyncImage
class MainComposables
{

    @Composable
    public fun showLoading()
    {
        BallBeatIndicator(ballDiameter = 8f,
            ballCount = 10,
            spaceBetweenBalls = integerResource(id = R.integer.text_spacing_default).toFloat()
        )
    }

    @Composable
    public fun showLogo(){
        Image(
            ImageBitmap.imageResource(R.drawable.edoctor_logo_white),
            contentDescription = "Patient",
            //tint=Color.White,
            modifier= Modifier
                .padding(top = 5.dp),
               // .align(alignment = Alignment.CenterHorizontally),
            filterQuality = FilterQuality.High
        )
    }


    @Composable
    public fun showLoadingPulse()
    {
        PulsatingDot(ballDiameter = 15f,
            color = Color.White
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ChatSummary(chat_slave: String, chat_date: String,chatfx: () -> Unit)
    {
        var btn_state = remember{mutableStateOf(true)}
        var btn_defaults= ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            )
        Button( onClick = {
            //btn_state.value = !btn_state.value
            chatfx()
        },
        modifier = Modifier
            .background(Color(0xff060b2b), RoundedCornerShape(1.dp))
            .fillMaxWidth(), colors = btn_defaults)
        {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth())
            {
                Icon(Icons.Filled.AccountCircle, contentDescription = "account", tint = Color.White,modifier = Modifier.size(30.dp))
                Column()
                {
                    showText(chat_slave)
                    showText(chat_date)
                }
            }
        }
    }

    /*
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun statusBox(status_title: String, status_msg: String,chatbox_nav_ctrl: NavHostController,home_dialog_ctrl: MutableBoolean)
    {
        
        AlertDialog(
            onDismissRequest = {
                                chatbox_nav_ctrl.navigate(DoctorViewScreens.CHATBOX_MAIN.name)
                                home_dialog_ctrl.value = false
                               },
            properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true),
            modifier = Modifier.background(color = Color.Blue),
            title = {showText(status_title)}
            text = {showText(status_msg)}
        )
        
        {
            showText(text = result_msg.value)
            //sshowText(text = "User name or password Incorrect")
        }
        

    }
    */

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppointmentSummary(chat_slave: String, chat_date: String,chatfx: () -> Unit)
    {
        var btn_state = remember{mutableStateOf(true)}
        var btn_defaults= ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            )
        Button( onClick = {
            //btn_state.value = !btn_state.value
            chatfx()
        },
        modifier = Modifier
            .background(Color(0xff060b2b), RoundedCornerShape(1.dp))
            .fillMaxWidth(), colors = btn_defaults)
        {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth())
            {
                Icon(Icons.Filled.CalendarMonth, contentDescription = "calender month", tint = Color.White,modifier = Modifier.size(30.dp))
                Column()
                {
                    showText("Appointment with: $chat_slave")
                    showText("Appointment On: $chat_date")
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DiagnosisSummary(chat_slave: String, chat_date: String,chatfx: () -> Unit)
    {
        var btn_state = remember{mutableStateOf(true)}
        var btn_defaults= ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            )
        Button( onClick = {
            //btn_state.value = !btn_state.value
            chatfx()
        },
        modifier = Modifier
            .background(Color(0xff060b2b), RoundedCornerShape(1.dp))
            .fillMaxWidth(), colors = btn_defaults)
        {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth())
            {
                Icon(Icons.Filled.CalendarMonth, contentDescription = "calender month", tint = Color.White,modifier = Modifier.size(30.dp))
                Column()
                {
                    showText("Patient Name: $chat_slave")
                    showText("Diagnosis On: $chat_date")
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LabTestSummary(chat_slave: String,test_name: String, chat_date: String,chatfx: () -> Unit)
    {
        var btn_state = remember{mutableStateOf(true)}
        /*
        var test_for = chat_slave.labtest_for
        var test_author = chat_slave.labtest_author
        var test_name = chat_slave.labtest_name
        var test_date = chat_slave.labtest_date
        var test_details = chat_slave.labtest_details
        var test_results = chat_slave.labtest_results*/
        var btn_defaults= ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.DarkGray
        )
        Button( onClick = {
            //btn_state.value = !btn_state.value
            chatfx()
        },
            modifier = Modifier
                .background(Color(0xff060b2b), RoundedCornerShape(1.dp))
                .fillMaxWidth(), colors = btn_defaults)
        {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth())
            {
                Icon(Icons.Filled.CalendarMonth, contentDescription = "calender month", tint = Color.White,modifier = Modifier.size(30.dp))
                Column()
                {
                    /*showText("\t\tTest name: $test_name")
                    showText("Patient name: $test_for")
                    showText("Test Author: $test_author")
                    showText("Test On: $test_date")
                    showText("Test details: $test_details")
                    showText("\t\tTest Results: $test_results")*/
                    showText("test name: $test_name")
                    showText("test by: $chat_slave")
                    showText("test on: $chat_date")

                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun RecordSummary(chat_slave: String, chat_date: String,chatfx: () -> Unit)
    {
        var btn_state = remember{mutableStateOf(true)}
        var btn_defaults= ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            )
        Button( onClick = {
            //btn_state.value = !btn_state.value
            chatfx()
        },
        modifier = Modifier
            .background(Color(0xff060b2b), RoundedCornerShape(1.dp))
            .fillMaxWidth(), colors = btn_defaults)
        {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth())
            {
                Icon(Icons.Filled.CalendarMonth, contentDescription = "calender month", tint = Color.White,modifier = Modifier.size(30.dp))
                Column()
                {
                    showText("Patient name: $chat_slave")
                    showText("Recorded On: $chat_date")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PrescriptionSummary(chat_slave: String, chat_date: String,chatfx: () -> Unit)
    {
        var btn_state = remember{mutableStateOf(true)}
        var btn_defaults= ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            )
        Button( onClick = {
            //btn_state.value = !btn_state.value
            chatfx()
        },
        modifier = Modifier
            .background(Color(0xff060b2b), RoundedCornerShape(1.dp))
            .fillMaxWidth(), colors = btn_defaults)
        {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth())
            {
                Icon(Icons.Filled.CalendarMonth, contentDescription = "calender month", tint = Color.White,modifier = Modifier.size(30.dp))
                Column()
                {
                    showText("Prescription For: $chat_slave")
                    showText("Created On: $chat_date")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OrderSummary(chat_slave: String, chat_date: String,chatfx: () -> Unit)
    {
        var btn_state = remember{mutableStateOf(true)}
        var btn_defaults= ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.DarkGray
                            )
        Button( onClick = {
            //btn_state.value = !btn_state.value
            chatfx()
        },
        modifier = Modifier
            .background(Color(0xff060b2b), RoundedCornerShape(1.dp))
            .fillMaxWidth(), colors = btn_defaults)
        {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth())
            {
                Icon(Icons.Filled.CalendarMonth, contentDescription = "calender month", tint = Color.White,modifier = Modifier.size(30.dp))
                Column()
                {
                    showText("Order For: $chat_slave")
                    showText("Order Date: $chat_date")
                }
            }
        }
    }

     @Composable
    fun showText(text: String,xmodifier: Modifier = Modifier)
    {
        Text( //Registration Box Heading
            text,
            modifier = xmodifier
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

    @Composable
    fun ItemView(item_details: ItemDetails,onclick_fx: () -> Unit)
    {
        //item view modifier
        var item_modifier = Modifier
            .background(Color.White, RoundedCornerShape(5.dp))
            .width(300.dp)
        
        Column(modifier = item_modifier.clickable(onClick = { onclick_fx()}))
        {

            AsyncImage(model = item_details.item_pic_url, contentDescription = "item image", modifier = Modifier.size(100.dp))
            Text( //Registration Box Heading
            text = item_details.item_name,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            //  .align(alignment = Alignment.CenterHorizontally)
            style = TextStyle(
                fontSize = TextUnit(15f, TextUnitType.Sp),
                fontStyle = FontStyle.Normal,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = TextUnit(3f,
                    TextUnitType.Sp
                )
            ))//Label for this layout

            Text( //Registration Box Heading
            text = item_details.item_price,
            modifier = Modifier.padding(start = 10.dp,top = 5.dp),
            //  .align(alignment = Alignment.CenterHorizontally)
            style = TextStyle(
                fontSize = TextUnit(12f, TextUnitType.Sp),
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                fontFamily = FontFamily.Monospace,
                letterSpacing = TextUnit(
                    integerResource(id = R.integer.text_spacing_default).toFloat(),
                    TextUnitType.Sp
                )
            )//Label for this layout
        )
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    disabledContainerColor = Color.Blue
                ),
                    
                onClick = { /*TODO*/
                    
                })
                {
                    showText("BUY")
                }
        
        }
    }

    
    


}