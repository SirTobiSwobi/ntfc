package org.SirTobiSwobi.c3.ntfc.core;

import org.SirTobiSwobi.c3.ntfc.db.WordEmbedding;

public class BamDistance extends DistanceMeasure{
	private BamFeatureExtractor fe;

	public BamDistance(WordEmbedding wordEmbedding) {
		super(wordEmbedding);
		fe=new BamFeatureExtractor(wordEmbedding);
		
	}

	@Override
	public double getDistance(String a, String b) {
		a = Utilities.sanitizeText(a);
		b = Utilities.sanitizeText(b);
		double[] vecA=fe.getVector(a);
		double[] vecB=fe.getVector(b);
		
		return Utilities.cosineDistance(vecA, vecB);
	}

}
