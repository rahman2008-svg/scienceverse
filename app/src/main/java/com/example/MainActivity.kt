package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.*
import com.example.ui.theme.ScienceAppTheme
import com.example.ui.theme.ScienceTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var activeTheme by remember { mutableStateOf(ScienceAppTheme.GALAXY) }

            ScienceTheme(themeState = activeTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScienceVerseApp(
                        activeTheme = activeTheme,
                        onThemeChange = { activeTheme = it }
                    )
                }
            }
        }
    }
}

enum class Screen {
    DASHBOARD,
    ENCYCLOPEDIA,
    TIMELINE,
    DISCOVERIES,
    SCIENCE_TIMELINE,
    FIELD_EXPLORER,
    BIOGRAPHY_READER,
    NOBEL_GALLERY,
    LAB_STORIES,
    INTERACTIVE_WORLD_MAP,
    DOCUMENTARY_LIBRARY,
    QUIZ,
    ACHIEVEMENTS,
    RESEARCH_NETWORK,
    DISCOVERY_TREE,
    SCIENCE_MUSEUM,
    TIME_MACHINE,
    SCIENCE_WORLD_HUB,
    ABOUT_INFO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScienceVerseApp(
    activeTheme: ScienceAppTheme,
    onThemeChange: (ScienceAppTheme) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var currentScreen by remember { mutableStateOf(Screen.DASHBOARD) }
    var selectedScientist by remember { mutableStateOf<Scientist?>(null) }
    var selectedDiscovery by remember { mutableStateOf<Discovery?>(null) }
    var selectedField by remember { mutableStateOf<String?>(null) }
    var selectedEra by remember { mutableStateOf<String>("Renaissance") }

    // Quiz and gamification state
    var quizScore by remember { mutableStateOf(0) }
    var quizStreak by remember { mutableStateOf(0) }
    val achievements = remember {
        mutableStateListOf(
            "Science Learner" to true,
            "Research Explorer" to false,
            "Discovery Master" to false,
            "Science Historian" to false,
            "Legend of Science" to false
        )
    }

    // Update achievement status based on score
    LaunchedEffect(quizScore) {
        if (quizScore >= 20) achievements[1] = "Research Explorer" to true
        if (quizScore >= 40) achievements[2] = "Discovery Master" to true
        if (quizScore >= 70) achievements[3] = "Science Historian" to true
        if (quizScore >= 100) achievements[4] = "Legend of Science" to true
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(310.dp),
                drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Drawer Header
                    Text(
                        text = "🔬 ScienceVerse",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Every Scientist. Every Discovery. One Universe.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    HorizontalDivider(modifier = Modifier.padding(bottom = 16.dp))

                    // Drawer Items (20 sub-items grouped elegantly)
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        item {
                            DrawerCategoryHeader(title = "Core Exploration")
                        }
                        item {
                            DrawerItem(
                                title = "Dashboard & Portal",
                                icon = Icons.Default.Dashboard,
                                selected = currentScreen == Screen.DASHBOARD,
                                onClick = { currentScreen = Screen.DASHBOARD; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Scientist Encyclopedia",
                                icon = Icons.Default.MenuBook,
                                selected = currentScreen == Screen.ENCYCLOPEDIA,
                                onClick = { currentScreen = Screen.ENCYCLOPEDIA; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Scientist Timeline",
                                icon = Icons.Default.Timeline,
                                selected = currentScreen == Screen.TIMELINE,
                                onClick = { currentScreen = Screen.TIMELINE; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Discoveries Library",
                                icon = Icons.Default.Biotech,
                                selected = currentScreen == Screen.DISCOVERIES,
                                onClick = { currentScreen = Screen.DISCOVERIES; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Field Explorer",
                                icon = Icons.Default.Explore,
                                selected = currentScreen == Screen.FIELD_EXPLORER,
                                onClick = { currentScreen = Screen.FIELD_EXPLORER; scope.launch { drawerState.close() } }
                            )
                        }

                        item {
                            DrawerCategoryHeader(title = "Interactive Zones")
                        }
                        item {
                            DrawerItem(
                                title = "⏳ Time Machine",
                                icon = Icons.Default.AutoMode,
                                selected = currentScreen == Screen.TIME_MACHINE,
                                onClick = { currentScreen = Screen.TIME_MACHINE; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Science World Map Hub",
                                icon = Icons.Default.Public,
                                selected = currentScreen == Screen.SCIENCE_WORLD_HUB,
                                onClick = { currentScreen = Screen.SCIENCE_WORLD_HUB; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Interactive Born Map",
                                icon = Icons.Default.Map,
                                selected = currentScreen == Screen.INTERACTIVE_WORLD_MAP,
                                onClick = { currentScreen = Screen.INTERACTIVE_WORLD_MAP; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Research Network Graph",
                                icon = Icons.Default.Share,
                                selected = currentScreen == Screen.RESEARCH_NETWORK,
                                onClick = { currentScreen = Screen.RESEARCH_NETWORK; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Discovery Trees",
                                icon = Icons.Default.AccountTree,
                                selected = currentScreen == Screen.DISCOVERY_TREE,
                                onClick = { currentScreen = Screen.DISCOVERY_TREE; scope.launch { drawerState.close() } }
                            )
                        }

                        item {
                            DrawerCategoryHeader(title = "Exhibits & Learning")
                        }
                        item {
                            DrawerItem(
                                title = "Virtual Science Museum",
                                icon = Icons.Default.AccountBalance,
                                selected = currentScreen == Screen.SCIENCE_MUSEUM,
                                onClick = { currentScreen = Screen.SCIENCE_MUSEUM; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Lab Stories & Anecdotes",
                                icon = Icons.Default.Science,
                                selected = currentScreen == Screen.LAB_STORIES,
                                onClick = { currentScreen = Screen.LAB_STORIES; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Nobel Gallery",
                                icon = Icons.Default.Star,
                                selected = currentScreen == Screen.NOBEL_GALLERY,
                                onClick = { currentScreen = Screen.NOBEL_GALLERY; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Chronology of Science",
                                icon = Icons.Default.CalendarMonth,
                                selected = currentScreen == Screen.SCIENCE_TIMELINE,
                                onClick = { currentScreen = Screen.SCIENCE_TIMELINE; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Documentary Library",
                                icon = Icons.Default.SlowMotionVideo,
                                selected = currentScreen == Screen.DOCUMENTARY_LIBRARY,
                                onClick = { currentScreen = Screen.DOCUMENTARY_LIBRARY; scope.launch { drawerState.close() } }
                            )
                        }

                        item {
                            DrawerCategoryHeader(title = "Assessments")
                        }
                        item {
                            DrawerItem(
                                title = "Quiz & Challenges",
                                icon = Icons.Default.Quiz,
                                selected = currentScreen == Screen.QUIZ,
                                onClick = { currentScreen = Screen.QUIZ; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerItem(
                                title = "Achievements & Ranks",
                                icon = Icons.Default.EmojiEvents,
                                selected = currentScreen == Screen.ACHIEVEMENTS,
                                onClick = { currentScreen = Screen.ACHIEVEMENTS; scope.launch { drawerState.close() } }
                            )
                        }
                        item {
                            DrawerCategoryHeader(title = "Information")
                        }
                        item {
                            DrawerItem(
                                title = "About Developer & Lab",
                                icon = Icons.Default.Info,
                                selected = currentScreen == Screen.ABOUT_INFO,
                                onClick = { currentScreen = Screen.ABOUT_INFO; scope.launch { drawerState.close() } }
                            )
                        }
                    }

                    // Theme selector in footer
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                    Text(
                        text = "CHOOSE PORTAL SYSTEM",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onThemeChange(ScienceAppTheme.GALAXY) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("theme_galaxy_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (activeTheme == ScienceAppTheme.GALAXY) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (activeTheme == ScienceAppTheme.GALAXY) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            Text("🌌 Galaxy", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { onThemeChange(ScienceAppTheme.LABORATORY) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("theme_lab_btn"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (activeTheme == ScienceAppTheme.LABORATORY) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (activeTheme == ScienceAppTheme.LABORATORY) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
                        ) {
                            Text("🧪 Laboratory", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = when (currentScreen) {
                                Screen.DASHBOARD -> "SCIENCEVERSE"
                                Screen.ENCYCLOPEDIA -> "SCIENTIST ENCYCLOPEDIA"
                                Screen.TIMELINE -> "SCIENTIST TIMELINE"
                                Screen.DISCOVERIES -> "DISCOVERIES LIBRARY"
                                Screen.SCIENCE_TIMELINE -> "SCIENCE CHRONOLOGY"
                                Screen.FIELD_EXPLORER -> "FIELD EXPLORER"
                                Screen.BIOGRAPHY_READER -> "BIOGRAPHY READER"
                                Screen.NOBEL_GALLERY -> "NOBEL GALLERY"
                                Screen.LAB_STORIES -> "LABORATORY STORIES"
                                Screen.INTERACTIVE_WORLD_MAP -> "INTERACTIVE BORN MAP"
                                Screen.DOCUMENTARY_LIBRARY -> "DOCUMENTARY REEL"
                                Screen.QUIZ -> "QUIZ CHALLENGE"
                                Screen.ACHIEVEMENTS -> "ACHIEVEMENT JOURNEY"
                                Screen.RESEARCH_NETWORK -> "RESEARCH INFLUENCE NETWORK"
                                Screen.DISCOVERY_TREE -> "DISCOVERY EVOLUTION TREE"
                                Screen.SCIENCE_MUSEUM -> "VIRTUAL SCIENCE MUSEUM"
                                Screen.TIME_MACHINE -> "⏳ TIME MACHINE"
                                Screen.SCIENCE_WORLD_HUB -> "SCIENCE WORLD HUBS"
                                Screen.ABOUT_INFO -> "ABOUT DEVELOPER & LAB"
                            },
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Navigation Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { currentScreen = Screen.QUIZ }) {
                            Icon(Icons.Default.WorkspacePremium, contentDescription = "Quizzes", tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9f)
                    )
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            // Draw background grid lines or space particle fields depending on active portal theme
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .drawBehind {
                        if (activeTheme == ScienceAppTheme.GALAXY) {
                            // Draw an ambient starfield
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color(0xFF0F0F12), Color(0xFF0A0A0D))
                                )
                            )
                            // Paint simple starry points
                            val starOffsets = listOf(
                                Offset(100f, 200f), Offset(400f, 150f), Offset(250f, 600f),
                                Offset(700f, 300f), Offset(600f, 850f), Offset(150f, 1100f),
                                Offset(850f, 1300f), Offset(350f, 1500f), Offset(800f, 500f)
                            )
                            starOffsets.forEachIndexed { idx, offset ->
                                val size = if (idx % 2 == 0) 3f else 5f
                                drawCircle(
                                    color = Color.White.copy(alpha = 0.4f),
                                    radius = size,
                                    center = offset
                                )
                            }
                        } else {
                            // Draw laboratory grid lines
                            drawRect(color = Color(0xFF090D0A))
                            val gridSpacing = 80f
                            for (x in 0..size.width.toInt() step gridSpacing.toInt()) {
                                drawLine(
                                    color = Color(0xFF00FF66).copy(alpha = 0.05f),
                                    start = Offset(x.toFloat(), 0f),
                                    end = Offset(x.toFloat(), size.height),
                                    strokeWidth = 1f
                                )
                            }
                            for (y in 0..size.height.toInt() step gridSpacing.toInt()) {
                                drawLine(
                                    color = Color(0xFF00FF66).copy(alpha = 0.05f),
                                    start = Offset(0f, y.toFloat()),
                                    end = Offset(size.width, y.toFloat()),
                                    strokeWidth = 1f
                                )
                            }
                        }
                    }
            ) {
                // Main animated screen router
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        slideInHorizontally { width -> width } + fadeIn() togetherWith
                                slideOutHorizontally { width -> -width } + fadeOut()
                    },
                    label = "screen_transition"
                ) { screen ->
                    when (screen) {
                        Screen.DASHBOARD -> DashboardScreen(
                            onNavigateTo = { currentScreen = it },
                            quizScore = quizScore,
                            streak = quizStreak,
                            achievements = achievements
                        )
                        Screen.ENCYCLOPEDIA -> EncyclopediaScreen(
                            onSelectScientist = {
                                selectedScientist = it
                                currentScreen = Screen.BIOGRAPHY_READER
                            }
                        )
                        Screen.TIMELINE -> ScientistTimelineScreen(
                            onSelectScientist = {
                                selectedScientist = it
                                currentScreen = Screen.BIOGRAPHY_READER
                            }
                        )
                        Screen.DISCOVERIES -> DiscoveriesLibraryScreen(
                            onSelectDiscovery = {
                                selectedDiscovery = it
                            }
                        )
                        Screen.SCIENCE_TIMELINE -> ScienceTimelineScreen()
                        Screen.FIELD_EXPLORER -> FieldExplorerScreen(
                            selectedField = selectedField,
                            onFieldSelected = { selectedField = it },
                            onSelectScientist = {
                                selectedScientist = it
                                currentScreen = Screen.BIOGRAPHY_READER
                            }
                        )
                        Screen.BIOGRAPHY_READER -> BiographyReaderScreen(
                            scientist = selectedScientist ?: ScienceDatabase.scientists.first(),
                            onBack = { currentScreen = Screen.ENCYCLOPEDIA }
                        )
                        Screen.NOBEL_GALLERY -> NobelGalleryScreen(
                            onSelectScientist = {
                                selectedScientist = it
                                currentScreen = Screen.BIOGRAPHY_READER
                            }
                        )
                        Screen.LAB_STORIES -> LabStoriesScreen()
                        Screen.INTERACTIVE_WORLD_MAP -> InteractiveWorldMapScreen(
                            onSelectScientist = {
                                selectedScientist = it
                                currentScreen = Screen.BIOGRAPHY_READER
                            }
                        )
                        Screen.DOCUMENTARY_LIBRARY -> DocumentaryLibraryScreen()
                        Screen.QUIZ -> QuizScreen(
                            score = quizScore,
                            onScoreChange = { quizScore = it },
                            streak = quizStreak,
                            onStreakChange = { quizStreak = it }
                        )
                        Screen.ACHIEVEMENTS -> AchievementsScreen(
                            score = quizScore,
                            achievements = achievements
                        )
                        Screen.RESEARCH_NETWORK -> ResearchNetworkScreen(
                            onSelectScientist = {
                                selectedScientist = it
                                currentScreen = Screen.BIOGRAPHY_READER
                            }
                        )
                        Screen.DISCOVERY_TREE -> DiscoveryTreeScreen()
                        Screen.SCIENCE_MUSEUM -> ScienceMuseumScreen()
                        Screen.TIME_MACHINE -> TimeMachineScreen(
                            selectedEra = selectedEra,
                            onSelectEra = { selectedEra = it },
                            onSelectScientist = {
                                selectedScientist = it
                                currentScreen = Screen.BIOGRAPHY_READER
                            }
                        )
                        Screen.SCIENCE_WORLD_HUB -> ScienceWorldHubScreen(
                            onFieldSelect = {
                                selectedField = it
                                currentScreen = Screen.FIELD_EXPLORER
                            },
                            onNobelSelect = {
                                currentScreen = Screen.NOBEL_GALLERY
                            }
                        )
                        Screen.ABOUT_INFO -> com.example.ui.AboutInfoScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerCategoryHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        ),
        modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 4.dp)
    )
}

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = { Icon(icon, contentDescription = title, modifier = Modifier.size(20.dp)) },
        label = { Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium) },
        selected = selected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedContainerColor = Color.Transparent,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            unselectedTextColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 4.dp)
    )
}

// ----------------------------------------------------
// SCREEN 1: DASHBOARD PORTAL
// ----------------------------------------------------
@Composable
fun DashboardScreen(
    onNavigateTo: (Screen) -> Unit,
    quizScore: Int,
    streak: Int,
    achievements: List<Pair<String, Boolean>>
) {
    val currentRank = when {
        quizScore >= 100 -> "Legend of Science"
        quizScore >= 70 -> "Science Historian"
        quizScore >= 40 -> "Discovery Master"
        quizScore >= 20 -> "Research Explorer"
        else -> "Science Learner"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            ) {
                // Background hero image with absolute safety fallback
                val heroPainter = painterResource(id = R.drawable.ic_launcher_background) // fallback resource
                Image(
                    painter = heroPainter,
                    contentDescription = "Cosmic Canvas",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "ScienceVerse Portal",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "Every Scientist. Every Discovery. One Universe.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                    )
                }
            }
        }

        // Today in Science Event Module
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Today, contentDescription = "Today in Science", tint = MaterialTheme.colorScheme.primary)
                        Text(
                            text = "TODAY IN SCIENCE (July 18)",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    ScienceDatabase.getTodayInScience(7, 18).forEach { event ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "• ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(
                                text = event,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }

        // Stats Panel
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Scientists",
                    value = "100+",
                    sub = "Biographies"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Discoveries",
                    value = "200+",
                    sub = "Curated entries"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    title = "Ranks",
                    value = currentRank.split(" ").first(),
                    sub = "$quizScore Points"
                )
            }
        }

        // Main Feature Hub grid
        item {
            Text(
                text = "EXPLORATION HUBS",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    InteractiveHubCard(
                        title = "⏳ Time Machine",
                        subtitle = "Travel through 7 historical eras",
                        color = MaterialTheme.colorScheme.primary,
                        onClick = { onNavigateTo(Screen.TIME_MACHINE) }
                    )
                }
                item {
                    InteractiveHubCard(
                        title = "🌌 Science World Map",
                        subtitle = "Isometric halls of science",
                        color = MaterialTheme.colorScheme.secondary,
                        onClick = { onNavigateTo(Screen.SCIENCE_WORLD_HUB) }
                    )
                }
                item {
                    InteractiveHubCard(
                        title = "🧩 Science Museum",
                        subtitle = "Interactive laboratory toys",
                        color = MaterialTheme.colorScheme.tertiary,
                        onClick = { onNavigateTo(Screen.SCIENCE_MUSEUM) }
                    )
                }
            }
        }

        // Quick Jumps
        item {
            Text(
                text = "OFFLINE DATABASE ENCYCLOPEDIA",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickJumpRow(
                    title = "Scientist Encyclopedia",
                    desc = "Browse complete biodata of legendary minds",
                    icon = Icons.Default.MenuBook,
                    onClick = { onNavigateTo(Screen.ENCYCLOPEDIA) }
                )
                QuickJumpRow(
                    title = "Discoveries & Inventions",
                    desc = "Gravity, DNA, quantum physics archives",
                    icon = Icons.Default.Biotech,
                    onClick = { onNavigateTo(Screen.DISCOVERIES) }
                )
                QuickJumpRow(
                    title = "Science Challenge Quiz",
                    desc = "Answer correctly and build your rank!",
                    icon = Icons.Default.Quiz,
                    onClick = { onNavigateTo(Screen.QUIZ) }
                )
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    sub: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onSurface)
            Text(sub, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun InteractiveHubCard(
    title: String,
    subtitle: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f)),
        border = BorderStroke(1.5.dp, color.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.AutoMode, contentDescription = title, tint = color)
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = color)
                Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
fun QuickJumpRow(
    title: String,
    desc: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(desc, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
            Icon(Icons.Default.ChevronRight, contentDescription = "Go")
        }
    }
}

// ----------------------------------------------------
// SCREEN 2: SCIENTIST ENCYCLOPEDIA (List / Details Grid)
// ----------------------------------------------------
@Composable
fun EncyclopediaScreen(
    onSelectScientist: (Scientist) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedEraFilter by remember { mutableStateOf("All") }
    var selectedFieldFilter by remember { mutableStateOf("All") }

    val filteredScientists = ScienceDatabase.scientists.filter {
        (it.name.contains(searchQuery, ignoreCase = true) || it.bengaliName.contains(searchQuery)) &&
                (selectedEraFilter == "All" || it.era == selectedEraFilter) &&
                (selectedFieldFilter == "All" || it.field == selectedFieldFilter)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search text field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search 10,000+ scientists... (English / বাংলা)") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            ),
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Era filter chips
        Text(
            text = "PERIOD / ERA FILTERS",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = selectedEraFilter == "All",
                    onClick = { selectedEraFilter = "All" },
                    label = { Text("All Eras") }
                )
            }
            val eras = listOf("Ancient", "Medieval", "Renaissance", "1800s", "1900s", "Contemporary")
            items(eras) { era ->
                FilterChip(
                    selected = selectedEraFilter == era,
                    onClick = { selectedEraFilter = era },
                    label = { Text(era) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Field filter chips
        Text(
            text = "SCIENTIFIC FIELD FILTERS",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = selectedFieldFilter == "All",
                    onClick = { selectedFieldFilter = "All" },
                    label = { Text("All Fields") }
                )
            }
            val fields = listOf("Physics", "Chemistry", "Biology", "Astronomy", "Mathematics", "Medicine", "Computer Science")
            items(fields) { field ->
                FilterChip(
                    selected = selectedFieldFilter == field,
                    onClick = { selectedFieldFilter = field },
                    label = { Text(field) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (filteredScientists.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.FolderOpen,
                        contentDescription = "Empty",
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("No scientists found matching filters.", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredScientists) { scientist ->
                    ScientistCard(
                        scientist = scientist,
                        onClick = { onSelectScientist(scientist) }
                    )
                }
            }
        }
    }
}

@Composable
fun ScientistCard(
    scientist: Scientist,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("scientist_card_${scientist.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Stylized profile photo placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = when (scientist.field) {
                            "Physics" -> Icons.Default.Waves
                            "Chemistry" -> Icons.Default.Science
                            "Biology" -> Icons.Default.Biotech
                            "Astronomy" -> Icons.Default.Public
                            "Mathematics" -> Icons.Default.Functions
                            "Computer Science" -> Icons.Default.Memory
                            else -> Icons.Default.Person
                        },
                        contentDescription = scientist.name,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = scientist.field.uppercase(),
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Monospace
                    )
                }
                if (scientist.hasNobel) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Nobel Laureate",
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Text(
                text = scientist.name,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = scientist.bengaliName,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = scientist.era,
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = scientist.nationality.split(",").first(),
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 3: SCIENTIST TIMELINE SCREEN
// ----------------------------------------------------
@Composable
fun ScientistTimelineScreen(
    onSelectScientist: (Scientist) -> Unit
) {
    val eras = listOf("Ancient", "Medieval", "Renaissance", "1800s", "1900s", "Contemporary")
    var selectedEra by remember { mutableStateOf("Renaissance") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScrollableTabRow(
            selectedTabIndex = eras.indexOf(selectedEra),
            edgePadding = 0.dp,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            eras.forEachIndexed { index, era ->
                Tab(
                    selected = selectedEra == era,
                    onClick = { selectedEra = era },
                    text = { Text(era, fontWeight = FontWeight.Bold) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val eraScientists = ScienceDatabase.scientists.filter { it.era == selectedEra }

        if (eraScientists.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("No timeline logs for this era yet.", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(eraScientists) { scientist ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectScientist(scientist) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Birth / Death Year Badge
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = scientist.birthDate.split(",").last().trim(),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "to",
                                        fontSize = 8.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                    Text(
                                        text = scientist.deathDate?.split(",")?.last()?.trim() ?: "Present",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(scientist.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(scientist.bengaliName, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Born in ${scientist.birthPlace}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            }
                            Icon(Icons.Default.ArrowForward, contentDescription = "Read Bio", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 4: DISCOVERIES LIBRARY SCREEN
// ----------------------------------------------------
@Composable
fun DiscoveriesLibraryScreen(
    onSelectDiscovery: (Discovery) -> Unit
) {
    var expandedDiscoveryId by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "HUNDREDS OF DISCOVERIES & INVENTIONS",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(ScienceDatabase.discoveries) { discovery ->
            val isExpanded = expandedDiscoveryId == discovery.id
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expandedDiscoveryId = if (isExpanded) null else discovery.id
                        onSelectDiscovery(discovery)
                    },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                                Text(
                                    text = "YEAR: ${discovery.year}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(discovery.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(discovery.bengaliName, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                        }
                        IconButton(onClick = { expandedDiscoveryId = if (isExpanded) null else discovery.id }) {
                            Icon(
                                if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = "Expand details"
                            )
                        }
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column(
                            modifier = Modifier.padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                            Text(
                                text = "Discoverer: ${discovery.discoverer}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = "HISTORY",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                            Text(discovery.history, fontSize = 13.sp)

                            Text(
                                text = "HOW IT WORKS",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                            Text(discovery.howItWorks, fontSize = 13.sp)

                            Text(
                                text = "CURRENT USAGE & IMPACT",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                            Text(discovery.currentUsage, fontSize = 13.sp)
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 5: CHRONOLOGY OF SCIENCE (TIMELINE EVENT LIST)
// ----------------------------------------------------
@Composable
fun ScienceTimelineScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "MILESTONES OF HUMAN PROGRESS",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(ScienceDatabase.timelineEvents) { event ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Year scale line node
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(60.dp)
                ) {
                    Text(
                        text = event.year,
                        fontWeight = FontWeight.Black,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(80.dp)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                    )
                }

                // Event Content Card
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = event.category.uppercase(),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = event.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = event.description,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 6: FIELD EXPLORER SCREEN (Physics, Chemistry, Biology, etc.)
// ----------------------------------------------------
@Composable
fun FieldExplorerScreen(
    selectedField: String?,
    onFieldSelected: (String?) -> Unit,
    onSelectScientist: (Scientist) -> Unit
) {
    val fields = listOf(
        "Physics" to Icons.Default.Waves,
        "Chemistry" to Icons.Default.Science,
        "Biology" to Icons.Default.Biotech,
        "Astronomy" to Icons.Default.Public,
        "Mathematics" to Icons.Default.Functions,
        "Medicine" to Icons.Default.Healing,
        "Computer Science" to Icons.Default.Memory
    )

    if (selectedField == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "EXPLORE BY DOMAIN",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(fields) { (fieldName, icon) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clickable { onFieldSelected(fieldName) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(icon, contentDescription = fieldName, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(fieldName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    } else {
        // Field Details Screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = { onFieldSelected(null) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "${selectedField.uppercase()} DEPARTMENT",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            val fieldScientists = ScienceDatabase.scientists.filter { it.field == selectedField }
            val fieldDiscoveries = ScienceDatabase.discoveries.filter { it.field == selectedField }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text("FIELD DESCRIPTION & IMPORTANCE", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)),
                        modifier = Modifier.padding(top = 4.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        Text(
                            text = "This scientific branch uncovers fundamental truths about nature, modeling systems using rigorous math and experimentation.",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(12.dp),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }

                item {
                    Text("PROMINENT MINDS", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                items(fieldScientists) { scientist ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectScientist(scientist) },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(scientist.name.first().toString(), fontWeight = FontWeight.Bold)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(scientist.name, fontWeight = FontWeight.Bold)
                                Text(scientist.bengaliName, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = "View Bio")
                        }
                    }
                }

                item {
                    Text("KEY DISCOVERIES IN THIS DEPT", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                items(fieldDiscoveries) { discovery ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(discovery.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(discovery.bengaliName, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(discovery.howItWorks, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 7: BIOGRAPHY READER & BOOK SHELF
// ----------------------------------------------------
@Composable
fun BiographyReaderScreen(
    scientist: Scientist,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Childhood", "Struggles", "Impact & Success", "Books & Papers")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button & Name Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column {
                Text(scientist.name, fontWeight = FontWeight.Black, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                Text(scientist.bengaliName, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 0.dp,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, tabTitle ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(tabTitle, fontWeight = FontWeight.Bold, fontSize = 11.sp) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                when (selectedTab) {
                    0 -> { // Overview Tab
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                // Large aesthetic avatar badge
                                Box(
                                    modifier = Modifier
                                        .size(90.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.School, contentDescription = "Education", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(48.dp))
                                }

                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    BioFact(label = "BORN PLACE", value = scientist.birthPlace)
                                    BioFact(label = "BORN ON", value = scientist.birthDate)
                                    BioFact(label = "DEATH", value = scientist.deathDate ?: "Present")
                                }
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                            BioFact(label = "NATIONALITY", value = scientist.nationality)
                            BioFact(label = "EDUCATION", value = scientist.education)
                            BioFact(label = "CAREER JOURNEY", value = scientist.career)

                            if (scientist.quotes.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("FAMOUS QUOTE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "\"${scientist.quotes.first()}\"",
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }
                    1 -> { // Childhood & Family
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("CHILDHOOD & EARLY ROOTS", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(scientist.childhood, fontSize = 14.sp, lineHeight = 20.sp)
                        }
                    }
                    2 -> { // Struggles & Hurdles
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("STRUGGLES, HURDLES & OPPOSITIONS", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(scientist.struggles, fontSize = 14.sp, lineHeight = 20.sp)
                        }
                    }
                    3 -> { // Success & Failure
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("CROWNED SUCCESSES", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(scientist.successes, fontSize = 13.sp, lineHeight = 18.sp)
                            HorizontalDivider()
                            Text("FAILURES & INACCURACIES", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                            Text(scientist.failures, fontSize = 13.sp, lineHeight = 18.sp)
                        }
                    }
                    4 -> { // Books & Papers
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("FAMOUS BOOKS & RESEARCH PAPERS", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            scientist.booksAndPapers.forEach { bp ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(bp.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f))
                                            Text(bp.year, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(bp.summary, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BioFact(label: String, value: String) {
    Column {
        Text(text = label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

// ----------------------------------------------------
// SCREEN 8: NOBEL GALLERY SCREEN
// ----------------------------------------------------
@Composable
fun NobelGalleryScreen(
    onSelectScientist: (Scientist) -> Unit
) {
    val nobelScientists = ScienceDatabase.scientists.filter { it.hasNobel }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "NOBEL LAUREATES HALL",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(nobelScientists) { scientist ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectScientist(scientist) },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                    border = BorderStroke(1.5.dp, Color(0xFFFFD700).copy(alpha = 0.6f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🏆 NOBEL IN ${scientist.nobelCategory} (${scientist.nobelYear})",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFFFFD700),
                                fontFamily = FontFamily.Monospace
                            )
                            Icon(Icons.Default.WorkspacePremium, contentDescription = "Gold Medal", tint = Color(0xFFFFD700))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(scientist.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(scientist.bengaliName, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = scientist.nobelResearch ?: "",
                            fontSize = 12.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 9: LABORATORY STORIES SCREEN
// ----------------------------------------------------
@Composable
fun LabStoriesScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "EPICENTERS OF WORLD DISCOVERIES",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(ScienceDatabase.laboratoryStories) { lab ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = lab.location.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(lab.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(lab.history, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f))

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("KEY DISCOVERIES", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        lab.keyDiscoveries.forEach { disc ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(disc, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("AMAZING ANECDOTE", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f)),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = lab.amazingAnecdote,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(10.dp),
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 10: INTERACTIVE BORN MAP SCREEN
// ----------------------------------------------------
@Composable
fun InteractiveWorldMapScreen(
    onSelectScientist: (Scientist) -> Unit
) {
    var highlightedScientist by remember { mutableStateOf<Scientist?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "TAP CHIPS TO MAP BIRTHPLACES",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Map Canvas Drawing representation
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.4f))
                .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Drawing styled map contour guides
                val gridPath = Path().apply {
                    moveTo(100f, 150f)
                    quadraticTo(250f, 50f, 400f, 150f)
                    quadraticTo(550f, 250f, 700f, 150f)
                    moveTo(150f, 300f)
                    quadraticTo(400f, 200f, 650f, 300f)
                }
                drawPath(
                    path = gridPath,
                    color = Color.White.copy(alpha = 0.08f),
                    style = Stroke(width = 4f)
                )

                // Place pins
                ScienceDatabase.scientists.forEach { sc ->
                    val x = size.width * sc.mapPosition.x
                    val y = size.height * sc.mapPosition.y
                    val isHighlighted = sc.id == highlightedScientist?.id

                    drawCircle(
                        color = if (isHighlighted) Color(0xFFFF3366) else Color(0xFF00FF66),
                        radius = if (isHighlighted) 12f else 6f,
                        center = Offset(x, y)
                    )
                }
            }

            Text(
                text = "Stylized Coordinate grid Projection",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.3f),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            )
        }

        // Selected Scientist Card hook
        highlightedScientist?.let { sc ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectScientist(sc) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("BIRTH PLACE: ${sc.birthPlace}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text(sc.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Nationality: ${sc.nationality}", fontSize = 12.sp)
                    }
                    IconButton(onClick = { onSelectScientist(sc) }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "View Profile", tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        // List to trigger markers
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(ScienceDatabase.scientists) { sc ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { highlightedScientist = sc },
                    colors = CardDefaults.cardColors(
                        containerColor = if (sc.id == highlightedScientist?.id) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                    ),
                    border = BorderStroke(1.dp, if (sc.id == highlightedScientist?.id) MaterialTheme.colorScheme.primary else Color.Transparent)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(sc.name, fontWeight = FontWeight.Bold)
                            Text(sc.birthPlace, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("PULL PIN", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 11: DOCUMENTARY LIBRARY (Interactive presentation slides)
// ----------------------------------------------------
@Composable
fun DocumentaryLibraryScreen() {
    var activeStoryIdx by remember { mutableStateOf(0) }
    var currentSlideIdx by remember { mutableStateOf(0) }

    val documentaryStories = listOf(
        "Newton and the Falling Apple" to listOf(
            "Isaac Newton escaped the London Plague of 1665 to his home at Woolsthorpe.",
            "As he was drinking tea in the orchard, he watched an apple fall straight to the ground.",
            "He wondered: why does the apple always descend perpendicular to the ground?",
            "He concluded that there must be an attractive force drawing the mass to the center of Earth, formulated as Gravity!"
        ),
        "Marie Curie's Isolation of Radium" to listOf(
            "Marie and Pierre worked in a leaky, drafty shed at the Paris school of physics.",
            "They stirred giant vats of heavy pitchblende ore with an iron rod for days.",
            "By purifying tonnes of waste, they isolated a fraction of a gram of pure radium chloride.",
            "The glowing blue light of radium in the dark shed confirmed the dawn of radioactivity!"
        ),
        "Turing and the Enigma Decryptor" to listOf(
            "In WWII, Bletchley Park was tasked with cracking the 'uncrackable' German Enigma rotor messages.",
            "While cryptanalysts worked by hand, Turing realized only a machine could defeat another machine.",
            "He designed the electromechanical 'Bombe', testing millions of combinations rapidly.",
            "By finding key repetitive words, the Bombe deciphered Enigma codes, saving millions of lives."
        )
    )

    val currentStory = documentaryStories[activeStoryIdx]
    val currentSlides = currentStory.second

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "CHOOSE SCIENTIFIC JOURNEY REEL",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Dropdown/Selector buttons for stories
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            documentaryStories.forEachIndexed { index, (title, _) ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            activeStoryIdx = index
                            currentSlideIdx = 0
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (activeStoryIdx == index) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                    ),
                    border = BorderStroke(1.dp, if (activeStoryIdx == index) MaterialTheme.colorScheme.primary else Color.Transparent)
                ) {
                    Text(
                        text = title.split(" ").first(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Visual animation slide window
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Animated slideshow graphic simulation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.3f))
                        .drawBehind {
                            // Dynamic animations drawn depending on active slide
                            drawCircle(
                                color = Color(0xFF00FF66).copy(alpha = 0.15f),
                                radius = 80f + 20f * sin(currentSlideIdx.toFloat()),
                                center = Offset(size.width / 2f, size.height / 2f)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = when (activeStoryIdx) {
                                0 -> Icons.Default.FilterDrama
                                1 -> Icons.Default.Science
                                else -> Icons.Default.Memory
                            },
                            contentDescription = "Anim",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "SCENE ${currentSlideIdx + 1}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Subtitle panel text
                Text(
                    text = currentSlides[currentSlideIdx],
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                // Navigation slides controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { if (currentSlideIdx > 0) currentSlideIdx-- },
                        enabled = currentSlideIdx > 0,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Prev")
                    }

                    // Progress indicator
                    Text(
                        text = "${currentSlideIdx + 1} / ${currentSlides.size}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    Button(
                        onClick = { if (currentSlideIdx < currentSlides.size - 1) currentSlideIdx++ },
                        enabled = currentSlideIdx < currentSlides.size - 1,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 12: QUIZ CHALLENGE (Gamified engine)
// ----------------------------------------------------
@Composable
fun QuizScreen(
    score: Int,
    onScoreChange: (Int) -> Unit,
    streak: Int,
    onStreakChange: (Int) -> Unit
) {
    var activeIdx by remember { mutableStateOf(0) }
    var selectedOptionIdx by remember { mutableStateOf<Int?>(null) }
    var hasAnswered by remember { mutableStateOf(false) }

    val currentQuestion = ScienceDatabase.quizzes[activeIdx]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Gamified score header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(Icons.Default.WorkspacePremium, contentDescription = "Score", tint = Color(0xFFFFD700))
                Text("SCORE: $score", fontWeight = FontWeight.Bold)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(Icons.Default.Whatshot, contentDescription = "Streak", tint = Color(0xFFFF4500))
                Text("STREAK: $streak", fontWeight = FontWeight.Bold)
            }
        }

        LinearProgressIndicator(
            progress = { (activeIdx + 1).toFloat() / ScienceDatabase.quizzes.size.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "CATEGORY: ${currentQuestion.category.uppercase()}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentQuestion.question,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )
            }
        }

        // Multiple Choices
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            currentQuestion.options.forEachIndexed { index, option ->
                val isSelected = selectedOptionIdx == index
                val optionColor = when {
                    hasAnswered && index == currentQuestion.correctAnswerIndex -> Color(0xFF00FF66).copy(alpha = 0.2f)
                    hasAnswered && isSelected && index != currentQuestion.correctAnswerIndex -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                    isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)
                }
                val borderColor = when {
                    hasAnswered && index == currentQuestion.correctAnswerIndex -> Color(0xFF00FF66)
                    hasAnswered && isSelected && index != currentQuestion.correctAnswerIndex -> MaterialTheme.colorScheme.error
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !hasAnswered) { selectedOptionIdx = index }
                        .testTag("quiz_option_$index"),
                    colors = CardDefaults.cardColors(containerColor = optionColor),
                    border = BorderStroke(1.5.dp, borderColor)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ('A' + index).toString(),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(option, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        // Control buttons and explanations
        if (hasAnswered) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "EXPLANATION: ${currentQuestion.explanation}",
                    fontSize = 11.sp,
                    modifier = Modifier.padding(10.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            Button(
                onClick = {
                    activeIdx = (activeIdx + 1) % ScienceDatabase.quizzes.size
                    selectedOptionIdx = null
                    hasAnswered = false
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("NEXT QUESTION")
            }
        } else {
            Button(
                onClick = {
                    if (selectedOptionIdx != null) {
                        hasAnswered = true
                        if (selectedOptionIdx == currentQuestion.correctAnswerIndex) {
                            onScoreChange(score + 10)
                            onStreakChange(streak + 1)
                        } else {
                            onStreakChange(0)
                        }
                    }
                },
                enabled = selectedOptionIdx != null,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("SUBMIT ANSWER")
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 13: ACHIEVEMENTS & PROGRESS SCREEN
// ----------------------------------------------------
@Composable
fun AchievementsScreen(
    score: Int,
    achievements: List<Pair<String, Boolean>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "SCIENTIFIC RECOGNITION PATH",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.WorkspacePremium, contentDescription = "Rank", tint = Color(0xFFFFD700), modifier = Modifier.size(54.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text("YOUR TOTAL SCORE: $score", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Keep answering quizzes correctly to level up your scientific title!", fontSize = 11.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(achievements) { (title, unlocked) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (unlocked) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
                    ),
                    border = BorderStroke(1.dp, if (unlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (unlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                if (unlocked) Icons.Default.Check else Icons.Default.Lock,
                                contentDescription = "Lock",
                                tint = if (unlocked) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(title, fontWeight = FontWeight.Bold)
                            Text(
                                text = if (unlocked) "Unlocked & Claimed" else "Accumulate points to unlock",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 14: RESEARCH NETWORK GRAPH
// ----------------------------------------------------
@Composable
fun ResearchNetworkScreen(
    onSelectScientist: (Scientist) -> Unit
) {
    var activeRootScientist by remember { mutableStateOf(ScienceDatabase.scientists.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "MENTORSHIP & COLLABORATIVE INFLUENCE",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Visual Canvas Node Network
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.3f))
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerNode = Offset(size.width / 2f, size.height / 2f)
                // Draw connecting radial lines
                val connectionOffsets = listOf(
                    Offset(centerNode.x - 120f, centerNode.y - 60f),
                    Offset(centerNode.x + 120f, centerNode.y - 60f),
                    Offset(centerNode.x - 80f, centerNode.y + 80f),
                    Offset(centerNode.x + 80f, centerNode.y + 80f)
                )

                connectionOffsets.forEach { off ->
                    drawLine(
                        color = Color(0xFF00FF66).copy(alpha = 0.4f),
                        start = centerNode,
                        end = off,
                        strokeWidth = 2f
                    )
                    drawCircle(
                        color = Color(0xFF00FF66),
                        radius = 8f,
                        center = off
                    )
                }

                // Center main scientist node
                drawCircle(
                    color = Color(0xFFFF3366),
                    radius = 16f,
                    center = centerNode
                )
            }

            Text(
                text = activeRootScientist.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 28.dp)
            )
        }

        // Connections Deck
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "CONNECTIONS OF ${activeRootScientist.name.uppercase()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace
                )

                // Mentors
                Column {
                    Text("TEACHERS / MENTORS", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    activeRootScientist.teachers.forEach { t ->
                        Text("• $t", fontSize = 13.sp)
                    }
                    if (activeRootScientist.teachers.isEmpty()) Text("No documented academic mentors in archives.", fontSize = 11.sp, color = Color.Gray)
                }

                HorizontalDivider()

                // CoWorkers
                Column {
                    Text("CO-WORKERS & ASSOCIATES", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    activeRootScientist.coWorkers.forEach { c ->
                        Text("• $c", fontSize = 13.sp)
                    }
                    if (activeRootScientist.coWorkers.isEmpty()) Text("Worked independently in research periods.", fontSize = 11.sp, color = Color.Gray)
                }
            }
        }

        // Tap to change networks
        Text("SELECT BASE SCIENTIST NODE", fontWeight = FontWeight.Bold, fontSize = 12.sp)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(ScienceDatabase.scientists) { sc ->
                FilterChip(
                    selected = sc.id == activeRootScientist.id,
                    onClick = { activeRootScientist = sc },
                    label = { Text(sc.name) }
                )
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 15: DISCOVERY EVOLUTION TREE
// ----------------------------------------------------
@Composable
fun DiscoveryTreeScreen() {
    val treeBranches = listOf(
        Triple("Gravity & Motion", "Newtonian mechanics formulated", "Formulated gravity in 1687, governing planetary mechanics."),
        Triple("Theory of Relativity", "Warped space-time concepts", "Albert Einstein showed gravity is a warped spacetime curvature in 1915."),
        Triple("Quantum Mechanics", "Subatomic packets of action", "Planck & Bohr proved mechanics are quantized at subatomic level.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "EVOLUTION OF PHYSICAL LAWS",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(treeBranches.size) { index ->
                val (title, concept, details) = treeBranches[index]
                Column {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "NODE STAGE ${index + 1}",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(concept, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(details, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                        }
                    }

                    if (index < treeBranches.size - 1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(20.dp)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                                Icon(
                                    Icons.Default.ArrowDownward,
                                    contentDescription = "evolves into",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 16: VIRTUAL SCIENCE MUSEUM (Interactive Simulators)
// ----------------------------------------------------
@Composable
fun ScienceMuseumScreen() {
    var selectedExhibitIdx by remember { mutableStateOf(0) }
    val exhibits = ScienceDatabase.museumExhibits

    // Simulator slider states
    var focalValue by remember { mutableStateOf(0.5f) }
    var lightingValue by remember { mutableStateOf(0.4f) }
    var pressureValue by remember { mutableStateOf(0.3f) }
    var matchedBasePairs by remember { mutableStateOf(0) }

    val activeExhibit = exhibits[selectedExhibitIdx]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "SELECT VIRTUAL MUSEUM TOY",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(exhibits.size) { idx ->
                FilterChip(
                    selected = selectedExhibitIdx == idx,
                    onClick = { selectedExhibitIdx = idx },
                    label = { Text(exhibits[idx].name) }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = activeExhibit.bengaliName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace
                )
                Text(activeExhibit.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(activeExhibit.description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))

                HorizontalDivider()

                Text(
                    text = "LIVE INTERACTIVE SIMULATOR: ${activeExhibit.interactiveSimulatorName.uppercase()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily.Monospace
                )

                // Render specific controls depending on exhibit
                when (activeExhibit.id) {
                    "telescope" -> {
                        Column {
                            Text("Focal Alignment Slider: ${(focalValue * 100).toInt()}%", fontSize = 12.sp)
                            Slider(
                                value = focalValue,
                                onValueChange = { focalValue = it },
                                modifier = Modifier.testTag("focal_slider")
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // Alignment result box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (focalValue in 0.45f..0.55f) {
                                    Text("🔭 Jupiter aligned! 4 Moons visible!", color = Color(0xFF00FF66), fontWeight = FontWeight.Bold)
                                } else {
                                    Text("Image blurry... keep sliding", color = Color.White.copy(alpha = 0.5f))
                                }
                            }
                        }
                    }
                    "microscope" -> {
                        Column {
                            Text("Illumination Aperture: ${(lightingValue * 100).toInt()}%", fontSize = 12.sp)
                            Slider(value = lightingValue, onValueChange = { lightingValue = it })
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (lightingValue >= 0.8f) {
                                    Text("🧫 Onion cell membranes crystal clear!", color = Color(0xFF00FF66), fontWeight = FontWeight.Bold)
                                } else {
                                    Text("Too dark. Move slider right", color = Color.White.copy(alpha = 0.5f))
                                }
                            }
                        }
                    }
                    "steam_engine" -> {
                        Column {
                            Text("Boiler Output: ${(pressureValue * 150).toInt()} PSI", fontSize = 12.sp)
                            Slider(value = pressureValue, onValueChange = { pressureValue = it })
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(90.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                val efficiency = (pressureValue * 40f).toInt()
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Flywheel Speed: ${(pressureValue * 300).toInt()} RPM", color = Color.White)
                                    Text("Calculated Carnot Efficiency: $efficiency%", color = Color(0xFF00FF66), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    "dna_model" -> {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Match correct Base Pairs!", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { matchedBasePairs++ },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("A with T")
                                }
                                Button(
                                    onClick = { matchedBasePairs++ },
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("C with G")
                                }
                            }
                            Text("Matched Strands: $matchedBasePairs / 10", color = Color(0xFF00FF66), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                activeExhibit.dynamicFormula?.let { formula ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("GOVERNING EQUATION", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Monospace)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = formula,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 17: TIME MACHINE (Immersive navigation)
// ----------------------------------------------------
@Composable
fun TimeMachineScreen(
    selectedEra: String,
    onSelectEra: (String) -> Unit,
    onSelectScientist: (Scientist) -> Unit
) {
    val eras = listOf(
        "Ancient Greece" to "Mathematical beginnings and philosophy origins.",
        "Islamic Golden Age" to "Algebra inventions and medical preservation.",
        "Renaissance" to "The scientific awakening of heliocentrism.",
        "Industrial Revolution" to "Steam, energy laws, electricity.",
        "Modern Science" to "Relativity, quantum, DNA helix.",
        "Space Age" to "Apollo moon expeditions and rockets."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "CHOOSE CHRONO-PORTAL",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Slide list of era selectors
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(eras.size) { idx ->
                val eraTitle = eras[idx].first
                FilterChip(
                    selected = selectedEra == eraTitle,
                    onClick = { onSelectEra(eraTitle) },
                    label = { Text(eraTitle) },
                    modifier = Modifier.testTag("era_chip_$idx")
                )
            }
        }

        // Deep space visual portal
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Interactive space animation canvas
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.HourglassEmpty, contentDescription = "Hourglass", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(36.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "PORTAL ENTRANCE ALIGNED",
                            color = Color(0xFF00FF66),
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }

                Text(
                    text = "ERA: ${selectedEra.uppercase()}",
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 14.sp
                )

                // Match database scientists belonging to this era
                val dbEraMapping = when (selectedEra) {
                    "Ancient Greece" -> "Ancient"
                    "Islamic Golden Age" -> "Medieval"
                    "Renaissance" -> "Renaissance"
                    "Industrial Revolution" -> "1800s"
                    "Modern Science" -> "1900s"
                    else -> "Contemporary"
                }

                val eraScientists = ScienceDatabase.scientists.filter { it.era == dbEraMapping }

                Text("LEADING INTELLECTS OF THIS AGE", fontWeight = FontWeight.Bold, fontSize = 12.sp)

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(eraScientists) { scientist ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectScientist(scientist) },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(scientist.name, fontWeight = FontWeight.Bold)
                                    Text(scientist.bengaliName, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                                }
                                Icon(Icons.Default.ArrowForward, contentDescription = "View Profile", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                    if (eraScientists.isEmpty()) {
                        item {
                            Text("No profiles mapped for this era in local index. Check out other eras!", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------
// SCREEN 18: SCIENCE WORLD LAB HUB (Map of halls)
// ----------------------------------------------------
@Composable
fun ScienceWorldHubScreen(
    onFieldSelect: (String) -> Unit,
    onNobelSelect: () -> Unit
) {
    val hubs = listOf(
        "Astronomy City" to "Explore celestial maps & cosmologists.",
        "Physics Lab" to "Newtonian & relativity mechanics toys.",
        "Chemistry Lab" to "Isolated molecules & periodic tables.",
        "Biology Center" to "DNA helix structures and genomes.",
        "Mathematics Hall" to "Algebra, primes, and infinity grids.",
        "Medical Institute" to "Anatomical structures & antibiotics.",
        "Nobel Hall" to "Prestige gold medals & research summaries."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "TAP HUB SECTORS TO EXPLORE",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(hubs) { (title, subtitle) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clickable {
                            if (title == "Nobel Hall") {
                                onNobelSelect()
                            } else {
                                onFieldSelect(title.split(" ").first())
                            }
                        },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = when (title) {
                                "Astronomy City" -> Icons.Default.Public
                                "Physics Lab" -> Icons.Default.Waves
                                "Chemistry Lab" -> Icons.Default.Science
                                "Biology Center" -> Icons.Default.Biotech
                                "Mathematics Hall" -> Icons.Default.Functions
                                "Medical Institute" -> Icons.Default.Healing
                                else -> Icons.Default.WorkspacePremium
                            },
                            contentDescription = title,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Column {
                            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}
