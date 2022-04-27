package com.cjh.uidGenerator.config;

import com.cjh.uidGenerator.entity.WorkerNode;
import com.cjh.uidGenerator.mapper.WorkerNodeMapper;
import com.xfvape.uid.utils.DockerUtils;
import com.xfvape.uid.utils.NetUtils;
import com.xfvape.uid.worker.WorkerIdAssigner;
import com.xfvape.uid.worker.WorkerNodeType;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Worker ID 分配器，用于为每个工作机器分配一个唯一的 ID，目前来说是用完即弃，在初始化 Bean 的时候会自动向 MySQL 中插入一条关于
 * 该服务的启动信息，待 MySQL 返回其自增 ID 之后，使用该 ID 作为工作机器 ID 并柔和到 UID 的生成当中。
 */
public class DisposableWorkerIdAssigner implements WorkerIdAssigner {

    @Resource
    private WorkerNodeMapper workerNodeMapper;
    @Override
    @Transactional
    public long assignWorkerId() {
        WorkerNode workerNode = buildWorkerNode();

        workerNodeMapper.addWorkerNode(workerNode);

        return workerNode.getId();
    }

    private WorkerNode buildWorkerNode() {
        WorkerNode workNode = new WorkerNode();
        if (DockerUtils.isDocker()) {
            workNode.setType(WorkerNodeType.CONTAINER.value());
            workNode.setHostName(DockerUtils.getDockerHost());
            workNode.setPort(DockerUtils.getDockerPort());
            workNode.setLaunchDate(new Date());
        } else {
            workNode.setType(WorkerNodeType.ACTUAL.value());
            workNode.setHostName(NetUtils.getLocalAddress());
            workNode.setPort(System.currentTimeMillis() + "-" + RandomUtils.nextInt(100000));
            workNode.setLaunchDate(new Date());
        }

        return workNode;
    }
}
