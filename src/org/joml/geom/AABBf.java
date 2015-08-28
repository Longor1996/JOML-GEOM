package org.joml.geom;

import java.util.Collection;

import org.joml.Vector3f;

/**
 * A axis aligned bounding box.
 *
 * <br><br>
 * <b>Important:</b>
 * <ul>
 * <li>The 'extent' of a {@link AABBf} is always <i>half the actual size</i> of the {@link AABBf}.
 * <li>The 'origin' of a {@link AABBf} is simply the location of the center of the {@link AABBf}.
 * </ul>
 **/
public class AABBf {
	
	/** The extent of the AABB on the X-axis. **/
	public float extentX;
	/** The extent of the AABB on the Y-axis. **/
	public float extentY;
	/** The extent of the AABB on the Z-axis. **/
	public float extentZ;
	
	/** The origin of the AABB on the X-Axis. **/
	public float originX;
	/** The origin of the AABB on the Y-Axis. **/
	public float originY;
	/** The origin of the AABB on the Z-Axis. **/
	public float originZ;
	
	/**
	 * Creates a new {@link AABBf} with a extent of (1,1,1) located at (0,0,0).
	 **/
	public AABBf() {
		extentX = 1;
		extentY = 1;
		extentZ = 1;
		originX = 0;
		originY = 0;
		originZ = 0;
	}
	
	/**
	 * Creates a new {@link AABBf}, copying the extent and origin from the given {@link AABBf}.
	 * @param load The {@link AABBf} to copy the extent and origin from.
	 **/
	public AABBf(AABBf load) {
		extentX = load.extentX;
		extentY = load.extentY;
		extentZ = load.extentZ;
		originX = load.originX;
		originY = load.originY;
		originZ = load.originZ;
	}
	
	/**
	 * Creates a new {@link AABBf} with the given extent, located at (0,0,0).
	 * @param extent The extent of the new {@link AABBf}.
	 **/
	public AABBf(Vector3f extent) {
		extentX = extent.x;
		extentY = extent.y;
		extentZ = extent.z;
		originX = 0;
		originY = 0;
		originZ = 0;
	}
	
	/**
	 * Creates a new {@link AABBf} with the given extent at the given location.
	 * @param extent The extent of the new {@link AABBf}.
	 * @param origin The location of the new {@link AABBf}.
	 **/
	public AABBf(Vector3f extent, Vector3f origin) {
		extentX = extent.x;
		extentY = extent.y;
		extentZ = extent.z;
		originX = origin.x;
		originY = origin.y;
		originZ = origin.z;
	}
	
	/**
	 * Creates a new {@link AABBf} with the given extent at the given location.
	 * @param extentXin The extent of this {@link AABBf} on the X-Axis.
	 * @param extentYin The extent of this {@link AABBf} on the Y-Axis.
	 * @param extentZin The extent of this {@link AABBf} on the Z-Axis.
	 * @param originXin The origin of this {@link AABBf} on the X-Axis.
	 * @param originYin The origin of this {@link AABBf} on the Y-Axis.
	 * @param originZin The origin of this {@link AABBf} on the Z-Axis.
	 **/
	public AABBf(float extentXin, float extentYin, float extentZin, float originXin, float originYin, float originZin) {
		extentX = extentXin;
		extentY = extentYin;
		extentZ = extentZin;
		originX = originXin;
		originY = originYin;
		originZ = originZin;
	}
	
	/**
	 * Creates a new {@link AABBf} with the given extent, located at (0,0,0).
	 * @param extentXin The extent of this {@link AABBf} on the X-Axis.
	 * @param extentYin The extent of this {@link AABBf} on the Y-Axis.
	 * @param extentZin The extent of this {@link AABBf} on the Z-Axis.
	 **/
	public AABBf(float extentXin, float extentYin, float extentZin) {
		extentX = extentXin;
		extentY = extentYin;
		extentZ = extentZin;
		originX = 0;
		originY = 0;
		originZ = 0;
	}
	
	/**
	 * Stores the origin of this {@link AABBf} in the given vector.
	 * @param store The vector to store the origin in.
	 * @return The vector.
	 **/
	public Vector3f getOrigin(Vector3f store) {
		return store.set(originX, originY, originZ);
	}
	
	/**
	 * Stores the extent of this {@link AABBf} in the given vector.
	 * @param store The vector to store the extent in.
	 * @return The vector.
	 **/
	public Vector3f getExtent(Vector3f store) {
		return store.set(extentX, extentY, extentZ);
	}
	
	/**
	 * Stores the minimum corner of this {@link AABBf} in the given vector.
	 * @param store The vector to store the minimum corner in.
	 * @return The vector.
	 **/
	public Vector3f getMinimum(Vector3f store) {
		return store.set(originX-extentX, originY-extentY, originZ-extentZ);
	}
	
	/**
	 * Stores the maximum corner of this {@link AABBf} in the given vector.
	 * @param store The vector to store the maximum corner in.
	 * @return The vector.
	 **/
	public Vector3f getMaximum(Vector3f store) {
		return store.set(originX+extentX, originY+extentY, originZ+extentZ);
	}
	
	/**
	 * Stores the minimum and maximum corner of this {@link AABBf} in the given vectors.
	 * @param minstore The vector to store the minimum corner in.
	 * @param maxstore The vector to store the maximum corner in.
	 * @return This {@link AABBf}.
	 **/
	public AABBf getMinAndMax(Vector3f minstore, Vector3f maxstore) {
		minstore.set(originX-extentX, originY-extentY, originZ-extentZ);
		maxstore.set(originX+extentX, originY+extentY, originZ+extentZ);
		return this;
	}
	
	/**
	 * Sets the extent of this {@link AABBf} to be the (x,y,z) components of the passed in vector.
	 * @param load The vector that holds the new extent of the {@link AABBf}.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setExtent(Vector3f load) {
		extentX = load.x;
		extentY = load.y;
		extentZ = load.z;
		return this;
	}
	
	/**
	 * Sets the origin of this {@link AABBf} to be the (x,y,z) components of the passed in vector.
	 * @param load The vector that holds the new origin of the {@link AABBf}.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setOrigin(Vector3f load) {
		originX = load.x;
		originY = load.y;
		originZ = load.z;
		return this;
	}
	
	/**
	 * Sets the extent of this {@link AABBf} to be the passed in (x,y,z) components.
	 * @param x The new extent of this {@link AABBf} on the X-Axis.
	 * @param y The new extent of this {@link AABBf} on the Y-Axis.
	 * @param z The new extent of this {@link AABBf} on the Z-Axis.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setExtent(float x, float y, float z) {
		extentX = x;
		extentY = y;
		extentZ = z;
		return this;
	}
	
	/**
	 * Sets the origin of this {@link AABBf} to be the passed in (x,y,z) components.
	 * @param x The new origin of this {@link AABBf} on the X-Axis.
	 * @param y The new origin of this {@link AABBf} on the Y-Axis.
	 * @param z The new origin of this {@link AABBf} on the Z-Axis.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setOrigin(float x, float y, float z) {
		originX = x;
		originY = y;
		originZ = z;
		return this;
	}
	
	/**
	 * Sets the extent and origin of this {@link AABBf} to the given values.
	 * @param extentXin The extent of this {@link AABBf} on the X-Axis.
	 * @param extentYin The extent of this {@link AABBf} on the Y-Axis.
	 * @param extentZin The extent of this {@link AABBf} on the Z-Axis.
	 * @param originXin The origin of this {@link AABBf} on the X-Axis.
	 * @param originYin The origin of this {@link AABBf} on the Y-Axis.
	 * @param originZin The origin of this {@link AABBf} on the Z-Axis.
	 * @return This {@link AABBf}.
	 **/
	public AABBf set(float extentXin, float extentYin, float extentZin, float originXin, float originYin, float originZin) {
		extentX = extentXin;
		extentY = extentYin;
		extentZ = extentZin;
		originX = originXin;
		originY = originYin;
		originZ = originZin;
		return this;
	}
	
	/**
	 * Sets the extent and origin of this {@link AABBf} to the values held by the given vectors.
	 * @param extent The new extent of the {@link AABBf}.
	 * @param origin The new location of the {@link AABBf}.
	 **/
	public AABBf set(Vector3f extent, Vector3f origin) {
		extentX = extent.x;
		extentY = extent.y;
		extentZ = extent.z;
		originX = origin.x;
		originY = origin.y;
		originZ = origin.z;
		return this;
	}
	
	/**
	 * Sets the extent and origin of this {@link AABBf} to be the same as the given {@link AABBf}.
	 * @param load The {@link AABBf} to copy the extent and origin from.
	 **/
	public AABBf set(AABBf load) {
		extentX = load.extentX;
		extentY = load.extentY;
		extentZ = load.extentZ;
		originX = load.originX;
		originY = load.originY;
		originZ = load.originZ;
		return this;
	}
	
	/**
	 * Sets the size of this {@link AABBf} to be the (x,y,z) components of the passed in vector.
	 * @param load The vector that holds the new size of the {@link AABBf}.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setSize(Vector3f load) {
		extentX = load.x/2f;
		extentY = load.y/2f;
		extentZ = load.z/2f;
		return this;
	}
	
	/**
	 * Sets the size of this {@link AABBf} to be the passed in (x,y,z) components.
	 * @param x The new size of this {@link AABBf} on the X-Axis.
	 * @param y The new size of this {@link AABBf} on the Y-Axis.
	 * @param z The new size of this {@link AABBf} on the Z-Axis.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setSize(float width, float height, float length) {
		extentX = width/2f;
		extentY = height/2f;
		extentZ = length/2f;
		return this;
	}
	
	/**
	 * Sets the width of this {@link AABBf} to the given size.
	 * @param width The new width of this {@link AABBf}.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setWidth(float width) {
		extentX = width/2f;
		return this;
	}
	
	/**
	 * Sets the height of this {@link AABBf} to the given size.
	 * @param height The new height of this {@link AABBf}.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setHeight(float height) {
		extentY = height/2f;
		return this;
	}
	
	/**
	 * Sets the length of this {@link AABBf} to the given size.
	 * @param length The new length of this {@link AABBf}.
	 * @return This {@link AABBf}.
	 **/
	public AABBf setLength(float length) {
		extentZ = length/2f;
		return this;
	}
	
	/**
	 * Makes sure the extent is not negative.
	 * @return This {@link AABBf}.
	 **/
	public AABBf correctExtent() {
		extentX = extentX < 0 ? -extentX : extentX;
		extentY = extentY < 0 ? -extentY : extentY;
		extentZ = extentZ < 0 ? -extentZ : extentZ;
		return this;
	}
	
	/**
	 * Moves the origin of this {@link AABBf} by the given vector.
	 * @param load The vector to add to the origin.
	 * @return This {@link AABBf}.
	 **/
	public AABBf move(Vector3f load) {
		originX += load.x;
		originY += load.y;
		originZ += load.z;
		return this;
	}
	
	/**
	 * Moves the origin of this {@link AABBf} by the given vector.
	 * @param x The x-component of the vector to add to the origin.
	 * @param y The y-component of the vector to add to the origin.
	 * @param z The z-component of the vector to add to the origin.
	 * @return This {@link AABBf}.
	 **/
	public AABBf move(float x, float y, float z) {
		originX += x;
		originY += y;
		originZ += z;
		return this;
	}
	
	/**
	 * Adds the given amount to this {@link AABBf}'s extent.
	 * @param amount The amount to add to this {@link AABBf}'s extent.
	 **/
	public AABBf grow(float amount) {
		extentX += amount;
		extentY += amount;
		extentZ += amount;
		return this;
	}
	
	/**
	 * Subtracts the given amount from this {@link AABBf}'s extent.
	 * @param amount The amount to subtract from this {@link AABBf}'s extent.
	 **/
	public AABBf shrink(float amount) {
		extentX -= amount;
		extentY -= amount;
		extentZ -= amount;
		return this;
	}
	
	/**
	 * Adds the given amount to this {@link AABBf}'s extent.
	 * @param amount The vector holding the amount to add to this {@link AABBf}'s extent.
	 **/
	public AABBf grow(Vector3f amount) {
		extentX += amount.x;
		extentY += amount.y;
		extentZ += amount.z;
		return this;
	}
	
	/**
	 * Subtracts the given amount from this {@link AABBf}'s extent.
	 * @param amount The vector holding the amount to subtract from this {@link AABBf}'s extent.
	 **/
	public AABBf shrink(Vector3f amount) {
		extentX -= amount.x;
		extentY -= amount.y;
		extentZ -= amount.z;
		return this;
	}
	
	public boolean intersect(AABBf aabb) {
		// XXX: The 'abs'-method could be inlined manually here.
		if (abs(originX - aabb.originX) >= (extentX + aabb.extentX) ) return false;
		if (abs(originY - aabb.originY) >= (extentY + aabb.extentY) ) return false;
		if (abs(originZ - aabb.originZ) >= (extentZ + aabb.extentZ) ) return false;
		
		// We have an overlap
		return true;
	}
	
	public static float abs(final float x) {
		return Float.intBitsToFloat(0x7fffffff & Float.floatToRawIntBits(x));
	}
	
	/**
	 * Calculates the minimum distance between this {@link AABBf} and the given point.
	 * @return The squared minimum distance between this {@link AABBf} and the given point.
	 **/
	public float minDistanceSquared(Vector3f point) {
		// Squared distance
		float sq = 0f;
		sq += minDistanceSquared_sub( point.x, originX-extentX, originX+extentX);
		sq += minDistanceSquared_sub( point.y, originY-extentY, originY+extentY);
		sq += minDistanceSquared_sub( point.z, originZ-extentZ, originZ+extentZ);
		return sq;
	}
	
	/**
	 * Calculates the minimum distance between this {@link AABBf} and the given point.
	 * @return The squared minimum distance between this {@link AABBf} and the given point.
	 **/
	public float minDistanceSquared(float x, float y, float z) {
		// Squared distance
		float sq = 0f;
		sq += minDistanceSquared_sub( x, originX-extentX, originX+extentX);
		sq += minDistanceSquared_sub( y, originY-extentY, originY+extentY);
		sq += minDistanceSquared_sub( z, originZ-extentZ, originZ+extentZ);
		return sq;
	}
	
	/**
	 * Calculates the minimum distance between this {@link AABBf} and the given point.
	 * @return The minimum distance between this {@link AABBf} and the given point.
	 **/
	public float minDistance(Vector3f point) {
		return (float) Math.sqrt(minDistanceSquared(point));
	}
	
	private final float minDistanceSquared_sub(final float pn, final float bmin, final float bmax ) {
		float out = 0;
		float val = 0;
		
		if(pn < bmin) {
			val = (bmin - pn);
			out += val * val;
		}
		
		if(pn > bmax) {
			val = (pn - bmax);
			out += val * val;
		}
		
		return out;
	}
	
	/**
	 * Calculates the overlap between this {@link AABBf} and the 'load' {@link AABBf}, and stores the result in the 'store' {@link AABBf}.
	 * @param load The bounding box to overlap with.
	 * @param store The bounding box to store the overlap region in.
	 * @return True, if the {@link AABBf}'s are overlapping. False if not.
	 **/
	public boolean overlapBoxes(AABBf load, AABBf store) {
		// Check if there is any overlap...
		if (abs(originX - load.originX) >= (extentX + load.extentX) ) return false;
		if (abs(originY - load.originY) >= (extentY + load.extentY) ) return false;
		if (abs(originZ - load.originZ) >= (extentZ + load.extentZ) ) return false;
		
		// There is overlap, continue...
		
		// Get MIN/MAX points for A ('this')
		float AminX = originX - extentX;
		float AminY = originY - extentY;
		float AminZ = originZ - extentZ;
		float AmaxX = originX + extentX;
		float AmaxY = originY + extentY;
		float AmaxZ = originZ + extentZ;
		
		// Get MIN/MAX points for B ('load')
		float BminX = load.originX - load.extentX;
		float BminY = load.originY - load.extentY;
		float BminZ = load.originZ - load.extentZ;
		float BmaxX = load.originX + load.extentX;
		float BmaxY = load.originY + load.extentY;
		float BmaxZ = load.originZ + load.extentZ;
		
		// Extract overlap...
		float OUTminX = Math.max(AminX, BminX);
		float OUTminY = Math.max(AminY, BminY);
		float OUTminZ = Math.max(AminZ, BminZ);
		
		float OUTmaxX = Math.min(AmaxX, BmaxX);
		float OUTmaxY = Math.min(AmaxY, BmaxY);
		float OUTmaxZ = Math.min(AmaxZ, BmaxZ);
		
		// Calculate extents and origin for overlap...
		// overlap.origin = (min+max)/2 = minPlusMax/2
		float OUToriginX = (OUTminX+OUTmaxX) / 2f;
		float OUToriginY = (OUTminY+OUTmaxY) / 2f;
		float OUToriginZ = (OUTminZ+OUTmaxZ) / 2f;
		
		// overlap.extent = (max-min)/2 = size/2
		float OUTextentX = (OUTmaxX-OUTminX) / 2f;
		float OUTextentY = (OUTmaxY-OUTminY) / 2f;
		float OUTextentZ = (OUTmaxZ-OUTminZ) / 2f;
		
		// Store result in 'store'!
		store.set(OUTextentX, OUTextentY, OUTextentZ, OUToriginX, OUToriginY, OUToriginZ);
		
		// There is overlap, so return true.
		return true;
	}
	
	public AABBf surroundPointsWithBox(Vector3f[] points) {
		float minX = 0;
		float minY = 0;
		float minZ = 0;
		float maxX = 0;
		float maxY = 0;
		float maxZ = 0;
		float x = 0;
		float y = 0;
		float z = 0;
		
		for(Vector3f point : points) {
			// we will do a lot of checks,
			// so we unwrap the point to the stack
			x = point.x;
			y = point.y;
			z = point.z;
			
			// check MINIMUM
			minX = x < minX ? x : minX;
			minY = y < minY ? y : minY;
			minZ = z < minZ ? z : minZ;
			// check MAXIMUM
			maxX = x > maxX ? x : maxX;
			maxY = y > maxY ? y : maxY;
			maxZ = z > maxZ ? z : maxZ;
		}
		
		// Calculate extents and origin for bounds...
		// bounds.origin = (min+max)/2 = minPlusMax/2
		float OUToriginX = (minX+maxX) / 2f;
		float OUToriginY = (minY+maxY) / 2f;
		float OUToriginZ = (minZ+maxZ) / 2f;
		
		// bounds.extent = (max-min)/2 = size/2
		float OUTextentX = (maxX-minX) / 2f;
		float OUTextentY = (maxY-minY) / 2f;
		float OUTextentZ = (maxZ-minZ) / 2f;
		
		// Store result in 'this'!
		set(OUTextentX, OUTextentY, OUTextentZ, OUToriginX, OUToriginY, OUToriginZ);
		
		return this;
	}
	
	public AABBf surroundPointsWithBox(Collection<Vector3f> points) {
		float minX = 0;
		float minY = 0;
		float minZ = 0;
		float maxX = 0;
		float maxY = 0;
		float maxZ = 0;
		float x = 0;
		float y = 0;
		float z = 0;
		
		for(Vector3f point : points) {
			// we will do a lot of checks,
			// so we unwrap the point to the stack
			x = point.x;
			y = point.y;
			z = point.z;
			
			// check MINIMUM
			minX = x < minX ? x : minX;
			minY = y < minY ? y : minY;
			minZ = z < minZ ? z : minZ;
			// check MAXIMUM
			maxX = x > maxX ? x : maxX;
			maxY = y > maxY ? y : maxY;
			maxZ = z > maxZ ? z : maxZ;
		}
		
		// Calculate extents and origin for bounds...
		// bounds.origin = (min+max)/2 = minPlusMax/2
		float OUToriginX = (minX+maxX) / 2f;
		float OUToriginY = (minY+maxY) / 2f;
		float OUToriginZ = (minZ+maxZ) / 2f;
		
		// bounds.extent = (max-min)/2 = size/2
		float OUTextentX = (maxX-minX) / 2f;
		float OUTextentY = (maxY-minY) / 2f;
		float OUTextentZ = (maxZ-minZ) / 2f;
		
		// Store result in 'this'!
		set(OUTextentX, OUTextentY, OUTextentZ, OUToriginX, OUToriginY, OUToriginZ);
		
		return this;
	}
	
	public boolean inside(float px, float py, float pz) {
		return
				px >= originX-extentX && px <= originX+extentX &&
				py >= originY-extentY && py <= originY+extentY &&
				pz >= originZ-extentZ && pz <= originZ+extentZ;
	}
	
	public boolean inside(Vector3f p) {
		return
				p.x >= originX-extentX && p.x <= originX+extentX &&
				p.y >= originY-extentY && p.y <= originY+extentY &&
				p.z >= originZ-extentZ && p.z <= originZ+extentZ;
	}
	
}
