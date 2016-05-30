package org.castafiore.wfs.service;

import org.castafiore.wfs.types.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface FileRepository extends GraphRepository<File>{
	
	

	public File findFirstByAbsolutePath(String absolutePath);
	
	public Long countByAbsolutePath(String absolutePath);
	
	
	public Page<File> findByParent_AbsolutePath(String absolutePath, Pageable page);
	
	
}
