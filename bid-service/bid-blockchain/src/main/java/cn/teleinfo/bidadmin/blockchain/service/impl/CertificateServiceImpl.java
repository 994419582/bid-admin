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
package cn.teleinfo.bidadmin.blockchain.service.impl;

import cn.teleinfo.bidadmin.blockchain.bifj.abi.certificate.Go_Certificate;
import cn.teleinfo.bidadmin.blockchain.entity.Certificate;
import cn.teleinfo.bidadmin.blockchain.entity.CertificateInfo;
import cn.teleinfo.bidadmin.blockchain.entity.CertificateSignature;
import cn.teleinfo.bidadmin.blockchain.mapper.CertificateMapper;
import cn.teleinfo.bidadmin.blockchain.service.ICertificateService;
import cn.teleinfo.bidadmin.blockchain.vo.CertificateVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.bifj.abi.datatypes.Bool;
import org.bifj.abi.datatypes.Type;
import org.bifj.abi.datatypes.Utf8String;
import org.bifj.abi.datatypes.generated.Uint64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 服务实现类
 *
 * @author Blade
 * @since 2019-12-09
 */
@Service
public class CertificateServiceImpl extends ServiceImpl<CertificateMapper, Certificate> implements ICertificateService {

    @Autowired
    private Go_Certificate certificate;

    @Override
    public IPage<CertificateVO> selectCertificatePage(IPage<CertificateVO> page, CertificateVO certificate) {
        return page.setRecords(baseMapper.selectCertificatePage(page, certificate));
    }

    @Override
    public void registerCertificate(CertificateInfo certificateInfo) {
        try {
            certificate.registerCertificate(
                    new Utf8String(certificateInfo.getId()),
                    new Utf8String(certificateInfo.getContext()),
                    new Utf8String(certificateInfo.getSubject()),
                    new Uint64(certificateInfo.getPeriod()),
                    new Utf8String(certificateInfo.getIssuerAlgorithm()),
                    new Utf8String(certificateInfo.getIssuerSignature()),
                    new Utf8String(certificateInfo.getSubjectPublicKey()),
                    new Utf8String(certificateInfo.getSubjectAlgorithm()),
                    new Utf8String(certificateInfo.getSubjectSignature())
            ).sendAsync();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void revocedCertificate(String address) {
        try {
            certificate.revocedCertificate(new Utf8String(address)).send();


            QueryWrapper<Certificate> certificateQueryWrapper = new QueryWrapper<>();
            certificateQueryWrapper.eq("proofId", address);
            certificateQueryWrapper.last("limit 1");

            Certificate c = baseMapper.selectOne(certificateQueryWrapper);

            c.setIsEnable(0);

            baseMapper.updateById(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int queryPeriod(String address) {
        try {
            Uint64 uint64 = certificate.queryPeriod(new Utf8String(address)).sendAsync().get();
            if (uint64 != null) {
                return uint64.getValue().intValue();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean queryActive(String address) {
        try {
            Bool bool = certificate.queryActive(new Utf8String(address)).sendAsync().get();
            if (bool != null) {
                return bool.getValue();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String queryIssuer(String address) {
        try {
            Utf8String utf8String = certificate.queryIssuer(new Utf8String(address)).sendAsync().get();
            if (utf8String != null) {
                return utf8String.toString();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CertificateSignature queryIssuerSignature(String address) {
        try {
            List<Type> signatures = certificate.queryIssuerSignature(new Utf8String(address)).sendAsync().get();
            if (signatures != null && !signatures.isEmpty()) {
                CertificateSignature c = new CertificateSignature();
                Type id = signatures.get(0);
                if (id != null) {
                    c.setId(id.toString());
                }
                Type publicKey = signatures.get(0);
                if (publicKey != null) {
                    c.setPublicKey(publicKey.toString());
                }
                Type algorithm = signatures.get(0);
                if (algorithm != null) {
                    c.setAlgorithm(algorithm.toString());
                }
                Type signature = signatures.get(0);
                if (signature != null) {
                    c.setSignature(signature.toString());
                }
                return c;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CertificateSignature querySubjectSignature(String address) {
        try {
            List<Type> signatures = certificate.querySubjectSignature(new Utf8String(address)).sendAsync().get();
            if (signatures != null && !signatures.isEmpty()) {
                CertificateSignature c = new CertificateSignature();
                Type id = signatures.get(0);
                if (id != null) {
                    c.setId(id.toString());
                }
                Type publicKey = signatures.get(0);
                if (publicKey != null) {
                    c.setPublicKey(publicKey.toString());
                }
                Type algorithm = signatures.get(0);
                if (algorithm != null) {
                    c.setAlgorithm(algorithm.toString());
                }
                Type signature = signatures.get(0);
                if (signature != null) {
                    c.setSignature(signature.toString());
                }
                return c;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
