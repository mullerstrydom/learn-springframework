package learn.example.hibernateeventlisteners.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import learn.example.hibernateeventlisteners.configuration.listeners.InsertEventListener;
import learn.example.hibernateeventlisteners.configuration.listeners.UpdateEventListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class HibernateListenerConfiguration {
    private final InsertEventListener insertEventListener;
    private final UpdateEventListener updateEventListener;
    @PersistenceUnit
    private EntityManagerFactory emf;

    @PostConstruct
    private void init() {
        final SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        final EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListeners(insertEventListener);
        registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListeners(updateEventListener);
    }

}
