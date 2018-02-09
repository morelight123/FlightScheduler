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
public class DateList {
    private static final String URL="jdbc:derby://localhost:1527/FlightSchedulerDBKaixuanMeng_kbm5393";
    private static final String USER = "java";
    private static final String PASSWORD = "java";
    
    public static List<Date> getDateList() {
        List<Date> date = new ArrayList<Date>();
        Connection connection;
        PreparedStatement selectAll;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            selectAll = connection.prepareStatement("select * from java.DAY");
            resultSet = selectAll.executeQuery();
            while (resultSet.next()) {
                date.add(resultSet.getDate("date"));
            }
        }
        catch (SQLException sqlException) {
            System.out.print("exception catched;\n");
        }
        return date;
    }
    public static int findDate(Date thisDate){
        for(int i=0; i<DateList.getDateList().size();i++){
            if(DateList.getDateList().get(i)== thisDate){
                return i;
            }
        }
        return -1;
    }
    public static int addDate(Date date){
       int result=0;
       Connection connection;
       PreparedStatement insertNew;
       
       try{
       connection = DriverManager.getConnection(URL, USER, PASSWORD);
       insertNew=connection.prepareStatement("INSERT INTO JAVA.DAY"+"(DATE)"+"values(?)");
       insertNew.setDate(1,date);
       result=insertNew.executeUpdate();
       }catch(SQLException eqlexception){}
       return result;
       }
}
