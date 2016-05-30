package org.castafiore.portal.neo4j;

import org.castafiore.portal.model.Configuration;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends GraphRepository<Configuration>{

}
