package com.edoctorug.projectstructure.patientchat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import patientdoctorwebsockets.Hospitalman
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import com.edoctorug.projectstructure.patientchat.constants.ConnectionParams
import okhttp3.WebSocketListener


import patientdoctorwebsockets.Models.*

class SharedHospitalModel(): ViewModel(){
    private var is_auth  = MutableLiveData<Boolean>()
    /*
    val hospital_url = ConnectionParams.hospital_url //localhost
    
    //Variable containing the port the hospital's server backend is listening on

    var hospital_port = ConnectionParams.hospital_port //port number the server backend is listening to

    private val live_authResponse = MutableLiveData<AuthResponse>()
    public var zhospital_man: Hospitalman = HospitalManSingleton.getInstance()
    //Hospitalman(hospital_url, hospital_port)
    suspend fun login(name: String,zpass: String){
        
        //loading_ctrl.value = true
        var aname: String? = name
        var apass: String? = zpass
        var auth_model: AuthModel = AuthModel(aname, apass)
        println(auth_model.toJson())
       // Log.i("auth data: ", auth_model.toJson())


        //var auth_fx = viewModelScope.async{
          var auth_data =  this.zhospital_man.auth(auth_model)
       // }
        //zhospital_man.auth(auth_model)
        //val auth_data = auth_fx.await()

        live_authResponse.postValue(auth_data)
        



        
        
    }

    suspend fun wslogin(ws_listener: WebSocketListener)
    {
        this.zhospital_man.authWebSocket(ws_listener)

    }

    fun printCookies()
    {
        println("\tSession id is: "+this.zhospital_man.getSessionId()+" with con: "+this.zhospital_man.con_ctx)
    }

    fun getLiveAuthResponse():MutableLiveData<AuthResponse>
    {
        return live_authResponse
    }

    fun getHospitalMan(): Hospitalman{
        return zhospital_man
    }

    fun setHospitalManWS(): Hospitalman{
        return zhospital_man
    }

    */

    fun getAuthState(): Boolean{
        var auth_val = is_auth.value
        return if (auth_val  != null) auth_val else false
    }

    fun setAuthState(new_auth_state: Boolean){
        is_auth.value = new_auth_state
    }

}
