package api.core.service;

import java.util.List;
import java.util.Map;

import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.exceptions.OntimizeJEERuntimeException;

import api.core.dto.TenantInfo;

public interface ITenantService {
	/**
	 * Tenant query.
	 *
	 * @param keysValues
	 *            the keys values
	 * @param attributes
	 *            the attributes
	 * @return the entity result
	 * @throws Exception
	 *             the exception
	 */
	EntityResult tenantQuery(Map<?, ?> keysValues, List<?> attributes) throws OntimizeJEERuntimeException;

	/**
	 * Get tenant.
	 *
	 * @param tenantId
	 *            the tenant id
	 * @return the configuration for authentication
	 */
	TenantInfo getTenant(String tenantId);

	/**
	 * Get user tenants.
	 *
	 * @param usertId
	 *            the user id
	 * @return the list of configurations for authentication
	 */
	List<TenantInfo> getUserTenants(String userId);
}
