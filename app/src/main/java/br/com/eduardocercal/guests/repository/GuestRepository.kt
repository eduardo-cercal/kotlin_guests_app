package br.com.eduardocercal.guests.repository

import android.content.Context
import br.com.eduardocercal.guests.model.GuestModel

class GuestRepository(context: Context) {

    private val dataBase = GuestDataBase.getDataBase(context).guestDAO()


    fun insert(guest: GuestModel): Boolean {
        return try {
            dataBase.insert(guest) > 0
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            dataBase.update(guest) > 0
        } catch (e: Exception) {
            false
        }

    }

    fun delete(guestId: Int) {
        try {
            val guest = getGuest(guestId)
            dataBase.delete(guest!!)
        } catch (_: Exception) {

        }

    }

    fun getAll(): List<GuestModel> {
        return dataBase.getAll()
    }

    fun getAllFiltered(isPresent: Boolean): List<GuestModel> {
        return dataBase.getAllFiltered(if (isPresent) 1 else 0)
    }

    fun getGuest(id: Int): GuestModel? {
        return dataBase.getGuest(id)
    }
}