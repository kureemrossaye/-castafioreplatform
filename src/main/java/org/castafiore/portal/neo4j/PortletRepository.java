package org.castafiore.portal.neo4j;

import org.castafiore.portal.model.Portlet;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortletRepository extends GraphRepository<Portlet>{

}
