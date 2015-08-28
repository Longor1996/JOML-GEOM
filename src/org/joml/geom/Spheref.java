package org.joml.geom;

import org.joml.Vector3f;

public class Spheref {
	public float centerX;
	public float centerY;
	public float centerZ;
	public float radius;
	
	public Spheref() {
		centerX = 0;
		centerY = 0;
		centerZ = 0;
		radius = 1;
	}
	
	public Spheref(float r) {
		centerX = 0;
		centerY = 0;
		centerZ = 0;
		radius = r;
	}
	
	public Spheref(float x, float y, float z, float r) {
		centerX = x;
		centerY = y;
		centerZ = z;
		radius = r;
	}
	
	public Spheref(Vector3f position, float r) {
		centerX = position.x;
		centerY = position.y;
		centerZ = position.z;
		radius = r;
	}
	
	public Vector3f getCenter(Vector3f store) {
		return store.set(centerX, centerY, centerZ);
	}
	
	public float getRadius() {
		return radius;
	}

	public float getRadiusSquared() {
		return radius*radius;
	}
	
	public Spheref setCenter(Vector3f load) {
		centerX = load.x;
		centerY = load.y;
		centerZ = load.z;
		return this;
	}
	
	public Spheref setCenter(float x, float y, float z) {
		centerX = x;
		centerY = y;
		centerZ = z;
		return this;
	}
	
	public Spheref setRadius(float r) {
		radius = r;
		return this;
	}
	
	public Spheref grow(float amount) {
		radius += amount;
		return this;
	}
	
	public Spheref shrink(float amount) {
		radius -= amount;
		return this;
	}
	
	public Spheref move(Vector3f load) {
		centerX += load.x;
		centerY += load.y;
		centerZ += load.z;
		return this;
	}
	
	public Spheref move(float x, float y, float z) {
		centerX += x;
		centerY += y;
		centerZ += z;
		return this;
	}
	
	public boolean intersect(Spheref sphereIn) {
		float offX = centerX - sphereIn.centerX;
		float offY = centerY - sphereIn.centerY;
		float offZ = centerZ - sphereIn.centerZ;
		return (offX*offX + offY*offY + offZ*offZ) <= (radius + sphereIn.radius);
	}
	
	public boolean intersect(Vector3f positionIn, float radiusIn) {
		float offX = centerX - positionIn.x;
		float offY = centerY - positionIn.y;
		float offZ = centerZ - positionIn.z;
		return (offX*offX + offY*offY + offZ*offZ) <= (radius + radiusIn);
	}
	
}
