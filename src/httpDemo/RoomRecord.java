package httpDemo;

public class RoomRecord {

    public String getRecordID() {
        return RecordID;
    }

    public void setRecordID(String recordID) {
        RecordID = recordID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRoomnum() {
        return roomnum;
    }

    public void setRoomnum(int roomnum) {
        this.roomnum = roomnum;
    }

    public String getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(String timeslots) {
        this.timeslots = timeslots;
    }

    String RecordID;
    String ID;
    String campus;
    String date;
    int roomnum;
    String timeslots;

    public RoomRecord(String date, int roomnum,  String ID, String campus,String RecordId,String timeslots) {
        this.date = date;
        this.roomnum = roomnum;
        this.ID = ID;
        this.campus = campus;
        this.RecordID = RecordId;
        this.timeslots = timeslots;
    }

    @Override
    public String toString() {
        return "RoomRecord{" +
                "RecordID='" + RecordID + '\'' +
                ", ID='" + ID + '\'' +
                ", campus='" + campus + '\'' +
                ", date='" + date + '\'' +
                ", roomnum=" + roomnum +
                ", timeslots='" + timeslots + '\'' +
                '}';
    }
}