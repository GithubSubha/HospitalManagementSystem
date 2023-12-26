package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection, Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }
    public void addPatients(){
        String name="",gender="";
       int age=0;
        System.out.print("Enter Patient Name: "+name);
        name=scanner.next();
        System.out.print("Enter Patient age :"+age);
        age=scanner.nextInt();
        System.out.print("Enter Patient gender :"+gender);
        gender=scanner.next();
        

        try{
          String query="INSERT INTO patients(name,age,gender) VALUES (?,?,?)";
          PreparedStatement preparedStatement=connection.prepareStatement(query);
          preparedStatement.setString(1,name);
          preparedStatement.setInt(2,age);
          preparedStatement.setString(3,gender);
          int affectedRows=preparedStatement.executeUpdate();
          if(affectedRows>0){
            System.out.println("Patient added successfully!!!");

          }
          else{
            System.out.println("failed to add patient");
          }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatients(){
        String query="SELECT * FROM patients";
        try{
           PreparedStatement preparedStatement=connection.prepareStatement(query);
           ResultSet resultSet=preparedStatement.executeQuery();
           System.out.println("PATIENTS: ");
           System.out.println("+----------+------------------------+-----------+--------------+");
           System.out.println("|Patient ID|          Name          |    Age    |    Gender    |");
           System.out.println("+----------+------------------------+-----------+--------------+");
                                                                                                
           while(resultSet.next()){
            int id=resultSet.getInt("id");
            String name=resultSet.getString("name");
            int age=resultSet.getInt("age");
            String gen=resultSet.getString("gender");
            System.out.printf("|%-10s|%-24s|%-11s|%-14s|\n",id,name,age,gen);
            System.out.println("+----------+------------------------+-----------+--------------+");
           }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id=?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
