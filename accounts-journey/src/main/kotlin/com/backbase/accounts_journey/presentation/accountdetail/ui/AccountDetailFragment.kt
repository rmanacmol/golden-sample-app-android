package com.backbase.accounts_journey.presentation.accountdetail.ui

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
import com.backbase.accounts_journey.presentation.compose.AccountsJourneyTheme
import org.koin.android.ext.android.inject

/**
 * The Fragment of the account detail.
 *
 * Created by Backbase R&D B.V on 16/11/2023.
 */
class AccountDetailFragment : Fragment() {

    private val viewModel: AccountDetailViewModel by inject()

    private val id by lazy {
        AccountDetailFragmentArgs.fromBundle(requireArguments()).id
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AccountsJourneyTheme {
                    val uiState by viewModel.uiState.collectAsState()
                    AccountDetailScreen(
                        uiState = uiState,
                        onBack = { findNavController().navigateUp() },
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onEvent(AccountDetailEvent.OnGetAccountDetail(id))
    }
}
