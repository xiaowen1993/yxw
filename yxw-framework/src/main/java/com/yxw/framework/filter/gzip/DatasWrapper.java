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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <p>
 * CompressionResponseWrapper类从HttpServletWrapper类继承 重写了getWriter()和getOutputStream()方法
 * 用GZIPServletOutputStream替换了ServletOutputStream对象
 * </p>
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年2月9日
 */

public class DatasWrapper extends HttpServletResponseWrapper {
    public static final int OUT_NONE = 0;
    public static final int OUT_WRITER = 1;
    public static final int OUT_STREAM = 2;
    private int outputType = OUT_NONE;
    private ServletOutputStream output = null;
    private PrintWriter writer = null;
    private ByteArrayOutputStream buffer = null;
    
    public DatasWrapper(HttpServletResponse response) {
        super(response);
        buffer = new ByteArrayOutputStream();
    }
    
    public PrintWriter getWriter() throws IOException {
        if (outputType == OUT_STREAM) {
            throw new IllegalStateException();
        } else if (outputType == OUT_WRITER) {
            return writer;
        } else {
            outputType = OUT_WRITER;
            writer = new PrintWriter(new OutputStreamWriter(buffer, getCharacterEncoding()));
            return writer;
        }
    }
    
    public ServletOutputStream getOutputStream() throws IOException {
        if (outputType == OUT_WRITER) {
            throw new IllegalStateException();
        } else if (outputType == OUT_STREAM) {
            return output;
        } else {
            outputType = OUT_STREAM;
            output = new DatasWrappedOutputStream(buffer);
            return output;
        }
    }
    
    public void flushBuffer() throws IOException {
        if (outputType == OUT_WRITER) {
            writer.flush();
        }
        if (outputType == OUT_STREAM) {
            output.flush();
        }
    }
    
    public void reset() {
        outputType = OUT_NONE;
        buffer.reset();
    }
    
    public void finalize() throws Throwable {
        super.finalize();
        output.close();
        writer.close();
        
    }
    
    public byte[] getResponseData() throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }
    
    class DatasWrappedOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream buffer;
        
        public DatasWrappedOutputStream(ByteArrayOutputStream buffer) {
            this.buffer = buffer;
        }
        
        public void write(int b) throws IOException {
            buffer.write(b);
        }
        
        public byte[] toByteArray() {
            return buffer.toByteArray();
        }
    }
}
