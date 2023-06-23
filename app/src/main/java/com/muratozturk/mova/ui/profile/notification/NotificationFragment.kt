package com.muratozturk.mova.ui.profile.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.muratozturk.mova.R
import com.muratozturk.mova.databinding.FragmentNotificationBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater)

        viewModel.getNotifUi()
        initUi()
        initRecyclerView()
        return binding.root
    }

   private fun initUi() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notifComponent.collect {
                val adapter = NotificationAdapter(it)
                binding.recyclerViewNotif.adapter = adapter
            }
        }
    }
}