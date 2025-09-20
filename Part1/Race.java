package Part1;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * Now upgraded with more features 
 *
 * @author McRaceface & Joel Priyadarshi
 * @version 2.0
 */
public class Race {
    private int raceLength;
    private Horse[] lanes;
    private int totalTime;
    private String raceStats = "";
    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int raceLength, int numberOfLanes) {
        if (raceLength <= 0 || numberOfLanes <= 0) {
            throw new IllegalArgumentException("Race length and number of lanes must be positive.");
        }
        this.raceLength = raceLength;
        this.lanes = new Horse[numberOfLanes];
    }

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber < 1 || laneNumber > lanes.length) {
            System.out.println("Invalid lane number. Must be between 1 and " + lanes.length + "");
            return;
        }
        lanes[laneNumber - 1] = theHorse;
    }

    /***
     * Returns the horse of a given lane number
     * @param laneNumber of the horse
     */
    public Horse getHorse(int laneNumber) {
        if (laneNumber < 1 || laneNumber > lanes.length) {
            System.out.println("Invalid lane number. Must be between 1 and " + lanes.length + "");
            return null;
        }
        return lanes[laneNumber - 1]; // Convert 1-based to 0-based index
    }

    /***
     * Update the race length to a new value
     * @param the new length
     */
    public void setRaceLength(int newLength) {
        if (newLength <= 0) {
            throw new IllegalArgumentException("Race length must be greater than 0.");
        }
        raceLength = newLength;
    }

    /***
     * Get the race length
     */
    public int getRaceLength() {
        return raceLength;
    }

    

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace() {
        boolean finished = false;
        totalTime = 0; // Total time taken for the race

        // Reset all horses to the start
        for (Horse horse : lanes) {
            if (horse != null) {
                horse.goBackToStart();
                System.out.println(horse.getName() + "'s confidence: " + horse.getConfidence());
            }
        }

        while (!finished) {
            // Move each horse
            for (Horse horse : lanes) {
                if (horse != null && !horse.hasFallen()) {
                    moveHorse(horse);
                }
            }

            // Increment total time
            totalTime++;

            // Print the race positions
            printRace();

            // Check if any horse has won
            for (Horse horse : lanes) {
                if (raceWonBy(horse)) {
                    horse.increaseConfidence();
                    finished = true;
                    break;
                }
            }

            // Check if all horses have fallen
            boolean allFallen = true;
            for (Horse horse : lanes) {
                if (horse != null && !horse.hasFallen()) {
                    allFallen = false;
                    break;
                }
            }
            if (allFallen) {
                System.out.println("All horses have fallen! The race is over and there is no winner.");
                finished = true;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {}
        }

        // Set the total time for the race
        setTotalTime(totalTime);

        // Calculate stats after the race
        calculateStats();

        for (Horse horse : lanes) {
            if (horse != null) {
                if (horse.hasFallen()){
                    horse.decreaseConfidence();
                    if (horse.getAccessory().equals("Rocket Horseshoes")) { //decrease confidence by 10% if the horse has fallen and has rocket shoes
                        horse.decreaseConfidence();
                    }
                }
                horse.goBackToStart();  // Now adjust the confidence after the race
                System.out.println(horse.getName() + "'s confidence: " + horse.getConfidence());
            }
        }
    }
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
                if ((theHorse.getAccessory().equals("Rocket Horseshoes")) && (raceLength - theHorse.getDistanceTravelled() >= 2)) {
                    Random random = new Random();
                    if (random.nextInt(100) < 30) {  // 30% chance to move extra distance rather than 100% (0 to 30)
                        theHorse.moveForward(); // Extra move for rocket shoes
                    }
                }
            }
            if (Math.random() < (0.1 * theHorse.getConfidence() * theHorse.getConfidence())) {
                if (theHorse.getAccessory().equals("Lifeline Lasso")) {
                    theHorse.setAccessory("None"); // Accessory used up
                }
                else if (theHorse.getAccessory().equals("Hoofshield")) {
                        // 80% chance to trigger fall
                        Random random = new Random();
                        if (random.nextInt(100) < 80) {  // 80% chance to fall rather than 100% (0 to 79)
                            theHorse.fall();
                        }
                }
                else{
                    theHorse.fall();
                }
            }
        }
    }



    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse) {
        if (theHorse != null && theHorse.getDistanceTravelled() == raceLength) {
            System.out.println("And the winner is... " + theHorse.getName() + "!");
            return true;
        }
        return false;
    }

    /**
    * Display performance metrics for each horse after the race.
    * Metrics include average speed and finishing time which was calculated in another method.
    */
    public void displayStats() {
        if (raceStats.isEmpty()) {
            System.out.println("No race has been run yet.");
        } else {
            System.out.println(raceStats);
        }
    }

    /**
    * Calculates stats for each horse after the race.
    * Metrics include average speed and finishing time.
    */
    private void calculateStats() {
        raceStats = "Race Statistics:\n";
    
        for (Horse horse : lanes) {
            if (horse != null) {
                boolean finished = horse.getDistanceTravelled() >= raceLength;
    
                // Calculate average speed
                double averageSpeed;
                if (finished) {
                    averageSpeed = (double) raceLength / totalTime;
                } else {
                    averageSpeed = (double) horse.getDistanceTravelled() / totalTime;
                }
    
                // Build stats for the horse
                raceStats += "Horse: " + horse.getName() + "\n";
                raceStats += "  Average Speed: " + String.format("%.2f", averageSpeed) + " units/time\n";
                if (finished) {
                    raceStats += "  Finishing Time: " + totalTime + " time units\n";
                } else {
                    raceStats += "  Finishing Time: Did not finish\n";
                }
                raceStats += "  Distance Travelled: " + horse.getDistanceTravelled() + " units\n";
            }
        }
    }

    
    /* Returns the name of the winning horse, or null if no winner exists.
    *
    * @return The name of the winning horse, or null if no winner exists.
    */
    public String getWinner() {
        for (Horse horse : lanes) {
            if (horse != null && horse.getDistanceTravelled() == raceLength) { 
                return horse.getName(); // Return the name of the winning horse
            }
        }
        return null; // No winner
    }

    /* Returns the number of tracks, or null if there are no tracks.
    *
    * @return The number of tracks, or null if there are no tracks.
    */
    public int getNumberOfLanes() {
        return lanes.length; // Return the number of lanes
    }

    /* Returns the array of horses
    *
    * @return The array of horses called lanes
    */
    public Horse[] getHorseArray() {
        return lanes; // Returns the horse array which are filled with the lanes
    }

    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print('\u000C');  // Clear the terminal

        multiplePrint('=', raceLength + 3); // Top edge of track
        System.out.println();

        for (Horse horse : lanes) {
            if (horse != null) {
                printLane(horse);
                System.out.println();
            }
        }

        multiplePrint('=', raceLength + 3); // Bottom edge of track
        System.out.println();
    }

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse) {
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        System.out.print('|');
        multiplePrint(' ', spacesBefore);
        if (theHorse.hasFallen()) {
            System.out.print('X');
        } else {
            System.out.print(theHorse.getSymbol());
        }
        multiplePrint(' ', spacesAfter);
        System.out.print('|');
    }

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(aChar);
        }
    }

    /**
    * Set the total time for the race.
    * @param totalTime The total time the race took to complete.
    */
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    /**
    * Get the total time for the race.
    * @return The total time the race took to complete.
    */
    public int getTotalTime() {
        return totalTime;
    }
}



