package com.backbase.accounts_journey.presentation.accountlist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.backbase.accounts_journey.R
import com.backbase.accounts_journey.presentation.accountlist.model.AccountHeaderUiModel
import com.backbase.accounts_journey.presentation.accountlist.model.AccountUiModel
import com.backbase.accounts_journey.presentation.accountlist.model.ListItem
import com.backbase.accounts_journey.presentation.compose.radiusLarge
import com.backbase.accounts_journey.presentation.compose.spacerLarge
import com.backbase.accounts_journey.presentation.compose.spacerMedium
import com.backbase.android.design.R as DesignR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountListScreen(
    title: String,
    uiState: AccountListScreenState,
    onRefresh: () -> Unit,
    onSearch: (String) -> Unit,
    onAccountClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by rememberSaveable { mutableStateOf("") }
    val spacerMedium = spacerMedium()
    val spacerLarge = spacerLarge()
    val radius = radiusLarge()
    val showEmptyState = !uiState.isLoading && (uiState.error != null || uiState.accountSummary.isEmpty())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacerMedium)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacerLarge)
                .testTag("header")
        )
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = spacerMedium),
            shape = RoundedCornerShape(radius),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { value ->
                        query = value
                        onSearch(value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacerMedium)
                        .testTag("searchTextInput"),
                    placeholder = { Text(stringResource(R.string.accounts_search_hint)) },
                    trailingIcon = {
                        Image(
                            painter = painterResource(DesignR.drawable.backbase_ic_search),
                            contentDescription = null,
                        )
                    },
                    singleLine = true,
                )
                PullToRefreshBox(
                    isRefreshing = uiState.isLoading,
                    onRefresh = onRefresh,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("accountlist_swipe_container")
                ) {
                    if (showEmptyState) {
                        EmptyOrErrorState(
                            isError = uiState.error != null,
                            messageRes = uiState.error ?: R.string.no_accounts,
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(
                                items = uiState.accountSummary,
                                contentType = { item -> item.viewType }
                            ) { item ->
                                AccountListRow(
                                    item = item,
                                    onAccountClick = onAccountClick,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyOrErrorState(
    isError: Boolean,
    messageRes: Int,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(
                if (isError) DesignR.drawable.backbase_ic_error else DesignR.drawable.backbase_ic_no_accounts
            ),
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .testTag("no_account_image")
        )
        Text(
            text = stringResource(messageRes),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = spacerMedium())
                .testTag("accounts_result_text")
        )
    }
}

@Composable
private fun AccountListRow(
    item: ListItem,
    onAccountClick: (String) -> Unit,
) {
    when (item) {
        is AccountHeaderUiModel -> {
            Text(
                text = item.name.orEmpty().uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacerMedium(), vertical = spacerMedium())
                    .testTag("account_header")
            )
        }

        is AccountUiModel -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = item.id != null) { item.id?.let(onAccountClick) }
                    .padding(spacerMedium()),
                verticalAlignment = Alignment.Top,
            ) {
                Image(
                    painter = painterResource(item.icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = spacerMedium())
                )
                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = item.name.orEmpty(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("account_name")
                        )
                        Text(
                            text = item.balance.orEmpty(),
                            modifier = Modifier
                                .padding(start = spacerMedium())
                                .testTag("account_balance")
                        )
                    }
                    Text(
                        text = item.state.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}
