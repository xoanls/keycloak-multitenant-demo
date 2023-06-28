package model.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;
import com.ontimize.jee.common.multitenant.ITenantAuthenticationInfo;
import com.ontimize.jee.server.security.keycloak.OntimizeKeycloakConfigResolver;

import api.core.dto.TenantAuthInfo;
import api.core.dto.TenantInfo;
import api.core.service.ITenantService;

@Lazy
@Service("TenantsService")
public class TenantsService implements ITenantService {
	private static final String TENANT_ID = "tenant_id";
	private static final String URL = "url";
	private static final String REALM = "realm";
	private static final String CLIENT = "client";

	private static final String ERROR_TENANT_ID_REQUIRED = "Tenant Id is required";

	private final Map<String, ITenantAuthenticationInfo> serverAuthenticationMap = new HashMap<>();
	private final Map<String, TenantInfo> clientAuthenticationMap = new HashMap<>();

	@Autowired
	private void setOntimizeMultitenantKeycloakConfigResolver(final OntimizeKeycloakConfigResolver keycloakConfigResolver) {
		keycloakConfigResolver.setTenantsAuthenticationInfo(this.serverAuthenticationMap);
	}

	public TenantsService() {
		this.AddTenant("keycloak-demo", "Keycloak demo", "http://localhost:8888/auth", "keycloak-demo", "keycloak-demo-server", "keycloak-demo-ui");
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public EntityResult tenantQuery(final Map<?, ?> keysValues, final List<?> attributes) throws OntimizeJEERuntimeException {
		final EntityResult er = new EntityResultMapImpl(List.of(TenantsService.TENANT_ID,
				 TenantsService.URL, TenantsService.REALM, TenantsService.CLIENT));
		er.setColumnSQLTypes(Map.of(TenantsService.TENANT_ID, java.sql.Types.VARCHAR,
				TenantsService.URL, java.sql.Types.VARCHAR,
				TenantsService.REALM, java.sql.Types.VARCHAR,
				TenantsService.CLIENT, java.sql.Types.VARCHAR));

		final String tenantId = (String) keysValues.getOrDefault(TenantsService.TENANT_ID, null);
		if (tenantId == null) {
			throw new OntimizeJEERuntimeException(TenantsService.ERROR_TENANT_ID_REQUIRED);
		}

		if (this.clientAuthenticationMap.containsKey(tenantId)) {
			final TenantAuthInfo tenant = (TenantAuthInfo) this.clientAuthenticationMap.get(tenantId);

			final Map<String, Object> r = new HashMap<>();

			r.put(TenantsService.TENANT_ID, tenantId);
			r.put(TenantsService.URL, tenant.getUrl());
			r.put(TenantsService.REALM, tenant.getRealm());
			r.put(TenantsService.CLIENT, tenant.getClient());

			er.addRecord(r);
		}

		return er;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public TenantInfo getTenant(final String tenantId) {
		TenantInfo tenant = null;
	
		if (this.clientAuthenticationMap.containsKey(tenantId)) {
			tenant = this.clientAuthenticationMap.get(tenantId);
		}

		return tenant;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public List<TenantInfo> getUserTenants(final String userId) {
		return this.clientAuthenticationMap.values().stream().collect(Collectors.toList());
	}

	private void AddTenant(final String tenantId, final String tenantName, final String url, final String realm, final String backClient, final String frontClient) {
		final TenantAuthInfo backAuthInfo = new TenantAuthInfo();
		backAuthInfo.setUrl(url);
		backAuthInfo.setRealm(realm);
		backAuthInfo.setClient(backClient);
		this.serverAuthenticationMap.put(tenantId, backAuthInfo);

		final TenantInfo frontAuthInfo = new TenantInfo();
		frontAuthInfo.setTenantId(tenantId);
		frontAuthInfo.setTenantName(tenantName);
		frontAuthInfo.setUrl(url);
		frontAuthInfo.setRealm(realm);
		frontAuthInfo.setClient(frontClient);
		this.clientAuthenticationMap.put(tenantId, frontAuthInfo);
	}
}
