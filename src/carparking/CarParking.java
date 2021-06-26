package carparking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


class Vehicle {

    String registration_num;
    int age;

    Vehicle(String registration_num, int age) {
        this.registration_num = registration_num;
        this.age = age;
    }
}

public class CarParking {

    /**
     * @param args the command line arguments
     */
    private final HashMap<Integer, Vehicle> slot_vehicle_mapping;
    private final HashMap<String, Integer> registration_slot_mapping;
    private final HashMap<Integer, List<Integer>> age_slot_mapping;
    private final HashMap<Integer, List<String>> age_registration_mapping;
    private final PriorityQueue<Integer> available_slots;

    CarParking(int slots) {
        slot_vehicle_mapping = new HashMap<>();
        registration_slot_mapping = new HashMap<>();
        age_slot_mapping = new HashMap<>();
        age_registration_mapping = new HashMap<>();

        available_slots = new PriorityQueue<>();
        for (int i = 1; i <= slots; i++) {
            available_slots.add(i);
        }

    }

    //Returns nearest slot to entry point otherwise 0
    private int getNearestSlot() {
        if (available_slots.isEmpty()) {
            return 0;
        }

        int slot = available_slots.peek();
        available_slots.poll();
        return slot;
    }

    public int park(Vehicle v) {
        int slot = getNearestSlot();

        //All slots are full
        if (slot == 0) {
            return 0;
        }

        slot_vehicle_mapping.put(slot, v);
        registration_slot_mapping.put(v.registration_num, slot);

        if (age_slot_mapping.get(v.age) == null) {
            age_slot_mapping.put(v.age, new ArrayList<>());
        }
        age_slot_mapping.get(v.age).add(slot);

        if (age_registration_mapping.get(v.age) == null) {
            age_registration_mapping.put(v.age, new ArrayList<>());
        }
        age_registration_mapping.get(v.age).add(v.registration_num);

        return slot;
    }

    public Vehicle leave(int slot) {
        //If the slot is already empty
        if (slot_vehicle_mapping.containsKey(slot) == false) {
            return null;
        }

        available_slots.add(slot);
        Vehicle v = slot_vehicle_mapping.get(slot);
        slot_vehicle_mapping.remove(slot);
        registration_slot_mapping.remove(v.registration_num);
        age_slot_mapping.get(v.age).remove(new Integer(slot));
        age_registration_mapping.get(v.age).remove(v.registration_num);
        return v;
    }

    public List<String> getRegNumberForDriverAge(int age) {
        //No Registration number are assigned to persons of this age
        if (age_registration_mapping.get(age) == null) {
            return new ArrayList<>();
        }

        return age_registration_mapping.get(age);
    }

    public List<Integer> getSlotsForDriverAge(int age) {
        //No slots are assigned to persons of this age
        if (age_slot_mapping.get(age) == null) {
            return new ArrayList<>();
        }

        return age_slot_mapping.get(age);
    }

    public Integer getSlotForRegNumber(String reg_num) {
        //No slot is assigned to given registration number
        if (registration_slot_mapping.get(reg_num) == null) {
            return 0;
        }

        return registration_slot_mapping.get(reg_num);
    }

}
