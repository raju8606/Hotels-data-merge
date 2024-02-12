package com.ascendaloyalty.util;

import com.fasterxml.jackson.databind.JsonNode;
import net.minidev.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for handling hotel data.
 */
public class HotelUtil {


    public static final Map<String, JSONObject> hotelIdMap = new ConcurrentHashMap<>();
    public static final Map<String, JSONObject> destIdMap = new ConcurrentHashMap<>();


    public static void updateMapData(String hotelId, JSONObject json, JsonNode templateNode) {

        // Adding Hotel data at first time
        if (!hotelIdMap.containsKey(hotelId)) {
            hotelIdMap.put(hotelId, json);
            destIdMap.put(json.getAsString("destination_id"), json);
        } else {
            mergeData(templateNode, hotelIdMap.get(hotelId), json);
        }
    }


    public static void mergeData(JsonNode templateNode, JSONObject oldJson, JSONObject newJson) {
        for (int i = 0; i < templateNode.size(); i++) {
            JsonNode template = templateNode.get(i);
            String columnName = template.get("column").textValue();
            String oldValue = oldJson.getAsString(columnName);
            // update existing json value
            if (oldValue == null || oldValue.equals("null") || oldValue.isEmpty() || oldValue.equals("NA")) {

                oldJson.put(columnName, newJson.getAsString(columnName));
            }
        }
    }
}