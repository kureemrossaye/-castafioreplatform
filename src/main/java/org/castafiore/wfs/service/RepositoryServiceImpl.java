/*
 * Copyright (C) 2007-2008 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */

package org.castafiore.wfs.service;

import org.castafiore.wfs.DuplicateFileException;
import org.castafiore.wfs.FileNotFoundException;
import org.castafiore.wfs.InsufficientPriviledgeException;
import org.castafiore.wfs.LockedFileException;
import org.castafiore.wfs.session.SessionImpl;
import org.castafiore.wfs.types.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 29, 2008
 */
@Service
public class RepositoryServiceImpl  implements RepositoryService {
	
	private ThreadLocal<String> tusername = new ThreadLocal<String>();
	

	
	private String superUser = "system";
	
	
	@Autowired
	private FileRepository fileRepository;
	
	
	
	public org.castafiore.wfs.session.Session getCastafioreSession(){
		return new SessionImpl(tusername.get(), this);
	}

	

	
	
	public String getSuperUser() {
		return superUser;
	}

	public void setSuperUser(String superUser) {
		this.superUser = superUser;
	}
	
	

	public boolean itemExists(String name)throws InsufficientPriviledgeException{
		
		Long count = fileRepository.countByName(name);
		if(count != null && count > 0){
			return true;
		}else{
			return false;
		}
	}

	public File getFile(String name, String username)throws InsufficientPriviledgeException, FileNotFoundException {
		tusername.set(username);
		File result = fileRepository.findFirstByName(name);
		if(result == null){
			throw new FileNotFoundException("the file " + name + " could not be found");
		}else{
			return result;
		}
	}
	
	public Page<File> getFiles(String username, Pageable params){
		return fileRepository.findAll(params);
	}
	

	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(File file, String username) throws  InsufficientPriviledgeException, LockedFileException {
		
		fileRepository.delete(file);
		tusername.set(username);
	}
 
//	@Transactional(propagation=Propagation.SUPPORTS)
//	public File getFileById(int id, String username)throws InsufficientPriviledgeException{
//		tusername.set(username);
//		return (File)getSession().get(File.class, id);
//	}
	

	@Transactional(propagation=Propagation.REQUIRED)
	public File save(File file, String username) throws DuplicateFileException, InsufficientPriviledgeException, LockedFileException {
		
		if(file.getName() == null || file.getName().length() == 0){
			throw new IllegalArgumentException("you cannot add a file without setting its name first");
		}
		
		if(file.getName() == null || file.getName().length() == 0){
			throw new IllegalArgumentException("the file " + file.getName() + " cannot be updated since it was not save previously. Please use the saveIn method to save it in a directory first");
		}
		
		if(file.getOwner() != null && file.getOwner().length() >0){}
		
		else{
			file.setOwner(username);
		}
		
		fileRepository.save(file);
		tusername.set(username);
		return file;
	}
	
	


	
	@Transactional(propagation=Propagation.REQUIRED)
	public void refresh(File file){
		file =fileRepository.findOne(file.getId());
	}



	
	
	

}
