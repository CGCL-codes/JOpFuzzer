
package com.top.hfc.cmts.handlers;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.top.hfc.cmts.dao.CmRecordDao;
import com.top.hfc.cmts.dao.factory.CmRecordDaoFactory;
import com.top.hfc.cmts.entity.cmInfoEntity;
import com.top.hfc.cmts.factory.PlatOidFactory;
import com.top.hfc.cmts.global.ConfigFileKey;
import com.top.hfc.cmts.utils.CollectFreXmlHelper;
import com.top.hfc.cmts.utils.StringHelper;
import com.top.hfc.cmts.utils.threadUtil;

public class Cycle60CmInfoHandler  implements Runnable {
	private static final Log logger = LogFactory.getLog(Cycle60CmInfoHandler.class.getName());
	private int cycle=30;
	private long startTime;
	private long endTime;
    private CmRecordDao cmtsInfo = CmRecordDaoFactory.getCmInfoInstance();
	 List<cmInfoEntity> info=null;
	 List<String> cmdomaininfo=null;
	@SuppressWarnings("unchecked")
	public void run() {
		// TODO Auto-generated method stub
		while (!Thread.currentThread().isInterrupted()) {
			try {
			 startTime= System.currentTimeMillis();
			//logger.info("CM info running.");
			  info=(List<cmInfoEntity>) cmtsInfo.cmtscmInfo();
			  cmdomaininfo=(List<String>) cmtsInfo.cmtsMacDomainInfo1();
			 
			String cycleString=CollectFreXmlHelper.selectXML(ConfigFileKey.COLLECT_CONFIG, ConfigFileKey.CYCLE_CM);
			cycle=Integer.parseInt(cycleString);
			if(StringHelper.isNumber(cycleString))
				cycle = Integer.parseInt(cycleString);
				int dataNum=info.size();
				int threadNum=threadUtil.getThreadNum(cycle, dataNum,ConfigFileKey.THREAD_NUM_CMALLINFO,4);
			 logger.info("CM info running."+cycle+"."+dataNum+"."+threadNum);
			if(dataNum>0){
				 ExecutorService ModuleOptPool = Executors.newFixedThreadPool(threadNum);
			 		String community;
		    		String cmip;
		    		String cmmac;
		    		int cmcid;
		    		int cmupid;
		    		int index;
		    		String cmtsip;
		    		String cmtsType;
				 for (cmInfoEntity cmmacInfo : info) {
					  community=cmmacInfo.getCommunity();
					  cmip=cmmacInfo.getCmIp();
					  cmmac=cmmacInfo.getCmMac();
					  cmcid=cmmacInfo.getCmtsId();
					  cmupid=cmmacInfo.getUpid();
					  index=cmmacInfo.getCmIndex();
			    	  cmtsType=cmmacInfo.getCmtsType();
			    	  cmtsip=cmmacInfo.getCmtsIp();
			    		   if(!"0.0.0.0".equals(cmip)&&cmdomaininfo!=null&&!cmdomaininfo.contains(cmmac)){
			    			   if(PlatOidFactory.CMTSTYPE_CC8800.equals(cmtsType)){
			    				   ModuleOptPool.execute(new CmInfoThread5(cmcid,cmupid,cmtsip,community,cmmac,index,cmtsType));
			    			   }else{
			    				   ModuleOptPool.execute(new CmInfoThread2(cmcid,cmupid,cmip,community,cmmac));    
			    			   }
			     			   ModuleOptPool.execute(new CmInfoThread4(cmcid,cmupid,cmtsip,community,cmmac,index,cmtsType));
						   }
						ModuleOptPool.execute(new CmInfoThread3(cmcid,cmupid,cmtsip,community,cmmac,index,cmtsType));	
				  }
				 ModuleOptPool.shutdown();
				 while(!ModuleOptPool.isTerminated()){}
   		      }
			 endTime= System.currentTimeMillis();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e);
			}
			try {
				 logger.info("CM info time= "+(endTime-startTime)/1000+" s");
				 if(cycle*60* 1000-(endTime-startTime)<0){
					 cycle=0;
				 }else{
					 cycle=(int) (cycle*60* 1000-(endTime-startTime));
				 }
				Thread.sleep(cycle);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.info(e);
			}
	}
		logger.error("CM info down !");
  }
}

