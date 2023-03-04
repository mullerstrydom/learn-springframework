package learn.example.hibernateeventlisteners.configuration.listeners;

import org.hibernate.event.spi.PostCommitUpdateEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
public class UpdateEventListener implements PostCommitUpdateEventListener {

    @Override
    public void onPostUpdateCommitFailed(PostUpdateEvent postUpdateEvent) {
        System.out.println("::::::" + Thread.currentThread().getId());
        System.out.println("UpdateEventListener.onPostUpdateCommitFailed");
        System.out.println(postUpdateEvent.getEntity());
        System.out.println("::::::");
    }

    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        System.out.println("::::::" + Thread.currentThread().getId());
        System.out.println("UpdateEventListener.onPostUpdate");
        System.out.println(postUpdateEvent.getEntity());
        System.out.println("::::::");

    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
        return true;
    }
}
