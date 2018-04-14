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
    private boolean fromCache;

    public GetTopSubredditCommand(boolean fromCache) {
        this(null, 0);
        this.fromCache = fromCache;
    }

    public GetTopSubredditCommand(String afterId, int count) {
        RingApplication.getComponent().inject(this);
        this.count = count;
        this.after = afterId;
        this.receivedData = cache.readData();
    }

    @Override protected void run(CommandCallback callback) throws Throwable {
        if (fromCache && !receivedData.isEmpty()) {
            callback.onSuccess(receivedData);
            return;
        }

        janet.createPipe(GetTopSubredditAction.class)
                .createObservableResult(new GetTopSubredditAction(count, after))
                .map(action -> action.getResult())
                .flatMap(data -> cache.writeData(data))
                .doOnNext(data -> receivedData = data)
                .subscribe(callback::onSuccess, Throwable::printStackTrace);
    }
}
