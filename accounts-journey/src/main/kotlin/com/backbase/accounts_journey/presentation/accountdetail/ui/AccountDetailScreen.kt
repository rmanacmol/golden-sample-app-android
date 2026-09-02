package com.backbase.accounts_journey.presentation.accountdetail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.backbase.accounts_journey.R
import com.backbase.accounts_journey.presentation.accountdetail.model.AccountDetailUiModel
import com.backbase.accounts_journey.presentation.compose.radiusLarge
import com.backbase.accounts_journey.presentation.compose.spacerLarge
import com.backbase.accounts_journey.presentation.compose.spacerMedium
import com.backbase.accounts_journey.presentation.compose.spacerSmall
import com.backbase.android.design.R as DesignR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountDetailScreen(
    uiState: AccountDetailScreenState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Image(
                        painter = painterResource(DesignR.drawable.backbase_ic_arrow_back),
                        contentDescription = null,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            modifier = Modifier
                .statusBarsPadding()
                .testTag("toolbar")
        )
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("content_loading"),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.accountDetail != null -> {
                AccountDetailContent(
                    model = uiState.accountDetail,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("content_main")
                )
            }

            uiState.error != null -> {
                AccountDetailError(messageRes = uiState.error)
            }
        }
    }
}

@Composable
private fun AccountDetailContent(
    model: AccountDetailUiModel,
    modifier: Modifier = Modifier,
) {
    val spacerSmall = spacerSmall()
    val spacerMedium = spacerMedium()
    val spacerLarge = spacerLarge()
    val radius = radiusLarge()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = spacerMedium, vertical = spacerMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(model.icon),
            contentDescription = null,
            modifier = Modifier.testTag("account_icon")
        )
        Text(
            text = model.name.orEmpty().uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = spacerSmall)
                .testTag("header_account")
        )
        Text(
            text = model.BBAN.orEmpty(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = spacerSmall)
                .testTag("header_bban")
        )
        Text(
            text = model.availableBalance.orEmpty(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = spacerSmall)
                .testTag("header_balance")
        )
        DetailSection(
            title = stringResource(R.string.header_account_details),
            titleTestTag = "header_account_details",
            cardTestTag = "content_account_details",
        ) {
            DetailField(
                label = stringResource(R.string.account_details_account_holder_names_label),
                value = model.accountHolderNames,
                labelTestTag = "account_details_account_holder_names_label",
                valueTestTag = "account_details_account_holder_names",
            )
            Spacer(Modifier.height(spacerMedium))
            DetailField(
                label = stringResource(R.string.account_details_account_number_label),
                value = model.BBAN,
                labelTestTag = "account_details_account_number_label",
                valueTestTag = "account_details_account_number",
            )
        }
        Spacer(Modifier.height(spacerLarge))
        DetailSection(
            title = stringResource(R.string.header_general),
            titleTestTag = "header_general",
            cardTestTag = "content_general",
        ) {
            DetailField(
                label = stringResource(R.string.general_account_type_label),
                value = model.productKindName,
                labelTestTag = "general_account_type_label",
                valueTestTag = "general_account_type",
            )
            Spacer(Modifier.height(spacerMedium))
            DetailField(
                label = stringResource(R.string.general_account_name_label),
                value = model.name,
                labelTestTag = "general_account_name_label",
                valueTestTag = "general_account_name",
            )
            if (model.bankBranchCode != null) {
                Spacer(Modifier.height(spacerMedium))
                DetailField(
                    label = stringResource(R.string.general_aba_routing_number_label),
                    value = model.bankBranchCode,
                    labelTestTag = "general_aba_routing_number_label",
                    valueTestTag = "general_aba_routing_number",
                )
            }
            Spacer(Modifier.height(spacerMedium))
            DetailField(
                label = stringResource(R.string.general_time_of_last_update_label),
                value = model.lastUpdateDate,
                labelTestTag = "general_time_of_last_update_label",
                valueTestTag = "general_time_of_last_update",
            )
        }
        Spacer(Modifier.height(spacerLarge))
        DetailSection(
            title = stringResource(R.string.header_interest_details),
            titleTestTag = "header_interest_details",
            cardTestTag = "content_interest_details",
        ) {
            if (model.accountInterestRate != null) {
                DetailField(
                    label = stringResource(R.string.interest_details_interest_rate_label),
                    value = model.accountInterestRate,
                    labelTestTag = "interest_details_interest_rate_label",
                    valueTestTag = "interest_details_interest_rate",
                )
                Spacer(Modifier.height(spacerMedium))
            }
            DetailField(
                label = stringResource(R.string.interest_details_accured_interest_label),
                value = model.accruedInterest,
                labelTestTag = "interest_details_accured_interest_label",
                valueTestTag = "interest_details_accured_interest",
            )
        }
        Spacer(Modifier.height(spacerLarge))
        DetailSection(
            title = stringResource(R.string.header_overdraft_details),
            titleTestTag = "header_overdraft_details",
            cardTestTag = "content_overdraft_details",
        ) {
            DetailField(
                label = stringResource(R.string.overdraft_details_overdraft_limit_label),
                value = model.creditLimit,
                labelTestTag = "overdraft_details_overdraft_limit_label",
                valueTestTag = "overdraft_details_overdraft_limit",
            )
        }
        Spacer(Modifier.height(spacerLarge))
        DetailSection(
            title = stringResource(R.string.header_other),
            titleTestTag = "header_other",
            cardTestTag = "content_other",
        ) {
            DetailField(
                label = stringResource(R.string.other_account_opening_date_label),
                value = model.accountOpeningDate,
                labelTestTag = "other_account_opening_date_label",
                valueTestTag = "other_account_opening_date",
            )
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    titleTestTag: String,
    cardTestTag: String,
    content: @Composable () -> Unit,
) {
    val spacerSmall = spacerSmall()
    val spacerMedium = spacerMedium()
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacerMedium)
            .testTag(titleTestTag)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = spacerSmall)
            .testTag(cardTestTag),
        shape = RoundedCornerShape(radiusLarge()),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(modifier = Modifier.padding(spacerMedium)) {
            content()
        }
    }
}

@Composable
private fun DetailField(
    label: String,
    value: String?,
    labelTestTag: String,
    valueTestTag: String,
) {
    Text(
        text = label,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(labelTestTag)
    )
    Text(
        text = value.orEmpty(),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(valueTestTag)
    )
}

@Composable
private fun AccountDetailError(messageRes: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacerMedium())
            .testTag("content_error"),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(radiusLarge()),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(DesignR.drawable.backbase_ic_error),
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .testTag("error_image")
                )
                Text(
                    text = stringResource(messageRes),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = spacerMedium())
                        .testTag("error_text")
                )
            }
        }
    }
}
