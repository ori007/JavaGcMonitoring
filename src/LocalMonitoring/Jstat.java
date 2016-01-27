package LocalMonitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.VmIdentifier;
import sun.tools.jstat.Arguments;
import sun.tools.jstat.OptionFormat;
import sun.tools.jstat.OptionOutputFormatter;
import sun.tools.jstat.OutputFormatter;


public class Jstat {
	static final DecimalFormat DoubleFormat = new DecimalFormat("#.##");
    private static long fgCount = 0;
	
	public static void main(String[] args) {

		//System.out.printf("Heap Usage : " + DoubleFormat.format(4.44444).toString() + "%%");
		
		if (null != args && args.length >= 2) {			
			LogTrace.writeScreenLog("Start PID : " +args[0]);
			Timer tm = new Timer();
			Thread t = new Thread(tm);
			
			tm.setPID(args[0]);
			tm.setInterval(Long.parseLong(args[1]));
			t.start();
		}
		else
		{
			LogTrace.writeScreenLog("Not Arguments ");
		}
	}	
	
	/**
	 * 타이머 쓰레드
	 *
	 */
	static class Timer implements Runnable {		
		private  long interval =0;		
		private String PID = "4445";

		public synchronized void setInterval(long interval) {
			this.interval = interval;
		}
		
		public synchronized  void setPID(String pid) {
			this.PID = pid;
		}
		
		
		@Override
		public void run() {
			
			//계속 초를 표시하기 위해서..
			while(true)
			{
				try {
					try {

						
						if (null != this.PID){
							
							GCUtil gcutil = Jstat.getGCUtil(this.PID);
							if (gcutil != null) {
//								System.out.println("FGC : " + gcutil.getFGC());
//								System.out.println("FGT : " + gcutil.getFGCT());
				
//								System.out.println("S0 : " + gcutil.getS0());
//								System.out.println("S1 : " + gcutil.getS1());
//								System.out.println("E : " + gcutil.getE());
//								System.out.println("O : " + gcutil.getO());
//								System.out.println("P : " + gcutil.getP());
								
								if (fgCount != gcutil.getFGC())
								{
									LogTrace.writeScreenLog("S0 : " + gcutil.getS0() + " S1 : " + gcutil.getS1() + " E : " + gcutil.getE() +" O : " + gcutil.getO() + " P : " + gcutil.getP());
									LogTrace.writeScreenLog("Heap Usage : " + DoubleFormat.format(gcutil.getHeapUsage()).toString() + "%%");
								}
								fgCount = gcutil.getFGC();
							}
						}

					} catch (Exception e) {
						LogTrace.writeExceptionLog(e);
					}
					
					// interval마다 슬립
					Thread.sleep(this.interval);
				
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static GCUtil getGCUtil(String pid) {
		Arguments arguments;
		
		GCUtil gcutil = null;
		try {
			String args[] = new String[4];
			args[0] = "-gcutil";
			args[1] = pid;
			args[2] = "100";
			args[3] = "1";
			arguments = new Arguments(args);

			gcutil = logSamples(arguments,args[0]);
			
		}  catch (Exception e) {
			LogTrace.writeExceptionLog(e);
		}
		
		return gcutil;
	}

	public static GCUtil getGC(String pid) {
		Arguments arguments;
		
		GCUtil gcutil = null;
		try {
			String args[] = new String[4];
			
			args[0] = "-gc";		
			args[1] = pid;
			args[2] = "1";
			args[3] = "1";
			arguments = new Arguments(args);
			
			gcutil = logSamples(arguments,args[0]);
		}  catch (Exception e) {
			LogTrace.writeExceptionLog(e);
		}
		
		return gcutil;
	}	
	
	static GCUtil logSamples(Arguments arguments,String option) {
		final VmIdentifier vmId	= arguments.vmId();
		int interval 			= arguments.sampleInterval();
		GCUtil gcutil 			= null;
		
		try {
			final MonitoredHost monitoredHost 	= MonitoredHost.getMonitoredHost(vmId);
			MonitoredVm monitoredVm 			= monitoredHost.getMonitoredVm(vmId, interval);
			OutputFormatter formatter			= null;
	
			OptionFormat format = arguments.optionFormat();
			formatter = new OptionOutputFormatter(monitoredVm, format);
			
			if (option.toLowerCase() == "-gcutil") {
				gcutil = GCUtil.parse(formatter.getRow().trim());
			}
			else {
				gcutil = GCUtil.gcParse(formatter.getRow().trim());
			}
			
			monitoredHost.detach(monitoredVm);
			
		} catch (MonitorException e) {
			if (e.getMessage() != null) {
				System.err.println(e.getMessage());
			} else {
				Throwable cause = e.getCause();
				if ((cause != null) && (cause.getMessage() != null)) {
					System.err.println(cause.getMessage());
				} else {
					e.printStackTrace();
				}
			}
			
			LogTrace.writeExceptionLog(e);
		}
		
		return gcutil;
	}
	
	public static boolean isRunningProcessId(String pid) {
	    
        boolean flagIsProcessId = false;
        try {
            //현재 로컬에서 수행되고 있는 java process 전체를 가져와서 확인
            MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");  
            Set<?> vmList = local.activeVms();
            
            for (Object id: vmList) {
                if(pid.equals(id.toString())) {
                    flagIsProcessId = true;
                    break;
                }
            }
            
        } catch (Exception e) {
            LogTrace.writeExceptionLog(e);

            flagIsProcessId = false;
        }
	    
        return flagIsProcessId;
	}
//	
//	public static GCUtil getJvmGCUtil(String pid) {
//	    
//	    GCUtil gcUtil = null;
//	    
//	    BufferedReader in = null;
//        try {
//            String[] arg = {"jstat", "-gcutil", pid, "1", "1"};
//            Process process = Runtime.getRuntime().exec(arg);
//
//            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//
//            /*
//             * ex)
//             *  JRE 1.6(1.7)
//             *  S0     S1     E      O      P     YGC     YGCT    FGC    FGCT     GCT
//             *  0.00  99.46  60.66   3.23  70.55      1    0.010     0    0.000    0.010
//             *  
//             *  JRE 1.8
//             *  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
//             *  0.00   0.00  15.67  86.45  93.29  83.70    187    1.484   119   32.037   33.522
//             */
//            String[] gcHeaders = null;
//            String str = in.readLine();
//            gcHeaders = str.split(" +");
//            
//            String[] gcInfos = null;
//            str = in.readLine();
//            gcInfos = str.split(" +");
//            
//            Map<String, String> gcInfoMap = new HashMap<String, String>();
//            for(int i=0; i<gcHeaders.length; i++) {
//                gcInfoMap.put(gcHeaders[i], gcInfos[i]);
//            }
//            
//            gcUtil = GCUtil.parseGcInfo(gcInfoMap);
//
//        } catch (IOException e) {
//            LogTrace.writeExceptionLog(e);
//            
//            gcUtil = null;
//        } finally {
//            if(in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {}
//            }
//        }
//        
//        return gcUtil;
//	}
}
