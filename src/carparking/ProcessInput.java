package carparking;

import java.io.*;
import java.util.List;


public class ProcessInput {

    private CarParking parking_lot;

    //Function to check if given String is Integer or not
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Function to process the given Input
    public void process(String st) throws FileNotFoundException {
        String[] arr = st.split(" ");
        String command = arr[0];

        switch (command) {
            case "Create_parking_lot":
                if (arr.length == 2 && isNumeric(arr[1])) {
                    int slots = Integer.parseInt(arr[1]);
                    //Creating a parking lot of given slots
                    parking_lot = new CarParking(slots);
                    
                    //Storing the output to Output.txt
                    PrintStream o = new PrintStream(new File("Output.txt"));
                    System.setOut(o);
                    System.out.println("Created parking of " + slots + " slots");
                }
                break;

            case "Park":
                if (arr.length == 4 && arr[2].equals("driver_age")) {
                    Vehicle car = new Vehicle(arr[1], Integer.parseInt(arr[3]));
                    int slot = parking_lot.park(car);

                    //All slots are full so no parking for this car
                    if (slot == 0) {
                        System.out.println();
                    } else {
                        System.out.println("Car with vehicle registration number " + arr[1] + " has been parked at slot number " + slot);
                    }
                }
                break;

            case "Leave":
                if (arr.length == 2 && isNumeric(arr[1])) {
                    int slot = Integer.parseInt(arr[1]);
                    Vehicle v = parking_lot.leave(slot);
                    //If vehicle is not present at given slot
                    if (v == null) {
                        System.out.println();
                    } else {
                        System.out.println("Slot number " + slot + " vacated, the car with vehicle registration number "
                                + v.registration_num + " left the space, the driver of the car was of age " + v.age);
                    }
                }
                break;

            case "Slot_numbers_for_driver_of_age":
                if (arr.length == 2) {
                    List<Integer> slots = parking_lot.getSlotsForDriverAge(Integer.parseInt(arr[1]));
                    slots.forEach((i) -> {
                        System.out.print(i + " ");
                    });
                    System.out.println();
                }
                break;

            case "Slot_number_for_car_with_number":
                if (arr.length == 2) {
                    int slot = parking_lot.getSlotForRegNumber(arr[1]);
                    //No slot is assigned to given registration number
                    if (slot == 0) {
                        System.out.println();
                    } else {
                        System.out.println(slot);
                    }
                }
                break;

            case "Vehicle_registration_number_for_driver_of_age":
                if (arr.length == 2) {
                    List<String> numbers = parking_lot.getRegNumberForDriverAge(Integer.parseInt(arr[1]));
                    numbers.forEach((num) -> {
                        System.out.print(num+" ");
                    });
                    System.out.println();
                }
                break;

            default:
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        //Reading inputs from Input.txt
        ProcessInput obj = new ProcessInput();
        File file = new File("Input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            obj.process(st);
        }
    }
}
