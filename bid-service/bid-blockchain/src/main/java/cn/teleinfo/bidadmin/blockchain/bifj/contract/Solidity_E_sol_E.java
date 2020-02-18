package cn.teleinfo.bidadmin.blockchain.bifj.contract;

import io.reactivex.Flowable;
import org.bifj.abi.EventEncoder;
import org.bifj.abi.TypeReference;
import org.bifj.abi.datatypes.Bool;
import org.bifj.abi.datatypes.Event;
import org.bifj.abi.datatypes.Function;
import org.bifj.abi.datatypes.Type;
import org.bifj.abi.datatypes.generated.Uint256;
import org.bifj.protocol.Bifj;
import org.bifj.protocol.core.DefaultBlockParameter;
import org.bifj.protocol.core.RemoteCall;
import org.bifj.protocol.core.RemoteFunctionCall;
import org.bifj.protocol.core.methods.request.CoreFilter;
import org.bifj.protocol.core.methods.response.BaseEventResponse;
import org.bifj.protocol.core.methods.response.Log;
import org.bifj.protocol.core.methods.response.TransactionReceipt;
import org.bifj.tx.Contract;
import org.bifj.tx.TransactionManager;
import org.bifj.tx.gas.ContractGasProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.bifj.io/command_line.html">bifj command line tools</a>,
 * or the org.bifj.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/bifj/bifj/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with bifj version none.
 */
public class Solidity_E_sol_E extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610100806100206000396000f30060806040526004361060485763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663967e6e658114604d578063d5dcf127146071575b600080fd5b348015605857600080fd5b50605f6088565b60408051918252519081900360200190f35b348015607c57600080fd5b506086600435608e565b005b60005490565b60408051600181526020810183905260008183015290517fa39e64cec782adadf8a62f2917e765c608c2d05c9f0d0414fb7231c55e05d3709181900360600190a16000555600a165627a7a72305820d03d270ea215129735b29f33570e314fa99ef6d96d477bd62f322fea0142adf40029";

    public static final String FUNC_GETAGE = "getAge";

    public static final String FUNC_SETAGE = "setAge";

    public static final Event EVENTAGE_EVENT = new Event("eventAge", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    protected Solidity_E_sol_E(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, bifj, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Uint256> getAge() {
        final Function function = new Function(FUNC_GETAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setAge(Uint256 _age) {
        final Function function = new Function(
                FUNC_SETAGE, 
                Arrays.<Type>asList(_age), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<EventAgeEventResponse> getEventAgeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EVENTAGE_EVENT, transactionReceipt);
        ArrayList<EventAgeEventResponse> responses = new ArrayList<EventAgeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            EventAgeEventResponse typedResponse = new EventAgeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.a = (Bool) eventValues.getNonIndexedValues().get(0);
            typedResponse._age = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.b = (Bool) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<EventAgeEventResponse> eventAgeEventFlowable(CoreFilter filter) {
        return bifj.coreLogFlowable(filter).map(new io.reactivex.functions.Function<Log, EventAgeEventResponse>() {
            @Override
            public EventAgeEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(EVENTAGE_EVENT, log);
                EventAgeEventResponse typedResponse = new EventAgeEventResponse();
                typedResponse.log = log;
                typedResponse.a = (Bool) eventValues.getNonIndexedValues().get(0);
                typedResponse._age = (Uint256) eventValues.getNonIndexedValues().get(1);
                typedResponse.b = (Bool) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Flowable<EventAgeEventResponse> eventAgeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        CoreFilter filter = new CoreFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EVENTAGE_EVENT));
        return eventAgeEventFlowable(filter);
    }

    public static Solidity_E_sol_E load(String contractAddress, Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Solidity_E_sol_E(contractAddress, bifj, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Solidity_E_sol_E> deploy(Bifj bifj, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Solidity_E_sol_E.class, bifj, transactionManager, contractGasProvider, BINARY, "");
    }

    public static class EventAgeEventResponse extends BaseEventResponse {
        public Bool a;

        public Uint256 _age;

        public Bool b;
    }
}
