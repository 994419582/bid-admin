/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.teleinfo.bidadmin.blockchain.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author Blade
 * @since 2019-10-17
 */
@Data
@ApiModel(value = "CertificateApply对象", description = "CertificateApply对象")
public class CertificateApply implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String context;

    private Content content;

    private Metadata metadata;

    private Signature signature;

    @Data
    public static class Content implements Serializable {
        private String name;
        private String idCardNumber;
        private String birthday;
        private String encryption;
    }

    @Data
    public static class Metadata implements Serializable {
        private LocalDateTime createTime;
        private String issuer;
        private String subject;
        private long iat;
        private long exp;
        private boolean revocation;
    }

    @Data
    public static class Signature implements Serializable {
        private String publicKeyId;
        private String format;
        private String algorithm;
        private String value;
    }

    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
