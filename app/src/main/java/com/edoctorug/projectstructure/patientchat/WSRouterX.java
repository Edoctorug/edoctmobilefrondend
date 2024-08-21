package com.edoctorug.projectstructure.patientchat;

import androidx.compose.runtime.MutableState;
import androidx.navigation.NavHostController;

import java.util.List;

import kotlin.jvm.internal.markers.KMutableList;
import patientdoctorwebsockets.Models.ResponseModel;
import patientdoctorwebsockets.WSRouter;
import com.edoctorug.projectstructure.patientchat.constants.MainParams.DoctorViewScreens;
import com.edoctorug.projectstructure.patientchat.constants.MainParams.PatientViewScreens;
public class WSRouterX extends WSRouter
{
    List<ChatModel> chat_lists;
    MutableState<String> results_string;

    NavHostController active_nav_ctrl;
    MutableState<Boolean> chat_loader_fin;

    MutableState<Boolean> show_dialog; //dialog box controller
    
    /**
    * WSRouterX constructor
    * @param chat_models a ChatModel object to which the recieved chat messages will be stored.
    * @param xresults_string a mutable string that will store the result message
    * @param xactiver_navCtrl NavHostController to controller the chat dashboard
    */
    public WSRouterX(List<ChatModel> chat_models, MutableState<String> xresults_string,NavHostController xactive_navCtrl)
    {
        chat_lists = chat_models;
        results_string = xresults_string;
        active_nav_ctrl = xactive_navCtrl;
    }

    public WSRouterX(List<ChatModel> chat_models, MutableState<String> xresults_string,MutableState<Boolean> xactive_navCtrl)
    {
        chat_lists = chat_models;
        results_string = xresults_string;
        chat_loader_fin = xactive_navCtrl;
    }
    

    public WSRouterX(List<ChatModel> chat_models, MutableState<String> xresults_string,MutableState<Boolean> xactive_navCtrl, MutableState<Boolean> dialog_state)
    {
        chat_lists = chat_models;
        results_string = xresults_string;
        chat_loader_fin = xactive_navCtrl;
        show_dialog = dialog_state;
    }

    /**
    * Handles the authentication response.
    * @param responseModel The response model containing authentication information.
    */
    @Override
    public void authHandler(ResponseModel responseModel)
    {
        super.authHandler(responseModel);
        results_string.setValue(responseModel.status_msg);

    }
    
    /**
    * Handles the message response.
    * @param responseModel The response model containing message information.
    */
    @Override
    public void msgHandler(ResponseModel responseModel)
    {
        super.msgHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);
        String str_msg = responseModel.status_msg;
        chat_lists.add(new ChatModel(str_msg,false,1999));

    }

    /**
    * Handle the prescription result
    */
    @Override
    public void prescribeHandler(ResponseModel responseModel)
    {
        super.msgHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);
        String str_msg = responseModel.status_msg;
        results_string.setValue(str_msg); //set websocket result message
        show_dialog.setValue(true);

    }

    /**
    * Handle the prescription result
    */
    @Override
    public void defaultHandler(ResponseModel responseModel)
    {
        super.msgHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);
        String str_msg = responseModel.status_msg;
        results_string.setValue(str_msg); //set websocket result message
        show_dialog.setValue(true);

    }

    
    @Override
    public void matchHandler(ResponseModel responseModel)
    {
        super.matchHandler(responseModel);

        switch(responseModel.status_code)
        {
            case 200:
                results_string.setValue(responseModel.status_msg);
                chat_loader_fin.setValue(true);
            case 500:
                results_string.setValue(responseModel.status_msg);
            break;
        }
        
    }
}
