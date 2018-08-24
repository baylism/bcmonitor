#!/usr/bin/env bash

# ========= Authentication =========

ZCASH_HOSTNAME=zcash-0
ZCASH_PORT=9998
ZCASH_UN=
ZCASH_PW=

DASH_HOSTNAME=dash-0
DASH_PORT=9998
DASH_UN=
DASH_PW=

BITCOIN_HOSTNAME=bitcoin-0
BITCOIN_PORT=9998
BITCOIN_UN=
BITCOIN_PW=

# ========= Params =========
#params:
#getblock
#getrawtransaction
#
#getblockhash
#
#no params:
#getrawmempool
#getmempoolinfo
#getblockchaininfo
#getbestblockhash


echo ========= Running zcash calls =========

echo ----- Parameterised calls -----

echo Get block
curl -w "\n\n" --user ${ZCASH_UN}:${ZCASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblock", "params": ["0007bc227e1c57a4a70e237cad00e7b7ce565155ab49166bc57397a26d339283", true] }' -H 'content-type: text/plain;' http://${ZCASH_HOSTNAME}:${ZCASH_PORT}

echo Get transaction
curl -w "\n\n" --user ${ZCASH_UN}:${ZCASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getrawtransaction", "params": ["851bf6fbf7a976327817c738c489d7fa657752445430922d94c983c0b9ed4609", 1] }' -H 'content-type: text/plain;' http://${ZCASH_HOSTNAME}:${ZCASH_PORT}


echo ----- Other calls -----

echo Get block hash
curl -w "\n\n" --user ${ZCASH_UN}:${ZCASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockhash", "params": [50000] }' http://${ZCASH_HOSTNAME}:${ZCASH_PORT}

echo Get raw mempool
curl -w "\n\n" --user ${ZCASH_UN}:${ZCASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getrawmempool", "params": [] }' -H 'content-type: text/plain;' http://${ZCASH_HOSTNAME}:${ZCASH_PORT}

echo Get mempool info
curl -w "\n\n" --user ${ZCASH_UN}:${ZCASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getmempoolinfo", "params": [] }' -H 'content-type: text/plain;' http://${ZCASH_HOSTNAME}:${ZCASH_PORT}

echo Get blockchain info
curl -w "\n\n" --user ${ZCASH_UN}:${ZCASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockchaininfo", "params": [] }' -H 'content-type: text/plain;' http://${ZCASH_HOSTNAME}:${ZCASH_PORT}

echo Get best block hash
curl -w "\n\n" --user ${ZCASH_UN}:${ZCASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getbestblockhash", "params": [] }' -H 'content-type: text/plain;' http://${ZCASH_HOSTNAME}:${ZCASH_PORT}


echo ========= Running dash calls =========

echo ----- Parameterised calls -----

echo Get block
curl -w "\n\n" --user ${DASH_UN}:${DASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblock", "params": ["000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582", true] }' -H 'content-type: text/plain;' http://${DASH_HOSTNAME}:${DASH_PORT}

echo Get transaction
curl -w "\n\n" --user ${DASH_UN}:${DASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getrawtransaction", "params": ["d903e90e5745ca48a88c84d6f2e0431d49a27cfede8e6171c1df7ed6aa7747ed", true] }' -H 'content-type: text/plain;' http://${DASH_HOSTNAME}:${DASH_PORT}


echo ----- Other calls -----

echo Get block hash
curl -w "\n\n" --user ${DASH_UN}:${DASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockhash", "params": [50000] }' http://${DASH_HOSTNAME}:${DASH_PORT}

echo Get raw mempool
curl -w "\n\n" --user ${DASH_UN}:${DASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getrawmempool", "params": [] }' -H 'content-type: text/plain;' http://${DASH_HOSTNAME}:${DASH_PORT}

echo Get mempool info
curl -w "\n\n" --user ${DASH_UN}:${DASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getmempoolinfo", "params": [] }' -H 'content-type: text/plain;' http://${DASH_HOSTNAME}:${DASH_PORT}

echo Get blockchain info
curl -w "\n\n" --user ${DASH_UN}:${DASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockchaininfo", "params": [] }' -H 'content-type: text/plain;' http://${DASH_HOSTNAME}:${DASH_PORT}

echo Get best block hash
curl -w "\n\n" --user ${DASH_UN}:${DASH_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getbestblockhash", "params": [] }' -H 'content-type: text/plain;' http://${DASH_HOSTNAME}:${DASH_PORT}



echo ========= Running bitcoin calls =========

echo ----- Parameterised calls -----

echo Get block
curl -w "\n\n" --user ${BITCOIN_UN}:${BITCOIN_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblock", "params": ["0000000000000000001728816d0abe995e0fa2821a4308f85b523919308c8b4b", true] }' -H 'content-type: text/plain;' http://${BITCOIN_HOSTNAME}:${BITCOIN_PORT}

echo Get transaction
curl -w "\n\n" --user ${BITCOIN_UN}:${BITCOIN_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getrawtransaction", "params": ["bde34bf84e4021ec087b881f95b6b1d699b49604ecb9b738fe9bd1dd672560b9", true] }' -H 'content-type: text/plain;' http://${BITCOIN_HOSTNAME}:${BITCOIN_PORT}


echo Other calls:

echo Get block hash
curl -w "\n\n" --user ${BITCOIN_UN}:${BITCOIN_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockhash", "params": [50000] }' http://${BITCOIN_HOSTNAME}:${BITCOIN_PORT}

echo Get raw mempool
curl -s --user ${BITCOIN_UN}:${BITCOIN_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getrawmempool", "params": [] }' -H 'content-type: text/plain;' http://${BITCOIN_HOSTNAME}:${BITCOIN_PORT} -o bcmempool

head -c 500 bcmempool
echo ...
tail -c 500 bcmempool

echo; echo;

echo Get mempool info
curl -w "\n\n" --user ${BITCOIN_UN}:${BITCOIN_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getmempoolinfo", "params": [] }' -H 'content-type: text/plain;' http://${BITCOIN_HOSTNAME}:${BITCOIN_PORT}

echo Get blockchain info
curl -w "\n\n" --user ${BITCOIN_UN}:${BITCOIN_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockchaininfo", "params": [] }' -H 'content-type: text/plain;' http://${BITCOIN_HOSTNAME}:${BITCOIN_PORT}

echo Get best block hash
curl -w "\n\n" --user ${BITCOIN_UN}:${BITCOIN_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getbestblockhash", "params": [] }' -H 'content-type: text/plain;' http://${BITCOIN_HOSTNAME}:${BITCOIN_PORT}


#echo ========= Running monero calls =========
#
#echo ----- Parameterised calls -----
#
#echo Get block
#curl -w "\n\n" --user ${MONERO_UN}:${MONERO_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblock", "params": ["000000000000003941fb8b64f23b1dc0391892c87dd8054a1f262b70203b2582", true] }' -H 'content-type: text/plain;' http://${MONERO_HOSTNAME}:${MONERO_PORT}
#
#echo Get transaction
#curl -w "\n\n" --user ${MONERO_UN}:${MONERO_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getrawtransaction", "params": ["d903e90e5745ca48a88c84d6f2e0431d49a27cfede8e6171c1df7ed6aa7747ed", true] }' -H 'content-type: text/plain;' http://${MONERO_HOSTNAME}:${MONERO_PORT}
#
#
#echo ----- Other calls -----
#
#echo Get block hash
#curl -w "\n\n" --user ${MONERO_UN}:${MONERO_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockhash", "params": [50000] }' http://${MONERO_HOSTNAME}:${MONERO_PORT}
#
#echo Get raw mempool
#curl -w "\n\n" --user ${MONERO_UN}:${MONERO_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getrawmempool", "params": [] }' -H 'content-type: text/plain;' http://${MONERO_HOSTNAME}:${MONERO_PORT}
#
#echo Get mempool info
#curl -w "\n\n" --user ${MONERO_UN}:${MONERO_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getmempoolinfo", "params": [] }' -H 'content-type: text/plain;' http://${MONERO_HOSTNAME}:${MONERO_PORT}
#
#echo Get blockchain info
#curl -w "\n\n" --user ${MONERO_UN}:${MONERO_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getblockchaininfo", "params": [] }' -H 'content-type: text/plain;' http://${MONERO_HOSTNAME}:${MONERO_PORT}
#
#echo Get best block hash
#curl -w "\n\n" --user ${MONERO_UN}:${MONERO_PW} --data-binary '{"jsonrpc": "1.0", "id":"curltest", "method": "getbestblockhash", "params": [] }' -H 'content-type: text/plain;' http://${MONERO_HOSTNAME}:${MONERO_PORT}