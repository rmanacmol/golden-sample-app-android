package com.backbase.golden_sample_app.extend_journey.contacts.presentation.contactdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.backbase.golden_sample_app.R
import com.backbase.golden_sample_app.extend_journey.contacts.presentation.compose.spacerLarge
import com.backbase.golden_sample_app.extend_journey.contacts.presentation.compose.spacerMedium

@Composable
internal fun ContactDetailScreen(
    contactId: String,
    modifier: Modifier = Modifier,
) {
    val spacerMedium = spacerMedium()
    val spacerLarge = spacerLarge()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacerMedium)
    ) {
        Text(
            text = stringResource(R.string.contact_details_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacerLarge)
                .testTag("header")
        )
        Text(
            text = stringResource(R.string.contact_details_unimplemented),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("todo")
        )
        Text(
            text = stringResource(R.string.contact_details_id_selected, contactId),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("idSelected")
        )
    }
}
