package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {
    private UUID token;
    @JsonProperty("ticket")
    private Seat seat;

    public Ticket(){}

    public Ticket(Seat seat){
        this.token = UUID.randomUUID();
        this.seat = seat;
    }

    public UUID getToken() {
        return token;
    }

    public void unBook(){
        this.seat.unBook();
    }

    public Seat getSeat() {
        return seat;
    }

}
