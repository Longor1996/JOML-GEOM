package org.joml.geom;

import org.joml.FrustumCuller;
import org.joml.Vector3f;

public class Intersections {
	
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
	 * @return True, if the given {@link AABBf} overlaps the sphere defined by the given position and radius. False if not.
	 **/
	public static final boolean intersectAabbWithSphere(AABBf aabb, Vector3f position, float radius) {
		return aabb.minDistanceSquared(position) <= (radius*radius);
	}
	
	public static final boolean intersectAabbWithAabb(AABBf aabbA, AABBf aabbB) {
		return aabbA.overlap(aabbB);
	}
	
}
