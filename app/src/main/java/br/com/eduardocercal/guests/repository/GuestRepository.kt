package br.com.eduardocercal.guests.repository

import android.content.ContentValues
import android.content.Context
import br.com.eduardocercal.guests.constants.DataBaseConstants
import br.com.eduardocercal.guests.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private val dataBase = GuestDataBase(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!Companion::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = dataBase.writableDatabase

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, if (guest.presence) 1 else 0)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = dataBase.writableDatabase

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, if (guest.presence) 1 else 0)

            val selection = "${DataBaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)

            true
        } catch (e: Exception) {
            false
        }

    }

    fun delete(guestId: Int): Boolean {
        return try {
            val db = dataBase.writableDatabase

            val selection = "${DataBaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(guestId.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)

            true
        } catch (e: Exception) {
            false
        }

    }

    fun getAll(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val db = dataBase.readableDatabase

            val projections = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )


            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projections,
                    null,
                    null,
                    null,
                    null,
                    null
                )

            while (cursor.moveToNext()) {
                list.add(
                    GuestModel(
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)),
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)),
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                    )
                )


            }

            cursor.close()



            return list
        } catch (e: Exception) {
            return list
        }
    }

    fun getAllFiltered(isPresent: Boolean): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val db = dataBase.readableDatabase

            val projections = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = "${DataBaseConstants.GUEST.COLUMNS.PRESENCE} = ?"
            val args = arrayOf(if (isPresent) "1" else "0")


            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projections,
                    selection,
                    args,
                    null,
                    null,
                    null
                )

            while (cursor.moveToNext()) {
                list.add(
                    GuestModel(
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)),
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)),
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                    )
                )


            }

            cursor.close()
            return list
        } catch (e: Exception) {
            return list
        }
    }

    fun getGuest(id: Int): GuestModel? {
        var guest: GuestModel? = null
        try {
            val db = dataBase.readableDatabase

            val projections = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = "${DataBaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(id.toString())


            val cursor =
                db.query(
                    DataBaseConstants.GUEST.TABLE_NAME,
                    projections,
                    selection,
                    args,
                    null,
                    null,
                    null
                )

            while (cursor.moveToNext()) {

                guest = GuestModel(
                    cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID)),
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1
                )


            }

            cursor.close()
            return guest
        } catch (e: Exception) {
            return guest
        }
    }
}