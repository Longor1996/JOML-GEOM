package org.joml.geom;

import org.joml.FrustumCuller;
import org.joml.Vector3f;

/**
 * This class contains utility methods for intersections between various geometric shapes.
 * Note: The ray intersection methods always return the distance to the 'hit point', or positive infinity if there is no hit.
 **/
public class Intersections {
	// The vectors defined here are private for the
	// simple reason that they should NOT BE CHANGED.
	private static final Vector3f ORIGIN = new Vector3f();
	private static final Vector3f RIGHT = new Vector3f(1,0,0);
	private static final Vector3f UP    = new Vector3f(0,1,0);
	private static final Vector3f FRONT = new Vector3f(0,0,1);
	private static final Vector3f LEFT = new Vector3f(-1,0,0);
	private static final Vector3f DOWN = new Vector3f(0,-1,0);
	private static final Vector3f BACK = new Vector3f(0,0,-1);
	
	/**
	 * @return True, if the given {@link Aabbf} intersects the given frustum.
	 **/
	public static final boolean intersectAabbWithFrustum(Aabbf aabb, FrustumCuller culler) {
		return culler.isAabInsideFrustum(
				aabb.originX - aabb.extentX,
				aabb.originY - aabb.extentY,
				aabb.originZ - aabb.extentZ,
				aabb.originX + aabb.extentX,
				aabb.originY + aabb.extentY,
				aabb.originZ + aabb.extentZ
		) == -1;
	}
	
	/**
	 * @param aabb The AABB.
	 * @param position The center of the sphere.
	 * @param radius The radius of the sphere.
	 * @return True, if the given {@link Aabbf} overlaps with the sphere defined by the given position and radius. False if not.
	 **/
	public static final boolean intersectAabbWithSphere(Aabbf aabb, Vector3f position, float radius) {
		return aabb.minDistanceSquared(position) <= (radius*radius);
	}
	
	/**
	 * @param aabb The AABB.
	 * @param sphere The sphere
	 * @return True, if the given {@link Aabbf} overlaps with the given sphere. False if not.
	 **/
	public static final boolean intersectAabbWithSphere(Aabbf aabb, Spheref sphere) {
		return aabb.minDistanceSquared(sphere.centerX,sphere.centerY,sphere.centerZ) <= sphere.getRadiusSquared();
	}
	
	public static final boolean intersectAabbWithAabb(Aabbf aabbA, Aabbf aabbB) {
		return aabbA.intersect(aabbB);
	}
	
	public static final boolean intersectSphereWithSphere(Spheref sphereA, Spheref sphereB) {
		return sphereA.intersect(sphereB);
	}
	
	public static final boolean intersectSphereWithFrustum(Spheref sphere, FrustumCuller culler) {
		return culler.isSphereInsideFrustum(sphere.centerX, sphere.centerY, sphere.centerZ, sphere.radius);
	}
	
	/** Warning: Not yet tested. **/
	public static final float intersectRayWithPlane(Rayf ray, Vector3f normal, Vector3f point) {
		// unwrap ray onto stack
		float rayDirX = ray.directionX;
		float rayDirY = ray.directionY;
		float rayDirZ = ray.directionZ;
		float rayOrgX = ray.originX;
		float rayOrgY = ray.originY;
		float rayOrgZ = ray.originZ;
		
		// -((normal.dot(ray.origin) + (-normal.dot(Point))) / normal.dot(ray.direction));
		
		// float : NDR = NORMAL dot RAYDIR
		float NDR = normal.x*rayDirX + normal.y*rayDirY + normal.z*rayDirZ;
		
		// float : NNDP = negate (NORMAL dot POINT)
		float NNDP = -(normal.x*point.x + normal.y*point.y + normal.z*point.x);
		
		// float : NDO = NORMAL dot RAYPOS
		float NDO = normal.x*rayOrgX + normal.y*rayOrgY + normal.z*rayOrgZ;
		
		// float : RET = -((NDO + NNDP) / NDR)
		return -((NDO + NNDP) / NDR);
	}
	
	public static final float intersectRayWithPositiveXAxisPlane(Rayf ray) {
		return intersectRayWithPlane(ray, RIGHT, ORIGIN);
	}
	
	public static final float intersectRayWithPositiveYAxisPlane(Rayf ray) {
		return intersectRayWithPlane(ray, UP, ORIGIN);
	}
	
	public static final float intersectRayWithPositiveZAxisPlane(Rayf ray) {
		return intersectRayWithPlane(ray, FRONT, ORIGIN);
	}
	
	public static final float intersectRayWithNegativeXAxisPlane(Rayf ray) {
		return intersectRayWithPlane(ray, LEFT, ORIGIN);
	}
	
	public static final float intersectRayWithNegativeYAxisPlane(Rayf ray) {
		return intersectRayWithPlane(ray, DOWN, ORIGIN);
	}
	
	public static final float intersectRayWithNegativeZAxisPlane(Rayf ray) {
		return intersectRayWithPlane(ray, BACK, ORIGIN);
	}
	
	public static final float intersectRayWithPlaneInBox(Rayf ray, Vector3f normal, Vector3f point, Aabbf aabb) {
		float t = intersectRayWithPlane(ray, normal, point);
		
		float px = ray.originX + ray.directionX * t;
		float py = ray.originY + ray.directionY * t;
		float pz = ray.originZ + ray.directionZ * t;
		
		return aabb.inside(px, py, pz) ? t : Float.POSITIVE_INFINITY;
	}
	
	/** Warning: Not yet tested. **/
	public static final float intersectRayWithDisk(Rayf ray, Vector3f n, Vector3f p0, float radius) {
		float planeIntersect = intersectRayWithPlane(ray, n, p0);
		
		if (planeIntersect < Float.POSITIVE_INFINITY) {
			float pX = ray.originX + ray.directionX*planeIntersect;
			float pY = ray.originY + ray.directionY*planeIntersect;
			float pZ = ray.originZ + ray.directionZ*planeIntersect;
			
			float vX = pX - p0.x;
			float vY = pY - p0.y;
			float vZ = pZ - p0.z;
			
			float d2 = vX*vX+vY*vY+vZ*vZ;
			
			return (d2 <= radius*radius) ? planeIntersect : Float.POSITIVE_INFINITY;
		}
		
		return Float.POSITIVE_INFINITY;
	}
	
	public static final float intersectRayWithSphere(Rayf ray, Spheref sphere) {
		return intersectRayWithSphere(ray, sphere.centerX, sphere.centerY, sphere.centerZ, sphere.radius);
	}
	
	/** Warning: Not yet tested. **/
	public static final float intersectRayWithSphere(Rayf ray, float centerX, float centerY, float centerZ, float radius) {
		// unwrap ray onto stack
		float rayOrgX = ray.originX;
		float rayOrgY = ray.originY;
		float rayOrgZ = ray.originZ;
		float rayDirX = ray.directionX;
		float rayDirY = ray.directionY;
		float rayDirZ = ray.directionZ;
		
		float vX = centerX - rayOrgX;
		float vY = centerY - rayOrgY;
		float vZ = centerZ - rayOrgZ;
		
		float b = vX * rayDirX + vY * rayDirY + vZ * rayDirZ;
		float vDot = vX*vX+vY*vY+vZ*vZ;
		float disc = b*b - vDot + radius*radius;
		
		if (disc < 0)
			return Float.POSITIVE_INFINITY;
		
		float d = (float) Math.sqrt(disc);
		float t2 = b+d;
		
		if (t2 < 0)
			return Float.POSITIVE_INFINITY;
		
		float t1 = b-d;
		return (t1 > 0 ? t1 : t2);
	}
	
	public static final float intersect(Rayf ray, Aabbf aabb) {
		float lbX = aabb.originX - aabb.extentX;
		float lbY = aabb.originY - aabb.extentY;
		float lbZ = aabb.originZ - aabb.extentZ;
		
		float rtX = aabb.originX + aabb.extentX;
		float rtY = aabb.originY + aabb.extentY;
		float rtZ = aabb.originZ + aabb.extentZ;
		
		// ray.directionXYZ is unit direction vector of ray
		// take inverse of ray direction
		float dirfracX = 1.0f / ray.directionX;
		float dirfracY = 1.0f / ray.directionY;
		float dirfracZ = 1.0f / ray.directionZ;
		
		// LB.XYZ is the corner of AABB with minimal coordinates, RT.XYZ is maximal corner
		// ray.originXYZ is origin of ray
		float t1 = (lbX - ray.originX)*dirfracX;
		float t2 = (rtX - ray.originX)*dirfracX;
		float t3 = (lbY - ray.originY)*dirfracY;
		float t4 = (rtY - ray.originY)*dirfracY;
		float t5 = (lbZ - ray.originZ)*dirfracZ;
		float t6 = (rtZ - ray.originZ)*dirfracZ;
		
		// This is some insane min/max-ing.
		float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
		float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));
		
		float t = Float.POSITIVE_INFINITY;
		
		// if tmax < 0, ray (line) is intersecting AABB, but whole AABB is behind us
		if (tmax < 0)
		{
			t = tmax;
			return Float.POSITIVE_INFINITY;
		}
		
		// if tmin > tmax, ray doesn't intersect AABB
		if (tmin > tmax)
		{
			t = tmax;
			return Float.POSITIVE_INFINITY;
		}
		
		t = tmin;
		return t;
	}
	
	public static float intersectRayWithLine(Rayf ray, Vector3f lineStart,Vector3f lineEnd){
		float uX = ray.directionX;
		float uY = ray.directionY;
		float uZ = ray.directionZ;
		
		float vX = lineEnd.x - lineStart.x;
		float vY = lineEnd.y - lineStart.y;
		float vZ = lineEnd.z - lineStart.z;
		
		float wX = ray.originX - lineStart.x;
		float wY = ray.originY - lineStart.y;
		float wZ = ray.originZ - lineStart.z;
		
		float a = uX * uX + uY * uY + uZ * uZ;	// always >= 0
		float b = uX * vX + uY * vY + uZ * vZ;
		float c = vX * vX + vY * vY + vZ * vZ;	// always >= 0
		float d = uX * wX + uY * wY + uZ * wZ;
		float e = vX * wX + vY * wY + vZ * wZ;
		float D = (a*c) - (b*b);	// always >= 0
		float sc, sN, sD = D;	// sc = sN / sD, default sD = D >= 0
		float tc, tN, tD = D;	// tc = tN / tD, default tD = D >= 0
		
		final float epsilon = (float) Math.E;
		
		// compute the line parameters of the two closest points
		if (D < epsilon) {	// the lines are almost parallel
			sN = 0F;			// force using point P0 on segment S1
			sD = 1F;			// to prevent possible division by 0.0 later
			tN = e;
			tD = c;
		}
		else {				// get the closest points on the infinite lines
			sN = ((b*e) - (c*d));
			tN = ((a*e) - (b*d));
			if (sN < 0.0) {	// sc < 0 => the s=0 edge is visible
				sN = 0.0f;
				tN = e;
				tD = c;
			}
		}
		
		if (tN < 0.0) {		// tc < 0 => the t=0 edge is visible
			tN = 0.0f;
			// recompute sc for this edge
			if (-d < 0.0) {
				sN = 0.0f;
			} else {
				sN = -d;
				sD = a;
			}
		}
		else if (tN > tD) {	  // tc > 1 => the t=1 edge is visible
			tN = tD;
			// recompute sc for this edge
			if ((-d + b) < 0.0) {
				sN = 0;
			} else {
				sN = (-d + b);
				sD = a;
			}
		}
		
		// finally do the division to get sc and tc
		sc = (Math.abs(sN) < epsilon ? 0.0f : sN / sD);
		tc = (Math.abs(tN) < epsilon ? 0.0f : tN / tD);
		
		float uscX = uX * sc;
		float uscY = uY * sc;
		float uscZ = uZ * sc;
		
		float vtcX = vX * tc;
		float vtcY = vY * tc;
		float vtcZ = vZ * tc;
		
		// get the difference of the two closest points
		
		// = S1(sc) - S2(tc)
		float dpX = wX + (uscX - vtcX);
		float dpY = wY + (uscY - vtcY);
		float dpZ = wZ + (uscZ - vtcZ);
		
		float distToRay = (float) Math.sqrt(dpX*dpX+dpY*dpY+dpZ*dpZ);
		float uscLEN = (float) Math.sqrt(uscX*uscX+uscY*uscY+uscZ*uscZ);
		float vtcLEN = (float) Math.sqrt(vtcX*vtcX+vtcY*vtcY+vtcZ*vtcZ);
		
		float distToRayOrigin = (float) Math.min(uscLEN, vtcLEN);
		
		// return the distance to the point that is the nearest.
		return distToRay < 0.1 ? distToRayOrigin : Float.POSITIVE_INFINITY;
	}
	
}
