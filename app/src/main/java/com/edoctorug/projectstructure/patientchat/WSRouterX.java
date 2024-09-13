package com.edoctorug.projectstructure.patientchat;

import android.util.Log;

import androidx.collection.MutableObjectList;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.navigation.NavHostController;

import java.util.LinkedHashMap;
import java.util.List;

import kotlin.jvm.internal.markers.KMutableList;
import patientdoctorwebsockets.Models.AppointmentDetails;
import patientdoctorwebsockets.Models.AppointmentsHistory;
import patientdoctorwebsockets.Models.ChatDetails;
import patientdoctorwebsockets.Models.ChatsHistory;
import patientdoctorwebsockets.Models.ChatsList;
import patientdoctorwebsockets.Models.DiagnosesHistory;
import patientdoctorwebsockets.Models.DiagnosisDetails;
import patientdoctorwebsockets.Models.LabTestDetails;
import patientdoctorwebsockets.Models.LabTestsHistory;
import patientdoctorwebsockets.Models.OrderDetails;
import patientdoctorwebsockets.Models.OrdersHistory;
import patientdoctorwebsockets.Models.PrescriptionDetails;
import patientdoctorwebsockets.Models.PrescriptionsHistory;
import patientdoctorwebsockets.Models.RecordDetails;
import patientdoctorwebsockets.Models.RecordsHistory;
import patientdoctorwebsockets.Models.ResponseModel;
import patientdoctorwebsockets.WSRouter;
import com.edoctorug.projectstructure.patientchat.constants.MainParams.DoctorViewScreens;
import com.edoctorug.projectstructure.patientchat.constants.MainParams.PatientViewScreens;
import com.edoctorug.projectstructure.patientchat.models.ChatCase;
import com.edoctorug.projectstructure.patientchat.models.XChatCase;

public class WSRouterX extends WSRouter
{
    List<ChatModel> chat_lists;
    MutableState<String> results_string;

    public SnapshotStateMap<String, AppointmentDetails> appointment_details;
    public SnapshotStateMap<String, PrescriptionDetails> prescriptions_details;
    public SnapshotStateMap<String, OrderDetails> orders_details;
    public SnapshotStateMap<String, RecordDetails> records_details;

    public SnapshotStateMap<String, LabTestDetails> labtests_details;
    public SnapshotStateMap<String, DiagnosisDetails> diagnoses_details;

    public SnapshotStateMap<String, XChatCase> chat_history_map;
    //SnapshotStateMap<String, List< patientdoctorwebsockets.Models.ChatModel>> chat_history_map;
    NavHostController active_nav_ctrl;
    MutableState<Boolean> chat_loader_fin;

    MutableState<Boolean> is_global_loading; //dialog box controller
    public Object chatDetails;
    /**
    * WSRouterX constructor
    * @param chat_models a ChatModel object to which the recieved chat messages will be stored.
    * @param xresults_string a mutable string that will store the result message
    * @param xactiver_navCtrl NavHostController to controller the chat dashboard
    */


    /**
     * set mutabale data holders data holders
     * @param xappointment_details
     * @param xprescriptions_details
     * @param xorders_details
     * @param xrecords_details
     * @param xdiagnoses_details
     */
    public void setDataHolders(SnapshotStateMap<String, AppointmentDetails> xappointment_details,SnapshotStateMap<String, PrescriptionDetails> xprescriptions_details, SnapshotStateMap<String, OrderDetails> xorders_details, SnapshotStateMap<String, RecordDetails> xrecords_details, SnapshotStateMap<String, DiagnosisDetails> xdiagnoses_details, SnapshotStateMap<String, LabTestDetails> xlabtests_details,SnapshotStateMap<String, XChatCase> xchat_history_map){
        prescriptions_details = xprescriptions_details;
        appointment_details = xappointment_details;
        orders_details = xorders_details;
        records_details = xrecords_details;
        diagnoses_details = xdiagnoses_details;
        chat_history_map = xchat_history_map;
        labtests_details = xlabtests_details;

    }
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
        is_global_loading = dialog_state;
    }

    /**
    * Handles the authentication response.
    * @param responseModel The response model containing authentication information.
    */
    @Override
    public void authHandler(ResponseModel responseModel)
    {
        super.authHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

    }

    /**
     * Handles the appointment response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void appointmentHandler(ResponseModel responseModel)
    {
        super.appointmentsHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

        int status_code = responseModel.status_code;

        if(status_code == 200)
        {

            //AppointmentsHistory appointment_history = AppointmentsHistory.deJson((LinkedHashMap) responseModel.meta_data);

            AppointmentDetails appointmentDetails = AppointmentDetails.deJson((LinkedHashMap) responseModel.meta_data);

            String appointment_uuid = appointmentDetails.appointment_uuid;
            System.out.println("Appointment uuid: "+appointment_uuid);
            appointment_details.put(appointment_uuid,appointmentDetails);
            is_global_loading.setValue(false);

        }
        else{

        }

    }

    /**
     * Handles the appointments response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void appointmentsHandler(ResponseModel responseModel)
    {
        super.appointmentsHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

        int status_code = responseModel.status_code;

        if(status_code == 200)
        {

            AppointmentsHistory appointment_history = AppointmentsHistory.deJson((LinkedHashMap) responseModel.meta_data);

            AppointmentDetails allAppointmentDetails[] = appointment_history.appointments_history;

            for(AppointmentDetails appointmentDetails: allAppointmentDetails)
            {
                String appointment_uuid = appointmentDetails.appointment_uuid;
                System.out.println("Appointment uuid: "+appointment_uuid);
                appointment_details.put(appointment_uuid,appointmentDetails);
            }

        }
        else{

        }

    }

    /**
     * Handles the prescriptions response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void prescriptionsHandler(ResponseModel responseModel)
    {
        super.prescriptionHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

        int status_code = responseModel.status_code;

        if(status_code == 200)
        {

            PrescriptionsHistory prescriptions_history = PrescriptionsHistory.deJson((LinkedHashMap) responseModel.meta_data);

            PrescriptionDetails allPrescriptionsDetails[] = prescriptions_history.prescriptions_history;

            for(PrescriptionDetails prescriptionDetails: allPrescriptionsDetails)
            {
                String prescription_uuid = prescriptionDetails.prescription_id;
                System.out.println("Prescription uuid: "+prescription_uuid);
                prescriptions_details.put(prescription_uuid,prescriptionDetails);
            }

        }
        else{

        }

    }

    /**
     * Handles the diagnoses response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void diagnosesHandler(ResponseModel responseModel)
    {
        super.diagnosesHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

        int status_code = responseModel.status_code;

        if(status_code == 200)
        {

            DiagnosesHistory diagnoses_history = DiagnosesHistory.deJson((LinkedHashMap) responseModel.meta_data);

            DiagnosisDetails allDiagnosesDetails[] = diagnoses_history.diagnoses_history;

            for(DiagnosisDetails diagnosisDetails: allDiagnosesDetails)
            {
                String diagnosis_uuid = diagnosisDetails.diagnosis_uuid;
                System.out.println("Diagnoses uuid: "+diagnosis_uuid);
                diagnoses_details.put(diagnosis_uuid,diagnosisDetails);
            }

        }
        else{

        }

    }

    /**
     * Handles the records response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void recordsHandler(ResponseModel responseModel)
    {
        super.recordsHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

        int status_code = responseModel.status_code;

        if(status_code == 200)
        {

            RecordsHistory records_history = RecordsHistory.deJson((LinkedHashMap) responseModel.meta_data);

            RecordDetails allRecordDetails[] = records_history.records_history;

            for(RecordDetails recordDetails: allRecordDetails)
            {
                String record_uuid = recordDetails.record_uuid;
                System.out.println("Appointment uuid: "+record_uuid);
                records_details.put(record_uuid,recordDetails);
            }

        }
        else{

        }

    }



    /**
     * Handles the labtests response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void labtestsHandler(ResponseModel responseModel)
    {
        super.labtestsHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

        int status_code = responseModel.status_code;

        if(status_code == 200)
        {

            LabTestsHistory labtests_history = LabTestsHistory.deJson((LinkedHashMap) responseModel.meta_data);

            LabTestDetails allLabTestDetails[] = labtests_history.labtests_history;

            for(LabTestDetails labtestDetails: allLabTestDetails)
            {
                String labtest_uuid = labtestDetails.labtest_uuid;
                System.out.println("Appointment uuid: "+labtest_uuid);
                labtests_details.put(labtest_uuid,labtestDetails);
            }

        }
        else{

        }

    }

    /**
     * Handles the orders response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void ordersHandler(ResponseModel responseModel)
    {
        super.ordersHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

        int status_code = responseModel.status_code;

        if(status_code == 200)
        {

            OrdersHistory orders_history = OrdersHistory.deJson((LinkedHashMap) responseModel.meta_data);

            OrderDetails allOrdersDetails[] = orders_history.orders_history;

            for(OrderDetails orderDetails: allOrdersDetails)
            {
                String order_uuid = orderDetails.order_uuid;
                System.out.println("Order uuid: "+order_uuid);
                orders_details.put(order_uuid,orderDetails);
            }

        }
        else{

        }

    }

    /**
     * Handles the chat History response.
     * @param responseModel The response model containing chat history information.
     */
    @Override
    public void chatHistoryHandler(ResponseModel responseModel)
    {
        super.chatHistoryHandler(responseModel);
        //results_string.setValue(responseModel.status_msg);

        int status_code = responseModel.status_code;
        Log.i("CALL HISTORY","CALLED CHAT HISTORY");
        if(status_code == 200)
        {
            Log.i("CALL HISTORY","STATUS CODE OKAY CALLED CHAT HISTORY");
            ChatsHistory chats_history = ChatsHistory.deJson((LinkedHashMap) responseModel.meta_data);
            Log.i("CHAT HISTORY: ",responseModel.meta_data.toString());
            ChatsList allchatsDetails[] = chats_history.chat_history;

            for(ChatsList chatsList: allchatsDetails)
            {
                ChatDetails chat_details = chatsList.summary;
                String chat_uuid = chat_details.chat_uuid;
                System.out.println("chat uuid: "+chat_uuid);
                String doctor_name = chat_details.full_names;
                String assigned_patient = "You";

                patientdoctorwebsockets.Models.ChatModel[] chat_models = chatsList.history;
                chat_history_map.put(chat_uuid,new XChatCase(assigned_patient,chat_details));
                ChatModel echat_models[];

                int chat_models_len = chat_models.length;
                for(int chat_models_ct = 0; chat_models_ct<chat_models_len; chat_models_ct++){
                    patientdoctorwebsockets.Models.ChatModel chat_model = chat_models[chat_models_ct];
                    int msg_type = (chat_model.msg_type==true)? 1998: 1999;

                    ChatModel echat_model = new ChatModel(chat_model.msg_data,!chat_model.msg_owner,msg_type);
                    chat_history_map.get(chat_uuid).getChatList().add(echat_model);

                    //int message_type = (chat_model.)
                    //new ChatModel(chat_model.msg_data,true,chat_model.)
                }

            }

        }
        else{

        }

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
        is_global_loading.setValue(false);

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
        is_global_loading.setValue(false);

    }

    
    @Override
    public void matchHandler(ResponseModel responseModel)
    {
        super.matchHandler(responseModel);

        switch(responseModel.status_code)
        {
            case 200:
                chatDetails = responseModel.meta_data;
                results_string.setValue(responseModel.status_msg);
                chat_loader_fin.setValue(true);
            case 500:
                results_string.setValue(responseModel.status_msg);
            break;
        }
        
    }
}
