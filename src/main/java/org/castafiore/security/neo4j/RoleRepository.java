package org.castafiore.security.neo4j;

import org.castafiore.security.model.Role;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface RoleRepository extends GraphRepository<Role>{
	
	
	public Role findOneByName(String name);

}
