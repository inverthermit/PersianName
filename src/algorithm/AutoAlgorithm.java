package algorithm;

import Model.Evaluation;

public abstract class AutoAlgorithm {
	
	public abstract String getDescription();
	public abstract Evaluation run();
	public abstract void init();

}
