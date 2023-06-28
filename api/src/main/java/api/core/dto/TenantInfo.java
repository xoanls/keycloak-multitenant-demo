package api.core.dto;

public class TenantInfo extends TenantAuthInfo {
	String tenantId;
	String tenantName;

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(final String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(final String tenantName) {
		this.tenantName = tenantName;
	}
}
