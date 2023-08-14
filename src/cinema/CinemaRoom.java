package cinema;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class CinemaRoom {
    private int total_rows ;
    private int total_columns ;
    private List<Seat> available_seats;
    @JsonIgnore
    private List<Ticket> unavailable_seats;

    public List<Ticket> getUnavailable_seats() {
        return unavailable_seats;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }

    public Seat getAvailable_seats(int row, int column) {
        for (Seat seat:available_seats) {
            if (seat.getRow() == row && seat.getColumn() == column){
                return seat;
            }
        }
        return null;
    }

    public CinemaRoom() {
        this.total_rows = 9;
        this.total_columns = 9;
        this.available_seats = generateAvailableSeats();
    }
    private List<Seat> generateAvailableSeats() {
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= total_rows; row++) {
            for (int column = 1; column <= total_columns; column++) {
                seats.add(new Seat(row, column));
            }
        }
        return seats;
    }
    public void addTicket(Ticket ticket){
        if(unavailable_seats == null){
            unavailable_seats = new ArrayList<>();
      }
        unavailable_seats.add(ticket);
    }

    public Ticket findAndRemoveTicket(UUID token) {
        for (Iterator<Ticket> iterator = unavailable_seats.iterator(); iterator.hasNext();) {
            Ticket ticket = iterator.next();
            if (ticket.getToken().equals(token)) {
                iterator.remove();
                return ticket;
            }
        }
        return null;
    }
}
