package com.cjh.uidGenerator.service.impl;

import com.cjh.uidGenerator.service.IWorkerNodeService;
import com.xfvape.uid.UidGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkerNodeServiceImpl implements IWorkerNodeService {
    @Resource
    private UidGenerator uidGenerator;

    @Override
    public long genUid() {
        //Long uu_id = UidGeneratorComponent.uu_id;
        return uidGenerator.getUID();
    }
}
