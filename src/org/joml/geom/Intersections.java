package org.joml.geom;

import org.joml.FrustumCuller;
import org.joml.Vector3f;

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
	
}
