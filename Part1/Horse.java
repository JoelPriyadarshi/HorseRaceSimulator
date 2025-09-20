package Part1;
/**
 * This Horse class contains details about the horse (name symbol etc) and also its functions (such as moving forward or going back to the start)
 * 
 * Author: Joel Priyadarshi
 * @version1 10/4/2025
 */

 public class Horse{
    private String horseName;
    private char horseSymbol;
    private double horseConfidence;
    private String horseAccessory;
    private int distanceTravelled = 0; // initially 0
    private boolean hasFallen = false; // hasFallen = fallen (true if fallen)

    /**
     * Constructor for objects of class Horse
     * No need to set distanceTravelled or hasFallen as they have default values
     */
    
    public Horse(char horseSymbol, String horseName, double horseConfidence, String horseAccessory) {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseAccessory = horseAccessory;

        if (horseConfidence <= 1 && horseConfidence >= 0) {
            this.horseConfidence = horseConfidence;
        }
        else {
            throw new IllegalArgumentException("Confidence must be between 0 and 1");
        }
    }
    
    /**
     * Changes hasFallen to true when a horse falls
     */
    public void fall(){
        hasFallen = true;
    }


    /**
     * Returns the confidence of a horse
     * Only accessed through an object of the class
     */
    public double getConfidence(){
        return horseConfidence;
    }

    /**
     * Returns the accessory of a horse
     * Only accessed through an object of the class
     */
    public String getAccessory(){
        return horseAccessory;
    }

    /**
     * Returns the distance travelled by a horse
     * Only accessed through an object of the class
     */
    public int getDistanceTravelled(){
        return distanceTravelled;
    }

    /**
     * Returns the name of a horse
     * Only accessed through an object of the class
     */
    public String getName(){
        return horseName;
    }

    /**
     * Returns the symbol of a horse object
     * Only accessed through an object of the class
     */
    public char getSymbol(){
        return horseSymbol;
    }

    /**
     * Sets the distance travelled to 0 and hasFallen back to false
     * Only accessed through an object of the class
     */
    public void goBackToStart(){
        distanceTravelled = 0;
        hasFallen = false;
    }

    
    /**
     * Increases confidence by 5% and ensures it is a valid confidence
     * Only accessed through an object of the class
     */
    public void increaseConfidence() {
        horseConfidence *= 1.05; // Increase by 5%
        
        if (horseConfidence > 1.0) {
            horseConfidence = 1.0; // Clamp to a maximum of 1.0
        }
    }
        
    /**
     * Decreases confidence by 5% and ensures it is a valid confidence
     * Only accessed through an object of the class
     */
    public void decreaseConfidence() {
        horseConfidence *= 0.95; // Decrease by 5%
            
        if (horseConfidence < 0.0) {
            horseConfidence = 0.0; // Clamp to a minimum of 0.0
    
        }
    }

    /**
     * Returns true or false if the horse has fallen or not
     * Only accessed through an object of the class
     */
    public boolean hasFallen(){
        return hasFallen;
    }

    /**
     * Increments the distance by 1
     * Only accessed through an object of the class
     */
    public void moveForward(){
        distanceTravelled++;
    }



    /**
     * Changes the confidence of a horse object and ensures it is a valid confidence
     * Only accessed through an object of the class
     */
    public void setConfidence(double newConfidence) {
        if (newConfidence <= 1 && newConfidence >= 0) {
            horseConfidence = newConfidence;
        } else {
            throw new IllegalArgumentException("Confidence must be between 0 and 1");
        }
    }

    /**
     * Sets the symbol to a new character for a horse object
     * Only accessed through an object of the class
     */
    public void setSymbol(char newSymbol){
        this.horseSymbol = newSymbol;
    }

    /**
     * Sets the accessory to a new value for a horse object
     * Only accessed through an object of the class
     */
    public void setAccessory(String newAccessory){
        this.horseAccessory = newAccessory;
    }
}