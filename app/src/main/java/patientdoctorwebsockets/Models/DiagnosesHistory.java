package patientdoctorwebsockets.Models;



import java.util.LinkedHashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import patientdoctorwebsockets.Models.*;
public class DiagnosesHistory
{


    public DiagnosisDetails[] diagnoses_history ;

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

    public static DiagnosesHistory deJson(String json_String)
    {

        ObjectMapper object_mapper = new ObjectMapper(); //get jackson object mapper

        try
        {
            DiagnosesHistory this_diagnosis_detail = object_mapper.readValue(json_String,DiagnosesHistory.class); //deserialize json object into a java class

            return this_diagnosis_detail; //return true if successful
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return null; //return false if unsuccessful
    }

    public static DiagnosesHistory deJson(LinkedHashMap hashmap)
    {

        ObjectMapper object_mapper = new ObjectMapper(); //get jackson object mapper

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            String json_String = objectMapper.writeValueAsString(hashmap);
            DiagnosesHistory this_diagnosis_detail = object_mapper.readValue(json_String,DiagnosesHistory.class); //deserialize json object into a java class

            return this_diagnosis_detail; //return true if successful
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return null; //return false if unsuccessful
    }

}
