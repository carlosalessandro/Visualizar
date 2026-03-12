package com.example.visualizar.ar

import com.example.visualizar.model.Point3D

/**
 * Renderiza pontos e linhas 3D na cena AR
 * Versão simplificada sem Sceneform
 */
class PointRenderer {
    private val pointNodes = mutableListOf<Point3D>()
    private val lineNodes = mutableListOf<Pair<Point3D, Point3D>>()

    /**
     * Adiciona um ponto visual na cena
     */
    fun addPointVisual(
        point: Point3D
    ): Point3D {
        pointNodes.add(point)
        return point
    }

    /**
     * Desenha uma linha entre dois pontos
     */
    fun drawLine(
        from: Point3D,
        to: Point3D
    ): Pair<Point3D, Point3D> {
        val line = Pair(from, to)
        lineNodes.add(line)
        return line
    }

    /**
     * Limpa todos os pontos visuais
     */
    fun clearPoints() {
        pointNodes.clear()
    }

    /**
     * Limpa todas as linhas
     */
    fun clearLines() {
        lineNodes.clear()
    }

    /**
     * Limpa todos os elementos visuais
     */
    fun clearAll() {
        clearPoints()
        clearLines()
    }

    /**
     * Retorna a quantidade de pontos renderizados
     */
    fun getPointCount(): Int = pointNodes.size

    /**
     * Retorna a quantidade de linhas renderizadas
     */
    fun getLineCount(): Int = lineNodes.size

    /**
     * Retorna os pontos renderizados
     */
    fun getPoints(): List<Point3D> = pointNodes.toList()

    /**
     * Retorna as linhas renderizadas
     */
    fun getLines(): List<Pair<Point3D, Point3D>> = lineNodes.toList()
}
