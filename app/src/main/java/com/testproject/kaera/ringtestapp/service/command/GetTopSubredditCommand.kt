package com.testproject.kaera.ringtestapp.service.command

import com.testproject.kaera.ringtestapp.enteties.APIRedditItem
import com.testproject.kaera.ringtestapp.service.api.GetTopSubredditAction
import com.testproject.kaera.ringtestapp.service.cache.StaticCache
import io.techery.janet.Command
import io.techery.janet.Janet
import io.techery.janet.command.annotations.CommandAction
import javax.inject.Inject

/**
 * Created by Dmitriy Puzak on 5/8/18.
 */
@CommandAction
class GetTopSubredditCommand constructor(var afterId: String? = null, var count: Int = 0, var fromCache: Boolean = false) : Command<List<APIRedditItem>>() {

    @Inject
    lateinit var janet: Janet
    @Inject
    lateinit var cache: StaticCache

    private var receivedData: List<APIRedditItem>? = null

    override fun run(callback: CommandCallback<List<APIRedditItem>>) {
        if (fromCache && receivedData != null) {
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