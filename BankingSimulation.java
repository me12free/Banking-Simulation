import java.util.ArrayList;
import java.util.Random;

/**
 * Customer class represents a customer in the banking simulation
 * It stores the various metrics related to each customer's experience
 * 
 */
class Customer {
    double IAT;                 // Inter-Arrival Time: Time between the arrival of two consecutive customers
    double arrivalTime;          // Arrival Time: Time at which the customer arrives at the bank
    double serviceTime;          // Service Time: The time it takes to serve the customer
    double serviceStartTime;     // Service Start Time: The time when service begins for the customer
    double serviceEndTime;       // Service End Time: The time when service for the customer is completed
    double waitingTime;          // Waiting Time: The time the customer spends waiting before service
    double timeInSystem;         // Time in System: The total time the customer spends in the bank (waiting + service)
    double idleTime;             // Idle Time: The time the server is idle between customers
    int queueLength;             // Queue Length: Number of customers in queue at the time of the current customer

    // Constructor to initialize customer data
    public Customer(double IAT, double arrivalTime, double serviceTime, double serviceStartTime,
                    double serviceEndTime, double waitingTime, double timeInSystem, double idleTime, int queueLength) {
        this.IAT = IAT;                         // Inter-arrival time between customers
        this.arrivalTime = arrivalTime;         // Arrival time of the customer
        this.serviceTime = serviceTime;         // Time required to serve the customer
        this.serviceStartTime = serviceStartTime; // Time when service begins for this customer
        this.serviceEndTime = serviceEndTime;   // Time when the customer’s service is completed
        this.waitingTime = waitingTime;         // Time spent waiting before service
        this.timeInSystem = timeInSystem;       // Total time spent in the system
        this.idleTime = idleTime;               // Idle time of the server between customers
        this.queueLength = queueLength;         // Number of customers in the queue at the time of service
    }
}

public class BankingSimulation {
    public static void main(String[] args) {
        Random rand = new Random();                 // Random object for generating random times
        ArrayList<Customer> customers = new ArrayList<>();  // List to store all customers
        int numberOfCustomers = 30;                 // Total number of customers to simulate

        // Initializing variables for the first customer
        double arrivalTime = 0;                     // First customer arrives at time 0
        double serviceStartTime = 0;                // Service for first customer starts immediately at time 0
        double serviceEndTime = 0;                  // Initial service end time set to 0
        double idleTime = 0;                        // No idle time at the start
        double serviceTime = generateServiceTime(rand); // Random service time generated for the first customer
        
        // Add the first customer to the list with initial values
        customers.add(new Customer(0, 0, serviceTime, 0, serviceTime, 0, serviceTime, 0, 0));

        // Loop to simulate the arrival and service of remaining customers
        for (int i = 1; i < numberOfCustomers; i++) {
            // Generate inter-arrival time and update arrival time
            double IAT = generateInterArrivalTime(rand);
            arrivalTime += IAT;

            // Calculate idle time for the server (if the next customer arrives after service ends)
            if (arrivalTime > serviceEndTime) {
                idleTime = arrivalTime - serviceEndTime;  // Server idle until next customer arrives
            } else {
                idleTime = 0;                             // No idle time if customer arrives before service ends
            }

            // Determine when service will start for the next customer
            serviceStartTime = Math.max(arrivalTime, serviceEndTime);  // Service starts after the previous one ends
            serviceTime = generateServiceTime(rand);       // Generate random service time for the current customer
            serviceEndTime = serviceStartTime + serviceTime;  // Calculate when service will end

            // Calculate the customer's waiting time and total time spent in the system
            double waitingTime = serviceStartTime - arrivalTime;   // Time customer spends waiting in queue
            double timeInSystem = serviceEndTime - arrivalTime;    // Total time spent in the bank

            // Queue length is 1 if the customer has to wait, otherwise 0 (no queue)
            int queueLength = waitingTime > 0 ? 1 : 0;

            // Add the new customer's details to the list
            customers.add(new Customer(IAT, arrivalTime, serviceTime, serviceStartTime, serviceEndTime, waitingTime, timeInSystem, idleTime, queueLength));
        }

        // Print Results: Display data in a formatted table
        System.out.printf("%-10s%-10s%-15s%-15s%-20s%-20s%-15s%-15s%-20s%-15s\n", "Customer", "IAT", "Arrival Time", "Service Time", "Service Start Time", "Service End Time", "Waiting Time", "Time in System", "Idle Time", "Queue Length");

        // Loop through all customers and print their data
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            System.out.printf("%-10d%-10.2f%-15.2f%-15.2f%-20.2f%-20.2f%-15.2f%-15.2f%-20.2f%-15d\n",
                    (i + 1), c.IAT, c.arrivalTime, c.serviceTime, c.serviceStartTime, c.serviceEndTime, c.waitingTime, c.timeInSystem, c.idleTime, c.queueLength);
        }
    }

    // Generates a random Inter-Arrival Time (IAT) between 0.3 and 2.0
    private static double generateInterArrivalTime(Random rand) {
        return 0.3 + (2.0 - 0.3) * rand.nextDouble();
    }

    // Generates a random Service Time between 0.2 and 3.0
    private static double generateServiceTime(Random rand) {
        return 0.2 + (3.0 - 0.2) * rand.nextDouble();
    }
}