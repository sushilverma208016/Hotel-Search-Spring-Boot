package hotelsearch.model;

/**
 * Created by vsushil on 9/15/2017.
 */
public class ResponseObjectDto {
    String message;
    Object object;

    public ResponseObjectDto() {}

    public ResponseObjectDto(String message, Object object) {
        super();
        this.message = message;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
}
