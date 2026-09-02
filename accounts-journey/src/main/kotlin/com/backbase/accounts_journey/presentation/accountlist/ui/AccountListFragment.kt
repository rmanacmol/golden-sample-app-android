package com.backbase.accounts_journey.presentation.accountlist.ui

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
import com.backbase.accounts_journey.configuration.AccountsJourneyConfiguration
import com.backbase.accounts_journey.configuration.accountlist.AccountListScreenConfiguration
import com.backbase.accounts_journey.presentation.compose.AccountsJourneyTheme
import com.backbase.accounts_journey.routing.AccountsRouting
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * The Fragment of the account list.
 *
 * Created by Backbase R&D B.V on 04/10/2023.
 */
class AccountListFragment : Fragment() {

    private val routing: AccountsRouting by inject()
    private val journeyConfiguration: AccountsJourneyConfiguration by inject()
    private val screenConfiguration: AccountListScreenConfiguration by lazy {
        journeyConfiguration.accountListScreenConfiguration
    }

    private val viewModel: AccountListViewModel by viewModel()

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
                    AccountListScreen(
                        title = getString(screenConfiguration.screenTitle),
                        uiState = uiState,
                        onRefresh = { viewModel.onEvent(AccountListEvent.OnRefresh) },
                        onSearch = { viewModel.onEvent(AccountListEvent.OnSearch(it)) },
                        onAccountClick = { routing.onAccountSelected(it) },
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        routing.bind(findNavController())
        viewModel.onEvent(AccountListEvent.OnGetAccounts)
    }
}
