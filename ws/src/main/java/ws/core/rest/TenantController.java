package ws.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ontimize.jee.server.rest.ORestController;

import api.core.service.ITenantService;

@RestController
@RequestMapping("/tenants")
@ComponentScan(basePackageClasses={api.core.service.ITenantService.class})
public class TenantController extends ORestController<ITenantService> {

	private final ITenantService tenantService;

	@Override
	public ITenantService getService() {
		return this.tenantService;
	}

	@Autowired
	TenantController(final ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	@GetMapping(path = "/tenantInfo", produces = { "application/json" })
	public ResponseEntity<Object> getTenant(
			@RequestParam(name = "tenantId", required = true) final String tenantId) {
		try {
			return new ResponseEntity<>(this.tenantService.getTenant(tenantId), HttpStatus.OK);
		} catch (final Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/userTenants", produces = { "application/json" })
	public ResponseEntity<Object> getUserTenants(
			@RequestParam(name = "userId", required = true) final String userId) {
		try {
			return new ResponseEntity<>(this.tenantService.getUserTenants(userId), HttpStatus.OK);
		} catch (final Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
