package com.fellow.profile.database;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSelectInfo;
import org.apache.commons.vfs.FileSelector;
import org.apache.commons.vfs.FileType;

import com.fellow.component.vfs.VFSComponent;
import com.fellow.profile.DatabaseBusiness;

public class DatabaseBusinessImpl implements DatabaseBusiness{
	
	private DataSource datasource;
	private DatabaseDAO databaseDAO;
	private VFSComponent vfsComponent;

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public DatabaseDAO getDatabaseDAO() {
		return databaseDAO;
	}

	public void setDatabaseDAO(DatabaseDAO databaseDAO) {
		this.databaseDAO = databaseDAO;
	}
	
	public VFSComponent getVfsComponent() {
		return vfsComponent;
	}


	public void setVfsComponent(VFSComponent vfsComponent) {
		this.vfsComponent = vfsComponent;
	}
	
	
	

	@Override
	public int getProfileVersion() {
		if(databaseDAO.versionExists() == null){
			return -1;
		}
		return databaseDAO.getVersion();
	}

	@Override
	public int getSystemVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update() {
		try {
			int version = getProfileVersion();
			while(true){
				version++;
				FileObject[] files = getDDLFiles(version);
				if(files == null || files.length ==0){
					break;
				}
				
				for(int i = 0; i < files.length; i++){
					//Read file
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(files[i].getContent().getInputStream()));
					StringBuffer sb = new StringBuffer();
					String line = null;
					while((line = reader.readLine()) != null){
						sb.append(line);
						sb.append("\r");
					}

					//get SQL String and run
					String sqlstring = sb.toString();
					if(sqlstring == null || sqlstring.trim().length() ==0)continue;

					Map<String, Object> param = new HashMap<String, Object>();
					param.put("sqlstring", sqlstring);
					databaseDAO.ddl(param);
				}
				
				databaseDAO.updateVersion(version);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	FileObject[] getDDLFiles(final int version){
		try {
			FileObject folder = vfsComponent.getFileSystemManager().resolveFile("ddl://");
			
			FileObject[] files = folder.findFiles(new FileSelector(){
				public boolean traverseDescendents(FileSelectInfo fileInfo) throws Exception{return true;}
					public boolean includeFile(FileSelectInfo fileInfo) throws Exception{
						return fileInfo.getFile().getType() == FileType.FILE
								&& fileInfo.getFile().getName().getBaseName().indexOf(version + ".") == 0
								&& "sql".equalsIgnoreCase(fileInfo.getFile().getName().getExtension());
					}
			});
			
			List<String> names = new ArrayList<String>();
			for(int i = 0; i < files.length; i++){
				String name = files[i].getName().getBaseName();
				names.add(name);
			}
			Collections.sort(names);
			
			files = new FileObject[names.size()];
			
			for(int i = 0; i < names.size(); i++){
				files[i] = folder.resolveFile(names.get(i));
			}
			
			return files;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
