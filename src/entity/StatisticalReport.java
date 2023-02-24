package entity;

import java.util.ArrayList;

public class StatisticalReport {
	public int average;
	public int median;
	public ArrayList<Integer> distribution;
	
	public StatisticalReport(int average, int median,
			ArrayList<Integer> distribution) {
		this.average = average;
		this.median = median;
		this.distribution = distribution;
	}

	public int getAverage() {
		return average;
	}

	public void setAverage(int average) {
		this.average = average;
	}

	public int getMedian() {
		return median;
	}

	public void setMedian(int median) {
		this.median = median;
	}

	public ArrayList<Integer> getDistribution() {
		return distribution;
	}

	public void setDistribution(ArrayList<Integer> distribution) {
		this.distribution = distribution;
	}

	@Override
	public String toString() {
		return "StatisticalReport [average=" + average + ", median=" + median + ", distribution=" + distribution + "]";
	}
}
