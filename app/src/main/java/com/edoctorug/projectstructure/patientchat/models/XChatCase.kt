package com.edoctorug.projectstructure.patientchat.models



import patientdoctorwebsockets.Models.ChatDetails
import com.edoctorug.projectstructure.patientchat.ChatModel


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class XChatCase(private var x_owner_name: String,private var x_chat_details: ChatDetails)
{
    private lateinit var ownner_name: String
    private lateinit var chat_details: ChatDetails
    private lateinit var chat_lists: SnapshotStateList<ChatModel>

    init{
        ownner_name = x_owner_name
        chat_details = x_chat_details
        chat_lists = mutableStateListOf<ChatModel>()

    }

    public fun getChatDetails(): ChatDetails
    {
        return chat_details
    }

    public fun getChatOwner(): String
    {
        return ownner_name
    }

    public fun getChatList(): SnapshotStateList<ChatModel>
    {
        return chat_lists
    }
}