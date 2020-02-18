package cn.teleinfo.bidadmin.blockchain.controller;


import cn.teleinfo.bidadmin.blockchain.entity.Resource;
import cn.teleinfo.bidadmin.blockchain.ipfs.IPFSClientTemplate;
import cn.teleinfo.bidadmin.blockchain.properties.IpfsProperties;
import cn.teleinfo.bidadmin.blockchain.service.IResourceService;
import cn.teleinfo.bidadmin.blockchain.vo.ResourceVO;
import cn.teleinfo.bidadmin.blockchain.wrapper.ResourceWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springblade.core.boot.ctrl.BladeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/ipfs")
@Api(value = "ipfs 资源", tags = "接口")
public class FileController  extends BladeController {


    @Autowired
	private IPFSClientTemplate template;
	@Autowired
    private IpfsProperties properties;
	@Autowired
	private IResourceService resourceService;


    @PostMapping("/uploadFile")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入file")
    public ResourceVO uploadFile(@RequestParam("file") MultipartFile file) {
		Resource resource =null;
    	if (!file.isEmpty()){
    		 resource =template.uploadFile(file);
		}

		if (resource !=null){
			resource.setRemarks("http://"+properties.getHost()+":"+properties.getCatPort()+"/ipfs/"+ resource.getHash());
			boolean flag=resourceService.save(resource);
			if (flag){
				resource.setHash("http://"+properties.getHost()+":"+properties.getCatPort()+"/ipfs/"+resource.getHash());
				return ResourceWrapper.build().entityVO(resource);
			}
		}
		return new ResourceVO();
    }
    
    
    
//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("packageName") String packageName) {
//    	this.properties.setUploadDir(properties.getUploadDir()+"/"+packageName);
//    	this.fileStorageService = new FileStorageService(properties);
//    	setCreateDir(false);
//
//
//    	List<UploadFileResponse> listFileResponse = Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file, packageName))
//                .collect(Collectors.toList());
//
//        try {
//
//        	File f = new File(properties.getUploadDir());
//        	NamedStreamable.FileWrapper streamable = new NamedStreamable.FileWrapper(f);
//			List<NamedStreamable> list = streamable.getChildren();
//			ipfs = new IPFS(configIPFS.getMultiAddr());
//			List<MerkleNode> nodes = ipfs.add(list, true,false);
//
//			List<String> hashs = nodes.stream().map(node -> node.hash.toString()).collect(Collectors.toList());
//			for (String value : hashs)
//			{
//			  System.out.println("Value of element :"+ value);
//			}
//			System.out.println(hashs.get(hashs.size()-1));
//
//			ListHashs listHashsModel = new ListHashs();
//			listHashsModel.setHash(hashs.get(hashs.size()-1));
//			listHashsModel.setHorario(LocalTime.now());
//			listHashsModel.setName(packageName);
//
//
//			hashController.criarListaRest(listHashsModel);
//
//
//			deleteDirectory(f);
//
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//    	return listFileResponse;
//
//    }

}
