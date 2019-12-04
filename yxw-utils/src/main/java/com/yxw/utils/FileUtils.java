/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2014 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年1月26日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * <p>
 * Title:文件管理
 * </p>
 * <p>
 * Description:支持文件查找,文件删除,文件复制,文件移动,文件重命名,创建文件夹,创建文件.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * <p>
 * Company:广州金税信息系统集成有限公司
 * </p>
 * 
 * @author 申午武 2013-8-5
 * @see
 * @since 1.0
 */
public class FileUtils {
    
    /**
     * 文件系统分隔符
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    // private final static int BIG_BUFFER_SIZE = 1024 * 1024 * 10;// 10MB
    /**
     * 缓冲大小为1024*16
     */
    private final static int BUFFER_SIZE = 1024 * 16;
    
    /**
     * 查找文件
     * 
     * @param path
     *            路径
     * @return
     */
    public static File searchFile(String path) {
        List<File> files = searchFiles(path, null, false);
        if (files.size() > 0) {
            return files.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * 查找文件
     * 
     * @param path
     *            路径
     * @param ext
     *            要包含的文件的后缀名
     * @return
     */
    public static List<File> searchFiles(String path, String ext) {
        return searchFiles(path, new String[] { ext }, false);
    }
    
    /**
     * 查找文件
     * 
     * @param path
     *            路径
     * @param isSearchChild
     *            是否查找子目录
     * @return
     */
    public static List<File> searchFiles(String path, boolean isSearchChild) {
        return searchFiles(path, null, isSearchChild);
    }
    
    /**
     * 查找文件
     * 
     * @param path
     *            路径
     * @param exts
     *            要包含的文件的后缀名
     * @param isSearchChild
     *            是否检索子目录
     * @return
     */
    public static List<File> searchFiles(String path, String[] exts, boolean isSearchChild) {
        
        List<File> files = new ArrayList<File>();
        File file = new File(path);
        if (!file.exists()) {
            return files;
        }
        String[] filePaths = file.list();
        for (int i = 0; i < filePaths.length; i++) {
            File tempFile = new File(path + FILE_SEPARATOR + filePaths[i]);
            // 递归检索子文件夹
            if (tempFile.isDirectory() && isSearchChild) {
                List<File> tempList = searchFiles(tempFile.getPath(), exts, isSearchChild);
                if (tempList != null && tempList.size() > 0) {
                    files.addAll(tempList);
                }
            } else {
                if (tempFile.exists()) {
                    if (exts == null || exts.length <= 0) {
                        files.add(tempFile);
                    } else {
                        String ext = getFileExt(tempFile.getName());
                        for (int j = 0; j < exts.length; j++) {
                            if (ext.equalsIgnoreCase(exts[j])) {
                                files.add(tempFile);
                            }
                        }
                    }
                }
                
            }
            
        }
        return files;
    }
    
    public static void main(String[] args) {
        List<File> files = FileUtils.searchFiles("F:\\yxw\\基础框架", null, true);
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            System.out.println(file.getName());
        }
        System.out.println(files.size());
    }
    
    /**
     * 获取文件扩展名
     * 
     * @return string
     */
    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    
    // /**
    // * find File count
    // * @param list 指定目录
    // * @param endName 指定以''结尾的文件
    // * @return 得到的文件数目
    // */
    // public int findFileCount(File list, String endName)
    // {
    // int textCount = 0;
    // File[] fileArray = list.listFiles();
    // if (null == fileArray)
    // {
    // return 0;
    // }
    // for (int i = 0; i < fileArray.length; i++)
    // {
    // if (fileArray[i].isDirectory())
    // {
    // textCount += findFileCount(fileArray[i].getAbsoluteFile(),
    // endName);
    // }
    // else if (fileArray[i].isFile())
    // {
    // if (fileArray[i].getName().endsWith(endName))
    // {
    // textCount++;
    // }
    // }
    // }
    // return textCount;
    // }
    //
    // /***
    // * find List File
    // * @param path 文件当前路径 查找当前目录下面的文件夹及文件,文件夹不包括子文件.
    // */
    // public List findListFile(String path)
    // {
    // File files = new File(path);
    // File[] fileList = files.listFiles();
    // List currentList = null;
    // if (fileList != null)
    // {
    // Arrays.sort(fileList);
    // }
    // if (fileList != null)
    // {
    // currentList = new ArrayList();
    // for (int i = 0; i < fileList.length; i++)
    // {
    // if (fileList[i].isDirectory())
    // {
    // currentList.add(fileList[i]);
    // }
    // }
    // for (int i = 0; i < fileList.length; i++)
    // {
    // if (fileList[i].isFile())
    // {
    // currentList.add(fileList[i]);
    // }
    // }
    // }
    // return currentList;
    // }
    //
    // /***
    // * expand File List
    // * @param files 文件列表
    // * @param inclDirs
    // * @return
    // */
    // @SuppressWarnings("unchecked")
    // public static Vector expandFileList(String[] files, boolean inclDirs)
    // {
    // Vector v = new Vector();
    // if (files == null)
    // {
    // return v;
    // }
    // for (int i = 0; i < files.length; i++)
    // {
    // v.add(new File(files[i]));
    // }
    // for (int i = 0; i < v.size(); i++)
    // {
    // File f = (File) v.get(i);
    // if (f.isDirectory())
    // {
    // File[] fs = f.listFiles();
    // for (int n = 0; n < fs.length; n++)
    // {
    // v.add(fs[n]);
    // }
    // if (!inclDirs)
    // {
    // v.remove(i);
    // i--;
    // }
    // }
    // }
    // return v;
    // }
    //
    // /***
    // * copy file
    // * @param files 要移动的文件列表
    // * @param newPath 要移动的新位置
    // * @param path 当前的文件的位置
    // * @return 返回成功以否的提示信息
    // */
    // public String copyFile(String[] files, String newPath, String path)
    // throws Exception
    // {
    // StringBuffer errorInfo = new StringBuffer();
    // boolean error = false;// 是否出现错误
    // String resultInfo = "";
    // boolean success = false;// 是否成功
    // int total = 0;
    // File f_old = null;
    // File f_new = null;
    // FileInputStream fis = null;
    // FileOutputStream fos = null;
    // Vector v = expandFileList(files, true);
    // byte buffer[] = new byte[0xffff];
    // try
    // {
    // int b;
    // for (int i = 0; i < v.size(); i++)
    // {
    // f_old = (File) v.get(i);
    // if (!newPath.trim().endsWith(File.separator))
    // {
    // newPath = newPath.trim() + File.separator;
    // }
    // f_new = new File(newPath.trim()
    // + f_old.getAbsolutePath().substring(path.length()));
    // // 如果是文件夹就直接创建
    // if (f_old.isDirectory())
    // {
    // f_new.mkdirs();
    // total++;
    // }
    // else if (f_new.exists())
    // {
    // errorInfo.append("不能复制[" + f_new.getAbsolutePath()
    // + "],文件已存在");
    // continue;
    // }
    // else
    // {
    // fis = new FileInputStream(f_old);
    // fos = new FileOutputStream(f_new);
    // while ((b = fis.read(buffer)) != -1)
    // {
    // fos.write(buffer, 0, b);
    // }
    // total++;
    // }
    // }
    // }
    // catch (Exception e)
    // {
    // error = true;
    // }
    // finally
    // {
    // if (fis != null)
    // {
    // fis.close();
    // }
    // if (fos != null)
    // {
    // fos.close();
    // }
    // }
    // if (error)
    // {
    // resultInfo = "{success:false,info:有" + total + "个文件已复制到:["
    // + newPath + "]," + errorInfo + "}";
    // }
    // else
    // {
    // resultInfo = "{success:true,info:有" + total + "个文件已复制到:[" + newPath
    // + "]}";
    // }
    // return resultInfo;
    // }
    //
    // /***
    // * delete file
    // * @param files 要删除的文件列表
    // * @return 返回成功与否的提示
    // */
    // public String deleteFile(String[] files)
    // {
    // String resultInfo = "";
    // boolean error = false;// 是否出现错误
    // Vector v = expandFileList(files, true);
    // int total = 0;
    // for (int i = v.size() - 1; i >= 0; i--)
    // {
    // File f = (File) v.get(i);
    // if (!f.delete())
    // {
    // error = true;
    // continue;// 继续进行循环
    // }
    // total++;
    // }
    // if (error)
    // {
    // resultInfo = "{success:false,info:有" + total + "个文件被删除,部分文件无法删除}";
    // }
    // else
    // {
    // resultInfo = "{success:true,info:有}" + total + "个文件已删除</div>";
    // }
    // return resultInfo;
    // }
    //
    // /***
    // * remove file
    // * @param files 要移动的文件列表
    // * @param newPath 要移动的新位置
    // * @return 成功则返回false,失败返回true.
    // */
    // public boolean removeFile(String[] files, String newPath)
    // {
    // StringBuffer errorInfo = new StringBuffer();
    // boolean error = false;// 是否出现错误
    // File f_old = null;
    // File f_new = null;
    // try
    // {
    // for (int i = 0; i < files.length; i++)
    // {
    // f_old = new File(files[i]);
    // if (!newPath.trim().endsWith(File.separator))
    // {
    // newPath = newPath.trim() + File.separator;
    // }
    // f_new = new File(newPath.trim() + f_old.getName());
    // if (!f_old.renameTo(f_new))
    // {
    // error = true;
    // continue;// 继续进行for循环
    // }
    // }
    // }
    // catch (Exception e)
    // {
    // error = true;
    // }
    // return error;
    //
    // }
    //
    // /***
    // * create Folder
    // * @param path 要创建文件夹的位置
    // * @param name 要创建文件夹的名称 return 返回成功以否的提示
    // */
    // public String createFolder(String path, String name)
    // {
    // String sysInfo = "";
    // String resultInfo = "";
    // String fileExistsError = "";
    // boolean success = false;// 是否成功
    // if (!path.trim().endsWith(File.separator))
    // {
    // path = path.trim() + File.separator;
    // }
    // String crPath = path.trim() + name;
    // try
    // {
    // File crfolderFile = new File(crPath);
    // if (crfolderFile.exists())
    // {
    // fileExistsError = "文件夹已存在";
    // }
    // else
    // {
    // success = crfolderFile.mkdir();// 如果创建成功返回true
    // }
    // }
    // catch (NullPointerException crFolderNPex)
    // {
    // sysInfo = crFolderNPex.getMessage();
    // success = false;
    // }
    // catch (SecurityException crFolderSFex)
    // {
    // sysInfo = crFolderSFex.getMessage();
    // success = false;
    // }
    // if (success)
    // {
    // resultInfo = "{success:true,info:1个文件夹已创建:[" + crPath + "]}";
    // }
    // else
    // {
    // resultInfo = "{success:false,info:0个文件夹已创建,不能创建:[" + crPath + "]."
    // + fileExistsError + "}";
    // }
    // return resultInfo;
    // }
    //
    // /***
    // * create File
    // * @param path 要创建文件的位置
    // * @param name 要创建文件的名称 return 返回成功以否的提示
    // */
    // public String createFile(String path, String name) throws Exception
    // {
    // String resultInfo = "";
    // String fileExistsError = "";
    // boolean success = false;// 是否成功
    // if (!path.trim().endsWith(File.separator))
    // {
    // path = path + File.separator;
    // }
    // String crPath = path + name;
    // File crFile = new File(crPath);
    // if (crFile.exists())
    // {
    // fileExistsError = "文件已经存在";
    // }
    // else
    // {
    // success = crFile.createNewFile();
    // }
    // if (success)
    // {
    // resultInfo = "{success:true,info:1个文件已创建:[" + crPath + "]}";
    // }
    // else
    // {
    // resultInfo = "{success:false,info:0个文件已创建,不能创建:[" + crPath + "]."
    // + fileExistsError + "}";
    // }
    // return resultInfo;
    // }
    //
    // /***
    // * rename file
    // * @param path 要重命名的文件位置
    // * @param oldFileName 旧文件名
    // * @param newFileName 新文件名
    // * @return 返回成功以否的提示
    // */
    // public String renameFile(String path, String oldFileName, String
    // newFileName)
    // throws Exception
    // {
    // String sysInfo = "";
    // String resultInfo = "";
    // String fileExistsError = "";
    // boolean success = false;// 是否成功
    // if (!path.trim().endsWith(File.separator))
    // {
    // path = path + File.separator;
    // }
    // File f_old = new File(path + oldFileName);
    // File f_new = new File(path + newFileName);
    // // 如果新文件名已有
    // if (f_new.exists())
    // {
    // fileExistsError = "文件或文件夹已经存在";
    // }
    // else
    // {
    // success = f_old.renameTo(f_new);
    // }
    // if (success)
    // {
    // resultInfo = "{success:true,info:[" + oldFileName + "]已重命名为:["
    // + newFileName + "]}";
    // }
    // else
    // {
    // resultInfo = "{success:false,info:不能重命名[" + oldFileName + "]为["
    // + newFileName + "]." + fileExistsError + "}";
    // }
    // return resultInfo;
    // }
}
