package br.com.eduardocercal.guests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eduardocercal.guests.model.GuestModel
import br.com.eduardocercal.guests.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GuestRepository.getInstance(application)

    private val _guest = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = _guest

    private val _savedGuest = MutableLiveData<String>()
    val savedGuest: LiveData<String> = _savedGuest

    fun save(guest: GuestModel) {
        val saveSuccess = if (guest.id == 0) {
            repository.insert(guest)
        } else {
            repository.update(guest)
        }

        _savedGuest.value =
            if (saveSuccess) "${if (guest.id == 0) "Inserção" else "Atualização"} feita com sucesso" else ""
    }

    fun getGuest(id: Int) {
        _guest.value = repository.getGuest(id)
    }
}