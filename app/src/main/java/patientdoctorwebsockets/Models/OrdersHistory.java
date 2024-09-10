package patientdoctorwebsockets.Models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

public class OrdersHistory
{


    public OrderDetails[] orders_history ;

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

    public static OrdersHistory deJson(String json_String)
    {

        ObjectMapper object_mapper = new ObjectMapper(); //get jackson object mapper

        try
        {
            OrdersHistory this_order_detail = object_mapper.readValue(json_String,OrdersHistory.class); //deserialize json object into a java class

            return this_order_detail; //return true if successful
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return null; //return false if unsuccessful
    }

    public static OrdersHistory deJson(LinkedHashMap hashmap)
    {

        ObjectMapper object_mapper = new ObjectMapper(); //get jackson object mapper

        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            String json_String = objectMapper.writeValueAsString(hashmap);
            OrdersHistory this_order_detail = object_mapper.readValue(json_String,OrdersHistory.class); //deserialize json object into a java class

            return this_order_detail; //return true if successful
        }
        catch(JsonProcessingException jse)
        {
            System.out.println(jse.getMessage());
        }
        return null; //return false if unsuccessful
    }

}
