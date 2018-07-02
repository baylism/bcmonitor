package com.bcam.bcmonitor.model;


import java.util.ArrayList;

/**
 * Two types of address: shielded and transaparent
 *
 * Four types of transaction:
 * 1. Shielding transaction (t-addr to z-addr): amount and sender public but recipient not
 * 2. De-shielding transaction (z-addr to t-addr): amount and recipient public
 * 3. Private transaction (z-addr to z-addr): both parties and amount private
 * 4. Public transaction: (t-addr to t-addr): all public
 *
 *
 *
 * Implications:
 *      - Shielded transactions can contain any number of shielded transactions. No accurate throughput.
 *      - But can track the total number of coins in the sheilded pool vs in public pools by summing the vpubs in each block.
 *      - Transactions can't be linked to the private notes they spend.
 *      - Different transfers can be made to the same shielded payment address without it being clear on the blockchain that the recipient is the same. Only way is to compare the two addresses off-chain (in which case it is trivially easy).
 *
 *
 */
public class ZCashTransaction extends BitcoinTransaction {
    private long fee; // zcash transactions contain fee field
    private Boolean sheilded;
    private String inputTreestate;
    private String outputTreestate;
    private ArrayList<JoinSplit> joinSplitDescriptions;

    private class JoinSplit {
        private float vpubNew; // value added to transparent pool by this joinsplit
        private float vpubOld; // value added to shielded pool by this joinsplit
    }

    @Override
    public float calculateFee() {
        return super.calculateFee();
    }
}
