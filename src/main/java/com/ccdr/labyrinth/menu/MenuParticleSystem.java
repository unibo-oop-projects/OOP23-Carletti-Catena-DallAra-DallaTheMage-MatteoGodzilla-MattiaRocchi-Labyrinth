package com.ccdr.labyrinth.menu;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.ccdr.labyrinth.Material;
import com.ccdr.labyrinth.TypeImag;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Class responsible for drawing and managing all of the decoration particles shown in the main menu.
 */
public final class MenuParticleSystem {
    private static final int PARTICLE_COUNT = 100;
    private static final double BOX_HALF_WIDTH = 8;
    private static final double BOX_HALF_HEIGHT = 6;
    private static final double BOX_DEPTH = 20;
    private static final double PARTICLE_MAX_SIZE = 50.0;
    private static final double PARTICLE_MAX_SPEED = 0.01;

    private Set<Particle> particles = new HashSet<>();
    private Random r = new Random();

    /**
     *
     */
    public MenuParticleSystem() {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            this.particles.add(generateParticle());
        }
    }

    private Particle generateParticle() {
        final Material m = Material.values()[r.nextInt(Material.values().length)];
        //from -BOX_WIDTH/2 to BOX_WIDTH/2
        final double x = r.nextDouble() * BOX_HALF_WIDTH * 2 - BOX_HALF_WIDTH;
        //from -BOX_HEIGHT/2 to BOX_HEIGHT/2
        final double y = r.nextDouble() * BOX_HALF_HEIGHT * 2 - BOX_HALF_HEIGHT;
        //from 0 to BOX_DEPTH
        final double z = r.nextDouble() * (BOX_DEPTH - 1) + 2;
        final double speed = r.nextDouble() * PARTICLE_MAX_SPEED;
        return new Particle(m, x, y, z, speed);
    }

    /**
     * Updates the current position of every particle, resetting the ones which are not visible to the user.
     */
    public void update() {
        for (Particle particle : this.particles) {
            particle.z -= particle.zSpeed;
        }
        this.particles = this.particles.stream()
            .filter(particle -> particle.z >= 1)
            .collect(Collectors.toSet());
        for (int i = this.particles.size(); i < PARTICLE_COUNT; i++) {
            this.particles.add(generateParticle());
        }
    }

    /**
     * Renders the particles in the specified target canvas.
     * @param target Viewport target
     */
    public void render(final Canvas target) {
        final GraphicsContext context = target.getGraphicsContext2D();
        final double width = target.getWidth();
        final double height = target.getHeight();
        context.save();
        context.beginPath();
        context.setStroke(Color.WHITESMOKE);
        for (final Particle particle : this.particles) {
            Image i;
            switch (particle.m) {
                case COAL:
                    i = TypeImag.COAL.getImage();
                    break;
                case COPPER:
                    i = TypeImag.COPPER.getImage();
                    break;
                case DIAMOND:
                    i = TypeImag.DIAMOND.getImage();
                    break;
                case IRON:
                    i = TypeImag.IRON.getImage();
                    break;
                case SILK:
                    i = TypeImag.SILK.getImage();
                    break;
                case WOOD:
                default:
                    i = TypeImag.WOOD.getImage();
                    break;
            }
            final double viewportX = width / 2 + particle.x / BOX_HALF_WIDTH * width / 2;
            final double viewportY = height / 2 + particle.y / BOX_HALF_HEIGHT * height / 2;
            final double size = PARTICLE_MAX_SIZE / particle.z;
            context.drawImage(i, viewportX - size / 2, viewportY - size / 2, size, size);
        }

        context.stroke();
        context.restore();
    }


    private static class Particle {
        private final Material m;
        private final double x;
        private final double y;
        private double z;
        private final double zSpeed;

        Particle(final Material m, final double x, final double y, final double z, final double zSpeed) {
            this.m = m;
            this.x = x;
            this.y = y;
            this.z = z;
            this.zSpeed = zSpeed;
        }

        @Override
        public String toString() {
            return "Particle [m=" + m + ", x=" + x + ", y=" + y + ", z=" + z + ", zSpeed=" + zSpeed + "]";
        }
    }
}
