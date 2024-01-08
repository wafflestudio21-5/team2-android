package com.wafflestudio.bunnybunny

import com.wafflestudio.bunnybunny.lib.network.dto.GoodsPostPreview
import java.sql.Timestamp


object SampleData {

    val GoodsPostSample= listOf(
        GoodsPostPreview(
            "코멧5단정리함",
            "TRADE",
            "NEW",
            "https://dnvefa72aowie.cloudfront.net/origin/article/202401/5169264e8d830f83fc1547435761333f4ce68c47d2d4723fa2b185e1bd30fd3b_0.webp?q=82&s=300x300&t=crop&f=webp",
            3000,
            refreshedAt = Timestamp.valueOf("2024-01-01 12:00:00"),

            createdAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            2,
            1,
            "경기도 평택시 고덕동"
        ),
        GoodsPostPreview(
            "Z플립4 기계값 15만원",
            "TRADE",
            "NEW",
            "https://dnvefa72aowie.cloudfront.net/origin/article/202401/11f6a028b850d3ae41fcb740aeea8c25fc8325d14197c7496487a97602ca37d4.jpg?q=82&s=300x300&t=crop&f=webp",
            100000,
            refreshedAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            createdAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            30,
            7,
            "경기도 파주시 금촌3동"
        ),
        GoodsPostPreview(
            "국산 생강청+배도라지청 선물",
            "TRADE",
            "NEW",
            "https://dnvefa72aowie.cloudfront.net/origin/article/202401/c20b04b8b5f6e63ae3306f74d063788b04b24b431713d3cf75206ba894992b50.jpg?q=82&s=300x300&t=crop&f=webp",
            8000,
            refreshedAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            createdAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            0,
            0,
            "서울 노원구 상계1동"
        ),
        GoodsPostPreview(
            "아이패드 6세대 팝니다",
            "TRADE",
            "NEW",
            "https://dnvefa72aowie.cloudfront.net/origin/article/202312/2cf483dcf705e393cffe5435a703638b95d39fedd3203e09dd3910b78dd44b93_0.webp?q=82&s=300x300&t=crop&f=webp",
            150000,
            refreshedAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            createdAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            0,
            3,
            "광주 동구 동명동",
        ),
        GoodsPostPreview(
            "철제선반",
            "TRADE",
            "NEW",
            "https://dnvefa72aowie.cloudfront.net/origin/article/202312/490baf1fa26bac6941cac02715f4a3a3febd5d7b8ca8f023111fda36fc8bc846_0.webp?q=82&s=300x300&t=crop&f=webp",
            10000,
            refreshedAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            createdAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            1,
            0,
            "충남 아산시 배방읍",
        ),
        GoodsPostPreview(
            "냉장고 판매합니다",
            "TRADE",
            "NEW",
            "https://dnvefa72aowie.cloudfront.net/origin/article/202401/10d17fc0559904a67d9565d720c64e7c600c95a3f0ef0092677fccee11712c46_0.webp?q=82&s=300x300&t=crop&f=webp",
            54000,
            refreshedAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            createdAt = Timestamp.valueOf("2024-01-01 12:00:00"),
            1,
            2,
            "서울 노원구 상계3.4동",
        ),

    )
}