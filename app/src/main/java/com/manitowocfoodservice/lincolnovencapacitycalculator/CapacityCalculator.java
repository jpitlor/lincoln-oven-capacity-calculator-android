package com.manitowocfoodservice.lincolnovencapacitycalculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Jordan on 7/8/2015.
 */
public class CapacityCalculator {
	private final double BW;

	private final double OC;

	private final double BT;

	private final double PD;

	private final double PW;

	private final double PL;

	private final boolean isRound;

	public CapacityCalculator(double beltWidth, double ovenCapacity, double bakeTime,
	                          double panDiameter) {
		BW = beltWidth;
		OC = ovenCapacity;
		BT = bakeTime;
		PD = panDiameter;
		isRound = true;

		PL = 0;
		PW = 0;
	}

	public CapacityCalculator(double beltWidth, double ovenCapacity, double bakeTime,
	                          double panWidth, double panLength) {
		BW = beltWidth;
		OC = ovenCapacity;
		BT = bakeTime;
		PL = panLength;
		PW = panWidth;
		isRound = false;

		PD = 0;
	}

	public double calculateCapacity() {
		double result = isRound ? IRC() : ISC();
		return (double) Math.round(result * 10) / 10;
	}

	private double ROUNDVEL() {
		return OC / BT * 60;
	}

	private double ROUNDTBL() {
		return ROUNDVEL();
	}

	private double NSAP() {
		return (double) ((int) (BW / PD));
	}

	private double NSDP() {
		return (double) ((int) ((BW - PD) / (0.866 * PD) + 1));
	}

	private double NOSP() {
		return (double) ((int) Math.round(NSDP() / 2));
	}

	private double NESP() {
		return (double) ((int) Math.round((NSDP() - 1) / 2));
	}

	private double SORC() {
		return (double) ((int) Math.round(ROUNDTBL() / PD)) * NOSP();
	}

	private double SERC() {
		return (double) ((int) Math.round((ROUNDTBL() - (PD / 2)) / PD)) * NESP() + NESP();
	}

	private double VFA() {
		return Math.asin((BW - PD) / (2 * PD)) * 180 / Math.PI;
	}

	private double VSAO() {
		return 2 * Math.sqrt(Math.pow(PD, 2) - Math.pow((BW - PD) / 2, 2));
	}

	private double VOST() {
		return VFA() > 60 ? PD : VSAO();
	}

	private double VOOC() {
		return (double) ((int) (ROUNDTBL() / VOST())) * 2;
	}

	private double VOEB() {
		return ROUNDTBL() - (VOOC() / 2) * VOST();
	}

	private double VORC() {
		return VOEB() >= PD ? VOOC() + 2 : VOOC();
	}

	private double VMPO() {
		return (BW - PD) / (2 * Math.tan(VFA() * Math.PI / 180));
	}

	private double VMOC() {
		return (double) ((int) ((ROUNDTBL() - VMPO()) / VOST()));
	}

	private double VMEB() {
		return (ROUNDTBL() - VMPO()) - (VMOC() * VOST());
	}

	private double VMRC() {
		return VMEB() >= PD / 2 ? VMOC() + 1 : VMOC();
	}

	private double ZFA() {
		return Math.asin((BW - PD) / PD) * 180 / Math.PI;
	}

	private double ZSAO() {
		return 2 * Math.sqrt(Math.pow(PD, 2) - Math.pow(BW - PD, 2));
	}

	private double ZOST() {
		return ZFA() >= 60 ? PD : ZSAO();
	}

	private double ZFOC() {
		return (double) ((int) ROUNDTBL() / ZOST());
	}

	private double ZFEB() {
		return ROUNDTBL() - (ZFOC() * ZOST());
	}

	private double ZFC() {
		return ZFEB() >= PD / 2 ? ZFOC() + 1 : ZFOC();
	}

	private double ZSPO() {
		return (BW - PD) * Math.tan((Math.PI / 180) * (90 - ZFA()));
	}

	private double ZSOC() {
		return (double) ((int) ((ROUNDTBL() - ZSPO()) / ZOST()));
	}

	private double ZSEB() {
		return (ROUNDTBL() - ZSPO()) - (ZSOC() * ZOST());
	}

	private double ZSC() {
		return ZSEB() >= PD / 2 ? ZSOC() + 1 : ZSOC();
	}

	private double SDC() {
		return BW / PD < 1 ? 0 : SORC() + SERC();
	}

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

	private double IRC() {
		double max1 = Math.max(SDC(), VC());
		return Math.max(max1, ZC());
	}

	private double IXRC() {
		return IRC() * 2;
	}

	private double RECVEL() {
		return OC / BT * 60;
	}

	private double RECTBL() {
		return RECVEL();
	}

	private double NPLO() {
		return (double) ((int) (BW / PL));
	}

	private double NPWO() {
		return (double) ((int) (BW / PW));
	}

	private double PLC() {
		return BW / PW < 1 ? 0 : (double) ((int) (Math.round(RECTBL() / PL) * NPWO()));
	}

	private double PWC() {
		return BW / PL < 1 ? 0 : (double) ((int) (Math.round(RECTBL() / PW) * NPLO()));
	}

	private double ISC() {
		return Math.max(PLC(), PWC());
	}

	private double IXSC() {
		return ISC() * 2;
	}
}