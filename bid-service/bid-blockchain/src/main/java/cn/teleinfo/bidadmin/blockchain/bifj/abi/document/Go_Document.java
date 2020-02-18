package cn.teleinfo.bidadmin.blockchain.bifj.abi.document;

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

public class Go_Document extends Contract {

	public static final String FUNC__INITIALIZATION_D_D_O = "InitializationDDO";

	public static final String FUNC__SET_BID_NAME = "SetBidName";

	public static final String FUNC__FIND_D_D_O_BY_TYPE = "FindDDOByType";

	public static final String FUNC__ADD_PUBLIC_KEY = "AddPublicKey";

	public static final String FUNC__DELETE_PUBLIC_KEY = "DeletePublicKey";

	public static final String FUNC__ADD_PROOF = "AddProof";

	public static final String FUNC__DELETE_PROOF = "DeleteProof";

	public static final String FUNC__ADD_ATTR = "AddAttr";

	public static final String FUNC__DELETE_ATTR = "DeleteAttr";

	public static final String FUNC__ENABLE = "Enable";

	public static final String FUNC__DISABLE = "Disable";

	public static final String FUNC__IS_ENABLE = "IsEnable";

	public static final Event BID_EVENT_EVENT = new Event("bidEvent",
			Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Uint32>() {},new TypeReference<Utf8String>() {},new TypeReference<Uint256>() {}));

	public RemoteFunctionCall<TransactionReceipt> InitializationDDO(Uint64 bidType) {
		final Function function = new Function(
				FUNC__INITIALIZATION_D_D_O,
				Arrays.<Type>asList(bidType),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> SetBidName(Utf8String bidName) {
		final Function function = new Function(
				FUNC__SET_BID_NAME,
				Arrays.<Type>asList(bidName),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<Utf8String> FindDDOByType(Uint64 key,Utf8String value) {
		final Function function = new Function(
				FUNC__FIND_D_D_O_BY_TYPE,
				Arrays.<Type>asList(key,value),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<TransactionReceipt> AddPublicKey(Utf8String type,Utf8String authority,Utf8String publicKey) {
		final Function function = new Function(
				FUNC__ADD_PUBLIC_KEY,
				Arrays.<Type>asList(type,authority,publicKey),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> DeletePublicKey(Utf8String publicKey) {
		final Function function = new Function(
				FUNC__DELETE_PUBLIC_KEY,
				Arrays.<Type>asList(publicKey),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> AddProof(Utf8String type,Utf8String proofID) {
		final Function function = new Function(
				FUNC__ADD_PROOF,
				Arrays.<Type>asList(type,proofID),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> DeleteProof(Utf8String proofID) {
		final Function function = new Function(
				FUNC__DELETE_PROOF,
				Arrays.<Type>asList(proofID),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> AddAttr(Utf8String type,Utf8String value) {
		final Function function = new Function(
				FUNC__ADD_ATTR,
				Arrays.<Type>asList(type,value),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> DeleteAttr(Utf8String type) {
		final Function function = new Function(
				FUNC__DELETE_ATTR,
				Arrays.<Type>asList(type),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> Enable() {
		final Function function = new Function(
				FUNC__ENABLE,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> Disable() {
		final Function function = new Function(
				FUNC__DISABLE,
				Arrays.<Type>asList(),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<Bool> IsEnable(Uint64 key,Utf8String value) {
		final Function function = new Function(
				FUNC__IS_ENABLE,
				Arrays.<Type>asList(key,value),
				Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public static class BidEventEventResponse extends BaseEventResponse {
		public Utf8String methodName;
		public Uint32 status;
		public Utf8String reason;
		public Uint256 time;
	}

	public List<BidEventEventResponse> getBidEventEvents(TransactionReceipt transactionReceipt) {		List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BID_EVENT_EVENT, transactionReceipt);
		ArrayList<BidEventEventResponse> responses = new ArrayList<>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			BidEventEventResponse typedResponse = new BidEventEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.methodName = (Utf8String) eventValues.getNonIndexedValues().get(0);
			typedResponse.status = (Uint32) eventValues.getNonIndexedValues().get(1);
			typedResponse.reason = (Utf8String) eventValues.getNonIndexedValues().get(2);
			typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(3);
			responses.add(typedResponse);
		}
		return responses;
	}

	public Flowable<BidEventEventResponse> bidEventEventFlowable(CoreFilter filter) {
		return bifj.coreLogFlowable(filter).map(new io.reactivex.functions.Function<Log, BidEventEventResponse>() {
			@Override
			public BidEventEventResponse apply(Log log) {
				Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BID_EVENT_EVENT, log);
				BidEventEventResponse typedResponse = new BidEventEventResponse();
				typedResponse.log = eventValues.getLog();
				typedResponse.methodName = (Utf8String) eventValues.getNonIndexedValues().get(0);
				typedResponse.status = (Uint32) eventValues.getNonIndexedValues().get(1);
				typedResponse.reason = (Utf8String) eventValues.getNonIndexedValues().get(2);
				typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(3);
				return typedResponse;
			}
		});
	}

	public Flowable<BidEventEventResponse> bidEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		CoreFilter filter = new CoreFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(BID_EVENT_EVENT));
		return bidEventEventFlowable(filter);
	}

	protected Go_Document(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		super("", contractAddress, bifj, transactionManager, contractGasProvider);
	}

	public static Go_Document load(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		return new Go_Document(contractAddress, bifj, transactionManager, contractGasProvider);
	}

}