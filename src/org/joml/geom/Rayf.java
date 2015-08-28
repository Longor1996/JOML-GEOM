package org.joml.geom;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Rayf {
	public float originX;
	public float originY;
	public float originZ;
	public float directionX;
	public float directionY;
	public float directionZ;
	
	/** Creates a new {@link Rayf} located at (0,0,0) pointing at (0,0,1)/positive Z. **/
	public Rayf() {
		originX = 0;
		originY = 0;
		originZ = 0;
		directionX = 0;
		directionY = 0;
		directionZ = 1;
	}
	
	/** Creates a new {@link Rayf} located at (0,0,0) pointing in the given direction. **/
	public Rayf(float dirX, float dirY, float dirZ) {
		originX = 0;
		originY = 0;
		originZ = 0;
		directionX = dirX;
		directionY = dirY;
		directionZ = dirZ;
	}
	
	/** Creates a new {@link Rayf} located at a given pointing in the given direction. **/
	public Rayf(float dirX, float dirY, float dirZ, float orgX, float orgY, float orgZ) {
		originX = orgX;
		originY = orgY;
		originZ = orgZ;
		directionX = dirX;
		directionY = dirY;
		directionZ = dirZ;
	}
	
	/** Creates a new {@link Rayf} located at (0,0,0) pointing in the given direction. **/
	public Rayf(Vector3f direction) {
		originX = 0;
		originY = 0;
		originZ = 0;
		directionX = direction.x;
		directionY = direction.y;
		directionZ = direction.z;
	}
	
	/** Creates a new {@link Rayf} located at a given pointing in the given direction. **/
	public Rayf(Vector3f direction, Vector3f origin) {
		originX = origin.x;
		originY = origin.y;
		originZ = origin.z;
		directionX = direction.x;
		directionY = direction.y;
		directionZ = direction.z;
	}
	
	public Vector3f getDirection(Vector3f store) {
		return store.set(directionX, directionY, directionZ);
	}
	
	public Vector3f getOrigin(Vector3f store) {
		return store.set(originX, originY, originZ);
	}
	
	public Vector3f trace(float t, Vector3f store) {
		return store.set(originX+directionX*t, originY+directionY*t, originZ+directionZ*t);
	}
	
	public Vector3f traceReverse(float t, Vector3f store) {
		return store.set(originX-directionX*t, originY-directionY*t, originZ-directionZ*t);
	}
	
	public Rayf move(float x, float y, float z) {
		originX += x;
		originY += y;
		originZ += z;
		return this;
	}
	
	public Rayf move(Vector3f load) {
		originX += load.x;
		originY += load.y;
		originZ += load.z;
		return this;
	}
	
	public Rayf transform(Matrix4f load, Vector4f store, boolean normalizeDirection) {
		// Transform Origin
		{
			store.set(originX, originY, originZ, 1f);
			load.transform(store);
			originX = store.x;
			originY = store.y;
			originZ = store.z;
		}
		
		// Transform Direction
		{
			store.set(directionX, directionY, directionZ, 1f);
			load.transform(store);
			
			if(normalizeDirection) {
				store.normalize();
			}
			
			directionX = store.x;
			directionY = store.y;
			directionZ = store.z;
		}
		
		return this;
	}
	
}
