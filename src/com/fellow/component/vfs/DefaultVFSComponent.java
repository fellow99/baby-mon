package com.fellow.component.vfs;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.cache.DefaultFilesCache;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.apache.commons.vfs.provider.FileProvider;

import com.fellow.component.vfs.wrap.WrappedResourceFileProvider;

public class DefaultVFSComponent implements VFSComponent{
  
  private static final Log logger = LogFactory.getLog(DefaultVFSComponent.class);
  
  protected FileSystemManager fsm;
  
  public DefaultVFSComponent(Map<String, FileProvider> providers, Map<String, String> fileSystems){
    // ����һ����׼���ļ�ϵͳ���������ù��������ᱻ��װ����
    StandardFileSystemManager fsmToWrap = new StandardFileSystemManager();
    
    //��ʼ����׼���ļ�ϵͳ����������������FileProvider
    try{
      //�ļ���������
      fsmToWrap.setFilesCache(new DefaultFilesCache());
      //�ļ�ϵͳ��ʼ��
      fsmToWrap.init();

      for (Iterator<Map.Entry<String, FileProvider>> it = providers.entrySet().iterator(); it.hasNext();) {
        Map.Entry<String, FileProvider> entry = it.next();
        String name = entry.getKey();
        FileProvider provider = entry.getValue();

        //���FileProvider����׼���ļ�ϵͳ��������
        fsmToWrap.addProvider(name, provider);

        logger.info("VFS provider successfully registered: " + name);
      }
    } catch(Exception e){
      throw new RuntimeException(e);
    }
    
    
    // ����������ļ�ϵͳ������
    DefaultFileSystemManager fsmWrapped = new DefaultFileSystemManager();
    //��ʼ��������ļ�ϵͳ������
    try{
      //�ļ���������
      fsmWrapped.setFilesCache(new DefaultFilesCache());
      //�ļ�ϵͳ��ʼ��
      fsmWrapped.init();

      for (Iterator<Map.Entry<String, String>> it = fileSystems.entrySet().iterator(); it.hasNext();) {
        try {
          Map.Entry<String, String> entry = it.next();
          String name = entry.getKey();
          String url = entry.getValue();

          if (StringUtils.isEmpty(name)) {
            break;
          }
          if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("URL value is empty.");
          }

          // ·��ע�ᵽ������ļ�ϵͳ������
          fsmWrapped.addProvider(name, new WrappedResourceFileProvider(fsmToWrap, url));

          logger.info("VFS resource successfully registered: " + name);
        } catch (Exception e) {
          logger.error("Error when registering a resource to VFS: " + e);
          throw e;
        }
      }
    } catch(Exception e){
      throw new RuntimeException(e);
    }
    fsm = fsmWrapped;
  }
  
  
  @Override
  public FileSystemManager getFileSystemManager() {
    return fsm;
  }

}
