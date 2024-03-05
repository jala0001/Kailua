import javax.swing.plaf.PanelUI;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MySQLConnection {
    String database = "jdbc:mysql://localhost:3306/kailua_car_rental";
    String username = "root";
    String password = "password1";
    private Connection connection = null;

    public MySQLConnection() {
        createConnection();
    }

    private void createConnection() {
        if (connection != null)
            return; // If connection already created, just return that to ensure singleton

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(database, username, password);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("EXCEPTION: " + e.getStackTrace());
        }
        connection = null;
    }


    public void deleteCar(int car_id) {
        String query = "DELETE FROM car WHERE car_id = ?;";

        try {
            PreparedStatement pS = connection.prepareStatement(query);
            pS.setInt(1, car_id);
            pS.executeUpdate();

            System.out.println("Car deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteRenter(int renter_id){
        String query = "DELETE FROM Renter WHERE renter_id = ?;";

        try {
            PreparedStatement pS = connection.prepareStatement(query);
            pS.setInt(1, renter_id);
            pS.executeUpdate();

            System.out.println("Renter deleted successfully.");
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println("EXCEPTION: " + e.getStackTrace());
        }
    }

    public void addCar(Car car) {
        try {
            String query = "INSERT INTO car (brand, model, fuel_type, registration_number, registration_year_month, drivin_km) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getFuelType());
            preparedStatement.setString(4, car.getRegistrationNumber());
            preparedStatement.setDate(5, Date.valueOf(car.getRegistrationYearMonth()));
            preparedStatement.setInt(6, car.getDrivinKm());

            preparedStatement.executeUpdate();

            System.out.println("Car added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void specifyCar(Car carType, int car_id, int inputChoice){ // fjernet 'sout' herfra og sat den i createCar(); i Menu
        switch (inputChoice) {

            case 1 -> createLuxuryCar(carType, car_id);
            case 2 -> createFamilyCar(car_id);
            case 3 -> createSportsCar(car_id);
        }

    }

    private void createLuxuryCar(Car carType, int carId) {
        LuxuryCar luxuryCar = (LuxuryCar) carType;
        try {
            String query = "INSERT INTO Luxery (car_id, ccm, gear, aricondition, speedpilot, leatherseats) VALUES (?, ?, ?, ?, ?, ?)"; // A'RI'CONDITION SKAL RETTES I SQL. speed_pilot er rettet til speedpilot
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, carId);
            preparedStatement.setInt(2, luxuryCar.getCcm());
            preparedStatement.setString(3, luxuryCar.getGear().name());
            preparedStatement.setBoolean(4, luxuryCar.isAircondition());
            preparedStatement.setBoolean(5, luxuryCar.isSpeedPilot());
            preparedStatement.setBoolean(6, luxuryCar.isLeatherSeats());

            preparedStatement.executeUpdate();

            System.out.println("Luxury Car added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void createFamilyCar(int carId) {

    }
    private void createSportsCar(int carId) {
    }



    public void addRenter(Renter renter) {
        try {
            String query = "INSERT INTO renter (fullname, adress, zipcode, city, mobil, phone, e_mail, license_number, license_date_of_issue ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, renter.getFullName());
            preparedStatement.setString(2, renter.getAdress());
            preparedStatement.setInt(3, renter.getZipcode());
            preparedStatement.setString(4, renter.getCity());
            preparedStatement.setInt(5, renter.getMobile());
            preparedStatement.setInt(6, renter.getPhone());
            preparedStatement.setString(7, renter.getEmail());
            preparedStatement.setInt(8, renter.getLicenseNumber());
            preparedStatement.setDate(9, Date.valueOf(renter.getLicenseDateOfIssue()));

            preparedStatement.executeUpdate();

            System.out.println("Renter added successfully.");
        } catch (SQLException e) {
            System.out.println("EXCEPTION: " + e.getStackTrace());
        }
    }

    public int getLatestCarId() { // Ny metode som bruges i createCar(); i Menu så vi kan få det sidste Car_id.
        try {
            String query = "SELECT car_id FROM car ORDER BY car_id DESC LIMIT 1";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    return resultSet.getInt("car_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }


    public ArrayList<Car> getAllCars() {
        ArrayList<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM car;";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int carId = rs.getInt("car_id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                String fuelType = rs.getString("fuel_type");
                String registrationNumber = rs.getString("registration_number");
                LocalDate registrationYearMonth = rs.getDate("registration_year_month").toLocalDate();
                int drivinKm = rs.getInt("drivin_km");
                Car car = new Car (carId, brand, model, fuelType, registrationNumber, registrationYearMonth, drivinKm);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public ArrayList<Renter> getAllRenters() {
        ArrayList<Renter> renters = new ArrayList<>();
        String query = "SELECT * FROM renter;";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int rentId = rs.getInt("rent_id");
                String fullName = rs.getString("fullname");
                String adress = rs.getString("adress");
                int zipcode = rs.getInt("zipcode");
                String city = rs.getString("city");
                int mobile = rs.getInt("mobil");
                int phone = rs.getInt("phone");
                String email = rs.getString("email");
                int licenseNumber = rs.getInt("license_number");
                LocalDate licenseDateOfIssue = rs.getDate("license_date_of_issue").toLocalDate();
                Renter renter = new Renter(rentId, fullName, adress, zipcode, city, mobile, phone, email, licenseNumber, licenseDateOfIssue );
                renters.add(renter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return renters;
    }




}
