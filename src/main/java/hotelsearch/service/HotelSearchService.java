package hotelsearch.service;

import hotelsearch.model.HotelDetailsDto;
import org.apache.log4j.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by vsushil on 9/14/2017.
 */

@Component
public class HotelSearchService {

    private static org.apache.log4j.Logger log = Logger.getLogger(HotelSearchService.class);

    public HotelDetailsDto getHotelDataById(int hotelId) {
        List<HotelDetailsDto> hotelsList = getAllHotelsFromCsv();
        for(HotelDetailsDto hotel : hotelsList) {
            if (hotel.getHotelId() == hotelId) {
                return hotel;
            }
        }
        return new HotelDetailsDto();
    }

    public List<HotelDetailsDto> getHotelsByCityName(String cityName, String sortType) {
        List<HotelDetailsDto> hotelsByCity = new ArrayList<>();
        List<HotelDetailsDto> hotelsList = getAllHotelsFromCsv();
        for(HotelDetailsDto hotel : hotelsList) {
            if (hotel.getCity().equals(cityName)) {
                log.error(hotel);
                hotelsByCity.add(hotel);
            }
        }
        if ( sortType!=null && (sortType.equals("ASC") || sortType.equals("asc")) ) {
            Collections.sort(hotelsByCity, sortByPriceAsc);
        }
        else if ( sortType!=null && (sortType.equals("DESC") || sortType.equals("desc")) ) {
            Collections.sort(hotelsByCity, sortByPriceDesc);
        }
        return hotelsByCity;
    }

    private static Comparator<HotelDetailsDto> sortByPriceAsc
            = new Comparator<HotelDetailsDto>() {
        public int compare(HotelDetailsDto hotel1, HotelDetailsDto hotel2) {

            int price1 = hotel1.getPrice();
            int price2 = hotel2.getPrice();

            return price1 - price2;
        }
    };

    private static Comparator<HotelDetailsDto> sortByPriceDesc
            = new Comparator<HotelDetailsDto>() {
        public int compare(HotelDetailsDto hotel1, HotelDetailsDto hotel2) {

            int price1 = hotel1.getPrice();
            int price2 = hotel2.getPrice();

            return price2 - price1;
        }
    };

    public List<HotelDetailsDto> getAllHotelsFromCsv() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String fileName= s + "/build/resources/main/Hotel-DB.csv";
        Path pathToFile = Paths.get(fileName);
        log.error(pathToFile);

        List<HotelDetailsDto> allHotels = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(",");
                HotelDetailsDto hotel = createHotel(attributes);
                allHotels.add(hotel);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return allHotels;
    }

    private static HotelDetailsDto createHotel(String[] metadata) {
        int hotelId = Integer.parseInt(metadata[1]);
        String city = metadata[0];
        int price = Integer.parseInt(metadata[3]);
        String room = metadata[2];
        return new HotelDetailsDto(hotelId, city, room, price);
    }
}
