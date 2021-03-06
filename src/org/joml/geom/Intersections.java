package org.joml.geom;

import org.joml.FrustumCuller;
import org.joml.Vector3f;

/**
 * This class contains utility methods for intersections between various geometric shapes.
 * Note: The ray intersection methods always return the distance to the 'hit point', or positive infinity if there is no hit.
 * Note: No validations what-so-ever are done on the input by this class. NONE!
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
	
	public static final boolean intersectAabbWithAabb(Aabbf aabb,
			float extentX, float extentY, float extentZ, float originX, float originY, float originZ) {
		// XXX: The 'abs'-method could be inlined manually here.
		if (abs(originX - aabb.originX) >= (extentX + aabb.extentX) ) return false;
		if (abs(originY - aabb.originY) >= (extentY + aabb.extentY) ) return false;
		if (abs(originZ - aabb.originZ) >= (extentZ + aabb.extentZ) ) return false;
		
		// We have an overlap
		return true;
	}
	
	public static final boolean intersectAabbWithAabb(
			float extentX, float extentY, float extentZ, float originX, float originY, float originZ,
			float extentXb, float extentYb, float extentZb, float originXb, float originYb, float originZb) {
		// XXX: The 'abs'-method could be inlined manually here.
		if (abs(originX - originXb) >= (extentX + extentXb) ) return false;
		if (abs(originY - originYb) >= (extentY + extentYb) ) return false;
		if (abs(originZ - originZb) >= (extentZ + extentZb) ) return false;
		
		// We have an overlap
		return true;
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
		float NNDP = -(normal.x*point.x + normal.y*point.y + normal.z*point.z);
		
		// float : NDO = NORMAL dot RAYPOS
		float NDO = normal.x*rayOrgX + normal.y*rayOrgY + normal.z*rayOrgZ;
		
		// float : RET = -((NDO + NNDP) / NDR)
		float T = -((NDO + NNDP) / NDR);
		
		return T > 0 ? T : Float.POSITIVE_INFINITY;
	}
	
	/** Warning: Not yet tested. **/
	public static final float intersectRayWithPlane(Rayf ray, Vector3f normal, float pointX, float pointY, float pointZ) {
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
		float NNDP = -(normal.x*pointX + normal.y*pointY + normal.z*pointZ);
		
		// float : NDO = NORMAL dot RAYPOS
		float NDO = normal.x*rayOrgX + normal.y*rayOrgY + normal.z*rayOrgZ;
		
		// float : RET = -((NDO + NNDP) / NDR)
		float T = -((NDO + NNDP) / NDR);
		
		return T > 0 ? T : Float.POSITIVE_INFINITY;
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
	
	public static final float intersectRayWithPlaneInBox(Rayf ray, Vector3f normal, float pointX, float pointY, float pointZ, Aabbf aabb) {
		float t = intersectRayWithPlane(ray, normal, pointX, pointY, pointZ);
		
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
	
	public static final float intersectRayWithAabb(Rayf ray, Aabbf aabb) {
		float tTop   = intersectRayWithPlaneInBox(ray, Intersections.UP   , 0,aabb.getMaxY(),0, aabb);
		float tBottom= intersectRayWithPlaneInBox(ray, Intersections.DOWN , 0,aabb.getMinY(),0, aabb);
		
		float tLeft  = intersectRayWithPlaneInBox(ray, Intersections.LEFT , aabb.getMinX(),0,0, aabb);
		float tRight = intersectRayWithPlaneInBox(ray, Intersections.RIGHT, aabb.getMaxX(),0,0, aabb);
		
		float tFront = intersectRayWithPlaneInBox(ray, Intersections.FRONT, 0,0,aabb.getMaxZ(), aabb);
		float tBack  = intersectRayWithPlaneInBox(ray, Intersections.BACK , 0,0,aabb.getMinZ(), aabb);
		
		return Math.min(tTop, Math.min(tBottom, Math.min(tLeft, Math.min(tRight, Math.min(tFront, tBack)))));
	}
	
	public static final float intersectRayWithAabb_doesNotWork_doNotUse(Rayf ray, Aabbf aabb) {
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
	
	public static final float intersectRayWithTriangle(Rayf ray, Vector3f point1, Vector3f point2, Vector3f point3){
		// unwrap ray onto stack
		float rayOrgX = ray.originX;
		float rayOrgY = ray.originY;
		float rayOrgZ = ray.originZ;
		float rayDirX = ray.directionX;
		float rayDirY = ray.directionY;
		float rayDirZ = ray.directionZ;
		
		float edge1X = point2.x - point1.x;
		float edge1Y = point2.y - point1.y;
		float edge1Z = point2.z - point1.z;
		
		float edge2X = point3.x - point1.x;
		float edge2Y = point3.y - point1.y;
		float edge2Z = point3.z - point1.z;
		
		// Find the cross product of edge2 and the ray direction
		float s1X = rayDirY * edge2Z - rayDirZ * edge2Y;
		float s1Y = rayDirZ * edge2X - rayDirX * edge2Z;
		float s1Z = rayDirX * edge2Y - rayDirY * edge2X;
		
		// Find the divisor, if its zero, return false as the triangle is
		// degenerated
		final float divisor = s1X*edge1X + s1Y*edge1Y + s1Z*edge1Z;
		
		if (divisor == 0.0) {
			return Float.POSITIVE_INFINITY;
		}
		
		// A inverted divisor, as multiplying is faster then division
		final float invDivisor = 1 / divisor;
		
		// Calculate the first barycentic coordinate. Barycentic coordinates
		// are between 0.0 and 1.0
		final float distanceX = rayOrgX - point1.x;
		final float distanceY = rayOrgY - point1.y;
		final float distanceZ = rayOrgZ - point1.z;
		
		final float barycCoord_1 = (distanceX*s1X+distanceY*s1Y+distanceZ*s1Z) * invDivisor;
		
		if ((barycCoord_1 < 0.0) || (barycCoord_1 > 1.0)) {
			return Float.POSITIVE_INFINITY;
		}
		
		final float s2X = distanceY * edge1Z - distanceZ * edge1Y;
		final float s2Y = distanceZ * edge1X - distanceX * edge1Z;
		final float s2Z = distanceX * edge1Y - distanceY * edge1X;
			
		final float barycCoord_2 = (rayDirX*s2X+rayDirY*s2Y+rayDirZ*s2Z) * invDivisor;
		
		if ((barycCoord_2 < 0.0) || ((barycCoord_1 + barycCoord_2) > 1.0)) {
			return Float.POSITIVE_INFINITY;
		}
		
		// After doing the barycentic coordinate test we know if the ray hits or
		// not. If we got this far the ray hits.
		// Calculate the distance to the intersection point
		final float intersectionDistance = (edge2X*s2X+edge2Y*s2Y+edge2Z*s2Z) * invDivisor;
		
		return intersectionDistance >= 0 ? intersectionDistance : Float.POSITIVE_INFINITY;
	}
	
	/**
	 * @return <ul>
	 * 					<li> 0: No Intersection
	 * 					<li> 1: Point Intersection
	 * 					<li> 2: Line Intersection
	 * 				</ul>
	 **/
	public static final int intersectLineWithPlane(Vector3f Sp0, Vector3f Sp1, Vector3f pNormal, Vector3f pPoint, Vector3f store) {
		// Vector3f  u = Sp1 - Sp0;
		float uX = Sp1.x - Sp0.x;
		float uY = Sp1.y - Sp0.y;
		float uZ = Sp1.z - Sp0.z;
		
		// Vector3f  w = Sp0 - Pn.V0;
		float wX = Sp0.x - pPoint.x;
		float wY = Sp0.y - pPoint.y;
		float wZ = Sp0.z - pPoint.z;
		
		float D = pNormal.x*uX+pNormal.y*uY+pNormal.z*uZ; // normal DOT u
		float N = -(pNormal.x*wX+pNormal.y*wY+pNormal.z*wZ); // normal DOT w
		
		if (abs(D) < Float.MIN_VALUE) {
			// segment is parallel to plane
			if (N == 0)
				// segment lies in plane
				return 2;
			else
				// no intersection
				return 0;
		}
		
		// they are not parallel
		// compute intersect parameter
		float sI = N / D;
		if (sI < 0 || sI > 1)
			// no intersection
			return 0;
		
		// compute segment intersect point
		// store = S.P0 + sI * u;
		store.set(
				Sp0.x + sI * uX,
				Sp0.y + sI * uY,
				Sp0.z + sI * uZ
		);
		
		return 1;
	}
	
	/**
	 * @return <ul>
	 * 					<li> 0: No Intersection
	 * 					<li> 1: Point Intersection
	 * 					<li> 2: Line Intersection
	 * 				</ul>
	 **/
	public static final int intersectLineWithPlane(
			float Sp0X, float Sp0Y, float Sp0Z,
			float Sp1X, float Sp1Y, float Sp1Z,
			Vector3f pNormal, Vector3f pPoint, Vector3f store) {
		// Vector3f  u = Sp1 - Sp0;
		float uX = Sp1X - Sp0X;
		float uY = Sp1Y - Sp0Y;
		float uZ = Sp1Z - Sp0Z;
		
		// Vector3f  w = Sp0 - Pn.V0;
		float wX = Sp0X - pPoint.x;
		float wY = Sp0Y - pPoint.y;
		float wZ = Sp0Z - pPoint.z;
		
		float D = pNormal.x*uX+pNormal.y*uY+pNormal.z*uZ; // normal DOT u
		float N = -(pNormal.x*wX+pNormal.y*wY+pNormal.z*wZ); // normal DOT w
		
		if (abs(D) < Float.MIN_VALUE) {
			// segment is parallel to plane
			if (N == 0)
				// segment lies in plane
				return 2;
			else
				// no intersection
				return 0;
		}
		
		// they are not parallel
		// compute intersect parameter
		float sI = N / D;
		if (sI < 0 || sI > 1)
			// no intersection
			return 0;
		
		// compute segment intersect point
		// store = S.P0 + sI * u;
		store.set(
				Sp0X + sI * uX,
				Sp0Y + sI * uY,
				Sp0Z + sI * uZ
		);
		
		return 1;
	}
	
	/**
	 * This method uses a given plane to cut trough a given AABB, producing 0..6 points.
	 *
	 * @param pNormal The normal vector of the cutting plane.
	 * @param pPoint A point that is located on the cutting plane.
	 * @param aabb The AABB to cut trough.
	 * @param store A array of 6 {@link Vector3f} in which the cutting points will be stored in.
	 **/
	public static final int intersectAabbWithPlane(Vector3f pNormal, Vector3f pPoint, Aabbf aabb, Vector3f[] store) {
		float minX = aabb.originX - aabb.extentX;
		float minY = aabb.originY - aabb.extentY;
		float minZ = aabb.originZ - aabb.extentZ;
		float maxX = aabb.originX + aabb.extentX;
		float maxY = aabb.originY + aabb.extentY;
		float maxZ = aabb.originZ + aabb.extentZ;
		
		int pointPtr = 0;
		int maximum = 6;
		
		// TOP TO BOTTOM
		if(intersectLineWithPlane(minX, minY, minZ, minX, maxY, minZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(maxX, minY, minZ, maxX, maxY, minZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(maxX, minY, maxZ, maxX, maxY, maxZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(minX, minY, maxZ, minX, maxY, maxZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		
		// BOTTOM
		if(intersectLineWithPlane(minX, minY, minZ, maxX, minY, minZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(minX, minY, maxZ, maxX, minY, maxZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(minX, minY, minZ, minX, minY, maxZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(maxX, minY, minZ, maxX, minY, maxZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		
		// TOP
		if(intersectLineWithPlane(minX, maxY, minZ, maxX, maxY, minZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(minX, maxY, maxZ, maxX, maxY, maxZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(minX, maxY, minZ, minX, maxY, maxZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		if(intersectLineWithPlane(maxX, maxY, minZ, maxX, maxY, maxZ, pNormal, pPoint, store[pointPtr]) == 1) {
			if(++pointPtr >= maximum)return pointPtr;
		}
		
		
		return pointPtr;
	}
	
	private static float abs(final float x) {
		return Float.intBitsToFloat(0x7fffffff & Float.floatToRawIntBits(x));
	}
	
}
