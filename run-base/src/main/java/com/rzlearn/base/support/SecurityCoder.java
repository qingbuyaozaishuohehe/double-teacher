/**
 * 2011-01-11
 */
package com.rzlearn.base.support;

import java.security.Security;

/**
 * <p>ClassName:SecurityCoder</p>
 * <p>Description:加密基类</p>
 * @author JiPeigong
 * @date 2019-01-10 11:22:32
 **/
public class SecurityCoder {
    private static Byte ADDFLAG = 0;
    static {
        if (ADDFLAG == 0) {
            // 加入BouncyCastleProvider支持
            Security.addProvider(new BouncyCastleProvider());
            ADDFLAG = 1;
        }
    }
}
