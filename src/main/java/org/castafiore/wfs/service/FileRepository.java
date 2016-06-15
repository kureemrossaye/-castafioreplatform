package org.castafiore.wfs.service;

import org.castafiore.wfs.types.File;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface FileRepository extends GraphRepository<File>{
	
	

	public File findFirstByName(String Name);
	
	public Long countByName(String Name);
	
	
	
	
}
