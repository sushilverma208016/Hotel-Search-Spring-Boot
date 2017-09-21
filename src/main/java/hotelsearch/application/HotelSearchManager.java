package hotelsearch.application;

import hotelsearch.model.ApiKeyDto;
import hotelsearch.model.HotelDetailsDto;
import hotelsearch.model.ResponseObjectDto;
import hotelsearch.service.HotelSearchService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vsushil on 9/14/2017.
 */

@RestController
@EnableConfigurationProperties
public class HotelSearchManager {

    @Autowired
    private HotelSearchService hotelSearchService;

    @Autowired
    private ServletContext servlet;

    @Value("${suspension-time}")
    BigInteger suspensionTime;

    @Value("${max-count}")
    int maxCount;

    private static org.apache.log4j.Logger log = Logger.getLogger(HotelSearchService.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseObjectDto getWelcomeMessage() {
        log.error("Welcome to Hotel-Search");
        return new ResponseObjectDto("Welcome to Hotel-Search", null);
    }

    @RequestMapping(value = "/hotel/{key}/{hotelId}", method = RequestMethod.GET)
    public ResponseObjectDto getHotelByHotelId(
            @PathVariable String key, @PathVariable int hotelId) {

        if (this.servlet.getAttribute(key) != null) {
            ApiKeyDto curKey = (ApiKeyDto) this.servlet.getAttribute(key);
            long timeInterval =  (long) System.currentTimeMillis() - (long) curKey.getLastAccessEpoch();

            if (curKey.getCount().get() <= maxCount ) {
                curKey.setLastAccessEpoch(System.currentTimeMillis());
                curKey.getCount().getAndIncrement();
                return allowAccessGetHotelByHotelId(hotelId);
            } else {
                return suspend(curKey);
            }
        }
        else {
            this.servlet.setAttribute( key, new ApiKeyDto(System.currentTimeMillis(), new AtomicInteger(1)) );
            return allowAccessGetHotelByHotelId(hotelId);
        }
    }

    private ResponseObjectDto allowAccessGetHotelByHotelId(int hotelId) {
        log.error("Call for hotel by hotel id " + hotelId);
        HotelDetailsDto hotel = hotelSearchService.getHotelDataById(hotelId);
        ResponseObjectDto response;

        if (hotel.getHotelId() != 0) {
            response = new ResponseObjectDto("OK", hotel);
        }
        else {
            response = new ResponseObjectDto("Hotel ID not found in Database", null);
        }
        return response;
    }

    // As city id is not given in csv file, returning list of hotels of city by city name.
    @RequestMapping(value = "/all-hotels/{key}/{cityName}", method = RequestMethod.GET)
    public ResponseObjectDto allHotelsByCityName(
            @PathVariable String key, @PathVariable String cityName,
            @RequestParam(value = "sort", required = false) String sortType) {

        if (this.servlet.getAttribute(key) != null) {
            ApiKeyDto curKey = (ApiKeyDto) this.servlet.getAttribute(key);
            long timeInterval =  (long) System.currentTimeMillis() - (long) curKey.getLastAccessEpoch();

            if (curKey.getCount().get() <= maxCount ) {
                curKey.setLastAccessEpoch(System.currentTimeMillis());
                curKey.getCount().getAndIncrement();
                return allowAccessAllHotelsByCityName(cityName, sortType);
            } else {
                return suspend(curKey);
            }
        }
        else {
            this.servlet.setAttribute( key, new ApiKeyDto(System.currentTimeMillis(), new AtomicInteger(1)) );
            return allowAccessAllHotelsByCityName(cityName, sortType);
        }
    }

    private ResponseObjectDto allowAccessAllHotelsByCityName(String cityName, String sortType) {
        log.error("Call for all hotels by city name " + cityName + " sortType " + sortType);
        List<HotelDetailsDto> hotelsByCity = hotelSearchService.getHotelsByCityName(cityName, sortType);
        ResponseObjectDto response;
        if (hotelsByCity.size() > 0) {
            response = new ResponseObjectDto("OK", hotelsByCity);
        }
        else {
            response = new ResponseObjectDto("Invalid city or no hotel available in the city", new Object());
        }

        return response;
    }

    private ResponseObjectDto suspend(ApiKeyDto curKey) {
        curKey.setLastAccessEpoch(System.currentTimeMillis());
        curKey.getCount().getAndIncrement();

        return new ResponseObjectDto("You have to wait!", null);
    }

    @RequestMapping("/set-key")
    public ResponseObjectDto setApiKey() {
        // generate a random string of 20 chars as key
        log.error("Setting API key");

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();
        while (key.length() < 20) {
            int index = (int) (rnd.nextFloat() * chars.length());
            key.append(chars.charAt(index));
        }
        String apikey = key.toString();
        return new ResponseObjectDto("OK", apikey);
    }

}
