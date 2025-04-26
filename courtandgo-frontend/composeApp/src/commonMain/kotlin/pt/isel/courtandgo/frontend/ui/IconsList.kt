package pt.isel.courtandgo.frontend.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val CalendarIcon: ImageVector
    get() {
        if (calendarIcon != null) {
            return calendarIcon!!
        }
        calendarIcon = ImageVector.Builder(
            name = "Calendar",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF0F172A)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(6.75f, 3f)
                verticalLineTo(5.25f)
                moveTo(17.25f, 3f)
                verticalLineTo(5.25f)
                moveTo(3f, 18.75f)
                verticalLineTo(7.5f)
                curveTo(3f, 6.2574f, 4.0074f, 5.25f, 5.25f, 5.25f)
                horizontalLineTo(18.75f)
                curveTo(19.9926f, 5.25f, 21f, 6.2574f, 21f, 7.5f)
                verticalLineTo(18.75f)
                moveTo(3f, 18.75f)
                curveTo(3f, 19.9926f, 4.0074f, 21f, 5.25f, 21f)
                horizontalLineTo(18.75f)
                curveTo(19.9926f, 21f, 21f, 19.9926f, 21f, 18.75f)
                moveTo(3f, 18.75f)
                verticalLineTo(11.25f)
                curveTo(3f, 10.0074f, 4.0074f, 9f, 5.25f, 9f)
                horizontalLineTo(18.75f)
                curveTo(19.9926f, 9f, 21f, 10.0074f, 21f, 11.25f)
                verticalLineTo(18.75f)
            }
        }.build()
        return calendarIcon!!
    }

private var calendarIcon: ImageVector? = null


