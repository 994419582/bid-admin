package cn.teleinfo.bidadmin.blockchain.ipfs;

import cn.teleinfo.bidadmin.blockchain.entity.Resource;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 实现文件上传下载
 *
 * @author ZhenJin
 */
@Slf4j
public class IPFSClientTemplate {

    private GenericObjectPool<IPFS> ipfsPool;


    public IPFSClientTemplate(IpfsClientFactory ipfsClientFactory) {
        this.ipfsPool = new GenericObjectPool<>(ipfsClientFactory);
    }

    /***
     * 上传ipfs文件
     *
     * @param file 当地文件
     * @return true or false
     */
    public Resource uploadFile(MultipartFile file) {
        IPFS ipfs = null;
        Resource resource = null;
        int n=0;
        File f = new File(file.getOriginalFilename());
        try {
            //从池中获取对象
            ipfs = ipfsPool.borrowObject();
            if (ipfs.id().isEmpty()) {
                log.warn("ipfs server refused connection");
                return null;
            }

            try (InputStream in  = file.getInputStream(); OutputStream os = new FileOutputStream(f)){
                byte[] buffer = new byte[4096];
                while ((n = in.read(buffer,0,4096)) != -1){
                    os.write(buffer,0,n);
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            NamedStreamable.FileWrapper streamable = new NamedStreamable.FileWrapper(f);
            MerkleNode  node = ipfs.add(streamable).get(0);
            resource = new Resource();
            resource.setHash(node.hash.toString());
            resource.setLargeSize(Long.getLong(node.largeSize.toString()));
            resource.setName(file.getOriginalFilename());
            resource.setType(file.getContentType());
            resource.setSize(file.getSize());
            f.delete();
            return resource;

        } catch (FileNotFoundException e) {
            log.error("file not found!{}", file);
        } catch (Exception e) {
            log.error("upload file failure!", e);
        } finally {
            f.deleteOnExit();
            ipfsPool.returnObject(ipfs);
        }
        return null;
    }

//    /**
//     * 下载文件
//     *
//     * @param remotePath FTP服务器文件目录
//     * @param fileName   需要下载的文件名称
//     * @param localPath  下载后的文件路径
//     * @return true or false
//     */
//    public boolean downloadFile(String remotePath, String fileName, String localPath) {
//        FTPClient ftpClient = null;
//        OutputStream outputStream = null;
//        try {
//            ftpClient = ftpClientPool.borrowObject();
//            // 验证FTP服务器是否登录成功
//            int replyCode = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(replyCode)) {
//                log.warn("ftpServer refused connection, replyCode:{}", replyCode);
//                return false;
//            }
//
//            // 切换FTP目录
//            ftpClient.changeWorkingDirectory(remotePath);
//            FTPFile[] ftpFiles = ftpClient.listFiles();
//            for (FTPFile file : ftpFiles) {
//                if (fileName.equalsIgnoreCase(file.getName())) {
//                    StringBuilder stringBuilder = new StringBuilder();
//                    stringBuilder.append(localPath).append(File.separator).append(file.getName());
//                    File localFile = new File(stringBuilder.toString());
//                    outputStream = new FileOutputStream(localFile);
//                    ftpClient.retrieveFile(file.getName(), outputStream);
//                }
//            }
//            ftpClient.logout();
//            return true;
//        } catch (Exception e) {
//            log.error("download file failure!", e);
//        } finally {
//            IOUtils.closeQuietly(outputStream);
//            ftpClientPool.returnObject(ftpClient);
//        }
//        return false;
//    }

    /**
     * 删除文件
     *
     * @param resource
     * @return true or false
     */
    public boolean deleteFile(Resource resource) {
        IPFS ipfs = null;
        try {
            ipfs = ipfsPool.borrowObject();
            // 验证FTP服务器是否登录成功
            //从池中获取对象
            ipfs = ipfsPool.borrowObject();
            if (ipfs.id().isEmpty()) {
                log.warn("ipfs server refused connection");
                return false;
            }
            // 切换FTP目录
            Multihash hash=new Multihash(resource.getHash().getBytes());
            ipfs.ls(hash).clear();
            log.debug("delete file success");
            return true;
        } catch (Exception e) {
            log.error("delete file failure!", e);
        } finally {
            ipfsPool.returnObject(ipfs);
        }
        return false;
    }


}
