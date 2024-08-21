package com.edoctorug.projectstructure.patientchat

/**
 * Class holding a chat message template
 * @property MEDIA_MSG constant for a nessage containing media. e.g an image, or video or sound recording value = 1998
 * @property TEXT_MSG constant for a nessage containing text only. value = 1999
 *@property message variable to hold the message.
 * @property is_patient boolean variable holding user type ( true for patient , false for doctor)
 * @property msg_type integer holding the message type
 *
 * @constructor
 * @param msg message to send
 * @param stat patient status true for patient , false for doctor
 * @param amsg_type type of message
 */
class ChatModel(msg: String,stat: Boolean, amsg_type: Int = 1999) //
{

    public var MEDIA_MSG :Int = 1998 //code for a message with media
    public var TEXT_MSG :Int = 1999 //code for a message with text

    lateinit var message: String //variable for messages
    var is_patient: Boolean = true //is the user a patienr
    public var msg_type: Int //variable to hold the message type
    init{
        message = msg //assign msg in arguments to inner class message variable 
        is_patient = stat
        msg_type = amsg_type
    }
}