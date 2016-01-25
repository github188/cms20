package com.scsvision.database.dao;

import javax.ejb.Local;

import com.scsvision.database.entity.PackageVersion;

@Local
public interface PackageVersionDAO extends BaseDAO<PackageVersion, Long> {
	
}
