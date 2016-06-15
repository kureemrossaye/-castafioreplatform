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
import org.castafiore.wfs.types.File;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 29, 2008
 */

public interface RepositoryService {
	
	public org.castafiore.wfs.session.Session getCastafioreSession();
	
	/**
	 * returns the user that has full access to all reasource
	 * @return
	 */
	public String getSuperUser();
	
	public Page<File> getFiles(String username, Pageable params);
	
	
	/**
	 * returns a file with the specified path
	 * @param path
	 * @return
	 * @throws FileNotFoundException 
	 * @throws InsufficientPriviledgeException
	 */
	public File getFile(String path, String username)throws  InsufficientPriviledgeException, FileNotFoundException;
	
	
	/**
	 * deletes a file
	 * @param file
	 * @throws Exception
	 */
	public void delete(File file, String username)throws  InsufficientPriviledgeException, LockedFileException;
	
	
	
	
	/**
	 * simply saves a file
	 * @param file
	 * @throws Exception
	 */
	public File save(File file, String username)throws  InsufficientPriviledgeException, DuplicateFileException, LockedFileException;
	
	

	
	/**
	 * refreshes the instance of file
	 * @param file
	 */
	public void refresh(File file);
	
	
	
	/**
	 * checks if the specified file exists
	 * @param path
	 * @param username
	 * @return
	 * @throws DataAccessException
	 */
	public boolean itemExists(String path);
	
	
	

}
