package com.example.visualizar.model

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Testes unitários para a classe Measurement
 */
class MeasurementTest {
    private lateinit var measurement: Measurement

    @Before
    fun setUp() {
        measurement = Measurement()
    }

    @Test
    fun testAddPoint() {
        val point = Point3D(1f, 0f, 0f)
        measurement.addPoint(point)
        assertEquals(1, measurement.points.size)
        assertEquals(point, measurement.points[0])
    }

    @Test
    fun testRemoveLastPoint() {
        measurement.addPoint(Point3D(1f, 0f, 0f))
        measurement.addPoint(Point3D(2f, 0f, 0f))
        assertTrue(measurement.removeLastPoint())
        assertEquals(1, measurement.points.size)
    }

    @Test
    fun testRemoveLastPointEmpty() {
        assertFalse(measurement.removeLastPoint())
    }

    @Test
    fun testClear() {
        measurement.addPoint(Point3D(1f, 0f, 0f))
        measurement.addPoint(Point3D(2f, 0f, 0f))
        measurement.clear()
        assertEquals(0, measurement.points.size)
    }

    @Test
    fun testGetDistance() {
        measurement.addPoint(Point3D(0f, 0f, 0f))
        measurement.addPoint(Point3D(3f, 4f, 0f))
        val distance = measurement.getDistance()
        assertEquals(5f, distance!!, 0.01f)
    }

    @Test
    fun testGetDistanceWithOnePoint() {
        measurement.addPoint(Point3D(0f, 0f, 0f))
        assertNull(measurement.getDistance())
    }

    @Test
    fun testGetAreaRectangle() {
        // Retângulo 5x4
        measurement.addPoint(Point3D(0f, 0f, 0f))
        measurement.addPoint(Point3D(5f, 0f, 0f))
        measurement.addPoint(Point3D(5f, 0f, 4f))
        measurement.addPoint(Point3D(0f, 0f, 4f))
        val area = measurement.getArea()
        assertEquals(20f, area, 0.01f)
    }

    @Test
    fun testGetAreaTriangle() {
        // Triângulo com base 4 e altura 3
        measurement.addPoint(Point3D(0f, 0f, 0f))
        measurement.addPoint(Point3D(4f, 0f, 0f))
        measurement.addPoint(Point3D(2f, 0f, 3f))
        val area = measurement.getArea()
        assertEquals(6f, area, 0.01f)
    }

    @Test
    fun testGetPerimeter() {
        // Quadrado com lado 1
        measurement.addPoint(Point3D(0f, 0f, 0f))
        measurement.addPoint(Point3D(1f, 0f, 0f))
        measurement.addPoint(Point3D(1f, 0f, 1f))
        measurement.addPoint(Point3D(0f, 0f, 1f))
        val perimeter = measurement.getPerimeter()
        assertEquals(4f, perimeter, 0.01f)
    }

    @Test
    fun testGetPerimeterWithTwoPoints() {
        measurement.addPoint(Point3D(0f, 0f, 0f))
        measurement.addPoint(Point3D(1f, 0f, 0f))
        val perimeter = measurement.getPerimeter()
        assertEquals(2f, perimeter, 0.01f)
    }
}
