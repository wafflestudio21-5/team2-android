package com.wafflestudio.bunnybunny

import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostContent
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostList
import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview


object SampleData {

    val GoodsPostListSample= GoodsPostList(data=listOf(
        GoodsPostPreview(
            id = 1,
            title="코멧5단정리함",
            repImg="https://dnvefa72aowie.cloudfront.net/origin/article/202401/5169264e8d830f83fc1547435761333f4ce68c47d2d4723fa2b185e1bd30fd3b_0.webp?q=82&s=300x300&t=crop&f=webp",
            createdAt = 0,
            refreshedAt = 0,
            wishCnt = 2,
            chatCnt = 1,
            sellPrice = 3000,
            tradingLocation = "경기도 평택시 고덕동",
            deadline = 0,
            type = "",
            status = "",
        ),
        GoodsPostPreview(
            id = 2,
            title="Z플립4 기계값 15만원",
            repImg = "https://dnvefa72aowie.cloudfront.net/origin/article/202401/11f6a028b850d3ae41fcb740aeea8c25fc8325d14197c7496487a97602ca37d4.jpg?q=82&s=300x300&t=crop&f=webp",
            createdAt = 0,
            refreshedAt = 0,
            wishCnt = 30,
            chatCnt = 7,
            sellPrice = 100000,
            tradingLocation = "경기도 파주시 금촌3동",
            deadline = System.currentTimeMillis(),
            type = "0",
            status = "0",
        ),
        GoodsPostPreview(
            id = 3,
            title="국산 생강청+배도라지청 선물",
            repImg = "https://dnvefa72aowie.cloudfront.net/origin/article/202401/c20b04b8b5f6e63ae3306f74d063788b04b24b431713d3cf75206ba894992b50.jpg?q=82&s=300x300&t=crop&f=webp",
            createdAt = System.currentTimeMillis(),
            refreshedAt = System.currentTimeMillis(),
            wishCnt = 0,
            chatCnt = 0,
            sellPrice = 8000,
            tradingLocation = "서울 노원구 상계1동",
            deadline = System.currentTimeMillis(),
            type = "0",
            status = "0",
        ),
        GoodsPostPreview(
            id = 4,
            title="아이패드 6세대 팝니다",
            repImg = "https://dnvefa72aowie.cloudfront.net/origin/article/202312/2cf483dcf705e393cffe5435a703638b95d39fedd3203e09dd3910b78dd44b93_0.webp?q=82&s=300x300&t=crop&f=webp",
            createdAt = System.currentTimeMillis(),
            refreshedAt = System.currentTimeMillis(),
            wishCnt = 0,
            chatCnt =3,
            sellPrice = 150000,
            tradingLocation = "광주 동구 동명동",
            deadline = System.currentTimeMillis(),
            type = "0",
            status = "0",
        ),
        GoodsPostPreview(
            id = 5,
            title="철제선반",
            repImg = "https://dnvefa72aowie.cloudfront.net/origin/article/202312/490baf1fa26bac6941cac02715f4a3a3febd5d7b8ca8f023111fda36fc8bc846_0.webp?q=82&s=300x300&t=crop&f=webp",
            createdAt = System.currentTimeMillis(),
            refreshedAt = System.currentTimeMillis(),
            wishCnt = 1,
            chatCnt =0,
            sellPrice = 10000,
            tradingLocation = "충남 아산시 배방읍",
            deadline = System.currentTimeMillis(),
            type = "0",
            status = "0",
        ),
        GoodsPostPreview(
            id = 6,
            title="냉장고 판매합니다",
            repImg = "https://dnvefa72aowie.cloudfront.net/origin/article/202401/10d17fc0559904a67d9565d720c64e7c600c95a3f0ef0092677fccee11712c46_0.webp?q=82&s=300x300&t=crop&f=webp",
            createdAt = System.currentTimeMillis(),
            refreshedAt = System.currentTimeMillis(),
            wishCnt = 1,
            chatCnt =2,
            sellPrice = 54000,
            tradingLocation = "서울 노원구 상계3.4동",
            deadline = System.currentTimeMillis(),
            type = "0",
            status = "0",
        ),
    ),cur=null,seed=null, isLast = true,count=6)
    val DefaultGoodsPostListSample= GoodsPostList(
        data =listOf(),cur = null,seed=null,isLast=false,count=null)


    val GoodsPostContentSample=GoodsPostContent(
        id = 0,
        title="코멧5단정리함",
        description = "라탄 무늬의 코멧 5단 서랍장입니다 흰색입니다\n" +
                "수납방식을 바꾸어서 팝니다\n" +
                "사용기간이 짧아 상태가 아주 좋습니다\n" +
                "\n" +
                "플라스틱이라 가볍고 사용하기 편리하며 가까운 거리는 들고 가실수 있으며\n" +
                "승용차에 실립니다 ^ ^\n" +
                "\n" +
                "사이즈\n" +
                "39×39 × 100.5cm입니다\n" +
                "\n" +
                "가볍기는 하지만 부피가 있어서 ᆢ\n" +
                "구로주공 109동 앞에서 거래합니다\n" +
                "\n" +
                "이제 괜찮니 너무 힘들었잖아\n" +
                "우리 그 마무리가 고작 이별뿐인 건데\n" +
                "우린 참 어려웠어\n" +
                "잘 지낸다고 전해 들었어 가끔\n" +
                "벌써 참 좋은 사람\n" +
                "만나 잘 지내고 있어\n" +
                "굳이 내게 전하더라\n" +
                "잘했어 넌 못 참았을 거야\n" +
                "그 허전함을 견뎌 내기엔\n" +
                "좋으니 사랑해서\n" +
                "사랑을 시작할 때\n" +
                "니가 얼마나 예쁜지 모르지\n" +
                "그 모습을 아직도 못 잊어\n" +
                "헤어 나오지 못해\n" +
                "니 소식 들린 날은 더\n" +
                "좋으니 그 사람\n" +
                "솔직히 견디기 버거워\n" +
                "네가 조금 더 힘들면 좋겠어\n" +
                "진짜 조금 내 십 분의 일만이라도\n" +
                "아프다 행복해줘\n" +
                "억울한가 봐 나만 힘든 것 같아\n" +
                "나만 무너진 건가\n" +
                "고작 사랑 한번 따위\n" +
                "나만 유난 떠는 건지\n" +
                "복잡해 분명 행복 바랬어\n" +
                "이렇게 빨리 보고 싶을 줄\n" +
                "좋으니 사랑해서\n" +
                "사랑을 시작할 때\n" +
                "니가 얼마나 예쁜지 모르지\n" +
                "그 모습을 아직도 못 잊어\n" +
                "헤어 나오지 못해\n" +
                "니 소식 들린 날은 더\n" +
                "좋으니 그 사람\n" +
                "솔직히 견디기 버거워\n" +
                "너도 조금 더 힘들면 좋겠어\n" +
                "진짜 조금 내 십 분의 일만이라도\n" +
                "아프다 행복해줘\n" +
                "혹시 잠시라도 내가 떠오르면\n" +
                "걘 잘 지내 물어 봐줘\n" +
                "잘 지내라고 답할 걸 모두 다\n" +
                "내가 잘 사는 줄 다 아니까\n" +
                "그 알량한 자존심 때문에\n" +
                "너무 잘 사는 척 후련한 척 살아가\n" +
                "좋아 정말 좋으니\n" +
                "딱 잊기 좋은 추억 정도니\n" +
                "난 딱 알맞게 사랑하지 못한\n" +
                "뒤끝 있는 너의 예전 남자친구일 뿐\n" +
                "스쳤던 그저 그런 사랑 오오, 우우",
        type = "TRADE",
        status = "NEW",
        authorId = 1,
        authorName = "이히히",
        buyerId = -1,
        sellingArea = "경기도 평택시 고덕동",
        profileImg ="https://mblogthumb-phinf.pstatic.net/MjAyMTAyMDRfMjcz/MDAxNjEyNDA5MDEyMjg0.lIRX6wm7X3nPYaviwnUFyLm5dC88Mggadj_nglswSHsg.r9so4CS-g8VZGAoaRWrwmPCIuDOsgsU64fQu0kKQRTwg.JPEG.sunny_side_up12/1612312679152%EF%BC%8D11.jpg?type=w800",
        images= listOf("https://dnvefa72aowie.cloudfront.net/origin/article/202401/5169264e8d830f83fc1547435761333f4ce68c47d2d4723fa2b185e1bd30fd3b_0.webp?q=82&s=300x300&t=crop&f=webp","https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcTbZwffvLNKJyX9SWN9Mal4FtZyblWwSYnN2SsZknKHWTWpYACnfWz4Hms88Yyl4TKKkTTAIpj4HtYY385q0doAG6Ts6E_3IVWEVQqXPvILDCIcaKHlZSG2VnFYLk87K_Stqlzg0XY&usqp=CAc"),
        viewCnt=50,
        offerYn=true,
        refreshCnt = 3,
        refreshedAt = System.currentTimeMillis(),
        createdAt = System.currentTimeMillis(),
        deadline = System.currentTimeMillis(),
        sellPrice = 3000,
        wishCnt = 2,
        chatCnt = 1,
        isWish = false,
    )

    val DefaultGoodsPostContentSample=GoodsPostContent(
        id = -1,
        title="",
        description = "",
        type = "TRADE",
        status = "NEW",
        authorId = -1,
        authorName = "",
        buyerId = -1,
        sellingArea = "",
        profileImg ="",
        images= listOf(),
        viewCnt=0,
        offerYn=true,
        refreshCnt = 0,
        refreshedAt = System.currentTimeMillis(),
        createdAt = System.currentTimeMillis(),
        deadline = System.currentTimeMillis(),
        sellPrice = 0,
        wishCnt = 0,
        chatCnt = 0,
        isWish=false,
    )
}