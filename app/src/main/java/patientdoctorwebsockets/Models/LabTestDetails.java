package patientdoctorwebsockets.Models;


import java.util.LinkedHashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LabTestDetails
{
    public String labtest_uuid ="";
    public String labtest_name = "";
    public String labtest_for = "";
    public String labtest_author = "";

    //public String labtest_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm:ss a")); // get the current date and time when
    public String labtest_date = "";
    public String labtest_details = "";
    public String labtest_results = "";
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

    public static LabTestDetails deJson(String json_String)
    {

        ObjectMapper object_mapper = new ObjectMapper(); //get jackson object mapper

        try
        {
            LabTestDetails this_chat_detail = object_mapper.readValue(json_String,LabTestDetails.class); //deserialize json object into a java class

            return this_chat_detail; //return true if successful
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return null; //return false if unsuccessful
    }

    public static LabTestDetails deJson(LinkedHashMap hashmap)
    {

        ObjectMapper object_mapper = new ObjectMapper(); //get jackson object mapper

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            String json_String = objectMapper.writeValueAsString(hashmap);
            LabTestDetails this_chat_detail = object_mapper.readValue(json_String,LabTestDetails.class); //deserialize json object into a java class

            return this_chat_detail; //return true if successful
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return null; //return false if unsuccessful
    }

}
