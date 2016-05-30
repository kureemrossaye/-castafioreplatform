package org.castafiore.security.neo4j;

import org.castafiore.security.model.Group;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface GroupRepository extends GraphRepository<Group>{
	
	public Group findOneByName(String name);

}
