package com.example.mealzapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.example.mealzapp.model.Categories
import com.example.mealzapp.ui.theme.MealzAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealzAppTheme {
                decisionScreen()
            }
        }
    }
}

@Composable
fun decisionScreen(){


    val viewModel: MAViewModel = viewModel()

    val rememberedMeals: MutableState<List<Categories>> = remember {
        mutableStateOf(emptyList())
    }

    val list = viewModel.ld.observeAsState(listOf<Categories>()).value
    rememberedMeals.value = list


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "route_meals" ){
        composable("route_meals"){
            MealsCategoryScreen(list, navController)
        }
        composable(route ="route_details/{mealId}",
           arguments = listOf(navArgument("mealId"){
               type = NavType.StringType
           })
        ){
            showMealDetailScreen(it.arguments?.getString("mealId"), list)
        }
    }
}

@Composable
fun MealsCategoryScreen(list: List<Categories>, navController: NavHostController) {

        LazyColumn {
            items(list) { gotItems ->
                showMeals(gotItems){
                    navController.navigate("route_details/${gotItems.idCategory}")
                }
            }

    }
}

@Composable
fun showMealDetailScreen(category: String?, list: List<Categories> ) {

    val state = remember{ mutableStateOf(false) }
    val animateDP: Dp by animateDpAsState(targetValue = if(state.value) 200.dp else 80.dp,
        label = ""
    )
    val ourList = list.find { cat -> cat.idCategory == category }
    Card(modifier = Modifier.fillMaxSize()) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(ourList?.strCategoryThumb),
                contentDescription = "ok",
                alignment = Alignment.Center,
                modifier = Modifier.size(animateDP).
                clickable { state.value = !state.value }
            )
            Text(text = ourList?.strCategoryDescription!!)
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun showMeals(gotItems: Categories, onCardClicked: () -> Unit) {
    val isExpanded : MutableState<Boolean> = remember{ mutableStateOf(false) }

    Card(shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp),
        backgroundColor = Color.Gray,
        onClick = {
            onCardClicked.invoke()
        }
    ) {
        Row {
            Image(painter = rememberAsyncImagePainter(gotItems.strCategoryThumb)
                , contentDescription = "ok",
                alignment = Alignment.Center
                , modifier = Modifier.size(80.dp)
            )
            Text(text = gotItems.strCategoryDescription!!,
                textAlign = TextAlign.Justify,
                color = Color.White,
                maxLines = if(isExpanded.value) 10 else 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Icon(imageVector = if(isExpanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
                , contentDescription = null
                    , modifier = Modifier
                    .wrapContentSize()
                    .padding(top = if (isExpanded.value) 60.dp else 10.dp),
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    Log.d("RE_LIFE", "Greeting: ")
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealzAppTheme {
    }
}