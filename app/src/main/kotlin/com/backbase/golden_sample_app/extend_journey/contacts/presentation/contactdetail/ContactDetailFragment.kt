package com.backbase.golden_sample_app.extend_journey.contacts.presentation.contactdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.backbase.golden_sample_app.extend_journey.contacts.presentation.compose.ContactsExtensionTheme

class ContactDetailFragment : Fragment() {

    private val contactId by lazy {
        ContactDetailFragmentArgs.fromBundle(requireArguments()).id
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ContactsExtensionTheme {
                    ContactDetailScreen(contactId = contactId)
                }
            }
        }
    }
}
