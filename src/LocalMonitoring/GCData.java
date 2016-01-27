package LocalMonitoring;
import java.text.DecimalFormat;

public class GCData {
	static final DecimalFormat DoubleFormat = new DecimalFormat("#.##");
	
	double S0 = 0;	
	double S1 = 0;
	double E = 0;
	double O = 0;
	double P = 0;
	
	double S0C = 0;
	double S0U = 0;
	double S1C = 0;
	double S1U = 0;
	double EC = 0;
	double EU = 0;
	double OC = 0;
	double OU = 0;
	double PC = 0;
	double PU = 0;
	double CCSC =0;
	double CCSU = 0;
	
	long YGC = 0;
	double YGCT = 0;
	long FGC = 0;
	double FGCT = 0;
	double GCT = 0;
	double HeapUsage = 0;

	public GCData(){
	}
	
	public GCData(GCData gcData){
		setGCData( gcData);
	}
	
	public void setGCData(GCData gcData) {
		setS0( gcData.getS0());
		setS1( gcData.getS1());
		setE( gcData.getE());
		setO( gcData.getO());
		setP( gcData.getP());

		setYGC( gcData.getYGC());
		setYGCT( gcData.getYGCT());
		setFGC( gcData.getFGC());
		setFGCT( gcData.getFGCT());
		setGCT( gcData.getGCT());
		
		setS0C(gcData.getS0C());
		setS0U(gcData.getS0U());
		setS1C(gcData.getS1C());
		setS1U(gcData.getS1U());
		setEC(gcData.getEC());
		setEU(gcData.getEU());
		setOC(gcData.getOC());
		setOU(gcData.getOU());		
		setPC(gcData.getPC());
		setPU(gcData.getPU());
		setCCSC(gcData.getCCSC());
		setCCSU(gcData.getCCSU());
		
		setHeapUsage(gcData.getHeapUsage());
	}

	protected void setS0(double s) {
		String conversion = DoubleFormat.format(s);
		S0 = Double.parseDouble(conversion);
	}
	public double getS0() {
		return S0;
	}

	protected void setS1(double s) {
		String conversion = DoubleFormat.format(s);
		S1 = Double.parseDouble(conversion);
	}
	public double getS1() {
		return S1;
	}

	protected void setE(double e) {
		String conversion = DoubleFormat.format(e);
		E = Double.parseDouble(conversion);
	}
	public double getE() {
		return E;
	}

	protected void setO(double o) {
		String conversion = DoubleFormat.format(o);
		O = Double.parseDouble(conversion);
	}
	public double getO() {
		return O;
	}

	protected void setP(double p) {
		String conversion = DoubleFormat.format(p);
		P = Double.parseDouble(conversion);
	}
	public double getP() {
		return P;
	}

	protected void setYGC(long c) {
		YGC = c;
	}
	public long getYGC() {
		return YGC;
	}

	protected void setYGCT(double t) {
		YGCT = t;
	}
	public double getYGCT() {
		return YGCT;
	}

	protected void setFGC(long c) {
		FGC = c;
	}
	public long getFGC() {
		return FGC;
	}

	protected void setFGCT(double t) {
		FGCT = t;
	}
	public double getFGCT() {
		return FGCT;
	}

	protected void setGCT(double t) {
		GCT = t;
	}
	public double getGCT() {
		return GCT;
	}
	
	protected void setHeapUsage(double heap) {
		String heapusage = DoubleFormat.format(heap);
		HeapUsage = Double.parseDouble(heapusage);
	}
	public double getHeapUsage() {
		return HeapUsage;
	}
	
	protected void setS0C(double t) {
		S0C = t;
	}
	public double getS0C() {
		return S0C;
	}	

	protected void setS0U(double t) {
		S0U = t;
	}
	public double getS0U() {
		return S0U;
	}	

	protected void setS1C(double t) {
		S1C = t;
	}
	public double getS1C() {
		return S1C;
	}

	protected void setS1U(double t) {
		S1U = t;
	}
	public double getS1U() {
		return S1U;
	}

	protected void setEC(double t) {
		EC = t;
	}
	public double getEC() {
		return EC;
	}

	protected void setEU(double t) {
		EU = t;
	}
	public double getEU() {
		return EU;
	}
	
	protected void setOC(double t) {
		OC = t;
	}
	public double getOC() {
		return OC;
	}
	
	protected void setOU(double t) {
		OU = t;
	}
	public double getOU() {
		return OU;
	}
	
	protected void setPC(double t) {
		PC = t;
	}
	public double getPC() {
		return PC;
	}
	
	protected void setPU(double t) {
		PU = t;
	}
	public double getPU() {
		return PU;
	}	
	
	protected void setCCSC(double t) {
		CCSC = t;
	}
	public double getCCSC() {
		return CCSC;
	}
	
	protected void setCCSU(double t) {
		CCSU = t;
	}
	public double getCCSU() {
		return CCSU;
	}
		
	public String getFGDuration() {
		double duration = (getFGC() == 0 ? 0 : getFGCT() / getFGC());

		return DoubleFormat.format(duration);
	}
	
}
