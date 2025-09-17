# Horse Racing Simulator

## Overview
The Horse Racing Simulator is a Java-based application that allows users to simulate horse races. It features a graphical user interface (GUI) for setting up races, adding horses, and starting the race.

## Features
- Configure race length and number of lanes.
- Add horses with custom names, symbols, confidence levels, and accessories.
- Simulate a race with real-time updates in the GUI.
- Accessories like "Rocket Horseshoes" and "Lifeline Lasso" affect horse performance.
- View stats button that displays the horses' speed and distance travelled.

## Requirements
- Java Development Kit (JDK) 8 or later.
- A terminal or IDE to compile and run the program (preferably Visual Studio Code).
- Newer versions of java (enter "java -version" in the terminal to check)
- To download the latest java versions: https://www.oracle.com/java/technologies/downloads/?er=221886#jdk24-windows

## Setup Instructions
1. **Compile the Code**:
   Navigate to the project directory and run in the terminal:

   javac -d bin Part1\*.java Part2\*.java

2. This will create a bin with the java files, now run:

   java -cp bin Part2.MainGUI

## Usage Instructions
1. Launch the application by running the `java -cp bin Part2.MainGUI` command.
2. Enter the race length and number of lanes in the input fields, then click **Initialize Race**.
3. Add horses by entering their details (name, symbol, etc.) and clicking **Add Horse**.
4. Start the race by clicking **Start Race**.
5. View the race stats by clicking **View Stats** after the race ends.

## Extra Information
- Horse confidence determines the likelihood of it either falling or moving forward
- The higher the confidence the more it will move forward but the more likely it is to fall
- When a horse falls its confidence will decrease
- When a horse wins its confidence will increase
- Horses can equip accessories that will modify certain stats
- Rocket Horseshoes allows the horse to sometimes move 2 units rather than 1 and when it falls its confidence reduces double the amount
- Lifeline Lasso grants the horse a second chance if it falls and then the accessory is used up
- Hoofshield reduces the fall rate of the horse by 20%
