package com.fellow.component.vfs.servlet;

import javax.servlet.ServletContext;

import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.VFS;

import com.fellow.component.vfs.wrap.WrappedResourceFileProvider;

public class ServletContextFileProvider extends WrappedResourceFileProvider{
  public ServletContextFileProvider(ServletContext servletContext) throws FileSystemException{
    super(VFS.getManager(), servletContext.getRealPath("/"));
  }
}
