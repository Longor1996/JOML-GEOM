package org.joml.geom;

import org.joml.FrustumCuller;
import org.joml.Vector3f;

/**
 * This class contains utility methods for intersections between various geometric shapes.
 * Note: The ray intersection methods always return the distance to the 'hit point', or positive infinity if there is no hit.
 **/
public class Intersections {
	
	/**
	 * @return True, if the given {@link AABBf} intersects the given frustum.
	 **/
	public static final boolean intersectAabbWithFrustum(AABBf aabb, FrustumCuller culler) {
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
	 * @return True, if the given {@link AABBf} overlaps with the sphere defined by the given position and radius. False if not.
	 **/
	public static final boolean intersectAabbWithSphere(AABBf aabb, Vector3f position, float radius) {
		return aabb.minDistanceSquared(position) <= (radius*radius);
	}
	
	/**
	 * @param aabb The AABB.
	 * @param sphere The sphere
	 * @return True, if the given {@link AABBf} overlaps with the given sphere. False if not.
	 **/
	public static final boolean intersectAabbWithSphere(AABBf aabb, Spheref sphere) {
		return aabb.minDistanceSquared(sphere.centerX,sphere.centerY,sphere.centerZ) <= sphere.getRadiusSquared();
	}
	
	public static final boolean intersectAabbWithAabb(AABBf aabbA, AABBf aabbB) {
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
	
	public static final float intersect(Rayf ray, AABBf aabb) {
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
}
