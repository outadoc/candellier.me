package fr.outadoc.portfolio

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import portfolio.composeapp.generated.resources.Res
import portfolio.composeapp.generated.resources.about_compose_action
import portfolio.composeapp.generated.resources.about_compose_text
import portfolio.composeapp.generated.resources.avatar
import portfolio.composeapp.generated.resources.avatar_caption
import portfolio.composeapp.generated.resources.bluesky
import portfolio.composeapp.generated.resources.compose
import portfolio.composeapp.generated.resources.envelope
import portfolio.composeapp.generated.resources.github
import portfolio.composeapp.generated.resources.linkedin
import portfolio.composeapp.generated.resources.mastodon
import portfolio.composeapp.generated.resources.rss

@Composable
fun App() {
    MaterialTheme(
        colorScheme = darkColorScheme(),
        typography = RenogareTypography()
    ) {
        val scrollState = rememberScrollState()
        val transition = rememberInfiniteTransition()
        val rotation by transition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                tween(
                    easing = LinearEasing,
                    durationMillis = 5_000,
                )
            )
        )

        Box(
            modifier = Modifier
                .drawBehind {
                    val gradient = Brush.linearGradient(
                        colors = listOf(
                            Color(0xffb455d0),
                            Color(0xff555bca)
                        ),
                    )

                    rotate(rotation) {
                        drawCircle(
                            brush = gradient,
                            radius = size.maxDimension,
                        )
                    }
                },
        ) {
            Scaffold(
                containerColor = Color.Unspecified,
                contentColor = MaterialTheme.colorScheme.onBackground,
                content = { insets ->
                    Row(
                        modifier = Modifier
                            .padding(insets)
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(32.dp)
                                .verticalScroll(scrollState),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                modifier = Modifier
                                    .hoverCard(shape = RoundedCornerShape(32.dp))
                                    .size(160.dp),
                                painter = painterResource(Res.drawable.avatar),
                                contentDescription = stringResource(Res.string.avatar_caption),
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                "Baptiste Candellier",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium,
                            )

                            Text(
                                "@outadoc",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                            )

                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .width(250.dp),
                            ) {
                                SocialButton(
                                    title = "Mastodon",
                                    url = "https://mastodon.social/@outadoc",
                                    icon = Res.drawable.mastodon
                                )

                                SocialButton(
                                    title = "Bluesky",
                                    url = "https://bsky.app/profile/outadoc.fr",
                                    icon = Res.drawable.bluesky
                                )

                                SocialButton(
                                    title = "GitHub",
                                    url = "https://github.com/outadoc",
                                    icon = Res.drawable.github
                                )

                                SocialButton(
                                    title = "LinkedIn",
                                    url = "https://www.linkedin.com/in/candellierba",
                                    icon = Res.drawable.linkedin
                                )

                                SocialButton(
                                    title = "Blog",
                                    url = "https://blog.outadoc.fr",
                                    icon = Res.drawable.rss
                                )

                                SocialButton(
                                    title = "Email",
                                    url = "mailto:baptiste@candellier.me",
                                    icon = Res.drawable.envelope
                                )
                            }
                        }
                    }
                },
                bottomBar = {
                    val uriHandler = LocalUriHandler.current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        ComposeLogo(
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable(
                                    onClick = { uriHandler.openUri("https://www.jetbrains.com/compose-multiplatform/") },
                                    onClickLabel = stringResource(Res.string.about_compose_action),
                                )
                                .padding(
                                    vertical = 8.dp,
                                    horizontal = 16.dp
                                )
                                .pointerHoverIcon(PointerIcon.Hand),
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun SocialButton(
    modifier: Modifier = Modifier,
    title: String,
    url: String,
    icon: DrawableResource,
) {
    val uriHandler = LocalUriHandler.current
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val scale by animateFloatAsState(if (isHovered) 1.1f else 1f)

    Button(
        modifier = modifier
            .hoverable(interactionSource)
            .scale(scale)
            .pointerHoverIcon(PointerIcon.Hand)
            .fillMaxWidth(),
        onClick = { uriHandler.openUri(url) },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(icon),
                contentDescription = null
            )
            Text(title)
        }
    }
}

@Composable
private fun ComposeLogo(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(Res.string.about_compose_text),
            style = MaterialTheme.typography.titleSmall,
        )
        Icon(
            modifier = Modifier.height(20.dp),
            tint = Color.Unspecified,
            painter = painterResource(Res.drawable.compose),
            contentDescription = "Compose Multiplatform",
        )
    }
}

@Composable
@Preview
private fun AppPreview() {
    App()
}
