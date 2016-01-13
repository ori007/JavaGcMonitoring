package LocalMonitoring;

import java.text.DecimalFormat;

public class GCData {
	static final DecimalFormat DoubleFormat = new DecimalFormat("#.##");
	
	double S0 = 0;
	double S1 = 0;
	double E = 0;
	double O = 0;
	double P = 0;
	
	long YGC = 0;
	double YGCT = 0;
	long FGC = 0;
	double FGCT = 0;
	double GCT = 0;
	double Heap = 0;

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
		setHeap(gcData.getHeap());
	}

	protected void setS0(double s) {
		S0 = s;
	}
	public double getS0() {
		return S0;
	}

	protected void setS1(double s) {
		S1 = s;
	}
	public double getS1() {
		return S1;
	}

	protected void setE(double e) {
		E = e;
	}
	public double getE() {
		return E;
	}

	protected void setO(double o) {
		O = o;
	}
	public double getO() {
		return O;
	}

	protected void setP(double p) {
		P = p;
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
	

	protected void setHeap(double t) {
		Heap = t;
	}

	public double getHeap() {
		return Heap;
	}
	
	
	public String getFGDuration() {
		double duration = (getFGC() == 0 ? 0 : getFGCT() / getFGC());

		return DoubleFormat.format(duration);
	}
	
}
