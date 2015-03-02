package com.redru.engine.particles;

import com.redru.engine.drawhandlers.IntParticleDrawHandler;
import com.redru.engine.drawhandlers.ParticlesDrawHandler;
import com.redru.engine.utils.OpenGLConstants;

public class ParticleSystem {
	private static ParticleSystem instance;
	
	public static final int POSITION_COMPONENT_COUNT = 3;
	public static final int COLOR_COMPONENT_COUNT = 4;
	public static final int VECTOR_COMPONENT_COUNT = 3;
	public static final int PARTICLE_START_TIME_COMPONENT_COUNT = 1;
	public static final int TOTAL_COMPONENT_COUNT = POSITION_COMPONENT_COUNT
			+ COLOR_COMPONENT_COUNT + VECTOR_COMPONENT_COUNT
			+ PARTICLE_START_TIME_COMPONENT_COUNT;
	public static final int STRIDE = TOTAL_COMPONENT_COUNT
			* OpenGLConstants.BYTES_PER_FLOAT;

	private final float[] particles;
	private final int maxParticleCount;
	private int currentParticleCount = 0;
	private int nextParticle = 0;

	private IntParticleDrawHandler drawHandler = new ParticlesDrawHandler();
// CONSTRUCTOR ---------------------------------------------------------------------------------------------------------------
	private ParticleSystem(int maxParticleCount) {
		this.particles = new float[maxParticleCount * TOTAL_COMPONENT_COUNT];
		this.maxParticleCount = maxParticleCount;
	}

	public static ParticleSystem getInstance() {
		if (instance == null) {
			instance = new ParticleSystem(10000);
		}
		
		return instance;
	}
// METHODS -------------------------------------------------------------------------------------------------------------------
	public void addParticle(float[] position, float[] color, float[] direction, float particleStartTime) {
		final int particleOffset = this.nextParticle * TOTAL_COMPONENT_COUNT;
		int currentOffset = particleOffset;
		this.nextParticle++;
		
		if (this.currentParticleCount < this.maxParticleCount) {
			this.currentParticleCount++;
		}
		
		if (this.nextParticle == this.maxParticleCount) {
			// Start over at the beginning, but keep currentParticleCount so
			// that all the other particles still get drawn.
			this.nextParticle = 0;
		}
	
		this.particles[currentOffset++] = position[0];
		this.particles[currentOffset++] = position[1];
		this.particles[currentOffset++] = position[2];
		this.particles[currentOffset++] = color[0];
		this.particles[currentOffset++] = color[1];
		this.particles[currentOffset++] = color[2];
		this.particles[currentOffset++] = color[3];
		this.particles[currentOffset++] = direction[0];
		this.particles[currentOffset++] = direction[1];
		this.particles[currentOffset++] = direction[2];
		this.particles[currentOffset++] = particleStartTime;
	}
	
	public void setup() {
		this.drawHandler.setup(this.particles);
	}
	
	public void draw() {
		this.drawHandler.draw();
	}
// GETTERS AND SETTERS -------------------------------------------------------------------------------------------------------
	public float[] getParticles() {
		return particles;
	}
	
	public void setDrawHandler(IntParticleDrawHandler drawHandler) {
		this.drawHandler = drawHandler;
	}
// ---------------------------------------------------------------------------------------------------------------------------
}
