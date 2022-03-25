import java.awt.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main {
    public static void main(String[] args) {
        int AU = 280;
        int speed = 10;
        double SunMass = 20;

        Planet sun = new Planet("",new Point(500.0,500.0), new Vector(0,0.0), SunMass, Color.YELLOW, 70);
        int offset = 500- sun.getRadius();
        Planet mercury = new Planet("Mercury",new Point(500,offset-0.4*AU),new Vector(0.9,0), 0.00000016*SunMass, Color.darkGray, 8);
        Planet venus = new Planet("Venus",new Point(500,offset-0.8*AU),new Vector(0.75,0),0.00000244 *SunMass, Color.white, 18);
        Planet earth = new Planet("Earth",new Point(500.0,offset-AU),new Vector(0.7,0),0.00000304*SunMass, Color.BLUE, 20);
        Planet mars = new Planet("Mars",new Point(500.0,offset-1.5*AU),new Vector(0.6,0),0.000000322*SunMass, Color.RED,10 );


        Planet[] planets = {sun, mercury, venus, earth,mars};
        final ParticleSimulation animation = new ParticleSimulation(planets);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame j = new JFrame();
                j.setTitle("Particle Simulation");
                j.setContentPane(animation);
                j.getContentPane().setBackground(Color.black);
                j.setSize(1000,1000);
                j.setVisible(true);
                j.setResizable(true);
                j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });

        while (args.length == 0) {
            try {
                Thread.sleep(speed);
                animation.move();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}