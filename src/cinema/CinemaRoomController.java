package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class CinemaRoomController {
    private final CinemaRoom cinemaroom = new CinemaRoom();
    @GetMapping("/seats")
    public CinemaRoom getCinemaRoom(){
        return cinemaroom;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody Seat requestedSeat){
        if(requestedSeat.getColumn() > cinemaroom.getTotal_columns() ||
           requestedSeat.getRow() > cinemaroom.getTotal_rows() ||
           requestedSeat.getRow() < 1 ||
           requestedSeat.getColumn() < 1){
            return new ResponseEntity<>(Map.of("error","The number of a row or a column is out of bounds!" ),
                                        HttpStatus.BAD_REQUEST);
        }
        Seat seat = cinemaroom.getAvailable_seats(requestedSeat.getRow(), requestedSeat.getColumn());

        if(seat.isBooked()) {
            return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"),
                                            HttpStatus.BAD_REQUEST);
        }

        seat.setPrice(seat.getRow() <= 4? 10: 8);
        seat.book();
        seat.purchase();
        Ticket ticket = new Ticket(seat);
        cinemaroom.addTicket(ticket);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }
    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> requestBody) {
        String tokenString = requestBody.get("token");
        UUID token = UUID.fromString(tokenString);
        Ticket ticket = cinemaroom.findAndRemoveTicket(token);
        if(ticket == null){
            return new ResponseEntity<>(Map.of("error", "Wrong token!"),
                                       HttpStatus.BAD_REQUEST);
        }  else {
            ticket.unBook();
            return new ResponseEntity<>(Map.of("returned_ticket",ticket.getSeat()), HttpStatus.OK);
        }
    }
    @GetMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam(required = false) String password){
        if("super_secret".equals(password)){
            Map<String, Integer> response = new HashMap<>();
            int currentIncome = 0;
            int numberOfPurchasedTickets = 0;
            for(Ticket ticket : cinemaroom.getUnavailable_seats()){
                currentIncome += ticket.getSeat().getPrice();
                numberOfPurchasedTickets++;
            }
            int numberOfAvailableSeats = 0;
            for (Seat seat : cinemaroom.getAvailable_seats()){
                if(!seat.isBooked()) {
                    numberOfAvailableSeats++;
                }
            }
            response.put("current_income", currentIncome);
            response.put("number_of_available_seats", numberOfAvailableSeats);
            response.put("number_of_purchased_tickets", numberOfPurchasedTickets);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }
}
