package org.castafiore.security.neo4j;

import java.util.List;

import org.castafiore.security.model.UserSecurity;
import org.springframework.data.repository.CrudRepository;

public interface UserSecurityRepository extends CrudRepository<UserSecurity, Integer> {
	
	public List<UserSecurity> findByUser_Username(String username);
	
	
	public Long countByUser_UsernameAndRole_NameAndGrp_Name(String username, String role, String group);
	
	public List<UserSecurity> findByUser_UsernameAndRole_NameAndGrp_Name(String username, String role, String group);
	
	
	public List<UserSecurity> findByUser_UsernameAndGrp_Name(String username, String grp);
	
	
	public List<UserSecurity> findByUser_UsernameAndRole_Name(String username, String role);

}
