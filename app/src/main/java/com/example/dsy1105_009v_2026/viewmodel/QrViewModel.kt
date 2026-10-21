package com.example.dsy1105_009v_2026.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dsy1105_009v_2026.data.SolicitudDatabase
import com.example.dsy1105_009v_2026.model.QrResult
import com.example.dsy1105_009v_2026.repository.QrRepository
import com.example.dsy1105_009v_2026.repository.SolicitudRepository
import kotlinx.coroutines.launch

class QrViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = QrRepository(
        SolicitudRepository(SolicitudDatabase.getDatabase(app).solicitudDao())
    )

    private val _qrResult = MutableLiveData<QrResult?>()
    val qrResult: LiveData<QrResult?> = _qrResult


    fun onQrDetected(content: String, cliente: String) {
        viewModelScope.launch {
            _qrResult.value = repository.processQrContent(content, cliente)
        }
    }

    fun clearResult() {
        _qrResult.value = null
    }
}