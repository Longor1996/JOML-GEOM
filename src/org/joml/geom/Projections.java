package org.joml.geom;

import org.joml.Vector3f;

public class Projections {
	
	// Projects the given point to the nearest point on the given plane.
	public static final Vector3f projectPointOntoPlane(Vector3f point, Vector3f planeNormal, Vector3f planePoint, Vector3f store) {
		// q = point
		// p = plane point
		// n = plane normal
		
		// q - p   -> vec3
		float tX = point.x - planePoint.x;
		float tY = point.y - planePoint.y;
		float tZ = point.z - planePoint.z;
		
		// $ dot n -> float
		float ndotqp = tX*planeNormal.x + tY*planeNormal.y + tZ*planeNormal.z;
		
		// $ mul n -> vec3
		tX = planeNormal.x * ndotqp;
		tY = planeNormal.y * ndotqp;
		tZ = planeNormal.z * ndotqp;
		
		// q - $
		tX = point.x - tX;
		tY = point.y - tY;
		tZ = point.z - tZ;
		
		store.x = tX;
		store.y = tY;
		store.z = tZ;
		
		return store;
	}
	
}
