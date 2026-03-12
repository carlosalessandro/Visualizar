package com.example.visualizar.service

import android.content.Context
import com.example.visualizar.model.Measurement
import java.io.File
import java.time.format.DateTimeFormatter

/**
 * Serviço para salvar e carregar medições do armazenamento local
 */
class MeasurementStorageService(private val context: Context) {
    private val storageDir = File(context.filesDir, "measurements")

    init {
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
    }

    /**
     * Exporta uma medição para CSV
     */
    fun exportToCSV(measurement: Measurement): String {
        val fileName = "measurement_${measurement.id}.csv"
        val file = File(storageDir, fileName)

        val csvContent = buildString {
            appendLine("ID,X,Y,Z")
            measurement.points.forEachIndexed { index, point ->
                appendLine("$index,${point.x},${point.y},${point.z}")
            }
            appendLine()
            appendLine("Estatísticas")
            appendLine("Distância Total,${measurement.getDistance()}")
            appendLine("Área,${measurement.getArea()}")
            appendLine("Perímetro,${measurement.getPerimeter()}")
        }

        file.writeText(csvContent)
        return file.absolutePath
    }

    /**
     * Exporta uma medição para JSON
     */
    fun exportToJSON(measurement: Measurement): String {
        val fileName = "measurement_${measurement.id}.json"
        val file = File(storageDir, fileName)

        val jsonContent = buildString {
            append("{\n")
            append("  \"id\": \"${measurement.id}\",\n")
            append("  \"name\": \"${measurement.name}\",\n")
            append("  \"timestamp\": \"${measurement.timestamp}\",\n")
            append("  \"points\": [\n")
            measurement.points.forEachIndexed { index, point ->
                append("    {\"x\": ${point.x}, \"y\": ${point.y}, \"z\": ${point.z}}")
                if (index < measurement.points.size - 1) append(",")
                append("\n")
            }
            append("  ],\n")
            append("  \"statistics\": {\n")
            append("    \"distance\": ${measurement.getDistance()},\n")
            append("    \"area\": ${measurement.getArea()},\n")
            append("    \"perimeter\": ${measurement.getPerimeter()}\n")
            append("  }\n")
            append("}\n")
        }

        file.writeText(jsonContent)
        return file.absolutePath
    }

    /**
     * Retorna a lista de medições salvas
     */
    fun getSavedMeasurements(): List<File> {
        return storageDir.listFiles()?.toList() ?: emptyList()
    }

    /**
     * Deleta uma medição salva
     */
    fun deleteMeasurement(fileName: String): Boolean {
        val file = File(storageDir, fileName)
        return file.delete()
    }

    /**
     * Limpa todas as medições salvas
     */
    fun clearAll(): Boolean {
        return storageDir.deleteRecursively()
    }
}
