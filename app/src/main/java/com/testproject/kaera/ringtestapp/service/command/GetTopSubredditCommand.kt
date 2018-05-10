package com.testproject.kaera.ringtestapp.service.command

import com.testproject.kaera.ringtestapp.enteties.APIRedditItem
import com.testproject.kaera.ringtestapp.service.api.GetTopSubredditAction
import com.testproject.kaera.ringtestapp.service.cache.StaticCache
import io.techery.janet.Command
import io.techery.janet.Janet
import io.techery.janet.command.annotations.CommandAction

/**
 * Created by Dmitriy Puzak on 5/8/18.
 */
@CommandAction
class GetTopSubredditCommand constructor(var afterId: String? = null, var count: Int = 0, var fromCache: Boolean = false) : Command<List<APIRedditItem>>() {

    lateinit var janet: Janet
    lateinit var cache: StaticCache
    lateinit var receivedData: List<APIRedditItem>

    override fun run(callback: CommandCallback<List<APIRedditItem>>) {
        if (fromCache && !receivedData.isEmpty()) {
            callback.onSuccess(receivedData)
            return
        }

        janet.createPipe(GetTopSubredditAction::class.java)
                .createObservableResult(GetTopSubredditAction(count, afterId))
                .map { it.result }
                .flatMap { cache.writeData(it) }
                .doOnNext { receivedData = it }
                .subscribe(callback::onSuccess, Throwable::printStackTrace)
    }

}