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
import com.muratozturk.mova.common.viewBinding
import com.muratozturk.mova.databinding.FragmentNotificationBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NotificationFragment : Fragment(R.layout.fragment_notification) {

    private val binding by viewBinding(FragmentNotificationBinding::bind)
    private val viewModel: NotificationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNotifUi()
        initUi()
        initRecyclerView()
    }

   private fun initUi() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initRecyclerView() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.notifComponent.collect {
                val adapter = NotificationAdapter(it)
                binding.recyclerViewNotif.adapter = adapter
            }
        }
    }
}