# Reglas de ProGuard/R8 del proyecto.
# GodziMind no usa reflexión ni serialización por nombre, así que no necesita
# reglas especiales por ahora. Se dejan las habituales de Compose.
-keepattributes *Annotation*
