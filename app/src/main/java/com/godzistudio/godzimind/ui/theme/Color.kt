package com.godzistudio.godzimind.ui.theme

import androidx.compose.ui.graphics.Color

// Paleta principal: verde profundo (calma) + índigo (enfoque).
val TealDeep = Color(0xFF0B5D56)
val Teal = Color(0xFF0F6B63)
val TealSoft = Color(0xFFBCEFE7)
val TealMist = Color(0xFFE8F6F2)
val IndigoDeep = Color(0xFF232A5C)
val Indigo = Color(0xFF39427A)
val Lavender = Color(0xFF7B6BC9)
val LavenderSoft = Color(0xFFE7E1FB)
val Amber = Color(0xFF9A6B1F)
val AmberSoft = Color(0xFFF8E7C8)

val Ink = Color(0xFF17211E)
val Mist = Color(0xFFF3F6F4)
val Cloud = Color(0xFFFFFFFF)
val Slate = Color(0xFF4C5B57)
val Line = Color(0xFFD7E2DD)

val Night = Color(0xFF0B1210)
val NightSurface = Color(0xFF141D1A)
val NightSurfaceVariant = Color(0xFF22302C)
val NightLine = Color(0xFF33443E)
val NightText = Color(0xFFE2ECE7)

/**
 * Colores de marca que no dependen del tema claro/oscuro: se usan sobre los
 * degradados fijos de las cabeceras (Inicio y Pendientes).
 */
val HeroGradient = listOf(TealDeep, IndigoDeep)
val OnHero = Color.White
