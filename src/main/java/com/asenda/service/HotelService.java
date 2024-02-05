package com.asenda.service;


import com.asenda.util.HotelUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

/**
 * The HotelService class is responsible for loading data from a JSON file, processing the data, and retrieving data by hotel or destination ID.
 */
@Service

public class HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    @Autowired
    ObjectMapper objectMapper;

    public void loaddData() {
        try {
            JsonNode templateNode = getJsonTemplate().get("datasource_hostname");
            for (int i = 0; i < templateNode.size(); i++) {
                String url = sanitizeUrl(templateNode.get(i));
                LOGGER.info("Processing URL :" + url);
                JsonNode jsonArray = objectMapper.readValue(new URL(url), JsonNode.class);
                processDatasource(jsonArray);
            }
        } catch (Exception ex) {
            LOGGER.error("An error occurred while processing json: ", ex);
            throw new RuntimeException("Error processing JSON", ex);
        }
        LOGGER.info("Data loaded successfully ...");
    }

    private String sanitizeUrl(JsonNode jsonNode) {
        return String.valueOf(jsonNode).replaceAll("\"", "").trim();
    }


    public void processDatasource(JsonNode jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonNode arrayElement = jsonArray.get(i);
            processJson(arrayElement);
        }
    }

    public void processJson(JsonNode node) {
        try {
            JSONObject data = new JSONObject();
            JsonNode templateNode = getJsonTemplate().get("mapping");
            for (int i = 0; i < templateNode.size(); i++) {
                JsonNode template = templateNode.get(i);
                String columnName = template.get("column").textValue();
                data.put(columnName, getJsonValue(node, template));
            }
            HotelUtil.updateMapData(data.getAsString("hotel_id"), data, templateNode);

        } catch (Exception ex) {
            LOGGER.error("An error occurred while processing json: ", ex);
            throw new RuntimeException("Error processing JSON", ex);
        }
    }

    public String getJsonValue(JsonNode node, JsonNode template) {
        String result = "";
        JsonNode lookupColumns = template.get("lookup_columns");
        for (int i = 0; i < lookupColumns.size(); i++) {
            String lookupColumn = lookupColumns.get(i).textValue();
            JsonNode jsonValue = node.get(lookupColumn);
            if (jsonValue != null) {
                result = String.valueOf(node.get(lookupColumn)).replaceAll("\"", "").trim();
            }
        }
        return StringUtils.isEmpty(result) || result.equals("'") || "null".equalsIgnoreCase(result.trim())
                ? template.get("default_value").asText()
                : result;
    }

    public JsonNode getJsonTemplate() throws IOException {
        JsonNode template = objectMapper.readValue(new ClassPathResource("template.json").getFile(), JsonNode.class);
        return template;
    }

    public String getJsonValueAA(JsonNode node, JsonNode template) {

        String result = "";
        JsonNode lookupColumns = template.get("lookup_columns");
        for (int i = 0; i < lookupColumns.size(); i++) {
            String lookupColumn = lookupColumns.get(i).textValue();
            JsonNode jsonValue = node.get(lookupColumn);
            if (jsonValue != null) {
                result = String.valueOf(node.get(lookupColumn)).replaceAll("\"", "").trim();
            }

        }
        if (result.trim().equals("null") || result.equals("'") || result.isEmpty())
            result = template.get("default_value").asText();
        return result;
    }

    public String getDatabyHotelId(String hotelId) {

        if (HotelUtil.hotelIdMap.containsKey(hotelId)) {
            return HotelUtil.hotelIdMap.get(hotelId).toJSONString();
        } else {
            return "Hotel Id= " + hotelId + " is not present";
        }
    }

    public String getDatabyDestId(String destId) {

        if (HotelUtil.destIdMap.containsKey(destId)) {
            return HotelUtil.destIdMap.get(destId).toJSONString();
        } else {
            return "Destination Id= " + destId + " is not present";
        }
    }
}

