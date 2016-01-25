package com.scsvision.cms.common.dto;

import com.scsvision.cms.response.BaseDTO;

public class PackageVersionDTO extends BaseDTO {
	public Package ControlPackage;

	public Package getControlPackage() {
		return ControlPackage;
	}

	public void setControlPackage(Package controlPackage) {
		ControlPackage = controlPackage;
	}

	public class Package {
		private String name;
		private String url;
		private String version;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
	}

}
