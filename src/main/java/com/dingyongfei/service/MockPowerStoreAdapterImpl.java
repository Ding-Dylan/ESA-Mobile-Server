/** Copyright © 2018-2019 Dell Inc. or its subsidiaries. All Rights Reserved. */
package com.dingyongfei.service;

import com.emc.pie.adapters.cyclone.rest.CycloneAdapter;
import com.emc.pie.adapters.cyclone.rest.RestConnection;
import com.emc.pie.adapters.cyclone.rest.RestConnectionImpl;
import com.emc.pie.adapters.cyclone.service.AlertService;
import com.emc.pie.adapters.cyclone.service.ApplianceService;
import com.emc.pie.adapters.cyclone.service.ClusterService;
import com.emc.pie.adapters.cyclone.service.EthPortService;
import com.emc.pie.adapters.cyclone.service.FcPortService;
import com.emc.pie.adapters.cyclone.service.HardwareService;
import com.emc.pie.adapters.cyclone.service.HostGroupService;
import com.emc.pie.adapters.cyclone.service.HostService;
import com.emc.pie.adapters.cyclone.service.InitiatorService;
import com.emc.pie.adapters.cyclone.service.LicenseService;
import com.emc.pie.adapters.cyclone.service.NodeService;
import com.emc.pie.adapters.cyclone.service.PolicyService;
import com.emc.pie.adapters.cyclone.service.RemoteSystemService;
import com.emc.pie.adapters.cyclone.service.ReplicationSessionService;
import com.emc.pie.adapters.cyclone.service.StorageContainerService;
import com.emc.pie.adapters.cyclone.service.VirtualVolumeService;
import com.emc.pie.adapters.cyclone.service.VolumeGroupService;
import com.emc.pie.adapters.cyclone.service.VolumeService;
import com.emc.pie.commons.adapters.PlatformAdapterCallback;
import com.emc.pie.commons.adapters.PlatformAdapterInfo;
import com.emc.pie.commons.adapters.PlatformAdapterStates;

/**
 * @author Dylan Ding
 */
public class MockPowerStoreAdapterImpl implements CycloneAdapter
{
    private RestConnection connection;

    protected MockPowerStoreAdapterImpl()
    {
        this.connection = new RestConnectionImpl("10.230.42.95", "admin", "Password123!", "443");
    }
    
    @Override
    public ApplianceService getApplianceService()
    {
        return new ApplianceService(this.connection);
    }

    @Override
    public VirtualVolumeService getVirtualVolumeService()
    {
        return new VirtualVolumeService(this.connection);
    }

    @Override
    public VolumeGroupService getVolumeGroupService()
    {
        return new VolumeGroupService(this.connection);
    }

    @Override
    public ClusterService getClusterService()
    {
        return new ClusterService(this.connection);
    }

    @Override
    public HostGroupService getHostGroupService()
    {
        return new HostGroupService(this.connection);
    }

    @Override
    public HostService getHostService()
    {
        return new HostService(this.connection);
    }

    @Override
    public FcPortService getFcPortService()
    {
        return new FcPortService(this.connection);
    }

    @Override
    public EthPortService getEthPortService()
    {
        return new EthPortService(this.connection);
    }

    @Override
    public InitiatorService getInitiatorService()
    {
        return new InitiatorService(this.connection);
    }

    @Override
    public LicenseService getLicenseService()
    {
        return new LicenseService(this.connection);
    }

    @Override
    public PolicyService getPolicyService()
    {
        return new PolicyService(this.connection);
    }

    @Override
    public RemoteSystemService getRemoteSystemService()
    {
        return new RemoteSystemService(this.connection);
    }

    @Override
    public ReplicationSessionService getReplicationSessionService()
    {
        return new ReplicationSessionService(this.connection);
    }

    @Override
    public VolumeService getVolumeService()
    {
        return new VolumeService(this.connection);
    }

    @Override
    public StorageContainerService getStorageContainerService()
    {
        return new StorageContainerService(this.connection);
    }

    @Override
    public NodeService getNodeService()
    {
        return new NodeService(this.connection);
    }

    @Override
    public HardwareService getHardwareService()
    {
        return new HardwareService(this.connection);
    }

    @Override
    public AlertService getAlertService()
    {
        return new AlertService(this.connection);
    }

    @Override
    public boolean close()
    {
        return true;
    }

    @Override
    public void close(PlatformAdapterCallback callback)
    {
        
    }

    @Override
    public PlatformAdapterInfo info()
    {
        return null;
    }

    @Override
    public boolean isOpen()
    {
        return true;
    }

    @Override
    public boolean open()
    {
        return true;
    }

    @Override
    public void open(PlatformAdapterCallback callback)
    {
        
    }

    @Override
    public PlatformAdapterStates state()
    {
        return null;
    }

    @Override
    public RestConnection getConnection()
    {
        return this.connection;
    }

    @Override
    public void setConnection(RestConnection connection)
    {
     this.connection = connection;   
    }

}
