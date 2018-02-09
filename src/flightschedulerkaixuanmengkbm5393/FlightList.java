package flightschedulerkaixuanmengkbm5393;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import java.util.ArrayList;
import java.util.List;

public class FlightList {
    private static final String URL="jdbc:derby://localhost:1527/FlightSchedulerDBKaixuanMeng_kbm5393";
    private static final String USER = "java";
    private static final String PASSWORD = "java";
    
    public static List<Flight> getFlightList() {
	//System.out.print(URL);
	Connection connection;
	PreparedStatement selectAll;
        ResultSet resultSet;
	List<Flight> fList = new ArrayList<Flight>();
	try {
            //System.out.print("aaa");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            selectAll = connection.prepareStatement("SELECT * FROM JAVA.FLIGHTS");			
            resultSet = selectAll.executeQuery();
            while (resultSet.next()) {
                fList.add(new Flight(resultSet.getString("Flights_number"), resultSet.getInt(2)));
            }
	}
	catch (SQLException sqlException) {}
	return fList;
	}
    public static Flight getFlight(int index) {
	return FlightList.getFlightList().get(index);
    }
    public static int find(String flight){
        for (int i=0;i<FlightList.getFlightList().size();i++){
            if(flight.equals(FlightList.getFlightList().get(i).getFlightNum()))
            {
                return i;}
        }
        return -1;
}
    public static void addFlight(String flight,int seats){
        Connection connection;
        PreparedStatement insertFlight;
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            insertFlight=connection.prepareStatement("INSERT INTO JAVA.FLIGHTS"+
                   "(FLIGHTS_NUMBER,NUM_OF_SEATS)"+"values(?,?)");
            insertFlight.setString(1,flight);
            insertFlight.setInt(2, seats);
            insertFlight.executeUpdate();  
        }
        catch(SQLException sqlException){}
    }
    public static boolean findFlight(String flight){
        Connection connection;
        PreparedStatement findFlight;
        ResultSet resultSet;
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            findFlight=connection.prepareStatement("SELECT * FROM JAVA.FLIGHTS "
                    + "WHERE FLIGHTS_NUMBER=?");
            findFlight.setString(1, flight);
            resultSet = findFlight.executeQuery(); 
            return resultSet.next();
        }
        catch (SQLException sqlException) {}
        return false;
            
        }
    public static int dropFlight(String flight){
        Connection connection;
        PreparedStatement dropFlight;
        try{
            connection=DriverManager.getConnection(URL, USER, PASSWORD);
            dropFlight=connection.prepareStatement("DELETE FROM JAVA.FLIGHTS "
                    + "WHERE FLIGHTS_NUMBER=?");
            dropFlight.setString(1, flight);
            int i = dropFlight.executeUpdate(); 
            return i;
        }
        catch (SQLException sqlException) {}
        return 0;
    }
}
