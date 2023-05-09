package com.example.testapp.home.testdoubles

import com.example.testapp.data.entity.posts.PostsItem
import com.example.testapp.domain.posts.PostsRepository


enum class ScenarioType {
    DEFAULT,
    EMPTY,
    DUPLICATES,
    UNSORTED
}

fun getFakePostsRepo(types: ScenarioType = ScenarioType.DEFAULT): PostsRepository {
    return when (types) {
        ScenarioType.DUPLICATES -> DuplicateFakeProductsRepository()
        ScenarioType.EMPTY -> EmptyFakeProductsRepository()
        else -> FakeProductsRepository()
    }
}

fun getPosts(types: ScenarioType = ScenarioType.DEFAULT): List<PostsItem> {
    return when (types) {
        ScenarioType.EMPTY -> emptyList()
        ScenarioType.DUPLICATES -> listOf(
            postsItem1,
            postsItem2,
            postsItem3,
            postsItem4,
            postsItem1,
            postsItem4
        )

        ScenarioType.UNSORTED -> listOf(
            postsItem3,
            postsItem2,
            postsItem1
        )

        else -> listOf(
            postsItem1,
            postsItem2,
            postsItem3
        )
    }
}

class EmptyFakeProductsRepository : PostsRepository {
    override suspend fun getAllPosts() = getPosts(ScenarioType.EMPTY)
}

class FakeProductsRepository : PostsRepository {
    override suspend fun getAllPosts() = getPosts(ScenarioType.DEFAULT)
}

class DuplicateFakeProductsRepository : PostsRepository {
    override suspend fun getAllPosts() = getPosts(ScenarioType.DUPLICATES)
}

val postsItem1 = PostsItem(
    id = 1,
    body = "body1",
    title = "title1",
    userId = 5
)

val postsItem2 = PostsItem(
    id = 2,
    body = "body2",
    title = "title2",
    userId = 2
)

val postsItem3 = PostsItem(
    id = 3,
    body = "body3",
    title = "title3",
    userId = 3
)

val postsItem4 = PostsItem(
    id = 4,
    body = "body4",
    title = "title4",
    userId = 4

    )
