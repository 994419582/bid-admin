package cn.teleinfo.bidadmin.blockchain.bifj.abi.trustanchor;

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

public class Go_Trustanchor extends Contract {

	public static final String FUNC_REGISTER_TRUST_ANCHOR = "registerTrustAnchor";

	public static final String FUNC_UN_REGISTER_TRUST_ANCHOR = "unRegisterTrustAnchor";

	public static final String FUNC_IS_TRUST_ANCHOR = "isTrustAnchor";

	public static final String FUNC_UPDATE_BASE_ANCHOR_INFO = "updateBaseAnchorInfo";

	public static final String FUNC_UPDATE_EXETEND_ANCHOR_INFO = "updateExetendAnchorInfo";

	public static final String FUNC_EXTRACT_OWN_BOUNTY = "extractOwnBounty";

	public static final String FUNC_QUERY_TRUST_ANCHOR = "queryTrustAnchor";

	public static final String FUNC_QUERY_VOTER = "queryVoter";

	public static final String FUNC_CHECK_SENDER_ADDRESS = "checkSenderAddress";

	public static final String FUNC_QUERY_TRUST_ANCHOR_STATUS = "queryTrustAnchorStatus";

	public static final String FUNC_QUERY_BASE_TRUST_ANCHOR_LIST = "queryBaseTrustAnchorList";

	public static final String FUNC_QUERY_BASE_TRUST_ANCHOR_NUM = "queryBaseTrustAnchorNum";

	public static final String FUNC_QUERY_EXPEND_TRUST_ANCHOR_LIST = "queryExpendTrustAnchorList";

	public static final String FUNC_QUERY_EXPEND_TRUST_ANCHOR_NUM = "queryExpendTrustAnchorNum";

	public static final Event TRUST_ANCHOR_EVENT_EVENT = new Event("trustAnchorEvent",
			Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Uint32>() {},new TypeReference<Utf8String>() {},new TypeReference<Uint256>() {}));

	public static final String FUNC_VOTE_ELECT = "voteElect";

	public static final String FUNC_CANCEL_VOTE = "cancelVote";

	public RemoteFunctionCall<TransactionReceipt> registerTrustAnchor(Utf8String anchor,Uint64 anchortype,Utf8String anchorname,Utf8String company,Utf8String companyurl,Utf8String website,Utf8String documenturl,Utf8String email,Utf8String desc) {
		final Function function = new Function(
				FUNC_REGISTER_TRUST_ANCHOR,
				Arrays.<Type>asList(anchor,anchortype,anchorname,company,companyurl,website,documenturl,email,desc),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> unRegisterTrustAnchor(Utf8String anchor) {
		final Function function = new Function(
				FUNC_UN_REGISTER_TRUST_ANCHOR,
				Arrays.<Type>asList(anchor),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<Bool> isTrustAnchor(Utf8String address) {
		final Function function = new Function(
				FUNC_IS_TRUST_ANCHOR,
				Arrays.<Type>asList(address),
				Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<TransactionReceipt> updateBaseAnchorInfo(Utf8String anchor,Utf8String companyUrl,Utf8String email) {
		final Function function = new Function(
				FUNC_UPDATE_BASE_ANCHOR_INFO,
				Arrays.<Type>asList(anchor,companyUrl,email),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> updateExetendAnchorInfo(Utf8String anchor,Utf8String companyUrl,Utf8String website,Utf8String documentUrl,Utf8String email,Utf8String desc) {
		final Function function = new Function(
				FUNC_UPDATE_EXETEND_ANCHOR_INFO,
				Arrays.<Type>asList(anchor,companyUrl,website,documentUrl,email,desc),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> extractOwnBounty(Utf8String anchor) {
		final Function function = new Function(
				FUNC_EXTRACT_OWN_BOUNTY,
				Arrays.<Type>asList(anchor),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<List<Type>> queryTrustAnchor(Utf8String anchor) {
		final Function function = new Function(
				FUNC_QUERY_TRUST_ANCHOR,
				Arrays.<Type>asList(anchor),
				Arrays.<TypeReference<?>>asList(
						new TypeReference<Utf8String>() {},	// id
						new TypeReference<Utf8String>() {},	// name
						new TypeReference<Utf8String>() {},	// company
						new TypeReference<Utf8String>() {},	// companyUrl
						new TypeReference<Utf8String>() {}, // website
						new TypeReference<Utf8String>() {},	// serverUrl
						new TypeReference<Utf8String>() {},	// documentUrl
						new TypeReference<Utf8String>() {},	// email
						new TypeReference<Utf8String>() {},	// desc
						new TypeReference<Uint64>() {},		// trustAnchorType
						new TypeReference<Uint64>() {},		// status
						new TypeReference<Bool>() {},		// active
						new TypeReference<Uint64>() {},		// totalBounty
						new TypeReference<Uint64>() {},		// extractedBounty
						new TypeReference<Uint64>() {},		// lastExtractTime
						new TypeReference<Uint64>() {},		// voteCount
						new TypeReference<Uint64>() {},		// stake
						new TypeReference<Uint64>() {},		// createDate
						new TypeReference<Uint64>() {}		// certificateAccount
				)
		);
		return executeRemoteCallMultipleValueReturn(function);
	}

	public RemoteFunctionCall<Utf8String> queryVoter(Utf8String voter) {
		final Function function = new Function(
				FUNC_QUERY_VOTER,
				Arrays.<Type>asList(voter),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<Utf8String> checkSenderAddress(Utf8String address) {
		final Function function = new Function(
				FUNC_CHECK_SENDER_ADDRESS,
				Arrays.<Type>asList(address),
				Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<Uint8> queryTrustAnchorStatus(Utf8String anchor) {
		final Function function = new Function(
				FUNC_QUERY_TRUST_ANCHOR_STATUS,
				Arrays.<Type>asList(anchor),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<Utf8String> queryBaseTrustAnchorList() {
		final Function function = new Function(
				FUNC_QUERY_BASE_TRUST_ANCHOR_LIST,
				Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<Uint64> queryBaseTrustAnchorNum() {
		final Function function = new Function(
				FUNC_QUERY_BASE_TRUST_ANCHOR_NUM,
				Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<Utf8String> queryExpendTrustAnchorList() {
		final Function function = new Function(
				FUNC_QUERY_EXPEND_TRUST_ANCHOR_LIST,
				Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<Uint64> queryExpendTrustAnchorNum() {
		final Function function = new Function(
				FUNC_QUERY_EXPEND_TRUST_ANCHOR_NUM,
				Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public static class TrustAnchorEventEventResponse extends BaseEventResponse {
		public Utf8String methodName;
		public Uint32 status;
		public Utf8String reason;
		public Uint256 time;
	}

	public List<TrustAnchorEventEventResponse> getTrustAnchorEventEvents(TransactionReceipt transactionReceipt) {		List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRUST_ANCHOR_EVENT_EVENT, transactionReceipt);
		ArrayList<TrustAnchorEventEventResponse> responses = new ArrayList<>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			TrustAnchorEventEventResponse typedResponse = new TrustAnchorEventEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.methodName = (Utf8String) eventValues.getNonIndexedValues().get(0);
			typedResponse.status = (Uint32) eventValues.getNonIndexedValues().get(1);
			typedResponse.reason = (Utf8String) eventValues.getNonIndexedValues().get(2);
			typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(3);
			responses.add(typedResponse);
		}
		return responses;
	}

	public Flowable<TrustAnchorEventEventResponse> trustAnchorEventEventFlowable(CoreFilter filter) {
		return bifj.coreLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TrustAnchorEventEventResponse>() {
			@Override
			public TrustAnchorEventEventResponse apply(Log log) {
				Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRUST_ANCHOR_EVENT_EVENT, log);
				TrustAnchorEventEventResponse typedResponse = new TrustAnchorEventEventResponse();
				typedResponse.log = eventValues.getLog();
				typedResponse.methodName = (Utf8String) eventValues.getNonIndexedValues().get(0);
				typedResponse.status = (Uint32) eventValues.getNonIndexedValues().get(1);
				typedResponse.reason = (Utf8String) eventValues.getNonIndexedValues().get(2);
				typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(3);
				return typedResponse;
			}
		});
	}

	public Flowable<TrustAnchorEventEventResponse> trustAnchorEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		CoreFilter filter = new CoreFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(TRUST_ANCHOR_EVENT_EVENT));
		return trustAnchorEventEventFlowable(filter);
	}

	public RemoteFunctionCall<TransactionReceipt> voteElect(Utf8String candidate) {
		final Function function = new Function(
				FUNC_VOTE_ELECT,
				Arrays.<Type>asList(candidate),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> cancelVote(Utf8String candidate) {
		final Function function = new Function(
				FUNC_CANCEL_VOTE,
				Arrays.<Type>asList(candidate),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	protected Go_Trustanchor(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		super("", contractAddress, bifj, transactionManager, contractGasProvider);
	}

	public static Go_Trustanchor load(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		return new Go_Trustanchor(contractAddress, bifj, transactionManager, contractGasProvider);
	}

}