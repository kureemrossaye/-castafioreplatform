package org.castafiore.security.neo4j;

import java.util.List;

import org.castafiore.security.model.UserSecurity;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserSecurityRepository extends GraphRepository<UserSecurity> {
	
	public List<UserSecurity> findByUser_Username(String username);
	
	
	public List<UserSecurity> countByUser_UsernameAndRole_NameAndGrp_Name(String username, String role, String group);
	
	public List<UserSecurity> findByUser_UsernameAndRole_NameAndGrp_Name(String username, String role, String group);
	
	
	public List<UserSecurity> findByUser_UsernameAndGrp_Name(String username, String grp);
	
	
	public List<UserSecurity> findByUser_UsernameAndRole_Name(String username, String role);

}
