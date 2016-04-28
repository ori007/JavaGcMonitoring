package LocalMonitoring;

import java.util.Map;


// S0     S1     E      O      P     YGC     YGCT    FGC    FGCT     GCT
// 0.00   0.00   5.31  67.17  94.46    384    2.774   337   69.141   71.915

public class GCUtil extends GCData {
	String sampleRow = null;
	Map<String, String> gcInfoMap = null;
	
	String logingTime = null;
	
	private GCUtil(String sampleRow) {
		this.sampleRow = sampleRow;
	}
	
	private GCUtil(Map<String, String> gcInfoMap) {
	    this.gcInfoMap = gcInfoMap;
	}
	

	protected void gcparse() throws Exception {
		/* Windows 10, Mac,  jdk 1.8.0._45 
		 *  S0C       S1C       S0U    S1U      EC           EU        OC            OU           MC  MU CCSC CCSU   YGC   YGCT    FGC   FGCT   GCT  count ; 17
			20736.0 21120.0  0.0     3626.0  131776.0  9952.0   139456.0   119083.9    -    -    -       -        55      0.317   5      1.167   1.484
			
			CCSC: Compressed class space capacity (kB).
			
			CCSU: Compressed class space used (kB).
			
			Windows7 java 1.8.0_66 이하
		 *  S0C       S1C       S0U    S1U      EC           EU        OC            OU         PC         PU         YGC   YGCT    FGC  FGCT   GCT    count : 15
			20736.0 21120.0  0.0     3626.0  131776.0  9952.0   139456.0   119083.9  22016.0  21963.4  55     0.317   5      1.167   1.484
		 */
		String[] elements = sampleRow.split(" +");
		
		setS0C(Double.parseDouble(elements[0]));
		setS0U(Double.parseDouble(elements[2]));
		setS0(usagePersent(getS0C(),getS0U()));
		
		setS1C(Double.parseDouble(elements[1]));
		setS1U(Double.parseDouble(elements[3]));
		setS1(usagePersent(getS1C(),getS1U()));
		
		setEC(Double.parseDouble(elements[4]));
		setEU(Double.parseDouble(elements[5]));
		setE(usagePersent(getEC(),getEU()));
		
		setOC(Double.parseDouble(elements[6]));
		setOU(Double.parseDouble(elements[7]));
		setO(usagePersent(getOC(),getOU()));
		
		setPC(Double.parseDouble(elements[8]));
		setPU(Double.parseDouble(elements[9]));
		setP(usagePersent(getPC(),getPU()));	    
		
		if (elements.length == 17) {			
			setCCSC(Double.parseDouble(elements[10]));
			setCCSU(Double.parseDouble(elements[11]));			
			setYGC(Long.parseLong(elements[12]));
			setYGCT(Double.parseDouble(elements[13]));
			setFGC(Long.parseLong(elements[14]));
			setFGCT(Double.parseDouble(elements[15]));
			setGCT(Double.parseDouble(elements[16]));
		}
		else {
			setYGC(Long.parseLong(elements[10]));
			setYGCT(Double.parseDouble(elements[11]));
			setFGC(Long.parseLong(elements[12]));
			setFGCT(Double.parseDouble(elements[13]));
			setGCT(Double.parseDouble(elements[14]));
		}
		
		setSummaryHeap();
	    
		logingTime = DateTimeHandler.getDateTime();
	}
	 
	private void setSummaryHeap() {
	    double summaryHeap  = 0;
	    double sumCapacity = 0;
	    double sumUsage = 0;
	    
	    sumCapacity = getS0C() + getS1C() + getEC() + getOC() + getPC();
	    sumUsage = getS0U() + getS1U() + getEU() + getOU() + getPU();	    
	    summaryHeap = sumUsage / sumCapacity * 100.0;
	    
	    setHeapUsage(summaryHeap);
	}
	
	
	private Double usagePersent(Double size, Double usage){
		double ret = 0;
		
		if (size > 0&& usage > 0) {
			ret = usage / size * 100.0;
		}
		
		return  ret;
	}
	
	public static GCUtil gcParse(String sampleRow) {
		try
		{
			GCUtil gcutil = new GCUtil(sampleRow);
			gcutil.gcparse();
			
			return gcutil;
		} catch (Exception e) {
			LogTrace.writeTraceLog("GCInfo.parse : " + sampleRow);
			LogTrace.writeExceptionLog(e);
		}
		
		return null;
	}
	
	protected void parse() throws Exception {
		
		String[] elements = sampleRow.split(" +");

		setS0( Double.parseDouble(elements[0]));
		setS1( Double.parseDouble(elements[1]));
		setE( Double.parseDouble(elements[2]));
		setO( Double.parseDouble(elements[3]));
		setP( Double.parseDouble(elements[4]));

		setYGC( Long.parseLong(elements[5]));
		setYGCT( Double.parseDouble(elements[6]));
		setFGC(Long.parseLong(elements[7]));
		setFGCT( Double.parseDouble(elements[8]));    
		setGCT( Double.parseDouble(elements[9]));

	    double summaryHeap  = 0;
	    summaryHeap = getS0() + getS1() + getE() + getO() + getP();
	    summaryHeap = summaryHeap / 5;
	    setHeapUsage(summaryHeap);
				
		logingTime = DateTimeHandler.getDateTime();
	}

	public static GCUtil parse(String sampleRow) {
		try
		{
			GCUtil gcutil = new GCUtil(sampleRow);
			gcutil.parse();

			return gcutil;
		} catch (Exception e) {
			LogTrace.writeTraceLog("GCInfo.parse : " + sampleRow);
			LogTrace.writeExceptionLog(e);
		}
		
		return null;
	}
	
	public String getLoggingTime() {
		return logingTime;
	}
	
	public String toString() {
		return (sampleRow == null) ? "" : sampleRow;
	}
//	
//	public static GCUtil parseGcInfo(Map<String, String> gcInfoMap) {
//        try
//        {
//            GCUtil gcutil = new GCUtil(gcInfoMap);
//            gcutil.parseGcInfo();
//
//            return gcutil;
//        } catch (Exception e) {
//            LogTrace.writeTraceLog("GCInfo.parseGcInfo : " + gcInfoMap.toString());
//            LogTrace.writeExceptionLog(e);
//        }
//        
//        return null;
//    }
//	
//	protected void parseGcInfo() throws Exception {
//	    /*
//	     * ex)
//	     *  JRE 1.6(1.7)
//	     *  S0     S1     E      O      P     YGC     YGCT    FGC    FGCT     GCT
//	     *  0.00  99.46  60.66   3.23  70.55      1    0.010     0    0.000    0.010
//	     *  
//	     *  JRE 1.8
//	     *  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
//	     *  0.00   0.00  15.67  86.45  93.29  83.70    187    1.484   119   32.037   33.522
//	     */
//	    setS0( Double.parseDouble(gcInfoMap.get("S0")));
//	    setS1( Double.parseDouble(gcInfoMap.get("S1")));
//	    setE( Double.parseDouble(gcInfoMap.get("E")));
//	    setO( Double.parseDouble(gcInfoMap.get("O")));
//	    
//	    /* JRE 버젼에 따라 달라짐 */
//	    String pInfo = gcInfoMap.get("P"); 
//	    if(pInfo == null) {
//	        pInfo = gcInfoMap.get("M");
//	    }
//	    setP( Double.parseDouble(pInfo));
//
//	    setYGC( Long.parseLong(gcInfoMap.get("YGC")));
//	    setYGCT( Double.parseDouble(gcInfoMap.get("YGCT")));
//	    setFGC( Long.parseLong(gcInfoMap.get("FGC")));
//	    setFGCT( Double.parseDouble(gcInfoMap.get("FGCT")));
//	    setGCT( Double.parseDouble(gcInfoMap.get("GCT")));
//	    
//	    double summaryHeap  = 0;
//	    summaryHeap = getS0() + getS1() + getE() + getO() + getP();
//		
//	    setHeap(summaryHeap);
//	    
//	    logingTime = DateTimeHandler.getDateTime();
//	}
}
