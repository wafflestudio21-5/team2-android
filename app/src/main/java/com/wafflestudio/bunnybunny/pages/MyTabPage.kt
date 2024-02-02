package com.wafflestudio.bunnybunny.pages

import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.UI.bunnyColor
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.components.compose.LoginInputTextField
import com.wafflestudio.bunnybunny.data.example.EditProfileRequest
import com.wafflestudio.bunnybunny.utils.calculateMannerTempColor
import com.wafflestudio.bunnybunny.utils.formatProductTime
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

@Composable
fun MyTabPageView(
    viewModel: MainViewModel,
    navController: NavController
){
    LaunchedEffect(Dispatchers.IO){
        viewModel.getUserInfo()
    }
    val user by viewModel.userInfo.collectAsState()
    viewModel.updateProfileImage(user.profileImageUrl?:"https://files.slack.com/files-pri/T06UKPBS8-F06FHL84UTH/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png")
    Log.d("abcd",viewModel.profileImage.collectAsState().value)
    Column{
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("ProfilePage")
                }
        ) {
            Row {
                val painter = rememberImagePainter(data = user.profileImageUrl?:"https://files.slack.com/files-pri/T06UKPBS8-F06FHL84UTH/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png")
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(20.dp)
                        .width(60.dp)
                        .clip(CircleShape)
                )
                Text(user.nickname, modifier = Modifier
                    .align(CenterVertically)
                    .weight(1.5f),
                    fontWeight = FontWeight.ExtraBold, fontSize = 25.sp)
                Box(
                    modifier = Modifier
                        .padding(15.dp)
                        .weight(1f)
                        .background(color = bunnyColor, shape = RoundedCornerShape(10.dp))
                        .align(CenterVertically),

                ) {
                    Text("프로필 보기", fontSize = 13.sp, color = Color.White,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Center))
                }
            }
        }
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("WishListPage")
                }
        ){
            Row{
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "WishList",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .align(CenterVertically)
                )
                Box(modifier = Modifier
                    .padding(10.dp)
                    .align(CenterVertically)
                    ,
                ) {
                    Text("관심목록")
                }
            }
        }
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("MyItemListPage")
                }
        ){
            Row{
                Icon(
                    Icons.Outlined.List,
                    contentDescription = "MyItemList",
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .align(CenterVertically)
                )
                Box(modifier = Modifier
                    .padding(10.dp)
                    .align(CenterVertically)
                    ,
                ) {
                    Text("판매내역")
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyItemListPage(viewModel: MainViewModel, navController: NavController){
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel.myPostList){
        viewModel.getMyPostList()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {Text("판매내역")},
                navigationIcon = {
                    Row{
                        BackButton(navController = navController)
                    }
                },
            )
        }
    ) { paddingvalues ->
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

    LazyColumn(state = listState, modifier = Modifier.padding(paddingvalues)) {
        item {
            //물품 필터
        }
        items(viewModel.myPostList.value) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp)
                .clickable {
                    Log.d("aaaa", it.id.toString())
                    //Log.d("aaaa","GoodsPostPage/${it.id}")
                    navController.navigate("GoodsPostPage/${it.id}")
                }
            ) {
                Row(Modifier.align(Alignment.CenterStart)) {
                    val painter = rememberImagePainter(data = it.repImg)

                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                1.dp,
                                Color.Gray.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(corner = CornerSize(8.dp))
                            )
                            .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                            .clipToBounds(),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(text = it.title, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = it.tradingLocation + "·" + formatProductTime(
                                it.createdAt,
                                it.refreshedAt
                            ), color = Color.Gray, fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = it.sellPrice.toString() + "원", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(3.dp),
                        ) {
                            Spacer(Modifier.weight(1f))
                            if (it.wishCnt > 0) {
                                Image(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = null
                                )
                                Text(text = it.wishCnt.toString())
                            }
                            if (it.chatCnt > 0) {
                                Text("채팅")
                                Text(text = it.chatCnt.toString())
                            }
                        }
                    }
                }
            }
            Divider(
                color = Color.Gray.copy(alpha = 0.2f),
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListPage(viewModel: MainViewModel, navController: NavController){
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel.wishList){
        viewModel.getWishList()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {Text("관심목록")},
                navigationIcon = {
                    Row{
                        BackButton(navController = navController)
                    }
                },
            )
        }
    ) { paddingvalues ->
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
        )

        LazyColumn(state = listState, modifier = Modifier.padding(paddingvalues)) {
            item {
                //물품 필터
            }
            items(viewModel.wishList.value) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 18.dp)
                    .clickable {
                        Log.d("aaaa", it.id.toString())
                        //Log.d("aaaa","GoodsPostPage/${it.id}")
                        navController.navigate("GoodsPostPage/${it.id}")
                    }
                ) {
                    Row(Modifier.align(Alignment.CenterStart)) {
                        val painter = rememberImagePainter(data = it.repImg)

                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .border(
                                    1.dp,
                                    Color.Gray.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(corner = CornerSize(8.dp))
                                )
                                .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
                                .clipToBounds(),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(text = it.title, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = it.tradingLocation + "·" + formatProductTime(
                                    it.createdAt,
                                    it.refreshedAt
                                ), color = Color.Gray, fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = it.sellPrice.toString() + "원", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                            ) {
                                Spacer(Modifier.weight(1f))
                                if (it.wishCnt > 0) {
                                    Image(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = null
                                    )
                                    Text(text = it.wishCnt.toString())
                                }
                                if (it.chatCnt > 0) {
                                    Text("채팅")
                                    Text(text = it.chatCnt.toString())
                                }
                            }
                        }
                    }
                }
                Divider(
                    color = Color.Gray.copy(alpha = 0.2f),
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(viewModel: MainViewModel, navController: NavController){
    val user by viewModel.userInfo.collectAsState()
    Log.d("user",user.nickname)
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {Text("프로필")},
                navigationIcon = {
                    Row{
                        BackButton(navController = navController)
                    }
                },
            )
        }
    ) { paddingvalues ->
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
            )
        Column(modifier = Modifier.padding(paddingvalues)) {
            Row(
                modifier = Modifier.padding(20.dp)
            ) {
                val painter = rememberImagePainter(data = user.profileImageUrl)
                Log.d("abcd",user.profileImageUrl?:"https://files.slack.com/files-pri/T06UKPBS8-F06FHL84UTH/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png")
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(100.dp, 100.dp)
                        .clip(CircleShape)
                )
                Text(
                    user.nickname, modifier = Modifier
                        .align(CenterVertically)
                        .padding(start = 20.dp),
                    fontWeight = FontWeight.ExtraBold, fontSize = 30.sp
                )
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(bunnyColor, shape = RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate("ProfileEditPage")
                    },
            ){
                Text("프로필 수정",
                    modifier = Modifier.align(Center),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
            val temp = user.mannerTemp.toDouble()
            val color = calculateMannerTempColor(temp)
            val normalizedTemp = (temp - 30).coerceIn(0.0, 15.0) / 15f
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
//                .border(
//                    width = 1.dp,
//                    color = bunnyColor,
//                    shape = RoundedCornerShape(percent = 20)
//                ),
                ){
                Text("매너 온도", modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.ExtraBold
                )
                Text(text = "${temp} °C \uD83D\uDE04",
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .align(Alignment.End),
                    color = color,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(temp.toString(),
                        modifier = Modifier
                            .weight((normalizedTemp + 0.05).toFloat())
                            .padding(5.dp),
                        textAlign = TextAlign.End,
                        )
                    Spacer(Modifier.weight(1-normalizedTemp.toFloat()))
                }
                LinearProgressIndicator(
                    progress = normalizedTemp.toFloat(), // Normalize to 0.0 to 1.0
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                        .clip(CircleShape),
                    color = color
                )
            }




        }

    }



}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditPage(viewModel: MainViewModel, navController: NavController){
    val user by viewModel.userInfo.collectAsState()
    Log.d("user",user.nickname)
    var newPassword by rememberSaveable { mutableStateOf("") }
    var newNickname by rememberSaveable { mutableStateOf(user.nickname) }

    val (permissionRequested, setPermissionRequested) = remember { mutableStateOf(false) }

    val allPermissionsGranted = viewModel.neededStoragePermissions().all {
        ContextCompat.checkSelfPermission(LocalContext.current, it) == PackageManager.PERMISSION_GRANTED
    }

    val multiplePermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { permissions ->
        // 권한 요청 결과 처리. permissions는 Map<String, Boolean> 형태입니다.
        if(permissions.entries.all { it.value }){
            Log.d("aaaa","all_granted")
            navController.navigate("GalleryViewProfilePage") {
                launchSingleTop = true
            }
        }
    }
    LaunchedEffect(permissionRequested) {
        if (permissionRequested) {
            if (allPermissionsGranted) {
                // 모든 권한이 이미 부여되었을 경우의 처리
                Log.d("aaaa","already_granted")
                navController.navigate("GalleryViewProfilePage") {
                    this.launchSingleTop = true
                }
            } else {
                // 하나 이상의 권한이 부여되지 않았을 경우 권한 요청 로직
                multiplePermissionsLauncher.launch(viewModel.neededStoragePermissions())
            }

            setPermissionRequested(false) // 상태를 다시 초기화
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {Text("프로필 수정")},
                navigationIcon = {
                    Row{
                        BackButton(navController = navController)
                    }
                },
            )
        }
    ){paddingValues ->
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(15.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            val context = LocalContext.current
            val painter = rememberImagePainter(data = viewModel.profileImage.collectAsState().value)
            Log.d("abc",viewModel.profileImage.collectAsState().value)
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(10.dp)
                    .size(100.dp, 100.dp)
                    .clip(CircleShape)
                    .clickable {
                        setPermissionRequested(true)
                    },
                alignment = Center
            )
            Text("새 닉네임", modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 17.dp, vertical = 5.dp))
            LoginInputTextField(
                value = newNickname,
                onValueChange = {newText -> newNickname = newText},
                placeholder = newNickname,)
            Text("새 비밀번호", modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 17.dp, vertical = 5.dp))
            LoginInputTextField(
                value = newPassword,
                onValueChange = {newText -> newPassword = newText},
                placeholder = newPassword,)
            Button(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = bunnyColor,
                    contentColor = Color.White,
                ),
                onClick = {
                    try {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (newPassword.length == 0) {
                                viewModel.editProfile(
                                    EditProfileRequest(
                                        null,
                                        newNickname,
                                        viewModel.profileImage.value
                                    )
                                )
                            } else {
                                viewModel.editProfile(
                                    EditProfileRequest(
                                        newPassword,
                                        newNickname,
                                        viewModel.profileImage.value
                                    )
                                )
                            }
                        }
                        Log.d("aaaa", "request success")
                        navController.popBackStack()
                    }
                    catch (e: HttpException){
                        Toast.makeText(context, e.response()?.errorBody()?.string(), Toast.LENGTH_LONG).show()
                    }
                }
            ){
                Text("완료")
            }
        }
    }

}