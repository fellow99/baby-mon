package com.fellow.util;

import java.util.HashMap;

/**
 * Ϊ�˽��������ʱ���д��ʱ��Сд�����⣬���ⴴ���������<br>
 * ���key���ַ�������ͳһ��key������Сд�ַ������룬��ȡ���ݵ�ʱ�����key�Ĵ�Сд
 */
public class LowerCaseKeyMap<T> extends HashMap<String, T>{
  
  /** serialVersionUID */
  private static final long serialVersionUID = 812064927267651841L;

  /**
   * ͳһ��key������Сд�ַ�������
   */
  protected String parseKey(Object key){
    if(key == null) {
      return null;
    } else if(key instanceof String){
      String keyStr = (String) key;
      return keyStr.toLowerCase();
    } else {
      return key.toString();
    }
  }
  
  public T put(String key, T value){
    return super.put(parseKey(key), value);
  }
  
  public T get(Object key){
    return super.get(parseKey(key));
  }
  
  public T remove(Object key){
    return super.remove(parseKey(key));
  }
  
  public boolean containsKey(Object key){
    return super.containsKey(parseKey(key));
  }
}
