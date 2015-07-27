package com.manitowocfoodservice.lincolnovencapacitycalculator;

/**
 * Coded by Jordan Pitlor, 2015
 * Formulas by Rob Townsend, 2002 (Updated by Christina Glaser, 2008)
 * <p/>
 * Please note that the figures above are potential capacity only and individual results will vary.
 * Actual capacity will fluctuate due to differences in environment, the type of product, ingredients,
 * finger kit, type of oven and oven temperature.
 */
public class CapacityCalculator {
	/**
	 * Belt Width
	 * <p/>
	 * in
	 */
	private final double BW;

	/**
	 * Oven Capacity
	 * <p/>
	 * in
	 */
	private final double OC;

	/**
	 * Bake Time
	 * <p/>
	 * min
	 */
	private final double BT;

	/**
	 * Pan Diameter
	 * <p/>
	 * in
	 */
	private final double PD;

	/**
	 * Pan Width
	 * <p/>
	 * in
	 */
	private final double PW;

	/**
	 * Pan Length
	 * <p/>
	 * in
	 */
	private final double PL;

	/**
	 * Used by the app to tell the calculator if the pan is round
	 * <p/>
	 * boolean
	 */
	private final boolean panIsRound;

	/**
	 * Constructor
	 *
	 * @param beltWidth    input - {@see BW}
	 * @param ovenCapacity input - {@see OC}
	 * @param bakeTime     input - {@see BT}
	 * @param panDiameter  input - {@see PD}
	 */
	public CapacityCalculator(double beltWidth, double ovenCapacity, double bakeTime,
	                          double panDiameter) {
		this.BW = beltWidth;
		this.OC = ovenCapacity;
		this.BT = bakeTime;
		this.PD = panDiameter;
		panIsRound = true;

		PL = 0;
		PW = 0;
	}

	/**
	 * Constructor
	 *
	 * @param beltWidth    input - {@see BW}
	 * @param ovenCapacity input - {@see OC}
	 * @param bakeTime     input - {@see BT}
	 * @param panWidth     input - {@see PW}
	 * @param panLength    input - {@see PL}
	 */
	public CapacityCalculator(double beltWidth, double ovenCapacity, double bakeTime,
	                          double panWidth, double panLength) {
		this.BW = beltWidth;
		this.OC = ovenCapacity;
		this.BT = bakeTime;
		this.PL = panLength;
		this.PW = panWidth;
		panIsRound = false;

		PD = 0;
	}

	/**
	 * Calculates the capacity of the oven
	 *
	 * @return pans/hr
	 */
	public double calculateCapacity() {
		double result = panIsRound ? IRC() : ISC();
		return (double) Math.round(result * 10) / 10;
	}

	/**
	 * Velocity for Round Pans
	 *
	 * @return in/hr
	 */
	private double CVEL() {
		return OC / BT * 60;
	}

	/**
	 * Total Belt Length for Round Pans
	 *
	 * @return in
	 */
	private double CTBL() {
		return CVEL();
	}

	/**
	 * Number of Straight Across Pans
	 *
	 * @return double
	 */
	private int NSAP() {
		return (int) (BW / PD);
	}

	/**
	 * Number of Rows of Sixty Degree Pans Across BW
	 *
	 * @return double
	 */
	private int NSDP() {
		return (int) ((BW - PD) / (Math.sin(60 * Math.PI / 180) * PD) + 1);
	}

	/**
	 * Number of Odd Rows of Sixty Degree Pans
	 *
	 * @return double
	 */
	private int NOSP() {
		return Math.round(NSDP() / 2);
	}

	/**
	 * Number Of Even Rows of Sixty Degree Pans
	 *
	 * @return double
	 */
	private double NESP() {
		return (double) ((int) Math.round((NSDP() - 1) / 2));
	}

	/**
	 * 60 Degree Odd Row Capacity
	 *
	 * @return pans/hr
	 */
	private double SORC() {
		return (double) ((int) Math.round(CTBL() / PD)) * NOSP();
	}

	/**
	 * 60 Degree Even Row Capacity
	 *
	 * @return pans/hr
	 */
	private double SERC() {
		return (double) ((int) Math.round((CTBL() - (PD / 2)) / PD)) * NESP() + NESP();
	}

	/**
	 * 3 Pans V Formation Angle
	 *
	 * @return degrees
	 */
	private double VFA() {
		return Math.asin((BW - PD) / (2 * PD)) * 180 / Math.PI;
	}

	/**
	 * 3 Pans V Formation Small Angle Offset
	 *
	 * @return in
	 */
	private double VSAO() {
		return 2 * Math.sqrt(Math.pow(PD, 2) - Math.pow((BW - PD) / 2, 2));
	}

	/**
	 * 3 Pans V Offset
	 *
	 * @return in
	 */
	private double VOST() {
		return VFA() > 60 ? PD : VSAO();
	}

	/**
	 * 3 Pans Outter Rows Offset Capacity
	 *
	 * @return pans/hr
	 */
	private int VOOC() {
		return (int) (CTBL() / VOST()) * 2;
	}

	/**
	 * 3 Pans V Outter Rows Extra Belt Length
	 *
	 * @return in
	 */
	private double VOEB() {
		return CTBL() - (VOOC() / 2) * VOST();
	}

	/**
	 * 3 Pans V Outter Rows Capacity
	 *
	 * @return pans/hr
	 */
	private double VORC() {
		return VOEB() >= PD ? VOOC() + 2 : VOOC();
	}

	/**
	 * 3 Pans V Middle Pan Offset
	 *
	 * @return in
	 */
	private double VMPO() {
		return (BW - PD) / (2 * Math.tan(VFA() * Math.PI / 180));
	}

	/**
	 * 3 Pans V Middle Pan Offset Capacity
	 *
	 * @return pans/hr
	 */
	private int VMOC() {
		return (int) ((CTBL() - VMPO()) / VOST());
	}

	/**
	 * 3 Pans V Middle Row Extra Belt Length
	 *
	 * @return in
	 */
	private double VMEB() {
		return (CTBL() - VMPO()) - (VMOC() * VOST());
	}

	/**
	 * 3 Pans V Middle Row Capacity
	 *
	 * @return pans/hr
	 */
	private double VMRC() {
		return VMEB() >= PD / 2 ? VMOC() + 1 : VMOC();
	}

	/**
	 * 2 Pans Zig-Zag Formation Angle
	 *
	 * @return degrees
	 */
	private double ZFA() {
		return Math.asin((BW - PD) / PD) * 180 / Math.PI;
	}

	/**
	 * 2 Pans Zig-Zag Small Angle Offset
	 *
	 * @return in
	 */
	private double ZSAO() {
		return 2 * Math.sqrt(Math.pow(PD, 2) - Math.pow(BW - PD, 2));
	}

	/**
	 * 2 Pans Zig-Zag Offset
	 *
	 * @return in
	 */
	private double ZOST() {
		return ZFA() >= 60 ? PD : ZSAO();
	}

	/**
	 * 2 Pans Zig-Zag First Row Offset Capacity
	 *
	 * @return pans/hr
	 */
	private int ZFOC() {
		return (int) (CTBL() / ZOST());
	}

	/**
	 * 2 Pans Zig-Zag First Row Extra Belt Length
	 *
	 * @return in
	 */
	private double ZFEB() {
		return CTBL() - (ZFOC() * ZOST());
	}

	/**
	 * 2 Pans Zig-Zag First Row Capacity
	 *
	 * @return pans/hr
	 */
	private double ZFC() {
		return ZFEB() >= PD / 2 ? ZFOC() + 1 : ZFOC();
	}

	/**
	 * 2 Pans Zig-Zag Second Pan Offset
	 *
	 * @return in
	 */
	private double ZSPO() {
		return (BW - PD) * Math.tan((Math.PI / 180) * (90 - ZFA()));
	}

	/**
	 * 2 Pans Zig-Zag Second Row Offset Capacity
	 *
	 * @return pans/hr
	 */
	private double ZSOC() {
		return (int) ((CTBL() - ZSPO()) / ZOST());
	}

	/**
	 * 2 Pans Zig-Zag Second Row Extra Belt Length
	 *
	 * @return in
	 */
	private double ZSEB() {
		return (CTBL() - ZSPO()) - (ZSOC() * ZOST());
	}

	/**
	 * 2 Pans Zig-Zag Second Row Capacity
	 *
	 * @return pans/hr
	 */
	private double ZSC() {
		return ZSEB() >= PD / 2 ? ZSOC() + 1 : ZSOC();
	}

	/**
	 * Sixty Degree Capacity
	 *
	 * @return pans/hr
	 */
	private double SDC() {
		return BW / PD < 1 ? 0 : SORC() + SERC();
	}

	/**
	 * 3 Pans V Capacity
	 *
	 * @return pans/hr
	 */
	private double VC() {
		if (BW / PD <= 2) {
			return 0;
		} else {
			if (BW / PD >= 3) {
				return 0;
			} else {
				return VORC() + VMRC();
			}
		}
	}

	/**
	 * 2 Pans Zig-Zag Capacity
	 *
	 * @return pans/hr
	 */
	private double ZC() {
		if (BW / PD <= 1) {
			return 0;
		} else {
			if (BW / PD >= 2) {
				return 0;
			} else {
				return ZFC() + ZSC();
			}
		}
	}

	/**
	 * Impinger Round Sheet Capacity
	 *
	 * @return pans/hr
	 */
	private double IRC() {
		double max1 = Math.max(SDC(), VC());
		return Math.max(max1, ZC());
	}

	/**
	 * Velocity for rectangular pans
	 *
	 * @return in/hr
	 */
	private double RVEL() {
		return OC / BT * 60;
	}

	/**
	 * Total Belt Length for rectangular pans
	 *
	 * @return in
	 */
	private double RTBL() {
		return RVEL();
	}

	/**
	 * Number of Pans Straiht Across Length Orientation
	 *
	 * @return integer
	 */
	private int NPLO() {
		return (int) (BW / PL);
	}

	/**
	 * Number of Pans Straight Across Width Orientation
	 *
	 * @return integer
	 */
	private int NPWO() {
		return (int) (BW / PW);
	}

	/**
	 * Pan Length Capacity
	 *
	 * @return pans/hr
	 */
	private double PLC() {
		return BW / PW < 1 ? 0 : (double) ((int) (Math.round(RTBL() / PL) * NPWO()));
	}

	/**
	 * Pan Width Capacity
	 *
	 * @return pans/hr
	 */
	private double PWC() {
		return BW / PL < 1 ? 0 : (double) ((int) (Math.round(RTBL() / PW) * NPLO()));
	}

	/**
	 * Impinger Rectangular Sheet Capacity
	 *
	 * @return pans/hr
	 */
	private double ISC() {
		return Math.max(PLC(), PWC());
	}
}