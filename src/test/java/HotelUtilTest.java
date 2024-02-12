import com.ascendaloyalty.util.HotelUtil;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the HotelUtil class.
 */
@Disabled
@SpringBootTest
class HotelUtilTest {

    private net.minidev.json.JSONObject jsonOld;
    private net.minidev.json.JSONObject jsonNew;
    private JsonNode templateNode;
    private String hotelId;

    @BeforeEach
    void setUp() throws JSONException {
        hotelId = "hotel123";

        //initialize jsonOld and jsonNew
        jsonOld = new net.minidev.json.JSONObject();
        jsonOld.put("name", "old hotel");

        jsonNew = new net.minidev.json.JSONObject();
        jsonNew.put("name", "new hotel");

        //initialize templateNode    
        ObjectMapper mapper = new ObjectMapper();
        templateNode = mapper.createObjectNode();

        // Values should be put to the templateNode according to your requirements before testing mergeData
    }

    @Test
    void updateMapDataTest() {
        HotelUtil.updateMapData(hotelId, jsonOld, templateNode);

        JSONObject jsonObject = new JSONObject(HotelUtil.hotelIdMap.get(hotelId));
        assertNotNull(jsonObject);
        assertEquals(jsonOld.toString(), jsonObject.toString());
    }

    @Test
    void mergeDataTest() {
        HotelUtil.mergeData(templateNode, jsonOld, jsonNew);

        // Assertions should be based on your mergeData logic.
    }

    @AfterEach
    void tearDown(){
        jsonOld = null;
        jsonNew = null;
        templateNode = null;
        hotelId = null;
    }
}