package org.castafiore.security.neo4j;

import org.castafiore.security.model.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer>{
	
	public Group findOneByName(String name);

}
