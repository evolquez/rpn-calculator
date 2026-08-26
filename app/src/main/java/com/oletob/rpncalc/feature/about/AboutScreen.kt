package com.oletob.rpncalc.feature.about

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oletob.rpncalc.BuildConfig
import com.oletob.rpncalc.R
import com.oletob.rpncalc.ui.theme.Accent
import com.oletob.rpncalc.ui.theme.AppBackground
import com.oletob.rpncalc.ui.theme.ButtonSecondary
import com.oletob.rpncalc.ui.theme.Divider as DividerColor
import com.oletob.rpncalc.ui.theme.Muted
import com.oletob.rpncalc.ui.theme.PanelBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onNavigateBack: () -> Unit) {
    val uriHandler = LocalUriHandler.current
    val githubUrl = stringResource(R.string.author_github_profile)

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.about), color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Accent)
                    }
                },
                actions = {
//                    VersionPill()
//                    Spacer(Modifier.width(16.dp))
                },
                //colors = TopAppBarDefaults.topAppBarColors(containerColor = AppBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            AppIconGraphic()
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.rpn_calculator),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.reverse_polish_notation).uppercase(),
                fontSize = 12.sp,
                color = Accent,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(Modifier.height(20.dp))
            EngineInfoCard()
            Spacer(Modifier.height(12.dp))
            HighlightsCard()
            Spacer(Modifier.height(12.dp))
            FooterCard(githubUrl = githubUrl, onOpenGithub = { uriHandler.openUri(githubUrl) })
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun VersionPill() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .border(1.dp, Accent, RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "v${BuildConfig.VERSION_NAME}",
            color = Accent,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AppIconGraphic() {
    Box(
        modifier = Modifier
            .size(96.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(ButtonSecondary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(Modifier.width(36.dp).height(5.dp).clip(RoundedCornerShape(3.dp)).background(Muted))
            Box(Modifier.width(48.dp).height(5.dp).clip(RoundedCornerShape(3.dp)).background(Muted))
            Box(Modifier.width(60.dp).height(7.dp).clip(RoundedCornerShape(4.dp)).background(Accent))
        }
    }
}

@Composable
private fun EngineInfoCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PanelBackground)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Info, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.about_engine_header).uppercase(),
                color = Muted,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
        }
        Spacer(Modifier.height(12.dp))
        val prefix = stringResource(R.string.engine_description_prefix)
        val highlight = stringResource(R.string.engine_operations_highlight)
        Text(
            text = buildAnnotatedString {
                append(prefix)
                append(" ")
                withStyle(SpanStyle(color = Accent, fontWeight = FontWeight.Bold)) {
                    append(highlight)
                }
                append(".")
            },
            color = Color.White,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )
    }
}

@Composable
private fun HighlightsCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PanelBackground)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.calculator_highlights_header).uppercase(),
            color = Muted,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        Spacer(Modifier.height(12.dp))
        FeatureRow(
            icon = Icons.Filled.DateRange,
            title = stringResource(R.string.feature_history_title),
            description = stringResource(R.string.feature_history_description)
        )
        HorizontalDivider(color = DividerColor, modifier = Modifier.padding(vertical = 12.dp))
        FeatureRow(
            icon = Icons.Filled.Settings,
            title = stringResource(R.string.feature_postfix_title),
            description = stringResource(R.string.feature_postfix_description)
        )
    }
}

@Composable
private fun FeatureRow(icon: ImageVector, title: String, description: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(ButtonSecondary),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(
                text = description,
                color = Muted,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun FooterCard(githubUrl: String, onOpenGithub: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PanelBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = githubUrl.removePrefix("https://"),
            color = Accent,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(onClick = onOpenGithub)
        )
        Text(
            text = stringResource(R.string.version_format, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE),
            color = Muted,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 12.dp)
        )
        Text(
            text = stringResource(R.string.about_tagline),
            color = Muted,
            fontSize = 11.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
