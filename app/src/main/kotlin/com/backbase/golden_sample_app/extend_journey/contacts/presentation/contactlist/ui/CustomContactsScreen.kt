package com.backbase.golden_sample_app.extend_journey.contacts.presentation.contactlist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.backbase.golden_sample_app.R
import com.backbase.golden_sample_app.extend_journey.contacts.presentation.compose.spacerMedium
import com.backbase.golden_sample_app.extend_journey.contacts.presentation.contactlist.model.ContactUiModel
import com.backbase.android.design.R as DesignR

@Composable
internal fun CustomContactsScreen(
    uiState: CustomContactsScreenState,
    onSearch: (String) -> Unit,
    onLoadMore: (String) -> Unit,
    onContactClick: (String) -> Unit,
    onBack: () -> Unit,
    onAddContact: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by rememberSaveable { mutableStateOf("") }
    val spacerMedium = spacerMedium()
    val listState = rememberLazyListState()

    LaunchedEffect(listState, query) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val total = listState.layoutInfo.totalItemsCount
            lastVisible to total
        }.collect { (lastVisible, total) ->
            if (total > 0 && lastVisible >= total - 1) {
                onLoadMore(query)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacerMedium)
        ) {
            Text(
                text = stringResource(R.string.contacts_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 64.dp)
                    .testTag("contactsTitle")
            )
            OutlinedTextField(
                value = query,
                onValueChange = { value ->
                    query = value
                    onSearch(value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("searchTextInput"),
                placeholder = { Text(stringResource(R.string.contacts_search_hint)) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(DesignR.drawable.backbase_ic_search),
                        contentDescription = null,
                    )
                },
                singleLine = true,
            )
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .testTag("contactlist")
            ) {
                items(
                    items = uiState.contacts,
                    key = { contact -> contact.id }
                ) { contact ->
                    ContactRow(
                        uiModel = contact,
                        onClick = onContactClick,
                    )
                }
            }
        }
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .testTag("toolbar")
        ) {
            Icon(
                painter = painterResource(DesignR.drawable.backbase_ic_arrow_back),
                contentDescription = stringResource(androidx.appcompat.R.string.abc_action_bar_up_description),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
        FloatingActionButton(
            onClick = onAddContact,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = spacerMedium, bottom = spacerMedium)
                .testTag("fab")
        ) {
            Icon(
                painter = painterResource(DesignR.drawable.backbase_ic_add),
                contentDescription = stringResource(R.string.contacts_add),
            )
        }
    }
}

@Composable
private fun ContactRow(
    uiModel: ContactUiModel,
    onClick: (String) -> Unit,
) {
    val spacerMedium = spacerMedium()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(uiModel.id) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(uiModel.color))
                .testTag("avatar"),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = uiModel.avatarName,
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = spacerMedium),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = uiModel.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("contactName")
            )
            Text(
                text = uiModel.number,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.testTag("contactNumber")
            )
        }
    }
}
