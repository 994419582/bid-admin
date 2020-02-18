package cn.teleinfo.bidadmin.blockchain.ipfs;

import cn.teleinfo.bidadmin.blockchain.properties.IpfsProperties;
import io.ipfs.api.IPFS;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * IPFSlient工厂类，通过IPFSClient工厂提供IPFSClient实例的创建和销毁
 */
@Slf4j
public class IpfsClientFactory extends BasePooledObjectFactory<IPFS> {
    private IpfsProperties config;

    public IpfsClientFactory(IpfsProperties config) {
        this.config = config;
    }

//    public IpfsProperties getIpfsProperties(){
//        return this.config;
//    }

    /**
     * 创建FtpClient对象
     */
    @Override
    public IPFS create() {
        IPFS ipfs = new IPFS(config.getMultiAddr());

        try {
            if(ipfs.id().isEmpty()){
                log.error("create ipfs connection failed...");
            }

        } catch (IOException e) {
            log.error("create ftp connection failed...", e);
        }
        return ipfs;
    }

    /**
     * 用PooledObject封装对象放入池中
     */
    @Override
    public PooledObject<IPFS> wrap(IPFS ipfs) {
        return new DefaultPooledObject<>(ipfs);
    }

    /**
     * 销毁FtpClient对象
     */
    @Override
    public void destroyObject(PooledObject<IPFS> ipfsPooledObject) {
        if (ipfsPooledObject == null) {
            return;
        }
    }

    /**
     * 验证FtpClient对象
     */
    @Override
    public boolean validateObject(PooledObject<IPFS> ipfsPooledObject) {
        try {
            IPFS ipfs = ipfsPooledObject.getObject();
            return ! ipfs.version().isEmpty();
        } catch (IOException e) {
            log.error("failed to validate client: {}", e);
        }
        return false;
    }


}
