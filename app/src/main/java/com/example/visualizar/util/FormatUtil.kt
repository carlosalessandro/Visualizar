package com.example.visualizar.util

/**
 * Utilitário para formatação de valores de medição
 */
object FormatUtil {
    /**
     * Formata distância em metros para string legível
     */
    fun formatDistance(meters: Float): String {
        return when {
            meters < 0.01 -> "${(meters * 1000).toInt()} mm"
            meters < 1 -> "${String.format("%.2f", meters * 100)} cm"
            else -> "${String.format("%.2f", meters)} m"
        }
    }

    /**
     * Formata área em metros quadrados
     */
    fun formatArea(squareMeters: Float): String {
        return when {
            squareMeters < 1 -> "${String.format("%.2f", squareMeters * 10000)} cm²"
            else -> "${String.format("%.2f", squareMeters)} m²"
        }
    }

    /**
     * Formata volume em metros cúbicos
     */
    fun formatVolume(cubicMeters: Float): String {
        return "${String.format("%.2f", cubicMeters)} m³"
    }

    /**
     * Formata coordenada 3D
     */
    fun formatCoordinate(value: Float): String {
        return String.format("%.3f", value)
    }
}
