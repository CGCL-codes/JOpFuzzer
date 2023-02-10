
package co.in;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class Setup {

	public static void main(String[] args) {
		ActiveXComponent ob=new ActiveXComponent("FP_CLOCK.FP_CLOCKCtrl.1");
	//ActiveXComponent obj=new ActiveXComponent("FP_CLOCK.FP_CLOCKCtrl.1");
       String Ip="http://192.168.1.254:80";
       System.out.println(ob);
      Dispatch.call(ob.getObject(),"SetIPAddress",Ip,5005,0);
    //  Dispatch.call(ob.getObject(),"OpenCommPort",2);
	}

}

