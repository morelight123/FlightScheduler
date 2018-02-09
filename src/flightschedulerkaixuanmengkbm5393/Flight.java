/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerkaixuanmengkbm5393;

/**
 *
 * @author paulm
 */
public class Flight {
    private int seats;
    private String FlightNum;
    public Flight(String FlightNum, int seats) {
        this.FlightNum=FlightNum;
        this.seats=seats;
    }
    public String getFlightNum() {
        return FlightNum;
    }
    public int getSeats() {
        return seats;
    }
}
