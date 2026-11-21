package com.godzistudio.godzimind.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.godzistudio.godzimind.ui.theme.Mist
import com.godzistudio.godzimind.ui.theme.Night

/** Fondo con degradado suave; todas las secciones viven en cards encima. */
@Composable
fun GodziBackground(content: @Composable BoxScope.() -> Unit) {
    val colors = if (isSystemInDarkTheme()) {
        listOf(Night, Color(0xFF0E1A16), Color(0xFF121411))
    } else {
        listOf(Color(0xFFE7F1ED), Mist, Color(0xFFFBF7EF))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors)),
        content = content
    )
}

/** Card base del proyecto: esquinas amplias, borde sutil y padding uniforme. */
@Composable
fun GodziCard(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentPadding: Dp = 20.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.55f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            content = content
        )
    }
}

/** Cuadro de emoji usado como icono ilustrativo en las cards. */
@Composable
fun EmojiBadge(
    emoji: String,
    background: Color = MaterialTheme.colorScheme.primaryContainer,
    size: Dp = 46.dp,
    fontSize: TextUnit = 22.sp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.34f))
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Text(text = emoji, fontSize = fontSize)
    }
}

/** Etiqueta tipo píldora para categorías y estados. */
@Composable
fun Pill(
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

/** Fila emoji + título + explicación, usada en la card "¿Qué hace GodziMind?". */
@Composable
fun InfoRow(
    emoji: String,
    title: String,
    body: String,
    tint: Color = MaterialTheme.colorScheme.primary
) {
    Row(verticalAlignment = Alignment.Top) {
        EmojiBadge(
            emoji = emoji,
            background = tint.copy(alpha = 0.14f),
            size = 38.dp,
            fontSize = 18.sp
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Encabezado reutilizable de card con emoji, título y subtítulo. */
@Composable
fun CardHeading(
    emoji: String,
    title: String,
    subtitle: String? = null,
    tint: Color = MaterialTheme.colorScheme.primary,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        EmojiBadge(emoji = emoji, background = tint.copy(alpha = 0.13f))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (trailing != null) {
            Spacer(Modifier.width(8.dp))
            trailing()
        }
    }
}
