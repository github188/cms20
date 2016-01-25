package com.scsvision.database.manager;

import java.util.Map;

import javax.ejb.Local;

import com.scsvision.database.entity.DicManufacture;

/**
 * @author sjt
 *
 */
@Local
public interface DicManufactureManager {
	public Map<Long, DicManufacture> getvDicManufactureList();
}
