package patientdoctorwebsockets.Models;



import java.util.LinkedHashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import patientdoctorwebsockets.Models.*;
public class LabTestsHistory
{


    public LabTestDetails[] labtests_history ;

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

    public static LabTestsHistory deJson(String json_String)
    {

        ObjectMapper object_mapper = new ObjectMapper(); //get jackson object mapper

        try
        {
            LabTestsHistory this_labtest_detail = object_mapper.readValue(json_String,LabTestsHistory.class); //deserialize json object into a java class

            return this_labtest_detail; //return true if successful
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return null; //return false if unsuccessful
    }

    public static LabTestsHistory deJson(LinkedHashMap hashmap)
    {

        ObjectMapper object_mapper = new ObjectMapper(); //get jackson object mapper

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            String json_String = objectMapper.writeValueAsString(hashmap);
            LabTestsHistory this_labtest_detail = object_mapper.readValue(json_String,LabTestsHistory.class); //deserialize json object into a java class

            return this_labtest_detail; //return true if successful
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return null; //return false if unsuccessful
    }

}
