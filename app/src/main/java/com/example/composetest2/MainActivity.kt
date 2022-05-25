package com.example.composetest2

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetest2.data.CustomObj
import com.example.composetest2.data.CustomPizza
import com.example.composetest2.data.Size
import com.example.composetest2.destinations.orderScreenDestination
import com.example.composetest2.ui.theme.ComposeTest2Theme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.parcelize.Parcelize

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTest2Theme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

@Destination(start = true)
@Composable
fun OrderCustomisationScreen(
    navigator: DestinationsNavigator, vmd:PizzaViewModel = PizzaViewModel())
{
    vmd.getPizzaCrusts()
    val _sizeList  = remember { mutableStateListOf<Size>() }
    _sizeList.addAll(vmd.crustSizeList)
    val sizeList:List<Size> = _sizeList

    val totalAmount: MutableState<Int> = remember { mutableStateOf(235) }
    val localAmt:MutableState<Int> = remember{ mutableStateOf(235)}
    val pizzaCount:MutableState<Int> = remember { mutableStateOf(1) }

/*
    val selectedBase:MutableState<String> = remember {(mutableStateOf("Hand-tossed"))}
    val selectedSize:MutableState<String> = remember {(mutableStateOf("Regular"))}
    val selectedPizzaList = remember { mutableStateListOf<CustomPizza>() }
*/

    Scaffold(
        bottomBar = {
            bottomBar(totalAmount,localAmt,pizzaCount)}
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
            Column(
                Modifier
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                // Pizza Image
                Image(painter = painterResource(id = R.drawable.pizza_pic), contentDescription = "pizza pic")
                
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    // Nonveg Icon
                    Icon(painter = painterResource(id = R.drawable.nonveg_ic), contentDescription = "", modifier = Modifier
                        .height(24.dp)
                        .width(24.dp), tint = Color.Red)
                    // Tag chip
                    Card(modifier = Modifier.wrapContentSize(),
                        backgroundColor = Color.Red) {
                        Text(
                            text = "Bestseller",
                            color = Color.White,
                            modifier = Modifier.padding(
                                top = 3.dp,
                                bottom = 3.dp,
                                start = 5.dp,
                                end = 5.dp),
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))

                    // Cart Icon Button
                    IconButton(
                        onClick = {
//                            val myOrder:CustomObj = CustomObj(selectedPizzaList)
//                                  navigator.navigate(orderScreenDestination(myOrder = myOrder))
                        },
                        modifier = Modifier
                            .height(31.dp)
                            .width(31.dp)) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "",
                            tint = Color(0xFFDD3838),
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp))
                    }
                }
                //Pizza Name Text
                Text(
                    text = "Non-Veg Pizza",
                    modifier = Modifier.padding(start = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500)

                // Pizza desc Text
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    modifier = Modifier.padding(start = 10.dp, end = 8.dp),
                    fontSize = 14.sp, color = Color(0xFF717171)
                )
                starRatingView()
                shareNLikeBtn()
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    color = Color(0xFFD1D1D1))

                // Pizza base selection
                pizzaBase(vmd,_sizeList)

                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    color = Color(0xFFD1D1D1))

                // Pizza crust selection
                pizzaCrustSize(totalAmount,sizeList,localAmt,pizzaCount)
            }
        }
    }
}

@Destination
@Composable
fun orderScreen(myOrder:CustomObj){
    val orderList:List<CustomPizza> = myOrder.orderedPizzas
    LazyColumn{
        items(orderList){item ->
            Row() {
                Text(text = item.base)
                Text(text = item.size)
                Text(text = item.totalAmount.toString())
            }
        }
    }
}
@Composable
fun starRatingView(){
    Card(modifier = Modifier
        .wrapContentSize()
        .padding(10.dp), border = BorderStroke(1.dp,Color(0xFFFFDF00))) {
        Row(modifier = Modifier.padding(5.dp)) {
            for(i in 1..5){
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "",
                    modifier = Modifier
                        .width(12.dp)
                        .height(12.dp),
                    tint = Color(0xFFFFDF00),)
            }
        }
    }
}

@Composable
fun shareNLikeBtn() {
    Row(
        Modifier
            .wrapContentSize()
            .padding(10.dp)) {
        likeIcon()
        Spacer(modifier = Modifier.width(8.dp))
        shareIcon()
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun likeIcon(){
    var checked by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier
        .clip(CircleShape)
        .wrapContentSize()
        .border(1.dp, Color(0xFFE1E1E1), CircleShape)
        .background(Color.White)){
        IconToggleButton(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
        ) {
            val tint by animateColorAsState(if (checked) Color(0xFFEC407A) else Color(0xFFB0BEC5))
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Localized description", tint = tint)
        }
    }

}

@Composable
fun shareIcon(){
    Box(modifier = Modifier
        .clip(CircleShape)
        .wrapContentSize()
        .border(1.dp, Color(0xFFE1E1E1), CircleShape)
        .background(Color.White)){
        IconButton(onClick = {  },
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "",
                tint = Color(0xFFEC407A))
        }
    }

}

@Composable
fun requiredChip(){
    Box(modifier = Modifier
        .wrapContentSize()
        .padding(top = 3.dp)
        .clip(shape = RoundedCornerShape(3.dp))
        .background(Color(0xFFF1BFBF)),

        ) {
        Text(
            text = ("REQUIRED"),
            color = Color(0xFFDD3838),
            fontSize = 12.sp,
            modifier = Modifier
                .padding(10.dp,3.dp,10.dp,3.dp),
            style = TextStyle(
                fontWeight = FontWeight.W600
            )
        )
    }
}

@Composable
fun pizzaBase(vmd:PizzaViewModel,_sizeList:SnapshotStateList<Size>){

    Column(Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Column {
                Text("Base", fontSize = 20.sp,fontWeight = FontWeight.W500)
                Text(text = "Select from below options", color = Color.Gray, fontSize = 14.sp)
            }
            requiredChip()
        }
        // List will be passed here
        val isSelected:MutableState<String> = remember{(mutableStateOf("Hand-tossed"))}

        LazyRow(Modifier.padding(top = 8.dp)){
            items(vmd.pizzaCrustList){item ->
                Card(modifier = Modifier
                    .padding(end = 5.dp)
                    .clickable {
//                        selectedBase.value = item.name
                        isSelected.value = item.name
                        _sizeList.clear()
                        _sizeList.addAll(item.sizes)
                    },
                    shape = RoundedCornerShape(10),
                    backgroundColor = if(isSelected.value == item.name) Color(0xFFFF1D5C) else Color.White,
                    border = BorderStroke(1.dp,Color(0xFFDE5886)),
                    elevation = 5.dp) {
                    Text(text = item.name,
                        Modifier.padding(25.dp),
                    color = if(isSelected.value == item.name) Color.White else Color.Black)
                }
//                baseCardItem(item,_sizeList,sizeList,vmd.pizzaCrustList)
            }
        }
    }
}

/* Not using it currentyly
@Composable
fun baseCardItem(crust: Crust,_sizeList:SnapshotStateList<Size>,sizeList: List<Size>,pizzaCrustList:List<Crust>){

    val isSelected:MutableState<String> = remember{(mutableStateOf("Hand-tossed"))}

    Card(modifier = Modifier
        .padding(end = 5.dp)
        .clickable {
             Checking for is selected or not
            if(isSelected.value == crust.name){
               Log.d("taggg","selected: ${crust.name}")
            }
            isSelected.value = crust.name
            _sizeList.clear()
            _sizeList.addAll(crust.sizes)
        },
        shape = RoundedCornerShape(10),
        backgroundColor = if(isSelected.value == crust.name) Color.Red else Color.Gray,
        border = BorderStroke(1.dp,Color(0xFFDE5886)),
            elevation = 5.dp) {
        Text(text = crust.name,
        Modifier.padding(25.dp))
    }
}*/

@Composable
fun pizzaCrustSize(totalAmnt:MutableState<Int>,sizeList:List<Size>,localAmt: MutableState<Int>,pizzaCount: MutableState<Int>){

    Column(Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Column {
                Text("Size", fontSize = 20.sp,fontWeight = FontWeight.W500)
                Text(text = "Select from below options", color = Color.Gray, fontSize = 14.sp)
            }
            requiredChip()
        }
        if(sizeList.isNotEmpty()){
            val selectedItem:MutableState<Int> = remember {
                mutableStateOf(sizeList[0].price)
            }
            Column {
                sizeList.forEach {
                    Row(modifier = Modifier.padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Text(text = it.name)
                        Spacer(modifier = Modifier.weight(1F))
                        Text(text = "₹${it.price}")
                        RadioButton(
                            //                    sizeItem(totalAmnt,sizeList,size = i)
                            selected = it.price == selectedItem.value,
                            onClick = {
//                                selectedSize.value = it.name
                                pizzaCount.value = 1
                                localAmt.value = it.price
                                totalAmnt.value = it.price
                                selectedItem.value = it.price })
                    }
                }
                }
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
    }

/* not using currently
@Composable
fun sizeItem(totalAmnt: MutableState<Int>,sizeList: List<Size>,size:Size){

    val selectedItem:MutableState<Int> = remember {
        mutableStateOf(sizeList[0].price)
    }
    Row(modifier = Modifier.padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Text(text = size.name)
        Spacer(modifier = Modifier.weight(1F))
            Text(text = "₹${size.price}")
            RadioButton(
                selected = size.price == selectedItem.value,
                onClick = {
                    selectedItem.value = size.price })
    }
}*/

@Composable
fun bottomBar(amount:MutableState<Int>, localAmt:MutableState<Int>, pizzaCount:MutableState<Int>){
    Row(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()
        .padding(12.dp),
    verticalAlignment = Alignment.Bottom){
        Card(modifier = Modifier.weight(1f), border = BorderStroke(1.dp,Color((0xFFFF1D5C)))){
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                IconButton(onClick = {
                    if(pizzaCount.value > 1){
                        pizzaCount.value--
                        amount.value = amount.value - localAmt.value
                    }
                }) {
                    Icon(imageVector = Icons.Filled.Remove,
                        contentDescription = "",
                    tint = Color(0xFFFF1D5C))
                }
                Text(text = pizzaCount.value.toString())

                IconButton(
                    onClick = {
                    pizzaCount.value++
                        amount.value = (localAmt.value) * (pizzaCount.value)
                    })
                {
                    Icon(imageVector = Icons.Filled.Add,
                        tint = Color(0xFFFF1D5C),
                        contentDescription = "")
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Card(modifier = Modifier.weight(2f), backgroundColor = Color(0xFFFF1D5C)) {
            Text(text = "Add to cart ₹${amount.value}",
                color = Color.White,
                textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(14.dp)
                .clickable {

/*                    val pza = CustomPizza(selectedBase.value, selectedSize.value, amount.value)
                    selectedPizzaList.add(pza)
                    Log.d("tagggg", selectedPizzaList.toString())*/

                })
        }
    }
}



