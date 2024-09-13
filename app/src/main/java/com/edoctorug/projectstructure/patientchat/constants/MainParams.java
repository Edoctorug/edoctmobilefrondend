package com.edoctorug.projectstructure.patientchat.constants;


/**
 * interface containing main constants used with in the Project
 */
public interface MainParams
{
    public enum DoctorViewScreens{//(val symbol: String = ""){
        CHAT_HISTORY,
        APPOINTMENTS,
        DASHBOARD, //dashboard link
        SPECIALITY, //choose speciality link
        CHAT, //chat link
        CHAT_LOADER, //chat loading link
        CHATBOX_MAIN,
        PRESCRIPTIONS,
        ORDERS,
        RECORDS,
        DIAGNOSES, //prescriptions compose link
        DIALOG,
        LABTEST,
        MAKEORDER,
        SAVE,
        FOCUS
       // FOCUS("FOCUS/{chat_UUID}")
    }

    public enum PatientViewScreens{
        DASHBOARD,

        CHATS,
        FOCUS,
        LABTESTS,
        SPECIALITY,
        CHAT,
        CHAT_LOADER,
        APPOINTMENTS,

        DIAGNOSES,

        PRESCRIPTIONS,

        ORDERS,

        RECORDS,
    }
}