package com.example.visualizar.util

import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.example.visualizar.model.Point3D

/**
 * Utilitário para testes de colisão (hit test) em AR
 */
object ARHitTestUtil {
    /**
     * Realiza um hit test na posição da tela e retorna um ponto 3D
     * @param frame Frame atual do ARCore
     * @param x Coordenada X da tela
     * @param y Coordenada Y da tela
     * @return Point3D se houver colisão, null caso contrário
     */
    fun hitTest(frame: Frame, x: Float, y: Float): Point3D? {
        val hitTestResults = frame.hitTest(x, y)

        // Prioriza planos detectados
        for (hit in hitTestResults) {
            val trackable = hit.trackable
            if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                val pose = hit.hitPose
                return Point3D(
                    x = pose.tx(),
                    y = pose.ty(),
                    z = pose.tz(),
                    anchor = hit.createAnchor()
                )
            }
        }

        // Se não encontrar plano, usa o primeiro hit
        return if (hitTestResults.isNotEmpty()) {
            val hit = hitTestResults[0]
            val pose = hit.hitPose
            Point3D(
                x = pose.tx(),
                y = pose.ty(),
                z = pose.tz(),
                anchor = hit.createAnchor()
            )
        } else null
    }

    /**
     * Detecta planos horizontais na cena
     */
    fun getHorizontalPlanes(frame: Frame): List<Plane> {
        return frame.getUpdatedTrackables(Plane::class.java).toList()
            .filter { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
    }

    /**
     * Detecta planos verticais na cena
     */
    fun getVerticalPlanes(frame: Frame): List<Plane> {
        return frame.getUpdatedTrackables(Plane::class.java).toList()
            .filter { it.type == Plane.Type.VERTICAL }
    }

    /**
     * Detecta todos os planos na cena
     */
    fun getAllPlanes(frame: Frame): List<Plane> {
        return frame.getUpdatedTrackables(Plane::class.java).toList()
    }
}
