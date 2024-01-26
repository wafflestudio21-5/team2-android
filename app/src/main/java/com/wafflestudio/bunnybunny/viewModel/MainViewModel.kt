package com.wafflestudio.bunnybunny.viewModel

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.wafflestudio.bunnybunny.data.example.AreaSearchResponse
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.viewModelScope
import com.wafflestudio.bunnybunny.SampleData.DefaultGoodsPostContentSample
import com.wafflestudio.bunnybunny.SampleData.DefaultGoodsPostListSample
import com.wafflestudio.bunnybunny.SampleData.DefaultUserInfo
import com.wafflestudio.bunnybunny.SampleData.GoodsPostContentSample
import com.wafflestudio.bunnybunny.SampleData.GoodsPostListSample
import com.wafflestudio.bunnybunny.data.example.EditProfileRequest
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.data.example.RefAreaId
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.data.example.SignupResponse
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.data.example.SocialLoginRequest
import com.wafflestudio.bunnybunny.data.example.SocialSignupRequest
import com.wafflestudio.bunnybunny.data.example.UserInfo
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostContent
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostList
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview
import com.wafflestudio.bunnybunny.lib.network.dto.SocialLoginResponse
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitPostRequest
import com.wafflestudio.bunnybunny.lib.network.dto.postImagesResponse
import com.wafflestudio.bunnybunny.model.ToggleImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: BunnyApi
): ViewModel() {
    //홈 탭:Home,0
    //동네생활 탭:Community,1
    //채팅 탭:Chat,2
    //나의당근 탭:My,3
    var selectedTabIndex= mutableIntStateOf(0)
    var isgettingNewPostList= false
    var isgettingNewPostContent= false

    private val _accessToken = MutableStateFlow("");
    val accessToken : StateFlow<String> = _accessToken.asStateFlow()

    private val _refAreaId = MutableStateFlow(listOf<Int>());
    val refAreaId : StateFlow<List<Int>> = _refAreaId.asStateFlow()

    private val _goodsPostList = MutableStateFlow(DefaultGoodsPostListSample)
    val goodsPostList: StateFlow<GoodsPostList> = _goodsPostList.asStateFlow()

    private val _wishList = MutableStateFlow(DefaultGoodsPostListSample.data)
    val wishList: StateFlow<List<GoodsPostPreview>> = _wishList.asStateFlow()
    fun updateGoodsPostList(newContent: GoodsPostList) {
        _goodsPostList.value = newContent
    }

    private val _goodsPostContent = MutableStateFlow(DefaultGoodsPostContentSample)
    val goodsPostContent: StateFlow<GoodsPostContent> = _goodsPostContent.asStateFlow()

    // 상태를 업데이트하는 함수입니다.
    fun updateGoodsPostContent(newContent: GoodsPostContent) {
        _goodsPostContent.value = newContent
    }

    private val _galleryImages = MutableStateFlow(listOf<ToggleImageItem>())
    val galleryImages: StateFlow<List<ToggleImageItem>> = _galleryImages.asStateFlow()

    // 상태를 업데이트하는 함수입니다.
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





    // 상태를 업데이트하는 함수입니다.



    private val _areaDetails: MutableStateFlow<List<SimpleAreaData>> = MutableStateFlow(listOf())
    val areaDetails: StateFlow<List<SimpleAreaData>> = _areaDetails.asStateFlow()

    private val _userInfo: MutableStateFlow<UserInfo> = MutableStateFlow(DefaultUserInfo)
    val userInfo : StateFlow<UserInfo> = _userInfo.asStateFlow()

    suspend fun getUserInfo(){
        _userInfo.value = api.getUserInfo(getToken())
    }
    suspend fun editProfile(request: EditProfileRequest){
        _userInfo.value = api.putUserInfo(getToken(), request)
    }
    companion object {}
    fun getToken():String{
        //Log.d("aaaa", "tag:$accessToken")
        return "Bearer ${_accessToken.value}"
    }

    fun setToken(token: String) {
        _accessToken.value = token
    }

    fun getRefAreaId(): List<Int> {
        return _refAreaId.value
    }

    fun setRefAreaId(refAreaId: List<Int>) {
        _refAreaId.value = refAreaId
    }

    fun getGoodsPostList(distance:Int,areaId: Int){
        Log.d("aaaa","getGoodsPostList called:")
        if(goodsPostList.value.count!=0&&goodsPostList.value.isLast){
            Log.d("aaaa","getGoodsPostList canceled:$isgettingNewPostList,${goodsPostList.value.isLast}")
            return
        }
        Log.d("aaaa","getGoodsPostList called")
        /*
        updateGoodsPostList(GoodsPostListSample)
        isgettingNewPostList.value=false*/

        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response=api.getGoodsPostList(
                    authToken=getToken(),
                    cur = goodsPostList.value.cur,
                    seed = goodsPostList.value.seed,
                    distance = distance,
                    areaId = areaId,
                    count=goodsPostList.value.count)
                Log.d("aaaa",response.toString())

                withContext(Dispatchers.Main){
                    updateGoodsPostList(response.copy(data = goodsPostList.value.data+response.data))
                    isgettingNewPostList=false
                }
            }catch(e: Exception){
                isgettingNewPostList=false
                Log.d("aaaa", "getGoodsPostList failed: $e")
                /////////////////////////////////////////
                //updateGoodsPostList(GoodsPostListSample)
            }
        }
    }
    fun getGoodsPostContent(id:Long){

        Log.d("aaaa","getGoodsPostContent called: authToken=${getToken()}, postId=$id")
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response=api.getGoodsPostContent(authToken=getToken(),postId=id)
                Log.d("aaaa",response.toString())

                withContext(Dispatchers.Main){
                    updateGoodsPostContent(response)
                    isgettingNewPostContent=false
                    //isgettingNewPostList.value=false
                }
            }catch(e: Exception){
                isgettingNewPostContent=false
                Log.d("aaaa",e.toString())
                ///////////////////////////////
                updateGoodsPostContent(GoodsPostContentSample)
            }
        }
    }
    suspend fun uploadImages(imageUris:List<Uri>,context:Context):postImagesResponse{
        return api.postImages(getToken(),prepareMultiPartList(imageUris, context))
    }

    suspend fun wishToggle(id:Long,enable:Boolean) {
        Log.d("wish","enable:$enable")
        api.wishToggle(authToken=getToken(),id,enable)
    }

    suspend fun submitPost(request: SubmitPostRequest){
        api.submitPostRequest(authToken=getToken(),request)
    }

    suspend fun tryLogin(email: String, password: String): LoginResponse {
            return api.loginRequest(LoginRequest(email, password))
    }
    suspend fun trySignup(data: SignupRequest): SignupResponse {
        return api.signupRequest(data)
    }

    suspend fun trySocialLogin(data: SocialLoginRequest): SocialLoginResponse {
        return api.socialLoginRequest(data, "kakao")
    }

    suspend fun trySocialSignUp(data: SocialSignupRequest): SignupRequest {
        return api.socialSignUpRequest(data, "kakao")
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
            parts.add(prepareFilePart("image", uri, context))
        }
        return parts
    }

    fun prepareFilePart(partName: String, fileUri: Uri, context: Context): MultipartBody.Part {
        // Uri로부터 파일을 얻음
        val file = File(context.cacheDir, "tempFile_${System.currentTimeMillis()}")
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
    suspend fun tryAreaSearch(query: String, cursor: Int): List<SimpleAreaData> {
        _areaDetails.value = api.areaSearch(query, cursor).areas;
        Log.d("VM", "${_areaDetails.value}")
        return _areaDetails.value;
    }
    suspend fun getWishList(){
        _wishList.value = api.getWishList(getToken())
    }

}