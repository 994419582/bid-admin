package cn.teleinfo.bidadmin.blockchain.bifj.bif;

import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.bifj.protocol.gbif.Gbif;
import org.bifj.protocol.http.HttpService;

import java.io.IOException;

/**
 * IPFSlient工厂类，通过IPFSClient工厂提供IPFSClient实例的创建和销毁
 */
@Slf4j
public class BIFClientFactory extends BasePooledObjectFactory<Gbif> {
    private BifProperties config;

    public BIFClientFactory(BifProperties config) {
        this.config = config;
    }

//    public IpfsProperties getIpfsProperties(){
//        return this.config;
//    }

    /**
     * 创建FtpClient对象
     */
    @Override
    public Gbif create() {
       Gbif gbif = null;
        for (String url:config.urls) {
            gbif = Gbif.build(new HttpService(url));
            if (testGbif(gbif)>0){
                break;
            }
        }
        return gbif;
    }

    private long testGbif(Gbif gbif){
        long result=0;
        try {
            result=gbif.coreBlockNumber().send().getBlockNumber().longValue();
        } catch (IOException e) {
            log.error("bif 链接失败，"+e.getMessage());
        }
        return result;
    }

    /**
     * 用PooledObject封装对象放入池中
     */
    @Override
    public PooledObject<Gbif> wrap(Gbif gbif) {
        return new DefaultPooledObject<>(gbif);
    }

    /**
     * 销毁FtpClient对象
     */
    @Override
    public void destroyObject(PooledObject<Gbif> gbifPooledObject) {
        if (gbifPooledObject == null) {
            return;
        }
    }

    /**
     * 验证FtpClient对象
     */
    @Override
    public boolean validateObject(PooledObject<Gbif> gbifPooledObject) {
        try {
            Gbif gbif = gbifPooledObject.getObject();
            return  gbif.coreBlockNumber().send().getBlockNumber().longValue()>0;
        } catch (IOException e) {
            log.error("failed to validate client: {}", e);
        }
        return false;
    }


}
