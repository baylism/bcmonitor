package com.bcam.bcmonitor.extractor.client;

import org.springframework.stereotype.Component;

/**
 *
 * https://github.com/zcash/zcash/blob/v1.1.1/doc/payment-api.md
 *
 * better: https://github.com/zcash/zcash/blob/master/src/rpcblockchain.cpp
 * https://github.com/zcash/zcash/blob/master/src/rpcrawtransaction.cpp
 *
 * the account parameter exists in the API, please use “” as its value, otherwise an error will be returned
 */
@Component
public class ReactiveZCashClient extends ReactiveBitcoinClient {

}
