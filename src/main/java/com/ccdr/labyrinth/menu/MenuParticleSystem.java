package com.ccdr.labyrinth.menu;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.ccdr.labyrinth.ImageLoader;

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

    private final Random r = new Random();
    private Set<Particle> particles = new HashSet<>();
    private final List<Image> sourceImages = List.of(
        //Materials
        ImageLoader.COAL.getImage(),
        ImageLoader.COPPER.getImage(),
        ImageLoader.DIAMOND.getImage(),
        ImageLoader.IRON.getImage(),
        ImageLoader.SILK.getImage(),
        ImageLoader.WOOD.getImage(),
        //Categories
        ImageLoader.ARMOR.getImage(),
        ImageLoader.CLOTHING.getImage(),
        ImageLoader.JEWEL.getImage(),
        ImageLoader.TOOL.getImage(),
        ImageLoader.WEAPON.getImage()
    );

    /**
     *
     */
    public MenuParticleSystem() {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            this.particles.add(generateParticle());
        }
    }

    private Particle generateParticle() {
        final Image i = sourceImages.get(r.nextInt(sourceImages.size()));
        //from -BOX_WIDTH/2 to BOX_WIDTH/2
        final double x = r.nextDouble() * BOX_HALF_WIDTH * 2 - BOX_HALF_WIDTH;
        //from -BOX_HEIGHT/2 to BOX_HEIGHT/2
        final double y = r.nextDouble() * BOX_HALF_HEIGHT * 2 - BOX_HALF_HEIGHT;
        //from 0 to BOX_DEPTH
        final double z = r.nextDouble() * (BOX_DEPTH - 1) + 2;
        final double speed = r.nextDouble() * PARTICLE_MAX_SPEED;
        return new Particle(i, x, y, z, speed);
    }

    /**
     * Updates the current position of every particle, resetting the ones which are not visible to the user.
     */
    public void update() {
        for (var particle : this.particles) {
            particle.z -= particle.zSpeed;
        }
        this.particles = this.particles.stream()
            .filter(particle -> particle.z >= 1)
            .collect(Collectors.toSet());
        for (int i = this.particles.size(); i < PARTICLE_COUNT / 2; i++) {
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
        context.setStroke(Color.WHITESMOKE);
        for (final Particle particle : this.particles) {
            final double x = getParticleX(width, particle);
            final double y = getParticleY(height, particle);
            final double size = getParticleSize(particle);
            context.drawImage(particle.image, x - size / 2, y - size / 2, size, size);
        }
        context.stroke();
        context.restore();
    }

    private double getParticleX(final double width, final Particle particle) {
        return width / 2 + particle.x / BOX_HALF_WIDTH * width / 2;
    }

    private double getParticleY(final double height, final Particle particle) {
        return height / 2 + particle.y / BOX_HALF_HEIGHT * height / 2;
    }

    private double getParticleSize(final Particle particle) {
        return PARTICLE_MAX_SIZE / particle.z;
    }

    private static class Particle {
        private final Image image;
        private final double x;
        private final double y;
        private double z;
        private final double zSpeed;

        Particle(final Image m, final double x, final double y, final double z, final double zSpeed) {
            this.image = m;
            this.x = x;
            this.y = y;
            this.z = z;
            this.zSpeed = zSpeed;
        }

        @Override
        public String toString() {
            return "Particle [m=" + image + ", x=" + x + ", y=" + y + ", z=" + z + ", zSpeed=" + zSpeed + "]";
        }
    }
}
