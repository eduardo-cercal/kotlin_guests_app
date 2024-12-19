package br.com.eduardocercal.guests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eduardocercal.guests.model.GuestModel
import br.com.eduardocercal.guests.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = GuestRepository.getInstance(application)

    private val _listAllGuests = MutableLiveData<List<GuestModel>>()
    val listAllGuests: LiveData<List<GuestModel>> = _listAllGuests

    fun getAll() {
        _listAllGuests.value = repository.getAll()
    }

    fun getAllFiltered(isPresent: Boolean) {
        _listAllGuests.value = repository.getAllFiltered(isPresent)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}