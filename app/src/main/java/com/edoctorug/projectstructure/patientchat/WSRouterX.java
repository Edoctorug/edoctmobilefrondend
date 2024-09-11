package com.edoctorug.projectstructure.patientchat;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.snapshots.SnapshotStateMap;
import androidx.navigation.NavHostController;

import java.util.LinkedHashMap;
import java.util.List;

import kotlin.jvm.internal.markers.KMutableList;
import patientdoctorwebsockets.Models.AppointmentDetails;
import patientdoctorwebsockets.Models.AppointmentsHistory;
import patientdoctorwebsockets.Models.ChatDetails;
import patientdoctorwebsockets.Models.DiagnosesHistory;
import patientdoctorwebsockets.Models.DiagnosisDetails;
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
public class WSRouterX extends WSRouter
{
    List<ChatModel> chat_lists;
    MutableState<String> results_string;

    public SnapshotStateMap<String, AppointmentDetails> appointment_details;
    public SnapshotStateMap<String, PrescriptionDetails> prescriptions_details;
    public SnapshotStateMap<String, OrderDetails> orders_details;
    public SnapshotStateMap<String, RecordDetails> records_details;
    public SnapshotStateMap<String, DiagnosisDetails> diagnoses_details;
    NavHostController active_nav_ctrl;
    MutableState<Boolean> chat_loader_fin;

    MutableState<Boolean> show_dialog; //dialog box controller
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
    public void setDataHolders(SnapshotStateMap<String, AppointmentDetails> xappointment_details,SnapshotStateMap<String, PrescriptionDetails> xprescriptions_details, SnapshotStateMap<String, OrderDetails> xorders_details, SnapshotStateMap<String, RecordDetails> xrecords_details, SnapshotStateMap<String, DiagnosisDetails> xdiagnoses_details){
        prescriptions_details = xprescriptions_details;
        appointment_details = xappointment_details;
        orders_details = xorders_details;
        records_details = xrecords_details;
        diagnoses_details = xdiagnoses_details;

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
        //results_string.setValue(responseModel.status_msg);

    }

    /**
     * Handles the appointments response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void appointmentsHandler(ResponseModel responseModel)
    {
        super.authHandler(responseModel);
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
        super.authHandler(responseModel);
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
        super.authHandler(responseModel);
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
        super.authHandler(responseModel);
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
     * Handles the orders response.
     * @param responseModel The response model containing authentication information.
     */
    @Override
    public void ordersHandler(ResponseModel responseModel)
    {
        super.authHandler(responseModel);
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
                chatDetails = responseModel.meta_data;
                results_string.setValue(responseModel.status_msg);
                chat_loader_fin.setValue(true);
            case 500:
                results_string.setValue(responseModel.status_msg);
            break;
        }
        
    }
}
