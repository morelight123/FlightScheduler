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
import java.sql.Timestamp;
import java.sql.Date;
public class Book {
    private Date date;
    private String flight;
    private String name;
    private Timestamp timestamp;
    public Book(String name, Date date, String flight, Timestamp timestamp) {
        this.date = date;
	this.flight = flight;
	this.name = name;
	this.timestamp = timestamp;
	}
	public void setDate(Date date) {this.date = date;}
        
	public void setFlight(String flight) {this.flight = flight;}
        
	public void setName(String name) {this.name = name;}
        
	public void setTimeStamp(Timestamp timeStamp) {this.timestamp = timestamp;}
        
	public Date getDate() {return date;}
        
	public String getName() {return name;}
        
	public String getFlight() {return flight;}
        
	public Timestamp getTimeStamp() {return timestamp;}
}
