package fpt.com.component.strategy;

import fpt.com.core.component.BaseStrategy;

/**
 * BinaryStrategy
 */
public class BinaryStrategy extends BaseStrategy
        implements fpt.com.SerializableStrategy {

	@Override
	public String getDestinationFilename() {
		return "products.ser";
	}

	/**
	 * This is needed for the combobox
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "BinaryStrategy";
	}
}
