package wtf.ewsc;

public class Booking {
    public String booking_from;
    public String booking_date;
    public String approved;
    public String booking_time;
    public int booking_amount;

    public int discount_percent;

    public int id;

    public int getDiscount_percent() {
        return discount_percent;
    }

    public int getId() {
        return id;
    }

    public int getBooking_amount() {
        return booking_amount;
    }

    public String getApproved() {
        return approved;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getBooking_from() {
        return booking_from;
    }

    public String getBooking_time() {
        return booking_time;
    }
}
