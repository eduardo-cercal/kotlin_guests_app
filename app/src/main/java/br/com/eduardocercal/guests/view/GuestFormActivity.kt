package br.com.eduardocercal.guests.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import br.com.eduardocercal.guests.R
import br.com.eduardocercal.guests.constants.DataBaseConstants
import br.com.eduardocercal.guests.databinding.ActivityGuestFormBinding
import br.com.eduardocercal.guests.model.GuestModel
import br.com.eduardocercal.guests.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]

        binding.buttonSave.setOnClickListener(this)
        binding.radioPresent.isChecked = true

        observe()

        loadData()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_save -> {
                saveGuestPresence()

            }
        }
    }

    private fun saveGuestPresence() {
        val name = binding.editName.text.toString()
        val presence = binding.radioPresent.isChecked

        viewModel.save(GuestModel().apply {
            this.id = guestId
            this.name = name
            this.presence = presence
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            guestId = bundle.getInt(DataBaseConstants.GUEST.ID)
            viewModel.getGuest(guestId)
        }
    }

    private fun observe() {
        viewModel.guest.observe(this) {
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked =
                    true
            } else {
                binding.radioAbsent.isChecked = true
            }
        }

        viewModel.savedGuest.observe(this) {
            if (it.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Falha ao realizar operação",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                Toast.makeText(
                    applicationContext,
                    it,
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}