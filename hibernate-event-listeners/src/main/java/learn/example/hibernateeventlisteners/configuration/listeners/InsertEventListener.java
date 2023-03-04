package learn.example.hibernateeventlisteners.configuration.listeners;

import org.hibernate.event.spi.PostCommitInsertEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
public class InsertEventListener implements PostCommitInsertEventListener {

    @Override
    public void onPostInsertCommitFailed(PostInsertEvent postInsertEvent) {
        System.out.println("::::::" + Thread.currentThread().getId());
        System.out.println("InsertEventListener.onPostInsertCommitFailed:");
        System.out.println(postInsertEvent.getEntity());
        System.out.println("::::::");
    }

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        System.out.println("::::::" + Thread.currentThread().getId());
        System.out.println("InsertEventListener.onPostInsert");
        System.out.println(postInsertEvent.getEntity());
        System.out.println("::::::");
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}
