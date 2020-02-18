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

import cn.teleinfo.bidadmin.blockchain.bifj.abi.document.DocumentConfigure;
import cn.teleinfo.bidadmin.blockchain.bifj.abi.document.Go_Document;
import cn.teleinfo.bidadmin.blockchain.bifj.abi.election.ElectionConfigure;
import cn.teleinfo.bidadmin.blockchain.bifj.abi.election.Go_Election;
import cn.teleinfo.bidadmin.blockchain.bifj.abi.trustanchor.Go_Trustanchor;
import cn.teleinfo.bidadmin.blockchain.bifj.abi.trustanchor.TrustanchorConfigure;
import cn.teleinfo.bidadmin.blockchain.bifj.bif.BIFClientTemplate;
import cn.teleinfo.bidadmin.blockchain.entity.*;
import cn.teleinfo.bidadmin.blockchain.properties.BifProperties;
import cn.teleinfo.bidadmin.blockchain.service.*;
import cn.teleinfo.bidadmin.blockchain.util.IPUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bifj.abi.datatypes.Type;
import org.bifj.abi.datatypes.Utf8String;
import org.bifj.abi.datatypes.generated.Uint64;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 *  服务实现类
 *
 * @author Blade
 * @since 2019-10-17
 */
@Service
public class DposPullInfoServiceImpl implements IDposPullInfoService {

	@Autowired
	private IDposCandidateService candidateService;
	@Autowired
	private IBidDocumentService documentService;
	@Autowired
	private IDposVoterService voterService;
	@Autowired
	private IDposStakeService stakeService;
	@Autowired
	private ITrustanchorManageService trustanchorManageService;
	@Autowired
	private ITrustanchorVoteService trustanchorVoteService;
	@Autowired
	private IDposCanditionVoterService canditionVoterService;
	@Autowired
	private DocumentConfigure documentConfigure;
	@Autowired
	private ElectionConfigure electionConfigure;
	@Autowired
	private TrustanchorConfigure trustanchorConfigure;
	@Autowired
	private BIFClientTemplate bifClientTemplate;

	@Autowired
	private Go_Document document;
	@Autowired
	private Go_Election election;
	@Autowired
	private Go_Trustanchor trustanchor;

	String[] ipLocations = new String[]{"北京","上海","重庆","武汉","广州"};

	@Override
	public R pullInfo() {
		try {
			List<Type> allCandidates = election.queryAllCandidates().sendAsync().get();
			if (allCandidates != null && allCandidates.size() == 2) {// 数量和地址
				Uint64 count = (Uint64)allCandidates.get(0);
				Utf8String addresses = (Utf8String)allCandidates.get(1);
				int addressLength = count.getValue().intValue() * 32;
				for (int i = 0; i < addressLength;) {
					String address = addresses.getValue().substring(i, i+=32);
					pullDocumentInfo(address);
				}
				System.out.println("Document Info is ok~~");
				for (int i = 0; i < addressLength;) {
					String address = addresses.getValue().substring(i, i+=32);
					pullCandidateListInfo(address);
				}
				System.out.println("Candidate List Info is ok~~");
				for (int i = 0; i < addressLength;) {
					String address = addresses.getValue().substring(i, i+=32);
					pullVoterListInfo(address);
				}
				System.out.println("Voter List Info is ok~~");
				for (int i = 0; i < addressLength;) {
					String address = addresses.getValue().substring(i, i+=32);
					pullStakeListInfo(address);
				}
				System.out.println("Stake List Info is ok~~");
			}
			System.out.println("finish~~~");
			return R.status(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		}
	}

	@Override
	@Transactional
	public R pullStakeListInfo(String address) {
		try {
			List<Type> stakes = election.queryStake(new Utf8String(address)).sendAsync().get();
			if (stakes != null && !stakes.isEmpty()) {
				DposStake s = new DposStake();
				Type owner = stakes.get(0);
				if (owner != null) {
					s.setOwner(owner.toString());
				}
				Type stakeCount = stakes.get(1);
				if (stakeCount != null) {
					BigInteger value = (BigInteger)stakeCount.getValue();
					s.setStakeCount(value.toString());
				}
				Type timestamp = stakes.get(2);
				if (timestamp != null) {
					BigInteger value = (BigInteger)timestamp.getValue();
					if (!"0".equalsIgnoreCase(value.toString())) {
						s.setStakeTime(LocalDateTime.ofInstant(
								Instant.ofEpochSecond(
										Long.parseLong(value.toString())),
								ZoneId.systemDefault()
								)
						);
					}
				}
				stakeService.saveOrUpdateStake(s);
			}
			return R.status(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		}
	}

	@Override
	@Transactional
	public R pullVoterListInfo(String address) {
		try {
			List<Type> voterLists = election.queryVoterList(new Utf8String(address)).sendAsync().get();
			if (voterLists != null && voterLists.size() == 3) {// 数量和地址
				Uint64 voterCount = (Uint64) voterLists.get(1);
				Utf8String voterAddresses = (Utf8String) voterLists.get(2);

				int voterAddressLength = voterCount.getValue().intValue() * 32;
				for (int j = 0; j < voterAddressLength; ) {
					String voterAddress = voterAddresses.getValue().substring(j, j += 32);

					List<Type> voters = election.queryVoter(new Utf8String(voterAddress)).sendAsync().get();
					if (voters != null && !voters.isEmpty()) {
						DposVoter v = new DposVoter();
						Type owner = voters.get(0);
						if (owner != null) {
							v.setOwner(owner.toString());
						}
						Type isproxy = voters.get(1);
						if (isproxy != null) {
							v.setIsProxy(0);
							if ((Boolean)isproxy.getValue()) {
								v.setIsProxy(1);
							}
						}
						Type proxyVoteCount = voters.get(2);
						if (proxyVoteCount != null) {
							BigInteger value = (BigInteger)proxyVoteCount.getValue();
							v.setProxyVoteCount(value.intValue());
						}
						Type proxy = voters.get(3);
						if (proxy != null) {
							v.setProxy(proxy.toString());
						}
						Type lastVoteCount = voters.get(4);
						if (lastVoteCount != null) {
							BigInteger value = (BigInteger)lastVoteCount.getValue();
							v.setLastVoteCount(value.intValue());
						}
						Type timestamp = voters.get(5);
						if (timestamp != null) {
							BigInteger value = (BigInteger)timestamp.getValue();
							if (!"0".equalsIgnoreCase(value.toString())) {
								v.setVoteTime(LocalDateTime.ofInstant(
										Instant.ofEpochSecond(
												Long.parseLong(value.toString())),
										ZoneId.systemDefault()
										)
								);
							}
						}
//								Type voteCandidateList = voters.get(6);
//								if (voteCandidateList != null) {
//								}
						voterService.saveOrUpdateVoter(v);
					}

					DposCanditionVoter cv = new DposCanditionVoter();
					cv.setCandition(address);
					cv.setVoter(voterAddress);
					canditionVoterService.saveOrUpdateCanditionVoter(cv);
				}

			}
			return R.status(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		}
	}

	@Override
	@Transactional
	public R pullCandidateListInfo(String address) {
		try {
			List<Type> candidates = election.queryCandidates(new Utf8String(address)).sendAsync().get();
			if (candidates != null && !candidates.isEmpty()) {
				DposCandidate c = new DposCandidate();
				Type owner = candidates.get(0);
				if (owner != null) {
					c.setOwner(owner.toString());
				}
				Type voteCount = candidates.get(1);
				if (voteCount != null) {
					BigInteger value = (BigInteger)voteCount.getValue();
					c.setVoteCount(value.intValue());
				}
				Type active = candidates.get(2);
				if (active != null) {
					c.setActive(0);
					if ((Boolean)active.getValue()) {
						c.setActive(1);
					}
				}
				Type url = candidates.get(3);
				if (url != null) {
					String u = url.toString();
					c.setUrl(u);

					String ip = IPUtils.filterIP(u);
					c.setIp(ip);
					if ("localhost".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
//						c.setLocation("本地回环");
						int index = new Random().nextInt(ipLocations.length);
						c.setLocation(ipLocations[index]);
					} else {
						c.setLocation(IPUtils.getAddresses("ip="+ip, "utf-8"));
					}
				}
				Type totalBounty = candidates.get(4);
				if (totalBounty != null) {
					BigInteger value = (BigInteger)totalBounty.getValue();
					c.setTotalBounty(value.toString());
				}
				Type extractedbounty = candidates.get(5);
				if (extractedbounty != null) {
					BigInteger value = (BigInteger)extractedbounty.getValue();
					c.setExtractedBounty(value.toString());
				}
				Type lastExtractTime = candidates.get(6);
				if (lastExtractTime != null) {
					BigInteger value = (BigInteger)lastExtractTime.getValue();
					if (!"0".equalsIgnoreCase(value.toString())) {
						c.setLastExtractTime(LocalDateTime.ofInstant(
								Instant.ofEpochSecond(
										Long.parseLong(value.toString())),
								ZoneId.systemDefault()
								)
						);
					}
				}
				Type webSite = candidates.get(7);
				if (webSite != null) {
					c.setWebsite(webSite.toString());
				}
				Type name = candidates.get(8);
				if (name != null) {
					c.setName(name.toString());
				}
				candidateService.saveOrUpdateCandidate(c);
			}
			return R.status(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return R.fail("任务执行失败 err:"+e.getMessage());
		}
	}

	@Override
	public R pullDocumentInfo(String address) {
		if (StringUtil.isEmpty(address)){
			return R.fail("地址不能空");
		}
		if (documentService.getByBId(address)!=null){
			return R.status(true);
		}
		try {
			BigInteger balanceBI = bifClientTemplate.getBalance(address);

			Utf8String documentString = document.FindDDOByType(new Uint64(0), new Utf8String(address)).sendAsync().get();

			if (balanceBI != null && documentString != null) {
				String balance = balanceBI.toString();
				documentService.saveDocument(documentString.getValue(),address, balance);
			}
			return R.status(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		}
	}

	@Override
	public R pullTrustAnchorInfo() {
		pullBaseTrustAnchorInfo();
		pullExpendTrustAnchorInfo();
		return R.status(true);
	}

	@Override
	public R pullBaseTrustAnchorInfo() {
		try {
			Uint64 number = trustanchor.queryExpendTrustAnchorNum().sendAsync().get();
			Utf8String addresses = trustanchor.queryExpendTrustAnchorList().sendAsync().get();

			if (number != null && addresses != null) {
				int count = number.getValue().intValue();
				List<TrustanchorManage> manages = new ArrayList<>(count);

				int addressLength = count * 32;
				for (int i = 0; i < addressLength;) {
					String address = addresses.getValue().substring(i, i += 32);

					List<Type> t = trustanchor.queryTrustAnchor(new Utf8String(address)).sendAsync().get();
					TrustanchorManage trustanchorManage = packTrustanchorManage(t);
					manages.add(trustanchorManage);

				}
				trustanchorManageService.saveOrUpdateTrustanchorManage(manages);
			}
			
			return R.status(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		}
	}

	@Override
	public R pullExpendTrustAnchorInfo() {
		try {
			Uint64 number = trustanchor.queryBaseTrustAnchorNum().sendAsync().get();
			Utf8String addresses = trustanchor.queryBaseTrustAnchorList().sendAsync().get();

			if (number != null && addresses != null) {
				int count = number.getValue().intValue();
				List<TrustanchorManage> manages = new ArrayList<>(count);

				int addressLength = count * 32;
				for (int i = 0; i < addressLength;) {
					String address = addresses.getValue().substring(i, i += 32);

					List<Type> t = trustanchor.queryTrustAnchor(new Utf8String(address)).sendAsync().get();
					TrustanchorManage trustanchorManage = packTrustanchorManage(t);
					manages.add(trustanchorManage);

				}
				trustanchorManageService.saveOrUpdateTrustanchorManage(manages);
			}
			return R.status(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		}
	}

	private TrustanchorManage packTrustanchorManage(List<Type> queryTrustAnchorType) {
		TrustanchorManage manage = new TrustanchorManage();
		Type id = queryTrustAnchorType.get(0);					// string
		if (id != null) {
			manage.setOwner(id.toString());
		}
		Type anchorName = queryTrustAnchorType.get(1);			// string
		if (anchorName != null) {
			manage.setName(anchorName.toString());
		}
		Type company = queryTrustAnchorType.get(2);				// string
		if (company != null) {
			manage.setCompany(company.toString());
		}
		Type companyUrl = queryTrustAnchorType.get(3);			// string
		if (companyUrl != null) {
			manage.setCompanyUrl(companyUrl.toString());
		}
		Type website = queryTrustAnchorType.get(4);				// string
		if (website != null) {
			manage.setWebsite(website.toString());
		}
		Type serverUrl = queryTrustAnchorType.get(5);				// string
		if (serverUrl != null) {
			manage.setServerUrl(serverUrl.toString());
		}
		Type doc = queryTrustAnchorType.get(6);					// string
		if (doc != null) {
			manage.setDocumentUrl(doc.toString());
		}
		Type email = queryTrustAnchorType.get(7);				// string
		if (email != null) {
			manage.setEmail(email.toString());
		}
		Type desc = queryTrustAnchorType.get(8);				// string
		if (desc != null) {
			manage.setDescription(desc.toString());
		}
		Type anchorType = queryTrustAnchorType.get(9);			// string
		if (anchorType != null) {
			BigInteger value = (BigInteger)anchorType.getValue();
			manage.setType(value.intValue());
		}
		Type status = queryTrustAnchorType.get(10);				// uint64
		if (status != null) {
			BigInteger value = (BigInteger)status.getValue();
			manage.setStatus(value.intValue());
		}
		Type active = queryTrustAnchorType.get(11);				// bool
		if (active != null) {
			manage.setActive(0);
			if ((Boolean)active.getValue()) {
				manage.setActive(1);
			}
		}
		Type totalBounty = queryTrustAnchorType.get(12);		// uint64
		if (totalBounty != null) {
			BigInteger value = (BigInteger)totalBounty.getValue();
			manage.setTotalBounty(value.toString());
		}
		Type extractedBounty = queryTrustAnchorType.get(13);	// uint64
		if (extractedBounty != null) {
			BigInteger value = (BigInteger)extractedBounty.getValue();
			manage.setExtractedBounty(value.toString());
		}
		Type lastExtracTime = queryTrustAnchorType.get(14);		// uint64
		if (lastExtracTime != null) {
			BigInteger value = (BigInteger)lastExtracTime.getValue();
			if (!"0".equalsIgnoreCase(value.toString())) {
				manage.setLastExtractTime(LocalDateTime.ofInstant(
						Instant.ofEpochSecond(
								Long.parseLong(value.toString())),
						ZoneId.systemDefault()
						)
				);
			}
		}
		Type voteCount = queryTrustAnchorType.get(15);			// uint64
		if (voteCount != null) {
			BigInteger value = (BigInteger)voteCount.getValue();
			manage.setVoteCount(value.intValue());
		}
		Type stake = queryTrustAnchorType.get(16);			// uint64
		if (stake != null) {
			BigInteger value = (BigInteger)stake.getValue();
			manage.setStake(value.intValue());
		}
		Type createDate = queryTrustAnchorType.get(17);			// uint64
		if (createDate != null) {
			BigInteger value = (BigInteger)createDate.getValue();
			if (!"0".equalsIgnoreCase(value.toString())) {
				manage.setCreateDate(LocalDateTime.ofInstant(
						Instant.ofEpochSecond(
								Long.parseLong(value.toString())),
						ZoneId.systemDefault()
						)
				);
			}
		}
		Type certificateAccount = queryTrustAnchorType.get(18);			// uint64
		if (certificateAccount != null) {
			BigInteger value = (BigInteger)certificateAccount.getValue();
			manage.setCertificateAccount(value.intValue());
		}
		return manage;
	}

	@Override
	public R pullTrustAnchorVoterInfo() {

		try {

			List<Type> allCandidates = election.queryAllCandidates().sendAsync().get();
			if (allCandidates != null && allCandidates.size() == 2) {// 数量和地址
				Uint64 count = (Uint64) allCandidates.get(0);
				Utf8String addresses = (Utf8String) allCandidates.get(1);
				int addressLength = count.getValue().intValue() * 32;
				for (int i = 0; i < addressLength; ) {
					String address = addresses.getValue().substring(i, i += 32);

					Utf8String utf8String = trustanchor.queryVoter(new Utf8String(address)).sendAsync().get();
					if (null != utf8String) {
						String str = utf8String.toString();

						JSONObject jsonObject = JSONObject.parseObject(str);
						String owner = jsonObject.getString("Owner");
						if ("0000000000000000000000000000000000000000".equals(owner)) {
							continue;
						}
						JSONArray VoteCandidates = jsonObject.getJSONArray("VoteCandidates");

						List<TrustanchorVote> trustanchorVotes = new ArrayList<>(VoteCandidates.size());

						for (Object VoteCandidate : VoteCandidates) {
							JSONObject obj = (JSONObject)VoteCandidate;

							TrustanchorVote vote = new TrustanchorVote();
							vote.setVoter(address);
							vote.setTrustAnchor(obj.getString("addr"));
							vote.setValidity(obj.getInteger("vote"));

							trustanchorVotes.add(vote);
						}
						trustanchorVoteService.saveOrUpdateTrustanchorVote(trustanchorVotes);
					}
				}
			}
			return R.status(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return R.fail("任务执行失败 err:"+e.getMessage());
		}
	}
}
