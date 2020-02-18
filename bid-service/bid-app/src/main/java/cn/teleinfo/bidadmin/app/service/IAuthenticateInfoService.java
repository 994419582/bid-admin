package cn.teleinfo.bidadmin.app.service;


import cn.teleinfo.bidadmin.app.entity.Result;
import cn.teleinfo.bidadmin.app.vo.ReservedDataEntity;

public interface IAuthenticateInfoService {

	public Result authenticateIdentity(ReservedDataEntity reservedDataEntity,String photo);

}
