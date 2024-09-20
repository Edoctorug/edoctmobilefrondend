package patientdoctorwebsockets.Models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DoctorDetails 
{
    public String doctor_id = "";
    public String user_name = "";
    public String full_names = "";
    public String assigned_patient = "";

    public String chat_uuid = "";
    public String toJson()
    {
        ObjectMapper this_mapper = new ObjectMapper();
        String json_str = "";
        try
        {
           json_str = this_mapper.writeValueAsString(this);
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return json_str;
    }
}
