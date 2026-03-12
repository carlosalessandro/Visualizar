package com.example.visualizar.model

import com.google.ar.core.Anchor
import kotlin.math.sqrt

/**
 * Representa um ponto no espaço 3D com suas coordenadas e âncora ARCore
 */
data class Point3D(
    val x: Float,
    val y: Float,
    val z: Float,
    val anchor: Anchor? = null,
    val id: String = System.currentTimeMillis().toString()
) {
    /**
     * Calcula a distância euclidiana entre este ponto e outro
     */
    fun distanceTo(other: Point3D): Float {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    /**
     * Converte a distância de metros para centímetros
     */
    fun distanceToCm(other: Point3D): Float {
        return distanceTo(other) * 100
    }

    override fun toString(): String {
        return "Point3D(x=$x, y=$y, z=$z)"
    }
}
