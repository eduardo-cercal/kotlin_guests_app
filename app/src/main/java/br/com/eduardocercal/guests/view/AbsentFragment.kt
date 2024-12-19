package br.com.eduardocercal.guests.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.eduardocercal.guests.constants.DataBaseConstants
import br.com.eduardocercal.guests.databinding.FragmentAbsentBinding
import br.com.eduardocercal.guests.view.adapter.GuestAdapter
import br.com.eduardocercal.guests.view.listener.OnGuestListener
import br.com.eduardocercal.guests.viewmodel.GuestsViewModel

class AbsentFragment : Fragment() {

    private var _binding: FragmentAbsentBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: GuestsViewModel

    private val adapter = GuestAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(GuestsViewModel::class.java)

        _binding = FragmentAbsentBinding.inflate(inflater, container, false)

        binding.recyclerGuests.layoutManager = LinearLayoutManager(context)

        binding.recyclerGuests.adapter = adapter

        val listener = object : OnGuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(DataBaseConstants.GUEST.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAllFiltered(false)
            }


        }

        viewModel.getAllFiltered(false)

        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllFiltered(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.listAllGuests.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}