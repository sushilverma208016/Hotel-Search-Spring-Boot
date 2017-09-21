package hotelsearch.model;

/**
 * Created by vsushil on 9/14/2017.
 */
public class HotelDetailsDto {
    private int hotelId;
    private String city;
    private String room;
    private int price;

    public HotelDetailsDto() {}

    public HotelDetailsDto(int hotelId, String city, String room, int price) {
        super();
        this.hotelId = hotelId;
        this.city = city;
        this.room = room;
        this.price = price;
    }

    public int getHotelId() {
        return hotelId;
    }
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Hotel Id: " + hotelId + ", City: " + city + ", Price: " + price + ", Room: " + room;
    }
}
