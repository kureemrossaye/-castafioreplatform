package org.castafiore.portal.neo4j;

import org.castafiore.portal.model.Notifications;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends GraphRepository<Notifications>{

}
