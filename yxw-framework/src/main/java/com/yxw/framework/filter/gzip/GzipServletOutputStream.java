/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年2月9日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.filter.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * <p>
 * GZipServletOutputStream继承自ServletOutputStream
 * 该类的对象用于替换HttpServletResponse.getOutputStream()方法返回的ServletOutputStream对象 其内部使用GZipServletOutputStream的write(int
 * b)方法实现ServletOutputStream类的write(int b)方法 以达到压缩数据的目的
 * </p>
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年2月9日
 */

public class GzipServletOutputStream extends ServletOutputStream {
    private ByteArrayOutputStream buffer;
    
    public GzipServletOutputStream(ByteArrayOutputStream buffer) {
        this.buffer = buffer;
    }
    
    public void write(int b) throws IOException {
        buffer.write(b);
    }
    
    public byte[] toByteArray() {
        return buffer.toByteArray();
    }
}
