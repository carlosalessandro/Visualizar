package com.example.visualizar.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Representa uma medição completa com múltiplos pontos
 */
data class Measurement(
    val id: String = System.currentTimeMillis().toString(),
    val points: MutableList<Point3D> = mutableListOf(),
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val name: String = "Medição ${timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}"
) {
    /**
     * Calcula a distância entre o primeiro e último ponto
     */
    fun getDistance(): Float? {
        return if (points.size >= 2) {
            points[0].distanceTo(points[points.size - 1])
        } else null
    }

    /**
     * Calcula a área de um polígono formado pelos pontos
     * Usa o algoritmo de Shoelace (fórmula de Gauss)
     */
    fun getArea(): Float {
        if (points.size < 3) return 0f

        // Projeta os pontos no plano XZ (ignorando Y para superfícies horizontais)
        var area = 0f
        for (i in points.indices) {
            val current = points[i]
            val next = points[(i + 1) % points.size]
            area += (current.x * next.z - next.x * current.z)
        }
        return kotlin.math.abs(area) / 2f
    }

    /**
     * Calcula o perímetro do polígono
     */
    fun getPerimeter(): Float {
        if (points.size < 2) return 0f
        var perimeter = 0f
        for (i in points.indices) {
            val current = points[i]
            val next = points[(i + 1) % points.size]
            perimeter += current.distanceTo(next)
        }
        return perimeter
    }

    /**
     * Adiciona um novo ponto à medição
     */
    fun addPoint(point: Point3D) {
        points.add(point)
    }

    /**
     * Remove o último ponto adicionado
     */
    fun removeLastPoint(): Boolean {
        return if (points.isNotEmpty()) {
            points.removeAt(points.size - 1)
            true
        } else false
    }

    /**
     * Limpa todos os pontos
     */
    fun clear() {
        points.clear()
    }
}
