package com.example.visualizar.ar

import android.content.Context
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableException

/**
 * Gerencia a sessão do ARCore e verifica disponibilidade
 */
class ARCoreManager(private val context: Context) {
    private var session: Session? = null

    /**
     * Verifica se o dispositivo suporta ARCore
     */
    fun isARCoreSupported(): Boolean {
        return try {
            when (ArCoreApk.getInstance().checkAvailability(context)) {
                ArCoreApk.Availability.SUPPORTED_INSTALLED -> true
                ArCoreApk.Availability.SUPPORTED_APK_TOO_OLD -> false
                ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE -> false
                else -> false
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Cria uma nova sessão do ARCore
     */
    fun createSession(): Session? {
        return try {
            Session(context).also { session = it }
        } catch (e: UnavailableException) {
            null
        }
    }

    /**
     * Retorna a sessão atual
     */
    fun getSession(): Session? = session

    /**
     * Libera recursos da sessão
     */
    fun close() {
        session?.close()
        session = null
    }
}
