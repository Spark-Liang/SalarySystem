package com.lzh.salarysystem.infrastructure.persistent.repository;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.lzh.salarysystem.infrastructure.persistent.po.VersionDataPO;
import com.lzh.salarysystem.test.envirnoment.DBUnitTestEnv;

@Component
public class VersionDataPORepositoryTest extends DBUnitTestEnv{
	@Autowired
	private VersionDataPORepository versionDataPORepository;
	
	@Autowired
	private EntityManager em;
	
	@Transactional
	@Test
	public void testGetAfterMultiSave() {
		Long id = 1L;
		VersionDataPO po = versionDataPORepository.findOne(1L);
		System.err.println(po);
		po.setData(1L);
		versionDataPORepository.save(po);
		System.err.println(po);
		
		po.setData(2L);
		versionDataPORepository.save(po);
		System.err.println(po);
		
		VersionDataPO poInDB = versionDataPORepository.findOne(id);
		assertEquals(po, poInDB);
	}
	
	@Transactional
	@Test
	public void testMultiThreadSave() throws Exception{
		Long id = 1L;
		VersionDataPO po = versionDataPORepository.findOne(1L);
		System.err.println(po);
		po.setData(1L);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		Runnable task = () -> {
			updateInThread(countDownLatch);
		};
		new Thread(task).start();
		countDownLatch.await();
		versionDataPORepository.saveAndFlush(po);
		System.err.println(po);
		
		po.setData(2L);
		versionDataPORepository.saveAndFlush(po);
		System.err.println(po);
		
		VersionDataPO poInDB = versionDataPORepository.findOne(id);
		assertEquals(po, poInDB);
	}
	
	@Transactional
	private void updateInThread(CountDownLatch countDownLatch) {
		long threadId = Thread.currentThread().getId();
		VersionDataPO po2 = versionDataPORepository.findOne(1L);
		System.err.println("Thread_" + threadId + " : " + po2);
		countDownLatch.countDown();
		po2.setData(threadId);
		versionDataPORepository.saveAndFlush(po2);
		System.err.println("Thread_" + threadId + " : " + po2);
	}
	
	public static final int MAX_TRY_NUM = 10;
	
	@Transactional
	private int updateData(Long id,Long data) {
		int try_num = 1;
		boolean success = false;
		while(success == false && try_num < MAX_TRY_NUM) {
			VersionDataPO versionDataPO = versionDataPORepository.findOne(id);
			try {
				Thread.sleep(new Random().nextInt(20));
			} catch (InterruptedException e) {}
			versionDataPO.setData(data);
			try {
				//versionDataPORepository.flush();
				versionDataPORepository.save(versionDataPO);
				success = true;
			} catch (OptimisticLockException e) {}
			try_num ++;
		}
		if(success == false) {
			VersionDataPO versionDataPO_ForLock = em.find(VersionDataPO.class, id, LockModeType.PESSIMISTIC_WRITE);
			try {
				Thread.sleep(new Random().nextInt(20));
			} catch (InterruptedException e) {}
			versionDataPO_ForLock.setData(data);
			em.refresh(versionDataPO_ForLock);
		}
		return try_num;
	}
	
	@Test
	public void testMultiUpdate() throws Exception{
		Map<Object, Object> resultMap = new ConcurrentHashMap<>();
		Long id = 5L;
		int taskNum = 1,currentTask = 0;
		CountDownLatch countDownLatch = new CountDownLatch(taskNum);
		Runnable task = ()->{
			int result = updateData(id, new Random().nextLong());
			resultMap.put(Thread.currentThread().getId(), result);
			countDownLatch.countDown();
		};
		while(currentTask < taskNum) {
			new Thread(task).start();
			currentTask ++;
		}
		countDownLatch.await();
		System.err.println(resultMap);
	}
}
