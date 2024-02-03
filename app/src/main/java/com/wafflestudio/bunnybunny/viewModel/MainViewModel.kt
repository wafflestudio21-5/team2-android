package com.wafflestudio.bunnybunny.viewModel

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wafflestudio.bunnybunny.SampleData
import com.wafflestudio.bunnybunny.SampleData.DefaultGoodsPostContentSample
import com.wafflestudio.bunnybunny.SampleData.DefaultGoodsPostListSample
import com.wafflestudio.bunnybunny.SampleData.DefaultUserInfo
import com.wafflestudio.bunnybunny.data.example.GoodsPostPagingSource
import com.wafflestudio.bunnybunny.data.example.EditProfileRequest
import com.wafflestudio.bunnybunny.data.example.LoginRequest
import com.wafflestudio.bunnybunny.data.example.LoginResponse
import com.wafflestudio.bunnybunny.data.example.PrefRepository
import com.wafflestudio.bunnybunny.data.example. SearchPostPagingSource
import com.wafflestudio.bunnybunny.data.example.SignupRequest
import com.wafflestudio.bunnybunny.data.example.SignupResponse
import com.wafflestudio.bunnybunny.data.example.SimpleAreaData
import com.wafflestudio.bunnybunny.data.example.SocialLoginRequest
import com.wafflestudio.bunnybunny.data.example.SocialSignupRequest
import com.wafflestudio.bunnybunny.data.example.SocialSignupResponse
import com.wafflestudio.bunnybunny.data.example.UserInfo
import com.wafflestudio.bunnybunny.lib.network.api.BunnyApi
import com.wafflestudio.bunnybunny.lib.network.dto.AuctionRequest
import com.wafflestudio.bunnybunny.lib.network.dto.CommunityPostList
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostContent
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview
import com.wafflestudio.bunnybunny.lib.network.dto.SocialLoginResponse
import com.wafflestudio.bunnybunny.lib.network.dto.SubmitPostRequest
import com.wafflestudio.bunnybunny.lib.network.dto.postImagesResponse
import com.wafflestudio.bunnybunny.model.ToggleImageItem
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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: BunnyApi,
    private val prefRepository: PrefRepository,
): ViewModel() {


    val querySignal = MutableStateFlow(GoodsPostPagingSource(
        api = api,
        token = "",
        distance = 0,
        areaId = 0,
    ))

    @OptIn(ExperimentalCoroutinesApi::class)
    val goodsPostList = querySignal.flatMapLatest {
        getGoodsPostList(it)
            .cachedIn(viewModelScope)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        PagingData.empty<GoodsPostPreview>().also { Log.d("cccc", "이게 여러번 불리면 안돼!!") }
    )

    suspend fun updateGoodsPostList(
        cur:Long?,
        seed:Int?,
        distance:Int,
        areaId:Int,
        count:Int,) {
        querySignal.emit(
            GoodsPostPagingSource(
                api = api,
                token = getTokenHeader()!!,
                distance = distance,
                areaId = areaId,
            )
        )
    }

    private val _wishList = MutableStateFlow(DefaultGoodsPostListSample.data)
    val wishList: StateFlow<List<GoodsPostPreview>> = _wishList.asStateFlow()

    private val _myItemList = MutableStateFlow(DefaultGoodsPostListSample.data)
    val myPostList: StateFlow<List<GoodsPostPreview>> = _myItemList.asStateFlow()


    private val _goodsPostContent = MutableStateFlow(DefaultGoodsPostContentSample)
    val goodsPostContent: StateFlow<GoodsPostContent> = _goodsPostContent.asStateFlow()

    private val _currentRefAreaId: MutableStateFlow<MutableList<Int>> = MutableStateFlow(getRefAreaId().toMutableList())
    val currentRefAreaId : StateFlow<MutableList<Int>> = _currentRefAreaId.asStateFlow()

    private val _currentDistance: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentDistance : StateFlow<Int> = _currentDistance.asStateFlow()

    fun updateGoodsPostContent(newContent: GoodsPostContent) {
        _goodsPostContent.value = newContent
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

    private val _profileImage = MutableStateFlow("d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png")
    val profileImage : StateFlow<String> = _profileImage.asStateFlow()

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex = _selectedIndex.asStateFlow()

    fun updateIndex(index: Int){
        _selectedIndex.value = index
        updateGalleryImages(
            galleryImages.value.toMutableList().mapIndexed { idx, it ->
                it.copy(isSelected =( idx == index) && it.isSelected.not())
            }
        )
    }
    fun updateProfileImage(url: String){
        _profileImage.value = url
    }

    private val _communityPostList = MutableStateFlow(SampleData.DefaultCommunityPostListSample)
    val communityPostList: StateFlow<CommunityPostList> = _communityPostList.asStateFlow()
    fun updateCommunityPostList(newContent: CommunityPostList) {
        _communityPostList.value = newContent
    }


    val searchQuerySignal = MutableStateFlow(
        SearchPostPagingSource(
            api = api,
            token = "",
            distance = 0,
            areaId = 0,
            keyword = ""
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val SearchPostList = searchQuerySignal.flatMapLatest {
        searchPostList(it)
            .cachedIn(viewModelScope)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        PagingData.empty()
    )

    suspend fun updateSearchPostList(
        distance:Int,
        areaId:Int,
        keyword:String) {
        searchQuerySignal.emit(
            SearchPostPagingSource(
                api = api,
                token = getTokenHeader()!!,
                distance = distance,
                areaId = areaId,
                keyword=keyword
            )
        )
    }

    fun searchPostList(item: SearchPostPagingSource): Flow<PagingData<GoodsPostPreview>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                SearchPostPagingSource(
                    api = api,
                    token = getTokenHeader()!!,
                    distance=item.distance,
                    areaId=item.areaId,
                    keyword = item.keyword
                )
            }
        ).flow

    }

    // 상태를 업데이트하는 함수입니다.



    private val _areaDetails: MutableStateFlow<List<SimpleAreaData>> = MutableStateFlow(listOf())
    val areaDetails: StateFlow<List<SimpleAreaData>> = _areaDetails.asStateFlow()

    private val _userInfo: MutableStateFlow<UserInfo> = MutableStateFlow(DefaultUserInfo)
    val userInfo : StateFlow<UserInfo> = _userInfo.asStateFlow()

    suspend fun getUserInfo(){
        _userInfo.value = api.getUserInfo()
    }
    suspend fun editProfile(request: EditProfileRequest){
        _userInfo.value = api.putUserInfo(request)
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

    fun clearToken(){
        prefRepository.clearPref("token")
        prefRepository.clearPref("refAreaId")
        prefRepository.clearPref("distance")
    }
    fun getRefAreaId(): List<Int> {
        return prefRepository.getPref("refAreaId")?.trimEnd()?.split(" ")?.map {
            it.toInt()
        } ?: emptyList()
    }

    fun setRefAreaId(refAreaId: List<Int>) {
        _currentRefAreaId.value = refAreaId.toMutableList()
        val builder = StringBuilder()
        refAreaId.forEach {
            builder.append("$it ")
        }
        Log.d("aaaaa",builder.toString())
        prefRepository.setPref("refAreaId",builder.toString())
    }
    /*
    fun getSelectedTabIndex():Int{
        return prefRepository.getPref("SelectedTabIndex")!!.toInt()
    }
    fun setSelectedTabIndex(index:Int){
        prefRepository.setPref("SelectedTabIndex",index.toString())
    }*/

    fun getGoodsPostList(item:GoodsPostPagingSource): Flow<PagingData<GoodsPostPreview>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                GoodsPostPagingSource(
                    api = api,
                    token = getTokenHeader()!!,
                    distance=item.distance,
                    areaId=item.areaId,
                )
            }
        ).flow

    }
    fun getGoodsPostContent(id:Long){

        Log.d("aaaa","getGoodsPostContent called: authToken=${getTokenHeader()!!}, postId=$id")
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response=api.getGoodsPostContent(postId=id)
                Log.d("aaaa",response.toString())

                withContext(Dispatchers.Main){
                    updateGoodsPostContent(response)
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


    suspend fun uploadImages(imageUris:List<Uri>,context:Context):postImagesResponse{
        Log.d("aaaa", "uploadImages called")
        val imageUrl = api.postImages(prepareMultiPartList(imageUris, context))
        _profileImage.value = imageUrl.images[0]
        return imageUrl
    }


    suspend fun wishToggle(id:Long,enable:Boolean) {
        Log.d("wish","enable:$enable")
        api.wishToggle(id,enable)
    }

    suspend fun submitPost(request: SubmitPostRequest){
        api.submitPostRequest(request)
    }

    suspend fun tryLogin(email: String, password: String): LoginResponse {
            return api.loginRequest(LoginRequest(email, password))
    }
    suspend fun trySignup(data: SignupRequest): SignupResponse {
        return api.signupRequest(data)
    }

    suspend fun tryCheckDuplicateNickname(nickName: String) {
        return api.checkDuplicateNickname(nickName)
    }

    suspend fun trySocialLogin(data: SocialLoginRequest): SocialLoginResponse {
        return api.socialLoginRequest(data, "kakao")
    }

    suspend fun trySocialSignUp(data: SocialSignupRequest): SocialSignupResponse {
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
    suspend fun tryAreaSearch(query: String, cursor: Int): List<SimpleAreaData> {
        _areaDetails.value = api.areaSearch(query, cursor).areas;
        Log.d("VM", "${_areaDetails.value}")
        return _areaDetails.value;
    }

    fun initializeApp() {
        enableCallFirstGoodsPostList()
    }
    fun logOutApp() {
        clearToken()
//        setRefAreaId(listOf())
        _currentRefAreaId.value = emptyList<Int>().toMutableList()
        _currentDistance.value = 0
    }
    suspend fun getWishList(){
        _wishList.value = api.getWishList()
    }

    suspend fun getMyPostList(){
        _myItemList.value = api.getMyPostList()
    }


    fun CanCallFirstGoodsPostList():Boolean{
        return prefRepository.getPref("CanCallGoodsPostList").toBoolean()
    }

    fun disableCallFirstGoodsPostList() {
        prefRepository.setPref("CanCallGoodsPostList","false")
    }

    fun enableCallFirstGoodsPostList() {
        prefRepository.setPref("CanCallGoodsPostList","true")
    }

    val firstName = MutableStateFlow("")
    val secondName = MutableStateFlow("추가하기")

    suspend fun fetchAreaName(id: Int) {
        if (id == _currentRefAreaId.value[0]) {
            firstName.value = api.getAreaName(id).name
        } else if (id == currentRefAreaId.value[1]) {
            secondName.value = api.getAreaName(id).name
        }
    }

    suspend fun addRefAreaId(id: Int) {
        val tokenHeader = getTokenHeader()
        val response = api.postRefArea(id)
        setToken(response.token)
        _currentRefAreaId.value.add(id)
        setRefAreaId(response.refAreaIds.toMutableList())

        for (id in _currentRefAreaId.value) {
            fetchAreaName(id)
        }
    }

    suspend fun deleteRefAreaId(id: Int) {
        val tokenHeader = getTokenHeader()
        val response = api.deleteRefArea(id)
        setToken(response.token)
        _currentRefAreaId.value.remove(id)
        setRefAreaId(response.refAreaIds.toMutableList())

        for (id in _currentRefAreaId.value) {
            fetchAreaName(id)
        }
    }

    fun getDistance(): Int {
        if (prefRepository.getPref("distance") == null) {
            return 0;
        }
        if (_currentDistance.value != prefRepository.getPref("distance")!!.toInt()) {
            _currentDistance.value = prefRepository.getPref("distance")!!.toInt()
        }
        return _currentDistance.value
    }
    fun setDistance(step: Int) {
        prefRepository.setPref("distance","$step")
        _currentDistance.value = step
    }

    fun swapRefAreaIdValues() {

        // 0번째와 1번째 값 바꾸기
        if (_currentRefAreaId.value.size == 2) {
            val temp = _currentRefAreaId.value[0]
            _currentRefAreaId.value = _currentRefAreaId.value.toMutableList().apply {
                this[0] = _currentRefAreaId.value[1]
                this[1] = temp
            }
        }

        setRefAreaId(_currentRefAreaId.value)
    }
    suspend fun postAuction(id: Long, maxPrice: Int){
        api.postAuction(id, AuctionRequest(maxPrice))
    }
    /*suspend fun getBidList(id: Long){
        api.getBidList(getTokenHeader()!!, id)
    }
    suspend fun getMyBidList(){
        api.getMyBidList(getTokenHeader()!!)
    }*/
}