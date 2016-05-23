package org.castafiore.wfs.service;

import org.castafiore.wfs.types.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long>{
	
	

	public File findFirstByAbsolutePath(String absolutePath);
	
	public Long countByAbsolutePath(String absolutePath);
	
	
	public Page<File> findByParent_AbsolutePath(String absolutePath, Pageable page);
	
	
}
