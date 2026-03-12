package com.example.visualizar.model

import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para a classe Point3D
 */
class Point3DTest {

    @Test
    fun testDistanceCalculation() {
        // Teste com triângulo 3-4-5
        val point1 = Point3D(0f, 0f, 0f)
        val point2 = Point3D(3f, 4f, 0f)
        val distance = point1.distanceTo(point2)
        assertEquals(5f, distance, 0.01f)
    }

    @Test
    fun testDistanceInCentimeters() {
        val point1 = Point3D(0f, 0f, 0f)
        val point2 = Point3D(1f, 0f, 0f)
        val distanceCm = point1.distanceToCm(point2)
        assertEquals(100f, distanceCm, 0.01f)
    }

    @Test
    fun testZeroDistance() {
        val point = Point3D(1f, 2f, 3f)
        val distance = point.distanceTo(point)
        assertEquals(0f, distance, 0.01f)
    }

    @Test
    fun testPoint3DCreation() {
        val point = Point3D(1.5f, 2.5f, 3.5f)
        assertEquals(1.5f, point.x, 0.01f)
        assertEquals(2.5f, point.y, 0.01f)
        assertEquals(3.5f, point.z, 0.01f)
    }

    @Test
    fun testPoint3DToString() {
        val point = Point3D(1f, 2f, 3f)
        val string = point.toString()
        assertTrue(string.contains("1.0"))
        assertTrue(string.contains("2.0"))
        assertTrue(string.contains("3.0"))
    }
}
