package com.backbase.golden_sample_app.extend_journey.contacts.presentation.contactlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.backbase.app_common.AppRouting
import com.backbase.golden_sample_app.R
import com.backbase.golden_sample_app.extend_journey.contacts.presentation.compose.ContactsExtensionTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CustomContactsFragment : Fragment() {

    private val navigator: AppRouting by inject()

    private val viewModel: CustomContactsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ContactsExtensionTheme {
                    val uiState by viewModel.uiState.collectAsState()
                    CustomContactsScreen(
                        uiState = uiState,
                        onSearch = { query -> viewModel.onEvent(CustomContactsEvent.OnGetContacts(query)) },
                        onLoadMore = { query -> viewModel.onEvent(CustomContactsEvent.OnGetContacts(query)) },
                        onContactClick = ::itemClicked,
                        onBack = { findNavController().navigateUp() },
                        onAddContact = { findNavController().navigate(R.id.upcoming_fragment) },
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onEvent(CustomContactsEvent.OnGetContacts())
    }

    private fun itemClicked(id: String) {
        navigator.getNavController()?.navigate(
            CustomContactsFragmentDirections.actionCustomContactsFragmentToCustomContactDetailsFragment(id)
        )
    }
}
