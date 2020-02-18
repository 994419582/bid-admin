package cn.teleinfo.bidadmin.blockchain.bifj.abi.certificate;

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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Go_Certificate extends Contract {

	public static final String FUNC_REGISTER_CERTIFICATE = "registerCertificate";

	public static final String FUNC_REVOCED_CERTIFICATE = "revocedCertificate";

	public static final String FUNC_QUERY_PERIOD = "queryPeriod";

	public static final String FUNC_QUERY_ACTIVE = "queryActive";

	public static final String FUNC_QUERY_ISSUER = "queryIssuer";

	public static final String FUNC_QUERY_ISSUER_SIGNATURE = "queryIssuerSignature";

	public static final String FUNC_QUERY_SUBJECT_SIGNATURE = "querySubjectSignature";

	public static final Event CERD_EVENT_EVENT = new Event("cerdEvent",
			Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Uint32>() {},new TypeReference<Utf8String>() {},new TypeReference<Uint256>() {}));

	public RemoteFunctionCall<TransactionReceipt> registerCertificate(Utf8String Id,Utf8String Context,Utf8String Subject,Uint64 Period,Utf8String IssuerAlgorithm,Utf8String IssuerSignature,Utf8String SubjectPublicKey,Utf8String SubjectAlgorithm,Utf8String SubjectSignature) {
		final Function function = new Function(
				FUNC_REGISTER_CERTIFICATE,
				Arrays.<Type>asList(Id,Context,Subject,Period,IssuerAlgorithm,IssuerSignature,SubjectPublicKey,SubjectAlgorithm,SubjectSignature),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<TransactionReceipt> revocedCertificate(Utf8String id) {
		final Function function = new Function(
				FUNC_REVOCED_CERTIFICATE,
				Arrays.<Type>asList(id),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteFunctionCall<Uint64> queryPeriod(Utf8String id) {
		final Function function = new Function(
				FUNC_QUERY_PERIOD,
				Arrays.<Type>asList(id),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<Bool> queryActive(Utf8String id) {
		final Function function = new Function(
				FUNC_QUERY_ACTIVE,
				Arrays.<Type>asList(id),
				Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<Utf8String> queryIssuer(Utf8String id) {
		final Function function = new Function(
				FUNC_QUERY_ISSUER,
				Arrays.<Type>asList(id),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
		return executeRemoteCallSingleValueReturn(function);
	}

	public RemoteFunctionCall<List<Type>> queryIssuerSignature(Utf8String id) {
		final Function function = new Function(
				FUNC_QUERY_ISSUER_SIGNATURE,
				Arrays.<Type>asList(id),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Utf8String>() {},new TypeReference<Utf8String>() {},new TypeReference<Utf8String>() {}));
		return executeRemoteCallMultipleValueReturn(function);
	}

	public RemoteFunctionCall<List<Type>> querySubjectSignature(Utf8String id) {
		final Function function = new Function(
				FUNC_QUERY_SUBJECT_SIGNATURE,
				Arrays.<Type>asList(id),
				Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {},new TypeReference<Utf8String>() {},new TypeReference<Utf8String>() {},new TypeReference<Utf8String>() {}));
		return executeRemoteCallMultipleValueReturn(function);
	}

	public static class CerdEventEventResponse extends BaseEventResponse {
		public Utf8String methodName;
		public Uint32 status;
		public Utf8String reason;
		public Uint256 time;
	}

	public List<CerdEventEventResponse> getCerdEventEvents(TransactionReceipt transactionReceipt) {		List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CERD_EVENT_EVENT, transactionReceipt);
		ArrayList<CerdEventEventResponse> responses = new ArrayList<>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			CerdEventEventResponse typedResponse = new CerdEventEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse.methodName = (Utf8String) eventValues.getNonIndexedValues().get(0);
			typedResponse.status = (Uint32) eventValues.getNonIndexedValues().get(1);
			typedResponse.reason = (Utf8String) eventValues.getNonIndexedValues().get(2);
			typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(3);
			responses.add(typedResponse);
		}
		return responses;
	}

	public Flowable<CerdEventEventResponse> cerdEventEventFlowable(CoreFilter filter) {
		return bifj.coreLogFlowable(filter).map(new io.reactivex.functions.Function<Log, CerdEventEventResponse>() {
			@Override
			public CerdEventEventResponse apply(Log log) {
				Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CERD_EVENT_EVENT, log);
				CerdEventEventResponse typedResponse = new CerdEventEventResponse();
				typedResponse.log = eventValues.getLog();
				typedResponse.methodName = (Utf8String) eventValues.getNonIndexedValues().get(0);
				typedResponse.status = (Uint32) eventValues.getNonIndexedValues().get(1);
				typedResponse.reason = (Utf8String) eventValues.getNonIndexedValues().get(2);
				typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(3);
				return typedResponse;
			}
		});
	}

	public Flowable<CerdEventEventResponse> cerdEventEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		CoreFilter filter = new CoreFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(CERD_EVENT_EVENT));
		return cerdEventEventFlowable(filter);
	}

	protected Go_Certificate(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		super("", contractAddress, bifj, transactionManager, contractGasProvider);
	}

	public static Go_Certificate load(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
		return new Go_Certificate(contractAddress, bifj, transactionManager, contractGasProvider);
	}

}