package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
public class Seat {

    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean isBooked;
    @JsonIgnore
    private boolean isPurchased;
    @JsonIgnore
    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        this.isBooked = true;
    }

    public void unBook(){this.isBooked = false; }

    public void purchase() {
        this.isPurchased = true;
    }

    public Seat() {
    }


    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = (row <= 4? 10 : 8);
        this.isBooked = false;
        this.isPurchased = false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
