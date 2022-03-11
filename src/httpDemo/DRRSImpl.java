package httpDemo;

import javax.jws.WebService;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;

@WebService(endpointInterface = "httpDemo.DRRS")
public class DRRSImpl implements DRRS {



        HashMap<String,ArrayList<HashMap<Integer,ArrayList<String>>>> DVL = new HashMap<>();
        HashMap<String,ArrayList<HashMap<Integer,ArrayList<String>>>> KKL = new HashMap<>();
        HashMap<String,ArrayList<HashMap<Integer,ArrayList<String>>>> WST = new HashMap<>();//room record
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String d = dateFormat.format(calendar.getTime());
        //RoomRecord[] roomRecord = new RoomRecord[10];
        HashMap<Integer,RoomRecord> roomRecord = new HashMap<>();
        int RR = 10000;

        public HashMap<String, ArrayList<HashMap<Integer, ArrayList<String>>>> getKKL() {
        return KKL;
    }

        public void setKKL(HashMap<String, ArrayList<HashMap<Integer, ArrayList<String>>>> KKL) {
        this.KKL = KKL;
    }

        public HashMap<String, ArrayList<HashMap<Integer, ArrayList<String>>>> getWST() {
        return WST;
    }

        public void setWST(HashMap<String, ArrayList<HashMap<Integer, ArrayList<String>>>> WST) {
        this.WST = WST;
    }


        public HashMap<String, ArrayList<HashMap<Integer, ArrayList<String>>>> getDVL() {
        return DVL;
    }

        public void setDVL(HashMap<String, ArrayList<HashMap<Integer, ArrayList<String>>>> DVL) {
        this.DVL = DVL;
    }



    public DRRSImpl() throws IOException {

    }


        @Override
        public String bookRoom(String campus, int roomnum, String date, String timeSlots,String ID) {
        HashMap<String, Integer> record = new HashMap<>();
        for (Map.Entry<Integer,RoomRecord> entry : roomRecord.entrySet()){
            if (record.containsKey(entry.getValue().ID)){
                int v = record.get(entry.getValue().ID);
                record.put(entry.getValue().ID,v+1);
            }else {
                record.put(entry.getValue().ID,1);
            }
        }
        for (Map.Entry<String, Integer> entry: record.entrySet()){
            if (entry.getValue()>=3){
                System.out.println("sorry you already have 3 record of reservation, you cannot book anymore  "+"   "+this.d);

                return "sorry you already have 3 record of reservation, you cannot book anymore";
            }
        }
        if (campus.equalsIgnoreCase("DVL")) {
            if (DVL.containsKey(date)){                                                             //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = DVL.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();

                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){
                        ArrayList<String> strings = hashMap.get(roomnum);
                        ArrayList<String> stringscopy = strings;
                        if (strings.contains(timeSlots)){
                            RoomRecord r = new RoomRecord(date,roomnum,ID,campus,"RR"+RR,timeSlots);
                            roomRecord.put(RR,r);
                            RR++;

                            strings.remove(timeSlots);
                            HashMap<Integer,ArrayList<String>> s = new HashMap<>();
                            s.put(roomnum,strings);
                            HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                            scopy.put(roomnum,stringscopy);
                            DVL.get(date).remove(scopy);                  //remove the old record
                            DVL.get(date).add(s);                         //renew the timeslots
                            System.out.println("room record: "+roomRecord.get(RR-1).toString()+"   "+this.d);
                            return "book successfully"+"\n"+
                                    roomRecord.get(RR-1).toString();
                        }
                    }
                }
                System.out.println("sorry, the room or time slot is unavailable on DVL campus"+"   "+this.d);
                return "sorry, the room or time slot is unavailable on DVL campus";//if the room in the specific date do not exist, build it
            }else {
                System.out.println("sorry, no available room for the date on DVL campus"+"   "+this.d);//if the date is not in the map
                return "sorry, no available room for the date on DVL campus";
            }


        }else if (campus.equalsIgnoreCase("KKL")){
            if (KKL.containsKey(date)){                                                             //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = KKL.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();

                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){
                        ArrayList<String> strings = hashMap.get(roomnum);
                        ArrayList<String> stringscopy = strings;
                        if (strings.contains(timeSlots)){

                            roomRecord.put(RR,new RoomRecord(date,roomnum,ID,campus,"RR"+RR,timeSlots));
                            RR++;
                            strings.remove(timeSlots);
                            HashMap<Integer,ArrayList<String>> s = new HashMap<>();
                            s.put(roomnum,strings);
                            HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                            scopy.put(roomnum,stringscopy);
                            KKL.get(date).remove(scopy);                  //remove the old record
                            KKL.get(date).add(s);                         //renew the timeslots
                            System.out.println(roomRecord.get(RR-1).toString()+"   "+this.d);
                            return "book successfully"+"\n" +
                                    roomRecord.get(RR-1).toString();

                        }
                    }
                }
                System.out.println("sorry, the room or time slot is unavailable on the KKL campus"+"   "+this.d);
                return "sorry, the room or time slot is unavailable on the KKL campus";//if the room in the specific date do not exist, build it
            }else {
                System.out.println("sorry, no available room for the date on KKL campus"+"   "+this.d);//if the date is not in the map
                return "sorry, no available room for the date on KKL campus";
            }


        }else if (campus.equalsIgnoreCase("WST")){
            if (WST.containsKey(date)){                                                             //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = WST.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();
                boolean flag = false;
                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){
                        ArrayList<String> strings = hashMap.get(roomnum);
                        ArrayList<String> stringscopy = strings;
                        if (strings.contains(timeSlots)){

                            roomRecord.put(RR,new RoomRecord(date,roomnum,ID,campus,"RR"+RR,timeSlots));
                            RR++;
                            flag = true;
                            strings.remove(timeSlots);
                            HashMap<Integer,ArrayList<String>> s = new HashMap<>();
                            s.put(roomnum,strings);
                            HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                            scopy.put(roomnum,stringscopy);
                            WST.get(date).remove(scopy);                  //remove the old record
                            WST.get(date).add(s);                         //renew the timeslots
                            System.out.println(roomRecord.get(RR-1).toString()+"   "+this.d);
                            return "book successfully"+"\n" +
                                    roomRecord.get(RR-1).toString();
                        }
                    }
                }
                System.out.println("sorry, the room or time slot is unavailable on the KKL campus"+"   "+this.d);
                return "sorry, the room or time slot is unavailable on the KKL campus";//if the room in the specific date do not exist, build it
            }else {
                System.out.println("sorry, no available room for the date on KKL campus"+"   "+this.d);//if the date is not in the map
                return "sorry, no available room for the date on KKL campus";
            }


        }else {
            System.out.println("not a valid campus"+"   "+this.d);
            return "not a valid campus";

        }
    }


        @Override
        public String getAvailableTimeSlot(String date) {
        String s = "";
        int dvlcount = 0;
        int kklcount = 0;
        int wstcount = 0;

        {
            String[] Dvl = new String[DVL.size()];
            int i = 0;
            for (Map.Entry<String, ArrayList<HashMap<Integer, ArrayList<String>>>> entry : DVL.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(date)) {
                    Dvl[i] = "DVL campus: Date:" + entry.getKey();
                    ArrayList<HashMap<Integer, ArrayList<String>>> arrayList = entry.getValue();
                    Iterator<HashMap<Integer, ArrayList<String>>> iterator = arrayList.iterator();

                    while (iterator.hasNext()) {
                        HashMap<Integer, ArrayList<String>> hashMap = iterator.next();
                        for (Map.Entry<Integer, ArrayList<String>> entry1 : hashMap.entrySet()) {
                            Dvl[i] += "   ROOM NUMBER:" + entry1.getKey();
                            Dvl[i] += "   TIME PLOTS" + entry1.getValue();
                            dvlcount++;
                        }

                    }
                    i++;
                }
            }
            for (int j = 0; j < Dvl.length; j++) {
                s += Dvl[j] + "\n";
            }
        }
        {
            String[] Kkl = new String[KKL.size()];
            int i = 0;
            for (Map.Entry<String, ArrayList<HashMap<Integer, ArrayList<String>>>> entry : KKL.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(date)) {
                    Kkl[i] = "KKL campus: Date:" + entry.getKey();
                    ArrayList<HashMap<Integer, ArrayList<String>>> arrayList = entry.getValue();
                    Iterator<HashMap<Integer, ArrayList<String>>> iterator = arrayList.iterator();

                    while (iterator.hasNext()) {
                        HashMap<Integer, ArrayList<String>> hashMap = iterator.next();
                        for (Map.Entry<Integer, ArrayList<String>> entry1 : hashMap.entrySet()) {
                            Kkl[i] += "   ROOM NUMBER:" + entry1.getKey();
                            Kkl[i] += "   TIME PLOTS" + entry1.getValue();
                            kklcount++;
                        }

                    }
                    i++;
                }
            }
            for (int j = 0; j < Kkl.length; j++) {
                s += Kkl[j] + "\n";
            }
        }

        {
            String[] Wst = new String[WST.size()];
            int i = 0;
            for (Map.Entry<String, ArrayList<HashMap<Integer, ArrayList<String>>>> entry : WST.entrySet()) {
                if (entry.getKey().equalsIgnoreCase(date)) {
                    Wst[i] = "WST campus: Date:" + entry.getKey();
                    ArrayList<HashMap<Integer, ArrayList<String>>> arrayList = entry.getValue();
                    Iterator<HashMap<Integer, ArrayList<String>>> iterator = arrayList.iterator();

                    while (iterator.hasNext()) {
                        HashMap<Integer, ArrayList<String>> hashMap = iterator.next();
                        for (Map.Entry<Integer, ArrayList<String>> entry1 : hashMap.entrySet()) {
                            Wst[i] += "   ROOM NUMBER:" + entry1.getKey();
                            Wst[i] += "   TIME PLOTS" + entry1.getValue();
                            wstcount++;
                        }

                    }
                    i++;
                }
            }
            for (int j = 0; j < Wst.length; j++) {
                s += Wst[j] + "\n";
            }

        }


        System.out.println(s+"   "+this.d);

        s+="DVL: "+dvlcount+" records."+"\n";
        s+="KKL: "+kklcount+" records."+"\n";
        s+="WST: "+wstcount+" records."+"\n";
        return s;
    }


        @Override
        public String cancelBooking(String bookingID) {
        int in = Integer.valueOf(bookingID.substring(2));
        for (Map.Entry<Integer, RoomRecord> entry: roomRecord.entrySet()){
            if (entry.getKey()==in){
                roomRecord.remove(entry.getKey());
                System.out.println("The reservation is moved:" + bookingID+"   "+this.d);
                return "The reservation is moved:" + bookingID;
            }
        }
        System.out.println("Booing ID not found"+"   "+this.d);
        return "Booing ID not found";



    }

        @Override
        public String createRoom(int roomnum, String date, String campus,ArrayList<String> timeSlots) throws IOException {

        if (campus.equalsIgnoreCase("DVL")){                                          //room fot thr DVL campus
            if (DVL.containsKey(date)){                                                             //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = DVL.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();

                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){
//                        Iterator<String> i = hashMap.get(roomnum).iterator();
//                        while (i.hasNext()){
//                            timeSlots.add(i.next());
//                        }
//                        hashMap.put(roomnum,timeSlots);
//                        DVL.get(date).add(hashMap);
//                        System.out.println(getDVL());
                        System.out.println("The room was built before    "+this.d);
                        return "The room was built before";                                                // if the room in the specific date exists
                    }
                }
                HashMap<Integer,ArrayList<String>> sub = new HashMap<>();
                sub.put(roomnum,timeSlots);
                arrayListHashMap.add(sub);
                DVL.put(date,arrayListHashMap);
                System.out.println(getDVL()+"   "+this.d);
                return "the room is built(the date exist)";                                                         //if the room in the specific date do not exist, build it


            }else {                                                                                   //if the date is not in the map
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayList = new ArrayList<>();
                HashMap<Integer,ArrayList<String>> sub = new HashMap<>();
                sub.put(roomnum,timeSlots);
                arrayList.add(sub);
                DVL.put(date,arrayList);
                System.out.println(getDVL()+"   "+this.d);
                return "the room is built(the date does not exist):"+getDVL();


            }
        }else if (campus.equalsIgnoreCase("KKL")){
            if (KKL.containsKey(date)){                                                             //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = KKL.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();

                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){
//                        Iterator<String> i = hashMap.get(roomnum).iterator();
//                        while (i.hasNext()){
//                            timeSlots.add(i.next());
//                        }
//                        hashMap.put(roomnum,timeSlots);
//                        KKL.get(date).add(hashMap);
//                        System.out.println(getKKL());
                        System.out.println("The room was built before   "+this.d);
                        return "The room was built before";                                                // if the room in the specific date exists
                    }
                }
                HashMap<Integer,ArrayList<String>> sub = new HashMap<>();
                sub.put(roomnum,timeSlots);
                arrayListHashMap.add(sub);
                KKL.put(date,arrayListHashMap);
                System.out.println("the room is built(the date exist)"+getKKL()+"   "+this.d);
                return "the room is built(the date exist)";                                                         //if the room in the specific date do not exist, build it


            }else {                                                                                   //if the date is not in the map
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayList = new ArrayList<>();
                HashMap<Integer,ArrayList<String>> sub = new HashMap<>();
                sub.put(roomnum,timeSlots);
                arrayList.add(sub);
                KKL.put(date,arrayList);
                System.out.println("the room is built(the date does not exist):"+getKKL()+"   "+this.d);
                return "the room is built(the date does not exist):"+getKKL();

            }

        }else {
            if (WST.containsKey(date)){                                                             //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = WST.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();

                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){
//                        Iterator<String> i = hashMap.get(roomnum).iterator();
//                        while (i.hasNext()){
//                            timeSlots.add(i.next());
//                        }
//                        hashMap.put(roomnum,timeSlots);
//                        WST.get(date).add(hashMap);
//                        System.out.println(getWST());
                        System.out.println("The room was built before"+"   "+this.d);
                        return "The room was built before";                                                // if the room in the specific date exists
                    }
                }
                HashMap<Integer,ArrayList<String>> sub = new HashMap<>();
                sub.put(roomnum,timeSlots);
                arrayListHashMap.add(sub);
                WST.put(date,arrayListHashMap);
                System.out.println("the room is built(the date exist)"+getWST()+"   "+this.d);
                return "the room is built(the date exist)";                                                         //if the room in the specific date do not exist, build it


            }else {                                                                                   //if the date is not in the map
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayList = new ArrayList<>();
                HashMap<Integer,ArrayList<String>> sub = new HashMap<>();
                sub.put(roomnum,timeSlots);
                arrayList.add(sub);
                WST.put(date,arrayList);
                System.out.println("the room is built(the date does not exist):"+getWST()+"   "+this.d);
                return "the room is built(the date does not exist):"+getWST();

            }

        }



    }

        @Override
        public String deleteRoom(int roomnum, String date, String campus,ArrayList<String> timeSlots) {
        String[] s = new String[timeSlots.size()];
        int j =0;
        Iterator<String> i = timeSlots.iterator();
        while (i.hasNext()){
            s[j] = i.next();
            j++;                                                                                    //put list of timeslots on array
        }
        if (campus.equalsIgnoreCase("DVL")){
            if (DVL.containsKey(date)){                                                                 //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = DVL.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();
                ArrayList<String> booked = new ArrayList<>();
                boolean flag = false;
                boolean bookedflag = false;
                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){                                              //if the room exists
                        ArrayList<String> strings = hashMap.get(roomnum);
                        ArrayList<String> stringscopy = strings;

                        for (int k = 0; k<s.length;k++){                                            //check time slots onr by onr
                            if (strings.contains(s[k])){

                                strings.remove(s[k]);
                                HashMap<Integer,ArrayList<String>> h = new HashMap<>();
                                h.put(roomnum,strings);
                                HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                                scopy.put(roomnum,stringscopy);
                                DVL.get(date).remove(scopy);                  //remove the target timeslots
                                DVL.get(date).add(h);                         //renew the timeslots
                                flag=true;

                            }
                            for (Map.Entry<Integer,RoomRecord> entry:roomRecord.entrySet()){
                                if (entry.getValue().timeslots.equalsIgnoreCase(s[k])){
                                    roomRecord.remove(entry.getKey());
                                    booked.add("RR"+entry.getKey());
                                    bookedflag = true;
                                }
                            }
                        }
                    }
                }
                if (flag==true || bookedflag == true){
                    if (flag==true&&bookedflag == true){
                        System.out.println(campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"\n"
                                + "the Created reservation is deleted: "+booked+"    "+this.d);
                        return campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"\n"
                                + "the Created reservation is deleted: "+booked;
                    }else if (flag==true){
                        System.out.println(campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"    "+this.d);
                        return campus+" campus "+ roomnum+" on "+timeSlots+" was deleted";
                    }else {
                        System.out.println("the Created reservation is deleted: "+booked+"    "+this.d);
                        return "the Created reservation is deleted: "+booked;

                    }
                }else {
                    System.out.println("sorry the room number is unavailable"+"    "+this.d);
                    return "sorry the room number is unavailable";
                }
            }else {
                System.out.println("the input date is unavailable"+"    "+this.d);//if the date is not in the map
                return "the input date is unavailable";
            }
        }else if (campus.equalsIgnoreCase("KKL")){

            if (KKL.containsKey(date)){                                                                 //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = KKL.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();
                ArrayList<String> booked = new ArrayList<>();
                boolean flag = false;
                boolean bookedflag = false;
                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){                                              //if the room exists
                        ArrayList<String> strings = hashMap.get(roomnum);
                        ArrayList<String> stringscopy = strings;

                        for (int k = 0; k<s.length;k++){                                            //check time slots onr by onr
                            if (strings.contains(s[k])){

                                strings.remove(s[k]);
                                HashMap<Integer,ArrayList<String>> h = new HashMap<>();
                                h.put(roomnum,strings);
                                HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                                scopy.put(roomnum,stringscopy);
                                KKL.get(date).remove(scopy);                  //remove the target timeslots
                                KKL.get(date).add(h);                         //renew the timeslots
                                flag=true;

                            }
                            for (Map.Entry<Integer,RoomRecord> entry:roomRecord.entrySet()){
                                if (entry.getValue().timeslots.equalsIgnoreCase(s[k])){
                                    roomRecord.remove(entry.getKey());
                                    booked.add("RR"+entry.getKey());
                                    bookedflag = true;
                                }
                            }
                        }
                    }
                }
                if (flag==true || bookedflag == true){
                    if (flag==true&&bookedflag == true){
                        System.out.println(campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"\n"
                                + "the Created reservation is deleted: "+booked+"    "+this.d);
                        return campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"\n"
                                + "the Created reservation is deleted: "+booked;
                    }else if (flag==true){
                        System.out.println(campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"    "+this.d);
                        return campus+" campus "+ roomnum+" on "+timeSlots+" was deleted";
                    }else {
                        System.out.println("the Created reservation is deleted: "+booked+"    "+this.d);
                        return "the Created reservation is deleted: "+booked;

                    }
                }else {
                    System.out.println("sorry the room number is unavailable"+"    "+this.d);
                    return "sorry the room number is unavailable";
                }
            }else {
                System.out.println("the input date is unavailable"+"    "+this.d);//if the date is not in the map
                return "the input date is unavailable";
            }

        }else if (campus.equalsIgnoreCase("WST")){

            if (WST.containsKey(date)){                                                                 //if the date is in the hashmap
                ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = WST.get(date);
                Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();
                ArrayList<String> booked = new ArrayList<>();
                boolean flag = false;
                boolean bookedflag = false;
                while (iterator.hasNext()){
                    HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                    if (hashMap.containsKey(roomnum)){                                              //if the room exists
                        ArrayList<String> strings = hashMap.get(roomnum);
                        ArrayList<String> stringscopy = strings;

                        for (int k = 0; k<s.length;k++){                                            //check time slots onr by onr
                            if (strings.contains(s[k])){

                                strings.remove(s[k]);
                                HashMap<Integer,ArrayList<String>> h = new HashMap<>();
                                h.put(roomnum,strings);
                                HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                                scopy.put(roomnum,stringscopy);
                                WST.get(date).remove(scopy);                  //remove the target timeslots
                                WST.get(date).add(h);                         //renew the timeslots
                                flag=true;

                            }
                            for (Map.Entry<Integer,RoomRecord> entry:roomRecord.entrySet()){
                                if (entry.getValue().timeslots.equalsIgnoreCase(s[k])){
                                    roomRecord.remove(entry.getKey());
                                    booked.add("RR"+entry.getKey());
                                    bookedflag = true;
                                }
                            }
                        }
                    }
                }
                if (flag==true || bookedflag == true){
                    if (flag==true&&bookedflag == true){
                        System.out.println(campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"\n"
                                + "the Created reservation is deleted: "+booked+"    "+this.d);
                        return campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"\n"
                                + "the Created reservation is deleted: "+booked;
                    }else if (flag==true){
                        System.out.println(campus+" campus "+ roomnum+" on "+timeSlots+" was deleted"+"    "+this.d);
                        return campus+" campus "+ roomnum+" on "+timeSlots+" was deleted";
                    }else {
                        System.out.println("the Created reservation is deleted: "+booked+"    "+this.d);
                        return "the Created reservation is deleted: "+booked;

                    }
                }else {
                    System.out.println("sorry the room number is unavailable"+"    "+this.d);
                    return "sorry the room number is unavailable";
                }
            }else {
                System.out.println("the input date is unavailable"+"    "+this.d);//if the date is not in the map
                return "the input date is unavailable";
            }

        }else {
            return "the campus is invalid";
        }
    }

    @Override
    public String changeReservation(String oldBookingID, String newCampus, Integer newRoomnum, String newTimeSlot) {
        RoomRecord roomRecord1 = null;
        int key = 0;
        String date = null;
        for (Map.Entry<Integer, RoomRecord> entry: roomRecord.entrySet()){
            if (entry.getValue().RecordID.equalsIgnoreCase(oldBookingID)){
                roomRecord1 = entry.getValue();
                roomRecord1.setCampus(newCampus);
                roomRecord1.setRoomnum(newRoomnum);
                roomRecord1.setTimeslots(newTimeSlot);
                key = entry.getKey();
                date = entry.getValue().getDate();
                roomRecord.remove(entry.getKey());
                roomRecord.put(key,roomRecord1);

                if (newCampus.equalsIgnoreCase("DVL")) {
                    if (DVL.containsKey(date)){                                                             //if the date is in the hashmap
                        ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = DVL.get(date);
                        Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();

                        while (iterator.hasNext()){
                            HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                            if (hashMap.containsKey(newRoomnum)){
                                ArrayList<String> strings = hashMap.get(newRoomnum);
                                ArrayList<String> stringscopy = strings;
                                if (strings.contains(newTimeSlot)){
                                    strings.remove(newTimeSlot);
                                    HashMap<Integer,ArrayList<String>> s = new HashMap<>();
                                    s.put(newRoomnum,strings);
                                    HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                                    scopy.put(newRoomnum,stringscopy);
                                    DVL.get(date).remove(scopy);                  //remove the old record
                                    DVL.get(date).add(s);                         //renew the timeslots
                                    System.out.println("room record: "+roomRecord1.toString()+"   ");
                                    return "change reservation successfully"+"\n"+
                                            roomRecord1.toString();
                                }
                            }
                        }
                        System.out.println("sorry, the room or time slot is unavailable on DVL campus"+"   "+this.d);
                        return "sorry, the room or time slot is unavailable on DVL campus";//if the room in the specific date do not exist, build it
                    }
                }else if (newCampus.equalsIgnoreCase("KKL")) {
                    if (KKL.containsKey(date)){                                                             //if the date is in the hashmap
                        ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = KKL.get(date);
                        Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();

                        while (iterator.hasNext()){
                            HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                            if (hashMap.containsKey(newRoomnum)){
                                ArrayList<String> strings = hashMap.get(newRoomnum);
                                ArrayList<String> stringscopy = strings;
                                if (strings.contains(newTimeSlot)){
                                    strings.remove(newTimeSlot);
                                    HashMap<Integer,ArrayList<String>> s = new HashMap<>();
                                    s.put(newRoomnum,strings);
                                    HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                                    scopy.put(newRoomnum,stringscopy);
                                    KKL.get(date).remove(scopy);                  //remove the old record
                                    KKL.get(date).add(s);                         //renew the timeslots
                                    System.out.println("room record: "+roomRecord1.toString()+"   ");
                                    return "change reservation successfully"+"\n"+
                                            roomRecord1.toString();
                                }
                            }
                        }
                        System.out.println("sorry, the room or time slot is unavailable on KKL campus"+"   "+this.d);
                        return "sorry, the room or time slot is unavailable on KKL campus";//if the room in the specific date do not exist, build it
                    }
                }else if (newCampus.equalsIgnoreCase("WST")) {
                    if (WST.containsKey(date)){                                                             //if the date is in the hashmap
                        ArrayList<HashMap<Integer,ArrayList<String>>> arrayListHashMap = WST.get(date);
                        Iterator<HashMap<Integer,ArrayList<String>>> iterator = arrayListHashMap.iterator();

                        while (iterator.hasNext()){
                            HashMap<Integer,ArrayList<String>> hashMap = iterator.next();
                            if (hashMap.containsKey(newRoomnum)){
                                ArrayList<String> strings = hashMap.get(newRoomnum);
                                ArrayList<String> stringscopy = strings;
                                if (strings.contains(newTimeSlot)){
                                    strings.remove(newTimeSlot);
                                    HashMap<Integer,ArrayList<String>> s = new HashMap<>();
                                    s.put(newRoomnum,strings);
                                    HashMap<Integer,ArrayList<String>> scopy = new HashMap<>();
                                    scopy.put(newRoomnum,stringscopy);
                                    WST.get(date).remove(scopy);                  //remove the old record
                                    WST.get(date).add(s);                         //renew the timeslots
                                    System.out.println("room record: "+roomRecord1.toString()+"   ");
                                    return "change reservation successfully"+"\n"+
                                            roomRecord1.toString();
                                }
                            }
                        }
                        System.out.println("sorry, the room or time slot is unavailable on WST campus"+"   "+this.d);
                        return "sorry, the room or time slot is unavailable on WST campus";//if the room in the specific date do not exist, build it
                    }
                }


            }

        }





        return "sorry cannot find your book ID";
    }
}
