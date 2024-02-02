package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.components.compose.CloseButton
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitCommunityPostRequest
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitPostRequest
import com.wafflestudio.bunnybunny.viewModel.CommunityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class WritePage {
    Home,
    Gallery,
}

@Composable
fun WriteCommunityPostPage(navController: NavController){
    val viewModel= hiltViewModel<CommunityViewModel>()
    val selectedWritePage by viewModel.selectedWritePage.collectAsState()
    when(selectedWritePage){
        WritePage.Home->{
            WriteCommunityPostPageView(navController)
        }
        WritePage.Gallery->{
            GalleryViewCommunityPage(viewModel = viewModel)
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteCommunityPostPageView(navController: NavController){
    val viewModel= hiltViewModel<CommunityViewModel>()
    val context=LocalContext.current

    val uploadImages by viewModel.uploadImages.collectAsState()

    //var title by rememberSaveable { mutableStateOf("") }
    //var isTitleFocused by remember { mutableStateOf(false) }
    val titleScrollState = rememberScrollState()
    //var sellPrice by rememberSaveable { mutableStateOf("") }
    //var isSellPriceFocused by remember { mutableStateOf(false) }
    //var offerYn by rememberSaveable { mutableStateOf(false) }
    //var description by rememberSaveable { mutableStateOf("") }
    //var isDescriptionFocused by remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current

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
            navController.navigate("GalleryViewPage")
        }
    }
    LaunchedEffect(permissionRequested) {
        if (permissionRequested) {
            if (allPermissionsGranted) {
                // 모든 권한이 이미 부여되었을 경우의 처리
                viewModel.updateGalleryImages(fetchGalleryImages(context))
                viewModel.updateSelectedImages(listOf())
                viewModel.selectPage(WritePage.Gallery)
            } else {
                // 하나 이상의 권한이 부여되지 않았을 경우 권한 요청 로직
                multiplePermissionsLauncher.launch(viewModel.neededStoragePermissions())
            }

            setPermissionRequested(false) // 상태를 다시 초기화
        }
    }

    Scaffold(topBar = {
        Column{
            TopAppBar(
                title = { Text("동네생활 글쓰기")},
                navigationIcon = {
                    Row {
                        CloseButton(navController = navController)
                    }
                },
                actions = {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .then(
                            if (viewModel.title.isNotEmpty()&&viewModel.description.isNotEmpty()) {
                                Modifier.clickable {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        try {
                                            val images=if(viewModel.uploadImages.value.isNotEmpty())viewModel.uploadImages(uploadImages,context).images else null
                                            Log.d("submitpost", "image success")
                                            viewModel.submitCommunityPost(
                                                SubmitCommunityPostRequest(
                                                    areaId = viewModel.getRefAreaId()[0],
                                                    title = viewModel.title,
                                                    description = viewModel.description,
                                                    repImg=if(images!=null)images[0] else null,
                                                    images = images,
                                                )
                                            )
                                            withContext(Dispatchers.Main) {
                                                //게시글 작성에 성공.
                                                // 내가 쓴 글 페이지로 이동(현재 페이지를 stack에서 지우면서)
                                                navController.popBackStack()
                                            }
                                        } catch (e: Exception) {
                                            //
                                            Log.d("submitpost", "submitpost failed:$e")
                                        }
                                    }
                                }
                            } else Modifier
                        ),
                        contentAlignment = Alignment.CenterEnd){
                        Text("완료", color = if(viewModel.title.isNotEmpty()&&viewModel.description.isNotEmpty())Color.Black else Color.Gray)

                    }
                },)
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    },bottomBar = {
        Column {
            Divider(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Box(contentAlignment = Alignment.Center, modifier = Modifier.clickable {
                setPermissionRequested(true)

            }){
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically){
                    Icon(imageVector = Icons.Outlined.Image, contentDescription = null)
                    Text("사진")
                }
            }
        }
    }){paddingValues->
        LazyColumn(Modifier.padding(paddingValues)){
            item{
                Column(Modifier.padding(16.dp)){
                    Row {
                        /*
                        Box(
                            Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .border(
                                    width = 2.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(7.dp)
                                )
                                .clickable {
                                    setPermissionRequested(true)
                                },
                            contentAlignment = Alignment.Center
                        ){
                            Column(horizontalAlignment = Alignment.CenterHorizontally){
                                Icon(
                                    imageVector = Icons.Outlined.CameraAlt,
                                    contentDescription = "CameraAlt"
                                )
                                Text("${uploadImages.size}/10")
                            }
                        }
                        */
                    }

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp))
                    BasicTextField(
                        value = viewModel.title,
                        onValueChange = { viewModel.title = it},
                        singleLine=true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                        ),
                        decorationBox = {
                                innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 45.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .padding(12.dp)
                                    .horizontalScroll(titleScrollState),
                            ){
                                if(viewModel.title.isEmpty()){
                                    Text("제목을 입력하세요", color = Color.Gray)
                                }
                                innerTextField()
                            }
                        }
                    )
                    LaunchedEffect(viewModel.title) {
                        titleScrollState.scrollTo(titleScrollState.maxValue)
                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp))

                    BasicTextField(
                        value = viewModel.description,
                        onValueChange = { viewModel.description = it},
                        keyboardOptions = KeyboardOptions.Default.copy(
                            //
                        ),
                        keyboardActions = KeyboardActions(
                            //
                        ),
                        decorationBox = {
                                innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 160.dp)
                                    .padding(12.dp),
                            ){
                                if(viewModel.description.isEmpty()){
                                    Text("(지역명) 관련된 질문이나 이야기를 해보세요", color = Color.Gray)
                                }
                                innerTextField()
                            }
                        }
                    )
                    LazyRow{
                        items(uploadImages){uri->
                            Spacer(modifier = Modifier
                                .fillMaxHeight()
                                .width(16.dp))
                            Box(modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .border(
                                    width = 2.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(7.dp)
                                )
                                .clickable {
                                    val updateList = uploadImages.toMutableList()
                                    updateList.remove(uri)
                                    viewModel.updateUploadImages(updateList)
                                }){
                                Image(
                                    painter = rememberImagePainter(data = uri),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentScale =  ContentScale.FillWidth
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteCommunityPostPageToolBar(navController: NavController){

}


