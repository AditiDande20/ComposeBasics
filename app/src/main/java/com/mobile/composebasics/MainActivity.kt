package com.mobile.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobile.composebasics.ui.theme.ComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CheckScreen()
                }
            }
        }
    }
}

@Composable
fun CheckScreen(){
    var shouldShowOnboardScreen by rememberSaveable {
        mutableStateOf(true)
    }
    if(shouldShowOnboardScreen){
        ShowSnowboardingScreen(onContinueClicked = { shouldShowOnboardScreen = false })
    }
    else{
        ShowList()
    }
}

@Composable
fun ShowSnowboardingScreen(onContinueClicked: () -> Unit) {
    Surface(modifier = Modifier
        .fillMaxWidth()) {
        Column(modifier = Modifier
            .fillMaxWidth(),verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to Jetpack Compose Codelab",modifier = Modifier.padding(5.dp))
            Button(modifier = Modifier.padding(10.dp),onClick = onContinueClicked) {
                Text(text = "CONTINUE")
            }
        }
    }
}

@Composable
fun ShowList(){
    val list = List(1000){"$it"}

   Column(modifier = Modifier.padding(2.dp)) {
       LazyColumn(){
           items(list){l->
               ShowItemView(name = l)
           }
       }
   }
    
}

@Composable
fun ShowItemView(name: String) {
    val isExpanded = remember {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(if(isExpanded.value) 48.dp else 0.dp,animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    ))

    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                .fillMaxWidth()) {
                Text(text = "Hello, ")
                Text(text = name, style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold))
                if (isExpanded.value) {
                    Text(
                        text = ("Go hands-on and learn the fundamentals of declarative UI, working with state, layouts, and theming. You'll see what composables and modifiers are, how to work with basic UI elements such as Row and Column, and how to give state to your app. ")
                            .repeat(1),
                    )
                }
            }

            IconButton(onClick = { isExpanded.value = !isExpanded.value }) {
                Icon(
                    imageVector = if (isExpanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded.value) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeBasicsTheme {
        ShowList()
    }
}