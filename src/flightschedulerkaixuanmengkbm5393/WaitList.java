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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WaitList {
    private static final String URL="jdbc:derby://localhost:1527/FlightSchedulerDBKaixuanMeng_kbm5393";
    private static final String USER = "java";
    private static final String PASSWORD = "java";
    public static List<Book> getWaitList() {
        Connection connection;
        PreparedStatement selectAll;
        List<Book> waitlist=new ArrayList<Book>();
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            selectAll=connection.prepareStatement("SELECT * FROM JAVA.WAIT_LIST");
            ResultSet resultSet=selectAll.executeQuery();
            while (resultSet.next()) {
                waitlist.add(new Book(resultSet.getString("Customer"), 
                        resultSet.getDate("Date"), 
                        resultSet.getString("Flight"),
			resultSet.getTimestamp("TimeStamp")));
            }
        }
        catch (SQLException sqlException) {}
        return waitlist;
    }
    
    public static void addWaitList(String name, Date date, String flight) {
	Connection connection;
	PreparedStatement insertNew;
	Timestamp currentTimeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
	try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            insertNew = connection.prepareStatement("INSERT INTO JAVA.WAIT_LIST " +
                "(Flight,Date,Customer,Timestamp) " + "values (?,?,?,?)");
            insertNew.setString(1, flight);			
            insertNew.setDate(2, date);
            insertNew.setString(3, name);
            insertNew.setTimestamp(4, currentTimeStamp);
            insertNew.executeUpdate();
        }
	catch (SQLException sqlException) {}
	}
    public static List<Book>  findByName(String CustomerName) {
        Connection connection;
        PreparedStatement selectCustomerByName;
        List<Book> results=null;
        ResultSet resultSet=null;
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            selectCustomerByName=connection.prepareStatement(
                    "SELECT * FROM JAVA.WAIT_LIST WHERE CUSTOMER=?");
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
        //List<Book> waitList = WaitList.getWaitList();
        //for (int i = 0; i < waitList.size(); i++) {
            //if (waitList.get(i).getName().equals(name)) {
            //return i;
            //}
        //}
        //return -1;
    
    
    public static List<Book>  findByFlightDate(Date DateBooked,String flightBooked) {
        Connection connection;
        PreparedStatement byFlightDate;
        List<Book> results=null;
        ResultSet resultSet=null;
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            byFlightDate=connection.prepareStatement(
                    "SELECT * FROM JAVA.WAIT_LIST WHERE"
                            +" DATE=?"
                            +" AND FLIGHT=?");
            results=new ArrayList<Book>();
            byFlightDate.setDate(1, DateBooked);
            byFlightDate.setString(2, flightBooked.toString());
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
    public static Book getWaitCustomer(int index){
        return WaitList.getWaitList().get(index);
    }
    public static Book updatetoBook(Book book) {
        Book newbook=null;
        Date date=book.getDate();
        ResultSet resultset;
        String flight=book.getFlight();
        Connection connection;
        PreparedStatement updateBook;
        PreparedStatement getnewbook;
        try {
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            getnewbook= connection.prepareStatement("SELECT * FROM JAVA.WAIT_LIST WHERE DATE=? AND FLIGHT=?");
            updateBook=connection.prepareStatement("DELETE FROM JAVA.WAIT_LIST WHERE DATE=? AND FLIGHT=? AND CUSTOMER=?");
            getnewbook.setDate(1, date);
            getnewbook.setString(2, flight);
            updateBook.setDate(1, date);
            updateBook.setString(2, flight);
            resultset=getnewbook.executeQuery();
            
            if (resultset.next()) {
                newbook=new Book(resultset.getString("Customer"),
                resultset.getDate("Date"), resultset.getString("Flight"),
                resultset.getTimestamp("TimeStamp"));
            }
            if (newbook!=null) {
                updateBook.setString(3, newbook.getName());
                int i=updateBook.executeUpdate();
            }
            return newbook;}
        catch(SQLException sqlexception){}
        System.out.print("Fail");
        return newbook;
    }
    public static int dropbyFlight(String flight){
        Connection connection;
        PreparedStatement dropflight;
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            dropflight= connection.prepareStatement("DELETE FROM JAVA.WAIT_LIST WHERE FLIGHT=?");
            dropflight.setString(1, flight);
            int i=dropflight.executeUpdate();
            return i;}
        catch (SQLException sqlException) {}
        System.out.print("WaitList dropbyFlight fail");
        return 0;
    }
    public static List<Book> findbyFlight(String flight) {
        Connection connection;
        PreparedStatement selectBook;
        ResultSet resultset;
        List<Book> booklist = new ArrayList<>();;
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            selectBook=connection.prepareStatement("SELECT * FROM JAVA.WAIT_LIST "
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
        System.out.print("WaitList findbyFlight fail");
        return booklist;
    }
}
