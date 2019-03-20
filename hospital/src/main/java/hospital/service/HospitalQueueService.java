package hospital.service;

import hospital.model.Patient;

import java.util.PriorityQueue;

public class HospitalQueueService {
    PriorityQueue<Patient> queue = new PriorityQueue<>();

    public void add(Patient patient) {
        queue.add(patient);
    }

    public Patient peek() {
        return queue.peek();
    }

    public Patient next() {
        return queue.poll();
    }

}
