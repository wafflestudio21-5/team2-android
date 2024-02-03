@file:OptIn(ExperimentalMaterialApi::class)

package com.wafflestudio.bunnybunny.pages

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TagFaces
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.wafflestudio.bunnybunny.SampleData.DefaultCommunityPostContentSample
import com.wafflestudio.bunnybunny.SampleData.DefaultUserInfo
import com.wafflestudio.bunnybunny.components.compose.BackButton
import com.wafflestudio.bunnybunny.components.compose.MoreVertButton
import com.wafflestudio.bunnybunny.components.compose.NotificationsButton
import com.wafflestudio.bunnybunny.components.compose.ShareButton
import com.wafflestudio.bunnybunny.data.example.UserInfo
import com.wafflestudio.bunnybunny.lib.network.dto.ChildComment
import com.wafflestudio.bunnybunny.lib.network.dto.Comment
import com.wafflestudio.bunnybunny.utils.convertEpochMillisToFormattedTime
import com.wafflestudio.bunnybunny.utils.formatProductTime
import com.wafflestudio.bunnybunny.viewModel.CommunityViewModel
import com.wafflestudio.bunnybunny.viewModel.MainViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CommunityPostPage(id: Long, navController: NavController) {
    val viewModel = hiltViewModel<CommunityViewModel>()
    val mainViewModel: MainViewModel = hiltViewModel()
    val communityPostContent by viewModel.communityPostContent.collectAsState()
    val comments by viewModel.comments.collectAsState()
    //Log.d("aaaa", id.toString() + " and " + communityPostContent.id.toString())
    LaunchedEffect(key1 = true) {
        viewModel.updateCommunityPostContent(DefaultCommunityPostContentSample)
        viewModel.getCommunityPostContent(id)
        viewModel.fetchComments(id)
    }
    val scope = rememberCoroutineScope()
    val keyboardManager = LocalSoftwareKeyboardController.current
    var writingComment by remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        mainViewModel.getUserInfo()
    }
    val myInfo = mainViewModel.userInfo.collectAsState(initial = DefaultUserInfo)
    var moreClickedCommentId by remember {
        mutableStateOf<Long?>(null)
    }
    var dimmed by remember {
        mutableStateOf(false)
    }
    var editingCommentId by remember {
        mutableStateOf<Long?>(null)
    }
    val focusManager = remember {
        FocusRequester()
    }


    //val listState = rememberLazyListState()
    //val imgHeight = 400
    /*
    val alpha = remember {
        derivedStateOf {
            if (listState.layoutInfo.visibleItemsInfo.firstOrNull() == null)
                0f
            else if (listState.firstVisibleItemIndex == 0)
                (listState.firstVisibleItemScrollOffset.toFloat() / listState.layoutInfo.visibleItemsInfo.firstOrNull()!!.size)
            else 1f
        }
    }

    //dpToPixel(imgHeight.toFloat(),)

    val alpha by remember {
        derivedStateOf {
            // Calculate the alpha based on the scroll offset
            // Coerce the value to be between 0f and 1f
            (
                    //1f-(listState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?:0)/imgHeight

            ).toFloat()//.coerceIn(0f, 1f)
        }
    }*/
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    ))
    BackHandler(scaffoldState.bottomSheetState.isExpanded) {
        scope.launch {
            scaffoldState.bottomSheetState.collapse()
            dimmed = false
        }
    }
    BottomSheetScaffold(
        sheetGesturesEnabled = false,
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "댓글 수정하기", modifier = Modifier
                    .clickable {
                        scope.launch {
                            dimmed = false
                            editingCommentId = moreClickedCommentId
                            scaffoldState.bottomSheetState.collapse()
                            moreClickedCommentId = null
                        }
                    }
                    .padding(vertical = 20.dp))
                Text(text = "댓글 삭제하기", modifier = Modifier
                    .clickable {
                        scope.launch {
                            dimmed = false
                            moreClickedCommentId?.let {
                                viewModel.deleteComment(id, it)
                                viewModel.fetchComments(id)
                                scaffoldState.bottomSheetState.collapse()
                                moreClickedCommentId = null
                            }
                        }
                    }
                    .padding(vertical = 20.dp))
            }
        },
        sheetPeekHeight = 0.dp ,
        scaffoldState = scaffoldState
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (dimmed) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .zIndex(10f)
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable {
                        scope.launch {
                            scaffoldState.bottomSheetState.collapse()
                            editingCommentId = null
                            moreClickedCommentId = null
                            dimmed = false
                        }
                    })
            }
            Column {
                CommunityPostToolbar(navController = navController)
                LazyColumn(
                    //state = listState,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    item{
                        Column(Modifier.padding(16.dp)){
                            Row(verticalAlignment = Alignment.CenterVertically){
                                val painter =
                                    rememberImagePainter(data = if (communityPostContent.profileImg != ""&&communityPostContent.profileImg!=null) communityPostContent.profileImg else "https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png")
                                Image(
                                    painter = painter,
                                    contentDescription =null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .border(
                                            1.dp,
                                            Color.Gray.copy(alpha = 0.2f),
                                            shape = CircleShape
                                        )
                                        .clip(shape = CircleShape),
                                    contentScale = ContentScale.Crop,
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(text = communityPostContent.authorName, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = communityPostContent.areaName+"·"+ formatProductTime(communityPostContent.community.createdAt,communityPostContent.community.createdAt), color = Color.Gray, fontSize = 13.sp)

                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = communityPostContent.community.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(text = communityPostContent.community.description, fontSize = 20.sp, lineHeight = 30.sp)
                        }
                    }
                    items(communityPostContent.community.images){
                        Image(
                            painter = rememberImagePainter(data=it),
                            contentDescription =null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp) ,// 이미지가 가로 방향으로 최대한 차지하도록 설정
                            contentScale = ContentScale.FillWidth
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                    }
                    item{
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = communityPostContent.community.viewCnt.toString()+"명이 봤어요",
                            fontSize = 15.sp, color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp))
                        Divider(
                            Modifier
                                .fillMaxWidth()
                                .height(5.dp),color= Color.Gray)
                    }

                    items(comments) {
                        val editing by remember(editingCommentId) {
                            mutableStateOf(editingCommentId == it.id)
                        }
                        CommentItem(userInfo = myInfo.value, it,
                            editingCommentId = editingCommentId,
                            onLike = {
                            scope.launch {
                                viewModel.likeComment(id, it.id)
                                viewModel.fetchComments(id)
                            }
                        }, onComment = { childComment ->
                            scope.launch {
                                viewModel.postComment(id, childComment, it.id, myInfo.value.profileImageUrl ?: "")
                                viewModel.fetchComments(id)
                                keyboardManager?.hide()
                            }
                        }, onEditComment = { commentId, comment ->
                            scope.launch {
                                viewModel.editComment(
                                    id, commentId, comment, myInfo.value.profileImageUrl ?: "")
                                viewModel.fetchComments(id)
                                editingCommentId = null
                                keyboardManager?.hide()
                            }
                        }, editing = editing, onLikeChild = {
                            scope.launch {
                                viewModel.likeComment(id, it)
                                viewModel.fetchComments(id)
                            }
                        }, onShowMore = { id ->
                            scope.launch {
                                dimmed = true
                                scaffoldState.bottomSheetState.expand()
                                moreClickedCommentId = id
                            }
                        })
                    }
                }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp),color= Color.Gray)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(imageVector = Icons.Default.Image, null)
                    Image(imageVector = Icons.Default.AddLocation, null)
                    Row(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
                        verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(4.dp))
                        BasicTextField(value = writingComment, onValueChange = {
                            writingComment = it
                        }, decorationBox = {
                            it()
                            if (writingComment.isEmpty()) {
                                Text(text = "댓글을 입력해주세요.")
                            }
                        }, modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusManager), keyboardActions = KeyboardActions(onSend = {
                            scope.launch {
                                keyboardManager?.hide()
                                focusManager.freeFocus()
                                viewModel.postComment(id, writingComment, null, myInfo.value.profileImageUrl ?: "")
                                writingComment = ""
                                viewModel.fetchComments(id)
                            }
                        }), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send))
                        Image(imageVector = Icons.Default.TagFaces, null)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityPostToolbar(navController: NavController) {


    Column{
        TopAppBar(
            title = { },
            navigationIcon = {
                Row {
                    BackButton(navController = navController)
                }
            },
            actions = {
                NotificationsButton(false)
                ShareButton()
                MoreVertButton()
            },


            )
        Divider(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
        )
    }

}

@Composable
fun CommentItem(
    userInfo: UserInfo,
    comment: Comment,
    editing: Boolean = false,
    editingCommentId: Long?,
    onLike: () -> Unit,
    onComment: (comment: String) -> Unit,
    onEditComment: (id: Long, comment: String) -> Unit,
    onLikeChild: (Long) -> Unit,
    onShowMore: (Long) -> Unit,
) {
    val painter = rememberImagePainter(data = checkProfileImgData(comment.imgUrl))
    Log.d("aaaa",comment.imgUrl)
    val scope = rememberCoroutineScope()
    var childCommentExpanded by remember {
        mutableStateOf(false)
    }
    var writingChildComment by remember {
        mutableStateOf("")
    }
    var editingCommentTextFieldValue by remember {
        mutableStateOf(TextFieldValue(
            comment.comment, selection = TextRange(comment.comment.length)
        ))
    }
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(editing) {
        if (editing) {
            focusRequester.requestFocus()
        }
    }

    Column{
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(6.dp))
        Row{
            Image(painter = painter, contentDescription = null,
                Modifier
                    .padding(horizontal = 12.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.Gray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.weight(1f)) {
                        Text(text = comment.nickname)
                        //Text(text = "·")
                        Row {

                        }
                    }
                    if (comment.nickname == userInfo.nickname) {
                        Image(imageVector = Icons.Default.MoreVert, contentDescription = null,
                            modifier = Modifier.clickable {
                                onShowMore(comment.id)
                            })
                    }
                }
                Text(text = convertEpochMillisToFormattedTime(comment.createdAt),color= Color.Gray, fontSize = 10.sp, textAlign = TextAlign.Center)
                if (editing) {
                    BasicTextField(value = editingCommentTextFieldValue, onValueChange = {
                        editingCommentTextFieldValue = it
                    },
                        decorationBox = {
                            it()
                            if (editingCommentTextFieldValue.text.isEmpty()) {
                                Text(text = "수정 댓글을 입력해주세요.")
                            }
                        }, modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(5.dp)
                            .focusRequester(focusRequester)
                        , keyboardActions = KeyboardActions(onSend = {
                            scope.launch {
                                onEditComment(comment.id, editingCommentTextFieldValue.text)
                            }
                        }), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send))
                } else {
                    Text(text = comment.comment)
                }
                Row (Modifier.padding(vertical = 6.dp)){
                    Row(modifier = Modifier.clickable { onLike() }) {
                        Image(imageVector = if (comment.isLiked.not()) Icons.Default.ThumbUpOffAlt else Icons.Default.ThumbUp, contentDescription = null)
                        Text(text = "좋아요")
                        if (comment.likeCnt != 0) Text(text = comment.likeCnt.toString())
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Row(modifier = Modifier.clickable { childCommentExpanded = childCommentExpanded.not() }) {
                        Image(imageVector = Icons.Default.Comment, null)
                        if (comment.childComments.isNotEmpty()) {
                            Text(text = "답글 ${comment.childComments.size}개")
                        } else {
                            Text(text = "답글쓰기")
                        }

                    }
                }
                AnimatedVisibility(visible = childCommentExpanded) {
                    val 대댓글FocusRequester = remember {
                        FocusRequester()
                    }
                    Column {
                        comment.childComments.forEach {
                            val editingChild by remember(editingCommentId) {
                                mutableStateOf(editingCommentId == it.id)
                            }
                            ChildCommentItem(
                                editing = editingChild,
                                userInfo = userInfo,
                                comment = it, onEditChildComment = { childId, edittedComment ->
                                    onEditComment(childId, edittedComment)
                                }, onLike = {
                                    onLikeChild(it)
                                    writingChildComment = ""
                                }, onShowMore = {
                                    onShowMore(it)
                                })
                        }
                        Row {
                            Image(imageVector = Icons.Default.Person, contentDescription = null)
                            BasicTextField(value = writingChildComment, onValueChange = {
                                writingChildComment = it
                            }, decorationBox = {
                                it()
                                if (writingChildComment.isEmpty()) {
                                    Text(text = "답글을 입력해주세요.")
                                }
                            }, modifier = Modifier
                                .weight(1f)
                                .focusRequester(대댓글FocusRequester)
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                ), keyboardActions = KeyboardActions(onSend = {
                                scope.launch {
                                    대댓글FocusRequester.freeFocus()
                                    onComment(writingChildComment)
                                    writingChildComment = ""
                                }
                            }), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send))
                        }

                    }

                }
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp))
    }
}

@Composable
fun ChildCommentItem(
    userInfo: UserInfo,
    comment: ChildComment,
    editing: Boolean = false,
    onLike: (childCommentId: Long) -> Unit,
    onEditChildComment: (Long, String) -> Unit,
    onShowMore: (Long) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val painter = rememberImagePainter(data = checkProfileImgData(comment.imgUrl))
    var editingCommentTextFieldValue by remember {
        mutableStateOf(TextFieldValue(
            comment.comment, selection = TextRange(comment.comment.length)
        ))
    }
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(editing) {
        if (editing) {
            focusRequester.requestFocus()
        }
    }
    Column{
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp))
        Row {
            Image(painter = painter, contentDescription = null,
                Modifier
                    .padding(horizontal = 6.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(2.dp, color = Color.Gray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.weight(1f)) {
                        Text(text = comment.nickname)
                        Row {
                            //Text(text = convertEpochMillisToFormattedTime(comment.createdAt))
                        }
                    }
                    if (comment.nickname == userInfo.nickname) {
                        Image(imageVector = Icons.Default.MoreVert, contentDescription = null,
                            modifier = Modifier.clickable {
                                onShowMore(comment.id)
                            })
                    }
                }
                Text(text = convertEpochMillisToFormattedTime(comment.createdAt),color= Color.Gray, fontSize = 10.sp, textAlign = TextAlign.Center)

                if (editing) {
                    BasicTextField(value = editingCommentTextFieldValue, onValueChange = {
                        editingCommentTextFieldValue = it
                    },
                        decorationBox = {
                            it()
                            if (editingCommentTextFieldValue.text.isEmpty()) {
                                Text(text = "수정 댓글을 입력해주세요.")
                            }
                        }, modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(5.dp)
                            .focusRequester(focusRequester)
                        , keyboardActions = KeyboardActions(onSend = {
                            scope.launch {
                                focusRequester.freeFocus()
                                onEditChildComment(comment.id, editingCommentTextFieldValue.text)
                            }
                        }), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send))
                } else {
                    Text(text = comment.comment)
                }
                Row(modifier = Modifier
                    .padding(vertical = 6.dp)
                    .clickable {
                        onLike(comment.id)

                    }) {
                    Image(imageVector = if (comment.isLiked.not()) Icons.Default.ThumbUpOffAlt else Icons.Default.ThumbUp, contentDescription = null)
                    Text(text = "좋아요")
                    if (comment.likeCnt != 0) Text(text = comment.likeCnt.toString())
                }
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(6.dp))
    }
}
fun checkProfileImgData(data:String?):String{
    Log.d("aaaa", "checkProfileImgData:$data")
    if(data!=null&&data!="") return data
    return "https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png"
}
