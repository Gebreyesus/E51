import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Creates a GUI that simulates the elevators in an entire building
 * with a certain number of floors and elevators
 * @author Kim
 */
public class BuildingSwing {

   /** Floors in the building */
   int numFloors;
   /** Elevators in the building */
   int numElevators;
   /** Array of sliders to represent each elevator */
   ElevatorSlider[] elevator;
   /** GUI frame */
   JFrame frame;
   JLabel timeLabel;
   JLabel people;

   /**
    * Creates a new GUI for the Building View
    * @param numFloors Number of floors in the building
    * @param numElevators Number of elevators in the building
    * @param system Control system for the elevator
    */
   public BuildingSwing (int floors, int elevators) {
      // Store the 
      numFloors = floors;
      numElevators = elevators;
      elevator = new ElevatorSlider[numElevators];
      timeLabel = new JLabel ();
      people = new JLabel ();
   }

   /**
    * Generates the building view using sliders to represent the elevators
    */
   public void init (Elevator[] inputElevators) {
      System.out.println ("Creating GUI");
      // Create a JFrame with "Elevator Proposal" as the title
      frame = new JFrame ("Building View");
      // Set the frame so that the program stops when the frame is closed
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      System.out.println ("Setting frame size");
      frame.setSize (500, 500);
      frame.add (timeLabel);
      // Generate the elevators
      for (int i = 0; i < numElevators; i++) {
         elevator[i] = new ElevatorSlider (inputElevators[i], i + 1);
         frame.add (elevator[i]);
      }
      frame.add (people);
      frame.setLayout (new FlowLayout ());
      frame.setVisible (true);
      frame.setBackground (Color.white);

   }

   /**
    * Changes the elevator positions
    * @param rate The number of milliseconds between "frames"
    * @throws InterruptedException 
    */
   public void update (int rate, Elevator[] elevators, int time, Building b)
   throws InterruptedException {
      updateTime (time);
      updatePeople (b);
      // Get the state of each individual elevator and then update
      for (int i = 0; i < numElevators; i++) {
         // Update the value
         elevator[i].update (elevators[i], i + 1);
      }
      Thread.sleep (rate);
      frame.pack ();
   }

   public void updateTime (int time) {
      String n = "Time: " + time;
      timeLabel.setText (n);
   }

   public void updatePeople (Building b) {
      String n = "<html>";
      for (int i = numFloors - 1; i >= 0; i--) {
         int sum = 0;
         for (int j = 0; j < 2; j++) {
            sum += b.getPeople (i, j);
         }
         n += sum + "<br>";
      }
      n += "</html>";
      people.setText (n);
   }
}
