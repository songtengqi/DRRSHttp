package httpDemo;

import javax.jws.WebService;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
@WebService
public interface DRRS {

    String bookRoom (String campus, int roomnum, String date, String timeSlots,String ID) throws RemoteException;

    String getAvailableTimeSlot(String date)throws RemoteException;

    String cancelBooking(String bookingID)throws RemoteException;
    String createRoom(int roomnum, String date, String campus, ArrayList<String> timeSlots) throws IOException;
    String  deleteRoom(int roonnum, String date, String campus, ArrayList<String> timeSlots) throws RemoteException;
    String changeReservation(String oldbookingID,String newCampuse, Integer newRoomnu, String newTimeSlot);

}
