package com.testproject.kaera.ringtestapp.service.command;

import com.testproject.kaera.ringtestapp.RingApplication;
import com.testproject.kaera.ringtestapp.enteties.APIRedditItem;
import com.testproject.kaera.ringtestapp.service.api.GetTopSubredditAction;
import com.testproject.kaera.ringtestapp.service.cache.StaticCache;

import java.util.List;

import javax.inject.Inject;

import io.techery.janet.Command;
import io.techery.janet.Janet;
import io.techery.janet.command.annotations.CommandAction;

@CommandAction
public class GetTopSubredditCommand extends Command<List<APIRedditItem>> {

    @Inject Janet janet;
    @Inject StaticCache cache;

    private int count;
    private String after;
    private List<APIRedditItem> receivedData;

    public GetTopSubredditCommand() {
        this(null, 0);
    }

    public GetTopSubredditCommand(String afterId, int count) {
        RingApplication.getComponent().inject(this);
        this.count = count;
        this.after = afterId;
        this.receivedData = cache.readData();
    }

    @Override protected void run(CommandCallback callback) throws Throwable {
        if (!receivedData.isEmpty()) {
            callback.onProgress(50);
        }

        janet.createPipe(GetTopSubredditAction.class)
                .createObservableResult(new GetTopSubredditAction(count, after))
                .map(action -> action.getResult())
                .doOnNext(data -> receivedData.addAll(data))
                .doOnNext(data -> cache.writeData(receivedData))
                .subscribe(callback::onSuccess, Throwable::printStackTrace);
    }

    public List<APIRedditItem> getCachedData() {
        return receivedData;
    }
}
