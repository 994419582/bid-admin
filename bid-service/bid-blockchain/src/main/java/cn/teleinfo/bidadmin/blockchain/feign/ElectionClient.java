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
package cn.teleinfo.bidadmin.blockchain.feign;

import cn.teleinfo.bidadmin.blockchain.bifj.abi.election.ElectionConfigure;
import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import cn.teleinfo.bidadmin.blockchain.service.*;
import cn.teleinfo.bidadmin.blockchain.service.IDposCandidateService;
import cn.teleinfo.bidadmin.blockchain.service.IDposCanditionVoterService;
import cn.teleinfo.bidadmin.blockchain.service.IDposStakeService;
import cn.teleinfo.bidadmin.blockchain.service.IDposVoterService;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Notice Feign
 *
 * @author Chill
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
public class ElectionClient implements IElectionClient {

	@Autowired
	private IDposPullInfoService electionInfoService;

	private IDposCandidateService candidateService;
	private IDposVoterService voterService;
	private IDposStakeService stakeService;
	private IDposCanditionVoterService canditionVoterService;
	private ElectionConfigure electionConfigure;
	private BifProperties properties;

	/**
	 * 执行定时任务
	 *
	 * @return
	 */
	@GetMapping(API_PREFIX + "/executeTask")
	public R executeTask() {
		System.out.println("execute load dpos data 定时任务");
		return electionInfoService.pullInfo();
	}


	@GetMapping(API_PREFIX + "/test")
	public R testTask() {
		System.out.println("test load dpos data 定时任务");
		return R.status(true);
	}



}
