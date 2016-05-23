package org.castafiore.security.neo4j;

import org.castafiore.security.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer>{
	
	
	public Role findOneByName(String name);

}
