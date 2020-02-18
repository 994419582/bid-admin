package cn.teleinfo.bidadmin.blockchain.bifj.abi.election;

import org.bifj.tx.Contract;
import org.bifj.protocol.Bifj;
import org.bifj.tx.TransactionManager;
import org.bifj.tx.gas.ContractGasProvider;

import org.bifj.abi.datatypes.*;
import org.bifj.abi.datatypes.generated.*;
import org.bifj.abi.TypeReference;
import org.bifj.protocol.core.RemoteFunctionCall;
import org.bifj.protocol.core.methods.response.TransactionReceipt;
import org.bifj.protocol.core.methods.response.BaseEventResponse;
import org.bifj.protocol.core.methods.response.Log;
import org.bifj.protocol.core.methods.request.CoreFilter;
import io.reactivex.Flowable;
import org.bifj.abi.EventEncoder;
import org.bifj.protocol.core.DefaultBlockParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Go_Election extends Contract {

	public static final String FUNC_REGISTER_WITNESS = "registerWitness";

	public static final String FUNC_UNREGISTER_WITNESS = "unregisterWitness";

	public static final String FUNC_VOTE_WITNESSES = "voteWitnesses";

	public static final String FUNC_CANCEL_VOTE = "cancelVote";

	public static final String FUNC_START_PROXY = "startProxy";

	public static final String FUNC_STOP_PROXY = "stopProxy";

	public static final String FUNC_CANCEL_PROXY = "cancelProxy";

	public static final String FUNC_SET_PROXY = "setProxy";

	public static final String FUNC_STAKE = "stake";

	public static final String FUNC_UN_STAKE = "unStake";

	public static final String FUNC_EXTRACT_OWN_BOUNTY = "extractOwnBounty";

	public static final Event ELECT_EVENT_EVENT = new Event("electEvent",
			Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Uint32>() {},new TypeReference<Utf8String>() {},new TypeReference<Uint256>() {}));

	public static final String FUNC_ISSUE_ADDTITIONAL_BOUNTY = "issueAddtitionalBounty";

	public static final String FUNC_QUERY_CANDIDATES = "queryCandidates";

	public static final String FUNC_QUERY_ALL_CANDIDATES = "queryAllCandidates";

	public static final String FUNC_QUERY_VOTER = "queryVoter";

	public static final String FUNC_QUERY_STAKE = "queryStake";

	public static final String FUNC_QUERY_VOTER_LIST = "queryVoterList";

	public RemoteFunctionCall<TransactionReceipt> registerWitness(Utf8String nodeUrl,Utf8String website,Utf8String name) {
		final Function function = new Function(
				FUNC_REGISTER_WITNESS,
				Arrays.<Type>asList(nodeUrl,website,name),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> unregisterWitness() {
		final Function function = new Function(
				FUNC_UNREGISTER_WITNESS,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> voteWitnesses(Utf8String candidate) {
		final Function function = new Function(
				FUNC_VOTE_WITNESSES,
				Arrays.<Type>asList(candidate),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> cancelVote() {
		final Function function = new Function(
				FUNC_CANCEL_VOTE,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> startProxy() {
		final Function function = new Function(
				FUNC_START_PROXY,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> stopProxy() {
		final Function function = new Function(
				FUNC_STOP_PROXY,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> cancelProxy() {
		final Function function = new Function(
				FUNC_CANCEL_PROXY,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> setProxy(Utf8String proxy) {
		final Function function = new Function(
				FUNC_SET_PROXY,
				Arrays.<Type>asList(proxy),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> stake(Uint256 stakeCount) {
		final Function function = new Function(
				FUNC_STAKE,
				Arrays.<Type>asList(stakeCount),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> unStake() {
		final Function function = new Function(
				FUNC_UN_STAKE,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> extractOwnBounty() {
		final Function function = new Function(
				FUNC_EXTRACT_OWN_BOUNTY,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public static class ElectEventEventResponse extends BaseEventResponse {
		public Utf8String methodName;
		public Uint32 status;
		public Utf8String reason;
		public Uint256 time;
	}

	public List<ElectEventEventResponse> getElectEventEvents(TransactionReceipt transactionReceipt) {		List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ELECT_EVENT_EVENT, transactionReceipt);
		ArrayList<ElectEventEventResponse> responses = new ArrayList<>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			ElectEventEventResponse typedResponse = new ElectEventEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.methodName = (Utf8String) eventValues.getNonIndexedValues().get(0);
			typedResponse.status = (Uint32) eventValues.getNonIndexedValues().get(1);
			typedResponse.reason = (Utf8String) eventValues.getNonIndexedValues().get(2);
			typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(3);
			responses.add(typedResponse);
		}
		return responses;
	}

	public Flowable<ElectEventEventResponse> electEventEventFlowable(CoreFilter filter) {
		return bifj.coreLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ElectEventEventResponse>() {
			@Override
			public ElectEventEventResponse apply(Log log) {
				Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ELECT_EVENT_EVENT, log);
				ElectEventEventResponse typedResponse = new ElectEventEventResponse();
				typedResponse.log = eventValues.getLog();
				typedResponse.methodName = (Utf8String) eventValues.getNonIndexedValues().get(0);
				typedResponse.status = (Uint32) eventValues.getNonIndexedValues().get(1);
				typedResponse.reason = (Utf8String) eventValues.getNonIndexedValues().get(2);
				typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(3);
				return typedResponse;
			}
		});
	}

	public Flowable<ElectEventEventResponse> electEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		CoreFilter filter = new CoreFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(ELECT_EVENT_EVENT));
		return electEventEventFlowable(filter);
	}

	public RemoteFunctionCall<TransactionReceipt> issueAddtitionalBounty() {
		final Function function = new Function(
				FUNC_ISSUE_ADDTITIONAL_BOUNTY,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<List<Type>> queryCandidates(Utf8String candiaddress) {
		final Function function = new Function(
				FUNC_QUERY_CANDIDATES,
				Arrays.<Type>asList(candiaddress),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Uint64>() {},new TypeReference<Bool>() {},new TypeReference<Utf8String>() {},new TypeReference<Uint64>() {},new TypeReference<Uint64>() {},new TypeReference<Uint64>() {},new TypeReference<Utf8String>() {},new TypeReference<Utf8String>() {}));
		return executeRemoteCallMultipleValueReturn(function);
	}

	public RemoteFunctionCall<List<Type>> queryAllCandidates() {
		final Function function = new Function(
				FUNC_QUERY_ALL_CANDIDATES,
				Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {},new TypeReference<Utf8String>() {}));
		return executeRemoteCallMultipleValueReturn(function);
	}

	public RemoteFunctionCall<List<Type>> queryVoter(Utf8String voteraddress) {
		final Function function = new Function(
				FUNC_QUERY_VOTER,
				Arrays.<Type>asList(voteraddress),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Bool>() {},new TypeReference<Uint64>() {},new TypeReference<Utf8String>() {},new TypeReference<Uint64>() {},new TypeReference<Uint64>() {},new TypeReference<Utf8String>() {}));
		return executeRemoteCallMultipleValueReturn(function);
	}

	public RemoteFunctionCall<List<Type>> queryStake(Utf8String voteraddress) {
		final Function function = new Function(
				FUNC_QUERY_STAKE,
				Arrays.<Type>asList(voteraddress),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Uint64>() {},new TypeReference<Uint64>() {}));
		return executeRemoteCallMultipleValueReturn(function);
	}

	public RemoteFunctionCall<List<Type>> queryVoterList(Utf8String candiaddress) {
		final Function function = new Function(
				FUNC_QUERY_VOTER_LIST,
				Arrays.<Type>asList(candiaddress),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Uint64>() {},new TypeReference<Utf8String>() {}));
		return executeRemoteCallMultipleValueReturn(function);
	}

	protected Go_Election(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		super("", contractAddress, bifj, transactionManager, contractGasProvider);
	}

	public static Go_Election load(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		return new Go_Election(contractAddress, bifj, transactionManager, contractGasProvider);
	}

}