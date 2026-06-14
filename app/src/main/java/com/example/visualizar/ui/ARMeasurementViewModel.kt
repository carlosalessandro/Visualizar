package com.example.visualizar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.visualizar.manager.MeasurementManager
import com.example.visualizar.model.Point3D
import com.example.visualizar.util.FormatUtil

/**
 * ViewModel para gerenciar o estado da medição em AR
 */
class ARMeasurementViewModel : ViewModel() {
    private val measurementManager = MeasurementManager()

    private val _points = MutableLiveData<List<Point3D>>(emptyList())
    val points: LiveData<List<Point3D>> = _points

    private val _distance = MutableLiveData<String>("")
    val distance: LiveData<String> = _distance

    private val _area = MutableLiveData<String>("")
    val area: LiveData<String> = _area

    private val _perimeter = MutableLiveData<String>("")
    val perimeter: LiveData<String> = _perimeter

    private val _pointCount = MutableLiveData<Int>(0)
    val pointCount: LiveData<Int> = _pointCount

    private val _lastDistance = MutableLiveData<String>("")
    val lastDistance: LiveData<String> = _lastDistance

    /**
     * Adiciona um novo ponto à medição
     */
    fun addPoint(point: Point3D) {
        measurementManager.addPoint(point)
        updateUI()
    }

    /**
     * Remove o último ponto
     */
    fun removeLastPoint() {
        if (measurementManager.removeLastPoint()) {
            updateUI()
        }
    }

    /**
     * Limpa a medição atual
     */
    fun clearMeasurement() {
        measurementManager.clearCurrent()
        updateUI()
    }

    /**
     * Atualiza a interface com os dados atuais
     */
    private fun updateUI() {
        val currentPoints = measurementManager.getCurrentPoints()
        _points.value = currentPoints
        _pointCount.value = currentPoints.size

        // Atualiza distância
        val lastDist = measurementManager.getLastDistance()
        _lastDistance.value = if (lastDist != null) {
            FormatUtil.formatDistance(lastDist)
        } else ""

        // Atualiza área
        val area = measurementManager.getCurrentArea()
        _area.value = if (area > 0) FormatUtil.formatArea(area) else ""

        // Atualiza perímetro
        val perimeter = measurementManager.getCurrentPerimeter()
        _perimeter.value = if (perimeter > 0) FormatUtil.formatDistance(perimeter) else ""

        // Atualiza distância total (primeiro ao último ponto)
        if (currentPoints.size >= 2) {
            val totalDist = currentPoints[0].distanceTo(currentPoints[currentPoints.size - 1])
            _distance.value = FormatUtil.formatDistance(totalDist)
        } else {
            _distance.value = ""
        }
    }

    /**
     * Retorna o gerenciador de medições
     */
    fun getMeasurementManager(): MeasurementManager = measurementManager
    
    /**
     * Retorna a lista atual de pontos
     */
    fun getPoints(): List<Point3D> = measurementManager.getCurrentPoints()
}
