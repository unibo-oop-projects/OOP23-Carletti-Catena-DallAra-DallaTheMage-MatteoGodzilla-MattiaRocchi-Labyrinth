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

public class MenuParticleSystem {
    private static final int PARTICLE_COUNT = 100;
    private static final double BOX_HALF_WIDTH = 8;
    private static final double BOX_HALF_HEIGHT = 6;
    private static final double BOX_DEPTH = 20;
    private static final double PARTICLE_MAX_SIZE = 50.0;
    private static final double PARTICLE_MAX_SPEED = 0.01;

    private Set<Particle> particles = new HashSet<>();
    Random r = new Random();

    public MenuParticleSystem(){
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            this.particles.add(generateParticle());
        }
    }

    Particle generateParticle(){
        Material m = Material.values()[r.nextInt(Material.values().length)];
        //from -BOX_WIDTH/2 to BOX_WIDTH/2
        double x = r.nextDouble() * BOX_HALF_WIDTH * 2 - BOX_HALF_WIDTH;
        //from -BOX_HEIGHT/2 to BOX_HEIGHT/2
        double y = r.nextDouble() * BOX_HALF_HEIGHT * 2 - BOX_HALF_HEIGHT;
        //from 0 to BOX_DEPTH
        double z = r.nextDouble() * (BOX_DEPTH - 1) + 2;
        double speed = r.nextDouble() * PARTICLE_MAX_SPEED;
        return new Particle(m, x, y, z, speed);
    }

    void update(){
        for (Particle particle : this.particles) {
            particle.z -= particle.zSpeed;
        }
        this.particles = this.particles.stream()
            .filter(particle -> particle.z >= 1)
            .collect(Collectors.toSet());
        for(int i = this.particles.size(); i< PARTICLE_COUNT; i++){
            this.particles.add(generateParticle());
        }
    }

    void draw(Canvas target){
        final GraphicsContext context = target.getGraphicsContext2D();
        double width = target.getWidth();
        double height = target.getHeight();
        context.save();
        context.beginPath();
        context.setStroke(Color.WHITESMOKE);
        for (Particle particle : this.particles) {
            Image i;
            switch(particle.m){
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
            double viewportX = width / 2 + particle.x / BOX_HALF_WIDTH * width/2;
            double viewportY = height / 2 + particle.y / BOX_HALF_HEIGHT * height/2;
            double size = PARTICLE_MAX_SIZE / particle.z;
            context.drawImage(i, viewportX - size/2, viewportY - size/2,size,size);
        }

        context.stroke();
        context.restore();
    }


    private class Particle{
        private Material m;
        private double x;
        private double y;
        private double z;
        private double zSpeed;

        Particle(Material m, double x, double y, double z, double zSpeed){
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
