package com.bcam.bcmonitor.model;

public class TransactionPoolInfo {

    private long size;
    private long sizeBytes;
    private long memoryUsage;
    private float minFeePerKB;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public long getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(long memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public float getMinFeePerKB() {
        return minFeePerKB;
    }

    public void setMinFeePerKB(float minFeePerKB) {
        this.minFeePerKB = minFeePerKB;
    }
}
