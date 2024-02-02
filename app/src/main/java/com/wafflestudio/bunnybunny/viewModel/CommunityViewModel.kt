package com.wafflestudio.bunnybunny.viewModel

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wafflestudio.bunnybunny.SampleData.DefaultCommunityPostContentSample
import com.wafflestudio.bunnybunny.data.example.CommunityPostPagingSource
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.lib.network.dto.CommunityPostPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.wafflestudio.bunnybunny.data.example.PrefRepository
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.lib.network.dto.Comment
import com.wafflestudio.bunnybunny.lib.network.dto.CommunityPostContent
import com.wafflestudio.bunnybunny.lib.network.dto.PostCommentParams
import com.wafflestudio.bunnybunny.lib.network.dto.PutCommentParams
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitCommunityPostRequest
import com.wafflestudio.bunnybunny.lib.network.dto.postImagesResponse
import com.wafflestudio.bunnybunny.model.ToggleImageItem
import com.wafflestudio.bunnybunny.pages.WritePage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val api: BunnyApi,
    private val prefRepository: PrefRepository,
): ViewModel() {

    var title by mutableStateOf("")
    //var isTitleFocused by remember { mutableStateOf(false) }
    //val titleScrollState = rememberScrollState()
    //var sellPrice by rememberSaveable { mutableStateOf("") }
    //var isSellPriceFocused by remember { mutableStateOf(false) }
    //var offerYn by rememberSaveable { mutableStateOf(false) }
    var description  by mutableStateOf("")

    private val _selectedWritePage = MutableStateFlow(WritePage.Home)
    val selectedWritePage = _selectedWritePage.asStateFlow()

    private val _comments = MutableStateFlow(emptyList<Comment>())
    val comments = _comments.asStateFlow()

    suspend fun fetchComments(communityId: Long) {
        _comments.emit(api.getCommentList(communityId))
    }

    suspend fun postComment(communityId: Long, comment: String, parentId: Long?, imageUrl: String) {
        api.postComment(communityId, PostCommentParams(
            comment = comment, parentId = parentId, imgUrl= imageUrl
        ))
    }

    suspend fun likeComment(communityId: Long, commentId: Long) {
        api.postCommentLike(communityId, commentId)
    }

    suspend fun editComment(communityId: Long, commentId: Long, editedComment: String, imageUrl: String) {
        api.putComment(communityId, commentId, PutCommentParams(editedComment, imageUrl))
    }

    suspend fun deleteComment(communityId: Long, commentId: Long) {
        api.deleteComment(communityId, commentId)
    }

    // 탭 변경 함수
    fun selectPage(page: WritePage) {
        _selectedWritePage.value = page
    }
    val querySignal = MutableStateFlow(
        CommunityPostPagingSource(
        api = api,
        token = getTokenHeader()!!,
        distance = 0,
        areaId = getRefAreaId()[0],
    )
    )
    @OptIn(ExperimentalCoroutinesApi::class)
    val communityPostList = querySignal.flatMapLatest {
        getCommunityPostList(it)
            .cachedIn(viewModelScope)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        PagingData.empty()
    )


    private val _communityPostContent = MutableStateFlow(DefaultCommunityPostContentSample)
    val communityPostContent: StateFlow<CommunityPostContent> = _communityPostContent.asStateFlow()

    fun updateCommunityPostContent(newContent: CommunityPostContent) {
        _communityPostContent.value = newContent
    }

    suspend fun updateCommunityPostList(
        cur:Long?,
        seed:Int?,
        distance:Int,
        areaId:Int,
        count:Int,) {
        querySignal.emit(
            CommunityPostPagingSource(
            api = api,
            token = getTokenHeader()!!,
            distance=distance,
            areaId=areaId,
        )
        )
    }



    companion object {}
    fun getTokenHeader():String?{
        //Log.d("aaaa", "tag:$accessToken")
        return prefRepository.getPref("token")?.let {
            "Bearer $it"
        } ?: ""
    }

    fun getOriginalToken():String?{
        //Log.d("aaaa", "tag:$accessToken")
        return prefRepository.getPref("token")
    }

    fun setToken(token: String) {
        prefRepository.setPref("token",token)
    }

    fun getRefAreaId(): List<Int> {
        return prefRepository.getPref("refAreaId")?.trimEnd()?.split(" ")?.map {
            it.toInt()
        } ?: emptyList()
    }

    fun setRefAreaId(refAreaId: List<Int>) {
        val builder = StringBuilder()
        refAreaId.forEach {
            builder.append("$it ")
        }
        Log.d("aaaaa",builder.toString())
        prefRepository.setPref("refAreaId",builder.toString())
    }

    fun getCommunityPostList(item:CommunityPostPagingSource): Flow<PagingData<CommunityPostPreview>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                CommunityPostPagingSource(
                    api = api,
                    token = getTokenHeader()!!,
                    distance = item.distance,
                    areaId = item.areaId,
                )
            }
        ).flow
    }

    fun getCommunityPostContent(id:Long){

        Log.d("aaaa","getCommunityPostContent called: authToken=${getTokenHeader()!!}, postId=$id")
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response=api.getCommunityPostContent(communityId =id)
                Log.d("aaaa",response.toString())

                withContext(Dispatchers.Main){
                    updateCommunityPostContent(response)
                    //isgettingNewPostContent=false
                    //isgettingNewPostList.value=false
                }
            }catch(e: Exception){
                //isgettingNewPostContent=false
                Log.d("aaaa",e.toString())
                ///////////////////////////////
                //updateGoodsPostContent(GoodsPostContentSample)
            }
        }
    }

    private val _galleryImages = MutableStateFlow(listOf<ToggleImageItem>())
    val galleryImages: StateFlow<List<ToggleImageItem>> = _galleryImages.asStateFlow()

    fun updateGalleryImages(newContent: List<ToggleImageItem>) {
        _galleryImages.value = newContent
        Log.d("aaaa",galleryImages.value.toString())
        Log.d("aaaa",selectedImages.value.toString())

    }

    private val _selectedImages = MutableStateFlow(listOf<Int>())
    val selectedImages: StateFlow<List<Int>> = _selectedImages.asStateFlow()
    fun updateSelectedImages(newContent: List<Int>) {
        if(newContent.size+uploadImages.value.size>10) return
        _selectedImages.value = newContent
        val updateList=galleryImages.value.toMutableList()
        updateList.forEachIndexed{imageIndex,item->
            updateList[imageIndex]=item.copy(
                isSelected = selectedImages.value.contains(imageIndex),
                selectedOrder =if(selectedImages.value.contains(imageIndex)) selectedImages.value.indexOf(imageIndex)+1 else null )
        }
        updateGalleryImages(updateList)
        //Log.d("aaaa","update gallery called")
    }
    private val _uploadImages = MutableStateFlow(listOf<Uri>())
    val uploadImages: StateFlow<List<Uri>> = _uploadImages.asStateFlow()
    fun updateUploadImages(newContent: List<Uri>) {
        _uploadImages.value=newContent
        //Log.d("aaaa","update gallery called")
    }

    suspend fun uploadImages(imageUris:List<Uri>,context: Context): postImagesResponse {
        Log.d("aaaa", imageUris.toString())
        return api.postImages(prepareMultiPartList(imageUris, context))
    }
    suspend fun submitCommunityPost(request: SubmitCommunityPostRequest){
        api.submitCommunityPostRequest(request)
    }

    fun CanCallFirstCommunityPostList():Boolean{
        return prefRepository.getPref("CanCallFirstCommunityPostList").toBoolean()
    }

    fun disableCallFirstCommunityPostList() {
        prefRepository.setPref("CanCallFirstCommunityPostList","false")
    }

    fun enableCallFirstCommunityPostList() {
        prefRepository.setPref("CanCallFirstCommunityPostList","true")
    }

    fun neededStoragePermissions():Array<String>{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_VIDEO,
                android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_VIDEO,)
        } else {
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
    fun prepareMultiPartList(imageUris: List<Uri>, context: Context): List<MultipartBody.Part> {
        val parts: MutableList<MultipartBody.Part> = ArrayList()
        imageUris.forEach { uri ->
            parts.add(prepareFilePart("multipartFiles", uri, context))
        }
        return parts
    }

    fun prepareFilePart(partName: String, fileUri: Uri, context: Context): MultipartBody.Part {
        // Uri로부터 파일을 얻음
        val file = File(context.cacheDir, "tempFile_${System.currentTimeMillis()}.png")
        val inputStream = context.contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)

        // 파일을 RequestBody로 생성
        val requestFile = file.asRequestBody(
            context.contentResolver.getType(fileUri)?.toMediaTypeOrNull()
        )

        // Part 이름과 파일 이름으로 MultipartBody.Part 생성
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

}