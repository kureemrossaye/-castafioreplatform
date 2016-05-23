package org.castafiore.security.neo4j;

import org.castafiore.security.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public User findOneByUsername(String username);

	public User findOneByEmail(String email);

	public User findOneByMobile(String mobile);

}
