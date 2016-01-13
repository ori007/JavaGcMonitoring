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
	
	protected void parse() throws Exception {
		
		String[] elements = sampleRow.split(" +");

		setS0( Double.parseDouble(elements[0]));
		setS1( Double.parseDouble(elements[1]));
		setE( Double.parseDouble(elements[2]));
		setO( Double.parseDouble(elements[3]));
		setP( Double.parseDouble(elements[4]));

		setYGC( Long.parseLong(elements[5]));
		setYGCT( Double.parseDouble(elements[6]));
		setFGC( Long.parseLong(elements[7]));
		setFGCT( Double.parseDouble(elements[8]));    
		setGCT( Double.parseDouble(elements[9]));

	    double summaryHeap  = 0;
	    summaryHeap = getS0() + getS1() + getE() + getO() + getP();
	    summaryHeap = summaryHeap / 5;
	    setHeap(summaryHeap);
				
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
	
	public static GCUtil parseGcInfo(Map<String, String> gcInfoMap) {
        try
        {
            GCUtil gcutil = new GCUtil(gcInfoMap);
            gcutil.parseGcInfo();

            return gcutil;
        } catch (Exception e) {
            LogTrace.writeTraceLog("GCInfo.parseGcInfo : " + gcInfoMap.toString());
            LogTrace.writeExceptionLog(e);
        }
        
        return null;
    }
	
	protected void parseGcInfo() throws Exception {
	    /*
	     * ex)
	     *  JRE 1.6(1.7)
	     *  S0     S1     E      O      P     YGC     YGCT    FGC    FGCT     GCT
	     *  0.00  99.46  60.66   3.23  70.55      1    0.010     0    0.000    0.010
	     *  
	     *  JRE 1.8
	     *  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
	     *  0.00   0.00  15.67  86.45  93.29  83.70    187    1.484   119   32.037   33.522
	     */
	    setS0( Double.parseDouble(gcInfoMap.get("S0")));
	    setS1( Double.parseDouble(gcInfoMap.get("S1")));
	    setE( Double.parseDouble(gcInfoMap.get("E")));
	    setO( Double.parseDouble(gcInfoMap.get("O")));
	    
	    /* JRE 버젼에 따라 달라짐 */
	    String pInfo = gcInfoMap.get("P"); 
	    if(pInfo == null) {
	        pInfo = gcInfoMap.get("M");
	    }
	    setP( Double.parseDouble(pInfo));

	    setYGC( Long.parseLong(gcInfoMap.get("YGC")));
	    setYGCT( Double.parseDouble(gcInfoMap.get("YGCT")));
	    setFGC( Long.parseLong(gcInfoMap.get("FGC")));
	    setFGCT( Double.parseDouble(gcInfoMap.get("FGCT")));
	    setGCT( Double.parseDouble(gcInfoMap.get("GCT")));
	    
	    double summaryHeap  = 0;
	    summaryHeap = getS0() + getS1() + getE() + getO() + getP();
		
	    setHeap(summaryHeap);
	    
	    logingTime = DateTimeHandler.getDateTime();
	}
}
