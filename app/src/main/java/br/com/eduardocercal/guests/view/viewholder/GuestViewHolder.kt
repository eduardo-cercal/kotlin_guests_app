package br.com.eduardocercal.guests.view.viewholder

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import br.com.eduardocercal.guests.databinding.RowGuestBinding
import br.com.eduardocercal.guests.model.GuestModel
import br.com.eduardocercal.guests.view.listener.OnGuestListener

class GuestViewHolder(private val binding: RowGuestBinding, private val listener: OnGuestListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(guest: GuestModel) {
        binding.textName.text = guest.name

        binding.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        binding.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context).setTitle("Remoção de convidado")
                .setMessage("Tem certeza que deseja remover?").setPositiveButton(
                    "Sim"
                ) { dialog, which -> listener.onDelete(guest.id) }.setNegativeButton(
                    "Não",
                    null
                )
                .create().show()

            true
        }
    }
}