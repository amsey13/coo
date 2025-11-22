package main.java.strategy;
import java.util.List;

import main.java.station.*;

public interface RedistributionStrategy {
	void redistribuer(List<Station> stations);
}
