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
class ChatSummaryModel(chat_slave: String,chat_date_time: String) //
{

    lateinit var chat_name: String //variable for messages
    lateinit var chat_time_date: String
    init{
        chat_name = chat_slave
        chat_time_date = chat_date_time
    }
}