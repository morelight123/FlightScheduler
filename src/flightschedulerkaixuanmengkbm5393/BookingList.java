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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class BookingList {
    private static final String URL="jdbc:derby://localhost:1527/FlightSchedulerDBKaixuanMeng_kbm5393";
    private static final String USER = "java";
    private static final String PASSWORD = "java";
    
    //private Connection connection;
    //private PreparedStatement selectAll;
    
    public BookingList() {
        //try {
        //    connection=DriverManager.getConnection(URL, USER, PASSWORD);
        //    selectAll=connection.prepareStatement("SELECT * FROM JAVA.BOOKING_LIST");
        //}
        //catch (SQLException sqlException) {}
    }
    public static List<Book> getBookList() {
        Connection connection;
        PreparedStatement selectAll;
        List<Book> booklist=new ArrayList<>();
        
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            selectAll=connection.prepareStatement("SELECT * FROM JAVA.BOOKING_LIST");
            ResultSet resultSet=selectAll.executeQuery();
            while (resultSet.next()) {
                booklist.add(new Book(resultSet.getString("Customer"), 
                        resultSet.getDate("Date"), 
                        resultSet.getString("Flight"),
			resultSet.getTimestamp("TimeStamp")));
            }
        }
        catch (SQLException sqlException) {}
        return booklist;
    }
    public static int getEmptySeats(Date date, String flight) {
        return FlightList.getFlight(FlightList.find(flight)).getSeats()-BookingList.getBookedSeats(date,flight);
    }
    public static List<Book>  findByName(String CustomerName) {
        Connection connection;
        PreparedStatement selectCustomerByName;
        List<Book> results=null;
        ResultSet resultSet=null;
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            selectCustomerByName=connection.prepareStatement(
                    "SELECT * FROM JAVA.BOOKING_LIST WHERE CUSTOMER=?");
            results=new ArrayList<Book>();
            selectCustomerByName.setString(1, CustomerName);
            resultSet = selectCustomerByName.executeQuery(); 
            while (resultSet.next()) {
                results.add(new Book(resultSet.getString("Customer"),
                resultSet.getDate("Date"), resultSet.getString("Flight"),
                resultSet.getTimestamp("TimeStamp")));
            }
        }
        catch (SQLException sqlException) {}
        return results;
    }
        //List<Book>booklist=BookingList.getBookList();
        //System.out.print(booklist);
        //for (int i = 0; i < booklist.size(); i++){
        //if(booklist.get(i).getName().equals(CustomerName)) {return i;}
        //}
        //return -1;
        //}
    
    public static List<Book>  findByFlightDate(Date DateBooked,String flightBooked) {
        Connection connection;
        PreparedStatement byFlightDate;
        List<Book> results=null;
        ResultSet resultSet=null;
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            byFlightDate=connection.prepareStatement(
                    "SELECT * FROM JAVA.BOOKING_LIST WHERE"
                            +" DATE=?"
                            +" AND FLIGHT=?");
            results=new ArrayList<Book>();
            byFlightDate.setDate(1, DateBooked);
            byFlightDate.setString(2, flightBooked);
            resultSet = byFlightDate.executeQuery(); 
            while (resultSet.next()) {
                results.add(new Book(resultSet.getString("Customer"),
                resultSet.getDate("Date"), resultSet.getString("Flight"),
                resultSet.getTimestamp("TimeStamp")));
            }
        }
        catch (SQLException sqlException) {}
        return results;
        }
    public static void newBook(String name, Date date, String flight) {
        Connection connection;
        PreparedStatement insertNew;
        Timestamp currentTimeStamp=new Timestamp(Calendar.getInstance().getTime().getTime());
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            insertNew= connection.prepareStatement("INSERT INTO JAVA.BOOKING_LIST"+"(Customer,Date,Flight,Timestamp)"+"VALUES(?,?,?,?)");
            insertNew.setString(1, name);
            insertNew.setDate(2,date);
            insertNew.setString(3,flight);
            insertNew.setTimestamp(4,currentTimeStamp);
            insertNew.executeUpdate();

        }
        catch(SQLException sqlexception){}
    }
    public static Book deleteBook(String name, Date date){ 
        ResultSet resultset;
        Book result=null;
        Connection connection;
        PreparedStatement deletebook;
        PreparedStatement getdeletebook;
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            getdeletebook= connection.prepareStatement("SELECT * FROM JAVA.BOOKING_LIST WHERE CUSTOMER=? AND DATE=?");
            deletebook= connection.prepareStatement("DELETE FROM JAVA.BOOKING_LIST WHERE CUSTOMER=? AND DATE=?");
            getdeletebook.setString(1, name);
            getdeletebook.setDate(2,date);
            deletebook.setString(1, name);
            deletebook.setDate(2,date);
            resultset=getdeletebook.executeQuery();
            while (resultset.next()) {
                result=new Book(resultset.getString("Customer"),
                resultset.getDate("Date"), resultset.getString("Flight"),
                resultset.getTimestamp("TimeStamp"));
            }
            if (result!=null) {
                int i = deletebook.executeUpdate();}
            return result;
        }
        catch(SQLException sqlexception){}
        return result;
    }
    public static int getBookedSeats(Date date, String flight) {
	int seats = 0;			
        PreparedStatement getFlightSeats;
        Connection connection;
	try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);			
            getFlightSeats = connection.prepareStatement("SELECT COUNT(Flight) FROM JAVA.BOOKING_LIST WHERE Flight = ? AND Date = ?");
            getFlightSeats.setDate(2, date);
            getFlightSeats.setString(1, flight);
            ResultSet resultSet = getFlightSeats.executeQuery();
            resultSet.next();
            seats = resultSet.getInt(1);
            }
	catch (SQLException sqlException) {}
	return seats;
    }
    public static Book getBookingInfo(int i){
        return BookingList.getBookList().get(i);
        };
    public static List<Book> findbyFlight(String flight) {
        Connection connection;
        PreparedStatement selectBook;
        ResultSet resultset;
        List<Book> booklist =new ArrayList<>();
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            selectBook=connection.prepareStatement("SELECT * FROM JAVA.BOOKING_LIST "
                    + "WHERE FLIGHT=?");
            selectBook.setString(1, flight);
            resultset = selectBook.executeQuery(); 
            while (resultset.next()) {
                booklist.add(new Book(resultset.getString("Customer"),
                resultset.getDate("Date"), resultset.getString("Flight"),
                resultset.getTimestamp("TimeStamp")));
            }
            return booklist;
        }
        catch (SQLException sqlException) {}
        System.out.print("BookingList FindbyFlight fail");
        return booklist;
    }
    public static int dropbyFlight(String flight) {
        Connection connection;
        PreparedStatement dropflight;
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            dropflight= connection.prepareStatement("DELETE FROM JAVA.BOOKING_LIST WHERE FLIGHT=?");
            dropflight.setString(1, flight);
            int i=dropflight.executeUpdate();
            return i;}
        catch (SQLException sqlException) {}
        System.out.print("BookingList DropbyFlight fail");
        return 0;
    }
}