package hotelsearch.model;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vsushil on 9/15/2017.
 */
public class ApiKeyDto {
    long lastAccessEpoch;
    AtomicInteger count;

    public ApiKeyDto() {}

    public ApiKeyDto(long lastAccessEpoch, AtomicInteger count) {
        super();
        this.lastAccessEpoch = lastAccessEpoch;
        this.count = count;
    }

    public long getLastAccessEpoch() {
        return lastAccessEpoch;
    }
    public void setLastAccessEpoch(long lastAccessEpoch) {
        this.lastAccessEpoch = lastAccessEpoch;
    }
    public AtomicInteger getCount() {
        return count;
    }
    public void setCount(AtomicInteger count) {
        this.count = count;
    }
}
