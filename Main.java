//as a good developer instead of importing the whole package you should import only those libraries i.e. required
import java.sql.*;
import java.util.Scanner;

public class Main
{
    private static final String url = "jdbc:mysql://localhost:3306/hotel_DB";
    private static final String username = "root";
    private static final String password = "**********";
    public static void main(String[] args)
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver"); // loading necessary drivers
            //Drivers loaded successfully
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url,username,password);
            Statement st = con.createStatement();
            Scanner in = new Scanner(System.in);

            System.out.println("HOTEL MANAGEMENT SYSTEM");
            System.out.println("1. Reserve a Room");
            System.out.println("2. View Reservations");
            System.out.println("3. Get Room Number");
            System.out.println("4. Update Reservations");
            System.out.println("5. Delete Reservations");
            System.out.println("0. Exit");
            while(true)
            {
                System.out.print("\nEnter your choice: ");
                int choice = in.nextInt();
                System.out.println();
                switch(choice)
                {
                    case 1:
                    {
                        reserveRoom(in,con,st);
                        break;
                    }
                    case 2:
                    {
                        viewReservations(in,con,st);
                        break;
                    }
                    case 3:
                    {
                        getRoomNo(in,con,st);
                        break;
                    }
                    case 4:
                    {
                        updateReservations(in,con,st);
                        break;
                    }
                    case 5:
                    {
                        deleteReservations(in,con,st);
                        break;
                    }
                    case 0:
                    {
                        in.close();
                        st.close();
                        con.close();
                        exit();
                        return;
                    }
                    default:
                        System.out.println("Wrong Choice entered :(");
                }
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        catch(InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void reserveRoom(Scanner in, Connection con, Statement st) throws SQLException
    {
        //reserving a room
        System.out.println("Enter the following information: ");
        System.out.print("ID: ");
        int id = in.nextInt();
        ResultSet res;
        res = st.executeQuery("select * from reservations where Reservation_ID="+id+";");
        if(res.next())
        {
            System.out.println("The person is already registered");
            res.close();
            return;
        }
        System.out.print("Room No: ");
        int roomNo = in.nextInt();
        in.nextLine();//since nextInt() does not consume /n and this is stored in buffer which is taken by next String
        System.out.print("Name: ");
        String name = in.nextLine();
        System.out.print("Mobile No: ");
        String mobNo = in.nextLine();
        int rowsAffected = st.executeUpdate("insert into Reservations(Reservation_ID,Room_No,Guest_Name,Mobile_Number,Reservation_Date) VALUES ("+id+","+roomNo+",'"+name+"','"+mobNo+"',CURRENT_TIMESTAMP)");
        if(rowsAffected != 0)
            System.out.println("New entry successfully registered :)");
        else
            System.out.println("Error :(");
        res.close();
    }
    public static void viewReservations(Scanner in, Connection con, Statement st) throws SQLException
    {
        ResultSet res = st.executeQuery("select * from Reservations");
        System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
        System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number       | Reservation Date        |");
        System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

        while(res.next())
        {
            int id = res.getInt("Reservation_ID");
            int roomNo = res.getInt("Room_No");
            String name = res.getString("Guest_Name");
            String mobNo = res.getString("Mobile_Number");
            Timestamp time = res.getTimestamp("Reservation_Date");
            System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",id, name, roomNo, mobNo, time);
        }
        System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
        res.close();
    }
    public static void getRoomNo(Scanner in, Connection con, Statement st) throws SQLException
    {
        //getting the room no on the basis of the name
        in.nextLine();
        System.out.print("Enter the Guest Name: ");
        String name = in.nextLine();
        ResultSet res = st.executeQuery("select Room_No from Reservations where Guest_Name = '"+name+"';");
        if(res.next())
            System.out.println(name+" is in Room No: "+res.getInt("Room_No"));
        else
            System.out.println("There is no person registered with the given name '"+name+"'");
        res.close();
    }
    public static void updateReservations(Scanner in, Connection con, Statement st) throws SQLException
    {
        //updating the details of the Guest on the basis of the Reservation ID
        System.out.print("Enter the Reservation ID you wish to update: ");
        int id = in.nextInt();
        ResultSet res = st.executeQuery("select * from Reservations where Reservation_ID = "+id+";");
        if(!res.next())
            System.out.println("There is no person registered with the given ID: "+id);
        else
        {
            System.out.println("Enter the following information: ");
            in.nextLine();
            System.out.print("Name: ");
            String name = in.nextLine();
            System.out.print("Mobile No: ");
            String mobNo = in.nextLine();
            int rowsAffected = st.executeUpdate("update Reservations SET Guest_Name='"+name+"', Mobile_Number='"+mobNo+"', Reservation_Date = NOW() WHERE Reservation_ID="+id+";");
            if(rowsAffected != 0)
                System.out.println("Data updated successfully :)");
            else
                System.out.println("Error :(");
        }
        res.close();
    }
    public static void deleteReservations(Scanner in, Connection con, Statement st) throws SQLException
    {
        //deleting the details of the Guest on the basis of the Reservation ID
        System.out.print("Enter the Reservation ID you wish to delete: ");
        int id = in.nextInt();
        ResultSet res = st.executeQuery("select * from Reservations where Reservation_ID = "+id+";");
        if(!res.next())
            System.out.println("There is no person registered with the given ID: "+id);
        else
        {
            int rowsAffected = st.executeUpdate("delete from Reservations WHERE Reservation_ID="+id+";");
            if(rowsAffected != 0)
                System.out.println("Entry Deleted Successfully :)");
            else
                System.out.println("Error :(");
        }
        res.close();
    }
    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Hotel Reservation System!!!");
    }
}
