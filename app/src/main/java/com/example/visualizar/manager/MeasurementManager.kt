package com.example.visualizar.manager

import com.example.visualizar.model.Measurement
import com.example.visualizar.model.Point3D

/**
 * Gerencia as medições e histórico de medições
 */
class MeasurementManager {
    private val measurements = mutableListOf<Measurement>()
    private var currentMeasurement = Measurement()

    /**
     * Adiciona um ponto à medição atual
     */
    fun addPoint(point: Point3D) {
        currentMeasurement.addPoint(point)
    }

    /**
     * Remove o último ponto da medição atual
     */
    fun removeLastPoint(): Boolean {
        return currentMeasurement.removeLastPoint()
    }

    /**
     * Retorna a medição atual
     */
    fun getCurrentMeasurement(): Measurement = currentMeasurement

    /**
     * Retorna os pontos da medição atual
     */
    fun getCurrentPoints(): List<Point3D> = currentMeasurement.points

    /**
     * Salva a medição atual e inicia uma nova
     */
    fun saveMeasurement(): Measurement {
        measurements.add(currentMeasurement)
        currentMeasurement = Measurement()
        return measurements.last()
    }

    /**
     * Limpa a medição atual
     */
    fun clearCurrent() {
        currentMeasurement.clear()
    }

    /**
     * Retorna o histórico de medições
     */
    fun getMeasurementHistory(): List<Measurement> = measurements.toList()

    /**
     * Retorna a quantidade de pontos na medição atual
     */
    fun getPointCount(): Int = currentMeasurement.points.size

    /**
     * Retorna a distância entre dois pontos consecutivos
     */
    fun getLastDistance(): Float? {
        val points = currentMeasurement.points
        return if (points.size >= 2) {
            points[points.size - 2].distanceTo(points[points.size - 1])
        } else null
    }

    /**
     * Retorna a área da medição atual
     */
    fun getCurrentArea(): Float = currentMeasurement.getArea()

    /**
     * Retorna o perímetro da medição atual
     */
    fun getCurrentPerimeter(): Float = currentMeasurement.getPerimeter()
}
