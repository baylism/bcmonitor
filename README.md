## Extraction Strategy:


Aim: method of extracting data that can be applied consistently across all clients. 

Approach: don't depend on functionality available in specific clients. For example Bitcoin RPC doesn't provide a method to get a block at a particular height: the proper way of traversing the blockchain is using the 'nextblockhash' or 'previousblockhash' fields and the getblock method. 


Different stages

1. Extract block hashes, heights (performed once per block)
   - can be used as an index for further queries
2. Extract block data (performed once per block)
    - should be fast as requires a single rpc call 
3. Extract secondary data (performed once per datum type per block)
    - e.g. transactions from txids 
    - likely to be slower e.g. a call to getrawtransaction for each tx in txid attribute populated in stage 2.
4. Addition of tertiary attributes calculated from other analysis (ongoing)
    - e.g. miner classification


Create an interface for each stage. Describes the boundaries for data access/modification.

