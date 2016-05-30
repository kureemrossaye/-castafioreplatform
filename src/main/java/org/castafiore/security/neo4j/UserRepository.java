package org.castafiore.security.neo4j;

import org.castafiore.security.model.User;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository()
public interface UserRepository extends GraphRepository<User> {

	public User findOneByUsername(@Param("username") String username);

	public User findOneByEmail(String email);

	public User findOneByMobile(String mobile);

}
