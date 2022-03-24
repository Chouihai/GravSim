import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * A simulation of three particles in two dimensional Cartesian space
 * acting on each other using Newtonian gravitation.
 */
public class ParticleSimulation extends JPanel {
    /**
     * Put this in to keep Eclipse happy ("KEH").
     */
    private static final long serialVersionUID = 1L;

    private final Planet[] planets;

    /**
     * Create a particle simulation with three particles
//     * @param p1 first particle, must not be null
//     * @param p2 second particle, must not be null
//     * @param p3 third particle, must not be null
     */
    public ParticleSimulation(Planet[] particles) {
        this.planets = particles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Planet p : planets) {
            p.draw(g);
        }
    }

    /**
     * Accelerate each particle by the gravitational force of each other particle.
     * Then compute the next position of all particles.
     */
    public void move() {
        for (Planet p : planets) {
            Vector force = new Vector();
            for (Planet other : planets) {
                if (other == p) continue; // no force from particle on itself
                force = force.add(other.gravForceOn(p));
            }
            p.applyForce(force);
        }
        for (Planet p : planets) {
            p.move();
        }
        repaint();
    }
}
