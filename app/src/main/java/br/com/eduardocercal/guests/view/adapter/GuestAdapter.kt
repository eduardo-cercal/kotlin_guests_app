package br.com.eduardocercal.guests.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardocercal.guests.databinding.RowGuestBinding
import br.com.eduardocercal.guests.model.GuestModel
import br.com.eduardocercal.guests.view.listener.OnGuestListener
import br.com.eduardocercal.guests.view.viewholder.GuestViewHolder

class GuestAdapter : RecyclerView.Adapter<GuestViewHolder>() {

    private var guestList: List<GuestModel> = listOf()

    private lateinit var listener: OnGuestListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val item = RowGuestBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GuestViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(guestList[position])
    }

    override fun getItemCount(): Int {
        return guestList.count()
    }

    fun updateGuests(list: List<GuestModel>) {
        guestList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: OnGuestListener) {
        this.listener = listener
    }
}