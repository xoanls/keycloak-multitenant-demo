package api.core.dto;

import com.ontimize.jee.common.multitenant.ITenantAuthenticationInfo;

public class TenantAuthInfo implements ITenantAuthenticationInfo {
	String url;
	String realm;
	String client;

	public String getUrl() {
		return url;
	}
	public void setUrl(final String url) {
		this.url = url;
	}

	public String getRealm() {
		return realm;
	}
	public void setRealm(final String realm) {
		this.realm = realm;
	}

	public String getClient() {
		return client;
	}
	public void setClient(final String client) {
		this.client = client;
	}
}
