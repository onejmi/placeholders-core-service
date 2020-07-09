package io.github.scarger.placeholders.service.video;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import io.github.scarger.placeholders.CoreService;
import io.github.scarger.placeholders.model.collection.document.account.UserAccount;
import io.github.scarger.placeholders.service.data.Disposable;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TitleChangeScheduler implements Disposable {

    private final CoreService context;
    private final VideoTitleManager titleContext;

    private final Deque<TitleChangeTransaction> changeQueue;
    private volatile boolean running;

    public TitleChangeScheduler(VideoTitleManager titleContext, CoreService context) {
        this.context = context;
        this.titleContext = titleContext;
        changeQueue = new ConcurrentLinkedDeque<>();
        running = true;
    }

    public void beginQueuer() {
        new Thread(() -> {
            while(running) {
                MongoCollection<UserAccount> col = context.getUserManager().getUsers().getCol();
                FindIterable<UserAccount> accountIterator = col.find();
                if(accountIterator.first() != null) {
                    for(UserAccount account : accountIterator) {
                        for(Map.Entry<String, Object> titleEntry : account.getTitles().entrySet()) {
                            TitleChangeTransaction transaction =
                                    new TitleChangeTransaction(account.getId(),
                                    titleEntry.getKey(),
                                    (String) titleEntry.getValue());
                            changeQueue.addLast(transaction);
                            try {
                                Thread.sleep(1000 * 10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }

    public void beginWorker() {
        new Thread(() -> {
            while (running) {
                if(!changeQueue.isEmpty()) {
                    TitleChangeTransaction transaction = changeQueue.pollFirst();
                    String formattedTitle = titleContext
                            .getTitleTransformer()
                            .transformTitle(transaction.getUserId(), transaction.getUnformattedTitle());
                    titleContext.updateTitle(transaction.getUserId(), transaction.getVideoId(), formattedTitle);
                }
            }
        }).start();
    }

    @Override
    public void dispose() {
        running = false;
    }
}
