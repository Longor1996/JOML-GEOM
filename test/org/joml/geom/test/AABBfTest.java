package org.joml.geom.test;

import junit.framework.TestCase;

import org.joml.Vector3f;
import org.joml.geom.AABBf;

public class AABBfTest extends TestCase {
	
	public void testOverlap() {
		AABBf a = new AABBf();
		AABBf b = new AABBf();
		
		a.set(1, 1, 1,/**/ 0, 0, 0);
		b.set(1, 1, 1,/**/ 0.5f, 0.5f, 0.5f);
		assertTrue("A and B should overlap, but they don't.",a.overlap(b));
		
		a.set(1, 1, 1,/**/ 0, 0, 0);
		b.set(1, 1, 1,/**/ 0, 2, 0);
		assertTrue("A and B should NOT overlap, but they do.",!a.overlap(b));
		
		a.set(1, 1, 1,/**/ 0, 0, 0);
		b.set(1, 1, 1,/**/ -1.5f, -1, -1.5f);
		assertTrue("A and B should overlap, but they don't.", a.overlap(b));
	}
	
	public void testDistance() {
		assertEquals(4f , new AABBf().minDistanceSquared(new Vector3f(0,3,0)), Math.ulp(1));
		assertEquals(12f, new AABBf().minDistanceSquared(new Vector3f(3,3,3)), Math.ulp(1));
	}
	
}
