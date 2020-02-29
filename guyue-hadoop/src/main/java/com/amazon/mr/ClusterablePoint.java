package com.amazon.mr;

public interface ClusterablePoint {
	double getDistance(ClusterablePoint other);

	String print();
}
