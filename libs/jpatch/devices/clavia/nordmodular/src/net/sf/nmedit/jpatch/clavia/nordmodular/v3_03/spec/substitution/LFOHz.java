package net.sf.nmedit.jpatch.clavia.nordmodular.v3_03.spec.substitution;

import net.sf.nmedit.jmisc.math.Math2;

/**
 * @author Christian Schneider
 * @hidden
 */
public class LFOHz extends Substitution {

	public LFOHz() {
		super();
	}

	public String valueToString(int value) {
		double aFloat = 440.0 * Math.pow(2.0, (value-177)/12.0d);

		if (aFloat<0.1)
			return ""+Math2.roundTo(1/aFloat, -1)+" s";

		else if (aFloat<10)
			return ""+Math2.roundTo(aFloat, -2)+" Hz";
				
		else if (aFloat<100)
			return ""+Math2.roundTo(aFloat, -1)+" Hz";

		else
			return ""+Math2.roundTo(aFloat, 0)+" Hz";
	}

}
