/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2017 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2017年7月5日</p>
 *  <p> Created by 谢家贵</p>
 *  </body>
 * </html>
 */

package com.yxw.mobileapp.invoke;

import com.yxw.mobileapp.invoke.dto.PayNotice;

/**
 * @author 谢家贵
 * @version 1.0
 */

public interface PayNoticeService {
    public void push(PayNotice payNotice);
}
