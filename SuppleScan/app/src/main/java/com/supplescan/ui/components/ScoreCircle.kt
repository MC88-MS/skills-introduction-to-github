package com.supplescan.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.supplescan.domain.scoring.ScoringEngine
import com.supplescan.domain.scoring.toColor
import com.supplescan.domain.scoring.toDisplayLetter

/**
 * Animated circular score indicator similar to Yuka's score display.
 */
@Composable
fun ScoreCircle(
    score: Int,
    grade: ScoringEngine.ScoreGrade,
    size: Dp = 140.dp,
    strokeWidth: Dp = 10.dp,
    modifier: Modifier = Modifier
) {
    val scoreColor = Color(grade.toColor())
    val trackColor = MaterialTheme.colorScheme.surfaceVariant
    val letter = grade.toDisplayLetter()

    // Animated sweep angle
    val animatedProgress = remember { Animatable(0f) }
    LaunchedEffect(score) {
        animatedProgress.animateTo(
            targetValue = score / 100f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = strokeWidth.toPx()
            val inset = strokePx / 2f
            val arcSize = Size(this.size.width - strokePx, this.size.height - strokePx)
            val topLeft = Offset(inset, inset)

            // Background track
            drawArc(
                color = trackColor,
                startAngle = -220f,
                sweepAngle = 260f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = scoreColor,
                startAngle = -220f,
                sweepAngle = 260f * animatedProgress.value,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = letter,
                fontSize = (size.value * 0.25f).sp,
                fontWeight = FontWeight.ExtraBold,
                color = scoreColor
            )
            Text(
                text = "$score",
                fontSize = (size.value * 0.14f).sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

/**
 * Small inline score badge for list items.
 */
@Composable
fun ScoreBadge(
    score: Int,
    grade: ScoringEngine.ScoreGrade,
    modifier: Modifier = Modifier
) {
    val scoreColor = Color(grade.toColor())
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(48.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = 4.dp.toPx()
            val inset = strokePx / 2f
            val arcSize = Size(this.size.width - strokePx, this.size.height - strokePx)

            drawArc(
                color = scoreColor.copy(alpha = 0.2f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = strokePx)
            )
            drawArc(
                color = scoreColor,
                startAngle = -90f,
                sweepAngle = 360f * (score / 100f),
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }
        Text(
            text = grade.toDisplayLetter(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = scoreColor
        )
    }
}
