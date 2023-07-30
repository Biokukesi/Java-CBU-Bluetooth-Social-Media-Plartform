package com.social.Networking;

import javax.bluetooth.*;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;

public class BluetoothManager {

    private LocalDevice localDevice;
    private Vector<RemoteDevice> devicesDiscovered;
    private boolean discoveryInProgress = false;

    public BluetoothManager() throws BluetoothStateException {
        localDevice = LocalDevice.getLocalDevice();
        devicesDiscovered = new Vector<>();
    }

    public synchronized CompletableFuture<Vector<RemoteDevice>> discoverDevices() { 
        CompletableFuture<Vector<RemoteDevice>> futureDevices = new CompletableFuture<>();

        if (discoveryInProgress) {
            System.out.println("Inquiry already in progress. Please wait...");
            futureDevices.completeExceptionally(new IllegalStateException("Inquiry already in progress."));
        } else {
            devicesDiscovered.clear();
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();
            discoveryInProgress = true;

            try {
                agent.startInquiry(DiscoveryAgent.GIAC, new MyDiscoveryListener(futureDevices));
            } catch (BluetoothStateException e) {
                discoveryInProgress = false;
                futureDevices.completeExceptionally(e);
            }
        }
        return futureDevices;
    }

    private class MyDiscoveryListener implements DiscoveryListener {
        private final CompletableFuture<Vector<RemoteDevice>> futureDevices;

        public MyDiscoveryListener(CompletableFuture<Vector<RemoteDevice>> futureDevices) {
            this.futureDevices = futureDevices;
        }

        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
            devicesDiscovered.add(btDevice);

            try {
                System.out.println("     name " + btDevice.getFriendlyName(false));
            } catch (IOException cantGetDeviceName) {
                System.err.println("Error getting device name for: " + btDevice.getBluetoothAddress());
                cantGetDeviceName.printStackTrace();
            }
        }

        public synchronized void inquiryCompleted(int discType) {
            discoveryInProgress = false;
            System.out.println("Device Inquiry completed!");
            futureDevices.complete(devicesDiscovered);
        }

        public void serviceSearchCompleted(int transID, int respCode) {}

        public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {}
    }
}
